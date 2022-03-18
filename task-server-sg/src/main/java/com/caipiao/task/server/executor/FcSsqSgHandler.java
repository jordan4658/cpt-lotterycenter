package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.FcSsqTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 福彩双色球 - 赛果录入
 *
 * @author lzy
 * @datetime 2018-07-30 16:07
 * <p>
 * 定时任务cron：0,10,20 3/5 * * * ?
 */
@Service
public class FcSsqSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(FcSsqSgHandler.class);

    @Autowired
    private FcSsqTaskService fcSsqTaskService;

    @XxlJob("FcSsqSgHandler")
    public ReturnT<String> executeFcSsqSg(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.fcSsqTaskService.addFcSsqSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            return ReturnT.FAIL;
        }
    }

    @XxlJob("FcSsqSgPrevHandler")
    public ReturnT<String> executeFcSsqSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.fcSsqTaskService.addFcSsqPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
