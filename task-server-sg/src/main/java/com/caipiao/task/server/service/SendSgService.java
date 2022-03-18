package com.caipiao.task.server.service;

public interface SendSgService {

    /**
     * 从sg库查询数据同步到caipiao库
     */
    void sendSgData();

    /**
     * 从sg库查询数据同步到caipiao库(一天有赛果的数据)
     */
    void sendSgOneDayData();

    /**
     * 从sg库查询数据同步到caipiao库(一天预期的数据)
     */
    void sendSgYuqiOneDayData();

}
