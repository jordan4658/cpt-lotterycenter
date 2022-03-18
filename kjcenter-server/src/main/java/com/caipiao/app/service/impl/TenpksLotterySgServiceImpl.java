package com.caipiao.app.service.impl;

import com.caipiao.app.service.TenpksLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.TenbjpksLotterySgMapper;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 	10分PK10
 *
 * @author Asheng
 * @create 2019-03-13 11:05
 **/
@Service
public class TenpksLotterySgServiceImpl implements TenpksLotterySgService {
	private static final Logger logger = LoggerFactory.getLogger(TenpksLotterySgServiceImpl.class);
    @Autowired
	private RedisTemplate<String, Object> redisTemplate;
    @Autowired
	private TenbjpksLotterySgMapper tenbjpksLotterySgMapper;
	
    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullResult();
		try {
			// 缓存中取开奖结果
			String redisKey = RedisKeys.TENPKS_RESULT_VALUE;
			TenbjpksLotterySg tenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (tenbjpksLotterySg == null) {
				// 查询最近一期信息
				tenbjpksLotterySg = this.getTenbjpksLotterySg();
				redisTemplate.opsForValue().set(redisKey, tenbjpksLotterySg);
			}

			// 缓存中取开奖数量
			String openRedisKey = RedisKeys.TENPKS_OPEN_VALUE;
			Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

//			if (openCount == null) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("openStatus", LotteryResultStatus.AUTO);
//				map.put("paramTime", TimeHelper.date("yyyy-MM-dd"));
//				openCount = tenbjpksLotterySgMapper.openCountByExample(map);
//				redisTemplate.opsForValue().set(openRedisKey, openCount);
//			}
			if (openCount != null) {
				result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
				// 获取开奖总期数
				Integer sumCount = CaipiaoSumCountEnum.TENPKS.getSumCount();
				// 计算当日剩余未开奖次数
				result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
			}
			String nextRedisKey = RedisKeys.TENPKS_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.TENPKS.getRedisTime();
			TenbjpksLotterySg nextTenbjpksLotterySg = (TenbjpksLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

			if (nextTenbjpksLotterySg == null) {
				nextTenbjpksLotterySg = this.getNextTenbjpksLotterySg();
				redisTemplate.opsForValue().set(nextRedisKey, nextTenbjpksLotterySg, redisTime, TimeUnit.MINUTES);
			}
			if (nextTenbjpksLotterySg != null) {
				String nextIssue = nextTenbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL
						: nextTenbjpksLotterySg.getIssue();
				String txffnextIssue = tenbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL
						: tenbjpksLotterySg.getIssue();

				if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					if (nextIssue.compareTo(txffnextIssue) < 0 || nextIssue.compareTo(txffnextIssue) > 2) {
						nextTenbjpksLotterySg = this.getNextTenbjpksLotterySg();
						redisTemplate.opsForValue().set(nextRedisKey, nextTenbjpksLotterySg, redisTime,
								TimeUnit.MINUTES);
						// 查询最近一期信息
						tenbjpksLotterySg = this.getTenbjpksLotterySg();
						redisTemplate.opsForValue().set(redisKey, tenbjpksLotterySg);
					}
				}

				if (tenbjpksLotterySg != null) {
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),
							tenbjpksLotterySg == null ? Constants.DEFAULT_NULL : tenbjpksLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(),
							tenbjpksLotterySg == null ? Constants.DEFAULT_NULL : tenbjpksLotterySg.getNumber());
				}

				if (nextTenbjpksLotterySg != null) {
					// 获取下一期开奖时间
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTenbjpksLotterySg.getIssue());
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextTenbjpksLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
							DateUtils.getTimeMillis(nextTenbjpksLotterySg.getIdealTime()) / 1000L);
				}
			} else {

				if (tenbjpksLotterySg != null) {
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),
							tenbjpksLotterySg == null ? Constants.DEFAULT_NULL : tenbjpksLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(),
							tenbjpksLotterySg == null ? Constants.DEFAULT_NULL : tenbjpksLotterySg.getNumber());
				}
			}

			//计算冠亚军和，1-5龙虎
			if(tenbjpksLotterySg != null){
				String number[] = tenbjpksLotterySg.getNumber().split(",");
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
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.TENPKS.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return ResultInfo.ok(result);
	}

	public TenbjpksLotterySg getTenbjpksLotterySg() {
		TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
		TenbjpksLotterySgExample.Criteria tenbjpksCriteria = example.createCriteria();
		tenbjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		example.setOrderByClause("ideal_time DESC");
		TenbjpksLotterySg tenbjpksLotterySg = tenbjpksLotterySgMapper.selectOneByExample(example);
		return tenbjpksLotterySg;
	}

	/**
	 * @Title: getTenbjpksLotterySg
	 * @Description: 获取下期数据
	 * @return TenbjpksLotterySg
	 * @author HANS
	 * @date 2019年4月29日下午9:24:21
	 */
	public TenbjpksLotterySg getNextTenbjpksLotterySg() {
		TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
		TenbjpksLotterySgExample.Criteria bjpksCriteria = example.createCriteria();
		bjpksCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		bjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		example.setOrderByClause("issue ASC");
		TenbjpksLotterySg nextTenbjpksLotterySg = tenbjpksLotterySgMapper.selectOneByExample(example);
		return nextTenbjpksLotterySg;
	}
	
	
}
