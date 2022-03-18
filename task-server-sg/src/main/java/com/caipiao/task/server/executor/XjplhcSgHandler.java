package com.caipiao.task.server.executor;
import com.caipiao.task.server.service.XjplhcTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 添加新加坡六合彩赛果定时任务
 *
 * @author lzy
 * @datetime 2019-12-31 11:22
 *
 * 定时任务Cron：0 0/5 * * * ? *
 **/
@Service
public class XjplhcSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(XjplhcSgHandler.class);

    @Autowired
    private XjplhcTaskService xjplhcTaskService;

    @XxlJob("XjplhcSgHandler")
    public ReturnT<String> executeXjplhcSg(String strings) {
        try {
            String name = this.getClass().getName();
            logger.info("name + \"开始执行任务\"");
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.xjplhcTaskService.addXjplhcSg();
            XxlJobLogger.log(name + "执行任务成功");
            logger.info("name + \"开始执行任务\"");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(),e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("XjplhcSgPrevHandler")
    public ReturnT<String> executeXjplhcSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.xjplhcTaskService.addXjplhcPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(),e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
