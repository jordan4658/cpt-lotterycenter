package com.caipiao.live.common.enums;

/**
 * WEB界面六合彩心水推荐列表类型枚举
 * 
 */
public enum WebParamEnum {

	UID("uid", "用户ID"),
	
	PAGESIZE("pageSize","每页数量"),
	
	PAGENUM("pageNo","页码"),
	
	SEARCHNAME("name","搜索关键字"),

	ID("id", "主键");

	private String name;
	private String code;

	private WebParamEnum(String name, String code) {
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
