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
     * 录入预期赛果信息
     */
    @Override
    @Transactional
    public void addXjsscPrevSg() {
        // 一天总期数
        int count = 48;
        // 获取当前赛果最后一期数据
        XjsscLotterySgExample sgExample = new XjsscLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        XjsscLotterySg lastSg = xjsscLotterySgMapper.selectOneByExample(sgExample);

        // 获取理想开奖时间
        String idealTime = lastSg.getIdealTime();
        // 将字符串转化为Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // 最后一期期号
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
     * system录入赛果【调用第三方接口】
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

        // 获取本地近15期开奖结果
        XjsscLotterySgExample sgExample = new XjsscLotterySgExample();
        XjsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("`ideal_time` desc");
        List<XjsscLotterySg> sgList = xjsscLotterySgMapper.selectByExample(sgExample);

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 遍历更新赛果信息
        String cpkNumber = "", kcwNumber = "", bywNumber = "", number;
        int i = 0;
        for (XjsscLotterySg sg : sgList) {
            i++;
            String issue = sg.getIssue();
            // 获取开奖结果
            LotterySgModel cpkModel = cpkMap.get(issue);
            LotterySgModel kcwModel = kcwMap.get(issue.substring(0,8)+"0"+issue.substring(8,10));
            LotterySgModel bywModel = bywMap.get(issue.substring(0,8)+"0"+issue.substring(8,10));
//            //这一期赛果有值 的个数，
//            int thisSgDataSize = TaskUtil.getNotNulSgModel(cpkModel,kcwModel,bywModel);
//            // 当这期赛果数据 个数 小于 2，不执行
//            if (thisSgDataSize < 2) {
//                continue;
//            }

            // 获取【彩票控】当前实际开奖期号与结果
            if (cpkModel != null) {
                cpkNumber = cpkModel.getSg();
            }
            // 获取【开彩网】当前实际开奖期号与结果
            if (kcwModel != null) {
                kcwNumber = kcwModel.getSg();
            }
            // 获取【博易网】当前实际开奖期号与结果
            if (bywModel != null) {
                bywNumber = bywModel.getSg();
            }

            // 获取最终开奖结果
            number = TaskUtil.getTrueSg(cpkNumber, kcwNumber, bywNumber);
            if (StringUtils.isEmpty(number)) { //两个赛果相同才入库
                continue;
            }

            // 是否需要修改
            boolean isPush = false;

            // 判断是否需要修改赛果
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

            // 判断是否需要更新彩票控赛果
            if (StringUtils.isBlank(sg.getCpkNumber()) && StringUtils.isNotBlank(cpkNumber)) {
                sg.setCpkNumber(cpkNumber);
            }

            // 判断是否需要更新开彩网赛果
            if (StringUtils.isBlank(sg.getKcwNumber()) && StringUtils.isNotBlank(kcwNumber)) {
                sg.setKcwNumber(kcwNumber);
            }

            // 判断是否需要更新博易网赛果
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
                //检查预期数据
                checkXjsscYuqiData();

                logger.info("【新疆时时彩】消息：{}", issue);
                // 将赛果推送到重庆时时彩相关队列
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_SSC_XJ, "XJSSC:" + issue + ":" + number);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_LM, "XJSSC:" + issue + ":" + number + ":" + jsonxjsscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_DN, "XJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_15, "XJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_QZH, "XJSSC:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_UPDATE_DATA, "XJSSC:" + issue + ":" + number);
                } catch (Exception e) {
                    logger.error("新疆时时彩发送消息失败：{}", e);
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
                    //每次只发送最新一条
                    if (i == 1) {
                        logger.info("TOPIC_APP_SSC_XJ发送消息：{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_SSC_XJ, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("新疆时时彩发送消息失败：{}", e);
                }
            }
        }
    }

    //检查预期数据
    public void checkXjsscYuqiData() {
        try {
            //检查预期数据任务开始
            XjsscLotterySgExample sgExample = new XjsscLotterySgExample();
            XjsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = xjsscLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
                xjsscTaskService.addXjsscPrevSg();
            }
            //检查预期数据任务结束
        } catch (Exception e) {
            logger.error("新疆时时彩检查预期数据失败：{}", e);
        }
    }

    /**
     * 免费推荐
     */
    @Override
    @Transactional
    public void addXjsscRecommend() {
        // 获取当前最后一期【免费推荐】数据
        XjsscRecommendExample recommendExample = new XjsscRecommendExample();
        recommendExample.setOrderByClause("`issue` desc");
        XjsscRecommend lastRecommend = xjsscRecommendMapper.selectOneByExample(recommendExample);

        // 获取下一期期号信息
        XjsscLotterySg nextSg = commonService.xjsscQueryNextSg();

        // 查询遗漏数据
        List<XjsscLotterySg> sgList = this.queryOmittedData(lastRecommend.getIssue(), nextSg.getIssue());

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 循环生成【免费推荐】数据
        XjsscRecommend recommend;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            recommend = new XjsscRecommend();
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
            xjsscRecommendMapper.insertSelective(recommend);

            String jsonObject = JSON.toJSONString(recommend);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_RECOMMEND_DATA, "XjsscRecommend:" + jsonObject);
        }
    }

    /**
     * 公式杀号
     */
    @Override
    @Transactional
    public void addXjsscGssh() {
        // 查询当前最后一期【公式杀号】数据
        XjsscKillNumberExample killNumberExample = new XjsscKillNumberExample();
        killNumberExample.setOrderByClause("`issue` DESC");
        XjsscKillNumber killNumber = xjsscKillNumberMapper.selectOneByExample(killNumberExample);

        // 获取下一期期号信息
        XjsscLotterySg nextSg = commonService.xjsscQueryNextSg();

        // 查询遗漏数据
        List<XjsscLotterySg> sgList = this.queryOmittedData(killNumber.getIssue(), nextSg.getIssue());

        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        // 生成杀号
        XjsscKillNumber nextKillNumber;
        for (int i = sgList.size() - 1; i >= 0; i--) {
            nextKillNumber = new XjsscKillNumber();

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
            xjsscKillNumberMapper.insertSelective(nextKillNumber);

            String jsonObject = JSON.toJSONString(nextKillNumber);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_KILL_DATA, "XjsscKillNumber:" + jsonObject);
        }
    }

    @Override
    public void sendMessageChangeIssue() {
        //每一期开始时间发送一个消息
        //时间范围： 0 20/20 10-2 * * ?
        //新疆时时彩  采集时间 10:20-2:00 20分钟一期 延迟1-2分钟采集到数据
        // 在这个时间内就生效：   10:20-23:40    0:00-2:00
        Long time0 = null;  //代表现在时间
        Long time1 = null;  //代表10：20
        Long time2 = null;  //代表23：45，  多出5分钟，以防延迟
        Long time3 = null;  //代表0：00
        Long time4 = null;  //代表2：05     多出5分钟，以防延迟

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

        boolean atTime = false;  //表示在这个时间段 0 20/20 10-2 * * ?
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
        objectAll.put("info", "成功");
        String jsonString = objectAll.toJSONString();
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_SSC_XJ, jsonString);
        XxlJobLogger.log(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_SSC_XJ + "执行任务成功" + DateUtils.formatDate(new Date(), DateUtils.fullDatePattern) + "," + jsonString);
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
     * 根据期号区间查询所有遗漏数据【近96期】
     *
     * @param startIssue 开始期号【不包括】
     * @param endIssue   结束期号【包括】
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
