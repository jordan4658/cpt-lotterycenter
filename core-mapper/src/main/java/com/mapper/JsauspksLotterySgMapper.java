package com.mapper;

import com.mapper.domain.JsauspksLotterySg;
import com.mapper.domain.JsauspksLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsauspksLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(JsauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(JsauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int insert(JsauspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(JsauspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    JsauspksLotterySg selectOneByExample(JsauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    List<JsauspksLotterySg> selectByExample(JsauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    JsauspksLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsauspksLotterySg record, @Param("example") JsauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsauspksLotterySg record, @Param("example") JsauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsauspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsauspksLotterySg record);
}