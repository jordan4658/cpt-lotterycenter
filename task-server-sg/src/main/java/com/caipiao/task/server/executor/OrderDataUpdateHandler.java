package com.caipiao.task.server.executor;

import com.caipiao.task.server.config.ActiveMQConfig;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 订单数据更新字段
 */
@Service
public class OrderDataUpdateHandler {
    private static final Logger logger = LoggerFactory.getLogger(OrderDataUpdateHandler.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @XxlJob("OrderDataUpdateHandler")
    public ReturnT<String> execute(String strings) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ORDER_DATA_UPDATE, "TOPIC_ORDER_DATA_UPDATE");
            XxlJobLogger.log(name + "执行任务成功");
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            logger.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
            return ReturnT.FAIL;
        }
    }

}
