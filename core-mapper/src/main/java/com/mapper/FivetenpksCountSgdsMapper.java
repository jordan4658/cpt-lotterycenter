package com.mapper;

import com.mapper.domain.FivetenpksCountSgds;
import com.mapper.domain.FivetenpksCountSgdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FivetenpksCountSgdsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int countByExample(FivetenpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByExample(FivetenpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int insert(FivetenpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int insertSelective(FivetenpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    FivetenpksCountSgds selectOneByExample(FivetenpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    List<FivetenpksCountSgds> selectByExample(FivetenpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    FivetenpksCountSgds selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FivetenpksCountSgds record, @Param("example") FivetenpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FivetenpksCountSgds record, @Param("example") FivetenpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FivetenpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fivetenpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FivetenpksCountSgds record);
}