package com.mapper;

import com.mapper.domain.LotteryResult;
import com.mapper.domain.LotteryResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LotteryResultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int countByExample(LotteryResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int deleteByExample(LotteryResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int insert(LotteryResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int insertSelective(LotteryResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    LotteryResult selectOneByExample(LotteryResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    List<LotteryResult> selectByExample(LotteryResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    LotteryResult selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LotteryResult record, @Param("example") LotteryResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LotteryResult record, @Param("example") LotteryResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LotteryResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_result
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LotteryResult record);
}