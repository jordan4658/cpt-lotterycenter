package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.FivesscLotterySg;
import com.caipiao.live.common.mybatis.entity.FivesscLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivesscLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(FivesscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(FivesscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int insert(FivesscLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(FivesscLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    FivesscLotterySg selectOneByExample(FivesscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    List<FivesscLotterySg> selectByExample(FivesscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    FivesscLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivesscLotterySg record, @Param("example") FivesscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivesscLotterySg record, @Param("example") FivesscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivesscLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivesscLotterySg record);
}
