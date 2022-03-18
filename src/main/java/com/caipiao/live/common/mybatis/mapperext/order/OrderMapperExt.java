package com.caipiao.live.common.mybatis.mapperext.order;

import com.caipiao.live.common.mybatis.entity.OrderBetRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 查询注单
 * @author dlucky
 *
 */
public interface OrderMapperExt{
    
    Integer countOrderBetList(@Param("issue") String issue,@Param("playIds") List<Integer> playIds,@Param("lotteryId") String lotteryId,@Param("status") String status);
    
    List<OrderBetRecord> selectOrderBetList(@Param("issue") String issue, @Param("playIds") List<Integer> playIds, @Param("lotteryId") String lotteryId, @Param("status") String status, @Param("id") int id, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);


}
