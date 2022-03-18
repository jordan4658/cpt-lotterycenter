package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.DzpceggLotterySg;
import com.caipiao.live.common.mybatis.entity.DzpceggLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.DzpceggLotterySgMapper;
import com.caipiao.live.common.service.lottery.DzpceggLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.lottery.PceggUtil;
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
 * 德州pc蛋蛋
 *
 * @author
 * @create 2019-03-13 11:05
 **/
@Service
public class DzpceggLotteryReadSgServiceImpl implements DzpceggLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(DzpceggLotteryReadSgServiceImpl.class);
    @Autowired
    private DzpceggLotterySgMapper dzpceggLotterySgMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            String redisKey = RedisKeys.DZPCDAND_RESULT_VALUE;
            DzpceggLotterySg dzpceggLotterySg = (DzpceggLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (dzpceggLotterySg == null) {
                dzpceggLotterySg = this.getDzpceggLotterySg();
                redisTemplate.opsForValue().set(redisKey, dzpceggLotterySg);
            }
            // 获取开奖次数
            String openRedisKey = RedisKeys.DZPCDAND_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                openCount = Integer.valueOf(dzpceggLotterySg.getIssue().substring(8));
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }
            result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
            // 获取开奖总期数
            Integer sumCount = CaipiaoSumCountEnum.DZPCEGG.getSumCount();
            // 计算当日剩余未开奖次数
            result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.DZPCDAND_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.DZPCDAND.getRedisTime();
            DzpceggLotterySg nextDzpceggLotterySg = (DzpceggLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextDzpceggLotterySg == null) {
                nextDzpceggLotterySg = this.getNextDzpceggLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextDzpceggLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextDzpceggLotterySg != null) {
                String nextIssue = nextDzpceggLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextDzpceggLotterySg.getIssue();
                String txffnextIssue = dzpceggLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : dzpceggLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextDzpceggLotterySg = this.getNextDzpceggLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextDzpceggLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        dzpceggLotterySg = this.getDzpceggLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzpceggLotterySg);
                    }
                }

                if (dzpceggLotterySg != null) {
                    if (StringUtils.isEmpty(dzpceggLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        dzpceggLotterySg = this.getDzpceggLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzpceggLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), dzpceggLotterySg == null ? Constants.DEFAULT_NULL : dzpceggLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), dzpceggLotterySg == null ? Constants.DEFAULT_NULL : dzpceggLotterySg.getNumber());
                }

                if (nextDzpceggLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextDzpceggLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextDzpceggLotterySg.getIdealTime().getTime() / 1000L);
                }
            } else {
                if (dzpceggLotterySg != null) {
                    if (StringUtils.isEmpty(dzpceggLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        dzpceggLotterySg = this.getDzpceggLotterySg();
                        redisTemplate.opsForValue().set(redisKey, dzpceggLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), dzpceggLotterySg == null ? Constants.DEFAULT_NULL : dzpceggLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), dzpceggLotterySg == null ? Constants.DEFAULT_NULL : dzpceggLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.DZPCDAND.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    public DzpceggLotterySg getNextDzpceggLotterySg() {
        DzpceggLotterySgExample nextExample = new DzpceggLotterySgExample();
        DzpceggLotterySgExample.Criteria dzpceggcriteria = nextExample.createCriteria();
        dzpceggcriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        dzpceggcriteria.andIdealTimeGreaterThan(new Date());
        nextExample.setOrderByClause("ideal_time ASC");
        DzpceggLotterySg nextDzpceggLotterySg = dzpceggLotterySgMapper.selectOneByExample(nextExample);
        return nextDzpceggLotterySg;
    }

    /**
     * @return AussscLotterySg
     * @Title: getDzpceggLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月4日下午7:17:47
     */
    public DzpceggLotterySg getDzpceggLotterySg() {
        DzpceggLotterySgExample dzpceggLotterySgExample = new DzpceggLotterySgExample();
        DzpceggLotterySgExample.Criteria dzpceggLotterySgExampleCriteria = dzpceggLotterySgExample.createCriteria();
        dzpceggLotterySgExampleCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        dzpceggLotterySgExample.setOrderByClause("ideal_time DESC");
        DzpceggLotterySg dzpceggLotterySg = dzpceggLotterySgMapper.selectOneByExample(dzpceggLotterySgExample);
        return dzpceggLotterySg;
    }


    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        DzpceggLotterySgExample example = new DzpceggLotterySgExample();
        DzpceggLotterySgExample.Criteria dzpceggCriteria = example.createCriteria();
        dzpceggCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
//        criteria.andWanIsNotNull();

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

        List<DzpceggLotterySg> dzpceggLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.DZPCEGG_SG_HS_LIST)) {
            DzpceggLotterySgExample exampleOne = new DzpceggLotterySgExample();
            DzpceggLotterySgExample.Criteria dzpceggCriteriaOne = exampleOne.createCriteria();
            dzpceggCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<DzpceggLotterySg> dzpceggLotterySgsOne = dzpceggLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.DZPCEGG_SG_HS_LIST, dzpceggLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            dzpceggLotterySgs = redisTemplate.opsForList().range(RedisKeys.DZPCEGG_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            dzpceggLotterySgs = dzpceggLotterySgMapper.selectByExample(example);
        }

//        List<DzpceggLotterySg> dzpceggLotterySgs = dzpceggLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = this.lishiSg(dzpceggLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> lishiSg(List<DzpceggLotterySg> dzpceggLotterySgs) {
        if (dzpceggLotterySgs == null) {
            return null;
        }
        int totalIssue = dzpceggLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            DzpceggLotterySg sg = dzpceggLotterySgs.get(i);
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

            Integer sum = PceggUtil.sumNumber(sg.getNumber());
            map.put("sum", sum);
            map.put("bigOrSmall", PceggUtil.checkBigOrSmall(sum));
            map.put("singleOrDouble", PceggUtil.checkSingleOrDouble(sum));
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
