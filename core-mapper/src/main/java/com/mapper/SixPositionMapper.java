package com.mapper;

import com.mapper.domain.SixPosition;
import com.mapper.domain.SixPositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SixPositionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int countByExample(SixPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int deleteByExample(SixPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int insert(SixPosition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int insertSelective(SixPosition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    SixPosition selectOneByExample(SixPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    List<SixPosition> selectByExample(SixPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    SixPosition selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SixPosition record, @Param("example") SixPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SixPosition record, @Param("example") SixPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SixPosition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table six_position
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SixPosition record);
}