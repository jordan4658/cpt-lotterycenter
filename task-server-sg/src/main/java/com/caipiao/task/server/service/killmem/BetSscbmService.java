package com.caipiao.task.server.service.killmem;

public interface BetSscbmService {


    /**
     * 结算【两面】
     * @param issue 期号
     * @param number 开奖号码
     */
    void countlm(String issue, String number, int lotteryId) throws Exception;

    /**
     * 结算【斗牛】
     * @param issue 期号
     * @param number 开奖号码
     */
    void countdn(String issue, String number, int lotteryId) throws Exception;

    /**
     * 结算1-5
     * @param issue 期号
     * @param number 开奖号码
     */
    void count15(String issue, String number, int lotteryId) throws Exception;

    /**
     * 三星前中后
     * @param issue 期号
     * @param number 开奖号码
     */
    void countqzh(String issue, String number, int lotteryId) throws Exception;



   
}
