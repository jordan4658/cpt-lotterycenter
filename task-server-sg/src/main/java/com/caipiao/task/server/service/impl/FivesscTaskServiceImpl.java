package com.caipiao.task.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.*;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.caipiao.task.server.util.AusPksUtil;
import com.mapper.FivesscCountSgdxdsMapper;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.caipiao.task.server.service.FivesscTaskService;
import com.mapper.FivesscKillNumberMapper;
import com.mapper.FivesscLotterySgMapper;
import com.mapper.FivesscRecommendMapper;
import com.mapper.domain.FivesscLotterySg;
import com.mapper.domain.FivesscLotterySgExample;

@Service
public class FivesscTaskServiceImpl implements FivesscTaskService {

	private static final Logger logger = LoggerFactory.getLogger(FivesscTaskServiceImpl.class);

    @Autowired
    private FivesscLotterySgMapper fivesscLotterySgMapper;
    @Autowired
    private FivesscRecommendMapper fivesscRecommendMapper;
    @Autowired
    private FivesscKillNumberMapper fivesscKillNumberMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private KillOrderService killOrderService;
    @Autowired
    private FivesscTaskService fivesscTaskService;
    @Autowired
    private FivesscCountSgdxdsMapper fivesscCountSgdxdsMapper;

    /**
     * ????????????????????????
     */
    @Override
    @Transactional
    public void addFivesscPrevSg() {
        // ???????????????
        int count = 288;
        // ????????????????????????????????????
        FivesscLotterySgExample sgExample = new FivesscLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        FivesscLotterySg lastSg = fivesscLotterySgMapper.selectOneByExample(sgExample);

        // ????????????????????????
        String idealTime = lastSg.getIdealTime();
        // ?????????????????????Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // ??????????????????
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(8));

        JSONArray jsonArray = new JSONArray();
        FivesscLotterySg sg;
        if (current < count) {
            for (int i = current; i < count; i++) {
                sg = new FivesscLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                sg.setDate(lastSg.getDate());
                dateTime = this.nextIssueTime(dateTime);
                sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
                sg.setOpenStatus("WAIT");

                FivesscLotterySg targetSg = new FivesscLotterySg();
                BeanUtils.copyProperties(sg,targetSg);
                jsonArray.add(targetSg);
                fivesscLotterySgMapper.insertSelective(sg);

            }
        }
        
        
        
        if (lastSg.getDate().compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if(jsonArray.size() > 0){
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_FIVE_YUQI_DATA,"FivesscLotterySg:" + jsonString);
            }

            return;
        }

        for (int i = 1; i <= count; i++) {
            sg = new FivesscLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            sg.setDate(issue.substring(0, 4) + "-" + issue.substring(4, 6) + "-" + issue.substring(6, 8));
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
            sg.setOpenStatus("WAIT");

            FivesscLotterySg targetSg = new FivesscLotterySg();
            BeanUtils.copyProperties(sg,targetSg);
            jsonArray.add(targetSg);
            fivesscLotterySgMapper.insertSelective(sg);

        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_FIVE_YUQI_DATA,"FivesscLotterySg:" + jsonString);

    }

	/**
	 * ????????????
	 */
	@Override
	public void addFivesscSg() {
        // ??????  ???????????????+ ?????????15?????????????????????1?????????
        FivesscLotterySgExample sgExample = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = sgExample.createCriteria();
        sgExample.setOffset(0);
        sgExample.setLimit(1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        sgExample.setOrderByClause("ideal_time desc");
        FivesscLotterySg fivesscLotterySg = fivesscLotterySgMapper.selectOneByExample(sgExample);

        sgExample = new FivesscLotterySgExample();
        criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThan(fivesscLotterySg.getIdealTime());
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        criteria.andOpenStatusEqualTo("WAIT");
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("ideal_time desc");
        List<FivesscLotterySg> sgFifteenList = fivesscLotterySgMapper.selectByExample(sgExample);

        List<FivesscLotterySg> sgList = new ArrayList<>();
        sgList.add(fivesscLotterySg);
        sgList.addAll(sgFifteenList);


		// ??????
		if (null == sgList) {
			return;
		}

		int i = 0;
		for (FivesscLotterySg sg : sgList) {
		    i++;
            boolean isPush = false; // ??????????????????
            boolean updateIssue = false; // ????????????????????????

			String issue = sg.getIssue();
			// ????????????????????????
			String numberStr = "";

			// ??????????????????????????????
			if (org.springframework.util.StringUtils.isEmpty(sg.getNumber())) {
                numberStr=killOrderService.getSscKillNumber(sg.getIssue(),CaipiaoTypeEnum.FIVESSC.getTagType());
				String[] numbers = numberStr.split(",");
				sg.setWan(Integer.valueOf(numbers[0]));
				sg.setQian(Integer.valueOf(numbers[1]));
				sg.setBai(Integer.valueOf(numbers[2]));
				sg.setShi(Integer.valueOf(numbers[3]));
				sg.setGe(Integer.valueOf(numbers[4]));
				sg.setNumber(numberStr);

                //?????????????????????????????? ??????????????????
                sg.setMoney(getMoneyByNumber(sg.getNumber()));

				sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
				sg.setOpenStatus("AUTO");
				updateIssue = isPush = true;
			}


			int count = 0;
            String jsonFivesscLotterySg = null;
			if (updateIssue) {
				count = fivesscLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonFivesscLotterySg = JSON.toJSONString(sg).replace(":","$");
			}

			if (isPush && count > 0) {
                //??????????????????
                checkFiveSscYuqiData();

				logger.info("??????????????????????????????{},{}",issue,numberStr);
				// ?????????????????????????????????????????????
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSC_FIVE,
//						"FIVESSC:" + issue + ":" + numberStr);

                try{
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_FIVE_LM,"FIVESSC:" + issue + ":" + numberStr + ":" + jsonFivesscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KLNN_FIVE,"FIVESSC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_FIVE_DN,"FIVESSC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_FIVE_15,"FIVESSC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_FIVE_QZH,"FIVESSC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_FIVE_UPDATE_DATA,"FIVESSC:" + issue + ":" + numberStr);
                }catch (Exception e){
                    logger.error("????????????????????????????????????{}",e);
                }


				// ??????????????????WenSocket????????????
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_RESULT_PUSH,
//						"FIVESSC:" + issue + ":" + numberStr);
//                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_WEB_RESULT_PUSH,"FIVESSC:" + issue + ":" + numberStr);

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue",issue);
                object.put("number",numberStr);

                String niuWinner = NnKlOperationUtils.getNiuWinner(sg.getNumber());
                object.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);

                object.put("nextTime",DateUtils.parseDate(queryNextSg().getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                object.put("nextIssue",queryNextSg().getIssue());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
                startCal.set(Calendar.HOUR_OF_DAY,0);
                startCal.set(Calendar.MINUTE,0);
                startCal.set(Calendar.SECOND,0);

                long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
                int openCount = (int)(jiange/300) + 1;
                int noOpenCount = CaipiaoSumCountEnum.FIVESSC.getSumCount() - openCount;

                object.put("openCount",openCount);
                object.put("noOpenCount",noOpenCount);
                lottery.put(CaipiaoTypeEnum.FIVESSC.getTagType(),object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data",lottery);
                objectAll.put("status",1);
                objectAll.put("time",new Date().getTime()/1000);
                objectAll.put("info","??????");
                String jsonString = objectAll.toJSONString();

                try{
                    //???????????????????????????
                    if(i == 1){
                        logger.info("TOPIC_APP_SSC_FIVE???????????????{}",jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_FIVE,jsonString);

                        object.put("number", sg.getNumber());//AusPksUtil.getAusSscbyAct(sg.getNumber()));//TODO getAusSscbyAct?????????????????????, ????????????5???, ???????????????(@min)
                        lottery.put(CaipiaoTypeEnum.KLNIU.getTagType(),object);
                        lottery.put(CaipiaoTypeEnum.FIVESSC.getTagType(),null);
                        objectAll.put("data",lottery);
                        String sscJsonString = objectAll.toJSONString();
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_NN_KL,sscJsonString);
                    }
                }catch (Exception e){
                    logger.error("????????????????????????????????????{}",e);
                }

			}
			
		}
		
	}

    public BigDecimal getMoneyByNumber(String number){
        String[] numArray = number.split(",");
        //??????????????????10000??????9.9???????????????
        boolean xunhuan = true;
        int cishu = 0;
        BigDecimal bmoney = null;
        while(xunhuan){
            //        String sg = 3,1,5,4,3
            Integer xiao2 = RandomUtil.getRandomOne(0,9) ; //????????????2???
            Integer xiao1 = RandomUtil.getRandomOne(0,9); //????????????1???
            Integer Number_ge = Integer.valueOf(numArray[4]);
            Integer Number_shi = Integer.valueOf(numArray[3]);
            Integer Number_bai = Integer.valueOf(numArray[2]);
            Integer Number_qian = Integer.valueOf(numArray[1]);
            Integer Number_wan = Integer.valueOf(numArray[0]);
            Integer wan = RandomUtil.getRandomOne(0,9);
            Integer shiwan = RandomUtil.getRandomOne(0,9);
            Integer baiwan = RandomUtil.getRandomOne(0,9);
            Integer qianwan = RandomUtil.getRandomOne(0,9);
            Integer yi = RandomUtil.getRandomOne(0,9);
            Integer sum = yi+qianwan+baiwan+shiwan+wan+Number_qian+Number_bai+Number_shi+Number_ge+xiao1+xiao2;
            cishu ++;
            if(sum%10 == Number_wan){
                Double money = Double.valueOf(yi*100000000+qianwan*10000000+baiwan*1000000+shiwan*100000+wan*10000+Number_qian*1000+Number_bai*100+Number_shi*10+Number_ge+0.1*xiao1+0.01*xiao2);
                if(money < 10000000 || money >990000000){
                    continue;
                }
                bmoney = new BigDecimal(money);
                bmoney = bmoney.setScale(2, BigDecimal.ROUND_HALF_UP);
                logger.info("???????????????{},?????????????????????{}",cishu,bmoney);
                xunhuan = false;
            }
        }
        return bmoney;
    }

    //??????????????????
    public void checkFiveSscYuqiData(){
        try{
            //??????????????????????????????
            FivesscLotterySgExample sgExample = new FivesscLotterySgExample();
            FivesscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = fivesscLotterySgMapper.countByExample(sgExample);
            if(afterCount < 10){  //?????????????????????10?????????????????????????????????
                fivesscTaskService.addFivesscPrevSg();
            }
            //??????????????????????????????
        }catch (Exception e){
            logger.error("??????????????????????????????????????????{}",e);
        }
    }

    /**
     * ????????????
     */
    @Override
    @Transactional
    public void addFivesscRecommend() {
        // ????????????????????????????????????????????????
        FivesscRecommendExample recommendExample = new FivesscRecommendExample();
        recommendExample.setOrderByClause("`issue` desc");
        FivesscRecommend lastRecommend = fivesscRecommendMapper.selectOneByExample(recommendExample);

        // ???????????????????????????
        FivesscLotterySg nextSg = this.queryNextSg();

        // ??????????????????
        List<FivesscLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????????????????????????????
        FivesscRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new FivesscRecommend();
            // ????????????
            recommend.setIssue(sgList.get(i).getIssue());

            // ??????????????????????????? 5???
            String numberStr1 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallOneNumber(numberStr1);
            // ????????????????????????|??????
            String[] str1 = numberStr1.split(",");
            recommend.setBallOneSize(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
            recommend.setBallOneSingle(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");

            // ??????????????????????????? 5???
            String numberStr2 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallTwoNumber(numberStr2);
            // ????????????????????????|??????
            String[] str2 = numberStr2.split(",");
            recommend.setBallTwoSize(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
            recommend.setBallTwoSingle(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");

            // ??????????????????????????? 5???
            String numberStr3 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallThreeNumber(numberStr3);
            // ????????????????????????|??????
            String[] str3 = numberStr3.split(",");
            recommend.setBallThreeSize(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
            recommend.setBallThreeSingle(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");

            // ??????????????????????????? 5???
            String numberStr4 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallFourNumber(numberStr4);
            // ????????????????????????|??????
            String[] str4 = numberStr4.split(",");
            recommend.setBallFourSize(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
            recommend.setBallFourSingle(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");

            // ??????????????????????????? 5???
            String numberStr5 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallFiveNumber(numberStr5);
            // ????????????????????????|??????
            String[] str5 = numberStr5.split(",");
            recommend.setBallFiveSize(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
            recommend.setBallFiveSingle(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");

            // ???????????? ????????? | ?????????
            recommend.setDragonTiger(RandomUtil.getRandomOne(0, 10) % 2 == 1 ? "???" : "???");

            // ????????????
            recommend.setCreateTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));

            // ??????????????????
            fivesscRecommendMapper.insertSelective(recommend);
        }
    }

    /**
     * ????????????
     */
    @Override
    @Transactional
    public void addFivesscGssh() {
        // ????????????????????????????????????????????????
        FivesscKillNumberExample killNumberExample = new FivesscKillNumberExample();
        killNumberExample.setOrderByClause("`issue` DESC");
        FivesscKillNumber killNumber = fivesscKillNumberMapper.selectOneByExample(killNumberExample);

        // ???????????????????????????
        FivesscLotterySg nextSg = this.queryNextSg();

        // ??????????????????
        List<FivesscLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????
        FivesscKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            nextKillNumber = new FivesscKillNumber();

            // ????????????
            nextKillNumber.setIssue(sgList.get(i).getIssue());

            // ??????????????????????????? 5???
            String numberStr1 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallOne(numberStr1);

            // ??????????????????????????? 5???
            String numberStr2 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallTwo(numberStr2);

            // ??????????????????????????? 5???
            String numberStr3 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallThree(numberStr3);

            // ??????????????????????????? 5???
            String numberStr4 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallFour(numberStr4);

            // ??????????????????????????? 5???
            String numberStr5 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallFive(numberStr5);

            nextKillNumber.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            fivesscKillNumberMapper.insertSelective(nextKillNumber);
        }
    }

    @Override
    public void dxdsSgCount() {
        String date = DateUtils.formatDate(DateUtils.getDayAfter(new Date(), -1L), DateUtils.datePattern);
        // ???????????????????????????
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        Calendar calendar = Calendar.getInstance();
        criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(calendar.getTime(),DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH,1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(),DateUtils.datePattern));
        List<FivesscLotterySg> fivesscLotterySgs = this.fivesscLotterySgMapper.selectByExample(example);

        if (fivesscLotterySgs != null && fivesscLotterySgs.size() > 0) {
            // ?????????????????????
            FivesscCountSgdxds fivesscCountSgdxds = SscUtils.fivessscCountDxds(fivesscLotterySgs);
            //??????????????????????????????????????????
            //????????????
            fivesscCountSgdxds.setDate(new Date());
            this.fivesscCountSgdxdsMapper.insertSelective(fivesscCountSgdxds);
        }
    }

    @Override
    public void dxdsSgCountLatelyTwoMonth() {   //??????????????????????????????,???????????????
        try{
            for(int i = 1; i <= 60;i++){
                //????????????????????????????????????
                FivesscCountSgdxdsExample fivesscCountSgdxdsExample = new FivesscCountSgdxdsExample();
                FivesscCountSgdxdsExample.Criteria dxdsCriteria = fivesscCountSgdxdsExample.createCriteria();
                Calendar countCalendar = Calendar.getInstance();
                countCalendar.set(Calendar.HOUR_OF_DAY,0);
                countCalendar.set(Calendar.MINUTE,0);
                countCalendar.set(Calendar.SECOND,0);
                countCalendar.add(Calendar.DAY_OF_MONTH,i*-1);
                dxdsCriteria.andDateGreaterThanOrEqualTo(countCalendar.getTime());
                Calendar countCalendarAddOneday = countCalendar;
                countCalendarAddOneday.add(Calendar.DAY_OF_MONTH,1);
                dxdsCriteria.andDateLessThan(countCalendarAddOneday.getTime());
                List<FivesscCountSgdxds> sgdxdsList = fivesscCountSgdxdsMapper.selectByExample(fivesscCountSgdxdsExample);
                if(sgdxdsList.size() > 0){
                    continue;
                }
                // ???????????????????????????
                FivesscLotterySgExample example = new FivesscLotterySgExample();
                FivesscLotterySgExample.Criteria criteria = example.createCriteria();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH,-1*i);
                calendar.set(Calendar.HOUR_OF_DAY,23);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(calendar.getTime(),DateUtils.datePattern));
                Calendar calendarAddOneday = (Calendar)calendar.clone();
                calendarAddOneday.add(Calendar.DAY_OF_MONTH,1);
                criteria.andDateLessThan(DateUtils.formatDate(calendarAddOneday.getTime(),DateUtils.datePattern));
                criteria.andWanIsNotNull();
                List<FivesscLotterySg> fivesscLotterySgs = this.fivesscLotterySgMapper.selectByExample(example);

                if (fivesscLotterySgs != null && fivesscLotterySgs.size() > 0) {
                    // ?????????????????????
                    FivesscCountSgdxds fivesscCountSgdxds = SscUtils.fivessscCountDxds(fivesscLotterySgs);
                    fivesscCountSgdxds.setDate(calendar.getTime());
                    //??????????????????????????????????????????
                    //????????????
                    this.fivesscCountSgdxdsMapper.insertSelective(fivesscCountSgdxds);
                }
            }
        }catch (Exception e){
            logger.error("5?????????????????????????????????{}",e);
        }

    }

    /**
     * ????????????????????????????????????
     *
     * @param issue ????????????
     * @return
     */
    private String createNextIssue(String issue) {
        // ?????????????????????
        String nextIssue;
        // ???????????????
        String num = issue.substring(8);
        // ???????????????????????????
        if ("288".equals(num)||"0288".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
        	String issueStrLeft = issue.substring(0,8);
        	String issueStrRight = issue.substring(8,issue.length());
        	int issueInt = Integer.parseInt(issueStrRight);
        	issueInt = issueInt+1;
        	issueStrRight = String.valueOf(issueInt);
        	String zeroStr = "";
        	switch (issueStrRight.length()) {
			case 1:
				zeroStr = "00";
				break;
			case 2:
				zeroStr = "0";
				break;
			}
        	issueStrRight = zeroStr.concat(issueStrRight);
        	nextIssue = issueStrLeft.concat(issueStrRight);
        }
        return nextIssue;
    }

    /**
     * ?????????????????????????????????
     *
     * @param lastTime ?????????????????????????????????????????????
     * @return ???????????????????????????
     */
    private Date nextIssueTime(Date lastTime) {
        Date dateTime;
        String date = DateUtils.formatDate(lastTime, "yyyy-MM-dd");
        String time = DateUtils.formatDate(lastTime, "HH:mm:ss");
        if ("23:55:00".equals(time)) {
        	String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(date, "yyyy-MM-dd"), 1L), "yyyy-MM-dd");
            dateTime = DateUtils.parseDate(prefix + " 00:00:00", DateUtils.fullDatePattern);
        } else {
            dateTime = DateUtils.getMinuteAfter(lastTime, 5);
        }
        return dateTime;
    }

    /**
     * ???????????????????????????
     *
     * @return
     */
    private FivesscLotterySg queryNextSg() {
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        return fivesscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * ????????????????????????????????????????????????96??????
     *
     * @param startIssue ???????????????????????????
     * @param endIssue   ????????????????????????
     * @return
     */
    private List<FivesscLotterySg> queryOmittedData(String startIssue, String endIssue) {
        FivesscLotterySgExample example = new FivesscLotterySgExample();
        FivesscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(startIssue);
        criteria.andIssueLessThanOrEqualTo(endIssue);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(	288);
        return fivesscLotterySgMapper.selectByExample(example);
    }


}
