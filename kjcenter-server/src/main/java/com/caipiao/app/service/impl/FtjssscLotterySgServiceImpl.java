package com.caipiao.app.service.impl;
import com.caipiao.app.service.FtjssscLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.enums.CaipiaoRedisTimeEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.CaipiaoUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.FtjssscLotterySgMapper;
import com.mapper.domain.FtjssscLotterySg;
import com.mapper.domain.FtjssscLotterySgExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 德州时时彩番摊资讯查询
 *
 * @author Hans
 * @create 2019-03-27 20:07
 **/
@Service("ftjssscLotterySgServiceImpl")
public class FtjssscLotterySgServiceImpl implements FtjssscLotterySgService {
	private static final Logger logger = LoggerFactory.getLogger(FtjssscLotterySgServiceImpl.class);
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
    private FtjssscLotterySgMapper ftjssscLotterySgMapper;
    
	/**
	   *  首页展示数据
	 * 
	 * */
	@Override
	public Map<String, Object> getNewestSgInfo() {
		// 定义返回结果
		Map<String, Object> result = new HashMap<>();
		try {
        	// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.JSSSCFT_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.JSSSCFT.getRedisTime();
			FtjssscLotterySg nextFtjssscLotterySg = (FtjssscLotterySg)redisTemplate.opsForValue().get(nextRedisKey);
			
			if(nextFtjssscLotterySg == null) {
				nextFtjssscLotterySg = this.getNextJssscLotterySg();
				redisTemplate.opsForValue().set(nextRedisKey, nextFtjssscLotterySg, redisTime, TimeUnit.SECONDS);
			}
			// 缓存中取开奖结果
        	String redisKey = RedisKeys.JSSSCFT_RESULT_VALUE;
        	FtjssscLotterySg ftjssscLotterySg = (FtjssscLotterySg)redisTemplate.opsForValue().get(redisKey);
        	
        	if(ftjssscLotterySg == null) {
        		ftjssscLotterySg = this.selectRecentIssue();
        		redisTemplate.opsForValue().set(redisKey, ftjssscLotterySg);
        	}
			
			if(nextFtjssscLotterySg != null) {
				String nextIssue = nextFtjssscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextFtjssscLotterySg.getIssue();
				String txffnextIssue = ftjssscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : ftjssscLotterySg.getIssue();
				
				if(StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;
					
					if(differenceNum < 1 || differenceNum > 2) {
						nextFtjssscLotterySg = this.getNextJssscLotterySg();
						redisTemplate.opsForValue().set(nextRedisKey, nextFtjssscLotterySg, redisTime, TimeUnit.SECONDS);
						// 获取当前期的数据
						ftjssscLotterySg = this.selectRecentIssue();
		        		redisTemplate.opsForValue().set(redisKey, ftjssscLotterySg);
					}
				}
	        	
	        	if(ftjssscLotterySg != null) {
	        		if(StringUtils.isEmpty(ftjssscLotterySg.getNumber())) {
	        			ftjssscLotterySg = this.selectRecentIssue();
		        		redisTemplate.opsForValue().set(redisKey, ftjssscLotterySg);
	        		}
	        		// 组织开奖号码
	    			this.getIssueSumAndAllNumber(ftjssscLotterySg, result);
//	    			// 计算开奖次数
//	    			this.openCount(ftjssscLotterySg, result);
	        	}
	        	
	        	if(nextFtjssscLotterySg != null) {
	        		result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFtjssscLotterySg.getIdealTime()) / 1000L);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFtjssscLotterySg.getIssue());
	        	}
			} else {
				if(ftjssscLotterySg != null) {
	        		if(StringUtils.isEmpty(ftjssscLotterySg.getNumber())) {
	        			ftjssscLotterySg = this.selectRecentIssue();
		        		redisTemplate.opsForValue().set(redisKey, ftjssscLotterySg);
	        		}
	        		// 组织开奖号码
	    			this.getIssueSumAndAllNumber(ftjssscLotterySg, result);
//	    			// 计算开奖次数
//	    			this.openCount(ftjssscLotterySg, result);
	        	}
			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.JSSSCFT.getTagType() + " 异常： " ,e);
			result = DefaultResultUtil.getNullResult();
		}
        return result;
	}

	/**
	 * 查询下一期投注信息
	 *
	 * */
	public FtjssscLotterySg getNextJssscLotterySg() {
		FtjssscLotterySgExample nextFtjssscExample = new FtjssscLotterySgExample();
		FtjssscLotterySgExample.Criteria nextFtJsssCriteria = nextFtjssscExample.createCriteria();
		nextFtJsssCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		nextFtJsssCriteria.andIdealTimeGreaterThan(DateUtils.getFullString(new Date()));
		nextFtjssscExample.setOrderByClause("ideal_time ASC");
		FtjssscLotterySg nextFtjssscLotterySg = this.ftjssscLotterySgMapper.selectOneByExample(nextFtjssscExample);
		return nextFtjssscLotterySg;
	}

	/**
	 * 查询最近一期信息
	 *
	 * */
	public FtjssscLotterySg selectRecentIssue() {
		FtjssscLotterySgExample ftJsssExample = new FtjssscLotterySgExample();
		FtjssscLotterySgExample.Criteria ftJsssCriteria = ftJsssExample.createCriteria();
		ftJsssCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		ftJsssExample.setOrderByClause("ideal_time DESC");
		FtjssscLotterySg ftjssscLotterySg = ftjssscLotterySgMapper.selectOneByExample(ftJsssExample);
		return ftjssscLotterySg;
	}

	/**
	 * @Title: getIssueSumAndAllNumber
	 * @Description: 组织开奖号码和合值
	 */
	public void getIssueSumAndAllNumber(FtjssscLotterySg ftjssscLotterySg, Map<String, Object> result) {
		Integer wan = ftjssscLotterySg.getWan();
		Integer qian = ftjssscLotterySg.getQian();
		Integer bai = ftjssscLotterySg.getBai();
		Integer shi = ftjssscLotterySg.getShi();
		Integer ge = ftjssscLotterySg.getGe();
		String issue = ftjssscLotterySg.getIssue();
		result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
		// 组织开奖号码
		//String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), ftjssscLotterySg.getNumber());
		// 计算开奖号码合值
		Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.HE.getParamEnName(),sumInteger);
	}

}
