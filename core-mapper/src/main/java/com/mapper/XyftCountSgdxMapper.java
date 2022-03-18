package com.mapper;

import com.mapper.domain.XyftCountSgdx;
import com.mapper.domain.XyftCountSgdxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XyftCountSgdxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int countByExample(XyftCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByExample(XyftCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int insert(XyftCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int insertSelective(XyftCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    XyftCountSgdx selectOneByExample(XyftCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    List<XyftCountSgdx> selectByExample(XyftCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    XyftCountSgdx selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") XyftCountSgdx record, @Param("example") XyftCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") XyftCountSgdx record, @Param("example") XyftCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(XyftCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xyft_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(XyftCountSgdx record);
}