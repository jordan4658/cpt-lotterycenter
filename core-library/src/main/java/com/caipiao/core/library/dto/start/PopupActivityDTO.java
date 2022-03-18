package com.caipiao.core.library.dto.start;

import com.mapper.domain.PopupActivity;

/**
 * @author lzy
 * @create 2018-06-26 17:58
 **/
public class PopupActivityDTO {

    private Integer id;

    /**
     * 说明: app的id
     */
    private Integer appId;

    /**
     * 说明: app版本的id
     */
    private Integer versionId;

    /**
     * 说明: 精彩活动id
     */
    private Integer activityId;

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

    public static PopupActivity getPopupActivity(PopupActivityDTO popupActivityDTO) {
        if (popupActivityDTO != null) {
            PopupActivity popupActivity = new PopupActivity();
            popupActivity.setId(popupActivityDTO.getId());
            popupActivity.setAppId(popupActivityDTO.getAppId());
            popupActivity.setVersionId(popupActivityDTO.getVersionId());
            popupActivity.setActivityId(popupActivityDTO.getActivityId());
            popupActivity.setTitle(popupActivityDTO.getTitle());
            popupActivity.setIssueTime(popupActivityDTO.getIssueTime());
            popupActivity.setStatus(popupActivityDTO.getStatus());
            popupActivity.setMessage(popupActivityDTO.getMessage());
            return popupActivity;
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

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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
}
