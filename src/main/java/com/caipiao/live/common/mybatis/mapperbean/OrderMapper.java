package com.caipiao.live.common.mybatis.mapperbean;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


public interface OrderMapper {


    /**
     * 查询注单
     */


    @Update("<script>"
            + "update lottery_order_record set open_number=#{sgnumber} where lottery_id =#{lotteryId} and issue=#{issue} and status='NORMAL' and open_number=''  LIMIT  50000 "
            + "</script>")
    int updateOrderRecord(@Param("lotteryId") String lotteryId, @Param("issue") String issue, @Param("sgnumber") String sgnumber);

}
