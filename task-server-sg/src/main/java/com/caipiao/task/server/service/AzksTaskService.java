package com.caipiao.task.server.service;

public interface AzksTaskService {
	/**
     * 澳洲快三 - 预开数据
     */
    void addAzksPrevSg();

    /**
     * 随机生成赛果并添加到数据库
     */
    void addAzksSg();

}
