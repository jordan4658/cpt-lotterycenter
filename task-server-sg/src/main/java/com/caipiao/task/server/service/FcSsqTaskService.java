package com.caipiao.task.server.service;

public interface FcSsqTaskService {
	/**
     * 福彩双色球 - 预开数据
     */
    void addFcSsqPrevSg();

    /**
     * 生成赛果并添加到数据库
     */
    void addFcSsqSg();

    /**
     * 生成福彩双色球免费推荐数据
     */
    void addFcSsqRecommend();

    /**
     * 生成福彩双色球公式杀号
     */
    void addFcSsqGssh();
}
