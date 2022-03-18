package com.caipiao.live.common.model.vo.order;

import java.math.BigDecimal;

public class OrderTodayBetListVo {
    private BigDecimal betAmount; //投注金额
    private BigDecimal winAmount; //中奖金额
    private String tbStatus;//开奖状态 /中奖 /未中奖 /等待开奖

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

    public String getTbStatus() { return tbStatus; }

    public void setTbStatus(String tbStatus) { this.tbStatus = tbStatus; }
}
