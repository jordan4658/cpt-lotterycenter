package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.vo.result.LotterySgResult;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.CaipiaoUtils;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.RandomUtil;
import com.caipiao.task.server.callable.ThreadTaskByw;
import com.caipiao.task.server.callable.ThreadTaskCpk;
import com.caipiao.task.server.callable.ThreadTaskKcw;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.XjsscTaskService;
import com.caipiao.task.server.util.CommonService;
import com.mapper.XjsscKillNumberMapper;
import com.mapper.XjsscLotterySgMapper;
import com.mapper.XjsscRecommendMapper;
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
public class XjsscTaskServiceImpl extends CommonServiceImpl implements XjsscTaskService {

    private static final Logger logger = LoggerFactory.getLogger(XjsscTaskServiceImpl.class);

    @Autowired
    private XjsscLotterySgMapper xjsscLotterySgMapper;
    @Autowired
    private XjsscRecommendMapper xjsscRecommendMapper;
    @Autowired
    private XjsscKillNumberMapper xjsscKillNumberMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private XjsscTaskService xjsscTaskService;
    @Autowired
    private CommonService commonService;

    /**
     * ????????????????????????
     */
    @Override
    @Transactional
    public void addXjsscPrevSg() {
        // ???????????????
        int count = 48;
        // ????????????????????????????????????
        XjsscLotterySgExample sgExample = new XjsscLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        XjsscLotterySg lastSg = xjsscLotterySgMapper.selectOneByExample(sgExample);

        // ????????????????????????
        String idealTime = lastSg.getIdealTime();
        // ?????????????????????Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // ??????????????????
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(8));

        JSONArray jsonArray = new JSONArray();
        XjsscLotterySg sg;
        if (current < count) {
            for (int i = current; i < count; i++) {
                sg = new XjsscLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                sg.setDate(lastSg.getDate());
                dateTime = CaipiaoUtils.nextIssueTime(dateTime);
                sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
                sg.setOpenStatus("WAIT");

                XjsscLotterySg targetSg = new XjsscLotterySg();
                BeanUtils.copyProperties(sg, targetSg);

                if (targetSg.getCpkNumber() == null) {
                    targetSg.setCpkNumber("");
                }
                if (targetSg.getKcwNumber() == null) {
                    targetSg.setKcwNumber("");
                }
                jsonArray.add(targetSg);
                xjsscLotterySgMapper.insertSelective(sg);
            }
        }

        if (lastSg.getDate().compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_YUQI_DATA, "XjsscLotterySg:" + jsonString);
            }
            return;
        }

        for (int i = 1; i <= count; i++) {
            sg = new XjsscLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            sg.setDate(issue.substring(0, 4) + "-" + issue.substring(4, 6) + "-" + issue.substring(6, 8));
            dateTime = CaipiaoUtils.nextIssueTime(dateTime);
            sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
            sg.setOpenStatus("WAIT");

            XjsscLotterySg targetSg = new XjsscLotterySg();
            BeanUtils.copyProperties(sg, targetSg);

            if (targetSg.getCpkNumber() == null) {
                targetSg.setCpkNumber("");
            }
            if (targetSg.getKcwNumber() == null) {
                targetSg.setKcwNumber("");
            }
            jsonArray.add(targetSg);
            xjsscLotterySgMapper.insertSelective(sg);
        }

        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_YUQI_DATA, "XjsscLotterySg:" + jsonString);
    }

    /**
     * system???????????????????????????????????????
     */
    @Override
    public void addXjsscSg() {
        Future<List<LotterySgModel>> cpk = threadPool.submit(new ThreadTaskCpk("xjssc", 15));
        Future<List<LotterySgModel>> kcw = threadPool.submit(new ThreadTaskKcw("xjssc", 15));
        Future<List<LotterySgModel>> byw = threadPool.submit(new ThreadTaskByw("xjssc", 15));

        LotterySgResult lotterySgResult = obtainSgResult(cpk, kcw, byw);
        Map<String, LotterySgModel> cpkMap = lotterySgResult.getCpk();
        Map<String, LotterySgModel> kcwMap = lotterySgResult.getKcw();
        Map<String, LotterySgModel> bywMap = lotterySgResult.getByw();

        // ???????????????15???????????????
        XjsscLotterySgExample sgExample = new XjsscLotterySgExample();
        XjsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("`ideal_time` desc");
        List<XjsscLotterySg> sgList = xjsscLotterySgMapper.selectByExample(sgExample);

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????????????????
        String cpkNumber = "", kcwNumber = "", bywNumber = "", number;
        int i = 0;
        for (XjsscLotterySg sg : sgList) {
            i++;
            String issue = sg.getIssue();
            // ??????????????????
            LotterySgModel cpkModel = cpkMap.get(issue);
            LotterySgModel kcwModel = kcwMap.get(issue.substring(0,8)+"0"+issue.substring(8,10));
            LotterySgModel bywModel = bywMap.get(issue.substring(0,8)+"0"+issue.substring(8,10));
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
            String jsonxjsscLotterySg = null;
            if (isPush) {
                count = xjsscLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonxjsscLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //??????????????????
                checkXjsscYuqiData();

                logger.info("??????????????????????????????{}", issue);
                // ?????????????????????????????????????????????
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSC_XJ, "XJSSC:" + issue + ":" + number);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_LM, "XJSSC:" + issue + ":" + number + ":" + jsonxjsscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_DN, "XJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_15, "XJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_QZH, "XJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_UPDATE_DATA, "XJSSC:" + issue + ":" + number);
                } catch (Exception e) {
                    logger.error("????????????????????????????????????{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", number);

                object.put("nextTime", DateUtils.parseDate(commonService.xjsscQueryNextSg().getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
                object.put("nextIssue", commonService.xjsscQueryNextSg().getIssue());

                int openCount = Integer.valueOf(issue.substring(8));
                int noOpenCount = CaipiaoSumCountEnum.XJSSC.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.XJSSC.getTagType(), object);
                String jsonString = ResultInfo.ok(lottery).toJSONString();
                try {
                    //???????????????????????????
                    if (i == 1) {
                        logger.info("TOPIC_APP_SSC_XJ???????????????{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_XJ, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("????????????????????????????????????{}", e);
                }
            }
        }
    }

    //??????????????????
    public void checkXjsscYuqiData() {
        try {
            //??????????????????????????????
            XjsscLotterySgExample sgExample = new XjsscLotterySgExample();
            XjsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = xjsscLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //?????????????????????10?????????????????????????????????
                xjsscTaskService.addXjsscPrevSg();
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
    public void addXjsscRecommend() {
        // ????????????????????????????????????????????????
        XjsscRecommendExample recommendExample = new XjsscRecommendExample();
        recommendExample.setOrderByClause("`issue` desc");
        XjsscRecommend lastRecommend = xjsscRecommendMapper.selectOneByExample(recommendExample);

        // ???????????????????????????
        XjsscLotterySg nextSg = commonService.xjsscQueryNextSg();

        // ??????????????????
        List<XjsscLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????????????????????????????
        XjsscRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new XjsscRecommend();
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
            xjsscRecommendMapper.insertSelective(recommend);

            String jsonObject = JSON.toJSONString(recommend);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_RECOMMEND_DATA, "XjsscRecommend:" + jsonObject);
        }
    }

    /**
     * ????????????
     */
    @Override
    @Transactional
    public void addXjsscGssh() {
        // ????????????????????????????????????????????????
        XjsscKillNumberExample killNumberExample = new XjsscKillNumberExample();
        killNumberExample.setOrderByClause("`issue` DESC");
        XjsscKillNumber killNumber = xjsscKillNumberMapper.selectOneByExample(killNumberExample);

        // ???????????????????????????
        XjsscLotterySg nextSg = commonService.xjsscQueryNextSg();

        // ??????????????????
        List<XjsscLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????
        XjsscKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            nextKillNumber = new XjsscKillNumber();

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
            xjsscKillNumberMapper.insertSelective(nextKillNumber);

            String jsonObject = JSON.toJSONString(nextKillNumber);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_KILL_DATA, "XjsscKillNumber:" + jsonObject);
        }
    }

    @Override
    public void sendMessageChangeIssue() {
        //???????????????????????????????????????
        //??????????????? 0 20/20 10-2 * * ?
        //???????????????  ???????????? 10:20-2:00 20???????????? ??????1-2?????????????????????
        // ??????????????????????????????   10:20-23:40    0:00-2:00
        Long time0 = null;  //??????????????????
        Long time1 = null;  //??????10???20
        Long time2 = null;  //??????23???45???  ??????5?????????????????????
        Long time3 = null;  //??????0???00
        Long time4 = null;  //??????2???05     ??????5?????????????????????

        Calendar cal = Calendar.getInstance();
        time0 = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 20);
        time1 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 45);
        time2 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        time3 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 2);
        cal.set(Calendar.MINUTE, 5);
        time4 = cal.getTimeInMillis();

        boolean atTime = false;  //???????????????????????? 0 20/20 10-2 * * ?
        if ((time0 >= time1 && time0 <= time2) || (time0 >= time3 && time0 <= time4)) {
            atTime = true;
        }
        if (!atTime) return;

        JSONObject object = new JSONObject();
        XjsscLotterySgExample example = new XjsscLotterySgExample();
        XjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        XjsscLotterySg nextSg = xjsscLotterySgMapper.selectOneByExample(example);

        example = new XjsscLotterySgExample();
        criteria = example.createCriteria();
        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` desc");
        example.setOffset(0);
        example.setLimit(10);
        List<XjsscLotterySg> lastSgList = xjsscLotterySgMapper.selectByExample(example);

        JSONObject lottery = new JSONObject();
        object.put("nextTime", DateUtils.parseDate(nextSg.getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
        object.put("nextIssue", nextSg.getIssue());

        XjsscLotterySg lastSgTrue = null;
        for (XjsscLotterySg lastSg : lastSgList) {
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
        int noOpenCount = CaipiaoSumCountEnum.XJSSC.getSumCount() - openCount;

        object.put("openCount", openCount);
        object.put("noOpenCount", noOpenCount);
        JSONObject objectAll = new JSONObject();
        lottery.put(CaipiaoTypeEnum.CQSSC.getTagType(), object);

        objectAll.put("data", lottery);
        objectAll.put("status", 1);
        objectAll.put("time", new Date().getTime() / 1000);
        objectAll.put("info", "??????");
        String jsonString = objectAll.toJSONString();
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_SSC_XJ, jsonString);
        XxlJobLogger.log(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_SSC_XJ + "??????????????????" + DateUtils.formatDate(new Date(), DateUtils.fullDatePattern) + "," + jsonString);
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
        if ("48".equals(num) || "96".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "01";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }


    /**
     * ????????????????????????????????????????????????96??????
     *
     * @param startIssue ???????????????????????????
     * @param endIssue   ????????????????????????
     * @return
     */
    private List<XjsscLotterySg> queryOmittedData(String startIssue, String endIssue) {
        XjsscLotterySgExample example = new XjsscLotterySgExample();
        XjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(startIssue);
        criteria.andIssueLessThanOrEqualTo(endIssue);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(48);
        return xjsscLotterySgMapper.selectByExample(example);
    }

}
