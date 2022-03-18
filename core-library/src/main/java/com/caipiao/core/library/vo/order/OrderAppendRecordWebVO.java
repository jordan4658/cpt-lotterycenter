package com.caipiao.core.library.vo.order;

import java.math.BigDecimal;

public class OrderAppendRecordWebVO {
    private String lotteryName; //彩种
    private String playName;    //玩法
    private String firstIssue;  //起始期号
    private Integer appendedCount;  //已投期数
    private Integer appendCount;    //总的追号期数
    private String betNumber;   //投注内容
    private BigDecimal currentTotalBetAmount;   //已投注的总金额
    private BigDecimal totalBetAmount;  //总的需要追号的金额
    private BigDecimal winAmount;   //已中奖金
    private Integer winCount;   //已中期数
    private Boolean winStop;    //追中即停
    private Boolean isStop; //是否已停止追号

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getFirstIssue() {
        return firstIssue;
    }

    public void setFirstIssue(String firstIssue) {
        this.firstIssue = firstIssue;
    }

    public Integer getAppendedCount() {
        return appendedCount;
    }

    public void setAppendedCount(Integer appendedCount) {
        this.appendedCount = appendedCount;
    }

    public Integer getAppendCount() {
        return appendCount;
    }

    public void setAppendCount(Integer appendCount) {
        this.appendCount = appendCount;
    }

    public String getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(String betNumber) {
        this.betNumber = betNumber;
    }

    public BigDecimal getCurrentTotalBetAmount() {
        return currentTotalBetAmount;
    }

    public void setCurrentTotalBetAmount(BigDecimal currentTotalBetAmount) {
        this.currentTotalBetAmount = currentTotalBetAmount;
    }

    public BigDecimal getTotalBetAmount() {
        return totalBetAmount;
    }

    public void setTotalBetAmount(BigDecimal totalBetAmount) {
        this.totalBetAmount = totalBetAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public Integer getWinCount() {
        return winCount;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    public Boolean getWinStop() {
        return winStop;
    }

    public void setWinStop(Boolean winStop) {
        this.winStop = winStop;
    }

    public Boolean getIsStop() {
        return isStop;
    }

    public void setIsStop(Boolean isStop) {
        this.isStop = isStop;
    }

}
