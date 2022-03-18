package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.TcplwLotterySg;
import com.caipiao.live.common.mybatis.entity.TcplwLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TcplwLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(TcplwLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(TcplwLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int insert(TcplwLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(TcplwLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    TcplwLotterySg selectOneByExample(TcplwLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    List<TcplwLotterySg> selectByExample(TcplwLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    TcplwLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TcplwLotterySg record, @Param("example") TcplwLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TcplwLotterySg record, @Param("example") TcplwLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TcplwLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tcplw_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TcplwLotterySg record);
}
