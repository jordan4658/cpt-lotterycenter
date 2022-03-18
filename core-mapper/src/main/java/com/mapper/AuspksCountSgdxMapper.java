package com.mapper;

import com.mapper.domain.AuspksCountSgdx;
import com.mapper.domain.AuspksCountSgdxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuspksCountSgdxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int countByExample(AuspksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByExample(AuspksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int insert(AuspksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int insertSelective(AuspksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    AuspksCountSgdx selectOneByExample(AuspksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    List<AuspksCountSgdx> selectByExample(AuspksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    AuspksCountSgdx selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") AuspksCountSgdx record, @Param("example") AuspksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") AuspksCountSgdx record, @Param("example") AuspksCountSgdxExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuspksCountSgdx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_count_sgdx
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuspksCountSgdx record);
}