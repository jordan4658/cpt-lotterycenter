package com.caipiao.core.library.vo.lotterymanage;

import com.mapper.domain.LotteryPlay;

import java.util.List;

public class LotteryPlayDTO extends LotteryPlay {
    
    private Integer sumLevel;

    private List<LotteryPlayDTO> children;
    private List<LhcPlayInfoDTO> playinfos;
    

    public List<LhcPlayInfoDTO> getPlayinfos() {
		return playinfos;
	}

	public void setPlayinfos(List<LhcPlayInfoDTO> playinfos) {
		this.playinfos = playinfos;
	}

	public Integer getSumLevel() {
        return sumLevel;
    }

    public void setSumLevel(Integer sumLevel) {
        this.sumLevel = sumLevel;
    }

    public List<LotteryPlayDTO> getChildren() {
        return children;
    }

    public void setChildren(List<LotteryPlayDTO> children) {
        this.children = children;
    }
}
