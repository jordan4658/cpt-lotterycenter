package com.mapper;

import com.mapper.domain.MemberTopUp;
import com.mapper.domain.MemberTopUpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberTopUpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int countByExample(MemberTopUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int deleteByExample(MemberTopUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int insert(MemberTopUp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int insertSelective(MemberTopUp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    MemberTopUp selectOneByExample(MemberTopUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    List<MemberTopUp> selectByExample(MemberTopUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    MemberTopUp selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MemberTopUp record, @Param("example") MemberTopUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MemberTopUp record, @Param("example") MemberTopUpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MemberTopUp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_top_up
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MemberTopUp record);
}