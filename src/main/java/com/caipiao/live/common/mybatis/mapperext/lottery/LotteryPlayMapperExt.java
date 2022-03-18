package com.caipiao.live.common.mybatis.mapperext.lottery;

import org.apache.ibatis.annotations.Param;

public interface LotteryPlayMapperExt {

    /**
     * 更新指定彩种的玩法的彩种分类id
     *
     * @param lotteryId
     * @param categoryId
     */
    void updateCategoryId(@Param("lotteryId") Integer lotteryId, @Param("categoryId") Integer categoryId);
}
