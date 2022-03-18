package com.caipiao.task.server.service;

public interface FivebjpksTaskService {
	/**
     * 五分北京PK10 - 预期数据
     */
    void addFivebjpksPrevSg();

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    void addFivebjpksSg();

    /**
     * 统计每天的赛果并添加到数据库
     */
    void addFivebjpksSgCount();

    /**
     * 生成五分北京PK10免费推荐数据
     */
    void addFivebjpksRecommend();

    /**
     * 添加每期的公式杀号
     */
    void addFivebjpksKillNumber();
}
