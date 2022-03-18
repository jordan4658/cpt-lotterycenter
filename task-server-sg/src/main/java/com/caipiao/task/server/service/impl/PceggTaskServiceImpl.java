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

        // 获取当前未开的期号信息
        List<PceggLotterySg> sgList = this.queryRecent();
        // 判空
        if (CollectionUtils.isEmpty(sgList)) {
            return;
        }

        String cpkNumber = "", kcwNumber = "", bywNumber = "", number;
        int i = 0;
        // 遍历待开奖信息
        for (PceggLotterySg sg : sgList) {
            i++;
            String issue = sg.getIssue();

            // 获取开奖结果
            LotterySgModel cpkModel = cpkMap.get(issue);
            LotterySgModel kcwModel = kcwMap.get(issue);
            LotterySgModel bywModel = bywMap.get(issue);

//            //这一期赛果有值 的个数，
//            int thisSgDataSize = TaskUtil.getNotNulSgModel(cpkModel,kcwModel,bywModel);
//            // 当这期赛果数据 个数 小于 2，不执行
//            if (thisSgDataSize < 2) {
//                continue;
//            }

            // 获取【彩票控】当前实际开奖期号与结果
            if (cpkModel != null) {
                cpkNumber = this.countNumber(cpkModel.getSg().substring(0, 59));
            }
            // 获取【开彩网】当前实际开奖期号与结果
            if (kcwModel != null) {
                kcwNumber = this.countNumber(kcwModel.getSg().substring(0, 59));
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

            // 判断是否需要修改赛果
            boolean isPush = false;
            if (StringUtils.isBlank(sg.getNumber())) {
                sg.setNumber(number);
                sg.setTime(DateUtils.getTimeString(new Date(), DateUtils.fullDatePattern));
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
            // 判断是否需要更新彩票控赛果
            if (StringUtils.isBlank(sg.getBywNumber()) && StringUtils.isNotBlank(bywNumber)) {
                sg.setBywNumber(bywNumber);
            }

            // 判断是否需要更新开彩网赛果
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
                //检查预期数据
                checkPcggYuqiData();

                logger.info("【PC蛋蛋】消息：{}", issue);
                // 将赛果推送到PC蛋蛋相关队列
//                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, RabbitConfig.BINDING_PCEGG, "PCDD:" + issue + ":" + number);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_TM, "PCDD:" + issue + ":" + number + ":" + jsonPceggLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_BZ, "PCDD:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_TMBS, "PCDD:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_SB, "PCDD:" + issue + ":" + number);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_HH, "PCDD:" + issue + ":" + number);
                } catch (Exception e) {
                    logger.error("pc蛋蛋发送消息失败：{}", e);
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
                    //每次只发送最新一条
                    logger.info("pcegg每次发送消息：{}", jsonString);
                    if (i == 1) {
                        logger.info("TOPIC_APP_PCEGG发送消息：{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_PCEGG, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("pc蛋蛋发送消息失败：{}", e);
                }
            }
        }
    }

    //检查预期数据
    public void checkPcggYuqiData() {
        try {
            //检查预期数据任务开始
            PceggLotterySgExample sgExample = new PceggLotterySgExample();
            PceggLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = pceggLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
                pceggTaskService.addPceggPrevSg();
            }
            //检查预期数据任务结束
        } catch (Exception e) {
            logger.error("pc蛋蛋检查预期数据失败：{}", e);
        }
    }

    /**
     * 获取当前未开奖期号信息
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
     * 获取当前应开奖信息
     *
     * @return
     */
    private PceggLotterySg queryOpenIssue() {
        // 获取当前应该开奖的期号
        PceggLotterySgExample example = new PceggLotterySgExample();
        example.setOrderByClause("`ideal_time` desc");
        PceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        return pceggLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 通过第三方开奖号码计算出PC蛋蛋开奖结果
     *
     * @param number 第三方开奖号码
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

        // 获取下一期期号信息
        PceggLotterySg sg = commonService.pceggQueryNextSg();
        String issue = sg.getIssue();

        // 查询该期是否有推荐
        PceggRecommendExample e = new PceggRecommendExample();
        PceggRecommendExample.Criteria criteria = e.createCriteria();
        criteria.andIssueEqualTo(issue);
        List<PceggRecommend> pceggRecommends = pceggRecommendMapper.selectByExample(e);
        if (!CollectionUtils.isEmpty(pceggRecommends)) {
            return;
        }
        recommend.setIssue(issue);

        // 生成第一区推荐号码 5 个
        String randomStr1 = RandomUtil.getRandomStringNoSame(5, 0, 10);
        recommend.setRegionOneNumber(randomStr1);
        // 根据号码生成大小|单双
        String[] str1 = randomStr1.split(",");
        recommend.setRegionOneSize(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
        recommend.setRegionOneSingle(Integer.parseInt(str1[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");

        // 生成第二区推荐号码 5 个
        String randomStr2 = RandomUtil.getRandomStringNoSame(5, 0, 10);
        recommend.setRegionTwoNumber(randomStr2);
        // 根据号码生成大小|单双
        String[] str2 = randomStr2.split(",");
        recommend.setRegionTwoSize(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
        recommend.setRegionTwoSingle(Integer.parseInt(str2[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");

        // 生成第三区推荐号码 5 个
        String randomStr3 = RandomUtil.getRandomStringNoSame(5, 0, 10);
        recommend.setRegionThreeNumber(randomStr3);
        // 根据号码生成大小|单双
        String[] str3 = randomStr3.split(",");
        recommend.setRegionThreeSize(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) >= 5 ? "大" : "小");
        recommend.setRegionThreeSingle(Integer.parseInt(str3[RandomUtil.getRandomOne(0, 5)]) % 2 == 0 ? "双" : "单");

        recommend.setCreateTime(DateUtils.formatDate(new Date(), DateUtils.fullDatePattern));
        pceggRecommendMapper.insertSelective(recommend);

        String jsonObject = JSON.toJSONString(recommend);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_RECOMMEND_DATA, "PceggRecommend:" + jsonObject);
    }

    @Override
    public void addPceggPrevSg() {
        int count = 179; // 一天总期数
        // 获取当前的最后一条数据
        PceggLotterySgExample example = new PceggLotterySgExample();
        example.setOrderByClause("ideal_time desc");
        PceggLotterySg pceggLotterySg = pceggLotterySgMapper.selectOneByExample(example);
        // 获取理想开奖时间
        String idealTime = pceggLotterySg.getIdealTime();
        String date = idealTime.substring(0, 10);
        String time = idealTime.substring(11);
        // 将字符串转化为Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // 最后一期期号
        String issue = pceggLotterySg.getIssue();

        JSONArray jsonArray = new JSONArray();
        // 判断预先录入数据是否是当天最后一期，如果不是，先补录完整
        if (!"23:55:00".equals(time)) {
            // 查询当天已开多少期
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

        // 获取当前录入预期结果时间与目标相差天数
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
        //每一期开始时间发送一个消息
        //时间范围： 0 5/5 9-23 * * ?
        //新疆时时彩  采集时间 9:05-23:55 5分钟一期 延迟1-2分钟采集到数据
        // 在这个时间内就生效：   9:05-23:55
        Long time0 = null;  //代表现在时间
        Long time1 = null;  //代表9：05
        Long time2 = null;  //代表23：56，  多出1分钟，以防延迟

        Calendar cal = Calendar.getInstance();
        time0 = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 5);
        time1 = cal.getTimeInMillis();

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 56);
        time2 = cal.getTimeInMillis();

        boolean atTime = false;  //表示在这个时间段  0 9/5 13-4 * * ?
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
        objectAll.put("info", "成功");
        String jsonString = objectAll.toJSONString();
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_PCEGG, jsonString);
        XxlJobLogger.log(ActiveMQConfig.TOPIC_APP_CHANGE_ISSUE_PCEGG + "执行任务成功" + DateUtils.formatDate(new Date(), DateUtils.fullDatePattern) + "," + jsonString);
    }


}
