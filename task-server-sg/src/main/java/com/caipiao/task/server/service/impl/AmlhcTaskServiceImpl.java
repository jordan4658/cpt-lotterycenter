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
//     * ????????????????????????
//     */
//    @Override
//    @Transactional
//    public void addSslhcPrevSg() {
//		// ???????????????
//		int count = 144;
//		// ????????????????????????????????????
//		SslhcLotterySgExample sgExample = new SslhcLotterySgExample();
//		sgExample.setOrderByClause("ideal_time desc");
//		SslhcLotterySg lastSg = sslhcLotterySgMapper.selectOneByExample(sgExample);
//
//		// ????????????????????????
//		String idealTime = lastSg.getIdealTime();
//		// ?????????????????????Date
//		Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);
//		// ??????????????????
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
            // [???????????????????????????]
            // ???????????????????????????????????????????????????????????????
            Date nowDate = new Date(System.currentTimeMillis());
            Date firstDayCurYear = DateUtils.getFirstDayCurYear(nowDate);
            String firstDayCurYearStr = DateUtils.getTimeString(firstDayCurYear, DateUtils.datePattern);
            // ?????????
            Date nextFirstDayCurYear = DateUtils.addOneYears(firstDayCurYear);
            String nextFirstDayCurYearStr = DateUtils.getTimeString(nextFirstDayCurYear, DateUtils.datePattern);

            // ??????????????????
            example = new AmlhcLotterySgExample();
            criteria = example.createCriteria();
            criteria.andIdealTimeLike(firstDayCurYearStr.split("-")[0] + "%");
            example.setOrderByClause("ideal_time ASC");
            amlhcLotterySg = amlhcLotterySgMapper.selectOneByExample(example);
            // ??????????????????????????????????????????????????????????????????
            if (null == amlhcLotterySg) {
                setOneYearsData(firstDayCurYear);
            }

            // ?????????????????????
            example = new AmlhcLotterySgExample();
            criteria = example.createCriteria();
            criteria.andIdealTimeLike(nextFirstDayCurYearStr.split("-")[0] + "%");
            example.setOrderByClause("ideal_time ASC");
            amlhcLotterySg = amlhcLotterySgMapper.selectOneByExample(example);
            // ????????????????????????????????????????????????????????????????????????
            if (null == amlhcLotterySg) {
                setOneYearsData(nextFirstDayCurYear);
            }
        } catch (Exception e) {
            logger.error("??????7?????????????????????????????????{}", e);
        }
    }

    /**
     * ??????????????????
     */
    @Override
    public void addSslhcSg() {
        // ??????  ???????????????+ ?????????15?????????????????????1?????????
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
            // ??????????????????
            boolean bool = false, isPush = false;

            List<String> numberList = new ArrayList<String>();
            for(int j = 1; j <= 49; j++){
                numberList.add(j<10?("0"+j):String.valueOf(j));
            }
            Collections.shuffle(numberList);
            String numberStr = "";

            String issue = "";
            // ??????????????????????????????
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

            // ????????????????????????????????????QUEUE_SSLHC_TM_ZT_LX

            if (isPush && count > 0) {
                //??????????????????
                checkSsLhcYuqiData();

                logger.info("??????????????????????????????SSLHC:{},{}",issue,numberStr);
                // ????????????????????????PK10????????????
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSLHC,
//						"SSLHC:" + issue + ":" + numberStr);
                try{
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_TM_ZT_LX,"AMLHC:" + issue + ":" + numberStr + ":" + jsonsslhcLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_ZM_BB_WS,"AMLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_LM_LX_LW,"AMLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_BZ_LH_WX,"AMLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_PT_TX,"AMLHC:" + issue + ":" + numberStr);
                }catch (Exception e){
                    logger.error("????????????????????????????????????{}",e);
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
                objectAll.put("info","??????");
                String jsonString = objectAll.toJSONString();

                try{
                    //???????????????????????????
                    if(i == 1){
                        logger.info("TOPIC_SS_LHC???????????????{}",jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AM_LHC,jsonString);
                    }
                }catch (Exception e){
                    logger.error("????????????????????????????????????{}",e);
                }

            }
        }
    }

    //??????????????????
    public void checkSsLhcYuqiData() {
        try {
            //??????????????????????????????
            AmlhcLotterySgExample sgExample = new AmlhcLotterySgExample();
            AmlhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = amlhcLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //?????????????????????10?????????????????????????????????
                amlhcTaskService.addAmlhcPrevSg();
            }
            //??????????????????????????????
        } catch (Exception e) {
            logger.error("??????????????????????????????????????????{}", e);
        }
    }


//    public void addKillNumber(String issue, String number) {
//
//        //????????????????????????????????????????????????????????????????????????
//        SslhcKillNumberExample example = new SslhcKillNumberExample();
//        SslhcKillNumberExample.Criteria criteria = example.createCriteria();
//        criteria.andIssueEqualTo(issue);
//        SslhcKillNumber lhcKillNumber = new SslhcKillNumber();
//        lhcKillNumber.setNumber(number);
//        this.sslhcKillNumberMapper.updateByExampleSelective(lhcKillNumber, example);
//
//
//        // ??????????????????
//        issue = Integer.valueOf(issue) + 1 + "";
//
//        //???????????????????????????????????????????????????????????????
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
            // ???????????????????????????
            int currntDays = DateUtils.getYearsDays(opearDate);
            // ????????????
            int index = 1;
            JSONArray jsonArray = new JSONArray();
            for (int i = 1; i < currntDays + 1; i++) {
                Date currentDate = DateUtils.addDate("dd", i - 1, opearDate);
                String currentDateStr = DateUtils.getTimeString(currentDate, DateUtils.datePattern);

                // ????????????
                String date = DateUtils.getTimeString(currentDate, DateUtils.datePattern);
                //????????????
                String year = date.split("-")[0];
                //????????????
                String month = date.split("-")[1];
                //????????????
                String day = date.split("-")[2];

                //????????????
                int yearN = Integer.parseInt(year);
                int monthN = Integer.parseInt(month);
                int dayN = Integer.parseInt(day);

                //?????????????????????????????????
                Lauar lauar = new Lauar(yearN, monthN, dayN);
                //??????????????????????????????????????????????????????????????????????????????????????????
                String lunarDay = lauar.getLunarDay();
                //?????????????????????????????????
                String lunarHoliday = lauar.getLunarHoliday();
//				//??????????????????
//				if (Lauar.SpringHoliday.contains(lunarDay)) {
//					continue;
//				}
//				//??????????????????
//				if (Lauar.SpringHoliday.contains(lunarHoliday)) {
//					continue;
//				}

                amlhcLotterySg = new AmlhcLotterySg();
                amlhcLotterySg.setOpenStatus("WAIT");
                amlhcLotterySg.setDate(date);
                // ????????????
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
            logger.error("?????????????????????1??????????????????{}", e);
        }
    }

    /**
     * ??????????????????????????????  ?????? https://macaujc.com/pc/#/
     * ?????????http://api.bjjfnet.com/data/opencode/2032
     */
    public List<LotterySgModel> collectionData() throws IOException {
        List<LotterySgModel> results = new ArrayList<>();
        String url = "https://api.bjjfnet.com/data/opencode/2032";
        String charset = "UTF-8";

        try {
            String jsonStr = GetHttpsgUtil.get(url, charset); // ??????JSON?????????
            if (org.apache.commons.lang3.StringUtils.isBlank(jsonStr)) {
                return null;
            }
            JSONObject json = JSONObject.parseObject(jsonStr); // ?????????JSON???
            JSONArray jsonArray = json.getJSONArray("data");
            LotterySgModel sgModel;
            // ???????????????????????????
            for (int i = 0; i < jsonArray.size(); i++) {
                Calendar calendar = Calendar.getInstance();
                sgModel = new LotterySgModel();
                // ????????????
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
            logger.error("????????????????????????????????????", e);
        }

        return results;
    }

    /**
     * @Title: getNextSslhcLotterySg
     * @Description: ??????????????????
     * @return SslhcLotterySg
     * @author HANS
     * @date 2019???4???29?????????9:14:27
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
