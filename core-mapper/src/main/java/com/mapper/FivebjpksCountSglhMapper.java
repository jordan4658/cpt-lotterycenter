package com.mapper;

import com.mapper.domain.FivebjpksCountSglh;
import com.mapper.domain.FivebjpksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivebjpksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(FivebjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(FivebjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int insert(FivebjpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(FivebjpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    FivebjpksCountSglh selectOneByExample(FivebjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    List<FivebjpksCountSglh> selectByExample(FivebjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    FivebjpksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivebjpksCountSglh record, @Param("example") FivebjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivebjpksCountSglh record, @Param("example") FivebjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivebjpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivebjpksCountSglh record);
}