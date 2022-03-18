package com.caipiao.live.common.util.redis;

import com.caipiao.live.common.util.SpringUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用 redis 实现 mybatis 二级缓存
 * 此缓存目前只用于赛果类，缓存时间较短
 * 通用缓存使用 RedisSecondCache
 */
public class RedisSgCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisSgCache.class);
    protected final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    protected final String id; // cache instance id
    protected RedisTemplate redisTemplate;
    private static final long EXPIRE_TIME_IN_SECONDS = 600; // redis过期时间
    protected final boolean isCanClearOp = false;

    public RedisSgCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.info("{}:id={}", this.getClass().getSimpleName(), id);
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
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
        opsForValue.set(key.toString(), value, EXPIRE_TIME_IN_SECONDS, TimeUnit.SECONDS);
//        logger.info("Put query key:{} to redis", key);
        logger.info("Put query key to redis");
    }

    /**
     * Get cached query result from redis
     *
     * @param key
     * @return
     */
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        Object result = opsForValue.get(key.toString());
//        logger.info("Get cached key:{} from redis", key);
        logger.info("Get cached key from redis");
        return result;
    }

    /**
     * Remove cached query result from redis
     *
     * @param key
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.delete(key.toString());
//        logger.info("Remove cached key:{} result from redis", key);
        logger.info("Remove cached key result from redis");
        return null;
    }

    /**
     * Clears this cache instance
     */
    @Override
    public void clear() {
        if (!isCanClearOp) {
            return;
        }
        try {
            if (!readWriteLock.writeLock().tryLock(1, TimeUnit.MINUTES)) {
                RedisTemplate redisTemplate = getRedisTemplate();
                redisTemplate.execute((RedisCallback) connection -> {
                    connection.flushDb();
                    logger.info("Clear all the cached query result from redis");
                    return null;
                });
            }
        } catch (Exception e) {
            logger.error("clear redis caches occur error:{}", e.getMessage(), e);
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    @Override
    public int getSize() {
        logger.error("to get redis size...");
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    //给模板对象RedisTemplate赋值，并传出去
    protected RedisTemplate getRedisTemplate() {
        RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getApplicationContext().getBean("redisTemplate");
        return redisTemplate;
    }

}


