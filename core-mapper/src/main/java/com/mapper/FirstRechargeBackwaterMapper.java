package com.mapper;

import com.mapper.domain.FirstRechargeBackwater;
import com.mapper.domain.FirstRechargeBackwaterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FirstRechargeBackwaterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int countByExample(FirstRechargeBackwaterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int deleteByExample(FirstRechargeBackwaterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int insert(FirstRechargeBackwater record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int insertSelective(FirstRechargeBackwater record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    FirstRechargeBackwater selectOneByExample(FirstRechargeBackwaterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    List<FirstRechargeBackwater> selectByExample(FirstRechargeBackwaterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    FirstRechargeBackwater selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FirstRechargeBackwater record, @Param("example") FirstRechargeBackwaterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FirstRechargeBackwater record, @Param("example") FirstRechargeBackwaterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FirstRechargeBackwater record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_backwater
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FirstRechargeBackwater record);
}