package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.*;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.TxffcTaskService;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.mapper.TxffcCountSgdxdsMapper;
import com.mapper.TxffcKillNumberMapper;
import com.mapper.TxffcLotterySgMapper;
import com.mapper.TxffcRecommendMapper;
import com.mapper.domain.*;
import com.xxl.job.core.log.XxlJobLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class TxffcTaskServiceImpl implements TxffcTaskService {
    private static final Logger logger = LoggerFactory.getLogger(TxffcTaskServiceImpl.class);
    @Autowired
    private TxffcLotterySgMapper txffcLotterySgMapper;
    @Autowired
    private TxffcRecommendMapper txffcRecommendMapper;
    @Autowired
    private TxffcKillNumberMapper txffcKillNumberMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private TxffcTaskService txffcTaskService;
    @Autowired
    private KillOrderService killOrderService;
    @Autowired
    private TxffcCountSgdxdsMapper txffcCountSgdxdsMapper;


    /**
     * 录入预期赛果信息
     */
    @Override
    @Transactional
    public void addTxffcPrevSg() {
        // 一天总期数
        int count = 1440;
        // 获取当前赛果最后一期数据
        TxffcLotterySgExample sgExample = new TxffcLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        TxffcLotterySg lastSg = txffcLotterySgMapper.selectOneByExample(sgExample);

        // 获取理想开奖时间
        String idealTime = lastSg.getIdealTime();
        // 将字符串转化为Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // 最后一期期号
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(9));

        JSONArray jsonArray = new JSONArray();
        TxffcLotterySg sg;
        if (current < count) {
            for (int i = current + 1; i < count; i++) {
                sg = new TxffcLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                dateTime = DateUtils.getMinuteAfter(dateTime, 1);
                sg.setDate(DateUtils.formatDate(dateTime, DateUtils.datePattern));
                sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

                TxffcLotterySg targetSg = new TxffcLotterySg();
                BeanUtils.copyProperties(sg,targetSg);
                jsonArray.add(targetSg);
                txffcLotterySgMapper.insertSelective(sg);

            }
        }

        if (lastSg.getDate().compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if(jsonArray.size() > 0){
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_YUQI_DATA,"TxffcLotterySg:" + jsonString);

            }

            return;
        }

        for (int i = 0; i < count; i++) {
            sg = new TxffcLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            dateTime = DateUtils.getMinuteAfter(dateTime, 1);
            sg.setDate(DateUtils.formatDate(dateTime, DateUtils.datePattern));
            sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));

            TxffcLotterySg targetSg = new TxffcLotterySg();
            BeanUtils.copyProperties(sg,targetSg);
            jsonArray.add(targetSg);
            txffcLotterySgMapper.insertSelective(sg);
        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_YUQI_DATA,"TxffcLotterySg:" + jsonString);
    }

    @Override
    public void addTxffcSg() {
        // 采集数据方案
        List<LotterySgModel> cpkResults = null;

        List<LotterySgModel> cpkResultsBian = null;
        try {
            cpkResults = this.collectionDataHuobi();
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("【网址一】获取【火币】赛果失败！");
        }

        try {
            cpkResultsBian = this.collectionDataBian();
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("【网址一】获取【币安】赛果失败！",e);
        }
//
//        // 采集数据方案2
//        List<LotterySgModel> kcwResults = null;
//        try {
//            kcwResults = this.collectionData2();
//        } catch (IOException e) {
////            e.printStackTrace();
//            logger.error("【网址二】获取【腾讯分分彩】赛果失败！");
//        }
//
//        // 判空（两个接口都没有获取到开奖结果）
//        if (CollectionUtils.isEmpty(cpkResults) && CollectionUtils.isEmpty(kcwResults)) {
//            return;
//        }

//         封装成map集合
        Map<String, LotterySgModel> cpkMap = new HashMap<>(), cpkMapBian = new HashMap<>();
        if (!CollectionUtils.isEmpty(cpkResults)) {
            for (LotterySgModel model : cpkResults) {
                cpkMap.put(model.getDate(), model);
            }
        }

        if (!CollectionUtils.isEmpty(cpkResultsBian)) {
            for (LotterySgModel model : cpkResultsBian) {
                cpkMapBian.put(model.getDate(), model);
            }
        }

        // 获取本地近35期开奖结果
        TxffcLotterySgExample sgExample = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(35);
        sgExample.setOrderByClause("`ideal_time` desc");
        List<TxffcLotterySg> sgList = txffcLotterySgMapper.selectByExample(sgExample);

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }
        String number="";
        int i = 0;
        String huobiPrice = null;
        String bianPrice = null;
        String price = null;
        for (TxffcLotterySg sg : sgList) {
            i++;
            String issue = sg.getIssue();
            // 获取开奖结果
            LotterySgModel cpkModel = cpkMap.get(sg.getIdealTime().substring(0,16));

            LotterySgModel cpkModelBian = cpkMapBian.get(sg.getIdealTime().substring(0,16));
//            LotterySgModel kcwModel = kcwMap.get(issue);
            // 判空
//            if (cpkModel == null && cpkModelBian == null) {
//                continue;
//            }
            // 获取【彩票控】当前实际开奖期号与结果
            if (cpkModel != null) {
                huobiPrice = cpkModel.getSg();
            }
            if(cpkModelBian != null){
                bianPrice = cpkModelBian.getSg();
            }
            if(huobiPrice != null){
                price = huobiPrice;
            }
            if(huobiPrice == null && bianPrice != null){
                price = bianPrice;
            }

//            // 获取【开彩网】当前实际开奖期号与结果
//            if (kcwModel != null) {
//                kcwNumber = kcwModel.getSg();
//            }

            // 获取最终开奖结果
           // number = StringUtils.isNotBlank(cpkNumber) ? cpkNumber : StringUtils.isNotBlank(kcwNumber) ? kcwNumber : "";

            // 判断开奖号码是否为空
//            if (StringUtils.isBlank(number)) {
//                continue;
//            }


            // 是否需要修改
            boolean bool = false, isPush = false;
            // 判断是否需要修改赛果
            if (sg.getWan() == null) {
                number = killOrderService.getSscKillNumber(sg.getIssue(),CaipiaoTypeEnum.TXFFC.getTagType());

                String[] numbers = number.split(",");
                sg.setWan(Integer.valueOf(numbers[0]));
                sg.setQian(Integer.valueOf(numbers[1]));
                sg.setBai(Integer.valueOf(numbers[2]));
                sg.setShi(Integer.valueOf(numbers[3]));
                sg.setGe(Integer.valueOf(numbers[4]));
                sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));

                if(price != null){
                    //计算万位
                    Integer xs1 = 0;  //小数后第1位
                    Integer xs2 = 0; //小数后第2位
                    if(price.contains(".")){
                        String xiaoshu = price.split("\\.")[1];
                        if(xiaoshu.length() == 1){
                            xs1 = Integer.valueOf(xiaoshu);
                        }else if(xiaoshu.length() == 2){
                            xs1 = Integer.valueOf(xiaoshu.substring(0,1));
                            xs2 = Integer.valueOf(xiaoshu.substring(1,2));
                        }else if(xiaoshu.length() > 2){
                            xs1 = Integer.valueOf(xiaoshu.substring(0,1));
                            xs2 = Integer.valueOf(xiaoshu.substring(1,2));
                        }
                    }
                    Integer xs3 = RandomUtil.getRandomOne(0,9);   //小数后第3位
                    Integer xs4 = RandomUtil.getRandomOne(0,9);   //小数后第4位
                    Integer xs5 = Integer.valueOf(numbers[1]);   //小数后第5位
                    Integer xs6 = Integer.valueOf(numbers[2]);   //小数后第6位
                    Integer xs7 = Integer.valueOf(numbers[3]);   //小数后第7位
                    Integer xs8 = Integer.valueOf(numbers[4]);   //小数后第8位

                    Integer wan = Integer.valueOf(numbers[0]);
                    boolean isTrue = true;
                    int xunSize = 0;

                    while(isTrue){
                        Integer sum = xs1+xs2+xs3+xs4+xs5+xs6+xs7+xs8;
                        Integer jisuanWan = sum%10;
                        if(jisuanWan == wan){
                            isTrue = false;
                        }else{
                            xunSize++;
                            xs3 = RandomUtil.getRandomOne(0,9);
                            xs4 = RandomUtil.getRandomOne(0,9);
                        }
                    }

                    logger.info("生成赛果数据循环：{}次，{}",xunSize,sg.getIssue());
                    price = price.replaceAll("\"","");
                    price = price.split("\\.")[0]+"."+xs1+xs2+xs3+xs4+xs5+xs6+xs7+xs8;
                    sg.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
                }

                sg.setOpenStatus("AUTO");
                sg.setCpkNumber(number);
                sg.setKcwNumber(number);
                bool = true;
                isPush = true;
            }

            int count = 0;
            String jsontxffcLotterySg = null;
            if (bool) {
                count = txffcLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsontxffcLotterySg = JSON.toJSONString(sg).replace(":","$");
            }

            if (isPush && count > 0) {
                //检查预期数据
                checkTxffcYuqiData();

                // 将赛果推送到腾讯分分彩相关队列
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSC_TX, "TXFFC:" + issue + ":" + number);
                try{
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_LM,"TXFFC:" + issue + ":" + number + ":" + jsontxffcLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_DN,"TXFFC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_15,"TXFFC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_QZH,"TXFFC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_UPDATE_DATA,"TXFFC:" + issue + ":" + number);
                }catch (Exception e){
                    logger.error("比特币分分彩发送消息失败：{}",e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue",issue);
                object.put("number",number);

                object.put("nextTime",DateUtils.parseDate(queryNextSg().getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
                object.put("nextIssue",queryNextSg().getIssue());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(DateUtils.parseDate(sg.getIdealTime(),DateUtils.fullDatePattern));
                startCal.set(Calendar.HOUR_OF_DAY,0);
                startCal.set(Calendar.MINUTE,0);
                startCal.set(Calendar.SECOND,0);

                long jiange = DateUtils.timeReduce(sg.getIdealTime(),DateUtils.formatDate(startCal.getTime(),DateUtils.fullDatePattern));
                int openCount = (int)(jiange/60) + 1;
                int noOpenCount = CaipiaoSumCountEnum.TXFFC.getSumCount() - openCount;

                object.put("openCount",openCount);
                object.put("noOpenCount",noOpenCount);
                lottery.put(CaipiaoTypeEnum.TXFFC.getTagType(),object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data",lottery);
                objectAll.put("status",1);
                objectAll.put("time",new Date().getTime()/1000);
                objectAll.put("info","成功");
                String jsonString = objectAll.toJSONString();

                try{
                    //每次只发送最新一条
                    if(i == 1){
                        logger.info("TOPIC_APP_SSC_TX：{}",jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_TX,jsonString);
                    }
                }catch (Exception e){
                    logger.error("比特币分分彩发送消息失败：{}",e);
                }

            }
        }
    }

    //检查预期数据
    public void checkTxffcYuqiData(){
        try{
            //检查预期数据任务开始
            TxffcLotterySgExample sgExample = new TxffcLotterySgExample();
            TxffcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = txffcLotterySgMapper.countByExample(sgExample);
            if(afterCount < 10){  //当预期数据少于10，则跑一下预期数据任务
                txffcTaskService.addTxffcPrevSg();
            }
            //检查预期数据任务结束
        }catch (Exception e){
            logger.error("比特币分分彩检查预期数据失败：{}",e);
        }
    }

    @Override
    @Transactional
    public void addTxffcRecommend() {
        // 获取当前最后一期【免费推荐】数据
        TxffcRecommendExample recommendExample = new TxffcRecommendExample();
        recommendExample.setOrderByClause("`issue` desc");
        TxffcRecommend lastRecommend = txffcRecommendMapper.selectOneByExample(recommendExample);

        // 获取下一期期号信息
        TxffcLotterySg nextSg = this.queryNextSg();

        // 查询遗漏数据
        List<TxffcLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 循环生成【免费推荐】数据
        TxffcRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new TxffcRecommend();
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
            txffcRecommendMapper.insertSelective(recommend);

            String jsonObject = JSON.toJSONString(recommend);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_RECOMMEND_DATA,"TxffcRecommend:" + jsonObject);
        }
    }

    @Override
    @Transactional
    public void addTxffcGssh() {
        // 查询当前最后一期【公式杀号】数据
        TxffcKillNumberExample killNumberExample = new TxffcKillNumberExample();
        killNumberExample.setOrderByClause("`issue` DESC");
        TxffcKillNumber killNumber = txffcKillNumberMapper.selectOneByExample(killNumberExample);

        // 获取下一期期号信息
        TxffcLotterySg nextSg = this.queryNextSg();

        // 查询遗漏数据
        List<TxffcLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 生成杀号
        TxffcKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            nextKillNumber = new TxffcKillNumber();

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
            txffcKillNumberMapper.insertSelective(nextKillNumber);

            String jsonObject = JSON.toJSONString(nextKillNumber);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_KILL_DATA,"TxffcKillNumber:" + jsonObject);
        }
    }

    @Override
    public void sendMessageChangeIssue() {
        //每一期开始时间发送一个消息
        //时间范围： 0 * * * * ?
        //腾讯分分彩  采集时间 1分钟一期 延迟1-2分钟采集到数据


        JSONObject object = new JSONObject();
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        TxffcLotterySg nextSg = txffcLotterySgMapper.selectOneByExample(example);

        example = new TxffcLotterySgExample();
        criteria = example.createCriteria();
        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` desc");
        example.setOffset(0);
        example.setLimit(10);
        List<TxffcLotterySg> lastSgList = txffcLotterySgMapper.selectByExample(example);

        JSONObject lottery = new JSONObject();
        object.put("nextTime",DateUtils.parseDate(nextSg.getIdealTime(),DateUtils.fullDatePattern).getTime()/1000);
        object.put("nextIssue",nextSg.getIssue());

        TxffcLotterySg lastSgTrue = null;
        for(TxffcLotterySg lastSg:lastSgList){
            String lastNumber = StringUtils.isNotBlank(lastSg.getCpkNumber()) ? lastSg.getCpkNumber() : StringUtils.isNotBlank(lastSg.getKcwNumber()) ? lastSg.getKcwNumber() : "";
            if(StringUtils.isNotBlank(lastNumber)){
                object.put("issue",lastSg.getIssue());
                object.put("number",lastNumber);
                lastSgTrue = lastSg;
                break;
            }
            continue;
        }


        int openCount = Integer.valueOf(lastSgTrue.getIssue().substring(8));
        int noOpenCount = CaipiaoSumCountEnum.TXFFC.getSumCount() - openCount;

        object.put("openCount",openCount);
        object.put("noOpenCount",noOpenCount);
        JSONObject objectAll = new JSONObject();
        lottery.put(CaipiaoTypeEnum.TXFFC.getTagType(),object);

        objectAll.put("data",lottery);
        objectAll.put("status",1);
        objectAll.put("time",new Date().getTime()/1000);
        objectAll.put("info","成功");
        String jsonString = objectAll.toJSONString();
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_TX_FFC,jsonString);
        XxlJobLogger.log(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_TX_FFC + "执行任务成功"+DateUtils.formatDate(new Date(),DateUtils.fullDatePattern)+","+jsonString);
    }

    /**
     * 根据上期期号生成下期期号
     *
     * @param issue 上期期号
     * @return
     */
    private String createNextIssue(String issue) {
        // 生成下一期期号
        String nextIssue = "";
        if (StringUtils.isBlank(issue)) {
            return nextIssue;
        }
        // 分割字符串
        String[] num = issue.split("-");
        // 判断是否已达最大值
        if ("1439".equals(num[1])) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(Objects.requireNonNull(DateUtils.parseDate(num[0], "yyyyMMdd")), 1L), "yyyyMMdd");
            nextIssue = prefix + "-0000";
        } else {
            StringBuilder next = new StringBuilder(Long.toString(Long.parseLong(num[1]) + 1));
            while (next.length() < 4) {
                next.insert(0, "0");
            }
            nextIssue = num[0] + "-" + next;
        }
        return nextIssue;
    }

    @Override
    public void dxdsSgCount() {
        String date = DateUtils.formatDate(DateUtils.getDayAfter(new Date(), -1L), DateUtils.datePattern);
        // 获取昨天的赛果记录
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        Calendar calendar = Calendar.getInstance();
        criteria.andWanIsNotNull();
        criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(calendar.getTime(),DateUtils.datePattern));
        calendar.add(Calendar.DAY_OF_MONTH,1);
        criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(),DateUtils.datePattern));
        List<TxffcLotterySg> txffcLotterySgs = this.txffcLotterySgMapper.selectByExample(example);

        if (txffcLotterySgs != null && txffcLotterySgs.size() > 0) {
            // 对赛果进行统计
            TxffcCountSgdxds txffcCountSgdxds = TxffcUtils.countDxds(txffcLotterySgs);
            txffcCountSgdxds.setDate(new Date());
            //根据日期判断是否已录入数据库
            //大小统计
            this.txffcCountSgdxdsMapper.insertSelective(txffcCountSgdxds);
        }
    }

    @Override
    public void dxdsSgCountLatelyTwoMonth() {   //统计最近两个月的数据,补历史数据
        try{
            for(int i = 1; i <= 60;i++){
                //查看当天有没有统计信息，
                TxffcCountSgdxdsExample txffcCountSgdxdsExample = new TxffcCountSgdxdsExample();
                TxffcCountSgdxdsExample.Criteria dxdsCriteria = txffcCountSgdxdsExample.createCriteria();
                Calendar countCalendar = Calendar.getInstance();
                countCalendar.set(Calendar.HOUR_OF_DAY,0);
                countCalendar.set(Calendar.MINUTE,0);
                countCalendar.set(Calendar.SECOND,0);
                countCalendar.add(Calendar.DAY_OF_MONTH,i*-1);
                dxdsCriteria.andDateGreaterThanOrEqualTo(countCalendar.getTime());
                Calendar countCalendarAddOneday = countCalendar;
                countCalendarAddOneday.add(Calendar.DAY_OF_MONTH,1);
                dxdsCriteria.andDateLessThan(countCalendarAddOneday.getTime());
                List<TxffcCountSgdxds> sgdxdsList = txffcCountSgdxdsMapper.selectByExample(txffcCountSgdxdsExample);
                if(sgdxdsList.size() > 0){
                    continue;
                }
                // 获取昨天的赛果记录
                TxffcLotterySgExample example = new TxffcLotterySgExample();
                TxffcLotterySgExample.Criteria criteria = example.createCriteria();
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
                List<TxffcLotterySg> txffcLotterySgs = this.txffcLotterySgMapper.selectByExample(example);

                if (txffcLotterySgs != null && txffcLotterySgs.size() > 0) {
                    // 对赛果进行统计
                    TxffcCountSgdxds txffcCountSgdxds = TxffcUtils.countDxds(txffcLotterySgs);
                    txffcCountSgdxds.setDate(calendar.getTime());
                    //根据日期判断是否已录入数据库
                    //大小统计
                    this.txffcCountSgdxdsMapper.insertSelective(txffcCountSgdxds);
                }
            }
        }catch (Exception e){
            logger.error("比特币分分彩赛果统计出错：{}",e);
        }

    }

    /**
     * 采集比特币分分彩数据 从火币网采集
     * 网址：https://api.huobi.pro/market/history/kline?symbol=btcusdt&period=1min&size=40
     */
    public List<LotterySgModel> collectionDataHuobi() throws IOException {
        List<LotterySgModel> results = new ArrayList<>();
        String url = "https://api.huobi.pro/market/history/kline?symbol=btcusdt&period=1min&size=40";
        Document doc = Jsoup.connect(url).get();
        Element body = doc.body();
        JSONObject json = JSONObject.parseObject(body.text());
        JSONArray jsonArray = json.getJSONArray("data");
        LotterySgModel sgModel;
        // 遍历第三方开奖结果
        for (int i = 0;i < jsonArray.size();i++) {
            Calendar calendar = Calendar.getInstance();
            sgModel = new LotterySgModel();
            // 获取价格
            JSONObject jsonSingle = JSONObject.parseObject(jsonArray.getString(i));
            Double price = jsonSingle.getDouble("close");
//
//            // 计算开奖号码
//            String wan = numberDoc.child(0).text().trim();
//            String qian = numberDoc.child(1).text().trim();
//            String bai = numberDoc.child(2).text().trim();
//            String shi = numberDoc.child(3).text().trim();
//            String ge = numberDoc.child(4).text().trim();

//            sgModel.setSg(wan + "," + qian + "," + bai + "," + shi + "," + ge);
            calendar.add(Calendar.MINUTE,i*-1);
            String time = DateUtils.formatDate(calendar.getTime(),DateUtils.FORMAT_YYYY_MM_DD_HHMM);
            sgModel.setSg(price.toString());
            sgModel.setDate(time);
//            sgModel.setIssue(issue);
            results.add(sgModel);
        }
        return results;
    }

    /**
     * 采集比特币分分彩数据 从币安网采集
     * 网址：https://api.huobi.pro/market/history/kline?symbol=btcusdt&period=1min&size=40
     */
    public List<LotterySgModel> collectionDataBian() throws IOException {
        List<LotterySgModel> results = new ArrayList<>();
        String url = "https://www.binance.com/api/v1/klines?symbol=BTCUSDT&interval=1m&limit=100";
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        Element body = doc.body();
        String message = body.text();
        String sgArray[] = message.split("],");
        List<String> sgList = new ArrayList<>();
        for(String sg:sgArray){
            sg = sg.replace("]","").replace("[","");
            sgList.add(sg);
        }

        // 遍历第三方开奖结果
        for (int i = 0;i < sgList.size();i++) {
            LotterySgModel sgModel = new LotterySgModel();
            // 获取价格
            String data[] = sgList.get(i).split(",");
            Date d = new Date();
            d.setTime(Long.valueOf(data[0]));
            sgModel.setDate(DateUtils.formatDate(d,DateUtils.FORMAT_YYYY_MM_DD_HHMM));
            sgModel.setSg(data[4]);
            results.add(sgModel);
        }
        return results;
    }

//    public static void main(String[] args) {
//        Date d = new Date();
//        d.setTime(1570415460000l);
//        System.out.println(DateUtils.formatDate(d,DateUtils.FORMAT_YYYY_MM_DD_HHMM));
//
//    }

//    /**
//     * 采集分分彩数据【数据较稳定，开奖速度快，目前没发现遗漏】
//     * 网址：https://www.qqff6.com/History/LoadHistory?count=15&showType=0&r=1543552504517
//     */
//    @Transactional
//    public List<LotterySgModel> collectionData1() throws IOException {
//        List<LotterySgModel> results = new ArrayList<>();
//        String url = "https://www.qqff6.com/History/LoadHistory?count=15&showType=0&r=" + System.currentTimeMillis();
//        Document doc = Jsoup.connect(url).get();
//        Elements elements = doc.select("span");
//        LotterySgModel sgModel;
//        // 遍历第三方开奖结果
//        for (int i = elements.size() / 4 - 1; i >= 0; i--) {
//            sgModel = new LotterySgModel();
//            // 期号标签
//            Element issueDoc = elements.get(i * 4);
//            // 开奖号码标签
//            Element numberDoc = elements.get(i * 4 + 1);
//            // 期号
//            String issue = issueDoc.text().replace(" ", "-").trim();
//            // 开奖号码
//            String wan = numberDoc.child(0).text().trim();
//            String qian = numberDoc.child(1).text().trim();
//            String bai = numberDoc.child(2).text().trim();
//            String shi = numberDoc.child(3).text().trim();
//            String ge = numberDoc.child(4).text().trim();
//
//            sgModel.setSg(wan + "," + qian + "," + bai + "," + shi + "," + ge);
//            sgModel.setIssue(issue);
//            results.add(sgModel);
//        }
//        return results;
//    }

    /**
     * 采集分分彩数据【备用】【有时候不稳定】
     * 网址：http://www.off0.com/index.php
     */
    @Transactional
    public List<LotterySgModel> collectionData2() throws IOException {
        List<LotterySgModel> results = new ArrayList<>();
        Document doc = Jsoup.connect("http://www.off0.com/index.php").get();
        // 找到相应的class标签
        Element element = doc.select(".gridview").get(0);
        // 抓取最近15期开奖结果数据【数据来源方】
        Elements elements = element.child(0).children();
        LotterySgModel sgModel;
        // 遍历第三方开奖结果
        for (int i = elements.size() - 1; i > 0; i--) {
            sgModel = new LotterySgModel();
            Element e = elements.get(i);
            // 获取期号
            String issue = e.child(1).text();

            // 获取其他开奖信息
            String time = e.child(2).text();
            String numberOnline = e.child(3).text();
            String openNumber = e.child(4).text();
            String diffValue = e.child(5).text();

            sgModel.setSg(openNumber);
            sgModel.setIssue(issue);
            sgModel.setDate(time);
            results.add(sgModel);
        }
        return results;
    }

    /**
     * 采集分分彩数据【实时，稳定】【会获取次数多了会被屏蔽】
     * http://www.tx-ffc.com/info-kj/kj_all.html
     */
    @Transactional
    public List<LotterySgModel> collectionData3() throws IOException {
        List<LotterySgModel> results = new ArrayList<>();
        Document doc = Jsoup.connect("http://www.tx-ffc.com/info-kj/kj_all.html").get();
        // 找到相应的class标签
        Element element = doc.select(".kj-list").get(0);
        // 抓取最近15期开奖结果数据【数据来源方】
        Element tbody = element.select("tbody").get(1);
        Elements elements = tbody.children();

        LotterySgModel sgModel;
        // 遍历第三方开奖结果
        for (int i = elements.size() - 1; i >= 0; i--) {
            sgModel = new LotterySgModel();
            Element e = elements.get(i);
            String issue = e.child(0).child(0).text() + "-" + e.child(1).text();//期号

            Elements numElements = e.child(2).children();
            StringBuilder number = new StringBuilder();
            for (int j = 0; j < numElements.size(); j++) {
                if (j > 0) {
                    number.append(",");
                }
                number.append(numElements.get(j).text());
            }
            sgModel.setSg(number.toString());
            sgModel.setIssue(issue);
            results.add(sgModel);
        }
        return results;
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    private TxffcLotterySg queryNextSg() {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        return txffcLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 根据期号区间查询所有遗漏数据【近1440期】
     *
     * @param startIssue 开始期号【不包括】
     * @param endIssue   结束期号【包括】
     * @return
     */
    private List<TxffcLotterySg> queryOmittedData(String startIssue, String endIssue) {
        TxffcLotterySgExample example = new TxffcLotterySgExample();
        TxffcLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(startIssue);
        criteria.andIssueLessThanOrEqualTo(endIssue);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(1440);
        return txffcLotterySgMapper.selectByExample(example);
    }

}
