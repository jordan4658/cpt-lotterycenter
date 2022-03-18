package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.mybatis.entity.AuspksLotterySg;
import com.caipiao.live.common.mybatis.entity.AuspksLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.AuspksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.AuspksLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AuspksLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.StringUtils;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.NnAzOperationUtils;
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
 * 澳洲PK10
 *
 * @author
 * @create 2019-03-13 11:05
 **/
@Service
public class AuspksLotteryReadSgServiceImpl implements AuspksLotterySgServiceReadSg {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AuspksLotterySgMapper auspksLotterySgMapper;
    @Autowired
    private AuspksLotterySgMapperExt auspksLotterySgMapperExt;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 缓存中取开奖结果
            String redisKey = RedisKeys.AUSPKS_RESULT_VALUE;
            AuspksLotterySg auspksLotterySg = null;
            if (redisTemplate.hasKey(redisKey)) {
                auspksLotterySg = (AuspksLotterySg) redisTemplate.opsForValue().get(redisKey);
            } else {
                auspksLotterySg = this.selectAuspksLotterySg();
                if (null != auspksLotterySg) {
                    redisTemplate.opsForValue().set(redisKey, auspksLotterySg);
                }
            }
            // 获取开奖次数
            String openRedisKey = RedisKeys.AUSPKS_OPEN_VALUE;
            Integer openCount = 0;
            if (redisTemplate.hasKey(openRedisKey)) {
                openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);
            } else {
                openCount = this.selectAuspksOpenCount();
                if (null != openCount) {
                    redisTemplate.opsForValue().set(openRedisKey, openCount);
                }
            }

            // 获取开奖总期数
            Integer sumCount = CaipiaoSumCountEnum.AUSPKS.getSumCount();
            result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
            // 计算当日剩余未开奖次数
            result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.AUSPKS_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.AUSPKS.getRedisTime();
            AuspksLotterySg nextAuspksLotterySg = null;
            if (redisTemplate.hasKey(nextRedisKey)) {
                nextAuspksLotterySg = (AuspksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);
            } else {
                nextAuspksLotterySg = this.queryAuspksLotterySgNextSg();
                if (null != nextAuspksLotterySg) {
                    redisTemplate.opsForValue().set(nextRedisKey, nextAuspksLotterySg, redisTime, TimeUnit.MINUTES);

                }
            }

            if (nextAuspksLotterySg != null) {
                String nextIssue = nextAuspksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextAuspksLotterySg.getIssue();
                String txffnextIssue = auspksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : auspksLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextAuspksLotterySg = this.queryAuspksLotterySgNextSg();
                        if (null != nextAuspksLotterySg) {
                            redisTemplate.opsForValue().set(nextRedisKey, nextAuspksLotterySg, redisTime, TimeUnit.MINUTES);
                        }
                        // 获取当前开奖数据
                        auspksLotterySg = this.selectAuspksLotterySg();
                        if (null != auspksLotterySg) {
                            redisTemplate.opsForValue().set(redisKey, auspksLotterySg);
                        }
                    }
                }
                if (null != auspksLotterySg) {
                    if (StringUtils.isEmpty(auspksLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        auspksLotterySg = this.selectAuspksLotterySg();
                        if (null != auspksLotterySg) {
                            redisTemplate.opsForValue().set(redisKey, auspksLotterySg);
                        }
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), auspksLotterySg == null ? "" : auspksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), auspksLotterySg == null ? "" : auspksLotterySg.getNumber());
                    String niuWinner = NnAzOperationUtils.getNiuWinner(auspksLotterySg.getNumber());
                    result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);
                }

                if (nextAuspksLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextAuspksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextAuspksLotterySg.getIdealTime()) / 1000L);
                }
            } else {
                if (auspksLotterySg != null) {
                    if (StringUtils.isEmpty(auspksLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        auspksLotterySg = this.selectAuspksLotterySg();
                        if (null != auspksLotterySg) {
                            redisTemplate.opsForValue().set(redisKey, auspksLotterySg);
                        }
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), auspksLotterySg == null ? "" : auspksLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), auspksLotterySg == null ? "" : auspksLotterySg.getNumber());
                    String niuWinner = NnAzOperationUtils.getNiuWinner(auspksLotterySg.getNumber());
                    result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.FIVESSC.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    /**
     * @return AuspksLotterySg
     * @Title: queryAuspksLotterySgNextSg
     * @Description: 获取下期数据
     * @author HANS
     * @date 2019年5月12日下午10:36:29
     */
    public AuspksLotterySg queryAuspksLotterySgNextSg() {
        AuspksLotterySgExample auspksNextExample = new AuspksLotterySgExample();
        AuspksLotterySgExample.Criteria auspksNextCriteria = auspksNextExample.createCriteria();
        auspksNextCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        auspksNextCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
        auspksNextExample.setOrderByClause("ideal_time ASC");
        AuspksLotterySg auspksNextLotterySg = auspksLotterySgMapper.selectOneByExample(auspksNextExample);
        return auspksNextLotterySg;
    }

    /**
     * @return Integer
     * @Title: selectAusactOpenCount
     * @Description: 查询当天开奖数量
     */
    public Integer selectAuspksOpenCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openStatus", LotteryResultStatus.AUTO);
        map.put("paramTime", TimeHelper.date("yyyy-MM-dd") + "%");
        Integer openCount = auspksLotterySgMapperExt.openCountByExample(map);
        return openCount;
    }

    /**
     * @return AuspksLotterySg
     * @Title: selectAuspksLotterySg
     * @Description: 查询当前开奖数据
     * @author HANS
     * @date 2019年5月12日下午10:29:40
     */
    public AuspksLotterySg selectAuspksLotterySg() {
        AuspksLotterySgExample auspksExample = new AuspksLotterySgExample();
        AuspksLotterySgExample.Criteria auspksCriteria = auspksExample.createCriteria();
        auspksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        auspksExample.setOrderByClause("ideal_time DESC");
        AuspksLotterySg auspksLotterySg = auspksLotterySgMapper.selectOneByExample(auspksExample);
        return auspksLotterySg;
    }

    private String createNextIssue(String issue) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        // 判断是否已达最大值
        if ("999".equals(num)) {
            String prefix = DateUtils.formatDate(
                    DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "000";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
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

    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        AuspksLotterySgExample example = new AuspksLotterySgExample();
        AuspksLotterySgExample.Criteria auspksCriteria = example.createCriteria();
//		bjpksCriteria.andNumberIsNotNull();
        auspksCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

//		bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<AuspksLotterySg> auspksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.AUSPKS_SG_HS_LIST)) {
            AuspksLotterySgExample exampleOne = new AuspksLotterySgExample();
            AuspksLotterySgExample.Criteria auspksCriteriaOne = exampleOne.createCriteria();
            auspksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<AuspksLotterySg> auspksLotterySgsOne = auspksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.AUSPKS_SG_HS_LIST, auspksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            auspksLotterySgs = redisTemplate.opsForList().range(RedisKeys.AUSPKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            auspksLotterySgs = auspksLotterySgMapper.selectByExample(example);
        }

//		List<AuspksLotterySg> tc7xcLotterySgs = auspksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = AuspksLotteryReadSgServiceImpl.lishiSg(auspksLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> lishiSg(List<AuspksLotterySg> AuspksLotterySgs) {
        if (AuspksLotterySgs == null) {
            return null;
        }
        int totalIssue = AuspksLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            AuspksLotterySg sg = AuspksLotterySgs.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());

//			if(StringUtils.isEmpty(sg.getTime())){
//				map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getIdealTime());
//			}else{
//				map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());
//			}

//			if(StringUtils.isEmpty(sg.getNumber())){
//				map.put(Constants.SGSIGN, 0);
//			}else{
//				map.put(Constants.SGSIGN, 1);
//				map.put("number", sg.getNumber());
//				map.put("numberstr", sg.getNumber());
//			}

            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());
            result.add(map);
        }
        return result;
    }

    @Override
    public ResultInfo<Map<String, Object>> azNNlishiSg(Integer pageNo, Integer pageSize) {
        AuspksLotterySgExample example = new AuspksLotterySgExample();
        AuspksLotterySgExample.Criteria auspksCriteria = example.createCriteria();
//		bjpksCriteria.andNumberIsNotNull();
        auspksCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

//		bjpksCriteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");

        List<AuspksLotterySg> auspksLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.AUSPKS_SG_HS_LIST)) {
            AuspksLotterySgExample exampleOne = new AuspksLotterySgExample();
            AuspksLotterySgExample.Criteria auspksCriteriaOne = exampleOne.createCriteria();
            auspksCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<AuspksLotterySg> auspksLotterySgsOne = auspksLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.AUSPKS_SG_HS_LIST, auspksLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            auspksLotterySgs = redisTemplate.opsForList().range(RedisKeys.AUSPKS_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            auspksLotterySgs = auspksLotterySgMapper.selectByExample(example);
        }

//		List<AuspksLotterySg> tc7xcLotterySgs = AuspksLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = AuspksLotteryReadSgServiceImpl.azNNlishiSg(auspksLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> azNNlishiSg(List<AuspksLotterySg> AuspksLotterySgs) {
        if (AuspksLotterySgs == null) {
            return null;
        }
        int totalIssue = AuspksLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            AuspksLotterySg sg = AuspksLotterySgs.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("issue", sg.getIssue());
            map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());

//			if(StringUtils.isEmpty(sg.getTime())){
//				map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getIdealTime());
//			}else{
//				map.put(AppMianParamEnum.TIME.getParamEnName(), sg.getTime());
//			}

//			if(StringUtils.isEmpty(sg.getNumber())){
//				map.put(Constants.SGSIGN, 0);
//			}else{
//				map.put(Constants.SGSIGN, 1);
//				map.put("number", sg.getNumber());
//				map.put("numberstr", sg.getNumber());
//				// 获取牛牛结果
//				String azNiuWinner = NnAzOperationUtils.getNiuWinner(sg.getNumber());
//				map.put(AppMianParamEnum.NIU_NIU.getParamEnName(), azNiuWinner);
//			}

            map.put("number", sg.getNumber());
            map.put("numberstr", sg.getNumber());
            // 获取牛牛结果
            String azNiuWinner = NnAzOperationUtils.getNiuWinner(sg.getNumber());
            map.put(AppMianParamEnum.NIU_NIU.getParamEnName(), azNiuWinner);

            result.add(map);
        }
        return result;
    }
}
