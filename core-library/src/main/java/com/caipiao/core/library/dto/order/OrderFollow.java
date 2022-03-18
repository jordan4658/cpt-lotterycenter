package com.caipiao.core.library.dto.order;

import java.math.BigDecimal;

public class OrderFollow {

    private Integer godPushId; // 大神推单记录id
    private Integer userId; // 跟单用户id
    private BigDecimal orderAmount; // 跟单金额
    private String source; // 来源

    public Integer getGodPushId() {
        return godPushId;
    }

    public void setGodPushId(Integer godPushId) {
        this.godPushId = godPushId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
