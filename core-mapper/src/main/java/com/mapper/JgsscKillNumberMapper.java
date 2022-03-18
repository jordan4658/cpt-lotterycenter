package com.mapper;

import com.mapper.domain.JgsscKillNumber;
import com.mapper.domain.JgsscKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JgsscKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int countByExample(JgsscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(JgsscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int insert(JgsscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(JgsscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    JgsscKillNumber selectOneByExample(JgsscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    List<JgsscKillNumber> selectByExample(JgsscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    JgsscKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JgsscKillNumber record, @Param("example") JgsscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JgsscKillNumber record, @Param("example") JgsscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JgsscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jgssc_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JgsscKillNumber record);
}