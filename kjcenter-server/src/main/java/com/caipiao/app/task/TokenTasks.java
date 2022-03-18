package com.caipiao.app.task;

import com.caipiao.app.service.TokenIpService;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.core.library.tool.redis.RedisLock;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.TokenIpMapper;
import com.mapper.domain.TokenIp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@EnableScheduling
@Component
public class TokenTasks {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenIpService tokenIpService;
    @Autowired
    private TokenIpMapper tokenIpMapper;

    /**
     *  从数据库查询token数据，放入缓存
     */
    @Scheduled(cron = "0 10 * * * ?")
    public void putToken() {
        String key = "tokenTaskOne";
        RedisLock lock = new RedisLock(key+"lock", 10 * 1000, 30 * 1000);
        log.info("从数据库查询token数据，放入缓存开始");
        try {
            if (lock.lock()) {
                if(redisTemplate.opsForValue().get(key) == null){
                    redisTemplate.opsForValue().set(key,1,60, TimeUnit.SECONDS);
                    List<TokenIp> tokenIpList = tokenIpService.getTokenIpLIst();
                    for(TokenIp tokenIp:tokenIpList){
                        redisTemplate.opsForHash().put(RedisKeys.TOKEN_KEY,tokenIp.getToken(),tokenIp.getIps());
                    }
                }
                log.info("从数据库查询token数据，放入缓存结束");
            }
        } catch (Exception e) {
            log.error("LotterySgTasks dealOneMinute is error {} "+e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    /**
     *  从缓存查询token对应的ips数据，放入数据库
     */
//    @Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "10 5 * * * ?")
    public void putTokenIps() {
        String key = "tokenIpsTaskOne";
        RedisLock lock = new RedisLock(key+"lock", 10 * 1000, 30 * 1000);
        log.info("从缓存查询token对应的ips数据，放入数据库开始");
        try {
            if (lock.lock()) {
                if(redisTemplate.opsForValue().get(key) == null){
                    redisTemplate.opsForValue().set(key,1,60, TimeUnit.SECONDS);
                    Map<String,String> map = redisTemplate.opsForHash().entries(RedisKeys.TOKEN_KEY_RE);
                    for(String token:map.keySet()){
                        String redisIps = map.get(token);
                        TokenIp tokenIp = tokenIpService.getTokenIpByToken(token);
                        String dataIps = tokenIp.getIps();
                        if(!dataIps.equals(redisIps)){
                            tokenIp.setIps(redisIps);
                            tokenIpMapper.updateByPrimaryKey(tokenIp);
                        }
                    }

                    List<TokenIp> tokenIpList = tokenIpService.getTokenIpLIst();
                    for(TokenIp tokenIp:tokenIpList){
                        redisTemplate.opsForHash().put(RedisKeys.TOKEN_KEY,tokenIp.getToken(),tokenIp.getIps());
                    }
                }
                log.info("从缓存查询token对应的ips数据，放入数据库结束");
            }
        } catch (Exception e) {
            log.error("LotterySgTasks dealOneMinute is error {} "+e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }


    /**
     *  从数据库 查询绑定ip ，放入缓存中
     */
    @Scheduled(cron = "0 5 * * * ?")
    public void putIpBingToRedis() {
        String key = "putIpBingTaskOne";
        RedisLock lock = new RedisLock(key+"lock", 10 * 1000, 30 * 1000);
        log.info("从数据库 查询绑定ip ，放入缓存中开始");
        try {
            if (lock.lock()) {
                if(redisTemplate.opsForValue().get(key) == null){
                    redisTemplate.opsForValue().set(key,1,60, TimeUnit.SECONDS);
                    List<TokenIp> tokenIps = tokenIpService.getTokenIpLIst();
                    for(TokenIp tokenIp:tokenIps){
                        if(StringUtils.isNotEmpty(tokenIp.getIpsBinding())){
                            redisTemplate.opsForHash().put(RedisKeys.TOKEN_KEY_RE,tokenIp.getToken(),tokenIp.getIpsBinding());

                        }
                    }
                }
                log.info("从数据库 查询绑定ip ，放入缓存中结束");
            }
        } catch (Exception e) {
            log.error("LotterySgTasks dealOneMinute is error {} "+e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }


    /**
     *  每隔1小时清除 查询违规次数
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void clearIllegalTimes() {
        String key = "clearIllegalTaskOne";
        RedisLock lock = new RedisLock(key+"lock", 0 * 1000, 30 * 1000);
        log.info("每隔1小时清除 查询违规次数开始");
        try {
            if (lock.lock()) {
                if(redisTemplate.opsForValue().get(key) == null){
                    redisTemplate.opsForValue().set(key,1,60, TimeUnit.SECONDS);
                    Map<String,String> map = redisTemplate.opsForHash().entries(RedisKeys.IP_VISIT_KEY);
                    for(String thisKey:map.keySet()){
                        String value = map.get(thisKey);
                        String array[] = value.split("=");
                        String time = array[0];
                        value = time + "="+"0";
                        redisTemplate.opsForHash().put(RedisKeys.IP_VISIT_KEY,thisKey,value);
                    }

                }
                log.info("每隔1小时清除 查询违规次数结束");
            }
        } catch (Exception e) {
            log.error("LotterySgTasks dealOneMinute is error {} "+e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }







}
