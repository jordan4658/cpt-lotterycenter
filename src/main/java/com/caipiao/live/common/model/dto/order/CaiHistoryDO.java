package com.caipiao.live.common.model.dto.order;

public class CaiHistoryDO {

    private String lotname;
    private Long lotkindid;
    private String period;
    private String kindimg;
    private String kindimgurl;
    private String parentcode;
    private String  luckynum;

    public String getLuckynum() {
        return luckynum;
    }

    public void setLuckynum(String luckynum) {
        this.luckynum = luckynum;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getLotname() {
        return lotname;
    }

    public void setLotname(String lotname) {
        this.lotname = lotname;
    }

    public Long getLotkindid() {
        return lotkindid;
    }

    public void setLotkindid(Long lotkindid) {
        this.lotkindid = lotkindid;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getKindimg() {
        return kindimg;
    }

    public void setKindimg(String kindimg) {
        this.kindimg = kindimg;
    }

    public String getKindimgurl() {
        return kindimgurl;
    }

    public void setKindimgurl(String kindimgurl) {
        this.kindimgurl = kindimgurl;
    }
}
