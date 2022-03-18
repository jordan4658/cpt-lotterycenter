package com.caipiao.task.server.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.order.OrderBetKillDto;
import com.caipiao.core.library.model.dao.KillConfig;
import com.caipiao.core.library.utils.RedisKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class KillOrderReceiver {
    private static final Logger logger = LoggerFactory.getLogger(KillOrderReceiver.class);

    @Autowired
    private RedisTemplate redisTemplate;

    // 私彩订单队列名
    public final static String SICAIORDERKILL = "SICAIORDERKILL";

    @JmsListener(destination = SICAIORDERKILL, containerFactory = "jmsListenerContainerQueue")
    public void readActiveTopic(String message) {
        try {
            int length = message.indexOf(":");
            String object = message.substring(length + 1);
            String messageArray[] = message.split(":");
            String orderjson = message.replace("orderkill:", "");
//        String  orderjson=message.replace("orderkill:","");
            logger.info("杀号功能消费订单：platform={},orderjson={}", messageArray[0], object);
            String platformChuan = "";

            String platformStrings[] = messageArray[0].split(",");
            if (platformStrings.length == 2) {
                platformChuan = platformStrings[0];
            }
            OrderBetKillDto KillDto = JSON.parseObject(object, new TypeReference<OrderBetKillDto>() {
            });

            if (redisTemplate.hasKey("kill" + KillDto.getLotteryId())) {
                KillConfig killConfig = JSONObject.parseObject(redisTemplate.opsForValue().get("kill" + KillDto.getLotteryId()).toString(), KillConfig.class);
                if (killConfig.getPlatfom().equals("ALL") || killConfig.getPlatfom().equals(platformChuan)) {
                    // redisTemplate.opsForValue().set(RedisKeys.KILL_ORDER+KillDto.getLotteryId()+KillDto.getIssue()+"_"+KillDto.getBetNumber()+KillDto.getOrderId(), KillDto,11, TimeUnit.MINUTES);
                    redisTemplate.opsForList().leftPush(RedisKeys.KILL_ORDER + KillDto.getLotteryId() + KillDto.getIssue(), KillDto);
                    //只有澳门六合彩的时间是1天，其他私彩的时间小于11分钟
                    if (null != KillDto.getLotteryId() && KillDto.getLotteryId() == Constants.LOTTERY_AMLHC) {
                        redisTemplate.opsForList().getOperations().expire(RedisKeys.KILL_ORDER + KillDto.getLotteryId() + KillDto.getIssue(), 24, TimeUnit.HOURS);
                    } else {
                        redisTemplate.opsForList().getOperations().expire(RedisKeys.KILL_ORDER + KillDto.getLotteryId() + KillDto.getIssue(), 11, TimeUnit.MINUTES);
                    }
                }
            } else {
                logger.info("该订单没有对应的杀号配置信息：platform={},orderjson={}", messageArray[0], object);
            }

        } catch (Exception e) {
            logger.info("杀号功能消费订单出错：{}，{}", message, e);
        }
    }
}
