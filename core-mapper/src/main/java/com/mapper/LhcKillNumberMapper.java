package com.mapper;

import com.mapper.domain.LhcKillNumber;
import com.mapper.domain.LhcKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LhcKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int countByExample(LhcKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(LhcKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int insert(LhcKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(LhcKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    LhcKillNumber selectOneByExample(LhcKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    List<LhcKillNumber> selectByExample(LhcKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    LhcKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LhcKillNumber record, @Param("example") LhcKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LhcKillNumber record, @Param("example") LhcKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LhcKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LhcKillNumber record);
}