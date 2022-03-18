package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;
import com.caipiao.live.common.mybatis.entity.OrderBetRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LotteryPlayOddsServiceReadSg {


    /**
     * 根据配置id查询相关赔率信息
     *
     * @param settingId 配置id
     * @return
     */
    List<LotteryPlayOdds> selectOddsListBySettingId(Integer settingId);

    /**
     * 精准获取指定号码的赔率
     *
     * @param lotteryId 彩种id
     * @param settingId 配置id
     * @param betNumber 投注号码
     * @return
     */
    BigDecimal countOdds(Integer lotteryId, Integer settingId, String betNumber);

    /**
     * 查询所有赔率设置List集合
     *
     * @return
     */
    List<LotteryPlayOdds> selectPlayOddsList();

    /**
     * 查询所有赔率设置List集合
     *
     * @return
     */
    Map<Integer, LotteryPlayOdds> selectPlayOddsMap();

    /**
     * 查询玩法赔率
     *
     * @param playSettingList
     * @return Map<#   settingId   ,   #   List   <   LotteryPlayOdds>>
     */
    Map<Integer, List<LotteryPlayOdds>> selectOddsListBySettingIdList(List<Integer> playSettingList);

    BigDecimal circleOdds(OrderBetRecord orderBetRecord);

    Map<String, LotteryPlayOdds> selectPlayOddsBySettingId(Integer settingId);

    /**
     * 通过playid查询具体名称
     * @param playId
     * @return
     */
    List<String> selectByEasyImportFlag(Integer playId);

}


