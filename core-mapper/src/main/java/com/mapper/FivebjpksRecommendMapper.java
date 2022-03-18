package com.mapper;

import com.mapper.domain.FivebjpksRecommend;
import com.mapper.domain.FivebjpksRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivebjpksRecommendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int countByExample(FivebjpksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int deleteByExample(FivebjpksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int insert(FivebjpksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int insertSelective(FivebjpksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    FivebjpksRecommend selectOneByExample(FivebjpksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    List<FivebjpksRecommend> selectByExample(FivebjpksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    FivebjpksRecommend selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivebjpksRecommend record, @Param("example") FivebjpksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivebjpksRecommend record, @Param("example") FivebjpksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivebjpksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivebjpks_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivebjpksRecommend record);
}