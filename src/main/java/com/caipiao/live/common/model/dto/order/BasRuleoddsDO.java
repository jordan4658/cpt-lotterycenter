package com.caipiao.live.common.model.dto.order;

import java.math.BigDecimal;

public class BasRuleoddsDO {

    private Long oddsid;

    private Long lotruleid;

    private String bettingname;

    private String bettingcode;

    private BigDecimal oddsval;

    public String getLotname() {
        return lotname;
    }

    public void setLotname(String lotname) {
        this.lotname = lotname;
    }

    private String lotname;

    public String getRulename() {
        return rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    private String rulename;

    public Long getOddsid() {
        return oddsid;
    }

    public void setOddsid(Long oddsid) {
        this.oddsid = oddsid;
    }

    public Long getLotruleid() {
        return lotruleid;
    }

    public void setLotruleid(Long lotruleid) {
        this.lotruleid = lotruleid;
    }

    public String getBettingname() {
        return bettingname;
    }

    public void setBettingname(String bettingname) {
        this.bettingname = bettingname;
    }

    public String getBettingcode() {
        return bettingcode;
    }

    public void setBettingcode(String bettingcode) {
        this.bettingcode = bettingcode;
    }

    public BigDecimal getOddsval() {
        return oddsval;
    }

    public void setOddsval(BigDecimal oddsval) {
        this.oddsval = oddsval;
    }
}
