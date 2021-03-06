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
import com.caipiao.task.server.service.TenbjpksTaskService;
import com.mapper.TenbjpksCountSgdsMapper;
import com.mapper.TenbjpksCountSgdxMapper;
import com.mapper.TenbjpksCountSglhMapper;
import com.mapper.TenbjpksKillNumberMapper;
import com.mapper.TenbjpksLotterySgMapper;
import com.mapper.TenbjpksRecommendMapper;

@Service
public class TenbjpksTaskServiceImpl implements TenbjpksTaskService {

	private static final Logger logger = LoggerFactory.getLogger(TenbjpksTaskServiceImpl.class);

	@Autowired
	private TenbjpksLotterySgMapper tenbjpksLotterySgMapper;
	@Autowired
	private TenbjpksCountSgdxMapper bjpksCountSgdxMapper;
	@Autowired
	private TenbjpksCountSgdsMapper bjpksCountSgdsMapper;
	@Autowired
	private TenbjpksCountSglhMapper bjpksCountSglhMapper;
	@Autowired
	private TenbjpksRecommendMapper bjpksRecommendMapper;
	@Autowired
	private TenbjpksKillNumberMapper bjpksKillNumberMapper;
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private KillOrderService killOrderService;
	@Autowired
	private TenbjpksTaskService tenbjpksTaskService;

	@Override
	@Transactional
	public void addTenbjpksPrevSg() {
		// ???????????????
		int count = 144;
		// ????????????????????????????????????
		TenbjpksLotterySgExample sgExample = new TenbjpksLotterySgExample();
		sgExample.setOrderByClause("ideal_time desc");
		TenbjpksLotterySg lastSg = tenbjpksLotterySgMapper.selectOneByExample(sgExample);

		// ????????????????????????
		String idealTime = lastSg.getIdealTime();
		// ?????????????????????Date
		Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);
		// ??????????????????
		Integer issue = Integer.valueOf(lastSg.getIssue());
		TenbjpksLotterySg sg;
		String time = DateUtils.formatDate(dateTime, "HH:mm:ss");

		JSONArray jsonArray = new JSONArray();
		if (!"23:50:00".equals(time)) {
			while (!"23:50:00".equals(time)) {
				sg = new TenbjpksLotterySg();
				issue += 1;
				sg.setIssue(String.valueOf(issue));
				dateTime = this.nextIssueTime(dateTime);
				time = DateUtils.formatDate(dateTime, DateUtils.timePattern);
				sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

				TenbjpksLotterySg targetSg = new TenbjpksLotterySg();
				BeanUtils.copyProperties(sg,targetSg);
				jsonArray.add(targetSg);
				tenbjpksLotterySgMapper.insertSelective(sg);

			}
		}

		String date = idealTime.substring(0, 10);
		if (date.compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
			if(jsonArray.size() > 0){
				String jsonString = JSONObject.toJSONString(jsonArray);
				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_YUQI_DATA,"TenbjpksLotterySg:" + jsonString);
			}

			return;
		}

		for (int i = 0; i < count; i++) {
			sg = new TenbjpksLotterySg();
			issue += 1;
			sg.setIssue(String.valueOf(issue));
			dateTime = this.nextIssueTime(dateTime);
			sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

			TenbjpksLotterySg targetSg = new TenbjpksLotterySg();
			BeanUtils.copyProperties(sg,targetSg);
			jsonArray.add(targetSg);
			tenbjpksLotterySgMapper.insertSelective(sg);

		}
		String jsonString = JSONObject.toJSONString(jsonArray);
		jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_YUQI_DATA,"TenbjpksLotterySg:" + jsonString);
	}

	@Override
	public void addTenbjpksSg() {
		// ??????  ???????????????+ ?????????15?????????????????????1?????????
		TenbjpksLotterySgExample sgExample = new TenbjpksLotterySgExample();
		TenbjpksLotterySgExample.Criteria criteria = sgExample.createCriteria();
		sgExample.setOffset(0);
		sgExample.setLimit(1);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		sgExample.setOrderByClause("ideal_time desc");
		TenbjpksLotterySg tenbjpksLotterySg = tenbjpksLotterySgMapper.selectOneByExample(sgExample);

		sgExample = new TenbjpksLotterySgExample();
		criteria = sgExample.createCriteria();
		criteria.andIdealTimeLessThan(tenbjpksLotterySg.getIdealTime());
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		criteria.andOpenStatusEqualTo("WAIT");
		sgExample.setOffset(0);
		sgExample.setLimit(15);
		sgExample.setOrderByClause("ideal_time desc");
		List<TenbjpksLotterySg> sgFifteenList = tenbjpksLotterySgMapper.selectByExample(sgExample);

		List<TenbjpksLotterySg> sgList = new ArrayList<>();
		sgList.add(tenbjpksLotterySg);
		sgList.addAll(sgFifteenList);

		int i = 0;
		for (TenbjpksLotterySg sg : sgList) {
			i++;
			// ??????????????????
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
			// ??????????????????????????????
			if (StringUtils.isBlank(sg.getNumber())) {
				numberStr=killOrderService.getBjpksKillNumber(sg.getIssue(),CaipiaoTypeEnum.TENPKS.getTagType());
				sg.setNumber(numberStr);
				sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
				sg.setOpenStatus("AUTO");
				bool = true;
				isPush = true;
				issue = sg.getIssue();
			}

			int count = 0;
			String jsontenbjpksLotterySg = null;
			if (bool) {
				count = tenbjpksLotterySgMapper.updateByPrimaryKeySelective(sg);
				jsontenbjpksLotterySg = JSON.toJSONString(sg).replace(":","$");
			}

			if (isPush && count > 0) {
				//??????????????????
				checkTenbjpksYuqiData();

				logger.info("?????????PK10????????????TENBJPKS:{},{}",issue,numberStr);
				// ????????????????????????PK10????????????
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_TENBJPKS,
//						"TENBJPKS:" + issue + ":" + numberStr);
				try{
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_LM,"TENBJPKS:" + issue + ":" + numberStr + ":" + jsontenbjpksLotterySg);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_CMC_CQJ,"TENBJPKS:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_DS_CQJ,"TENBJPKS:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_DWD,"TENBJPKS:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_GYH,"TENBJPKS:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_UPDATE_DATA,"TENBJPKS:" + issue + ":" + numberStr);
				}catch (Exception e){
					logger.error("??????PK?????????????????????{}",e);
				}

				JSONObject object = new JSONObject();
				JSONObject lottery = new JSONObject();
				object.put("issue",issue);
				object.put("number",numberStr);

				object.put("nextTime",nextIssueTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern)).getTime()/1000);
				object.put("nextIssue",queryNextSg().getIssue());

				Calendar startCal = Calendar.getInstance();
				startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
				startCal.set(Calendar.HOUR_OF_DAY,0);
				startCal.set(Calendar.MINUTE,0);
				startCal.set(Calendar.SECOND,0);

				long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
				int openCount = (int)(jiange/600) + 1;
				int noOpenCount = CaipiaoSumCountEnum.TENPKS.getSumCount() - openCount;

				object.put("openCount",openCount);
				object.put("noOpenCount",noOpenCount);
				lottery.put(CaipiaoTypeEnum.TENPKS.getTagType(),object);

				JSONObject objectAll = new JSONObject();
				objectAll.put("data",lottery);
				objectAll.put("status",1);
				objectAll.put("time",new Date().getTime()/1000);
				objectAll.put("info","??????");
				String jsonString = objectAll.toJSONString();

				try{
					//???????????????????????????
					if(i == 1){
						logger.info("TOPIC_TENBJPKS???????????????{}",jsonString);
						jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS,jsonString);
					}
				}catch (Exception e){
					logger.error("??????PK?????????????????????{}",e);
				}
			}
		}
	}

	//??????????????????
	public void checkTenbjpksYuqiData(){
		try{
			//??????????????????????????????
			TenbjpksLotterySgExample sgExample = new TenbjpksLotterySgExample();
			TenbjpksLotterySgExample.Criteria criteria = sgExample.createCriteria();
			criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
			sgExample.setOrderByClause("`ideal_time` asc");
			int afterCount = tenbjpksLotterySgMapper.countByExample(sgExample);
			if(afterCount < 10){  //?????????????????????10?????????????????????????????????
				tenbjpksTaskService.addTenbjpksPrevSg();
			}
			//??????????????????????????????
		}catch (Exception e){
			logger.error("??????PK???????????????????????????{}",e);
		}
	}



	@Override
	@Transactional
	public void addTenbjpksRecommend() {
		// ????????????????????????????????????????????????
		TenbjpksRecommendExample recommendExample = new TenbjpksRecommendExample();
		recommendExample.setOrderByClause("`issue` desc");
		TenbjpksRecommend lastRecommend = bjpksRecommendMapper.selectOneByExample(recommendExample);

		// ???????????????????????????
		TenbjpksLotterySg nextSg = this.queryNextSg();

		// ??????????????????
		List<TenbjpksLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

		// ??????
		if (CollectionUtils.isEmpty(sgList)) {
			return;
		}

		// ????????????????????????????????????
		TenbjpksRecommend recommend;
		for (int i = sgList.size() - 1; i >= 0; i--) {
			recommend = new TenbjpksRecommend();
			// ????????????
			recommend.setIssue(sgList.get(i).getIssue());

			// ???????????????????????? 5???
			StringBuilder builder1 = new StringBuilder();
			String numberStr1 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder1.append(numberStr1);
			// ????????????????????????|??????
			String[] str1 = numberStr1.split(",");
			builder1.append("|");
			builder1.append(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder1.append("|");
			builder1.append(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setFirst(builder1.toString());

			// ???????????????????????? 5???
			StringBuilder builder2 = new StringBuilder();
			String numberStr2 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder2.append(numberStr2);
			// ????????????????????????|??????
			String[] str2 = numberStr2.split(",");
			builder2.append("|");
			builder2.append(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder2.append("|");
			builder2.append(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setSecond(builder2.toString());

			// ???????????????????????? 5???
			StringBuilder builder3 = new StringBuilder();
			String numberStr3 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder3.append(numberStr3);
			// ????????????????????????|??????
			String[] str3 = numberStr3.split(",");
			builder3.append("|");
			builder3.append(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder3.append("|");
			builder3.append(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setThird(builder3.toString());

			// ???????????????????????? 5???
			StringBuilder builder4 = new StringBuilder();
			String numberStr4 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder4.append(numberStr4);
			// ????????????????????????|??????
			String[] str4 = numberStr4.split(",");
			builder4.append("|");
			builder4.append(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder4.append("|");
			builder4.append(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setFourth(builder4.toString());

			// ???????????????????????? 5???
			StringBuilder builder5 = new StringBuilder();
			String numberStr5 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder5.append(numberStr5);
			// ????????????????????????|??????
			String[] str5 = numberStr5.split(",");
			builder5.append("|");
			builder5.append(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder5.append("|");
			builder5.append(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setFifth(builder5.toString());

			// ???????????????????????? 5???
			StringBuilder builder6 = new StringBuilder();
			String numberStr6 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder6.append(numberStr6);
			// ????????????????????????|??????
			String[] str6 = numberStr6.split(",");
			builder6.append("|");
			builder6.append(Integer.parseInt(str6[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder6.append("|");
			builder6.append(Integer.parseInt(str6[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setSixth(builder6.toString());

			// ???????????????????????? 5???
			StringBuilder builder7 = new StringBuilder();
			String numberStr7 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder7.append(numberStr7);
			// ????????????????????????|??????
			String[] str7 = numberStr7.split(",");
			builder7.append("|");
			builder7.append(Integer.parseInt(str7[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder7.append("|");
			builder7.append(Integer.parseInt(str7[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setSeventh(builder7.toString());

			// ???????????????????????? 5???
			StringBuilder builder8 = new StringBuilder();
			String numberStr8 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder8.append(numberStr8);
			// ????????????????????????|??????
			String[] str8 = numberStr8.split(",");
			builder8.append("|");
			builder8.append(Integer.parseInt(str8[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder8.append("|");
			builder8.append(Integer.parseInt(str8[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setEighth(builder8.toString());

			// ???????????????????????? 5???
			StringBuilder builder9 = new StringBuilder();
			String numberStr9 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder9.append(numberStr9);
			// ????????????????????????|??????
			String[] str9 = numberStr9.split(",");
			builder9.append("|");
			builder9.append(Integer.parseInt(str9[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder9.append("|");
			builder9.append(Integer.parseInt(str9[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setNinth(builder9.toString());

			// ???????????????????????? 5???
			StringBuilder builder10 = new StringBuilder();
			String numberStr10 = RandomUtil.getRandomStringNoSame(5, 1, 11);
			builder10.append(numberStr10);
			// ????????????????????????|??????
			String[] str10 = numberStr10.split(",");
			builder10.append("|");
			builder10.append(Integer.parseInt(str10[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");
			builder10.append("|");
			builder10.append(Integer.parseInt(str10[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
			recommend.setTenth(builder10.toString());

			String numberStr11 = RandomUtil.getRandomStringNoSame(5, 3, 20);
			recommend.setFirstSecond(numberStr11);

			recommend.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
			// ??????????????????
			bjpksRecommendMapper.insertSelective(recommend);
		}
	}

	@Override
	@Transactional
	public void addTenbjpksKillNumber() {
		// ????????????????????????????????????????????????
		TenbjpksKillNumberExample killNumberExample = new TenbjpksKillNumberExample();
		killNumberExample.setOrderByClause("`issue` DESC");
		TenbjpksKillNumber killNumber = bjpksKillNumberMapper.selectOneByExample(killNumberExample);

		// ???????????????????????????
		TenbjpksLotterySg nextSg = this.queryNextSg();

		// ??????????????????
		List<TenbjpksLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

		// ??????
		if (CollectionUtils.isEmpty(sgList)) {
			return;
		}

		// ????????????
		TenbjpksKillNumber nextKillNumber;
		for (int i = sgList.size() - 1; i >= 0; i--) {
			TenbjpksLotterySg sg = sgList.get(i);
			// ???????????????????????????
			nextKillNumber = BjpksUtils.getTenKillNumber(sg.getIssue());
			bjpksKillNumberMapper.insertSelective(nextKillNumber);
		}
	}

	@Override
	@Transactional
	public void addTenbjpksSgCount() {
		String date = DateUtils.formatDate(DateUtils.getDayAfter(new Date(), -1L), DateUtils.datePattern);

		// ???????????????????????????
		TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
		TenbjpksLotterySgExample.Criteria criteria = example.createCriteria();
		criteria.andTimeLike(date + "%");
		List<TenbjpksLotterySg> bjpksLotterySgs = this.tenbjpksLotterySgMapper.selectByExample(example);

		if (bjpksLotterySgs != null && bjpksLotterySgs.size() > 0) {
			// ?????????????????????
			TenbjpksCountSgdx countSgdx = BjpksUtils.countSgDxTen(bjpksLotterySgs);
			TenbjpksCountSgds bjpksCountSgds = BjpksUtils.countSgDsTen(bjpksLotterySgs);
			TenbjpksCountSglh bjpksCountSglh = BjpksUtils.countSgLhTen(bjpksLotterySgs);

			// ??????????????????????????????????????????
			// ????????????
			TenbjpksCountSgdxExample example1 = new TenbjpksCountSgdxExample();
			TenbjpksCountSgdxExample.Criteria criteria1 = example1.createCriteria();
			criteria1.andDateEqualTo(date);
			TenbjpksCountSgdx countSgdx1 = this.bjpksCountSgdxMapper.selectOneByExample(example1);
			if (countSgdx1 == null) {
				// ???????????????
				this.bjpksCountSgdxMapper.insertSelective(countSgdx);
			} else {
				// ?????????????????????
				this.bjpksCountSgdxMapper.updateByExample(countSgdx, example1);
			}

			// ????????????
			TenbjpksCountSgdsExample example2 = new TenbjpksCountSgdsExample();
			TenbjpksCountSgdsExample.Criteria criteria2 = example2.createCriteria();
			criteria2.andDateEqualTo(date);
			TenbjpksCountSgds countSgds2 = this.bjpksCountSgdsMapper.selectOneByExample(example2);
			if (countSgds2 == null) {
				// ???????????????
				this.bjpksCountSgdsMapper.insertSelective(bjpksCountSgds);
			} else {
				// ?????????????????????
				this.bjpksCountSgdsMapper.updateByExample(bjpksCountSgds, example2);
			}

			// ????????????
			TenbjpksCountSglhExample example3 = new TenbjpksCountSglhExample();
			TenbjpksCountSglhExample.Criteria criteria3 = example3.createCriteria();
			criteria3.andDateEqualTo(date);
			TenbjpksCountSglh countSglh3 = this.bjpksCountSglhMapper.selectOneByExample(example3);
			if (countSglh3 == null) {
				// ???????????????
				this.bjpksCountSglhMapper.insertSelective(bjpksCountSglh);
			} else {
				// ?????????????????????
				this.bjpksCountSglhMapper.updateByExample(bjpksCountSglh, example3);
			}
		}
	}

	/**
	 * ?????????????????????????????????
	 *
	 * @param dateTime ???????????????????????????
	 * @return
	 */
	private Date nextIssueTime(Date dateTime) {
		return DateUtils.getMinuteAfter(dateTime, 10);
	}

	/**
	 * ???????????????????????????
	 *
	 * @return
	 */
	private TenbjpksLotterySg queryNextSg() {
		TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
		TenbjpksLotterySgExample.Criteria criteria = example.createCriteria();
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
		example.setOrderByClause("`ideal_time` ASC");
		return tenbjpksLotterySgMapper.selectOneByExample(example);
	}

	/**
	 * ????????????????????????????????????????????????144??????
	 *
	 * @param startIssue ???????????????????????????
	 * @param endIssue   ????????????????????????
	 * @return
	 */
	private List<TenbjpksLotterySg> queryOmittedData(String startIssue, String endIssue) {
		TenbjpksLotterySgExample example = new TenbjpksLotterySgExample();
		TenbjpksLotterySgExample.Criteria criteria = example.createCriteria();
		criteria.andIssueGreaterThan(startIssue);
//		criteria.andIssueLessThanOrEqualTo(endIssue);
		
		example.setOrderByClause("`ideal_time` DESC");
		example.setOffset(0);
		example.setLimit(144);
		return tenbjpksLotterySgMapper.selectByExample(example);
	}

}
