package com.caipiao.live.common.model.dto.lottery;

import java.util.List;

public class LotterySetDTO {

    private String cateName;
    private String cateID;
    private List<LotteryDTO> lotterys;

    private List<LotteryPlayDTO> playlist;

    public List<LotteryPlayDTO> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<LotteryPlayDTO> playlist) {
        this.playlist = playlist;
    }

    private String intro;

    public String getCateID() {
        return cateID;
    }

    public void setCateID(String cateID) {
        this.cateID = cateID;
    }

    public List<LotteryDTO> getLotterys() {
        return lotterys;
    }

    public void setLotterys(List<LotteryDTO> lotterys) {
        this.lotterys = lotterys;
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

}
