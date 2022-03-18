package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.AzksLotterySg;
import com.caipiao.live.common.mybatis.entity.AzksLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.AzksLotterySgMapper;
import com.caipiao.live.common.service.lottery.AzksLotterySgServiceReadSg;
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
 * 澳洲快三
 *
 * @author
 * @create 2019-03-13 11:05
 **/
@Service
public class AzksLotteryReadSgServiceImpl implements AzksLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(AzksLotteryReadSgServiceImpl.class);
    @Autowired
    private AzksLotterySgMapper azksLotterySgMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            String redisKey = RedisKeys.AZKS_RESULT_VALUE;
            AzksLotterySg azksLotterySg = (AzksLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (azksLotterySg == null) {
                azksLotterySg = this.getAzksLotterySg();
                redisTemplate.opsForValue().set(redisKey, azksLotterySg);
            }
            // 获取开奖次数
            String openRedisKey = RedisKeys.AZKS_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                openCount = Integer.valueOf(azksLotterySg.getIssue().substring(8));
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }
            result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
            // 获取开奖总期数
            Integer sumCount = CaipiaoSumCountEnum.AZKS.getSumCount();
            // 计算当日剩余未开奖次数
            result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.AZKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.AZKS.getRedisTime();
            AzksLotterySg nextAzksLotterySg = (AzksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextAzksLotterySg == null) {
                nextAzksLotterySg = this.getNextAzksLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextAzksLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextAzksLotterySg != null) {
                String nextIssue = nextAzksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextAzksLotterySg.getIssue();
                String azksnextIssue = azksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : azksLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(azksnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(azksnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextAzksLotterySg = this.getNextAzksLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextAzksLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        azksLotterySg = this.getAzksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, azksLotterySg);
                    }
                }

                if (azksLotterySg != null) {
                    if (StringUtils.isEmpty(azksLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        azksLotterySg = this.getAzksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, azksLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), azksLotterySg == null ? Constants.DEFAULT_NULL : azksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), azksLotterySg == null ? Constants.DEFAULT_NULL : azksLotterySg.getNumber());
                }

                if (nextAzksLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextAzksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextAzksLotterySg.getIdealTime().getTime() / 1000L);
                }
            } else {
                if (azksLotterySg != null) {
                    if (StringUtils.isEmpty(azksLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        azksLotterySg = this.getAzksLotterySg();
                        redisTemplate.opsForValue().set(redisKey, azksLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), azksLotterySg == null ? Constants.DEFAULT_NULL : azksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), azksLotterySg == null ? Constants.DEFAULT_NULL : azksLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.AZKS.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    public AzksLotterySg getNextAzksLotterySg() {
        AzksLotterySgExample nextExample = new AzksLotterySgExample();
        AzksLotterySgExample.Criteria azkscriteria = nextExample.createCriteria();
        azkscriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        azkscriteria.andIdealTimeGreaterThan(new Date());
        nextExample.setOrderByClause("ideal_time ASC");
        AzksLotterySg nextAzksLotterySg = azksLotterySgMapper.selectOneByExample(nextExample);
        return nextAzksLotterySg;
    }

    /**
     * @return AussscLotterySg
     * @Title: getAzksLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月4日下午7:17:47
     */
    public AzksLotterySg getAzksLotterySg() {
        AzksLotterySgExample azksLotterySgExample = new AzksLotterySgExample();
        AzksLotterySgExample.Criteria azksLotterySgExampleCriteria = azksLotterySgExample.createCriteria();
        azksLotterySgExampleCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        azksLotterySgExample.setOrderByClause("ideal_time DESC");
        AzksLotterySg azksLotterySg = azksLotterySgMapper.selectOneByExample(azksLotterySgExample);
        return azksLotterySg;
    }


    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        AzksLotterySgExample example = new AzksLotterySgExample();
        AzksLotterySgExample.Criteria azksCriteria = example.createCriteria();
        azksCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
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

        List<AzksLotterySg> azksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.AZKS_SG_HS_LIST)) {
            AzksLotterySgExample exampleOne = new AzksLotterySgExample();
            AzksLotterySgExample.Criteria azksCriteriaOne = exampleOne.createCriteria();
            azksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<AzksLotterySg> azksLotterySgsOne = azksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.AZKS_SG_HS_LIST, azksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            azksLotterySgs = redisTemplate.opsForList().range(RedisKeys.AZKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            azksLotterySgs = azksLotterySgMapper.selectByExample(example);
        }

//        List<AzksLotterySg> azksLotterySgs = azksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = this.lishiSg(azksLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> lishiSg(List<AzksLotterySg> azksLotterySgs) {
        if (azksLotterySgs == null) {
            return null;
        }
        int totalIssue = azksLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            AzksLotterySg sg = azksLotterySgs.get(i);
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
