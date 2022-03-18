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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.service.OnelhcTaskService;
import com.mapper.OnelhcKillNumberMapper;
import com.mapper.OnelhcLotterySgMapper;

@Service
public class OnelhcTaskServiceImpl implements OnelhcTaskService {

	private static final Logger logger = LoggerFactory.getLogger(OnelhcTaskServiceImpl.class);

    @Autowired
    private OnelhcLotterySgMapper onelhcLotterySgMapper;
    @Autowired
    private OnelhcKillNumberMapper onelhcKillNumberMapper;
	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private KillOrderService killOrderService;
	@Autowired
	private OnelhcTaskService onelhcTaskService;

    /**
     * 录入预期赛果信息
     */
    @Override
    @Transactional
    public void addOnelhcPrevSg() {
		// 一天总期数
		int count = 1440;
		// 获取当前赛果最后一期数据
		OnelhcLotterySgExample sgExample = new OnelhcLotterySgExample();
		sgExample.setOrderByClause("ideal_time desc");
		OnelhcLotterySg lastSg = onelhcLotterySgMapper.selectOneByExample(sgExample);



		// 获取理想开奖时间
		String idealTime = lastSg.getIdealTime();
		// 将字符串转化为Date
		Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);
		// 最后一期期号
		Integer issue = Integer.valueOf(lastSg.getIssue());
		OnelhcLotterySg sg;
		String time = DateUtils.formatDate(dateTime, "HH:mm:ss");

		JSONArray jsonArray = new JSONArray();
		if (!"23:59:00".equals(time)) {
			List<OnelhcLotterySg> sgList = new ArrayList<>();
			while (!"23:59:00".equals(time)) {
				sg = new OnelhcLotterySg();
				issue += 1;
				sg.setIssue(String.valueOf(issue));
				dateTime = this.nextIssueTime(dateTime);
				time = DateUtils.formatDate(dateTime, DateUtils.timePattern);
				sg.setDate(DateUtils.formatDate(dateTime, DateUtils.datePattern));
				sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
				sg.setOpenStatus("WAIT");

				sg.setTime("");
				sg.setNumber("");
				sgList.add(sg);
				onelhcLotterySgMapper.insertSelective(sg);
				jsonArray.add(sg);
			}
			onelhcLotterySgMapper.insertBatch(sgList);
		}

		String date = idealTime.substring(0, 10);
		if (date.compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
			if(jsonArray.size() > 0){
				String jsonString = JSONObject.toJSONString(jsonArray);
				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_YUQI_DATA,"OnelhcLotterySg:" + jsonString);
			}
			return;
		}


		List<OnelhcLotterySg> sgList = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			sg = new OnelhcLotterySg();
			issue += 1;
			sg.setIssue(String.valueOf(issue));
			dateTime = this.nextIssueTime(dateTime);
			sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
			sg.setDate(DateUtils.formatDate(dateTime, DateUtils.datePattern));
			sg.setOpenStatus("WAIT");
			sg.setTime("");
			sg.setNumber("");
			sgList.add(sg);

			jsonArray.add(sg);
		}

		onelhcLotterySgMapper.insertBatch(sgList);
		String jsonString = JSONObject.toJSONString(jsonArray);
		jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_YUQI_DATA,"OnelhcLotterySg:" + jsonString);

	}

    /**
     * 随机生成赛果
     */
    @Override
    public void addOnelhcSg() {
		// 获取  最近第一期+ 本地近15期未开奖结果（1天内）
		logger.info("【一分六合彩】消息查询开始========================：");
		OnelhcLotterySgExample sgExample = new OnelhcLotterySgExample();
		OnelhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
		sgExample.setOffset(0);
		sgExample.setLimit(1);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		sgExample.setOrderByClause("ideal_time desc");
		OnelhcLotterySg onelhcLotterySg = onelhcLotterySgMapper.selectOneByExample(sgExample);

		sgExample = new OnelhcLotterySgExample();
		criteria = sgExample.createCriteria();
		criteria.andIdealTimeLessThan(onelhcLotterySg.getIdealTime());
		criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
		criteria.andOpenStatusEqualTo("WAIT");
		sgExample.setOffset(0);
		sgExample.setLimit(15);
		sgExample.setOrderByClause("ideal_time desc");
		List<OnelhcLotterySg> sgFifteenList = onelhcLotterySgMapper.selectByExample(sgExample);

		List<OnelhcLotterySg> sgList = new ArrayList<>();
		sgList.add(onelhcLotterySg);
		sgList.addAll(sgFifteenList);
		logger.info("【一分六合彩】消息查询结束：");

		int i = 0;
		for (OnelhcLotterySg sg : sgList) {
			i++;
			// 是否需要修改
			boolean bool = false, isPush = false;

			List<String> numberList = new ArrayList<String>();
			for(int j = 1; j <= 49; j++){
				numberList.add(j<10?("0"+j):String.valueOf(j));
			}
			Collections.shuffle(numberList);
			String numberStr = "";

			logger.info("【一分六合彩】消息杀号开始：");
			String issue = "";
			// 判断是否需要修改赛果
			if (StringUtils.isBlank(sg.getNumber())) {
				numberStr=killOrderService.getLhcKillNumber(sg.getIssue(),CaipiaoTypeEnum.ONELHC.getTagType());
				sg.setNumber(numberStr);
				sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
				sg.setOpenStatus("AUTO");
				bool = true;
				isPush = true;
				issue = sg.getIssue();
			}
			logger.info("【一分六合彩】消息杀号结束：");
			int count = 0;
			String jsonOnelhcLotterySg = null;
			if (bool) {
				count = onelhcLotterySgMapper.updateByPrimaryKeySelective(sg);
				jsonOnelhcLotterySg = JSON.toJSONString(sg).replace(":","$");
			}
			


			if (isPush && count > 0) {

				// 添加下一期的公式杀号数据
				addKillNumber(issue, numberStr);
				//检查预期数据
				checkOneLhcYuqiData();

				logger.info("【一分六合彩】消息：ONELHC:{},{}",issue,numberStr);
				// 将赛果推送到北京PK10相关队列
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_ONELHC,
//						"ONELHC:" + issue + ":" + numberStr);
				try {
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_TM_ZT_LX,"ONELHC:" + issue + ":" + numberStr + ":" + jsonOnelhcLotterySg);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_ZM_BB_WS,"ONELHC:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_LM_LX_LW,"ONELHC:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_BZ_LH_WX,"ONELHC:" + issue + ":" + numberStr);
					jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_PT_TX,"ONELHC:" + issue + ":" + numberStr);
				}catch (Exception e){
					logger.error("一分六合彩发送消息失败：{}",e);
				}

				// 将赛果推送到WenSocket相关队列
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_RESULT_PUSH,
//						"ONELHC:" + issue + ":" + numberStr);
//				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_WEB_RESULT_PUSH,"ONELHC:" + issue + ":" + numberStr);

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

				object.put("nextTime",nextIssueTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern)).getTime()/1000);
				object.put("nextIssue",String.valueOf(Long.valueOf(issue)+1));

				Calendar startCal = Calendar.getInstance();
				startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
				startCal.set(Calendar.HOUR_OF_DAY,0);
				startCal.set(Calendar.MINUTE,0);
				startCal.set(Calendar.SECOND,0);

				long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
				int openCount = (int)(jiange/60) + 1;
				int noOpenCount = CaipiaoSumCountEnum.ONELHC.getSumCount() - openCount;

				object.put("openCount",openCount);
				object.put("noOpenCount",noOpenCount);
				lottery.put(CaipiaoTypeEnum.ONELHC.getTagType(),object);


				JSONObject objectAll = new JSONObject();
				objectAll.put("data",lottery);
				objectAll.put("status",1);
				objectAll.put("time",new Date().getTime()/1000);
				objectAll.put("info","成功");
				String jsonString = objectAll.toJSONString();

				try{
					//每次只发送最新一条
					if(i == 1){
						logger.info("TOPIC_LHC_ONE发送消息：{}",jsonString);
						jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_LHC_ONE,jsonString);
					}
				}catch (Exception e){
					logger.error("一分六合彩发送消息失败：{}",e);
				}

			}
		}
	}

	//检查预期数据
	public void checkOneLhcYuqiData(){
    	try{
			//检查预期数据任务开始
			OnelhcLotterySgExample sgExample = new OnelhcLotterySgExample();
			OnelhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
			criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
			sgExample.setOrderByClause("`ideal_time` asc");
			int afterCount = onelhcLotterySgMapper.countByExample(sgExample);
			if(afterCount < 10){  //当预期数据少于10，则跑一下预期数据任务
				onelhcTaskService.addOnelhcPrevSg();
			}
			//检查预期数据任务结束
		}catch (Exception e){
    		logger.error("一分六合彩检查预期数据出错：{}",e);
		}

	}

    public void addKillNumber(String issue, String number) {
    	try{
			//查询这期的杀号记录，并把开奖结果保存在这条数据上
			OnelhcKillNumberExample example = new OnelhcKillNumberExample();
			OnelhcKillNumberExample.Criteria criteria = example.createCriteria();
			criteria.andIssueEqualTo(issue);
			OnelhcKillNumber lhcKillNumber = new OnelhcKillNumber();
			lhcKillNumber.setNumber(number);
			this.onelhcKillNumberMapper.updateByExampleSelective(lhcKillNumber, example);


			// 下一期的期号
			issue = Integer.valueOf(issue) + 1 + "";


			//查询这期是否有杀号，如果没有则生成一条记录
			OnelhcKillNumberExample example2 = new OnelhcKillNumberExample();
			OnelhcKillNumberExample.Criteria criteria2 = example2.createCriteria();
			criteria2.andIssueEqualTo(issue);
			OnelhcKillNumber lhcKillNumber1 = this.onelhcKillNumberMapper.selectOneByExample(example2);
			if (lhcKillNumber1 == null) {
				LhcKillNumber kill = LhcUtils.getKillNumber(issue);

				OnelhcKillNumber killNumber = new OnelhcKillNumber();
				killNumber.setCreateTime(kill.getCreateTime());
				killNumber.setIssue(kill.getIssue());
				killNumber.setNumber(kill.getNumber());
				killNumber.setTema(kill.getTema());
				killNumber.setZhengma(kill.getZhengma());

				this.onelhcKillNumberMapper.insertSelective(killNumber);

				String jsonObject = JSON.toJSONString(killNumber);
				jsonObject = jsonObject.replace(":","$");
				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_KILL_DATA,"OnelhcKillNumber:" + jsonObject+":"+number);
			}
		}catch (Exception e){
    		logger.error("一分六合彩公式杀号出错：{},{},{}",issue,number,e);
		}
    }

    

    /**
     * 获取下一期官方开奖时间
     *
     * @param lastTime 当前最后设定的一个官方开奖时间
     * @return 下一期官方开奖时间
     */
    private Date nextIssueTime(Date lastTime) {
        return DateUtils.getMinuteAfter(lastTime, 1);
    }

}
