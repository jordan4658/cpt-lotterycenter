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
import com.caipiao.task.server.service.TjsscTaskService;
import com.caipiao.task.server.util.CommonService;
import com.mapper.TjsscKillNumberMapper;
import com.mapper.TjsscLotterySgMapper;
import com.mapper.TjsscRecommendMapper;
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
public class TjsscTaskServiceImpl extends CommonServiceImpl implements TjsscTaskService {

    private static final Logger logger = LoggerFactory.getLogger(TjsscTaskServiceImpl.class);
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;
    @Autowired
    private TjsscRecommendMapper tjsscRecommendMapper;
    @Autowired
    private TjsscKillNumberMapper tjsscKillNumberMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private TjsscTaskService tjsscTaskService;
    @Autowired
    private CommonService commonService;

    /**
     * ????????????????????????
     */
    @Override
    @Transactional
    public void addTjsscPrevSg() {
        // ???????????????
        int count = 42;
        // ????????????????????????????????????
        TjsscLotterySgExample sgExample = new TjsscLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        TjsscLotterySg lastSg = tjsscLotterySgMapper.selectOneByExample(sgExample);

        // ????????????????????????
        String idealTime = lastSg.getIdealTime();
        // ?????????????????????Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // ??????????????????
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(8));

        TjsscLotterySg sg;

        JSONArray jsonArray = new JSONArray();
        if (current < count) {
            for (int i = current; i < count; i++) {
                sg = new TjsscLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                sg.setDate(lastSg.getDate());
                dateTime = this.nextIssueTime(dateTime);
                sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
                sg.setOpenStatus("WAIT");

                TjsscLotterySg targetSg = new TjsscLotterySg();
                BeanUtils.copyProperties(sg, targetSg);

                if (targetSg.getCpkNumber() == null) {
                    targetSg.setCpkNumber("");
                }
                if (targetSg.getKcwNumber() == null) {
                    targetSg.setKcwNumber("");
                }
                jsonArray.add(targetSg);
                tjsscLotterySgMapper.insertSelective(sg);
            }
        }

        if (lastSg.getDate().compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_YUQI_DATA, "TjsscLotterySg:" + jsonString);
            }
            return;
        }

        for (int i = 1; i <= count; i++) {
            sg = new TjsscLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            sg.setDate(issue.substring(0, 4) + "-" + issue.substring(4, 6) + "-" + issue.substring(6, 8));
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(DateUtils.formatDate(dateTime, DateUtils.fullDatePattern));
            sg.setOpenStatus("WAIT");

            TjsscLotterySg targetSg = new TjsscLotterySg();
            BeanUtils.copyProperties(sg, targetSg);

            if (targetSg.getCpkNumber() == null) {
                targetSg.setCpkNumber("");
            }
            if (targetSg.getKcwNumber() == null) {
                targetSg.setKcwNumber("");
            }
            jsonArray.add(targetSg);
            tjsscLotterySgMapper.insertSelective(sg);
        }

        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_YUQI_DATA, "TjsscLotterySg:" + jsonString);
    }

    /**
     * ???????????????????????????????????????
     */
    @Override
    public void addTjsscSg() {
        Future<List<LotterySgModel>> cpk = threadPool.submit(new ThreadTaskCpk("tjssc", 15));
        Future<List<LotterySgModel>> kcw = threadPool.submit(new ThreadTaskKcw("tjssc", 15));
        Future<List<LotterySgModel>> byw = threadPool.submit(new ThreadTaskByw("tjssc", 15));

        LotterySgResult lotterySgResult = obtainSgResult(cpk, kcw, byw);
        Map<String, LotterySgModel> cpkMap = lotterySgResult.getCpk();
        Map<String, LotterySgModel> kcwMap = lotterySgResult.getKcw();
        Map<String, LotterySgModel> bywMap = lotterySgResult.getByw();

        // ???????????????15???????????????
        TjsscLotterySgExample sgExample = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("`ideal_time` desc");
        List<TjsscLotterySg> sgList = tjsscLotterySgMapper.selectByExample(sgExample);

        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????????????????
        String cpkNumber = "", kcwNumber = "", bywNumber = "", number;
        int i = 0;
        for (TjsscLotterySg sg : sgList) {
            i++;
            String issue = sg.getIssue();
            // ??????????????????
            StringBuilder issuBuilder = new StringBuilder();
            issuBuilder.append(issue.substring(0,8)).append("0").append(issue.substring(8));
            LotterySgModel cpkModel = cpkMap.get(issuBuilder.toString());
            LotterySgModel kcwModel = kcwMap.get(issuBuilder.toString());
            LotterySgModel bywModel = bywMap.get(issuBuilder.toString());

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
            String jsontjsscLotterySg = null;
            if (isPush) {
                count = tjsscLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsontjsscLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //??????????????????
                checkTjsscYuqiData();

                logger.info("??????????????????????????????{}", issue);
                // ?????????????????????????????????????????????
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSC_TJ, "TJSSC:" + issue + ":" + number);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_LM, "TJSSC:" + issue + ":" + number + ":" + jsontjsscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_DN, "TJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_15, "TJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_QZH, "TJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_UPDATE_DATA, "TJSSC:" + issue + ":" + number);
                } catch (Exception e) {
                    logger.error("????????????????????????????????????{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", number);

                object.put("nextTime", DateUtils.parseDate(commonService.tjsscQueryNextSg().getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
                object.put("nextIssue", commonService.tjsscQueryNextSg().getIssue());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(DateUtils.parseDate(sg.getIdealTime(), DateUtils.fullDatePattern));
                startCal.set(Calendar.HOUR_OF_DAY, 9);
                startCal.set(Calendar.MINUTE, 20);
                startCal.set(Calendar.SECOND, 0);

                long jiange = DateUtils.timeReduce(sg.getIdealTime(), DateUtils.formatDate(startCal.getTime(), DateUtils.fullDatePattern));
                int openCount = (int) (jiange / 1200) + 1;
                int noOpenCount = CaipiaoSumCountEnum.TJSSC.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                JSONObject objectAll = new JSONObject();
                lottery.put(CaipiaoTypeEnum.TJSSC.getTagType(), object);

                objectAll.put("data", lottery);
                objectAll.put("status", 1);
                objectAll.put("time", new Date().getTime() / 1000);
                objectAll.put("info", "??????");
                String jsonString = objectAll.toJSONString();

                try {
                    //???????????????????????????
                    if (i == 1) {
                        logger.info("TOPIC_APP_SSC_TJ???????????????{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_TJ, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("????????????????????????????????????{}", e);
                }
            }
        }
    }

    //??????????????????
    public void checkTjsscYuqiData() {
        try {
            //??????????????????????????????
            TjsscLotterySgExample sgExample = new TjsscLotterySgExample();
            TjsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = tjsscLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //?????????????????????10?????????????????????????????????
                tjsscTaskService.addTjsscPrevSg();
            }
            //??????????????????????????????
        } catch (Exception e) {
            logger.error("????????????????????????????????????{}", e);
        }
    }

    /**
     * ????????????
     */
    @Override
    @Transactional
    public void addTjsscRecommend() {
        // ????????????????????????????????????????????????
        TjsscRecommendExample recommendExample = new TjsscRecommendExample();
        recommendExample.setOrderByClause("`issue` desc");
        TjsscRecommend lastRecommend = tjsscRecommendMapper.selectOneByExample(recommendExample);

        // ???????????????????????????
        TjsscLotterySg nextSg = commonService.tjsscQueryNextSg();
        // ??????????????????
        List<TjsscLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());
        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????????????????????????????
        TjsscRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new TjsscRecommend();
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
            tjsscRecommendMapper.insertSelective(recommend);

            String jsonObject = JSON.toJSONString(recommend);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_RECOMMEND_DATA, "TjsscRecommend:" + jsonObject);
        }
    }

    /**
     * ????????????
     */
    @Override
    @Transactional
    public void addTjsscGssh() {
        // ????????????????????????????????????????????????
        TjsscKillNumberExample killNumberExample = new TjsscKillNumberExample();
        killNumberExample.setOrderByClause("`issue` DESC");
        TjsscKillNumber killNumber = tjsscKillNumberMapper.selectOneByExample(killNumberExample);

        // ???????????????????????????
        TjsscLotterySg nextSg = commonService.tjsscQueryNextSg();
        // ??????????????????
        List<TjsscLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());
        // ??????
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // ????????????
        TjsscKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            nextKillNumber = new TjsscKillNumber();
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
            tjsscKillNumberMapper.insertSelective(nextKillNumber);

            String jsonObject = JSON.toJSONString(nextKillNumber);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_KILL_DATA, "TjsscKillNumber:" + jsonObject);
        }
    }

    @Override
    public void sendMessageChangeIssue() {
        //???????????????????????????????????????
        //??????????????? 0 20/20 9-23 * * ?
        //???????????????  ???????????? 9:20-23:00 20???????????? ??????1-2?????????????????????
        // ??????????????????????????????   9:20-23:00
        Long time0 = null;  //??????????????????
        Long time1 = null;  //??????9???20
        Long time2 = null;  //??????23???05???  ??????5?????????????????????

        Calendar cal = Calendar.getInstance();
        time0 = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 20);
        time1 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 5);
        time2 = cal.getTimeInMillis();

        boolean atTime = false;  //???????????????????????? 0 20/20 9-23 * * ?
        if ((time0 >= time1 && time0 <= time2)) {
            atTime = true;
        }
        if (!atTime) {
            return;
        }

        JSONObject object = new JSONObject();
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` ASC");
        TjsscLotterySg nextSg = tjsscLotterySgMapper.selectOneByExample(example);

        example = new TjsscLotterySgExample();
        criteria = example.createCriteria();
        criteria.andIdealTimeLessThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        example.setOrderByClause("`ideal_time` desc");
        example.setOffset(0);
        example.setLimit(10);
        List<TjsscLotterySg> lastSgList = tjsscLotterySgMapper.selectByExample(example);

        JSONObject lottery = new JSONObject();
        object.put("nextTime", DateUtils.parseDate(nextSg.getIdealTime(), DateUtils.fullDatePattern).getTime() / 1000);
        object.put("nextIssue", nextSg.getIssue());

        TjsscLotterySg lastSgTrue = null;
        for (TjsscLotterySg lastSg : lastSgList) {
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
        lottery.put(CaipiaoTypeEnum.TJSSC.getTagType(), object);
        String jsonString = ResultInfo.ok(lottery).toJSONString();
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_SSC_TJ, jsonString);
        XxlJobLogger.log(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_SSC_TJ + "??????????????????" + DateUtils.formatDate(new Date(), DateUtils.fullDatePattern) + "," + jsonString);
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
        if ("042".equals(num) || "42".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "01";
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
        if ("23:00:00".equals(time)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(date, "yyyy-MM-dd"), 1L), "yyyy-MM-dd");
            dateTime = DateUtils.parseDate(prefix + " 09:20:00", DateUtils.fullDatePattern);
        } else {
            dateTime = DateUtils.getMinuteAfter(lastTime, 20);
        }
        return dateTime;
    }

    /**
     * ????????????????????????????????????????????????96??????
     *
     * @param startIssue ???????????????????????????
     * @param endIssue   ????????????????????????
     * @return
     */
    private List<TjsscLotterySg> queryOmittedData(String startIssue, String endIssue) {
        TjsscLotterySgExample example = new TjsscLotterySgExample();
        TjsscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(startIssue);
        criteria.andIssueLessThanOrEqualTo(endIssue);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(42);
        return tjsscLotterySgMapper.selectByExample(example);
    }

}
