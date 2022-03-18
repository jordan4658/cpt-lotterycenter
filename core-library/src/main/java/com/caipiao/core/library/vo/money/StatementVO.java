package com.caipiao.core.library.vo.money;

import java.math.BigDecimal;

public class StatementVO {
    private Integer totalOrder;     //总注单
    private Integer iosOrder;     //iOS注单
    private Integer androidOrder;     //安卓注单
    private Integer pcOrder;     //PC注单

    private BigDecimal totalBet;    //总投注
    private BigDecimal iosBet;    //iOS投注
    private BigDecimal androidBet;    //安卓投注
    private BigDecimal pcBet;    //PC投注

    private BigDecimal totalProfitLoss;    //总盈亏
    private BigDecimal iosProfitLoss;    //iOS盈亏
    private BigDecimal androidProfitLoss;    //安卓盈亏
    private BigDecimal pcProfitLoss;    //PC盈亏

    private BigDecimal totalCharge;    //总充值
    private BigDecimal iosCharge;    //iOS充值
    private BigDecimal androidCharge;    //安卓充值
    private BigDecimal pcCharge;    //PC充值

    private BigDecimal totalWithdraw;    //总提现
    private BigDecimal iosWithdraw;    //iOS提现
    private BigDecimal androidWithdraw;    //安卓提现
    private BigDecimal pcWithdraw;    //PC提现

    private Integer registerNum;    //总人数
    private Integer chargeNum;  //充值人数
    private BigDecimal chargeMoney; //充值金额
    private BigDecimal withdrawMoney;   //提现金额

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Integer getIosOrder() {
        return iosOrder;
    }

    public void setIosOrder(Integer iosOrder) {
        this.iosOrder = iosOrder;
    }

    public Integer getAndroidOrder() {
        return androidOrder;
    }

    public void setAndroidOrder(Integer androidOrder) {
        this.androidOrder = androidOrder;
    }

    public Integer getPcOrder() {
        return pcOrder;
    }

    public void setPcOrder(Integer pcOrder) {
        this.pcOrder = pcOrder;
    }

    public BigDecimal getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(BigDecimal totalBet) {
        this.totalBet = totalBet;
    }

    public BigDecimal getIosBet() {
        return iosBet;
    }

    public void setIosBet(BigDecimal iosBet) {
        this.iosBet = iosBet;
    }

    public BigDecimal getAndroidBet() {
        return androidBet;
    }

    public void setAndroidBet(BigDecimal androidBet) {
        this.androidBet = androidBet;
    }

    public BigDecimal getPcBet() {
        return pcBet;
    }

    public void setPcBet(BigDecimal pcBet) {
        this.pcBet = pcBet;
    }

    public BigDecimal getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(BigDecimal totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    public BigDecimal getIosProfitLoss() {
        return iosProfitLoss;
    }

    public void setIosProfitLoss(BigDecimal iosProfitLoss) {
        this.iosProfitLoss = iosProfitLoss;
    }

    public BigDecimal getAndroidProfitLoss() {
        return androidProfitLoss;
    }

    public void setAndroidProfitLoss(BigDecimal androidProfitLoss) {
        this.androidProfitLoss = androidProfitLoss;
    }

    public BigDecimal getPcProfitLoss() {
        return pcProfitLoss;
    }

    public void setPcProfitLoss(BigDecimal pcProfitLoss) {
        this.pcProfitLoss = pcProfitLoss;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public BigDecimal getIosCharge() {
        return iosCharge;
    }

    public void setIosCharge(BigDecimal iosCharge) {
        this.iosCharge = iosCharge;
    }

    public BigDecimal getAndroidCharge() {
        return androidCharge;
    }

    public void setAndroidCharge(BigDecimal androidCharge) {
        this.androidCharge = androidCharge;
    }

    public BigDecimal getPcCharge() {
        return pcCharge;
    }

    public void setPcCharge(BigDecimal pcCharge) {
        this.pcCharge = pcCharge;
    }

    public BigDecimal getTotalWithdraw() {
        return totalWithdraw;
    }

    public void setTotalWithdraw(BigDecimal totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
    }

    public BigDecimal getIosWithdraw() {
        return iosWithdraw;
    }

    public void setIosWithdraw(BigDecimal iosWithdraw) {
        this.iosWithdraw = iosWithdraw;
    }

    public BigDecimal getAndroidWithdraw() {
        return androidWithdraw;
    }

    public void setAndroidWithdraw(BigDecimal androidWithdraw) {
        this.androidWithdraw = androidWithdraw;
    }

    public BigDecimal getPcWithdraw() {
        return pcWithdraw;
    }

    public void setPcWithdraw(BigDecimal pcWithdraw) {
        this.pcWithdraw = pcWithdraw;
    }

    public Integer getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Integer registerNum) {
        this.registerNum = registerNum;
    }

    public Integer getChargeNum() {
        return chargeNum;
    }

    public void setChargeNum(Integer chargeNum) {
        this.chargeNum = chargeNum;
    }

    public BigDecimal getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(BigDecimal chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public BigDecimal getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(BigDecimal withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }
}
