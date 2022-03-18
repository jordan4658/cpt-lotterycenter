package com.mapper;

import com.mapper.domain.JsfivepksKillNumber;
import com.mapper.domain.JsfivepksKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsfivepksKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int countByExample(JsfivepksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(JsfivepksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int insert(JsfivepksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(JsfivepksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    JsfivepksKillNumber selectOneByExample(JsfivepksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    List<JsfivepksKillNumber> selectByExample(JsfivepksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    JsfivepksKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsfivepksKillNumber record, @Param("example") JsfivepksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsfivepksKillNumber record, @Param("example") JsfivepksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsfivepksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsfivepksKillNumber record);
}