package com.caipiao.core.library.dto.money;

import java.math.BigDecimal;

import com.mapper.domain.WithdrawPick;

public class WithdrawPickDTO {
	public static WithdrawPick getWithdrawPick(WithdrawPickDTO w){
		WithdrawPick wp= new WithdrawPick();
		if(w!=null){
			wp.setId(w.getDelete());
			wp.setAccount(w.getAccount());
			wp.setBank(w.getBank());
		}
		
		return null;
	}
	
    private Integer id;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getOldMoney() {
		return oldMoney;
	}

	public void setOldMoney(BigDecimal oldMoney) {
		this.oldMoney = oldMoney;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getDelete() {
		return delete;
	}

	public void setDelete(Integer delete) {
		this.delete = delete;
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

}
