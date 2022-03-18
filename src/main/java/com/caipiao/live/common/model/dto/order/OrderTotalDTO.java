package com.caipiao.live.common.model.dto.order;

import java.math.BigDecimal;

public class OrderTotalDTO {

    /**
     * 礼物分成总数
     */
    private BigDecimal familyGiftTotal;
    /**
     * 投注分成总数
     */
    private BigDecimal familyBetTotal;

    public BigDecimal getFamilyGiftTotal() {
        return familyGiftTotal;
    }

    public void setFamilyGiftTotal(BigDecimal familyGiftTotal) {
        this.familyGiftTotal = familyGiftTotal;
    }

    public BigDecimal getFamilyBetTotal() {
        return familyBetTotal;
    }

    public void setFamilyBetTotal(BigDecimal familyBetTotal) {
        this.familyBetTotal = familyBetTotal;
    }
}
