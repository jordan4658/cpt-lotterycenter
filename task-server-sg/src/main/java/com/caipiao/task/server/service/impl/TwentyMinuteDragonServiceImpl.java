package com.caipiao.task.server.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.CaipiaoPlayTypeEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.enums.TaskTypeEnum;
import com.caipiao.core.library.utils.RedisKeys;
import com.caipiao.task.server.service.TaskSgService;
import com.caipiao.task.server.service.TwentyMinuteDragonService;
import com.caipiao.task.server.util.AusactSgUtils;
import com.caipiao.task.server.util.JspksSgUtils;
import com.mapper.BjpksLotterySgMapper;
import com.mapper.CqsscLotterySgMapper;
import com.mapper.TjsscLotterySgMapper;
import com.mapper.XjsscLotterySgMapper;
import com.mapper.domain.BjpksLotterySg;
import com.mapper.domain.BjpksLotterySgExample;
import com.mapper.domain.CqsscLotterySg;
import com.mapper.domain.CqsscLotterySgExample;
import com.mapper.domain.TjsscLotterySg;
import com.mapper.domain.TjsscLotterySgExample;
import com.mapper.domain.XjsscLotterySg;
import com.mapper.domain.XjsscLotterySgExample;

@Service
public class TwentyMinuteDragonServiceImpl implements TwentyMinuteDragonService{
	private static final Logger logger = LoggerFactory.getLogger(TwentyMinuteDragonServiceImpl.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private TaskSgService taskSgService;
    @Autowired
    private BjpksLotterySgMapper bjpksLotterySgMapper;
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;	
    @Autowired
    private XjsscLotterySgMapper xjsscLotterySgMapper;
    @Autowired
    private CqsscLotterySgMapper cqsscLotterySgMapper;

	@Override
	public void totalDragonOneMinute() {
		// 天津时时彩  1103    20F
		this.getTjsscSgDragonLong();
		// 新疆时时彩  1102    20F
		this.getxjsscSgLong();
		// 重庆时时彩  1101    20F
		this.getCqsscSgLong();
		// 北京PK10    1301    20F
		this.getBjPksSgLong();
	}
	
	/**
	 * 天津时时彩 长龙
	 */
	public void getTjsscSgDragonLong() {
		try {
        	String algorithm = RedisKeys.TJSSC_ALGORITHM_VALUE;
        	List<TjsscLotterySg> tjsscLotterySgList = (List<TjsscLotterySg>)redisTemplate.opsForValue().get(algorithm);
        	
        	if(CollectionUtils.isEmpty(tjsscLotterySgList)) {
        		tjsscLotterySgList = this.getTjsscAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, tjsscLotterySgList);
			}
        	// 获取大小长龙
			this.getTjsscBigAndSmallLong(tjsscLotterySgList);
			// 获取单双长龙
			this.getTjsscSigleAndDoubleLong(tjsscLotterySgList);
		} catch (Exception e) {
			logger.error("天津时时彩长龙数据统计异常" ,e);
		}
	}
	
	/**
	 * 新疆时时彩  长龙
	 */
	public void getxjsscSgLong() {
		try {
			String algorithm = RedisKeys.XJSSC_ALGORITHM_VALUE;
			List<XjsscLotterySg> xjsscLotterySgList = (List<XjsscLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(xjsscLotterySgList)) {
				xjsscLotterySgList = this.getxjsscAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, xjsscLotterySgList);
			}
			// 获取大小长龙
			this.getXjsscBigAndSmallLong(xjsscLotterySgList);
			// 获取单双长龙
			this.getXjsscSigleAndDoubleLong(xjsscLotterySgList);
		} catch (Exception e) {
			logger.error("新疆时时彩长龙数据统计异常" ,e);
		}
	}
	
	/**
	 * 重庆时时彩  长龙
	 */
	public void getCqsscSgLong() {
		try {
			String algorithm = RedisKeys.CQSSC_ALGORITHM_VALUE;
			List<CqsscLotterySg> cqsscLotterySgList = (List<CqsscLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(cqsscLotterySgList)) {
				cqsscLotterySgList = this.getCqsscAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, cqsscLotterySgList);
			}	
			// 获取大小长龙
			this.getCqsscBigAndSmallLong(cqsscLotterySgList);
			// 获取单双长龙
			this.getCqsscSigleAndDoubleLong(cqsscLotterySgList);
		} catch (Exception e) {
			logger.error("重庆时时彩长龙数据统计异常" ,e);
		}
	}
	
	/**
	 * 北京PK10  长龙
	 */
	public void getBjPksSgLong() {
		try {
			String algorithm = RedisKeys.BJPKS_ALGORITHM_VALUE;
			List<BjpksLotterySg> bjpksLotterySgList = (List<BjpksLotterySg>) redisTemplate.opsForValue().get(algorithm);

			if (CollectionUtils.isEmpty(bjpksLotterySgList)) {
				bjpksLotterySgList = this.selectOpenIssueList();
				redisTemplate.opsForValue().set(algorithm, bjpksLotterySgList);
			}
			// 大小长龙
			this.getBigAndSmallLong(bjpksLotterySgList);
			// 单双长龙
			this.getDoubleAndSingleLong(bjpksLotterySgList);
			// 龙虎长龙
			this.getTrigleAndDragonLong(bjpksLotterySgList);
		} catch (Exception e) {
			logger.error("北京PK10长龙数据统计异常" ,e);
		}
	}
	
	/**
	 * @param cqsscLotterySgList
	 */
	private void getCqsscBigAndSmallLong(List<CqsscLotterySg> cqsscLotterySgList){
		// 收集重庆时时彩两面总和大小
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCLMZHBIG.getTagType(), CaipiaoPlayTypeEnum.CQSSCLMZHBIG.getTagCnName());
		// 收集重庆时时彩第一球大小
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDYQBIG.getTagType(), CaipiaoPlayTypeEnum.CQSSCDYQBIG.getTagCnName());
		// 收集重庆时时彩第二球大小
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDEQBIG.getTagType(), CaipiaoPlayTypeEnum.CQSSCDEQBIG.getTagCnName());
		// 收集重庆时时彩第三球大小
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDSQBIG.getTagType(), CaipiaoPlayTypeEnum.CQSSCDSQBIG.getTagCnName());
		// 收集重庆时时彩第四球大小
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDFQBIG.getTagType(), CaipiaoPlayTypeEnum.CQSSCDFQBIG.getTagCnName());
		// 收集重庆时时彩第五球大小
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDWQBIG.getTagType(), CaipiaoPlayTypeEnum.CQSSCDWQBIG.getTagCnName());
	}
	
	/**
	 * @param cqsscLotterySgList
	 */
	private void getCqsscSigleAndDoubleLong(List<CqsscLotterySg> cqsscLotterySgList){
		// 收集重庆时时彩两面总和单双
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCLMZHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.CQSSCLMZHDOUBLE.getTagCnName());
		// 收集重庆时时彩第一球单双
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDYQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.CQSSCDYQDOUBLE.getTagCnName());
		// 收集重庆时时彩第二球单双
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDEQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.CQSSCDEQDOUBLE.getTagCnName());
		// 收集重庆时时彩第三球单双
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDSQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.CQSSCDSQDOUBLE.getTagCnName());
		// 收集重庆时时彩第四球单双
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDFQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.CQSSCDFQDOUBLE.getTagCnName());
		// 收集重庆时时彩第五球单双
		this.getCqsscDragonInfo(cqsscLotterySgList, CaipiaoPlayTypeEnum.CQSSCDWQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.CQSSCDWQDOUBLE.getTagCnName());
	}
	
	/**
	 * @param cqsscLotterySgList
	 * @param type
	 * @param playType
	 */
	private void getCqsscDragonInfo(List<CqsscLotterySg> cqsscLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(cqsscLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();

				for (int index = Constants.DEFAULT_INTEGER; index < cqsscLotterySgList.size() ; index++) {
					CqsscLotterySg cqsscLotterySg = cqsscLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateCqsscResult(type, cqsscLotterySg);
					
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
				if(dragonSize >= Constants.DEFAULT_SIX) {
					Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
					String  caiPiaoType = CaipiaoTypeEnum.CQSSC.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}				
			}
		} catch (Exception e) {
			logger.error("重庆时时彩长龙统计_error:", e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果   
	* @author HANS
	* @date 2019年5月17日下午6:57:10
	*/ 
	private String calculateCqsscResult(int type, CqsscLotterySg cqsscLotterySg) {
		String result = Constants.DEFAULT_NULL;

		String number = Constants.DEFAULT_NULL;
		number = cqsscLotterySg.getCpkNumber() == null ? Constants.DEFAULT_NULL : cqsscLotterySg.getCpkNumber();

		if (StringUtils.isEmpty(number)) {
			number = cqsscLotterySg.getKcwNumber() == null ? Constants.DEFAULT_NULL : cqsscLotterySg.getKcwNumber();
		}

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 108:
			result = AusactSgUtils.getJssscBigOrSmall(number);//两面总和大小
			break;
		case 109:
			result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getWan());//第一球大小
			break;
		case 110:
			result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getQian());//第二球大小
			break;
		case 111:
			result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getBai());//第三球大小
			break;
		case 112:
			result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getShi());//第四球大小
			break;
		case 113:
			result = AusactSgUtils.getJssscSingleNumber(cqsscLotterySg.getGe());//第五球大小
			break;
		case 114:
			result = AusactSgUtils.getSingleAndDouble(number);//两面总和单双
			break;
		case 115:
			result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getWan());//第一球单双
			break;
		case 116:
			result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getQian());//第二球单双
			break;
		case 117:
			result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getBai());//第三球单双
			break;
		case 118:
			result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getShi());//第四球单双
			break;
		case 119:
			result = AusactSgUtils.getOneSingleAndDouble(cqsscLotterySg.getGe());//第五球单双
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * @param xjsscLotterySgList
	 */
	private void getXjsscBigAndSmallLong(List<XjsscLotterySg> xjsscLotterySgList){
		// 收集新疆时时彩两面总和大小
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCLMZHBIG.getTagType(), CaipiaoPlayTypeEnum.XJSSCLMZHBIG.getTagCnName());
		// 收集新疆时时彩第一球大小
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDYQBIG.getTagType(), CaipiaoPlayTypeEnum.XJSSCDYQBIG.getTagCnName());
		// 收集新疆时时彩第二球大小
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDEQBIG.getTagType(), CaipiaoPlayTypeEnum.XJSSCDEQBIG.getTagCnName());
		// 收集新疆时时彩第三球大小
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDSQBIG.getTagType(), CaipiaoPlayTypeEnum.XJSSCDSQBIG.getTagCnName());
		// 收集新疆时时彩第四球大小
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDFQBIG.getTagType(), CaipiaoPlayTypeEnum.XJSSCDFQBIG.getTagCnName());
		// 收集新疆时时彩第五球大小
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDWQBIG.getTagType(), CaipiaoPlayTypeEnum.XJSSCDWQBIG.getTagCnName());
	}
	
	/**
	 * @param xjsscLotterySgList
	 */
	private void getXjsscSigleAndDoubleLong(List<XjsscLotterySg> xjsscLotterySgList){
		// 收集新疆时时彩两面总和单双
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCLMZHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XJSSCLMZHDOUBLE.getTagCnName());
		// 收集新疆时时彩第一球单双
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDYQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XJSSCDYQDOUBLE.getTagCnName());
		// 收集新疆时时彩第二球单双
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDEQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XJSSCDEQDOUBLE.getTagCnName());
		// 收集新疆时时彩第三球单双
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDSQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XJSSCDSQDOUBLE.getTagCnName());
		// 收集新疆时时彩第四球单双
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDFQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XJSSCDFQDOUBLE.getTagCnName());
		// 收集新疆时时彩第五球单双
		this.getXjsscDragonInfo(xjsscLotterySgList, CaipiaoPlayTypeEnum.XJSSCDWQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XJSSCDWQDOUBLE.getTagCnName());
	}
	
	/**
	 * @param xjsscLotterySgList
	 * @param type
	 * @param playType
	 */
	private void getXjsscDragonInfo(List<XjsscLotterySg> xjsscLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(xjsscLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();

				for (int index = Constants.DEFAULT_INTEGER; index < xjsscLotterySgList.size() ; index++) {
					XjsscLotterySg xjsscLotterySg = xjsscLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateXjsscResult(type, xjsscLotterySg);
					
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
				if(dragonSize >= Constants.DEFAULT_SIX) {
					Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
					String  caiPiaoType = CaipiaoTypeEnum.XJSSC.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}				
			}
		} catch (Exception e) {
			logger.error("新疆时时彩统计长龙_error:", e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果  
	* @author HANS
	* @date 2019年5月17日下午3:50:15
	*/ 
	private String calculateXjsscResult(int type, XjsscLotterySg xjsscLotterySg) {
        String result = Constants.DEFAULT_NULL;
		
		String number = Constants.DEFAULT_NULL;
		number = xjsscLotterySg.getCpkNumber() == null ? Constants.DEFAULT_NULL : xjsscLotterySg.getCpkNumber();
		
		if(StringUtils.isEmpty(number)) {
			number = xjsscLotterySg.getKcwNumber() == null ? Constants.DEFAULT_NULL : xjsscLotterySg.getKcwNumber();
		} 

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 96:
			result = AusactSgUtils.getJssscBigOrSmall(number);//两面总和大小
			break;
		case 97:
			result = AusactSgUtils.getJssscSingleNumber(xjsscLotterySg.getWan());//第一球大小
			break;
		case 98:
			result = AusactSgUtils.getJssscSingleNumber(xjsscLotterySg.getQian());//第二球大小
			break;
		case 99:
			result = AusactSgUtils.getJssscSingleNumber(xjsscLotterySg.getBai());//第三球大小
			break;
		case 100:
			result = AusactSgUtils.getJssscSingleNumber(xjsscLotterySg.getShi());//第四球大小
			break;
		case 101:
			result = AusactSgUtils.getJssscSingleNumber(xjsscLotterySg.getGe());//第五球大小
			break;
		case 102:
			result = AusactSgUtils.getSingleAndDouble(number);//两面总和单双
			break;
		case 103:
			result = AusactSgUtils.getOneSingleAndDouble(xjsscLotterySg.getWan());//第一球单双
			break;
		case 104:
			result = AusactSgUtils.getOneSingleAndDouble(xjsscLotterySg.getQian());//第二球单双
			break;
		case 105:
			result = AusactSgUtils.getOneSingleAndDouble(xjsscLotterySg.getBai());//第三球单双
			break;
		case 106:
			result = AusactSgUtils.getOneSingleAndDouble(xjsscLotterySg.getShi());//第四球单双
			break;
		case 107:
			result = AusactSgUtils.getOneSingleAndDouble(xjsscLotterySg.getGe());//第五球单双
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * @param tjsscLotterySgList
	 */
	private void getTjsscBigAndSmallLong(List<TjsscLotterySg> tjsscLotterySgList){
		// 收集天津时时彩两面总和大小
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCLMZHBIG.getTagType(), CaipiaoPlayTypeEnum.TJSSCLMZHBIG.getTagCnName());
		// 收集天津时时彩第一球大小
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDYQBIG.getTagType(), CaipiaoPlayTypeEnum.TJSSCDYQBIG.getTagCnName());
		// 收集天津时时彩第二球大小
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDEQBIG.getTagType(), CaipiaoPlayTypeEnum.TJSSCDEQBIG.getTagCnName());
		// 收集天津时时彩第三球大小
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDSQBIG.getTagType(), CaipiaoPlayTypeEnum.TJSSCDSQBIG.getTagCnName());
		// 收集天津时时彩第四球大小
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDFQBIG.getTagType(), CaipiaoPlayTypeEnum.TJSSCDFQBIG.getTagCnName());
		// 收集天津时时彩第五球大小
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDWQBIG.getTagType(), CaipiaoPlayTypeEnum.TJSSCDWQBIG.getTagCnName());
	}
	
	/**
	 * @param tjsscLotterySgList
	 */
	private void getTjsscSigleAndDoubleLong(List<TjsscLotterySg> tjsscLotterySgList){
		// 收集天津时时彩两面总和单双
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCLMZHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TJSSCLMZHDOUBLE.getTagCnName());
		// 收集天津时时彩第一球单双
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDYQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TJSSCDYQDOUBLE.getTagCnName());
		// 收集天津时时彩第二球单双
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDEQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TJSSCDEQDOUBLE.getTagCnName());
		// 收集天津时时彩第三球单双
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDSQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TJSSCDSQDOUBLE.getTagCnName());
		// 收集天津时时彩第四球单双
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDFQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TJSSCDFQDOUBLE.getTagCnName());
		// 收集天津时时彩第五球单双
		this.getTjsscDragonInfo(tjsscLotterySgList, CaipiaoPlayTypeEnum.TJSSCDWQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TJSSCDWQDOUBLE.getTagCnName());
	}
	
	/**
	 * @param tjsscLotterySgList
	 * @param type
	 * @param playType
	 */
	private void getTjsscDragonInfo(List<TjsscLotterySg> tjsscLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(tjsscLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();

				for (int index = Constants.DEFAULT_INTEGER; index < tjsscLotterySgList.size() ; index++) {
					TjsscLotterySg tjsscLotterySg = tjsscLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateTjsscResult(type, tjsscLotterySg);
					
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
				if(dragonSize >= Constants.DEFAULT_SIX) {
					Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
					String  caiPiaoType = CaipiaoTypeEnum.TJSSC.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}				
			}
		} catch (Exception e) {
			logger.error("天津时时彩统计长龙_error:", e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果 
	* @author HANS
	* @date 2019年5月17日上午11:50:43
	*/ 
	private String calculateTjsscResult(int type, TjsscLotterySg tjsscLotterySg) {
		String result = Constants.DEFAULT_NULL;
		
		String number = Constants.DEFAULT_NULL;
		number = tjsscLotterySg.getCpkNumber() == null ? Constants.DEFAULT_NULL : tjsscLotterySg.getCpkNumber();
		
		if(StringUtils.isEmpty(number)) {
			number = tjsscLotterySg.getKcwNumber() == null ? Constants.DEFAULT_NULL : tjsscLotterySg.getKcwNumber();
		} 

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 84:
			result = AusactSgUtils.getJssscBigOrSmall(number);//两面总和大小
			break;
		case 85:
			result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getWan());//第一球大小
			break;
		case 86:
			result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getQian());//第二球大小
			break;
		case 87:
			result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getBai());//第三球大小
			break;
		case 88:
			result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getShi());//第四球大小
			break;
		case 89:
			result = AusactSgUtils.getJssscSingleNumber(tjsscLotterySg.getGe());//第五球大小
			break;
		case 90:
			result = AusactSgUtils.getSingleAndDouble(number);//两面总和单双
			break;
		case 91:
			result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getWan());//第一球单双
			break;
		case 92:
			result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getQian());//第二球单双
			break;
		case 93:
			result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getBai());//第三球单双
			break;
		case 94:
			result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getShi());//第四球单双
			break;
		case 95:
			result = AusactSgUtils.getOneSingleAndDouble(tjsscLotterySg.getGe());//第五球单双
			break;
		default:
			break;
		}
		return result;
	}
		
	/**
	 * @param 北京PK10 大小长龙
	 */
	public void getBigAndSmallLong(List<BjpksLotterySg> bjpksLotterySgList){
		//冠亚和大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGYHBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSGYHBIG.getTagCnName());
		//冠军大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSGJBIG.getTagCnName());
		//亚军大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSYJBIG.getTagCnName());
		//第三名大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSDSMBIG.getTagCnName());
		//第四名大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSDFMBIG.getTagCnName());
		//第五名大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDWMBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSDWMBIG.getTagCnName());
		//第六名大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDLMBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSDLMBIG.getTagCnName());
		//第七名大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDQMBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSDQMBIG.getTagCnName());
		//第八名大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDMMBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSDMMBIG.getTagCnName());
		//第九名大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDJMBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSDJMBIG.getTagCnName());
		//第十名大小
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDTMBIG.getTagType(), CaipiaoPlayTypeEnum.BJPKSDTMBIG.getTagCnName());
	}
	
	/**
	 * @param 北京PK10 单双长龙
	 */
	public void getDoubleAndSingleLong(List<BjpksLotterySg> bjpksLotterySgList){
		//冠亚和单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGYHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSGYHDOUBLE.getTagCnName());
		//冠军单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSGJDOUBLE.getTagCnName());
		//亚军单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSYJDOUBLE.getTagCnName());
		//第三名单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSDSMDOUBLE.getTagCnName());
		//第四名单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSDFMDOUBLE.getTagCnName());
		//第五名单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDWMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSDWMDOUBLE.getTagCnName());
		//第六名单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDLMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSDLMDOUBLE.getTagCnName());
		//第七名单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDQMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSDQMDOUBLE.getTagCnName());
		//第八名单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDMMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSDMMDOUBLE.getTagCnName());
		//第九名单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDJMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSDJMDOUBLE.getTagCnName());
		//第十名单双
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDTMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.BJPKSDTMDOUBLE.getTagCnName());
	}
	
	/**
	 * @param 北京PK10 龙虎长龙
	 */
	public void getTrigleAndDragonLong(List<BjpksLotterySg> bjpksLotterySgList){
		//冠军龙虎
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSGJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.BJPKSGJTIDRAGON.getTagCnName());
		//亚军龙虎
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSYJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.BJPKSYJTIDRAGON.getTagCnName());
		//第三名龙虎
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDSMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.BJPKSDSMTIDRAGON.getTagCnName());
		//第四名龙虎
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDFMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.BJPKSDFMTIDRAGON.getTagCnName());
		//第五名龙虎
		this.getBjPksDragonInfo(bjpksLotterySgList, CaipiaoPlayTypeEnum.BJPKSDWMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.BJPKSDWMTIDRAGON.getTagCnName());
	}
	
	/**
	 * @param bjpksLotterySgList
	 * @param type
	 */
	public void getBjPksDragonInfo(List<BjpksLotterySg> bjpksLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(bjpksLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < bjpksLotterySgList.size() ; index++) {
					BjpksLotterySg bjpksLotterySg = bjpksLotterySgList.get(index);
					String numberString = bjpksLotterySg.getNumber();
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateBjpksResult(type, numberString);
					
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
				if(dragonSize >=  Constants.DEFAULT_SIX) {
					Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
					String  caiPiaoType = CaipiaoTypeEnum.BJPKS.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("北京PK10统计长龙异常:", e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果  
	* @author HANS
	* @date 2019年5月18日上午11:22:00
	*/ 
	public String calculateBjpksResult(int type, String numberString) {
		String result = Constants.DEFAULT_NULL;

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 174:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//冠亚和大小
			break;
		case 175:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//冠军大小
			break;
		case 176:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//亚军大小
			break;
		case 177:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第三名大小
			break;
		case 178:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第四名大小 
			break;
		case 179:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第五名大小
			break;
		case 180:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第六名大小
			break;
		case 181:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第七名大小
			break;
		case 182:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第八名大小
			break;
		case 183:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第九名大小
			break;
		case 184:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第十名大小
			break;
		case 185:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//冠亚和单双 
			break;
		case 186:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//冠军单双 
			break;
		case 187:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//亚军单双 
			break;
		case 188:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第三名单双
			break;
		case 189:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第四名单双
			break;
		case 190:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第五名单双
			break;
		case 191:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第六名单双
			break;
		case 192:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第七名单双
			break;
		case 193:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第八名单双
			break;
		case 194:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第九名单双
			break;
		case 195:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第十名单双
			break;
		case 196:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//冠军龙虎
			break;
		case 197:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//亚军龙虎
			break;
		case 198:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//第三名龙虎
			break;
		case 199:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//第四名龙虎
			break;
		case 200:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//第五名龙虎
			break;
		default:
			break;
		}
		return result;
	}
	
    /** 
    * @Title: selectOpenIssueList 
    * @Description: 获取 
    * @author HANS
    * @date 2019年5月18日下午4:56:25
    */ 
    public List<BjpksLotterySg> selectOpenIssueList() {
        BjpksLotterySgExample example = new BjpksLotterySgExample();
        BjpksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        return bjpksLotterySgMapper.selectByExample(example);
    }
    
	/** 
	* @Title: getCqsscAlgorithmData 
	* @Description: 缓存重庆时时彩数据 
	* @author HANS
	* @date 2019年5月17日下午4:20:03
	*/ 
	private List<CqsscLotterySg> getCqsscAlgorithmData(){
		CqsscLotterySgExample cqsscExample = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria cqsscCriteria = cqsscExample.createCriteria();
        cqsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        cqsscExample.setOrderByClause("`ideal_time` DESC");
        cqsscExample.setOffset(Constants.DEFAULT_INTEGER);
        cqsscExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        return this.cqsscLotterySgMapper.selectByExample(cqsscExample);  
	}
	
    /** 
    * @Title: getxjsscAlgorithmData 
    * @Description: 新疆时时彩获取近期开奖数据 
    * @author HANS
    * @date 2019年5月17日下午3:31:17
    */ 
    private List<XjsscLotterySg> getxjsscAlgorithmData() {
    	XjsscLotterySgExample xjsscExample = new XjsscLotterySgExample();
        XjsscLotterySgExample.Criteria xjsscCriteria = xjsscExample.createCriteria();
        xjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        xjsscExample.setOrderByClause("`ideal_time` DESC");
		xjsscExample.setOffset(Constants.DEFAULT_INTEGER);
		xjsscExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
        return xjsscLotterySgMapper.selectByExample(xjsscExample);
    }
    
	/** 
	* @Title: getTjsscAlgorithmData 
	* @Description: 获取近期天津时时彩开奖数据 
	* @author HANS
	* @date 2019年5月17日上午10:53:14
	*/ 
	private List<TjsscLotterySg> getTjsscAlgorithmData(){
		TjsscLotterySgExample tjExample = new TjsscLotterySgExample();
		TjsscLotterySgExample.Criteria tjsscCriteria = tjExample.createCriteria();
		tjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		tjExample.setOrderByClause("`ideal_time` DESC");
		tjExample.setOffset(Constants.DEFAULT_INTEGER);
		tjExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
		List<TjsscLotterySg> tjsscLotterySgList = tjsscLotterySgMapper.selectByExample(tjExample);
		return tjsscLotterySgList;
	}

}
