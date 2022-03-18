package com.caipiao.live.common.model.request;

import java.util.ArrayList;
import java.util.List;

public class XinjiangshishicaiRequest {

    private List<Long> selected = new ArrayList<>();
    private Long roomid;
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<Long> getSelected() {
        return selected;
    }

    public void setSelected(List<Long> selected) {
        this.selected = selected;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

}
