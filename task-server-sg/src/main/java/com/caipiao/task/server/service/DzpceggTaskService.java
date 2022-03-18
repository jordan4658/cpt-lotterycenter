package com.caipiao.task.server.service;

/**
 * 德州PC蛋蛋 - 接口
 */
public interface DzpceggTaskService {

    /**
     * PC蛋蛋 - 预期数据
     */
    void addDzpceggPrevSg();

    /**
     * 从第三方接口获取幸运飞艇赛果并添加到数据库
     */
    void addDzpceggSg();

}
