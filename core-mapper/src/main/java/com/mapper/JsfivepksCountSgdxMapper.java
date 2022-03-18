package com.mapper;

import com.mapper.domain.JsfivepksCountSgdx;
import com.mapper.domain.JsfivepksCountSgdxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsfivepksCountSgdxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int countByExample(JsfivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByExample(JsfivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int insert(JsfivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int insertSelective(JsfivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    JsfivepksCountSgdx selectOneByExample(JsfivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    List<JsfivepksCountSgdx> selectByExample(JsfivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    JsfivepksCountSgdx selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsfivepksCountSgdx record, @Param("example") JsfivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsfivepksCountSgdx record, @Param("example") JsfivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsfivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsfivepksCountSgdx record);
}