package com.caipiao.task.server.service;

/**
 * 重庆时时彩定时任务Service
 */
public interface CqsscTaskService {

    /**
     * 生成重庆时时彩预开数据
     */
    void addCqsscPrevSg();

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    void addCqsscSg();

    /**
     * 生成重庆时时彩免费推荐数据
     */
    void addCqsscRecommend();

    /**
     * 生成重庆时时彩公式杀号
     */
    void addCqsscGssh();

    /**
     * 重庆时时彩切换期号发消息
     */
    void sendMessageChangeIssue();

}
