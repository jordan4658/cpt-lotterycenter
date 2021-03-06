package com.caipiao.live.common.mybatis.mapper;

import com.caipiao.live.common.mybatis.entity.MemWallet;
import com.caipiao.live.common.mybatis.entity.MemWalletExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemWalletMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int countByExample(MemWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int deleteByExample(MemWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int insert(MemWallet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int insertSelective(MemWallet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    MemWallet selectOneByExample(MemWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    List<MemWallet> selectByExample(MemWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    MemWallet selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MemWallet record, @Param("example") MemWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MemWallet record, @Param("example") MemWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MemWallet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MemWallet record);
}