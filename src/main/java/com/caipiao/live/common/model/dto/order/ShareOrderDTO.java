package com.caipiao.live.common.model.dto.order;

public class ShareOrderDTO {
    private Integer orderBetId; // 投注 id
    private Integer userId; // 跟单用户id
    private Integer betCount; // 投注金额
    private String source; // 来源
    private Integer gid; // 聊天室 id

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getOrderBetId() {
        return orderBetId;
    }

    public void setOrderBetId(Integer orderBetId) {
        this.orderBetId = orderBetId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBetCount() {
        return betCount;
    }

    public void setBetCount(Integer betCount) {
        this.betCount = betCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
