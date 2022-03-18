package com.caipiao.app.service.impl;

import com.caipiao.app.service.OnelhcLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.enums.CaipiaoRedisTimeEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.OnelhcLotterySgMapper;
import com.mapper.domain.OnelhcLotterySg;
import com.mapper.domain.OnelhcLotterySgExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** 
 * @ClassName: OnelhcLotterySgServiceImpl 
 * @Description: 一分六合彩服务类
 * @author: HANS
 * @date: 2019年5月14日 下午9:06:58  
 */
@Service
public class OnelhcLotterySgServiceImpl implements OnelhcLotterySgService {
	
	private static final Logger logger = LoggerFactory.getLogger(OnelhcLotterySgServiceImpl.class);
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Autowired
	private OnelhcLotterySgMapper onelhcLotterySgMapper;

	/* (non Javadoc) 
	 * @Title: getOnelhcNewestSgInfo
	 * @Description: 获取一分六合彩开奖数据
	 * @return 
	 * @see com.caipiao.business.read.service.result.OnelhcLotterySgService#getOnelhcNewestSgInfo() 
	 */ 
	@Override
	public ResultInfo<Map<String, Object>> getOnelhcNewestSgInfo() {
		Map<String, Object> result = new HashMap<>();
		try {
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.ONELHC_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.ONELHC.getRedisTime();
			OnelhcLotterySg nextOnelhcLotterySg = (OnelhcLotterySg)redisTemplate.opsForValue().get(nextRedisKey);
			
			if(nextOnelhcLotterySg == null) {
				nextOnelhcLotterySg = this.getNextOnelhcLotterySg();
				// 缓存到下期信息
				redisTemplate.opsForValue().set(nextRedisKey, nextOnelhcLotterySg, redisTime, TimeUnit.MINUTES);
			}
			// 缓存中取开奖结果
			String redisKey = RedisKeys.ONELHC_RESULT_VALUE;
			OnelhcLotterySg onelhcLotterySg = (OnelhcLotterySg)redisTemplate.opsForValue().get(redisKey);
			
			if(onelhcLotterySg == null) {
				// 查询最近一期开奖信息
				onelhcLotterySg = this.getOnelhcLotterySg();
	            redisTemplate.opsForValue().set(redisKey, onelhcLotterySg);
			}
			
			if (nextOnelhcLotterySg != null) {
				String nextIssue = nextOnelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextOnelhcLotterySg.getIssue();
				String txffnextIssue = onelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getIssue();
				
				if(StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;
					
					if(differenceNum < 1 || differenceNum > 2) {
						// 缓存到下期信息
						nextOnelhcLotterySg = this.getNextOnelhcLotterySg();
						redisTemplate.opsForValue().set(nextRedisKey, nextOnelhcLotterySg, redisTime, TimeUnit.MINUTES);
						// 查询最近一期开奖信息
						onelhcLotterySg = this.getOnelhcLotterySg();
			            redisTemplate.opsForValue().set(redisKey, onelhcLotterySg);
					}
				}
				
				if (onelhcLotterySg != null) {
					String number = onelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getNumber();
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),onelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
					result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, onelhcLotterySg.getTime()));
				}
				
				if (nextOnelhcLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextOnelhcLotterySg.getIdealTime()) / 1000L);
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextOnelhcLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextOnelhcLotterySg.getIssue());
				}
			} else {
				result = DefaultResultUtil.getNullResult();
				
				if (onelhcLotterySg != null) {
					String number = onelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getNumber();
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),onelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
					result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(),LhcUtils.getNumberZodiac(number, onelhcLotterySg.getTime()));
				}
			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.ONELHC.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return ResultInfo.ok(result);
	}

	/**
	 * @Title: getNextOnelhcLotterySg
	 * @Description: 获取下期数据
	 * @author HANS
	 * @date 2019年5月15日上午11:22:52
	 */
	private OnelhcLotterySg getNextOnelhcLotterySg() {
		OnelhcLotterySgExample onelhcExample = new OnelhcLotterySgExample();
		OnelhcLotterySgExample.Criteria onelhcCriteria = onelhcExample.createCriteria();
		onelhcCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		onelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		onelhcExample.setOrderByClause("`ideal_time` ASC");
		OnelhcLotterySg nextOnelhcLotterySg = onelhcLotterySgMapper.selectOneByExample(onelhcExample);
		return nextOnelhcLotterySg;
	}

	/**
	 * @Title: getOnelhcLotterySg
	 * @Description: 获取当前开奖数据
	 * @return OnelhcLotterySg
	 * @author HANS
	 * @date 2019年5月15日上午11:27:33
	 */
	private OnelhcLotterySg getOnelhcLotterySg() {
		OnelhcLotterySgExample onelhcExample = new OnelhcLotterySgExample();
		OnelhcLotterySgExample.Criteria onelhcCriteria = onelhcExample.createCriteria();
		onelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		onelhcExample.setOrderByClause("ideal_time DESC");
		OnelhcLotterySg onelhcLotterySg = onelhcLotterySgMapper.selectOneByExample(onelhcExample);
		return onelhcLotterySg;
	}


}
