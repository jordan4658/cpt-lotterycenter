package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.SendSgService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: admin
 * @Description: 每10分钟发送sg数据同步到caipiao库， 防止平常漏同步
 * @Version: 1.0.0
 * @Date; 2018/6/26 026 17:12
 */
@Service
public class SendSgHandler {
    private static final Logger logger = LoggerFactory.getLogger(SendSgHandler.class);

    @Autowired
    private SendSgService sendSgService;

    @XxlJob("SendSgHandler")
    public ReturnT<String> execute(String strings) throws Exception {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");

            sendSgService.sendSgData();

            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

}
