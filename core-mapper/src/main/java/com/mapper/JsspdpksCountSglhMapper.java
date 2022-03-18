package com.mapper;

import com.mapper.domain.JsspdpksCountSglh;
import com.mapper.domain.JsspdpksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsspdpksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(JsspdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(JsspdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int insert(JsspdpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(JsspdpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    JsspdpksCountSglh selectOneByExample(JsspdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    List<JsspdpksCountSglh> selectByExample(JsspdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    JsspdpksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsspdpksCountSglh record, @Param("example") JsspdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsspdpksCountSglh record, @Param("example") JsspdpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsspdpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsspdpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsspdpksCountSglh record);
}