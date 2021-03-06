package com.mapper;

import com.mapper.domain.PopupActivity;
import com.mapper.domain.PopupActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PopupActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int countByExample(PopupActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int deleteByExample(PopupActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int insert(PopupActivity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int insertSelective(PopupActivity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    PopupActivity selectOneByExample(PopupActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    List<PopupActivity> selectByExample(PopupActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    PopupActivity selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PopupActivity record, @Param("example") PopupActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PopupActivity record, @Param("example") PopupActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PopupActivity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table popup_activity
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PopupActivity record);
}