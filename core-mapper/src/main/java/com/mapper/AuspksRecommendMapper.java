package com.mapper;

import com.mapper.domain.AuspksRecommend;
import com.mapper.domain.AuspksRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuspksRecommendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_recommend
     *
     * @mbggenerated
     */
    int countByExample(AuspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_recommend
     *
     * @mbggenerated
     */
    int deleteByExample(AuspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_recommend
     *
     * @mbggenerated
     */
    int insert(AuspksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_recommend
     *
     * @mbggenerated
     */
    int insertSelective(AuspksRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_recommend
     *
     * @mbggenerated
     */
    AuspksRecommend selectOneByExample(AuspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_recommend
     *
     * @mbggenerated
     */
    List<AuspksRecommend> selectByExample(AuspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_recommend
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") AuspksRecommend record, @Param("example") AuspksRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auspks_recommend
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") AuspksRecommend record, @Param("example") AuspksRecommendExample example);
}