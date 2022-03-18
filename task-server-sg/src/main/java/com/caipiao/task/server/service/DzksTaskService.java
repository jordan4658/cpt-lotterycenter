package com.caipiao.task.server.service;

public interface DzksTaskService {
	/**
     * 德州快三 - 预开数据
     */
    void addDzksPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addDzksSg();

}
