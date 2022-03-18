package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.LotteryInformationType;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.StatusCode;
import com.caipiao.live.common.model.common.ResultInfo;

import com.caipiao.live.common.model.vo.BjpksSgVO;
import com.caipiao.live.common.model.vo.KjlsVO;
import com.caipiao.live.common.model.vo.MapIntegerVO;
import com.caipiao.live.common.model.vo.MapListVO;
import com.caipiao.live.common.model.vo.ThereIntegerVO;
import com.caipiao.live.common.model.vo.ThereMemberVO;
import com.caipiao.live.common.model.vo.lottery.ThereMemberListVO;
import com.caipiao.live.common.mybatis.entity.AuspksLotterySg;
import com.caipiao.live.common.mybatis.entity.AuspksLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.AuspksLotterySgMapper;
import com.caipiao.live.common.mybatis.mapperbean.AuspksBeanMapper;
import com.caipiao.live.common.service.lottery.AzPrixSgLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.StringUtils;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.AzPrixSgUtils;
import com.caipiao.live.common.util.lottery.BjpksUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AzPrixSgLotterySgServiceImpl 
 * @Description: 澳洲F1赛车资询服务类
 * @author: HANS
 * @date: 2019年6月24日 下午19:21:33  
 */
@Service
public class AzPrixSgLotteryReadSgServiceImpl implements AzPrixSgLotterySgServiceReadSg {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private AuspksLotterySgMapper auspksLotterySgMapper;
	@Autowired
	private AuspksBeanMapper auspksBeanMapper;
	
	/** 
	 * @Title: getNowIssueAndTime
	 * @Description: 澳洲F1赛车当前开奖数据
	 * @return 
	 */ 
	@Override
	public ResultInfo<Map<String, Object>> getNowIssueAndTime() {
		Map<String, Object> result = new HashMap<String, Object>();
		// 缓存中取开奖结果
		String nextRedisKey = RedisKeys.AUSPKS_NEXT_VALUE;
		AuspksLotterySg nextAuspksLotterySg = (AuspksLotterySg)redisTemplate.opsForValue().get(nextRedisKey);

		if(nextAuspksLotterySg == null) {
			nextAuspksLotterySg = this.queryAuspksLotterySgNextSg();
		}
					
        if(nextAuspksLotterySg != null) {
        	result.put("issue", nextAuspksLotterySg.getIssue());
            // 获取下期开奖时间
            result.put("time", DateUtils.getTimeMillis(nextAuspksLotterySg.getIdealTime()) / 1000L);
            return ResultInfo.ok(result);
        }
        return ResultInfo.ok(null);
	}
	
	/** 
	 * @Title: numNoOpen
	 * @Description: 获取号码遗漏
	 * @return 
	 */ 
	@Override
	public ResultInfo<List<MapListVO>> numNoOpen() {
		String date = TimeHelper.date("yyyy-MM-dd");
		List<String> sgs = auspksBeanMapper.selectNumberByDate(date + "%");
		List<MapListVO> listVOs = BjpksUtils.numNoOpen(sgs);
        return ResultInfo.ok(listVOs);
	}
	
	/** 
	 * @Title: todayNumber
	 * @Description: 获取今日号码资讯
	 * @return 
	 */ 
	@Override
	public ResultInfo<List<MapListVO>> todayNumber(String type) {
		List<MapListVO> voList = new ArrayList<MapListVO>();

		// 校验参数
		if (!LotteryInformationType.AUZPKS_JRHM.equals(type)) {
			return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
		}
		String date = TimeHelper.date("yyyy-MM-dd");
		List<String> sgs = auspksBeanMapper.selectNumberByDate(date + "%");
		// 判空
		if (CollectionUtils.isEmpty(sgs)) {
			return ResultInfo.ok(voList);
		}
		// 今日号码
		voList = BjpksUtils.todayNumber(sgs);
		return ResultInfo.ok(voList);
	}
	
	/** 
	 * @Title: historySg
	 * @Description: 历史开奖数据
	 * @return 
	 */ 
	@Override
	public ResultInfo<List<KjlsVO>> historySg(String type, String date, Integer pageNo, Integer pageSize) {
		AuspksLotterySgExample auspksExample = new AuspksLotterySgExample();
		AuspksLotterySgExample.Criteria auspksCriteria = auspksExample.createCriteria();
		auspksCriteria.andNumberIsNotNull();
		
        if (!StringUtils.isBlank(date)) {
        	auspksCriteria.andIdealTimeLike(date + "%");
        }
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        auspksExample.setOffset((pageNo - 1) * pageSize);
        auspksExample.setLimit(pageSize);
        auspksExample.setOrderByClause("ideal_time DESC");
		
        List<AuspksLotterySg> auspksLotterySgs = auspksLotterySgMapper.selectByExample(auspksExample);
		
        if (CollectionUtils.isEmpty(auspksLotterySgs)) {
        	auspksLotterySgs = new ArrayList<AuspksLotterySg>(0);
        }
        // 澳洲F1历史开奖
        if (LotteryInformationType.AUSPKS_LSKJ.equals(type)) {
            List<KjlsVO> result = AzPrixSgUtils.historySg(auspksLotterySgs);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
	}
	
	/** 
	 * @Title: historySg
	 * @Description: 开奖数据冷热分析
	 * @return 
	 */ 
	@Override
	public ResultInfo<List<MapListVO>> lengRe(String type, Integer issue) {
		if (issue == null) {
            issue = 20;
        }
        if (issue > 100) {
            issue = 100;
        }
        //澳洲F1冷热分析
        if (LotteryInformationType.AUZPKS_LRFX.equals(type)) {
            List<String> sg = auspksBeanMapper.selectNumberLimitDesc(issue);
            
            if (sg == null) {
                sg = new ArrayList<>(0);
            }
            List<MapListVO> listVOs = BjpksUtils.lengRe(sg);
            return ResultInfo.ok(listVOs);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
	}
	
	/**
	 * @Title: guanYaCount
	 * @Description: 获取冠亚和统计
	 * @return 
	 */ 
    @Override
    public ResultInfo<Map<String, List<ThereIntegerVO>>> guanYaCount() {
        String date = TimeHelper.date("yyyy-MM-dd");
        List<String> sgs = auspksBeanMapper.selectNumberByDate(date + "%");
        //澳洲F1冠亚和统计
        Map<String, List<ThereIntegerVO>> result = BjpksUtils.guanYaCount(sgs);
        return ResultInfo.ok(result);
    }
	
	/**
	 * @Title: liangMianC
	 * @Description: 获取两面长龙
	 * @return 
	 */ 
    @Override
    public ResultInfo<List<ThereMemberVO>> liangMianC(String type) {
        //澳洲F1两面长龙
        if (LotteryInformationType.AUZPKS_LMCL.equals(type)) {
        	String date = TimeHelper.date("yyyy-MM-dd");
        	List<String> sg = auspksBeanMapper.selectNumberByDate(date + "%");
        	
        	if (sg == null) {
                sg = new ArrayList<>(0);
            }
        	List<ThereMemberVO> listVOs = BjpksUtils.liangMianC(sg);
            return ResultInfo.ok(listVOs);       	
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }
	    
    /** 
     * @Title: luzhuG
     * @Description: 获取冠军和路珠
     * @return 
     */ 
    @Override
    public ResultInfo<Map<String, ThereMemberListVO>> luzhuG(String type, String date) {
    	if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        //澳洲F1冠军和路珠
        if (LotteryInformationType.AUZPKS_GJLZ.equals(type)) {
        	List<String> sg = auspksBeanMapper.selectNumberByDate(date + "%");
        	
        	if (sg == null) {
                sg = new ArrayList<>(0);
            }
            Map<String, ThereMemberListVO> result = BjpksUtils.luzhuG(sg);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }
    
    /** 
     * @Title: luzhuQ
     * @Description: 获取前后路珠 前后路珠371,两面路珠之大小372,之单双373,之龙虎374
     * @return 
     */ 
    @Override
    public ResultInfo<Map<String, ThereMemberListVO>> luzhuQ(String type, String date) {
    	if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
    	List<String> sg = auspksBeanMapper.selectNumberByDate(date + "%");
    	if (sg == null) {
            sg = new ArrayList<>(0);
        }
    	//澳洲F1前后路珠
        if (LotteryInformationType.AUZPKS_QHLZ.equals(type)) {
            Map<String, ThereMemberListVO> result = AzPrixSgUtils.luzhuQ(sg);
            return ResultInfo.ok(result);
        }
        //澳洲F1两面路珠之大小, 之单双, 之龙虎
        if (LotteryInformationType.AUZPKS_LMLZ_DX.equals(type) || LotteryInformationType.AUZPKS_LMLZ_DS.equals(type) || LotteryInformationType.AUZPKS_LMLZ_LH.equals(type)) {
            Map<String, ThereMemberListVO> result = AzPrixSgUtils.luzhuLiangMian(sg, type);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);    	
    }
    
    /** 
     * @Title: lianMianYl
     * @Description: 获取两面遗漏之大小,单双
     * @return 
     */ 
    @Override
    public ResultInfo<Map<String, ArrayList<MapIntegerVO>>> lianMianYl(String type, String way, Integer number) {
        if (number == null) {
            number = 0;
        } else if (number > 10 || number < 1) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        } else {
            number = number - 1;
        }
        int limit = 179 * 30;  // 一天开179期
        if ("3".equals(way)) {
            limit = 179 * 30 * 3 + 1;
        } else if ("6".equals(way)) {
            limit = 179 * 30 * 6 + 3;
        }
        List<String> sgList = auspksBeanMapper.selectNumberLimitDesc(limit);

        if (sgList == null) {
            sgList = new ArrayList<>(0);
        }
        //澳洲F1两面遗漏之大小
        if (LotteryInformationType.AUZPKS_LMYL_DX.equals(type)) {
            Map<String, ArrayList<MapIntegerVO>> result = BjpksUtils.noOpenLiangMianDx(sgList, number);
            return ResultInfo.ok(result);
        }

        //澳洲F1两面遗漏之单双
        if (LotteryInformationType.AUZPKS_LMYL_DS.equals(type)) {
            Map<String, ArrayList<MapIntegerVO>> result = BjpksUtils.noOpenLiangMianDs(sgList, number);
            return ResultInfo.ok(result);
        }
        return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
    }
    
    /** 
     * @Title: getSgTrend
     * @Description: 获取横版走势
     * @return 
     */ 
    @Override
    public ResultInfo<List<BjpksSgVO>> getSgTrend(Integer issue) {
        if (issue == null) {
            issue = 40;
        }
        List<BjpksSgVO> bjpksLotterySgs = auspksBeanMapper.selectLimitDesc(issue);
        return ResultInfo.ok(bjpksLotterySgs);
    }
	
	/** 
	* @Title: selectAuspksLotterySg 
	* @Description: 查询当前开奖数据 
	* @return AuspksLotterySg
	*/ 
	public AuspksLotterySg selectAuspksLotterySg() {
		AuspksLotterySgExample auspksExample = new AuspksLotterySgExample();
		AuspksLotterySgExample.Criteria auspksCriteria = auspksExample.createCriteria();
		auspksCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		auspksExample.setOrderByClause("ideal_time DESC");
		AuspksLotterySg auspksLotterySg = auspksLotterySgMapper.selectOneByExample(auspksExample);
		return auspksLotterySg;
	}

	/** 
	* @Title: queryAuspksLotterySgNextSg 
	* @Description: 获取下期数据
	* @return AuspksLotterySg
	* @author HANS
	* @date 2019年5月12日下午10:36:29
	*/ 
	public AuspksLotterySg queryAuspksLotterySgNextSg() {
		AuspksLotterySgExample auspksNextExample = new AuspksLotterySgExample();
		AuspksLotterySgExample.Criteria auspksNextCriteria = auspksNextExample.createCriteria();
        auspksNextCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
		auspksNextCriteria.andIdealTimeGreaterThan(DateUtils.getFullStringZeroSecond(new Date()));
		auspksNextExample.setOrderByClause("ideal_time ASC");
		AuspksLotterySg auspksNextLotterySg = auspksLotterySgMapper.selectOneByExample(auspksNextExample);
		return auspksNextLotterySg;
	}
}
