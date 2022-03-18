package com.caipiao.live.common.enums;

/**
 * WEB界面六合彩心水推荐列表类型枚举
 * 
 */
public enum MemberReturnEnum {

	ACCOUNT("account", "用户名"),
	
	NICKNAME("nickname", "昵称"),
	
	HEADS("heads", "头像"),

	FANSNUM("fansNum", "粉丝数量");

	private String enName;
	private String cnName;

	private MemberReturnEnum(String enName, String cnName) {
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
