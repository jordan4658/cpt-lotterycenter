package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;
import com.caipiao.live.common.mybatis.entity.OnelhcLotterySg;
import com.caipiao.live.common.mybatis.entity.OnelhcLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.OnelhcLotterySgMapper;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.OnelhcLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.lottery.LhcUtils;
import com.caipiao.live.common.util.lottery.OnelhcSgUtils;
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

/**
 * @ClassName: OnelhcLotterySgServiceImpl
 * @Description: 一分六合彩服务类
 * @author: HANS
 * @date: 2019年5月14日 下午9:06:58
 */
@Service
public class OnelhcLotteryReadSgServiceImpl implements OnelhcLotterySgServiceReadSg {

    private static final Logger logger = LoggerFactory.getLogger(OnelhcLotteryReadSgServiceImpl.class);
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private OnelhcLotterySgMapper onelhcLotterySgMapper;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;

    /* (non Javadoc)
     * @Title: getOnelhcNewestSgInfo
     * @Description: 获取一分六合彩开奖数据
     * @return
     * @see com.caipiao.live.read.service.result.OnelhcLotterySgService#getOnelhcNewestSgInfo()
     */
    @Override
    public ResultInfo<Map<String, Object>> getOnelhcNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.ONELHC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.ONELHC.getRedisTime();
            OnelhcLotterySg nextOnelhcLotterySg = (OnelhcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextOnelhcLotterySg == null) {
                nextOnelhcLotterySg = this.getNextOnelhcLotterySg();
                // 缓存到下期信息
                redisTemplate.opsForValue().set(nextRedisKey, nextOnelhcLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.ONELHC_RESULT_VALUE;
            OnelhcLotterySg onelhcLotterySg = (OnelhcLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (onelhcLotterySg == null) {
                // 查询最近一期开奖信息
                onelhcLotterySg = this.getOnelhcLotterySg();
                redisTemplate.opsForValue().set(redisKey, onelhcLotterySg);
            }

            if (nextOnelhcLotterySg != null) {
                String nextIssue = nextOnelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextOnelhcLotterySg.getIssue();
                String txffnextIssue = onelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        // 缓存到下期信息
                        nextOnelhcLotterySg = this.getNextOnelhcLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextOnelhcLotterySg, redisTime, TimeUnit.MINUTES);
                        // 查询最近一期开奖信息
                        onelhcLotterySg = this.getOnelhcLotterySg();
                        redisTemplate.opsForValue().set(redisKey, onelhcLotterySg);
                    }
                }

                if (onelhcLotterySg != null) {
                    String number = onelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getNumber();
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), onelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
                    result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, onelhcLotterySg.getTime()));
                }

                if (nextOnelhcLotterySg != null) {
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextOnelhcLotterySg.getIdealTime()) / 1000L);
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextOnelhcLotterySg.getIssue());
                }
            } else {
                result = DefaultResultUtil.getNullResult();

                if (onelhcLotterySg != null) {
                    String number = onelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getNumber();
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), onelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
                    result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, onelhcLotterySg.getTime()));
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.ONELHC.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }

    /* (non Javadoc)
     * @Title: getJspksSgLong
     * @Description: 获取一分六合彩长龙
     * @return
     * @see com.caipiao.live.read.service.result.OnelhcLotterySgService#getJspksSgLong()
     */
    @Override
    public List<Map<String, Object>> getOnelhcSgLong() {
        List<Map<String, Object>> onelhcLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.ONELHC_ALGORITHM_VALUE;
            List<OnelhcLotterySg> onelhcLotterySgList = (List<OnelhcLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(onelhcLotterySgList)) {
                onelhcLotterySgList = this.getOnelhcAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, onelhcLotterySgList, 10, TimeUnit.SECONDS);
            }
            // 特码两面 单双大小
            List<Map<String, Object>> wallBigAndSmallLongList = this.getWallBigAndSmallLong(onelhcLotterySgList);
            onelhcLongMapList.addAll(wallBigAndSmallLongList);
            // 正码 总单总双总大总小
            List<Map<String, Object>> totalDoubleAndBigLongList = this.getTotalDoubleAndBigLong(onelhcLotterySgList);
            onelhcLongMapList.addAll(totalDoubleAndBigLongList);
            // 正特 单双
            List<Map<String, Object>> ztsigleAndDoubleLongList = this.getZtsigleAndDoubleLong(onelhcLotterySgList);
            onelhcLongMapList.addAll(ztsigleAndDoubleLongList);
            // 正特 大小
            List<Map<String, Object>> ztbigAndSmallLongList = this.getZtbigAndSmallLong(onelhcLotterySgList);
            onelhcLongMapList.addAll(ztbigAndSmallLongList);
            // 增加下期数据
            onelhcLongMapList = this.addNextIssueInfo(onelhcLongMapList, onelhcLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#OnelhcLotterySgServiceImpl_getOnelhcSgLong_error:", e);
        }
        return onelhcLongMapList;
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: 返回
     * @author HANS
     * @date 2019年5月26日下午2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> onelhcLongMapList, List<OnelhcLotterySg> onelhcLotterySgList) {
        List<Map<String, Object>> onelhcResultLongMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(onelhcLongMapList)) {
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.ONELHC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.ONELHC.getRedisTime();
            OnelhcLotterySg nextOnelhcLotterySg = (OnelhcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextOnelhcLotterySg == null) {
                nextOnelhcLotterySg = this.getNextOnelhcLotterySg();
                // 缓存到下期信息
                redisTemplate.opsForValue().set(nextRedisKey, nextOnelhcLotterySg, 10, TimeUnit.SECONDS);
            }

            if (nextOnelhcLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // 缓存中取开奖结果
            OnelhcLotterySg onelhcLotterySg = onelhcLotterySgList.get(Constants.DEFAULT_INTEGER);
            String nextIssue = nextOnelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextOnelhcLotterySg.getIssue();
            String txffnextIssue = onelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getIssue();

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // 如果长龙期数与下期期数相差太大长龙不存在
                if (differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }

            for (Map<String, Object> longMap : onelhcLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextOnelhcLotterySg.getIssue());
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextOnelhcLotterySg.getIdealTime()) / 1000L);
                onelhcResultLongMapList.add(longMap);
            }
        }
        return onelhcResultLongMapList;
    }

    /**
     * @Title: getWallBigAndSmallLong
     * @Description: 获取特码两面 单双、大小
     * @author HANS
     * @date 2019年5月15日下午3:56:40
     */
    private List<Map<String, Object>> getWallBigAndSmallLong(List<OnelhcLotterySg> onelhcLotterySgList) {
        List<Map<String, Object>> onelhcBigLongMapList = new ArrayList<Map<String, Object>>();
        // 特码两面单双
        Map<String, Object> twoWallDoubleAndSigleDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getTagType());
        // 特码两面大小
        Map<String, Object> twoWallBigAndSmallDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCTMLMBIGDRAGON.getTagType());

        // 计算后的数据放入返回集合
        if (twoWallDoubleAndSigleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcBigLongMapList.add(twoWallDoubleAndSigleDragonMap);
        }

        if (twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcBigLongMapList.add(twoWallBigAndSmallDragonMap);
        }
        return onelhcBigLongMapList;
    }

    /**
     * @Title: getBigAndSmallLong
     * @Description: 获取正码 总单总双、总大总小
     * @author HANS
     * @date 2019年5月15日下午3:57:05
     */
    private List<Map<String, Object>> getTotalDoubleAndBigLong(List<OnelhcLotterySg> onelhcLotterySgList) {
        List<Map<String, Object>> onelhcTotalDoubleAndBigMapList = new ArrayList<Map<String, Object>>();
        // 正码总单总双
        Map<String, Object> totalSigleAndDoubleDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getTagType());
        // 正码总大总小
        Map<String, Object> totalBigAndSmallDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCTOTALBIGDRAGON.getTagType());

        // 计算后的数据放入返回集合
        if (totalSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcTotalDoubleAndBigMapList.add(totalSigleAndDoubleDragonMap);
        }

        if (totalBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcTotalDoubleAndBigMapList.add(totalBigAndSmallDragonMap);
        }
        return onelhcTotalDoubleAndBigMapList;
    }

    /**
     * @Title: getBigAndSmallLong
     * @Description: 获取 正特 单双
     * @author HANS
     * @date 2019年5月15日下午3:57:23
     */
    private List<Map<String, Object>> getZtsigleAndDoubleLong(List<OnelhcLotterySg> onelhcLotterySgList) {
        List<Map<String, Object>> onelhcZtsigleAndDoubleMapList = new ArrayList<Map<String, Object>>();
        // 正1特单双
        Map<String, Object> firstSigleAndDoubleDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZYTDOUBLEDRAGON.getTagType());
        // 正2特单双
        Map<String, Object> secondSigleAndDoubleDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZETDOUBLEDRAGON.getTagType());
        // 正3特单双
        Map<String, Object> thirdSigleAndDoubleDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZSTDOUBLEDRAGON.getTagType());
        // 正4特单双
        Map<String, Object> fourthSigleAndDoubleDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZFTDOUBLEDRAGON.getTagType());
        // 正5特单双
        Map<String, Object> fivethSigleAndDoubleDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZWTDOUBLEDRAGON.getTagType());
        // 正6特单双
        Map<String, Object> sixthSigleAndDoubleDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZLTDOUBLEDRAGON.getTagType());

        // 计算后的数据放入返回集合
        if (firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtsigleAndDoubleMapList.add(firstSigleAndDoubleDragonMap);
        }

        if (secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtsigleAndDoubleMapList.add(secondSigleAndDoubleDragonMap);
        }

        if (thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtsigleAndDoubleMapList.add(thirdSigleAndDoubleDragonMap);
        }

        if (fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtsigleAndDoubleMapList.add(fourthSigleAndDoubleDragonMap);
        }

        if (fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtsigleAndDoubleMapList.add(fivethSigleAndDoubleDragonMap);
        }

        if (sixthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtsigleAndDoubleMapList.add(sixthSigleAndDoubleDragonMap);
        }
        return onelhcZtsigleAndDoubleMapList;
    }

    /**
     * @Title: getBigAndSmallLong
     * @Description: 获取 正特 大小
     * @author HANS
     * @date 2019年5月15日下午3:57:40
     */
    private List<Map<String, Object>> getZtbigAndSmallLong(List<OnelhcLotterySg> onelhcLotterySgList) {
        List<Map<String, Object>> onelhcZtbigAndSmallMapList = new ArrayList<Map<String, Object>>();
        // 正1特大小
        Map<String, Object> firstBigAndSmallDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZYTBIGDRAGON.getTagType());
        // 正2特大小
        Map<String, Object> secondBigAndSmallDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZETBIGDRAGON.getTagType());
        // 正3特大小
        Map<String, Object> thirdBigAndSmallDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZSTBIGDRAGON.getTagType());
        // 正4特大小
        Map<String, Object> fourthBigAndSmallDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZFTBIGDRAGON.getTagType());
        // 正5特大小
        Map<String, Object> fivethBigAndSmallDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZWTBIGDRAGON.getTagType());
        // 正6特大小
        Map<String, Object> sixthBigAndSmallDragonMap = this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZLTBIGDRAGON.getTagType());

        // 计算后的数据放入返回集合
        if (firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtbigAndSmallMapList.add(firstBigAndSmallDragonMap);
        }

        if (secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtbigAndSmallMapList.add(secondBigAndSmallDragonMap);
        }

        if (thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtbigAndSmallMapList.add(thirdBigAndSmallDragonMap);
        }

        if (fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtbigAndSmallMapList.add(fourthBigAndSmallDragonMap);
        }

        if (fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtbigAndSmallMapList.add(fivethBigAndSmallDragonMap);
        }

        if (sixthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            onelhcZtbigAndSmallMapList.add(sixthBigAndSmallDragonMap);
        }
        return onelhcZtbigAndSmallMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: 一分六合彩获取长龙数据
     * @author HANS
     * @date 2019年5月15日下午4:48:14
     */
    private Map<String, Object> getOneLhcTotalDragonInfo(List<OnelhcLotterySg> onelhcLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(onelhcLotterySgList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < onelhcLotterySgList.size(); index++) {
                    OnelhcLotterySg onelhcLotterySg = onelhcLotterySgList.get(index);
                    String numberString = onelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getNumber();
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
                OnelhcLotterySg onelhcLotterySg = onelhcLotterySgList.get(Constants.DEFAULT_INTEGER);
                // 组织返回数据
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationOneLhcDragonResultMap(type, onelhcLotterySg, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#OnelhcLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: organizationDragonResultMap
     * @Description: 获取返回数据
     * @author HANS
     * @date 2019年5月15日下午9:31:18
     */
    private Map<String, Object> organizationOneLhcDragonResultMap(int type, OnelhcLotterySg onelhcLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // 有龙情况下查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // 玩法赔率
            Map<String, Object> oddsListMap = this.addOddListInfo(type);
            ;
            // 把获取的赔率加入到返回MAP中
            longDragonMap.putAll(oddsListMap);

            List<String> dragonList = new ArrayList<String>(dragonSet);
            String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
            //String returnPlayType = JspksSgUtils.interceptionPlayString(sourcePlayType);
            // 添加返回值
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.ONELHC.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.ONELHC.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), sourcePlayType);
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.OnelhcLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: addOddListInfo
     * @Description: 增加其他参数
     * @author HANS
     * @date 2019年5月15日下午10:32:33
     */
    private Map<String, Object> addOddListInfo(int type) {
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        // 赔率集合
        Map<String, Object> oddsListMap = new HashMap<String, Object>();

        if (type == 44) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getPlayTag());
            // 获取到赔率
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getTagType() + "");
            oddsListMap = this.addTmlmDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_TMLM_SIGLE);
        } else if (type == 45) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTMLMBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTMLMBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTMLMBIGDRAGON.getPlayTag());
            // 获取到赔率
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getTagType() + "");
            oddsListMap = this.addTmlmDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_TMLM_BIG);
        } else if (type == 46) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getPlayTag());
            // 获取一分六合彩 正码总单总双和正码总大总小赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getTagType() + "");
            oddsListMap = this.addZmtotalDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_ZMTOTAL_SIGLE);
        } else if (type == 47) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTOTALBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTOTALBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCTOTALBIGDRAGON.getPlayTag());
            // 获取一分六合彩 正码总单总双和正码总大总小赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getTagType() + "");
            oddsListMap = this.addZmtotalDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_ZMTOTAL_BIG);
        } else if (type == 48) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZYTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZYTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZYTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 49) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZETDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZETDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZETDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 50) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZSTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZSTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZSTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 51) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZFTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZFTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZFTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 52) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZWTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZWTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZWTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 53) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZLTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZLTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZLTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 54) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZYTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZYTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZYTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 55) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZETBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZETBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZETBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 56) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZSTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZSTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZSTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 57) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZFTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZFTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZFTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 58) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZWTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZWTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZWTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 59) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZLTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZLTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.ONELHCZLTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = this.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        }
        // 把获取的赔率加入到返回MAP中
        longDragonMap.putAll(oddsListMap);
        return longDragonMap;
    }

    /**
     * @Title: getZhengTePlayInfo
     * @Description: 按照玩法获取赔率
     * @author HANS
     * @date 2019年5月29日下午2:58:56
     * @return
     */
    private PlayAndOddListInfoVO getZhengTePlayInfo(int type) {
        // 定义返回值
        PlayAndOddListInfoVO twoWallplayAndOddListInfo = new PlayAndOddListInfoVO();
        // 获取一分六合彩 正1特单双\正1特大小赔率数据
        if (type == 48 || type == 54) {
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCZYTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCZYTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_ONE);
        } else if (type == 49 || type == 55) {
            // 获取一分六合彩 正2特单双\正2特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCZETDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCZETDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_TWO);
        } else if (type == 50 || type == 56) {
            // 获取一分六合彩 正3特单双\正3特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCZSTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCZSTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_THREE);
        } else if (type == 51 || type == 57) {
            // 获取一分六合彩 正4特单双\正4特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCZFTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCZFTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_FOUR);
        } else if (type == 52 || type == 58) {
            // 获取一分六合彩 正5特单双\正5特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCZWTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCZWTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_FIVE);
        } else if (type == 53 || type == 59) {
            // 获取一分六合彩 正6特单双\正6特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.ONELHC.getTagCnName(), CaipiaoPlayTypeEnum.ONELHCZLTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoPlayTypeEnum.ONELHCZLTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_SIX);
        }
        return twoWallplayAndOddListInfo;
    }

    /**
     * @Title: calculateResult
     * @Description: 获取计算结果
     * @author HANS
     * @date 2019年5月15日下午6:48:34
     */
    @Override
    public String calculateResult(int type, String number) {
        String result = Constants.DEFAULT_NULL;
        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 44:
                result = OnelhcSgUtils.getOnelhcBigOrDouble(number, type);//特码两面单双
                break;
            case 45:
                result = OnelhcSgUtils.getOnelhcBigOrDouble(number, type);//特码两面大小
                break;
            case 46:
                result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number, type);//正码总单总双
                break;
            case 47:
                result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number, type);//正码总大总小
                break;
            case 48:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 0);//正1特单双
                break;
            case 49:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 1);//正2特单双
                break;
            case 50:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 2);//正3特单双
                break;
            case 51:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 3);//正4特单双
                break;
            case 52:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 4);//正5特单双
                break;
            case 53:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 5);//正6特单双
                break;
            case 54:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 0);//正1特大小
                break;
            case 55:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 1);//正2特大小
                break;
            case 56:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 2);//正3特大小
                break;
            case 57:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 3);//正4特大小
                break;
            case 58:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 4);//正5特大小
                break;
            case 59:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 5);//正6特大小
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @Title: addTmlmDoublePlayMapList
     * @Description: 特码两面单双和大小赔率
     * @author HANS
     * @date 2019年5月15日下午10:48:45
     */
    @Override
    public Map<String, Object> addTmlmDoublePlayMapList(PlayAndOddListInfoVO twoWallplayAndOddListInfo, String playTyep) {
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        if (twoWallplayAndOddListInfo != null) {
            LotteryPlaySetting lotteryPlaySetting = twoWallplayAndOddListInfo.getLotteryPlaySetting();

            if (lotteryPlaySetting != null) {
                longDragonMap.put(AppMianParamEnum.COTEGORY.getParamEnName(), lotteryPlaySetting.getCateId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getCateId());
                longDragonMap.put(AppMianParamEnum.PLAYTAGID.getParamEnName(), lotteryPlaySetting.getPlayTagId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getPlayTagId());
            }
            List<LotteryPlayOdds> oddsList = twoWallplayAndOddListInfo.getOddsList();

            if (!CollectionUtils.isEmpty(oddsList)) {
                List<Map<String, Object>> playMapList = new ArrayList<Map<String, Object>>();
                for (LotteryPlayOdds lotteryPlayOdds : oddsList) {
                    //  特码两面单双
                    if (Constants.LHC_PLAYWAY_TMLM_SIGLE.equalsIgnoreCase(playTyep)) {
                        if (Constants.BIGORSMALL_ODD_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.BIGORSMALL_EVEN_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }

                    // 特码两面大小
                    if (Constants.LHC_PLAYWAY_TMLM_BIG.equalsIgnoreCase(playTyep)) {
                        if (Constants.BIGORSMALL_BIG.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.BIGORSMALL_SMALL.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(playMapList)) {
                    longDragonMap.put(AppMianParamEnum.ODDS.getParamEnName(), playMapList);
                }
            }
        }
        return longDragonMap;
    }

    /**
     * @Title: addZmtotalDoublePlayMapList
     * @Description: 正码总单总双\正码总大总小
     * @author HANS
     * @date 2019年5月15日下午11:17:01
     */
    @Override
    public Map<String, Object> addZmtotalDoublePlayMapList(PlayAndOddListInfoVO twoWallplayAndOddListInfo, String playTyep) {
        Map<String, Object> longDragonMap = new HashMap<String, Object>();

        if (twoWallplayAndOddListInfo != null) {
            LotteryPlaySetting lotteryPlaySetting = twoWallplayAndOddListInfo.getLotteryPlaySetting();

            if (lotteryPlaySetting != null) {
                longDragonMap.put(AppMianParamEnum.COTEGORY.getParamEnName(), lotteryPlaySetting.getCateId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getCateId());
                longDragonMap.put(AppMianParamEnum.PLAYTAGID.getParamEnName(), lotteryPlaySetting.getPlayTagId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getPlayTagId());
            }
            List<LotteryPlayOdds> oddsList = twoWallplayAndOddListInfo.getOddsList();

            if (!CollectionUtils.isEmpty(oddsList)) {
                List<Map<String, Object>> playMapList = new ArrayList<Map<String, Object>>();
                for (LotteryPlayOdds lotteryPlayOdds : oddsList) {
                    // 正码总单总双
                    if (Constants.LHC_PLAYWAY_ZMTOTAL_SIGLE.equalsIgnoreCase(playTyep)) {
                        if (Constants.ZONG_BIGORSMALL_ODD_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.ZONG_BIGORSMALL_EVEN_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }

                    // 正码总大总小
                    if (Constants.LHC_PLAYWAY_ZMTOTAL_BIG.equalsIgnoreCase(playTyep)) {
                        if (Constants.ZONG_BIGORSMALL_BIG.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.ZONG_BIGORSMALL_SMALL.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(playMapList)) {
                    longDragonMap.put(AppMianParamEnum.ODDS.getParamEnName(), playMapList);
                }
            }
        }
        return longDragonMap;
    }

    /**
     * @Title: addZytDoublePlayMapList
     * @Description: 正1特单双\正1特大小
     * @author HANS
     * @date 2019年5月16日上午11:01:48
     */
    @Override
    public Map<String, Object> addZytDoublePlayMapList(String playTyep, PlayAndOddListInfoVO twoWallplayAndOddListInfo) {
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        List<LotteryPlayOdds> oddsList = new ArrayList<LotteryPlayOdds>();
        LotteryPlaySetting lotteryPlaySetting = twoWallplayAndOddListInfo.getLotteryPlaySetting();

        if (lotteryPlaySetting != null) {
            longDragonMap.put(AppMianParamEnum.COTEGORY.getParamEnName(), lotteryPlaySetting.getCateId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getCateId());
            longDragonMap.put(AppMianParamEnum.PLAYTAGID.getParamEnName(), lotteryPlaySetting.getPlayTagId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getPlayTagId());
        }
        oddsList = twoWallplayAndOddListInfo.getOddsList();

        if (!CollectionUtils.isEmpty(oddsList)) {
            List<Map<String, Object>> playMapList = this.getplayMapList(oddsList, playTyep);

            if (!CollectionUtils.isEmpty(playMapList)) {
                longDragonMap.put(AppMianParamEnum.ODDS.getParamEnName(), playMapList);
            }
        }
        return longDragonMap;
    }

    /**
     * @Title: getplayMapList
     * @Description: 获取大小或者单双赔率
     * @author HANS
     * @date 2019年5月16日上午11:29:09
     */
    private List<Map<String, Object>> getplayMapList(List<LotteryPlayOdds> oddsList, String playTyep) {
        List<Map<String, Object>> playMapList = new ArrayList<Map<String, Object>>();
        for (LotteryPlayOdds lotteryPlayOdds : oddsList) {
            // 正特单双
            if (Constants.LHC_PLAYWAY_ZT_SIGLE.equalsIgnoreCase(playTyep)) {
                if (Constants.BIGORSMALL_ODD_NUMBER.equals(lotteryPlayOdds.getName())) {
                    Map<String, Object> playMap = new HashMap<String, Object>();
                    playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                    playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                    playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                    playMapList.add(playMap);
                }

                if (Constants.BIGORSMALL_EVEN_NUMBER.equals(lotteryPlayOdds.getName())) {
                    Map<String, Object> playMap = new HashMap<String, Object>();
                    playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                    playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                    playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                    playMapList.add(playMap);
                }
            }

            // 正特大小
            if (Constants.LHC_PLAYWAY_ZT_BIG.equalsIgnoreCase(playTyep)) {
                if (Constants.BIGORSMALL_BIG.equals(lotteryPlayOdds.getName())) {
                    Map<String, Object> playMap = new HashMap<String, Object>();
                    playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                    playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                    playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                    playMapList.add(playMap);
                }

                if (Constants.BIGORSMALL_SMALL.equals(lotteryPlayOdds.getName())) {
                    Map<String, Object> playMap = new HashMap<String, Object>();
                    playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                    playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                    playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                    playMapList.add(playMap);
                }
            }
        }
        return playMapList;
    }

    /**
     * @Title: getNextOnelhcLotterySg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年5月15日上午11:22:52
     */
    private OnelhcLotterySg getNextOnelhcLotterySg() {
        OnelhcLotterySgExample onelhcExample = new OnelhcLotterySgExample();
        OnelhcLotterySgExample.Criteria onelhcCriteria = onelhcExample.createCriteria();
        onelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        onelhcCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        onelhcExample.setOrderByClause("`ideal_time` ASC");
        OnelhcLotterySg nextOnelhcLotterySg = onelhcLotterySgMapper.selectOneByExample(onelhcExample);
        return nextOnelhcLotterySg;
    }

    /**
     * @return OnelhcLotterySg
     * @Title: getOnelhcLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月15日上午11:27:33
     */
    private OnelhcLotterySg getOnelhcLotterySg() {
        OnelhcLotterySgExample onelhcExample = new OnelhcLotterySgExample();
        OnelhcLotterySgExample.Criteria onelhcCriteria = onelhcExample.createCriteria();
        onelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        onelhcExample.setOrderByClause("ideal_time DESC");
        OnelhcLotterySg onelhcLotterySg = onelhcLotterySgMapper.selectOneByExample(onelhcExample);
        return onelhcLotterySg;
    }

    /**
     * @Title: getAlgorithmData
     * @Description: 缓存近期数据
     * @author HANS
     * @date 2019年5月15日上午10:58:26
     */
    private List<OnelhcLotterySg> getOnelhcAlgorithmData() {
        OnelhcLotterySgExample onelhcExample = new OnelhcLotterySgExample();
        OnelhcLotterySgExample.Criteria onelhcCriteria = onelhcExample.createCriteria();
        onelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        onelhcExample.setOrderByClause("`ideal_time` DESC");
        onelhcExample.setOffset(Constants.DEFAULT_INTEGER);
        onelhcExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        return onelhcLotterySgMapper.selectByExample(onelhcExample);
    }

}
