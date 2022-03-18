package com.caipiao.app.service.impl;

import com.caipiao.app.service.FvpksLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.FivebjpksLotterySgMapper;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * 	5分PK10
 *
 * @author Asheng
 * @create 2019-03-13 11:05
 **/
@Service
public class FvpksLotterySgServiceImpl implements FvpksLotterySgService {
	
	private static final Logger logger = LoggerFactory.getLogger(FvpksLotterySgServiceImpl.class);
    @Autowired
	private RedisTemplate<String, Object> redisTemplate;
    @Autowired
	private FivebjpksLotterySgMapper fivebjpksLotterySgMapper;
    
    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullResult();
		try {
			// 缓存中取开奖结果
			String redisKey = RedisKeys.FIVEPKS_RESULT_VALUE;
			FivebjpksLotterySg fivebjpksLotterySg = (FivebjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (fivebjpksLotterySg == null) {
				fivebjpksLotterySg = this.getFivebjpksLotterySg();
				redisTemplate.opsForValue().set(redisKey, fivebjpksLotterySg);
			}

			if (fivebjpksLotterySg != null) {
				result.put(AppMianParamEnum.ISSUE.getParamEnName(),fivebjpksLotterySg == null ? Constants.DEFAULT_NULL : fivebjpksLotterySg.getIssue());
				result.put(AppMianParamEnum.NUMBER.getParamEnName(),fivebjpksLotterySg == null ? Constants.DEFAULT_NULL : fivebjpksLotterySg.getNumber());
			}
			// 缓存中取开奖数量
			String openRedisKey = RedisKeys.FIVEPKS_OPEN_VALUE;
			Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

//			if (openCount == null) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("openStatus", LotteryResultStatus.AUTO);
//				map.put("paramTime", TimeHelper.date("yyyy-MM-dd"));
//				openCount = fivebjpksLotterySgMapper.openCountByExample(map);
//				redisTemplate.opsForValue().set(openRedisKey, openCount);
//			}

			if (openCount != null) {
				result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
				// 获取开奖总期数
				Integer sumCount = CaipiaoSumCountEnum.FIVEPKS.getSumCount();
				// 计算当日剩余未开奖次数
				result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
			}
			String nextRedisKey = RedisKeys.FIVEPKS_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.FIVEPKS.getRedisTime();
			FivebjpksLotterySg nextFivebjpksLotterySg = (FivebjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

			if (nextFivebjpksLotterySg == null) {
				nextFivebjpksLotterySg = this.getNextFivebjpksLotterySg();
				redisTemplate.opsForValue().set(nextRedisKey, nextFivebjpksLotterySg, redisTime, TimeUnit.MINUTES);
			}

			if (nextFivebjpksLotterySg != null) {
				String nextIssue = nextFivebjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextFivebjpksLotterySg.getIssue();
				String txffnextIssue = fivebjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : fivebjpksLotterySg.getIssue();

				if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					if (nextIssue.compareTo(txffnextIssue) < 0 || nextIssue.compareTo(txffnextIssue) > 2) {
						nextFivebjpksLotterySg = this.getNextFivebjpksLotterySg();
						redisTemplate.opsForValue().set(nextRedisKey, nextFivebjpksLotterySg, redisTime,TimeUnit.MINUTES);
						// 查询当前开奖数据
						fivebjpksLotterySg = this.getFivebjpksLotterySg();
						redisTemplate.opsForValue().set(redisKey, fivebjpksLotterySg);
					}
				}

				if (nextFivebjpksLotterySg != null) {
					// 获取下一期开奖时间
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFivebjpksLotterySg.getIssue());
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextFivebjpksLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFivebjpksLotterySg.getIdealTime()) / 1000L);
				}
			} else {
				if (nextFivebjpksLotterySg != null) {
					// 获取下一期开奖时间
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFivebjpksLotterySg.getIssue());
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextFivebjpksLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFivebjpksLotterySg.getIdealTime()) / 1000L);
				}
			}

			//计算冠亚军和，1-5龙虎
			if(fivebjpksLotterySg != null){
				String number[] = fivebjpksLotterySg.getNumber().split(",");
				int num1 = Integer.valueOf(number[0]);
				int num2 = Integer.valueOf(number[1]);
				int num3 = Integer.valueOf(number[2]);
				int num4 = Integer.valueOf(number[3]);
				int num5 = Integer.valueOf(number[4]);
				int num6 = Integer.valueOf(number[5]);
				int num7 = Integer.valueOf(number[6]);
				int num8 = Integer.valueOf(number[7]);
				int num9 = Integer.valueOf(number[8]);
				int num10 = Integer.valueOf(number[9]);
				String num1Lh = "龙";String num2Lh = "龙";String num3Lh = "龙";String num4Lh = "龙";String num5Lh = "龙";
				String num6Lh = "龙";String num7Lh = "龙";String num8Lh = "龙";String num9Lh = "龙";String num10Lh = "龙";
				Integer guanyaSum = Integer.valueOf(number[0]) + Integer.valueOf(number[1]);
				String BigOrSmall = "小";
				String danOrShuang = "单";
				if(guanyaSum > 11){
					BigOrSmall = "大";
				}
				if(guanyaSum%2==0){
					danOrShuang = "双";
				}
				if(num1 > num10){
					num1Lh = "虎";
				}
				if(num2 > num9){
					num2Lh = "虎";
				}
				if(num3 > num8){
					num3Lh = "虎";
				}
				if(num4 > num7){
					num4Lh = "虎";
				}
				if(num5 > num6){
					num5Lh = "虎";
				}
				result.put("calMessage", guanyaSum+","+BigOrSmall+","+danOrShuang+","+num1Lh+","+num2Lh+","+num3Lh+","+num4Lh+","+num5Lh);

			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids: " + CaipiaoTypeEnum.FIVEPKS.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return ResultInfo.ok(result);
	}	

	
	
	/** 
	* @Title: selectFivebjpksByIssue 
	* @Description: 获取近期开奖数据 
	* @author HANS
	* @date 2019年5月17日下午11:14:49
	*/ 
	public List<FivebjpksLotterySg> selectFivebjpksByIssue() {
		FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
        FivebjpksLotterySgExample.Criteria fivebjpksCriteria = example.createCriteria();
        fivebjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        List<FivebjpksLotterySg> fivebjpksLotterySgList = fivebjpksLotterySgMapper.selectByExample(example);
		return fivebjpksLotterySgList;
	}

	/**
	 * @Title: getFivebjpksLotterySg
	 * @Description: 查询当前开奖数据
	 * @return FivebjpksLotterySg
	 * @author HANS
	 * @date 2019年5月3日下午1:23:27
	 */
	public FivebjpksLotterySg getFivebjpksLotterySg() {
		FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
		FivebjpksLotterySgExample.Criteria fivebjpksCriteria = example.createCriteria();
		fivebjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		example.setOrderByClause("ideal_time DESC");
		FivebjpksLotterySg fivebjpksLotterySg = fivebjpksLotterySgMapper.selectOneByExample(example);
		return fivebjpksLotterySg;
	}

	/**
	 * @Title: getNextFivebjpksLotterySg
	 * @Description: 获取下期数据
	 * @return FivebjpksLotterySg
	 * @author HANS
	 * @date 2019年4月29日下午9:26:32
	 */
	public FivebjpksLotterySg getNextFivebjpksLotterySg() {
		FivebjpksLotterySgExample nextExample = new FivebjpksLotterySgExample();
		FivebjpksLotterySgExample.Criteria nextFivebjpksCriteria = nextExample.createCriteria();
		nextFivebjpksCriteria.andIdealTimeGreaterThan(DateUtils.getFullString(new Date()));
		nextFivebjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		nextExample.setOrderByClause("issue ASC");
		FivebjpksLotterySg nextFivebjpksLotterySg = this.fivebjpksLotterySgMapper.selectOneByExample(nextExample);
		return nextFivebjpksLotterySg;
	}
    	
}
