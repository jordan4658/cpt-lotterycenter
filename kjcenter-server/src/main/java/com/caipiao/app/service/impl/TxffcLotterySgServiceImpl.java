package com.caipiao.app.service.impl;
import com.caipiao.app.service.TxffcLotterySgService;
import com.caipiao.app.util.DefaultResultUtil;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.*;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.tool.CaipiaoUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.TxffcUtils;
import com.caipiao.core.library.utils.RedisKeys;
import com.mapper.TxffcCountSgdxdsMapper;
import com.mapper.TxffcKillNumberMapper;
import com.mapper.TxffcLotterySgMapper;
import com.mapper.TxffcRecommendMapper;
import com.mapper.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 比特币分分彩资讯查询
 *
 * @author lzy
 * @create 2018-07-28 11:07
 **/
@Service
public class TxffcLotterySgServiceImpl implements TxffcLotterySgService {

	private static final Logger logger = LoggerFactory.getLogger(TxffcLotterySgServiceImpl.class);
	@Autowired
	private RedisTemplate redisTemplate;
    @Autowired
    private TxffcLotterySgMapper txffcLotterySgMapper;
    @Autowired
    private TxffcRecommendMapper txffcRecommendMapper;
    @Autowired
    private TxffcKillNumberMapper txffcKillNumberMapper;
	@Autowired
	private TxffcCountSgdxdsMapper txffcCountSgdxdsMapper;

    @Override
    public Map<String, Object> getNewestSgInfo() {
    	Map<String, Object> result = DefaultResultUtil.getNullResult();
    	try {
    		// 缓存中取开奖结果
			String redisKey = RedisKeys.TXFFC_RESULT_VALUE;
			TxffcLotterySg txffcLotterySg = (TxffcLotterySg) redisTemplate.opsForValue().get(redisKey);

			if(txffcLotterySg == null) {
				txffcLotterySg = this.getTxffcLotterySg();
		        redisTemplate.opsForValue().set(redisKey, txffcLotterySg);
			}
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.TXFFC_NEXT_VALUE;
			TxffcLotterySg nextTxffcLotterySg = (TxffcLotterySg)redisTemplate.opsForValue().get(nextRedisKey);
			
			// 缓存到下期信息
			Long redisTime = CaipiaoRedisTimeEnum.TXFFC.getRedisTime();
			if(nextTxffcLotterySg == null) {
				nextTxffcLotterySg = this.getNextTxffcLotterySg();
				redisTemplate.opsForValue().set(nextRedisKey, nextTxffcLotterySg, redisTime, TimeUnit.SECONDS);
			}
			if(nextTxffcLotterySg != null) {
				String nextIssue = nextTxffcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : nextTxffcLotterySg.getIssue();
				String txffnextIssue = txffcLotterySg.getIssue() == null ? Constants.DEFAULT_NULL : txffcLotterySg.getIssue();
				
				if(StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					String nextIssueNumber = nextIssue.substring(9, nextIssue.length());
					String txffnextIssueNumber = txffnextIssue.substring(9, txffnextIssue.length());
					
					Long nextIssueNum = Long.parseLong(nextIssueNumber);
					Long txffnextIssueNum = Long.parseLong(txffnextIssueNumber);
					Long differenceNum = nextIssueNum - txffnextIssueNum;
					
					if(differenceNum < 1 || differenceNum > 2) {
				        nextTxffcLotterySg = this.getNextTxffcLotterySg();
				        redisTemplate.opsForValue().set(nextRedisKey, nextTxffcLotterySg, redisTime, TimeUnit.SECONDS);
				        // 重新获取当前开奖数据
				        txffcLotterySg = this.getTxffcLotterySg();
				        redisTemplate.opsForValue().set(redisKey, txffcLotterySg);
					}
				}
				if(txffcLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumber(txffcLotterySg, result);
//					// 计算开奖次数
//					this.openTxffcCount(txffcLotterySg, result);
				}
				
				if(nextTxffcLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(), DateUtils.getTimeMillis(nextTxffcLotterySg.getIdealTime()) / 1000L);
					//计算到下期时间和当前时间的距离（单位：秒）
					Long miao = (DateUtils.getTimeMillis(nextTxffcLotterySg.getIdealTime())-new Date().getTime())/1000;
					result.put("DAOJISHI", miao);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTxffcLotterySg.getIssue());
				}
			} else {
				if(txffcLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumber(txffcLotterySg, result);
//					// 计算开奖次数
//					this.openTxffcCount(txffcLotterySg, result);
				}
			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.TXFFC.getTagType() + "异常： " ,e);
			result = DefaultResultUtil.getNullResult();
		}
        return result;
    }

	@Override
	public Map<String, Object> getTxffcStasticSgInfo(String date) {
		Map<String, Object> result = new HashMap<>();
		try {
			TxffcCountSgdxdsExample example = new TxffcCountSgdxdsExample();
			TxffcCountSgdxdsExample.Criteria txffcCriteria = example.createCriteria();
			txffcCriteria.andDateGreaterThanOrEqualTo(DateUtils.parseDate(date,DateUtils.datePattern));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.parseDate(date,DateUtils.datePattern));
			calendar.add(Calendar.DAY_OF_MONTH,1);
			txffcCriteria.andDateLessThan(calendar.getTime());
			example.setOrderByClause("date DESC");
			TxffcCountSgdxds txffcCountSgdxds = txffcCountSgdxdsMapper.selectOneByExample(example);
			if(txffcCountSgdxds != null){
				result.put("one",txffcCountSgdxds.getOne());
				result.put("two",txffcCountSgdxds.getTwo());
				result.put("three",txffcCountSgdxds.getThree());
				result.put("four",txffcCountSgdxds.getFour());
				result.put("five",txffcCountSgdxds.getFive());
				result.put("six",txffcCountSgdxds.getSix());
				result.put("seven",txffcCountSgdxds.getSeven());
				result.put("eight",txffcCountSgdxds.getEight());
				result.put("nine",txffcCountSgdxds.getNigh());
				result.put("zero",txffcCountSgdxds.getTen());
				result.put("big",txffcCountSgdxds.getBig());
				result.put("small",txffcCountSgdxds.getSmall());
				result.put("odd",txffcCountSgdxds.getOdd());
				result.put("even",txffcCountSgdxds.getEven());
			}else{
				result.put("one",0);
				result.put("two",0);
				result.put("three",0);
				result.put("four",0);
				result.put("five",0);
				result.put("six",0);
				result.put("seven",0);
				result.put("eight",0);
				result.put("nine",0);
				result.put("zero",0);
				result.put("big",0);
				result.put("small",0);
				result.put("odd",0);
				result.put("even",0);
			}
			return result;
		} catch (Exception e) {
			logger.error("getTxffcStasticSgInfo:" + CaipiaoTypeEnum.TXFFC.getTagType() + "异常： " ,e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}

	@Override
	public  ResultInfo<Map<String, Object>> lishiSg(String date, Integer pageNum, Integer pageSize) {
		TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
		TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
		txffcCriteria.andWanIsNotNull();
		if (pageNum == null || pageNum < 1) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		txffcExample.setOffset((pageNum - 1) * pageSize);
		txffcExample.setLimit(pageSize);
		txffcExample.setOrderByClause("ideal_time DESC");
		txffcCriteria.andIdealTimeGreaterThanOrEqualTo(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.parseDate(date,DateUtils.datePattern));
		calendar.add(Calendar.DAY_OF_MONTH,1);
		txffcCriteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(),DateUtils.datePattern));
		int count = txffcLotterySgMapper.countByExample(txffcExample);
		List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(txffcExample);
		List<Map<String, Object>> maps = TxffcUtils.lishiSg(txffcLotterySgs);
		Map<String, Object> result = new HashMap<>();
		result.put("list", maps);
		result.put("pageNum", pageNum);
		result.put("pageSize", pageSize);
		result.put("totalSize",count);
		return ResultInfo.ok(result);
	}

	@Override
	public ResultInfo<Map<String, Object>> lishiSgLately(Integer pageNum, Integer pageSize) {
		TxffcLotterySgExample txffcExample = new TxffcLotterySgExample();
		TxffcLotterySgExample.Criteria txffcCriteria = txffcExample.createCriteria();
		txffcCriteria.andWanIsNotNull();
		if (pageNum == null || pageNum < 1) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		txffcExample.setOffset((pageNum - 1) * pageSize);
		txffcExample.setLimit(pageSize);
		txffcExample.setOrderByClause("ideal_time DESC");
		List<TxffcLotterySg> txffcLotterySgs = txffcLotterySgMapper.selectByExample(txffcExample);
		List<Map<String, Object>> maps = TxffcUtils.thirdLishiSg(txffcLotterySgs);
		Map<String, Object> result = new HashMap<>();
		result.put("list", maps);
		result.put("rows", maps.size());
		result.put("code", CaipiaoTypeEnum.BTBFFC.getTagEnName());
		return ResultInfo.ok(result);
	}

	/**
     * @Title: getTxffcLotterySg
     * @Description: 获取当前开奖数据
     * @return TxffcLotterySg
     * @author HANS
     * @date 2019年5月3日下午1:31:56
     */
    public TxffcLotterySg getTxffcLotterySg() {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
        example.setOrderByClause("ideal_time DESC");
        TxffcLotterySg txffcLotterySg = txffcLotterySgMapper.selectOneByExample(example);
        return txffcLotterySg;
    }

    /**
     * @Title: getTxffcLotterySg
     * @Description: 获取下期数据
     * @return TxffcLotterySg
     * @author HANS
     * @date 2019年4月29日下午10:03:35
     */
    public TxffcLotterySg getNextTxffcLotterySg() {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria txffcCriteria = example.createCriteria();
        txffcCriteria.andIdealTimeGreaterThan(DateUtils.getFullString(new Date()));
        txffcCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        example.setOrderByClause("ideal_time ASC");
        TxffcLotterySg nextTxffcLotterySg = txffcLotterySgMapper.selectOneByExample(example);
        return nextTxffcLotterySg;
    }

    /**
     * @Title: getIssueSumAndAllNumber
     * @Description: 组织开奖号码和合值
     */
    public void getIssueSumAndAllNumber(TxffcLotterySg txffcLotterySg, Map<String, Object> result) {
        Integer wan = txffcLotterySg.getWan();
        Integer qian = txffcLotterySg.getQian();
        Integer bai = txffcLotterySg.getBai();
        Integer shi = txffcLotterySg.getShi();
        Integer ge = txffcLotterySg.getGe();
        String issue = txffcLotterySg.getIssue();
        result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
        // 组织开奖号码
        String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
        result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);

        // 计算开奖号码合值
        Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
        result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);

		//计算和值大小单双，前三，中三，后三，斗牛，前中后
		String BigOrSmall = "小";
		String danOrShuang = "单";
		String qianSan = "";
		if(sumInteger >= 23){
			BigOrSmall = "大";
		}
		if(sumInteger%2==0){
			danOrShuang = "双";
		}
		int numQian[] = {wan,qian,bai};
		int numZhong[] = {qian,bai,shi};
		int numHou[] = {bai,shi,ge};
		String qianValue = TxffcUtils.getQzhValue(numQian);
		String zhongValue = TxffcUtils.getQzhValue(numZhong);
		String houValue = TxffcUtils.getQzhValue(numHou);
		String douniu = TxffcUtils.isWinByDN(allNumberString);
		result.put("calMessage", sumInteger+","+BigOrSmall+","+danOrShuang+","+qianValue+","+zhongValue+","+houValue+","+douniu);
    }

}
