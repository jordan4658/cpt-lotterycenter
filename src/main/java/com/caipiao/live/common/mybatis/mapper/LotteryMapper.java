package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.Lottery;
import com.caipiao.live.common.mybatis.entity.LotteryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LotteryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int countByExample(LotteryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int deleteByExample(LotteryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int insert(Lottery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int insertSelective(Lottery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    Lottery selectOneByExample(LotteryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    List<Lottery> selectByExample(LotteryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    Lottery selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Lottery record, @Param("example") LotteryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Lottery record, @Param("example") LotteryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Lottery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Lottery record);
}
