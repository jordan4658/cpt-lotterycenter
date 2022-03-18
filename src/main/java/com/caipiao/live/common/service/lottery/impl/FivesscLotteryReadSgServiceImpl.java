package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoPlayTypeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.vo.lottery.PlayAndOddListInfoVO;
import com.caipiao.live.common.mybatis.entity.FivesscLotterySg;
import com.caipiao.live.common.mybatis.entity.FivesscLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.FivesscLotterySgMapper;
import com.caipiao.live.common.service.lottery.AusactLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.FivesscLotterySgServiceReadSg;
import com.caipiao.live.common.service.lottery.JssscLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.lottery.AusactSgUtils;
import com.caipiao.live.common.util.lottery.CaipiaoUtils;
import com.caipiao.live.common.util.lottery.NnKlOperationUtils;
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
 * @ClassName: FivesscLotterySgServiceImpl 
 * @Description: 5分时时彩赛果服务类
 * @author: HANS
 * @date: 2019年5月16日 下午6:52:24  
 */
@Service
public class FivesscLotteryReadSgServiceImpl implements FivesscLotterySgServiceReadSg {
	private static final Logger logger = LoggerFactory.getLogger(FivesscLotteryReadSgServiceImpl.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private FivesscLotterySgMapper fivesscLotterySgMapper;
	@Autowired
    private AusactLotterySgServiceReadSg ausactLotterySgService;
	@Autowired
	private JssscLotterySgServiceReadSg jssscLotterySgService;
	
	/* (non Javadoc) 
	 * @Title: getActSgLong
	 * @Description:获取5分时时彩长龙数据
	 * @return 
	 * @see com.caipiao.live.read.issue.service.result.FivesscLotterySgService#getActSgLong()
	 */ 
	@Override
	public List<Map<String, Object>> getFivesscSgLong() {
		List<Map<String, Object>> fivesscLongMapList = new ArrayList<Map<String, Object>>();
        try {
        	String algorithm = RedisKeys.FIVESSC_ALGORITHM_VALUE;
        	List<FivesscLotterySg> fivesscLotterySgList = (List<FivesscLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(fivesscLotterySgList)) {
				fivesscLotterySgList = this.selectFivesscByIssue();
				redisTemplate.opsForValue().set(algorithm, fivesscLotterySgList, 10, TimeUnit.SECONDS);
			}
			// 获取大小长龙
			List<Map<String, Object>> jssscBigLongMapList = this.getFivesscBigAndSmallLong(fivesscLotterySgList);
			fivesscLongMapList.addAll(jssscBigLongMapList);
			// 获取单双长龙
			List<Map<String, Object>> jssscSigleLongMapList = this.getFivesscSigleAndDoubleLong(fivesscLotterySgList);
			fivesscLongMapList.addAll(jssscSigleLongMapList);
			fivesscLongMapList = this.addNextIssueInfo(fivesscLongMapList, fivesscLotterySgList);
        } catch (Exception e) {
			logger.error("app_getSgLongDragons.json#FivesscLotterySgServiceImpl_getFivesscSgLong_error:", e);
		}
		// 返回
		return fivesscLongMapList;
	}
	
	/** 
	* @Title: addNextIssueInfo 
	* @Description: 返回
	* @author HANS
	* @date 2019年5月26日下午2:43:19
	*/ 
	private List<Map<String, Object>> addNextIssueInfo(List<Map<String, Object>> fivesscLongMapList, List<FivesscLotterySg> fivesscLotterySgList){
		List<Map<String, Object>> fivesscResultLongMapList = new ArrayList<Map<String, Object>>();
		if(!CollectionUtils.isEmpty(fivesscLongMapList)) {
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.FIVESSC_NEXT_VALUE;
			FivesscLotterySg nextFivesscLotterySg = (FivesscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);
			// 缓存到下期信息
			Long redisTime = CaipiaoRedisTimeEnum.FIVESSC.getRedisTime();

			if (nextFivesscLotterySg == null) {
				nextFivesscLotterySg = this.getNextFivesscLotterySg();
				redisTemplate.opsForValue().set(nextRedisKey, nextFivesscLotterySg, 10, TimeUnit.SECONDS);
			}
			
			if (nextFivesscLotterySg == null) {
				return new ArrayList<Map<String, Object>>();
			}
			// 缓存中取开奖结果
			String redisKey = RedisKeys.FIVESSC_RESULT_VALUE;
			FivesscLotterySg fivesscLotterySg = (FivesscLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (fivesscLotterySg == null) {
				fivesscLotterySg = this.getFivesscLotterySg();
				redisTemplate.opsForValue().set(redisKey, fivesscLotterySg);
			}
			String nextIssue = nextFivesscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextFivesscLotterySg.getIssue();
			String txffnextIssue = fivesscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : fivesscLotterySg.getIssue();
			
			if(StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
				Long nextIssueNum = Long.parseLong(nextIssue);
				Long txffnextIssueNum = Long.parseLong(txffnextIssue);
				Long differenceNum = nextIssueNum - txffnextIssueNum;
				
				// 如果长龙期数与下期期数相差太大长龙不存在
				if(differenceNum < 1 || differenceNum > 2) {
					return new ArrayList<Map<String, Object>>();
				}
			}
			// 获取5分时时彩的下期数据
			String issueString = nextFivesscLotterySg.getIssue();
			Long nextTimeLong = DateUtils.getTimeMillis(nextFivesscLotterySg.getIdealTime()) / 1000L;
			for (Map<String, Object> longMap : fivesscLongMapList) {
				longMap.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), issueString);
				longMap.put(AppMianParamEnum.NEXTTIME.getParamEnName(),  nextTimeLong);
				fivesscResultLongMapList.add(longMap);
			}
		}
		return fivesscResultLongMapList;
	}
	
	/** 
	* @Title: getFivesscBigAndSmallLong 
	* @Description: 获取5分时时彩大小长龙 
	* @author HANS
	* @date 2019年5月16日下午4:46:35
	*/ 
	private List<Map<String, Object>> getFivesscBigAndSmallLong(List<FivesscLotterySg> fivesscLotterySgList){
		List<Map<String, Object>> jssscBigLongMapList = new ArrayList<Map<String, Object>>();
		// 收集5分时时彩两面总和大小
		Map<String, Object> twoWallBigAndSmallDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCLMZHBIG.getTagType());
		// 收集5分时时彩第一球大小
		Map<String, Object> firstBigAndSmallDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDYQBIG.getTagType());
		// 收集5分时时彩第二球大小
		Map<String, Object> secondBigAndSmallDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDEQBIG.getTagType());
		// 收集5分时时彩第三球大小
		Map<String, Object> thirdBigAndSmallDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDSQBIG.getTagType());
		// 收集5分时时彩第四球大小
		Map<String, Object> fourthBigAndSmallDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDFQBIG.getTagType());
		// 收集5分时时彩第五球大小
		Map<String, Object> fivethBigAndSmallDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDWQBIG.getTagType());
		
		// 计算后的数据放入返回集合
		if(twoWallBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscBigLongMapList.add(twoWallBigAndSmallDragonMap);
		}
		
		if(firstBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscBigLongMapList.add(firstBigAndSmallDragonMap);
		}
		
		if(secondBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscBigLongMapList.add(secondBigAndSmallDragonMap);
		}
		
		if(thirdBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscBigLongMapList.add(thirdBigAndSmallDragonMap);
		}
		
		if(fourthBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscBigLongMapList.add(fourthBigAndSmallDragonMap);
		}
		
		if(fivethBigAndSmallDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscBigLongMapList.add(fivethBigAndSmallDragonMap);
		}
		return jssscBigLongMapList;
	}
	
	/** 
	* @Title: getFivesscSigleAndDoubleLong 
	* @Description: 获取五分时时彩单双长龙 
	* @author HANS
	* @date 2019年5月16日下午4:47:06
	*/ 
	private List<Map<String, Object>> getFivesscSigleAndDoubleLong(List<FivesscLotterySg> fivesscLotterySgList){
		List<Map<String, Object>> jssscSigleLongMapList = new ArrayList<Map<String, Object>>();
		// 收集5分时时彩两面总和单双
		Map<String, Object> twoWallSigleAndDoubleDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCLMZHDOUBLE.getTagType());
		// 收集5分时时彩第一球单双
		Map<String, Object> firstSigleAndDoubleDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDYQDOUBLE.getTagType());
		// 收集5分时时彩第二球单双
		Map<String, Object> secondSigleAndDoubleDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDEQDOUBLE.getTagType());
		// 收集5分时时彩第三球单双
		Map<String, Object> thirdSigleAndDoubleDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDSQDOUBLE.getTagType());
		// 收集5分时时彩第四球单双
		Map<String, Object> fourthSigleAndDoubleDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDFQDOUBLE.getTagType());
		// 收集5分时时彩第五球单双
		Map<String, Object> fivethSigleAndDoubleDragonMap = this.getDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDWQDOUBLE.getTagType());
		// 单双
		if(twoWallSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscSigleLongMapList.add(twoWallSigleAndDoubleDragonMap);
		}
		
		if(firstSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscSigleLongMapList.add(firstSigleAndDoubleDragonMap);
		}
		
		if(secondSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscSigleLongMapList.add(secondSigleAndDoubleDragonMap);
		}
		
		if(thirdSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscSigleLongMapList.add(thirdSigleAndDoubleDragonMap);
		}
		
		if(fourthSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscSigleLongMapList.add(fourthSigleAndDoubleDragonMap);
		}
		
		if(fivethSigleAndDoubleDragonMap.size() > Constants.DEFAULT_INTEGER) {
			jssscSigleLongMapList.add(fivethSigleAndDoubleDragonMap);
		}
		return jssscSigleLongMapList;
	}
	
	/** 
	* @Title: getDragonInfo 
	* @Description: 公共方法，获取长龙数据 
	* @return Map<String,Object>
	* @author HANS
	* @date 2019年5月13日上午12:00:46
	*/ 
	private Map<String, Object> getDragonInfo(List<FivesscLotterySg> fivesscLotterySgList, int type){
		Map<String, Object> resultDragonMap = new HashMap<String, Object>();
		try {
			if (!CollectionUtils.isEmpty(fivesscLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();

				for (int index = Constants.DEFAULT_INTEGER; index < fivesscLotterySgList.size() ; index++) {
					FivesscLotterySg fivesscLotterySg = fivesscLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateResult(type, fivesscLotterySg);
					
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
				// 组织返回数据	
				if(dragonSize.equals(Constants.DEFAULT_THREE) || dragonSize > Constants.DEFAULT_THREE) {
				    resultDragonMap = this.organizationDragonResultMap(type, dragonSet, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#JssscLotterySgServiceImpl_getDragonInfo_error:", e);
		}
		return resultDragonMap;
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果 
	* @return String
	* @author HANS
	* @date 2019年5月13日上午10:33:30
	*/ 
	private String calculateResult(int type, FivesscLotterySg fivesscLotterySg) {
		String result = Constants.DEFAULT_NULL;

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 60:
			result = AusactSgUtils.getJssscBigOrSmall(fivesscLotterySg.getNumber());//两面总和大小
			break;
		case 61:
			result = AusactSgUtils.getJssscSingleNumber(fivesscLotterySg.getWan());//第一球大小
			break;
		case 62:
			result = AusactSgUtils.getJssscSingleNumber(fivesscLotterySg.getQian());//第二球大小
			break;
		case 63:
			result = AusactSgUtils.getJssscSingleNumber(fivesscLotterySg.getBai());//第三球大小
			break;
		case 64:
			result = AusactSgUtils.getJssscSingleNumber(fivesscLotterySg.getShi());//第四球大小
			break;
		case 65:
			result = AusactSgUtils.getJssscSingleNumber(fivesscLotterySg.getGe());//第五球大小
			break;
		case 66:
			result = AusactSgUtils.getSingleAndDouble(fivesscLotterySg.getNumber());//两面总和单双
			break;
		case 67:
			result = AusactSgUtils.getOneSingleAndDouble(fivesscLotterySg.getWan());//第一球单双
			break;
		case 68:
			result = AusactSgUtils.getOneSingleAndDouble(fivesscLotterySg.getQian());//第二球单双
			break;
		case 69:
			result = AusactSgUtils.getOneSingleAndDouble(fivesscLotterySg.getBai());//第三球单双
			break;
		case 70:
			result = AusactSgUtils.getOneSingleAndDouble(fivesscLotterySg.getShi());//第四球单双
			break;
		case 71:
			result = AusactSgUtils.getOneSingleAndDouble(fivesscLotterySg.getGe());//第五球单双
			break;
		default:
			break;
		}
		return result;
	}
	
	/** 
	* @Title: organizationDragonResultMap 
	* @author HANS
	* @date 2019年5月13日下午1:53:19
	*/ 
	private Map<String, Object> organizationDragonResultMap(int type, Set<String> dragonSet, Integer dragonSize){
		// 有龙情况下查询下期数据
		Map<String, Object> longDragonMap = new HashMap<String, Object>();
		try {
			// 获取德州时时彩 两面 赔率数据
			PlayAndOddListInfoVO twoWallplayAndOddListInfo = ausactLotterySgService.getAusactOddsList(CaipiaoTypeEnum.FIVESSC.getTagCnName(), CaipiaoPlayTypeEnum.FIVESSCLMZHBIG.getPlayName(),
					CaipiaoTypeEnum.FIVESSC.getTagType(), CaipiaoPlayTypeEnum.FIVESSCLMZHBIG.getTagType()+"");			
			List<String> dragonList = new ArrayList<String>(dragonSet);
			// 玩法赔率
			Map<String, Object> oddsListMap = new HashMap<String, Object>();
						
			if(type == 60) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCLMZHBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCLMZHBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCLMZHBIG.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALBIG);
			} else if(type == 61) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDYQBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDYQBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDYQBIG.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
			} else if(type == 62) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDEQBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDEQBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDEQBIG.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
			} else if(type == 63) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDSQBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDSQBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDSQBIG.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
			} else if(type == 64) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDFQBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDFQBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDFQBIG.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
			} else if(type == 65) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDWQBIG.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDWQBIG.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDWQBIG.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEBIG);
			} else if(type == 66) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCLMZHDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCLMZHDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCLMZHDOUBLE.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_TOTALDOUBLE);
			} else if(type == 67) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDYQDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDYQDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDYQDOUBLE.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
			} else if(type == 68) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDEQDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDEQDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDEQDOUBLE.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
			} else if(type == 69) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDSQDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDSQDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDSQDOUBLE.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
			} else if(type == 70) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDFQDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDFQDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDFQDOUBLE.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
			} else if(type == 71) {
				longDragonMap.put(AppMianParamEnum.ID.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDWQDOUBLE.getTagType());
				longDragonMap.put(AppMianParamEnum.PLAYTYPE.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDWQDOUBLE.getTagCnName());
				longDragonMap.put(AppMianParamEnum.PLAYTAG.getParamEnName(), CaipiaoPlayTypeEnum.FIVESSCDWQDOUBLE.getPlayTag());
				oddsListMap = jssscLotterySgService.getOddInfo(twoWallplayAndOddListInfo, Constants.SSC_PLAYWAY_NAME_FIVEDOUBLE);
			}
			// 把获取的赔率加入到返回MAP中
			longDragonMap.putAll(oddsListMap);
			// 加入其它返回值
			String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
			//String returnPlayType = JspksSgUtils.interceptionPlayString(sourcePlayType);
			longDragonMap.put(AppMianParamEnum.TYPE.getParamEnName(), CaipiaoTypeEnum.FIVESSC.getTagCnName());
			longDragonMap.put(AppMianParamEnum.TYPEID.getParamEnName(), CaipiaoTypeEnum.FIVESSC.getTagType());
			longDragonMap.put(AppMianParamEnum.DRAGONType.getParamEnName(), sourcePlayType);
			longDragonMap.put(AppMianParamEnum.DRAGONSUM.getParamEnName(), dragonSize);
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#FivesscLotterySgServiceImpl_organizationDragonResultMap_error:", e);
		}
		return longDragonMap;
	}
	
	/** 
	* @Title: selectFivesscByIssue 
	* @Description: 缓存近期数据
	* @author HANS
	* @date 2019年5月16日下午4:38:42
	*/ 
	private List<FivesscLotterySg> selectFivesscByIssue() {
		FivesscLotterySgExample example = new FivesscLotterySgExample();
		FivesscLotterySgExample.Criteria fivesscCriteria = example.createCriteria();
		fivesscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        List<FivesscLotterySg> fivesscLotterySgList = this.fivesscLotterySgMapper.selectByExample(example);
		return fivesscLotterySgList;
	}
	
	/**
	 * @Title: openCount
	 * @Description: 计算开奖次数和未开奖次数
	 */
	private void openCount(FivesscLotterySg fivesscLotterySg, Map<String, Object> result) {
		// 计算开奖次数
		String issue = fivesscLotterySg.getIssue();
		String openNumString = issue.substring(8, issue.length());
		Integer openNumInteger = Integer.valueOf(openNumString);
		result.put("openCount", openNumInteger);
		// 计算剩余开奖次数
		Integer sumCount = CaipiaoSumCountEnum.FIVESSC.getSumCount();
		result.put("noOpenCount", sumCount - openNumInteger);
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
					// 计算开奖次数
					this.openCount(fivesscLotterySg, result);
					String niuWinner = NnKlOperationUtils.getNiuWinner(fivesscLotterySg.getNumber());
					result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);
				}

				if (nextFivesscLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
							DateUtils.getTimeMillis(nextFivesscLotterySg.getIdealTime()) / 1000L);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextFivesscLotterySg.getIssue());
				}

			} else {
				result = DefaultResultUtil.getNullResult();
				
				if (fivesscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumberForFivessc(fivesscLotterySg, result);
					// 计算开奖次数
					this.openCount(fivesscLotterySg, result);
					String niuWinner = NnKlOperationUtils.getNiuWinner(fivesscLotterySg.getNumber());
					result.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);
				}
			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.FIVESSC.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
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
		result.put(AppMianParamEnum.SOURCENUMBER.getParamEnName(), fivesscLotterySg.getNumber());
		// 组织开奖号码
		String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
		// 计算开奖号码合值
		Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
	}
	
	/**
	 * @Title: getIssueSumAndAllNumber
	 * @Description: 查询下期数据
	 */
	private FivesscLotterySg getNextFivesscLotterySg() {
		FivesscLotterySgExample nextExample = new FivesscLotterySgExample();
		FivesscLotterySgExample.Criteria nextFivesscCriteria = nextExample.createCriteria();
		nextFivesscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		nextFivesscCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		nextExample.setOrderByClause("ideal_time ASC");
		FivesscLotterySg nextTjsscLotterySg = this.fivesscLotterySgMapper.selectOneByExample(nextExample);
		return nextTjsscLotterySg;
	}

}
