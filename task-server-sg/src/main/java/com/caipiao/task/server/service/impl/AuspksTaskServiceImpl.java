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
    // ???????????????
    @Autowired
    private AuspksKillNumberMapper auspksKillNumberMapper;//?????????
    @Autowired
    private AuspksCountSglhMapper auspksCountSglhMapper;//??????
    @Autowired
    private AuspksCountSgdxMapper auspksCountSgdxMapper;//??????
    @Autowired
    private AuspksCountSgdsMapper auspksCountSgdsMapper;//??????
    @Autowired
    private AuspksRecommendMapper auspksRecommendMapper;//????????????
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public void addAuspksSg() {
        // ????????????f1??????20?????????
        List<LotterySgModel> actResults = null;
        LotterySgModel yuqiActResult = null;
        //?????????????????????
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
            logger.error("??????????????????:{}", e);
        }


        // ??????????????????????????????????????????????????????
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

        // ?????????map??????
        Map<String, LotterySgModel> cpkMap = new HashMap<>();
        // ??????
        if (!CollectionUtils.isEmpty(actResults)) {
            for (LotterySgModel model : actResults) {
                cpkMap.put(model.getIssue(), model);
            }
        }


        // ???????????????20???????????????
        AusactLotterySgExample sgExample = new AusactLotterySgExample();
        AusactLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(20);
        sgExample.setOrderByClause("ideal_time desc");
        List<AusactLotterySg> sgList = ausactLotterySgMapper.selectByExample(sgExample);

        //???????????????????????????????????????????????????????????????????????????
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
                logger.info("??????ACT???????????????{}", thisSg.getIssue());
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
                logger.info("??????PKS???????????????{}", thisSg.getIssue());
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
                logger.info("??????????????????????????????{}", thisSg.getIssue());
            }
        }


        // ??????
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
            // ??????????????????
            LotterySgModel cpkModel = cpkMap.get(issue);

            // ??????
            if (cpkModel == null) {
                continue;
            }
            // ???????????????????????????????????????
            if (cpkModel != null) {
                number = cpkModel.getSg().replace(" ", "");
            }

            // ??????????????????????????????
            if (StringUtils.isBlank(number)) {
                continue;
            }

            // ??????????????????
            boolean isPush = false;

            // ??????????????????????????????
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
                logger.info("?????????act????????????ausact:{},{},{},{}", issue, number, resulString, sscResultString);
                // ????????????????????????PK10????????????
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_AUS_ACT, "AUSACT:" + issue + ":" + number + ":" + resulString);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_ACT_NAME, "AUSACT:" + issue + ":" + number + ":" + jsonAusactLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_SSC_NAME, "AUSSSC:" + issue + ":" + AusPksUtil.getAusSscbyAct(sg.getNumber()) + ":" + jsonAussscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_F1_NAME, "AUSF1:" + issue + ":" + resulString + ":" + jsonAuspksLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_F1_UPDATE_DATA, "AUSF1:" + issue + ":" + resulString);
                } catch (Exception e) {
                    logger.error("?????????????????????????????????{}", e);
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
                objectAll.put("info", "??????");
                String jsonString = objectAll.toJSONString();

                try {
                    //???????????????????????????
                    if (i == 1) {
                        logger.info("TOPIC_AUS_ACT???????????????{}", jsonString);
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
                    logger.error("????????????????????????????????????{}", e);
                }


//                // APP???????????????
//                String pushMsg = String.format("????????????????????????ACT???%s?????????????????????????????????%s", issue, number);
//                JPushClientUtil.sendPush("2", new String[]{Constants.KJ_AUSACT}, pushMsg, Constants.MSG_TYPE_OPENPUSH, "????????????");
//
//                // ?????????????????????????????????app???????????????
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
     * ????????????????????????????????????
     *
     * @param issue ????????????
     * @return
     */
    private String createNextIssue(String issue, Date dateTime) {
        // ?????????????????????
        String nextIssue;
        // ???????????????
        String num = issue.substring(8);
        String prefix = DateUtils.formatDate(nextIssueTime(dateTime), "yyyyMMdd");
        // ???????????????????????????
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
        // ????????????????????????????????????????????????
        AuspksRecommendExample recommendExample = new AuspksRecommendExample();
        recommendExample.setOrderByClause("`create_time` desc");
        AuspksRecommend lastRecommend = auspksRecommendMapper.selectOneByExample(recommendExample);
        String lastIssue = "";

        if (null != lastRecommend) {
            lastIssue = lastRecommend.getIssue();
        }
        // ???????????????????????????
        AuspksLotterySg nextSg = this.queryNextSg();
        String nextIssue = "";

        if (null != nextSg) {
            nextIssue = nextSg.getIssue();
        }
        // ??????????????????
        List<AuspksLotterySg> sgList = this.queryOmittedData(lastIssue, nextIssue);

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }
        // ????????????????????????????????????
        AuspksRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new AuspksRecommend();
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

            recommend.setCreateTime(new Date());
            // ??????????????????
            AuspksRecommendExample auspksRecommendExample = new AuspksRecommendExample();
            AuspksRecommendExample.Criteria criteria = auspksRecommendExample.createCriteria();
            criteria.andIssueEqualTo(recommend.getIssue());
            AuspksRecommend auspksRecommend = auspksRecommendMapper.selectOneByExample(auspksRecommendExample);
            if (auspksRecommend == null) {
                auspksRecommendMapper.insertSelective(recommend);
            }

            // ????????????
            String jsonObject = JSON.toJSONString(recommend);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_RECOMMEND_DATA, "AuspksRecommend:" + jsonObject);
        }
    }

    @Override
    @Transactional
    public void addAuspksKillNumber() {
        // ????????????????????????????????????????????????
        AuspksKillNumberExample killNumberExample = new AuspksKillNumberExample();
        killNumberExample.setOrderByClause("`create_time` DESC");
        AuspksKillNumber killNumber = auspksKillNumberMapper.selectOneByExample(killNumberExample);
        String killIssue = "";

        if (null != killNumber) {
            killIssue = killNumber.getIssue();
        }
        // ???????????????????????????
        AuspksLotterySg nextSg = this.queryNextSg();
        String nextIssue = "";

        if (null != nextSg) {
            nextIssue = nextSg.getIssue();
        }
        // ??????????????????
        List<AuspksLotterySg> sgList = this.queryOmittedData(killIssue, nextIssue);

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }
        // ????????????
        AuspksKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            AuspksLotterySg sg = sgList.get(i);
            // ???????????????????????????
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

            // ????????????
            String jsonObject = JSON.toJSONString(nextKillNumber);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_KILL_DATA, "AuspksKillNumber:" + jsonObject);
        }
    }

    @Override
    @Transactional
    public void addAuspksSgCount() {
        String date = DateUtils.formatDate(DateUtils.getDayAfter(new Date(), -1L), DateUtils.datePattern);
        // ???????????????????????????
        AuspksLotterySgExample example = new AuspksLotterySgExample();
        AuspksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andTimeLike(date + "%");
        List<AuspksLotterySg> auspksLotterySgs = this.auspksLotterySgMapper.selectByExample(example);

        if (auspksLotterySgs != null && auspksLotterySgs.size() > 0) {
            // ?????????????????????
            AuspksCountSgdx auspkscountSgdx = AuspksUtils.countSgDx(auspksLotterySgs);
            AuspksCountSgds auspksCountSgds = AuspksUtils.countSgDs(auspksLotterySgs);
            AuspksCountSglh auspksCountSglh = AuspksUtils.countSgLh(auspksLotterySgs);

            //??????????????????????????????????????????
            //????????????
            AuspksCountSgdxExample example1 = new AuspksCountSgdxExample();
            AuspksCountSgdxExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andTotalDateEqualTo(new Date());
            AuspksCountSgdx countSgdx1 = this.auspksCountSgdxMapper.selectOneByExample(example1);
            if (countSgdx1 == null) {
                //???????????????
                this.auspksCountSgdxMapper.insertSelective(auspkscountSgdx);
            } else {
                //?????????????????????
                this.auspksCountSgdxMapper.updateByExample(auspkscountSgdx, example1);
            }
            // ??????????????????
            String jsonObject = JSON.toJSONString(auspkscountSgdx);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_DX_TJ_DATA, "AuspksCountSgdx:" + jsonObject);
            //????????????
            AuspksCountSgdsExample example2 = new AuspksCountSgdsExample();
            AuspksCountSgdsExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andTotalDateEqualTo(new Date());
            AuspksCountSgds countSgds2 = this.auspksCountSgdsMapper.selectOneByExample(example2);
            if (countSgds2 == null) {
                //???????????????
                this.auspksCountSgdsMapper.insertSelective(auspksCountSgds);
            } else {
                //?????????????????????
                this.auspksCountSgdsMapper.updateByExample(auspksCountSgds, example2);
            }
            // ??????????????????
            jsonObject = JSON.toJSONString(auspksCountSgds);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_DS_TJ_DATA, "AuspksCountSgds:" + jsonObject);
            // ????????????
            AuspksCountSglhExample example3 = new AuspksCountSglhExample();
            AuspksCountSglhExample.Criteria criteria3 = example3.createCriteria();
            criteria3.andTotalDateEqualTo(new Date());
            AuspksCountSglh countSglh3 = this.auspksCountSglhMapper.selectOneByExample(example3);
            if (countSglh3 == null) {
                // ???????????????
                this.auspksCountSglhMapper.insertSelective(auspksCountSglh);
            } else {
                // ?????????????????????
                this.auspksCountSglhMapper.updateByExample(auspksCountSglh, example3);
            }
            // ??????????????????
            jsonObject = JSON.toJSONString(auspksCountSglh);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUSPKS_LH_TJ_DATA, "AuspksCountSglh:" + jsonObject);
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param lastTime ???????????????????????????
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
     * ???????????????????????????
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
     * ????????????????????????????????????????????????179??????
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
