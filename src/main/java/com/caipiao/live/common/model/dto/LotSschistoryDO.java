package com.caipiao.live.common.model.dto;

import java.util.ArrayList;
import java.util.List;

public class LotSschistoryDO {

    List<String> luckynums = new ArrayList<String>();

    private String lotcode;

    public String getLotcode() {
        return lotcode;
    }

    public void setLotcode(String lotcode) {
        this.lotcode = lotcode;
    }

    public List<String> getLuckynums() {
        return luckynums;
    }

    public void setLuckynums(List<String> luckynums) {
        this.luckynums = luckynums;
    }
}
