package com.caipiao.app.service.impl;
import com.caipiao.app.service.TensscLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.tool.CaipiaoUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.TensscLotterySgMapper;
import com.mapper.domain.FivesscCountSgdxds;
import com.mapper.domain.*;
import com.mapper.domain.FivesscCountSgdxdsExample;
import com.mapper.domain.TensscLotterySg;
import com.mapper.domain.TensscLotterySgExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import com.mapper.TensscCountSgdxdsMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TensscLotterySgServiceImpl implements TensscLotterySgService {

	private static final Logger logger = LoggerFactory.getLogger(TensscLotterySgServiceImpl.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private TensscLotterySgMapper tensscLotterySgMapper;
    @Autowired
	private TensscCountSgdxdsMapper tensscCountSgdxdsMapper;
    
    @Override
    public Map<String, Object> getTensscNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullResult();
		try {
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.TENSSC_NEXT_VALUE;
			TensscLotterySg nextTensscLotterySg = (TensscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);
			// 缓存到下期信息
			Long redisTime = CaipiaoRedisTimeEnum.TENSSC.getRedisTime();
			if (nextTensscLotterySg == null) {
				nextTensscLotterySg = this.getNextTensscLotterySg();
				redisTemplate.opsForValue().set(nextRedisKey, nextTensscLotterySg, redisTime, TimeUnit.MINUTES);
			}

			// 缓存到开奖结果
			String redisKey = RedisKeys.TENSSC_RESULT_VALUE;
			TensscLotterySg tensscLotterySg = (TensscLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (tensscLotterySg == null) {
				// 查询最近一期开奖信息
				tensscLotterySg = this.getTensscLotterySg();
				redisTemplate.opsForValue().set(redisKey, tensscLotterySg);
			}

			if (nextTensscLotterySg != null) {
				String nextIssue = nextTensscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTensscLotterySg.getIssue();
				String txffnextIssue = tensscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : tensscLotterySg.getIssue();

				if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;

					if (differenceNum < 1 || differenceNum > 2) {
						// 查询下一期信息
						nextTensscLotterySg = this.getNextTensscLotterySg();
						redisTemplate.opsForValue().set(nextRedisKey, nextTensscLotterySg, redisTime, TimeUnit.MINUTES);
						// 查询最近一期开奖信息
						tensscLotterySg = this.getTensscLotterySg();
						redisTemplate.opsForValue().set(redisKey, tensscLotterySg);
					}
				}

				if (tensscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumber(tensscLotterySg, result);
				}

				if (nextTensscLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
							DateUtils.getTimeMillis(nextTensscLotterySg.getIdealTime()) / 1000L);
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextTensscLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTensscLotterySg.getIssue());
				}

			} else {
				result = DefaultResultUtil.getNullResult();
				
				if (tensscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumber(tensscLotterySg, result);
				}
			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.TENSSC.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}

	@Override
	public Map<String, Object> getTensscStasticSgInfo(String date) {
		Map<String, Object> result = new HashMap<>();
		try {
			TensscCountSgdxdsExample example = new TensscCountSgdxdsExample();
			TensscCountSgdxdsExample.Criteria tensscCriteria = example.createCriteria();
			tensscCriteria.andDateGreaterThanOrEqualTo(DateUtils.parseDate(date,DateUtils.datePattern));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.parseDate(date,DateUtils.datePattern));
			calendar.add(Calendar.DAY_OF_MONTH,1);
			tensscCriteria.andDateLessThan(calendar.getTime());
			example.setOrderByClause("date DESC");
			TensscCountSgdxds tensscCountSgdxds = tensscCountSgdxdsMapper.selectOneByExample(example);
			if(tensscCountSgdxds != null){
				result.put("one",tensscCountSgdxds.getOne());
				result.put("two",tensscCountSgdxds.getTwo());
				result.put("three",tensscCountSgdxds.getThree());
				result.put("four",tensscCountSgdxds.getFour());
				result.put("five",tensscCountSgdxds.getFive());
				result.put("six",tensscCountSgdxds.getSix());
				result.put("seven",tensscCountSgdxds.getSeven());
				result.put("eight",tensscCountSgdxds.getEight());
				result.put("nine",tensscCountSgdxds.getNigh());
				result.put("zero",tensscCountSgdxds.getTen());
				result.put("big",tensscCountSgdxds.getBig());
				result.put("small",tensscCountSgdxds.getSmall());
				result.put("odd",tensscCountSgdxds.getOdd());
				result.put("even",tensscCountSgdxds.getEven());
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
			logger.error("getTensscStasticSgInfo:" + CaipiaoTypeEnum.TENSSC.getTagType() + "异常： " ,e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}

	/**
	 * @Title: getNextTensscLotterySg
	 * @Description: 获取下期数据
	 * @author HANS
	 * @date 2019年5月16日下午8:27:51
	 */
	public TensscLotterySg getNextTensscLotterySg() {
		TensscLotterySgExample next_example = new TensscLotterySgExample();
		TensscLotterySgExample.Criteria next_TensscCriteria = next_example.createCriteria();
		next_TensscCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		next_TensscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		next_example.setOrderByClause("ideal_time ASC");
		TensscLotterySg next_TensscLotterySg = this.tensscLotterySgMapper.selectOneByExample(next_example);
		return next_TensscLotterySg;
	}

	/**
	 * @Title: getTensscLotterySg
	 * @Description: 获取当期开奖数据
	 * @author HANS
	 * @date 2019年5月16日下午8:28:39
	 */
	public TensscLotterySg getTensscLotterySg() {
		TensscLotterySgExample example = new TensscLotterySgExample();
		TensscLotterySgExample.Criteria tensscCriteria = example.createCriteria();
		tensscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		example.setOrderByClause("ideal_time DESC");
		TensscLotterySg tensscLotterySg = this.tensscLotterySgMapper.selectOneByExample(example);
		return tensscLotterySg;
	}

	/**
	 * @Title: getIssueSumAndAllNumber
	 * @Description: 组织开奖号码和合值
	 */
	public void getIssueSumAndAllNumber(TensscLotterySg tensscLotterySg, Map<String, Object> result) {
		Integer wan = tensscLotterySg.getWan();
		Integer qian = tensscLotterySg.getQian();
		Integer bai = tensscLotterySg.getBai();
		Integer shi = tensscLotterySg.getShi();
		Integer ge = tensscLotterySg.getGe();
		String issue = tensscLotterySg.getIssue();
		result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
		// 组织开奖号码
		String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan,qian,bai,shi,ge);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
		// 计算开奖号码合值
		Integer sumInteger = CaipiaoUtils.getNumberTotal(tensscLotterySg.getNumber());
		result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
	}
    
	
	
}
