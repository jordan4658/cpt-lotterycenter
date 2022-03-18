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
import com.caipiao.task.server.service.FiveMinuteDragonService;
import com.caipiao.task.server.service.TaskSgService;
import com.caipiao.task.server.util.AusactSgUtils;
import com.caipiao.task.server.util.JspksSgUtils;
import com.caipiao.task.server.util.OnelhcSgUtils;
import com.mapper.FivebjpksLotterySgMapper;
import com.mapper.FivelhcLotterySgMapper;
import com.mapper.FivesscLotterySgMapper;
import com.mapper.PceggLotterySgMapper;
import com.mapper.XyftLotterySgMapper;
import com.mapper.domain.FivebjpksLotterySg;
import com.mapper.domain.FivebjpksLotterySgExample;
import com.mapper.domain.FivelhcLotterySg;
import com.mapper.domain.FivelhcLotterySgExample;
import com.mapper.domain.FivesscLotterySg;
import com.mapper.domain.FivesscLotterySgExample;
import com.mapper.domain.PceggLotterySg;
import com.mapper.domain.PceggLotterySgExample;
import com.mapper.domain.XyftLotterySg;
import com.mapper.domain.XyftLotterySgExample;

@Service
public class FiveMinuteDragonServiceImpl implements FiveMinuteDragonService{
	
	private static final Logger logger = LoggerFactory.getLogger(FiveMinuteDragonServiceImpl.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private TaskSgService taskSgService;
    @Autowired
    private PceggLotterySgMapper pceggLotterySgMapper;
	@Autowired
	private FivesscLotterySgMapper fivesscLotterySgMapper;
	@Autowired
	private FivelhcLotterySgMapper fivelhcLotterySgMapper;
    @Autowired
	private FivebjpksLotterySgMapper fivebjpksLotterySgMapper;
    @Autowired
    private XyftLotterySgMapper xyftLotterySgMapper;
		
	/**
	 * 
	 * SYSTEM五分钟开奖彩种长龙统计
	 */
	@Override
	public void totalDragonOneMinute() {
		// PC蛋蛋      1501    5F
		this.getPceggSgLong();
		// 5分时时彩   1105    5F
		this.getFivesscSgLong();
		// 5分六合彩   1203    5F
		this.getFivelhcSgLong();
		// 5分PK10     1303    5F
		this.getFivePksSgLong();
		// 幸运飞艇    1401    5F
		this.getXyftPksSgLong();
	}
	
	/**
	 *  PC蛋蛋 长龙数据统计
	 */
	public void getPceggSgLong() {
		try {
			String algorithm = RedisKeys.PCDAND_ALGORITHM_VALUE;
			List<PceggLotterySg> pceggLotterySgList = (List<PceggLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(pceggLotterySgList)) {
				pceggLotterySgList = this.getAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, pceggLotterySgList);
			}
			
			if (!CollectionUtils.isEmpty(pceggLotterySgList)) {
				this.pceggTotalDragon(pceggLotterySgList);
			}
		} catch (Exception e) {
			logger.error("PC蛋蛋长龙数据统计异常" ,e);
		}
	}
	
	/**
	 *  5分时时彩 长龙数据统计
	 */
	public void getFivesscSgLong() {
		try {
			String algorithm = RedisKeys.FIVESSC_ALGORITHM_VALUE;
        	List<FivesscLotterySg> fivesscLotterySgList = (List<FivesscLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(fivesscLotterySgList)) {
				fivesscLotterySgList = this.selectFivesscByIssue();
				redisTemplate.opsForValue().set(algorithm, fivesscLotterySgList);
			}
			// 获取大小长龙
			this.getFivesscBigAndSmallLong(fivesscLotterySgList);
			// 获取单双长龙
			this.getFivesscSigleAndDoubleLong(fivesscLotterySgList);
		} catch (Exception e) {
			logger.error("5分时时彩长龙数据统计异常" ,e);
		}
	}
	
	/**
	 *  5分六合彩 长龙数据统计
	 */
	public void getFivelhcSgLong() {
		try {
			String algorithm = RedisKeys.FIVELHC_ALGORITHM_VALUE;
			List<FivelhcLotterySg> fivelhcLotterySgList = (List<FivelhcLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(fivelhcLotterySgList)) {
				fivelhcLotterySgList = this.getFivelhcAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, fivelhcLotterySgList);
			}
			// 特码两面 单双大小
			this.getWallBigAndSmallLong(fivelhcLotterySgList);
			// 正码 总单总双总大总小
			this.getTotalDoubleAndBigLong(fivelhcLotterySgList);
			// 正特 单双
			this.getZtsigleAndDoubleLong(fivelhcLotterySgList);
			// 正特 大小
			this.getZtbigAndSmallLong(fivelhcLotterySgList);
		} catch (Exception e) {
			logger.error("5分六合彩长龙数据统计异常" ,e);
		}
	}
	
	/**
	 *  5分PK10 长龙数据统计
	 */
	public void getFivePksSgLong() {
		try {
			String algorithm = RedisKeys.FIVEPKS_ALGORITHM_VALUE;
			List<FivebjpksLotterySg> fivePksLotteryList = (List<FivebjpksLotterySg>)redisTemplate.opsForValue().get(algorithm);
			
			if(CollectionUtils.isEmpty(fivePksLotteryList)) {
				fivePksLotteryList = this.selectFivebjpksByIssue();
				redisTemplate.opsForValue().set(algorithm, fivePksLotteryList);
			}
			// 大小长龙
			this.getBigAndSmallLong(fivePksLotteryList);
			// 单双长龙
			this.getDoubleAndSingleLong(fivePksLotteryList);
			// 龙虎长龙
			this.getTrigleAndDragonLong(fivePksLotteryList);
		} catch (Exception e) {
			logger.error("5分PK10长龙数据统计异常" ,e);
		}
	}
	
	/**
	 *  幸运飞艇 长龙数据统计
	 */
	public void getXyftPksSgLong() {
		try {
			String algorithm = RedisKeys.XYFEIT_ALGORITHM_VALUE;
			List<XyftLotterySg> xyftLotterySgList = (List<XyftLotterySg>) redisTemplate.opsForValue().get(algorithm);

			if (CollectionUtils.isEmpty(xyftLotterySgList)) {
				xyftLotterySgList = this.getXyftpksAlgorithmData();
				redisTemplate.opsForValue().set(algorithm, xyftLotterySgList);
			}
			// 大小长龙
			 this.getXyftBigAndSmallLong(xyftLotterySgList);
			// 单双长龙
			this.getXyftDoubleAndSingleLong(xyftLotterySgList);
			// 龙虎长龙
			this.getXyftTrigleAndDragonLong(xyftLotterySgList);
		} catch (Exception e) {
			logger.error("幸运飞艇长龙数据统计异常" ,e);
		}
	}
	
	/**
	 * @param 幸运飞艇  大小长龙
	 */
	private void getXyftBigAndSmallLong(List<XyftLotterySg> xyftLotterySgList){
		//冠亚和大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGYHBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSGYHBIG.getTagCnName());
		//冠军大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGJBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSGJBIG.getTagCnName());
		//亚军大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSYJBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSYJBIG.getTagCnName());
		//第三名大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDSMBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDSMBIG.getTagCnName());
		//第四名大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDFMBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDFMBIG.getTagCnName());
		//第五名大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDWMBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDWMBIG.getTagCnName());
		//第六名大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDLMBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDLMBIG.getTagCnName());
		//第七名大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDQMBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDQMBIG.getTagCnName());
		//第八名大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDMMBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDMMBIG.getTagCnName());
		//第九名大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDJMBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDJMBIG.getTagCnName());
		//第十名大小
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDTMBIG.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDTMBIG.getTagCnName());
	}
	
	/**
	 * @param 幸运飞艇  大小长龙
	 */
	private void getXyftDoubleAndSingleLong(List<XyftLotterySg> xyftLotterySgList){
		//冠亚和单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGYHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSGYHDOUBLE.getTagCnName());
		//冠军单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSGJDOUBLE.getTagCnName());
		//亚军单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSYJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSYJDOUBLE.getTagCnName());
		//第三名单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDSMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDSMDOUBLE.getTagCnName());
		//第四名单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDFMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDFMDOUBLE.getTagCnName());
		//第五名单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDWMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDWMDOUBLE.getTagCnName());
		//第六名单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDLMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDLMDOUBLE.getTagCnName());
		//第七名单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDQMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDQMDOUBLE.getTagCnName());
		//第八名单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDMMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDMMDOUBLE.getTagCnName());
		//第九名单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDJMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDJMDOUBLE.getTagCnName());
		//第十名单双
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDTMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDTMDOUBLE.getTagCnName());
	}
	
	/**
	 * @param 幸运飞艇  大小长龙
	 */
	private void getXyftTrigleAndDragonLong(List<XyftLotterySg> xyftLotterySgList){
		//冠军龙虎
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSGJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSGJTIDRAGON.getTagCnName());
		//亚军龙虎
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSYJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSYJTIDRAGON.getTagCnName());
		//第三名龙虎
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDSMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDSMTIDRAGON.getTagCnName());
		//第四名龙虎
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDFMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDFMTIDRAGON.getTagCnName());
		//第五名龙虎
		this.getXyftDragonInfo(xyftLotterySgList, CaipiaoPlayTypeEnum.XYFTPKSDWMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.XYFTPKSDWMTIDRAGON.getTagCnName());
	}
	
	/** 
	* @Title: getDragonInfo 
	* @Description: 公共方法，获取长龙数据   
	* @author HANS
	* @date 2019年5月18日下午7:04:47
	*/ 
	private void getXyftDragonInfo(List<XyftLotterySg> xyftLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(xyftLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < xyftLotterySgList.size() ; index++) {
					XyftLotterySg xyftLotterySg = xyftLotterySgList.get(index);
					String numberString = xyftLotterySg.getNumber();
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateXyftResult(type, numberString);
					
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
				if(dragonSize  >= Constants.DEFAULT_SIX) {
					Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
					String  caiPiaoType = CaipiaoTypeEnum.XYFEIT.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("app_getSgLongDragons.json#TenpksLotterySgServiceImpl_getDragonInfo_error:", e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果  
	* @author HANS
	* @date 2019年5月18日上午11:22:00
	*/ 
	private String calculateXyftResult(int type, String numberString) {
		String result = Constants.DEFAULT_NULL;

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 245:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//冠亚和大小
			break;
		case 246:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//冠军大小
			break;
		case 247:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//亚军大小
			break;
		case 248:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第三名大小
			break;
		case 249:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第四名大小 
			break;
		case 250:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第五名大小
			break;
		case 251:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第六名大小
			break;
		case 252:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第七名大小
			break;
		case 253:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第八名大小
			break;
		case 254:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第九名大小
			break;
		case 255:
			result = JspksSgUtils.getJspksBigOrSmall(numberString,type);//第十名大小
			break;
		case 256:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//冠亚和单双 
			break;
		case 257:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//冠军单双 
			break;
		case 258:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//亚军单双 
			break;
		case 259:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第三名单双
			break;
		case 260:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第四名单双
			break;
		case 261:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第五名单双
			break;
		case 262:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第六名单双
			break;
		case 263:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第七名单双
			break;
		case 264:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第八名单双
			break;
		case 265:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第九名单双
			break;
		case 266:
			result = JspksSgUtils.getJspksSigleAndDouble(numberString,type);//第十名单双
			break;
		case 267:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//冠军龙虎
			break;
		case 268:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//亚军龙虎
			break;
		case 269:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//第三名龙虎
			break;
		case 270:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//第四名龙虎
			break;
		case 271:
			result = JspksSgUtils.getJspksDragonAndtiger(numberString,type);//第五名龙虎
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * @param 5分PK10  大小长龙
	 */
	public void getBigAndSmallLong(List<FivebjpksLotterySg> fivePksLotteryList){
		//冠亚和大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGYHBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSGYHBIG.getTagCnName());
		//冠军大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGJBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSGJBIG.getTagCnName());
		//亚军大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSYJBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSYJBIG.getTagCnName());
		//第三名大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDSMBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDSMBIG.getTagCnName());
		//第四名大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDFMBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDFMBIG.getTagCnName());
		//第五名大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDWMBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDWMBIG.getTagCnName());
		//第六名大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDLMBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDLMBIG.getTagCnName());
		//第七名大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDQMBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDQMBIG.getTagCnName());
		//第八名大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDMMBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDMMBIG.getTagCnName());
		//第九名大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDJMBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDJMBIG.getTagCnName());
		//第十名大小
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDTMBIG.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDTMBIG.getTagCnName());
	}
	
	/**
	 * @param 5分PK10  单双长龙
	 */
	public void getDoubleAndSingleLong(List<FivebjpksLotterySg> fivePksLotteryList){
		//冠亚和单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGYHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSGYHDOUBLE.getTagCnName());
		//冠军单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSGJDOUBLE.getTagCnName());
		//亚军单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSYJDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSYJDOUBLE.getTagCnName());
		//第三名单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDSMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDSMDOUBLE.getTagCnName());
		//第四名单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDFMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDFMDOUBLE.getTagCnName());
		//第五名单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDWMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDWMDOUBLE.getTagCnName());
		//第六名单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDLMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDLMDOUBLE.getTagCnName());
		//第七名单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDQMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDQMDOUBLE.getTagCnName());
		//第八名单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDMMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDMMDOUBLE.getTagCnName());
		//第九名单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDJMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDJMDOUBLE.getTagCnName());
		//第十名单双
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDTMDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDTMDOUBLE.getTagCnName());
	}
	
	/**
	 * @param 5分PK10 龙虎长龙
	 */
	public void getTrigleAndDragonLong(List<FivebjpksLotterySg> fivePksLotteryList){
		//冠军龙虎
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSGJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSGJTIDRAGON.getTagCnName());
		//亚军龙虎
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSYJTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSYJTIDRAGON.getTagCnName());
		//第三名龙虎
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDSMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDSMTIDRAGON.getTagCnName());
		//第四名龙虎
	    this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDFMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDFMTIDRAGON.getTagCnName());
		//第五名龙虎
		this.getFivePksDragonInfo(fivePksLotteryList, CaipiaoPlayTypeEnum.FIVEPKSDWMTIDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVEPKSDWMTIDRAGON.getTagCnName());
	}
	
	/** 
	* @Title: getDragonInfo 
	* @Description: 公共方法，获取长龙数据  
	* @author HANS
	* @date 2019年5月18日上午11:18:00
	*/ 
	public void getFivePksDragonInfo(List<FivebjpksLotterySg> fivePksLotteryList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(fivePksLotteryList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < fivePksLotteryList.size() ; index++) {
					FivebjpksLotterySg fivebjpksLotterySg = fivePksLotteryList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateFivePksResult(type, fivebjpksLotterySg);
					
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
				if(dragonSize  >= Constants.DEFAULT_SIX) {
					Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
					String  caiPiaoType = CaipiaoTypeEnum.FIVEPKS.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("五分PK10长龙统计异常:", e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果  
	* @author HANS
	* @date 2019年5月18日上午11:22:00
	*/ 
	public String calculateFivePksResult(int type, FivebjpksLotterySg fivebjpksLotterySg) {
		String result = Constants.DEFAULT_NULL;

		switch (type) {
		case 0:
			return Constants.DEFAULT_NULL;
		case 120:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//冠亚和大小
			break;
		case 121:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//冠军大小
			break;
		case 122:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//亚军大小
			break;
		case 123:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//第三名大小
			break;
		case 124:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//第四名大小 
			break;
		case 125:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//第五名大小
			break;
		case 126:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//第六名大小
			break;
		case 127:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//第七名大小
			break;
		case 128:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//第八名大小
			break;
		case 129:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//第九名大小
			break;
		case 130:
			result = JspksSgUtils.getJspksBigOrSmall(fivebjpksLotterySg.getNumber(),type);//第十名大小
			break;
		case 131:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//冠亚和单双 
			break;
		case 132:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//冠军单双 
			break;
		case 133:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//亚军单双 
			break;
		case 134:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//第三名单双
			break;
		case 135:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//第四名单双
			break;
		case 136:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//第五名单双
			break;
		case 137:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//第六名单双
			break;
		case 138:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//第七名单双
			break;
		case 139:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//第八名单双
			break;
		case 140:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//第九名单双
			break;
		case 141:
			result = JspksSgUtils.getJspksSigleAndDouble(fivebjpksLotterySg.getNumber(),type);//第十名单双
			break;
		case 142:
			result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(),type);//冠军龙虎
			break;
		case 143:
			result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(),type);//亚军龙虎
			break;
		case 144:
			result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(),type);//第三名龙虎
			break;
		case 145:
			result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(),type);//第四名龙虎
			break;
		case 146:
			result = JspksSgUtils.getJspksDragonAndtiger(fivebjpksLotterySg.getNumber(),type);//第五名龙虎
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * @param 5分六合彩 特码两面 单双大小
	 */
	public void getWallBigAndSmallLong(List<FivelhcLotterySg> fivelhcLotterySgList){
		// 特码两面单双
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCTMLMDOUBLEDRAGON.getTagCnName());
		// 特码两面大小
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCTMLMBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCTMLMBIGDRAGON.getTagCnName());
	}
	
	/**
	 * @param 5分六合彩 正码 总单总双总大总小
	 */
	public void getTotalDoubleAndBigLong(List<FivelhcLotterySg> fivelhcLotterySgList){
		// 正码总单总双
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZMTOTALDRAGON.getTagCnName());
		// 正码总大总小
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCTOTALBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCTOTALBIGDRAGON.getTagCnName());
	}
	
	/**
	 * @param 5分六合彩 正特 单双
	 */
	public void getZtsigleAndDoubleLong(List<FivelhcLotterySg> fivelhcLotterySgList){
		// 正1特单双
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZYTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZYTDOUBLEDRAGON.getTagCnName());
		// 正2特单双
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZETDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZETDOUBLEDRAGON.getTagCnName());
		// 正3特单双
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZSTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZSTDOUBLEDRAGON.getTagCnName());
		// 正4特单双
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZFTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZFTDOUBLEDRAGON.getTagCnName());
		// 正5特单双
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZWTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZWTDOUBLEDRAGON.getTagCnName());
		// 正6特单双
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZLTDOUBLEDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZLTDOUBLEDRAGON.getTagCnName());
	}
	
	/**
	 * @param 5分六合彩 正特 大小
	 */
	public void getZtbigAndSmallLong(List<FivelhcLotterySg> fivelhcLotterySgList){
		// 正1特大小
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZYTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZYTBIGDRAGON.getTagCnName());
		// 正2特大小
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZETBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZETBIGDRAGON.getTagCnName());
		// 正3特大小
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZSTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZSTBIGDRAGON.getTagCnName());
		// 正4特大小
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZFTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZFTBIGDRAGON.getTagCnName());
		// 正5特大小
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZWTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZWTBIGDRAGON.getTagCnName());
		// 正6特大小
		this.getFivelhcDragonInfo(fivelhcLotterySgList, CaipiaoPlayTypeEnum.FIVELHCZLTBIGDRAGON.getTagType(), CaipiaoPlayTypeEnum.FIVELHCZLTBIGDRAGON.getTagCnName());
	}
	
	/** 
	* @Title: getDragonInfo 
	* @Description: 五分六合彩获取长龙数据
	* @author HANS
	* @date 2019年5月21日下午4:23:03
	*/ 
	public void getFivelhcDragonInfo(List<FivelhcLotterySg> fivelhcLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(fivelhcLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();
				
				for (int index = Constants.DEFAULT_INTEGER; index < fivelhcLotterySgList.size() ; index++) {
					FivelhcLotterySg fivelhcLotterySg = fivelhcLotterySgList.get(index);
					String numberString = fivelhcLotterySg.getNumber() == null ? Constants.DEFAULT_NULL : fivelhcLotterySg.getNumber();
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateFivelhcResult(type, numberString);
					
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
					String  caiPiaoType = CaipiaoTypeEnum.FIVELHC.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("获取5分六合彩长龙数据异常：",e);
		}
	}
	
	public String calculateFivelhcResult(int type, String number) {
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
	 * @param 5分时时彩 获取大小长龙  
	 */
	private void getFivesscBigAndSmallLong(List<FivesscLotterySg> fivesscLotterySgList){
		// 收集5分时时彩两面总和大小
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCLMZHBIG.getTagType(), CaipiaoPlayTypeEnum.FIVESSCLMZHBIG.getTagCnName());
		// 收集5分时时彩第一球大小
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDYQBIG.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDYQBIG.getTagCnName());
		// 收集5分时时彩第二球大小
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDEQBIG.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDEQBIG.getTagCnName());
		// 收集5分时时彩第三球大小
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDSQBIG.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDSQBIG.getTagCnName());
		// 收集5分时时彩第四球大小
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDFQBIG.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDFQBIG.getTagCnName());
		// 收集5分时时彩第五球大小
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDWQBIG.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDWQBIG.getTagCnName());
	}
	
	/**
	 * @param 5分时时彩 获取单双长龙  
	 */
	private void getFivesscSigleAndDoubleLong(List<FivesscLotterySg> fivesscLotterySgList){
		// 收集5分时时彩两面总和单双
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCLMZHDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVESSCLMZHDOUBLE.getTagCnName());
		// 收集5分时时彩第一球单双
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDYQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDYQDOUBLE.getTagCnName());
		// 收集5分时时彩第二球单双
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDEQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDEQDOUBLE.getTagCnName());
		// 收集5分时时彩第三球单双
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDSQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDSQDOUBLE.getTagCnName());
		// 收集5分时时彩第四球单双
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDFQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDFQDOUBLE.getTagCnName());
		// 收集5分时时彩第五球单双
		this.getFivesscDragonInfo(fivesscLotterySgList, CaipiaoPlayTypeEnum.FIVESSCDWQDOUBLE.getTagType(), CaipiaoPlayTypeEnum.FIVESSCDWQDOUBLE.getTagCnName());
	}
	
	/** 
	* @Title: getDragonInfo 
	* @Description: 公共方法，获取长龙数据 
	* @author HANS
	* @date 2019年5月13日上午12:00:46
	*/ 
	private void getFivesscDragonInfo(List<FivesscLotterySg> fivesscLotterySgList, int type, String playType){
		try {
			if (!CollectionUtils.isEmpty(fivesscLotterySgList)) {
				// 标记变量
				Integer dragonSize = Constants.DEFAULT_INTEGER;
				Set<String> dragonSet = new HashSet<String>();

				for (int index = Constants.DEFAULT_INTEGER; index < fivesscLotterySgList.size() ; index++) {
					FivesscLotterySg fivesscLotterySg = fivesscLotterySgList.get(index);
					// 按照玩法计算结果
					String bigOrSmallName = this.calculateFivesscResult(type, fivesscLotterySg);
					
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
				if(dragonSize  >= Constants.DEFAULT_SIX) {
					Integer taskType = TaskTypeEnum.DRAGONPUSH.getKeyValue();
					String  caiPiaoType = CaipiaoTypeEnum.FIVESSC.getTagCnName();
					List<String> dragonList = new ArrayList<String>(dragonSet);
					String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
					taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, dragonSize);
				}
			}
		} catch (Exception e) {
			logger.error("获取5分时时彩长龙数据异常：",e);
		}
	}
	
	/** 
	* @Title: calculateResult 
	* @Description: 按照玩法计算结果 
	* @return String
	* @author HANS
	* @date 2019年5月13日上午10:33:30
	*/ 
	private String calculateFivesscResult(int type, FivesscLotterySg fivesscLotterySg) {
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
	 * PC蛋蛋统计长龙
	 */
	public void pceggTotalDragon(List<PceggLotterySg> pceggLotterySgList) {
		// 大/小标记变量
		Integer bigAndSmallDragonSize = Constants.DEFAULT_INTEGER;
		Set<String> bigAndSmallDragonSet = new HashSet<String>();
		boolean bigAllowFlag = true;
		// 单/双标记变量
		Integer sigleAndDoubleDragonSize = Constants.DEFAULT_INTEGER;
		Set<String> sigleAndDoubleDragonSet = new HashSet<String>();
		boolean sigleAllowFlag = true;
		
		for (int index = Constants.DEFAULT_INTEGER; index < pceggLotterySgList.size() ; index++) {
			// 如果不符合规则， 大/小和单/双都不再统计
			if(!bigAllowFlag && !sigleAllowFlag) {
				break;
			}
			PceggLotterySg pceggLotterySg = pceggLotterySgList.get(index);
			// 大/小
			String bigOrSmallName = AusactSgUtils.getPceegBigOrSmall(pceggLotterySg.getNumber());
			
			if(StringUtils.isEmpty(bigOrSmallName)) {
				bigAllowFlag = false;
			}
			// 把第一个结果加入SET集合
			if(index == Constants.DEFAULT_INTEGER) {
				if(bigAllowFlag) {
					bigAndSmallDragonSet.add(bigOrSmallName);
				}
			}
			// 单/双
			String singleAndDoubleName = AusactSgUtils.getSingleAndDouble(pceggLotterySg.getNumber());
			
			if(StringUtils.isEmpty(singleAndDoubleName)) {
				sigleAllowFlag = false;
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
			String playType = CaipiaoPlayTypeEnum.PCEGGBIG.getTagCnName();
			List<String> dragonList = new ArrayList<String>(bigAndSmallDragonSet);
			String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
			// 大小统计入库
			taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, bigAndSmallDragonSize);
		}
		
		if(sigleAndDoubleDragonSize >= Constants.DEFAULT_SIX) {
			String playType = CaipiaoPlayTypeEnum.PCEGGDOUBLE.getTagCnName();
			List<String> dragonList = new ArrayList<String>(sigleAndDoubleDragonSet);
			String dragonName = dragonList.get(Constants.DEFAULT_INTEGER);
			// 单双统计入库
			taskSgService.saveDragonPushTask(taskType, caiPiaoType, playType, dragonName, sigleAndDoubleDragonSize);
		}				

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
	
	/** 
	* @Title: getAlgorithmData 
	* @Description: 获取PC蛋蛋最近200期开奖数据 
	* @author HANS
	* @date 2019年5月12日下午7:58:30
	*/ 
	public List<PceggLotterySg> getAlgorithmData() {
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        return pceggLotterySgMapper.selectByExample(example);
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
	* @Title: selectFivebjpksByIssue 
	* @Description: 获取近期开奖数据 
	* @author HANS
	* @date 2019年5月17日下午11:14:49
	*/ 
	public List<FivebjpksLotterySg> selectFivebjpksByIssue() {
		FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
        FivebjpksLotterySgExample.Criteria fivebjpksCriteria = example.createCriteria();
        fivebjpksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        List<FivebjpksLotterySg> fivebjpksLotterySgList = fivebjpksLotterySgMapper.selectByExample(example);
		return fivebjpksLotterySgList;
	}
	
    /** 
    * @Title: getXyftpksAlgorithmData 
    * @Description: 查询幸运飞艇近期开奖数据
    * @author HANS
    * @date 2019年5月29日下午10:26:51
    */ 
    private List<XyftLotterySg> getXyftpksAlgorithmData() {
        XyftLotterySgExample example = new XyftLotterySgExample();
        XyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(Constants.DEFAULT_INTEGER);
        example.setLimit(Constants.DEFAULT_ALGORITHM_PAGESIZE);  
        return xyftLotterySgMapper.selectByExample(example);
    }

}
