package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.AmlhcLotterySg;
import com.caipiao.live.common.mybatis.entity.AmlhcLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.AmlhcLotterySgMapper;
import com.caipiao.live.common.service.lottery.AmlhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.OnelhcLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
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
 * @ClassName: SslhcLotterySgServiceImpl
 * @Description: 时时六合彩服务类
 * @author: HANS
 * @date: 2019年5月21日 下午10:06:22
 */
@Service
public class AmlhcLotteryReadSgServiceImpl implements AmlhcLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(AmlhcLotteryReadSgServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AmlhcLotterySgMapper amlhcLotterySgMapper;
    @Autowired
    private OnelhcLotterySgServiceReadSg onelhcLotterySgService;
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;


    @Override
    public List<Map<String, Object>> getSslhcSgLong() {
        List<Map<String, Object>> sslhcLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.AMLHC_ALGORITHM_VALUE;
            List<AmlhcLotterySg> sslhcLotterySgList = (List<AmlhcLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(sslhcLotterySgList)) {
                sslhcLotterySgList = this.getSslhcAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, sslhcLotterySgList, 10, TimeUnit.SECONDS);
            }

            // 特码两面 单双大小
            List<Map<String, Object>> wallBigAndSmallLongList = this.getWallBigAndSmallLong(sslhcLotterySgList);
            sslhcLongMapList.addAll(wallBigAndSmallLongList);
            // 正码 总单总双总大总小
            List<Map<String, Object>> totalDoubleAndBigLongList = this.getTotalDoubleAndBigLong(sslhcLotterySgList);
            sslhcLongMapList.addAll(totalDoubleAndBigLongList);
            // 正特 单双
            List<Map<String, Object>> ztsigleAndDoubleLongList = this.getZtsigleAndDoubleLong(sslhcLotterySgList);
            sslhcLongMapList.addAll(ztsigleAndDoubleLongList);
            // 正特 大小
            List<Map<String, Object>> ztbigAndSmallLongList = this.getZtbigAndSmallLong(sslhcLotterySgList);
            sslhcLongMapList.addAll(ztbigAndSmallLongList);
            sslhcLongMapList = this.addNextIssueInfo(sslhcLongMapList, sslhcLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#SslhcLotterySgServiceImpl_getFivelhcSgLong_error:", e);
        }
        return sslhcLongMapList;
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: 返回
     * @author HANS
     * @date 2019年5月26日下午2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> sslhcLongMapList, List<AmlhcLotterySg> sslhcLotterySgList) {
        List<Map<String, Object>> sslhcResultLongMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(sslhcLongMapList)) {
            // 获取下期数据
            String nextRedisKey = RedisKeys.AMLHC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.AMLHC.getRedisTime();
            AmlhcLotterySg nextSslhcLotterySg = (AmlhcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextSslhcLotterySg == null) {
                nextSslhcLotterySg = this.getNextAmlhcLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextSslhcLotterySg, 10, TimeUnit.SECONDS);
            }
            if (nextSslhcLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.AMLHC_RESULT_VALUE;
            AmlhcLotterySg ssllhcLotterySg = (AmlhcLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (ssllhcLotterySg == null) {
                // 查询最近一期开奖信息
                ssllhcLotterySg = this.getAmlhcLotterySg();
                redisTemplate.opsForValue().set(redisKey, ssllhcLotterySg);
            }
            String nextIssue = nextSslhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextSslhcLotterySg.getIssue();
            String txffnextIssue = ssllhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : ssllhcLotterySg.getIssue();

            if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                Long nextIssueNum = Long.parseLong(nextIssue);
                Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                Long differenceNum = nextIssueNum - txffnextIssueNum;

                // 如果长龙期数与下期期数相差太大长龙不存在
                if (differenceNum < 1 || differenceNum > 2) {
                    return new ArrayList<Map<String, Object>>();
                }
            }

            for (Map<String, Object> longMap : sslhcLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextSslhcLotterySg.getIssue());
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextSslhcLotterySg.getIdealTime()) / 1000L);
                sslhcResultLongMapList.add(longMap);
            }
        }
        return sslhcResultLongMapList;
    }

    /**
     * @Title: getWallBigAndSmallLong
     * @Description: 获取特码两面 单双、大小
     * @author HANS
     * @date 2019年5月21日下午10:29:29
     */
    private List<Map<String, Object>> getWallBigAndSmallLong(List<AmlhcLotterySg> sslhcLotterySgList) {
        List<Map<String, Object>> fivelhcBigLongMapList = new ArrayList<Map<String, Object>>();
        // 特码两面单双
        Map<String, Object> twoWallDoubleAndSigleDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCTMLMDOUBLEDRAGON.getTagType());
        // 特码两面大小
        Map<String, Object> twoWallBigAndSmallDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCTMLMBIGDRAGON.getTagType());

        // 计算后的数据放入返回集合
        if (twoWallDoubleAndSigleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            fivelhcBigLongMapList.add(twoWallDoubleAndSigleDragonMap);
        }

        if (twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            fivelhcBigLongMapList.add(twoWallBigAndSmallDragonMap);
        }
        return fivelhcBigLongMapList;
    }

    /**
     * @Title: getTotalDoubleAndBigLong
     * @Description: 获取正码 总单总双、总大总小
     * @author HANS
     * @date 2019年5月21日下午10:29:12
     */
    private List<Map<String, Object>> getTotalDoubleAndBigLong(List<AmlhcLotterySg> sslhcLotterySgList) {
        List<Map<String, Object>> sslhcTotalDoubleAndBigMapList = new ArrayList<Map<String, Object>>();
        // 正码总单总双
        Map<String, Object> totalSigleAndDoubleDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZMTOTALDRAGON.getTagType());
        // 正码总大总小
        Map<String, Object> totalBigAndSmallDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCTOTALBIGDRAGON.getTagType());

        // 计算后的数据放入返回集合
        if (totalSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            sslhcTotalDoubleAndBigMapList.add(totalSigleAndDoubleDragonMap);
        }

        if (totalBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            sslhcTotalDoubleAndBigMapList.add(totalBigAndSmallDragonMap);
        }
        return sslhcTotalDoubleAndBigMapList;
    }

    /**
     * @Title: getZtsigleAndDoubleLong
     * @Description: 获取 正特 单双
     * @author HANS
     * @date 2019年5月21日下午10:28:51
     */
    private List<Map<String, Object>> getZtsigleAndDoubleLong(List<AmlhcLotterySg> sslhcLotterySgList) {
        List<Map<String, Object>> sslhcZtsigleAndDoubleMapList = new ArrayList<Map<String, Object>>();
        // 正1特单双
        Map<String, Object> firstSigleAndDoubleDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZYTDOUBLEDRAGON.getTagType());
        // 正2特单双
        Map<String, Object> secondSigleAndDoubleDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZETDOUBLEDRAGON.getTagType());
        // 正3特单双
        Map<String, Object> thirdSigleAndDoubleDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZSTDOUBLEDRAGON.getTagType());
        // 正4特单双
        Map<String, Object> fourthSigleAndDoubleDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZFTDOUBLEDRAGON.getTagType());
        // 正5特单双
        Map<String, Object> fivethSigleAndDoubleDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZWTDOUBLEDRAGON.getTagType());
        // 正6特单双
        Map<String, Object> sixthSigleAndDoubleDragonMap = this.getPlayDragonMap(sslhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZLTDOUBLEDRAGON.getTagType());

        // 计算后的数据放入返回集合
        if (firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            sslhcZtsigleAndDoubleMapList.add(firstSigleAndDoubleDragonMap);
        }

        if (secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            sslhcZtsigleAndDoubleMapList.add(secondSigleAndDoubleDragonMap);
        }

        if (thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            sslhcZtsigleAndDoubleMapList.add(thirdSigleAndDoubleDragonMap);
        }

        if (fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            sslhcZtsigleAndDoubleMapList.add(fourthSigleAndDoubleDragonMap);
        }

        if (fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            sslhcZtsigleAndDoubleMapList.add(fivethSigleAndDoubleDragonMap);
        }

        if (sixthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            sslhcZtsigleAndDoubleMapList.add(sixthSigleAndDoubleDragonMap);
        }
        return sslhcZtsigleAndDoubleMapList;
    }

    /**
     * @Title: getZtbigAndSmallLong
     * @Description: 获取 正特 大小
     * @author HANS
     * @date 2019年5月21日下午10:28:30
     */
    private List<Map<String, Object>> getZtbigAndSmallLong(List<AmlhcLotterySg> amlhcLotterySgList) {
        List<Map<String, Object>> amlhcZtbigAndSmallMapList = new ArrayList<Map<String, Object>>();
        // 正1特大小
        Map<String, Object> firstBigAndSmallDragonMap = this.getPlayDragonMap(amlhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZYTBIGDRAGON.getTagType());
        // 正2特大小
        Map<String, Object> secondBigAndSmallDragonMap = this.getPlayDragonMap(amlhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZETBIGDRAGON.getTagType());
        // 正3特大小
        Map<String, Object> thirdBigAndSmallDragonMap = this.getPlayDragonMap(amlhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZSTBIGDRAGON.getTagType());
        // 正4特大小
        Map<String, Object> fourthBigAndSmallDragonMap = this.getPlayDragonMap(amlhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZFTBIGDRAGON.getTagType());
        // 正5特大小
        Map<String, Object> fivethBigAndSmallDragonMap = this.getPlayDragonMap(amlhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZWTBIGDRAGON.getTagType());
        // 正6特大小
        Map<String, Object> sixthBigAndSmallDragonMap = this.getPlayDragonMap(amlhcLotterySgList, CaipiaoPlayTypeEnum.AMLHCZLTBIGDRAGON.getTagType());

        // 计算后的数据放入返回集合
        if (firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            amlhcZtbigAndSmallMapList.add(firstBigAndSmallDragonMap);
        }

        if (secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            amlhcZtbigAndSmallMapList.add(secondBigAndSmallDragonMap);
        }

        if (thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            amlhcZtbigAndSmallMapList.add(thirdBigAndSmallDragonMap);
        }

        if (fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            amlhcZtbigAndSmallMapList.add(fourthBigAndSmallDragonMap);
        }

        if (fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            amlhcZtbigAndSmallMapList.add(fivethBigAndSmallDragonMap);
        }

        if (sixthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            amlhcZtbigAndSmallMapList.add(sixthBigAndSmallDragonMap);
        }
        return amlhcZtbigAndSmallMapList;
    }

    /**
     * @Title: getDragonInfo
     * @Description: 时时六合彩获取长龙数据
     * @author HANS
     * @date 2019年5月22日上午10:29:38
     */
    private Map<String, Object> getPlayDragonMap(List<AmlhcLotterySg> amlhcLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(amlhcLotterySgList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < amlhcLotterySgList.size(); index++) {
                    AmlhcLotterySg amlhcLotterySg = amlhcLotterySgList.get(index);
                    String numberString = amlhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : amlhcLotterySg.getNumber();
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateGoriResult(type, numberString);

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
                // 组织返回数据
                if (dragonSize >= Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationSslhcDragonResultMap(type, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#FivelhcLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: organizationDragonResultMap
     * @Description: 获取返回数据
     * @author HANS
     * @date 2019年5月22日上午11:01:13
     */
    private Map<String, Object> organizationSslhcDragonResultMap(int type, Set<String> dragonSet, Integer dragonSize) {
        // 有龙情况下查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // 玩法赔率
            Map<String, Object> oddsListMap = this.addSslhcOddListInfo(type);
            // 把获取的赔率加入到返回MAP中
            longDragonMap.putAll(oddsListMap);

            List<String> dragonList = new ArrayList<String>(dragonSet);
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.AMLHC.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.AMLHC.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonList.get(Constants.DEFAULT_INTEGER));
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.SslhcLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: addOddListInfo
     * @Description: 增加其他参数
     * @author HANS
     * @date 2019年5月15日下午10:32:33
     */
    private Map<String, Object> addSslhcOddListInfo(int type) {
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        // 赔率集合
        Map<String, Object> oddsListMap = new HashMap<String, Object>();

        if (type == 217) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTMLMDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTMLMDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTMLMDOUBLEDRAGON.getPlayTag());
            // 获取到赔率
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCTMLMDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCTMLMDOUBLEDRAGON.getTagType() + "");
            oddsListMap = onelhcLotterySgService.addTmlmDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_TMLM_SIGLE);
        } else if (type == 218) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTMLMBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTMLMBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTMLMBIGDRAGON.getPlayTag());
            // 获取到赔率
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCTMLMDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCTMLMDOUBLEDRAGON.getTagType() + "");
            oddsListMap = onelhcLotterySgService.addTmlmDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_TMLM_BIG);
        } else if (type == 219) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZMTOTALDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZMTOTALDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZMTOTALDRAGON.getPlayTag());
            // 获取时时六合彩 正码总单总双和正码总大总小赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCZMTOTALDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCZMTOTALDRAGON.getTagType() + "");
            oddsListMap = onelhcLotterySgService.addZmtotalDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_ZMTOTAL_SIGLE);
        } else if (type == 220) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTOTALBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTOTALBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCTOTALBIGDRAGON.getPlayTag());
            // 获取时时六合彩 正码总单总双和正码总大总小赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCZMTOTALDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCZMTOTALDRAGON.getTagType() + "");
            oddsListMap = onelhcLotterySgService.addZmtotalDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_ZMTOTAL_BIG);
        } else if (type == 221) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZYTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZYTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZYTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 222) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZETDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZETDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZETDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 223) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZSTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZSTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZSTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 224) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZFTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZFTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZFTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 225) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZWTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZWTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZWTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 226) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZLTDOUBLEDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZLTDOUBLEDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZLTDOUBLEDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
        } else if (type == 227) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZYTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZYTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZYTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 228) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZETBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZETBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZETBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 229) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZSTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZSTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZSTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 230) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZFTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZFTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZFTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 231) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZWTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZWTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZWTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
        } else if (type == 232) {
            longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZLTBIGDRAGON.getTagType());
            longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZLTBIGDRAGON.getTagCnName());
            longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.AMLHCZLTBIGDRAGON.getPlayTag());
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
            oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
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
     */
    private PlayAndOddListInfoVO getZhengTePlayInfo(int type) {
        // 定义返回值
        PlayAndOddListInfoVO twoWallplayAndOddListInfo = new PlayAndOddListInfoVO();
        // 获取时时六合彩 正1特单双\正1特大小赔率数据
        if (type == 221 || type == 227) {
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCZYTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCZYTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_ONE);
        } else if (type == 222 || type == 228) {
            // 获取时时六合彩 正2特单双\正2特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCZETDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCZETDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_TWO);
        } else if (type == 223 || type == 229) {
            // 获取时时六合彩 正3特单双\正3特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCZSTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCZSTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_THREE);
        } else if (type == 224 || type == 230) {
            // 获取时时六合彩 正4特单双\正4特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCZFTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCZFTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_FOUR);
        } else if (type == 225 || type == 231) {
            // 获取时时六合彩 正5特单双\正5特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCZWTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCZWTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_FIVE);
        } else if (type == 226 || type == 232) {
            // 获取时时六合彩 正6特单双\正6特大小赔率数据
            twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.AMLHC.getTagCnName(), CaipiaoPlayTypeEnum.AMLHCZLTDOUBLEDRAGON.getPlayName(),
                    CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoPlayTypeEnum.AMLHCZLTDOUBLEDRAGON.getTagType() + "", Constants.LHC_PLAYWAY_ZT_SIX);
        }
        return twoWallplayAndOddListInfo;
    }

    /**
     * @Title: calculateResult
     * @Description: 计算每种算法的结果
     * @author HANS
     * @date 2019年5月22日上午10:32:14
     */
    private String calculateGoriResult(int type, String number) {
        String result = Constants.DEFAULT_NULL;
        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 217:
                result = OnelhcSgUtils.getOnelhcBigOrDouble(number, type);//特码两面单双
                break;
            case 218:
                result = OnelhcSgUtils.getOnelhcBigOrDouble(number, type);//特码两面大小
                break;
            case 219:
                result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number, type);//正码总单总双
                break;
            case 220:
                result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number, type);//正码总大总小
                break;
            case 221:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 0);//正1特单双
                break;
            case 222:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 1);//正2特单双
                break;
            case 223:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 2);//正3特单双
                break;
            case 224:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 3);//正4特单双
                break;
            case 225:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 4);//正5特单双
                break;
            case 226:
                result = OnelhcSgUtils.getZytSigleOrDouble(number, 5);//正6特单双
                break;
            case 227:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 0);//正1特大小
                break;
            case 228:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 1);//正2特大小
                break;
            case 229:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 2);//正3特大小
                break;
            case 230:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 3);//正4特大小
                break;
            case 231:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 4);//正5特大小
                break;
            case 232:
                result = OnelhcSgUtils.getZytBigOrSmall(number, 5);//正6特大小
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @return SslhcLotterySg
     * @Title: getSslhcLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月21日下午10:09:21
     */
    private AmlhcLotterySg getAmlhcLotterySg() {
        AmlhcLotterySgExample example = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria lhcCriteria = example.createCriteria();
        lhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        AmlhcLotterySg amllhcLotterySg = amlhcLotterySgMapper.selectOneByExample(example);
        return amllhcLotterySg;
    }

    /**
     * @return SslhcLotterySg
     * @Title: getNextSslhcLotterySg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年4月29日下午9:14:27
     */
    private AmlhcLotterySg getNextAmlhcLotterySg() {
        AmlhcLotterySgExample nextExample = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria criteria = nextExample.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        criteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
        nextExample.setOrderByClause("ideal_time ASC");
        AmlhcLotterySg nextTjsscLotterySg = this.amlhcLotterySgMapper.selectOneByExample(nextExample);
        return nextTjsscLotterySg;
    }

    /**
     * @return List<SslhcLotterySg>
     * @Title: getSslhcAlgorithmData
     * @Description: 获取近期数据
     * @author HANS
     * @date 2019年5月21日下午10:25:17
     */
    private List<AmlhcLotterySg> getSslhcAlgorithmData() {
        AmlhcLotterySgExample sslhcExample = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria sslhcCriteria = sslhcExample.createCriteria();
        sslhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        sslhcExample.setOrderByClause("`ideal_time` DESC");
        sslhcExample.setOffset(Constants.DEFAULT_INTEGER);
        sslhcExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<AmlhcLotterySg> lhcLotterySgList = amlhcLotterySgMapper.selectByExample(sslhcExample);
        return lhcLotterySgList;
    }

}
