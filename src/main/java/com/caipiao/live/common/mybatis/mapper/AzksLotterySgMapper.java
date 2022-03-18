package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.AzksLotterySg;
import com.caipiao.live.common.mybatis.entity.AzksLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AzksLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(AzksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(AzksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int insert(AzksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(AzksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    AzksLotterySg selectOneByExample(AzksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    List<AzksLotterySg> selectByExample(AzksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    AzksLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") AzksLotterySg record, @Param("example") AzksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") AzksLotterySg record, @Param("example") AzksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AzksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table azks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AzksLotterySg record);
}