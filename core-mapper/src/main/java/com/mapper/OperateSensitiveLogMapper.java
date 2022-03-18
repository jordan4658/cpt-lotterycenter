package com.mapper;

import com.mapper.domain.OperateSensitiveLog;
import com.mapper.domain.OperateSensitiveLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperateSensitiveLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int countByExample(OperateSensitiveLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int deleteByExample(OperateSensitiveLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int insert(OperateSensitiveLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int insertSelective(OperateSensitiveLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    OperateSensitiveLog selectOneByExample(OperateSensitiveLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    List<OperateSensitiveLog> selectByExample(OperateSensitiveLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    OperateSensitiveLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") OperateSensitiveLog record, @Param("example") OperateSensitiveLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") OperateSensitiveLog record, @Param("example") OperateSensitiveLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OperateSensitiveLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table operate_sensitive_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OperateSensitiveLog record);
}