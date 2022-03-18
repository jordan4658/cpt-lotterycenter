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
import com.caipiao.live.common.mybatis.entity.JsbjpksLotterySg;
import com.caipiao.live.common.mybatis.entity.JsbjpksLotterySgExample;
import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;
import com.caipiao.live.common.mybatis.mapper.JsbjpksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperext.sg.JsbjpksLotterySgMapperExt;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JspksLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.JspksSgUtils;
import com.caipiao.live.common.util.lottery.NnJsOperationUtils;
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
 * @ClassName: JspksLotterySgServiceImpl 
 * @Description: 德州PK10服务类
 * @author: HANS
 * @date: 2019年5月13日 下午9:26:11  
 */
@Service
public class JspksLotteryReadSgServiceImpl implements JspksLotterySgServiceReadSg {
	
	private final Logger logger = LoggerFactory.getLogger(JspksLotteryReadSgServiceImpl.class);
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Autowired
    private JsbjpksLotterySgMapper jsbjpksLotterySgMapper;
	@Autowired
	private JsbjpksLotterySgMapperExt jsbjpksLotterySgMapperExt;
	@Autowired
	private AusactLotterySgServiceReadSg ausactLotterySgService;
	
	/* (non Javadoc) 
	 * @Title: getJspksNewestSgInfo
	 * @Description: 获取德州PK10赛果
	 * @return 
	 * @see com.caipiao.live.read.issue.service.result.JspksLotterySgService#getJspksNewestSgInfo()
	 */ 
	@Override
	public ResultInfo<Map<String, Object>> getJspksNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullPkResult();
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

			if (openCount == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("openStatus", LotteryResultStatus.AUTO);
				map.put("paramTime", TimeHelper.date("yyyy-MM-dd")+"%");
				openCount = jsbjpksLotterySgMapperExt.openCountByExample(map);
				redisTemplate.opsForValue().set(openRedisKey, openCount);
			}

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
					String jsNiuWinner = NnJsOperationUtils.getNiuWinner(jsbjpksLotterySg.getNumber());
					result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), jsNiuWinner);
				}

				if (nextJsbjpksLotterySg != null) {
					// 获取下一期开奖时间
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextJsbjpksLotterySg.getIssue());
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextJsbjpksLotterySg.getIdealTime()) / 1000L);
				}

			} else {
				if (jsbjpksLotterySg != null) {
					result.put(AppMianParamEnum.ISSUE.getParamEnName(),jsbjpksLotterySg == null ? Constants.DEFAULT_NULL : jsbjpksLotterySg.getIssue());
					result.put(AppMianParamEnum.NUMBER.getParamEnName(),jsbjpksLotterySg == null ? Constants.DEFAULT_NULL : jsbjpksLotterySg.getNumber());
					String jsNiuWinner = NnJsOperationUtils.getNiuWinner(jsbjpksLotterySg.getNumber());
					result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), jsNiuWinner);
				}
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
		next_TjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		next_TjsscCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(nowDate));
		next_example.setOrderByClause("ideal_time ASC");
		JsbjpksLotterySg next_TjsscLotterySg = this.jsbjpksLotterySgMapper.selectOneByExample(next_example);
		return next_TjsscLotterySg;
	}
	
	/* (non Javadoc) 
	 * @Title: getJspksSgLong
	 * @Description: 获取德州PK10长龙 
	 * @return 
	 * @see com.caipiao.live.read.issue.service.result.JspksLotterySgService#getJspksSgLong()
	 */ 
	@Override
	public List<Map<String, Object>> getJspksSgLong() {
		List<Map<String, Object>> jspksLongMapList = new ArrayList<Map<String, Object>>();
		try {
			String algorithm = RedisKeys.JSPKS_ALGORITHM_VALUE;
			List<JsbjpksLotterySg> jsbjpksLotterySgList = (List<JsbjpksLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(jsbjpksLotterySgList)) {
				jsbjpksLotterySgList = this.getJsbjpksAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, jsbjpksLotterySgList, 10, TimeUnit.SECONDS);
			}
			// 大小长龙
			List<Map<String, Object>> bigAndSmallLongList = this.getBigAndSmallLong(jsbjpksLotterySgList);
			jspksLongMapList.addAll(bigAndSmallLongList);
			// 单双长龙
			List<Map<String, Object>> sigleAndDoubleLongList = this.getDoubleAndSingleLong(jsbjpksLotterySgList);
			jspksLongMapList.addAll(sigleAndDoubleLongList);
			// 龙虎长龙
			List<Map<String, Object>> dragonAndTigleLongList = this.getTrigleAndDragonLong(jsbjpksLotterySgList);
			jspksLongMapList.addAll(dragonAndTigleLongList);
			// 增加下期数据
			jspksLongMapList = this.addNextIssueInfo(jspksLongMapList, jsbjpksLotterySgList);
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#JspksLotterySgServiceImpl_getJspksSgLong_error:", e);
		}
		return jspksLongMapList;
	}
	
	/** 
	* @Title: addNextIssueInfo 
	* @Description: 返回
	* @author HANS
	* @date 2019年5月26日下午2:43:19
	*/ 
	private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> jspksLongMapList, List<JsbjpksLotterySg> jsbjpksLotterySgList){
		List<Map<String, Object>> jspksResultLongMapList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(jspksLongMapList)) {
			// 下期数据
			String nextRedisKey = RedisKeys.JSPKS_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.JSPKS.getRedisTime();
			JsbjpksLotterySg nextJsbjpksLotterySg = (JsbjpksLotterySg)redisTemplate.opsForValue().get(nextRedisKey);
			
			if(nextJsbjpksLotterySg == null) {
				nextJsbjpksLotterySg = this.queryJsbjpksNextSg();
				// 缓存到下期信息
				redisTemplate.opsForValue().set(nextRedisKey, nextJsbjpksLotterySg,10, TimeUnit.SECONDS);
			}
			
			if(nextJsbjpksLotterySg == null) {
				return new ArrayList<Map<String, Object>>();
			}
			JsbjpksLotterySg jsbjpksLotterySg = jsbjpksLotterySgList.get(Constants.DEFAULT_INTEGER);
			String nextIssue = nextJsbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextJsbjpksLotterySg.getIssue();
			String txffnextIssue = jsbjpksLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : jsbjpksLotterySg.getIssue();
			
			if(StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
				Long nextIssueNum = Long.parseLong(nextIssue);
				Long txffnextIssueNum = Long.parseLong(txffnextIssue);
				Long differenceNum = nextIssueNum - txffnextIssueNum;
				
				// 如果长龙期数与下期期数相差太大长龙不存在
				if(differenceNum < 1 || differenceNum > 2) {
					return new ArrayList<Map<String, Object>>();
				}
			}
			
			for (Map<String, Object> longMap : jspksLongMapList) {
				longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextJsbjpksLotterySg.getIssue());
				longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(),  DateUtils.getTimeMillis(nextJsbjpksLotterySg.getIdealTime()) / 1000L);
				jspksResultLongMapList.add(longMap);
			}
		}
		return jspksResultLongMapList;
	}
	
	/** 
	* @Title: getBigAndSmallLong 
	* @Description: 获取大小的长龙数据
	* @return List<Map<String,Object>>
	* @author HANS
	* @date 2019年5月13日下午10:49:38
	*/ 
	public List<Map<String, Object>> getBigAndSmallLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		List<Map<String, Object>> jspksBigLongMapList = new ArrayList<Map<String, Object>>();
		//冠亚和大小
		Map<String, Object> gyhDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGYHBIG.getTagType());
		//冠军大小
		Map<String, Object> gjbDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJBIG.getTagType());
		//亚军大小
		Map<String, Object> yjbDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJBIG.getTagType());
		//第三名大小
		Map<String, Object> dsmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMBIG.getTagType());
		//第四名大小
		Map<String, Object> dfmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMBIG.getTagType());
		//第五名大小
		Map<String, Object> dwmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDWMBIG.getTagType());
		//第六名大小
		Map<String, Object> dlmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDLMBIG.getTagType());
		//第七名大小
		Map<String, Object> dqmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDQMBIG.getTagType());
		//第八名大小
		Map<String, Object> dmmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDMMBIG.getTagType());
		//第九名大小
		Map<String, Object> djmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDJMBIG.getTagType());
		//第十名大小
		Map<String, Object> dtmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDTMBIG.getTagType());
		
		// 计算后的数据放入返回集合
		if(gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(gyhDragonMap);
		}
		
		if(gjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(gjbDragonMap);
		}
		
		if(yjbDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(yjbDragonMap);
		}
		
		if(dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(dsmDragonMap);
		}
		
		if(dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(dfmDragonMap);
		}
		
		if(dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(dwmDragonMap);
		}
		
		if(dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(dlmDragonMap);
		}
		
		if(dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(dqmDragonMap);
		}
		
		if(dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(dmmDragonMap);
		}
		
		if(djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(djmDragonMap);
		}
		
		if(dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksBigLongMapList.add(dtmDragonMap);
		}
		return jspksBigLongMapList;
	}
	
	/** 
	* @Title: getDoubleAndSingleLong 
	* @Description:  获取单双长龙的数据
	* @return List<Map<String,Object>>
	* @author HANS
	* @date 2019年5月13日下午10:50:31
	*/ 
	public List<Map<String, Object>> getDoubleAndSingleLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		List<Map<String, Object>> jspksDoubleLongMapList = new ArrayList<Map<String, Object>>();
		//冠亚和单双
		Map<String, Object> gyhDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGYHDOUBLE.getTagType());
		//冠军单双
		Map<String, Object> gjDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJDOUBLE.getTagType());
		//亚军单双
		Map<String, Object> syjDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJDOUBLE.getTagType());
		//第三名单双
		Map<String, Object> dsmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMDOUBLE.getTagType());
		//第四名单双
		Map<String, Object> dfmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMDOUBLE.getTagType());
		//第五名单双
		Map<String, Object> dwmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDWMDOUBLE.getTagType());
		//第六名单双
		Map<String, Object> dlmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDLMDOUBLE.getTagType());
		//第七名单双
		Map<String, Object> dqmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDQMDOUBLE.getTagType());
		//第八名单双
		Map<String, Object> dmmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDMMDOUBLE.getTagType());
		//第九名单双
		Map<String, Object> djmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDJMDOUBLE.getTagType());
		//第十名单双
		Map<String, Object> dtmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDTMDOUBLE.getTagType());
		
		if (gyhDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(gyhDragonMap);
		}
		
		if (gjDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(gjDragonMap);
		}
		
		if (syjDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(syjDragonMap);
		}
		
		if (dsmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(dsmDragonMap);
		}
		
		if (dfmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(dfmDragonMap);
		}
		
		if (dwmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(dwmDragonMap);
		}
		
		if (dlmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(dlmDragonMap);
		}
		
		if (dqmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(dqmDragonMap);
		}
		
		if (dmmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(dmmDragonMap);
		}
		
		if (djmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(djmDragonMap);
		}
		
		if (dtmDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksDoubleLongMapList.add(dtmDragonMap);
		}
		return jspksDoubleLongMapList;
	}
	
	/** 
	* @Title: getDoubleAndSingleLong 
	* @Description: 获取龙虎长龙的数据 
	* @return List<Map<String,Object>>
	* @author HANS
	* @date 2019年5月13日下午10:53:00
	*/ 
	public List<Map<String, Object>> getTrigleAndDragonLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		List<Map<String, Object>> jspksTrigLongMapList = new ArrayList<Map<String, Object>>();
		//冠军龙虎
		Map<String, Object> gjtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJTIDRAGON.getTagType());
		//亚军龙虎
		Map<String, Object> yjtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJTIDRAGON.getTagType());
		//第三名龙虎
		Map<String, Object> dsmtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMTIDRAGON.getTagType());
		//第四名龙虎
		Map<String, Object> dfmtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMTIDRAGON.getTagType());
		//第五名龙虎
		Map<String, Object> dwmtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDWMTIDRAGON.getTagType());
		
		if(gjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksTrigLongMapList.add(gjtiDragonMap);
		}
		
		if(yjtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksTrigLongMapList.add(yjtiDragonMap);
		}
		
		if(dsmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksTrigLongMapList.add(dsmtiDragonMap);
		}
		
		if(dfmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksTrigLongMapList.add(dfmtiDragonMap);
		}
		
		if(dwmtiDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jspksTrigLongMapList.add(dwmtiDragonMap);
		}
		return jspksTrigLongMapList;
	}
	
	/** 
	* @Title: getDragonInfo 
	* @Description: 公共方法，获取长龙数据 
	* @param jsbjpksLotterySgList
	* @param type
	* @return Map<String,Object>
	* @author HANS
	* @date 2019年5月13日下午11:22:03
	*/ 
	public Map<String, Object> getDragonInfo(List<JsbjpksLotterySg> jsbjpksLotterySgList, int type){
		Map<String, Object> resultDragonMap = new HashMap<String, Object>();
		try {
			if (!CollectionUtils.isEmpty(jsbjpksLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < jsbjpksLotterySgList.size() ; index++) {
					JsbjpksLotterySg jsbjpksLotterySg = jsbjpksLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateResult(type, jsbjpksLotterySg);
					
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
				JsbjpksLotterySg jsbjpksLotterySg = jsbjpksLotterySgList.get(Constants.DEFAULT_INTEGER);
				// 组织返回数据	
				if(dragonSize >= Constants.DEFAULT_THREE) {
				    resultDragonMap = this.organizationDragonResultMap(type, jsbjpksLotterySg, dragonSet, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#JspksLotterySgServiceImpl_getDragonInfo_error:", e);
		}
		return resultDragonMap;
	}
	
	
	/** 
	* @Title: organizationDragonResultMap 
	* @Description: 返回前端数据 
	* @author HANS
	* @date 2019年5月14日下午7:16:01
	*/ 
	public Map<String, Object> organizationDragonResultMap(int type,JsbjpksLotterySg jsbjpksLotterySg, Set<String> dragonSet, Integer dragonSize){
		// 有龙情况下查询下期数据
		Map<String, Object> longDragonMap = new HashMap<String, Object>();
		try {
			// 获取德州PK10    两面 赔率数据
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.JSPKS.getTagCnName(), CaipiaoPlayTypeEnum.JSPKSGYHBIG.getPlayName(),
					CaipiaoTypeEnum.JSPKS.getTagType(), CaipiaoPlayTypeEnum.JSPKSGYHBIG.getTagType()+"");	
			
			List<String> dragonList = new ArrayList<String>(dragonSet);
			// 玩法赔率
			Map<String, Object> oddsListMap = new HashMap<String, Object>();
			
			if (type == 17) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGYHBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGYHBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGYHBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGBIG);
			} else if (type == 18) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 19) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 20) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 21) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 22) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 23) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDLMBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDLMBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDLMBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 24) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDQMBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDQMBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDQMBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 25) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDMMBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDMMBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDMMBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 26) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDJMBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDJMBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDJMBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 27) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDTMBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDTMBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDTMBIG.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_BIGANDSMALL);
			} else if (type == 28) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGYHDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGYHDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGYHDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_KINGDOUBLE);
			} else if (type == 29) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 30) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 31) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 32) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 33) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 34) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDLMDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDLMDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDLMDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 35) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDQMDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDQMDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDQMDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 36) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDMMDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDMMDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDMMDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 37) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDJMDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDJMDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDJMDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 38) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDTMDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDTMDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDTMDOUBLE.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE);
			} else if (type == 39) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJTIDRAGON.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJTIDRAGON.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSGJTIDRAGON.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
			} else if (type == 40) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJTIDRAGON.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJTIDRAGON.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSYJTIDRAGON.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
			} else if (type == 41) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMTIDRAGON.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMTIDRAGON.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDSMTIDRAGON.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
			} else if (type == 42) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMTIDRAGON.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMTIDRAGON.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDFMTIDRAGON.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
			} else if (type == 43) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMTIDRAGON.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMTIDRAGON.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.JSPKSDWMTIDRAGON.getPlayTag());
				oddsListMap = this.getPKtenOddInfo(twoWallplayAndOddListInfo, Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE);
			}
			// 把获取的赔率加入到返回MAP中
			longDragonMap.putAll(oddsListMap);
			// 加入其它返回值
			String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
			//String returnPlayType = JspksSgUtils.interceptionPlayString(sourcePlayType);
			longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.JSPKS.getTagCnName());
			longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.JSPKS.getTagType());
			longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), sourcePlayType);
			longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#JspksLotterySgServiceImpl_organizationDragonResultMap_error:", e);
		}
		return longDragonMap;
	}
	
	/** 
	* @Title: getOddInfo 
	* @Description: 赔率匹配
	* @author HANS
	* @date 2019年5月13日下午4:57:09
	*/ 
	public Map<String, Object> getPKtenOddInfo(PlayAndOddListInfoVO twoWallplayAndOddListInfo, String playTyep) {
		Map<String, Object> longDragonMap = new HashMap<String, Object>();
		LotteryPlaySetting lotteryPlaySetting = twoWallplayAndOddListInfo.getLotteryPlaySetting();
		
		if(lotteryPlaySetting != null) {
			longDragonMap.put(AppMianParamEnum.COTEGORY.getParamEnName(), lotteryPlaySetting.getCateId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getCateId());
			longDragonMap.put(AppMianParamEnum.PLAYTAGID.getParamEnName(), lotteryPlaySetting.getPlayTagId() == null ? Constants.DEFAULT_NULL : lotteryPlaySetting.getPlayTagId());
		}
        List<LotteryPlayOdds> oddsList = twoWallplayAndOddListInfo.getOddsList();
        
		if (!CollectionUtils.isEmpty(oddsList)) {
			List<Map<String, Object>> playMapList = new ArrayList<Map<String, Object>>();
			for (LotteryPlayOdds lotteryPlayOdds : oddsList) {
				// 两面玩法
				// 冠亚大小
				if (Constants.PKS_PLAYWAY_NAME_KINGBIG.equalsIgnoreCase(playTyep)) {
					if (Constants.CROWN_BIGORSMALL_BIG.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}

					if (Constants.CROWN_BIGORSMALL_SMALL.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}
				}
				// 大小
				if (Constants.PKS_PLAYWAY_NAME_BIGANDSMALL.equalsIgnoreCase(playTyep)) {
					if (Constants.BIGORSMALL_BIG.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}

					if (Constants.BIGORSMALL_SMALL.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}
				}
				// 冠亚单双
				if (Constants.PKS_PLAYWAY_NAME_KINGDOUBLE.equalsIgnoreCase(playTyep)) {
					if (Constants.CROWN_BIGORSMALL_ODD_NUMBER.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}

					if (Constants.CROWN_BIGORSMALL_EVEN_NUMBER.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}
				}
				// 单双
				if (Constants.PKS_PLAYWAY_NAME_SIGLEANDDOUBLE.equalsIgnoreCase(playTyep)) {
					if (Constants.BIGORSMALL_ODD_NUMBER.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}

					if (Constants.BIGORSMALL_EVEN_NUMBER.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}
				}
				// 龙虎
				if (Constants.PKS_PLAYWAY_NAME_DRAGONANDTIGLE.equalsIgnoreCase(playTyep)) {
					if (Constants.PLAYRESULT_DRAGON.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}

					if (Constants.PLAYRESULT_TIGER.equals(lotteryPlayOdds.getName())) {
						Map<String, Object> playMap = new HashMap<String, Object>();
						playMap.put(AppMianParamEnum.PLAYTYPEID.getParamEnName(), lotteryPlayOdds.getId());
						playMap.put(AppMianParamEnum.PLAYTYPENAME.getParamEnName(), lotteryPlayOdds.getName());
						playMap.put(AppMianParamEnum.SETTINGID.getParamEnName(), lotteryPlayOdds.getSettingId());
						playMapList.add(playMap);
					}
				}
			}
			
			if(!CollectionUtils.isEmpty(playMapList)) {
				longDragonMap.put(AppMianParamEnum.ODDS.getParamEnName(), playMapList);
			}
		}
		return longDragonMap;
	}
	
	
	/** 
	* @Title: queryJsbjpksNextSg 
	* @Description: 获取下期数据
	* @author HANS
	* @date 2019年5月14日下午7:24:13
	*/ 
	public JsbjpksLotterySg queryJsbjpksNextSg() {
		JsbjpksLotterySgExample example = new JsbjpksLotterySgExample();
        JsbjpksLotterySgExample.Criteria jsbjpksCriteria = example.createCriteria();
        jsbjpksCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
        jsbjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        example.setOrderByClause("issue ASC");
        JsbjpksLotterySg nextJsbjpksLotterySg = jsbjpksLotterySgMapper.selectOneByExample(example);
		return nextJsbjpksLotterySg;
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果 
	* @return String
	* @author HANS
	* @date 2019年5月13日上午10:33:30
	*/ 
	public String calculateResult(int type, JsbjpksLotterySg jsbjpksLotterySg) {
		String result = Constants.DEFAULT_NULL;

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 17:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//冠亚和大小
			break;
		case 18:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//冠军大小
			break;
		case 19:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//亚军大小
			break;
		case 20:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//第三名大小
			break;
		case 21:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//第四名大小 
			break;
		case 22:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//第五名大小
			break;
		case 23:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//第六名大小
			break;
		case 24:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//第七名大小
			break;
		case 25:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//第八名大小
			break;
		case 26:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//第九名大小
			break;
		case 27:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//第十名大小
			break;
		case 28:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//冠亚和单双 
			break;
		case 29:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//冠军单双 
			break;
		case 30:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//亚军单双 
			break;
		case 31:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//第三名单双
			break;
		case 32:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//第四名单双
			break;
		case 33:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//第五名单双
			break;
		case 34:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//第六名单双
			break;
		case 35:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//第七名单双
			break;
		case 36:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//第八名单双
			break;
		case 37:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//第九名单双
			break;
		case 38:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//第十名单双
			break;
		case 39:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//冠军龙虎
			break;
		case 40:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//亚军龙虎
			break;
		case 41:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//第三名龙虎
			break;
		case 42:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//第四名龙虎
			break;
		case 43:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//第五名龙虎
			break;
		default:
			break;
		}
		return result;
	}
	
	/** 
	* @Title: getJsbjpksAlgorithmData 
	* @Description:缓存近期开奖数据
	* @return JsbjpksLotterySg
	* @author HANS
	* @date 2019年5月13日下午10:31:53
	*/ 
	public List<JsbjpksLotterySg> getJsbjpksAlgorithmData() {
		JsbjpksLotterySgExample example = new JsbjpksLotterySgExample();
        JsbjpksLotterySgExample.Criteria jsbjpksCriteria = example.createCriteria();
        jsbjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        return jsbjpksLotterySgMapper.selectByExample(example);
	}

}
