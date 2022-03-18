package com.caipiao.live.common.mybatis.mapperbean;

import com.caipiao.live.common.model.vo.BjpksSgVO;
import com.caipiao.live.common.mybatis.entity.BjpksLotterySg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface XyftBeanMapper {

    @Select("SELECT s.`number` FROM `lottery_xyft_lottery_sg` s WHERE s.`issue` LIKE #{date} and s.number is not null ORDER BY s.`issue` ASC")
    List<String> selectNumberByDate(@Param("date") String date);

    @Select("SELECT s.`issue`, s.`number` FROM `lottery_xyft_lottery_sg` s where s.number is not null ORDER BY s.`issue` DESC LIMIT #{size}")
    List<BjpksSgVO> selectLimitDesc(@Param("size") Integer size);

}
