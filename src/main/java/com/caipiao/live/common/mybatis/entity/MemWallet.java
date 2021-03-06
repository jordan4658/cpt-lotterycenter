package com.caipiao.live.common.mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemWallet implements Serializable {
    /**
     * 字段: mem_wallet.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 字段: mem_wallet.user_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * 字段: mem_wallet.balance<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 16<br/>
     * 说明: 可用余额
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * 字段: mem_wallet.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 字段: mem_wallet.update_time<br/>
     * 必填: false<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 字段: mem_wallet.merchant_code<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 32<br/>
     * 说明: 商户code值，默认为0
     *
     * @mbggenerated
     */
    private String merchantCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return mem_wallet.id: 
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * 字段: mem_wallet.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return mem_wallet.user_id: 用户id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 字段: mem_wallet.user_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return mem_wallet.balance: 可用余额
     *
     * @mbggenerated
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 字段: mem_wallet.balance<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 16<br/>
     * 说明: 可用余额
     *
     * @mbggenerated
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * @return mem_wallet.create_time: 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 字段: mem_wallet.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return mem_wallet.update_time: 修改时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 字段: mem_wallet.update_time<br/>
     * 必填: false<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 修改时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return mem_wallet.merchant_code: 商户code值，默认为0
     *
     * @mbggenerated
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     * 字段: mem_wallet.merchant_code<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 32<br/>
     * 说明: 商户code值，默认为0
     *
     * @mbggenerated
     */
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MemWallet other = (MemWallet) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getMerchantCode() == null ? other.getMerchantCode() == null : this.getMerchantCode().equals(other.getMerchantCode()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getMerchantCode() == null) ? 0 : getMerchantCode().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mem_wallet
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", balance=").append(balance);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", merchantCode=").append(merchantCode);
        sb.append("]");
        return sb.toString();
    }
}