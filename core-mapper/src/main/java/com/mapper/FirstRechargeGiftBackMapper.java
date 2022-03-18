package com.mapper;

import com.mapper.domain.FirstRechargeGiftBack;
import com.mapper.domain.FirstRechargeGiftBackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FirstRechargeGiftBackMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int countByExample(FirstRechargeGiftBackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int deleteByExample(FirstRechargeGiftBackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int insert(FirstRechargeGiftBack record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int insertSelective(FirstRechargeGiftBack record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    FirstRechargeGiftBack selectOneByExample(FirstRechargeGiftBackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    List<FirstRechargeGiftBack> selectByExample(FirstRechargeGiftBackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    FirstRechargeGiftBack selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FirstRechargeGiftBack record, @Param("example") FirstRechargeGiftBackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FirstRechargeGiftBack record, @Param("example") FirstRechargeGiftBackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FirstRechargeGiftBack record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FirstRechargeGiftBack record);
}