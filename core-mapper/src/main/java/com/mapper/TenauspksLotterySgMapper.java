package com.mapper;

import com.mapper.domain.TenauspksLotterySg;
import com.mapper.domain.TenauspksLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenauspksLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(TenauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(TenauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int insert(TenauspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(TenauspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    TenauspksLotterySg selectOneByExample(TenauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    List<TenauspksLotterySg> selectByExample(TenauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    TenauspksLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TenauspksLotterySg record, @Param("example") TenauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TenauspksLotterySg record, @Param("example") TenauspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TenauspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TenauspksLotterySg record);
}