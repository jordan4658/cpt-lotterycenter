package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.DzxyftTaskService;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.mapper.DzxyftLotterySgMapper;
import com.mapper.domain.DzxyftLotterySg;
import com.mapper.domain.DzxyftLotterySgExample;
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

/**
 * 幸运飞艇 - 定时任务实现类
 */
@Service
public class DzxyftTaskServiceImpl implements DzxyftTaskService {
    private static final Logger logger = LoggerFactory.getLogger(DzxyftTaskServiceImpl.class);

    @Autowired
    private DzxyftLotterySgMapper dzxyftLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private KillOrderService killOrderService;

    @Override
    @Transactional
    public void addDzxyftPrevSg() {
        // 一天总期数
        int count = CaipiaoSumCountEnum.DZXYFT.getSumCount();
        // 获取当前赛果最后一期数据
        DzxyftLotterySgExample sgExample = new DzxyftLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        DzxyftLotterySg lastSg = dzxyftLotterySgMapper.selectOneByExample(sgExample);

        // 获取理想开奖时间
        Date dateTime = lastSg.getIdealTime();

        // 最后一期期号
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(8));

        JSONArray jsonArray = new JSONArray();
        DzxyftLotterySg sg;
        if (current < count) {
            for (int i = current; i < count; i++) {
                sg = new DzxyftLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                dateTime = this.nextIssueTime(dateTime);
                sg.setIdealTime(dateTime);

                DzxyftLotterySg targetSg = new DzxyftLotterySg();
                BeanUtils.copyProperties(sg, targetSg);
                jsonArray.add(targetSg);
                dzxyftLotterySgMapper.insertSelective(sg);
            }
        }

        if (DateUtils.formatDate(lastSg.getIdealTime(), DateUtils.datePattern).compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZXYFT_YUQI_DATA, "DzxyftLotterySg:" + jsonString);
            }

            return;
        }

        for (int i = 1; i <= count; i++) {
            sg = new DzxyftLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(dateTime);

            DzxyftLotterySg targetSg = new DzxyftLotterySg();
            BeanUtils.copyProperties(sg, targetSg);
            jsonArray.add(targetSg);

            dzxyftLotterySgMapper.insertSelective(sg);

        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZXYFT_YUQI_DATA, "DzxyftLotterySg:" + jsonString);
    }

    @Override
    public void addDzxyftSg() {
        // 获取  最近第一期+ 本地近15期未开奖结果（1天内）
        DzxyftLotterySgExample sgExample = new DzxyftLotterySgExample();
        DzxyftLotterySgExample.Criteria criteria = sgExample.createCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        criteria.andIdealTimeLessThanOrEqualTo(new Date());
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        sgExample.setOffset(0);
        sgExample.setLimit(1);
        sgExample.setOrderByClause("ideal_time desc");
        DzxyftLotterySg dzxyftLotterySg = dzxyftLotterySgMapper.selectOneByExample(sgExample);

        sgExample = new DzxyftLotterySgExample();
        criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThan(dzxyftLotterySg.getIdealTime());
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        criteria.andOpenStatusEqualTo("WAIT");
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("ideal_time desc");
        List<DzxyftLotterySg> sgFifteenList = dzxyftLotterySgMapper.selectByExample(sgExample);

        List<DzxyftLotterySg> sgList = new ArrayList<>();
        sgList.add(dzxyftLotterySg);
        sgList.addAll(sgFifteenList);


        // 判空
        if (null == sgList) {
            return;
        }

        int i = 0;
        for (DzxyftLotterySg sg : sgList) {
            i++;
            boolean isPush = false; // 是否推送消息
            boolean updateIssue = false; // 是否更新开奖信息

            String issue = sg.getIssue();

            // 随机生成赛果数据
            String numberStr = "";

            // 判断是否需要修改赛果
            if (org.springframework.util.StringUtils.isEmpty(sg.getNumber())) {
                numberStr = killOrderService.getDzxyftKillNumber(sg.getIssue(), CaipiaoTypeEnum.DZXYFT.getTagType());
                String numberArray[] = numberStr.split(",");
                String numberLast = "";
                for (String num : numberArray) {
                    if (num.length() == 1) {
                        numberLast += ("0" + num + ",");
                    } else {
                        numberLast += (num + ",");
                    }
                }
                numberStr = numberLast.substring(0, numberLast.length() - 1);

                sg.setNumber(numberStr);
                sg.setTime(new Date());
                sg.setOpenStatus("AUTO");
                updateIssue = isPush = true;
            }

            int count = 0;
            String jsondzxyftLotterySg = null;
            if (updateIssue) {
                count = dzxyftLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsondzxyftLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //检查预期数据
                checkDzxyftYuqiData();

                logger.info("【德州洲幸运飞艇】消息：{}", issue);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZXYFT_LM, "DZXYFT:" + issue + ":" + numberStr + ":" + jsondzxyftLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZXYFT_CMC_CQJ, "DZXYFT:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZXYFT_GYH, "DZXYFT:" + issue + ":" + numberStr);
                } catch (Exception e) {
                    logger.error("德州幸运飞艇发送消息失败：{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", numberStr);

                object.put("nextTime", dzxyftQueryNextSg().getIdealTime().getTime() / 1000);
                object.put("nextIssue", dzxyftQueryNextSg().getIssue());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(sg.getIdealTime());
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                long jiange = DateUtils.timeReduce(DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS), DateUtils.formatDate(startCal.getTime(), DateUtils.fullDatePattern));
                int openCount = (int) (jiange / 60) + 1;
                int noOpenCount = CaipiaoSumCountEnum.DZXYFT.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.DZXYFT.getTagType(), object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data", lottery);
                objectAll.put("status", 1);
                objectAll.put("time", new Date().getTime() / 1000);
                objectAll.put("info", "成功");
                String jsonString = objectAll.toJSONString();

                try {
                    //每次只发送最新一条
                    if (i == 1) {
                        logger.info("TOPIC_APP_DZXYFT发送消息：{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_DZXYFT, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("德州幸运飞艇发送消息失败：{}", e);
                }
            }
        }
    }


    //检查预期数据
    public void checkDzxyftYuqiData() {
        try {
            //检查预期数据任务开始
            DzxyftLotterySgExample sgExample = new DzxyftLotterySgExample();
            DzxyftLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(new Date());
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = dzxyftLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
                addDzxyftPrevSg();
            }
            //检查预期数据任务结束
        } catch (Exception e) {
            logger.error("德州幸运飞艇发送消息失败：{}", e);
        }
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public DzxyftLotterySg dzxyftQueryNextSg() {
        DzxyftLotterySgExample example = new DzxyftLotterySgExample();
        DzxyftLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(new Date());
        example.setOrderByClause("`ideal_time` ASC");
        return dzxyftLotterySgMapper.selectOneByExample(example);
    }


    /**
     * 获取下一期官方开奖时间
     *
     * @param dateTime 当前期官方开奖时间
     * @return
     */
    private Date nextIssueTime(Date dateTime) {
        return DateUtils.getMinuteAfter(dateTime, 1);
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
        if ("1440".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "0001";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }

}
