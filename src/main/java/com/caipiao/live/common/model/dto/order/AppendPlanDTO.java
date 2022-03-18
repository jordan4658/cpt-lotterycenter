package com.caipiao.live.common.model.dto.order;

import java.math.BigDecimal;

public class AppendPlanDTO {

    private String issue; // 期号
    private Double multiples; // 倍数
    private String number; // 投注号码
    private BigDecimal amount; // 追号金额

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Double getMultiples() {
        return multiples;
    }

    public void setMultiples(Double multiples) {
        this.multiples = multiples;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
