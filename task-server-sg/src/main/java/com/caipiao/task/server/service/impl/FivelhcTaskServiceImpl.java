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
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.service.FivelhcTaskService;
import com.mapper.FivelhcKillNumberMapper;
import com.mapper.FivelhcLotterySgMapper;

@Service
public class FivelhcTaskServiceImpl implements FivelhcTaskService {

	private static final Logger logger = LoggerFactory.getLogger(FivelhcTaskServiceImpl.class);

    @Autowired
    private FivelhcLotterySgMapper fivelhcLotterySgMapper;
    @Autowired
    private FivelhcKillNumberMapper fivelhcKillNumberMapper;
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private KillOrderService killOrderService;
	@Autowired
	private FivelhcTaskService fivelhcTaskService;


	/**
     * 录入预期赛果信息
     */
    @Override
    @Transactional
    public void addFivelhcPrevSg() {
		// 一天总期数
		int count = 288;
		// 获取当前赛果最后一期数据
		FivelhcLotterySgExample sgExample = new FivelhcLotterySgExample();
		sgExample.setOrderByClause("ideal_time desc");
		FivelhcLotterySg lastSg = fivelhcLotterySgMapper.selectOneByExample(sgExample);

		// 获取理想开奖时间
		String idealTime = lastSg.getIdealTime();
		// 将字符串转化为Date
		Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);
		// 最后一期期号
		Integer issue = Integer.valueOf(lastSg.getIssue());
		FivelhcLotterySg sg;
		String time = DateUtils.formatDate(dateTime, "HH:mm:ss");

		JSONArray jsonArray = new JSONArray();
		if (!"23:55:00".equals(time)) {
			while (!"23:55:00".equals(time)) {
				sg = new FivelhcLotterySg();
				issue += 1;
				sg.setIssue(String.valueOf(issue));
				dateTime = this.nextIssueTime(dateTime);
				time = DateUtils.formatDate(dateTime, DateUtils.timePattern);
				sg.setDate(DateUtils.formatDate(dateTime, DateUtils.datePattern));
				sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

				FivelhcLotterySg targetSg = new FivelhcLotterySg();
				BeanUtils.copyProperties(sg,targetSg);
				jsonArray.add(targetSg);
				fivelhcLotterySgMapper.insertSelective(sg);
			}
		}

		String date = idealTime.substring(0, 10);
		if (date.compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
			if(jsonArray.size() > 0){
				String jsonString = JSONObject.toJSONString(jsonArray);
				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_YUQI_DATA,"FivelhcLotterySg:" + jsonString);
			}

			return;
		}

		for (int i = 0; i < count; i++) {
			sg = new FivelhcLotterySg();
			issue += 1;
			sg.setIssue(String.valueOf(issue));
			dateTime = this.nextIssueTime(dateTime);
			sg.setDate(DateUtils.formatDate(dateTime, DateUtils.datePattern));
			sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

			FivelhcLotterySg targetSg = new FivelhcLotterySg();
			BeanUtils.copyProperties(sg,targetSg);
			jsonArray.add(targetSg);
			fivelhcLotterySgMapper.insertSelective(sg);
		}

		String jsonString = JSONObject.toJSONString(jsonArray);
		jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_YUQI_DATA,"FivelhcLotterySg:" + jsonString);
	}

    /**
     * 随机生成赛果
     */
    @Override
    public void addFivelhcSg() {
		// 获取  最近第一期+ 本地近15期未开奖结果（1天内）
		FivelhcLotterySgExample sgExample = new FivelhcLotterySgExample();
		FivelhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
		criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
		sgExample.setOffset(0);
		sgExample.setLimit(1);
		sgExample.setOrderByClause("ideal_time desc");
		FivelhcLotterySg fivelhcLotterySg = fivelhcLotterySgMapper.selectOneByExample(sgExample);

		sgExample = new FivelhcLotterySgExample();
		criteria = sgExample.createCriteria();
		criteria.andIdealTimeLessThan(fivelhcLotterySg.getIdealTime());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		criteria.andOpenStatusEqualTo("WAIT");
		sgExample.setOffset(0);
		sgExample.setLimit(15);
		sgExample.setOrderByClause("ideal_time desc");
		List<FivelhcLotterySg> sgFifteenList = fivelhcLotterySgMapper.selectByExample(sgExample);

		List<FivelhcLotterySg> sgList = new ArrayList<>();
		sgList.add(fivelhcLotterySg);
		sgList.addAll(sgFifteenList);


		int i = 0;
		for (FivelhcLotterySg sg : sgList) {
			i++;
			// 是否需要修改
			boolean bool = false, isPush = false;

			List<String> numberList = new ArrayList<String>();
			for(int j = 1; j <= 49; j++){
				numberList.add(j<10?("0"+j):String.valueOf(j));
			}
			Collections.shuffle(numberList);
			String numberStr = "";

			String issue = "";
			// 判断是否需要修改赛果
			if (StringUtils.isBlank(sg.getNumber())) {
				numberStr=killOrderService.getLhcKillNumber(sg.getIssue(),CaipiaoTypeEnum.FIVELHC.getTagType());
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
				count = fivelhcLotterySgMapper.updateByPrimaryKeySelective(sg);
				jsonFivebjpksLotterySg = JSON.toJSONString(sg).replace(":","$");
			}

			if (isPush && count > 0) {
				// 添加下一期的公式杀号数据
				addKillNumber(issue, numberStr);

				//检查预期数据
				checkFiveLhcYuqiData();

				logger.info("【五分六合彩】消息：FIVELHC:{},{}",issue,numberStr);
				// 将赛果推送到北京PK10相关队列
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_FIVELHC,
//						"FIVELHC:" + issue + ":" + numberStr);

				try{
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_TM_ZT_LX,"FIVELHC:" + issue + ":" + numberStr + ":" + jsonFivebjpksLotterySg);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_ZM_BB_WS,"FIVELHC:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_LM_LX_LW,"FIVELHC:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_BZ_LH_WX,"FIVELHC:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_PT_TX,"FIVELHC:" + issue + ":" + numberStr);
				}catch (Exception e){
					logger.error("五分六合彩发送消息失败：{}",e);
				}


				// 将赛果推送到WenSocket相关队列
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_RESULT_PUSH,
//						"FIVELHC:" + issue + ":" + numberStr);
//				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_WEB_RESULT_PUSH,"AUSACT:" + issue + ":" + numberStr);

				JSONObject object = new JSONObject();
				JSONObject lottery = new JSONObject();

				object.put("issue",issue);
				object.put("number",numberStr);
				String sgArray[] = numberStr.split(",");
				String shengxiaoString = LhcUtils.getShengXiao(Integer.valueOf(sgArray[0]),sg.getIdealTime())+","+LhcUtils.getShengXiao(Integer.valueOf(sgArray[1]),sg.getIdealTime())
						+","+LhcUtils.getShengXiao(Integer.valueOf(sgArray[2]),sg.getIdealTime())+","+LhcUtils.getShengXiao(Integer.valueOf(sgArray[3]),sg.getIdealTime())
						+","+LhcUtils.getShengXiao(Integer.valueOf(sgArray[4]),sg.getIdealTime())+","+LhcUtils.getShengXiao(Integer.valueOf(sgArray[5]),sg.getIdealTime())
						+","+LhcUtils.getShengXiao(Integer.valueOf(sgArray[6]),sg.getIdealTime());
				object.put("shengxiao",shengxiaoString);

				object.put("nextIssue",String.valueOf(Long.valueOf(issue)+1));
				object.put("nextTime",nextIssueTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern)).getTime()/1000);

				Calendar startCal = Calendar.getInstance();
				startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
				startCal.set(Calendar.HOUR_OF_DAY,0);
				startCal.set(Calendar.MINUTE,0);
				startCal.set(Calendar.SECOND,0);

				long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
				int openCount = (int)(jiange/300) + 1;
				int noOpenCount = CaipiaoSumCountEnum.FIVELHC.getSumCount() - openCount;

				object.put("openCount",openCount);
				object.put("noOpenCount",noOpenCount);
				lottery.put(CaipiaoTypeEnum.FIVELHC.getTagType(),object);


				JSONObject objectAll = new JSONObject();
				objectAll.put("data",lottery);
				objectAll.put("status",1);
				objectAll.put("time",new Date().getTime()/1000);
				objectAll.put("info","成功");
				String jsonString = objectAll.toJSONString();

				try{
					//每次只发送最新一条
					if(i == 1){
						logger.info("TOPIC_LHC_FIVE发送消息：{}",jsonString);
						jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_LHC_FIVE,jsonString);
					}
				}catch (Exception e){
					logger.error("五分六合彩发送消息失败：{}",e);
				}

			}
		}
	}

	//检查预期数据
	public void checkFiveLhcYuqiData(){
		try{
			//检查预期数据任务开始
			FivelhcLotterySgExample sgExample = new FivelhcLotterySgExample();
			FivelhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
			criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
			sgExample.setOrderByClause("`ideal_time` asc");
			int afterCount = fivelhcLotterySgMapper.countByExample(sgExample);
			if(afterCount < 10){  //当预期数据少于10，则跑一下预期数据任务
				fivelhcTaskService.addFivelhcPrevSg();
			}
			//检查预期数据任务结束
		}catch (Exception e){
			logger.error("五分六合彩检查预期数据失败：{}",e);
		}

	}

    public void addKillNumber(String issue, String number) {
		try{
			//查询这期的杀号记录，并把开奖结果保存在这条数据上
			FivelhcKillNumberExample example = new FivelhcKillNumberExample();
			FivelhcKillNumberExample.Criteria criteria = example.createCriteria();
			criteria.andIssueEqualTo(issue);
			FivelhcKillNumber lhcKillNumber = new FivelhcKillNumber();
			lhcKillNumber.setNumber(number);
			this.fivelhcKillNumberMapper.updateByExampleSelective(lhcKillNumber, example);


			// 下一期的期号
			issue = Integer.valueOf(issue) + 1 + "";

			//查询这期是否有杀号，如果没有则生成一条记录
			FivelhcKillNumberExample example2 = new FivelhcKillNumberExample();
			FivelhcKillNumberExample.Criteria criteria2 = example2.createCriteria();
			criteria2.andIssueEqualTo(issue);
			FivelhcKillNumber lhcKillNumber1 = this.fivelhcKillNumberMapper.selectOneByExample(example2);
			if (lhcKillNumber1 == null) {
				LhcKillNumber kill = LhcUtils.getKillNumber(issue);

				FivelhcKillNumber killNumber = new FivelhcKillNumber();
				killNumber.setCreateTime(kill.getCreateTime());
				killNumber.setIssue(kill.getIssue());
				killNumber.setNumber(kill.getNumber());
				killNumber.setTema(kill.getTema());
				killNumber.setZhengma(kill.getZhengma());

				this.fivelhcKillNumberMapper.insertSelective(killNumber);

				String jsonObject = JSON.toJSONString(killNumber);
				jsonObject = jsonObject.replace(":","$");
				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_KILL_DATA,"FivelhcKillNumber:" + jsonObject+":"+number);
			}
		}catch (Exception e){
			logger.error("五分六合彩生成杀号失败：{}",e);
		}

    }

    

    /**
     * 获取下一期官方开奖时间
     *
     * @param lastTime 当前最后设定的一个官方开奖时间
     * @return 下一期官方开奖时间
     */
    private Date nextIssueTime(Date lastTime) {
        return DateUtils.getMinuteAfter(lastTime, 5);
    }

}
