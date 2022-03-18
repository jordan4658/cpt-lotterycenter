package com.mapper;

import com.mapper.domain.JsfivepksCountSgds;
import com.mapper.domain.JsfivepksCountSgdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsfivepksCountSgdsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int countByExample(JsfivepksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByExample(JsfivepksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int insert(JsfivepksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int insertSelective(JsfivepksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    JsfivepksCountSgds selectOneByExample(JsfivepksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    List<JsfivepksCountSgds> selectByExample(JsfivepksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    JsfivepksCountSgds selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsfivepksCountSgds record, @Param("example") JsfivepksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsfivepksCountSgds record, @Param("example") JsfivepksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsfivepksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsfivepks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsfivepksCountSgds record);
}