package com.caipiao.live.common.model.dto.lottery;

import java.util.List;

public class LotteryInfo {

    private String cateName;
    private String cateID;
    private List<LotteryAllDTO> lotterys;

    private String intro;
    /**
     * 说明: 是否开售
     */
    private Integer isWork;
    /**
     * 排序字段
     */
    private Integer sort;


    public String getCateID() {
        return cateID;
    }

    public void setCateID(String cateID) {
        this.cateID = cateID;
    }


    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<LotteryAllDTO> getLotterys() {
        return lotterys;
    }

    public void setLotterys(List<LotteryAllDTO> lotterys) {
        this.lotterys = lotterys;
    }

    public Integer getIsWork() {
        return isWork;
    }

    public void setIsWork(Integer isWork) {
        this.isWork = isWork;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "LotteryInfo{" +
                "cateName='" + cateName + '\'' +
                ", cateID='" + cateID + '\'' +
                ", lotterys=" + lotterys +
                ", intro='" + intro + '\'' +
                ", isWork=" + isWork +
                ", sort=" + sort +
                '}';
    }
}
