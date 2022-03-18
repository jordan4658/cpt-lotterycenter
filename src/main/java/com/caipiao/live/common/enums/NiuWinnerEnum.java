package com.caipiao.live.common.enums;

/**
 * WEB界面六合彩心水推荐列表类型枚举
 * 
 */
public enum NiuWinnerEnum {

	BANKER("banker", "庄家"),

	IDLERONE("idlerOne", "闲一"),

	IDLERTWO("idlerTwo", "闲二"), 
	
	IDLERTHREE("idlerThree", "闲三"),
	
	IDLERFOUR("idlerFour", "闲四"),
	
	IDLERFIVE("idlerFive", "闲五");

	private String enName;
	private String cnName;

	private NiuWinnerEnum(String enName, String cnName) {
		this.enName = enName;
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

}
