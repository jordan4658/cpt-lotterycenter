package com.caipiao.task.server.service;

public interface JsbjpksTaskService {
	/**
     * 极速北京PK10 - 预期数据
     */
    void addJsbjpksPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addJsbjpksSg();

    /**
     * 统计每天的赛果并添加到数据库
     */
    void addJsbjpksSgCount();

    /**
     * 生成极速北京PK10免费推荐数据
     */
    void addJsbjpksRecommend();

    /**
     * 添加每期的公式杀号
     */
    void addJsbjpksKillNumber();
}
