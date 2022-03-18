package com.mapper;

import com.mapper.domain.TenauspksRecommend;
import com.mapper.domain.TenauspksRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenauspksRecommendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int countByExample(TenauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int deleteByExample(TenauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int insert(TenauspksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int insertSelective(TenauspksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    TenauspksRecommend selectOneByExample(TenauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    List<TenauspksRecommend> selectByExample(TenauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    TenauspksRecommend selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TenauspksRecommend record, @Param("example") TenauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TenauspksRecommend record, @Param("example") TenauspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TenauspksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenauspks_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TenauspksRecommend record);
}