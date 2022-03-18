package com.caipiao.core.library.enums;

public enum CurEnum {
	CNY("CNY","人民币"), 
	
	USD("USD","美元"), 
	
	HKD("HKD","港元"), 
	
	KER("KER","韩元"), 
	
	SGD("SGD","新加坡币"), 
	
	MYR("MYR","马来西亚币"),
	
	JPY("JPY","日元"), 
	
	BTC("BTC","比特币"), 
	
	THB("THB","泰珠"), 
	
	IDR("IDR","印尼盾"), 
	
	VND("VND","越南盾"), 
	
	EUR("EUR","欧元"), 
	
	INR("INR","印度卢比号");
	
	private String keyName;
	private String keyValue;
	
	private CurEnum(String keyName, String keyValue) {
		this.keyName = keyName;
		this.keyValue = keyValue;
	}
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
}
