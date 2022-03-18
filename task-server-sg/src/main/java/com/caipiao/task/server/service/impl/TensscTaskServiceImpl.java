package com.caipiao.task.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.SscUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.mapper.TensscCountSgdxdsMapper;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.RandomUtil;
import com.caipiao.task.server.service.TensscTaskService;
import com.mapper.TensscKillNumberMapper;
import com.mapper.TensscLotterySgMapper;
import com.mapper.TensscRecommendMapper;

@Service
public class TensscTaskServiceImpl implements TensscTaskService {

	private static final Logger logger = LoggerFactory.getLogger(TensscTaskServiceImpl.class);
    @Autowired
    private TensscLotterySgMapper tensscLotterySgMapper;
    @Autowired
    private TensscRecommendMapper tensscRecommendMapper;
    @Autowired
    private TensscKillNumberMapper tensscKillNumberMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private KillOrderService killOrderService;
    @Autowired
    private TensscTaskService tensscTaskService;
    @Autowired
    private TensscCountSgdxdsMapper tensscCountSgdxdsMapper;

    /**
     * 录入预期赛果信息
     */
    @Override
    @Transactional
    public void addTensscPrevSg() {
        // 一天总期数
        int count = 144;
        // 获取当前赛果最后一期数据
        TensscLotterySgExample sgExample = new TensscLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        TensscLotterySg lastSg = tensscLotterySgMapper.selectOneByExample(sgExample);

        // 获取理想开奖时间
        String idealTime = lastSg.getIdealTime();
        // 将字符串转化为Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // 最后一期期号
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(8));

        JSONArray jsonArray = new JSONArray();
        TensscLotterySg sg;
        if (current < count) {
            for (int i = current; i < count; i++) {
                sg = new TensscLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                sg.setDate(lastSg.getDate());
                dateTime = this.nextIssueTime(dateTime);
                sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
                sg.setOpenStatus("WAIT");

                TensscLotterySg targetSg = new TensscLotterySg();
                BeanUtils.copyProperties(sg,targetSg);
                jsonArray.add(targetSg);
                tensscLotterySgMapper.insertSelective(sg);
            }
        }

        if (lastSg.getDate().compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if(jsonArray.size() > 0){
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TEN_YUQI_DATA,"TensscLotterySg:" + jsonString);
            }

            return;
        }

        for (int i = 1; i <= count; i++) {
            sg = new TensscLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            sg.setDate(issue.substring(0, 4) + "-" + issue.substring(4, 6) + "-" + issue.substring(6, 8));
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
            sg.setOpenStatus("WAIT");

            TensscLotterySg targetSg = new TensscLotterySg();
            BeanUtils.copyProperties(sg,targetSg);
            jsonArray.add(targetSg);
            tensscLotterySgMapper.insertSelective(sg);

        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TEN_YUQI_DATA,"TensscLotterySg:" + jsonString);
    }

	/**
	 * 录入赛果
	 */
	@Override
	public void addTensscSg() {
        // 获取  最近第一期+ 本地近15期未开奖结果（1天内）
        TensscLotterySgExample sgExample = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = sgExample.createCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        sgExample.setOffset(0);
        sgExample.setLimit(1);
        sgExample.setOrderByClause("ideal_time desc");
        TensscLotterySg tensscLotterySg = tensscLotterySgMapper.selectOneByExample(sgExample);

        sgExample = new TensscLotterySgExample();
        criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThan(tensscLotterySg.getIdealTime());
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
        criteria.andOpenStatusEqualTo("WAIT");
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("ideal_time desc");
        List<TensscLotterySg> sgFifteenList = tensscLotterySgMapper.selectByExample(sgExample);

        List<TensscLotterySg> sgList = new ArrayList<>();
        sgList.add(tensscLotterySg);
        sgList.addAll(sgFifteenList);


		// 判空
		if (null == sgList) {
			return;
		}

		int i = 0;
		for (TensscLotterySg sg : sgList) {
		    i++;
            boolean isPush = false; // 是否推送消息
            boolean updateIssue = false; // 是否更新开奖信息

			String issue = sg.getIssue();
			
			// 随机生成赛果数据
			String numberStr = "";

			// 判断是否需要修改赛果
			if (org.springframework.util.StringUtils.isEmpty(sg.getNumber())) {
                numberStr=killOrderService.getSscKillNumber(sg.getIssue(),CaipiaoTypeEnum.TENSSC.getTagType());
				String[] numbers = numberStr.split(",");
				sg.setWan(Integer.valueOf(numbers[0]));
				sg.setQian(Integer.valueOf(numbers[1]));
				sg.setBai(Integer.valueOf(numbers[2]));
				sg.setShi(Integer.valueOf(numbers[3]));
				sg.setGe(Integer.valueOf(numbers[4]));
				sg.setNumber(numberStr);

                //根据开奖号码生成一个 每期销售金额
                sg.setMoney(getMoneyByNumber(sg.getNumber()));

				sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
				sg.setOpenStatus("AUTO");
				updateIssue = isPush = true;
			}

			int count = 0;
            String jsontensscLotterySg = null;
			if (updateIssue) {
				count = tensscLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsontensscLotterySg = JSON.toJSONString(sg).replace(":","$");
			}

			if (isPush && count > 0) {
                //检查预期数据
                checkTensscYuqiData();

				logger.info("【十分时时彩】消息：{}",issue);
				// 将赛果推送到十分时时彩相关队列
//				rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSC_TEN,
//						"TENSSC:" + issue + ":" + numberStr);
                try{
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TEN_LM,"TENSSC:" + issue + ":" + numberStr + ":" + jsontensscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TEN_DN,"TENSSC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TEN_15,"TENSSC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TEN_QZH,"TENSSC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TEN_UPDATE_DATA,"TENSSC:" + issue + ":" + numberStr);
                }catch (Exception e){
                    logger.error("十分时时彩发送消息失败：{}",e);
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
                int openCount = (int)(jiange/600) + 1;
                int noOpenCount = CaipiaoSumCountEnum.TENSSC.getSumCount() - openCount;

                object.put("openCount",openCount);
                object.put("noOpenCount",noOpenCount);
                lottery.put(CaipiaoTypeEnum.TENSSC.getTagType(),object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data",lottery);
                objectAll.put("status",1);
                objectAll.put("time",new Date().getTime()/1000);
                objectAll.put("info","成功");
                String jsonString = objectAll.toJSONString();

                try{
                    //每次只发送最新一条
                    if(i == 1){
                        logger.info("TOPIC_APP_SSC_TEN发送消息：{}",jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_TEN,jsonString);
                    }
                }catch (Exception e){
                    logger.error("十分时时彩发送消息失败：{}",e);
                }

			}
			
		}

	}

    public BigDecimal getMoneyByNumber(String number){
        String[] numArray = number.split(",");
        //设置销售金额1000万到9.9亿的随机数
        boolean xunhuan = true;
        int cishu = 0;
        BigDecimal bmoney = null;
        while(xunhuan){
            //        String sg = 3,1,5,4,3
            Integer xiao2 = RandomUtil.getRandomOne(0,9); //小数点第2位
            Integer xiao1 = RandomUtil.getRandomOne(0,9); //小数点第1位
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
                logger.info("循环次数：{},生成销售金额：{}",cishu,bmoney);
                xunhuan = false;
            }
        }
        return bmoney;
    }

    //检查预期数据
    public void checkTensscYuqiData(){
        try{
            //检查预期数据任务开始
            TensscLotterySgExample sgExample = new TensscLotterySgExample();
            TensscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = tensscLotterySgMapper.countByExample(sgExample);
            if(afterCount < 10){  //当预期数据少于10，则跑一下预期数据任务
                tensscTaskService.addTensscPrevSg();
            }
            //检查预期数据任务结束
        }catch (Exception e){
            logger.error("十分时时彩检查预期失败：{}",e);
        }
    }

    /**
     * 免费推荐
     */
    @Override
    @Transactional
    public void addTensscRecommend() {
        // 获取当前最后一期【免费推荐】数据
        TensscRecommendExample recommendExample = new TensscRecommendExample();
        recommendExample.setOrderByClause("`issue` desc");
        TensscRecommend lastRecommend = tensscRecommendMapper.selectOneByExample(recommendExample);

        // 获取下一期期号信息
        TensscLotterySg nextSg = this.queryNextSg();

        // 查询遗漏数据
        List<TensscLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 循环生成【免费推荐】数据
        TensscRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new TensscRecommend();
            // 设置期号
            recommend.setIssue(sgList.get(i).getIssue());

            // 生成第一球号码推荐 5个
            String numberStr1 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallOneNumber(numberStr1);
            // 根据号码生成大小|单双
            String[] str1 = numberStr1.split(",");
            recommend.setBallOneSize(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setBallOneSingle(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");

            // 生成第二球号码推荐 5个
            String numberStr2 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallTwoNumber(numberStr2);
            // 根据号码生成大小|单双
            String[] str2 = numberStr2.split(",");
            recommend.setBallTwoSize(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setBallTwoSingle(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");

            // 生成第三球号码推荐 5个
            String numberStr3 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallThreeNumber(numberStr3);
            // 根据号码生成大小|单双
            String[] str3 = numberStr3.split(",");
            recommend.setBallThreeSize(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setBallThreeSingle(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");

            // 生成第四球号码推荐 5个
            String numberStr4 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallFourNumber(numberStr4);
            // 根据号码生成大小|单双
            String[] str4 = numberStr4.split(",");
            recommend.setBallFourSize(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setBallFourSingle(Integer.parseInt(str4[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");

            // 生成第五球号码推荐 5个
            String numberStr5 = RandomUtil.getRandomStringNoSame(5, 0, 10);
            recommend.setBallFiveNumber(numberStr5);
            // 根据号码生成大小|单双
            String[] str5 = numberStr5.split(",");
            recommend.setBallFiveSize(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
            recommend.setBallFiveSingle(Integer.parseInt(str5[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");

            // 生成随机 “龙” | “虎”
            recommend.setDragonTiger(RandomUtil.getRandomOne(0, 10) % 2 == 1 ? "龙" : "虎");

            // 设置时间
            recommend.setCreateTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));

            // 保存生成数据
            tensscRecommendMapper.insertSelective(recommend);
        }
    }

    /**
     * 公式杀号
     */
    @Override
    @Transactional
    public void addTensscGssh() {
        // 查询当前最后一期【公式杀号】数据
        TensscKillNumberExample killNumberExample = new TensscKillNumberExample();
        killNumberExample.setOrderByClause("`issue` DESC");
        TensscKillNumber killNumber = tensscKillNumberMapper.selectOneByExample(killNumberExample);

        // 获取下一期期号信息
        TensscLotterySg nextSg = this.queryNextSg();

        // 查询遗漏数据
        List<TensscLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 生成杀号
        TensscKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            nextKillNumber = new TensscKillNumber();

            // 设置期号
            nextKillNumber.setIssue(sgList.get(i).getIssue());

            // 生成第一球号码推荐 5个
            String numberStr1 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallOne(numberStr1);

            // 生成第二球号码推荐 5个
            String numberStr2 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallTwo(numberStr2);

            // 生成第三球号码推荐 5个
            String numberStr3 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallThree(numberStr3);

            // 生成第四球号码推荐 5个
            String numberStr4 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallFour(numberStr4);

            // 生成第五球号码推荐 5个
            String numberStr5 = RandomUtil.getRandomStringSame(5, 0, 10);
            nextKillNumber.setBallFive(numberStr5);

            nextKillNumber.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            tensscKillNumberMapper.insertSelective(nextKillNumber);
        }
    }

    @Override
    public void dxdsSgCount() {
        String date = DateUtils.formatDate(DateUtils.getDayAfter(new Date(), -1L), DateUtils.datePattern);
        // 获取昨天的赛果记录
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andWanIsNotNull();
        Calendar calendar = Calendar.getInstance();
        criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(calendar.getTime(),DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH,1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(),DateUtils.datePattern));
        List<TensscLotterySg> tensscLotterySgs = this.tensscLotterySgMapper.selectByExample(example);

        if (tensscLotterySgs != null && tensscLotterySgs.size() > 0) {
            // 对赛果进行统计
            TensscCountSgdxds tensscCountSgdxds = SscUtils.tenssscCountDxds(tensscLotterySgs);
            //根据日期判断是否已录入数据库
            //大小统计
            tensscCountSgdxds.setDate(new Date());
            this.tensscCountSgdxdsMapper.insertSelective(tensscCountSgdxds);
        }
    }

    @Override
    public void dxdsSgCountLatelyTwoMonth() {   //统计最近两个月的数据,补历史数据
        try{
            for(int i = 1; i <= 60;i++){
                //查看当天有没有统计信息，
                TensscCountSgdxdsExample tensscCountSgdxdsExample = new TensscCountSgdxdsExample();
                TensscCountSgdxdsExample.Criteria dxdsCriteria = tensscCountSgdxdsExample.createCriteria();
                Calendar countCalendar = Calendar.getInstance();
                countCalendar.set(Calendar.HOUR_OF_DAY,0);
                countCalendar.set(Calendar.MINUTE,0);
                countCalendar.set(Calendar.SECOND,0);
                countCalendar.add(Calendar.DAY_OF_MONTH,i*-1);
                dxdsCriteria.andDateGreaterThanOrEqualTo(countCalendar.getTime());
                Calendar countCalendarAddOneday = countCalendar;
                countCalendarAddOneday.add(Calendar.DAY_OF_MONTH,1);
                dxdsCriteria.andDateLessThan(countCalendarAddOneday.getTime());
                List<TensscCountSgdxds> sgdxdsList = tensscCountSgdxdsMapper.selectByExample(tensscCountSgdxdsExample);
                if(sgdxdsList.size() > 0){
                    continue;
                }
                // 获取昨天的赛果记录
                TensscLotterySgExample example = new TensscLotterySgExample();
                TensscLotterySgExample.Criteria criteria = example.createCriteria();
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
                List<TensscLotterySg> tensscLotterySgs = this.tensscLotterySgMapper.selectByExample(example);

                if (tensscLotterySgs != null && tensscLotterySgs.size() > 0) {
                    // 对赛果进行统计
                    TensscCountSgdxds tensscCountSgdxds = SscUtils.tenssscCountDxds(tensscLotterySgs);
                    tensscCountSgdxds.setDate(calendar.getTime());
                    //根据日期判断是否已录入数据库
                    //大小统计
                    this.tensscCountSgdxdsMapper.insertSelective(tensscCountSgdxds);
                }
            }
        }catch (Exception e){
            logger.error("10分时时彩赛果统计出错：{}",e);
        }

    }

    /**
     * 根据上期期号生成下期期号
     *
     * @param issue 上期期号
     * @return
     */
    private String createNextIssue(String issue) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        // 判断是否已达最大值
        if ("144".equals(num)||"0144".equals(num)) {
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
     * 获取下一期官方开奖时间
     *
     * @param lastTime 当前最后设定的一个官方开奖时间
     * @return 下一期官方开奖时间
     */
    private Date nextIssueTime(Date lastTime) {
        Date dateTime;
        String date = DateUtils.formatDate(lastTime, "yyyy-MM-dd");
        String time = DateUtils.formatDate(lastTime, "HH:mm:ss");
        if ("23:50:00".equals(time)) {
        	String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(date, "yyyy-MM-dd"), 1L), "yyyy-MM-dd");
            dateTime = DateUtils.parseDate(prefix + " 00:00:00", DateUtils.fullDatePattern);
        } else {
            dateTime = DateUtils.getMinuteAfter(lastTime, 10);
        }
        return dateTime;
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    private TensscLotterySg queryNextSg() {
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        TensscLotterySg sg = tensscLotterySgMapper.selectOneByExample(example);
        return tensscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 根据期号区间查询所有遗漏数据【近96期】
     *
     * @param startIssue 开始期号【不包括】
     * @param endIssue   结束期号【包括】
     * @return
     */
    private List<TensscLotterySg> queryOmittedData(String startIssue, String endIssue) {
        TensscLotterySgExample example = new TensscLotterySgExample();
        TensscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(startIssue);
        criteria.andIssueLessThanOrEqualTo(endIssue);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(144);
        return tensscLotterySgMapper.selectByExample(example);
    }


}
