package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.model.ResultInfo;
import com.caipiao.core.library.model.dao.SscModel.LotterySgModel;
import com.caipiao.core.library.tool.*;
import com.caipiao.core.library.vo.result.LotterySgResult;
import com.caipiao.task.server.callable.ThreadTaskByw;
import com.caipiao.task.server.callable.ThreadTaskCpk;
import com.caipiao.task.server.callable.ThreadTaskKcw;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.XyftTaskService;
import com.caipiao.task.server.service.XyftscTaskService;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.caipiao.task.server.util.CommonService;
import com.mapper.*;
import com.mapper.domain.*;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.*;
import java.util.concurrent.Future;

/**
 * 幸运飞艇私彩 - 定时任务实现类
 */
@Service
public class XyftscTaskServiceImpl extends CommonServiceImpl implements XyftscTaskService {
    private static final Logger logger = LoggerFactory.getLogger(XyftscTaskServiceImpl.class);

    @Resource
    private XyftscLotterySgMapper xyftscLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private XyftscTaskService xyftscTaskService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private KillOrderService killOrderService;

    @Override
    @Transactional
    public void addXyftscPrevSg() {
        logger.error("======================addXyftscPrevSg======================");
        // 一天总期数
        int count = CaipiaoSumCountEnum.XYFEITSC.getSumCount();
        // 获取当前赛果最后一期数据
        XyftscLotterySgExample sgExample = new XyftscLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        XyftscLotterySg lastSg = xyftscLotterySgMapper.selectOneByExample(sgExample);
        if(lastSg==null){
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY,0);
            c.set(Calendar.MINUTE,0);
            c.set(Calendar.SECOND,0);
            lastSg = new XyftscLotterySg();
            lastSg.setIdealTime(c.getTime());
            lastSg.setIssue(DateUtils.getFullsString(new Date()));
        }
        // 获取理想开奖时间
        String idealTime = DateUtils.formatDate(lastSg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS);
        // 将字符串转化为Date
        Date dateTime = DateUtils.dateStringToDate(idealTime, DateUtils.fullDatePattern);

        // 最后一期期号
        String issue = lastSg.getIssue();
        JSONArray jsonArray = new JSONArray();
        XyftscLotterySg sg;
        String time = DateUtils.formatDate(dateTime, "HH:mm:ss");
        if (!"04:04:00".equals(time)) {
            while (time.compareTo("13:09:00") >= 0 || time.compareTo("04:04:00") < 0) {
                sg = new XyftscLotterySg();
                issue = this.createNextIssue(issue);
                sg.setIssue(issue);
                dateTime = this.nextIssueTime(dateTime);
                time = DateUtils.formatDate(dateTime, DateUtils.timePattern);
                sg.setIdealTime(dateTime);

                XyftscLotterySg targetSg = new XyftscLotterySg();
                BeanUtils.copyProperties(sg, targetSg);

                jsonArray.add(targetSg);
                xyftscLotterySgMapper.insertSelective(sg);
            }
        }
        String date = idealTime.substring(0, 10);
        if (date.compareTo(DateUtils.formatDate(DateUtils.getDayAfter(new Date(), 1L), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFTSC_YUQI_DATA, "XyftLotterySg:" + jsonString);
            }
            return;
        }
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                dateTime = DateUtils.parseDate(DateUtils.formatDate(dateTime, DateUtils.datePattern) + " 13:04:00", DateUtils.fullDatePattern);
            }
            sg = new XyftscLotterySg();
            issue = this.createNextIssue(issue);
            sg.setIssue(issue);
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(dateTime);

            XyftscLotterySg targetSg = new XyftscLotterySg();
            BeanUtils.copyProperties(sg, targetSg);
            jsonArray.add(targetSg);
            xyftscLotterySgMapper.insertSelective(sg);
        }
        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFTSC_YUQI_DATA, "XyftscLotterySg:" + jsonString);
    }


    @Override
    public void addXyftscSg() {
        // 获取  最近第一期+ 本地近15期未开奖结果（1天内）
        XyftscLotterySgExample sgExample = new XyftscLotterySgExample();
        XyftscLotterySgExample.Criteria criteria = sgExample.createCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        criteria.andIdealTimeLessThanOrEqualTo(new Date());
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        sgExample.setOffset(0);
        sgExample.setLimit(1);
        sgExample.setOrderByClause("ideal_time desc");
        XyftscLotterySg xyftscLotterySg = xyftscLotterySgMapper.selectOneByExample(sgExample);

        sgExample = new XyftscLotterySgExample();
        criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThan(xyftscLotterySg.getIdealTime());
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        criteria.andOpenStatusEqualTo("WAIT");
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("ideal_time desc");
        List<XyftscLotterySg> sgFifteenList = xyftscLotterySgMapper.selectByExample(sgExample);

        List<XyftscLotterySg> sgList = new ArrayList<>();
        sgList.add(xyftscLotterySg);
        sgList.addAll(sgFifteenList);

        // 判空
        if (null == sgList) {
            return;
        }

        int i = 0;
        for (XyftscLotterySg sg : sgList) {
            i++;
            boolean isPush = false; // 是否推送消息
            boolean updateIssue = false; // 是否更新开奖信息

            String issue = sg.getIssue();

            // 随机生成赛果数据
            String numberStr = "";

            // 判断是否需要修改赛果
            if (org.springframework.util.StringUtils.isEmpty(sg.getNumber())) {
                numberStr = killOrderService.getXyftKillNumber(sg.getIssue(), CaipiaoTypeEnum.XYFEITSC.getTagType());
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
                count = xyftscLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsondzxyftLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }
            if (isPush && count > 0) {
                //检查预期数据
                checkXyftscYuqiData();

                logger.info("【幸运飞艇私彩】消息：{}", issue);
                try {
                    Destination destinationLM = new ActiveMQTopic(ActiveMQConfig.TOPIC_XYFTSC_LM);
                    Destination destinationCQJ = new ActiveMQTopic(ActiveMQConfig.TOPIC_XYFTSC_CMC_CQJ);
                    Destination destinationGYH = new ActiveMQTopic(ActiveMQConfig.TOPIC_XYFTSC_GYH);
                    jmsMessagingTemplate.convertAndSend(destinationLM, "XYFTSC:" + issue + ":" + numberStr + ":" + jsondzxyftLotterySg);
                    jmsMessagingTemplate.convertAndSend(destinationCQJ, "XYFTSC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(destinationGYH, "XYFTSC:" + issue + ":" + numberStr);
                } catch (Exception e) {
                    logger.error("幸运飞艇私彩发送消息失败：{}", e);
                }

                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();
                object.put("issue", issue);
                object.put("number", numberStr);

                object.put("nextTime", xyftscQueryNextSg().getIdealTime().getTime() / 1000);
                object.put("nextIssue", xyftscQueryNextSg().getIssue());

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(sg.getIdealTime());
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                int openCount = Integer.valueOf(issue.substring(8));
                int noOpenCount = CaipiaoSumCountEnum.XYFEITSC.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.XYFEITSC.getTagType(), object);

                JSONObject objectAll = new JSONObject();
                objectAll.put("data", lottery);
                objectAll.put("status", 1);
                objectAll.put("time", new Date().getTime() / 1000);
                objectAll.put("info", "成功");
                String jsonString = objectAll.toJSONString();

                try {
                    //每次只发送最新一条
                    if (i == 1) {
                        logger.info("TOPIC_APP_XYFTSC发送消息：{}", jsonString);
                        Destination destinationXYFTSC = new ActiveMQTopic(ActiveMQConfig.TOPIC_APP_XYFTSC);
                        jmsMessagingTemplate.convertAndSend(destinationXYFTSC, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("幸运飞艇私彩发送消息失败：{}", e);
                }
            }
        }
    }


    //检查预期数据
    public void checkXyftscYuqiData() {
        try {
            //检查预期数据任务开始
            XyftscLotterySgExample sgExample = new XyftscLotterySgExample();
            XyftscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(new Date());
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = xyftscLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
                xyftscTaskService.addXyftscPrevSg();
            }
            //检查预期数据任务结束
        } catch (Exception e) {
            logger.error("幸运飞艇私彩发送消息失败：{}", e);
        }
    }

    /**
     * 获取下一期期号信息
     *
     * @return
     */
    public XyftscLotterySg xyftscQueryNextSg() {
        XyftscLotterySgExample example = new XyftscLotterySgExample();
        XyftscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIdealTimeGreaterThan(new Date());
        example.setOrderByClause("`ideal_time` ASC");
        return xyftscLotterySgMapper.selectOneByExample(example);
    }

    /**
     * 获取下一期官方开奖时间
     *
     * @param dateTime 当前期官方开奖时间
     * @return
     */
    private Date nextIssueTime(Date dateTime) {
        return DateUtils.getMinuteAfter(dateTime, 5);
    }

    /**
     * 根据期号区间查询所有遗漏数据【近179期】
     *
     * @param startIssue 开始期号【不包括】
     * @param endIssue   结束期号【包括】
     * @return
     */
    private List<XyftscLotterySg> queryOmittedData(String startIssue, String endIssue) {
        XyftscLotterySgExample example = new XyftscLotterySgExample();
        XyftscLotterySgExample.Criteria criteria = example.createCriteria();
        criteria.andIssueGreaterThan(startIssue);
        criteria.andIssueLessThanOrEqualTo(endIssue);
        example.setOrderByClause("`ideal_time` DESC");
        example.setOffset(0);
        example.setLimit(180);
        return xyftscLotterySgMapper.selectByExample(example);
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
        if ("180".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }

}
