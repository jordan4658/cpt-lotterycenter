package com.caipiao.core.library.vo.betorder;

import java.math.BigDecimal;

/**
 * @author lzy
 * @create 2018-07-13 11:13
 **/
public class MemberBetVO {

    private Integer id;

    /**
     * 说明: 订单号
     */
    private String orderCode;

    /**
     * 说明: 会员id
     */
    private Integer memberId;

    /**
     * 说明: 会员账号
     */
    private String account;

    /**
     * 说明: 彩种id
     */
    private Integer lotteryId;

    /**
     * 说明: 彩种
     */
    private String lottery;

    /**
     * 说明: 期号
     */
    private String issue;

    /**
     * 说明: 玩法id
     */
    private Integer lotteryPlayId;

    /**
     * 说明: 玩法
     */
    private String lotteryPlay;

    /**
     * 说明: 投注内容
     */
    private String betMessage;

    /**
     * 说明: 投注额
     */
    private BigDecimal betMoney;

    /**
     * 说明: 返点
     */
    private BigDecimal backwaterMoney;

    /**
     * 说明: 用户盈亏
     */
    private BigDecimal changeMoney;

    /**
     * 说明: 中奖额
     */
    private BigDecimal winMoney;

    /**
     * 说明: 投注时间
     */
    private String betTime;

    /**
     * 说明: 状态; 0,等待开奖; 1,中奖;2,未中奖;3,撤单
     */
    private String status;

    /**
     * 说明: 来源
     */
    private String source;

    /**
     * 说明: 撤单金额
     */
    private Integer cancelMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLottery() {
        return lottery;
    }

    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Integer getLotteryPlayId() {
        return lotteryPlayId;
    }

    public void setLotteryPlayId(Integer lotteryPlayId) {
        this.lotteryPlayId = lotteryPlayId;
    }

    public String getLotteryPlay() {
        return lotteryPlay;
    }

    public void setLotteryPlay(String lotteryPlay) {
        this.lotteryPlay = lotteryPlay;
    }

    public String getBetMessage() {
        return betMessage;
    }

    public void setBetMessage(String betMessage) {
        this.betMessage = betMessage;
    }

    public String getBetTime() {
        return betTime;
    }

    public void setBetTime(String betTime) {
        this.betTime = betTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getCancelMoney() {
        return cancelMoney;
    }

    public void setCancelMoney(Integer cancelMoney) {
        this.cancelMoney = cancelMoney;
    }

    public BigDecimal getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(BigDecimal betMoney) {
        this.betMoney = betMoney;
    }

    public BigDecimal getBackwaterMoney() {
        return backwaterMoney;
    }

    public void setBackwaterMoney(BigDecimal backwaterMoney) {
        this.backwaterMoney = backwaterMoney;
    }

    public BigDecimal getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(BigDecimal changeMoney) {
        this.changeMoney = changeMoney;
    }

    public BigDecimal getWinMoney() {
        return winMoney;
    }

    public void setWinMoney(BigDecimal winMoney) {
        this.winMoney = winMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
