package com.caipiao.live.common.util.redis;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.SysParameterEnum;
import com.caipiao.live.common.model.dto.lottery.LotteryInfo;
import com.caipiao.live.common.mybatis.entity.*;
import com.caipiao.live.common.util.CollectionUtil;
import com.caipiao.live.common.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    /////////////////////////////////////////通用基础方法/////////////////////////////////////////

    public static <T> T get(Object key) {
        if (null == key || StringUtils.isEmpty(key.toString())) {
            return null;
        }
        return (T) redisTemplate.opsForValue().get(key);
    }

    public static void set(Object key, Object value) {
        set(key, value, null, null);
    }

    public static void set(Object key, Object value, Long expireTime) {
        set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public static void set(Object key, Object value, Long expireTime, TimeUnit unit) {
        if (null == key || StringUtils.isEmpty(key.toString()) || null == value) {
            return;
        }
        if (null != expireTime && expireTime > 0) {
            unit = null == unit ? TimeUnit.SECONDS : unit;
            redisTemplate.opsForValue().set(key, value, expireTime, unit);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    public static void delete(String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        redisTemplate.delete(key);
        logger.info("delete:{} success.", key);
    }

    public static void delete(String... keys) {
        if (null == keys || keys.length == 0) {
            return;
        }
        Set set = new HashSet();
        for (String k : keys) {
            if (null == k || "".equals(k.trim())) {
                continue;
            }
            set.add(k);
        }
        if (set.size() > 0) {
            redisTemplate.delete(set);
            logger.info("delete:{} success.", JSONObject.toJSONString(keys));
        }
    }

    public static void delete(Collection collection) {
        if (null != collection && collection.size() > 0) {
            redisTemplate.delete(collection);
        }
    }

    /**
     * 删除指定key的缓存，多个key间以逗号分隔
     *
     * @param keys
     */
    public static void deleteByKeys(String keys) {
        if (StringUtils.isEmpty(keys)) {
            return;
        }
        List<String> idList = StringUtils.splitStringList(keys);
        redisTemplate.delete(idList);
        logger.info("deleteByKeys:{} success.", keys);
    }

    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public static void setExpire(String key, Long second) {
        setExpire(key, second, TimeUnit.SECONDS);
    }

    public static void setExpire(String key, Long second, TimeUnit unit) {
        try {
            if (StringUtils.isEmpty(key)) {
                return;
            }
            unit = null == unit ? TimeUnit.SECONDS : unit;
            redisTemplate.expire(key, second, unit);
        } catch (Exception e) {
            logger.error("setExpireTime:{} expire:{} occur error.", key, second, e);
        }
    }

    public static boolean exists(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /////////////////////////////////////////业务方法/////////////////////////////////////////

    /**
     * 删除彩种类缓存
     */
    public static void deleteLotteryCaches() {

        logger.info("entry deleteLotteryCache...");
        List<String> keys = new ArrayList<>();
        keys.add(RedisKeys.LOTTERY_CATEGORY_LIST_KEY);
        keys.add(RedisKeys.LOTTERY_CATEGORY_MAP_KEY);
        keys.add(RedisKeys.LOTTERY_LIST_KEY);
        keys.add(RedisKeys.LOTTERY_MAP_KEY);
        keys.add(RedisKeys.LOTTERY_ALL_LIST_KEY);
        keys.add(RedisKeys.LOTTERY_ALL_MAP_KEY);
        keys.add(RedisKeys.LOTTERY_PLAY_LIST_KEY);
        keys.add(RedisKeys.LOTTERY_PLAY_MAP_KEY);
        keys.add(RedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA);
        keys.add(RedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA);
        keys.add(RedisKeys.LOTTERY_FAVORITE_USER_DATA_DEFAULT);
        keys.add(RedisKeys.LOTTERY_FAVORITE_DEFAULT);
        keys.add(RedisKeys.LOTTERY_ALL_INNER_LIST);

        //删除彩种压缩包缓存
        keys.add(SysParameterEnum.LOTTERY_VERSION_ZIP_URL.getCode());
        keys.add(SysParameterEnum.LOTTERY_VERSION_ZIP_URL.getCode() + Constants.SYSTEM_INFO_VALUE_SUFFIX);

        redisTemplate.delete(keys);
        logger.info("deleteLotteryCache success.");

        String favoriteUser = RedisKeys.LOTTERY_FAVORITE_USER_PREFIX + "*";
        String favoriteUserData = RedisKeys.LOTTERY_FAVORITE_USER_DATA_PREFIX + "*";
        String lotteryAllInfo = RedisKeys.LOTTERY_ALL_INFO + "*";
        String lotteryInfo = RedisKeys.LOTTERY_KEY + "*";

        Set<String> favoriteUserSet = redisTemplate.keys(favoriteUser);
        Set<String> favoriteUserDataSet = redisTemplate.keys(favoriteUserData);
        Set<String> lotteryAllInfoSet = redisTemplate.keys(lotteryAllInfo);
        Set<String> lotteryInfoSet = redisTemplate.keys(lotteryInfo);

        Set<String> keySet = new HashSet<>();
        keySet.addAll(favoriteUserSet);
        keySet.addAll(favoriteUserDataSet);
        keySet.addAll(lotteryAllInfoSet);
        keySet.addAll(lotteryInfoSet);
        if (keySet.size() > 0) {
            redisTemplate.delete(keySet);
            logger.info("deleteLotteryCache for keySet success.");
        }
    }

    public static void updateCacheForValueByKey(String key, Object value, String type) {
        if (null == value || "delete".equals(type)) {
            redisTemplate.delete(key);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }


    /**
     * 获取内部彩种（非第三方）列表信息
     *
     * @return
     */
    public static List<Map<String, Object>> getLotteryAllInnerList() {
        return (List<Map<String, Object>>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_ALL_INNER_LIST);
    }

    /**
     * 缓存内部彩种（非第三方）列表信息
     *
     * @param list
     */
    public static void addLotteryAllInfo(List list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForValue().set(RedisKeys.LOTTERY_ALL_INNER_LIST, list);
    }

    /**
     * 获取彩种所有信息
     *
     * @param type
     * @return
     */
    public static List<LotteryInfo> getLotteryAllInfo(String type) {
        String key = RedisKeys.LOTTERY_ALL_INFO;
        if (StringUtils.isNotEmpty(type)) {
            key = key + "_" + type;
        }
        return (List<LotteryInfo>) redisTemplate.opsForValue().get(key);
    }

    /**
     * 缓存彩种及赔率所有信息
     *
     * @param type
     * @param list
     */
    public static void addLotteryAllInfo(String type, List<LotteryInfo> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        String key = RedisKeys.LOTTERY_ALL_INFO;
        if (StringUtils.isNotEmpty(type)) {
            key = key + "_" + type;
        }
        redisTemplate.opsForValue().set(key, list);
    }


    public static void addSysParameter(SysParameter info) {
        if (null == info || StringUtils.isEmpty(info.getParamCode())) {
            return;
        }
        redisTemplate.opsForValue().set(info.getParamCode().toUpperCase(), info);
        redisTemplate.opsForValue().set(info.getParamCode().toUpperCase() + Constants.SYSTEM_INFO_VALUE_SUFFIX, info.getParamValue());
    }

    public static void addSysParameterList(List<SysParameter> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.SYSTEM_INFO_LIST, list);
    }

    public static List<SysParameter> getSysParameterList() {
        return redisTemplate.opsForList().range(RedisKeys.SYSTEM_INFO_LIST, 0, -1);
    }

    public static SysParameter getSysParameter(SysParameterEnum sysParameterEnum) {
        if (null == sysParameterEnum) {
            return null;
        }
        return (SysParameter) redisTemplate.opsForValue().get(sysParameterEnum.getCode());
    }

    public static SysParameter getSysParameter(String key) {
        if (null == key) {
            return null;
        }
        return (SysParameter) redisTemplate.opsForValue().get(key);
    }

    public static String getSysParameterValue(SysParameterEnum sysParameterEnum) {
        if (null == sysParameterEnum) {
            return null;
        }
        return (String) redisTemplate.opsForValue().get(sysParameterEnum.getCode() + Constants.SYSTEM_INFO_VALUE_SUFFIX);
    }

    public static void setSystemInfoValue(SysParameter info) {
        if (null == info) {
            return;
        }
        redisTemplate.opsForValue().set(info.getParamCode() + Constants.SYSTEM_INFO_VALUE_SUFFIX, info.getParamValue());
    }

    /**
     * 删除系统配置
     *
     * @param code
     */
    public static void deleteSysParameter(String code) {
        if (null == code || "".equals(code.trim())) {
            return;
        }
        code = code.trim().toUpperCase();
        List<String> keys = Arrays.asList(code, code + Constants.SYSTEM_INFO_VALUE_SUFFIX);
        redisTemplate.delete(keys);
    }

    public static void deleteSystemInfoCaches(List<String> keys) {
        if (CollectionUtil.isEmpty(keys)) {
            return;
        }
        List<String> infoKeys = new ArrayList<>();
        for (String key : keys) {
            infoKeys.add(key + Constants.SYSTEM_INFO_VALUE_SUFFIX);
        }
        keys.addAll(infoKeys);
        keys.add(RedisKeys.SYSTEM_INFO_LIST);
        redisTemplate.delete(keys);
    }

    public static void deleteSystemInfoCaches(Map<String, String> map) {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries) {
            keys.add(entry.getKey());
            keys.add(entry.getKey() + Constants.SYSTEM_INFO_VALUE_SUFFIX);
        }
        keys.add(RedisKeys.SYSTEM_INFO_LIST);
        redisTemplate.delete(keys);
    }


    /**
     * 获取所有彩种大类缓存
     *
     * @return
     */
    public static List<LotteryCategory> getAllLotteryCategory() {
        return (List<LotteryCategory>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_CATEGORY_LIST_KEY);
    }

    /**
     * 获取所有彩种缓存
     *
     * @return
     */
    public static List<Lottery> getAllLottery() {
        return (List<Lottery>) redisTemplate.opsForValue().get(RedisKeys.LOTTERY_LIST_KEY);
    }

    public static void updateLsSgCache(Integer lotteryId, String key, Object updateSg) {
        try {
            if (redisTemplate.hasKey(key)) {
                if (lotteryId == Constants.LOTTERY_ONELHC) {
                    OnelhcLotterySg updateThisSg = (OnelhcLotterySg) updateSg;
                    OnelhcLotterySg onelhcLotterySg = (OnelhcLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(onelhcLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_FIVELHC) {
                    FivelhcLotterySg updateThisSg = (FivelhcLotterySg) updateSg;
                    FivelhcLotterySg fivelhcLotterySg = (FivelhcLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(fivelhcLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_AMLHC) {
                    AmlhcLotterySg updateThisSg = (AmlhcLotterySg) updateSg;
                    AmlhcLotterySg amlhcLotterySg = (AmlhcLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(amlhcLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_XYFT) {
                    XyftLotterySg updateThisSg = (XyftLotterySg) updateSg;
                    XyftLotterySg xyftLotterySg = (XyftLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(xyftLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_DLT) {
                    TcdltLotterySg updateThisSg = (TcdltLotterySg) updateSg;
                    TcdltLotterySg dltLotterySg = (TcdltLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(dltLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_TXFFC) {
                    TxffcLotterySg updateThisSg = (TxffcLotterySg) updateSg;
                    TxffcLotterySg txffcLotterySg = (TxffcLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue().split("-")[1]) - Long.valueOf(txffcLotterySg.getIssue().split("-")[1]) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_CQSSC) {
                    CqsscLotterySg updateThisSg = (CqsscLotterySg) updateSg;
                    CqsscLotterySg cqsscLotterySg = (CqsscLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(cqsscLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_XJSSC) {
                    XjsscLotterySg updateThisSg = (XjsscLotterySg) updateSg;
                    XjsscLotterySg xjsscLotterySg = (XjsscLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(xjsscLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_TJSSC) {
                    TjsscLotterySg updateThisSg = (TjsscLotterySg) updateSg;
                    TjsscLotterySg tjsscLotterySg = (TjsscLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(tjsscLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_TENSSC) {
                    TensscLotterySg updateThisSg = (TensscLotterySg) updateSg;
                    TensscLotterySg tensscLotterySg = (TensscLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(tensscLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_FIVESSC) {
                    FivesscLotterySg updateThisSg = (FivesscLotterySg) updateSg;
                    FivesscLotterySg fivesscLotterySg = (FivesscLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(fivesscLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_DZSSC) {
                    JssscLotterySg updateThisSg = (JssscLotterySg) updateSg;
                    JssscLotterySg jssscLotterySg = (JssscLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(jssscLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_PL35) {
                    TcplwLotterySg updateThisSg = (TcplwLotterySg) updateSg;
                    TcplwLotterySg tcplwLotterySg = (TcplwLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(tcplwLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_PCEGG) {
                    PceggLotterySg updateThisSg = (PceggLotterySg) updateSg;
                    PceggLotterySg pceggLotterySg = (PceggLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(pceggLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_HNQXC) {
                    Tc7xcLotterySg updateThisSg = (Tc7xcLotterySg) updateSg;
                    Tc7xcLotterySg qxcLotterySg = (Tc7xcLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(qxcLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_SSQ) {
                    FcssqLotterySg updateThisSg = (FcssqLotterySg) updateSg;
                    FcssqLotterySg fcssqLotterySg = (FcssqLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(fcssqLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_QLC) {
                    Fc7lcLotterySg updateThisSg = (Fc7lcLotterySg) updateSg;
                    Fc7lcLotterySg fc7lcLotterySg = (Fc7lcLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(fc7lcLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_FC3D) {
                    Fc3dLotterySg updateThisSg = (Fc3dLotterySg) updateSg;
                    Fc3dLotterySg fc3dLotterySg = (Fc3dLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(fc3dLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_BJPKS) {
                    BjpksLotterySg updateThisSg = (BjpksLotterySg) updateSg;
                    BjpksLotterySg bjpksLotterySg = (BjpksLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(bjpksLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_FIVEPKS) {
                    FivebjpksLotterySg updateThisSg = (FivebjpksLotterySg) updateSg;
                    FivebjpksLotterySg fivebjpksLotterySg = (FivebjpksLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(fivebjpksLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_DZPKS) {
                    JsbjpksLotterySg updateThisSg = (JsbjpksLotterySg) updateSg;
                    JsbjpksLotterySg jsbjpksLotterySg = (JsbjpksLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(jsbjpksLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_TENPKS) {
                    TenbjpksLotterySg updateThisSg = (TenbjpksLotterySg) updateSg;
                    TenbjpksLotterySg tenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(tenbjpksLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_AUSSSC) {
                    AussscLotterySg updateThisSg = (AussscLotterySg) updateSg;
                    AussscLotterySg aussscLotterySg = (AussscLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(aussscLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_AUSPKS) {
                    AuspksLotterySg updateThisSg = (AuspksLotterySg) updateSg;
                    AuspksLotterySg auspksLotterySg = (AuspksLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(auspksLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_AUSACT) {
                    AusactLotterySg updateThisSg = (AusactLotterySg) updateSg;
                    AusactLotterySg ausactLotterySg = (AusactLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(ausactLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_XJPLHC) {
                    XjplhcLotterySg updateThisSg = (XjplhcLotterySg) updateSg;
                    XjplhcLotterySg ausactLotterySg = (XjplhcLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(ausactLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_AZKS) {
                    AzksLotterySg updateThisSg = (AzksLotterySg) updateSg;
                    AzksLotterySg azksLotterySg = (AzksLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(azksLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_DZKS) {
                    DzksLotterySg updateThisSg = (DzksLotterySg) updateSg;
                    DzksLotterySg dzksLotterySg = (DzksLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(dzksLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_DZPCEGG) {
                    DzpceggLotterySg updateThisSg = (DzpceggLotterySg) updateSg;
                    DzpceggLotterySg dzpceggLotterySg = (DzpceggLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(dzpceggLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                } else if (lotteryId == Constants.LOTTERY_DZXYFT) {
                    DzxyftLotterySg updateThisSg = (DzxyftLotterySg) updateSg;
                    DzxyftLotterySg dzxyftLotterySg = (DzxyftLotterySg) redisTemplate.opsForList().index(key, 14);
                    //当期号相邻一期的时（说明缓存和数据库一致），则更新缓存，否则（清理下缓存，让历史和数据库同步）
                    if (Long.valueOf(updateThisSg.getIssue()) - Long.valueOf(dzxyftLotterySg.getIssue()) == 15) {
                        redisTemplate.opsForList().leftPush(key, updateSg);
                        redisTemplate.opsForList().rightPop(key);
                    } else {
                        redisTemplate.delete(key);
                    }
                }

            }
        } catch (Exception e) {
            logger.error("更新赛果历史缓存 出错：lotteryId:{},e:{}", lotteryId, e);
        }
    }


    public static void setCateIdByLotteryIdCache(String lotteryCategoryListKey, HashMap<Integer, Integer> map) {
        redisTemplate.opsForValue().set(lotteryCategoryListKey, map);
    }

    public static void addCacheForValueAndMinutes(Object key, Object value, Long time, TimeUnit minutes) {
        if (null == key || StringUtils.isEmpty(key.toString())) {
            return;
        }
        redisTemplate.opsForValue().set(key, value, time, minutes);
    }


    /**
     *  添加十分时时彩 赛果缓存
     * @param list
     */
    public static void addTensscLotterySgList(List<TensscLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.TENSSC_SG_HS_LIST, list);
    }

    /**
     * 获取十分时时彩 全部赛果缓存
     * @return
     */
    public static List<TensscLotterySg> getTensscLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.TENSSC_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取十分时时彩 分段赛果缓存
     * @return
     */
    public static List<TensscLotterySg> getTensscLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.TENSSC_SG_HS_LIST, pageNo, pageSize);
    }

    /**
     *  添加五分时时彩 赛果缓存
     * @param list
     */
    public static void addFivesscLotterySgList(List<FivesscLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.FIVESSC_SG_HS_LIST, list);
    }

    /**
     * 获取五分时时彩 全部赛果缓存
     * @return
     */
    public static List<FivesscLotterySg> getFivesscLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.FIVESSC_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取五分时时彩 分段赛果缓存
     * @return
     */
    public static List<FivesscLotterySg> getFivesscLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.FIVESSC_SG_HS_LIST, pageNo, pageSize);
    }


    //急速时时彩 RedisKeys.JSSSC_SG_HS_LIST

    /**
     *  添加急速时时彩 赛果缓存
     * @param list
     */
    public static void addJssscLotterySgList(List<JssscLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.JSSSC_SG_HS_LIST, list);
    }

    /**
     * 获取急速时时彩 全部赛果缓存
     * @return
     */
    public static List<JssscLotterySg> getJssscLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.JSSSC_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取急速时时彩 分段赛果缓存
     * @return
     */
    public static List<JssscLotterySg> getJssscLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.JSSSC_SG_HS_LIST, pageNo, pageSize);
    }

    //十分北京pk10 RedisKeys.TENPKS_SG_HS_LIST

    /**
     *  添加十分北京pk10 赛果缓存
     * @param list
     */
    public static void addTenpksLotterySgList(List<TenbjpksLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.TENPKS_SG_HS_LIST, list);
    }

    /**
     * 获取十分北京pk10 全部赛果缓存
     * @return
     */
    public static List<TenbjpksLotterySg> getTenpksLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.TENPKS_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取十分北京pk10 分段赛果缓存
     * @return
     */
    public static List<TenbjpksLotterySg> getTenpksLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.TENPKS_SG_HS_LIST, pageNo, pageSize);
    }

    //五分北京pk10 RedisKeys.FIVEPKS_SG_HS_LIST

    /**
     *  添加五分北京pk10 赛果缓存
     * @param list
     */
    public static void addFivepksLotterySgList(List<FivebjpksLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.FIVEPKS_SG_HS_LIST, list);
    }

    /**
     * 获取五分北京pk10 全部赛果缓存
     * @return
     */
    public static List<FivebjpksLotterySg> getFivepksLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.FIVEPKS_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取五分北京pk10 分段赛果缓存
     * @return
     */
    public static List<FivebjpksLotterySg> getFivepksLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.FIVEPKS_SG_HS_LIST, pageNo, pageSize);
    }

    //急速北京pk10 RedisKeys.JSPKS_SG_HS_LIST

    /**
     *  添加急速北京pk10 赛果缓存
     * @param list
     */
    public static void addJspksLotterySgList(List<JsbjpksLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.JSPKS_SG_HS_LIST, list);
    }

    /**
     * 获取急速北京pk10 全部赛果缓存
     * @return
     */
    public static List<JsbjpksLotterySg> getJspksLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.JSPKS_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取急速北京pk10 分段赛果缓存
     * @return
     */
    public static List<JsbjpksLotterySg> getJspksLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.JSPKS_SG_HS_LIST, pageNo, pageSize);
    }

    //幸运飞艇 RedisKeys.XYFT_SG_HS_LIST

    /**
     *  添加幸运飞艇 赛果缓存
     * @param list
     */
    public static void addXyftLotterySgList(List<XyftLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.XYFT_SG_HS_LIST, list);
    }

    /**
     * 获取幸运飞艇 全部赛果缓存
     * @return
     */
    public static List<XyftLotterySg> getXyftLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.XYFT_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取幸运飞艇 分段赛果缓存
     * @return
     */
    public static List<XyftLotterySg> getXyftLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.XYFT_SG_HS_LIST, pageNo, pageSize);
    }

    //德州幸运飞艇 RedisKeys.DZXYFT_SG_HS_LIST
    /**
     *  添加德州幸运飞艇 赛果缓存
     * @param list
     */
    public static void addDzxyftLotterySgList(List<DzxyftLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.DZXYFT_SG_HS_LIST, list);
    }

    /**
     * 获取幸运飞艇 全部赛果缓存
     * @return
     */
    public static List<DzxyftLotterySg> getDzxyftLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.DZXYFT_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取幸运飞艇 分段赛果缓存
     * @return
     */
    public static List<DzxyftLotterySg> getDzxyftLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.DZXYFT_SG_HS_LIST, pageNo, pageSize);
    }


    //德州pc蛋蛋 RedisKeys.DZPCEGG_SG_HS_LIST

    /**
     *  添加德州pc蛋蛋 赛果缓存
     * @param list
     */
    public static void addDzpceggLotterySgList(List<DzpceggLotterySg> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        redisTemplate.opsForList().rightPushAll(RedisKeys.DZPCEGG_SG_HS_LIST, list);
    }

    /**
     * 获取德州pc蛋蛋 全部赛果缓存
     * @return
     */
    public static List<DzpceggLotterySg> getDzpceggLotterySgList() {
        return redisTemplate.opsForList().range(RedisKeys.DZPCEGG_SG_HS_LIST, 0, -1);
    }

    /**
     * 获取德州pc蛋蛋 分段赛果缓存
     * @return
     */
    public static List<DzpceggLotterySg> getDzpceggLotterySgList(Integer pageNo,Integer pageSize) {
        return redisTemplate.opsForList().range(RedisKeys.DZPCEGG_SG_HS_LIST, pageNo, pageSize);
    }

    /** 设置赔率配置表list */
    public static void setOddsSettingList(List<LotteryPlayOdds> list, Integer settingId) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        String key = RedisKeys.ODDS_LIST_SETTING_KEY + settingId;
        String jsonString = JSON.toJSONString(list);
        set(key, jsonString,2l, TimeUnit.MINUTES);
    }


    /** 获取赔率配置list */
    public static List<LotteryPlayOdds> getOddsSettingList(Integer settingId) {
        if (settingId == null) {
            return null;
        }
        String key = RedisKeys.ODDS_LIST_SETTING_KEY + settingId;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        List<LotteryPlayOdds> list = JSONObject.parseArray(array.toJSONString(), LotteryPlayOdds.class);
        return list;
    }


}