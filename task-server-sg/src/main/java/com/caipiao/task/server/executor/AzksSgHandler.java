package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.AzksTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 添加澳洲快三赛果定时任务
 *
 * @author lzy
 * @datetime 2018-07-27 11:22
 *
 * 定时任务Cron：16,26,36 0/10 10-2 * * ?
 **/
@Service
public class AzksSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(AzksSgHandler.class);

    @Autowired
    private AzksTaskService azksTaskService;

    @XxlJob("AzksSgHandler")
    public ReturnT<String> executeAzksSg(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.azksTaskService.addAzksSg();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(),e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("AzksSgPrevHandler")
    public ReturnT<String> executeAzksSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.azksTaskService.addAzksPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(),e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
