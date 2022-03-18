package com.caipiao.live.common.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * 通用 redis 二级缓存
 */
public class RedisSecondCache extends RedisSgCache {
    private static final Logger logger = LoggerFactory.getLogger(RedisSecondCache.class);
    private static final long EXPIRE_TIME = 24; // redis过期时间

    public RedisSecondCache(String id) {
        super(id);
    }

    /**
     * Put query result to redis
     *
     * @param key
     * @param value
     */
    @Override
    @SuppressWarnings("unchecked")
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = getRedisTemplate();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key.toString(), value, EXPIRE_TIME, TimeUnit.HOURS);
//        logger.info("Put query key:{} to redis", key);
        logger.info("Put query key to redis");
    }

}
