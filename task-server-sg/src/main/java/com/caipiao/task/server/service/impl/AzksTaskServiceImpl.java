package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.AzksTaskService;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.mapper.AzksLotterySgMapper;
import com.mapper.domain.AzksLotterySg;
import com.mapper.domain.AzksLotterySgExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AzksTaskServiceImpl implements AzksTaskService {

    private static final Logger logger = LoggerFactory.getLogger(AzksTaskServiceImpl.class);
    @Autowired
    private AzksLotterySgMapper azksLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private KillOrderService killOrderService;


    /**
     * 录入预期赛果信息
     */
    @Override
    @Transactional
    public void addAzksPrevSg() {
        // 一天总期数 540期，2分40秒一期
        int count = 540;
        // 获取当前赛果最后一期数据
        AzksLotterySgExample sgExample = new AzksLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        AzksLotterySg lastSg = azksLotterySgMapper.selectOneByExample(sgExample);

        // 获取理想开奖时间
        Date dateTime = lastSg.getIdealTime();

        // 最后一期期号
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(8));

        JSONArray jsonArray = new JSONArray();
        AzksLotterySg sg;
        if (current < count) {
            for (int i = current; i < count; i++) {
                sg = new AzksLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                dateTime = this.nextIssueTime(dateTime);
                sg.setIdealTime(dateTime);

                AzksLotterySg targetSg = new AzksLotterySg();
                BeanUtils.copyProperties(sg, targetSg);
                jsonArray.add(targetSg);
                azksLotterySgMapper.insertSelective(sg);
            }
        }

        if (DateUtils.formatDate(lastSg.getIdealTime(), DateUtils.datePattern).compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_AZ_YUQI_DATA, "AzksLotterySg:" + jsonString);
            }

            return;
        }

        for (int i = 1; i <= count; i++) {
            sg = new AzksLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(dateTime);

            AzksLotterySg targetSg = new AzksLotterySg();
            BeanUtils.copyProperties(sg, targetSg);
            jsonArray.add(targetSg);

            azksLotterySgMapper.insertSelective(sg);

        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_AZ_YUQI_DATA, "AzksLotterySg:" + jsonString);
    }

    /**
     * 录入赛果
     */
    @Override
    public void addAzksSg() {
        // 获取  最近第一期+ 本地近15期未开奖结果（1天内）
        AzksLotterySgExample sgExample = new AzksLotterySgExample();
        AzksLotterySgExample.Criteria criteria = sgExample.createCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        criteria.andIdealTimeLessThanOrEqualTo(new Date());
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        sgExample.setOffset(0);
        sgExample.setLimit(1);
        sgExample.setOrderByClause("ideal_time desc");
        AzksLotterySg azksLotterySg = azksLotterySgMapper.selectOneByExample(sgExample);

        sgExample = new AzksLotterySgExample();
        criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThan(azksLotterySg.getIdealTime());
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        criteria.andOpenStatusEqualTo("WAIT");
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("ideal_time desc");
        List<AzksLotterySg> sgFifteenList = azksLotterySgMapper.selectByExample(sgExample);

        List<AzksLotterySg> sgList = new ArrayList<>();
        sgList.add(azksLotterySg);
        sgList.addAll(sgFifteenList);


        // 判空
        if (null == sgList) {
            return;
        }

        int i = 0;
        for (AzksLotterySg sg : sgList) {
            i++;
            boolean isPush = false; // 是否推送消息
            boolean updateIssue = false; // 是否更新开奖信息

            String issue = sg.getIssue();

            // 随机生成赛果数据
            String numberStr = "";

            // 判断是否需要修改赛果
            if (org.springframework.util.StringUtils.isEmpty(sg.getNumber())) {
                numberStr = killOrderService.getAzksKillNumber(sg.getIssue(), CaipiaoTypeEnum.AZKS.getTagType());
                String[] numbers = numberStr.split(",");
                sg.setBai(Integer.valueOf(numbers[0]));
                sg.setShi(Integer.valueOf(numbers[1]));
                sg.setGe(Integer.valueOf(numbers[2]));
                sg.setNumber(numberStr);


                sg.setTime(new Date());
                sg.setOpenStatus("AUTO");
                updateIssue = isPush = true;
            }

            int count = 0;
            String jsontensscLotterySg = null;
            if (updateIssue) {
                count = azksLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsontensscLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //检查预期数据
                checkAzksYuqiData();

                logger.info("【澳洲快三】消息：{}", issue);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_AZ_HZ, "AZKS:" + issue + ":" + numberStr + ":" + jsontensscLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_AZ_DD, "AZKS:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_AZ_LH, "AZKS:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_AZ_THREE, "AZKS:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_AZ_TWO, "AZKS:" + issue + ":" + numberStr);

                } catch (Exception e) {
                    logger.error("澳洲快三发送消息失败：{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", numberStr);

                object.put("nextTime", queryNextSg().getIdealTime().getTime() / 1000);
                object.put("nextIssue", queryNextSg().getIssue());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(sg.getIdealTime());
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                long jiange = DateUtils.timeReduce(DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS), DateUtils.formatDate(startCal.getTime(), DateUtils.fullDatePattern));
                int openCount = (int) (jiange / 160) + 1;
                int noOpenCount = CaipiaoSumCountEnum.AZKS.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.AZKS.getTagType(), object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data", lottery);
                objectAll.put("status", 1);
                objectAll.put("time", new Date().getTime() / 1000);
                objectAll.put("info", "成功");
                String jsonString = objectAll.toJSONString();

                try {
                    //每次只发送最新一条
                    if (i == 1) {
                        logger.info("TOPIC_APP_KS_AZ发送消息：{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_KS_AZ, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("澳洲快三发送消息失败：{}", e);
                }

            }
        }
    }


    //检查预期数据
    public void checkAzksYuqiData() {
        try {
            //检查预期数据任务开始
            AzksLotterySgExample sgExample = new AzksLotterySgExample();
            AzksLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(new Date());
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = azksLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
                addAzksPrevSg();
            }
            //检查预期数据任务结束
        } catch (Exception e) {
            logger.error("澳洲快三检查预期失败：{}", e);
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
        if ("540".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
            String issueStrLeft = issue.substring(0, 8);
            String issueStrRight = issue.substring(8, issue.length());
            int issueInt = Integer.parseInt(issueStrRight);
            issueInt = issueInt + 1;
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
        Date dateTime = DateUtils.getSecondAfter(lastTime, 160);
        return dateTime;
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    private AzksLotterySg queryNextSg() {
        AzksLotterySgExample example = new AzksLotterySgExample();
        AzksLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(new Date());
        example.setOrderByClause("`ideal_time` ASC");
        AzksLotterySg sg = azksLotterySgMapper.selectOneByExample(example);
        return azksLotterySgMapper.selectOneByExample(example);
    }


}
