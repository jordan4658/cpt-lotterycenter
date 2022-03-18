package com.caipiao.task.server.service;

public interface AuspksTaskService {
	  /**
     * 澳洲PK10 - 预期数据
     */
   // void addAuspksPrevSg();

    /**
     * 从第三方接口获取赛果并添加到数据库
     */
    void addAuspksSg();

    /**
     * 统计每天的赛果并添加到数据库
     */
    void addAuspksSgCount();

    /**
     * 生成澳洲PK10免费推荐数据
     */
    void addAuspksRecommend();

    /**
     * 添加每期的公式杀号
     */
    void addAuspksKillNumber();
}
