package com.mapper;

import com.mapper.domain.FivespdpksCountSgds;
import com.mapper.domain.FivespdpksCountSgdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivespdpksCountSgdsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int countByExample(FivespdpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByExample(FivespdpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int insert(FivespdpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int insertSelective(FivespdpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    FivespdpksCountSgds selectOneByExample(FivespdpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    List<FivespdpksCountSgds> selectByExample(FivespdpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    FivespdpksCountSgds selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivespdpksCountSgds record, @Param("example") FivespdpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivespdpksCountSgds record, @Param("example") FivespdpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivespdpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivespdpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivespdpksCountSgds record);
}