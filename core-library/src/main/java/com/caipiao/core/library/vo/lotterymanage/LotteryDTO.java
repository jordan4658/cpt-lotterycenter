package com.caipiao.core.library.vo.lotterymanage;

import java.util.List;

import com.mapper.domain.Lottery;

public class LotteryDTO extends Lottery {

    private Long openTime;
    private String cateName;
    private List<LotteryDTO> lotterys;
    private String intro;
    

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
