package com.caipiao.task.server.service;

public interface TcPlwTaskService {
	/**
     * 体彩排列五 - 预开数据
     */
    void addTcPlwPrevSg();

    /**
     * 生成赛果并添加到数据库
     */
    void addTcPlwSg();

    /**
     * 生成体彩排列五免费推荐数据
     */
    void addTcPlwRecommend();

    /**
     * 生成体彩排列五公式杀号
     */
    void addTcPlwGssh();
}
