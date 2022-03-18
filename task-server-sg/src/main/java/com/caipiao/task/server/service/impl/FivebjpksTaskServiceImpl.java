package com.caipiao.task.server.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.caipiao.core.library.tool.BjpksUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.RandomUtil;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.service.FivebjpksTaskService;
import com.mapper.FivebjpksCountSgdsMapper;
import com.mapper.FivebjpksCountSgdxMapper;
import com.mapper.FivebjpksCountSglhMapper;
import com.mapper.FivebjpksKillNumberMapper;
import com.mapper.FivebjpksLotterySgMapper;
import com.mapper.FivebjpksRecommendMapper;

@Service
public class FivebjpksTaskServiceImpl implements FivebjpksTaskService {

	private static final Logger logger = LoggerFactory.getLogger(FivebjpksTaskServiceImpl.class);
	@Autowired
	private FivebjpksLotterySgMapper fivebjpksLotterySgMapper;
	@Autowired
	private FivebjpksCountSgdxMapper bjpksCountSgdxMapper;
	@Autowired
	private FivebjpksCountSgdsMapper bjpksCountSgdsMapper;
	@Autowired
	private FivebjpksCountSglhMapper bjpksCountSglhMapper;
	@Autowired
	private FivebjpksRecommendMapper bjpksRecommendMapper;
	@Autowired
	private FivebjpksKillNumberMapper bjpksKillNumberMapper;
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private KillOrderService killOrderService;
	@Autowired
	private FivebjpksTaskService fivebjpksTaskService;


	@Override
	@Transactional
	public void addFivebjpksPrevSg() {
		// 一天总期数
		int count = 288;
		// 获取当前赛果最后一期数据
		FivebjpksLotterySgExample sgExample = new FivebjpksLotterySgExample();
		sgExample.setOrderByClause("ideal_time desc");
		FivebjpksLotterySg lastSg = fivebjpksLotterySgMapper.selectOneByExample(sgExample);

		// 获取理想开奖时间
		String idealTime = lastSg.getIdealTime();
		// 将字符串转化为Date
		Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);
		// 最后一期期号
		Integer issue = Integer.valueOf(lastSg.getIssue());
		FivebjpksLotterySg sg;
		String time = DateUtils.formatDate(dateTime, "HH:mm:ss");

		JSONArray jsonArray = new JSONArray();
		if (!"23:55:00".equals(time)) {
			while (!"23:55:00".equals(time)) {
				sg = new FivebjpksLotterySg();
				issue += 1;
				sg.setIssue(String.valueOf(issue));
				dateTime = this.nextIssueTime(dateTime);
				time = DateUtils.formatDate(dateTime, DateUtils.timePattern);
				sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

				FivebjpksLotterySg targetSg = new FivebjpksLotterySg();
				BeanUtils.copyProperties(sg,targetSg);
				jsonArray.add(targetSg);
				fivebjpksLotterySgMapper.insertSelective(sg);

			}
		}

		String date = idealTime.substring(0, 10);
		if (date.compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
			if(jsonArray.size() > 0){
				String jsonString = JSONObject.toJSONString(jsonArray);
				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_YUQI_DATA,"FivebjpksLotterySg:" + jsonString);
			}

			return;
		}

		for (int i = 0; i < count; i++) {
			sg = new FivebjpksLotterySg();
			issue += 1;
			sg.setIssue(String.valueOf(issue));
			dateTime = this.nextIssueTime(dateTime);
			sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

			FivebjpksLotterySg targetSg = new FivebjpksLotterySg();
			BeanUtils.copyProperties(sg,targetSg);
			jsonArray.add(targetSg);
			fivebjpksLotterySgMapper.insertSelective(sg);

		}
		String jsonString = JSONObject.toJSONString(jsonArray);
		jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_YUQI_DATA,"FivebjpksLotterySg:" + jsonString);

	}

	@Override
	public void addFivebjpksSg() {
		// 获取  最近第一期+ 本地近15期未开奖结果（1天内）
		FivebjpksLotterySgExample sgExample = new FivebjpksLotterySgExample();
		FivebjpksLotterySgExample.Criteria criteria = sgExample.createCriteria();
		sgExample.setOffset(0);
		sgExample.setLimit(1);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		sgExample.setOrderByClause("ideal_time desc");
		FivebjpksLotterySg fivebjpksLotterySg = fivebjpksLotterySgMapper.selectOneByExample(sgExample);

		sgExample = new FivebjpksLotterySgExample();
		criteria = sgExample.createCriteria();
		criteria.andIdealTimeLessThan(fivebjpksLotterySg.getIdealTime());
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		criteria.andOpenStatusEqualTo("WAIT");
		sgExample.setOffset(0);
		sgExample.setLimit(15);
		sgExample.setOrderByClause("ideal_time desc");
		List<FivebjpksLotterySg> sgFifteenList = fivebjpksLotterySgMapper.selectByExample(sgExample);

		List<FivebjpksLotterySg> sgList = new ArrayList<>();
		sgList.add(fivebjpksLotterySg);
		sgList.addAll(sgFifteenList);


		int i = 0;
		for (FivebjpksLotterySg sg : sgList) {
			i++;
			// 是否需要修改
			boolean bool = false, isPush = false;

			List<String> numberList = new ArrayList<String>();
			numberList.add("01");
			numberList.add("02");
			numberList.add("03");
			numberList.add("04");
			numberList.add("05");
			numberList.add("06");
			numberList.add("07");
			numberList.add("08");
			numberList.add("09");
			numberList.add("10");
			Collections.shuffle(numberList);
			String numberStr = "";

			String issue = "";
			// 判断是否需要修改赛果
			if (StringUtils.isBlank(sg.getNumber())) {
				numberStr=killOrderService.getBjpksKillNumber(sg.getIssue(),CaipiaoTypeEnum.FIVEPKS.getTagType());

				sg.setNumber(numberStr);
				sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
				sg.setOpenStatus("AUTO");
				bool = true;
				isPush = true;
				issue = sg.getIssue();
			}

			int count = 0;
			String jsonFivebjpksLotterySg = null;
			if (bool) {
				count = fivebjpksLotterySgMapper.updateByPrimaryKeySelective(sg);
				jsonFivebjpksLotterySg = JSON.toJSONString(sg).replace(":","$");
			}

			if (isPush && count > 0) {
				//检查预期数据
				checkFiveBjpksYuqiData();

				logger.info("【五分PK10】消息：FIVEBJPKS:{}，{}",issue,numberStr);
				// 将赛果推送到北京PK10相关队列
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_FIVEBJPKS,
//						"FIVEBJPKS:" + issue + ":" + numberStr);

				try{
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_LM,"FIVEBJPKS:" + issue + ":" + numberStr + ":" + jsonFivebjpksLotterySg);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_CMC_CQJ,"FIVEBJPKS:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_DS_CQJ,"FIVEBJPKS:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_DWD,"FIVEBJPKS:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_GYH,"FIVEBJPKS:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_UPDATE_DATA,"FIVEBJPKS:" + issue + ":" + numberStr);
				}catch (Exception e){
					logger.error("五分PK发送消息失败：{}",e);
				}

				JSONObject object = new JSONObject();
				JSONObject lottery = new JSONObject();
				object.put("issue",issue);
				object.put("number",numberStr);

				object.put("nextTime",DateUtils.parseDate(queryNextSg().getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
				object.put("nextIssue",queryNextSg().getIssue());

				Calendar startCal = Calendar.getInstance();
				startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
				startCal.set(Calendar.HOUR_OF_DAY,0);
				startCal.set(Calendar.MINUTE,0);
				startCal.set(Calendar.SECOND,0);

				long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
				int openCount = (int)(jiange/300) + 1;
				int noOpenCount = CaipiaoSumCountEnum.FIVEPKS.getSumCount() - openCount;

				object.put("openCount",openCount);
				object.put("noOpenCount",noOpenCount);
				lottery.put(CaipiaoTypeEnum.FIVEPKS.getTagType(),object);

				JSONObject objectAll = new JSONObject();
				objectAll.put("data",lottery);
				objectAll.put("status",1);
				objectAll.put("time",new Date().getTime()/1000);
				objectAll.put("info","成功");
				String jsonString = objectAll.toJSONString();

				try{
					//每次只发送最新一条
					if(i == 1){
						logger.info("TOPIC_FIVEBJPKS发送消息：{}",jsonString);
						jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS,jsonString);
					}
				}catch (Exception e){
					logger.error("五分PK发送消息失败：{}",e);
				}

			}
		}
	}

	//检查预期数据
	public void checkFiveBjpksYuqiData(){
		try{
			//检查预期数据任务开始
			FivebjpksLotterySgExample sgExample = new FivebjpksLotterySgExample();
			FivebjpksLotterySgExample.Criteria criteria = sgExample.createCriteria();
			criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
			sgExample.setOrderByClause("`ideal_time` asc");
			int afterCount = fivebjpksLotterySgMapper.countByExample(sgExample);
			if(afterCount < 10){  //当预期数据少于10，则跑一下预期数据任务
				fivebjpksTaskService.addFivebjpksPrevSg();
			}
			//检查预期数据任务结束
		}catch (Exception e){
			logger.error("五分PK检查预期数据失败：{}",e);
		}
	}

	@Override
	@Transactional
	public void addFivebjpksRecommend() {
		// 获取当前最后一期【免费推荐】数据
		FivebjpksRecommendExample recommendExample = new FivebjpksRecommendExample();
		recommendExample.setOrderByClause("`issue` desc");
		FivebjpksRecommend lastRecommend = bjpksRecommendMapper.selectOneByExample(recommendExample);

		// 获取下一期期号信息
		FivebjpksLotterySg nextSg = this.queryNextSg();

		// 查询遗漏数据
		List<FivebjpksLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

		// 判空
		if (CollectionUtils.isEmpty(sgList)) {
			return;
		}

		// 循环生成【免费推荐】数据
		FivebjpksRecommend recommend;
		for (int i = sgList.size() - 1; i >= 0; i--) {
			recommend = new FivebjpksRecommend();
			// 设置期号
			recommend.setIssue(sgList.get(i).getIssue());

			// 生成冠军号码推荐 5个
			StringBuilder builder1 = new StringBuilder();
			String numberStr1 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder1.append(numberStr1);
			// 根据号码生成大小|单双
			String[] str1 = numberStr1.split(",");
			builder1.append("|");
			builder1.append(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder1.append("|");
			builder1.append(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setFirst(builder1.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder2 = new StringBuilder();
			String numberStr2 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder2.append(numberStr2);
			// 根据号码生成大小|单双
			String[] str2 = numberStr2.split(",");
			builder2.append("|");
			builder2.append(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder2.append("|");
			builder2.append(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setSecond(builder2.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder3 = new StringBuilder();
			String numberStr3 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder3.append(numberStr3);
			// 根据号码生成大小|单双
			String[] str3 = numberStr3.split(",");
			builder3.append("|");
			builder3.append(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder3.append("|");
			builder3.append(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setThird(builder3.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder4 = new StringBuilder();
			String numberStr4 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder4.append(numberStr4);
			// 根据号码生成大小|单双
			String[] str4 = numberStr4.split(",");
			builder4.append("|");
			builder4.append(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder4.append("|");
			builder4.append(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setFourth(builder4.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder5 = new StringBuilder();
			String numberStr5 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder5.append(numberStr5);
			// 根据号码生成大小|单双
			String[] str5 = numberStr5.split(",");
			builder5.append("|");
			builder5.append(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder5.append("|");
			builder5.append(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setFifth(builder5.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder6 = new StringBuilder();
			String numberStr6 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder6.append(numberStr6);
			// 根据号码生成大小|单双
			String[] str6 = numberStr6.split(",");
			builder6.append("|");
			builder6.append(Integer.parseInt(str6[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder6.append("|");
			builder6.append(Integer.parseInt(str6[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setSixth(builder6.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder7 = new StringBuilder();
			String numberStr7 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder7.append(numberStr7);
			// 根据号码生成大小|单双
			String[] str7 = numberStr7.split(",");
			builder7.append("|");
			builder7.append(Integer.parseInt(str7[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder7.append("|");
			builder7.append(Integer.parseInt(str7[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setSeventh(builder7.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder8 = new StringBuilder();
			String numberStr8 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder8.append(numberStr8);
			// 根据号码生成大小|单双
			String[] str8 = numberStr8.split(",");
			builder8.append("|");
			builder8.append(Integer.parseInt(str8[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder8.append("|");
			builder8.append(Integer.parseInt(str8[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setEighth(builder8.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder9 = new StringBuilder();
			String numberStr9 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder9.append(numberStr9);
			// 根据号码生成大小|单双
			String[] str9 = numberStr9.split(",");
			builder9.append("|");
			builder9.append(Integer.parseInt(str9[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder9.append("|");
			builder9.append(Integer.parseInt(str9[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setNinth(builder9.toString());

			// 生成冠军号码推荐 5个
			StringBuilder builder10 = new StringBuilder();
			String numberStr10 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder10.append(numberStr10);
			// 根据号码生成大小|单双
			String[] str10 = numberStr10.split(",");
			builder10.append("|");
			builder10.append(Integer.parseInt(str10[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");
			builder10.append("|");
			builder10.append(Integer.parseInt(str10[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
			recommend.setTenth(builder10.toString());

			String numberStr11 = RandomUtil.getRandomStringNoSame(5, 3, 20);
			recommend.setFirstSecond(numberStr11);

			recommend.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
			// 保存生成数据
			bjpksRecommendMapper.insertSelective(recommend);
		}
	}

	@Override
	@Transactional
	public void addFivebjpksKillNumber() {
		// 查询当前最后一期【公式杀号】数据
		FivebjpksKillNumberExample killNumberExample = new FivebjpksKillNumberExample();
		killNumberExample.setOrderByClause("`issue` DESC");
		FivebjpksKillNumber killNumber = bjpksKillNumberMapper.selectOneByExample(killNumberExample);

		// 获取下一期期号信息
		FivebjpksLotterySg nextSg = this.queryNextSg();

		// 查询遗漏数据
		List<FivebjpksLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

		// 判空
		if (CollectionUtils.isEmpty(sgList)) {
			return;
		}

		// 生成杀号
		FivebjpksKillNumber nextKillNumber;
		for (int i = sgList.size() - 1; i >= 0; i--) {
			FivebjpksLotterySg sg = sgList.get(i);
			// 生成下一期杀号信息
			nextKillNumber = BjpksUtils.getFiveKillNumber(sg.getIssue());
			bjpksKillNumberMapper.insertSelective(nextKillNumber);
		}
	}

	@Override
	@Transactional
	public void addFivebjpksSgCount() {
		String date = DateUtils.formatDate(DateUtils.getDayAfter(new Date(), -1L), DateUtils.datePattern);

		// 获取昨天的赛果记录
		FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
		FivebjpksLotterySgExample.Criteria criteria = example.createCriteria();
		criteria.andTimeLike(date + "%");
		List<FivebjpksLotterySg> bjpksLotterySgs = this.fivebjpksLotterySgMapper.selectByExample(example);

		if (bjpksLotterySgs != null && bjpksLotterySgs.size() > 0) {
			// 对赛果进行统计
			FivebjpksCountSgdx countSgdx = BjpksUtils.countSgDxFive(bjpksLotterySgs);
			FivebjpksCountSgds bjpksCountSgds = BjpksUtils.countSgDsFive(bjpksLotterySgs);
			FivebjpksCountSglh bjpksCountSglh = BjpksUtils.countSgLhFive(bjpksLotterySgs);

			// 根据日期判断是否已录入数据库
			// 大小统计
			FivebjpksCountSgdxExample example1 = new FivebjpksCountSgdxExample();
			FivebjpksCountSgdxExample.Criteria criteria1 = example1.createCriteria();
			criteria1.andDateEqualTo(date);
			FivebjpksCountSgdx countSgdx1 = this.bjpksCountSgdxMapper.selectOneByExample(example1);
			if (countSgdx1 == null) {
				// 没有就录入
				this.bjpksCountSgdxMapper.insertSelective(countSgdx);
			} else {
				// 已经录入就更新
				this.bjpksCountSgdxMapper.updateByExample(countSgdx, example1);
			}

			// 单双统计
			FivebjpksCountSgdsExample example2 = new FivebjpksCountSgdsExample();
			FivebjpksCountSgdsExample.Criteria criteria2 = example2.createCriteria();
			criteria2.andDateEqualTo(date);
			FivebjpksCountSgds countSgds2 = this.bjpksCountSgdsMapper.selectOneByExample(example2);
			if (countSgds2 == null) {
				// 没有就录入
				this.bjpksCountSgdsMapper.insertSelective(bjpksCountSgds);
			} else {
				// 已经录入就更新
				this.bjpksCountSgdsMapper.updateByExample(bjpksCountSgds, example2);
			}

			// 龙虎统计
			FivebjpksCountSglhExample example3 = new FivebjpksCountSglhExample();
			FivebjpksCountSglhExample.Criteria criteria3 = example3.createCriteria();
			criteria3.andDateEqualTo(date);
			FivebjpksCountSglh countSglh3 = this.bjpksCountSglhMapper.selectOneByExample(example3);
			if (countSglh3 == null) {
				// 没有就录入
				this.bjpksCountSglhMapper.insertSelective(bjpksCountSglh);
			} else {
				// 已经录入就更新
				this.bjpksCountSglhMapper.updateByExample(bjpksCountSglh, example3);
			}
		}
	}

	/**
	 * 获取下一期官方开奖时间
	 *
	 * @param dateTime 当前期官方开奖时间
	 * @return
	 */
	private Date nextIssueTime(Date dateTime) {
		return DateUtils.getMinuteAfter(dateTime, 5);
	}

	/**
	 * 获取下一期期号信息
	 *
	 * @return
	 */
	private FivebjpksLotterySg queryNextSg() {
		FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
		FivebjpksLotterySgExample.Criteria criteria = example.createCriteria();
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
		example.setOrderByClause("`ideal_time` ASC");
		return fivebjpksLotterySgMapper.selectOneByExample(example);
	}

	/**
	 * 根据期号区间查询所有遗漏数据【近288期】
	 *
	 * @param startIssue 开始期号【不包括】
	 * @param endIssue   结束期号【包括】
	 * @return
	 */
	private List<FivebjpksLotterySg> queryOmittedData(String startIssue, String endIssue) {
		FivebjpksLotterySgExample example = new FivebjpksLotterySgExample();
		FivebjpksLotterySgExample.Criteria criteria = example.createCriteria();
		criteria.andIssueGreaterThan(startIssue);
//		criteria.andIssueLessThanOrEqualTo(endIssue);
		
		example.setOrderByClause("`ideal_time` DESC");
		example.setOffset(0);
		example.setLimit(288);
		return fivebjpksLotterySgMapper.selectByExample(example);
	}

}
