package com.caipiao.app.service.impl;

import com.caipiao.app.service.FtjspksLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.enums.CaipiaoRedisTimeEnum;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.FtjspksLotterySgMapper;
import com.mapper.domain.FtjspksLotterySg;
import com.mapper.domain.FtjspksLotterySgExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 德州PK10番摊资讯查询
 *
 * @author Hans
 * @create 2019-03-27 20:07
 **/
@Service
public class FtjspksLotterySgServiceImpl implements FtjspksLotterySgService {
	private static final Logger logger = LoggerFactory.getLogger(FtjspksLotterySgServiceImpl.class);
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
    private FtjspksLotterySgMapper ftjspksLotterySgMapper;

	/**
	   *app 首页展示数据
	 * 
	 * */
	@Override
	public Map<String, Object> getNewestSgInfo() {
		// 定义返回结果
		Map<String, Object> result = DefaultResultUtil.getNullResult();
		try {
			// 缓存中取开奖结果
			String redisKey = RedisKeys.JSPKFT_RESULT_VALUE;
			FtjspksLotterySg ftjspksLotterySg = (FtjspksLotterySg) redisTemplate.opsForValue().get(redisKey);
			
			if(ftjspksLotterySg == null) {
				ftjspksLotterySg = this.getFtjspksLotterySg();
				redisTemplate.opsForValue().set(redisKey, ftjspksLotterySg);
			}
			
			// 缓存中取开奖数量
			String nextRedisKey = RedisKeys.JSPKFT_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.JSPKFT.getRedisTime();
			FtjspksLotterySg nextFtjspksLotterySg = (FtjspksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);
			
			if(nextFtjspksLotterySg == null) {
				nextFtjspksLotterySg = this.getNextJspksLotterySg();
		        redisTemplate.opsForValue().set(nextRedisKey, nextFtjspksLotterySg, redisTime, TimeUnit.SECONDS);
			}
			
			if(nextFtjspksLotterySg != null) {
				String nextIssue = nextFtjspksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextFtjspksLotterySg.getIssue();
				String txffnextIssue = ftjspksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : ftjspksLotterySg.getIssue();
				
				if(StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;
					
					if(differenceNum < 1 || differenceNum > 2) {
						nextFtjspksLotterySg = this.getNextJspksLotterySg();
				        redisTemplate.opsForValue().set(nextRedisKey, nextFtjspksLotterySg, redisTime, TimeUnit.SECONDS);
				        // 获取当前开奖数据
				        ftjspksLotterySg = this.getFtjspksLotterySg();
						redisTemplate.opsForValue().set(redisKey, ftjspksLotterySg);
					}
				}
				
				if(ftjspksLotterySg != null) {
					if(StringUtils.isEmpty(ftjspksLotterySg.getNumber())) {
						// 获取当前开奖数据
				        ftjspksLotterySg = this.getFtjspksLotterySg();
						redisTemplate.opsForValue().set(redisKey, ftjspksLotterySg);
					}
					result.put(AppMianParamEnum.ISSUE.getParamEnName(), ftjspksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : ftjspksLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(), ftjspksLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : ftjspksLotterySg.getNumber());
				}
				
				if(nextFtjspksLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFtjspksLotterySg.getIdealTime())/ 1000L);
		        	result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), Integer.parseInt(nextFtjspksLotterySg.getIssue()));
				}
			} else {
				if(ftjspksLotterySg != null) {
					if(StringUtils.isEmpty(ftjspksLotterySg.getNumber())) {
						// 获取当前开奖数据
				        ftjspksLotterySg = this.getFtjspksLotterySg();
						redisTemplate.opsForValue().set(redisKey, ftjspksLotterySg);
					}
					result.put(AppMianParamEnum.ISSUE.getParamEnName(), ftjspksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : ftjspksLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(), ftjspksLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : ftjspksLotterySg.getNumber());
				}
			}
			// 缓存中取开奖数量
			String openRedisKey = RedisKeys.JSPKFT_OPEN_VALUE;
			Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);
			
			if(null == openCount) {
				openCount = Constants.DEFAULT_INTEGER;
			}
			result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
			// 获取开奖总期数
	        Integer sumCount = CaipiaoSumCountEnum.JSPKFT.getSumCount();
	        // 计算当日剩余未开奖次数
	        result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.JSPKFT.getTagType() + " 异常： " ,e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}


	/**
	 * @Title: getFtjspksLotterySg
	 * @Description: 获取当前开奖数据
	 * @return FtjspksLotterySg
	 * @author HANS
	 * @date 2019年5月3日下午10:18:00
	 */
	public FtjspksLotterySg getFtjspksLotterySg() {
		FtjspksLotterySgExample ftjspksExample = new FtjspksLotterySgExample();
		FtjspksLotterySgExample.Criteria ftjspkCriteria = ftjspksExample.createCriteria();
		ftjspkCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		ftjspksExample.setOrderByClause("ideal_time DESC");
		FtjspksLotterySg ftjspksLotterySg = ftjspksLotterySgMapper.selectOneByExample(ftjspksExample);
		return ftjspksLotterySg;
	}

	/**
	 * 查询下一期投注信息
	 *
	 * */
	public FtjspksLotterySg getNextJspksLotterySg() {
		FtjspksLotterySgExample ftjspksExample = new FtjspksLotterySgExample();
		FtjspksLotterySgExample.Criteria cqsscCriteria = ftjspksExample.createCriteria();
		cqsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullString(new Date()));
		ftjspksExample.setOrderByClause("ideal_time ASC");
		FtjspksLotterySg ftjspksLotterySg = ftjspksLotterySgMapper.selectOneByExample(ftjspksExample);
		return ftjspksLotterySg;
	}
	

}
