package com.mapper;

import com.mapper.domain.FivespdpksCountSglh;
import com.mapper.domain.FivespdpksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivespdpksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(FivespdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(FivespdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int insert(FivespdpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(FivespdpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    FivespdpksCountSglh selectOneByExample(FivespdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    List<FivespdpksCountSglh> selectByExample(FivespdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    FivespdpksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivespdpksCountSglh record, @Param("example") FivespdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivespdpksCountSglh record, @Param("example") FivespdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivespdpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivespdpksCountSglh record);
}