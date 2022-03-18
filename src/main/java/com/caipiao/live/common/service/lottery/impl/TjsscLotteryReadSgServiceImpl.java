package com.caipiao.live.common.service.lottery.impl;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.constant.LotteryInformationType;
import com.caipiao.live.common.constant.LotteryResultStatus;
import com.caipiao.live.common.constant.RedisKeys;
import com.caipiao.live.common.enums.AppMianParamEnum;
import com.caipiao.live.common.enums.StatusCode;
import com.caipiao.live.common.enums.lottery.CaipiaoRedisTimeEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoSumCountEnum;
import com.caipiao.live.common.enums.lottery.CaipiaoTypeEnum;
import com.caipiao.live.common.model.common.ResultInfo;
import com.caipiao.live.common.model.dto.result.TjsscMissDTO;
import com.caipiao.live.common.model.dto.result.TjsscMissNumDTO;
import com.caipiao.live.common.model.dto.result.TjsscSizeMissDTO;
import com.caipiao.live.common.mybatis.entity.TjsscLotterySg;
import com.caipiao.live.common.mybatis.entity.TjsscLotterySgExample;
import com.caipiao.live.common.mybatis.mapper.TjsscLotterySgMapper;
import com.caipiao.live.common.service.lottery.TjsscLotterySgServiceReadSg;
import com.caipiao.live.common.util.DateUtils;
import com.caipiao.live.common.util.DefaultResultUtil;
import com.caipiao.live.common.util.TimeHelper;
import com.caipiao.live.common.util.lottery.CaipiaoUtils;
import com.caipiao.live.common.util.lottery.TjsscUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 天津时时彩资讯查询
 *
 * @author lzy
 * @create 2018-07-28 11:07
 **/

@Service
public class TjsscLotteryReadSgServiceImpl implements TjsscLotterySgServiceReadSg {
	
	private static final Logger logger = LoggerFactory.getLogger(TjsscLotteryReadSgServiceImpl.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;
    
    
    @Override
    public List<TjsscLotterySg> getSgByDate(String date) {
    	
        if (StringUtils.isBlank(date)) {
            date = TimeHelper.date("yyyy-MM-dd");
        }
        date = date.replaceAll("-", "") + "%";
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        criteria.andIssueLike(date);
        example.setOrderByClause("ideal_time DESC");
        List<TjsscLotterySg> tjsscLotterySgs = tjsscLotterySgMapper.selectByExample(example);
        return tjsscLotterySgs;
    }    
    
    
	@Override
	public List<TjsscLotterySg> getSgByDatePageSize(String date, Integer pageNo, Integer pageSize) {
		TjsscLotterySgExample example = new TjsscLotterySgExample();
		TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        if(!org.springframework.util.StringUtils.isEmpty(date)) {
        	if (StringUtils.isBlank(date)) {
                date = TimeHelper.date("yyyy-MM-dd");
            }
            date = date.replaceAll("-", "") + "%";
            criteria.andIssueLike(date);
        }
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        example.setOffset((pageNo - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("ideal_time DESC");
        List<TjsscLotterySg> tjsscLotterySgs = tjsscLotterySgMapper.selectByExample(example);
        return tjsscLotterySgs;
	}

	
    @Override
    public List<TjsscLotterySg> getSgByIssues(Integer issues) {
        if (issues == null) {
            issues = 30;
        }
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria tjsscCriteria = example.createCriteria();
        tjsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(issues);
        example.setOrderByClause("ideal_time DESC");
        List<TjsscLotterySg> tjsscLotterySgs = tjsscLotterySgMapper.selectByExample(example);
        return tjsscLotterySgs;
    } 	
	
	
    @Override
    public ResultInfo<List<Map<String, Object>>> lishiKaiJiang(String type, Integer issues) {
        // 判断类型
        if (!LotteryInformationType.CQSSC_LSKJ_KJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        // 获取往期开奖结果
        List<TjsscLotterySg> tjsscLotterySgs = getSgByIssues(issues);
        // 封装数据
        List<Map<String, Object>> list = TjsscUtils.lishiKaiJiang(tjsscLotterySgs);
        return ResultInfo.ok(list);
    }	
	
    /**
     * 天津时时彩今日统计
     *
     * @param type 类型（校验）
     * @param date 日期, 如2018-08-21
     * @return
     */
    @Override
    public ResultInfo<Map<String, Object>> todayCount(String type, String date) {
        // 校验类型
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }

        List<TjsscLotterySg> tjsscLotterySgs = getSgByDate(date);
        if (tjsscLotterySgs == null || tjsscLotterySgs.size() == 0) {
            return ResultInfo.getInstance(null, StatusCode.NO_DATA);
        }
        Map<String, Object> map = TjsscUtils.todayCount(tjsscLotterySgs);
        return ResultInfo.ok(map);
    }	
		
    @Override
    public Map<String, Object> getNewestSgInfo() {
		Map<String, Object> result = DefaultResultUtil.getNullResult();
		try {
			// 缓存中取下一期信息
			String nextRedisKey = RedisKeys.TJSSC_NEXT_VALUE;
			Long redisTime = CaipiaoRedisTimeEnum.TJSSC.getRedisTime();
			TjsscLotterySg nextTjsscLotterySg = (TjsscLotterySg) redisTemplate.opsForValue().get(nextRedisKey);

			if (nextTjsscLotterySg == null) {
				nextTjsscLotterySg = this.getNextTjsscLotterySg();
				// 缓存到下期信息
				redisTemplate.opsForValue().set(nextRedisKey, nextTjsscLotterySg, redisTime, TimeUnit.MINUTES);
			}
			// 缓存中取开奖结果
			String redisKey = RedisKeys.TJSSC_RESULT_VALUE;
			TjsscLotterySg tjsscLotterySg = (TjsscLotterySg) redisTemplate.opsForValue().get(redisKey);

			if (tjsscLotterySg == null) {
				// 查询最近一期开奖信息
				tjsscLotterySg = this.getTjsscLotterySg();
				redisTemplate.opsForValue().set(redisKey, tjsscLotterySg);
			}

			if (nextTjsscLotterySg != null) {
				String nextIssue = nextTjsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL
						: nextTjsscLotterySg.getIssue();
				String txffnextIssue = tjsscLotterySg.getIssue() == null ? Constants.DEFAULT_NULL
						: tjsscLotterySg.getIssue();

				if (StringUtils.isNotBlank(nextIssue) && StringUtils.isNotBlank(txffnextIssue)) {
					Long nextIssueNum = Long.parseLong(nextIssue);
					Long txffnextIssueNum = Long.parseLong(txffnextIssue);
					Long differenceNum = nextIssueNum - txffnextIssueNum;

					if (differenceNum < 1 || differenceNum > 2) {
						nextTjsscLotterySg = this.getNextTjsscLotterySg();
						// 缓存到下期信息
						redisTemplate.opsForValue().set(nextRedisKey, nextTjsscLotterySg, redisTime, TimeUnit.MINUTES);
						// 查询最近一期开奖信息
						tjsscLotterySg = this.getTjsscLotterySg();
						redisTemplate.opsForValue().set(redisKey, tjsscLotterySg);
					}
				}
				if (tjsscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumber(tjsscLotterySg, result);
					// 计算开奖次数
					this.openCount(tjsscLotterySg, result);
				}

				if (nextTjsscLotterySg != null) {
					result.put(AppMianParamEnum.NEXTTIME.getParamEnName(),
							DateUtils.getTimeMillis(nextTjsscLotterySg.getIdealTime()) / 1000L);
					result.put(AppMianParamEnum.NEXTISSUE.getParamEnName(), nextTjsscLotterySg.getIssue());
				}

			} else {
				result = DefaultResultUtil.getNullResult();
				
				if (tjsscLotterySg != null) {
					// 组织开奖号码
					this.getIssueSumAndAllNumber(tjsscLotterySg, result);
					// 计算开奖次数
					this.openCount(tjsscLotterySg, result);
				}
			}
		} catch (Exception e) {
			logger.error("getNewestSgInfobyids:" + CaipiaoTypeEnum.TJSSC.getTagType() + " 异常： ", e);
			result = DefaultResultUtil.getNullResult();
		}
		return result;
	}	

    
    
    @Override
	public ResultInfo<List<Map<String, Object>>> todayData(String type, String date,Integer pageNo, Integer pageSize) {
    	 // 校验类型
        if (!LotteryInformationType.CQSSC_JRTJ.equals(type)) {
            return ResultInfo.getInstance(null, StatusCode.PARAM_ERROR);
        }
        List<TjsscLotterySg> tjsscLotterySgs = getSgByDatePageSize(date, pageNo, pageSize);
        List<Map<String, Object>> list = TjsscUtils.lishiKaiJiang(tjsscLotterySgs);
        return ResultInfo.ok(list);
	}    
	
    @Override
    public ResultInfo<List<TjsscMissDTO>> getTjsscMissCount(String date) {
        // 创建返回数据集合
        List<TjsscMissDTO> list = new ArrayList<>();
//        TjsscLotterySgExample
        // 根据条件查询赛果集合
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andDateEqualTo(date);
        criteria.andWanIsNotNull();
        List<TjsscLotterySg> tjsscLotterySgs = tjsscLotterySgMapper.selectByExample(example);

        TjsscMissDTO dto;
        int allCount, currCount;
        for (int i = 0; i < 10; i++) {
            dto = new TjsscMissDTO();
            currCount = allCount = 0;
            dto.setNumber(i);
            for (TjsscLotterySg sg : tjsscLotterySgs) {
                if (sg.getGe().equals(i)) {
                    allCount++;
                }
                if (sg.getShi().equals(i)) {
                    allCount++;
                }
                if (sg.getBai().equals(i)) {
                    allCount++;
                }
                if (sg.getQian().equals(i)) {
                    allCount++;
                }
                if (sg.getWan().equals(i)) {
                    allCount++;
                }

                if (sg.getGe().equals(i) || sg.getShi().equals(i) || sg.getBai().equals(i) || sg.getQian().equals(i) || sg.getWan().equals(i)) {
                    currCount = 0;
                } else {
                    currCount++;
                }
            }
            dto.setAllCount(allCount);
            dto.setCurrCount(currCount);
            list.add(dto);
        }
        return ResultInfo.ok(list);
    }
   
    private List<TjsscSizeMissDTO> countList(List<Integer> list) {
        List<TjsscSizeMissDTO> result = new ArrayList<>();
        for (Integer num : list) {
            Boolean found = false;
            for (TjsscSizeMissDTO dto : result) {
                if (dto.getMissValue().equals(num)) {
                    found = true;
                    dto.setMissCount(dto.getMissCount() + 1);
                    break;
                }
            }
            if (!found) {
                TjsscSizeMissDTO dto = new TjsscSizeMissDTO();
                dto.setMissValue(num);
                dto.setMissCount(1);
                result.add(dto);
            }
        }
        return result;
    }    
    
       
    /**
     * 大小遗漏统计
     *
     * @param count  近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @Override
    public Map<String, List<TjsscSizeMissDTO>> getTjsscSizeMissCount(Integer count, Integer number) {
        Map<String, List<TjsscSizeMissDTO>> result = new HashMap<>();

        // 获取开奖记录
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        Date dayAfter = DateUtils.getDayAfter(new Date(), -(count * 30L));
        String dateStr = DateUtils.formatDate(dayAfter, DateUtils.FORMAT_YYYY_MM_DD);
        criteria.andDateGreaterThan(dateStr);
        List<TjsscLotterySg> tjsscLotterySgs = tjsscLotterySgMapper.selectByExample(example);

        List<Integer> bigMissCount = new ArrayList<>();
        Integer bigCount = 0;
        List<Integer> smallMissCount = new ArrayList<>();
        Integer smallCount = 0;

        Iterator iter = tjsscLotterySgs.iterator();
        while (iter.hasNext()) {
            TjsscLotterySg sg = (TjsscLotterySg) iter.next();
            switch (number) {
                case 5:
                    if (sg.getGe() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 4:
                    if (sg.getShi() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 3:
                    if (sg.getBai() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                case 2:
                    if (sg.getQian() > 4) {
                        smallCount++;

                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;

                default:
                    if (sg.getWan() > 4) {
                        smallCount++;
                        if (bigCount != 0) {
                            bigMissCount.add(bigCount);
                        }
                        bigCount = 0;
                    } else {
                        bigCount++;

                        if (smallCount != 0) {
                            smallMissCount.add(smallCount);
                        }
                        smallCount = 0;
                    }
                    if (smallCount != 0 && !iter.hasNext()) {
                        smallMissCount.add(smallCount);
                    }
                    if (bigCount != 0 && !iter.hasNext()) {
                        bigMissCount.add(bigCount);
                    }
                    break;
            }
        }

        List<TjsscSizeMissDTO> bigList = this.countList(bigMissCount);
        Collections.sort(bigList);
        result.put("bigList", bigList);

        List<TjsscSizeMissDTO> smallList = this.countList(smallMissCount);
        Collections.sort(smallList);
        result.put("smallList", smallList);

        return result;
    }    
    
    
    /**
     * 单双遗漏统计
     *
     * @param count  近几月
     * @param number 球号（1 | 2 | 3 | 4 | 5）
     * @return
     */
    @Override
    public Map<String, List<TjsscSizeMissDTO>> getTjsscSingleMissCount(Integer count, Integer number) {
        Map<String, List<TjsscSizeMissDTO>> result = new HashMap<>();

        // 获取开奖记录
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        Date dayAfter = DateUtils.getDayAfter(new Date(), -(count * 30L));
        String dateStr = DateUtils.formatDate(dayAfter, DateUtils.FORMAT_YYYY_MM_DD);
        criteria.andDateGreaterThan(dateStr);
        example.setOrderByClause("`time` desc");
        List<TjsscLotterySg> tjsscLotterySgs = tjsscLotterySgMapper.selectByExample(example);

        List<Integer> singleMissCount = new ArrayList<>();
        Integer singleCount = 0;
        List<Integer> doubleMissCount = new ArrayList<>();
        Integer doubleCount = 0;

        Iterator iter = tjsscLotterySgs.iterator();
        while (iter.hasNext()) {
            TjsscLotterySg sg = (TjsscLotterySg) iter.next();
            switch (number) {
                case 5:
                    if (sg.getGe() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 4:
                    if (sg.getShi() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 3:
                    if (sg.getBai() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                case 2:
                    if (sg.getQian() % 2 == 1) {
                        doubleCount++;

                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;

                default:
                    if (sg.getWan() % 2 == 1) {
                        doubleCount++;
                        if (singleCount != 0) {
                            singleMissCount.add(singleCount);
                        }
                        singleCount = 0;
                    } else {
                        singleCount++;

                        if (doubleCount != 0) {
                            doubleMissCount.add(doubleCount);
                        }
                        doubleCount = 0;
                    }
                    if (doubleCount != 0 && !iter.hasNext()) {
                        doubleMissCount.add(doubleCount);
                    }
                    if (singleCount != 0 && !iter.hasNext()) {
                        singleMissCount.add(singleCount);
                    }
                    break;
            }
        }

        List<TjsscSizeMissDTO> singleList = this.countList(singleMissCount);
        // 排序
        Collections.sort(singleList);
        result.put("singleList", singleList);
        List<TjsscSizeMissDTO> doubleList = this.countList(doubleMissCount);
        // 排序
        Collections.sort(doubleList);
        result.put("doubleList", doubleList);

        return result;
    }

    /**
     * 统计指定号码连出数量
     *
     * @param list   遗漏列表
     * @param number 号码
     * @return
     */
    private Integer getContinuousValue(List<TjsscMissNumDTO> list, Integer number) {
        Integer max = 0, count = 0, first = 0;
        String beforeValue = "-1";
        for (TjsscMissNumDTO dto : list) {
            String realNum = dto.getNumber();
            // 判断是不是第一次出现，如果是第一次出现，count赋值为1
            if (first.equals(0) && realNum.contains(number.toString())) {
                first++;
                count = 1;
            }
            // 如果此次开奖号码为当前号码并且与上一次开奖号码一致，count ++
            if (realNum.contains(number.toString()) && realNum.contains(beforeValue)) {
                count++;
                beforeValue = number.toString();
            } else if (realNum.contains(number.toString())) {
                beforeValue = number.toString();
            } else {
                beforeValue = "-1";
                first = 0;
            }
            max = max > count ? max : count;
        }
        return max;
    }    
    
    
    
    /**
     * 计算【最大连出值】
     *
     * @param list
     * @return
     */
    private TjsscMissNumDTO countMaxContinuous(List<TjsscMissNumDTO> list) {
        TjsscMissNumDTO continuous = new TjsscMissNumDTO();
        continuous.setIssue("最大连出值");
        continuous.setMissing0(this.getContinuousValue(list, 0));
        continuous.setMissing1(this.getContinuousValue(list, 1));
        continuous.setMissing2(this.getContinuousValue(list, 2));
        continuous.setMissing3(this.getContinuousValue(list, 3));
        continuous.setMissing4(this.getContinuousValue(list, 4));
        continuous.setMissing5(this.getContinuousValue(list, 5));
        continuous.setMissing6(this.getContinuousValue(list, 6));
        continuous.setMissing7(this.getContinuousValue(list, 7));
        continuous.setMissing8(this.getContinuousValue(list, 8));
        continuous.setMissing9(this.getContinuousValue(list, 9));
        return continuous;
    }    
    
    /**
     * 计算平均遗漏值
     * 公式：总遗漏数/(总出现次数+1)
     *
     * @param size      统计期数
     * @param openCount 总出现次数
     * @return
     */
    private TjsscMissNumDTO countAvgMissValue(Integer size, TjsscMissNumDTO openCount) {
        TjsscMissNumDTO avgMissVal = new TjsscMissNumDTO();
        avgMissVal.setIssue("平均遗漏值");
        avgMissVal.setMissing0((size - openCount.getMissing0()) / (openCount.getMissing0() + 1));
        avgMissVal.setMissing1((size - openCount.getMissing1()) / (openCount.getMissing1() + 1));
        avgMissVal.setMissing2((size - openCount.getMissing2()) / (openCount.getMissing2() + 1));
        avgMissVal.setMissing3((size - openCount.getMissing3()) / (openCount.getMissing3() + 1));
        avgMissVal.setMissing4((size - openCount.getMissing4()) / (openCount.getMissing4() + 1));
        avgMissVal.setMissing5((size - openCount.getMissing5()) / (openCount.getMissing5() + 1));
        avgMissVal.setMissing6((size - openCount.getMissing6()) / (openCount.getMissing6() + 1));
        avgMissVal.setMissing7((size - openCount.getMissing7()) / (openCount.getMissing7() + 1));
        avgMissVal.setMissing8((size - openCount.getMissing8()) / (openCount.getMissing8() + 1));
        avgMissVal.setMissing9((size - openCount.getMissing9()) / (openCount.getMissing9() + 1));
        return avgMissVal;
    }    
    
    /**
     * 计算【最大遗漏值】
     *
     * @param list 统计集合
     * @return
     */
    private TjsscMissNumDTO countMaxMissValue(List<TjsscMissNumDTO> list) {
        TjsscMissNumDTO maxMissVal = new TjsscMissNumDTO();
        maxMissVal.setIssue("最大遗漏值");
        // 判空
        if (CollectionUtils.isEmpty(list)) {
            return maxMissVal;
        }
        // 定义变量
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0, num8 = 0, num9 = 0;
        // 遍历
        for (TjsscMissNumDTO dto : list) {
            num0 = num0 > dto.getMissing0() ? num0 : dto.getMissing0();
            num1 = num1 > dto.getMissing1() ? num1 : dto.getMissing1();
            num2 = num2 > dto.getMissing2() ? num2 : dto.getMissing2();
            num3 = num3 > dto.getMissing3() ? num3 : dto.getMissing3();
            num4 = num4 > dto.getMissing4() ? num4 : dto.getMissing4();
            num5 = num5 > dto.getMissing5() ? num5 : dto.getMissing5();
            num6 = num6 > dto.getMissing6() ? num6 : dto.getMissing6();
            num7 = num7 > dto.getMissing7() ? num7 : dto.getMissing7();
            num8 = num8 > dto.getMissing8() ? num8 : dto.getMissing8();
            num9 = num9 > dto.getMissing9() ? num9 : dto.getMissing9();
        }
        // 赋值
        maxMissVal.setMissing0(num0);
        maxMissVal.setMissing1(num1);
        maxMissVal.setMissing2(num2);
        maxMissVal.setMissing3(num3);
        maxMissVal.setMissing4(num4);
        maxMissVal.setMissing5(num5);
        maxMissVal.setMissing6(num6);
        maxMissVal.setMissing7(num7);
        maxMissVal.setMissing8(num8);
        maxMissVal.setMissing9(num9);
        return maxMissVal;
    }
    
    
    /**
     * 计算【总出现次数】
     *
     * @param list 统计集合
     * @return
     */
    private TjsscMissNumDTO countSumOpenCount(List<TjsscMissNumDTO> list) {
        TjsscMissNumDTO openCount = new TjsscMissNumDTO();
        openCount.setIssue("总出现次数");
        // 判空
        if (CollectionUtils.isEmpty(list)) {
            return openCount;
        }
        // 定义变量
        Integer num0 = 0, num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0, num8 = 0, num9 = 0;
        // 遍历
        for (TjsscMissNumDTO dto : list) {
            String openNumber = dto.getNumber();
            if (openNumber.contains("0")) {
                num0++;
            }
            if (openNumber.contains("1")) {
                num1++;
            }
            if (openNumber.contains("2")) {
                num2++;
            }
            if (openNumber.contains("3")) {
                num3++;
            }
            if (openNumber.contains("4")) {
                num4++;
            }
            if (openNumber.contains("5")) {
                num5++;
            }
            if (openNumber.contains("6")) {
                num6++;
            }
            if (openNumber.contains("7")) {
                num7++;
            }
            if (openNumber.contains("8")) {
                num8++;
            }
            if (openNumber.contains("9")) {
                num9++;
            }
        }
        // 赋值
        openCount.setMissing0(num0);
        openCount.setMissing1(num1);
        openCount.setMissing2(num2);
        openCount.setMissing3(num3);
        openCount.setMissing4(num4);
        openCount.setMissing5(num5);
        openCount.setMissing6(num6);
        openCount.setMissing7(num7);
        openCount.setMissing8(num8);
        openCount.setMissing9(num9);
        return openCount;
    }    
       
    /**
          * 天津时时彩：直选走势
     *
     * @param number 1:万位 2:千位  3:百位 4:十位 5:个位
     * @param limit  显示条数
     * @return
     */
    @Override
    public Map<String, Object> getTjsscTrend(Integer number, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        // 获取开奖记录
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria tjsscCriteria = example.createCriteria();
        tjsscCriteria.andWanIsNotNull();
        example.setOffset(0);
        example.setLimit(limit + 100);
        example.setOrderByClause("`time` desc");
        List<TjsscLotterySg> tjsscLotterySgs = tjsscLotterySgMapper.selectByExample(example);

        // 判空
        if (CollectionUtils.isEmpty(tjsscLotterySgs)) {
            return result;
        }

        // 定义遗漏值集合容器
        List<TjsscMissNumDTO> list = this.missValueByNum(number, tjsscLotterySgs, limit);

        // 定义统计集合容器
        List<TjsscMissNumDTO> statistics = new ArrayList<>();

        // 统计【总出现次数】
        TjsscMissNumDTO openCount = this.countSumOpenCount(list);

        // 统计【最大遗漏值】
        TjsscMissNumDTO maxMissVal = this.countMaxMissValue(list);

        // 统计【平均遗漏值】
        TjsscMissNumDTO avgMissVal = this.countAvgMissValue(limit, openCount);

        // 统计【最大连出值】
        TjsscMissNumDTO maxContinuous = this.countMaxContinuous(list);

        statistics.add(openCount);
        statistics.add(avgMissVal);
        statistics.add(maxMissVal);
        statistics.add(maxContinuous);

        result.put("list", list);
        result.put("statistics", statistics);

        return result;
    }    

    /**
     * 获取指定位置开奖号码
     *
     * @param number 指定位置 6 全部位数之和
     * @param sg     赛果
     * @return
     */
    private Integer getNumber(Integer number, TjsscLotterySg sg) {
        Integer sgNumber;
        switch (number) {
            case 1:
                sgNumber = sg.getWan();
                break;

            case 2:
                sgNumber = sg.getQian();
                break;

            case 3:
                sgNumber = sg.getBai();
                break;

            case 4:
                sgNumber = sg.getShi();
                break;

            case 5:
                sgNumber = sg.getGe();
                break;

            default:
                sgNumber = sg.getShi() + sg.getGe();
                break;
        }
        return sgNumber;
    }    
    
    /**
     * 计算指定号码每期的遗漏值
     *
     * @param i      每期的循环变量
     * @param list   统计集合
     * @param number 指定号码
     * @return
     */
    private Integer countMissNumber(int i, List<TjsscMissNumDTO> list, Integer number) {
        int count = 0;
        for (int j = i; j < list.size(); j++) {
        	TjsscMissNumDTO dto = list.get(j);
            String openNumber = dto.getNumber();
            if (j == i && openNumber.contains(number.toString())) {
                count = number;
                break;
            } else if (openNumber.contains(number.toString())) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }    
    
    /**
     * 计算遗漏值
     *
     * @param list 统计的集合
     */
    private void countMissValue(List<TjsscMissNumDTO> list) {
        for (int i = 0; i < list.size(); i++) {
        	TjsscMissNumDTO missNumDTO = list.get(i);
            missNumDTO.setMissing0(this.countMissNumber(i, list, 0));
            missNumDTO.setMissing1(this.countMissNumber(i, list, 1));
            missNumDTO.setMissing2(this.countMissNumber(i, list, 2));
            missNumDTO.setMissing3(this.countMissNumber(i, list, 3));
            missNumDTO.setMissing4(this.countMissNumber(i, list, 4));
            missNumDTO.setMissing5(this.countMissNumber(i, list, 5));
            missNumDTO.setMissing6(this.countMissNumber(i, list, 6));
            missNumDTO.setMissing7(this.countMissNumber(i, list, 7));
            missNumDTO.setMissing8(this.countMissNumber(i, list, 8));
            missNumDTO.setMissing9(this.countMissNumber(i, list, 9));
        }
    }    
    
    /**
     * 拼装开奖号码
     *
     * @param sg 赛果
     * @return
     */
    private String getOpenNumberStr(TjsscLotterySg sg) {
        StringBuilder sb = new StringBuilder();
        sb.append(sg.getWan());
        sb.append(",");
        sb.append(sg.getQian());
        sb.append(",");
        sb.append(sg.getBai());
        sb.append(",");
        sb.append(sg.getShi());
        sb.append(",");
        sb.append(sg.getGe());
        return sb.toString();
    }    
    
    /**
     * 获取指定位置的遗漏信息
     *
     * @param number     位置（1 万位，2 千位，3 百位，4 十位，5 个位）
     * @param lotterySgs 赛果集合
     * @return
     */
    private List<TjsscMissNumDTO> missValueByNum(Integer number, List<TjsscLotterySg> lotterySgs, Integer limit) {
        // 定义遗漏值集合容器
        List<TjsscMissNumDTO> dtos = new ArrayList<>();
        // 遍历赛果，获取期号及跨度值
        TjsscMissNumDTO dto;
        for (TjsscLotterySg sg : lotterySgs) {
            dto = new TjsscMissNumDTO();
            dto.setIssue(sg.getIssue());
            dto.setOpenNumber(this.getOpenNumberStr(sg));
            dto.setNumber(this.getNumber(number, sg).toString());
            dtos.add(dto);
        }
        // 计算【遗漏值列表】
        this.countMissValue(dtos);
        return dtos.subList(0, limit);
    }  
    
	/**
	 * @Title: getTjsscLotterySg
	 * @Description: 获取当前开奖数据
	 * @return TjsscLotterySg
	 * @author admin
	 * @date 2019年5月1日下午3:40:45
	 */
	public TjsscLotterySg getTjsscLotterySg() {
		TjsscLotterySgExample example = new TjsscLotterySgExample();
		TjsscLotterySgExample.Criteria tjsscCriteria = example.createCriteria();
		tjsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.AUTO);
		example.setOrderByClause("ideal_time DESC");
		TjsscLotterySg tjsscLotterySg = this.tjsscLotterySgMapper.selectOneByExample(example);
		return tjsscLotterySg;
	}

	/**
	 * @Title: openCount
	 * @Description: 计算开奖次数和未开奖次数
	 * @param
	 */
	public void openCount(TjsscLotterySg tjsscLotterySg, Map<String, Object> result) {
		// 计算开奖次数
		String issue = tjsscLotterySg.getIssue();
		String openNumString = issue.substring(8, issue.length());
		Integer openNumInteger = Integer.valueOf(openNumString);
		result.put("openCount", openNumInteger);
		// 计算剩余开奖次数
		Integer sumCount = CaipiaoSumCountEnum.TJSSC.getSumCount();
		result.put("noOpenCount", sumCount - openNumInteger);
	}

	/**
	 * @Title: getIssueSumAndAllNumber
	 * @Description: 组织开奖号码和合值
	 * @author admin
	 * @date 2019年4月21日下午2:31:55
	 */
	public void getIssueSumAndAllNumber(TjsscLotterySg tjsscLotterySg, Map<String, Object> result) {
		Integer wan = tjsscLotterySg.getWan();
		Integer qian = tjsscLotterySg.getQian();
		Integer bai = tjsscLotterySg.getBai();
		Integer shi = tjsscLotterySg.getShi();
		Integer ge = tjsscLotterySg.getGe();
		String issue = tjsscLotterySg.getIssue();
		result.put(AppMianParamEnum.ISSUE.getParamEnName(), issue);
		// 组织开奖号码
		String allNumberString = CaipiaoUtils.getAllIsuueNumber(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.NUMBER.getParamEnName(), allNumberString);
		// 计算开奖号码合值
		Integer sumInteger = CaipiaoUtils.getAllIsuueSum(wan, qian, bai, shi, ge);
		result.put(AppMianParamEnum.HE.getParamEnName(), sumInteger);
	}

	private TjsscLotterySg getNextTjsscLotterySg() {
		TjsscLotterySgExample example = new TjsscLotterySgExample();
		TjsscLotterySgExample.Criteria tqsscCriteria = example.createCriteria();
        tqsscCriteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        tqsscCriteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		example.setOrderByClause("ideal_time ASC");
		TjsscLotterySg next_TjsscLotterySg = this.tjsscLotterySgMapper.selectOneByExample(example);
		return next_TjsscLotterySg;
	}

	/** 
	* @Title: getTjsscAlgorithmData 
	* @Description: 获取近期天津时时彩开奖数据 
	* @author HANS
	* @date 2019年5月17日上午10:53:14
	*/ 
	public List<TjsscLotterySg> getTjsscAlgorithmData(){
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
