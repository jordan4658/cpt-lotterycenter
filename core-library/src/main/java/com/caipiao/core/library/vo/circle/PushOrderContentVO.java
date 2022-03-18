package com.caipiao.core.library.vo.circle;

import java.math.BigDecimal;

public class PushOrderContentVO {
    private String playName;    //玩法名称
    private BigDecimal odds;    //赔率
    private String betNumber;  //投注号码
    private BigDecimal betAmount;   //投注金额
    private BigDecimal winAmount; //彩金

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public String getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(String betNumber) {
        this.betNumber = betNumber;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }
}
