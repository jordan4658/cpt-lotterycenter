package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.XjsscLotterySg;
import com.caipiao.live.common.mybatis.entity.XjsscLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XjsscLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(XjsscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(XjsscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int insert(XjsscLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(XjsscLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    XjsscLotterySg selectOneByExample(XjsscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    List<XjsscLotterySg> selectByExample(XjsscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    XjsscLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") XjsscLotterySg record, @Param("example") XjsscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") XjsscLotterySg record, @Param("example") XjsscLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(XjsscLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(XjsscLotterySg record);
}