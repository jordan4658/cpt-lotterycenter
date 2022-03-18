package com.mapper.domain;

import java.io.Serializable;

public class FirstRechargeGiftBack implements Serializable {
    /**
     * 字段: first_recharge_gift_back.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 字段: first_recharge_gift_back.order_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 订单号
     *
     * @mbggenerated
     */
    private String orderCode;

    /**
     * 字段: first_recharge_gift_back.member_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 字段: first_recharge_gift_back.account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 会员账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 字段: first_recharge_gift_back.money<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 金额(单位:分)
     *
     * @mbggenerated
     */
    private Integer money;

    /**
     * 字段: first_recharge_gift_back.bet_money<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 投注金额(单位:分)
     *
     * @mbggenerated
     */
    private Integer betMoney;

    /**
     * 字段: first_recharge_gift_back.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 添加时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return first_recharge_gift_back.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: first_recharge_gift_back.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return first_recharge_gift_back.order_code: 订单号
     *
     * @mbggenerated
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 字段: first_recharge_gift_back.order_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 订单号
     *
     * @mbggenerated
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * @return first_recharge_gift_back.member_id: 会员id
     *
     * @mbggenerated
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 字段: first_recharge_gift_back.member_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 会员id
     *
     * @mbggenerated
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * @return first_recharge_gift_back.account: 会员账号
     *
     * @mbggenerated
     */
    public String getAccount() {
        return account;
    }

    /**
     * 字段: first_recharge_gift_back.account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 会员账号
     *
     * @mbggenerated
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return first_recharge_gift_back.money: 金额(单位:分)
     *
     * @mbggenerated
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * 字段: first_recharge_gift_back.money<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 金额(单位:分)
     *
     * @mbggenerated
     */
    public void setMoney(Integer money) {
        this.money = money;
    }

    /**
     * @return first_recharge_gift_back.bet_money: 投注金额(单位:分)
     *
     * @mbggenerated
     */
    public Integer getBetMoney() {
        return betMoney;
    }

    /**
     * 字段: first_recharge_gift_back.bet_money<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 投注金额(单位:分)
     *
     * @mbggenerated
     */
    public void setBetMoney(Integer betMoney) {
        this.betMoney = betMoney;
    }

    /**
     * @return first_recharge_gift_back.create_time: 添加时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: first_recharge_gift_back.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 添加时间
     *
     * @mbggenerated
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
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
        FirstRechargeGiftBack other = (FirstRechargeGiftBack) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderCode() == null ? other.getOrderCode() == null : this.getOrderCode().equals(other.getOrderCode()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getBetMoney() == null ? other.getBetMoney() == null : this.getBetMoney().equals(other.getBetMoney()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderCode() == null) ? 0 : getOrderCode().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getBetMoney() == null) ? 0 : getBetMoney().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table first_recharge_gift_back
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
        sb.append(", orderCode=").append(orderCode);
        sb.append(", memberId=").append(memberId);
        sb.append(", account=").append(account);
        sb.append(", money=").append(money);
        sb.append(", betMoney=").append(betMoney);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}