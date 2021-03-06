package com.mapper;

import com.mapper.domain.JstenpksKillNumber;
import com.mapper.domain.JstenpksKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JstenpksKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int countByExample(JstenpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(JstenpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int insert(JstenpksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(JstenpksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    JstenpksKillNumber selectOneByExample(JstenpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    List<JstenpksKillNumber> selectByExample(JstenpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    JstenpksKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JstenpksKillNumber record, @Param("example") JstenpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JstenpksKillNumber record, @Param("example") JstenpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JstenpksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jstenpks_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JstenpksKillNumber record);
}