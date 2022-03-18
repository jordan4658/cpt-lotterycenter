package com.caipiao.task.server.service;

public interface BjpksTaskService {

    /**
     * 北京PK10 - 预期数据
     */
    void addBjpksPrevSg();

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    void addBjpksSg();

    /**
     * 统计每天的赛果并添加到数据库
     */
    void addBjpksSgCount();

    /**
     * 生成北京PK10免费推荐数据
     */
    void addBjpksRecommend();

    /**
     * 添加每期的公式杀号
     */
    void addBjpksKillNumber();

    /**
     * 北京PK10切换期号发消息
     */
    void sendMessageChangeIssue();

}
