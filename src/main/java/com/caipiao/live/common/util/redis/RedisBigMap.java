package com.caipiao.live.common.util.redis;

import com.caipiao.live.common.spring.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Redis 大 key Map 操作类
 */
public class RedisBigMap {

    private static final Logger logger = LoggerFactory.getLogger(RedisBigMap.class);

    /** Redis 默认分配的 key 数量 */
    protected static final int DEFAULT_KEY_SIZE = 1 << 4;

    /** 最大 key 数量 */
    protected static final int MAX_KEY_SIZE = 1 << 5;

    /** Redis 单节点最大记录数，超过此记录，自动分裂。暂不支持 rehash */
    protected static final int REHASH_MAX_ENTRRIS = 10000;

    /** Redis key 数量 */
    protected int keySize;

    protected RedisTemplate redisTemplate;

    public RedisBigMap() {
        this.keySize = DEFAULT_KEY_SIZE;
        this.redisTemplate = (RedisTemplate) SpringContextUtils.getBean("redisTemplate");
        logger.info("RedisBigMap default constructor keySize:{}.", keySize);
    }

    public RedisBigMap(int keySize) {
        this();
        if (keySize > this.keySize && keySize <= MAX_KEY_SIZE) {
            this.keySize = MAX_KEY_SIZE;
            logger.info("RedisBigMap constructor real keySize:{}.", keySize);
        }
    }

    public RedisBigMap put(String key, String hashKey, Object value) {
        if (isEmpty(key) || isEmpty(hashKey)) {
            return this;
        }
        String realKey = realKey(key, hashKey);
        this.redisTemplate.opsForHash().put(realKey, hashKey, value);
        logger.info("put realKey:{} hashKey:{} value:{} succeed.", realKey, hashKey, value);
        return this;
    }

    public Map<String, Object> get(String key) {
        if (isEmpty(key)) {
            return new HashMap<>();
        }
        Map<String, Object> result = new HashMap<>();
        Set<String> keys = this.redisTemplate.keys(key + "*");
        for (String curr : keys) {
            Map<String, Object> map = this.redisTemplate.opsForHash().entries(curr);
            if (null != map) {
                result.putAll(map);
            }
        }
        return result;
    }

    public Object get(String key, String hashKey) {
        if (isEmpty(key) || isEmpty(hashKey)) {
            return null;
        }
        String realKey = realKey(key, hashKey);
        Object value = this.redisTemplate.opsForHash().get(realKey, hashKey);
        logger.info("get realKey:{} hashKey:{} value:{} succeed.", realKey, hashKey, value);
        return value;
    }

    public RedisBigMap delete(String key, String hashKey) {
        if (isEmpty(key)) {
            return this;
        }
        String realKey = realKey(key, hashKey);
        this.redisTemplate.delete(realKey);
        logger.info("delete realKey:{} succeed.", realKey);
        return this;
    }

    public RedisBigMap delete(String key, String... hashKeys) {
        if (isEmpty(key) || null == hashKeys || hashKeys.length == 0) {
            return this;
        }
        for (String curr : hashKeys) {
            String realKey = realKey(key, curr);
            Long effects = this.redisTemplate.opsForHash().delete(realKey);
            logger.info("delete realKey:{} hashKey:{}  succeed. effects values:{}.", realKey, curr, effects);
        }
        return this;
    }

    protected String realKey(String key, String hashKey) {
        String realKey = key + "_" + hash(hashKey);
        logger.info("realKey: {}, for origin key:{}; hashKey:{}.", realKey, key, hashKey);
        return realKey;
    }

    public int hash(Object key) {
        return (key == null) ? 0 : (key.hashCode() % keySize);
    }

    private boolean isEmpty(String key) {
        return null == key || "".equals(key.trim());
    }

    public int getKeySize() {
        return keySize;
    }

}
