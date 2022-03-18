package com.caipiao.core.library.dto.order;

import com.mapper.domain.OrderAppendRecord;

import java.math.BigDecimal;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/10/23 19:09
 */
public class OrderAppendDTO extends OrderAppendRecord {
    private String issue;
    private String lotteryName;
    private String playName;
    private BigDecimal odds;
    private BigDecimal currentBetPrice;

    public String getLotteryName() {
        return lotteryName;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public BigDecimal getCurrentBetPrice() {
        return currentBetPrice;
    }

    public void setCurrentBetPrice(BigDecimal currentBetPrice) {
        this.currentBetPrice = currentBetPrice;
    }
}
