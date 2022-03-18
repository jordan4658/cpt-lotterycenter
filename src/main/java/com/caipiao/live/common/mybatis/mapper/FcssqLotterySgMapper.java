package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.FcssqLotterySg;
import com.caipiao.live.common.mybatis.entity.FcssqLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FcssqLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(FcssqLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(FcssqLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int insert(FcssqLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(FcssqLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    FcssqLotterySg selectOneByExample(FcssqLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    List<FcssqLotterySg> selectByExample(FcssqLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    FcssqLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FcssqLotterySg record, @Param("example") FcssqLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FcssqLotterySg record, @Param("example") FcssqLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FcssqLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fcssq_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FcssqLotterySg record);
}
