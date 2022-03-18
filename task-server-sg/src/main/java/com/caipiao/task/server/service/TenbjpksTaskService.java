package com.caipiao.task.server.service;

public interface TenbjpksTaskService {
	/**
     * 十分PK10 - 预期数据
     */
    void addTenbjpksPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addTenbjpksSg();

    /**
     * 统计每天的赛果并添加到数据库
     */
    void addTenbjpksSgCount();

    /**
     * 生成十分北京PK10免费推荐数据
     */
    void addTenbjpksRecommend();

    /**
     * 添加每期的公式杀号
     */
    void addTenbjpksKillNumber();
}
