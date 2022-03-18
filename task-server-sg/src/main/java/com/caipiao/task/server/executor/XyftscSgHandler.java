package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.XyftscTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 添加幸运飞艇私彩赛果定时任务
 * <p>
 * 定时任务Cron：35,40,45,50 4/5 13-4 * * ?
 */
@Service
public class XyftscSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(XyftscSgHandler.class);

    @Autowired
    private XyftscTaskService xyftscTaskService;

    @XxlJob("XyftscSgHandler")
    public ReturnT<String> executeXyftscSg(String strings) throws Exception {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.xyftscTaskService.addXyftscSg();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("XyftscSgPrevHandler")
    public ReturnT<String> executeXyftscSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.xyftscTaskService.addXyftscPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
