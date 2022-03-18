package com.caipiao.core.library.vo.lotterymanage;

import com.mapper.domain.Bonus;

public class BonusDTO extends Bonus {
    private String cateName;
    private String lotteryCateName;
    private String typeName;
    private String planName;
    private String playName;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getLotteryCateName() {
        return lotteryCateName;
    }

    public void setLotteryCateName(String lotteryCateName) {
        this.lotteryCateName = lotteryCateName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }
}
