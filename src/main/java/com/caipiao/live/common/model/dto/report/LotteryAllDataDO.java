package com.caipiao.live.common.model.dto.report;


import java.math.BigDecimal;


public class LotteryAllDataDO {


    /**
     * 彩票总投注
     */
    private BigDecimal lotamt;
    /**
     * 彩票总中奖金额
     */
    private BigDecimal lotawardamt;
    /**
     * 彩票盈利
     */
    private BigDecimal lotrofit;


    /**
     * 最大盈利
     */
    private BigDecimal maxprofit;
    /**
     * 彩种id
     */
    private Long maxprofitlotteryId;
    /**
     * 彩种名称
     */
    private String maxprofitlotteryname;

    /**
     * 最大亏损
     */
    private BigDecimal maxloss;

    /**
     * 彩种id
     */
    private Long maxlosslotteryid;
    /**
     * 彩种名称
     */
    private String maxlosslotteryname;

    /**
     * 最大投注
     */
    private BigDecimal maxbet;


    /**
     * 彩种id
     */
    private Long maxbetlotteryid;
    /**
     * 彩种名称
     */
    private String maxbetlotteryname;

    public BigDecimal getLotrofit() {
        return lotrofit;
    }

    public void setLotrofit(BigDecimal lotrofit) {
        this.lotrofit = lotrofit;
    }

    public BigDecimal getLotamt() {
        return lotamt;
    }

    public void setLotamt(BigDecimal lotamt) {
        this.lotamt = lotamt;
    }

    public BigDecimal getLotawardamt() {
        return lotawardamt;
    }

    public void setLotawardamt(BigDecimal lotawardamt) {
        this.lotawardamt = lotawardamt;
    }

    public BigDecimal getMaxprofit() {
        return maxprofit;
    }

    public void setMaxprofit(BigDecimal maxprofit) {
        this.maxprofit = maxprofit;
    }

    public Long getMaxprofitlotteryId() {
        return maxprofitlotteryId;
    }

    public void setMaxprofitlotteryId(Long maxprofitlotteryId) {
        this.maxprofitlotteryId = maxprofitlotteryId;
    }

    public String getMaxprofitlotteryname() {
        return maxprofitlotteryname;
    }

    public void setMaxprofitlotteryname(String maxprofitlotteryname) {
        this.maxprofitlotteryname = maxprofitlotteryname;
    }

    public BigDecimal getMaxloss() {
        return maxloss;
    }

    public void setMaxloss(BigDecimal maxloss) {
        this.maxloss = maxloss;
    }

    public Long getMaxlosslotteryid() {
        return maxlosslotteryid;
    }

    public void setMaxlosslotteryid(Long maxlosslotteryid) {
        this.maxlosslotteryid = maxlosslotteryid;
    }

    public String getMaxlosslotteryname() {
        return maxlosslotteryname;
    }

    public void setMaxlosslotteryname(String maxlosslotteryname) {
        this.maxlosslotteryname = maxlosslotteryname;
    }

    public BigDecimal getMaxbet() {
        return maxbet;
    }

    public void setMaxbet(BigDecimal maxbet) {
        this.maxbet = maxbet;
    }

    public Long getMaxbetlotteryid() {
        return maxbetlotteryid;
    }

    public void setMaxbetlotteryid(Long maxbetlotteryid) {
        this.maxbetlotteryid = maxbetlotteryid;
    }

    public String getMaxbetlotteryname() {
        return maxbetlotteryname;
    }

    public void setMaxbetlotteryname(String maxbetlotteryname) {
        this.maxbetlotteryname = maxbetlotteryname;
    }


    public static LotteryAllDataDO getDefault() {
        LotteryAllDataDO lotteryAllDataDO = new LotteryAllDataDO();
        lotteryAllDataDO.setLotrofit(new BigDecimal("0").setScale(3, BigDecimal.ROUND_DOWN));
        lotteryAllDataDO.setLotamt(new BigDecimal("0").setScale(3, BigDecimal.ROUND_DOWN));
        lotteryAllDataDO.setLotawardamt(new BigDecimal("0").setScale(3, BigDecimal.ROUND_DOWN));
        lotteryAllDataDO.setMaxprofit(new BigDecimal("0").setScale(3, BigDecimal.ROUND_DOWN));
        lotteryAllDataDO.setMaxprofitlotteryId(0L);
        lotteryAllDataDO.setMaxprofitlotteryname("");
        lotteryAllDataDO.setMaxloss(new BigDecimal("0").setScale(3, BigDecimal.ROUND_DOWN));
        lotteryAllDataDO.setMaxlosslotteryid(0L);
        lotteryAllDataDO.setMaxlosslotteryname("");
        lotteryAllDataDO.setMaxbet(new BigDecimal("0").setScale(3, BigDecimal.ROUND_DOWN));
        lotteryAllDataDO.setMaxbetlotteryid(0L);
        lotteryAllDataDO.setMaxbetlotteryname("");


        return lotteryAllDataDO;
    }
}
