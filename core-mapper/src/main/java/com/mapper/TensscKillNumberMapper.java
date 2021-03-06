package com.mapper;

import com.mapper.domain.TensscKillNumber;
import com.mapper.domain.TensscKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TensscKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int countByExample(TensscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(TensscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int insert(TensscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(TensscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    TensscKillNumber selectOneByExample(TensscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    List<TensscKillNumber> selectByExample(TensscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    TensscKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TensscKillNumber record, @Param("example") TensscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TensscKillNumber record, @Param("example") TensscKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TensscKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenssc_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TensscKillNumber record);
}