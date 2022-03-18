package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.XjplhcLotterySg;
import com.caipiao.live.common.mybatis.entity.XjplhcLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.XjplhcLotterySgMapper;
import com.caipiao.live.common.service.lottery.XjplhcLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.lottery.LhcUtils;
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
 * 新加坡六合彩
 *
 * @author
 * @create 2019-03-13 11:05
 **/
@Service
public class XjplhcLotteryReadSgServiceImpl implements XjplhcLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(XjplhcLotteryReadSgServiceImpl.class);
    @Autowired
    private XjplhcLotterySgMapper xjplhcLotterySgMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            String redisKey = RedisKeys.XJPLHC_RESULT_VALUE;
            XjplhcLotterySg xjplhcLotterySg = (XjplhcLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (xjplhcLotterySg == null) {
                xjplhcLotterySg = this.getXjplhcLotterySg();
                redisTemplate.opsForValue().set(redisKey, xjplhcLotterySg);
            }
            // 获取开奖次数
            String openRedisKey = RedisKeys.XJPLHC_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                openCount = Integer.valueOf(xjplhcLotterySg.getIssue().substring(8));
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }
            result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
            // 获取开奖总期数
            Integer sumCount = CaipiaoSumCountEnum.XJPLHC.getSumCount();
            // 计算当日剩余未开奖次数
            result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.XJPLHC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.XJPLHC.getRedisTime();
            XjplhcLotterySg nextXjplhcLotterySg = (XjplhcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextXjplhcLotterySg == null) {
                nextXjplhcLotterySg = this.getNextXjplhcLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextXjplhcLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextXjplhcLotterySg != null) {
                String nextIssue = nextXjplhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextXjplhcLotterySg.getIssue();
                String xjplhcnextIssue = xjplhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : xjplhcLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(xjplhcnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(xjplhcnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextXjplhcLotterySg = this.getNextXjplhcLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextXjplhcLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        xjplhcLotterySg = this.getXjplhcLotterySg();
                        redisTemplate.opsForValue().set(redisKey, xjplhcLotterySg);
                    }
                }

                if (xjplhcLotterySg != null) {
                    if (StringUtils.isEmpty(xjplhcLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        xjplhcLotterySg = this.getXjplhcLotterySg();
                        redisTemplate.opsForValue().set(redisKey, xjplhcLotterySg);
                    }
                    String number = xjplhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : xjplhcLotterySg.getNumber();
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), xjplhcLotterySg == null ? Constants.DEFAULT_NULL : xjplhcLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
                    result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, DateUtils.formatDate(xjplhcLotterySg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)));
                }

                if (nextXjplhcLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextXjplhcLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), nextXjplhcLotterySg.getIdealTime().getTime() / 1000L);
                }
            } else {
                if (xjplhcLotterySg != null) {
                    if (StringUtils.isEmpty(xjplhcLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        xjplhcLotterySg = this.getXjplhcLotterySg();
                        redisTemplate.opsForValue().set(redisKey, xjplhcLotterySg);
                    }
                    String number = xjplhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : xjplhcLotterySg.getNumber();
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), xjplhcLotterySg == null ? Constants.DEFAULT_NULL : xjplhcLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
                    result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, DateUtils.formatDate(xjplhcLotterySg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)));
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.XJPLHC.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    public XjplhcLotterySg getNextXjplhcLotterySg() {
        XjplhcLotterySgExample nextExample = new XjplhcLotterySgExample();
        XjplhcLotterySgExample.Criteria xjplhccriteria = nextExample.createCriteria();
        xjplhccriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        xjplhccriteria.andIdealTimeGreaterThan(new Date());
        nextExample.setOrderByClause("ideal_time ASC");
        XjplhcLotterySg nextXjplhcLotterySg = xjplhcLotterySgMapper.selectOneByExample(nextExample);
        return nextXjplhcLotterySg;
    }

    /**
     * @return AussscLotterySg
     * @Title: getXjplhcLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月4日下午7:17:47
     */
    public XjplhcLotterySg getXjplhcLotterySg() {
        XjplhcLotterySgExample xjplhcLotterySgExample = new XjplhcLotterySgExample();
        XjplhcLotterySgExample.Criteria xjplhcLotterySgExampleCriteria = xjplhcLotterySgExample.createCriteria();
        xjplhcLotterySgExampleCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        xjplhcLotterySgExample.setOrderByClause("ideal_time DESC");
        XjplhcLotterySg xjplhcLotterySg = xjplhcLotterySgMapper.selectOneByExample(xjplhcLotterySgExample);
        return xjplhcLotterySg;
    }


    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        XjplhcLotterySgExample example = new XjplhcLotterySgExample();
        XjplhcLotterySgExample.Criteria xjplhcCriteria = example.createCriteria();
        xjplhcCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
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

        List<XjplhcLotterySg> xjplhcLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.XJPLHC_SG_HS_LIST)) {
            XjplhcLotterySgExample exampleOne = new XjplhcLotterySgExample();
            XjplhcLotterySgExample.Criteria xjplhcCriteriaOne = exampleOne.createCriteria();
            xjplhcCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<XjplhcLotterySg> xjplhcLotterySgsOne = xjplhcLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.XJPLHC_SG_HS_LIST, xjplhcLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            xjplhcLotterySgs = redisTemplate.opsForList().range(RedisKeys.XJPLHC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            xjplhcLotterySgs = xjplhcLotterySgMapper.selectByExample(example);
        }

//        List<XjplhcLotterySg> xjplhcLotterySgs = xjplhcLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = this.lishiSg(xjplhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> lishiSg(List<XjplhcLotterySg> xjplhcLotterySgs) {
        if (xjplhcLotterySgs == null) {
            return null;
        }
        int totalIssue = xjplhcLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            XjplhcLotterySg sg = xjplhcLotterySgs.get(i);
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

            map.put("number", sg.getNumber().split(","));
            map.put("numberstr", sg.getNumber());
            map.put("shengxiao2", LhcUtils.getNumberZodiac(sg.getNumber(), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)));
            map.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(sg.getNumber(), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)).split(","));
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
