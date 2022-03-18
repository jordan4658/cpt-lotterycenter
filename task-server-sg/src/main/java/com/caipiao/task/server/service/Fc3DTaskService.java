package com.caipiao.task.server.service;

public interface Fc3DTaskService {
	/**
     * 福彩3d - 预开数据
     */
    void addFc3DPrevSg();

    /**
     * 生成赛果并添加到数据库
     */
    void addFc3DSg();

    /**
     * 生成福彩3d免费推荐数据
     */
    void addFc3DRecommend();

    /**
     * 生成福彩3d公式杀号
     */
    void addFc3DGssh();
}
