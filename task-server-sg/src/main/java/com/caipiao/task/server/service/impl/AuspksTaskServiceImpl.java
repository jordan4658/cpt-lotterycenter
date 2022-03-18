package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.AppMianParamEnum;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.AuspksUtils;
import com.caipiao.core.library.tool.BjpksUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.NnAzOperationUtils;
import com.caipiao.core.library.tool.RandomUtil;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.AuspksTaskService;
import com.caipiao.task.server.util.AusPksUtil;
import com.caipiao.task.server.util.GetHttpsgUtil;
import com.mapper.AusactLotterySgMapper;
import com.mapper.AuspksCountSgdsMapper;
import com.mapper.AuspksCountSgdxMapper;
import com.mapper.AuspksCountSglhMapper;
import com.mapper.AuspksKillNumberMapper;
import com.mapper.AuspksLotterySgMapper;
import com.mapper.AuspksRecommendMapper;
import com.mapper.AussscLotterySgMapper;
import com.mapper.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class AuspksTaskServiceImpl implements AuspksTaskService {

    private static final Logger logger = LoggerFactory.getLogger(AuspksTaskServiceImpl.class);

    @Autowired
    private AusactLotterySgMapper ausactLotterySgMapper;
    @Autowired
    private AussscLotterySgMapper aussscLotterySgMapper;
    @Autowired
    private AuspksLotterySgMapper auspksLotterySgMapper;
    // 数据分析表
    @Autowired
    private AuspksKillNumberMapper auspksKillNumberMapper;//杀号表
    @Autowired
    private AuspksCountSglhMapper auspksCountSglhMapper;//龙虎
    @Autowired
    private AuspksCountSgdxMapper auspksCountSgdxMapper;//大小
    @Autowired
    private AuspksCountSgdsMapper auspksCountSgdsMapper;//单双
    @Autowired
    private AuspksRecommendMapper auspksRecommendMapper;//免费推荐
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public void addAuspksSg() {
        // 获取澳洲f1官方20期数据
        List<LotterySgModel> actResults = null;
        LotterySgModel yuqiActResult = null;
        //抓取到最新期号
        String firstIssue = null;
        try {

            AusactLotterySgExample ausactLotterySgExample = new AusactLotterySgExample();
            ausactLotterySgExample.setOrderByClause("ideal_time desc");
            AusactLotterySg ausactLotterySgNew = ausactLotterySgMapper.selectOneByExample(ausactLotterySgExample);

            actResults = GetHttpsgUtil.getAct(ausactLotterySgNew);
            if (actResults.size() == 0) {
                return;
            }

            yuqiActResult = actResults.get(actResults.size() - 1);

            JSONArray actJsonArray = new JSONArray();
            AusactLotterySgExample sgExample = new AusactLotterySgExample();
            AusactLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIssueEqualTo(yuqiActResult.getIssue());
            AusactLotterySg ausactLotterySg = ausactLotterySgMapper.selectOneByExample(sgExample);

            if (ausactLotterySg == null) {
                AusactLotterySg actSg = new AusactLotterySg();
                actSg.setIssue(yuqiActResult.getIssue());
                actSg.setIdealTime(yuqiActResult.getDate());
                ausactLotterySgMapper.insertSelective(actSg);
                firstIssue = yuqiActResult.getIssue();

                actJsonArray.add(actSg);
                String jsonString = JSONObject.toJSONString(actJsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_ACT_YUQI_DATA, "AusactLotterySg:" + jsonString);
            } else {
                ausactLotterySg.setIdealTime(yuqiActResult.getDate());
                firstIssue = yuqiActResult.getIssue();
                ausactLotterySgMapper.updateByPrimaryKeySelective(ausactLotterySg);
            }

            JSONArray sscJsonArray = new JSONArray();
            AussscLotterySgExample sgSscExample = new AussscLotterySgExample();
            AussscLotterySgExample.Criteria sscCriteria = sgSscExample.createCriteria();
            sscCriteria.andIssueEqualTo(yuqiActResult.getIssue());
            AussscLotterySg ausSscLotterySg = aussscLotterySgMapper.selectOneByExample(sgSscExample);
            if (ausSscLotterySg == null) {
                AussscLotterySg sscSg = new AussscLotterySg();
                sscSg.setIssue(yuqiActResult.getIssue());
                sscSg.setIdealTime(yuqiActResult.getDate());
                aussscLotterySgMapper.insertSelective(sscSg);

                sscJsonArray.add(sscSg);
                String jsonString = JSONObject.toJSONString(sscJsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_SSC_YUQI_DATA, "AussscLotterySg:" + jsonString);
            } else {
                ausSscLotterySg.setIdealTime(yuqiActResult.getDate());
                aussscLotterySgMapper.updateByPrimaryKeySelective(ausSscLotterySg);
            }

            JSONArray pksJsonArray = new JSONArray();
            AuspksLotterySgExample sgPksExample = new AuspksLotterySgExample();
            AuspksLotterySgExample.Criteria pksCriteria = sgPksExample.createCriteria();
            pksCriteria.andIssueEqualTo(yuqiActResult.getIssue());
            AuspksLotterySg auspksLotterySg = auspksLotterySgMapper.selectOneByExample(sgPksExample);
            if (auspksLotterySg == null) {
                AuspksLotterySg pksSg = new AuspksLotterySg();
                pksSg.setIssue(yuqiActResult.getIssue());
                pksSg.setIdealTime(yuqiActResult.getDate());
                auspksLotterySgMapper.insertSelective(pksSg);

                pksJsonArray.add(pksSg);
                String jsonString = JSONObject.toJSONString(pksJsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_PKS_YUQI_DATA, "AuspksLotterySg:" + jsonString);
            } else {
                auspksLotterySg.setIdealTime(yuqiActResult.getDate());
                auspksLotterySgMapper.updateByPrimaryKeySelective(auspksLotterySg);
            }

            actResults = actResults.subList(0, actResults.size() - 1);
        } catch (Exception e) {
            logger.error("获取赛果失败:{}", e);
        }


        // 判空（两个接口都没有获取到开奖结果）
        if (CollectionUtils.isEmpty(actResults)) {
            return;
        }

//        for (LotterySgModel sgmodel: actResults) {
//            AusactLotterySg sg=new AusactLotterySg();
//            sg.setIssue(sgmodel.getIssue());
//            sg.setNumber(sgmodel.getSg());
//            sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
//            sg.setOpenStatus("AUTO");
//            sg.setIdealTime(sgmodel.getOpenTime());
//            AusactLotterySgExample example=new AusactLotterySgExample();
//            example.createCriteria().andIssueEqualTo(sgmodel.getIssue());
//           // ausactLotterySgMapper.selectByExample(example);
//            if(ausactLotterySgMapper.selectOneByExample(example)==null){
//
//                ausactLotterySgMapper.insertSelective(sg);
//        }

        // 封装成map集合
        Map<String, LotterySgModel> cpkMap = new HashMap<>();
        // 判空
        if (!CollectionUtils.isEmpty(actResults)) {
            for (LotterySgModel model : actResults) {
                cpkMap.put(model.getIssue(), model);
            }
        }


        // 获取本地近20期开奖结果
        AusactLotterySgExample sgExample = new AusactLotterySgExample();
        AusactLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(20);
        sgExample.setOrderByClause("ideal_time desc");
        List<AusactLotterySg> sgList = ausactLotterySgMapper.selectByExample(sgExample);

        //如果抓到了赛果数据，在数据库中不存在，则插入数据库
        for (String issue : cpkMap.keySet()) {
            sgExample = new AusactLotterySgExample();
            AusactLotterySgExample.Criteria criteriaSingle = sgExample.createCriteria();
            criteriaSingle.andIssueEqualTo(issue);
            AusactLotterySg ausactLotterySg = ausactLotterySgMapper.selectOneByExample(sgExample);
            if (ausactLotterySg == null) {
                LotterySgModel thisModel = cpkMap.get(issue);
                AusactLotterySg thisSg = new AusactLotterySg();
                thisSg.setIssue(issue);
                thisSg.setNumber(thisModel.getSg());
                thisSg.setTime(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

                sgExample = new AusactLotterySgExample();
                AusactLotterySgExample.Criteria criteriaFirst = sgExample.createCriteria();
                criteriaFirst.andIssueEqualTo(firstIssue);
                AusactLotterySg ausactLotterySgFirst = ausactLotterySgMapper.selectOneByExample(sgExample);
                Long jiange = Long.valueOf(firstIssue) - Long.valueOf(issue);
                Calendar firstCalendar = Calendar.getInstance();
                firstCalendar.setTime(DateUtils.parseDate(ausactLotterySgFirst.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (jiange.intValue() < 0) {
                    continue;
                }
                firstCalendar.add(Calendar.SECOND, jiange.intValue() * -160);
                thisSg.setIdealTime(DateUtils.formatDate(firstCalendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                thisSg.setOpenStatus("AUTO");
                ausactLotterySgMapper.insert(thisSg);

                JSONArray actJsonArray = new JSONArray();
                actJsonArray.add(thisSg);
                String jsonString = JSONObject.toJSONString(actJsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_ACT_YUQI_DATA, "AusactLotterySg:" + jsonString);
                logger.info("澳洲ACT补赛果成功{}", thisSg.getIssue());
            }

            AuspksLotterySgExample auspksLotterySgExample = new AuspksLotterySgExample();
            AuspksLotterySgExample.Criteria auspksCriteria = auspksLotterySgExample.createCriteria();
            auspksCriteria.andIssueEqualTo(issue);
            AuspksLotterySg auspksLotterySg = auspksLotterySgMapper.selectOneByExample(auspksLotterySgExample);
            if (auspksLotterySg == null) {
                LotterySgModel thisModel = cpkMap.get(issue);
                AuspksLotterySg thisSg = new AuspksLotterySg();
                thisSg.setIssue(issue);
                String resulString = AusPksUtil.getAusPksbyAct(thisModel.getSg());
                if (!StringUtils.isEmpty(resulString)) {
                    resulString = resulString.replace("0", "10");
                    thisSg.setNumber(resulString);
                }
                thisSg.setTime(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

                auspksLotterySgExample = new AuspksLotterySgExample();
                AuspksLotterySgExample.Criteria criteriaFirst = auspksLotterySgExample.createCriteria();
                criteriaFirst.andIssueEqualTo(firstIssue);
                AuspksLotterySg auspksLotterySgFirst = auspksLotterySgMapper.selectOneByExample(auspksLotterySgExample);
                Long jiange = Long.valueOf(firstIssue) - Long.valueOf(issue);
                Calendar firstCalendar = Calendar.getInstance();
                firstCalendar.setTime(DateUtils.parseDate(auspksLotterySgFirst.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (jiange.intValue() < 0) {
                    continue;
                }
                firstCalendar.add(Calendar.SECOND, jiange.intValue() * -160);
                thisSg.setIdealTime(DateUtils.formatDate(firstCalendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                thisSg.setOpenStatus("AUTO");
                auspksLotterySgMapper.insert(thisSg);

                JSONArray pksJsonArray = new JSONArray();
                pksJsonArray.add(thisSg);
                String jsonString = JSONObject.toJSONString(pksJsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_PKS_YUQI_DATA, "AuspksLotterySg:" + jsonString);
                logger.info("澳洲PKS补赛果成功{}", thisSg.getIssue());
            }


            AussscLotterySgExample aussscLotterySgExample = new AussscLotterySgExample();
            AussscLotterySgExample.Criteria aussscCriteria = aussscLotterySgExample.createCriteria();
            aussscCriteria.andIssueEqualTo(issue);
            AussscLotterySg aussscLotterySg = aussscLotterySgMapper.selectOneByExample(aussscLotterySgExample);
            if (aussscLotterySg == null) {
                LotterySgModel thisModel = cpkMap.get(issue);
                AussscLotterySg thisSg = new AussscLotterySg();
                thisSg.setIssue(issue);
                thisSg.setNumber(AusPksUtil.getAusSscbyAct(thisModel.getSg()));
                thisSg.setTime(DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));

                aussscLotterySgExample = new AussscLotterySgExample();
                AussscLotterySgExample.Criteria criteriaFirst = aussscLotterySgExample.createCriteria();
                criteriaFirst.andIssueEqualTo(firstIssue);
                AussscLotterySg aussscLotterySgFirst = aussscLotterySgMapper.selectOneByExample(aussscLotterySgExample);
                Long jiange = Long.valueOf(firstIssue) - Long.valueOf(issue);
                Calendar firstCalendar = Calendar.getInstance();
                firstCalendar.setTime(DateUtils.parseDate(aussscLotterySgFirst.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                if (jiange.intValue() < 0) {
                    continue;
                }
                firstCalendar.add(Calendar.SECOND, jiange.intValue() * -160);
                thisSg.setIdealTime(DateUtils.formatDate(firstCalendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                thisSg.setOpenStatus("AUTO");
                aussscLotterySgMapper.insert(thisSg);

                JSONArray sscJsonArray = new JSONArray();
                sscJsonArray.add(thisSg);
                String jsonString = JSONObject.toJSONString(sscJsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_SSC_YUQI_DATA, "AussscLotterySg:" + jsonString);
                logger.info("澳洲时时彩补赛果成功{}", thisSg.getIssue());
            }
        }


        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        String number = "";
        String resulString = "";
        String sscResultString = "";
        int i = 0;
        for (AusactLotterySg sg : sgList) {
            i++;
            String issue = sg.getIssue();
            // 获取开奖结果
            LotterySgModel cpkModel = cpkMap.get(issue);

            // 判空
            if (cpkModel == null) {
                continue;
            }
            // 获取当前实际开奖期号与结果
            if (cpkModel != null) {
                number = cpkModel.getSg().replace(" ", "");
            }

            // 判断开奖号码是否为空
            if (StringUtils.isBlank(number)) {
                continue;
            }

            // 是否需要修改
            boolean isPush = false;

            // 判断是否需要修改赛果
            if (StringUtils.isBlank(sg.getNumber())) {
                sg.setNumber(number);
                sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
                sg.setOpenStatus("AUTO");
                isPush = true;
            }


            int count = 0;
            String jsonAusactLotterySg = null;
            String jsonAussscLotterySg = null;
            String jsonAuspksLotterySg = null;
            if (isPush) {
                count = ausactLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonAusactLotterySg = JSON.toJSONString(sg).replace(":", "$");

                AussscLotterySg sscsg = new AussscLotterySg();
                BeanUtils.copyProperties(sg, sscsg);
                sscsg.setNumber(AusPksUtil.getAusSscbyAct(sg.getNumber()));
                sscResultString = AusPksUtil.getAusSscbyAct(sg.getNumber());
                sscsg.setId(this.querysscsg(sg.getIssue()).getId());
                aussscLotterySgMapper.updateByPrimaryKeySelective(sscsg);
                jsonAussscLotterySg = JSON.toJSONString(sscsg).replace(":", "$");

                AuspksLotterySg pkssg = new AuspksLotterySg();
                BeanUtils.copyProperties(sg, pkssg);
                resulString = AusPksUtil.getAusPksbyAct(sg.getNumber());
                if (!StringUtils.isEmpty(resulString)) {
                    resulString = resulString.replace("0", "10");
                    pkssg.setNumber(resulString);
                }
                pkssg.setId(this.querypkssg(sg.getIssue()).getId());
                auspksLotterySgMapper.updateByPrimaryKeySelective(pkssg);
                jsonAuspksLotterySg = JSON.toJSONString(pkssg).replace(":", "$");
            }

            if (isPush && count > 0) {
                logger.info("【澳洲act】消息：ausact:{},{},{},{}", issue, number, resulString, sscResultString);
                // 将赛果推送到北京PK10相关队列
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_AUS_ACT, "AUSACT:" + issue + ":" + number + ":" + resulString);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_ACT_NAME, "AUSACT:" + issue + ":" + number + ":" + jsonAusactLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_SSC_NAME, "AUSSSC:" + issue + ":" + AusPksUtil.getAusSscbyAct(sg.getNumber()) + ":" + jsonAussscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_F1_NAME, "AUSF1:" + issue + ":" + resulString + ":" + jsonAuspksLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_F1_UPDATE_DATA, "AUSF1:" + issue + ":" + resulString);
                } catch (Exception e) {
                    logger.error("澳洲系列发送消息失败：{}", e);
                }
//
                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();

                object.put("issue", issue);
                object.put("number", number);


                object.put("nextTime", nextIssueTime(DateUtils.parseDate(sg.getIdealTime(), DateUtils.fullDatePattern)).getTime() / 1000);
                object.put("nextIssue", createNextIssue(issue, DateUtils.parseDate(sg.getIdealTime(), DateUtils.fullDatePattern)));

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(DateUtils.parseDate(sg.getIdealTime(), DateUtils.fullDatePattern));
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 1);
                startCal.set(Calendar.SECOND, 40);

                long jiange = DateUtils.timeReduce(sg.getIdealTime(), DateUtils.formatDate(startCal.getTime(), DateUtils.fullDatePattern));
                int openCount = (int) (jiange / 160) + 1;
                int noOpenCount = CaipiaoSumCountEnum.AZACT.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.AUSACT.getTagType(), object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data", lottery);
                objectAll.put("status", 1);
                objectAll.put("time", new Date().getTime() / 1000);
                objectAll.put("info", "成功");
                String jsonString = objectAll.toJSONString();

                try {
                    //每次只发送最新一条
                    if (i == 1) {
                        logger.info("TOPIC_AUS_ACT发送消息：{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_ACT, jsonString);

                        object.put("number", resulString);
                        lottery.put(CaipiaoTypeEnum.AUSPKS.getTagType(), object);
                        lottery.put(CaipiaoTypeEnum.AUSACT.getTagType(), null);
                        objectAll.put("data", lottery);
                        String f1JsonString = objectAll.toJSONString();
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_F1, f1JsonString);

                        object.put("number", AusPksUtil.getAusSscbyAct(sg.getNumber()));
                        lottery.put(CaipiaoTypeEnum.AUSSSC.getTagType(), object);
                        lottery.put(CaipiaoTypeEnum.AUSPKS.getTagType(), null);
                        objectAll.put("data", lottery);
                        String sscJsonString = objectAll.toJSONString();
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_SSC, sscJsonString);

                        String niuWinner = NnAzOperationUtils.getNiuWinner(resulString);
                        object.put(AppMianParamEnum.NIU_NIU.getParamEnName(), niuWinner);
                        object.put("number", resulString);
                        lottery.put(CaipiaoTypeEnum.AZNIU.getTagType(), object);
                        lottery.put(CaipiaoTypeEnum.AUSSSC.getTagType(), null);
                        objectAll.put("data", lottery);
                        sscJsonString = objectAll.toJSONString();
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_NN, sscJsonString);
                    }
                } catch (Exception e) {
                    logger.error("一分六合彩发送消息失败：{}", e);
                }


//                // APP端开奖推送
//                String pushMsg = String.format("尊敬的用户，澳洲ACT第%s期已开奖！开奖号码为：%s", issue, number);
//                JPushClientUtil.sendPush("2", new String[]{Constants.KJ_AUSACT}, pushMsg, Constants.MSG_TYPE_OPENPUSH, "开奖通知");
//
//                // 开奖推送自定义消息（给app刷新列表）
//                JPushClientUtil.sendCustomePush(Constants.KJ_AUSACT);
            }
        }
    }

//    public static void main(String[] args) {
//        String sscSg = AusPksUtil.getAusSscbyAct("1,18,77,14,57,43,54,48,11,44,24,67,12,9,22,33,71,70,52,17");
//        System.out.println("sscSg:" + sscSg);
//
//        String resulString = AusPksUtil.getAusPksbyAct("1,18,77,14,57,43,54,48,11,44,24,67,12,9,22,33,71,70,52,17");
//        if(!StringUtils.isEmpty(resulString)) {
//            resulString = resulString.replace("0", "10");
//            System.out.println("pksSg:" + resulString);
//        }
//
//    }

    private List<LotterySgModel> getauspksList(List<LotterySgModel> actResults) {
        List<LotterySgModel> list = new ArrayList<LotterySgModel>();
        for (LotterySgModel sgModel : actResults) {
            String sg = this.getauspkssg(sgModel.getSg());

            //   sgModel.setSg(sg);
            list.add(sgModel);
        }
        return list;
    }

    private AussscLotterySg querysscsg(String issue) {
        AussscLotterySg ssc = new AussscLotterySg();
        AussscLotterySgExample e = new AussscLotterySgExample();
        e.createCriteria().andIssueEqualTo(issue);
        ssc = this.aussscLotterySgMapper.selectOneByExample(e);
        return ssc;
    }

    private AuspksLotterySg querypkssg(String issue) {
        AuspksLotterySg pks = new AuspksLotterySg();
        AuspksLotterySgExample e = new AuspksLotterySgExample();
        e.createCriteria().andIssueEqualTo(issue);
        pks = this.auspksLotterySgMapper.selectOneByExample(e);
        return pks;
    }

    private String getauspkssg(String sg) {
        String pkssg = "";
        String[] numarr = sg.split(",");

        return pkssg;
    }

    /**
     * 根据上期期号生成下期期号
     *
     * @param issue 上期期号
     * @return
     */
    private String createNextIssue(String issue, Date dateTime) {
        // 生成下一期期号
        String nextIssue;
        // 截取后三位
        String num = issue.substring(8);
        String prefix = DateUtils.formatDate(nextIssueTime(dateTime), "yyyyMMdd");
        // 判断是否已达最大值
        if ("999".equals(num)) {

            nextIssue = prefix + "000";
        } else {
            long next = Long.parseLong(num) + 1;
            nextIssue = prefix + String.format("%03d", next);
        }
        return nextIssue;
    }


    @Override
    @Transactional
    public void addAuspksRecommend() {
        // 获取当前最后一期【免费推荐】数据
        AuspksRecommendExample recommendExample = new AuspksRecommendExample();
        recommendExample.setOrderByClause("`create_time` desc");
        AuspksRecommend lastRecommend = auspksRecommendMapper.selectOneByExample(recommendExample);
        String lastIssue = "";

        if (null != lastRecommend) {
            lastIssue = lastRecommend.getIssue();
        }
        // 获取下一期期号信息
        AuspksLotterySg nextSg = this.queryNextSg();
        String nextIssue = "";

        if (null != nextSg) {
            nextIssue = nextSg.getIssue();
        }
        // 查询遗漏数据
        List<AuspksLotterySg> sgList = this.queryOmittedData(lastIssue, nextIssue);

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }
        // 循环生成【免费推荐】数据
        AuspksRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new AuspksRecommend();
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

            recommend.setCreateTime(new Date());
            // 保存生成数据
            AuspksRecommendExample auspksRecommendExample = new AuspksRecommendExample();
            AuspksRecommendExample.Criteria criteria = auspksRecommendExample.createCriteria();
            criteria.andIssueEqualTo(recommend.getIssue());
            AuspksRecommend auspksRecommend = auspksRecommendMapper.selectOneByExample(auspksRecommendExample);
            if (auspksRecommend == null) {
                auspksRecommendMapper.insertSelective(recommend);
            }

            // 同步数据
            String jsonObject = JSON.toJSONString(recommend);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_RECOMMEND_DATA, "AuspksRecommend:" + jsonObject);
        }
    }

    @Override
    @Transactional
    public void addAuspksKillNumber() {
        // 查询当前最后一期【公式杀号】数据
        AuspksKillNumberExample killNumberExample = new AuspksKillNumberExample();
        killNumberExample.setOrderByClause("`create_time` DESC");
        AuspksKillNumber killNumber = auspksKillNumberMapper.selectOneByExample(killNumberExample);
        String killIssue = "";

        if (null != killNumber) {
            killIssue = killNumber.getIssue();
        }
        // 获取下一期期号信息
        AuspksLotterySg nextSg = this.queryNextSg();
        String nextIssue = "";

        if (null != nextSg) {
            nextIssue = nextSg.getIssue();
        }
        // 查询遗漏数据
        List<AuspksLotterySg> sgList = this.queryOmittedData(killIssue, nextIssue);

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }
        // 生成杀号
        AuspksKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            AuspksLotterySg sg = sgList.get(i);
            // 生成下一期杀号信息
            nextKillNumber = AuspksUtils.getKillNumber(sg.getIssue());

            String time = sg.getIdealTime().substring(11);
            //Date openTime = DateUtils.parseDate(time, timePattern);
            nextKillNumber.setOpenTime(time);

            AuspksKillNumberExample auspksKillNumberExample = new AuspksKillNumberExample();
            AuspksKillNumberExample.Criteria criteria = auspksKillNumberExample.createCriteria();
            criteria.andIssueEqualTo(killNumber.getIssue());
            AuspksKillNumber auspksKillNumber = auspksKillNumberMapper.selectOneByExample(auspksKillNumberExample);
            if (auspksKillNumber == null) {
                auspksKillNumberMapper.insertSelective(nextKillNumber);
            }

            // 数据同步
            String jsonObject = JSON.toJSONString(nextKillNumber);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_KILL_DATA, "AuspksKillNumber:" + jsonObject);
        }
    }

    @Override
    @Transactional
    public void addAuspksSgCount() {
        String date = DateUtils.formatDate(DateUtils.getDayAfter(new Date(), -1L), DateUtils.datePattern);
        // 获取昨天的赛果记录
        AuspksLotterySgExample example = new AuspksLotterySgExample();
        AuspksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andTimeLike(date + "%");
        List<AuspksLotterySg> auspksLotterySgs = this.auspksLotterySgMapper.selectByExample(example);

        if (auspksLotterySgs != null && auspksLotterySgs.size() > 0) {
            // 对赛果进行统计
            AuspksCountSgdx auspkscountSgdx = AuspksUtils.countSgDx(auspksLotterySgs);
            AuspksCountSgds auspksCountSgds = AuspksUtils.countSgDs(auspksLotterySgs);
            AuspksCountSglh auspksCountSglh = AuspksUtils.countSgLh(auspksLotterySgs);

            //根据日期判断是否已录入数据库
            //大小统计
            AuspksCountSgdxExample example1 = new AuspksCountSgdxExample();
            AuspksCountSgdxExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andTotalDateEqualTo(new Date());
            AuspksCountSgdx countSgdx1 = this.auspksCountSgdxMapper.selectOneByExample(example1);
            if (countSgdx1 == null) {
                //没有就录入
                this.auspksCountSgdxMapper.insertSelective(auspkscountSgdx);
            } else {
                //已经录入就更新
                this.auspksCountSgdxMapper.updateByExample(auspkscountSgdx, example1);
            }
            // 大小数据同步
            String jsonObject = JSON.toJSONString(auspkscountSgdx);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_DX_TJ_DATA, "AuspksCountSgdx:" + jsonObject);
            //单双统计
            AuspksCountSgdsExample example2 = new AuspksCountSgdsExample();
            AuspksCountSgdsExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andTotalDateEqualTo(new Date());
            AuspksCountSgds countSgds2 = this.auspksCountSgdsMapper.selectOneByExample(example2);
            if (countSgds2 == null) {
                //没有就录入
                this.auspksCountSgdsMapper.insertSelective(auspksCountSgds);
            } else {
                //已经录入就更新
                this.auspksCountSgdsMapper.updateByExample(auspksCountSgds, example2);
            }
            // 单双数据同步
            jsonObject = JSON.toJSONString(auspksCountSgds);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_DS_TJ_DATA, "AuspksCountSgds:" + jsonObject);
            // 龙虎统计
            AuspksCountSglhExample example3 = new AuspksCountSglhExample();
            AuspksCountSglhExample.Criteria criteria3 = example3.createCriteria();
            criteria3.andTotalDateEqualTo(new Date());
            AuspksCountSglh countSglh3 = this.auspksCountSglhMapper.selectOneByExample(example3);
            if (countSglh3 == null) {
                // 没有就录入
                this.auspksCountSglhMapper.insertSelective(auspksCountSglh);
            } else {
                // 已经录入就更新
                this.auspksCountSglhMapper.updateByExample(auspksCountSglh, example3);
            }
            // 龙虎数据同步
            jsonObject = JSON.toJSONString(auspksCountSglh);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_LH_TJ_DATA, "AuspksCountSglh:" + jsonObject);
        }
    }

    /**
     * 获取下一期官方开奖时间
     *
     * @param lastTime 当前期官方开奖时间
     * @return
     */
    private Date nextIssueTime(Date lastTime) {
        Date dateTime;
        String date = DateUtils.formatDate(lastTime, "yyyy-MM-dd");
        String time = DateUtils.formatDate(lastTime, "HH:mm:ss");

        if ("02:30:40".equals(time)) {
            dateTime = DateUtils.parseDate(date + " 03:05:40", DateUtils.fullDatePattern);
        } else if ("02:36:20".equals(time)) {
            dateTime = DateUtils.parseDate(date + " 03:00:20", DateUtils.fullDatePattern);
        } else {
            dateTime = DateUtils.getSecondAfter(lastTime, 160);
        }
        return dateTime;
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    private AuspksLotterySg queryNextSg() {
        AuspksLotterySgExample example = new AuspksLotterySgExample();
        AuspksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        criteria.andOpenStatusEqualTo("WAIT");
        example.setOrderByClause("ideal_time ASC");
        return auspksLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 根据期号区间查询所有遗漏数据【近179期】
     *
     * @return
     */
    private List<AuspksLotterySg> queryOmittedData(String startIssue, String endIssue) {
        AuspksLotterySgExample example = new AuspksLotterySgExample();
        AuspksLotterySgExample.Criteria criteria = example.createCriteria();

//		if(!StringUtils.isEmpty(startIssue)) {
//			criteria.andIssueGreaterThan(startIssue);
//		}
//
//		if(!StringUtils.isEmpty(endIssue)) {
//			criteria.andIssueLessThanOrEqualTo(endIssue);
//		}
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(1);
        List<AuspksLotterySg> sgList = auspksLotterySgMapper.selectByExample(example);
        return sgList;
    }

}
