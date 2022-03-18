package com.caipiao.core.library.vo.betorder;

/**
 * 彩种热度VO
 *
 * @author lzy
 * @create 2018-07-13 15:41
 **/
public class LotteryHeatVO {

    /**
     * 说明: 彩种id
     */
    private Integer lotteryId;

    /**
     * 说明: 玩法id
     */
    private Integer lotteryPlayId;

    /**
     * 说明: 玩法
     */
    private String lotteryPlay;

    /**
     * 说明: 总注数
     */
    private Integer totalBet;

    /**
     * 说明: 总有效注数
     */
    private Integer totalValidBet;

    /**
     * 说明: 总投注额
     */
    private Integer totalBetMoney;

    /**
     * 说明: 总有效投注额
     */
    private Integer totalValidBetMoney;

    /**
     * 说明: 未开奖金额
     */
    private Integer noStartMoney;

    /**
     * 说明: 撤单金额
     */
    private Integer cancelMoney;

    /**
     * 说明: 中奖额
     */
    private Integer winMoney;

    /**
     * 说明: 返点
     */
    private Integer backwaterMoney;

    /**
     * 说明: 用户盈亏
     */
    private Integer changeMoney;

    /**
     * 说明: 总用户
     */
    private Integer totalMember;

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
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

    public Integer getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(Integer totalBet) {
        this.totalBet = totalBet;
    }

    public Integer getTotalValidBet() {
        return totalValidBet;
    }

    public void setTotalValidBet(Integer totalValidBet) {
        this.totalValidBet = totalValidBet;
    }

    public Integer getTotalBetMoney() {
        return totalBetMoney;
    }

    public void setTotalBetMoney(Integer totalBetMoney) {
        this.totalBetMoney = totalBetMoney;
    }

    public Integer getTotalValidBetMoney() {
        return totalValidBetMoney;
    }

    public void setTotalValidBetMoney(Integer totalValidBetMoney) {
        this.totalValidBetMoney = totalValidBetMoney;
    }

    public Integer getNoStartMoney() {
        return noStartMoney;
    }

    public void setNoStartMoney(Integer noStartMoney) {
        this.noStartMoney = noStartMoney;
    }

    public Integer getCancelMoney() {
        return cancelMoney;
    }

    public void setCancelMoney(Integer cancelMoney) {
        this.cancelMoney = cancelMoney;
    }

    public Integer getWinMoney() {
        return winMoney;
    }

    public void setWinMoney(Integer winMoney) {
        this.winMoney = winMoney;
    }

    public Integer getBackwaterMoney() {
        return backwaterMoney;
    }

    public void setBackwaterMoney(Integer backwaterMoney) {
        this.backwaterMoney = backwaterMoney;
    }

    public Integer getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(Integer changeMoney) {
        this.changeMoney = changeMoney;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }
}
