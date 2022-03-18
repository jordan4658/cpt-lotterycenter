package com.caipiao.core.library.vo.circle;

import java.math.BigDecimal;

public class GodListWebVO {
    private Integer godId;      //大神id
    private Integer memberId;    //用户id
    private String heads;   //头像
    private String nickname;    //昵称，没有的话展示用户名
    private BigDecimal profitRate;  //盈利率
    private BigDecimal winRate; //胜率
    private Integer lianWin;    //连胜
    private Integer winNumber;  //盈利单数
    private BigDecimal huoli;   //获利
    private Integer isFocus;    //是否关注  0否1是

    public Integer getGodId() {
        return godId;
    }

    public void setGodId(Integer godId) {
        this.godId = godId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getHeads() {
        return heads;
    }

    public void setHeads(String heads) {
        this.heads = heads;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getWinRate() {
        return winRate;
    }

    public void setWinRate(BigDecimal winRate) {
        this.winRate = winRate;
    }

    public Integer getLianWin() {
        return lianWin;
    }

    public void setLianWin(Integer lianWin) {
        this.lianWin = lianWin;
    }

    public Integer getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(Integer winNumber) {
        this.winNumber = winNumber;
    }

    public BigDecimal getHuoli() {
        return huoli;
    }

    public void setHuoli(BigDecimal huoli) {
        this.huoli = huoli;
    }

    public Integer getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(Integer isFocus) {
        this.isFocus = isFocus;
    }
}
