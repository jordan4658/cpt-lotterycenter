package com.caipiao.live.common.service.lottery.impl;


import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.SysParameterEnum;
import com.caipiao.live.common.mybatis.entity.*;
import com.caipiao.live.common.mybatis.mapper.LotteryPlayMapper;
import com.caipiao.live.common.mybatis.mapper.LotteryPlayOddsMapper;
import com.caipiao.live.common.service.lottery.LotteryPlayOddsServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryServiceReadSg;
import com.caipiao.live.common.service.sys.SysParamService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class LotteryPlayOddsReadServiceImpl implements LotteryPlayOddsServiceReadSg {

    @Autowired
    private LotteryPlayOddsMapper lotteryPlayOddsMapper;
    @Autowired
    private LotteryServiceReadSg lotteryService;
    @Resource
    private SysParamService sysParamService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LotteryPlayOddsServiceReadSg lotteryPlayOddsService;
    @Autowired
    private LotteryPlayMapper lotteryPlayMapper;

    private static final Logger logger = LoggerFactory.getLogger(LotteryPlayOddsReadServiceImpl.class);

    @Override
    public List<LotteryPlayOdds> selectOddsListBySettingId(Integer settingId) {
        List<LotteryPlayOdds> oddsList = (List<LotteryPlayOdds>) redisTemplate.opsForValue()
                .get(RedisKeys.ODDS_LIST_SETTING_KEY + settingId);
        if (CollectionUtils.isEmpty(oddsList)) {
            LotteryPlayOddsExample oddsExample = new LotteryPlayOddsExample();
            LotteryPlayOddsExample.Criteria oddsCriteria = oddsExample.createCriteria();
            oddsCriteria.andIsDeleteEqualTo(false);
            oddsCriteria.andSettingIdEqualTo(settingId);
            oddsList = lotteryPlayOddsMapper.selectByExample(oddsExample);
            redisTemplate.opsForValue().set(RedisKeys.ODDS_LIST_SETTING_KEY + settingId, oddsList);
        }
        return oddsList;
    }

    @Override
    public BigDecimal countOdds(Integer lotteryId, Integer settingId, String betNumber) {
        // 获取彩种信息
        Lottery lottery = lotteryService.selectLotteryByLotteryId(lotteryId);

        // 获取赔率因子
        SysParameter systemInfo = sysParamService.getByCode(SysParameterEnum.REGISTER_MEMBER_ODDS);
        BigDecimal divisor = new BigDecimal(systemInfo.getParamValue());

        // 获取赔率因子
        Double maxOdds = lottery.getMaxOdds();
        divisor = maxOdds.equals(0D) ? divisor : new BigDecimal(maxOdds);

        // 获取赔率信息
        List<LotteryPlayOdds> oddsList = this.selectOddsListBySettingId(settingId);

        BigDecimal odds = new BigDecimal(0);
        if (oddsList.size() > 1) {
            for (LotteryPlayOdds playOdds : oddsList) {
                if (!playOdds.getName().equals(betNumber.trim())) {
                    continue;
                }
                BigDecimal total = new BigDecimal(playOdds.getTotalCount());
                BigDecimal win = new BigDecimal(playOdds.getWinCount());
                odds = total.divide(win, 10, BigDecimal.ROUND_HALF_UP);
                break;
            }
        } else {
            LotteryPlayOdds playOdds = oddsList.get(0);
            BigDecimal total;
            if (playOdds.getTotalCount().indexOf("/") > -1) {
                String str = playOdds.getTotalCount().substring(0, playOdds.getTotalCount().indexOf("/"));
                total = new BigDecimal(str);
            } else {
                total = new BigDecimal(playOdds.getTotalCount());
            }
            BigDecimal win = new BigDecimal(playOdds.getWinCount());
            odds = total.divide(win, 10, BigDecimal.ROUND_HALF_UP);
        }
        return odds.multiply(divisor).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<LotteryPlayOdds> selectPlayOddsList() {
        // 获取所有配置信息
        List<LotteryPlayOdds> oddsList = (List<LotteryPlayOdds>) redisTemplate.opsForValue()
                .get(RedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA);
        if (CollectionUtils.isEmpty(oddsList)) {
            LotteryPlayOddsExample oddsExample = new LotteryPlayOddsExample();
            LotteryPlayOddsExample.Criteria oddsCriteria = oddsExample.createCriteria();
            oddsCriteria.andIsDeleteEqualTo(false);
            oddsList = lotteryPlayOddsMapper.selectByExample(oddsExample);
            redisTemplate.opsForValue().set(RedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA, oddsList);
        }
        return oddsList;
    }

    @Override
    public Map<Integer, LotteryPlayOdds> selectPlayOddsMap() {
        Map<Integer, LotteryPlayOdds> map = new HashMap<>();
        // 查询所有赔率配置
        List<LotteryPlayOdds> list = this.selectPlayOddsList();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }
        for (LotteryPlayOdds odds : list) {
            map.put(odds.getId(), odds);
        }
        return map;
    }

    @Override
    public Map<String, LotteryPlayOdds> selectPlayOddsBySettingId(Integer settingId) {
        // 获取赔率信息
        List<LotteryPlayOdds> oddsList = this.selectOddsListBySettingId(settingId);
        Map<String, LotteryPlayOdds> oddsMap = new HashMap<>();
        for (LotteryPlayOdds odds : oddsList) {
            oddsMap.put(odds.getName(), odds);
        }
        return oddsMap;
    }

    @Override
    public List<String> selectByEasyImportFlag(Integer playId) {
        LotteryPlayOddsExample example = new LotteryPlayOddsExample();
        LotteryPlayOddsExample.Criteria criteria = example.createCriteria();
        criteria.andEasyImportFlagEqualTo(playId);
        List<LotteryPlayOdds> lotteryPlayOdds = lotteryPlayOddsMapper.selectByExample(example);
        return lotteryPlayOdds.stream().map(LotteryPlayOdds::getName).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<LotteryPlayOdds>> selectOddsListBySettingIdList(List<Integer> playSettingList) {
        Map<Integer, List<LotteryPlayOdds>> map = new HashMap<>();
        // 查询所有赔率配置
        List<LotteryPlayOdds> list = this.selectPlayOddsList();
        if (!CollectionUtils.isEmpty(playSettingList)) {
            list = list.parallelStream().filter(odds -> playSettingList.contains(odds.getSettingId()))
                    .collect(Collectors.toList());
        }
        for (LotteryPlayOdds odds : list) {
            List<LotteryPlayOdds> lotteryPlayOddsList;
            if (map.containsKey(odds.getSettingId())) {
                lotteryPlayOddsList = map.get(odds.getSettingId());
            } else {
                lotteryPlayOddsList = new ArrayList<>();
            }
            lotteryPlayOddsList.add(odds);
            map.put(odds.getSettingId(), lotteryPlayOddsList);
        }
        return map;
    }

    /**
     * 跟单赔率
     *
     * @param orderBetRecord
     * @return
     */
    @Override
    public BigDecimal circleOdds(OrderBetRecord orderBetRecord) {

        BigDecimal momey = BigDecimal.ZERO;
        switch (orderBetRecord.getLotteryId()) {
            case Constants.LOTTERY_CQSSC:
                momey = commOdds(orderBetRecord);
                break;
            case Constants.LOTTERY_XJSSC:
                momey = commOdds(orderBetRecord);
                break;
            case Constants.LOTTERY_TJSSC:
                momey = commOdds(orderBetRecord);
                break;
            case Constants.LOTTERY_BJPKS:
                momey = commOdds(orderBetRecord);
                break;
            case Constants.LOTTERY_PL35:
                momey = commOdds(orderBetRecord);
                break;
            case Constants.LOTTERY_FC3D:
                momey = commOdds(orderBetRecord);
                break;
            case Constants.LOTTERY_HNQXC:
                momey = commOdds(orderBetRecord);
                break;
            case Constants.LOTTERY_QLC:
                momey = countOddsWithDivisor(orderBetRecord);
                break;
            case Constants.LOTTERY_SSQ:
                momey = countOddsWithDivisor(orderBetRecord);
                break;
            case Constants.LOTTERY_DLT:
                momey = countOddsWithDivisor(orderBetRecord);
                break;
            case Constants.LOTTERY_LHC:
                momey = commLhc(orderBetRecord);
                break;
            default:
                break;
        }
        return momey.setScale(3, BigDecimal.ROUND_DOWN);

    }

    /**
     * 公共赔率计算
     *
     * @param orderBetRecord
     * @return
     */
    private BigDecimal commOdds(OrderBetRecord orderBetRecord) {
        // 获取彩种信息
        Lottery lottery = lotteryService.selectLotteryByLotteryId(orderBetRecord.getLotteryId());
        // 获取赔率因子
        SysParameter systemInfo = sysParamService.getByCode(SysParameterEnum.REGISTER_MEMBER_ODDS);
        double divisor = Double.parseDouble(systemInfo.getParamValue());
        // 获取赔率因子
        Double maxOdds = lottery.getMaxOdds();
        divisor = maxOdds.equals(0D) ? divisor : maxOdds;

        // 获取配置信息中的赔率信息
        String betNumber = orderBetRecord.getBetNumber();
        if (betNumber.contains("@")) {
            betNumber = betNumber.split("@")[1];
        }

        // 通过配置获取赔率信息
        List<LotteryPlayOdds> oddsList = lotteryPlayOddsService
                .selectOddsListBySettingId(orderBetRecord.getSettingId());

        // 判空
        if (CollectionUtils.isEmpty(oddsList)) {
            return BigDecimal.ZERO;
        }
        Map<String, LotteryPlayOdds> mapList = new HashMap<String, LotteryPlayOdds>();
        for (LotteryPlayOdds playOddsList : oddsList) {
            mapList.put(playOddsList.getName(), playOddsList);
        }
        BigDecimal tatolOdds = BigDecimal.ZERO;
        BigDecimal maxTatolOdds = BigDecimal.ZERO;
        String[] splitBetNum = betNumber.split(",");

        LotteryPlayExample example = new LotteryPlayExample();
        LotteryPlayExample.Criteria criteria = example.createCriteria();
        criteria.andPlayTagIdEqualTo(orderBetRecord.getPlayId());
        LotteryPlay lotteryPlay = lotteryPlayMapper.selectOneByExample(example);

        // 普通玩法
        if (splitBetNum.length <= 1) {
            LotteryPlayOdds playOdds = new LotteryPlayOdds();
            if (oddsList.size() > 1) {
                playOdds = mapList.get(betNumber);
            } else {
                playOdds = oddsList.get(0);
            }
            tatolOdds = new BigDecimal(playOdds.getTotalCount()).divide(new BigDecimal(playOdds.getWinCount()));
        } else {// TODO 特殊玩法（投注号码与后台设置内容不符）

            if (lotteryPlay.getPlayTagId() == Constants.QXC_PLAY_LM
                    || lotteryPlay.getPlayTagId() == Constants.QXC_PLAY_DWD) {
                // 海南七星彩 两面 定位胆
                String strBetNumber = orderBetRecord.getBetNumber();
                String[] split = strBetNumber.split("_");
                for (String betContent : split) {
                    if (betContent.contains("@")) {
                        betNumber = betContent.split("@")[1];
                        String[] splitBetNumber = betNumber.split(",");
                        BigDecimal tatolOdd = commonBigDecimal(splitBetNumber, lotteryPlay, oddsList, mapList);
                        tatolOdds = tatolOdds.add(tatolOdd);
                    }
                }
            } else if (lotteryPlay.getPlayTagId() == Constants.PL35_PLAY_LM
                    || lotteryPlay.getPlayTagId() == Constants.PL35_PLAY_DWD) {
                // 海南七星彩 两面 定位胆
                String strBetNumber = orderBetRecord.getBetNumber();
                String[] split = strBetNumber.split("_");
                for (String betContent : split) {
                    if (betContent.contains("@")) {
                        betNumber = betContent.split("@")[1];
                        String[] splitBetNumber = betNumber.split(",");
                        BigDecimal tatolOdd = commonBigDecimal(splitBetNumber, lotteryPlay, oddsList, mapList);
                        tatolOdds = tatolOdds.add(tatolOdd);
                    }
                }
            } else if (lotteryPlay.getPlayTagId() == Constants.FC3D_PLAY_LM
                    || lotteryPlay.getPlayTagId() == Constants.FC3D_PLAY_1D) {
                // 福彩3D 两面 1D
                String strBetNumber = orderBetRecord.getBetNumber();
                String[] split = strBetNumber.split("_");
                for (String betContent : split) {
                    if (betContent.contains("@")) {
                        betNumber = betContent.split("@")[1];
                        String[] splitBetNumber = betNumber.split(",");
                        BigDecimal tatolOdd = commonBigDecimal(splitBetNumber, lotteryPlay, oddsList, mapList);
                        tatolOdds = tatolOdds.add(tatolOdd);
                    }
                }
            } else {
                tatolOdds = commonBigDecimal(splitBetNum, lotteryPlay, oddsList, mapList);
            }
        }

        // 特殊玩法 最高赔率
        if (lotteryPlay.getPlayTagId() == Constants.FC3D_PLAY_2D) {
            // 海南七星彩玩法- 不定位2
            int num = StringUtils.countMatches(orderBetRecord.getBetNumber(), "_");
            if (num > 1) {
                maxTatolOdds = new BigDecimal(3).multiply(tatolOdds);
                maxTatolOdds = maxTatolOdds.divide(new BigDecimal(orderBetRecord.getBetCount()), 3,
                        BigDecimal.ROUND_DOWN);
            } else {
                maxTatolOdds = maxTatolOdds.divide(new BigDecimal(orderBetRecord.getBetCount()), 3,
                        BigDecimal.ROUND_DOWN);
            }
        } else if (lotteryPlay.getPlayTagId() == Constants.QXC_PLAY_BDW2) {
            // 海南七星彩玩法- 不定位2 最高中6注
            if (orderBetRecord.getBetCount() > 6) {
                maxTatolOdds = new BigDecimal(6).multiply(tatolOdds);
                maxTatolOdds = maxTatolOdds.divide(new BigDecimal(orderBetRecord.getBetCount()), 3,
                        BigDecimal.ROUND_DOWN);
            } else {
                maxTatolOdds = tatolOdds;
            }
        } else if (lotteryPlay.getPlayTagId() == Constants.QXC_PLAY_BDW3) {
            // 海南七星彩玩法- 不定位3 最高中4注
            if (orderBetRecord.getBetCount() > 4) {
                maxTatolOdds = new BigDecimal(4).multiply(tatolOdds);
                maxTatolOdds = maxTatolOdds.divide(new BigDecimal(orderBetRecord.getBetCount()), 3,
                        BigDecimal.ROUND_DOWN);
            } else {
                maxTatolOdds = tatolOdds;
            }
        } else if (lotteryPlay.getPlayTagId() == Constants.FC3D_PLAY_C1D) {
            // 福彩3D 猜1D 最高中3注
            if (orderBetRecord.getBetCount() > 3) {
                maxTatolOdds = new BigDecimal(3).multiply(tatolOdds);
                maxTatolOdds = maxTatolOdds.divide(new BigDecimal(orderBetRecord.getBetCount()), 3,
                        BigDecimal.ROUND_DOWN);
            } else {
                maxTatolOdds = tatolOdds;
            }
        } else if (lotteryPlay.getPlayTagId() == Constants.FC3D_PLAY_2D_2BTH) {
            // 福彩3D玩法- 2D 最高中3注
            if (orderBetRecord.getBetCount() > 3) {
                maxTatolOdds = new BigDecimal(3).multiply(tatolOdds);
                maxTatolOdds = maxTatolOdds.divide(new BigDecimal(orderBetRecord.getBetCount()), 3,
                        BigDecimal.ROUND_DOWN);
            } else {
                maxTatolOdds = tatolOdds.divide(new BigDecimal(orderBetRecord.getBetCount()), 3, BigDecimal.ROUND_DOWN);
            }
        } else {
            maxTatolOdds = tatolOdds.divide(new BigDecimal(orderBetRecord.getBetCount()), 3, BigDecimal.ROUND_DOWN);
        }

        return maxTatolOdds.setScale(3, BigDecimal.ROUND_DOWN);
    }

    /**
     * 公共多种投注计算
     *
     * @param splitBetNum
     * @param lotteryPlay
     * @param oddsList
     * @param mapList
     * @return
     */
    private BigDecimal commonBigDecimal(String[] splitBetNum, LotteryPlay lotteryPlay, List<LotteryPlayOdds> oddsList,
                                        Map<String, LotteryPlayOdds> mapList) {
        BigDecimal tatolOdds = BigDecimal.ZERO;
        BigDecimal bigOrSmall = BigDecimal.ZERO;
        BigDecimal singleOrdouble = BigDecimal.ZERO;
        BigDecimal joinBigOrSmall = BigDecimal.ZERO;
        BigDecimal joinSingleOrdouble = BigDecimal.ZERO;
        BigDecimal dragonOrTiger = BigDecimal.ZERO;
        BigDecimal other = BigDecimal.ZERO;
        BigDecimal crownBigOrSmall = BigDecimal.ZERO;
        BigDecimal crownSingleOrdouble = BigDecimal.ZERO;
        BigDecimal smallSingleOrdouble = BigDecimal.ZERO;
        BigDecimal bigSingleOrdouble = BigDecimal.ZERO;
        for (String betContent : splitBetNum) {
            // 是否大小
            if (lotteryPlay.getPlayTagId() == Constants.FC3D_PLAY_HE) {
                if (Integer.parseInt(betContent) < 10) {
                    if (!betContent.contains("0")) {
                        betContent = "0".concat(betContent);
                    }
                }
            }
            LotteryPlayOdds playOdds = mapList.get(betContent);
            if (oddsList.size() > 1) {
                playOdds = mapList.get(betContent);
            } else {
                playOdds = oddsList.get(0);
            }
            if (betContent.equals(Constants.BIGORSMALL_BIG) || betContent.equals(Constants.BIGORSMALL_SMALL)) {
                if (bigOrSmall.compareTo(BigDecimal.ZERO) == 0) {
                    bigOrSmall = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(bigOrSmall) == 1) {
                        bigOrSmall = number;
                    }
                }
            } else if (betContent.equals(Constants.TOTAL_BIGORSMALL_BIG)
                    || betContent.equals(Constants.TOTAL_BIGORSMALL_SMALL)) {
                // 是否总和大小
                if (joinBigOrSmall.compareTo(BigDecimal.ZERO) == 0) {
                    joinBigOrSmall = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(joinBigOrSmall) == 1) {
                        joinBigOrSmall = number;
                    }
                }
            } else if (betContent.equals(Constants.BIGORSMALL_ODD_NUMBER)
                    || betContent.equals(Constants.BIGORSMALL_EVEN_NUMBER)) {
                // 是否单双
                if (singleOrdouble.compareTo(BigDecimal.ZERO) == 0) {
                    singleOrdouble = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(singleOrdouble) == 1) {
                        singleOrdouble = number;
                    }
                }
            } else if (betContent.equals(Constants.TOTAL_BIGORSMALL_ODD_NUMBER)
                    || betContent.equals(Constants.TOTAL_BIGORSMALL_EVEN_NUMBER)) {
                // 是否总和单双
                if (joinSingleOrdouble.compareTo(BigDecimal.ZERO) == 0) {
                    joinSingleOrdouble = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(joinSingleOrdouble) == 1) {
                        joinSingleOrdouble = number;
                    }
                }
            } else if (betContent.equals(Constants.PLAYRESULT_DRAGON) || betContent.equals(Constants.PLAYRESULT_TIGER)
                    || betContent.equals(Constants.BIGORSMALL_SAME)) {
                // 是否龙虎
                if (dragonOrTiger.compareTo(BigDecimal.ZERO) == 0) {
                    dragonOrTiger = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(dragonOrTiger) == 1) {
                        dragonOrTiger = number;
                    }
                }
            } else if (betContent.equals(Constants.CROWN_BIGORSMALL_ODD_NUMBER)
                    || betContent.equals(Constants.CROWN_BIGORSMALL_EVEN_NUMBER)) {
                // 是否冠亚 单双
                if (dragonOrTiger.compareTo(BigDecimal.ZERO) == 0) {
                    dragonOrTiger = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(dragonOrTiger) == 1) {
                        dragonOrTiger = number;
                    }
                }
            } else if (betContent.equals(Constants.CROWN_BIGORSMALL_ODD_NUMBER)
                    || betContent.equals(Constants.CROWN_BIGORSMALL_BIG)) {
                // 是否冠亚 大小
                if (crownSingleOrdouble.compareTo(BigDecimal.ZERO) == 0) {
                    crownSingleOrdouble = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(crownSingleOrdouble) == 1) {
                        crownSingleOrdouble = number;
                    }
                }
            } else if (betContent.equals(Constants.BIGORSMALL_SMALL_NUMBER)
                    || betContent.equals(Constants.BIGORSMALL_SMALL_DOUBLE)) {
                // 是否和值小单小双
                if (smallSingleOrdouble.compareTo(BigDecimal.ZERO) == 0) {
                    smallSingleOrdouble = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(smallSingleOrdouble) == 1) {
                        smallSingleOrdouble = number;
                    }
                }
            } else if (betContent.equals(Constants.BIGORSMALL_BIG_NUMBER)
                    || betContent.equals(Constants.BIGORSMALL_BIG_DOUBLE)) {
                // 是否和值大单大双
                if (bigSingleOrdouble.compareTo(BigDecimal.ZERO) == 0) {
                    bigSingleOrdouble = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(bigSingleOrdouble) == 1) {
                        bigSingleOrdouble = number;
                    }
                }
            } else {
                if (other.compareTo(BigDecimal.ZERO) == 0) {
                    other = new BigDecimal(playOdds.getTotalCount()).divide(new BigDecimal(playOdds.getWinCount()));
                } else {
                    // 获取最大
                    BigDecimal number = new BigDecimal(playOdds.getTotalCount())
                            .divide(new BigDecimal(playOdds.getWinCount()));
                    if (number.compareTo(other) == 1) {
                        other = number;
                    }
                }
            }
        }
        tatolOdds = bigOrSmall.add(singleOrdouble).add(joinBigOrSmall).add(joinSingleOrdouble).add(dragonOrTiger)
                .add(other).add(crownBigOrSmall).add(crownSingleOrdouble).add(smallSingleOrdouble)
                .add(bigSingleOrdouble);
        return tatolOdds;
    }

    /**
     * 获取一个最大赔率
     *
     * @param orderBetRecord
     * @return
     */
    private BigDecimal countOddsWithDivisor(OrderBetRecord orderBetRecord) {
        // 获取彩种信息
        Lottery lottery = lotteryService.selectLotteryByLotteryId(orderBetRecord.getLotteryId());

        // 获取赔率因子
        SysParameter systemInfo = sysParamService.getByCode(SysParameterEnum.REGISTER_MEMBER_ODDS);
        double divisor = Double.parseDouble(systemInfo.getParamValue());
        // 获取赔率因子
        Double maxOdds = lottery.getMaxOdds();
        divisor = maxOdds.equals(0D) ? divisor : maxOdds;

        // 获取配置信息中的赔率信息
        String betNumber = orderBetRecord.getBetNumber();
        if (betNumber.contains("@")) {
            betNumber = betNumber.split("@")[1];
        }

        // 通过配置获取赔率信息
        List<LotteryPlayOdds> oddsList = lotteryPlayOddsService
                .selectOddsListBySettingId(orderBetRecord.getSettingId());

        // 判空
        if (CollectionUtils.isEmpty(oddsList)) {
            return BigDecimal.ZERO;
        }
        LotteryPlayOdds odds = null;
        if (oddsList.size() == 1) {
            odds = oddsList.get(0);
        } else {
            TreeMap<Double, LotteryPlayOdds> maxOddstTreeMap = new TreeMap<>();
            for (LotteryPlayOdds playOdds : oddsList) {
                String[] splitBetNum = betNumber.split(",");
                for (String betContent : splitBetNum) {
                    if (playOdds.getName().equals(betContent)) {
                        maxOddstTreeMap.put(Double.parseDouble(playOdds.getTotalCount())
                                / Double.parseDouble(playOdds.getWinCount()), playOdds);
                    }
                }
            }
            // 获取最大odds
            int treeMapSize = maxOddstTreeMap.size();
            if (treeMapSize > 0) {
                odds = maxOddstTreeMap.get(maxOddstTreeMap.lastKey());
            }
        }

        if (odds == null) {
            return BigDecimal.ZERO;
        }

        String totalCount = odds.getTotalCount();
        BigDecimal winCount = BigDecimal.valueOf(Double.valueOf(odds.getWinCount()));
        BigDecimal oddsStr = BigDecimal.ZERO;
        if (totalCount.contains("/")) {
            String[] str = totalCount.split("/");
            oddsStr = BigDecimal.valueOf(Double.valueOf(str[0])).multiply(BigDecimal.valueOf(divisor)).divide(winCount,
                    3, BigDecimal.ROUND_HALF_UP);
        }
        return oddsStr;
    }

    private BigDecimal commLhc(OrderBetRecord orderBetRecord) {
        // 该订单最大中奖赔率
        Double maxOdd = 0.0;
        try {
            // 获取彩种信息
            Lottery lottery = lotteryService.selectLotteryByLotteryId(orderBetRecord.getLotteryId());

            // 获取赔率因子
            SysParameter systemInfo = sysParamService.getByCode(SysParameterEnum.REGISTER_MEMBER_ODDS);

            double divisor = Double.parseDouble(systemInfo.getParamValue());
            // 获取赔率因子
            Double maxOdds = lottery.getMaxOdds();
            divisor = maxOdds.equals(0D) ? divisor : maxOdds;

            // 获取配置信息中的赔率信息
            String betNumber = orderBetRecord.getBetNumber();

            // 通过配置获取赔率信息
            List<LotteryPlayOdds> oddsList = lotteryPlayOddsService
                    .selectOddsListBySettingId(orderBetRecord.getSettingId());
            Map<String, LotteryPlayOdds> oddsMap = this.selectPlayOddsBySettingId(orderBetRecord.getSettingId());

            // 判空
            if (CollectionUtils.isEmpty(oddsList)) {
                return BigDecimal.ZERO;
            }

            String[] splitBetNum = betNumber.split("@")[1].split(",");

            logger.info("commLhc splitBetNum = {} oddsMap={} ", splitBetNum, oddsMap);

            if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_TM)) {
                // 单注中奖可中最大赔率
                maxOdd = getMaxSingleOdd(maxOdd, splitBetNum, oddsMap, orderBetRecord.getBetCount(), oddsList);
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_TMLM)) {
                Double szOdd = 0.0; // 数字1-10,11-20
                Double dsOdd = 0.0; // 单双
                Double dxOdd = 0.0; // 大小
                Double hdsOdd = 0.0; // 合单双
                Double jyOdd = 0.0; // 家禽野兽
                Double wdxOdd = 0.0; // 尾大尾小
                Double bsOdd = 0.0; // 波色
                for (String betNum : splitBetNum) {
                    LotteryPlayOdds odd = oddsMap.get(betNum);
                    if ("单".equals(betNum) || "双".equals(betNum)) {
                        dsOdd = getMaxTypeOdd(dsOdd, odd);
                    } else if ("大".equals(betNum) || "小".equals(betNum)) {
                        dxOdd = getMaxTypeOdd(dxOdd, odd);
                    } else if ("合单".equals(betNum) || "合双".equals(betNum)) {
                        hdsOdd = getMaxTypeOdd(hdsOdd, odd);
                    } else if ("家禽".equals(betNum) || "野兽".equals(betNum)) {
                        jyOdd = getMaxTypeOdd(jyOdd, odd);
                    } else if ("尾大".equals(betNum) || "尾小".equals(betNum)) {
                        wdxOdd = getMaxTypeOdd(wdxOdd, odd);
                    } else if ("红波".equals(betNum) || "蓝波".equals(betNum) || "绿波".equals(betNum)) {
                        bsOdd = getMaxTypeOdd(bsOdd, odd);
                    } else {
                        szOdd = getMaxTypeOdd(szOdd, odd);
                    }
                }
                maxOdd = (szOdd + dsOdd + dxOdd + hdsOdd + jyOdd + wdxOdd + bsOdd) / orderBetRecord.getBetCount();
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_ZM)) {
                if ("正码两面".equals(betNumber.split("@")[0])) {
                    Double zdsOdd = 0.0; // 总单双
                    Double zdxOdd = 0.0; // 总大小
                    Double zwdxOdd = 0.0; // 总尾大小
                    Double lhOdd = 0.0; // 龙虎
                    for (String betNum : splitBetNum) {
                        LotteryPlayOdds odd = oddsMap.get(betNum);
                        if ("总单".equals(betNum) || "总双".equals(betNum)) {
                            zdsOdd = getMaxTypeOdd(zdsOdd, odd);
                        } else if ("总大".equals(betNum) || "总小".equals(betNum)) {
                            zdxOdd = getMaxTypeOdd(zdxOdd, odd);
                        } else if ("总尾大".equals(betNum) || "总尾小".equals(betNum)) {
                            zwdxOdd = getMaxTypeOdd(zwdxOdd, odd);
                        } else if ("龙".equals(betNum) || "虎".equals(betNum)) {
                            lhOdd = getMaxTypeOdd(lhOdd, odd);
                        }
                    }
                    maxOdd = (zdsOdd + zdxOdd + zwdxOdd + lhOdd) / orderBetRecord.getBetCount();
                } else if ("正码".equals(betNumber.split("@")[0])) { // 最多中6个球
                    if (splitBetNum.length <= 6) {
                        for (String singleBetNum : splitBetNum) {
                            LotteryPlayOdds odds = oddsMap.get(singleBetNum);
                            maxOdd += (Double.valueOf(odds.getTotalCount()) / Double.valueOf(odds.getWinCount()));
                        }
                    } else { // 找出6个赔率最大的球
                        Double odds[] = new Double[splitBetNum.length];
                        for (int i = 0; i < odds.length; i++) {
                            LotteryPlayOdds lotteryPlayOdds = oddsMap.get(splitBetNum[i]);
                            odds[i] = (Double.valueOf(lotteryPlayOdds.getTotalCount())
                                    / Double.valueOf(lotteryPlayOdds.getWinCount()));
                        }
                        Arrays.sort(odds);
                        for (int i = odds.length; i > odds.length - 6; i--) {
                            maxOdd += odds[i - 1];
                        }
                    }
                    maxOdd = maxOdd / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_ZM16)) {
                Double dsOdd = 0.0; // 正码1-6单双
                Double dxOdd = 0.0; // 正码1-6大小
                Double hdsOdd = 0.0; // 正码1-6合单双
                Double wdxOdd = 0.0; // 正码1-6尾大小
                Double bsOdd = 0.0; // 正码1-6波色
                for (String betNum : splitBetNum) {
                    LotteryPlayOdds odd = oddsMap.get(betNum);
                    if ("单".equals(betNum) || "双".equals(betNum)) {
                        dsOdd = getMaxTypeOdd(dsOdd, odd);
                    } else if ("大".equals(betNum) || "小".equals(betNum)) {
                        dxOdd = getMaxTypeOdd(dxOdd, odd);
                    } else if ("合单".equals(betNum) || "合双".equals(betNum)) {
                        hdsOdd = getMaxTypeOdd(hdsOdd, odd);
                    } else if ("尾大".equals(betNum) || "尾小".equals(betNum)) {
                        wdxOdd = getMaxTypeOdd(wdxOdd, odd);
                    } else if ("红波".equals(betNum) || "蓝波".equals(betNum) || "绿波".equals(betNum)) {
                        bsOdd = getMaxTypeOdd(bsOdd, odd);
                    }
                }
                maxOdd = (dsOdd + dxOdd + hdsOdd + wdxOdd + bsOdd) / orderBetRecord.getBetCount();
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_Z1T)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_Z2T)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_Z3T)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_Z4T)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_Z5T)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_Z6T)) {
                if (betNumber.split("@")[0].contains("两面")) {
                    Double dsOdd = 0.0; // 单双
                    Double dxOdd = 0.0; // 大小
                    Double wdxOdd = 0.0; // 尾大小
                    Double hdsOdd = 0.0; // 合单双
                    Double bsOdd = 0.0; // 波色
                    for (String betNum : splitBetNum) {
                        LotteryPlayOdds odd = oddsMap.get(betNum);
                        if ("单".equals(betNum) || "双".equals(betNum)) {
                            dsOdd = getMaxTypeOdd(dsOdd, odd);
                        } else if ("大".equals(betNum) || "小".equals(betNum)) {
                            dxOdd = getMaxTypeOdd(dxOdd, odd);
                        } else if ("尾大".equals(betNum) || "尾小".equals(betNum)) {
                            wdxOdd = getMaxTypeOdd(wdxOdd, odd);
                        } else if ("合单".equals(betNum) || "合双".equals(betNum)) {
                            hdsOdd = getMaxTypeOdd(hdsOdd, odd);
                        } else if ("红波".equals(betNum) || "蓝波".equals(betNum) || "绿波".equals(betNum)) {
                            bsOdd = getMaxTypeOdd(bsOdd, odd);
                        }
                    }
                    maxOdd = (dsOdd + dxOdd + wdxOdd + hdsOdd + bsOdd) / orderBetRecord.getBetCount();
                } else { // 正1特-正6特 数字 只中一个赔率
                    // 单注中奖可中最大赔率
                    maxOdd = getMaxSingleOdd(maxOdd, splitBetNum, oddsMap, orderBetRecord.getBetCount(), oddsList);
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_3Z2)) {
                // 1注(3个号码) 最多中1注 1大
                // 4注(4个号码) 最多中4注 4大
                // 10注(5个号码) 最多中10注 10大
                // 20注(6个号码) 最多中20注 20大
                // 35注(7个号码) 最多中20注 20大

                String[] oddArray = new String[2];
                if (oddsList.size() <= 1) {
                    LotteryPlayOdds odd = oddsList.get(0);
                    oddArray = odd.getTotalCount().split("/");
                }
                if (orderBetRecord.getBetCount() == 1) {
                    maxOdd = Double.valueOf(oddArray[0]) * 1 / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 4) {
                    maxOdd = (Double.valueOf(oddArray[0]) * 4) / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 10) {
                    maxOdd = (Double.valueOf(oddArray[0]) * 10) / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() >= 20) {
                    maxOdd = (Double.valueOf(oddArray[0]) * 20) / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_2ZT)) {
                // 1注(2个号码) 最多中1注 1大
                // 3注(3个号码) 最多中3注 2大+1小
                // 6注(4个号码) 最多中6注 3大+3小
                // 10注(5个号码) 最多中10注 4大+6小
                // 15注(6个号码) 最多中15注 5大+10小
                // 21注(7个号码) 最多中21注 6大+15小
                // 28注(8个号码) 最多中21注 6大+15小
                String[] oddArray = new String[2];
                if (oddsList.size() <= 1) {
                    LotteryPlayOdds odd = oddsList.get(0);
                    oddArray = odd.getTotalCount().split("/");
                }
                if (orderBetRecord.getBetCount() == 1) {
                    maxOdd = Double.valueOf(oddArray[0]) * 1 / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 3) {
                    maxOdd = (Double.valueOf(oddArray[0]) * 2 + Double.valueOf(oddArray[1]) * 1)
                            / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 6) {
                    maxOdd = (Double.valueOf(oddArray[0]) * 3 + Double.valueOf(oddArray[1]) * 3)
                            / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 10) {
                    maxOdd = (Double.valueOf(oddArray[0]) * 4 + Double.valueOf(oddArray[1]) * 6)
                            / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 15) {
                    maxOdd = (Double.valueOf(oddArray[0]) * 5 + Double.valueOf(oddArray[1]) * 10)
                            / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() >= 21) {
                    maxOdd = (Double.valueOf(oddArray[0]) * 6 + Double.valueOf(oddArray[1]) * 15)
                            / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_TC)) {// 特串， 取其中一个赔率*基数
                // 1注(2个号码) 最多中1注
                // 3注(3个号码) 最多中2注
                // 6注(4个号码) 最多中3注
                // 10注(5个号码) 最多中4注
                // 15注(6个号码) 最多中5注
                // 21注(7个号码) 最多中6注
                // 28注(8个号码) 最多中6注
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getBetCount() == 1) {
                    maxOdd = maxOdd * 1 / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 3) {
                    maxOdd = maxOdd * 2 / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 6) {
                    maxOdd = maxOdd * 3 / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 10) {
                    maxOdd = maxOdd * 4 / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() == 15) {
                    maxOdd = maxOdd * 5 / orderBetRecord.getBetCount();
                } else if (orderBetRecord.getBetCount() >= 21) {
                    maxOdd = maxOdd * 6 / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_EQZ)) {
                // 最高可中15注， 当注数<=15 基数=1，当注数>15 基数=15/注数
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getBetCount() > 15) {
                    maxOdd = maxOdd * 15 / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_3QZ)) { // 3全中 取其中一个赔率*基数
                // 最高可中20注， 当注数<=20 基数=1，当注数>20 基数=20/注数
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getBetCount() > 20) {
                    maxOdd = maxOdd * 20 / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_RED)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_BLUE)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_GREEN)) {
                Double dsOdd = 0.0; // 单双
                Double dxOdd = 0.0; // 大小
                Double hdsOdd = 0.0; // 合单双
                for (String betNum : splitBetNum) {
                    LotteryPlayOdds odd = oddsMap.get(betNum);
                    if (betNum.contains("合单") || betNum.contains("合双")) {
                        hdsOdd = getMaxTypeOdd(hdsOdd, odd);
                    } else if (betNum.contains("单") || betNum.contains("双")) {
                        dsOdd = getMaxTypeOdd(dsOdd, odd);
                    } else if (betNum.contains("大") || betNum.contains("小")) {
                        dxOdd = getMaxTypeOdd(dxOdd, odd);
                    }
                }
                maxOdd = (dsOdd + dxOdd + hdsOdd) / orderBetRecord.getBetCount();
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_ALLTAIL)) {
                // 全尾（最高可中 7 注）
                // 低于7 注 有最低赔率要算
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                Double minOdd = getMinOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (maxOdd > minOdd) {
                    if (orderBetRecord.getBetCount() > 7) {
                        double momey = 6 * minOdd;
                        double number = momey + maxOdd;
                        maxOdd = number / orderBetRecord.getBetCount();
                    } else {
                        double momey = (orderBetRecord.getBetCount() - 1) * minOdd;
                        double number = momey + maxOdd;
                        maxOdd = number / orderBetRecord.getBetCount();
                    }
                } else {
                    if (orderBetRecord.getBetCount() > 7) {
                        maxOdd = (7 * maxOdd) / orderBetRecord.getBetCount();
                    }
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_TETAIL)) {
                // 单注中奖可中最大赔率
                maxOdd = getMaxSingleOdd(maxOdd, splitBetNum, oddsMap, orderBetRecord.getBetCount(), oddsList);
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_5BZ)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_6BZ)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_7BZ)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_8BZ)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_9BZ)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_10BZ)) { // 5不中，6不中。。。。10不中 取其中一个赔率
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_PT)) { // 平特-可中多个赔率
                maxOdd = getMaxDuoOdd(maxOdd, splitBetNum, oddsMap, orderBetRecord.getBetCount());
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_6XLZ)) { // 6肖连中 取其中一个赔率*基数
//			6个球  c5,5/c6,6  1/1
//			7个球  c6,5/c7,6  6/7
//			8个球  c7,5/c8,6  21/28
//			9个球  c8,5/c9,6  56/84
//			10个球 c9,5/c10,6  126/210
//			11个球 c10,5/c11,6  252/462
//			12个球 c11,5/c12,6  462/924
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getBetCount() == 6) {
                    maxOdd = maxOdd * 1;
                } else if (orderBetRecord.getBetCount() == 7) {
                    maxOdd = maxOdd * 6 / 7;
                } else if (orderBetRecord.getBetCount() == 8) {
                    maxOdd = maxOdd * 21 / 28;
                } else if (orderBetRecord.getBetCount() == 9) {
                    maxOdd = maxOdd * 56 / 84;
                } else if (orderBetRecord.getBetCount() == 10) {
                    maxOdd = maxOdd * 126 / 210;
                } else if (orderBetRecord.getBetCount() == 11) {
                    maxOdd = maxOdd * 252 / 462;
                } else if (orderBetRecord.getBetCount() == 12) {
                    maxOdd = maxOdd * 462 / 924;
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_6XLBZ)) { // 6肖连不中 取其中一个赔率
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_2LXZ)) {
                // 2肖连中(最高中21注)
                // 2肖连中 选有最小赔率
                // 2 球组合 含有1 注
                // 3 球组合 含有2 注
                // 4 球组合 含有3 注
                // 5 球组合 含有4 注
                // 6 球组合 含有5 注
                // 7 球组合 含有6 注
                // 大于等于 8 球不用管最小配合注数
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                Double minOdd = getMinOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (minOdd < maxOdd) {
                    if (splitBetNum.length >= 8) {
                        if (orderBetRecord.getBetCount() > 21) {
                            maxOdd = maxOdd * 21 / orderBetRecord.getBetCount();
                        }
                    } else {
                        double number = (splitBetNum.length - 1) * minOdd;
                        double maxNumberOdd = (orderBetRecord.getBetCount() - (splitBetNum.length - 1)) * maxOdd;
                        maxOdd = (number + maxNumberOdd) / orderBetRecord.getBetCount();
                    }
                } else {
                    if (orderBetRecord.getBetCount() > 21) {
                        maxOdd = maxOdd * 21 / orderBetRecord.getBetCount();
                    }
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_2LXBZ)) { // 2肖连不中(最高中45注) 取其中一个赔率*基数
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getBetCount() > 45) {
                    maxOdd = maxOdd * 45 / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_3LXZ)) {
                // 3肖连中(最高中35注) 取其中一个赔率*基数
                // 3肖连中 选有最小赔率
                // 3 球组合 含有1 注
                // 4 球组合 含有3 注
                // 5 球组合 含有6 注
                // 6 球组合 含有10 注
                // 7 球组合 含有15 注
                // 大于等于 8 球不用管最小配合注数
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                Double minOdd = getMinOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (minOdd < maxOdd) {
                    int num = 0;
                    double number = 0;
                    if (splitBetNum.length == 3) {
                        num = 1;
                        number = num * minOdd;
                    }
                    if (splitBetNum.length == 4) {
                        num = 3;
                        number = num * minOdd;
                    }
                    if (splitBetNum.length == 5) {
                        num = 6;
                        number = num * minOdd;
                    } else if (splitBetNum.length == 6) {
                        num = 10;
                        number = num * minOdd;
                    } else if (splitBetNum.length == 7) {
                        num = 15;
                        number = num * minOdd;
                    }
                    if (splitBetNum.length >= 8) {
                        if (orderBetRecord.getBetCount() > 35) {
                            maxOdd = maxOdd * 35 / orderBetRecord.getBetCount();
                        }
                    } else {
                        double maxNumberOdd = 0;
                        if (orderBetRecord.getBetCount() > 35) {
                            maxNumberOdd = (35 - num) * maxOdd;
                        } else {
                            maxNumberOdd = (orderBetRecord.getBetCount() - num) * maxOdd;
                        }
                        maxOdd = (number + maxNumberOdd) / orderBetRecord.getBetCount();
                    }
                } else {
                    if (orderBetRecord.getBetCount() > 35) {
                        maxOdd = maxOdd * 35 / orderBetRecord.getBetCount();
                    }
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_3LXBZ)) { // 3肖连不中(最高中120注) 取其中一个赔率*基数
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getBetCount() > 120) {
                    maxOdd = maxOdd * 120 / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_4LXZ)) {
                // 4肖连中(最高中35注)
                // 4肖连中 选有最小赔率
                // 4 球组合 含有1 注
                // 5 球组合 含有4 注
                // 6 球组合 含有10 注
                // 7 球组合 含有20 注
                // 大于等于 8 球不用管最小配合注数
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                Double minOdd = getMinOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (minOdd < maxOdd) {
                    int num = 0;
                    double number = 0;
                    if (splitBetNum.length == 4) {
                        num = 1;
                        number = num * minOdd;
                    }
                    if (splitBetNum.length == 5) {
                        num = 4;
                        number = num * minOdd;
                    } else if (splitBetNum.length == 6) {
                        num = 10;
                        number = num * minOdd;
                    } else if (splitBetNum.length == 7) {
                        num = 20;
                        number = num * minOdd;
                    }
                    if (splitBetNum.length >= 8) {
                        if (orderBetRecord.getBetCount() > 35) {
                            maxOdd = maxOdd * 35 / orderBetRecord.getBetCount();
                        }
                    } else {
                        double maxNumberOdd = 0;
                        if (orderBetRecord.getBetCount() > 35) {
                            maxNumberOdd = (35 - num) * maxOdd;
                        } else {
                            maxNumberOdd = (orderBetRecord.getBetCount() - num) * maxOdd;
                        }
                        maxOdd = (number + maxNumberOdd) / orderBetRecord.getBetCount();
                    }
                } else {
                    if (orderBetRecord.getBetCount() > 35) {
                        maxOdd = maxOdd * 35 / orderBetRecord.getBetCount();
                    }
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_4LXBZ)) { // 4肖连不中(最高中210注) 取其中一个赔率*基数
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getBetCount() > 210) {
                    maxOdd = maxOdd * 210 / orderBetRecord.getBetCount();
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_TX)) { // 特肖，只中一个赔率
                // 单注中奖可中最大赔率
                maxOdd = getMaxSingleOdd(maxOdd, splitBetNum, oddsMap, orderBetRecord.getBetCount(), oddsList);
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_2LWZ)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_2LWBZ)) {
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_2LWBZ)) {
                    // 三连尾不中 选有最小赔率 最高可中（28注）
                    // 3 球组合 含有2 注
                    // 4 球组合 含有3 注
                    // 5 球组合 含有4 注
                    // 6 球组合 含有5 注
                    // 7 球组合 含有6 注
                    // 8 球以上不用管最小配合注数
                    Double minOdd = getMinOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                    if (minOdd < maxOdd) {
                        if (splitBetNum.length > 8) {
                            if (orderBetRecord.getBetCount() > 28) {
                                maxOdd = maxOdd * 28 / orderBetRecord.getBetCount();
                            }
                        } else {

                            double number = (splitBetNum.length - 1) * minOdd;
                            double maxNumberOdd = (orderBetRecord.getBetCount() - (splitBetNum.length - 1)) * maxOdd;
                            maxOdd = (number + maxNumberOdd) / orderBetRecord.getBetCount();
                        }
                    } else {
                        if (orderBetRecord.getBetCount() > 28) {
                            maxOdd = maxOdd * 28 / orderBetRecord.getBetCount();
                        }
                    }
                } else {
                    // 二连尾中 最高可中（21注）
                    if (orderBetRecord.getBetCount() > 21) {
                        maxOdd = maxOdd * 21 / orderBetRecord.getBetCount();
                    }
                }

            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_3LWZ)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_3LWBZ)) {
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_3LWBZ)) {
                    // 三连尾不中 选有最小赔率 最高可中（56注）
                    // 4 球组合 含有1 注
                    // 5 球组合 含有3 注
                    // 6 球组合 含有6 注
                    // 7 球组合 含有10 注
                    // 8 球组合 含有21 注
                    // 8 球以上不用管最小配合注数
                    Double minOdd = getMinOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                    if (minOdd < maxOdd) {
                        int num = 0;
                        double number = 0;
                        if (splitBetNum.length == 3) {
                            num = 1;
                            number = num * minOdd;
                        }
                        if (splitBetNum.length == 4) {
                            num = 3;
                            number = num * minOdd;
                        }
                        if (splitBetNum.length == 5) {
                            num = 6;
                            number = num * minOdd;
                        } else if (splitBetNum.length == 6) {
                            num = 10;
                            number = num * minOdd;
                        } else if (splitBetNum.length == 7) {
                            num = 15;
                            number = num * minOdd;
                        } else if (splitBetNum.length == 8) {
                            num = 21;
                            number = num * minOdd;
                        }
                        if (splitBetNum.length > 8) {
                            if (orderBetRecord.getBetCount() > 56) {
                                maxOdd = maxOdd * 56 / orderBetRecord.getBetCount();
                            }
                        } else {
                            double maxNumberOdd = (orderBetRecord.getBetCount() - num) * maxOdd;
                            maxOdd = (number + maxNumberOdd) / orderBetRecord.getBetCount();
                        }
                    } else {
                        if (orderBetRecord.getBetCount() > 56) {
                            maxOdd = maxOdd * 56 / orderBetRecord.getBetCount();
                        }
                    }
                } else {
                    // 三连尾中（最高中35 注）
                    if (orderBetRecord.getBetCount() > 35) {
                        maxOdd = maxOdd * 35 / orderBetRecord.getBetCount();
                    }
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_4LWZ)
                    || orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_4LWBZ)) {
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_4LWBZ)) {
                    // 四连尾不中（最高中70 注
                    Double minOdd = getMinOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
                    if (minOdd < maxOdd) {
                        // 四连尾不中 选有最小赔率
                        // 4 球组合 含有1 注
                        // 5 球组合 含有4 注
                        // 6 球组合 含有10 注
                        // 7 球组合 含有20 注
                        // 8 球组合 含有35 注
                        // 8 球以上不用管最小配合注数
                        int num = 0;
                        double number = 0;
                        if (splitBetNum.length == 4) {
                            num = 1;
                            number = num * minOdd;
                        }
                        if (splitBetNum.length == 5) {
                            num = 4;
                            number = num * minOdd;
                        } else if (splitBetNum.length == 6) {
                            num = 10;
                            number = num * minOdd;
                        } else if (splitBetNum.length == 7) {
                            num = 20;
                            number = num * minOdd;
                        } else if (splitBetNum.length == 8) {
                            num = 35;
                            number = num * minOdd;
                        }
                        if (splitBetNum.length > 8) {
                            maxOdd = maxOdd * 70 / orderBetRecord.getBetCount();
                        } else {
                            double maxNumberOdd = (orderBetRecord.getBetCount() - num) * maxOdd;
                            maxOdd = (number + maxNumberOdd) / orderBetRecord.getBetCount();
                        }
                    } else {
                        if (orderBetRecord.getBetCount() > 70) {
                            maxOdd = maxOdd * 70 / orderBetRecord.getBetCount();
                        }
                    }
                } else {
                    // 四连尾中（最高中35 注）
                    if (orderBetRecord.getBetCount() > 35) {
                        maxOdd = maxOdd * 35 / orderBetRecord.getBetCount();
                    }
                }
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_16LH)) { // 1-6龙虎 取其中一个赔率
                maxOdd = getMaxOneOdd(maxOdd, splitBetNum, oddsMap, oddsList);
            } else if (orderBetRecord.getPlayId().equals(Constants.LHC_PLAY_WX)) { // 五行 取其中最大一个赔率
                // 单注中奖可中最大赔率
                maxOdd = getMaxSingleOdd(maxOdd, splitBetNum, oddsMap, orderBetRecord.getBetCount(), oddsList);
            }
        } catch (Exception e) {
            logger.error("commLhc error", e);
        }
        BigDecimal bmaxOdd = new BigDecimal(Double.toString(maxOdd));
        return bmaxOdd;
    }

    // 单注中奖可中最大赔率-只中一个赔率
    public static Double getMaxSingleOdd(Double maxOdd, String[] splitBetNum, Map<String, LotteryPlayOdds> oddsMap,
                                         int betCount, List<LotteryPlayOdds> oddsList) {
        Double maxSingleOdd = 0.0;
        if (oddsMap.size() > 1) {
            for (String betNum : splitBetNum) {
                LotteryPlayOdds odd = oddsMap.get(betNum);
                if (maxSingleOdd == 0.0) {
                    maxSingleOdd = Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount());
                } else {
                    Double thisOdd = Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount());
                    if (thisOdd > maxSingleOdd) {
                        maxSingleOdd = thisOdd;
                    }
                }
            }
        } else {
            LotteryPlayOdds odd = oddsList.get(0);
            maxSingleOdd = (Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount()));
        }
        maxOdd = maxSingleOdd / betCount;
        return maxOdd;
    }

    // 单注中奖可中最大赔率-可中多个赔率
    public static Double getMaxDuoOdd(Double maxOdd, String[] splitBetNum, Map<String, LotteryPlayOdds> oddsMap,
                                      int betCount) {
        for (String betNum : splitBetNum) {
            LotteryPlayOdds odd = oddsMap.get(betNum);
            maxOdd += (Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount()));
        }
        maxOdd = maxOdd / betCount;
        return maxOdd;
    }

    // 单注中奖可中最大赔率-取其中一个赔率
    public static Double getMaxOneOdd(Double maxOdd, String[] splitBetNum, Map<String, LotteryPlayOdds> oddsMap,
                                      List<LotteryPlayOdds> oddsList) {
        if (oddsMap.size() > 1) {
            for (String betNum : splitBetNum) {
                LotteryPlayOdds odd = oddsMap.get(betNum);
                if (maxOdd == 0.0) {
                    maxOdd = (Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount()));
                } else {
                    Double thisOdd = Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount());
                    if (thisOdd > maxOdd) {
                        maxOdd = thisOdd;
                    }
                }
            }
        } else {
            LotteryPlayOdds odd = oddsList.get(0);
            maxOdd = (Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount()));
        }
        return maxOdd;
    }

    // 单注中奖可中最大赔率-取最小一个赔率
    public static Double getMinOneOdd(Double maxOdd, String[] splitBetNum, Map<String, LotteryPlayOdds> oddsMap,
                                      List<LotteryPlayOdds> oddsList) {
        if (oddsMap.size() > 1) {
            for (String betNum : splitBetNum) {
                LotteryPlayOdds odd = oddsMap.get(betNum);
                if (maxOdd == 0.0) {
                    maxOdd = (Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount()));
                } else {
                    Double thisOdd = Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount());
                    if (thisOdd < maxOdd) {
                        maxOdd = thisOdd;
                    }
                }
            }
        } else {
            LotteryPlayOdds odd = oddsList.get(0);
            maxOdd = (Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount()));
        }
        return maxOdd;
    }

    // 求某一类型的最大赔率
    public static Double getMaxTypeOdd(Double dsOdd, LotteryPlayOdds odd) {
        if (dsOdd == 0.0) {
            dsOdd = Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount());
        } else {
            Double thisOdd = Double.valueOf(odd.getTotalCount()) / Double.valueOf(odd.getWinCount());
            if (thisOdd > dsOdd) {
                dsOdd = thisOdd;
            }
        }
        return dsOdd;
    }
}
