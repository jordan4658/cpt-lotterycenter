package com.caipiao.live.common.model.dto.order;

import java.math.BigDecimal;

public class AppendBetDTO {

    private Integer lotteryId; // 彩种id
    private Integer playId; // 玩法id
    private Integer settingId; // 配置id
    private String betNumber; // 投注号码
    private Integer betCount; // 投注注数
    private BigDecimal betPrice; // 单注金额

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public String getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(String betNumber) {
        this.betNumber = betNumber;
    }

    public Integer getBetCount() {
        return betCount;
    }

    public void setBetCount(Integer betCount) {
        this.betCount = betCount;
    }

    public BigDecimal getBetPrice() {
        return betPrice;
    }

    public void setBetPrice(BigDecimal betPrice) {
        this.betPrice = betPrice;
    }
}
