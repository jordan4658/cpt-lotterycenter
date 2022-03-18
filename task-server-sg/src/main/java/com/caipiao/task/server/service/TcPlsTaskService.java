package com.caipiao.task.server.service;

public interface TcPlsTaskService {
	/**
     * 体彩排列三 - 预开数据
     */
    void addTcPlsPrevSg();

    /**
     * 生成赛果并添加到数据库
     */
    void addTcPlsSg();

    /**
     * 生成体彩排列三免费推荐数据
     */
    void addTcPlsRecommend();

    /**
     * 生成体彩排列三公式杀号
     */
    void addTcPlsGssh();
}
