package com.caipiao.live.common.model.dto.result;

import java.util.List;

public class BeadWayColorReturn {

    //红总数
    private Integer redTotal;
    //蓝总数
    private Integer blueTotal;
    //绿总数
    private Integer greenTotal;
    //结果组
    private List<String> list;

    public Integer getRedTotal() {
        return redTotal;
    }

    public void setRedTotal(Integer redTotal) {
        this.redTotal = redTotal;
    }

    public Integer getBlueTotal() {
        return blueTotal;
    }

    public void setBlueTotal(Integer blueTotal) {
        this.blueTotal = blueTotal;
    }

    public Integer getGreenTotal() {
        return greenTotal;
    }

    public void setGreenTotal(Integer greenTotal) {
        this.greenTotal = greenTotal;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

}
