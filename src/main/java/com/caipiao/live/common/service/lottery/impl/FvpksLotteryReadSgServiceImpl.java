package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryInformationType;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.StatusCode;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.FivebjpksLotterySg;
import com.caipiao.live.common.mybatis.entity.FivebjpksLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.FivebjpksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperbean.FivepksBeanMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.FivebjpksLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.FvpksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JspksLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.StringUtils;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.FivepksUtils;
import com.caipiao.live.common.util.lottery.JspksSgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 5???PK10
 *
 * @author Asheng
 * @create 2019-03-13 11:05
 **/
@Service
public class FvpksLotteryReadSgServiceImpl implements FvpksLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(FvpksLotteryReadSgServiceImpl.class);
    @Autowired
    private FivepksBeanMapper fivepksBeanMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private FivebjpksLotterySgMapper fivebjpksLotterySgMapper;
    @Autowired
    private FivebjpksLotterySgMapperExt fivebjpksLotterySgMapperExt;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
    @Autowired
    private JspksLotterySgServiceReadSg jspksLotterySgService;

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullPkResult();
        try {
            // ????????????????????????
            String redisKey = RedisKeys.FIVEPKS_RESULT_VALUE;
            FivebjpksLotterySg fivebjpksLotterySg = (FivebjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (fivebjpksLotterySg == null) {
                fivebjpksLotterySg = this.getFivebjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, fivebjpksLotterySg);
            }

            if (fivebjpksLotterySg != null) {
                result.put(AppMianParamEnum.ISSUE.getParamEnName(), fivebjpksLotterySg == null ? Constants.DEFAULT_NULL : fivebjpksLotterySg.getIssue());
                result.put(AppMianParamEnum.NUMBER.getParamEnName(), fivebjpksLotterySg == null ? Constants.DEFAULT_NULL : fivebjpksLotterySg.getNumber());
            }
            // ????????????????????????
            String openRedisKey = RedisKeys.FIVEPKS_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("openStatus", LotteryResultStatus.AUTO);
                map.put("paramTime", TimeHelper.date("yyyy-MM-dd") + "%");
                openCount = fivebjpksLotterySgMapperExt.openCountByExample(map);
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }

            if (openCount != null) {
                result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
                // ?????????????????????
                Integer sumCount = CaipiaoSumCountEnum.FIVEPKS.getSumCount();
                // ?????????????????????????????????
                result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            }
            String nextRedisKey = RedisKeys.FIVEPKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.FIVEPKS.getRedisTime();
            FivebjpksLotterySg nextFivebjpksLotterySg = (FivebjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextFivebjpksLotterySg == null) {
                nextFivebjpksLotterySg = this.getNextFivebjpksLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextFivebjpksLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextFivebjpksLotterySg != null) {
                String nextIssue = nextFivebjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextFivebjpksLotterySg.getIssue();
                String txffnextIssue = fivebjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : fivebjpksLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    if (nextIssue.compareTo(txffnextIssue) < 0 || nextIssue.compareTo(txffnextIssue) > 2) {
                        nextFivebjpksLotterySg = this.getNextFivebjpksLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextFivebjpksLotterySg, redisTime, TimeUnit.MINUTES);
                        // ????????????????????????
                        fivebjpksLotterySg = this.getFivebjpksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, fivebjpksLotterySg);
                    }
                }

                if (nextFivebjpksLotterySg != null) {
                    // ???????????????????????????
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFivebjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFivebjpksLotterySg.getIdealTime()) / 1000L);
                }
            } else {
                if (nextFivebjpksLotterySg != null) {
                    // ???????????????????????????
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFivebjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFivebjpksLotterySg.getIdealTime()) / 1000L);
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids: " + CaipiaoTypeEnum.FIVEPKS.getTagType() + " ????????? ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }


    /**
     * @return FivebjpksLotterySg
     * @Title: getFivebjpksLotterySg
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???3?????????1:23:27
     */
    public FivebjpksLotterySg getFivebjpksLotterySg() {
        FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
        FivebjpksLotterySgExample.Criteria fivebjpksCriteria = example.createCriteria();
        fivebjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        FivebjpksLotterySg fivebjpksLotterySg = fivebjpksLotterySgMapper.selectOneByExample(example);
        return fivebjpksLotterySg;
    }

    /**
     * @return FivebjpksLotterySg
     * @Title: getNextFivebjpksLotterySg
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???4???29?????????9:26:32
     */
    public FivebjpksLotterySg getNextFivebjpksLotterySg() {
        FivebjpksLotterySgExample nextExample = new FivebjpksLotterySgExample();
        FivebjpksLotterySgExample.Criteria nextFivebjpksCriteria = nextExample.createCriteria();
        nextFivebjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        nextFivebjpksCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
        nextExample.setOrderByClause("issue ASC");
        FivebjpksLotterySg nextFivebjpksLotterySg = this.fivebjpksLotterySgMapper.selectOneByExample(nextExample);
        return nextFivebjpksLotterySg;
    }


    @Override
    public ResultInfo<List<MapListVO>> todayNumber(String type) {
        List<MapListVO> voList = new ArrayList<>();

        // ????????????
        if (!LotteryInformationType.FIVEPKS_JRHM.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        String date = TimeHelper.date("yyyy-MM-dd");
        List<String> sgs = fivepksBeanMapper.selectNumberByDate(date + "%");
        // ??????
        if (CollectionUtils.isEmpty(sgs)) {
            return ResultInfo.ok(voList);
        }

        // ????????????
        voList = FivepksUtils.todayNumber(sgs);
        return ResultInfo.ok(voList);
    }

    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> fivepksLongMapList, List<FivebjpksLotterySg> fivePksLotteryList) {
        List<Map<String, Object>> fivepksResultLongMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(fivepksLongMapList)) {
            // ??????????????????
            String nextRedisKey = RedisKeys.FIVEPKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.FIVEPKS.getRedisTime();
            FivebjpksLotterySg nextFivebjpksLotterySg = (FivebjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextFivebjpksLotterySg == null) {
                nextFivebjpksLotterySg = this.getNextFivebjpksLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextFivebjpksLotterySg, 10, TimeUnit.SECONDS);
            }

            if (nextFivebjpksLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // ????????????????????????
            String redisKey = RedisKeys.FIVEPKS_RESULT_VALUE;
            FivebjpksLotterySg fivebjpksLotterySg = (FivebjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (fivebjpksLotterySg == null) {
                fivebjpksLotterySg = this.getFivebjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, fivebjpksLotterySg);
            }
            String nextIssue = nextFivebjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextFivebjpksLotterySg.getIssue();
            String txffnextIssue = fivebjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : fivebjpksLotterySg.getIssue();

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // ????????????????????????????????????????????????????????????
                if (differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }

            for (Map<String, Object> longMap : fivepksLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFivebjpksLotterySg.getIssue());
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFivebjpksLotterySg.getIdealTime()) / 1000L);
                fivepksResultLongMapList.add(longMap);
            }
        }
        return fivepksResultLongMapList;
    }

    /**
     * @return List<Map < String, Object>>
     * @Title: getBigAndSmallLong
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???13?????????10:49:38
     */
    public List<Map<String, Object>> getBigAndSmallLong(List<FivebjpksLotterySg> fivePksLotteryList) {
        List<Map<String, Object>> jspksBigLongMapList = new ArrayList<Map<String, Object>>();
        //???????????????
        Map<String, Object> gyhDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGYHBIG.getTagType());
        //????????????
        Map<String, Object> gjbDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGJBIG.getTagType());
        //????????????
        Map<String, Object> yjbDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSYJBIG.getTagType());
        //???????????????
        Map<String, Object> dsmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDSMBIG.getTagType());
        //???????????????
        Map<String, Object> dfmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDFMBIG.getTagType());
        //???????????????
        Map<String, Object> dwmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDWMBIG.getTagType());
        //???????????????
        Map<String, Object> dlmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDLMBIG.getTagType());
        //???????????????
        Map<String, Object> dqmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDQMBIG.getTagType());
        //???????????????
        Map<String, Object> dmmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDMMBIG.getTagType());
        //???????????????
        Map<String, Object> djmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDJMBIG.getTagType());
        //???????????????
        Map<String, Object> dtmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDTMBIG.getTagType());

        // ????????????????????????????????????
        if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(gyhDragonMap);
        }

        if (gjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(gjbDragonMap);
        }

        if (yjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(yjbDragonMap);
        }

        if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(dsmDragonMap);
        }

        if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(dfmDragonMap);
        }

        if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(dwmDragonMap);
        }

        if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(dlmDragonMap);
        }

        if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(dqmDragonMap);
        }

        if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(dmmDragonMap);
        }

        if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(djmDragonMap);
        }

        if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksBigLongMapList.add(dtmDragonMap);
        }
        return jspksBigLongMapList;
    }

    /**
     * @return List<Map < String, Object>>
     * @Title: getDoubleAndSingleLong
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???13?????????10:50:31
     */
    public List<Map<String, Object>> getDoubleAndSingleLong(List<FivebjpksLotterySg> fivePksLotteryList) {
        List<Map<String, Object>> jspksDoubleLongMapList = new ArrayList<Map<String, Object>>();
        //???????????????
        Map<String, Object> gyhDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGYHDOUBLE.getTagType());
        //????????????
        Map<String, Object> gjDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGJDOUBLE.getTagType());
        //????????????
        Map<String, Object> syjDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSYJDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dsmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDSMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dfmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDFMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dwmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDWMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dlmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDLMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dqmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDQMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dmmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDMMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> djmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDJMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dtmDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDTMDOUBLE.getTagType());

        if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(gyhDragonMap);
        }

        if (gjDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(gjDragonMap);
        }

        if (syjDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(syjDragonMap);
        }

        if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(dsmDragonMap);
        }

        if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(dfmDragonMap);
        }

        if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(dwmDragonMap);
        }

        if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(dlmDragonMap);
        }

        if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(dqmDragonMap);
        }

        if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(dmmDragonMap);
        }

        if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(djmDragonMap);
        }

        if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksDoubleLongMapList.add(dtmDragonMap);
        }
        return jspksDoubleLongMapList;
    }

    /**
     * @return List<Map < String, Object>>
     * @Title: getDoubleAndSingleLong
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???13?????????10:53:00
     */
    public List<Map<String, Object>> getTrigleAndDragonLong(List<FivebjpksLotterySg> fivePksLotteryList) {
        List<Map<String, Object>> jspksTrigLongMapList = new ArrayList<Map<String, Object>>();
        //????????????
        Map<String, Object> gjtiDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGJTIDRAGON.getTagType());
        //????????????
        Map<String, Object> yjtiDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSYJTIDRAGON.getTagType());
        //???????????????
        Map<String, Object> dsmtiDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDSMTIDRAGON.getTagType());
        //???????????????
        Map<String, Object> dfmtiDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDFMTIDRAGON.getTagType());
        //???????????????
        Map<String, Object> dwmtiDragonMap = this.getDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDWMTIDRAGON.getTagType());

        if (gjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksTrigLongMapList.add(gjtiDragonMap);
        }

        if (yjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksTrigLongMapList.add(yjtiDragonMap);
        }

        if (dsmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksTrigLongMapList.add(dsmtiDragonMap);
        }

        if (dfmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksTrigLongMapList.add(dfmtiDragonMap);
        }

        if (dwmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            jspksTrigLongMapList.add(dwmtiDragonMap);
        }
        return jspksTrigLongMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: ?????????????????????????????????
     * @author HANS
     * @date 2019???5???18?????????11:18:00
     */
    public Map<String, Object> getDragonInfo(List<FivebjpksLotterySg> fivePksLotteryList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(fivePksLotteryList)) {
                // ????????????
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < fivePksLotteryList.size(); index++) {
                    FivebjpksLotterySg fivebjpksLotterySg = fivePksLotteryList.get(index);
                    // ????????????????????????
                    String bigOrSmallName = this.calculateResult(type, fivebjpksLotterySg);

                    if (StringUtils.isEmpty(bigOrSmallName)) {
                        break;
                    }
                    // ????????????????????????SET??????
                    if (index == Constants.DEFAULT_INTEGER) {
                        dragonSet.add(bigOrSmallName);
                    }
                    // ???????????????????????????????????????????????????????????????
                    if (index == Constants.DEFAULT_ONE) {
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        continue;
                    }
                    // ???????????????3???????????????
                    if (index == Constants.DEFAULT_TWO) {
                        // ???????????????
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        dragonSize = Constants.DEFAULT_THREE;
                        continue;
                    }
                    // ????????????3????????????????????????????????????????????????
                    if (index > Constants.DEFAULT_TWO) {
                        // ???/?????????
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // ???/?????????????????????????????????
                            break;
                        }
                        dragonSize++;
                    }
                }
                // ????????????????????????
                FivebjpksLotterySg fivebjpksLotterySg = fivePksLotteryList.get(Constants.DEFAULT_INTEGER);
                // ??????????????????
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type, fivebjpksLotterySg, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#FvpksLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: organizationDragonResultMap
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???5???18?????????11:32:14
     */
    public Map<String, Object> organizationDragonResultMap(int type, FivebjpksLotterySg fivebjpksLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // ?????????????????????????????????
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // ????????????PK10    ?????? ????????????
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.FIVEPKS.getTagCnName(), CaipiaoPlayTypeEnum.FIVEPKSGYHBIG.getPlayName(),
                    CaipiaoTypeEnum.FIVEPKS.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSGYHBIG.getTagType() + "");

            List<String> dragonList = new ArrayList<String>(dragonSet);
            // ????????????
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if (type == 120) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGYHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGYHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGYHBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGBIG);
            } else if (type == 121) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 122) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 123) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 124) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 125) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 126) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDLMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDLMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDLMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 127) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDQMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDQMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDQMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 128) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDMMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDMMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDMMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 129) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDJMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDJMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDJMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 130) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDTMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDTMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDTMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 131) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGYHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGYHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGYHDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGDOUBLE);
            } else if (type == 132) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 133) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 134) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 135) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 136) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 137) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDLMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDLMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDLMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 138) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDQMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDQMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDQMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 139) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDMMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDMMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDMMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 140) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDJMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDJMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDJMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 141) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDTMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDTMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDTMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 142) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSGJTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 143) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSYJTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 144) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDSMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 145) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDFMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 146) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVEPKSDWMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            }
            // ?????????????????????????????????MAP???
            longDragonMap.putAll(oddsListMap);
            // ?????????????????????
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.FIVEPKS.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.FIVEPKS.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonList.get(Constants.DEFAULT_INTEGER));
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#FvpksLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: calculateResult
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???18?????????11:22:00
     */
    public String calculateResult(int type, FivebjpksLotterySg fivebjpksLotterySg) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 120:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 121:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//????????????
                break;
            case 122:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//????????????
                break;
            case 123:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 124:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 125:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 126:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 127:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 128:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 129:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 130:
                result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 131:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 132:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//????????????
                break;
            case 133:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//????????????
                break;
            case 134:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 135:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 136:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 137:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 138:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 139:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 140:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 141:
                result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 142:
                result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(), type);//????????????
                break;
            case 143:
                result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(), type);//????????????
                break;
            case 144:
                result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 145:
                result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            case 146:
                result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(), type);//???????????????
                break;
            default:
                break;
        }
        return result;
    }


    /**
     * @Title: selectFivebjpksByIssue
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???17?????????11:14:49
     */
    public List<FivebjpksLotterySg> selectFivebjpksByIssue() {
        FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
        FivebjpksLotterySgExample.Criteria fivebjpksCriteria = example.createCriteria();
        fivebjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<FivebjpksLotterySg> fivebjpksLotterySgList = fivebjpksLotterySgMapper.selectByExample(example);
        return fivebjpksLotterySgList;
    }

    @Override
    public List<Map<String, Object>> getFivePksSgLong() {
        List<Map<String, Object>> fivepksLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.FIVEPKS_ALGORITHM_VALUE;
            List<FivebjpksLotterySg> fivePksLotteryList = (List<FivebjpksLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(fivePksLotteryList)) {
                fivePksLotteryList = this.selectFivebjpksByIssue();
                redisTemplate.opsForValue().set(algorithm, fivePksLotteryList, 10, TimeUnit.SECONDS);
            }
            // ????????????
            List<Map<String, Object>> bigAndSmallLongList = this.getBigAndSmallLong(fivePksLotteryList);
            fivepksLongMapList.addAll(bigAndSmallLongList);
            // ????????????
            List<Map<String, Object>> sigleAndDoubleLongList = this.getDoubleAndSingleLong(fivePksLotteryList);
            fivepksLongMapList.addAll(sigleAndDoubleLongList);
            // ????????????
            List<Map<String, Object>> dragonAndTigleLongList = this.getTrigleAndDragonLong(fivePksLotteryList);
            fivepksLongMapList.addAll(dragonAndTigleLongList);
            fivepksLongMapList = this.addNextIssueInfo(fivepksLongMapList, fivePksLotteryList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#FvpksLotterySgServiceImpl_getJspksSgLong_error:", e);
        }
        return fivepksLongMapList;
    }

}
