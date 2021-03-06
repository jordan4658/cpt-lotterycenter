package com.mapper;

import com.mapper.domain.JsspdpksLotterySg;
import com.mapper.domain.JsspdpksLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsspdpksLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(JsspdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(JsspdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int insert(JsspdpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(JsspdpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    JsspdpksLotterySg selectOneByExample(JsspdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    List<JsspdpksLotterySg> selectByExample(JsspdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    JsspdpksLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsspdpksLotterySg record, @Param("example") JsspdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsspdpksLotterySg record, @Param("example") JsspdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsspdpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsspdpksLotterySg record);
}