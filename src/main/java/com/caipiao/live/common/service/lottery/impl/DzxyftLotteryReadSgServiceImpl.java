package com.caipiao.live.common.service.lottery.impl;


import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.DzxyftLotterySg;
import com.caipiao.live.common.mybatis.entity.DzxyftLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.DzxyftLotterySgMapper;
import com.caipiao.live.common.service.lottery.DzxyftLotterySgServiceReadSg;
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
 * 德州幸运飞艇
 *
 * @author
 * @create 2019-03-13 11:05
 **/
@Service
public class DzxyftLotteryReadSgServiceImpl implements DzxyftLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(DzxyftLotteryReadSgServiceImpl.class);
    @Autowired
    private DzxyftLotterySgMapper dzxyftLotterySgMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            String redisKey = RedisKeys.DZXYFEIT_RESULT_VALUE;
            DzxyftLotterySg dzxyftLotterySg = (DzxyftLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (dzxyftLotterySg == null) {
                dzxyftLotterySg = this.getDzxyftLotterySg();
                redisTemplate.opsForValue().set(redisKey, dzxyftLotterySg);
            }
            // 获取开奖次数
            String openRedisKey = RedisKeys.DZXYFEIT_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                openCount = Integer.valueOf(dzxyftLotterySg.getIssue().substring(8));
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }
            result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
            // 获取开奖总期数
            Integer sumCount = CaipiaoSumCountEnum.DZXYFT.getSumCount();
            // 计算当日剩余未开奖次数
            result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.DZXYFEIT_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.DZXYFEIT.getRedisTime();
            DzxyftLotterySg nextDzxyftLotterySg = (DzxyftLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextDzxyftLotterySg == null) {
                nextDzxyftLotterySg = this.getNextDzxyftLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextDzxyftLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextDzxyftLotterySg != null) {
                String nextIssue = nextDzxyftLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextDzxyftLotterySg.getIssue();
                String dzxyftnextIssue = dzxyftLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : dzxyftLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(dzxyftnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(dzxyftnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextDzxyftLotterySg = this.getNextDzxyftLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextDzxyftLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        dzxyftLotterySg = this.getDzxyftLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzxyftLotterySg);
                    }
                }

                if (dzxyftLotterySg != null) {
                    if (StringUtils.isEmpty(dzxyftLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        dzxyftLotterySg = this.getDzxyftLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzxyftLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), dzxyftLotterySg == null ? Constants.DEFAULT_NULL : dzxyftLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), dzxyftLotterySg == null ? Constants.DEFAULT_NULL : dzxyftLotterySg.getNumber());
                }

                if (nextDzxyftLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextDzxyftLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextDzxyftLotterySg.getIdealTime().getTime() / 1000L);
                }
            } else {
                if (dzxyftLotterySg != null) {
                    if (StringUtils.isEmpty(dzxyftLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        dzxyftLotterySg = this.getDzxyftLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzxyftLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), dzxyftLotterySg == null ? Constants.DEFAULT_NULL : dzxyftLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), dzxyftLotterySg == null ? Constants.DEFAULT_NULL : dzxyftLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.DZXYFEIT.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    public DzxyftLotterySg getNextDzxyftLotterySg() {
        DzxyftLotterySgExample nextExample = new DzxyftLotterySgExample();
        DzxyftLotterySgExample.Criteria dzxyftcriteria = nextExample.createCriteria();
        dzxyftcriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        dzxyftcriteria.andIdealTimeGreaterThan(new Date());
        nextExample.setOrderByClause("ideal_time ASC");
        DzxyftLotterySg nextDzxyftLotterySg = dzxyftLotterySgMapper.selectOneByExample(nextExample);
        return nextDzxyftLotterySg;
    }

    /**
     * @return AussscLotterySg
     * @Title: getDzxyftLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月4日下午7:17:47
     */
    public DzxyftLotterySg getDzxyftLotterySg() {
        DzxyftLotterySgExample dzxyftLotterySgExample = new DzxyftLotterySgExample();
        DzxyftLotterySgExample.Criteria dzxyftLotterySgExampleCriteria = dzxyftLotterySgExample.createCriteria();
        dzxyftLotterySgExampleCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        dzxyftLotterySgExample.setOrderByClause("ideal_time DESC");
        DzxyftLotterySg dzxyftLotterySg = dzxyftLotterySgMapper.selectOneByExample(dzxyftLotterySgExample);
        return dzxyftLotterySg;
    }


    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        DzxyftLotterySgExample example = new DzxyftLotterySgExample();
        DzxyftLotterySgExample.Criteria dzxyftCriteria = example.createCriteria();
        dzxyftCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
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

        List<DzxyftLotterySg> dzxyftLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.DZXYFT_SG_HS_LIST)) {
            DzxyftLotterySgExample exampleOne = new DzxyftLotterySgExample();
            DzxyftLotterySgExample.Criteria cqsscCriteriaOne = exampleOne.createCriteria();
            cqsscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<DzxyftLotterySg> dzxyftLotterySgsOne = dzxyftLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.DZXYFT_SG_HS_LIST, dzxyftLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            dzxyftLotterySgs = redisTemplate.opsForList().range(RedisKeys.DZXYFT_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            dzxyftLotterySgs = dzxyftLotterySgMapper.selectByExample(example);
        }

//        List<DzxyftLotterySg> dzxyftLotterySgs = dzxyftLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = this.lishiSg(dzxyftLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> lishiSg(List<DzxyftLotterySg> dzxtftLotterySgs) {
        if (dzxtftLotterySgs == null) {
            return null;
        }
        int totalIssue = dzxtftLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            DzxyftLotterySg sg = dzxtftLotterySgs.get(i);
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
