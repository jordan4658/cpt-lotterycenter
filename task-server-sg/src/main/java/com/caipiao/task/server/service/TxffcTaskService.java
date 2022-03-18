package com.caipiao.task.server.service;

/**
 * 腾讯分分彩定时任务Service
 */
public interface TxffcTaskService {

    /**
     * 生成腾讯分分彩预开数据
     */
    void addTxffcPrevSg();

    /**
     * 从网络上爬去腾讯分分彩赛果数据
     */
    void addTxffcSg();

    /**
     * 生成腾讯分分彩免费推荐数据
     */
    void addTxffcRecommend();

    /**
     * 生成腾讯分分彩公式杀号
     */
    void addTxffcGssh();

    /**
     * 腾讯分分彩切换期号发消息
     */
    void sendMessageChangeIssue();

    /**
     * 腾讯分分彩大小单双等统计
     */
    void dxdsSgCount();

    /**
     * 腾讯分分彩大小单双等统计,最近两个月历史数据
     */
    void dxdsSgCountLatelyTwoMonth();

}
