package com.mapper;

import com.mapper.domain.JsfivepksCountSglh;
import com.mapper.domain.JsfivepksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsfivepksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(JsfivepksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(JsfivepksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int insert(JsfivepksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(JsfivepksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    JsfivepksCountSglh selectOneByExample(JsfivepksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    List<JsfivepksCountSglh> selectByExample(JsfivepksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    JsfivepksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsfivepksCountSglh record, @Param("example") JsfivepksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsfivepksCountSglh record, @Param("example") JsfivepksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsfivepksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsfivepksCountSglh record);
}