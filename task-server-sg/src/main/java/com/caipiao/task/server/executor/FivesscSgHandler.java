package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.FivesscTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 添加天津时时彩赛果定时任务
 *
 * @author lzy
 * @datetime 2018-07-27 11:22
 * <p>
 * 定时任务Cron：16,26,36 0/10 10-2 * * ?
 **/
@Service
public class FivesscSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(FivesscSgHandler.class);
    @Autowired
    private FivesscTaskService fivesscTaskService;

    @XxlJob("FivesscSgHandler")
    public ReturnT<String> executeFivesscSg(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.fivesscTaskService.addFivesscSg();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("FivesscSgPrevHandler")
    public ReturnT<String> executeFivesscSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            fivesscTaskService.addFivesscPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
