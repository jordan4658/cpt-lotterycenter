package com.mapper;

import com.mapper.domain.TokenIp;
import com.mapper.domain.TokenIpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TokenIpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int countByExample(TokenIpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int deleteByExample(TokenIpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int insert(TokenIp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int insertSelective(TokenIp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    TokenIp selectOneByExample(TokenIpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    List<TokenIp> selectByExample(TokenIpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    TokenIp selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TokenIp record, @Param("example") TokenIpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TokenIp record, @Param("example") TokenIpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TokenIp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table token_ip
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TokenIp record);
}