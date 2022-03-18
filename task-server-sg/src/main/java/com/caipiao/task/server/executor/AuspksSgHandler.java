package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.AuspksTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 澳洲PK10 - 录入赛果
 */
@Service
public class AuspksSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuspksSgHandler.class);

    @Autowired
    private AuspksTaskService auspksTaskService;

    @XxlJob("AuspksSgHandler")
    public ReturnT<String> execute(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            // 从第三方接口获取赛果并添加到数据库
            this.auspksTaskService.addAuspksSg();
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            return ReturnT.FAIL;
        }
    }

}
