package com.caipiao.live.common.mybatis.mapperext.lottery;

import com.caipiao.live.common.mybatis.entity.LotteryFavorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LotteryFavoriteMapperExt {

    /**
     * 用户，彩票：我的收藏变更
     *
     * @param userId
     * @param lotteryFavorites
     */
    void update(@Param("userId") int userId, @Param("lotteryFavorites") List<LotteryFavorite> lotteryFavorites);

    /**
     * 删除用户收藏彩票
     *
     * @param userId
     * @param lotteryFavoriteIds
     */
    void delete(@Param("userId") int userId, @Param("lotteryFavoriteIds") List<Integer> lotteryFavoriteIds);
}
