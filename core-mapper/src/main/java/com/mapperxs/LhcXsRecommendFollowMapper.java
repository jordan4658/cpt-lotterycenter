package com.mapperxs;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mapper.domain.LhcXsRecommendFollow;
import com.mapper.domain.LhcXsRecommendFollowExample;

public interface LhcXsRecommendFollowMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int countByExample(LhcXsRecommendFollowExample example);
    
    

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int deleteByExample(LhcXsRecommendFollowExample example);

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
    int insert(LhcXsRecommendFollow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int insertSelective(LhcXsRecommendFollow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    LhcXsRecommendFollow selectOneByExample(LhcXsRecommendFollowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    List<LhcXsRecommendFollow> selectByExample(LhcXsRecommendFollowExample example);
    

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    LhcXsRecommendFollow selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LhcXsRecommendFollow record, @Param("example") LhcXsRecommendFollowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LhcXsRecommendFollow record, @Param("example") LhcXsRecommendFollowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LhcXsRecommendFollow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_xs_recommend_content
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LhcXsRecommendFollow record);
}