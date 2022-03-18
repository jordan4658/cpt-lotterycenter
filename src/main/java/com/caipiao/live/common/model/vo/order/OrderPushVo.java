package com.caipiao.live.common.model.vo.order;

import java.math.BigDecimal;


public class OrderPushVo {
	/**
	 * 彩种名称
	 */
	public  String lotteryName;
	/**
	 * 期号
	 */
	public  String issue;
	/**
	 * 订单号
	 */
	public  String  orderSn;
	/**
	 * 呢称
	 */
	public String   nickName;
	/**
	 * 投注金额
	 */
	public  BigDecimal  betAmount;
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public BigDecimal getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(BigDecimal betAmount) {
		this.betAmount = betAmount;
	}

}
