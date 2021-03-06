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
 * @Description: ??????PK10?????????
 * @author: HANS
 * @date: 2019???5???13??? ??????9:26:11  
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
	 * @Description: ????????????PK10??????
	 * @return 
	 * @see com.caipiao.live.read.issue.service.result.JspksLotterySgService#getJspksNewestSgInfo()
	 */ 
	@Override
	public ResultInfo<Map<String, Object>> getJspksNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullPkResult();
		try {
			// ????????????????????????
			String redisKey = RedisKeys.JSPKS_RESULT_VALUE;
			JsbjpksLotterySg jsbjpksLotterySg = (JsbjpksLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (jsbjpksLotterySg == null) {
				jsbjpksLotterySg = this.getJsbjpksLotterySg();
				redisTemplate.opsForValue().set(redisKey, jsbjpksLotterySg);
			}

			// ????????????????????????
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
				// ?????????????????????
				Integer sumCount = CaipiaoSumCountEnum.JSPKS.getSumCount();
				// ?????????????????????????????????
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
						// ????????????????????????
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
					// ???????????????????????????
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
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.JSPKS.getTagType() + " ????????? ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return ResultInfo.ok(result);
	}
	
	
	/**
	 * @Title: getJsbjpksLotterySg
	 * @Description: ????????????????????????
	 * @return JsbjpksLotterySg
	 * @author HANS
	 * @date 2019???5???3?????????1:26:17
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
	 * @Description: ?????????????????????
	 * @return JsbjpksLotterySg
	 * @author HANS
	 * @date 2019???5???3?????????1:25:39
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
	 * @Description: ????????????PK10?????? 
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
			// ????????????
			List<Map<String, Object>> bigAndSmallLongList = this.getBigAndSmallLong(jsbjpksLotterySgList);
			jspksLongMapList.addAll(bigAndSmallLongList);
			// ????????????
			List<Map<String, Object>> sigleAndDoubleLongList = this.getDoubleAndSingleLong(jsbjpksLotterySgList);
			jspksLongMapList.addAll(sigleAndDoubleLongList);
			// ????????????
			List<Map<String, Object>> dragonAndTigleLongList = this.getTrigleAndDragonLong(jsbjpksLotterySgList);
			jspksLongMapList.addAll(dragonAndTigleLongList);
			// ??????????????????
			jspksLongMapList = this.addNextIssueInfo(jspksLongMapList, jsbjpksLotterySgList);
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#JspksLotterySgServiceImpl_getJspksSgLong_error:", e);
		}
		return jspksLongMapList;
	}
	
	/** 
	* @Title: addNextIssueInfo 
	* @Description: ??????
	* @author HANS
	* @date 2019???5???26?????????2:43:19
	*/ 
	private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> jspksLongMapList, List<JsbjpksLotterySg> jsbjpksLotterySgList){
		List<Map<String, Object>> jspksResultLongMapList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(jspksLongMapList)) {
			// ????????????
			String nextRedisKey = RedisKeys.JSPKS_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.JSPKS.getRedisTime();
			JsbjpksLotterySg nextJsbjpksLotterySg = (JsbjpksLotterySg)redisTemplate.opsForValue().get(nextRedisKey);
			
			if(nextJsbjpksLotterySg == null) {
				nextJsbjpksLotterySg = this.queryJsbjpksNextSg();
				// ?????????????????????
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
				
				// ????????????????????????????????????????????????????????????
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
	* @Description: ???????????????????????????
	* @return List<Map<String,Object>>
	* @author HANS
	* @date 2019???5???13?????????10:49:38
	*/ 
	public List<Map<String, Object>> getBigAndSmallLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		List<Map<String, Object>> jspksBigLongMapList = new ArrayList<Map<String, Object>>();
		//???????????????
		Map<String, Object> gyhDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGYHBIG.getTagType());
		//????????????
		Map<String, Object> gjbDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJBIG.getTagType());
		//????????????
		Map<String, Object> yjbDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJBIG.getTagType());
		//???????????????
		Map<String, Object> dsmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMBIG.getTagType());
		//???????????????
		Map<String, Object> dfmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMBIG.getTagType());
		//???????????????
		Map<String, Object> dwmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDWMBIG.getTagType());
		//???????????????
		Map<String, Object> dlmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDLMBIG.getTagType());
		//???????????????
		Map<String, Object> dqmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDQMBIG.getTagType());
		//???????????????
		Map<String, Object> dmmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDMMBIG.getTagType());
		//???????????????
		Map<String, Object> djmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDJMBIG.getTagType());
		//???????????????
		Map<String, Object> dtmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDTMBIG.getTagType());
		
		// ????????????????????????????????????
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
	* @Description:  ???????????????????????????
	* @return List<Map<String,Object>>
	* @author HANS
	* @date 2019???5???13?????????10:50:31
	*/ 
	public List<Map<String, Object>> getDoubleAndSingleLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		List<Map<String, Object>> jspksDoubleLongMapList = new ArrayList<Map<String, Object>>();
		//???????????????
		Map<String, Object> gyhDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGYHDOUBLE.getTagType());
		//????????????
		Map<String, Object> gjDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJDOUBLE.getTagType());
		//????????????
		Map<String, Object> syjDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJDOUBLE.getTagType());
		//???????????????
		Map<String, Object> dsmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMDOUBLE.getTagType());
		//???????????????
		Map<String, Object> dfmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMDOUBLE.getTagType());
		//???????????????
		Map<String, Object> dwmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDWMDOUBLE.getTagType());
		//???????????????
		Map<String, Object> dlmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDLMDOUBLE.getTagType());
		//???????????????
		Map<String, Object> dqmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDQMDOUBLE.getTagType());
		//???????????????
		Map<String, Object> dmmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDMMDOUBLE.getTagType());
		//???????????????
		Map<String, Object> djmDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDJMDOUBLE.getTagType());
		//???????????????
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
	* @Description: ??????????????????????????? 
	* @return List<Map<String,Object>>
	* @author HANS
	* @date 2019???5???13?????????10:53:00
	*/ 
	public List<Map<String, Object>> getTrigleAndDragonLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		List<Map<String, Object>> jspksTrigLongMapList = new ArrayList<Map<String, Object>>();
		//????????????
		Map<String, Object> gjtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJTIDRAGON.getTagType());
		//????????????
		Map<String, Object> yjtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJTIDRAGON.getTagType());
		//???????????????
		Map<String, Object> dsmtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMTIDRAGON.getTagType());
		//???????????????
		Map<String, Object> dfmtiDragonMap = this.getDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMTIDRAGON.getTagType());
		//???????????????
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
	* @Description: ????????????????????????????????? 
	* @param jsbjpksLotterySgList
	* @param type
	* @return Map<String,Object>
	* @author HANS
	* @date 2019???5???13?????????11:22:03
	*/ 
	public Map<String, Object> getDragonInfo(List<JsbjpksLotterySg> jsbjpksLotterySgList, int type){
		Map<String, Object> resultDragonMap = new HashMap<String, Object>();
		try {
			if (!CollectionUtils.isEmpty(jsbjpksLotterySgList)) {
				// ????????????
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < jsbjpksLotterySgList.size() ; index++) {
					JsbjpksLotterySg jsbjpksLotterySg = jsbjpksLotterySgList.get(index);
					// ????????????????????????
					String bigOrSmallName = this.calculateResult(type, jsbjpksLotterySg);
					
					if(StringUtils.isEmpty(bigOrSmallName)) {
						break;
					}
					// ????????????????????????SET??????
					if(index == Constants.DEFAULT_INTEGER) {
						dragonSet.add(bigOrSmallName);
					}
					// ???????????????????????????????????????????????????????????????
					if(index == Constants.DEFAULT_ONE) {
						if(!dragonSet.contains(bigOrSmallName)) {
							// ???/?????????????????????????????????
							break;
						}
						continue;
					}
					// ???????????????3???????????????
					if(index == Constants.DEFAULT_TWO) {
						// ???????????????
						if(!dragonSet.contains(bigOrSmallName)) {
							// ???/?????????????????????????????????
							break;
						}
						dragonSize = Constants.DEFAULT_THREE;
						continue;
					}
					// ????????????3????????????????????????????????????????????????
					if(index > Constants.DEFAULT_TWO) {
						// ???/?????????
						if(!dragonSet.contains(bigOrSmallName)) {
							// ???/?????????????????????????????????
							break;
						}
						dragonSize++;
					}
				}
				// ????????????????????????
				JsbjpksLotterySg jsbjpksLotterySg = jsbjpksLotterySgList.get(Constants.DEFAULT_INTEGER);
				// ??????????????????	
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
	* @Description: ?????????????????? 
	* @author HANS
	* @date 2019???5???14?????????7:16:01
	*/ 
	public Map<String, Object> organizationDragonResultMap(int type,JsbjpksLotterySg jsbjpksLotterySg, Set<String> dragonSet, Integer dragonSize){
		// ?????????????????????????????????
		Map<String, Object> longDragonMap = new HashMap<String, Object>();
		try {
			// ????????????PK10    ?????? ????????????
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.JSPKS.getTagCnName(), CaipiaoPlayTypeEnum.JSPKSGYHBIG.getPlayName(),
					CaipiaoTypeEnum.JSPKS.getTagType(), CaipiaoPlayTypeEnum.JSPKSGYHBIG.getTagType()+"");	
			
			List<String> dragonList = new ArrayList<String>(dragonSet);
			// ????????????
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
			// ?????????????????????????????????MAP???
			longDragonMap.putAll(oddsListMap);
			// ?????????????????????
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
	* @Description: ????????????
	* @author HANS
	* @date 2019???5???13?????????4:57:09
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
				// ????????????
				// ????????????
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
				// ??????
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
				// ????????????
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
				// ??????
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
				// ??????
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
	* @Description: ??????????????????
	* @author HANS
	* @date 2019???5???14?????????7:24:13
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
	* @Description: ???????????????????????? 
	* @return String
	* @author HANS
	* @date 2019???5???13?????????10:33:30
	*/ 
	public String calculateResult(int type, JsbjpksLotterySg jsbjpksLotterySg) {
		String result = Constants.DEFAULT_NULL;

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 17:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 18:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//????????????
			break;
		case 19:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//????????????
			break;
		case 20:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 21:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//??????????????? 
			break;
		case 22:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 23:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 24:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 25:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 26:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 27:
			result = JspksSgUtils.getJspksBigOrSmall(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 28:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//??????????????? 
			break;
		case 29:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????? 
			break;
		case 30:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????? 
			break;
		case 31:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 32:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 33:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 34:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 35:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 36:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 37:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 38:
			result = JspksSgUtils.getJspksSigleAndDouble(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 39:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//????????????
			break;
		case 40:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//????????????
			break;
		case 41:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 42:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		case 43:
			result = JspksSgUtils.getJspksDragonAndtiger(jsbjpksLotterySg.getNumber(),type);//???????????????
			break;
		default:
			break;
		}
		return result;
	}
	
	/** 
	* @Title: getJsbjpksAlgorithmData 
	* @Description:????????????????????????
	* @return JsbjpksLotterySg
	* @author HANS
	* @date 2019???5???13?????????10:31:53
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
