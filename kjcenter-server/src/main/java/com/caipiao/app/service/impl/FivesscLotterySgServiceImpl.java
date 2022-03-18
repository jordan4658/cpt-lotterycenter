package com.caipiao.app.service.impl;

import com.caipiao.app.service.FivesscLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.tool.CaipiaoUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.FivesscLotterySgMapper;
import com.mapper.domain.FivesscLotterySg;
import com.mapper.domain.FivesscLotterySgExample;
import com.mapper.FivesscCountSgdxdsMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import com.mapper.domain.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/** 
 * @ClassName: FivesscLotterySgServiceImpl 
 * @Description: 5分时时彩赛果服务类
 * @author: HANS
 * @date: 2019年5月16日 下午6:52:24  
 */
@Service
public class FivesscLotterySgServiceImpl implements FivesscLotterySgService {
	private static final Logger logger = LoggerFactory.getLogger(FivesscLotterySgServiceImpl.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private FivesscLotterySgMapper fivesscLotterySgMapper;
	@Autowired
	private FivesscCountSgdxdsMapper fivesscCountSgdxdsMapper;

	@Override
	public Map<String, Object> getFivesscNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullResult();
		try {
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.FIVESSC_NEXT_VALUE;
			FivesscLotterySg nextFivesscLotterySg = (FivesscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);
			// 缓存到下期信息
			Long redisTime = CaipiaoRedisTimeEnum.FIVESSC.getRedisTime();

			if (nextFivesscLotterySg == null) {
				nextFivesscLotterySg = this.getNextFivesscLotterySg();
				redisTemplate.opsForValue().set(nextRedisKey, nextFivesscLotterySg, redisTime, TimeUnit.MINUTES);
			}

			// 缓存中取开奖结果
			String redisKey = RedisKeys.FIVESSC_RESULT_VALUE;
			FivesscLotterySg fivesscLotterySg = (FivesscLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (fivesscLotterySg == null) {
				fivesscLotterySg = this.getFivesscLotterySg();
				redisTemplate.opsForValue().set(redisKey, fivesscLotterySg);
			}

			if (nextFivesscLotterySg != null) {
				String nextIssue = nextFivesscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextFivesscLotterySg.getIssue();
				String txffnextIssue = fivesscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : fivesscLotterySg.getIssue();

				if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;

					if (differenceNum < 1 || differenceNum > 2) {
						// 查询下一期信息
						nextFivesscLotterySg = this.getNextFivesscLotterySg();
						redisTemplate.opsForValue().set(nextRedisKey, nextFivesscLotterySg, redisTime,
								TimeUnit.MINUTES);
						// 查询当前期开奖信息
						fivesscLotterySg = this.getFivesscLotterySg();
						redisTemplate.opsForValue().set(redisKey, fivesscLotterySg);
					}
				}

				if (fivesscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumberForFivessc(fivesscLotterySg, result);
//					String niuWinner = NnKlOperationUtils.getNiuWinner(fivesscLotterySg.getNumber());
//					result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);
				}

				if (nextFivesscLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
							DateUtils.getTimeMillis(nextFivesscLotterySg.getIdealTime()) / 1000L);
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextFivesscLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFivesscLotterySg.getIssue());
				}

			} else {
				result = DefaultResultUtil.getNullResult();
				
				if (fivesscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumberForFivessc(fivesscLotterySg, result);
//					String niuWinner = NnKlOperationUtils.getNiuWinner(fivesscLotterySg.getNumber());
//					result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);
				}
			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.FIVESSC.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}

	@Override
	public Map<String, Object> getFivesscStasticSgInfo(String date) {
		Map<String, Object> result = new HashMap<>();
		try {
			FivesscCountSgdxdsExample example = new FivesscCountSgdxdsExample();
			FivesscCountSgdxdsExample.Criteria jssscCriteria = example.createCriteria();
			jssscCriteria.andDateGreaterThanOrEqualTo(DateUtils.parseDate(date,DateUtils.datePattern));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.parseDate(date,DateUtils.datePattern));
			calendar.add(Calendar.DAY_OF_MONTH,1);
			jssscCriteria.andDateLessThan(calendar.getTime());
			example.setOrderByClause("date DESC");
			FivesscCountSgdxds fivesscCountSgdxds = fivesscCountSgdxdsMapper.selectOneByExample(example);
			if(fivesscCountSgdxds != null){
				result.put("one",fivesscCountSgdxds.getOne());
				result.put("two",fivesscCountSgdxds.getTwo());
				result.put("three",fivesscCountSgdxds.getThree());
				result.put("four",fivesscCountSgdxds.getFour());
				result.put("five",fivesscCountSgdxds.getFive());
				result.put("six",fivesscCountSgdxds.getSix());
				result.put("seven",fivesscCountSgdxds.getSeven());
				result.put("eight",fivesscCountSgdxds.getEight());
				result.put("nine",fivesscCountSgdxds.getNigh());
				result.put("zero",fivesscCountSgdxds.getTen());
				result.put("big",fivesscCountSgdxds.getBig());
				result.put("small",fivesscCountSgdxds.getSmall());
				result.put("odd",fivesscCountSgdxds.getOdd());
				result.put("even",fivesscCountSgdxds.getEven());
			}else{
                result.put("one",0);
                result.put("two",0);
                result.put("three",0);
                result.put("four",0);
                result.put("five",0);
                result.put("six",0);
                result.put("seven",0);
                result.put("eight",0);
                result.put("nine",0);
                result.put("zero",0);
                result.put("big",0);
                result.put("small",0);
                result.put("odd",0);
                result.put("even",0);
            }
		} catch (Exception e) {
			logger.error("getJssscStasticSgInfo:" + CaipiaoTypeEnum.FIVESSC.getTagType() + "异常： " ,e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}

	/**
	 * @Title: getIssueSumAndAllNumber
	 * @Description: 查询下期数据
	 */
	private FivesscLotterySg getNextFivesscLotterySg() {
		FivesscLotterySgExample nextExample = new FivesscLotterySgExample();
		FivesscLotterySgExample.Criteria nextFivesscCriteria = nextExample.createCriteria();
		nextFivesscCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		nextFivesscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		nextExample.setOrderByClause("ideal_time ASC");
		FivesscLotterySg nextTjsscLotterySg = this.fivesscLotterySgMapper.selectOneByExample(nextExample);
		return nextTjsscLotterySg;
	}

	/**
	 * @Title: openCount
	 * @Description: 计算开奖次数和未开奖次数
	 */
	private FivesscLotterySg getFivesscLotterySg() {
		FivesscLotterySgExample example = new FivesscLotterySgExample();
		FivesscLotterySgExample.Criteria fivesscCriteria = example.createCriteria();
		fivesscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		example.setOrderByClause("ideal_time DESC");
		FivesscLotterySg fivesscLotterySg = this.fivesscLotterySgMapper.selectOneByExample(example);
		return fivesscLotterySg;
	}

	/**
	 * @Title: getIssueSumAndAllNumber
	 * @Description: 组织开奖号码和合值
	 */
	private void getIssueSumAndAllNumberForFivessc(FivesscLotterySg fivesscLotterySg, Map<String, Object> result) {
		Integer wan = fivesscLotterySg.getWan();
		Integer qian = fivesscLotterySg.getQian();
		Integer bai = fivesscLotterySg.getBai();
		Integer shi = fivesscLotterySg.getShi();
		Integer ge = fivesscLotterySg.getGe();
		String issue = fivesscLotterySg.getIssue();
		result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
//		result.put(AppMianParamEnum.SOURCENUMBER.getParamEnName(), fivesscLotterySg.getNumber());
		// 组织开奖号码
		String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
		// 计算开奖号码合值
		Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
	}

}
