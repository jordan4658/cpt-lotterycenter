package com.mapper;

import com.mapper.domain.FivefivepksCountSgdx;
import com.mapper.domain.FivefivepksCountSgdxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivefivepksCountSgdxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int countByExample(FivefivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByExample(FivefivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int insert(FivefivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int insertSelective(FivefivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    FivefivepksCountSgdx selectOneByExample(FivefivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    List<FivefivepksCountSgdx> selectByExample(FivefivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    FivefivepksCountSgdx selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivefivepksCountSgdx record, @Param("example") FivefivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivefivepksCountSgdx record, @Param("example") FivefivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivefivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivefivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivefivepksCountSgdx record);
}