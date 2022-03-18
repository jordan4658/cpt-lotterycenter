package com.mapper;

import com.mapper.domain.OperateLog;
import com.mapper.domain.OperateLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperateLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int countByExample(OperateLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int deleteByExample(OperateLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int insert(OperateLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int insertSelective(OperateLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    OperateLog selectOneByExample(OperateLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    List<OperateLog> selectByExample(OperateLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    OperateLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OperateLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OperateLog record);
}