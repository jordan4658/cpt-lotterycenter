
package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.TjsscLotterySg;
import com.caipiao.live.common.mybatis.entity.TjsscLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.TjsscLotterySgMapper;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JssscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.TjsscDragonServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.lottery.AusactSgUtils;
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
public class TjsscDragonReadSgServiceImpl implements TjsscDragonServiceReadSg {

    private static final Logger logger = LoggerFactory.getLogger(TjsscDragonReadSgServiceImpl.class);
    @Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
    @Autowired
    private JssscLotterySgServiceReadSg jssscLotterySgService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;

    @Override
    public List<Map<String, Object>> getTjsscSgDragonLong() {
        List<Map<String, Object>> tjsscLongMapList = new ArrayList<Map<String, Object>>();
        try {
            String algorithm = RedisKeys.TJSSC_ALGORITHM_VALUE;
            List<TjsscLotterySg> tjsscLotterySgList = (List<TjsscLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(tjsscLotterySgList)) {
                tjsscLotterySgList = this.getTjsscAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, tjsscLotterySgList);
            }
            // 获取大小长龙
            List<Map<String, Object>> tjsscBigLongMapList = this.getTjsscBigAndSmallLong(tjsscLotterySgList);
            tjsscLongMapList.addAll(tjsscBigLongMapList);
            // 获取单双长龙
            List<Map<String, Object>> tjsscSigleLongMapList = this.getTjsscSigleAndDoubleLong(tjsscLotterySgList);
            tjsscLongMapList.addAll(tjsscSigleLongMapList);
            tjsscLongMapList = this.addNextIssueInfo(tjsscLongMapList, tjsscLotterySgList);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TjsscLotterySgServiceImpl_getActSgLong_error:", e);
        }
        // 返回
        return tjsscLongMapList;
    }

    /**
     * @Title: addNextIssueInfo
     * @Description: 返回
     * @author HANS
     * @date 2019年5月26日下午2:43:19
     */
    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> tjsscLongMapList, List<TjsscLotterySg> tjsscLotterySgList) {
        List<Map<String, Object>> tjsscResultLongMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(tjsscLongMapList)) {
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.TJSSC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.TJSSC.getRedisTime();
            TjsscLotterySg nextTjsscLotterySg = (TjsscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextTjsscLotterySg == null) {
                nextTjsscLotterySg = this.getNextTjsscLotterySg();
                // 缓存到下期信息
                redisTemplate.opsForValue().set(nextRedisKey, nextTjsscLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextTjsscLotterySg == null) {
                return new ArrayList<Map<String, Object>>();
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.TJSSC_RESULT_VALUE;
            TjsscLotterySg tjsscLotterySg = (TjsscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (tjsscLotterySg == null) {
                // 查询最近一期开奖信息
                tjsscLotterySg = this.getTjsscLotterySg();
                redisTemplate.opsForValue().set(redisKey, tjsscLotterySg);
            }
            String nextIssue = nextTjsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTjsscLotterySg.getIssue();
            String txffnextIssue = tjsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : tjsscLotterySg.getIssue();

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
            String issueString = nextTjsscLotterySg.getIssue();
            Long nextTimeLong = DateUtils.getTimeMillis(nextTjsscLotterySg.getIdealTime()) / 1000L;

            for (Map<String, Object> longMap : tjsscLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), issueString);
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextTimeLong);
                tjsscResultLongMapList.add(longMap);
            }
        }
        return tjsscResultLongMapList;
    }

    /**
     * @Title: getTjsscBigAndSmallLong
     * @Description: 获取天津时时彩大小长龙
     * @author HANS
     * @date 2019年5月17日上午11:01:46
     */
    private List<Map<String, Object>> getTjsscBigAndSmallLong(List<TjsscLotterySg> tjsscLotterySgList) {
        List<Map<String, Object>> tjsscBigLongMapList = new ArrayList<Map<String, Object>>();
        // 收集天津时时彩两面总和大小
        Map<String, Object> twoWallBigAndSmallDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCLMZHBIG.getTagType());
        // 收集天津时时彩第一球大小
        Map<String, Object> firstBigAndSmallDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDYQBIG.getTagType());
        // 收集天津时时彩第二球大小
        Map<String, Object> secondBigAndSmallDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDEQBIG.getTagType());
        // 收集天津时时彩第三球大小
        Map<String, Object> thirdBigAndSmallDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDSQBIG.getTagType());
        // 收集天津时时彩第四球大小
        Map<String, Object> fourthBigAndSmallDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDFQBIG.getTagType());
        // 收集天津时时彩第五球大小
        Map<String, Object> fivethBigAndSmallDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDWQBIG.getTagType());

        // 计算后的数据放入返回集合
        if (twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscBigLongMapList.add(twoWallBigAndSmallDragonMap);
        }

        if (firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscBigLongMapList.add(firstBigAndSmallDragonMap);
        }

        if (secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscBigLongMapList.add(secondBigAndSmallDragonMap);
        }

        if (thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscBigLongMapList.add(thirdBigAndSmallDragonMap);
        }

        if (fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscBigLongMapList.add(fourthBigAndSmallDragonMap);
        }

        if (fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscBigLongMapList.add(fivethBigAndSmallDragonMap);
        }
        return tjsscBigLongMapList;
    }

    /**
     * @Title: getTjsscSigleAndDoubleLong
     * @Description: 获取天津时时彩单双长龙
     * @author HANS
     * @date 2019年5月17日上午11:01:53
     */
    private List<Map<String, Object>> getTjsscSigleAndDoubleLong(List<TjsscLotterySg> tjsscLotterySgList) {
        List<Map<String, Object>> tjsscDoubleLongMapList = new ArrayList<Map<String, Object>>();
        // 收集天津时时彩两面总和单双
        Map<String, Object> twoWallSigleAndDoubleDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCLMZHDOUBLE.getTagType());
        // 收集天津时时彩第一球单双
        Map<String, Object> firstSigleAndDoubleDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDYQDOUBLE.getTagType());
        // 收集天津时时彩第二球单双
        Map<String, Object> secondSigleAndDoubleDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDEQDOUBLE.getTagType());
        // 收集天津时时彩第三球单双
        Map<String, Object> thirdSigleAndDoubleDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDSQDOUBLE.getTagType());
        // 收集天津时时彩第四球单双
        Map<String, Object> fourthSigleAndDoubleDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDFQDOUBLE.getTagType());
        // 收集天津时时彩第五球单双
        Map<String, Object> fivethSigleAndDoubleDragonMap = this.getDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDWQDOUBLE.getTagType());
        // 单双
        if (twoWallSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscDoubleLongMapList.add(twoWallSigleAndDoubleDragonMap);
        }

        if (firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscDoubleLongMapList.add(firstSigleAndDoubleDragonMap);
        }

        if (secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscDoubleLongMapList.add(secondSigleAndDoubleDragonMap);
        }

        if (thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscDoubleLongMapList.add(thirdSigleAndDoubleDragonMap);
        }

        if (fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscDoubleLongMapList.add(fourthSigleAndDoubleDragonMap);
        }

        if (fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            tjsscDoubleLongMapList.add(fivethSigleAndDoubleDragonMap);
        }
        return tjsscDoubleLongMapList;
    }


    /**
     * @Title: getDragonInfo
     * @Description: 公共方法，获取长龙数据
     * @author HANS
     * @date 2019年5月17日上午11:19:44
     */
    private Map<String, Object> getDragonInfo(List<TjsscLotterySg> tjsscLotterySgList, int type) {
        Map<String, Object> resultDragonMap = new HashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(tjsscLotterySgList)) {
                // 标记变量
                Integer dragonSize = Constants.DEFAULT_INTEGER;
                Set<String> dragonSet = new HashSet<String>();

                for (int index = Constants.DEFAULT_INTEGER; index < tjsscLotterySgList.size(); index++) {
                    TjsscLotterySg tjsscLotterySg = tjsscLotterySgList.get(index);
                    // 按照玩法计算结果
                    String bigOrSmallName = this.calculateResult(type, tjsscLotterySg);

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
                TjsscLotterySg tjsscLotterySg = tjsscLotterySgList.get(Constants.DEFAULT_INTEGER);
                // 组织返回数据
                if (dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
                    resultDragonMap = this.organizationDragonResultMap(type, tjsscLotterySg, dragonSet, dragonSize);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TjsscLotterySgServiceImpl_getDragonInfo_error:", e);
        }
        return resultDragonMap;
    }

    /**
     * @Title: calculateResult
     * @Description: 按照玩法计算结果
     * @author HANS
     * @date 2019年5月17日上午11:50:43
     */
    private String calculateResult(int type, TjsscLotterySg tjsscLotterySg) {
        String result = Constants.DEFAULT_NULL;

        String number = Constants.DEFAULT_NULL;
        number = tjsscLotterySg.getCpkNumber() == null ? Constants.DEFAULT_NULL : tjsscLotterySg.getCpkNumber();

        if (StringUtils.isEmpty(number)) {
            number = tjsscLotterySg.getKcwNumber() == null ? Constants.DEFAULT_NULL : tjsscLotterySg.getKcwNumber();
        }

        switch (type) {
            case 0:
                return Constants.DEFAULT_NULL;
            case 84:
                result = AusactSgUtils.getJssscBigOrSmall(number);//两面总和大小
                break;
            case 85:
                result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getWan());//第一球大小
                break;
            case 86:
                result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getQian());//第二球大小
                break;
            case 87:
                result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getBai());//第三球大小
                break;
            case 88:
                result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getShi());//第四球大小
                break;
            case 89:
                result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getGe());//第五球大小
                break;
            case 90:
                result = AusactSgUtils.getSingleAndDouble(number);//两面总和单双
                break;
            case 91:
                result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getWan());//第一球单双
                break;
            case 92:
                result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getQian());//第二球单双
                break;
            case 93:
                result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getBai());//第三球单双
                break;
            case 94:
                result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getShi());//第四球单双
                break;
            case 95:
                result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getGe());//第五球单双
                break;
            default:
                break;
        }
        return result;
    }


    /**
     * @Title: organizationDragonResultMap
     * @Description: 组织返回数据
     * @author HANS
     * @date 2019年5月17日下午1:22:57
     */
    private Map<String, Object> organizationDragonResultMap(int type, TjsscLotterySg tjsscLotterySg, Set<String> dragonSet, Integer dragonSize) {
        // 有龙情况下查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        try {
            // 获取德州时时彩 两面 赔率数据
            PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.TJSSC.getTagCnName(), CaipiaoPlayTypeEnum.TJSSCLMZHBIG.getPlayName(),
                    CaipiaoTypeEnum.TJSSC.getTagType(), CaipiaoPlayTypeEnum.TJSSCLMZHBIG.getTagType() + "");
            List<String> dragonList = new ArrayList<String>(dragonSet);
            // 玩法赔率
            Map<String, Object> oddsListMap = new HashMap<String, Object>();

            if (type == 84) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCLMZHBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCLMZHBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCLMZHBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALBIG);
            } else if (type == 85) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDYQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDYQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDYQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 86) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDEQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDEQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDEQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 87) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDSQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDSQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDSQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 88) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDFQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDFQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDFQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 89) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDWQBIG.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDWQBIG.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDWQBIG.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
            } else if (type == 90) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCLMZHDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCLMZHDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCLMZHDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALDOUBLE);
            } else if (type == 91) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDYQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDYQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDYQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 92) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDEQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDEQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDEQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 93) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDSQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDSQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDSQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 94) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDFQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDFQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDFQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            } else if (type == 95) {
                longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDWQDOUBLE.getTagType());
                longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDWQDOUBLE.getTagCnName());
                longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.TJSSCDWQDOUBLE.getPlayTag());
                oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
            }
            // 把获取的赔率加入到返回MAP中
            longDragonMap.putAll(oddsListMap);
            // 加入其它返回值
            String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
            //String returnPlayType = JspksSgUtils.interceptionPlayString(sourcePlayType);
            longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.TJSSC.getTagCnName());
            longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.TJSSC.getTagType());
            longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), sourcePlayType);
            longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#TjsscLotterySgServiceImpl_organizationDragonResultMap_error:", e);
        }
        return longDragonMap;
    }

    /**
     * @Title: getTjsscAlgorithmData
     * @Description: 获取近期天津时时彩开奖数据
     * @author HANS
     * @date 2019年5月17日上午10:53:14
     */
    private List<TjsscLotterySg> getTjsscAlgorithmData() {
        TjsscLotterySgExample tjExample = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria tjsscCriteria = tjExample.createCriteria();
        tjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        tjExample.setOrderByClause("`ideal_time` DESC");
        tjExample.setOffset(Constants.DEFAULT_INTEGER);
        tjExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<TjsscLotterySg> tjsscLotterySgList = tjsscLotterySgMapper.selectByExample(tjExample);
        return tjsscLotterySgList;
    }

    /**
     * @Title: getNextTjsscLotterySg
     * @Description: 获取近期天津时时彩预期数据
     * @author HANS
     * @date 2019年5月17日下午9:05:08
     */
    private TjsscLotterySg getNextTjsscLotterySg() {
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria tqsscCriteria = example.createCriteria();
        tqsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        tqsscCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        example.setOrderByClause("issue ASC");
        TjsscLotterySg next_TjsscLotterySg = this.tjsscLotterySgMapper.selectOneByExample(example);
        return next_TjsscLotterySg;
    }

    /**
     * @return TjsscLotterySg
     * @Title: getTjsscLotterySg
     * @Description: 获取当前开奖数据
     * @author admin
     * @date 2019年5月1日下午3:40:45
     */
    private TjsscLotterySg getTjsscLotterySg() {
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria tjsscCriteria = example.createCriteria();
        tjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        TjsscLotterySg tjsscLotterySg = this.tjsscLotterySgMapper.selectOneByExample(example);
        return tjsscLotterySg;
    }

}
