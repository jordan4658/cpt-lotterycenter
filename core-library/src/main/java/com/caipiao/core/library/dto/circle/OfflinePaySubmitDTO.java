package com.caipiao.core.library.dto.circle;

import java.math.BigDecimal;

public class OfflinePaySubmitDTO {
    private Integer accountId;  //账号id
    private BigDecimal amount;  //充值金额
    private Integer fuyan;  //附言

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getFuyan() {
        return fuyan;
    }

    public void setFuyan(Integer fuyan) {
        this.fuyan = fuyan;
    }
}
