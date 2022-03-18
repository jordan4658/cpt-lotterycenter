package com.caipiao.live.common.model.dto.order;

public class CaiBettingRecordDO {

    private Long orderid;
    private Double realamt;
    // 注单多个状态
    private String statuss;
    // 赢得金额
    private Double winamt;
    // 平局金额
    private Double pingjujine;
    private Long lotkindid;
    private Long sschistoryid;
    private String lotname;
    private String period;
    private String orderno;
    private String status;
    private String lotcode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Double getRealamt() {
        return realamt;
    }

    public void setRealamt(Double realamt) {
        this.realamt = realamt;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public Double getWinamt() {
        return winamt;
    }

    public void setWinamt(Double winamt) {
        this.winamt = winamt;
    }

    public Double getPingjujine() {
        return pingjujine;
    }

    public void setPingjujine(Double pingjujine) {
        this.pingjujine = pingjujine;
    }

    public Long getLotkindid() {
        return lotkindid;
    }

    public void setLotkindid(Long lotkindid) {
        this.lotkindid = lotkindid;
    }

    public Long getSschistoryid() {
        return sschistoryid;
    }

    public void setSschistoryid(Long sschistoryid) {
        this.sschistoryid = sschistoryid;
    }

    public String getLotname() {
        return lotname;
    }

    public void setLotname(String lotname) {
        this.lotname = lotname;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLotcode() {
        return lotcode;
    }

    public void setLotcode(String lotcode) {
        this.lotcode = lotcode;
    }
}
