package com.mapper;

import com.mapper.domain.SpdpksLotterySg;
import com.mapper.domain.SpdpksLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpdpksLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(SpdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(SpdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int insert(SpdpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(SpdpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    SpdpksLotterySg selectOneByExample(SpdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    List<SpdpksLotterySg> selectByExample(SpdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    SpdpksLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SpdpksLotterySg record, @Param("example") SpdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SpdpksLotterySg record, @Param("example") SpdpksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SpdpksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spdpks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SpdpksLotterySg record);
}