package com.mapper;

import com.mapper.domain.OrderBetRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GodInfoTaskMapper {
    /**
     * 大神一段时间内的注单记录（根据推单来的）
     * @param godId
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderBetRecord> getBetOrderListByGods(@Param("godId")Integer godId, @Param("startTime")String startTime, @Param("endTime")String endTime);

}
