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
import com.caipiao.task.server.service.CqsscTaskService;
import com.caipiao.task.server.util.CommonService;
import com.mapper.CqsscKillNumberMapper;
import com.mapper.CqsscLotterySgMapper;
import com.mapper.CqsscRecommendMapper;
import com.mapper.domain.*;
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

@Service
public class CqsscTaskServiceImpl extends CommonServiceImpl implements CqsscTaskService {
    private static final Logger logger = LoggerFactory.getLogger(CqsscTaskServiceImpl.class);

    @Autowired
    private CqsscLotterySgMapper cqsscLotterySgMapper;
    @Autowired
    private CqsscRecommendMapper cqsscRecommendMapper;
    @Autowired
    private CqsscKillNumberMapper cqsscKillNumberMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private CqsscTaskService cqsscTaskService;
    @Autowired
    private CommonService commonService;

    /**
     * ????????????????????????
     */
    @Override
    @Transactional
    public void addCqsscPrevSg() {
        // ???????????????
        int count = 59;
        // ????????????????????????????????????
        CqsscLotterySgExample sgExample = new CqsscLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        CqsscLotterySg lastSg = cqsscLotterySgMapper.selectOneByExample(sgExample);

        // ????????????????????????
        String idealTime = lastSg.getIdealTime();
        // ?????????????????????Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // ??????????????????
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(8));

        CqsscLotterySg sg;
        JSONArray jsonArray = new JSONArray();
        if (current < count) {
            for (int i = current; i < count; i++) {
                sg = new CqsscLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                sg.setDate(lastSg.getDate());
                dateTime = this.nextIssueTime(dateTime);
                sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
                sg.setOpenStatus("WAIT");

                CqsscLotterySg targetSg = new CqsscLotterySg();
                BeanUtils.copyProperties(sg, targetSg);

                if (targetSg.getCpkNumber() == null) {
                    targetSg.setCpkNumber("");
                }
                if (targetSg.getKcwNumber() == null) {
                    targetSg.setKcwNumber("");
                }
                jsonArray.add(targetSg);
                cqsscLotterySgMapper.insertSelective(sg);
                //????????????????????????add???????????????????????????????????????


            }
        }

        if (lastSg.getDate().compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_YUQI_DATA, "CqsscLotterySg:" + jsonString);
            }

            return;
        }

        for (int i = 1; i <= count; i++) {
            sg = new CqsscLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            sg.setDate(issue.substring(0, 4) + "-" + issue.substring(4, 6) + "-" + issue.substring(6, 8));
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
            sg.setOpenStatus("WAIT");

            CqsscLotterySg targetSg = new CqsscLotterySg();
            BeanUtils.copyProperties(sg, targetSg);
            if (targetSg.getCpkNumber() == null) {
                targetSg.setCpkNumber("");
            }
            if (targetSg.getKcwNumber() == null) {
                targetSg.setKcwNumber("");
            }
            jsonArray.add(targetSg);
            cqsscLotterySgMapper.insertSelective(sg);
        }

        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_YUQI_DATA, "CqsscLotterySg:" + jsonString);
//        List<CqsscRecommend> list =  JSONObject.parseArray(jsonString,CqsscRecommend.class);
    }

    /**
     * getBywSg
     * ????????????
     */
    @Override
    public void addCqsscSg() {

        Future<List<LotterySgModel>> cpk = threadPool.submit(new ThreadTaskCpk("cqssc", 15));
        Future<List<LotterySgModel>> kcw = threadPool.submit(new ThreadTaskKcw("cqssc", 15));
        Future<List<LotterySgModel>> byw = threadPool.submit(new ThreadTaskByw("cqssc", 15));

        LotterySgResult lotterySgResult = obtainSgResult(cpk, kcw, byw);
        Map<String, LotterySgModel> cpkMap = lotterySgResult.getCpk();
        Map<String, LotterySgModel> kcwMap = lotterySgResult.getKcw();
        Map<String, LotterySgModel> bywMap = lotterySgResult.getByw();

        // ???????????????15???????????????
        CqsscLotterySgExample sgExample = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("`ideal_time` desc");
        List<CqsscLotterySg> sgList = cqsscLotterySgMapper.selectByExample(sgExample);

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        int i = 0;
        for (CqsscLotterySg sg : sgList) {
            String cpkNumber = "", kcwNumber = "", bywNumber = "", number;
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
                cpkNumber = cpkModel.getSg();
            }
            // ??????????????????????????????????????????????????????
            if (kcwModel != null) {
                kcwNumber = kcwModel.getSg();
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

            // ??????????????????
            boolean isPush = false;

            // ??????????????????????????????
            if (sg.getWan() == null) {
                String[] numbers = number.split(",");
                sg.setWan(Integer.valueOf(numbers[0]));
                sg.setQian(Integer.valueOf(numbers[1]));
                sg.setBai(Integer.valueOf(numbers[2]));
                sg.setShi(Integer.valueOf(numbers[3]));
                sg.setGe(Integer.valueOf(numbers[4]));
                sg.setTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
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

            if(StringUtils.isEmpty(sg.getCpkNumber()) && !StringUtils.isEmpty(sg.getBywNumber())){
                sg.setCpkNumber(sg.getBywNumber());
            }
            int count = 0;
            String jsonCqsscLotterySg = null;
            if (isPush) {
                count = cqsscLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonCqsscLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //??????????????????
                checkCqsscYuqiData();

                logger.info("????????????????????????????????????{}", issue);
                // ?????????????????????????????????????????????
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSC_CQ, "CQSSC:" + issue + ":" + number);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_LM, "CQSSC:" + issue + ":" + number + ":" + jsonCqsscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_DN, "CQSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_15, "CQSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_QZH, "CQSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_UPDATE_DATA, "CQSSC:" + issue + ":" + number);
                } catch (Exception e) {
                    logger.error("????????????????????????????????????{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", number);

                object.put("nextTime", nextIssueTime(DateUtils.parseDate(sg.getIdealTime(), DateUtils.fullDatePattern)).getTime() / 1000);
                object.put("nextIssue", createNextIssue(issue));

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(DateUtils.parseDate(sg.getIdealTime(), DateUtils.fullDatePattern));
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 30);
                startCal.set(Calendar.SECOND, 0);

                int openCount = Integer.valueOf(issue.substring(issue.length() - 2, issue.length()));
                int noOpenCount = CaipiaoSumCountEnum.CQSSC.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.CQSSC.getTagType(), object);
                String jsonString = ResultInfo.ok(lottery).toJSONString();
                try {
                    //???????????????????????????
                    if (i == 1) {
                        logger.info("TOPIC_APP_SSC_CQ???????????????{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_CQ, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("????????????????????????????????????{}", e);
                }
            }
        }
    }

    //??????????????????
    public void checkCqsscYuqiData() {
        try {
            //??????????????????????????????
            CqsscLotterySgExample sgExample = new CqsscLotterySgExample();
            CqsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = cqsscLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //?????????????????????10?????????????????????????????????
                cqsscTaskService.addCqsscPrevSg();
            }
            //??????????????????????????????
        } catch (Exception e) {
            logger.error("??????????????????????????????????????????{}", e);
        }
    }

    /**
     * ????????????
     */
    @Override
    @Transactional
    public void addCqsscRecommend() {
        // ????????????????????????????????????????????????
        CqsscRecommendExample recommendExample = new CqsscRecommendExample();
        recommendExample.setOrderByClause("`issue` desc");
        CqsscRecommend lastRecommend = cqsscRecommendMapper.selectOneByExample(recommendExample);

        // ???????????????????????????
        CqsscLotterySg nextSg = commonService.cqsscQueryNextSg();

        // ??????????????????
        List<CqsscLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????????????????????????????
        CqsscRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new CqsscRecommend();
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
            cqsscRecommendMapper.insertSelective(recommend);

            String jsonObject = JSON.toJSONString(recommend);
//            CqsscRecommend r = JSONObject.parseObject(jsonObject,CqsscRecommend.class);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_RECOMMEND_DATA, "CqsscRecommend:" + jsonObject);
        }
    }


    /**
     * ????????????
     */
    @Override
    @Transactional
    public void addCqsscGssh() {
        // ????????????????????????????????????????????????
        CqsscKillNumberExample killNumberExample = new CqsscKillNumberExample();
        killNumberExample.setOrderByClause("`issue` DESC");
        CqsscKillNumber killNumber = cqsscKillNumberMapper.selectOneByExample(killNumberExample);

        // ???????????????????????????
        CqsscLotterySg nextSg = commonService.cqsscQueryNextSg();

        // ??????????????????
        List<CqsscLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????
        CqsscKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            nextKillNumber = new CqsscKillNumber();

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
            cqsscKillNumberMapper.insertSelective(nextKillNumber);

            String jsonObject = JSON.toJSONString(nextKillNumber);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_kill_DATA, "CqsscKillNumber:" + jsonObject);
        }
    }

    @Override
    public void sendMessageChangeIssue() {
        //???????????????????????????????????????
        //??????????????? 0 30/20 7-23,0-3 * * ?
        //???????????????  ???????????? 7:30-23:50 0:30-3:10 20???????????? ??????1-2?????????????????????
        // ??????????????????????????????   7:30-23:50    0:30-3:10
        Long time0 = null;  //??????????????????
        Long time1 = null;  //??????7???30
        Long time2 = null;  //??????23???55???  ??????5?????????????????????
        Long time3 = null;  //??????0???30
        Long time4 = null;  //??????3???15     ??????5?????????????????????

        Calendar cal = Calendar.getInstance();
        time0 = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 30);
        time1 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 55);
        time2 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 30);
        time3 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 3);
        cal.set(Calendar.MINUTE, 15);
        time4 = cal.getTimeInMillis();

        boolean atTime = false;  //???????????????????????? 0 30/20 7-23,0-3 * * ?
        if ((time0 >= time1 && time0 <= time2) || (time0 >= time3 && time0 <= time4)) {
            atTime = true;
        }
        if (!atTime) return;

        JSONObject object = new JSONObject();
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        CqsscLotterySg nextSg = cqsscLotterySgMapper.selectOneByExample(example);

        example = new CqsscLotterySgExample();
        criteria = example.createCriteria();
        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` desc");
        example.setOffset(0);
        example.setLimit(10);
        List<CqsscLotterySg> lastSgList = cqsscLotterySgMapper.selectByExample(example);

        JSONObject lottery = new JSONObject();
        object.put("nextTime", DateUtils.parseDate(nextSg.getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
        object.put("nextIssue", nextSg.getIssue());

        CqsscLotterySg lastSgTrue = null;
        for (CqsscLotterySg lastSg : lastSgList) {
            String lastNumber = StringUtils.isNotBlank(lastSg.getCpkNumber()) ? lastSg.getCpkNumber() : StringUtils.isNotBlank(lastSg.getKcwNumber()) ? lastSg.getKcwNumber() : "";
            if (StringUtils.isNotBlank(lastNumber)) {
                object.put("issue", lastSg.getIssue());
                object.put("number", lastNumber);
                lastSgTrue = lastSg;
                break;
            }
            continue;
        }


        int openCount = Integer.valueOf(lastSgTrue.getIssue().substring(8));
        int noOpenCount = CaipiaoSumCountEnum.CQSSC.getSumCount() - openCount;

        object.put("openCount", openCount);
        object.put("noOpenCount", noOpenCount);
        JSONObject objectAll = new JSONObject();
        lottery.put(CaipiaoTypeEnum.CQSSC.getTagType(), object);

        objectAll.put("data", lottery);
        objectAll.put("status", 1);
        objectAll.put("time", new Date().getTime() / 1000);
        objectAll.put("info", "??????");
        String jsonString = objectAll.toJSONString();
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_SSC_CQ, jsonString);
        XxlJobLogger.log(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_SSC_CQ + "??????????????????" + DateUtils.formatDate(new Date(), DateUtils.fullDatePattern) + "," + jsonString);

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
        if ("059".equals(num) || "120".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
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
        if ("23:50:00".equals(time)) {

            return DateUtils.getDayAfter(DateUtils.parseDate(date + " 00:30:00", DateUtils.fullDatePattern), 1L);
        }
        if ("03:10:00".equals(time)) {
            return DateUtils.parseDate(date + " 07:30:00", DateUtils.fullDatePattern);
        }

        dateTime = DateUtils.getMinuteAfter(lastTime, 20);

        return dateTime;
    }

    /**
     * ????????????????????????????????????????????????120??????
     *
     * @param startIssue ???????????????????????????
     * @param endIssue   ????????????????????????
     * @return
     */
    private List<CqsscLotterySg> queryOmittedData(String startIssue, String endIssue) {
        CqsscLotterySgExample example = new CqsscLotterySgExample();
        CqsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(startIssue);
        criteria.andIssueLessThanOrEqualTo(endIssue);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(59);
        return cqsscLotterySgMapper.selectByExample(example);
    }

}
