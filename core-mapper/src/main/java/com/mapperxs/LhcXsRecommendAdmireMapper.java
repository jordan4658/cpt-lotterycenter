package com.mapperxs;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mapper.domain.LhcXsRecommendAdmire;
import com.mapper.domain.LhcXsRecommendAdmireExample;

public interface LhcXsRecommendAdmireMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int countByExample(LhcXsRecommendAdmireExample example);
    
    int countByExampleForAdminPage(Map<String,Object> map);
    

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int deleteByExample(LhcXsRecommendAdmireExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int insert(LhcXsRecommendAdmire record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int insertSelective(LhcXsRecommendAdmire record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    LhcXsRecommendAdmire selectOneByExample(LhcXsRecommendAdmireExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    List<LhcXsRecommendAdmire> selectByExample(LhcXsRecommendAdmireExample example);
    
    List<LhcXsRecommendAdmire> selectByExampleForAdminPage(Map<String,Object> map);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    LhcXsRecommendAdmire selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LhcXsRecommendAdmire record, @Param("example") LhcXsRecommendAdmireExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LhcXsRecommendAdmire record, @Param("example") LhcXsRecommendAdmireExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LhcXsRecommendAdmire record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LhcXsRecommendAdmire record);
}