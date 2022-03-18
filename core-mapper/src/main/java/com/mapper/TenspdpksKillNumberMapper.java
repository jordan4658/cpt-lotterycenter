package com.mapper;

import com.mapper.domain.TenspdpksKillNumber;
import com.mapper.domain.TenspdpksKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenspdpksKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int countByExample(TenspdpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(TenspdpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int insert(TenspdpksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(TenspdpksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    TenspdpksKillNumber selectOneByExample(TenspdpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    List<TenspdpksKillNumber> selectByExample(TenspdpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    TenspdpksKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TenspdpksKillNumber record, @Param("example") TenspdpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TenspdpksKillNumber record, @Param("example") TenspdpksKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TenspdpksKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenspdpks_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TenspdpksKillNumber record);
}