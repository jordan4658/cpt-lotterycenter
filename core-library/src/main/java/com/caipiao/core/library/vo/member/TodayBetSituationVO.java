package com.caipiao.core.library.vo.member;

import java.math.BigDecimal;

public class TodayBetSituationVO {
    private BigDecimal betAmount;   //投注金额
    private BigDecimal winAmount;   //中奖金额
    private BigDecimal profitAmount;    //盈利金额

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

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }
}
