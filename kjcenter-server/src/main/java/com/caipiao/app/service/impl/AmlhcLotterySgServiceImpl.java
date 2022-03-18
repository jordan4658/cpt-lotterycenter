package com.caipiao.app.service.impl;

import com.caipiao.app.service.AmlhcLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.tool.TxffcUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.AmlhcLotterySgMapper;
import com.mapper.domain.AmlhcLotterySg;
import com.mapper.domain.AmlhcLotterySgExample;
import com.mapper.domain.TxffcLotterySg;
import com.mapper.domain.TxffcLotterySgExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SslhcLotterySgServiceImpl
 * @Description: 时时六合彩服务类
 * @author: HANS
 * @date: 2019年5月21日 下午10:06:22
 */
@Service
public class AmlhcLotterySgServiceImpl implements AmlhcLotterySgService {
    private static final Logger logger = LoggerFactory.getLogger(AmlhcLotterySgServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AmlhcLotterySgMapper amlhcLotterySgMapper;

    @Override
    public ResultInfo<Map<String, Object>> getSslhcNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 查询今日已售期数
//            String openRedisKey = RedisKeys.SSLHC_OPEN_VALUE;
//            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

//			if (openCount == null) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("openStatus", LotteryResultStatus.AUTO);
//				map.put("paramTime", TimeHelper.date("yyyy-MM-dd"));
//				openCount = sslhcLotterySgMapper.openCountByExample(map);
//				redisTemplate.opsForValue().set(openRedisKey, openCount);
//			}
//			// 获取开奖总期数
//			Integer sumCount = CaipiaoSumCountEnum.SSLHC.getSumCount();
//			// 计算当日剩余未开奖次数
//			result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 获取下期数据
//            String nextRedisKey = RedisKeys.SSLHC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.AMLHC.getRedisTime();
            AmlhcLotterySg nextAmlhcLotterySg = null;
//            if(redisTemplate.hasKey(nextRedisKey)){
//                nextAmlhcLotterySg = (AmlhcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);
//            }

            if (nextAmlhcLotterySg == null) {
                nextAmlhcLotterySg = this.getNextSslhcLotterySg();
//                redisTemplate.opsForValue().set(nextRedisKey, nextAmlhcLotterySg, redisTime, TimeUnit.MINUTES);
            }
            // 缓存中取开奖结果
//            String redisKey = RedisKeys.SSLHC_RESULT_VALUE;
            AmlhcLotterySg amllhcLotterySg = null;
//            AmlhcLotterySg amllhcLotterySg = (AmlhcLotterySg) redisTemplate.opsForValue().get(redisKey);  //等cpt上线了，才放开

            if (amllhcLotterySg == null) {
                // 查询最近一期开奖信息
                amllhcLotterySg = this.getAmlhcLotterySg();
//                redisTemplate.opsForValue().set(redisKey, amllhcLotterySg);
            }

            if (nextAmlhcLotterySg != null) {
                String nextIssue = nextAmlhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextAmlhcLotterySg.getIssue();
                String txffnextIssue = amllhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : amllhcLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextAmlhcLotterySg = this.getNextSslhcLotterySg();
//                        redisTemplate.opsForValue().set(nextRedisKey, nextAmlhcLotterySg, redisTime, TimeUnit.MINUTES);
                        // 查询最近一期开奖信息
                        amllhcLotterySg = this.getAmlhcLotterySg();
//                        redisTemplate.opsForValue().set(redisKey, amllhcLotterySg);
                    }
                }
                if (amllhcLotterySg != null) {
                    String number = amllhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : amllhcLotterySg.getNumber();
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), amllhcLotterySg == null ? Constants.DEFAULT_NULL : amllhcLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
                    result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, amllhcLotterySg.getTime()));
                }

                if (nextAmlhcLotterySg != null) {
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextAmlhcLotterySg.getIssue());
                    //计算到下期时间和当前时间的距离（单位：秒）
                    Long miao = (DateUtils.getTimeMillis(nextAmlhcLotterySg.getIdealTime()) - new Date().getTime()) / 1000;
                    result.put("DAOJISHI", miao);

                    String ideatime = nextAmlhcLotterySg.getIdealTime();
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(ideatime) / 1000L);
                }

            } else {

                if (amllhcLotterySg != null) {
                    String number = amllhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : amllhcLotterySg.getNumber();
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), amllhcLotterySg == null ? Constants.DEFAULT_NULL : amllhcLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
                    result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, amllhcLotterySg.getTime()));
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.AMLHC.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return ResultInfo.ok(result);
    }

    @Override
    public ResultInfo<Map<String, Object>> lishiSg(String type, int num, String starteDate, String endDate, Integer pageNum, Integer pageSize) {
        // type: dateType 按日期查询， numberType  按搅珠期数查询
        AmlhcLotterySgExample amlhcExample = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria amlhcCriteria = amlhcExample.createCriteria();
        amlhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 20;
        }
        amlhcExample.setOffset((pageNum - 1) * pageSize);
        amlhcExample.setLimit(pageSize);
        amlhcExample.setOrderByClause("ideal_time DESC");

        if (type != null && type.equals("dateType")) {
            amlhcCriteria.andIdealTimeGreaterThanOrEqualTo(starteDate);
            amlhcCriteria.andIdealTimeLessThan(endDate + " 23:59:59");
        } else if (type != null && type.equals("numberType")) {
            AmlhcLotterySgExample amlhcExampleThis = new AmlhcLotterySgExample();
            AmlhcLotterySgExample.Criteria amlhcCriteriaThis = amlhcExampleThis.createCriteria();
            amlhcCriteriaThis.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
            amlhcExampleThis.setOffset(0);
            amlhcExampleThis.setLimit(num);
            amlhcExampleThis.setOrderByClause("ideal_time DESC");
            List<AmlhcLotterySg> amlhcLotterySgsThis = amlhcLotterySgMapper.selectByExample(amlhcExampleThis);
            if(amlhcLotterySgsThis.size() > 0){
                amlhcCriteria.andIdealTimeGreaterThanOrEqualTo(amlhcLotterySgsThis.get(amlhcLotterySgsThis.size()-1).getIdealTime());
            }
        }

        int count = amlhcLotterySgMapper.countByExample(amlhcExample);
        List<AmlhcLotterySg> amlhcLotterySgs = amlhcLotterySgMapper.selectByExample(amlhcExample);

        List<Map<String, Object>> maps = LhcUtils.lishisslhcSg(amlhcLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalSize", count);
        return ResultInfo.ok(result);

    }

    /**
     * @return SslhcLotterySg
     * @Title: getNextSslhcLotterySg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年4月29日下午9:14:27
     */
    private AmlhcLotterySg getNextSslhcLotterySg() {
        AmlhcLotterySgExample nextExample = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria nextTjsscCriteria = nextExample.createCriteria();
        nextTjsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullString(new Date()));
        nextTjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        nextExample.setOrderByClause("ideal_time ASC");
        AmlhcLotterySg nexAmlhccLotterySg = this.amlhcLotterySgMapper.selectOneByExample(nextExample);
        return nexAmlhccLotterySg;
    }

    /**
     * @return SslhcLotterySg
     * @Title: getSslhcLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月21日下午10:09:21
     */
    public AmlhcLotterySg getAmlhcLotterySg() {
        AmlhcLotterySgExample example = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria lhcCriteria = example.createCriteria();
        lhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        AmlhcLotterySg ssllhcLotterySg = amlhcLotterySgMapper.selectOneByExample(example);
        return ssllhcLotterySg;
    }

}
