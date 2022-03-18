package com.caipiao.task.server.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.caipiao.task.server.service.TaskSgService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.CaipiaoPlayTypeEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.enums.TaskTypeEnum;
import com.caipiao.core.library.utils.RedisKeys;
import com.caipiao.task.server.service.LhcSeriesDragonService;
import com.caipiao.task.server.util.AusactSgUtils;
import com.caipiao.task.server.util.JspksSgUtils;
import com.caipiao.task.server.util.OnelhcSgUtils;
import com.mapper.AusactLotterySgMapper;
import com.mapper.JsbjpksLotterySgMapper;
import com.mapper.JssscLotterySgMapper;
import com.mapper.OnelhcLotterySgMapper;
import com.mapper.TxffcLotterySgMapper;
import com.mapper.domain.AusactLotterySg;
import com.mapper.domain.AusactLotterySgExample;
import com.mapper.domain.JsbjpksLotterySg;
import com.mapper.domain.JsbjpksLotterySgExample;
import com.mapper.domain.JssscLotterySg;
import com.mapper.domain.JssscLotterySgExample;
import com.mapper.domain.OnelhcLotterySg;
import com.mapper.domain.OnelhcLotterySgExample;
import com.mapper.domain.TxffcLotterySg;
import com.mapper.domain.TxffcLotterySgExample;
import com.caipiao.core.library.tool.StringUtils;

@Service
public class LhcSeriesDragonServiceimpl implements LhcSeriesDragonService{
	
	private static final Logger logger = LoggerFactory.getLogger(LhcSeriesDragonServiceimpl.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private TaskSgService taskSgService;
	@Autowired
	private AusactLotterySgMapper ausactLotterySgMapper;
	@Autowired
    private JssscLotterySgMapper jssscLotterySgMapper;
	@Autowired
    private TxffcLotterySgMapper txffcLotterySgMapper;
	@Autowired
	private OnelhcLotterySgMapper onelhcLotterySgMapper;
	@Autowired
    private JsbjpksLotterySgMapper jsbjpksLotterySgMapper;

	/**
	 * 
	 * SYSTEM一分钟开奖彩种长龙统计
	 */
	@Override
	public void totalDragonOneMinute() {
		// 澳洲ACT     2201    2F40M
		this.getActSgLong();
		// 极速时时彩  1106    1F
		this.getJssscSgLong();
		// 腾讯分分彩  1601    1F
		this.getTxffcSgLong();
		// 闪电六合彩  1202    1F
		this.getOnelhcSgLong();
		// 极速PK10    1304    1F	
		this.getJspksSgLong();
	}
	
	/**
	 *  澳洲ACT 长龙数据统计
	 */
	public void getActSgLong() {
		try {
			String algorithm = RedisKeys.AUSACT_ALGORITHM_VALUE;
			List<AusactLotterySg> ausactLotterySgList = (List<AusactLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(ausactLotterySgList)) {
				ausactLotterySgList = this.getActAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, ausactLotterySgList);
			}
			
			if (!CollectionUtils.isEmpty(ausactLotterySgList)) {
				// 大/小标记变量
				Integer bigAndSmallDragonSize = Constants.DEFAULT_INTEGER;
				Set<String> bigAndSmallDragonSet = new HashSet<String>();
				boolean bigAllowFlag = true;
				// 单/双标记变量
				Integer sigleAndDoubleDragonSize = Constants.DEFAULT_INTEGER;
				Set<String> sigleAndDoubleDragonSet = new HashSet<String>();
				boolean sigleAllowFlag = true;
				
				for (int index = Constants.DEFAULT_INTEGER; index < ausactLotterySgList.size() ; index++) {
					// 如果不符合规则， 大/小和单/双都不再统计
					if(!bigAllowFlag && !sigleAllowFlag) {
						break;
					}
					AusactLotterySg ausactLotterySg = ausactLotterySgList.get(index);
					// 大/小
					String bigOrSmallName = AusactSgUtils.getBigOrSmall(ausactLotterySg.getNumber());
					
					if(StringUtils.isEmpty(bigOrSmallName)) {
						// 大/小已经没有龙了，不再统计
						bigAllowFlag = false;
					}
					// 如果是“和”，退出
					if(Constants.BIGORSMALL_SAME.equals(bigOrSmallName)) {
						// 大/小已经没有龙了，不再统计
						bigAllowFlag = false;
					}
					// 把第一个结果加入SET集合
					if(index == Constants.DEFAULT_INTEGER) {
						if(bigAllowFlag) {
							bigAndSmallDragonSet.add(bigOrSmallName);
						}
					}			
					// 单/双
					String singleAndDoubleName = AusactSgUtils.getSingleAndDouble(ausactLotterySg.getNumber());
					
					if(StringUtils.isEmpty(singleAndDoubleName)) {
						// 单/双已经没有龙了，不再统计
						bigAllowFlag = false;
					}
					// 把第一个结果加入SET集合
					if(index == Constants.DEFAULT_INTEGER) {
						if(sigleAllowFlag) {
							sigleAndDoubleDragonSet.add(singleAndDoubleName);
						}
					}
					// 如果第一个和第二个开奖结果不一样，统计截止
					if(index == Constants.DEFAULT_ONE) {
						if(!bigAndSmallDragonSet.contains(bigOrSmallName)) {
							// 大/小已经没有龙了，不再统计
							bigAllowFlag = false;
						}
						if(!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
							// 单/双已经没有龙了，不再统计
							sigleAllowFlag = false;
						}
					}
					// 如果不符合规则， 大/小和单/双都不再统计
					if(!bigAllowFlag && !sigleAllowFlag) {
						break;
					}
					// 规则：连续3个开奖一样
					if(index == Constants.DEFAULT_TWO) {
						// 第三个数据
						if(!bigAndSmallDragonSet.contains(bigOrSmallName)) {
							// 大/小已经没有龙了，不再统计
							bigAllowFlag = false;
						}
						
						if(!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
							// 单/双已经没有龙了，不再统计
							sigleAllowFlag = false;
						}
						
						if(bigAllowFlag) {
							bigAndSmallDragonSize = Constants.DEFAULT_THREE;
						}
						
						if(sigleAllowFlag) {
							sigleAndDoubleDragonSize = Constants.DEFAULT_THREE;
						}
					}
					// 如果不符合规则， 大/小和单/双都不再统计
					if(!bigAllowFlag && !sigleAllowFlag) {
						break;
					}
					// 如果大于3个以上，继续统计，直到结果不一样
					if(index > Constants.DEFAULT_TWO) {
						// 大/小统计
						if(!bigAndSmallDragonSet.contains(bigOrSmallName)) {
							// 大/小已经没有龙了，不再统计
							bigAllowFlag = false;
						}
						
						if(bigAllowFlag) {
							bigAndSmallDragonSize++;
						}
						// 单/双统计
						if(!sigleAndDoubleDragonSet.contains(singleAndDoubleName)) {
							// 单/双已经没有龙了，不再统计
							sigleAllowFlag = false;
						}
						if(sigleAllowFlag) {
							sigleAndDoubleDragonSize++;
						}
					}
					// 如果不符合规则， 大/小和单/双都不再统计
					if(!bigAllowFlag && !sigleAllowFlag) {
						break;
					}
				}
				Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
				String  caiPiaoType = CaipiaoTypeEnum.AUSACT.getTagCnName(); 
				// 组织返回数据	
				if(bigAndSmallDragonSize >= Constants.DEFAULT_SIX) {
					String playType = CaipiaoPlayTypeEnum.AUSACTBIG.getTagCnName();
					List<String> dragonList = new ArrayList<String>(bigAndSmallDragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					// 大小统计入库
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType , dragonName, bigAndSmallDragonSize);
				}
				
				if(sigleAndDoubleDragonSize >= Constants.DEFAULT_SIX) {
					String playType = CaipiaoPlayTypeEnum.AUSACTDOUBLE.getTagCnName();
					List<String> dragonList = new ArrayList<String>(sigleAndDoubleDragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					// 单双统计入库
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, sigleAndDoubleDragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("澳洲ACT长龙数据统计异常" ,e);
		}
	}
	
	/**
	 *    极速时时彩 长龙数据统计
	 */
	public void getJssscSgLong() {
		try {
			String algorithm = RedisKeys.JSSSC_ALGORITHM_VALUE;
			List<JssscLotterySg> jssscLotterySgList = (List<JssscLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(jssscLotterySgList)) {
				jssscLotterySgList = this.getJssscAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, jssscLotterySgList);
			}
			// 获取大小长龙
			this.getJssscBigAndSmallLong(jssscLotterySgList);
			// 获取单双长龙
			this.getJssscSigleAndDoubleLong(jssscLotterySgList);
		} catch (Exception e) {
			logger.error("极速时时彩 长龙数据统计异常" ,e);
		}
	}
	
	/**
	 *  腾讯分分彩 长龙数据统计
	 */
	public void getTxffcSgLong() {
		try {
			String algorithm = RedisKeys.TXFFC_ALGORITHM_VALUE;
			List<TxffcLotterySg> txffcLotterySgList = (List<TxffcLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(txffcLotterySgList)) {
				txffcLotterySgList = this.selectNearTxffcIssue();
				redisTemplate.opsForValue().set(algorithm, txffcLotterySgList);
			}
			// 获取大小长龙
			this.getTxffcBigAndSmallLong(txffcLotterySgList);
			// 获取单双长龙
			this.getTxffcSigleAndDoubleLong(txffcLotterySgList);
		} catch (Exception e) {
			logger.error("腾讯分分彩 长龙数据统计异常" ,e);
		}
	}
	
	/**
	 *  德州六合彩 长龙数据统计
	 */
	public void getOnelhcSgLong() {
		try {
			String algorithm = RedisKeys.ONELHC_ALGORITHM_VALUE;
			List<OnelhcLotterySg> onelhcLotterySgList = (List<OnelhcLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(onelhcLotterySgList)) {
				onelhcLotterySgList = this.getOnelhcAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, onelhcLotterySgList);
			}
			// 特码两面 单双大小
			this.getWallBigAndSmallLong(onelhcLotterySgList);
			// 正码 总单总双总大总小
			this.getTotalDoubleAndBigLong(onelhcLotterySgList);
			// 正特 单双
			this.getZtsigleAndDoubleLong(onelhcLotterySgList);
			// 正特 大小
			this.getZtbigAndSmallLong(onelhcLotterySgList);
		} catch (Exception e) {
			logger.error("德州六合彩 长龙数据统计异常" ,e);
		}
	}
	
	/**
	 *  德州PK10 长龙数据统计
	 */
	public void getJspksSgLong() {
		try {
			String algorithm = RedisKeys.JSPKS_ALGORITHM_VALUE;
			List<JsbjpksLotterySg> jsbjpksLotterySgList = (List<JsbjpksLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(jsbjpksLotterySgList)) {
				jsbjpksLotterySgList = this.getJsbjpksAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, jsbjpksLotterySgList);
			}
			// 大小长龙
			this.getBigAndSmallLong(jsbjpksLotterySgList);
			// 单双长龙
			this.getDoubleAndSingleLong(jsbjpksLotterySgList);
			// 龙虎长龙
			this.getTrigleAndDragonLong(jsbjpksLotterySgList);
		} catch (Exception e) {
			logger.error("德州PK10 长龙数据统计异常" ,e);
		}
	}
	
	/**
	 * 德州PK10  大小长龙
	 */
	public void getBigAndSmallLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		//冠亚和大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGYHBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSGYHBIG.getTagCnName());
		//冠军大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSGJBIG.getTagCnName());
		//亚军大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSYJBIG.getTagCnName());
		//第三名大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSDSMBIG.getTagCnName());
		//第四名大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSDFMBIG.getTagCnName());
		//第五名大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDWMBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSDWMBIG.getTagCnName());
		//第六名大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDLMBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSDLMBIG.getTagCnName());
		//第七名大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDQMBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSDQMBIG.getTagCnName());
		//第八名大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDMMBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSDMMBIG.getTagCnName());
		//第九名大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDJMBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSDJMBIG.getTagCnName());
		//第十名大小
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDTMBIG.getTagType(), CaipiaoPlayTypeEnum.JSPKSDTMBIG.getTagCnName());
	}
	
	/**
	 * 德州PK10  单双长龙
	 */
	public void getDoubleAndSingleLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		//冠亚和单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGYHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSGYHDOUBLE.getTagCnName());
		//冠军单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSGJDOUBLE.getTagCnName());
		//亚军单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSYJDOUBLE.getTagCnName());
		//第三名单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSDSMDOUBLE.getTagCnName());
		//第四名单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSDFMDOUBLE.getTagCnName());
		//第五名单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDWMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSDWMDOUBLE.getTagCnName());
		//第六名单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDLMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSDLMDOUBLE.getTagCnName());
		//第七名单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDQMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSDQMDOUBLE.getTagCnName());
		//第八名单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDMMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSDMMDOUBLE.getTagCnName());
		//第九名单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDJMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSDJMDOUBLE.getTagCnName());
		//第十名单双
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDTMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSPKSDTMDOUBLE.getTagCnName());	
	}
	
	/**
	 * 德州PK10  龙虎长龙
	 */
	public void getTrigleAndDragonLong(List<JsbjpksLotterySg> jsbjpksLotterySgList){
		//冠军龙虎
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSGJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.JSPKSGJTIDRAGON.getTagCnName());
		//亚军龙虎
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSYJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.JSPKSYJTIDRAGON.getTagCnName());
		//第三名龙虎
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDSMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.JSPKSDSMTIDRAGON.getTagCnName());
		//第四名龙虎
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDFMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.JSPKSDFMTIDRAGON.getTagCnName());
		//第五名龙虎
		this.getJsbjpksDragonInfo(jsbjpksLotterySgList, CaipiaoPlayTypeEnum.JSPKSDWMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.JSPKSDWMTIDRAGON.getTagCnName());
	}
	
	/** 
	* @Title: getDragonInfo 
	* @Description: 公共方法，获取长龙数据 
	* @author HANS
	* @date 2019年5月13日下午11:22:03
	*/ 
	public void getJsbjpksDragonInfo(List<JsbjpksLotterySg> jsbjpksLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(jsbjpksLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < jsbjpksLotterySgList.size() ; index++) {
					JsbjpksLotterySg jsbjpksLotterySg = jsbjpksLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateOnePkResult(type, jsbjpksLotterySg);
					
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
					String  caiPiaoType = CaipiaoTypeEnum.JSPKS.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("获取德州PK10长龙数据异常：",e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果 
	* @return String
	* @author HANS
	* @date 2019年5月13日上午10:33:30
	*/ 
	public String calculateOnePkResult(int type, JsbjpksLotterySg jsbjpksLotterySg) {
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
	 * @param 德州六合彩 特码两面 单双大小
	 */
	private void getWallBigAndSmallLong(List<OnelhcLotterySg> onelhcLotterySgList){
		// 特码两面单双
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCTMLMDOUBLEDRAGON.getTagCnName());
		// 特码两面大小
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCTMLMBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCTMLMBIGDRAGON.getTagCnName());
	}
	
	/**
	 * @param 德州六合彩 正码 总单总双总大总小
	 */
	private void getTotalDoubleAndBigLong(List<OnelhcLotterySg> onelhcLotterySgList){
		// 正码总单总双
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZMTOTALDRAGON.getTagCnName());
		// 正码总大总小
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCTOTALBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCTOTALBIGDRAGON.getTagCnName());
	}
	
	/**
	 * @param 德州六合彩 正特 单双
	 */
	private void getZtsigleAndDoubleLong(List<OnelhcLotterySg> onelhcLotterySgList){
		// 正1特单双
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZYTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZYTDOUBLEDRAGON.getTagCnName());
		// 正2特单双
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZETDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZETDOUBLEDRAGON.getTagCnName());
		// 正3特单双
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZSTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZSTDOUBLEDRAGON.getTagCnName());
		// 正4特单双
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZFTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZFTDOUBLEDRAGON.getTagCnName());
		// 正5特单双
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZWTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZWTDOUBLEDRAGON.getTagCnName());
		// 正6特单双
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZLTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZLTDOUBLEDRAGON.getTagCnName());
	}
	
	/**
	 * @param 德州六合彩 正特 大小
	 */
	private void getZtbigAndSmallLong(List<OnelhcLotterySg> onelhcLotterySgList){
		// 正1特大小
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZYTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZYTBIGDRAGON.getTagCnName());
		// 正2特大小
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZETBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZETBIGDRAGON.getTagCnName());
		// 正3特大小
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZSTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZSTBIGDRAGON.getTagCnName());
		// 正4特大小
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZFTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZFTBIGDRAGON.getTagCnName());
		// 正5特大小
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZWTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZWTBIGDRAGON.getTagCnName());
		// 正6特大小
		this.getOneLhcTotalDragonInfo(onelhcLotterySgList, CaipiaoPlayTypeEnum.ONELHCZLTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.ONELHCZLTBIGDRAGON.getTagCnName());
	}
	
	/** 
	* @Title: getOneLhcTotalDragonInfo 
	* @Description: 一分六合彩获取长龙数据
	* @author HANS
	* @date 2019年5月15日下午4:48:14
	*/ 
	private void getOneLhcTotalDragonInfo(List<OnelhcLotterySg> onelhcLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(onelhcLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < onelhcLotterySgList.size() ; index++) {
					OnelhcLotterySg onelhcLotterySg = onelhcLotterySgList.get(index);
					String numberString = onelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : onelhcLotterySg.getNumber();
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateOneLhcResult(type, numberString);
					
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
					String  caiPiaoType = CaipiaoTypeEnum.ONELHC.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("获取德州六合彩长龙数据异常：",e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 获取计算结果 
	* @author HANS
	* @date 2019年5月15日下午6:48:34
	*/ 
	public String calculateOneLhcResult(int type, String number) {
		String result = Constants.DEFAULT_NULL;
		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 44:
			result = OnelhcSgUtils.getOnelhcBigOrDouble(number,type);//特码两面单双
			break;
		case 45:
			result = OnelhcSgUtils.getOnelhcBigOrDouble(number,type);//特码两面大小
			break;
		case 46:
			result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number,type);//正码总单总双
			break;
		case 47:
			result = OnelhcSgUtils.getOnelhcTotalBigOrDouble(number,type);//正码总大总小
			break;
		case 48:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,0);//正1特单双
			break;
		case 49:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,1);//正2特单双
			break;
		case 50:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,2);//正3特单双
			break;
		case 51:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,3);//正4特单双
			break;
		case 52:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,4);//正5特单双
			break;
		case 53:
			result = OnelhcSgUtils.getZytSigleOrDouble(number,5);//正6特单双
			break;
		case 54:
			result = OnelhcSgUtils.getZytBigOrSmall(number,0);//正1特大小
			break;
		case 55:
			result = OnelhcSgUtils.getZytBigOrSmall(number,1);//正2特大小
			break;
		case 56:
			result = OnelhcSgUtils.getZytBigOrSmall(number,2);//正3特大小
			break;
		case 57:
			result = OnelhcSgUtils.getZytBigOrSmall(number,3);//正4特大小
			break;
		case 58:
			result = OnelhcSgUtils.getZytBigOrSmall(number,4);//正5特大小
			break;
		case 59:
			result = OnelhcSgUtils.getZytBigOrSmall(number,5);//正6特大小
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * 获取腾讯分分彩大小长龙
	 */
	private void getTxffcBigAndSmallLong(List<TxffcLotterySg> txffcLotterySgList){
		// 腾讯分分彩两面总和大小
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCLMZHBIG.getTagType(), CaipiaoPlayTypeEnum.TXFFCLMZHBIG.getTagCnName());
		// 腾讯分分彩第一球大小
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDYQBIG.getTagType(), CaipiaoPlayTypeEnum.TXFFCDYQBIG.getTagCnName());
		// 腾讯分分彩第二球大小
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDEQBIG.getTagType(), CaipiaoPlayTypeEnum.TXFFCDEQBIG.getTagCnName());
		// 腾讯分分彩第三球大小
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDSQBIG.getTagType(), CaipiaoPlayTypeEnum.TXFFCDSQBIG.getTagCnName());
		// 腾讯分分彩第四球大小
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDFQBIG.getTagType(), CaipiaoPlayTypeEnum.TXFFCDFQBIG.getTagCnName());
		// 腾讯分分彩第五球大小
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDWQBIG.getTagType(), CaipiaoPlayTypeEnum.TXFFCDWQBIG.getTagCnName());
	}
	
	/**
	 * 获取腾讯分分彩单双长龙
	 */
	private void getTxffcSigleAndDoubleLong(List<TxffcLotterySg> txffcLotterySgList){
		// 腾讯分分彩两面总和单双
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCLMZHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TXFFCLMZHDOUBLE.getTagCnName());
		// 腾讯分分彩第一球单双
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDYQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TXFFCDYQDOUBLE.getTagCnName());
		// 腾讯分分彩第二球单双
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDEQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TXFFCDEQDOUBLE.getTagCnName());
		// 腾讯分分彩第三球单双
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDSQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TXFFCDSQDOUBLE.getTagCnName());
		// 腾讯分分彩第四球单双
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDFQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TXFFCDFQDOUBLE.getTagCnName());
		// 腾讯分分彩第五球单双
		this.getTxffcDragonInfo(txffcLotterySgList, CaipiaoPlayTypeEnum.TXFFCDWQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.TXFFCDWQDOUBLE.getTagCnName());
	}
	
	/**
	 * @param 公共方法，获取腾讯分分彩长龙数据
	 * @param type
	 */
	private void getTxffcDragonInfo(List<TxffcLotterySg> txffcLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(txffcLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();

				for (int index = Constants.DEFAULT_INTEGER; index < txffcLotterySgList.size() ; index++) {
					TxffcLotterySg txffcLotterySg = txffcLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateTxffcResult(type, txffcLotterySg);
					
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
					String  caiPiaoType = CaipiaoTypeEnum.TXFFC.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("获取腾讯分分彩长龙数据异常：",e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果 
	* @return String
	* @author HANS
	* @date 2019年5月13日上午10:33:30
	*/ 
	private String calculateTxffcResult(int type, TxffcLotterySg txffcLotterySg) {
		String result = Constants.DEFAULT_NULL;
		String number = Constants.DEFAULT_NULL;
		number = txffcLotterySg.getCpkNumber() == null ? Constants.DEFAULT_NULL : txffcLotterySg.getCpkNumber();
		
		if(StringUtils.isEmpty(number)) {
			number = txffcLotterySg.getKcwNumber() == null ? Constants.DEFAULT_NULL : txffcLotterySg.getKcwNumber();
		} 
		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 233:
			result = AusactSgUtils.getJssscBigOrSmall(number);//两面总和大小
			break;
		case 234:
			result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getWan());//第一球大小
			break;
		case 235:
			result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getQian());//第二球大小
			break;
		case 236:
			result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getBai());//第三球大小
			break;
		case 237:
			result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getShi());//第四球大小
			break;
		case 238:
			result = AusactSgUtils.getJssscSingleNumber(txffcLotterySg.getGe());//第五球大小
			break;
		case 239:
			result = AusactSgUtils.getSingleAndDouble(number);//两面总和单双
			break;
		case 240:
			result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getWan());//第一球单双
			break;
		case 241:
			result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getQian());//第二球单双
			break;
		case 242:
			result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getBai());//第三球单双
			break;
		case 243:
			result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getShi());//第四球单双
			break;
		case 244:
			result = AusactSgUtils.getOneSingleAndDouble(txffcLotterySg.getGe());//第五球单双
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * 德州时时彩大小长龙统计
	 */
	private void getJssscBigAndSmallLong(List<JssscLotterySg> jssscLotterySgList){
		// 德州时时彩两面总和大小
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCLMZHBIG.getTagType(), CaipiaoPlayTypeEnum.JSSSCLMZHBIG.getTagCnName());
		// 德州时时彩第一球大小
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDYQBIG.getTagType(), CaipiaoPlayTypeEnum.JSSSCDYQBIG.getTagCnName());
		// 德州时时彩第二球大小
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDEQBIG.getTagType(), CaipiaoPlayTypeEnum.JSSSCDEQBIG.getTagCnName());
		// 德州时时彩第三球大小
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDSQBIG.getTagType(), CaipiaoPlayTypeEnum.JSSSCDSQBIG.getTagCnName());
		// 德州时时彩第四球大小
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDFQBIG.getTagType(), CaipiaoPlayTypeEnum.JSSSCDFQBIG.getTagCnName());
		// 德州时时彩第五球大小
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDWQBIG.getTagType(), CaipiaoPlayTypeEnum.JSSSCDWQBIG.getTagCnName());
	}
	
	/**
	 * 德州时时彩单双长龙统计
	 */
	private void getJssscSigleAndDoubleLong(List<JssscLotterySg> jssscLotterySgList){
		// 德州时时彩两面总和单双
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCLMZHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSSSCLMZHDOUBLE.getTagCnName());
		// 德州时时彩第一球单双
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDYQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSSSCDYQDOUBLE.getTagCnName());
		// 德州时时彩第二球单双
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDEQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSSSCDEQDOUBLE.getTagCnName());
		// 德州时时彩第三球单双
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDSQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSSSCDSQDOUBLE.getTagCnName());
		// 德州时时彩第四球单双
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDFQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSSSCDFQDOUBLE.getTagCnName());
		// 德州时时彩第五球单双
		this.getJssscDragonInfo(jssscLotterySgList, CaipiaoPlayTypeEnum.JSSSCDWQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.JSSSCDWQDOUBLE.getTagCnName());
	}
	
	/**
	 * @param 公共方法，获取德州时时彩长龙数据
	 * @param type
	 */
	private void getJssscDragonInfo(List<JssscLotterySg> jssscLotterySgList, int type, String typeName){
		try {
			if (!CollectionUtils.isEmpty(jssscLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();

				for (int index = Constants.DEFAULT_INTEGER; index < jssscLotterySgList.size() ; index++) {
					JssscLotterySg jssscLotterySg = jssscLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateResult(type, jssscLotterySg);
					
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
					String  caiPiaoType = CaipiaoTypeEnum.JSSSC.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String sourcePlayType = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, typeName, sourcePlayType, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("获取德州时时彩长龙数据异常：",e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果 
	* @return String
	* @author HANS
	* @date 2019年5月13日上午10:33:30
	*/ 
	private String calculateResult(int type, JssscLotterySg jssscLotterySg) {
		String result = Constants.DEFAULT_NULL;

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 5:
			result = AusactSgUtils.getJssscBigOrSmall(jssscLotterySg.getNumber());//两面总和大小
			break;
		case 6:
			result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getWan());//第一球大小
			break;
		case 7:
			result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getQian());//第二球大小
			break;
		case 8:
			result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getBai());//第三球大小
			break;
		case 9:
			result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getShi());//第四球大小
			break;
		case 10:
			result = AusactSgUtils.getJssscSingleNumber(jssscLotterySg.getGe());//第五球大小
			break;
		case 11:
			result = AusactSgUtils.getSingleAndDouble(jssscLotterySg.getNumber());//两面总和单双
			break;
		case 12:
			result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getWan());//第一球单双
			break;
		case 13:
			result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getQian());//第二球单双
			break;
		case 14:
			result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getBai());//第三球单双
			break;
		case 15:
			result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getShi());//第四球单双
			break;
		case 16:
			result = AusactSgUtils.getOneSingleAndDouble(jssscLotterySg.getGe());//第五球单双
			break;
		default:
			break;
		}
		return result;
	}
	
	/** 
	* @Title: getAlgorithmData 
	* @Description: 查询近200期开奖数据 
	* @return List<AusactLotterySg>
	* @author HANS
	* @date 2019年5月7日下午2:51:59
	*/ 
	public List<AusactLotterySg> getActAlgorithmData() {
		AusactLotterySgExample ausactExample = new AusactLotterySgExample();
        AusactLotterySgExample.Criteria ausactCriteria = ausactExample.createCriteria();
        ausactCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        ausactExample.setOrderByClause("ideal_time DESC");
        ausactExample.setOffset(Constants.DEFAULT_INTEGER);
        ausactExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);               
        List<AusactLotterySg> ausactLotterySgList = ausactLotterySgMapper.selectByExample(ausactExample);
		return ausactLotterySgList;
	}
	
	/** 
	* @Title: getAlgorithmData 
	* @Description: 查询近期数据 
	* @return List<JssscLotterySg>
	* @author HANS
	* @date 2019年5月13日上午12:00:24
	*/ 
	private List<JssscLotterySg> getJssscAlgorithmData() {
		JssscLotterySgExample example = new JssscLotterySgExample();
        JssscLotterySgExample.Criteria jssscCriteria = example.createCriteria();
        jssscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        List<JssscLotterySg> jssscLotterySgList = jssscLotterySgMapper.selectByExample(example);
		return jssscLotterySgList;
	}
	
	/** 
	* @Title: selectNearTxffcIssue 
	* @Description: 查询近期数据
	* @author HANS
	* @date 2019年5月28日下午8:26:41
	*/ 
	private List<TxffcLotterySg> selectNearTxffcIssue() {
		TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
        txffcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        txffcExample.setOrderByClause("`ideal_time` DESC");
        txffcExample.setOffset(Constants.DEFAULT_INTEGER);
        txffcExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        List<TxffcLotterySg> txffcLotterySgList = txffcLotterySgMapper.selectByExample(txffcExample);
		return txffcLotterySgList;
	}
	
    /** 
    * @Title: getAlgorithmData 
    * @Description: 缓存近期数据
    * @author HANS
    * @date 2019年5月15日上午10:58:26
    */ 
	private List<OnelhcLotterySg> getOnelhcAlgorithmData() {
		OnelhcLotterySgExample onelhcExample = new OnelhcLotterySgExample();
		OnelhcLotterySgExample.Criteria onelhcCriteria = onelhcExample.createCriteria();
		onelhcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		onelhcExample.setOrderByClause("`ideal_time` DESC");
		onelhcExample.setOffset(Constants.DEFAULT_INTEGER);
		onelhcExample.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);
		return onelhcLotterySgMapper.selectByExample(onelhcExample);
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
