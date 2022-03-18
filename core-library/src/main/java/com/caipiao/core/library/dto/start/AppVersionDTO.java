package com.caipiao.core.library.dto.start;

import com.mapper.domain.AppVersion;

/**
 * @author lzy
 * @create 2018-06-15 18:04
 **/
public class AppVersionDTO {

    private Integer id;

    /**
     * 说明: app的id
     */
    private Integer appId;

    /**
     * 说明: 版本号
     */
    private String number;

    /**
     * 说明: 包大小
     */
    private Double size;

    private String downUrl;

    private Integer noticeStatus;

    /**
     * 说明: 更新内容
     */
    private String message;


    public static AppVersion getAppVersion(AppVersionDTO appVersionDTO) {
        if (appVersionDTO != null){
            AppVersion appVersion = new AppVersion();
            appVersion.setId(appVersionDTO.getId());
            appVersion.setAppId(appVersionDTO.getAppId());
            appVersion.setNoticeStatus(appVersionDTO.getNoticeStatus());
            appVersion.setMessage(appVersionDTO.getMessage());
            appVersion.setNumber(appVersionDTO.getNumber());
            appVersion.setSize(appVersionDTO.getSize());
            appVersion.setDownUrl(appVersionDTO.getDownUrl());
            return appVersion;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
}
