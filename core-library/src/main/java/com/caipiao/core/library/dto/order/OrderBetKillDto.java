package com.caipiao.core.library.dto.order;

import com.mapper.domain.OrderBetRecord;

public class OrderBetKillDto extends OrderBetRecord {
    private String odds;

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    private String issue;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

}
