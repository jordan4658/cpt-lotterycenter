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
 * 10分PK10
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
            // 缓存中取开奖结果
            String redisKey = RedisKeys.TENPKS_RESULT_VALUE;
            TenbjpksLotterySg tenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (tenbjpksLotterySg == null) {
                // 查询最近一期信息
                tenbjpksLotterySg = this.getTenbjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, tenbjpksLotterySg);
            }

            // 缓存中取开奖数量
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
                // 获取开奖总期数
                Integer sumCount = CaipiaoSumCountEnum.TENPKS.getSumCount();
                // 计算当日剩余未开奖次数
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
                        // 查询最近一期信息
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
                    // 获取下一期开奖时间
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
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.TENPKS.getTagType() + " 异常： ", e);
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
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年4月29日下午9:24:21
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

        // 校验参数
        if (!LotteryInformationType.TENPKS_JRHM.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        String date = TimeHelper.date("yyyy-MM-dd");
        List<String> sgs = tenpksBeanMapper.selectNumberByDate(date + "%");
        // 判空
        if (CollectionUtils.isEmpty(sgs)) {
            return ResultInfo.ok(voList);
        }

        // 今日号码
        voList = TenpksUtils.todayNumber(sgs);
        return ResultInfo.ok(voList);
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: 返回
     * @author HANS
     * @date 2019年5月26日下午2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> tenpksLongMapList, List<TenbjpksLotterySg> tenpksLotterySgList) {
        List<Map<String, Object>> tenpksResultLongMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(tenpksLongMapList)) {
            // 获取缓存数据
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
            // 缓存中取开奖结果
            String redisKey = RedisKeys.TENPKS_RESULT_VALUE;
            TenbjpksLotterySg tenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (tenbjpksLotterySg == null) {
                // 查询最近一期信息
                tenbjpksLotterySg = this.getTenbjpksLotterySg();
                redisTemplate.opsForValue().set(redisKey, tenbjpksLotterySg);
            }
            String nextIssue = nextTenbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : String.valueOf(nextTenbjpksLotterySg.getIssue());
            String txffnextIssue = tenbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : String.valueOf(tenbjpksLotterySg.getIssue());

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // 如果长龙期数与下期期数相差太大长龙不存在
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
     * @Description: 获取大小的长龙数据
     * @author HANS
     * @date 2019年5月18日下午2:48:34
     */
    public List<Map<String, Object>> getBigAndSmallLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        List<Map<String, Object>> tenpksBigLongMapList = new ArrayList<Map<String, Object>>();
        //冠亚和大小
        Map<String, Object> gyhDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGYHBIG.getTagType());
        //冠军大小
        Map<String, Object> gjbDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJBIG.getTagType());
        //亚军大小
        Map<String, Object> yjbDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJBIG.getTagType());
        //第三名大小
        Map<String, Object> dsmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMBIG.getTagType());
        //第四名大小
        Map<String, Object> dfmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMBIG.getTagType());
        //第五名大小
        Map<String, Object> dwmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDWMBIG.getTagType());
        //第六名大小
        Map<String, Object> dlmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDLMBIG.getTagType());
        //第七名大小
        Map<String, Object> dqmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDQMBIG.getTagType());
        //第八名大小
        Map<String, Object> dmmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDMMBIG.getTagType());
        //第九名大小
        Map<String, Object> djmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDJMBIG.getTagType());
        //第十名大小
        Map<String, Object> dtmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDTMBIG.getTagType());

        // 计算后的数据放入返回集合
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
     * @Description: 获取单双长龙的数据
     * @author HANS
     * @date 2019年5月18日下午2:53:08
     */
    public List<Map<String, Object>> getDoubleAndSingleLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        List<Map<String, Object>> tenpksDoubleLongMapList = new ArrayList<Map<String, Object>>();
        //冠亚和单双
        Map<String, Object> gyhDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGYHDOUBLE.getTagType());
        //冠军单双
        Map<String, Object> gjDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJDOUBLE.getTagType());
        //亚军单双
        Map<String, Object> syjDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJDOUBLE.getTagType());
        //第三名单双
        Map<String, Object> dsmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMDOUBLE.getTagType());
        //第四名单双
        Map<String, Object> dfmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMDOUBLE.getTagType());
        //第五名单双
        Map<String, Object> dwmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDWMDOUBLE.getTagType());
        //第六名单双
        Map<String, Object> dlmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDLMDOUBLE.getTagType());
        //第七名单双
        Map<String, Object> dqmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDQMDOUBLE.getTagType());
        //第八名单双
        Map<String, Object> dmmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDMMDOUBLE.getTagType());
        //第九名单双
        Map<String, Object> djmDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDJMDOUBLE.getTagType());
        //第十名单双
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
     * @Description: 获取龙虎长龙的数据
     * @author HANS
     * @date 2019年5月18日下午2:53:15
     */
    public List<Map<String, Object>> getTrigleAndDragonLong(List<TenbjpksLotterySg> tenpksLotterySgList) {
        List<Map<String, Object>> tenpksTrigLongMapList = new ArrayList<Map<String, Object>>();
        //冠军龙虎
        Map<String, Object> gjtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSGJTIDRAGON.getTagType());
        //亚军龙虎
        Map<String, Object> yjtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSYJTIDRAGON.getTagType());
        //第三名龙虎
        Map<String, Object> dsmtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDSMTIDRAGON.getTagType());
        //第四名龙虎
        Map<String, Object> dfmtiDragonMap = this.getDragonInfo(tenpksLotterySgList, CaipiaoPlayTypeEnum.TENPKSDFMTIDRAGON.getTagType());
        //第五名龙虎
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
     * @Description: 公共方法，获取长龙数据
     * @author HANS
     * @date 2019年5月18日上午11:18:00
     */
    public Map<String, Object> getDragonInfo(List<TenbjpksLotterySg> tenpksLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(tenpksLotterySgList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < tenpksLotterySgList.size(); index++) {
                    TenbjpksLotterySg tenbjpksLotterySg = tenpksLotterySgList.get(index);
                    String numberString = tenbjpksLotterySg.getNumber();
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateResult(type, numberString);

                    if (StringUtils.isEmpty(bigOrSmallName)) {
                        break;
                    }
                    // 把第一个结果加入SET集合
                    if (index == Constants.DEFAULT_INTEGER) {
                        dragonSet.add(bigOrSmallName);
                    }
                    // 如果第一个和第二个开奖结果不一样，统计截止
                    if (index == Constants.DEFAULT_ONE) {
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了不再统计
                            break;
                        }
                        continue;
                    }
                    // 规则：连续3个开奖一样
                    if (index == Constants.DEFAULT_TWO) {
                        // 第三个数据
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了不再统计
                            break;
                        }
                        dragonSize = Constants.DEFAULT_THREE;
                        continue;
                    }
                    // 如果大于3个以上，继续统计，直到结果不一样
                    if (index > Constants.DEFAULT_TWO) {
                        // 大/小统计
                        if (!dragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了不再统计
                            break;
                        }
                        dragonSize++;
                    }
                }
                // 最近一期开奖数据
                TenbjpksLotterySg tenbjpksLotterySg = tenpksLotterySgList.get(Constants.DEFAULT_INTEGER);
                // 组织返回数据
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
     * @Description: 返回前端数据
     * @author HANS
     * @date 2019年5月18日下午3:54:00
     */
    public Map<String, Object> organizationDragonResultMap(int type, TenbjpksLotterySg tenbjpksLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // 有龙情况下查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // 获取德州PK10    两面 赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.TENPKS.getTagCnName(), CaipiaoPlayTypeEnum.TENPKSGYHBIG.getPlayName(),
                    CaipiaoTypeEnum.TENPKS.getTagType(), CaipiaoPlayTypeEnum.TENPKSGYHBIG.getTagType() + "");

            List<String> dragonList = new ArrayList<String>(dragonSet);
            // 玩法赔率
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
            // 把获取的赔率加入到返回MAP中
            longDragonMap.putAll(oddsListMap);
            // 加入其它返回值
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
     * @Description: 按照玩法计算结果
     * @author HANS
     * @date 2019年5月18日上午11:22:00
     */
    public String calculateResult(int type, String numberString) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 147:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//冠亚和大小
                break;
            case 148:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//冠军大小
                break;
            case 149:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//亚军大小
                break;
            case 150:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第三名大小
                break;
            case 151:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第四名大小
                break;
            case 152:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第五名大小
                break;
            case 153:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第六名大小
                break;
            case 154:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第七名大小
                break;
            case 155:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第八名大小
                break;
            case 156:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第九名大小
                break;
            case 157:
                result = JspksSgUtils.getJspksBigOrSmall(numberString, type);//第十名大小
                break;
            case 158:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//冠亚和单双
                break;
            case 159:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//冠军单双
                break;
            case 160:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//亚军单双
                break;
            case 161:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第三名单双
                break;
            case 162:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第四名单双
                break;
            case 163:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第五名单双
                break;
            case 164:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第六名单双
                break;
            case 165:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第七名单双
                break;
            case 166:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第八名单双
                break;
            case 167:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第九名单双
                break;
            case 168:
                result = JspksSgUtils.getJspksSigleAndDouble(numberString, type);//第十名单双
                break;
            case 169:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//冠军龙虎
                break;
            case 170:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//亚军龙虎
                break;
            case 171:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第三名龙虎
                break;
            case 172:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第四名龙虎
                break;
            case 173:
                result = JspksSgUtils.getJspksDragonAndtiger(numberString, type);//第五名龙虎
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: selectTenbjpksSg
     * @Description: 获取10分PK开奖数据
     * @author HANS
     * @date 2019年5月18日下午2:39:19
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
            // 大小长龙
            List<Map<String, Object>> bigAndSmallLongList = this.getBigAndSmallLong(tenpksLotterySgList);
            tenpksLongMapList.addAll(bigAndSmallLongList);
            // 单双长龙
            List<Map<String, Object>> sigleAndDoubleLongList = this.getDoubleAndSingleLong(tenpksLotterySgList);
            tenpksLongMapList.addAll(sigleAndDoubleLongList);
            // 龙虎长龙
            List<Map<String, Object>> dragonAndTigleLongList = this.getTrigleAndDragonLong(tenpksLotterySgList);
            tenpksLongMapList.addAll(dragonAndTigleLongList);
            tenpksLongMapList = this.addNextIssueInfo(tenpksLongMapList, tenpksLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TenpksLotterySgServiceImpl_getJspksSgLong_error:", e);
        }
        return tenpksLongMapList;
    }

}
