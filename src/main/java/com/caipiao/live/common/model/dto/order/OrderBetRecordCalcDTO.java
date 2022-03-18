package com.caipiao.live.common.model.dto.order;

import java.math.BigDecimal;

public class OrderBetRecordCalcDTO {

    private int betCount;

    private BigDecimal betAmount;

    public int getBetCount() {
        return betCount;
    }

    public void setBetCount(int betCount) {
        this.betCount = betCount;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }
}
