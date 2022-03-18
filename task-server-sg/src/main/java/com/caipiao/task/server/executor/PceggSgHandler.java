package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.PceggTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PC蛋蛋 - 录入赛果
 *
 * @author ShaoMing
 * @datetime 2018/7/27 16:23
 * <p>
 * 定时任务：0,30 1/5 9-23 * * ?
 */
@Service
public class PceggSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(PceggSgHandler.class);

    @Autowired
    private PceggTaskService pceggTaskService;

    @XxlJob("PceggSgHandler")
    public ReturnT<String> executePceggSg(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            pceggTaskService.addPceggSg();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("PceggSgPrevHandler")
    public ReturnT<String> executePceggSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            pceggTaskService.addPceggPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

}
