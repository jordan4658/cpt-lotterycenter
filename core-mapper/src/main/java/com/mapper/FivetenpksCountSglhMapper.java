package com.mapper;

import com.mapper.domain.FivetenpksCountSglh;
import com.mapper.domain.FivetenpksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivetenpksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(FivetenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(FivetenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int insert(FivetenpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(FivetenpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    FivetenpksCountSglh selectOneByExample(FivetenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    List<FivetenpksCountSglh> selectByExample(FivetenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    FivetenpksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivetenpksCountSglh record, @Param("example") FivetenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivetenpksCountSglh record, @Param("example") FivetenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivetenpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivetenpksCountSglh record);
}