package com.caipiao.live.common.model.dto.lottery;

import com.caipiao.live.common.mybatis.entity.Lottery;

import java.util.List;

public class LotteryDTO extends Lottery {

    private Long openTime;
    private String cateName;
    private List<LotteryDTO> lotterys;
    private String intro;
    private Integer isNew;

    public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
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

	public Long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Long openTime) {
        this.openTime = openTime;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

}
