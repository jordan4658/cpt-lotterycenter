package com.caipiao.core.library.vo.start;

/**
 * @author lzy
 * @create 2018-06-11 18:16
 **/
public class AppVersionVO {

    private Integer id;

    /**
     * 说明: app的id
     */
    private Integer appId;

    private String appName;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
}
