package com.caipiao.live.common.model.dto.report;

import java.math.BigDecimal;


public class LotteryDataDO {

    /**
     * 彩种id
     */
    private Long lotteryid;
    /**
     * 彩种名称
     */
    private String lotteryname;


    /**
     * 彩票充值金额
     */
    private BigDecimal lotamt;


    /**
     * 彩票赢钱金额
     */
    private BigDecimal lotawardamt;

    public Long getLotteryid() {
        return lotteryid;
    }

    public void setLotteryid(Long lotteryid) {
        this.lotteryid = lotteryid;
    }

    public String getLotteryname() {
        return lotteryname;
    }

    public void setLotteryname(String lotteryname) {
        this.lotteryname = lotteryname;
    }

    public BigDecimal getLotamt() {
        return lotamt;
    }

    public void setLotamt(BigDecimal lotamt) {
        this.lotamt = lotamt;
    }

    public BigDecimal getLotawardamt() {
        return lotawardamt;
    }

    public void setLotawardamt(BigDecimal lotawardamt) {
        this.lotawardamt = lotawardamt;
    }

    public BigDecimal getLotprofit() {
        return lotamt != null ? lotamt.subtract(lotawardamt) : BigDecimal.ZERO;
    }
}
