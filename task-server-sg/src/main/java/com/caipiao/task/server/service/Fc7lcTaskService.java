package com.caipiao.task.server.service;

public interface Fc7lcTaskService {
	/**
     * 福彩七乐彩 - 预开数据
     */
    void addFc7lcPrevSg();

    /**
     * 生成赛果并添加到数据库
     */
    void addFc7lcSg();

    /**
     * 生成福彩七乐彩免费推荐数据
     */
    void addFc7lcRecommend();

    /**
     * 生成福彩七乐彩公式杀号
     */
    void addFc7lcGssh();
}
