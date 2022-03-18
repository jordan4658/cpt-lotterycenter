package com.caipiao.core.library.vo.start;

import java.util.List;

/**
 * @author lzy
 * @create 2018-06-11 13:41
 **/
public class SlideshowVO {

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
     * 说明: 名称
     */
    private String name;

    /**
     * 说明: 类型:1,精彩活动;2,App;3,公告
     */
    private Integer type;

    /**
     * 说明: 跳转地址,根据type对应不同的表id
     */
    private Integer jumpUrl;

    /**
     * 说明: 图片地址
     */
    private String photoUrl;

    /**
     * 说明: 排序
     */
    private Integer sort;

    /**
     * 说明: 发布时间
     */
    private String issueTime;

    private List<AppVO> appList;

    private List<AppVersionVO> appVersionList;

    private List<ActivityVO> activityList;

    private List<AppNoticeVO> appNoticeList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(Integer jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public List<AppNoticeVO> getAppNoticeList() {
        return appNoticeList;
    }

    public void setAppNoticeList(List<AppNoticeVO> appNoticeList) {
        this.appNoticeList = appNoticeList;
    }
}
