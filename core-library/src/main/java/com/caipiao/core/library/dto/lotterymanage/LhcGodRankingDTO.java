package com.caipiao.core.library.dto.lotterymanage;

/**
 * @author lzy
 * @create 2018-08-13 17:39
 **/
public class LhcGodRankingDTO  {

	private String id;
    private String winsNumber;
    private String continuousNumber;
    private String winRate,fansNumber;
    private String fakeFansNumber;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWinsNumber() {
		return winsNumber;
	}
	public void setWinsNumber(String winsNumber) {
		this.winsNumber = winsNumber;
	}
	public String getContinuousNumber() {
		return continuousNumber;
	}
	public void setContinuousNumber(String continuousNumber) {
		this.continuousNumber = continuousNumber;
	}
	public String getWinRate() {
		return winRate;
	}
	public void setWinRate(String winRate) {
		this.winRate = winRate;
	}
	public String getFansNumber() {
		return fansNumber;
	}
	public void setFansNumber(String fansNumber) {
		this.fansNumber = fansNumber;
	}
	public String getFakeFansNumber() {
		return fakeFansNumber;
	}
	public void setFakeFansNumber(String fakeFansNumber) {
		this.fakeFansNumber = fakeFansNumber;
	}

    
}
