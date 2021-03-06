package com.mapper;

import com.mapper.domain.JssscCountSgdxds;
import com.mapper.domain.JssscCountSgdxdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JssscCountSgdxdsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int countByExample(JssscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int deleteByExample(JssscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int insert(JssscCountSgdxds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int insertSelective(JssscCountSgdxds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    JssscCountSgdxds selectOneByExample(JssscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    List<JssscCountSgdxds> selectByExample(JssscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    JssscCountSgdxds selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JssscCountSgdxds record, @Param("example") JssscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JssscCountSgdxds record, @Param("example") JssscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JssscCountSgdxds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_count_sgdxds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JssscCountSgdxds record);
}