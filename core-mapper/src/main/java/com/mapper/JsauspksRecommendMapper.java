package com.mapper;

import com.mapper.domain.JsauspksRecommend;
import com.mapper.domain.JsauspksRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsauspksRecommendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int countByExample(JsauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int deleteByExample(JsauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int insert(JsauspksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int insertSelective(JsauspksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    JsauspksRecommend selectOneByExample(JsauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    List<JsauspksRecommend> selectByExample(JsauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    JsauspksRecommend selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsauspksRecommend record, @Param("example") JsauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsauspksRecommend record, @Param("example") JsauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsauspksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsauspks_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsauspksRecommend record);
}