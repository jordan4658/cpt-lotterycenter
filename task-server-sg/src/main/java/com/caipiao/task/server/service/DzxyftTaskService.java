package com.caipiao.task.server.service;

/**
 * 德州幸运飞艇 - 接口
 */
public interface DzxyftTaskService {

    /**
     * 幸运飞艇 - 预期数据
     */
    void addDzxyftPrevSg();

    /**
     * 从第三方接口获取幸运飞艇赛果并添加到数据库
     */
    void addDzxyftSg();

}
