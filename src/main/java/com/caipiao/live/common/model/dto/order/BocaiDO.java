package com.caipiao.live.common.model.dto.order;

public class BocaiDO {
    private String oddsname;
    private String amount;
    private String oddsid;
    private String lotname;
    private Integer betnum = 1;

    public Integer getBetnum() {
        return betnum;
    }

    public void setBetnum(Integer betnum) {
        this.betnum = betnum;
    }

    public String getOddsname() {
        return oddsname;
    }

    public void setOddsname(String oddsname) {
        this.oddsname = oddsname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOddsid() {
        return oddsid;
    }

    public void setOddsid(String oddsid) {
        this.oddsid = oddsid;
    }

    public String getLotname() {
        return lotname;
    }

    public void setLotname(String lotname) {
        this.lotname = lotname;
    }
}
