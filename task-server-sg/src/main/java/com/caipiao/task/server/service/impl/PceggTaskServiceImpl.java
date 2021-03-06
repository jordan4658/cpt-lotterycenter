package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.result.LotterySgResult;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.RandomUtil;
import com.caipiao.task.server.callable.ThreadTaskByw;
import com.caipiao.task.server.callable.ThreadTaskCpk;
import com.caipiao.task.server.callable.ThreadTaskKcw;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.PceggTaskService;
import com.caipiao.task.server.util.CommonService;
import com.mapper.PceggLotterySgMapper;
import com.mapper.PceggRecommendMapper;
import com.mapper.domain.PceggLotterySg;
import com.mapper.domain.PceggLotterySgExample;
import com.mapper.domain.PceggRecommend;
import com.mapper.domain.PceggRecommendExample;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author ShaoMing
 * @version 1.0.0
 * @date 2018/11/12 9:47
 */
@Service
@Transactional
public class PceggTaskServiceImpl extends CommonServiceImpl implements PceggTaskService {
    private static final Logger logger = LoggerFactory.getLogger(PceggTaskServiceImpl.class);

    @Autowired
    private PceggLotterySgMapper pceggLotterySgMapper;
    @Autowired
    private PceggRecommendMapper pceggRecommendMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private PceggTaskService pceggTaskService;
    @Autowired
    private CommonService commonService;

    @Override
    public void addPceggSg() {

        Future<List<LotterySgModel>> cpk = threadPool.submit(new ThreadTaskCpk("bjklb", 15));
        Future<List<LotterySgModel>> kcw = threadPool.submit(new ThreadTaskKcw("bjkl8", 15));
        Future<List<LotterySgModel>> byw = threadPool.submit(new ThreadTaskByw("pcdd", 15));

        LotterySgResult lotterySgResult = obtainSgResult(cpk, kcw, byw);
        Map<String, LotterySgModel> cpkMap = lotterySgResult.getCpk();
        Map<String, LotterySgModel> kcwMap = lotterySgResult.getKcw();
        Map<String, LotterySgModel> bywMap = lotterySgResult.getByw();

        // ?????????????????????????????????
        List<PceggLotterySg> sgList = this.queryRecent();
        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        String cpkNumber = "", kcwNumber = "", bywNumber = "", number;
        int i = 0;
        // ?????????????????????
        for (PceggLotterySg sg : sgList) {
            i++;
            String issue = sg.getIssue();

            // ??????????????????
            LotterySgModel cpkModel = cpkMap.get(issue);
            LotterySgModel kcwModel = kcwMap.get(issue);
            LotterySgModel bywModel = bywMap.get(issue);

//            //????????????????????? ????????????
//            int thisSgDataSize = TaskUtil.getNotNulSgModel(cpkModel,kcwModel,bywModel);
//            // ????????????????????? ?????? ?????? 2????????????
//            if (thisSgDataSize < 2) {
//                continue;
//            }

            // ??????????????????????????????????????????????????????
            if (cpkModel != null) {
                cpkNumber = this.countNumber(cpkModel.getSg().substring(0, 59));
            }
            // ??????????????????????????????????????????????????????
            if (kcwModel != null) {
                kcwNumber = this.countNumber(kcwModel.getSg().substring(0, 59));
            }
            // ??????????????????????????????????????????????????????
            if (bywModel != null) {
                bywNumber = bywModel.getSg();
            }

            // ????????????????????????
            number = TaskUtil.getTrueSg(cpkNumber, kcwNumber, bywNumber);
            if (StringUtils.isEmpty(number)) { //???????????????????????????
                continue;
            }

            // ??????????????????????????????
            boolean isPush = false;
            if (StringUtils.isBlank(sg.getNumber())) {
                sg.setNumber(number);
                sg.setTime(DateUtils.getTimeString(new Date(), DateUtils.fullDatePattern));
                sg.setOpenStatus("AUTO");
                isPush = true;
            }

            // ???????????????????????????????????????
            if (StringUtils.isBlank(sg.getCpkNumber()) && StringUtils.isNotBlank(cpkNumber)) {
                sg.setCpkNumber(cpkNumber);
            }
            // ???????????????????????????????????????
            if (StringUtils.isBlank(sg.getKcwNumber()) && StringUtils.isNotBlank(kcwNumber)) {
                sg.setKcwNumber(kcwNumber);
            }
            // ???????????????????????????????????????
            if (StringUtils.isBlank(sg.getBywNumber()) && StringUtils.isNotBlank(bywNumber)) {
                sg.setBywNumber(bywNumber);
            }

            // ???????????????????????????????????????
            if (StringUtils.isBlank(sg.getKcwNumber()) && StringUtils.isNotBlank(kcwNumber)) {
                sg.setKcwNumber(kcwNumber);
            }

            int count = 0;
            String jsonPceggLotterySg = null;
            if (isPush) {
                count = pceggLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonPceggLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //??????????????????
                checkPcggYuqiData();

                logger.info("???PC??????????????????{}", issue);
                // ??????????????????PC??????????????????
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_PCEGG, "PCDD:" + issue + ":" + number);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_TM, "PCDD:" + issue + ":" + number + ":" + jsonPceggLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_BZ, "PCDD:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_TMBS, "PCDD:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_SB, "PCDD:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_HH, "PCDD:" + issue + ":" + number);
                } catch (Exception e) {
                    logger.error("pc???????????????????????????{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", number);

                object.put("nextTime", DateUtils.parseDate(commonService.pceggQueryNextSg().getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
                object.put("nextIssue", commonService.pceggQueryNextSg().getIssue());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(DateUtils.parseDate(sg.getIdealTime(), DateUtils.fullDatePattern));
                startCal.set(Calendar.HOUR_OF_DAY, 9);
                startCal.set(Calendar.MINUTE, 5);
                startCal.set(Calendar.SECOND, 0);

                long jiange = DateUtils.timeReduce(sg.getIdealTime(), DateUtils.formatDate(startCal.getTime(), DateUtils.fullDatePattern));
                int openCount = (int) (jiange / 300) + 1;
                int noOpenCount = CaipiaoSumCountEnum.PCDAND.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.PCDAND.getTagType(), object);
                String jsonString = ResultInfo.ok(lottery).toJSONString();
                try {
                    //???????????????????????????
                    logger.info("pcegg?????????????????????{}", jsonString);
                    if (i == 1) {
                        logger.info("TOPIC_APP_PCEGG???????????????{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_PCEGG, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("pc???????????????????????????{}", e);
                }
            }
        }
    }

    //??????????????????
    public void checkPcggYuqiData() {
        try {
            //??????????????????????????????
            PceggLotterySgExample sgExample = new PceggLotterySgExample();
            PceggLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = pceggLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //?????????????????????10?????????????????????????????????
                pceggTaskService.addPceggPrevSg();
            }
            //??????????????????????????????
        } catch (Exception e) {
            logger.error("pc?????????????????????????????????{}", e);
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    private List<PceggLotterySg> queryRecent() {
        PceggLotterySgExample example = new PceggLotterySgExample();
        example.setOrderByClause("`ideal_time` desc");
        example.setOffset(0);
        example.setLimit(15);
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        criteria.andOpenStatusEqualTo("WAIT");
        return pceggLotterySgMapper.selectByExample(example);
    }

    /**
     * ???????????????????????????
     *
     * @return
     */
    private PceggLotterySg queryOpenIssue() {
        // ?????????????????????????????????
        PceggLotterySgExample example = new PceggLotterySgExample();
        example.setOrderByClause("`ideal_time` desc");
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        return pceggLotterySgMapper.selectOneByExample(example);
    }

    /**
     * ????????????????????????????????????PC??????????????????
     *
     * @param number ?????????????????????
     * @return
     */
    private String countNumber(String number) {
        if (StringUtils.isBlank(number)) {
            return "";
        }
        String[] numbers = number.split(",");
        int number1 = 0, number2 = 0, number3 = 0;
        for (int i = 0; i < numbers.length - 2; i++) {
            if (i < 6) {
                number1 += Integer.parseInt(numbers[i]);
            } else if (i < 12) {
                number2 += Integer.parseInt(numbers[i]);
            } else if (i < 18) {
                number3 += Integer.parseInt(numbers[i]);
            }
        }
        int num1 = number1 % 10;
        int num2 = number2 % 10;
        int num3 = number3 % 10;
        return num1 + "," + num2 + "," + num3;
    }

    @Override
    public void addPceggRecommend() {
        PceggRecommend recommend = new PceggRecommend();

        // ???????????????????????????
        PceggLotterySg sg = commonService.pceggQueryNextSg();
        String issue = sg.getIssue();

        // ???????????????????????????
        PceggRecommendExample e = new PceggRecommendExample();
        PceggRecommendExample.Criteria criteria = e.createCriteria();
        criteria.andIssueEqualTo(issue);
        List<PceggRecommend> pceggRecommends = pceggRecommendMapper.selectByExample(e);
        if (!CollectionUtils.isEmpty(pceggRecommends)) {
            return;
        }
        recommend.setIssue(issue);

        // ??????????????????????????? 5 ???
        String randomStr1 = RandomUtil.getRandomStringNoSame(5, 0, 10);
        recommend.setRegionOneNumber(randomStr1);
        // ????????????????????????|??????
        String[] str1 = randomStr1.split(",");
        recommend.setRegionOneSize(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
        recommend.setRegionOneSingle(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");

        // ??????????????????????????? 5 ???
        String randomStr2 = RandomUtil.getRandomStringNoSame(5, 0, 10);
        recommend.setRegionTwoNumber(randomStr2);
        // ????????????????????????|??????
        String[] str2 = randomStr2.split(",");
        recommend.setRegionTwoSize(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
        recommend.setRegionTwoSingle(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");

        // ??????????????????????????? 5 ???
        String randomStr3 = RandomUtil.getRandomStringNoSame(5, 0, 10);
        recommend.setRegionThreeNumber(randomStr3);
        // ????????????????????????|??????
        String[] str3 = randomStr3.split(",");
        recommend.setRegionThreeSize(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "???" : "???");
        recommend.setRegionThreeSingle(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "???" : "???");

        recommend.setCreateTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        pceggRecommendMapper.insertSelective(recommend);

        String jsonObject = JSON.toJSONString(recommend);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_RECOMMEND_DATA, "PceggRecommend:" + jsonObject);
    }

    @Override
    public void addPceggPrevSg() {
        int count = 179; // ???????????????
        // ?????????????????????????????????
        PceggLotterySgExample example = new PceggLotterySgExample();
        example.setOrderByClause("ideal_time desc");
        PceggLotterySg pceggLotterySg = pceggLotterySgMapper.selectOneByExample(example);
        // ????????????????????????
        String idealTime = pceggLotterySg.getIdealTime();
        String date = idealTime.substring(0, 10);
        String time = idealTime.substring(11);
        // ?????????????????????Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // ??????????????????
        String issue = pceggLotterySg.getIssue();

        JSONArray jsonArray = new JSONArray();
        // ????????????????????????????????????????????????????????????????????????????????????
        if (!"23:55:00".equals(time)) {
            // ???????????????????????????
            PceggLotterySgExample hasExample = new PceggLotterySgExample();
            PceggLotterySgExample.Criteria hasCriteria = hasExample.createCriteria();
            hasCriteria.andIdealTimeLike(date + "%");
            int openCount = pceggLotterySgMapper.countByExample(hasExample);
            PceggLotterySg sg;
            for (int i = 0; i < count - openCount; i++) {
                sg = new PceggLotterySg();
                sg.setIssue(Integer.toString(Integer.valueOf(issue) + i + 1));
                dateTime = DateUtils.getMinuteAfter(dateTime, 5);
                sg.setIdealTime(DateUtils.getTimeString(dateTime, DateUtils.fullDatePattern));

                PceggLotterySg targetSg = new PceggLotterySg();
                BeanUtils.copyProperties(sg, targetSg);
                jsonArray.add(targetSg);
                pceggLotterySgMapper.insertSelective(sg);

            }
            issue = Integer.toString(Integer.valueOf(issue) + count - openCount);
        }

        // ?????????????????????????????????????????????????????????
        int diffDays = DateUtils.diffDays(DateUtils.parseDate(idealTime, DateUtils.fullDatePattern), new Date());

        for (int i = 0; i < diffDays + 1; i++) {
            Date minuteAfter = DateUtils.getMinuteAfter(dateTime, 10);
            dateTime = DateUtils.setTime(minuteAfter, 9, 5, 0, 0);
            PceggLotterySg sg;
            for (int j = 0; j < 179; j++) {
                sg = new PceggLotterySg();
                sg.setIssue(Integer.toString(Integer.valueOf(issue) + j + 1));
                if (j > 0) {
                    dateTime = DateUtils.getMinuteAfter(dateTime, 5);
                }
                sg.setIdealTime(DateUtils.getTimeString(dateTime, DateUtils.fullDatePattern));

                PceggLotterySg targetSg = new PceggLotterySg();
                BeanUtils.copyProperties(sg, targetSg);
                jsonArray.add(targetSg);
                pceggLotterySgMapper.insertSelective(sg);

            }
            issue = Integer.toString(Integer.valueOf(issue) + count);
        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_YUQI_DATA, "PceggLotterySg:" + jsonString);
    }

    @Override
    public void sendMessageChangeIssue() {
        //???????????????????????????????????????
        //??????????????? 0 5/5 9-23 * * ?
        //???????????????  ???????????? 9:05-23:55 5???????????? ??????1-2?????????????????????
        // ??????????????????????????????   9:05-23:55
        Long time0 = null;  //??????????????????
        Long time1 = null;  //??????9???05
        Long time2 = null;  //??????23???56???  ??????1?????????????????????

        Calendar cal = Calendar.getInstance();
        time0 = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 5);
        time1 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 56);
        time2 = cal.getTimeInMillis();

        boolean atTime = false;  //????????????????????????  0 9/5 13-4 * * ?
        if (time0 >= time1 && time0 <= time2) {
            atTime = true;
        }
        if (!atTime) return;

        JSONObject object = new JSONObject();
        PceggLotterySgExample example = new PceggLotterySgExample();
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        PceggLotterySg nextSg = pceggLotterySgMapper.selectOneByExample(example);

        example = new PceggLotterySgExample();
        criteria = example.createCriteria();
        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` desc");
        example.setOffset(0);
        example.setLimit(10);
        List<PceggLotterySg> lastSgList = pceggLotterySgMapper.selectByExample(example);

        JSONObject lottery = new JSONObject();
        object.put("nextTime", DateUtils.parseDate(nextSg.getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
        object.put("nextIssue", nextSg.getIssue());

        PceggLotterySg lastSgTrue = null;
        for (PceggLotterySg lastSg : lastSgList) {
            String lastNumber = StringUtils.isNotBlank(lastSg.getCpkNumber()) ? lastSg.getCpkNumber() : StringUtils.isNotBlank(lastSg.getKcwNumber()) ? lastSg.getKcwNumber() : "";
            if (com.caipiao.core.library.tool.StringUtils.isNotBlank(lastNumber)) {
                object.put("issue", lastSg.getIssue());
                object.put("number", lastNumber);
                lastSgTrue = lastSg;
                break;
            }
            continue;
        }

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(DateUtils.parseDate(lastSgTrue.getIdealTime(), DateUtils.fullDatePattern));
        startCal.set(Calendar.HOUR_OF_DAY, 9);
        startCal.set(Calendar.MINUTE, 5);
        startCal.set(Calendar.SECOND, 0);

        long jiange = DateUtils.timeReduce(lastSgTrue.getIdealTime(), DateUtils.formatDate(startCal.getTime(), DateUtils.fullDatePattern));
        int openCount = (int) (jiange / 300) + 1;

        int noOpenCount = CaipiaoSumCountEnum.PCDAND.getSumCount() - openCount;

        object.put("openCount", openCount);
        object.put("noOpenCount", noOpenCount);
        JSONObject objectAll = new JSONObject();
        lottery.put(CaipiaoTypeEnum.PCDAND.getTagType(), object);

        objectAll.put("data", lottery);
        objectAll.put("status", 1);
        objectAll.put("time", new Date().getTime() / 1000);
        objectAll.put("info", "??????");
        String jsonString = objectAll.toJSONString();
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_PCEGG, jsonString);
        XxlJobLogger.log(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_PCEGG + "??????????????????" + DateUtils.formatDate(new Date(), DateUtils.fullDatePattern) + "," + jsonString);
    }


}
