package com.caipiao.core.library.dto.start;

/**
 * @author lzy
 * @create 2018-06-13 17:12
 **/
public class SystemInfoSetDTO {

    private String astrictIpLoginStatus;

    private String astrictIpGroup;

    private String hintRefreshTime;

    private String hintVoiceStatus;

    public String getAstrictIpLoginStatus() {
        return astrictIpLoginStatus;
    }

    public void setAstrictIpLoginStatus(String astrictIpLoginStatus) {
        this.astrictIpLoginStatus = astrictIpLoginStatus;
    }

    public String getAstrictIpGroup() {
        return astrictIpGroup;
    }

    public void setAstrictIpGroup(String astrictIpGroup) {
        this.astrictIpGroup = astrictIpGroup;
    }

    public String getHintRefreshTime() {
        return hintRefreshTime;
    }

    public void setHintRefreshTime(String hintRefreshTime) {
        this.hintRefreshTime = hintRefreshTime;
    }

    public String getHintVoiceStatus() {
        return hintVoiceStatus;
    }

    public void setHintVoiceStatus(String hintVoiceStatus) {
        this.hintVoiceStatus = hintVoiceStatus;
    }
}
