package com.mapper;

import com.mapper.domain.SystemInfo;
import com.mapper.domain.SystemInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int countByExample(SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int deleteByExample(SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int insert(SystemInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int insertSelective(SystemInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    SystemInfo selectOneByExample(SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    List<SystemInfo> selectByExample(SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    SystemInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SystemInfo record, @Param("example") SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SystemInfo record, @Param("example") SystemInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SystemInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SystemInfo record);
}