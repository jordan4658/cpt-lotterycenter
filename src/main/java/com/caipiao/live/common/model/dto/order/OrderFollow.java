package com.caipiao.live.common.model.dto.order;

import java.math.BigDecimal;
import java.util.List;

public class OrderFollow {

    private Integer godPushId; // 大神推单记录id
    private Integer userId; // 跟单用户id
    private BigDecimal orderAmount; // 跟单金额
    private String source; // 来源
    private Integer gid; //聊天室ID
    private List<Integer> orders; //投注id;
    private Integer orderId;////订单id
    //标识号 (主播id)
    private Long familymemid;
    //直播间id
    private Long roomId;

    public Long getFamilymemid() {
        return familymemid;
    }

    public void setFamilymemid(Long familymemid) {
        this.familymemid = familymemid;
    }

    public List<Integer> getOrders() {
        return orders;
    }

    public void setOrders(List<Integer> orders) {
        this.orders = orders;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

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
