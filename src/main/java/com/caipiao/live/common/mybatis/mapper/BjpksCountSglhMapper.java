package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.BjpksCountSglh;
import com.caipiao.live.common.mybatis.entity.BjpksCountSglhExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BjpksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(BjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(BjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int insert(BjpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(BjpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    BjpksCountSglh selectOneByExample(BjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    List<BjpksCountSglh> selectByExample(BjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    BjpksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") BjpksCountSglh record, @Param("example") BjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") BjpksCountSglh record, @Param("example") BjpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BjpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BjpksCountSglh record);
}
