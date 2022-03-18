package com.caipiao.live.common.model.dto.result;

import java.util.List;

public class BeadWayFiveReturn {
    //金个数
    private Integer goldTotal;
    //木个数
    private Integer woodTotal;
    //水个数
    private Integer waterTotal;
    //火个数
    private Integer fireTotal;
    //土个数
    private Integer soilTotal;

    public Integer getGoldTotal() {
        return goldTotal;
    }

    public void setGoldTotal(Integer goldTotal) {
        this.goldTotal = goldTotal;
    }

    public Integer getWoodTotal() {
        return woodTotal;
    }

    public void setWoodTotal(Integer woodTotal) {
        this.woodTotal = woodTotal;
    }

    public Integer getWaterTotal() {
        return waterTotal;
    }

    public void setWaterTotal(Integer waterTotal) {
        this.waterTotal = waterTotal;
    }

    public Integer getFireTotal() {
        return fireTotal;
    }

    public void setFireTotal(Integer fireTotal) {
        this.fireTotal = fireTotal;
    }

    public Integer getSoilTotal() {
        return soilTotal;
    }

    public void setSoilTotal(Integer soilTotal) {
        this.soilTotal = soilTotal;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    private List<String> list;
}
