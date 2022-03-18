package com.caipiao.core.library.dto.start;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.AppNotice;

/**
 * @author lzy
 * @create 2018-06-13 11:33
 **/
public class AppNoticeDTO {

    private Integer id;

    /**
     * 说明: 分类id
     */
    private Integer categoryId;

    /**
     * 说明: 标题
     */
    private String title;

    /**
     * 说明: 作者
     */
    private String author;

    /**
     * 说明: 发布时间
     */
    private String issueTime;

    /**
     * 说明: 有效开始时间
     */
    private String startDate;

    /**
     * 说明: 有效结束时间
     */
    private String endDate;

    /**
     * 说明: 是否最新:0否;1是
     */
    private Integer newest;

    /**
     * 说明: 状态:0,关闭活动;1,开启活动
     */
    private Integer status;

    /**
     * 说明: 简介
     */
    private String intro;

    /**
     * 说明: 内容
     */
    private String message;

    /**
     * 说明: 滚动状态:0,不滚动;1,滚动
     */
    private Integer rollStatus;
    private Integer popup;

    public Integer getPopup() {
		return popup;
	}

	public void setPopup(Integer popup) {
		this.popup = popup;
	}

	public static AppNotice getAppNotice(AppNoticeDTO appNoticeDTO) {
        if (appNoticeDTO == null) {
            return null;
        }
        AppNotice appNotice = new AppNotice();
        appNotice.setId(appNoticeDTO.getId());
        appNotice.setCategoryId(appNoticeDTO.getCategoryId());
        appNotice.setTitle(appNoticeDTO.getTitle());
        appNotice.setAuthor(appNoticeDTO.getAuthor());
        appNotice.setIssueTime(appNoticeDTO.getIssueTime());
        appNotice.setStartDate(appNoticeDTO.getStartDate());
        appNotice.setEndDate(appNoticeDTO.getEndDate());
        appNotice.setNewest(appNoticeDTO.getNewest());
        appNotice.setStatus(appNoticeDTO.getStatus());
        appNotice.setIntro(appNoticeDTO.getIntro());
        appNotice.setMessage(appNoticeDTO.getMessage());
        appNotice.setRollStatus(appNoticeDTO.getRollStatus());
        appNotice.setPopup(appNoticeDTO.getPopup());
        if (appNoticeDTO.getId() == null) {
            appNotice.setCreateTime(TimeHelper.date());
        }
        return appNotice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getNewest() {
        return newest;
    }

    public void setNewest(Integer newest) {
        this.newest = newest;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRollStatus() {
        return rollStatus;
    }

    public void setRollStatus(Integer rollStatus) {
        this.rollStatus = rollStatus;
    }
}
