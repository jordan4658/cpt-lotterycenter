package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.DzpceggTaskService;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.mapper.DzpceggLotterySgMapper;
import com.mapper.domain.DzpceggLotterySg;
import com.mapper.domain.DzpceggLotterySgExample;
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
public class DzpceggTaskServiceImpl implements DzpceggTaskService {
	private static final Logger logger = LoggerFactory.getLogger(DzpceggTaskServiceImpl.class);

    @Autowired
    private DzpceggLotterySgMapper dzpceggLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private KillOrderService killOrderService;

    @Override
    @Transactional
    public void addDzpceggPrevSg() {
        // 一天总期数
        int count = CaipiaoSumCountEnum.DZPCEGG.getSumCount();
        // 获取当前赛果最后一期数据
        DzpceggLotterySgExample sgExample = new DzpceggLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        DzpceggLotterySg lastSg = dzpceggLotterySgMapper.selectOneByExample(sgExample);

        // 获取理想开奖时间
        Date dateTime = lastSg.getIdealTime();

        // 最后一期期号
        String issue = lastSg.getIssue();
        int current = Integer.valueOf(issue.substring(8));

        JSONArray jsonArray = new JSONArray();
        DzpceggLotterySg sg;
        if (current < count) {
            for (int i = current; i < count; i++) {
                sg = new DzpceggLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                dateTime = this.nextIssueTime(dateTime);
                sg.setIdealTime(dateTime);

                DzpceggLotterySg targetSg = new DzpceggLotterySg();
                BeanUtils.copyProperties(sg, targetSg);
                jsonArray.add(targetSg);
                dzpceggLotterySgMapper.insertSelective(sg);
            }
        }

        if (DateUtils.formatDate(lastSg.getIdealTime(), DateUtils.datePattern).compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZPCEGG_YUQI_DATA, "DzpceggLotterySg:" + jsonString);
            }

            return;
        }

        for (int i = 1; i <= count; i++) {
            sg = new DzpceggLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(dateTime);

            DzpceggLotterySg targetSg = new DzpceggLotterySg();
            BeanUtils.copyProperties(sg, targetSg);
            jsonArray.add(targetSg);

            dzpceggLotterySgMapper.insertSelective(sg);

        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZPCEGG_YUQI_DATA, "DzpceggLotterySg:" + jsonString);
    }

    @Override
    public void addDzpceggSg() {
        // 获取  最近第一期+ 本地近15期未开奖结果（1天内）
        DzpceggLotterySgExample sgExample = new DzpceggLotterySgExample();
        DzpceggLotterySgExample.Criteria criteria = sgExample.createCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        criteria.andIdealTimeLessThanOrEqualTo(new Date());
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        sgExample.setOffset(0);
        sgExample.setLimit(1);
        sgExample.setOrderByClause("ideal_time desc");
        DzpceggLotterySg dzpceggLotterySg = dzpceggLotterySgMapper.selectOneByExample(sgExample);

        sgExample = new DzpceggLotterySgExample();
        criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThan(dzpceggLotterySg.getIdealTime());
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        criteria.andOpenStatusEqualTo("WAIT");
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("ideal_time desc");
        List<DzpceggLotterySg> sgFifteenList = dzpceggLotterySgMapper.selectByExample(sgExample);

        List<DzpceggLotterySg> sgList = new ArrayList<>();
        sgList.add(dzpceggLotterySg);
        sgList.addAll(sgFifteenList);


        // 判空
        if (null == sgList) {
            return;
        }

        int i = 0;
        for (DzpceggLotterySg sg : sgList) {
            i++;
            boolean isPush = false; // 是否推送消息
            boolean updateIssue = false; // 是否更新开奖信息

            String issue = sg.getIssue();

            // 随机生成赛果数据
            String numberStr = "";

            // 判断是否需要修改赛果
            if (org.springframework.util.StringUtils.isEmpty(sg.getNumber())) {
                numberStr = killOrderService.getDzpceggKillNumber(sg.getIssue(), CaipiaoTypeEnum.DZPCEGG.getTagType());
                sg.setNumber(numberStr);
                sg.setTime(new Date());
                sg.setOpenStatus("AUTO");
                updateIssue = isPush = true;
            }

            int count = 0;
            String jsondzpceggLotterySg = null;
            if (updateIssue) {
                count = dzpceggLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsondzpceggLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //检查预期数据
                checkDzpceggYuqiData();

                logger.info("【德州PC蛋蛋】消息：{}", issue);
                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZPCEGG_TM, "DZPCEGG:" + issue + ":" + numberStr + ":" + jsondzpceggLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZPCEGG_BZ, "DZPCEGG:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZPCEGG_TMBS, "DZPCEGG:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZPCEGG_SB, "DZPCEGG:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZPCEGG_HH, "DZPCEGG:" + issue + ":" + numberStr);

                } catch (Exception e) {
                    logger.error("德州PC蛋蛋发送消息失败：{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", numberStr);

                object.put("nextTime", dzpceggQueryNextSg().getIdealTime().getTime() / 1000);
                object.put("nextIssue", dzpceggQueryNextSg().getIssue());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(sg.getIdealTime());
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                long jiange = DateUtils.timeReduce(DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS), DateUtils.formatDate(startCal.getTime(), DateUtils.fullDatePattern));
                int openCount = (int) (jiange / 60) + 1;
                int noOpenCount = CaipiaoSumCountEnum.DZPCEGG.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.DZPCEGG.getTagType(), object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data", lottery);
                objectAll.put("status", 1);
                objectAll.put("time", new Date().getTime() / 1000);
                objectAll.put("info", "成功");
                String jsonString = objectAll.toJSONString();

                try {
                    //每次只发送最新一条
                    if (i == 1) {
                        logger.info("TOPIC_APP_DZPCEGG发送消息：{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_DZPCEGG, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("德州PC蛋蛋发送消息失败：{}", e);
                }
            }
        }
    }


    //检查预期数据
    public void checkDzpceggYuqiData(){
        try{
            //检查预期数据任务开始
            DzpceggLotterySgExample sgExample = new DzpceggLotterySgExample();
            DzpceggLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(new Date());
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = dzpceggLotterySgMapper.countByExample(sgExample);
            if(afterCount < 10){  //当预期数据少于10，则跑一下预期数据任务
                addDzpceggPrevSg();
            }
            //检查预期数据任务结束
        }catch (Exception e){
            logger.error("德州幸运飞艇发送消息失败：{}",e);
        }
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public DzpceggLotterySg dzpceggQueryNextSg() {
        DzpceggLotterySgExample example = new DzpceggLotterySgExample();
        DzpceggLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(new Date());
        example.setOrderByClause("`ideal_time` ASC");
        return dzpceggLotterySgMapper.selectOneByExample(example);
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
