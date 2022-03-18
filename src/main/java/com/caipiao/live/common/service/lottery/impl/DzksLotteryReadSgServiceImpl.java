package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.DzksLotterySg;
import com.caipiao.live.common.mybatis.entity.DzksLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.DzksLotterySgMapper;
import com.caipiao.live.common.service.lottery.DzksLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 德州快三
 *
 * @author
 * @create 2019-03-13 11:05
 **/
@Service
public class DzksLotteryReadSgServiceImpl implements DzksLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(DzksLotteryReadSgServiceImpl.class);
    @Autowired
    private DzksLotterySgMapper dzksLotterySgMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            String redisKey = RedisKeys.DZKS_RESULT_VALUE;
            DzksLotterySg dzksLotterySg = (DzksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (dzksLotterySg == null) {
                dzksLotterySg = this.getDzksLotterySg();
                redisTemplate.opsForValue().set(redisKey, dzksLotterySg);
            }
            // 获取开奖次数
            String openRedisKey = RedisKeys.DZKS_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                openCount = Integer.valueOf(dzksLotterySg.getIssue().substring(8));
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }
            result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
            // 获取开奖总期数
            Integer sumCount = CaipiaoSumCountEnum.DZKS.getSumCount();
            // 计算当日剩余未开奖次数
            result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.DZKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.DZKS.getRedisTime();
            DzksLotterySg nextDzksLotterySg = (DzksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextDzksLotterySg == null) {
                nextDzksLotterySg = this.getNextDzksLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextDzksLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextDzksLotterySg != null) {
                String nextIssue = nextDzksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextDzksLotterySg.getIssue();
                String dzksnextIssue = dzksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : dzksLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(dzksnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(dzksnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextDzksLotterySg = this.getNextDzksLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextDzksLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        dzksLotterySg = this.getDzksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzksLotterySg);
                    }
                }

                if (dzksLotterySg != null) {
                    if (StringUtils.isEmpty(dzksLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        dzksLotterySg = this.getDzksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzksLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), dzksLotterySg == null ? Constants.DEFAULT_NULL : dzksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), dzksLotterySg == null ? Constants.DEFAULT_NULL : dzksLotterySg.getNumber());
                }

                if (nextDzksLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextDzksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextDzksLotterySg.getIdealTime().getTime() / 1000L);
                }
            } else {
                if (dzksLotterySg != null) {
                    if (StringUtils.isEmpty(dzksLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        dzksLotterySg = this.getDzksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzksLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), dzksLotterySg == null ? Constants.DEFAULT_NULL : dzksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), dzksLotterySg == null ? Constants.DEFAULT_NULL : dzksLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.DZKS.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    public DzksLotterySg getNextDzksLotterySg() {
        DzksLotterySgExample nextExample = new DzksLotterySgExample();
        DzksLotterySgExample.Criteria dzkscriteria = nextExample.createCriteria();
        dzkscriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        dzkscriteria.andIdealTimeGreaterThan(new Date());
        nextExample.setOrderByClause("ideal_time ASC");
        DzksLotterySg nextDzksLotterySg = dzksLotterySgMapper.selectOneByExample(nextExample);
        return nextDzksLotterySg;
    }

    /**
     * @return AussscLotterySg
     * @Title: getDzksLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月4日下午7:17:47
     */
    public DzksLotterySg getDzksLotterySg() {
        DzksLotterySgExample dzksLotterySgExample = new DzksLotterySgExample();
        DzksLotterySgExample.Criteria dzksLotterySgExampleCriteria = dzksLotterySgExample.createCriteria();
        dzksLotterySgExampleCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        dzksLotterySgExample.setOrderByClause("ideal_time DESC");
        DzksLotterySg dzksLotterySg = dzksLotterySgMapper.selectOneByExample(dzksLotterySgExample);
        return dzksLotterySg;
    }


    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        DzksLotterySgExample example = new DzksLotterySgExample();
        DzksLotterySgExample.Criteria dzksCriteria = example.createCriteria();
        dzksCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<DzksLotterySg> dzksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.DZKS_SG_HS_LIST)) {
            DzksLotterySgExample exampleOne = new DzksLotterySgExample();
            DzksLotterySgExample.Criteria dzksCriteriaOne = exampleOne.createCriteria();
            dzksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<DzksLotterySg> dzksLotterySgsOne = dzksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.DZKS_SG_HS_LIST, dzksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            dzksLotterySgs = redisTemplate.opsForList().range(RedisKeys.DZKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            dzksLotterySgs = dzksLotterySgMapper.selectByExample(example);
        }

//        List<DzksLotterySg> dzksLotterySgs = dzksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = this.lishiSg(dzksLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> lishiSg(List<DzksLotterySg> dzksLotterySgs) {
        if (dzksLotterySgs == null) {
            return null;
        }
        int totalIssue = dzksLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            DzksLotterySg sg = dzksLotterySgs.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put(AppMianParamEnum.TIME.getParamEnName(), DateUtils.formatDate(sg.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

//            if(StringUtils.isEmpty(sg.getTime())){
//                map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getIdealTime());
//            }else{
//                map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());
//            }

//            if(StringUtils.isEmpty(sg.getNumber())){
//                map.put(Constants.SGSIGN, 0);
//            }else{
//                map.put(Constants.SGSIGN, 1);
//                map.put("number", sg.getNumber());
//                map.put("numberstr", removeCommand(sg.getNumber()));
//            }

            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());

            result.add(map);
        }
        return result;
    }

    /**
     * @param number void
     * @Title: RemoveCommand
     * @Description: 显示格式，去除开奖号码中的逗号
     * @author admin
     * @date 2019年4月13日下午10:23:39
     */
    public static String removeCommand(String number) {
        if (StringUtils.isEmpty(number)) {
            return number;
        }

        String noCommandNumber = number.replace(",", "");
        return noCommandNumber;
    }
}
