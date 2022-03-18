package com.caipiao.task.server.service;

public interface Tc7xcTaskService {
	/**
     * 体彩七星彩 - 预开数据
     */
    void addTc7xcPrevSg();

    /**
     * 生成赛果并添加到数据库
     */
    void addTc7xcSg();

    /**
     * 生成体彩七星彩免费推荐数据
     */
    void addTc7xcRecommend();

    /**
     * 生成体彩七星彩公式杀号
     */
    void addTc7xcGssh();
}
