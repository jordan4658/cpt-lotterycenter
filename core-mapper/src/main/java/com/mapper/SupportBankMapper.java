package com.mapper;

import com.mapper.domain.SupportBank;
import com.mapper.domain.SupportBankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SupportBankMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int countByExample(SupportBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int deleteByExample(SupportBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int insert(SupportBank record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int insertSelective(SupportBank record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    SupportBank selectOneByExample(SupportBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    List<SupportBank> selectByExample(SupportBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    SupportBank selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SupportBank record, @Param("example") SupportBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SupportBank record, @Param("example") SupportBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SupportBank record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table support_bank
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SupportBank record);
}