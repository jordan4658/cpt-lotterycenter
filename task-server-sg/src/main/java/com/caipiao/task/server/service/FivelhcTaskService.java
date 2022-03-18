package com.caipiao.task.server.service;

public interface FivelhcTaskService {
	/**
     * 五分六合彩 - 预开数据
     */
    void addFivelhcPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addFivelhcSg();

}
