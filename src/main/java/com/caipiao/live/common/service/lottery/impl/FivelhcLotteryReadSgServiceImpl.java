package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.FivelhcLotterySg;
import com.caipiao.live.common.mybatis.entity.FivelhcLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.FivelhcLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.FivelhcLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.FivelhcLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.OnelhcLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.LhcUtils;
import com.caipiao.live.common.util.lottery.OnelhcSgUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/** 
 * @ClassName: FivelhcLotterySgServiceImpl 
 * @Description: 五分六合彩服务类
 * @author: HANS
 * @date: 2019年5月21日 下午3:33:37  
 */
@Service
public class FivelhcLotteryReadSgServiceImpl implements FivelhcLotterySgServiceReadSg {
	private static final Logger logger = LoggerFactory.getLogger(FivelhcLotteryReadSgServiceImpl.class);
	
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Autowired
	private FivelhcLotterySgMapper fivelhcLotterySgMapper;
	@Autowired
	private FivelhcLotterySgMapperExt fivelhcLotterySgMapperExt;
	@Autowired
	private OnelhcLotterySgServiceReadSg onelhcLotterySgService;
	@Autowired
	private AusactLotterySgServiceReadSg ausactLotterySgService;
	
	/* (non Javadoc) 
	 * @Title: getFivelhcNewestSgInfo
	 * @Description:  获取5分六合彩开奖 
	 * @return 
	 * @see com.caipiao.live.read.issue.service.result.FivelhcLotterySgService#getFivelhcNewestSgInfo()
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
			Integer openCount = (Integer) redisTemplate.opsForValue().get(openRedisKey);

			if (openCount == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("openStatus", LotteryResultStatus.AUTO);
				map.put("paramTime", TimeHelper.date("yyyy-MM-dd")+"%");
				openCount = fivelhcLotterySgMapperExt.openCountByExample(map);
			}

			if (openCount != null) {
				result.put(AppMianParamEnum.OPENCOUNT.getParamEnName(), openCount);
				// 获取开奖总期数
				Integer sumCount = CaipiaoSumCountEnum.FIVELHC.getSumCount();
				// 计算当日剩余未开奖次数
				result.put(AppMianParamEnum.NOOPENCOUNT.getParamEnName(), sumCount - openCount);
			}

			if (nextFivelhcLotterySg != null) {
				String nextIssue = nextFivelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : String.valueOf(nextFivelhcLotterySg.getIssue());
				String txffnextIssue = fivelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : String.valueOf(fivelhcLotterySg.getIssue());

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
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),fivelhcLotterySg == null ? Constants.DEFAULT_NULL : String.valueOf(fivelhcLotterySg.getIssue()));
					result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
					String timeString = fivelhcLotterySg.getTime() == null ? fivelhcLotterySg.getIdealTime() : fivelhcLotterySg.getTime();
					result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(), LhcUtils.getNumberZodiac(number, timeString));
				}

				if (nextFivelhcLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextFivelhcLotterySg.getIdealTime()) / 1000L);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), String.valueOf(nextFivelhcLotterySg.getIssue()));
				}

			} else {
				if (fivelhcLotterySg != null) {
					if(StringUtils.isEmpty(fivelhcLotterySg.getNumber())) {
						// 获取当前开奖数据 
						fivelhcLotterySg = this.getFivelhcLotterySg();
		        		redisTemplate.opsForValue().set(redisKey, fivelhcLotterySg);
					}
					String number = fivelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : fivelhcLotterySg.getNumber();
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),fivelhcLotterySg == null ? Constants.DEFAULT_NULL : String.valueOf(fivelhcLotterySg.getIssue()));
					result.put(AppMianParamEnum.NUMBER.getParamEnName(), number);
					String timeString = fivelhcLotterySg.getTime() == null ? fivelhcLotterySg.getIdealTime() : fivelhcLotterySg.getTime();
					result.put(AppMianParamEnum.SHENGXIAO.getParamEnName(),LhcUtils.getNumberZodiac(number, timeString));
				}
			}
		} catch (Exception e) {
			logger.error("getFivelhcNewestSgInfo:" + CaipiaoTypeEnum.FIVELHC.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return ResultInfo.ok(result);
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
		nextTFivelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		nextTFivelhcCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
		nextExample.setOrderByClause("issue ASC");
		FivelhcLotterySg nextTjsscLotterySg = this.fivelhcLotterySgMapper.selectOneByExample(nextExample);
		return nextTjsscLotterySg;
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
		fivelhcExample.setOrderByClause("issue DESC");
		FivelhcLotterySg fivelhcLotterySg = fivelhcLotterySgMapper.selectOneByExample(fivelhcExample);
		return fivelhcLotterySg;
	}
	
	/** 
	* @Title: getFivelhcAlgorithmData 
	* @Description: 获取5分六合彩近期开奖数据 
	* @author HANS
	* @date 2019年5月21日下午4:04:44
	*/ 
	public List<FivelhcLotterySg> getFivelhcAlgorithmData(){
		FivelhcLotterySgExample fivelhcExample = new FivelhcLotterySgExample();
		FivelhcLotterySgExample.Criteria fivelhcCriteria = fivelhcExample.createCriteria();
		fivelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		fivelhcExample.setOrderByClause("`ideal_time` DESC");
		fivelhcExample.setOffset(Constants.DEFAULT_INTEGER);
		fivelhcExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        List<FivelhcLotterySg> fivelhcLotterySgList = fivelhcLotterySgMapper.selectByExample(fivelhcExample);
		return fivelhcLotterySgList;
	}

	/* (non Javadoc) 
	 * @Title: getFivelhcSgLong
	 * @Description: 获取5分六合彩长龙数据
	 * @return 
	 * @see com.caipiao.live.read.issue.service.result.FivelhcLotterySgService#getFivelhcSgLong()
	 */ 
	@Override
	public List<Map<String, Object>> getFivelhcSgLong() {
		List<Map<String, Object>> fivelhcLongMapList = new ArrayList<Map<String, Object>>();
		try {
			String algorithm = RedisKeys.FIVELHC_ALGORITHM_VALUE;
			List<FivelhcLotterySg> fivelhcLotterySgList = (List<FivelhcLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(fivelhcLotterySgList)) {
				fivelhcLotterySgList = this.getFivelhcAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, fivelhcLotterySgList, 5, TimeUnit.SECONDS);
			}
			// 特码两面 单双大小
			List<Map<String, Object>> wallBigAndSmallLongList = this.getWallBigAndSmallLong(fivelhcLotterySgList);
			fivelhcLongMapList.addAll(wallBigAndSmallLongList);
			// 正码 总单总双总大总小
			List<Map<String, Object>> totalDoubleAndBigLongList = this.getTotalDoubleAndBigLong(fivelhcLotterySgList);
			fivelhcLongMapList.addAll(totalDoubleAndBigLongList);
			// 正特 单双
			List<Map<String, Object>> ztsigleAndDoubleLongList = this.getZtsigleAndDoubleLong(fivelhcLotterySgList);
			fivelhcLongMapList.addAll(ztsigleAndDoubleLongList);
			// 正特 大小
			List<Map<String, Object>> ztbigAndSmallLongList = this.getZtbigAndSmallLong(fivelhcLotterySgList);
			fivelhcLongMapList.addAll(ztbigAndSmallLongList);
			fivelhcLongMapList = this.addNextIssueInfo(fivelhcLongMapList, fivelhcLotterySgList);
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#FivelhcLotterySgServiceImpl_getFivelhcSgLong_error:", e);
		}
		return fivelhcLongMapList;
	}
	
	/** 
	* @Title: addNextIssueInfo 
	* @Description: 返回
	* @author HANS
	* @date 2019年5月26日下午2:43:19
	*/ 
	public List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> fivelhcLongMapList, List<FivelhcLotterySg> fivelhcLotterySgList){
		List<Map<String, Object>> fivelhcResultLongMapList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(fivelhcLongMapList)) {
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.FIVELHC_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.FIVELHC.getRedisTime();
			FivelhcLotterySg nextFivelhcLotterySg = (FivelhcLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

			if (nextFivelhcLotterySg == null) {
				nextFivelhcLotterySg = this.getNextFivelhcLotterySg();
				// 缓存到下期信息
				redisTemplate.opsForValue().set(nextRedisKey, nextFivelhcLotterySg, redisTime, TimeUnit.MINUTES);
			}
			if(nextFivelhcLotterySg == null) {
				return new ArrayList<Map<String, Object>>();
			}
			// 缓存中取当期信息
			String redisKey = RedisKeys.FIVELHC_RESULT_VALUE;
			FivelhcLotterySg fivelhcLotterySg = (FivelhcLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (fivelhcLotterySg == null) {
				fivelhcLotterySg = this.getFivelhcLotterySg();
				// 缓存到当期信息
				redisTemplate.opsForValue().set(redisKey, fivelhcLotterySg);
			}
			String nextIssue = nextFivelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : String.valueOf(nextFivelhcLotterySg.getIssue());
			String txffnextIssue = fivelhcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : String.valueOf(fivelhcLotterySg.getIssue());
			
			if(StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
				Long nextIssueNum = Long.parseLong(nextIssue);
				Long txffnextIssueNum = Long.parseLong(txffnextIssue);
				Long differenceNum = nextIssueNum - txffnextIssueNum;
				
				// 如果长龙期数与下期期数相差太大长龙不存在
				if(differenceNum < 1 || differenceNum > 2) {
					return new ArrayList<Map<String, Object>>();
				}
			}
			
			for (Map<String, Object> longMap : fivelhcLongMapList) {
				longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), String.valueOf(nextFivelhcLotterySg.getIssue()));
				longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(),  DateUtils.getTimeMillis(nextFivelhcLotterySg.getIdealTime()) / 1000L);
				fivelhcResultLongMapList.add(longMap);
			}
		}
		return fivelhcResultLongMapList;
	}
			
	/** 
	* @Title: getWallBigAndSmallLong 
	* @Description: 获取特码两面 单双、大小
	* @author HANS
	* @date 2019年5月21日下午4:16:42
	*/ 
	public List<Map<String, Object>> getWallBigAndSmallLong(List<FivelhcLotterySg> fivelhcLotterySgList){
		List<Map<String, Object>> fivelhcBigLongMapList = new ArrayList<Map<String, Object>>();
		// 特码两面单双
		Map<String, Object> twoWallDoubleAndSigleDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getTagType());
		// 特码两面大小
		Map<String, Object> twoWallBigAndSmallDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCTMLMBIGDRAGON.getTagType());
		
		// 计算后的数据放入返回集合
		if(twoWallDoubleAndSigleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcBigLongMapList.add(twoWallDoubleAndSigleDragonMap);
		}
		
		if(twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcBigLongMapList.add(twoWallBigAndSmallDragonMap);
		}
		return fivelhcBigLongMapList;
	}
	
	/** 
	* @Title: getTotalDoubleAndBigLong 
	* @Description: 获取正码 总单总双、总大总小 
	* @author HANS
	* @date 2019年5月21日下午4:16:36
	*/ 
	public List<Map<String, Object>> getTotalDoubleAndBigLong(List<FivelhcLotterySg> fivelhcLotterySgList){
		List<Map<String, Object>> fivelhcTotalDoubleAndBigMapList = new ArrayList<Map<String, Object>>();
		// 正码总单总双
		Map<String, Object> totalSigleAndDoubleDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getTagType());
		// 正码总大总小
		Map<String, Object> totalBigAndSmallDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCTOTALBIGDRAGON.getTagType());
		
		// 计算后的数据放入返回集合
		if(totalSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcTotalDoubleAndBigMapList.add(totalSigleAndDoubleDragonMap);
		}
		
		if(totalBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcTotalDoubleAndBigMapList.add(totalBigAndSmallDragonMap);
		}
		return fivelhcTotalDoubleAndBigMapList;
	}
	
	/** 
	* @Title: getZtsigleAndDoubleLong 
	* @Description: 获取 正特 单双
	* @author HANS
	* @date 2019年5月21日下午4:16:30
	*/ 
	public List<Map<String, Object>> getZtsigleAndDoubleLong(List<FivelhcLotterySg> fivelhcLotterySgList){
		List<Map<String, Object>> fivelhcZtsigleAndDoubleMapList = new ArrayList<Map<String, Object>>();
		// 正1特单双
		Map<String, Object> firstSigleAndDoubleDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZYTDOUBLEDRAGON.getTagType());
		// 正2特单双
		Map<String, Object> secondSigleAndDoubleDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZETDOUBLEDRAGON.getTagType());
		// 正3特单双
		Map<String, Object> thirdSigleAndDoubleDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZSTDOUBLEDRAGON.getTagType());
		// 正4特单双
		Map<String, Object> fourthSigleAndDoubleDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZFTDOUBLEDRAGON.getTagType());
		// 正5特单双
		Map<String, Object> fivethSigleAndDoubleDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZWTDOUBLEDRAGON.getTagType());
		// 正6特单双
		Map<String, Object> sixthSigleAndDoubleDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZLTDOUBLEDRAGON.getTagType());
		
		// 计算后的数据放入返回集合
		if(firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtsigleAndDoubleMapList.add(firstSigleAndDoubleDragonMap);
		}
		
		if(secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtsigleAndDoubleMapList.add(secondSigleAndDoubleDragonMap);
		}
		
		if(thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtsigleAndDoubleMapList.add(thirdSigleAndDoubleDragonMap);
		}
		
		if(fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtsigleAndDoubleMapList.add(fourthSigleAndDoubleDragonMap);
		}
		
		if(fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtsigleAndDoubleMapList.add(fivethSigleAndDoubleDragonMap);
		}
		
		if(sixthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtsigleAndDoubleMapList.add(sixthSigleAndDoubleDragonMap);
		}
		return fivelhcZtsigleAndDoubleMapList;
	}
	
	/** 
	* @Title: getZtbigAndSmallLong 
	* @Description: 获取 正特 大小
	* @author HANS
	* @date 2019年5月21日下午4:16:24
	*/ 
	public List<Map<String, Object>> getZtbigAndSmallLong(List<FivelhcLotterySg> fivelhcLotterySgList){
		List<Map<String, Object>> fivelhcZtbigAndSmallMapList = new ArrayList<Map<String, Object>>();
		// 正1特大小
		Map<String, Object> firstBigAndSmallDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZYTBIGDRAGON.getTagType());
		// 正2特大小
		Map<String, Object> secondBigAndSmallDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZETBIGDRAGON.getTagType());
		// 正3特大小
		Map<String, Object> thirdBigAndSmallDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZSTBIGDRAGON.getTagType());
		// 正4特大小
		Map<String, Object> fourthBigAndSmallDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZFTBIGDRAGON.getTagType());
		// 正5特大小
		Map<String, Object> fivethBigAndSmallDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZWTBIGDRAGON.getTagType());
		// 正6特大小
		Map<String, Object> sixthBigAndSmallDragonMap = this.getDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZLTBIGDRAGON.getTagType());
		
		// 计算后的数据放入返回集合
		if(firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtbigAndSmallMapList.add(firstBigAndSmallDragonMap);
		}
		
		if(secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtbigAndSmallMapList.add(secondBigAndSmallDragonMap);
		}
		
		if(thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtbigAndSmallMapList.add(thirdBigAndSmallDragonMap);
		}
		
		if(fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtbigAndSmallMapList.add(fourthBigAndSmallDragonMap);
		}
		
		if(fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtbigAndSmallMapList.add(fivethBigAndSmallDragonMap);
		}
		
		if(sixthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			fivelhcZtbigAndSmallMapList.add(sixthBigAndSmallDragonMap);
		}
		return fivelhcZtbigAndSmallMapList;
	}
	
	/** 
	* @Title: getDragonInfo 
	* @Description: 五分六合彩获取长龙数据
	* @author HANS
	* @date 2019年5月21日下午4:23:03
	*/ 
	public Map<String, Object> getDragonInfo(List<FivelhcLotterySg> fivelhcLotterySgList, int type){
		Map<String, Object> resultDragonMap = new HashMap<String, Object>();
		try {
			if (!CollectionUtils.isEmpty(fivelhcLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < fivelhcLotterySgList.size() ; index++) {
					FivelhcLotterySg fivelhcLotterySg = fivelhcLotterySgList.get(index);
					String numberString = fivelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : fivelhcLotterySg.getNumber();
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateResult(type, numberString);
					
					if(StringUtils.isEmpty(bigOrSmallName)) {
						break;
					}
					// 把第一个结果加入SET集合
					if(index == Constants.DEFAULT_INTEGER) {
						dragonSet.add(bigOrSmallName);
					}
					// 如果第一个和第二个开奖结果不一样，统计截止
					if(index == Constants.DEFAULT_ONE) {
						if(!dragonSet.contains(bigOrSmallName)) {
							// 大/小已经没有龙了不再统计
							break;
						}
						continue;
					}
					// 规则：连续3个开奖一样
					if(index == Constants.DEFAULT_TWO) {
						// 第三个数据
						if(!dragonSet.contains(bigOrSmallName)) {
							// 大/小已经没有龙了不再统计
							break;
						}
						dragonSize = Constants.DEFAULT_THREE;
						continue;
					}
					// 如果大于3个以上，继续统计，直到结果不一样
					if(index > Constants.DEFAULT_TWO) {
						// 大/小统计
						if(!dragonSet.contains(bigOrSmallName)) {
							// 大/小已经没有龙了不再统计
							break;
						}
						dragonSize++;
					}
				}
				// 最近一期开奖数据
				FivelhcLotterySg fivelhcLotterySg = fivelhcLotterySgList.get(Constants.DEFAULT_INTEGER);
				// 组织返回数据	
				if(dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
				    resultDragonMap = this.organizationDragonResultMap(type, fivelhcLotterySg, dragonSet, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#FivelhcLotterySgServiceImpl_getDragonInfo_error:", e);
		}
		return resultDragonMap;
	}
	
	public String calculateResult(int type, String number) {
		String result = Constants.DEFAULT_NULL;
		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 201:
			result = OnelhcSgUtils.getOnelhcBigOrDouble(number,type);//特码两面单双
			break;
		case 202:
			result = OnelhcSgUtils.getOnelhcBigOrDouble(number,type);//特码两面大小
			break;
		case 203:
			result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number,type);//正码总单总双
			break;
		case 204:
			result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number,type);//正码总大总小
			break;
		case 205:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,0);//正1特单双
			break;
		case 206:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,1);//正2特单双
			break;
		case 207:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,2);//正3特单双
			break;
		case 208:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,3);//正4特单双
			break;
		case 209:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,4);//正5特单双
			break;
		case 210:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,5);//正6特单双
			break;
		case 211:
			result = OnelhcSgUtils.getZytBigOrSmall(number,0);//正1特大小
			break;
		case 212:
			result = OnelhcSgUtils.getZytBigOrSmall(number,1);//正2特大小
			break;
		case 213:
			result = OnelhcSgUtils.getZytBigOrSmall(number,2);//正3特大小
			break;
		case 214:
			result = OnelhcSgUtils.getZytBigOrSmall(number,3);//正4特大小
			break;
		case 215:
			result = OnelhcSgUtils.getZytBigOrSmall(number,4);//正5特大小
			break;
		case 216:
			result = OnelhcSgUtils.getZytBigOrSmall(number,5);//正6特大小
			break;
		default:
			break;
		}
		return result;
	}
	
	
	/** 
	* @Title: organizationDragonResultMap 
	* @Description: 获取返回数据 
	* @author HANS
	* @date 2019年5月21日下午4:33:17
	*/ 
	public Map<String, Object> organizationDragonResultMap(int type,FivelhcLotterySg fivelhcLotterySg, Set<String> dragonSet, Integer dragonSize){
		// 有龙情况下查询下期数据
		Map<String, Object> longDragonMap = new HashMap<String, Object>();
		try {
			// 玩法赔率
			Map<String, Object> oddsListMap = this.addOddListInfo(type);;
			// 把获取的赔率加入到返回MAP中
			longDragonMap.putAll(oddsListMap);	
			List<String> dragonList = new ArrayList<String>(dragonSet);
			longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.FIVELHC.getTagCnName());
			longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.FIVELHC.getTagType());
			longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), dragonList.get(Constants.DEFAULT_INTEGER));
			longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.FivelhcLotterySgServiceImpl_organizationDragonResultMap_error:", e);
		}
		return longDragonMap;
	}
	
	/** 
	* @Title: addOddListInfo 
	* @Description: 增加其他参数
	* @author HANS
	* @date 2019年5月15日下午10:32:33
	*/ 
	public Map<String, Object> addOddListInfo(int type) {
		Map<String, Object> longDragonMap = new HashMap<String, Object>();
		// 赔率集合
        Map<String, Object> oddsListMap = new HashMap<String, Object>();
        
		if (type == 201) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getPlayTag());
			// 获取到赔率
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getPlayName(),
					CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getTagType()+"");
			oddsListMap = onelhcLotterySgService.addTmlmDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_TMLM_SIGLE);
		} else if (type == 202) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTMLMBIGDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTMLMBIGDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTMLMBIGDRAGON.getPlayTag());
			// 获取到赔率
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getPlayName(),
					CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getTagType()+"");
			oddsListMap = onelhcLotterySgService.addTmlmDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_TMLM_BIG);
		} else if (type == 203) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getPlayTag());
			// 获取五分六合彩 正码总单总双和正码总大总小赔率数据
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getPlayName(),
					CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getTagType()+"");
			oddsListMap = onelhcLotterySgService.addZmtotalDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_ZMTOTAL_SIGLE);
		} else if (type == 204) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTOTALBIGDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTOTALBIGDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCTOTALBIGDRAGON.getPlayTag());
			// 获取五分六合彩 正码总单总双和正码总大总小赔率数据
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getLhcOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getPlayName(),
					CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getTagType()+"");
			oddsListMap = onelhcLotterySgService.addZmtotalDoublePlayMapList(twoWallplayAndOddListInfo, Constants.LHC_PLAYWAY_ZMTOTAL_BIG);
		} else if (type == 205) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZYTDOUBLEDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZYTDOUBLEDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZYTDOUBLEDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
		} else if (type == 206) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZETDOUBLEDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZETDOUBLEDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZETDOUBLEDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
		} else if (type == 207) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZSTDOUBLEDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZSTDOUBLEDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZSTDOUBLEDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
		} else if (type == 208) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZFTDOUBLEDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZFTDOUBLEDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZFTDOUBLEDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
		} else if (type == 209) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZWTDOUBLEDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZWTDOUBLEDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZWTDOUBLEDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
		} else if (type == 210) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZLTDOUBLEDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZLTDOUBLEDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZLTDOUBLEDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_SIGLE, twoWallplayAndOddListInfo);
		} else if (type == 211) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZYTBIGDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZYTBIGDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZYTBIGDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
		} else if (type == 212) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZETBIGDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZETBIGDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZETBIGDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
		} else if (type == 213) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZSTBIGDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZSTBIGDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZSTBIGDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
		} else if (type == 214) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZFTBIGDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZFTBIGDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZFTBIGDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
		} else if (type == 215) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZWTBIGDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZWTBIGDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZWTBIGDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
		} else if (type == 216) {
			longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZLTBIGDRAGON.getTagType());
			longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZLTBIGDRAGON.getTagCnName());
			longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVELHCZLTBIGDRAGON.getPlayTag());
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = this.getZhengTePlayInfo(type);
			oddsListMap = onelhcLotterySgService.addZytDoublePlayMapList(Constants.LHC_PLAYWAY_ZT_BIG, twoWallplayAndOddListInfo);
		}
		// 把获取的赔率加入到返回MAP中
		longDragonMap.putAll(oddsListMap);
		return longDragonMap;
	}
	

	/** 
	* @Title: getZhengTePlayInfo 
	* @Description: 按照玩法获取赔率 
	* @author HANS
	* @date 2019年5月29日下午2:58:56
	 * @return
	*/ 
	public PlayAndOddListInfoVO getZhengTePlayInfo(int type) {
		// 定义返回值	
    	PlayAndOddListInfoVO twoWallplayAndOddListInfo = new PlayAndOddListInfoVO();
    	// 获取五分六合彩 正1特单双\正1特大小赔率数据   
    	if (type == 205 || type == 211) {
    		twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCZYTDOUBLEDRAGON.getPlayName(), 
    				CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZYTDOUBLEDRAGON.getTagType()+"",Constants.LHC_PLAYWAY_ZT_ONE);	
    	} else if(type == 206 || type == 212) {
    		// 获取五分六合彩 正2特单双\正2特大小赔率数据   
    		twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCZETDOUBLEDRAGON.getPlayName(), 
    				CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZETDOUBLEDRAGON.getTagType()+"",Constants.LHC_PLAYWAY_ZT_TWO);
    	} else if(type == 207 || type == 213) {
    		// 获取五分六合彩 正3特单双\正3特大小赔率数据   
    		twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCZSTDOUBLEDRAGON.getPlayName(), 
    				CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZSTDOUBLEDRAGON.getTagType()+"",Constants.LHC_PLAYWAY_ZT_THREE);
    	} else if(type == 208 || type == 214) {
    		// 获取五分六合彩 正4特单双\正4特大小赔率数据   
    		twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCZFTDOUBLEDRAGON.getPlayName(), 
    				CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZFTDOUBLEDRAGON.getTagType()+"",Constants.LHC_PLAYWAY_ZT_FOUR);
    	} else if(type == 209 || type == 215) {
    		// 获取五分六合彩 正5特单双\正5特大小赔率数据   
    		twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCZWTDOUBLEDRAGON.getPlayName(), 
    				CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZWTDOUBLEDRAGON.getTagType()+"",Constants.LHC_PLAYWAY_ZT_FIVE);
    	} else if(type == 210 || type == 216) {
    		// 获取五分六合彩 正6特单双\正6特大小赔率数据   
    		twoWallplayAndOddListInfo = ausactLotterySgService.getLhcZhengTeOddsList(CaipiaoTypeEnum.FIVELHC.getTagCnName(), CaipiaoPlayTypeEnum.FIVELHCZLTDOUBLEDRAGON.getPlayName(), 
    				CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZLTDOUBLEDRAGON.getTagType()+"",Constants.LHC_PLAYWAY_ZT_SIX);
    	}   
    	return twoWallplayAndOddListInfo;
	}
}
