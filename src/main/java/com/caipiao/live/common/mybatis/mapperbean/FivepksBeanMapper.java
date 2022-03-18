package com.caipiao.live.common.mybatis.mapperbean;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FivepksBeanMapper {

    @Select("SELECT b.number FROM lottery_fivepks_lottery_sg b where b.time like #{date} and b.number is not null")
    List<String> selectNumberByDate(@Param("date") String date);

}
