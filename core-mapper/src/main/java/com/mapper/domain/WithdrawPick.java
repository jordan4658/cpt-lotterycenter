package com.mapper.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawPick implements Serializable {
    /**
     * 字段: withdraw_pick.id<br/>
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
     * 字段: withdraw_pick.member_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 会员id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 字段: withdraw_pick.account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 字段: withdraw_pick.order_code<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 订单号
     *
     * @mbggenerated
     */
    private String orderCode;

    /**
     * 字段: withdraw_pick.vip<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: vip等级
     *
     * @mbggenerated
     */
    private String vip;

    /**
     * 字段: withdraw_pick.card_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 卡号
     *
     * @mbggenerated
     */
    private String cardNumber;

    /**
     * 字段: withdraw_pick.bank<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 转入银行
     *
     * @mbggenerated
     */
    private String bank;

    /**
     * 字段: withdraw_pick.money<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 金额
     *
     * @mbggenerated
     */
    private BigDecimal money;

    /**
     * 字段: withdraw_pick.old_money<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 12<br/>
     * 说明: 已提金额
     *
     * @mbggenerated
     */
    private BigDecimal oldMoney;

    /**
     * 字段: withdraw_pick.source<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 来源
     *
     * @mbggenerated
     */
    private String source;

    /**
     * 字段: withdraw_pick.op_name<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作人用户名
     *
     * @mbggenerated
     */
    private String opName;

    /**
     * 字段: withdraw_pick.op_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作人id
     *
     * @mbggenerated
     */
    private String opId;

    /**
     * 字段: withdraw_pick.status<br/>
     * 必填: false<br/>
     * 缺省: 2<br/>
     * 长度: 10<br/>
     * 说明: 状态(1.待处理2.处理中3.拒绝4.成功)
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 字段: withdraw_pick.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * 字段: withdraw_pick.op_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作时间
     *
     * @mbggenerated
     */
    private String opTime;

    /**
     * 字段: withdraw_pick.op_type<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 操作方式 1线上 2线下
     *
     * @mbggenerated
     */
    private Integer opType;

    /**
     * 字段: withdraw_pick.mark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 4000<br/>
     * 说明: 备注
     *
     * @mbggenerated
     */
    private String mark;

    /**
     * 字段: withdraw_pick.delete<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Integer delete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table withdraw_pick
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return withdraw_pick.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: withdraw_pick.id<br/>
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
     * @return withdraw_pick.member_id: 会员id
     *
     * @mbggenerated
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 字段: withdraw_pick.member_id<br/>
     * 必填: true<br/>
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
     * @return withdraw_pick.account: 账号
     *
     * @mbggenerated
     */
    public String getAccount() {
        return account;
    }

    /**
     * 字段: withdraw_pick.account<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 账号
     *
     * @mbggenerated
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return withdraw_pick.order_code: 订单号
     *
     * @mbggenerated
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 字段: withdraw_pick.order_code<br/>
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
     * @return withdraw_pick.vip: vip等级
     *
     * @mbggenerated
     */
    public String getVip() {
        return vip;
    }

    /**
     * 字段: withdraw_pick.vip<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: vip等级
     *
     * @mbggenerated
     */
    public void setVip(String vip) {
        this.vip = vip;
    }

    /**
     * @return withdraw_pick.card_number: 卡号
     *
     * @mbggenerated
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 字段: withdraw_pick.card_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 卡号
     *
     * @mbggenerated
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return withdraw_pick.bank: 转入银行
     *
     * @mbggenerated
     */
    public String getBank() {
        return bank;
    }

    /**
     * 字段: withdraw_pick.bank<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 转入银行
     *
     * @mbggenerated
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * @return withdraw_pick.money: 金额
     *
     * @mbggenerated
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 字段: withdraw_pick.money<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 金额
     *
     * @mbggenerated
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * @return withdraw_pick.old_money: 已提金额
     *
     * @mbggenerated
     */
    public BigDecimal getOldMoney() {
        return oldMoney;
    }

    /**
     * 字段: withdraw_pick.old_money<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 12<br/>
     * 说明: 已提金额
     *
     * @mbggenerated
     */
    public void setOldMoney(BigDecimal oldMoney) {
        this.oldMoney = oldMoney;
    }

    /**
     * @return withdraw_pick.source: 来源
     *
     * @mbggenerated
     */
    public String getSource() {
        return source;
    }

    /**
     * 字段: withdraw_pick.source<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 来源
     *
     * @mbggenerated
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return withdraw_pick.op_name: 操作人用户名
     *
     * @mbggenerated
     */
    public String getOpName() {
        return opName;
    }

    /**
     * 字段: withdraw_pick.op_name<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作人用户名
     *
     * @mbggenerated
     */
    public void setOpName(String opName) {
        this.opName = opName;
    }

    /**
     * @return withdraw_pick.op_id: 操作人id
     *
     * @mbggenerated
     */
    public String getOpId() {
        return opId;
    }

    /**
     * 字段: withdraw_pick.op_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作人id
     *
     * @mbggenerated
     */
    public void setOpId(String opId) {
        this.opId = opId;
    }

    /**
     * @return withdraw_pick.status: 状态(1.待处理2.处理中3.拒绝4.成功)
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 字段: withdraw_pick.status<br/>
     * 必填: false<br/>
     * 缺省: 2<br/>
     * 长度: 10<br/>
     * 说明: 状态(1.待处理2.处理中3.拒绝4.成功)
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return withdraw_pick.create_time: 创建时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: withdraw_pick.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return withdraw_pick.op_time: 操作时间
     *
     * @mbggenerated
     */
    public String getOpTime() {
        return opTime;
    }

    /**
     * 字段: withdraw_pick.op_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 操作时间
     *
     * @mbggenerated
     */
    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    /**
     * @return withdraw_pick.op_type: 操作方式 1线上 2线下
     *
     * @mbggenerated
     */
    public Integer getOpType() {
        return opType;
    }

    /**
     * 字段: withdraw_pick.op_type<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 操作方式 1线上 2线下
     *
     * @mbggenerated
     */
    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    /**
     * @return withdraw_pick.mark: 备注
     *
     * @mbggenerated
     */
    public String getMark() {
        return mark;
    }

    /**
     * 字段: withdraw_pick.mark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 4000<br/>
     * 说明: 备注
     *
     * @mbggenerated
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * @return withdraw_pick.delete: 
     *
     * @mbggenerated
     */
    public Integer getDelete() {
        return delete;
    }

    /**
     * 字段: withdraw_pick.delete<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_pick
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
        WithdrawPick other = (WithdrawPick) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getOrderCode() == null ? other.getOrderCode() == null : this.getOrderCode().equals(other.getOrderCode()))
            && (this.getVip() == null ? other.getVip() == null : this.getVip().equals(other.getVip()))
            && (this.getCardNumber() == null ? other.getCardNumber() == null : this.getCardNumber().equals(other.getCardNumber()))
            && (this.getBank() == null ? other.getBank() == null : this.getBank().equals(other.getBank()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getOldMoney() == null ? other.getOldMoney() == null : this.getOldMoney().equals(other.getOldMoney()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getOpName() == null ? other.getOpName() == null : this.getOpName().equals(other.getOpName()))
            && (this.getOpId() == null ? other.getOpId() == null : this.getOpId().equals(other.getOpId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getOpTime() == null ? other.getOpTime() == null : this.getOpTime().equals(other.getOpTime()))
            && (this.getOpType() == null ? other.getOpType() == null : this.getOpType().equals(other.getOpType()))
            && (this.getMark() == null ? other.getMark() == null : this.getMark().equals(other.getMark()))
            && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_pick
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getOrderCode() == null) ? 0 : getOrderCode().hashCode());
        result = prime * result + ((getVip() == null) ? 0 : getVip().hashCode());
        result = prime * result + ((getCardNumber() == null) ? 0 : getCardNumber().hashCode());
        result = prime * result + ((getBank() == null) ? 0 : getBank().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getOldMoney() == null) ? 0 : getOldMoney().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getOpName() == null) ? 0 : getOpName().hashCode());
        result = prime * result + ((getOpId() == null) ? 0 : getOpId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getOpTime() == null) ? 0 : getOpTime().hashCode());
        result = prime * result + ((getOpType() == null) ? 0 : getOpType().hashCode());
        result = prime * result + ((getMark() == null) ? 0 : getMark().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table withdraw_pick
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
        sb.append(", memberId=").append(memberId);
        sb.append(", account=").append(account);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", vip=").append(vip);
        sb.append(", cardNumber=").append(cardNumber);
        sb.append(", bank=").append(bank);
        sb.append(", money=").append(money);
        sb.append(", oldMoney=").append(oldMoney);
        sb.append(", source=").append(source);
        sb.append(", opName=").append(opName);
        sb.append(", opId=").append(opId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", opTime=").append(opTime);
        sb.append(", opType=").append(opType);
        sb.append(", mark=").append(mark);
        sb.append(", delete=").append(delete);
        sb.append("]");
        return sb.toString();
    }
}