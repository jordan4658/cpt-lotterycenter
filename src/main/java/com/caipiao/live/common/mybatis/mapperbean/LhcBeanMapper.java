package com.caipiao.live.common.mybatis.mapperbean;

import com.caipiao.live.common.model.dto.lottery.LotterySgModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LhcBeanMapper {

    /**
     * 获取六合彩最近多少期的开奖结果
     *
     * @param issues 期数
     * @return
     */
    @Select("select number from lottery_lhc_lottery_sg order by year desc,issue desc limit #{issues}")
    List<String> getSg(@Param("issues") Integer issues);

    @Select("select issue, number as sg, time as date from lottery_lhc_lottery_sg where year = #{year} order by issue desc")
    List<LotterySgModel> getSgByYear(@Param("year") String year);

    @Select("select issue, number as sg, time as date from lottery_lhc_lottery_sg where year = #{year} order by issue DESC limit #{pageNo},#{pageSize}")
    List<LotterySgModel> getSgByYearDesc(@Param("year") String year, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    /**
     * 获取六合彩最近多少期的开奖结果和开奖日期
     *
     * @param issues 期数
     * @return
     */
    @Select("select number as sg, issue, time as date from lottery_lhc_lottery_sg order by time desc limit #{issues}")
    List<LotterySgModel> getSg2(@Param("issues") Integer issues);
}
