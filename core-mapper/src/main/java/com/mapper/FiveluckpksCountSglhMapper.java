package com.mapper;

import com.mapper.domain.FiveluckpksCountSglh;
import com.mapper.domain.FiveluckpksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FiveluckpksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(FiveluckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(FiveluckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int insert(FiveluckpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(FiveluckpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    FiveluckpksCountSglh selectOneByExample(FiveluckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    List<FiveluckpksCountSglh> selectByExample(FiveluckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    FiveluckpksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FiveluckpksCountSglh record, @Param("example") FiveluckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FiveluckpksCountSglh record, @Param("example") FiveluckpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FiveluckpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fiveluckpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FiveluckpksCountSglh record);
}