package com.caipiao.app.service.impl;

import com.caipiao.app.service.FivelhcLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.FivelhcLotterySgMapper;
import com.mapper.domain.FivelhcLotterySg;
import com.mapper.domain.FivelhcLotterySgExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** 
 * @ClassName: FivelhcLotterySgServiceImpl 
 * @Description: 五分六合彩服务类
 * @author: HANS
 * @date: 2019年5月21日 下午3:33:37  
 */
@Service
public class FivelhcLotterySgServiceImpl implements FivelhcLotterySgService {
	private static final Logger logger = LoggerFactory.getLogger(FivelhcLotterySgServiceImpl.class);
	
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Autowired
	private FivelhcLotterySgMapper fivelhcLotterySgMapper;
	
	/* (non Javadoc) 
	 * @Title: getFivelhcNewestSgInfo
	 * @Description:  获取5分六合彩开奖 
	 * @return 
	 * @see com.caipiao.business.read.service.result.FivelhcLotterySgService#getFivelhcNewestSgInfo() 
	 */ 
	@Override
	public ResultInfo<Map<String, Object>> getFivelhcNewestSgInfo() {
		Map<String, Object> result = new HashMap<>();
		try {
			// 缓存中取当期信息
			String redisKey = RedisKeys.FIVELHC_RESULT_VALUE;
			FivelhcLotterySg fivelhcLotterySg = (FivelhcLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (fivelhcLotterySg == null) {
				fivelhcLotterySg = this.getFivelhcLotterySg();
				// 缓存到当期信息
				redisTemplate.opsForValue().set(redisKey, fivelhcLotterySg);
			}
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.FIVELHC_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.FIVELHC.getRedisTime();
			FivelhcLotterySg nextFivelhcLotterySg = (FivelhcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

			if (nextFivelhcLotterySg == null) {
				nextFivelhcLotterySg = this.getNextFivelhcLotterySg();
				// 缓存到下期信息
				redisTemplate.opsForValue().set(nextRedisKey, nextFivelhcLotterySg, redisTime, TimeUnit.MINUTES);
			}

			String openRedisKey = RedisKeys.FIVELHC_OPEN_VALUE;
//			Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);
//
//			if (openCount == null) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("openStatus", LotteryResultStatus.AUTO);
//				map.put("paramTime", TimeHelper.date("yyyy-MM-dd"));
//				openCount = fivelhcLotterySgMapper.openCountByExample(map);
//			}
//
//			if (openCount != null) {
//				result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
//				// 获取开奖总期数
//				Integer sumCount = CaipiaoSumCountEnum.FIVELHC.getSumCount();
//				// 计算当日剩余未开奖次数
//				result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
//			}

			if (nextFivelhcLotterySg != null) {
				String nextIssue = nextFivelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextFivelhcLotterySg.getIssue();
				String txffnextIssue = fivelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : fivelhcLotterySg.getIssue();

				if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;

					if (differenceNum < 1 || differenceNum > 2) {
						nextFivelhcLotterySg = this.getNextFivelhcLotterySg();
						// 缓存到下期信息
						redisTemplate.opsForValue().set(nextRedisKey, nextFivelhcLotterySg, redisTime, TimeUnit.MINUTES);
						// 缓存到当期信息
						fivelhcLotterySg = this.getFivelhcLotterySg();
						redisTemplate.opsForValue().set(redisKey, fivelhcLotterySg);
					}
				}
				if (fivelhcLotterySg != null) {
					if(StringUtils.isEmpty(fivelhcLotterySg.getNumber())) {
						// 获取当前开奖数据 
						fivelhcLotterySg = this.getFivelhcLotterySg();
		        		redisTemplate.opsForValue().set(redisKey, fivelhcLotterySg);
					}
					String number = fivelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : fivelhcLotterySg.getNumber();
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),fivelhcLotterySg == null ? Constants.DEFAULT_NULL : fivelhcLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
					String timeString = fivelhcLotterySg.getTime() == null ? fivelhcLotterySg.getIdealTime() : fivelhcLotterySg.getTime();
					result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(),LhcUtils.getNumberZodiac(number, timeString));
				}

				if (nextFivelhcLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFivelhcLotterySg.getIdealTime()) / 1000L);
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextFivelhcLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFivelhcLotterySg.getIssue());
				}

			} else {
				if (fivelhcLotterySg != null) {
					if(StringUtils.isEmpty(fivelhcLotterySg.getNumber())) {
						// 获取当前开奖数据 
						fivelhcLotterySg = this.getFivelhcLotterySg();
		        		redisTemplate.opsForValue().set(redisKey, fivelhcLotterySg);
					}
					String number = fivelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : fivelhcLotterySg.getNumber();
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),fivelhcLotterySg == null ? Constants.DEFAULT_NULL : fivelhcLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
					String timeString = fivelhcLotterySg.getTime() == null ? fivelhcLotterySg.getIdealTime() : fivelhcLotterySg.getTime();
					result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, timeString));
				}
			}
		} catch (Exception e) {
			logger.error("getFivelhcNewestSgInfo:" + CaipiaoTypeEnum.FIVELHC.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return ResultInfo.ok(result);
	}

	/**
	 * @Title: getFivelhcLotterySg
	 * @Description: 查询当前开奖数据
	 * @return FivelhcLotterySg
	 * @author HANS
	 * @date 2019年5月3日下午4:39:14
	 */
	public FivelhcLotterySg getFivelhcLotterySg() {
		FivelhcLotterySgExample fivelhcExample = new FivelhcLotterySgExample();
		FivelhcLotterySgExample.Criteria fivelhcCriteria = fivelhcExample.createCriteria();
		fivelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		fivelhcExample.setOrderByClause("ideal_time DESC");
		FivelhcLotterySg fivelhcLotterySg = fivelhcLotterySgMapper.selectOneByExample(fivelhcExample);
		return fivelhcLotterySg;
	}

	/**
	 * @Title: getNextFivelhcLotterySg
	 * @Description: 查询下期开奖数据
	 * @return FivelhcLotterySg
	 * @author HANS
	 * @date 2019年5月3日下午4:45:56
	 */
	public FivelhcLotterySg getNextFivelhcLotterySg() {
		FivelhcLotterySgExample nextExample = new FivelhcLotterySgExample();
		FivelhcLotterySgExample.Criteria nextTFivelhcCriteria = nextExample.createCriteria();
		nextTFivelhcCriteria.andIdealTimeGreaterThan(DateUtils.getFullString(new Date()));
		nextTFivelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		nextExample.setOrderByClause("issue ASC");
		FivelhcLotterySg nextTjsscLotterySg = this.fivelhcLotterySgMapper.selectOneByExample(nextExample);
		return nextTjsscLotterySg;
	}
}
