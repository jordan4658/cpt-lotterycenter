package com.caipiao.live.common.enums;

/**
 * 玩法类型枚举
 * 
 */
public enum AlgorithmEnum {

	BIGORSMALL("大小", "1"),
	
	SINGLEANDDOUBLE("单双", "2"),
	
	FIVEELEMENTS("五行", "3");

	private String name;
	private String code;

	private AlgorithmEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
