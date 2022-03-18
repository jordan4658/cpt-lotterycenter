package com.mapper;

import com.mapper.domain.TenfivepksLotterySg;
import com.mapper.domain.TenfivepksLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenfivepksLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(TenfivepksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(TenfivepksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int insert(TenfivepksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(TenfivepksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    TenfivepksLotterySg selectOneByExample(TenfivepksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    List<TenfivepksLotterySg> selectByExample(TenfivepksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    TenfivepksLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TenfivepksLotterySg record, @Param("example") TenfivepksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TenfivepksLotterySg record, @Param("example") TenfivepksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TenfivepksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenfivepks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TenfivepksLotterySg record);
}