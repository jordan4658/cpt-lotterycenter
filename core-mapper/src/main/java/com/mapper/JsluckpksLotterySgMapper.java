package com.mapper;

import com.mapper.domain.JsluckpksLotterySg;
import com.mapper.domain.JsluckpksLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsluckpksLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(JsluckpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(JsluckpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int insert(JsluckpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(JsluckpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    JsluckpksLotterySg selectOneByExample(JsluckpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    List<JsluckpksLotterySg> selectByExample(JsluckpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    JsluckpksLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsluckpksLotterySg record, @Param("example") JsluckpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsluckpksLotterySg record, @Param("example") JsluckpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsluckpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsluckpksLotterySg record);
}