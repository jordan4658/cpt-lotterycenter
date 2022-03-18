package com.mapper;

import com.mapper.domain.LuckpksCountSglh;
import com.mapper.domain.LuckpksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LuckpksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(LuckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(LuckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int insert(LuckpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(LuckpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    LuckpksCountSglh selectOneByExample(LuckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    List<LuckpksCountSglh> selectByExample(LuckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    LuckpksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LuckpksCountSglh record, @Param("example") LuckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LuckpksCountSglh record, @Param("example") LuckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LuckpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LuckpksCountSglh record);
}