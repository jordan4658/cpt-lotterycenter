package com.mapper;

import com.mapper.domain.AppMember;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BackWaterInfoMapper {
    /**
     * 查询出有效投注>0的用户
     */
    List<AppMember> getValidUsers();

    /**
     * 根据用户集合查询有效投注
     */
    BigDecimal getValidBetAmountByUserIds(@Param("idList") List<Integer> idList, @Param("startTime") String startTime);

    /**
     * 查询2层用户
     */
    List<Integer> getTwoCengUsersByOneCengUserIds(@Param("idList") List<Integer> idList);
}
