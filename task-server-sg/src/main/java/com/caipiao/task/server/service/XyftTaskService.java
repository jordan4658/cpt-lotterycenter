package com.caipiao.task.server.service;

/**
 * 幸运飞艇 - 接口
 */
public interface XyftTaskService {

    /**
     * 幸运飞艇 - 预期数据
     */
    void addXyftPrevSg();

    /**
     * 从第三方接口获取幸运飞艇赛果并添加到数据库
     */
    void addXyftSg();

    /**
     * 统计每天的幸运飞艇赛果并添加到数据库
     */
    void addXyftSgCount();

    /**
     * 生成幸运飞艇免费推荐数据
     */
    void addXyftRecommend();

    /**
     * 添加幸运飞艇每期的公式杀号
     */
    void addXyftKillNumber();

    /**
     * 幸运飞艇切换期号发消息
     */
    void sendMessageChangeIssue();

}
