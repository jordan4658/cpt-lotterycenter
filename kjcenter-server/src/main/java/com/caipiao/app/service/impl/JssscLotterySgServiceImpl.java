package com.caipiao.app.service.impl;

import com.caipiao.app.service.JssscLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.tool.CaipiaoUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.JssscLotterySgMapper;
import com.mapper.JssscCountSgdxdsMapper;
import com.mapper.domain.JssscLotterySg;
import com.mapper.domain.JssscLotterySgExample;
import org.apache.commons.lang3.StringUtils;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/** 
 * @ClassName: JssscLotterySgServiceImpl 
 * @Description: 德州时时彩服务类
 * @author: HANS
 * @date: 2019年5月12日 下午11:09:38  
 */
@Service("jssscLotterySgServiceImpl")
public class JssscLotterySgServiceImpl implements JssscLotterySgService {

	private final Logger logger = LoggerFactory.getLogger(JssscLotterySgServiceImpl.class);
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private JssscLotterySgMapper jssscLotterySgMapper;
	@Autowired
	private JssscCountSgdxdsMapper jssscCountSgdxdsMappper;
    
	@Override
	public Map<String, Object> getJssscNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullResult();
		try {
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.JSSSC_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.JSSSC.getRedisTime();
			JssscLotterySg nextTjsscLotterySg = (JssscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

			if (nextTjsscLotterySg == null) {
				nextTjsscLotterySg = this.getNextJssscLotterySg();
				// 缓存到下期信息
				redisTemplate.opsForValue().set(nextRedisKey, nextTjsscLotterySg, redisTime, TimeUnit.MINUTES);
			}
			// 缓存中取开奖结果
			String redisKey = RedisKeys.JSSSC_RESULT_VALUE;
			JssscLotterySg jssscLotterySg = (JssscLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (jssscLotterySg == null) {
				jssscLotterySg = this.getJssscLotterySg();
				redisTemplate.opsForValue().set(redisKey, jssscLotterySg);
			}

			if (nextTjsscLotterySg != null) {
				String nextIssue = nextTjsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTjsscLotterySg.getIssue();
				String txffnextIssue = jssscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : jssscLotterySg.getIssue();

				if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;

					if (differenceNum < 1 || differenceNum > 2) {
						nextTjsscLotterySg = this.getNextJssscLotterySg();
						// 缓存到下期信息
						redisTemplate.opsForValue().set(nextRedisKey, nextTjsscLotterySg, redisTime, TimeUnit.MINUTES);
						// 查询当前期数据
						jssscLotterySg = this.getJssscLotterySg();
						redisTemplate.opsForValue().set(redisKey, jssscLotterySg);
					}
				}

				if (jssscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumber(jssscLotterySg, result);
				}

				if (nextTjsscLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
							DateUtils.getTimeMillis(nextTjsscLotterySg.getIdealTime()) / 1000L);
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextTjsscLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTjsscLotterySg.getIssue());
				}
			} else {
				result = DefaultResultUtil.getNullResult();
				
				if (jssscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumber(jssscLotterySg, result);
				}
			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.JSSSC.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}

	@Override
	public Map<String, Object> getJssscStasticSgInfo(String date) {
		Map<String, Object> result = new HashMap<>();
		try {
			JssscCountSgdxdsExample example = new JssscCountSgdxdsExample();
			JssscCountSgdxdsExample.Criteria jssscCriteria = example.createCriteria();
			jssscCriteria.andDateGreaterThanOrEqualTo(DateUtils.parseDate(date,DateUtils.datePattern));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.parseDate(date,DateUtils.datePattern));
			calendar.add(Calendar.DAY_OF_MONTH,1);
			jssscCriteria.andDateLessThan(calendar.getTime());
			example.setOrderByClause("date DESC");
			JssscCountSgdxds jssscCountSgdxds = jssscCountSgdxdsMappper.selectOneByExample(example);
			if(jssscCountSgdxds != null){
				result.put("one",jssscCountSgdxds.getOne());
				result.put("two",jssscCountSgdxds.getTwo());
				result.put("three",jssscCountSgdxds.getThree());
				result.put("four",jssscCountSgdxds.getFour());
				result.put("five",jssscCountSgdxds.getFive());
				result.put("six",jssscCountSgdxds.getSix());
				result.put("seven",jssscCountSgdxds.getSeven());
				result.put("eight",jssscCountSgdxds.getEight());
				result.put("nine",jssscCountSgdxds.getNigh());
				result.put("zero",jssscCountSgdxds.getTen());
				result.put("big",jssscCountSgdxds.getBig());
				result.put("small",jssscCountSgdxds.getSmall());
				result.put("odd",jssscCountSgdxds.getOdd());
				result.put("even",jssscCountSgdxds.getEven());
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
			return result;
		} catch (Exception e) {
			logger.error("getJssscStasticSgInfo:" + CaipiaoTypeEnum.JSSSC.getTagType() + "异常： " ,e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}

	/**
	 * @Title: getNextJssscLotterySg
	 * @Description: 查询下一期数据
	 * @return JssscLotterySg
	 * @author HANS
	 * @date 2019年5月13日下午1:59:40
	 */
	private JssscLotterySg getNextJssscLotterySg() {
		Date nowDate = new Date();
		JssscLotterySgExample next_example = new JssscLotterySgExample();
		JssscLotterySgExample.Criteria next_TjsscCriteria = next_example.createCriteria();
		next_TjsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullString(nowDate));
		next_TjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		next_example.setOrderByClause("ideal_time ASC");
		JssscLotterySg next_TjsscLotterySg = this.jssscLotterySgMapper.selectOneByExample(next_example);
		return next_TjsscLotterySg;
	}

	/**
	 * @Title: getJssscLotterySg
	 * @Description: 获取下期开奖数据
	 * @author HANS
	 * @date 2019年5月15日下午2:02:00
	 */
	public JssscLotterySg getJssscLotterySg() {
		JssscLotterySgExample example = new JssscLotterySgExample();
		JssscLotterySgExample.Criteria jssscCriteria = example.createCriteria();
		jssscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		example.setOrderByClause("ideal_time DESC");
		JssscLotterySg jssscLotterySg = this.jssscLotterySgMapper.selectOneByExample(example);
		return jssscLotterySg;
	}

	/**
	 * @Title: getIssueSumAndAllNumber
	 * @Description: 组织开奖号码和合值
	 */
	public void getIssueSumAndAllNumber(JssscLotterySg jssscLotterySg, Map<String, Object> result) {
		Integer wan = jssscLotterySg.getWan();
		Integer qian = jssscLotterySg.getQian();
		Integer bai = jssscLotterySg.getBai();
		Integer shi = jssscLotterySg.getShi();
		Integer ge = jssscLotterySg.getGe();
		String issue = jssscLotterySg.getIssue();
		result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
		// 组织开奖号码
		String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
		// 计算开奖号码合值
		Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
	}
	
}
