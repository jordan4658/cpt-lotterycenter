package com.caipiao.task.server.executor;

import com.caipiao.task.server.service.GfSgNextTaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: admin
 * @Description: 每2/10分钟检查下一个小时需要发送下一期官方开采赛果信息（准点发送），不需要等到开奖结果
 * @Version: 1.0.0
 * @Date; 2018/6/26 026 17:12
 */
@Service
public class NextSgHandlerManager {
    private static final Logger logger = LoggerFactory.getLogger(NextSgHandlerManager.class);

    @Autowired
    private GfSgNextTaskService gfSgNextTaskService;

    @XxlJob("NextSgHandlerManager")
    public ReturnT<String> execute(String strings) throws Exception {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");

            gfSgNextTaskService.gfSgCqsscNextMessage();
            gfSgNextTaskService.gfSgXjsscNextMessage();
            gfSgNextTaskService.gfSgTjsscNextMessage();
            gfSgNextTaskService.gfSgBjpk10NextMessage();
            gfSgNextTaskService.gfSgXyftNextMessage();
            gfSgNextTaskService.gfSgXyftFtNextMessage();
            gfSgNextTaskService.gfSgPcddNextMessage();

            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

}
