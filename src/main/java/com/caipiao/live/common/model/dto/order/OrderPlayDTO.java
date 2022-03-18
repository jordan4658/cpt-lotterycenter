package com.caipiao.live.common.model.dto.order;

import java.util.List;

public class OrderPlayDTO {

    private String playName;

    private List<AppendPlanDTO> appendInfo;

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public List<AppendPlanDTO> getAppendInfo() {
        return appendInfo;
    }

    public void setAppendInfo(List<AppendPlanDTO> appendInfo) {
        this.appendInfo = appendInfo;
    }
}
