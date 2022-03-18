package com.caipiao.live.common.model.dto.report;

import java.math.BigDecimal;


public class LotteryBetDataDO {



    private String userid;
    /**
     * 彩票投注金额
     */
    private BigDecimal lotamt;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public BigDecimal getLotamt() {
        return lotamt;
    }

    public void setLotamt(BigDecimal lotamt) {
        this.lotamt = lotamt;
    }
}
