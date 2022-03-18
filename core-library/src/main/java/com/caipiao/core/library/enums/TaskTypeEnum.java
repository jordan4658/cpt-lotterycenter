package com.caipiao.core.library.enums;

public enum TaskTypeEnum {
	/**
	 * 长龙推送
	 * 
	 * */
	DRAGONPUSH("dragonPush",1);
	
	private String keyName;
	private Integer keyValue;
	
	private TaskTypeEnum(String keyName, Integer keyValue) {
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
