package com.caipiao.app.service.impl;

import com.caipiao.app.service.JspksLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.JsbjpksLotterySgMapper;
import com.mapper.domain.JsbjpksLotterySg;
import com.mapper.domain.JsbjpksLotterySgExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** 
 * @ClassName: JspksLotterySgServiceImpl 
 * @Description: 德州PK10服务类
 * @author: HANS
 * @date: 2019年5月13日 下午9:26:11  
 */
@Service
public class JspksLotterySgServiceImpl implements JspksLotterySgService {
	
	private final Logger logger = LoggerFactory.getLogger(JspksLotterySgServiceImpl.class);
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Autowired
    private JsbjpksLotterySgMapper jsbjpksLotterySgMapper;
	
	/* (non Javadoc) 
	 * @Title: getJspksNewestSgInfo
	 * @Description: 获取德州PK10赛果
	 * @return 
	 * @see com.caipiao.business.read.service.result.JspksLotterySgService#getJspksNewestSgInfo() 
	 */ 
	@Override
	public ResultInfo<Map<String, Object>> getJspksNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullResult();
		try {
			// 缓存中取开奖结果
			String redisKey = RedisKeys.JSPKS_RESULT_VALUE;
			JsbjpksLotterySg jsbjpksLotterySg = (JsbjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (jsbjpksLotterySg == null) {
				jsbjpksLotterySg = this.getJsbjpksLotterySg();
				redisTemplate.opsForValue().set(redisKey, jsbjpksLotterySg);
			}

			// 缓存中取开奖数量
			String openRedisKey = RedisKeys.JSPKS_OPEN_VALUE;
			Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

//			if (openCount == null) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("openStatus", LotteryResultStatus.AUTO);
//				map.put("paramTime", TimeHelper.date("yyyy-MM-dd"));
//				openCount = jsbjpksLotterySgMapper.openCountByExample(map);
//				redisTemplate.opsForValue().set(openRedisKey, openCount);
//			}

			if (openCount != null) {
				result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
				// 获取开奖总期数
				Integer sumCount = CaipiaoSumCountEnum.JSPKS.getSumCount();
				// 计算当日剩余未开奖次数
				result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
			}
			String nextRedisKey = RedisKeys.JSPKS_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.JSPKS.getRedisTime();
			JsbjpksLotterySg nextJsbjpksLotterySg = (JsbjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

			if (nextJsbjpksLotterySg == null) {
				nextJsbjpksLotterySg = this.getNextJspksLotterySg();
				redisTemplate.opsForValue().set(nextRedisKey, nextJsbjpksLotterySg, redisTime, TimeUnit.MINUTES);
			}

			if (nextJsbjpksLotterySg != null) {
				String nextIssue = nextJsbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL
						: nextJsbjpksLotterySg.getIssue();
				String txffnextIssue = jsbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL
						: jsbjpksLotterySg.getIssue();

				if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;

					if (differenceNum < 1 || differenceNum > 2) {
						nextJsbjpksLotterySg = this.getNextJspksLotterySg();
						redisTemplate.opsForValue().set(nextRedisKey, nextJsbjpksLotterySg, redisTime,
								TimeUnit.MINUTES);
						// 获取当前开奖数据
						jsbjpksLotterySg = this.getJsbjpksLotterySg();
						redisTemplate.opsForValue().set(redisKey, jsbjpksLotterySg);
					}
				}
				if (jsbjpksLotterySg != null) {
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),jsbjpksLotterySg == null ? Constants.DEFAULT_NULL : jsbjpksLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(),jsbjpksLotterySg == null ? Constants.DEFAULT_NULL : jsbjpksLotterySg.getNumber());
//					String jsNiuWinner = NnJsOperationUtils.getNiuWinner(jsbjpksLotterySg.getNumber());
//					result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), jsNiuWinner);
				}

				if (nextJsbjpksLotterySg != null) {
					// 获取下一期开奖时间
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextJsbjpksLotterySg.getIssue());
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextJsbjpksLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextJsbjpksLotterySg.getIdealTime()) / 1000L);
				}

			} else {
				if (jsbjpksLotterySg != null) {
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),jsbjpksLotterySg == null ? Constants.DEFAULT_NULL : jsbjpksLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(),jsbjpksLotterySg == null ? Constants.DEFAULT_NULL : jsbjpksLotterySg.getNumber());
//					String jsNiuWinner = NnJsOperationUtils.getNiuWinner(jsbjpksLotterySg.getNumber());
//					result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), jsNiuWinner);
				}
			}

			//计算冠亚军和，1-5龙虎
			if(jsbjpksLotterySg != null){
				String number[] = jsbjpksLotterySg.getNumber().split(",");
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
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.JSPKS.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return ResultInfo.ok(result);
	}
	
	
	/**
	 * @Title: getJsbjpksLotterySg
	 * @Description: 获取当前开奖数据
	 * @return JsbjpksLotterySg
	 * @author HANS
	 * @date 2019年5月3日下午1:26:17
	 */
	public JsbjpksLotterySg getJsbjpksLotterySg() {
		JsbjpksLotterySgExample example = new JsbjpksLotterySgExample();
		JsbjpksLotterySgExample.Criteria jsbjpksCriteria = example.createCriteria();
		jsbjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		example.setOrderByClause("ideal_time DESC");
		JsbjpksLotterySg jsbjpksLotterySg = jsbjpksLotterySgMapper.selectOneByExample(example);
		return jsbjpksLotterySg;
	}
	

	/**
	 * @Title: getNextJspksLotterySg
	 * @Description: 获取预开奖数据
	 * @return JsbjpksLotterySg
	 * @author HANS
	 * @date 2019年5月3日下午1:25:39
	 */
	private JsbjpksLotterySg getNextJspksLotterySg() {
		Date nowDate = new Date();
		JsbjpksLotterySgExample next_example = new JsbjpksLotterySgExample();
		JsbjpksLotterySgExample.Criteria next_TjsscCriteria = next_example.createCriteria();
		next_TjsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullString(nowDate));
		next_TjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		next_example.setOrderByClause("ideal_time ASC");
		JsbjpksLotterySg next_TjsscLotterySg = this.jsbjpksLotterySgMapper.selectOneByExample(next_example);
		return next_TjsscLotterySg;
	}


}
