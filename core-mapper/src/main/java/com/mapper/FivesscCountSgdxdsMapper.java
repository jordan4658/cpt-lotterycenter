package com.mapper;

import com.mapper.domain.FivesscCountSgdxds;
import com.mapper.domain.FivesscCountSgdxdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivesscCountSgdxdsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int countByExample(FivesscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int deleteByExample(FivesscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int insert(FivesscCountSgdxds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int insertSelective(FivesscCountSgdxds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    FivesscCountSgdxds selectOneByExample(FivesscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    List<FivesscCountSgdxds> selectByExample(FivesscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    FivesscCountSgdxds selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivesscCountSgdxds record, @Param("example") FivesscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivesscCountSgdxds record, @Param("example") FivesscCountSgdxdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivesscCountSgdxds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivessc_count_sgdxds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivesscCountSgdxds record);
}