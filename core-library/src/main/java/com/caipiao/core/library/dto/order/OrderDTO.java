package com.caipiao.core.library.dto.order;

import com.mapper.domain.OrderBetRecord;
import com.mapper.domain.OrderRecord;

import java.util.List;

public class OrderDTO extends OrderRecord {

    private List<OrderBetRecord> orderBetList;

    public List<OrderBetRecord> getOrderBetList() {
        return orderBetList;
    }

    public void setOrderBetList(List<OrderBetRecord> orderBetList) {
        this.orderBetList = orderBetList;
    }

}
