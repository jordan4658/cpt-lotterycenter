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
import com.caipiao.live.common.mybatis.entity.TenbjpksLotterySg;
import com.caipiao.live.common.mybatis.entity.TenbjpksLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.TenbjpksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperbean.TenpksBeanMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.TenbjpksLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JspksLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.TenpksLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.StringUtils;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.JspksSgUtils;
import com.caipiao.live.common.util.lottery.TenpksUtils;
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
 * 10???PK10
 *
 * @author Asheng
 * @create 2019-03-13 11:05
 **/
@Service
public class TenpksLotteryReadSgServiceImpl implements TenpksLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(TenpksLotteryReadSgServiceImpl.class);
    @Autowired
    private TenpksBeanMapper tenpksBeanMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TenbjpksLotterySgMapper tenbjpksLotterySgMapper;
    @Autowired
    private TenbjpksLotterySgMapperExt tenbjpksLotterySgMapperExt;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
    @Autowired
    private JspksLotterySgServiceReadSg jspksLotterySgService;

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullPkResult();
        try {
            // ????????????????????????
            String redisKey = RedisKeys.TENPKS_RESULT_VALUE;
            TenbjpksLotterySg tenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (tenbjpksLotterySg == null) {
                // ????????????????????????
                tenbjpksLotterySg = this.getTenbjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, tenbjpksLotterySg);
            }

            // ????????????????????????
            String openRedisKey = RedisKeys.TENPKS_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("openStatus", LotteryResultStatus.AUTO);
                map.put("paramTime", TimeHelper.date("yyyy-MM-dd") + "%");
                openCount = tenbjpksLotterySgMapperExt.openCountByExample(map);
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }
            if (openCount != null) {
                result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
                // ?????????????????????
                Integer sumCount = CaipiaoSumCountEnum.TENPKS.getSumCount();
                // ?????????????????????????????????
                result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            }
            String nextRedisKey = RedisKeys.TENPKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.TENPKS.getRedisTime();
            TenbjpksLotterySg nextTenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextTenbjpksLotterySg == null) {
                nextTenbjpksLotterySg = this.getNextTenbjpksLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextTenbjpksLotterySg, redisTime, TimeUnit.MINUTES);
            }
            if (nextTenbjpksLotterySg != null) {
                String nextIssue = nextTenbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL
                        : String.valueOf(nextTenbjpksLotterySg.getIssue());
                String txffnextIssue = tenbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL
                        : String.valueOf(tenbjpksLotterySg.getIssue());

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    if (nextIssue.compareTo(txffnextIssue) < 0 || nextIssue.compareTo(txffnextIssue) > 2) {
                        nextTenbjpksLotterySg = this.getNextTenbjpksLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextTenbjpksLotterySg, redisTime,
                                TimeUnit.MINUTES);
                        // ????????????????????????
                        tenbjpksLotterySg = this.getTenbjpksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, tenbjpksLotterySg);
                    }
                }

                if (tenbjpksLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(),
                            tenbjpksLotterySg == null ? Constants.DEFAULT_NULL : tenbjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(),
                            tenbjpksLotterySg == null ? Constants.DEFAULT_NULL : tenbjpksLotterySg.getNumber());
                }

                if (nextTenbjpksLotterySg != null) {
                    // ???????????????????????????
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTenbjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
                            DateUtils.getTimeMillis(nextTenbjpksLotterySg.getIdealTime()) / 1000L);
                }
            } else {

                if (tenbjpksLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(),
                            tenbjpksLotterySg == null ? Constants.DEFAULT_NULL : tenbjpksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(),
                            tenbjpksLotterySg == null ? Constants.DEFAULT_NULL : tenbjpksLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.TENPKS.getTagType() + " ????????? ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }

    public TenbjpksLotterySg getTenbjpksLotterySg() {
        TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
        TenbjpksLotterySgExample.Criteria tenbjpksCriteria = example.createCriteria();
        tenbjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        TenbjpksLotterySg tenbjpksLotterySg = tenbjpksLotterySgMapper.selectOneByExample(example);
        return tenbjpksLotterySg;
    }

    /**
     * @return TenbjpksLotterySg
     * @Title: getTenbjpksLotterySg
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???4???29?????????9:24:21
     */
    public TenbjpksLotterySg getNextTenbjpksLotterySg() {
        TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
        TenbjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
        bjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        bjpksCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        example.setOrderByClause("issue ASC");
        TenbjpksLotterySg nextTenbjpksLotterySg = tenbjpksLotterySgMapper.selectOneByExample(example);
        return nextTenbjpksLotterySg;
    }


    @Override
    public ResultInfo<List<MapListVO>> todayNumber(String type) {
        List<MapListVO> voList = new ArrayList<>();

        // ????????????
        if (!LotteryInformationType.TENPKS_JRHM.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        String date = TimeHelper.date("yyyy-MM-dd");
        List<String> sgs = tenpksBeanMapper.selectNumberByDate(date + "%");
        // ??????
        if (CollectionUtils.isEmpty(sgs)) {
            return ResultInfo.ok(voList);
        }

        // ????????????
        voList = TenpksUtils.todayNumber(sgs);
        return ResultInfo.ok(voList);
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: ??????
     * @author HANS
     * @date 2019???5???26?????????2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> tenpksLongMapList, List<TenbjpksLotterySg> tenpksLotterySgList) {
        List<Map<String, Object>> tenpksResultLongMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(tenpksLongMapList)) {
            // ??????????????????
            String nextRedisKey = RedisKeys.TENPKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.TENPKS.getRedisTime();
            TenbjpksLotterySg nextTenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextTenbjpksLotterySg == null) {
                nextTenbjpksLotterySg = this.getNextTenbjpksLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextTenbjpksLotterySg, 10, TimeUnit.SECONDS);
            }

            if (nextTenbjpksLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // ????????????????????????
            String redisKey = RedisKeys.TENPKS_RESULT_VALUE;
            TenbjpksLotterySg tenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (tenbjpksLotterySg == null) {
                // ????????????????????????
                tenbjpksLotterySg = this.getTenbjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, tenbjpksLotterySg);
            }
            String nextIssue = nextTenbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : String.valueOf(nextTenbjpksLotterySg.getIssue());
            String txffnextIssue = tenbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : String.valueOf(tenbjpksLotterySg.getIssue());

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // ????????????????????????????????????????????????????????????
                if (differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }

            for (Map<String, Object> longMap : tenpksLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTenbjpksLotterySg.getIssue());
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextTenbjpksLotterySg.getIdealTime()) / 1000L);
                tenpksResultLongMapList.add(longMap);
            }
        }
        return tenpksResultLongMapList;
    }

    /**
     * @Title: getBigAndSmallLong
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???18?????????2:48:34
     */
    public List<Map<String, Object>> getBigAndSmallLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        List<Map<String, Object>> tenpksBigLongMapList = new ArrayList<Map<String, Object>>();
        //???????????????
        Map<String, Object> gyhDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGYHBIG.getTagType());
        //????????????
        Map<String, Object> gjbDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJBIG.getTagType());
        //????????????
        Map<String, Object> yjbDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJBIG.getTagType());
        //???????????????
        Map<String, Object> dsmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMBIG.getTagType());
        //???????????????
        Map<String, Object> dfmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMBIG.getTagType());
        //???????????????
        Map<String, Object> dwmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDWMBIG.getTagType());
        //???????????????
        Map<String, Object> dlmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDLMBIG.getTagType());
        //???????????????
        Map<String, Object> dqmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDQMBIG.getTagType());
        //???????????????
        Map<String, Object> dmmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDMMBIG.getTagType());
        //???????????????
        Map<String, Object> djmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDJMBIG.getTagType());
        //???????????????
        Map<String, Object> dtmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDTMBIG.getTagType());

        // ????????????????????????????????????
        if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(gyhDragonMap);
        }

        if (gjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(gjbDragonMap);
        }

        if (yjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(yjbDragonMap);
        }

        if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(dsmDragonMap);
        }

        if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(dfmDragonMap);
        }

        if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(dwmDragonMap);
        }

        if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(dlmDragonMap);
        }

        if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(dqmDragonMap);
        }

        if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(dmmDragonMap);
        }

        if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(djmDragonMap);
        }

        if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksBigLongMapList.add(dtmDragonMap);
        }
        return tenpksBigLongMapList;
    }

    /**
     * @Title: getDoubleAndSingleLong
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???18?????????2:53:08
     */
    public List<Map<String, Object>> getDoubleAndSingleLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        List<Map<String, Object>> tenpksDoubleLongMapList = new ArrayList<Map<String, Object>>();
        //???????????????
        Map<String, Object> gyhDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGYHDOUBLE.getTagType());
        //????????????
        Map<String, Object> gjDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJDOUBLE.getTagType());
        //????????????
        Map<String, Object> syjDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dsmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dfmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dwmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDWMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dlmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDLMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dqmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDQMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dmmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDMMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> djmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDJMDOUBLE.getTagType());
        //???????????????
        Map<String, Object> dtmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDTMDOUBLE.getTagType());

        if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(gyhDragonMap);
        }

        if (gjDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(gjDragonMap);
        }

        if (syjDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(syjDragonMap);
        }

        if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(dsmDragonMap);
        }

        if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(dfmDragonMap);
        }

        if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(dwmDragonMap);
        }

        if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(dlmDragonMap);
        }

        if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(dqmDragonMap);
        }

        if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(dmmDragonMap);
        }

        if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(djmDragonMap);
        }

        if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksDoubleLongMapList.add(dtmDragonMap);
        }
        return tenpksDoubleLongMapList;
    }

    /**
     * @Title: getTrigleAndDragonLong
     * @Description: ???????????????????????????
     * @author HANS
     * @date 2019???5???18?????????2:53:15
     */
    public List<Map<String, Object>> getTrigleAndDragonLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        List<Map<String, Object>> tenpksTrigLongMapList = new ArrayList<Map<String, Object>>();
        //????????????
        Map<String, Object> gjtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJTIDRAGON.getTagType());
        //????????????
        Map<String, Object> yjtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJTIDRAGON.getTagType());
        //???????????????
        Map<String, Object> dsmtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMTIDRAGON.getTagType());
        //???????????????
        Map<String, Object> dfmtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMTIDRAGON.getTagType());
        //???????????????
        Map<String, Object> dwmtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDWMTIDRAGON.getTagType());

        if (gjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksTrigLongMapList.add(gjtiDragonMap);
        }

        if (yjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksTrigLongMapList.add(yjtiDragonMap);
        }

        if (dsmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksTrigLongMapList.add(dsmtiDragonMap);
        }

        if (dfmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksTrigLongMapList.add(dfmtiDragonMap);
        }

        if (dwmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tenpksTrigLongMapList.add(dwmtiDragonMap);
        }
        return tenpksTrigLongMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: ?????????????????????????????????
     * @author HANS
     * @date 2019???5???18?????????11:18:00
     */
    public Map<String, Object> getDragonInfo(List<TenbjpksLotterySg> tenpksLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(tenpksLotterySgList)) {
                // ????????????
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < tenpksLotterySgList.size(); index++) {
                    TenbjpksLotterySg tenbjpksLotterySg = tenpksLotterySgList.get(index);
                    String numberString = tenbjpksLotterySg.getNumber();
                    // ????????????????????????
                    String bigOrSmallName = this.calculateResult(type, numberString);

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
                TenbjpksLotterySg tenbjpksLotterySg = tenpksLotterySgList.get(Constants.DEFAULT_INTEGER);
                // ??????????????????
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type, tenbjpksLotterySg, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TenpksLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: organizationDragonResultMap
     * @Description: ??????????????????
     * @author HANS
     * @date 2019???5???18?????????3:54:00
     */
    public Map<String, Object> organizationDragonResultMap(int type, TenbjpksLotterySg tenbjpksLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // ?????????????????????????????????
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // ????????????PK10    ?????? ????????????
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.TENPKS.getTagCnName(), CaipiaoPlayTypeEnum.TENPKSGYHBIG.getPlayName(),
                    CaipiaoTypeEnum.TENPKS.getTagType(), CaipiaoPlayTypeEnum.TENPKSGYHBIG.getTagType() + "");

            List<String> dragonList = new ArrayList<String>(dragonSet);
            // ????????????
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if (type == 147) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGYHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGYHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGYHBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGBIG);
            } else if (type == 148) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 149) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 150) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 151) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 152) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 153) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDLMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDLMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDLMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 154) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDQMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDQMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDQMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 155) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDMMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDMMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDMMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 156) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDJMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDJMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDJMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 157) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDTMBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDTMBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDTMBIG.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
            } else if (type == 158) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGYHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGYHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGYHDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGDOUBLE);
            } else if (type == 159) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 160) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 161) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 162) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 163) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 164) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDLMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDLMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDLMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 165) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDQMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDQMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDQMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 166) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDMMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDMMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDMMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 167) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDJMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDJMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDJMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 168) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDTMDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDTMDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDTMDOUBLE.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
            } else if (type == 169) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSGJTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 170) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSYJTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 171) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDSMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 172) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDFMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            } else if (type == 173) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMTIDRAGON.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMTIDRAGON.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENPKSDWMTIDRAGON.getPlayTag());
                oddsListMap = jspksLotterySgService.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
            }
            // ?????????????????????????????????MAP???
            longDragonMap.putAll(oddsListMap);
            // ?????????????????????
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.TENPKS.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.TENPKS.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonList.get(Constants.DEFAULT_INTEGER));
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TenpksLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: calculateResult
     * @Description: ????????????????????????
     * @author HANS
     * @date 2019???5???18?????????11:22:00
     */
    public String calculateResult(int type, String numberString) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 147:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 148:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//????????????
                break;
            case 149:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//????????????
                break;
            case 150:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 151:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 152:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 153:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 154:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 155:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 156:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 157:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//???????????????
                break;
            case 158:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 159:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//????????????
                break;
            case 160:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//????????????
                break;
            case 161:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 162:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 163:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 164:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 165:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 166:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 167:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 168:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//???????????????
                break;
            case 169:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//????????????
                break;
            case 170:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//????????????
                break;
            case 171:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//???????????????
                break;
            case 172:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//???????????????
                break;
            case 173:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//???????????????
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: selectTenbjpksSg
     * @Description: ??????10???PK????????????
     * @author HANS
     * @date 2019???5???18?????????2:39:19
     */
    public List<TenbjpksLotterySg> selectTenbjpksSg() {
        TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
        TenbjpksLotterySgExample.Criteria tenpksCriteria = example.createCriteria();
        tenpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<TenbjpksLotterySg> bjpksLotterySgList = tenbjpksLotterySgMapper.selectByExample(example);
        return bjpksLotterySgList;
    }

    @Override
    public List<Map<String, Object>> getTenPksSgLong() {
        List<Map<String, Object>> tenpksLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.TENPKS_ALGORITHM_VALUE;
            List<TenbjpksLotterySg> tenpksLotterySgList = (List<TenbjpksLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(tenpksLotterySgList)) {
                tenpksLotterySgList = this.selectTenbjpksSg();
                redisTemplate.opsForValue().set(algorithm, tenpksLotterySgList, 10, TimeUnit.SECONDS);
            }
            // ????????????
            List<Map<String, Object>> bigAndSmallLongList = this.getBigAndSmallLong(tenpksLotterySgList);
            tenpksLongMapList.addAll(bigAndSmallLongList);
            // ????????????
            List<Map<String, Object>> sigleAndDoubleLongList = this.getDoubleAndSingleLong(tenpksLotterySgList);
            tenpksLongMapList.addAll(sigleAndDoubleLongList);
            // ????????????
            List<Map<String, Object>> dragonAndTigleLongList = this.getTrigleAndDragonLong(tenpksLotterySgList);
            tenpksLongMapList.addAll(dragonAndTigleLongList);
            tenpksLongMapList = this.addNextIssueInfo(tenpksLongMapList, tenpksLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TenpksLotterySgServiceImpl_getJspksSgLong_error:", e);
        }
        return tenpksLongMapList;
    }

}
