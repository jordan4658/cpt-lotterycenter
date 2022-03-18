package com.caipiao.task.server.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 极光推送配置
 */
@Configuration
@ConfigurationProperties(prefix = "jg")
public class JPushProperties {
	
	//#极光IOS正式和测试密钥
    private String ios_total;
    private String ios_appkey;
    private String ios_secret;
    
//    //#极光ANDROID测试密钥
//    private String test_android_total;
//    private String test_android_appkey;
//    private String test_android_secret;
    
    //#极光ANDROID正式密钥
    private String android_total;
    private String android_appkey;
    private String android_secret;
    
  //#配置IOS是否启用正式环境
    private Boolean ios_apnsproduction;
//    //#配置安卓是否启用正式环境
//    private Boolean android_apnsproduction;

    public JPushProperties(){}
    
    

	public String getAndroid_total() {
		return android_total;
	}

	public void setAndroid_total(String android_total) {
		this.android_total = android_total;
	}

	public String getAndroid_appkey() {
		return android_appkey;
	}

	public void setAndroid_appkey(String android_appkey) {
		this.android_appkey = android_appkey;
	}

	public String getAndroid_secret() {
		return android_secret;
	}

	public void setAndroid_secret(String android_secret) {
		this.android_secret = android_secret;
	}



	public String getIos_total() {
		return ios_total;
	}

	public void setIos_total(String ios_total) {
		this.ios_total = ios_total;
	}

	public String getIos_appkey() {
		return ios_appkey;
	}

	public void setIos_appkey(String ios_appkey) {
		this.ios_appkey = ios_appkey;
	}

	public String getIos_secret() {
		return ios_secret;
	}

	public void setIos_secret(String ios_secret) {
		this.ios_secret = ios_secret;
	}

	public Boolean getIos_apnsproduction() {
		return ios_apnsproduction;
	}

	public void setIos_apnsproduction(Boolean ios_apnsproduction) {
		this.ios_apnsproduction = ios_apnsproduction;
	}
    
}
