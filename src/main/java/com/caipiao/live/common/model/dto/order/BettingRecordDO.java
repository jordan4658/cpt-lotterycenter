package com.caipiao.live.common.model.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class BettingRecordDO {

    private String period;
    private Integer status;
    private String nickname;
    private BigDecimal realamt;
    private String orderno;
    private String ordernote;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderdate;
    private BigDecimal winamt;
    private String rulename;
    private String lotname;
    private String oddsname;
    private String luckynum;
    private Long lotkindid;
    private String kindimg;
    private String kindimgurl;

    public String getRulename() {
        return rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    public String getKindimgurl() {
        return kindimgurl;
    }

    public void setKindimgurl(String kindimgurl) {
        this.kindimgurl = kindimgurl;
    }

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

    public String getLuckynum() {
        return luckynum;
    }

    public void setLuckynum(String luckynum) {
        this.luckynum = luckynum;
    }

    public String getOddsname() {
        return oddsname;
    }

    public void setOddsname(String oddsname) {
        this.oddsname = oddsname;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BigDecimal getRealamt() {
        return realamt;
    }

    public void setRealamt(BigDecimal realamt) {
        this.realamt = realamt;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrdernote() {
        return ordernote;
    }

    public void setOrdernote(String ordernote) {
        this.ordernote = ordernote;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public BigDecimal getWinamt() {
        return winamt;
    }

    public void setWinamt(BigDecimal winamt) {
        this.winamt = winamt;
    }

}
