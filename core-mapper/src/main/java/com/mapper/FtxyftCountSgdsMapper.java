package com.mapper;

import com.mapper.domain.FtxyftCountSgds;
import com.mapper.domain.FtxyftCountSgdsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FtxyftCountSgdsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int countByExample(FtxyftCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int deleteByExample(FtxyftCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int insert(FtxyftCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int insertSelective(FtxyftCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    FtxyftCountSgds selectOneByExample(FtxyftCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    List<FtxyftCountSgds> selectByExample(FtxyftCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    FtxyftCountSgds selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FtxyftCountSgds record, @Param("example") FtxyftCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FtxyftCountSgds record, @Param("example") FtxyftCountSgdsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FtxyftCountSgds record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_count_sgds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FtxyftCountSgds record);
}