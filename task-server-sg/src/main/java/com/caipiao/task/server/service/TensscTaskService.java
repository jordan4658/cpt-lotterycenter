package com.caipiao.task.server.service;

public interface TensscTaskService {
	/**
     * 十分时时彩 - 预开数据
     */
    void addTensscPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addTensscSg();

    /**
     * 生成十分时时彩免费推荐数据
     */
    void addTensscRecommend();

    /**
     * 生成十分时时彩公式杀号
     */
    void addTensscGssh();

    /**
     * 统计十分时时彩赛果大小单双
     */
    void dxdsSgCount();

    /**
     * 统计十分时时彩赛果大小单双,最近两个月历史数据
     */
    void dxdsSgCountLatelyTwoMonth();
}
