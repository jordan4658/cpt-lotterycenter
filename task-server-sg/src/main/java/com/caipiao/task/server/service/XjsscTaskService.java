package com.caipiao.task.server.service;

/**
 * 重庆时时彩定时任务Service
 */
public interface XjsscTaskService {

    /**
     * 新疆时时彩 - 预开数据
     */
    void addXjsscPrevSg();

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    void addXjsscSg();

    /**
     * 生成重庆时时彩免费推荐数据
     */
    void addXjsscRecommend();

    /**
     * 生成重庆时时彩公式杀号
     */
    void addXjsscGssh();

    /**
     * 新疆时时彩切换期号发消息
     */
    void sendMessageChangeIssue();

}
