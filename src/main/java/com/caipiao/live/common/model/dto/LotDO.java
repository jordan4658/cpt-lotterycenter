package com.caipiao.live.common.model.dto;

public class LotDO {

    private Long lotkindid;
    private String lotname;
    private String lotcode;
    private String kindimg;
    private Integer stoptime;
    private Long parlotkindid;
    private String parlotname;
    private String parlotcode;

    public String getKindimg() {
        return kindimg;
    }

    public void setKindimg(String kindimg) {
        this.kindimg = kindimg;
    }

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

    public Integer getStoptime() {
        return stoptime;
    }

    public void setStoptime(Integer stoptime) {
        this.stoptime = stoptime;
    }

    public Long getParlotkindid() {
        return parlotkindid;
    }

    public void setParlotkindid(Long parlotkindid) {
        this.parlotkindid = parlotkindid;
    }

    public String getParlotname() {
        return parlotname;
    }

    public void setParlotname(String parlotname) {
        this.parlotname = parlotname;
    }

    public String getParlotcode() {
        return parlotcode;
    }

    public void setParlotcode(String parlotcode) {
        this.parlotcode = parlotcode;
    }

}
