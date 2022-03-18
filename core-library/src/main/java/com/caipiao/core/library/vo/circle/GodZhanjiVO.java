package com.caipiao.core.library.vo.circle;

import java.math.BigDecimal;

public class GodZhanjiVO {
    private String account;
    private Integer fansNumber;
    private BigDecimal calcDml; //最近一周打码量
    private BigDecimal calcWinAmount;   //最近一周中奖金额
    private Integer pushOrderCount;    //最近一周推单数
    private BigDecimal calcProfitRate;  //最近一周累计盈利率
    private BigDecimal calcWinRate;  //最近一周累计胜率
    private BigDecimal calcFenhong; //最近一周累计获得分红
    private Integer calcMaxLz;  //最近一周最大连中
    private Integer gdCount;    //最近一周跟单总数

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(Integer fansNumber) {
        this.fansNumber = fansNumber;
    }

    public BigDecimal getCalcDml() {
        return calcDml;
    }

    public void setCalcDml(BigDecimal calcDml) {
        this.calcDml = calcDml;
    }

    public BigDecimal getCalcWinAmount() {
        return calcWinAmount;
    }

    public void setCalcWinAmount(BigDecimal calcWinAmount) {
        this.calcWinAmount = calcWinAmount;
    }

    public Integer getPushOrderCount() {
        return pushOrderCount;
    }

    public void setPushOrderCount(Integer pushOrderCount) {
        this.pushOrderCount = pushOrderCount;
    }

    public BigDecimal getCalcProfitRate() {
        return calcProfitRate;
    }

    public void setCalcProfitRate(BigDecimal calcProfitRate) {
        this.calcProfitRate = calcProfitRate;
    }

    public BigDecimal getCalcWinRate() {
        return calcWinRate;
    }

    public void setCalcWinRate(BigDecimal calcWinRate) {
        this.calcWinRate = calcWinRate;
    }

    public BigDecimal getCalcFenhong() {
        return calcFenhong;
    }

    public void setCalcFenhong(BigDecimal calcFenhong) {
        this.calcFenhong = calcFenhong;
    }

    public Integer getCalcMaxLz() {
        return calcMaxLz;
    }

    public void setCalcMaxLz(Integer calcMaxLz) {
        this.calcMaxLz = calcMaxLz;
    }

    public Integer getGdCount() {
        return gdCount;
    }

    public void setGdCount(Integer gdCount) {
        this.gdCount = gdCount;
    }
}
