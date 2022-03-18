package com.caipiao.live.common.service.lottery;

import com.caipiao.live.common.mybatis.entity.LotteryFavorite;

import java.util.List;

public interface LotteryFavoriteReadServiceReadSg {

    /**
     * 查询用户彩票收藏列表
     *
     * @param userId
     * @return
     */
    List<LotteryFavorite> queryByUserId(int userId);

    /**
     *
     * @return
     */
    List<LotteryFavorite> queryDefaultLotteryFavorite();
}
