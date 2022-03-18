package com.mapper;

import com.mapper.domain.TentenpksCountSglh;
import com.mapper.domain.TentenpksCountSglhExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TentenpksCountSglhMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int countByExample(TentenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByExample(TentenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int insert(TentenpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int insertSelective(TentenpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    TentenpksCountSglh selectOneByExample(TentenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    List<TentenpksCountSglh> selectByExample(TentenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    TentenpksCountSglh selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TentenpksCountSglh record, @Param("example") TentenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TentenpksCountSglh record, @Param("example") TentenpksCountSglhExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TentenpksCountSglh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tentenpks_count_sglh
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TentenpksCountSglh record);
}