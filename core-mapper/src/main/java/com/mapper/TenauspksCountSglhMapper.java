package com.mapper;

import com.mapper.domain.TenauspksCountSglh;
import com.mapper.domain.TenauspksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenauspksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(TenauspksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(TenauspksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int insert(TenauspksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(TenauspksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    TenauspksCountSglh selectOneByExample(TenauspksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    List<TenauspksCountSglh> selectByExample(TenauspksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    TenauspksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TenauspksCountSglh record, @Param("example") TenauspksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TenauspksCountSglh record, @Param("example") TenauspksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TenauspksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TenauspksCountSglh record);
}