package com.caipiao.core.library.vo.betorder;

import java.math.BigDecimal;

/**
 * 盈亏统计VO
 *
 * @author lzy
 * @create 2018-07-14 14:50
 **/
public class ProfitAndLossCountVO {

    //有效投注总额
    private BigDecimal totalValidBet;

    //中奖总额
    private BigDecimal totalWins;

    //返点总额
    private BigDecimal totalBackWater;

    //盈亏总额
    private BigDecimal totalChange;

    public BigDecimal getTotalValidBet() {
        return totalValidBet;
    }

    public void setTotalValidBet(BigDecimal totalValidBet) {
        this.totalValidBet = totalValidBet;
    }

    public BigDecimal getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(BigDecimal totalWins) {
        this.totalWins = totalWins;
    }

    public BigDecimal getTotalBackWater() {
        return totalBackWater;
    }

    public void setTotalBackWater(BigDecimal totalBackWater) {
        this.totalBackWater = totalBackWater;
    }

    public BigDecimal getTotalChange() {
        return totalChange;
    }

    public void setTotalChange(BigDecimal totalChange) {
        this.totalChange = totalChange;
    }
}
