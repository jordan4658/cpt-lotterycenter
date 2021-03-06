package com.mapper;

import com.mapper.domain.FtxyftLotterySg;
import com.mapper.domain.FtxyftLotterySgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FtxyftLotterySgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int countByExample(FtxyftLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByExample(FtxyftLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int insert(FtxyftLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int insertSelective(FtxyftLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    FtxyftLotterySg selectOneByExample(FtxyftLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    List<FtxyftLotterySg> selectByExample(FtxyftLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    FtxyftLotterySg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FtxyftLotterySg record, @Param("example") FtxyftLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FtxyftLotterySg record, @Param("example") FtxyftLotterySgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FtxyftLotterySg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ftxyft_lottery_sg
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FtxyftLotterySg record);
}