package com.caipiao.live.common.model;

public class LotteryTicket {

    Long lotteryid;
    String lotteryname;
    String lotterycode;
    String kindimg;
    String kindimgurl;

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

    public String getLotterycode() {
        return lotterycode;
    }

    public void setLotterycode(String lotterycode) {
        this.lotterycode = lotterycode;
    }
}
