package com.caipiao.live.common.mybatis.mapperbean;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PublicBeanMapper {

    @Select("SELECT s.`issue` FROM ${tableName} s where s.ideal_time > #{date} ORDER BY s.`issue` ASC LIMIT 0,1")
    List<String> selectByTableName(@Param("tableName") String tableName, @Param("date") String date);
    
    
    @Select("SELECT is_work FROM `lottery` where lottery_id = #{lotteryId} LIMIT 1")
    Integer selectLotteryIsWork(@Param("lotteryId") Integer lotteryId);
    
}
