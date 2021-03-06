package com.mapper;

import com.mapper.domain.FivepksCountSgdx;
import com.mapper.domain.FivepksCountSgdxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivepksCountSgdxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int countByExample(FivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByExample(FivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int insert(FivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int insertSelective(FivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    FivepksCountSgdx selectOneByExample(FivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    List<FivepksCountSgdx> selectByExample(FivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    FivepksCountSgdx selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivepksCountSgdx record, @Param("example") FivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivepksCountSgdx record, @Param("example") FivepksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivepksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivepks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivepksCountSgdx record);
}