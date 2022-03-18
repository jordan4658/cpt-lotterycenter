package com.caipiao.live.common.mybatis.mapperbean;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PceggBeanMapper {

    /**
     * 获取指定日期已开奖次数
     * @return
     */
    @Select("SELECT COUNT(*) FROM `lottery_pcegg_lottery_sg` s WHERE s.`time` LIKE #{date}")
    Integer getOpenCount(@Param("date") String date);

}
