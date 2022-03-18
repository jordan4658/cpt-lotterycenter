package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.dto.lotterymanage.LotteryResultStatus;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.AmlhcTaskService;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.caipiao.task.server.util.GetHttpsgUtil;
import com.caipiao.task.server.util.Lauar;
import com.mapper.AmlhcLotterySgMapper;
import com.mapper.SslhcKillNumberMapper;
import com.mapper.domain.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

@Service
public class AmlhcTaskServiceImpl implements AmlhcTaskService {

    private static final Logger logger = LoggerFactory.getLogger(AmlhcTaskServiceImpl.class);
    @Autowired
    private AmlhcLotterySgMapper amlhcLotterySgMapper;
    @Autowired
    private SslhcKillNumberMapper sslhcKillNumberMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private KillOrderService killOrderService;
    @Autowired
    private AmlhcTaskService amlhcTaskService;

//    /**
//     * 录入预期赛果信息
//     */
//    @Override
//    @Transactional
//    public void addSslhcPrevSg() {
//		// 一天总期数
//		int count = 144;
//		// 获取当前赛果最后一期数据
//		SslhcLotterySgExample sgExample = new SslhcLotterySgExample();
//		sgExample.setOrderByClause("ideal_time desc");
//		SslhcLotterySg lastSg = sslhcLotterySgMapper.selectOneByExample(sgExample);
//
//		// 获取理想开奖时间
//		String idealTime = lastSg.getIdealTime();
//		// 将字符串转化为Date
//		Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);
//		// 最后一期期号
//		Integer issue = Integer.valueOf(lastSg.getIssue());
//		SslhcLotterySg sg;
//		String time = DateUtils.formatDate(dateTime, "HH:mm:ss");
//
//		JSONArray jsonArray = new JSONArray();
//		if (!"23:50:00".equals(time)) {
//			while (!"23:50:00".equals(time)) {
//				sg = new SslhcLotterySg();
//				issue += 1;
//				sg.setIssue(String.valueOf(issue));
//				dateTime = this.nextIssueTime(dateTime);
//				time = DateUtils.formatDate(dateTime, DateUtils.timePattern);
//				sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
//				sg.setDate(DateUtils.formatDate(dateTime, DateUtils.datePattern));
//
//				SslhcLotterySg targetSg = new SslhcLotterySg();
//				BeanUtils.copyProperties(sg,targetSg);
//				jsonArray.add(targetSg);
//				sslhcLotterySgMapper.insertSelective(sg);
//			}
//		}
//
//		String date = idealTime.substring(0, 10);
//		if (date.compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
//			if(jsonArray.size() > 0){
//				String jsonString = JSONObject.toJSONString(jsonArray);
//				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSLHC_YUQI_DATA,"SslhcLotterySg:" + jsonString);
//			}
//
//			return;
//		}
//
//		for (int i = 0; i < count; i++) {
//			sg = new SslhcLotterySg();
//			issue += 1;
//			sg.setIssue(String.valueOf(issue));
//			dateTime = this.nextIssueTime(dateTime);
//			sg.setDate(DateUtils.formatDate(dateTime, DateUtils.datePattern));
//			sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
//			sslhcLotterySgMapper.insertSelective(sg);
//
//			jsonArray.add(sg);
//		}
//
//		String jsonString = JSONObject.toJSONString(jsonArray);
//		jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSLHC_YUQI_DATA,"SslhcLotterySg:" + jsonString);
//	}


    @Override
    public void addAmlhcPrevSg() {
        try {
            AmlhcLotterySgExample example = null;
            AmlhcLotterySgExample.Criteria criteria = null;
            AmlhcLotterySg amlhcLotterySg = null;
            // [每周二、五、日开奖]
            // 以当前时间为基数创建当年所有的期数预期数据
            Date nowDate = new Date(System.currentTimeMillis());
            Date firstDayCurYear = DateUtils.getFirstDayCurYear(nowDate);
            String firstDayCurYearStr = DateUtils.getTimeString(firstDayCurYear, DateUtils.datePattern);
            // 加一年
            Date nextFirstDayCurYear = DateUtils.addOneYears(firstDayCurYear);
            String nextFirstDayCurYearStr = DateUtils.getTimeString(nextFirstDayCurYear, DateUtils.datePattern);

            // 获取当年数据
            example = new AmlhcLotterySgExample();
            criteria = example.createCriteria();
            criteria.andIdealTimeLike(firstDayCurYearStr.split("-")[0] + "%");
            example.setOrderByClause("ideal_time ASC");
            amlhcLotterySg = amlhcLotterySgMapper.selectOneByExample(example);
            // 如果没有查询到当年的数据就创建当年的预期数据
            if (null == amlhcLotterySg) {
                setOneYearsData(firstDayCurYear);
            }

            // 获取下一年数据
            example = new AmlhcLotterySgExample();
            criteria = example.createCriteria();
            criteria.andIdealTimeLike(nextFirstDayCurYearStr.split("-")[0] + "%");
            example.setOrderByClause("ideal_time ASC");
            amlhcLotterySg = amlhcLotterySgMapper.selectOneByExample(example);
            // 如果没有查询到下一年的数据就创建下一年的预期数据
            if (null == amlhcLotterySg) {
                setOneYearsData(nextFirstDayCurYear);
            }
        } catch (Exception e) {
            logger.error("体彩7星彩生成预期数据出错：{}", e);
        }
    }

    /**
     * 随机生成赛果
     */
    @Override
    public void addSslhcSg() {
        // 获取  最近第一期+ 本地近15期未开奖结果（1天内）
        AmlhcLotterySgExample sgExample = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-15);
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        sgExample.setOffset(0);
        sgExample.setLimit(1);
        sgExample.setOrderByClause("ideal_time desc");
        AmlhcLotterySg amlhcLotterySg = amlhcLotterySgMapper.selectOneByExample(sgExample);

        sgExample = new AmlhcLotterySgExample();
        criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThan(amlhcLotterySg.getIdealTime());
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        criteria.andOpenStatusEqualTo("WAIT");
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("ideal_time desc");
        List<AmlhcLotterySg> sgFifteenList = amlhcLotterySgMapper.selectByExample(sgExample);

        List<AmlhcLotterySg> sgList = new ArrayList<>();
        sgList.add(amlhcLotterySg);
        sgList.addAll(sgFifteenList);

        int i = 0;
        for (AmlhcLotterySg sg : sgList) {
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
                numberStr=killOrderService.getLhcKillNumber(sg.getIssue(),CaipiaoTypeEnum.AMLHC.getTagType());
                sg.setNumber(numberStr);
                sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
                sg.setOpenStatus("AUTO");
                bool = true;
                isPush = true;
                issue = sg.getIssue();
//                addKillNumber(issue, numberStr);
            }

            int count = 0;
            String jsonsslhcLotterySg = null;
            if (bool) {
                count = amlhcLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonsslhcLotterySg = JSON.toJSONString(sg).replace(":","$");
            }

            // 添加下一期的公式杀号数据QUEUE_SSLHC_TM_ZT_LX

            if (isPush && count > 0) {
                //检查预期数据
                checkSsLhcYuqiData();

                logger.info("【澳门六合彩】消息：SSLHC:{},{}",issue,numberStr);
                // 将赛果推送到北京PK10相关队列
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSLHC,
//						"SSLHC:" + issue + ":" + numberStr);
                try{
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_TM_ZT_LX,"AMLHC:" + issue + ":" + numberStr + ":" + jsonsslhcLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_ZM_BB_WS,"AMLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_LM_LX_LW,"AMLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_BZ_LH_WX,"AMLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_PT_TX,"AMLHC:" + issue + ":" + numberStr);
                }catch (Exception e){
                    logger.error("澳门六合彩发送消息失败：{}",e);
                }

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

                AmlhcLotterySg nextAmlhcLotterySg = getNextAmlhcLotterySg();
                if(null != nextAmlhcLotterySg){
                    String ideatime = nextAmlhcLotterySg.getIdealTime();
                    object.put("nextTime",DateUtils.getTimeMillis(ideatime) / 1000L);
                    object.put("nextIssue",nextAmlhcLotterySg.getIssue());
                }

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
                startCal.set(Calendar.HOUR_OF_DAY,0);
                startCal.set(Calendar.MINUTE,0);
                startCal.set(Calendar.SECOND,0);

                long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
                int openCount = (int)(jiange/24*3600) + 1;
                int noOpenCount = CaipiaoSumCountEnum.AMLHC.getSumCount() - openCount;

                object.put("openCount",openCount);
                object.put("noOpenCount",noOpenCount);
                lottery.put(CaipiaoTypeEnum.AMLHC.getTagType(),object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data",lottery);

                objectAll.put("status",1);
                objectAll.put("time",new Date().getTime()/1000);
                objectAll.put("info","成功");
                String jsonString = objectAll.toJSONString();

                try{
                    //每次只发送最新一条
                    if(i == 1){
                        logger.info("TOPIC_SS_LHC发送消息：{}",jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AM_LHC,jsonString);
                    }
                }catch (Exception e){
                    logger.error("澳门六合彩发送消息失败：{}",e);
                }

            }
        }
    }

    //检查预期数据
    public void checkSsLhcYuqiData() {
        try {
            //检查预期数据任务开始
            AmlhcLotterySgExample sgExample = new AmlhcLotterySgExample();
            AmlhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = amlhcLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
                amlhcTaskService.addAmlhcPrevSg();
            }
            //检查预期数据任务结束
        } catch (Exception e) {
            logger.error("澳门六合彩检查预期数据失败：{}", e);
        }
    }


//    public void addKillNumber(String issue, String number) {
//
//        //查询这期的杀号记录，并把开奖结果保存在这条数据上
//        SslhcKillNumberExample example = new SslhcKillNumberExample();
//        SslhcKillNumberExample.Criteria criteria = example.createCriteria();
//        criteria.andIssueEqualTo(issue);
//        SslhcKillNumber lhcKillNumber = new SslhcKillNumber();
//        lhcKillNumber.setNumber(number);
//        this.sslhcKillNumberMapper.updateByExampleSelective(lhcKillNumber, example);
//
//
//        // 下一期的期号
//        issue = Integer.valueOf(issue) + 1 + "";
//
//        //查询这期是否有杀号，如果没有则生成一条记录
//        SslhcKillNumberExample example2 = new SslhcKillNumberExample();
//        SslhcKillNumberExample.Criteria criteria2 = example2.createCriteria();
//        criteria2.andIssueEqualTo(issue);
//        SslhcKillNumber lhcKillNumber1 = this.sslhcKillNumberMapper.selectOneByExample(example2);
//        if (lhcKillNumber1 == null) {
//        	LhcKillNumber kill = LhcUtils.getKillNumber(issue);
//
//        	SslhcKillNumber killNumber = new SslhcKillNumber();
//        	killNumber.setCreateTime(kill.getCreateTime());
//        	killNumber.setIssue(kill.getIssue());
//        	killNumber.setNumber(kill.getNumber());
//        	killNumber.setTema(kill.getTema());
//        	killNumber.setZhengma(kill.getZhengma());
//
//            this.sslhcKillNumberMapper.insertSelective(killNumber);
//
//			String jsonObject = JSON.toJSONString(killNumber).replace(":","$");
//			jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_YUQI_DATA,"AmlhcKillNumber:" + jsonObject+":"+number);
//        }
//    }


    private void setOneYearsData(Date opearDate) {
        try {
            AmlhcLotterySg amlhcLotterySg;
            // 获取当年的天数总数
            int currntDays = DateUtils.getYearsDays(opearDate);
            // 记录期数
            int index = 1;
            JSONArray jsonArray = new JSONArray();
            for (int i = 1; i < currntDays + 1; i++) {
                Date currentDate = DateUtils.addDate("dd", i - 1, opearDate);
                String currentDateStr = DateUtils.getTimeString(currentDate, DateUtils.datePattern);

                // 设置时间
                String date = DateUtils.getTimeString(currentDate, DateUtils.datePattern);
                //获取年份
                String year = date.split("-")[0];
                //获取月份
                String month = date.split("-")[1];
                //获取日期
                String day = date.split("-")[2];

                //日期转换
                int yearN = Integer.parseInt(year);
                int monthN = Integer.parseInt(month);
                int dayN = Integer.parseInt(day);

                //判断是否是农历新年假期
                Lauar lauar = new Lauar(yearN, monthN, dayN);
                //农历日期（正月初二，正月初三，正月初四，正月初五，正月初六）
                String lunarDay = lauar.getLunarDay();
                //农历假期（除夕，春节）
                String lunarHoliday = lauar.getLunarHoliday();
//				//是否新年假期
//				if (Lauar.SpringHoliday.contains(lunarDay)) {
//					continue;
//				}
//				//是否新年假期
//				if (Lauar.SpringHoliday.contains(lunarHoliday)) {
//					continue;
//				}

                amlhcLotterySg = new AmlhcLotterySg();
                amlhcLotterySg.setOpenStatus("WAIT");
                amlhcLotterySg.setDate(date);
                // 设置期数
                String numIssue = String.valueOf(index);
                String issue = "";
                switch (numIssue.length()) {
                    case 1:
                        issue = "00" + numIssue;
                        break;
                    case 2:
                        issue = "0" + numIssue;
                        break;
                    case 3:
                        issue = numIssue;
                        break;
                }
                issue = year + issue;
                amlhcLotterySg.setIssue(issue);
                amlhcLotterySg.setIdealTime(date + " " + "21:30:00");
                amlhcLotterySgMapper.insertSelective(amlhcLotterySg);
                index++;

                jsonArray.add(amlhcLotterySg);
            }

            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_YUQI_DATA, "AmLhcLotterySg:" + jsonString);
            logger.info("");
        } catch (Exception e) {
            logger.error("澳门六合彩生成1年数据出错：{}", e);
        }
    }

    /**
     * 采集澳门六合彩彩数据  网址 https://macaujc.com/pc/#/
     * 接口：http://api.bjjfnet.com/data/opencode/2032
     */
    public List<LotterySgModel> collectionData() throws IOException {
        List<LotterySgModel> results = new ArrayList<>();
        String url = "https://api.bjjfnet.com/data/opencode/2032";
        String charset = "UTF-8";

        try {
            String jsonStr = GetHttpsgUtil.get(url, charset); // 得到JSON字符串
            if (org.apache.commons.lang3.StringUtils.isBlank(jsonStr)) {
                return null;
            }
            JSONObject json = JSONObject.parseObject(jsonStr); // 转化为JSON类
            JSONArray jsonArray = json.getJSONArray("data");
            LotterySgModel sgModel;
            // 遍历第三方开奖结果
            for (int i = 0; i < jsonArray.size(); i++) {
                Calendar calendar = Calendar.getInstance();
                sgModel = new LotterySgModel();
                // 获取价格
                JSONObject jsonSingle = JSONObject.parseObject(jsonArray.getString(i));
                String issue = jsonSingle.getString("issue");
                String openCode = jsonSingle.getString("openCode");
                String openTime = jsonSingle.getString("openTime");

//				"issue": "2020107",
//						"openCode": "28,30,37,11,23,45,05",
//						"openTime": "2020-04-16 21:34:40"

                sgModel.setSg(openCode);
                sgModel.setDate(openTime);
                sgModel.setIssue(issue);
                results.add(sgModel);
            }
        } catch (JSONException e) {
            logger.error("澳门六合彩获取赛果失败！", e);
        }

        return results;
    }

    /**
     * @Title: getNextSslhcLotterySg
     * @Description: 获取下期数据
     * @return SslhcLotterySg
     * @author HANS
     * @date 2019年4月29日下午9:14:27
     */
    private AmlhcLotterySg getNextAmlhcLotterySg() {
        AmlhcLotterySgExample nextExample = new AmlhcLotterySgExample();
        AmlhcLotterySgExample.Criteria criteria = nextExample.createCriteria();
        criteria.andOpenStatusEqualTo(LotteryResultStatus.WAIT);
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        nextExample.setOrderByClause("ideal_time ASC");
        AmlhcLotterySg nextTjsscLotterySg = this.amlhcLotterySgMapper.selectOneByExample(nextExample);
        return nextTjsscLotterySg;
    }
}
