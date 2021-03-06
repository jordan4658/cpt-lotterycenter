package com.mapper;

import com.mapper.domain.FtxyftKillNumber;
import com.mapper.domain.FtxyftKillNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FtxyftKillNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int countByExample(FtxyftKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int deleteByExample(FtxyftKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int insert(FtxyftKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int insertSelective(FtxyftKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    FtxyftKillNumber selectOneByExample(FtxyftKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    List<FtxyftKillNumber> selectByExample(FtxyftKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    FtxyftKillNumber selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FtxyftKillNumber record, @Param("example") FtxyftKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FtxyftKillNumber record, @Param("example") FtxyftKillNumberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FtxyftKillNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_kill_number
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FtxyftKillNumber record);
}