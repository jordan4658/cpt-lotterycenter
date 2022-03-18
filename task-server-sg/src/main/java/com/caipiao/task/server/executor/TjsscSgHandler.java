package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.TjsscTaskService;
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
public class TjsscSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(TjsscSgHandler.class);

    @Autowired
    private TjsscTaskService tjsscTaskService;

    @XxlJob("TjsscSgHandler")
    public ReturnT<String> executeTjsscSg(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.tjsscTaskService.addTjsscSg();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("TjsscSgPrevHandler")
    public ReturnT<String> executeTjsscSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            tjsscTaskService.addTjsscPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
