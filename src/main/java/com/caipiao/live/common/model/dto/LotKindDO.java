package com.caipiao.live.common.model.dto;

public class LotKindDO {

    // l.lotkindid,l.lotname,l.lotcode,l.kindimg
    private Long lotkindid;
    private String lotname;
    private String lotcode;
    private String kindimg;
    private String kindimgurl;

    public Long getLotkindid() {
        return lotkindid;
    }

    public void setLotkindid(Long lotkindid) {
        this.lotkindid = lotkindid;
    }

    public String getLotname() {
        return lotname;
    }

    public void setLotname(String lotname) {
        this.lotname = lotname;
    }

    public String getLotcode() {
        return lotcode;
    }

    public void setLotcode(String lotcode) {
        this.lotcode = lotcode;
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
