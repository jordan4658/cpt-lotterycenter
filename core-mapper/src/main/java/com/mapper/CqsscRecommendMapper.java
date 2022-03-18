package com.mapper;

import com.mapper.domain.CqsscRecommend;
import com.mapper.domain.CqsscRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CqsscRecommendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int countByExample(CqsscRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int deleteByExample(CqsscRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int insert(CqsscRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int insertSelective(CqsscRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    CqsscRecommend selectOneByExample(CqsscRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    List<CqsscRecommend> selectByExample(CqsscRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    CqsscRecommend selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") CqsscRecommend record, @Param("example") CqsscRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") CqsscRecommend record, @Param("example") CqsscRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CqsscRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CqsscRecommend record);
}