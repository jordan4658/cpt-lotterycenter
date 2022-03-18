package com.caipiao.task.server.service;

public interface OnelhcTaskService {
	/**
     * 一分六合彩 - 预开数据
     */
    void addOnelhcPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addOnelhcSg();

}
