package com.mapper;

import com.mapper.domain.AuspksLotterySg;
import com.mapper.domain.AuspksLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuspksLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(AuspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(AuspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int insert(AuspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(AuspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    AuspksLotterySg selectOneByExample(AuspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    List<AuspksLotterySg> selectByExample(AuspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    AuspksLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") AuspksLotterySg record, @Param("example") AuspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") AuspksLotterySg record, @Param("example") AuspksLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuspksLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuspksLotterySg record);
}