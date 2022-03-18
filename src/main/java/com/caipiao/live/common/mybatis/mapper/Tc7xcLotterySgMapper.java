package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.Tc7xcLotterySg;
import com.caipiao.live.common.mybatis.entity.Tc7xcLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Tc7xcLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(Tc7xcLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(Tc7xcLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int insert(Tc7xcLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(Tc7xcLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    Tc7xcLotterySg selectOneByExample(Tc7xcLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    List<Tc7xcLotterySg> selectByExample(Tc7xcLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    Tc7xcLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Tc7xcLotterySg record, @Param("example") Tc7xcLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Tc7xcLotterySg record, @Param("example") Tc7xcLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Tc7xcLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tc7xc_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Tc7xcLotterySg record);
}
