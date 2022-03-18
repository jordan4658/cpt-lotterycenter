package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.TenbjpksTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 北京PK10 - 录入赛果
 *
 * @author lzy
 * @datetime 2018-07-30 16:07
 * <p>
 * 定时任务cron：0,10,20 3/5 * * * ?
 */
@Service
public class TenbjpksSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(TenbjpksSgHandler.class);

    @Autowired
    private TenbjpksTaskService tenbjpksTaskService;

    @XxlJob("TenbjpksSgHandler")
    public ReturnT<String> executeTenbjpksSg(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.tenbjpksTaskService.addTenbjpksSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

    @XxlJob("TenbjpksSgPrevHandler")
    public ReturnT<String> executeTenbjpksSgPrev(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.tenbjpksTaskService.addTenbjpksPrevSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
