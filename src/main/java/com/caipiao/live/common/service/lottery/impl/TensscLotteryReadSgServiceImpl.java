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
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.TensscLotterySg;
import com.caipiao.live.common.mybatis.entity.TensscLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.TensscLotterySgMapper;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JssscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.TensscLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.AusactSgUtils;
import com.caipiao.live.common.util.lottery.CaipiaoUtils;
import com.caipiao.live.common.util.lottery.TensscUtils;
import org.apache.commons.lang3.StringUtils;
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


@Service
public class TensscLotteryReadSgServiceImpl implements TensscLotterySgServiceReadSg {

    private static final Logger logger = LoggerFactory.getLogger(TensscLotteryReadSgServiceImpl.class);
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TensscLotterySgMapper tensscLotterySgMapper;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
    @Autowired
    private JssscLotterySgServiceReadSg jssscLotterySgService;

    /**
     * @Title: getNextTensscLotterySg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年5月16日下午8:27:51
     */
    public TensscLotterySg getNextTensscLotterySg() {
        TensscLotterySgExample next_example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria next_TensscCriteria = next_example.createCriteria();
        next_TensscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        next_TensscCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        next_example.setOrderByClause("ideal_time ASC");
        TensscLotterySg next_TensscLotterySg = this.tensscLotterySgMapper.selectOneByExample(next_example);
        return next_TensscLotterySg;
    }

    /**
     * @Title: getTensscLotterySg
     * @Description: 获取当期开奖数据
     * @author HANS
     * @date 2019年5月16日下午8:28:39
     */
    public TensscLotterySg getTensscLotterySg() {
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria tensscCriteria = example.createCriteria();
        tensscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        TensscLotterySg tensscLotterySg = this.tensscLotterySgMapper.selectOneByExample(example);
        return tensscLotterySg;
    }

    @Override
    public Map<String, Object> getTensscNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullResult();
        try {
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.TENSSC_NEXT_VALUE;
            TensscLotterySg nextTensscLotterySg = (TensscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);
            // 缓存到下期信息
            Long redisTime = CaipiaoRedisTimeEnum.TENSSC.getRedisTime();
            if (nextTensscLotterySg == null) {
                nextTensscLotterySg = this.getNextTensscLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextTensscLotterySg, redisTime, TimeUnit.MINUTES);
            }

            // 缓存到开奖结果
            String redisKey = RedisKeys.TENSSC_RESULT_VALUE;
            TensscLotterySg tensscLotterySg = (TensscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (tensscLotterySg == null) {
                // 查询最近一期开奖信息
                tensscLotterySg = this.getTensscLotterySg();
                redisTemplate.opsForValue().set(redisKey, tensscLotterySg);
            }

            if (nextTensscLotterySg != null) {
                String nextIssue = nextTensscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTensscLotterySg.getIssue();
                String txffnextIssue = tensscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : tensscLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        // 查询下一期信息
                        nextTensscLotterySg = this.getNextTensscLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextTensscLotterySg, redisTime, TimeUnit.MINUTES);
                        // 查询最近一期开奖信息
                        tensscLotterySg = this.getTensscLotterySg();
                        redisTemplate.opsForValue().set(redisKey, tensscLotterySg);
                    }
                }

                if (tensscLotterySg != null) {
                    // 组织开奖号码
                    this.getIssueSumAndAllNumber(tensscLotterySg, result);
                    // 计算开奖次数
                    this.openCount(tensscLotterySg, result);
                }

                if (nextTensscLotterySg != null) {
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
                            DateUtils.getTimeMillis(nextTensscLotterySg.getIdealTime()) / 1000L);
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTensscLotterySg.getIssue());
                }

            } else {
                result = DefaultResultUtil.getNullResult();

                if (tensscLotterySg != null) {
                    // 组织开奖号码
                    this.getIssueSumAndAllNumber(tensscLotterySg, result);
                    // 计算开奖次数
                    this.openCount(tensscLotterySg, result);
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.TENSSC.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    /**
     * @Title: openCount
     * @Description: 计算开奖次数和未开奖次数
     */
    public void openCount(TensscLotterySg tensscLotterySg, Map<String, Object> result) {
        // 计算开奖次数
        String issue = tensscLotterySg.getIssue();
        String openNumString = issue.substring(8, issue.length());
        Integer openNumInteger = Integer.valueOf(openNumString);
        result.put("openCount", openNumInteger);
        // 计算剩余开奖次数
        Integer sumCount = CaipiaoSumCountEnum.TENSSC.getSumCount();
        result.put("noOpenCount", sumCount - openNumInteger);
    }

    /**
     * @Title: getIssueSumAndAllNumber
     * @Description: 组织开奖号码和合值
     */
    public void getIssueSumAndAllNumber(TensscLotterySg tensscLotterySg, Map<String, Object> result) {
        String issue = tensscLotterySg.getIssue();
        result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
        // 组织开奖号码
        String allNumberString = CaipiaoUtils.getNocommaNumber(tensscLotterySg.getNumber());
        result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
        // 计算开奖号码合值
        Integer sumInteger = CaipiaoUtils.getNumberTotal(tensscLotterySg.getNumber());
        result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
    }

    @Override
    public List<TensscLotterySg> getSgByDatePageSize(String date, Integer pageNo, Integer pageSize) {
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if (!org.springframework.util.StringUtils.isEmpty(date)) {
            if (StringUtils.isBlank(date)) {
                date = TimeHelper.date("yyyy-MM-dd");
            }
            date = date.replaceAll("-", "") + "%";
            criteria.andIssueLike(date);
        }
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<TensscLotterySg> tensscLotterySgs = tensscLotterySgMapper.selectByExample(example);
        return tensscLotterySgs;
    }

    @Override
    public List<TensscLotterySg> getSgByIssues(Integer issues) {
        if (issues == null) {
            issues = 30;
        }
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria tensscCriteria = example.createCriteria();
        tensscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(issues);
        example.setOrderByClause("ideal_time DESC");
        List<TensscLotterySg> tensscLotterySgs = tensscLotterySgMapper.selectByExample(example);
        return tensscLotterySgs;
    }


    @Override
    public ResultInfo<List<Map<String, Object>>> todayData(String type, String date, Integer pageNo, Integer pageSize) {
        // 校验类型
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        List<TensscLotterySg> tensscLotterySg = getSgByDatePageSize(date, pageNo, pageSize);
        List<Map<String, Object>> list = TensscUtils.lishiKaiJiang(tensscLotterySg);
        return ResultInfo.ok(list);
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues) {
        // 判断类型
        if (!LotteryInformationType.CQSSC_LSKJ_KJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        // 获取往期开奖结果
        List<TensscLotterySg> tensscLotterySgs = getSgByIssues(issues);
        // 封装数据
        List<Map<String, Object>> list = TensscUtils.lishiKaiJiang(tensscLotterySgs);
        return ResultInfo.ok(list);
    }

    @Override
    public List<TensscLotterySg> getSgByDate(String date) {

        if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        date = date.replaceAll("-", "") + "%";
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andIssueLike(date);
        example.setOrderByClause("ideal_time DESC");
        List<TensscLotterySg> tensscLotterySgs = tensscLotterySgMapper.selectByExample(example);
        return tensscLotterySgs;
    }


    /**
     * 10分时时彩今日统计
     *
     * @param type 类型（校验）
     * @param date 日期, 如2018-08-21
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> todayCount(String type, String date) {
        // 校验类型
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        List<TensscLotterySg> tensscLotterySgs = getSgByDate(date);
        if (tensscLotterySgs == null || tensscLotterySgs.size() == 0) {
            return ResultInfo.getInstance(null, StatusCode.NO_DATA);
        }
        Map<String, Object> map = TensscUtils.todayCount(tensscLotterySgs);
        return ResultInfo.ok(map);
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: 返回
     * @author HANS
     * @date 2019年5月26日下午2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> tensscLongMapList, List<TensscLotterySg> tensscLotterySqList) {
        List<Map<String, Object>> tensscResultLongMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(tensscLongMapList)) {
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.TENSSC_NEXT_VALUE;
            TensscLotterySg nextTensscLotterySg = (TensscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);
            // 缓存到下期信息
            Long redisTime = CaipiaoRedisTimeEnum.TENSSC.getRedisTime();
            if (nextTensscLotterySg == null) {
                nextTensscLotterySg = this.getNextTensscLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextTensscLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextTensscLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // 缓存到开奖结果
            String redisKey = RedisKeys.TENSSC_RESULT_VALUE;
            TensscLotterySg tensscLotterySg = (TensscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (tensscLotterySg == null) {
                // 查询最近一期开奖信息
                tensscLotterySg = this.getTensscLotterySg();
                redisTemplate.opsForValue().set(redisKey, tensscLotterySg);
            }
            String nextIssue = nextTensscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTensscLotterySg.getIssue();
            String txffnextIssue = tensscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : tensscLotterySg.getIssue();

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // 如果长龙期数与下期期数相差太大长龙不存在
                if (differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }
            // 获取德州时时彩的下期数据
            String issueString = nextTensscLotterySg.getIssue();
            Long nextTimeLong = DateUtils.getTimeMillis(nextTensscLotterySg.getIdealTime()) / 1000L;

            for (Map<String, Object> longMap : tensscLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), issueString);
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextTimeLong);
                tensscResultLongMapList.add(longMap);
            }
        }
        return tensscResultLongMapList;
    }

    /**
     * @Title: getTensscBigAndSmallLong
     * @Description: 获取十分六合彩大小长龙
     * @author HANS
     * @date 2019年5月16日下午9:23:20
     */
    private List<Map<String, Object>> getTensscBigAndSmallLong(List<TensscLotterySg> tensscLotterySqList) {
        List<Map<String, Object>> tensscBigLongMapList = new ArrayList<Map<String, Object>>();
        // 收集10分时时彩两面总和大小
        Map<String, Object> twoWallBigAndSmallDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCLMZHBIG.getTagType());
        // 收集10分时时彩第一球大小
        Map<String, Object> firstBigAndSmallDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDYQBIG.getTagType());
        // 收集10分时时彩第二球大小
        Map<String, Object> secondBigAndSmallDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDEQBIG.getTagType());
        // 收集10分时时彩第三球大小
        Map<String, Object> thirdBigAndSmallDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDSQBIG.getTagType());
        // 收集10分时时彩第四球大小
        Map<String, Object> fourthBigAndSmallDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDFQBIG.getTagType());
        // 收集10分时时彩第五球大小
        Map<String, Object> fivethBigAndSmallDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDWQBIG.getTagType());

        // 计算后的数据放入返回集合
        if (twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscBigLongMapList.add(twoWallBigAndSmallDragonMap);
        }

        if (firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscBigLongMapList.add(firstBigAndSmallDragonMap);
        }

        if (secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscBigLongMapList.add(secondBigAndSmallDragonMap);
        }

        if (thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscBigLongMapList.add(thirdBigAndSmallDragonMap);
        }

        if (fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscBigLongMapList.add(fourthBigAndSmallDragonMap);
        }

        if (fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscBigLongMapList.add(fivethBigAndSmallDragonMap);
        }
        return tensscBigLongMapList;
    }

    /**
     * @Title: getTensscSigleAndDoubleLong
     * @Description: 获取十分六合彩单双长龙
     * @author HANS
     * @date 2019年5月16日下午9:23:20
     */
    private List<Map<String, Object>> getTensscSigleAndDoubleLong(List<TensscLotterySg> tensscLotterySqList) {
        List<Map<String, Object>> tensscSigleLongMapList = new ArrayList<Map<String, Object>>();
        // 收集10分时时彩两面总和单双
        Map<String, Object> twoWallSigleAndDoubleDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCLMZHDOUBLE.getTagType());
        // 收集10分时时彩第一球单双
        Map<String, Object> firstSigleAndDoubleDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDYQDOUBLE.getTagType());
        // 收集10分时时彩第二球单双
        Map<String, Object> secondSigleAndDoubleDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDEQDOUBLE.getTagType());
        // 收集10分时时彩第三球单双
        Map<String, Object> thirdSigleAndDoubleDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDSQDOUBLE.getTagType());
        // 收集10分时时彩第四球单双
        Map<String, Object> fourthSigleAndDoubleDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDFQDOUBLE.getTagType());
        // 收集10分时时彩第五球单双
        Map<String, Object> fivethSigleAndDoubleDragonMap = this.getDragonInfo(tensscLotterySqList, CaipiaoPlayTypeEnum.TENSSCDWQDOUBLE.getTagType());
        // 单双
        if (twoWallSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscSigleLongMapList.add(twoWallSigleAndDoubleDragonMap);
        }

        if (firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscSigleLongMapList.add(firstSigleAndDoubleDragonMap);
        }

        if (secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscSigleLongMapList.add(secondSigleAndDoubleDragonMap);
        }

        if (thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscSigleLongMapList.add(thirdSigleAndDoubleDragonMap);
        }

        if (fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscSigleLongMapList.add(fourthSigleAndDoubleDragonMap);
        }

        if (fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tensscSigleLongMapList.add(fivethSigleAndDoubleDragonMap);
        }
        return tensscSigleLongMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: 公共方法，获取长龙数据
     * @author HANS
     * @date 2019年5月16日下午10:00:29
     */
    private Map<String, Object> getDragonInfo(List<TensscLotterySg> tensscLotterySqList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(tensscLotterySqList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < tensscLotterySqList.size(); index++) {
                    TensscLotterySg tensscLotterySg = tensscLotterySqList.get(index);
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateResult(type, tensscLotterySg);

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
                TensscLotterySg tensscLotterySg = tensscLotterySqList.get(Constants.DEFAULT_INTEGER);
                // 组织返回数据
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type, tensscLotterySg, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TensscLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: calculateResult
     * @Description: 按照玩法计算结果
     * @author HANS
     * @date 2019年5月16日下午10:03:40
     */
    private String calculateResult(int type, TensscLotterySg tensscLotterySg) {
        String result = Constants.DEFAULT_NULL;

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 72:
                result = AusactSgUtils.getJssscBigOrSmall(tensscLotterySg.getNumber());//两面总和大小
                break;
            case 73:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getWan());//第一球大小
                break;
            case 74:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getQian());//第二球大小
                break;
            case 75:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getBai());//第三球大小
                break;
            case 76:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getShi());//第四球大小
                break;
            case 77:
                result = AusactSgUtils.getJssscSingleNumber(tensscLotterySg.getGe());//第五球大小
                break;
            case 78:
                result = AusactSgUtils.getSingleAndDouble(tensscLotterySg.getNumber());//两面总和单双
                break;
            case 79:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getWan());//第一球单双
                break;
            case 80:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getQian());//第二球单双
                break;
            case 81:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getBai());//第三球单双
                break;
            case 82:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getShi());//第四球单双
                break;
            case 83:
                result = AusactSgUtils.getOneSingleAndDouble(tensscLotterySg.getGe());//第五球单双
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: organizationDragonResultMap
     * @author HANS
     * @date 2019年5月13日下午1:53:19
     */
    private Map<String, Object> organizationDragonResultMap(int type, TensscLotterySg tensscLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // 有龙情况下查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // 获取德州时时彩 两面 赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.TENSSC.getTagCnName(), CaipiaoPlayTypeEnum.TENSSCLMZHBIG.getPlayName(),
                    CaipiaoTypeEnum.TENSSC.getTagType(), CaipiaoPlayTypeEnum.TENSSCLMZHBIG.getTagType() + "");
            List<String> dragonList = new ArrayList<String>(dragonSet);
            // 玩法赔率
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if (type == 72) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCLMZHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCLMZHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCLMZHBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALBIG);
            } else if (type == 73) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDYQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDYQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDYQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 74) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDEQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDEQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDEQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 75) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDSQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDSQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDSQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 76) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDFQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDFQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDFQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 77) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDWQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDWQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDWQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 78) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCLMZHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCLMZHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCLMZHDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALDOUBLE);
            } else if (type == 79) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDYQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDYQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDYQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 80) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDEQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDEQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDEQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 81) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDSQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDSQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDSQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 82) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDFQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDFQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDFQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 83) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDWQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDWQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TENSSCDWQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            }
            // 把获取的赔率加入到返回MAP中
            longDragonMap.putAll(oddsListMap);
            // 加入其它返回值
            String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
            //String returnPlayType = JspksSgUtils.interceptionPlayString(sourcePlayType);
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.TENSSC.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.TENSSC.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), sourcePlayType);
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TensscLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: getAlgorithmData
     * @Description: 缓存近期数据
     * @author HANS
     * @date 2019年5月15日上午10:58:26
     */
    private List<TensscLotterySg> getTensscAlgorithmData() {
        TensscLotterySgExample tensscExample = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria tensscCriteria = tensscExample.createCriteria();
        tensscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        tensscExample.setOrderByClause("`ideal_time` DESC");
        tensscExample.setOffset(Constants.DEFAULT_INTEGER);
        tensscExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<TensscLotterySg> tensscLotterySqList = tensscLotterySgMapper.selectByExample(tensscExample);
        return tensscLotterySqList;
    }

    @Override
    public List<Map<String, Object>> getTensscSgLong() {
        List<Map<String, Object>> tensscLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.TENSSC_ALGORITHM_VALUE;
            List<TensscLotterySg> tensscLotterySqList = (List<TensscLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(tensscLotterySqList)) {
                tensscLotterySqList = this.getTensscAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, tensscLotterySqList);
            }
            // 获取大小长龙
            List<Map<String, Object>> tensscBigLongMapList = this.getTensscBigAndSmallLong(tensscLotterySqList);
            tensscLongMapList.addAll(tensscBigLongMapList);
            // 获取单双长龙
            List<Map<String, Object>> tensscSigleLongMapList = this.getTensscSigleAndDoubleLong(tensscLotterySqList);
            tensscLongMapList.addAll(tensscSigleLongMapList);
            tensscLongMapList = this.addNextIssueInfo(tensscLongMapList, tensscLotterySqList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TensscLotterySgServiceImpl_getActSgLong_error:", e);
        }
        // 返回
        return tensscLongMapList;
    }

}
