package com.caipiao.core.library.vo.start;

import java.util.List;

/**
 * @author lzy
 * @create 2018-06-08 18:09
 **/
public class PopupActivityVO {

    private Integer id;

    /**
     * 说明: app的id
     */
    private Integer appId;

    private String appName;

    /**
     * 说明: app版本的id
     */
    private Integer versionId;

    private String versionNumber;

    /**
     * 说明: 精彩活动id
     */
    private Integer activityId;

    private String activityName;

    /**
     * 说明: 标题
     */
    private String title;

    /**
     * 说明: 状态:0,关闭;1,开启
     */
    private Integer status;

    /**
     * 说明: 内容
     */
    private String message;

    /**>
     * 说明: 发布时间
     */
    private String issueTime;

    private List<AppVO> appList;

    private List<AppVersionVO> appVersionList;

    private List<ActivityVO> activityList;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public List<AppVO> getAppList() {
        return appList;
    }

    public void setAppList(List<AppVO> appList) {
        this.appList = appList;
    }

    public List<AppVersionVO> getAppVersionList() {
        return appVersionList;
    }

    public void setAppVersionList(List<AppVersionVO> appVersionList) {
        this.appVersionList = appVersionList;
    }

    public List<ActivityVO> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityVO> activityList) {
        this.activityList = activityList;
    }
}
