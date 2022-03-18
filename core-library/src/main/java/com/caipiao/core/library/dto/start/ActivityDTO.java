package com.caipiao.core.library.dto.start;

import com.caipiao.core.library.tool.StringUtils;
import com.mapper.domain.Activity;

/**
 * @author lzy
 * @create 2018-06-26 11:31
 **/
public class ActivityDTO {

    private Integer id;

    /**
     * 说明: 标题
     */
    private String title;

    /**
     * 说明: 类型:0,Url;1,App
     */
    private Integer type;

    /**
     * 说明: 跳转地址
     */
    private String jumpUrl;

    /**
     * 说明: 图片地址
     */
    private String photoUrl;

    /**
     * 说明: 发布时间
     */
    private String issueTime;

    /**
     * 说明: 有效开始时间
     */
    private String startTime;

    /**
     * 说明: 有效结束时间
     */
    private String endTime;

    /**
     * 说明: 排序
     */
    private Integer sort;

    /**
     * 说明: 0,关闭;1,开启
     */
    private Integer status;

    /**
     * 说明: 0,隐藏;1,显示
     */
    private Integer showStatus;

    public Integer getId() {
        return id;
    }

    public static Activity getActivity(ActivityDTO activityDTO) {
        if (activityDTO != null) {
            Activity activity = new Activity();
            activity.setId(activityDTO.getId());
            activity.setTitle(activityDTO.getTitle());
            activity.setType(activityDTO.getType());
            activity.setJumpUrl(activityDTO.getJumpUrl());
            activity.setPhotoUrl(activityDTO.getPhotoUrl());
            activity.setIssueTime(activityDTO.getIssueTime());
            activity.setStartTime(StringUtils.isNotBlank(activityDTO.getStartTime()) ? activityDTO.getStartTime() + " 00:00:00" : null);
            activity.setEndTime(StringUtils.isNotBlank(activityDTO.getEndTime()) ? activityDTO.getEndTime() + " 23:59:59" : null);
            activity.setSort(activityDTO.getSort());
            activity.setStatus(activityDTO.getStatus());
            activity.setShowStatus(activityDTO.getShowStatus());
            return activity;
        }
        return null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }
}
