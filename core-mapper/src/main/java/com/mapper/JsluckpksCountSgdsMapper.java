package com.mapper;

import com.mapper.domain.JsluckpksCountSgds;
import com.mapper.domain.JsluckpksCountSgdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JsluckpksCountSgdsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int countByExample(JsluckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByExample(JsluckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int insert(JsluckpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int insertSelective(JsluckpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    JsluckpksCountSgds selectOneByExample(JsluckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    List<JsluckpksCountSgds> selectByExample(JsluckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    JsluckpksCountSgds selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JsluckpksCountSgds record, @Param("example") JsluckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JsluckpksCountSgds record, @Param("example") JsluckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JsluckpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsluckpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JsluckpksCountSgds record);
}