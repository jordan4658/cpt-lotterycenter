package com.caipiao.live.common.mybatis.mapperbean;


import com.caipiao.live.common.model.vo.BjpksSgVO;
import com.caipiao.live.common.mybatis.entity.BjpksLotterySg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LuckpksBeanMapper {

    @Select("SELECT b.number FROM lottery_luckpks_lottery_sg b where b.time like #{date} and b.number is not null")
    List<String> selectNumberByDate(@Param("date") String date);

    @Select("SELECT b.number FROM lottery_luckpks_lottery_sg b where b.time like #{date} and b.number is not null order by issue DESC")
    List<String> selectNumberByDateDesc(@Param("date") String date);

    @Select("SELECT b.number FROM lottery_luckpks_lottery_sg b where b.number is not null ORDER BY b.issue desc limit #{issue}")
    List<String> selectNumberLimitDesc(@Param("issue") Integer issue);

    @Select("SELECT b.id, b.issue, b.number, b.time FROM lottery_luckpks_lottery_sg b where b.time like #{date} and b.number is not null order by b.issue desc")
    List<BjpksLotterySg> selectByDateDesc(@Param("date") String date);

    @Select("SELECT b.issue, b.number FROM lottery_luckpks_lottery_sg b where b.number is not null ORDER BY b.issue desc limit #{issue}")
    List<BjpksSgVO> selectLimitDesc(@Param("issue") Integer issue);

}
