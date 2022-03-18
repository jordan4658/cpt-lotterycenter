package com.mapper;

import com.mapper.domain.LuckpksCountSgds;
import com.mapper.domain.LuckpksCountSgdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LuckpksCountSgdsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int countByExample(LuckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByExample(LuckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int insert(LuckpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int insertSelective(LuckpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    LuckpksCountSgds selectOneByExample(LuckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    List<LuckpksCountSgds> selectByExample(LuckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    LuckpksCountSgds selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LuckpksCountSgds record, @Param("example") LuckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LuckpksCountSgds record, @Param("example") LuckpksCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LuckpksCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table luckpks_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LuckpksCountSgds record);
}