package com.mapper;

import com.mapper.domain.MemberActivity;
import com.mapper.domain.MemberActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int countByExample(MemberActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int deleteByExample(MemberActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int insert(MemberActivity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int insertSelective(MemberActivity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    MemberActivity selectOneByExample(MemberActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    List<MemberActivity> selectByExample(MemberActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    MemberActivity selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MemberActivity record, @Param("example") MemberActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MemberActivity record, @Param("example") MemberActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MemberActivity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_activity
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MemberActivity record);
}