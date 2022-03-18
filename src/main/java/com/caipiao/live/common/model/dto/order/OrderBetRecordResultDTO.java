package com.caipiao.live.common.model.dto.order;


import com.caipiao.live.common.mybatis.entity.OrderBetRecord;

import java.math.BigDecimal;

/**
 * @Date:Created in 0:102020/3/17
 * @Descriotion
 * @Author
 **/

public class OrderBetRecordResultDTO extends OrderBetRecord {
    private Integer betId; //投注id
    private String issue;

    private String orderSn;

    private BigDecimal win;

    private String Source;

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public BigDecimal getWin() {
        return win;
    }

    public void setWin(BigDecimal win) {
        this.win = win;
    }
}
