package com.mapper;

import com.mapper.domain.JssscKillNumber;
import com.mapper.domain.JssscKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JssscKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int countByExample(JssscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(JssscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int insert(JssscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(JssscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    JssscKillNumber selectOneByExample(JssscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    List<JssscKillNumber> selectByExample(JssscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    JssscKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JssscKillNumber record, @Param("example") JssscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JssscKillNumber record, @Param("example") JssscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JssscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JssscKillNumber record);
}