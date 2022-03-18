package com.caipiao.task.server.service;

public interface TcDltTaskService {
	/**
     * 体彩大乐透 - 预开数据
     */
    void addTcDltPrevSg();

    /**
     * 生成赛果并添加到数据库
     */
    void addTcDltSg();

    /**
     * 生成体彩大乐透免费推荐数据
     */
    void addTcDltRecommend();

    /**
     * 生成体彩大乐透公式杀号
     */
    void addTcDltGssh();
}
