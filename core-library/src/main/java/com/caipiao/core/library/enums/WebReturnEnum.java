package com.caipiao.core.library.enums;

/**
 * WEB界面六合彩心水推荐列表类型枚舉
 * 
 */
public enum WebReturnEnum {

	LIST("list", "数据集合"),
	
	RESULT("result", "结果"),

	TOTALNUM("totalNum", "数量");

	private String enName;
	private String cnName;

	private WebReturnEnum(String enName, String cnName) {
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
