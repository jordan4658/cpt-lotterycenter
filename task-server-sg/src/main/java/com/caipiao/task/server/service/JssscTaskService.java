package com.caipiao.task.server.service;

import java.util.Date;

public interface JssscTaskService {
	/**
     * 极速时时彩 - 预开数据
     */
    void addJssscPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addJssscSg();

    /**
     * 生成极速时时彩免费推荐数据
     */
    void addJssscRecommend();

    /**
     * 生成极速时时彩公式杀号
     */
    void addJssscGssh();
    
    /**
     * 根据上期期号生成下期期号
     * */
    public String createNextIssue(String issue);
    
    /**
     * 获取下一期官方开奖时间
     * */
    public Date nextIssueTime(Date lastTime);

    /**
     * 统计赛果大小单双
     * */
    public void dxdsSgCount();

    /**
     * 统计德州时时彩赛果大小单双,最近两个月历史数据
     */
    void dxdsSgCountLatelyTwoMonth();
}
