package com.caipiao.core.library.enums;

/**
 * WEB界面六合彩心水推荐列表类型枚舉
 * 
 */
public enum FourtyEightEnum {

	ONE("01", "1020"),

	TWO("02", "1040"),

	THREE("03", "1100"),

	FOUR("04", "1120")
	
	
	
	
	
	
	
	
	
	;

	private String issueId;
	private String issueTime;

	private FourtyEightEnum(String issueId, String issueTime) {
		this.issueId = issueId;
		this.issueTime = issueTime;
	}
	
	public static String getIssueId(String issueTime) {
		for (FourtyEightEnum fourtyEightEnum : FourtyEightEnum.values()) {
			if(fourtyEightEnum.getIssueTime().equals(issueTime)) {
				return fourtyEightEnum.getIssueId();
			}
		}
		return null;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(String issueTime) {
		this.issueTime = issueTime;
	}

}
