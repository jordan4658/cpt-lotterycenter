package com.caipiao.task.server.service;

public interface FivesscTaskService {
	/**
     * 五分时时彩 - 预开数据
     */
    void addFivesscPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addFivesscSg();

    /**
     * 生成五分时时彩免费推荐数据
     */
    void addFivesscRecommend();

    /**
     * 生成五分时时彩公式杀号
     */
    void addFivesscGssh();

    /**
     * 统计五分时时彩赛果大小单双
     */
    void dxdsSgCount();

    /**
     * 统计五分时时彩赛果大小单双,最近两个月历史数据
     */
    void dxdsSgCountLatelyTwoMonth();
}
