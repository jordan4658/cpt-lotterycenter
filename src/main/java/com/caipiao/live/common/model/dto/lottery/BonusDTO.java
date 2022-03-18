package com.caipiao.live.common.model.dto.lottery;

import com.caipiao.live.common.mybatis.entity.Bonus;

public class BonusDTO extends Bonus {
    private String cateName;
    private String lotteryCateName;
    private String typeName;
    private String planName;
    private String playName;

    private String playId1;
    private String playId2;
    private String playId3;
    private String lotteryName;

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

    public String getPlayId1() {
        return playId1;
    }

    public void setPlayId1(String playId1) {
        this.playId1 = playId1;
    }

    public String getPlayId2() {
        return playId2;
    }

    public void setPlayId2(String playId2) {
        this.playId2 = playId2;
    }

    public String getPlayId3() {
        return playId3;
    }

    public void setPlayId3(String playId3) {
        this.playId3 = playId3;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }
}
