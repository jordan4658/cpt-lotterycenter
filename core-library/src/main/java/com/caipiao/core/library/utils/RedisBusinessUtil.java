package com.caipiao.core.library.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 纯 redis 业务操作工具类，不包含 DB 访问，主要场景是在controller里直接用，不用远程调用进入server等应用
 */
public class RedisBusinessUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisBusinessUtil.class);
    public volatile static RedisTemplate redisTemplate;

    public static void init(RedisTemplate redisTemplate) {
        if (null == RedisBusinessUtil.redisTemplate && null != redisTemplate) {
            RedisBusinessUtil.redisTemplate = redisTemplate;
            logger.info("RedisBusinessUtil init:{}", redisTemplate);
        }
    }

}
