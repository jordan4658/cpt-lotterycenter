package com.caipiao.task.server.service;

public interface JPushService {
    /**
     * 开奖号码推送
     */
    void openNmberPush(String lotteryName, String issue, String openNumber);
}
