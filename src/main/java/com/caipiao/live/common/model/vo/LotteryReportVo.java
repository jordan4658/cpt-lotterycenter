package com.caipiao.live.common.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class LotteryReportVo {
    /**
     * 玩法id
     */
    private Integer playId;
    /**
     * 期号
     */
    private String issue;
    /**
     * 玩法名称
     */
    private String playName;

    /**
     * 投注时间
     */
    private Date createTime;
    /**
     * 有效投注金额
     */
    private BigDecimal betAmount;
    /**
     * 输赢金额
     */
    private BigDecimal winAmount;
    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 投注号码
     */
    private String betNumber;
    /**
     * 开奖号码
     */
    private String openNumber;
    /**
     * 结算状态
     */
    private String settStatus;

    /**
     * 彩种id
     *
     * @return
     */
    private Integer lotteryId;

    /**
     * 玩法配置id
     * @return
     */
    private Integer settingId;

    /**
     * 赔率
     */
    private String odd;
    /**
     * Ae游戏Id
     */
    private String gameId;
    /**
     * Ag游戏类型
     */
    private String gameType;
    /**
     * 开元游戏id
     */
    private Integer kindId;

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(String betNumber) {
        this.betNumber = betNumber;
    }

    public String getOpenNumber() {
        return openNumber;
    }

    public void setOpenNumber(String openNumber) {
        this.openNumber = openNumber;
    }

    public String getSettStatus() {
        return settStatus;
    }

    public void setSettStatus(String settStatus) {
        this.settStatus = settStatus;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }


}
