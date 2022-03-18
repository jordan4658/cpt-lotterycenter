package com.caipiao.live.common.model.vo.order;

import java.math.BigDecimal;

/**
 * @ClassName OrderBetVo
 * @Description TODO
 * @Author yeezy
 * @Date 2019/11/21 17:51
 * @Version 1.0
 **/
public class OrderBetVo {
    private BigDecimal betAmount; //投注金额
    private BigDecimal winAmount; //中奖金额
    private String day; //结算日期
    private Integer userId; //用户id

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
