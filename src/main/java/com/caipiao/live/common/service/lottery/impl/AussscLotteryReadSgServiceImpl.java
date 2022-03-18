package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.AussscLotterySg;
import com.caipiao.live.common.mybatis.entity.AussscLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.AussscLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.AussscLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AussscLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
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
 * 澳洲ssc
 *
 * @author
 * @create 2019-03-13 11:05
 **/
@Service
public class AussscLotteryReadSgServiceImpl implements AussscLotterySgServiceReadSg {
    private static final Logger logger = LoggerFactory.getLogger(AussscLotteryReadSgServiceImpl.class);
    @Autowired
    private AussscLotterySgMapper aussscLotterySgMapper;
    @Autowired
    private AussscLotterySgMapperExt aussscLotterySgMapperExt;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            String redisKey = RedisKeys.AUZSSC_RESULT_VALUE;
            AussscLotterySg aussscLotterySg = (AussscLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (aussscLotterySg == null) {
                aussscLotterySg = this.getAussscLotterySg();
                redisTemplate.opsForValue().set(redisKey, aussscLotterySg);
            }
            // 获取开奖次数
            String openRedisKey = RedisKeys.AUZSSC_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                openCount = this.selectAussscOpenCount();
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }
            result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
            // 获取开奖总期数
            Integer sumCount = CaipiaoSumCountEnum.AZSSC.getSumCount();
            // 计算当日剩余未开奖次数
            result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.AUZSSC_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.AUZSSC.getRedisTime();
            AussscLotterySg nextAussscLotterySg = (AussscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextAussscLotterySg == null) {
                nextAussscLotterySg = this.getNextAussscLotterySg();
                redisTemplate.opsForValue().set(nextRedisKey, nextAussscLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextAussscLotterySg != null) {
                String nextIssue = nextAussscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextAussscLotterySg.getIssue();
                String txffnextIssue = aussscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : aussscLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextAussscLotterySg = this.getNextAussscLotterySg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextAussscLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        aussscLotterySg = this.getAussscLotterySg();
                        redisTemplate.opsForValue().set(redisKey, aussscLotterySg);
                    }
                }

                if (aussscLotterySg != null) {
                    if (StringUtils.isEmpty(aussscLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        aussscLotterySg = this.getAussscLotterySg();
                        redisTemplate.opsForValue().set(redisKey, aussscLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), aussscLotterySg == null ? Constants.DEFAULT_NULL : aussscLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), aussscLotterySg == null ? Constants.DEFAULT_NULL : aussscLotterySg.getNumber());
                }

                if (nextAussscLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextAussscLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextAussscLotterySg.getIdealTime()) / 1000L);
                }
            } else {
                if (aussscLotterySg != null) {
                    if (StringUtils.isEmpty(aussscLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        aussscLotterySg = this.getAussscLotterySg();
                        redisTemplate.opsForValue().set(redisKey, aussscLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), aussscLotterySg == null ? Constants.DEFAULT_NULL : aussscLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), aussscLotterySg == null ? Constants.DEFAULT_NULL : aussscLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.FIVESSC.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    public AussscLotterySg getNextAussscLotterySg() {
        AussscLotterySgExample nextExample = new AussscLotterySgExample();
        AussscLotterySgExample.Criteria aussscCriteria = nextExample.createCriteria();
        aussscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        aussscCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
        nextExample.setOrderByClause("ideal_time ASC");
        AussscLotterySg nextAussscLotterySg = aussscLotterySgMapper.selectOneByExample(nextExample);
        return nextAussscLotterySg;
    }

    /**
     * @return Integer
     * @Title: selectAusactOpenCount
     * @Description: 获取当天开奖总数
     */
    public Integer selectAussscOpenCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openStatus", LotteryResultStatus.AUTO);
        map.put("paramTime", TimeHelper.date("yyyy-MM-dd") + "%");
        Integer openCount = aussscLotterySgMapperExt.openCountByExample(map);
        return openCount;
    }

    /**
     * @return AussscLotterySg
     * @Title: getAussscLotterySg
     * @Description: 获取当前开奖数据
     * @author HANS
     * @date 2019年5月4日下午7:17:47
     */
    public AussscLotterySg getAussscLotterySg() {
        AussscLotterySgExample aussscExample = new AussscLotterySgExample();
        AussscLotterySgExample.Criteria aussscCriteria = aussscExample.createCriteria();
        aussscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        aussscExample.setOrderByClause("ideal_time DESC");
        AussscLotterySg aussscLotterySg = aussscLotterySgMapper.selectOneByExample(aussscExample);
        return aussscLotterySg;
    }

    /**
     * 获取下一期官方开奖时间
     *
     * @param lastTime 当前期官方开奖时间
     * @return
     */
    private Date nextIssueTime(Date lastTime) {
        Date dateTime = DateUtils.getSecondAfter(lastTime, 160);
        return dateTime;
    }

    private String createNextIssue(String issue) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        // 判断是否已达最大值
        if ("999".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "000";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }

    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        AussscLotterySgExample example = new AussscLotterySgExample();
        AussscLotterySgExample.Criteria aussscCriteria = example.createCriteria();
//        bjpksCriteria.andNumberIsNotNull();
        aussscCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

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

        List<AussscLotterySg> aussscLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.AUSSSC_SG_HS_LIST)) {
            AussscLotterySgExample exampleOne = new AussscLotterySgExample();
            AussscLotterySgExample.Criteria aussscCriteriaOne = exampleOne.createCriteria();
            aussscCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<AussscLotterySg> aussscLotterySgsOne = aussscLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.AUSSSC_SG_HS_LIST, aussscLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            aussscLotterySgs = redisTemplate.opsForList().range(RedisKeys.AUSSSC_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            aussscLotterySgs = aussscLotterySgMapper.selectByExample(example);
        }

//        List<AussscLotterySg> tc7xcLotterySgs = aussscLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = AussscLotteryReadSgServiceImpl.lishiSg(aussscLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> lishiSg(List<AussscLotterySg> aussscLotterySgs) {
        if (aussscLotterySgs == null) {
            return null;
        }
        int totalIssue = aussscLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            AussscLotterySg sg = aussscLotterySgs.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());

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
            map.put("numberstr", removeCommand(sg.getNumber()));

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
