package com.caipiao.app.service.impl;


import com.caipiao.app.mapper.lottery.XyftscBeanMapper;
import com.caipiao.app.service.XyftscLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.constant.LotteryInformationType;
import com.caipiao.core.library.constant.StatusCode;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.enums.CaipiaoRedisTimeEnum;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.TimeHelper;
import com.caipiao.core.library.tool.XyftUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.caipiao.core.library.vo.common.*;
import com.mapper.XyftscLotterySgMapper;
import com.mapper.domain.XyftscLotterySg;
import com.mapper.domain.XyftscLotterySgExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 幸运飞艇
 *
 * @author lzy
 * @create 2018-07-30 16:52
 **/
@Service
public class XyftscLotterySgServiceImpl implements XyftscLotterySgService {

    private static final Logger logger = LoggerFactory.getLogger(XyftscLotterySgServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private XyftscLotterySgMapper xyftscLotterySgMapper;
    @Autowired
    private XyftscBeanMapper xyftscBeanMapper;

    @Override
    public ResultInfo<List<KjlsVO>> historySg(String type, String date, Integer pageNum, Integer pageSize) {
        // 幸运飞艇历史开奖
        if (!LotteryInformationType.BJPKS_LSKJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        XyftscLotterySgExample example = new XyftscLotterySgExample();
        XyftscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andNumberIsNotNull();
        if (StringUtils.isNotBlank(date) && date.equals(TimeHelper.date("yyyy-MM-dd"))) {
            date = XyftUtils.dateStr();
        }

        if (StringUtils.isNotBlank(date)) {
            criteria.andIssueLike(date.replace("-", "") + "%");
        }
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("`ideal_time` DESC");
        List<XyftscLotterySg> xyftscLotterySgs = xyftscLotterySgMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(xyftscLotterySgs)) {
            xyftscLotterySgs = new ArrayList<>();
        }
        List<KjlsVO> result = XyftUtils.historyScSg(xyftscLotterySgs);
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishiSg(String issue, String date, Integer pageNum, Integer pageSize) {
        XyftscLotterySgExample example = new XyftscLotterySgExample();
        XyftscLotterySgExample.Criteria sgCriteria = example.createCriteria();
//        sgCriteria.andNumberIsNotNull();
//        criteria.andWanIsNotNull();
        sgCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);
        if (StringUtils.isNotEmpty(issue)) {
            sgCriteria.andIssueEqualTo(issue);
        }
        if (StringUtils.isNotEmpty(date)) {
            Date dateD = DateUtils.parseDate(date, DateUtils.datePattern);
            sgCriteria.andIdealTimeGreaterThanOrEqualTo(dateD);
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(dateD);
            calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
            calendarEnd.add(Calendar.HOUR_OF_DAY, 5);
            sgCriteria.andIdealTimeLessThanOrEqualTo(calendarEnd.getTime());
        }
//        sgCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNum - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("`ideal_time` DESC");

        List<XyftscLotterySg> xyftscLotterySgs = xyftscLotterySgMapper.selectByExample(example);

        List<Map<String, Object>> maps = XyftUtils.lishiScSg(xyftscLotterySgs);
        for (Map<String, Object> singleMap : maps) {
            Date singled = (Date) singleMap.get("time");
            singled = DateUtils.add(singled,Calendar.HOUR_OF_DAY,-6);
            singleMap.put("time",singled.getTime()/1000l);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfo() {
        Map<String, Object> result = DefaultResultUtil.getNullResult();
        try {
            // 缓存中取开奖数量
            String openRedisKey = RedisKeys.XYFEITSC_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                Map<String, Object> map = new HashMap<String, Object>();
                openCount = xyftscBeanMapper.openCountByExample(LotteryResultStatus.AUTO, TimeHelper.date("yyyy-MM-dd") + "%");
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }

            if (openCount != null) {
                result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
                // 获取开奖总期数
                Integer sumCount = CaipiaoSumCountEnum.XYFEITSC.getSumCount();
                // 计算当日剩余未开奖次数
                result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            }
            String nextRedisKey = RedisKeys.XYFEITSC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.XYFEITSC.getRedisTime();
            XyftscLotterySg nextXyftscLotterySg = (XyftscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextXyftscLotterySg == null) {
                nextXyftscLotterySg = this.getNextXyftscLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextXyftscLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // 缓存中取开奖结果
            String redisKey = RedisKeys.XYFEITSC_RESULT_VALUE;
            XyftscLotterySg xyftscLotterySg = (XyftscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (xyftscLotterySg == null) {
                xyftscLotterySg = this.getXyftscLotterySg();
                redisTemplate.opsForValue().set(redisKey, xyftscLotterySg);
            }

            if (nextXyftscLotterySg != null) {
                String nextIssue = nextXyftscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextXyftscLotterySg.getIssue();
                String txffnextIssue = nextXyftscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextXyftscLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextXyftscLotterySg = this.getNextXyftscLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextXyftscLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        xyftscLotterySg = this.getXyftscLotterySg();
                        redisTemplate.opsForValue().set(redisKey, xyftscLotterySg);
                    }
                }
                if (xyftscLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), xyftscLotterySg == null ? Constants.DEFAULT_NULL : xyftscLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), xyftscLotterySg == null ? Constants.DEFAULT_NULL : xyftscLotterySg.getNumber());
                }

                if (nextXyftscLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextXyftscLotterySg.getIssue());
                    String ideatime = DateUtils.formatDate(nextXyftscLotterySg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS);
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(ideatime) / 1000L - 6 * 3600L);
                    result.put("daojishi", DateUtils.getTimeMillis(ideatime) / 1000L - new Date().getTime()/1000);

                }
            } else {
                if (xyftscLotterySg != null) {
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), xyftscLotterySg == null ? Constants.DEFAULT_NULL : xyftscLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), xyftscLotterySg == null ? Constants.DEFAULT_NULL : xyftscLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.XYFEITSC.getTagType() + "异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }

    /**
     * @return XyftscLotterySg
     * @Title: getXyftscLotterySg
     * @Description: 获取当前开奖数据
     * @author admin
     * @date 2019年5月1日下午4:09:43
     */
    public XyftscLotterySg getXyftscLotterySg() {
        XyftscLotterySgExample example = new XyftscLotterySgExample();
        XyftscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        XyftscLotterySg xyftscLotterySg = xyftscLotterySgMapper.selectOneByExample(example);
        return xyftscLotterySg;
    }

    /**
     * @return XyftscLotterySg
     * @Title: getXyftscLotterySg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年4月29日下午9:30:37
     */
    public XyftscLotterySg getNextXyftscLotterySg() {
        XyftscLotterySgExample example = new XyftscLotterySgExample();
        XyftscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        criteria.andIdealTimeGreaterThan(new Date());
        example.setOrderByClause("ideal_time ASC");
        XyftscLotterySg nextXyftscLotterySg = xyftscLotterySgMapper.selectOneByExample(example);
        return nextXyftscLotterySg;
    }

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfoWeb() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "幸运飞艇");

        // 查询最近一期信息
        XyftscLotterySgExample example = new XyftscLotterySgExample();
        XyftscLotterySgExample.Criteria sgCriteria = example.createCriteria();
        sgCriteria.andNumberIsNotNull();
        example.setOrderByClause("`ideal_time` DESC");
        XyftscLotterySg xyftscLotterySg = xyftscLotterySgMapper.selectOneByExample(example);
        if (xyftscLotterySg != null) {
            result.put("issue", xyftscLotterySg.getIssue());
            result.put("number", xyftscLotterySg.getNumber());
            result.put("time", xyftscLotterySg.getTime());
        } else {
            result.put("issue", null);
            result.put("number", null);
            result.put("time", null);
        }
        return ResultInfo.ok(result);
    }

}
