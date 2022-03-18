package com.caipiao.core.library.dto.order;

import java.util.List;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/12/29 11:29
 */
public class OrderBackDTO {
    private List<Integer> orderBetIds;
    private Integer userId;

    public List<Integer> getOrderBetIds() {
        return orderBetIds;
    }

    public void setOrderBetIds(List<Integer> orderBetIds) {
        this.orderBetIds = orderBetIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
