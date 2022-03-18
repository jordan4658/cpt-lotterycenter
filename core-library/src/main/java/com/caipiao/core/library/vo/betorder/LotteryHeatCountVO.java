package com.caipiao.core.library.vo.betorder;

import java.math.BigDecimal;

/**
 * 彩种热度统计VO
 *
 * @author lzy
 * @create 2018-07-13 15:58
 **/
public class LotteryHeatCountVO {

    /**
     * 说明: 玩法名称
     */
    private String playName;

    /**
     * 说明: 玩法id
     */
    private Integer playId;

    /**
     * 说明: 总注数
     */
    private Integer totalBet;

    /**
     * 说明: 总有效注数
     */
    private Integer totalValidBet;

    /**
     * 说明: 总投注额
     */
    private BigDecimal totalBetMoney;

    /**
     * 说明: 总有效投注额
     */
    private BigDecimal totalValidBetMoney;

    /**
     * 说明: 未开奖金额
     */
    private BigDecimal noStartMoney;

    /**
     * 说明: 撤单金额
     */
    private BigDecimal cancelMoney;

    /**
     * 说明: 中奖额
     */
    private BigDecimal winMoney;

    /**
     * 说明: 返点
     */
    private BigDecimal backwaterMoney;

    /**
     * 说明: 盈亏
     */
    private BigDecimal changeMoney;

    /**
     * 说明: 总用户
     */
    private Integer totalMember;

    public static LotteryHeatCountVO getInstance(int totalBet, int totalValidBet, BigDecimal totalBetMoney, BigDecimal totalValidBetMoney, BigDecimal noStartMoney,
                                                 BigDecimal cancelMoney, BigDecimal winMoney, BigDecimal backwaterMoney, BigDecimal changeMoney, int totalMember) {
        LotteryHeatCountVO lotteryHeatCountVO = new LotteryHeatCountVO();
        lotteryHeatCountVO.setTotalBet(totalBet);
        lotteryHeatCountVO.setTotalValidBet(totalValidBet);
        lotteryHeatCountVO.setTotalBetMoney(totalBetMoney);
        lotteryHeatCountVO.setTotalValidBetMoney(totalValidBetMoney);
        lotteryHeatCountVO.setNoStartMoney(noStartMoney);
        lotteryHeatCountVO.setCancelMoney(cancelMoney);
        lotteryHeatCountVO.setWinMoney(winMoney);
        lotteryHeatCountVO.setBackwaterMoney(backwaterMoney);
        lotteryHeatCountVO.setChangeMoney(changeMoney);
        lotteryHeatCountVO.setTotalMember(totalMember);
        return lotteryHeatCountVO;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    public Integer getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(Integer totalBet) {
        this.totalBet = totalBet;
    }

    public Integer getTotalValidBet() {
        return totalValidBet;
    }

    public void setTotalValidBet(Integer totalValidBet) {
        this.totalValidBet = totalValidBet;
    }

    public BigDecimal getTotalBetMoney() {
        return totalBetMoney;
    }

    public void setTotalBetMoney(BigDecimal totalBetMoney) {
        this.totalBetMoney = totalBetMoney;
    }

    public BigDecimal getTotalValidBetMoney() {
        return totalValidBetMoney;
    }

    public void setTotalValidBetMoney(BigDecimal totalValidBetMoney) {
        this.totalValidBetMoney = totalValidBetMoney;
    }

    public BigDecimal getNoStartMoney() {
        return noStartMoney;
    }

    public void setNoStartMoney(BigDecimal noStartMoney) {
        this.noStartMoney = noStartMoney;
    }

    public BigDecimal getCancelMoney() {
        return cancelMoney;
    }

    public void setCancelMoney(BigDecimal cancelMoney) {
        this.cancelMoney = cancelMoney;
    }

    public BigDecimal getWinMoney() {
        return winMoney;
    }

    public void setWinMoney(BigDecimal winMoney) {
        this.winMoney = winMoney;
    }

    public BigDecimal getBackwaterMoney() {
        return backwaterMoney;
    }

    public void setBackwaterMoney(BigDecimal backwaterMoney) {
        this.backwaterMoney = backwaterMoney;
    }

    public BigDecimal getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(BigDecimal changeMoney) {
        this.changeMoney = changeMoney;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }
}
