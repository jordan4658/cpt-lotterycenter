package com.caipiao.core.library.dto.appmember;

import com.mapper.domain.MemberBalanceChange;

import java.math.BigDecimal;

public class MemberBalanceChangeDTO extends MemberBalanceChange {

    /**
     * 说明: 累计投注（元）变动值
     */
    private BigDecimal betAmount;

    /**
     * 说明: 累计充值（元）变动值
     */
    private BigDecimal payAmount;

    /**
     * 说明: 累计提现（元）变动值
     */
    private BigDecimal withdrawalAmount;

    /**
     * 说明: 不可提现金额（元）变动值
     */
    private BigDecimal noWithdrawalAmount;

    /**
     * 记录账变记录的值
     */
    private BigDecimal showChange;

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public BigDecimal getNoWithdrawalAmount() {
        return noWithdrawalAmount;
    }

    public void setNoWithdrawalAmount(BigDecimal noWithdrawalAmount) {
        this.noWithdrawalAmount = noWithdrawalAmount;
    }

    public BigDecimal getShowChange() {
        return showChange;
    }

    public void setShowChange(BigDecimal showChange) {
        this.showChange = showChange;
    }
}
