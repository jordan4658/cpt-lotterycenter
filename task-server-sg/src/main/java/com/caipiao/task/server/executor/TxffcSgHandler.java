package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.TxffcTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 腾讯分分彩 - 录入赛果
 *
 * @author shaoming
 * @datetime 2018-07-27 11:22
 * <p>
 * 定时任务Cron：5,6,7 0/1 * * * ?
 **/
@Service
public class TxffcSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(TxffcSgHandler.class);

    @Autowired
    private TxffcTaskService txffcTaskService;

    @XxlJob("TxffcSgHandler")
    public ReturnT<String> executeTxffcSg(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");

//            TxffcCrawler crawler = new TxffcCrawler("crawler", false);
//            crawler.start(1);

            // 从网址：http://www.off0.com/index.php 抓取数据
            txffcTaskService.addTxffcSg();

            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("TxffcSgPrevHandler")
    public ReturnT<String> executeTxffcSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.txffcTaskService.addTxffcPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
