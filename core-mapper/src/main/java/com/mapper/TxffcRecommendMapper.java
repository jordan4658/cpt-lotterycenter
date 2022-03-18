package com.mapper;

import com.mapper.domain.TxffcRecommend;
import com.mapper.domain.TxffcRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TxffcRecommendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int countByExample(TxffcRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int deleteByExample(TxffcRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int insert(TxffcRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int insertSelective(TxffcRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    TxffcRecommend selectOneByExample(TxffcRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    List<TxffcRecommend> selectByExample(TxffcRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    TxffcRecommend selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TxffcRecommend record, @Param("example") TxffcRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TxffcRecommend record, @Param("example") TxffcRecommendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TxffcRecommend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table txffc_recommend
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TxffcRecommend record);
}