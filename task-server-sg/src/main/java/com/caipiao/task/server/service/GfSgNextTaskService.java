package com.caipiao.task.server.service;

/*
官方下一期赛果信息
 */
public interface GfSgNextTaskService {
	/**
     * 官方下一期数据 重庆时时彩 active发送
     */
    void gfSgCqsscNextMessage();

    /**
     * 官方下一期数据 新疆时时彩 active发送
     */
    void gfSgXjsscNextMessage();

    /**
     * 官方下一期数据 天津时时彩 active发送
     */
    void gfSgTjsscNextMessage();

    /**
     * 官方下一期数据 北京PK10 active发送
     */
    void gfSgBjpk10NextMessage();

    /**
     * 官方下一期数据 幸运飞艇 active发送
     */
    void gfSgXyftNextMessage();

    /**
     * 官方下一期数据 幸运飞艇番摊 active发送
     */
    void gfSgXyftFtNextMessage();

    /**
     * 官方下一期数据 pc蛋蛋 active发送
     */
    void gfSgPcddNextMessage();

}
