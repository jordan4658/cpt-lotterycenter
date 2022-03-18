package com.caipiao.live.common.model.dto;


import com.caipiao.live.common.model.dto.order.BasRuleoddsDO;

import java.util.ArrayList;
import java.util.List;

public class KuaiSanDO {

    private Long lotruleid;
    private String rulename;
    private String lotname;
    private List<BasRuleoddsDO> oddslist = new ArrayList<BasRuleoddsDO>();

    public String getLotname() {
        return lotname;
    }

    public void setLotname(String lotname) {
        this.lotname = lotname;
    }

    public Long getLotruleid() {
        return lotruleid;
    }

    public void setLotruleid(Long lotruleid) {
        this.lotruleid = lotruleid;
    }

    public String getRulename() {
        return rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    public List<BasRuleoddsDO> getOddslist() {
        return oddslist;
    }

    public void setOddslist(List<BasRuleoddsDO> oddslist) {
        this.oddslist = oddslist;
    }
}
