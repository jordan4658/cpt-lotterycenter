package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AlgorithmEnum;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.AusactLotterySg;
import com.caipiao.live.common.mybatis.entity.AusactLotterySgExample;
import com.caipiao.live.common.mybatis.entity.Lottery;
import com.caipiao.live.common.mybatis.entity.LotteryPlay;
import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;
import com.caipiao.live.common.mybatis.mapper.AusactLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.AusactLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryPlayOddsServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryPlayServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryPlaySettingServiceReadSg;
import com.caipiao.live.common.service.lottery.LotteryServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.AusactSgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 澳洲act
 *
 * @author
 * @create 2019-03-13 11:05
 **/
@Service
public class AusactLotteryReadSgServiceImpl implements AusactLotterySgServiceReadSg {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private AusactLotterySgMapper ausactLotterySgMapper;
    @Resource
    private AusactLotterySgMapperExt ausactLotterySgMapperExt;
    @Resource
    private LotteryServiceReadSg lotteryService;
    @Resource
    private LotteryPlayServiceReadSg lotteryPlayService;
    @Resource
    private LotteryPlaySettingServiceReadSg lotteryPlaySettingService;
    @Resource
    private LotteryPlayOddsServiceReadSg lotteryPlayOddsService;

    @Override
    public Map<String, Object> getNewestSgInfo() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 缓存中取开奖结果
            String redisKey = RedisKeys.AUSACT_RESULT_VALUE;
            AusactLotterySg ausactLotterySg = (AusactLotterySg) redisTemplate.opsForValue().get(redisKey);

            if (ausactLotterySg == null) {
                ausactLotterySg = this.selectAusactLottery();
                redisTemplate.opsForValue().set(redisKey, ausactLotterySg);
            }

            // 获取开奖次数
            String openRedisKey = RedisKeys.AUSACT_OPEN_VALUE;
            Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

            if (openCount == null) {
                openCount = this.selectAusactOpenCount();
                redisTemplate.opsForValue().set(openRedisKey, openCount);
            }
            // 获取开奖总期数
            Integer sumCount = CaipiaoSumCountEnum.AZACT.getSumCount();
            result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
            // 计算当日剩余未开奖次数
            result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.AUSACT_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.AUSACT.getRedisTime();
            AusactLotterySg nextAusactLotterySg = (AusactLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextAusactLotterySg == null) {
                nextAusactLotterySg = this.queryAusactLotteryNextSg();
                redisTemplate.opsForValue().set(nextRedisKey, nextAusactLotterySg, redisTime, TimeUnit.MINUTES);
            }

            if (nextAusactLotterySg != null) {
                String nextIssue = nextAusactLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextAusactLotterySg.getIssue();
                String txffnextIssue = ausactLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : ausactLotterySg.getIssue();

                if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
                    Long nextIssueNum = Long.parseLong(nextIssue);
                    Long txffnextIssueNum = Long.parseLong(txffnextIssue);
                    Long differenceNum = nextIssueNum - txffnextIssueNum;

                    if (differenceNum < 1 || differenceNum > 2) {
                        nextAusactLotterySg = this.queryAusactLotteryNextSg();
                        redisTemplate.opsForValue().set(nextRedisKey, nextAusactLotterySg, redisTime, TimeUnit.MINUTES);
                        // 获取当前开奖数据
                        ausactLotterySg = this.selectAusactLottery();
                        redisTemplate.opsForValue().set(redisKey, ausactLotterySg);
                    }
                }
                if (ausactLotterySg != null) {
                    if (StringUtils.isEmpty(ausactLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        ausactLotterySg = this.selectAusactLottery();
                        redisTemplate.opsForValue().set(redisKey, ausactLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), ausactLotterySg == null ? "" : ausactLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), ausactLotterySg == null ? "" : ausactLotterySg.getNumber());
                }

                if (nextAusactLotterySg != null) {
                    // 获取下一期开奖时间
                    result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextAusactLotterySg.getIssue());
                    result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextAusactLotterySg.getIdealTime()) / 1000L);
                }
            } else {
                if (ausactLotterySg != null) {
                    if (StringUtils.isEmpty(ausactLotterySg.getNumber())) {
                        // 获取当前开奖数据
                        ausactLotterySg = this.selectAusactLottery();
                        redisTemplate.opsForValue().set(redisKey, ausactLotterySg);
                    }
                    result.put(AppMianParamEnum.ISSUE.getParamEnName(), ausactLotterySg == null ? "" : ausactLotterySg.getIssue());
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), ausactLotterySg == null ? "" : ausactLotterySg.getNumber());
                }
            }
        } catch (Exception e) {
            logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.FIVESSC.getTagType() + " 异常： ", e);
            result = DefaultResultUtil.getNullResult();
        }
        return result;
    }

    /**
     * @return AusactLotterySg
     * @Title: queryAusactLotteryNextSg
     * @Description: 获取下期数据
     */
    public AusactLotterySg queryAusactLotteryNextSg() {
        AusactLotterySgExample ausactExample = new AusactLotterySgExample();
        AusactLotterySgExample.Criteria ausactCriteria = ausactExample.createCriteria();
        ausactCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        ausactCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
        ausactExample.setOrderByClause("ideal_time ASC");
        AusactLotterySg nextAusactLotterySg = ausactLotterySgMapper.selectOneByExample(ausactExample);
        return nextAusactLotterySg;
    }

    /**
     * @return Integer
     * @Title: selectAusactOpenCount
     * @Description: 获取当天开奖总数
     */
    public Integer selectAusactOpenCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openStatus", LotteryResultStatus.AUTO);
        map.put("paramTime", TimeHelper.date("yyyy-MM-dd") + "%");
        Integer openCount = ausactLotterySgMapperExt.openCountByExample(map);
        return openCount;
    }

    /**
     * @return AusactLotterySg
     * @Title: selectAusactLottery
     * @Description: 获取当前开奖数据
     */
    public AusactLotterySg selectAusactLottery() {
        AusactLotterySgExample ausactExample = new AusactLotterySgExample();
        AusactLotterySgExample.Criteria ausactCriteria = ausactExample.createCriteria();
        ausactCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        ausactExample.setOrderByClause("ideal_time DESC");
        AusactLotterySg ausactLotterySg = ausactLotterySgMapper.selectOneByExample(ausactExample);
        return ausactLotterySg;
    }



    /**
     * 获取下一期官方开奖时间
     *
     * @param
     * @return
     */
//	private Date nextIssueTime(Date lastTime) {
//		Date dateTime = DateUtils.getSecondAfter(lastTime, 160);
//		return dateTime;
//	}
    @Override
    public ResultInfo<Map<String, Object>> lishiSg(Integer pageNo, Integer pageSize) {
        AusactLotterySgExample example = new AusactLotterySgExample();
        AusactLotterySgExample.Criteria ausactCriteria = example.createCriteria();
//        ausactCriteria.andNumberIsNotNull();
        ausactCriteria.andOpenStatusEqualTo(Constants.STATUS_AUTO);

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

        List<AusactLotterySg> ausactLotterySgs = null;
        //存储100条 最新历史数据到缓存里，供页面查询
        if (!redisTemplate.hasKey(RedisKeys.AUSACT_SG_HS_LIST)) {
            AusactLotterySgExample exampleOne = new AusactLotterySgExample();
            AusactLotterySgExample.Criteria ausactCriteriaOne = exampleOne.createCriteria();
            ausactCriteriaOne.andOpenStatusEqualTo(Constants.STATUS_AUTO);
            exampleOne.setOffset(0);
            exampleOne.setLimit(100);
            exampleOne.setOrderByClause("ideal_time DESC");
            List<AusactLotterySg> ausactLotterySgsOne = ausactLotterySgMapper.selectByExample(exampleOne);
            redisTemplate.opsForList().rightPushAll(RedisKeys.AUSACT_SG_HS_LIST, ausactLotterySgsOne);
        }
        if ((pageNo - 1) * pageSize + pageSize <= 100) {     //从缓存中取
            ausactLotterySgs = redisTemplate.opsForList().range(RedisKeys.AUSACT_SG_HS_LIST, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        } else {  //从数据库中取
            ausactLotterySgs = ausactLotterySgMapper.selectByExample(example);
        }

//        List<AusactLotterySg> tc7xcLotterySgs = AusactLotterySgMapper.selectByExample(example);
        List<Map<String, Object>> maps = AusactLotteryReadSgServiceImpl.lishiSg(ausactLotterySgs);
        Map<String, Object> result = new HashMap<>();
        result.put("list", maps);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);

        return ResultInfo.ok(result);
    }

    public static List<Map<String, Object>> lishiSg(List<AusactLotterySg> AusactLotterySgs) {
        if (AusactLotterySgs == null) {
            return null;
        }
        int totalIssue = AusactLotterySgs.size();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < totalIssue; i++) {
            AusactLotterySg sg = AusactLotterySgs.get(i);
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

    /**
     * @Description: 澳洲ACT大小
     */
    @Override
    public ResultInfo<List<Map<String, Object>>> algorithm(String type) {
        List<Map<String, Object>> resultMaps = new ArrayList<Map<String, Object>>();

        String algorithm = RedisKeys.AUSACT_ALGORITHM_VALUE;
        List<AusactLotterySg> ausactLotterySgList = (List<AusactLotterySg>) redisTemplate.opsForValue().get(algorithm);

        if (CollectionUtils.isEmpty(ausactLotterySgList)) {
            ausactLotterySgList = this.getAlgorithmData();
            redisTemplate.opsForValue().set(algorithm, ausactLotterySgList);
        }

        if (!CollectionUtils.isEmpty(ausactLotterySgList)) {
            for (AusactLotterySg ausactLotterySg : ausactLotterySgList) {
                Map<String, Object> result = new HashMap<>();
                result.put(AppMianParamEnum.ISSUE.getParamEnName(), ausactLotterySg.getIssue());

                if (AlgorithmEnum.BIGORSMALL.getCode().equals(type)) {
                    // 大小
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), AusactSgUtils.getBigOrSmall(ausactLotterySg.getNumber()));
                } else if (AlgorithmEnum.SINGLEANDDOUBLE.getCode().equals(type)) {
                    // 单双
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), AusactSgUtils.getSingleAndDouble(ausactLotterySg.getNumber()));
                } else if (AlgorithmEnum.FIVEELEMENTS.getCode().equals(type)) {
                    // 五行
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), AusactSgUtils.getFiveElements(ausactLotterySg.getNumber()));
                } else {
                    result.put(AppMianParamEnum.NUMBER.getParamEnName(), ausactLotterySg.getNumber());
                }
                resultMaps.add(result);
            }
        } else {
            Map<String, Object> result = new HashMap<>();
            result = DefaultResultUtil.getNullAlgorithmResult();
            resultMaps.add(result);
        }
        return ResultInfo.ok(resultMaps);
    }

    /**
     * @return List<AusactLotterySg>
     * @Title: getAlgorithmData
     * @Description: 查询近200期开奖数据
     * @author HANS
     * @date 2019年5月7日下午2:51:59
     */
    public List<AusactLotterySg> getAlgorithmData() {
        AusactLotterySgExample ausactExample = new AusactLotterySgExample();
        AusactLotterySgExample.Criteria ausactCriteria = ausactExample.createCriteria();
        ausactCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        ausactExample.setOrderByClause("ideal_time DESC");
        ausactExample.setOffset(Constants.DEFAULT_INTEGER);
        ausactExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<AusactLotterySg> ausactLotterySgList = ausactLotterySgMapper.selectByExample(ausactExample);
        return ausactLotterySgList;
    }

    /* (non Javadoc)
     * @Title: getActSgLong
     * @Description: 获取澳洲ACT长龙
     * @return
     * @see com.caipiao.live.read.service.result.AusactLotterySgService#getActSgLong()
     */
    @Override
    public List<Map<String, Object>> getActSgLong() {
        List<Map<String, Object>> ausactLongMapList = new ArrayList<Map<String, Object>>();
        // 收集澳洲ACT赛果大/小结果
        Map<String, Object> bigAndSmallDragonMap = new HashMap<String, Object>();
        // 收集澳洲ACT赛果单/双结果
        Map<String, Object> sigleAndDoubleDragonMap = new HashMap<String, Object>();
        try {
            String algorithm = RedisKeys.AUSACT_ALGORITHM_VALUE;
            List<AusactLotterySg> ausactLotterySgList = (List<AusactLotterySg>) redisTemplate.opsForValue().get(algorithm);

            if (CollectionUtils.isEmpty(ausactLotterySgList)) {
                ausactLotterySgList = this.getAlgorithmData();
                redisTemplate.opsForValue().set(algorithm, ausactLotterySgList, 10, TimeUnit.SECONDS);
            }

            if (!CollectionUtils.isEmpty(ausactLotterySgList)) {
                // 大/小标记变量
                Integer bigAndSmallDragonSize = Constants.DEFAULT_INTEGER;
                Set<String> bigAndSmallDragonSet = new HashSet<String>();
                boolean bigAllowFlag = true;
                // 单/双标记变量
                Integer sigleAndDoubleDragonSize = Constants.DEFAULT_INTEGER;
                Set<String> sigleAndDoubleDragonSet = new HashSet<String>();
                boolean sigleAllowFlag = true;

                for (int index = Constants.DEFAULT_INTEGER; index < ausactLotterySgList.size(); index++) {
                    // 如果不符合规则， 大/小和单/双都不再统计
                    if (!bigAllowFlag && !sigleAllowFlag) {
                        break;
                    }
                    AusactLotterySg ausactLotterySg = ausactLotterySgList.get(index);
                    // 大/小
                    String bigOrSmallName = AusactSgUtils.getBigOrSmall(ausactLotterySg.getNumber());

                    if (StringUtils.isEmpty(bigOrSmallName)) {
                        // 大/小已经没有龙了，不再统计
                        bigAllowFlag = false;
                    }

                    // 如果是“和”，退出
                    if (Constants.BIGORSMALL_SAME.equals(bigOrSmallName)) {
                        // 大/小已经没有龙了，不再统计
                        bigAllowFlag = false;
                    }
                    // 把第一个结果加入SET集合
                    if (index == Constants.DEFAULT_INTEGER) {
                        if (bigAllowFlag) {
                            bigAndSmallDragonSet.add(bigOrSmallName);
                        }
                    }
                    // 单/双
                    String singleAndDoubleName = AusactSgUtils.getSingleAndDouble(ausactLotterySg.getNumber());

                    if (StringUtils.isEmpty(singleAndDoubleName)) {
                        // 单/双已经没有龙了，不再统计
                        bigAllowFlag = false;
                    }
                    // 把第一个结果加入SET集合
                    if (index == Constants.DEFAULT_INTEGER) {
                        if (sigleAllowFlag) {
                            sigleAndDoubleDragonSet.add(singleAndDoubleName);
                        }
                    }
                    // 如果第一个和第二个开奖结果不一样，统计截止
                    if (index == Constants.DEFAULT_ONE) {
                        if (!bigAndSmallDragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了，不再统计
                            bigAllowFlag = false;
                        }
                        if (!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
                            // 单/双已经没有龙了，不再统计
                            sigleAllowFlag = false;
                        }
                    }
                    // 如果不符合规则， 大/小和单/双都不再统计
                    if (!bigAllowFlag && !sigleAllowFlag) {
                        break;
                    }
                    // 规则：连续3个开奖一样
                    if (index == Constants.DEFAULT_TWO) {
                        // 第三个数据
                        if (!bigAndSmallDragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了，不再统计
                            bigAllowFlag = false;
                        }

                        if (!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
                            // 单/双已经没有龙了，不再统计
                            sigleAllowFlag = false;
                        }

                        if (bigAllowFlag) {
                            bigAndSmallDragonSize = Constants.DEFAULT_THREE;
                        }

                        if (sigleAllowFlag) {
                            sigleAndDoubleDragonSize = Constants.DEFAULT_THREE;
                        }
                    }
                    // 如果不符合规则， 大/小和单/双都不再统计
                    if (!bigAllowFlag && !sigleAllowFlag) {
                        break;
                    }
                    // 如果大于3个以上，继续统计，直到结果不一样
                    if (index > Constants.DEFAULT_TWO) {
                        // 大/小统计
                        if (!bigAndSmallDragonSet.contains(bigOrSmallName)) {
                            // 大/小已经没有龙了，不再统计
                            bigAllowFlag = false;
                        }

                        if (bigAllowFlag) {
                            bigAndSmallDragonSize++;
                        }
                        // 单/双统计
                        if (!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
                            // 单/双已经没有龙了，不再统计
                            sigleAllowFlag = false;
                        }
                        if (sigleAllowFlag) {
                            sigleAndDoubleDragonSize++;
                        }
                    }
                    // 如果不符合规则， 大/小和单/双都不再统计
                    if (!bigAllowFlag && !sigleAllowFlag) {
                        break;
                    }
                }
                // 组织返回数据
                if (bigAndSmallDragonSize >= Constants.DEFAULT_THREE) {
                    bigAndSmallDragonMap = this.organizationDragonResultMap(CaipiaoPlayTypeEnum.AUSACTBIG.getTagCnName(), CaipiaoPlayTypeEnum.AUSACTBIG.getPlayTag(), CaipiaoPlayTypeEnum.AUSACTBIG.getTagType(), bigAndSmallDragonSize, bigAndSmallDragonSet);
                }

                if (sigleAndDoubleDragonSize >= Constants.DEFAULT_THREE) {
                    sigleAndDoubleDragonMap = this.organizationDragonResultMap(CaipiaoPlayTypeEnum.AUSACTDOUBLE.getTagCnName(), CaipiaoPlayTypeEnum.AUSACTDOUBLE.getPlayTag(), CaipiaoPlayTypeEnum.AUSACTDOUBLE.getTagType(), sigleAndDoubleDragonSize, sigleAndDoubleDragonSet);
                }
            }
        } catch (Exception e) {
            logger.error("app_getSgLongDragons.json#getActSgLong_error:", e);
        }
        // 计算后的数据，放入返回集合
        if (bigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
            ausactLongMapList.add(bigAndSmallDragonMap);
        }
        if (sigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
            ausactLongMapList.add(sigleAndDoubleDragonMap);
        }
        ausactLongMapList = this.addNextIssueInfo(ausactLongMapList);
        // 返回
        return ausactLongMapList;
    }

    private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> ausactLongMapList) {
        List<Map<String, Object>> ausactResultLongMapList = new ArrayList<Map<String, Object>>();

        if (!CollectionUtils.isEmpty(ausactLongMapList)) {
            // 缓存中取下一期信息
            String nextRedisKey = RedisKeys.AUSACT_NEXT_VALUE;
            Long redisTime = CaipiaoRedisTimeEnum.AUSACT.getRedisTime();
            AusactLotterySg nextAusactLotterySg = (AusactLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

            if (nextAusactLotterySg == null) {
                nextAusactLotterySg = this.queryAusactLotteryNextSg();
                redisTemplate.opsForValue().set(nextRedisKey, nextAusactLotterySg, redisTime, TimeUnit.MINUTES);
            }
//			// 获取当前开奖数据
// 			String redisKey = RedisKeys.AUSACT_RESULT_VALUE;
//        	AusactLotterySg ausactLotterySg = (AusactLotterySg)redisTemplate.opsForValue().get(redisKey);
//        	
//        	if(ausactLotterySg == null) {
//        		ausactLotterySg = this.selectAusactLottery();
//        		redisTemplate.opsForValue().set(redisKey, ausactLotterySg);
//        	}
//        	
//        	
//			
            for (Map<String, Object> longMap : ausactLongMapList) {
                longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextAusactLotterySg.getIssue());
                longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextAusactLotterySg.getIdealTime()) / 1000L);
                ausactResultLongMapList.add(longMap);
            }
        }
        return ausactResultLongMapList;
    }

    /**
     * @Title: organizationDragonResultMap
     * @Description: 组织长龙结果返回 Map格式
     * @author HANS
     * @date 2019年5月10日下午3:25:13
     */
    private Map<String, Object> organizationDragonResultMap(String playTyep, String playtag, int tagType, Integer dragonSize, Set<String> dragonSet) {
        // 有龙情况下，查询下期数据
        Map<String, Object> longDragonMap = new HashMap<String, Object>();
        List<String> dragonList = new ArrayList<String>(dragonSet);
        longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), tagType);
        longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.AUSACT.getTagCnName());
        longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.AUSACT.getTagType());
        longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), playTyep);
        longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), playtag);
        longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonList.get(Constants.DEFAULT_INTEGER));
        longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
        // 获取赔率数据
        PlayAndOddListInfoVO playAndOddListInfo = this.getAusactOddsList(CaipiaoTypeEnum.AUSACT.getTagCnName(), CaipiaoTypeEnum.AUSACT.getTagCnName(),
                CaipiaoTypeEnum.AUSACT.getTagType(), CaipiaoTypeEnum.AUSACT.getTagType());

        if (playAndOddListInfo != null) {
            LotteryPlaySetting lotteryPlaySetting = playAndOddListInfo.getLotteryPlaySetting();

            if (lotteryPlaySetting != null) {
                longDragonMap.put(AppMianParamEnum.COTEGORY.getParamEnName(), lotteryPlaySetting.getCateId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getCateId());
                longDragonMap.put(AppMianParamEnum.PLAYTAGID.getParamEnName(), lotteryPlaySetting.getPlayTagId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getPlayTagId());
            }
            List<LotteryPlayOdds> oddsList = playAndOddListInfo.getOddsList();

            if (!CollectionUtils.isEmpty(oddsList)) {
                List<Map<String, Object>> playMapList = new ArrayList<Map<String, Object>>();
                for (LotteryPlayOdds lotteryPlayOdds : oddsList) {
                    // 大小
                    if (CaipiaoPlayTypeEnum.AUSACTBIG.getTagCnName().equalsIgnoreCase(playTyep)) {
                        if (Constants.BIGORSMALL_BIG.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.BIGORSMALL_SMALL.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }
                    // 单双
                    if (CaipiaoPlayTypeEnum.AUSACTDOUBLE.getTagCnName().equalsIgnoreCase(playTyep)) {
                        if (Constants.BIGORSMALL_ODD_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }

                        if (Constants.BIGORSMALL_EVEN_NUMBER.equals(lotteryPlayOdds.getName())) {
                            Map<String, Object> playMap = new HashMap<String, Object>();
                            playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
                            playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
                            playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
                            playMapList.add(playMap);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(playMapList)) {
                    longDragonMap.put(AppMianParamEnum.ODDS.getParamEnName(), playMapList);
                }
            }
        }
        // 缓存中取下一期信息
        String nextRedisKey = RedisKeys.AUSACT_NEXT_VALUE;
        Long redisTime = CaipiaoRedisTimeEnum.AUSACT.getRedisTime();
        AusactLotterySg nextAusactLotterySg = (AusactLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

        if (nextAusactLotterySg == null) {
            nextAusactLotterySg = this.queryAusactLotteryNextSg();
            redisTemplate.opsForValue().set(nextRedisKey, nextAusactLotterySg, redisTime, TimeUnit.MINUTES);
        }

        if (nextAusactLotterySg == null) {
            return new HashMap<String, Object>();
        }
        // 缓存中取开奖结果
        String redisKey = RedisKeys.AUSACT_RESULT_VALUE;
        AusactLotterySg ausactLotterySg = (AusactLotterySg) redisTemplate.opsForValue().get(redisKey);

        if (ausactLotterySg == null) {
            ausactLotterySg = this.selectAusactLottery();
            redisTemplate.opsForValue().set(redisKey, ausactLotterySg);
        }
        String nextIssue = nextAusactLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextAusactLotterySg.getIssue();
        String txffnextIssue = ausactLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : ausactLotterySg.getIssue();

        if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
            Long nextIssueNum = Long.parseLong(nextIssue);
            Long txffnextIssueNum = Long.parseLong(txffnextIssue);
            Long differenceNum = nextIssueNum - txffnextIssueNum;

            // 如果长龙期数与下期期数相差太大长龙不存在
            if (differenceNum < 1 || differenceNum > 2) {
                return new HashMap<String, Object>();
            }
        }
        longDragonMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextAusactLotterySg.getIssue());
        longDragonMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextAusactLotterySg.getIdealTime()) / 1000L);
        return longDragonMap;
    }

    /**
     * @return List<LotteryInfo>
     * @Title: getAusactOddsList
     * @Description: 获取澳洲ACT赔率
     * @author HANS
     * @date 2019年5月11日下午9:07:19
     */
    @Override
    public PlayAndOddListInfoVO getAusactOddsList(String caipiaoName, String lotteryPlayName, String caipiaoType, String cachKey) {
        PlayAndOddListInfoVO playAndOddListInfo = new PlayAndOddListInfoVO();
        String redisKey = RedisKeys.ODDS_LIST_LONG_Dragon_SETTING_KEY + caipiaoType + "_" + cachKey;
        PlayAndOddListInfoVO playAndOddListCacheInfo = (PlayAndOddListInfoVO) redisTemplate.opsForValue().get(redisKey);

        if (playAndOddListCacheInfo != null) {
            if (!CollectionUtils.isEmpty(playAndOddListCacheInfo.getOddsList())) {
                return playAndOddListCacheInfo;
            }
        }
        // 获取到配置信息
//		Lottery lottery = lotteryService.selectLotteryForName(caipiaoName);
//		
//		if(lottery == null) {
//			return playAndOddListInfo;
//		}
        Integer caiZhongId = Integer.valueOf(caipiaoType);
        // 获取到配置玩法
        List<LotteryPlay> lotteryPlayList = lotteryPlayService.getPlaysFirstLevelByLotteryId(caiZhongId);
        // 取到配置名称数据
        if (CollectionUtils.isEmpty(lotteryPlayList)) {
            return playAndOddListInfo;
        }
        LotteryPlay typeLotteryPlay = new LotteryPlay();
        for (LotteryPlay lotteryPlay : lotteryPlayList) {
            String nameString = lotteryPlay.getName() == null ? Constants.DEFAULT_NULL : lotteryPlay.getName();
            Integer playTagId = lotteryPlay.getPlayTagId() == null ? Constants.DEFAULT_INTEGER : lotteryPlay.getPlayTagId();

            if (lotteryPlayName.equals(nameString) && !playTagId.equals(Constants.DEFAULT_INTEGER)) {
                BeanUtils.copyProperties(lotteryPlay, typeLotteryPlay);
                break;
            }
        }
        if (typeLotteryPlay.getId() == null || typeLotteryPlay.getPlayTagId() == null) {
            return playAndOddListInfo;
        }
        // 查询配置信息
        LotteryPlaySetting lotteryPlaySetting = lotteryPlaySettingService.queryLotteryPlaySettingForPlayId(typeLotteryPlay.getId(), typeLotteryPlay.getPlayTagId());

        if (lotteryPlaySetting == null) {
            return playAndOddListInfo;
        }
        List<LotteryPlayOdds> oddsList = lotteryPlayOddsService.selectOddsListBySettingId(lotteryPlaySetting.getId());

        if (CollectionUtils.isEmpty(oddsList)) {
            return playAndOddListInfo;
        }
        // 组织数据
        playAndOddListInfo.setLotteryPlaySetting(lotteryPlaySetting);
        playAndOddListInfo.setOddsList(oddsList);
        // 放入缓存
        redisTemplate.opsForValue().set(redisKey, playAndOddListInfo, Constants.DRAGON_ODDS_CACHE_TIME, TimeUnit.MINUTES);
        return playAndOddListInfo;
    }

    /* (non Javadoc)
     * @Title: getLhcOddsList
     * @Description: 获取六合彩的赔率
     * @return
     * @see com.caipiao.live.read.service.result.AusactLotterySgService#getLhcOddsList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public PlayAndOddListInfoVO getLhcOddsList(String caipiaoName, String lotteryPlayName, String caipiaoType, String cachKey) {
        PlayAndOddListInfoVO playAndOddListInfo = new PlayAndOddListInfoVO();
        String redisKey = RedisKeys.LHC_ODDS_LIST_LONG_DRAGON_SETTING_KEY + caipiaoType + "_" + cachKey;
        PlayAndOddListInfoVO playAndOddListCacheInfo = (PlayAndOddListInfoVO) redisTemplate.opsForValue().get(redisKey);

        if (playAndOddListCacheInfo != null) {
            if (!CollectionUtils.isEmpty(playAndOddListCacheInfo.getOddsList())) {
                return playAndOddListCacheInfo;
            }
        }

        // 获取到配置信息
        Lottery lottery = lotteryService.selectLotteryForName(caipiaoName);

        if (lottery == null) {
            return playAndOddListInfo;
        }

        // 获取到配置玩法上层类
        List<LotteryPlay> lotteryPlayList = lotteryPlayService.getPlaysFirstLevelByLotteryId(lottery.getLotteryId());
        // 取到配置名称数据
        if (CollectionUtils.isEmpty(lotteryPlayList)) {
            return playAndOddListInfo;
        }
        // 获取到上层配置信息
        LotteryPlay typeLotteryPlay = new LotteryPlay();
        for (LotteryPlay lotteryPlay : lotteryPlayList) {
            String nameString = lotteryPlay.getName() == null ? Constants.DEFAULT_NULL : lotteryPlay.getName();

            if (lotteryPlayName.equals(nameString)) {
                BeanUtils.copyProperties(lotteryPlay, typeLotteryPlay);
                break;
            }
        }
        if (lottery.getLotteryId() == null || typeLotteryPlay.getId() == null) {
            return playAndOddListInfo;
        }
        // 获取下层信息
        List<LotteryPlay> lowerLotteryPlayList = lotteryPlayService.getPlaysFirstLevelByLotteryAndParentId(lottery.getLotteryId(), typeLotteryPlay.getId());
        // 取到下层配置名称数据
        if (CollectionUtils.isEmpty(lowerLotteryPlayList)) {
            return playAndOddListInfo;
        }
        // 获取到上层配置信息
        LotteryPlay lowerTypeLotteryPlay = new LotteryPlay();
        for (LotteryPlay lowerLotteryPlay : lowerLotteryPlayList) {
            String nameString = lowerLotteryPlay.getName() == null ? Constants.DEFAULT_NULL : lowerLotteryPlay.getName();
            Integer playTagId = lowerLotteryPlay.getPlayTagId() == null ? Constants.DEFAULT_INTEGER : lowerLotteryPlay.getPlayTagId();

            if (lotteryPlayName.equals(nameString) && !playTagId.equals(Constants.DEFAULT_INTEGER)) {
                BeanUtils.copyProperties(lowerLotteryPlay, lowerTypeLotteryPlay);
                break;
            }
        }

        if (lowerTypeLotteryPlay.getId() == null || lowerTypeLotteryPlay.getPlayTagId() == null) {
            return playAndOddListInfo;
        }
        // 查询配置信息
        LotteryPlaySetting lotteryPlaySetting = lotteryPlaySettingService.queryLotteryPlaySettingForPlayId(lowerTypeLotteryPlay.getId(), lowerTypeLotteryPlay.getPlayTagId());

        if (lotteryPlaySetting == null) {
            return playAndOddListInfo;
        }
        List<LotteryPlayOdds> oddsList = lotteryPlayOddsService.selectOddsListBySettingId(lotteryPlaySetting.getId());

        if (CollectionUtils.isEmpty(oddsList)) {
            return playAndOddListInfo;
        }
        // 组织数据
        playAndOddListInfo.setLotteryPlaySetting(lotteryPlaySetting);
        playAndOddListInfo.setOddsList(oddsList);
        // 放入缓存
        redisTemplate.opsForValue().set(redisKey, playAndOddListInfo);
        return playAndOddListInfo;
    }

    /* (non Javadoc)
     * @Title: getLhcZhengTeOddsList
     * @Description: 获取正特玩法的赔率
     * @return
     * @see com.caipiao.live.read.service.result.AusactLotterySgService#getLhcZhengTeOddsList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public PlayAndOddListInfoVO getLhcZhengTeOddsList(String caipiaoName, String lotteryPlayName, String caipiaoType,
                                                      String cachKey, String zhengTeName) {
        PlayAndOddListInfoVO playAndOddListInfo = new PlayAndOddListInfoVO();
        String redisKey = RedisKeys.LHC_ODDS_LIST_LONG_DRAGON_SETTING_KEY + caipiaoType + "_" + cachKey;
        PlayAndOddListInfoVO playAndOddListCacheInfo = (PlayAndOddListInfoVO) redisTemplate.opsForValue().get(redisKey);

        if (playAndOddListCacheInfo != null) {
            if (!CollectionUtils.isEmpty(playAndOddListCacheInfo.getOddsList())) {
                return playAndOddListCacheInfo;
            }
        }

        // 获取到配置信息
        Lottery lottery = lotteryService.selectLotteryForName(caipiaoName);

        if (lottery == null) {
            return playAndOddListInfo;
        }

        // 获取到配置玩法上层类
        List<LotteryPlay> lotteryPlayList = lotteryPlayService.getPlaysFirstLevelByLotteryId(lottery.getLotteryId());
        // 取到配置名称数据
        if (CollectionUtils.isEmpty(lotteryPlayList)) {
            return playAndOddListInfo;
        }
        // 获取到上层配置信息
        LotteryPlay typeLotteryPlay = new LotteryPlay();
        for (LotteryPlay lotteryPlay : lotteryPlayList) {
            String nameString = lotteryPlay.getName() == null ? Constants.DEFAULT_NULL : lotteryPlay.getName();

            if (lotteryPlayName.equals(nameString)) {
                BeanUtils.copyProperties(lotteryPlay, typeLotteryPlay);
                break;
            }
        }

        if (lottery.getLotteryId() == null || typeLotteryPlay.getId() == null) {
            return playAndOddListInfo;
        }
        // 获取下层信息
        List<LotteryPlay> lowerLotteryPlayList = lotteryPlayService.getPlaysFirstLevelByLotteryAndParentId(lottery.getLotteryId(), typeLotteryPlay.getId());
        // 取到下层配置名称数据
        if (CollectionUtils.isEmpty(lowerLotteryPlayList)) {
            return playAndOddListInfo;
        }
        // 获取到上层配置信息
        LotteryPlay lowerTypeLotteryPlay = new LotteryPlay();
        for (LotteryPlay lowerLotteryPlay : lowerLotteryPlayList) {
            String nameString = lowerLotteryPlay.getName() == null ? Constants.DEFAULT_NULL : lowerLotteryPlay.getName();
            Integer playTagId = lowerLotteryPlay.getPlayTagId() == null ? Constants.DEFAULT_INTEGER : lowerLotteryPlay.getPlayTagId();

            // 正特获取赔率
            if (zhengTeName.equals(nameString) && !playTagId.equals(Constants.DEFAULT_INTEGER)) {
                BeanUtils.copyProperties(lowerLotteryPlay, lowerTypeLotteryPlay);
                break;
            }
        }

        if (lowerTypeLotteryPlay.getId() == null || lowerTypeLotteryPlay.getPlayTagId() == null) {
            return playAndOddListInfo;
        }
        // 查询配置信息
        LotteryPlaySetting lotteryPlaySetting = lotteryPlaySettingService.queryLotteryPlaySettingForPlayId(lowerTypeLotteryPlay.getId(), lowerTypeLotteryPlay.getPlayTagId());

        if (lotteryPlaySetting == null) {
            return playAndOddListInfo;
        }
        List<LotteryPlayOdds> oddsList = lotteryPlayOddsService.selectOddsListBySettingId(lotteryPlaySetting.getId());

        if (CollectionUtils.isEmpty(oddsList)) {
            return playAndOddListInfo;
        }
        // 组织数据
        playAndOddListInfo.setLotteryPlaySetting(lotteryPlaySetting);
        playAndOddListInfo.setOddsList(oddsList);
        // 放入缓存
        redisTemplate.opsForValue().set(redisKey, playAndOddListInfo);
        return playAndOddListInfo;
    }

}
