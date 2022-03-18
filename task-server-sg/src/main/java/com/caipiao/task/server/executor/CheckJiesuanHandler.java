package com.caipiao.task.server.executor;

import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: admin
 * @Description: 检查未结算的订单
 * @Version: 1.0.0
 * @Date; 2018/6/26 026 17:12
 */
@Service
public class CheckJiesuanHandler {
    private static final Logger logger = LoggerFactory.getLogger(CheckJiesuanHandler.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @XxlJob("CheckJiesuanHandler")
    public ReturnT<String> execute(String strings) throws Exception {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");

            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_CHECK_ORDER, DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));

            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
        }
        return ReturnT.SUCCESS;
    }

}
