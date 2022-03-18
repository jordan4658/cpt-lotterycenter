package com.mapper;

import com.mapper.domain.JsauspksKillNumber;
import com.mapper.domain.JsauspksKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsauspksKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int countByExample(JsauspksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(JsauspksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int insert(JsauspksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(JsauspksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    JsauspksKillNumber selectOneByExample(JsauspksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    List<JsauspksKillNumber> selectByExample(JsauspksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    JsauspksKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsauspksKillNumber record, @Param("example") JsauspksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsauspksKillNumber record, @Param("example") JsauspksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsauspksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsauspksKillNumber record);
}