package com.caipiao.task.server.service;

public interface TjsscTaskService {
	/**
     * 天津时时彩 - 预开数据
     */
    void addTjsscPrevSg();

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    void addTjsscSg();

    /**
     * 生成天津时时彩免费推荐数据
     */
    void addTjsscRecommend();

    /**
     * 生成天津时时彩公式杀号
     */
    void addTjsscGssh();

    /**
     * 天津时时彩切换期号发消息
     */
    void sendMessageChangeIssue();
}
