package com.caipiao.core.library.enums;

public enum TaskStatusEnum {
	/**
	 * 初始状态
	 * 
	 * */
	INIT("init",0), 
	
	/**
	 * 执行中
	 * 
	 * */
	EXECUTION("execution",10), 
	
	/**
	 * 执行成功
	 * 
	 * */
	SUCCESS("success",20), 
	
	/**
	 * 执行失败
	 * 
	 * */
	FAIL("fail",30);
	
	private String keyName;
	private Integer keyValue;
	
	private TaskStatusEnum(String keyName, Integer keyValue) {
		this.keyName = keyName;
		this.keyValue = keyValue;
	}
	
	public String getKeyName() {
		return keyName;
	}
	
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Integer getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(Integer keyValue) {
		this.keyValue = keyValue;
	}

}
