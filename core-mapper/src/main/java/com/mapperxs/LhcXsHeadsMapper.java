package com.mapperxs;

import com.mapper.domain.LhcXsHeads;
import com.mapper.domain.LhcXsHeadsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LhcXsHeadsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int countByExample(LhcXsHeadsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int deleteByExample(LhcXsHeadsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int insert(LhcXsHeads record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int insertSelective(LhcXsHeads record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    LhcXsHeads selectOneByExample(LhcXsHeadsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    List<LhcXsHeads> selectByExample(LhcXsHeadsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    LhcXsHeads selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LhcXsHeads record, @Param("example") LhcXsHeadsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LhcXsHeads record, @Param("example") LhcXsHeadsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LhcXsHeads record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_heads
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LhcXsHeads record);
}