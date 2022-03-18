package com.caipiao.task.server.service;

public interface PceggTaskService {

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    void addPceggSg();

    /**
     * PC蛋蛋-免费推荐定时任务
     */
    void addPceggRecommend();

    /**
     * 预存 PC蛋蛋 期号数据
     */
    void addPceggPrevSg();

    /**
     * pc蛋蛋切换期号发消息
     */
    void sendMessageChangeIssue();

}
