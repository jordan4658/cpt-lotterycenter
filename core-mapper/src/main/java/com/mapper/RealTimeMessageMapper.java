package com.mapper;

import com.mapper.domain.RealTimeMessage;
import com.mapper.domain.RealTimeMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RealTimeMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int countByExample(RealTimeMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int deleteByExample(RealTimeMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int insert(RealTimeMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int insertSelective(RealTimeMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    RealTimeMessage selectOneByExample(RealTimeMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    List<RealTimeMessage> selectByExample(RealTimeMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    RealTimeMessage selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") RealTimeMessage record, @Param("example") RealTimeMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") RealTimeMessage record, @Param("example") RealTimeMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RealTimeMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table real_time_message
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RealTimeMessage record);
}