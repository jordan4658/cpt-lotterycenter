package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.enums.CaipiaoSumCountEnum;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.LhcUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.XjplhcTaskService;
import com.caipiao.task.server.service.killmem.KillOrderService;
import com.mapper.XjplhcLotterySgMapper;
import com.mapper.domain.XjplhcLotterySg;
import com.mapper.domain.XjplhcLotterySgExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class XjplhcTaskServiceImpl implements XjplhcTaskService {

    private static final Logger logger = LoggerFactory.getLogger(XjplhcTaskServiceImpl.class);

    @Autowired
    private XjplhcLotterySgMapper xjplhcLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private KillOrderService killOrderService;
    @Autowired
    private XjplhcTaskService xjplhcTaskService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 录入预期赛果信息
     */
    @Override
    @Transactional
    public void addXjplhcPrevSg() {
        // 一天总期数
        int count = 288;
        // 获取当前赛果最后一期数据
        XjplhcLotterySgExample sgExample = new XjplhcLotterySgExample();
        sgExample.setOrderByClause("ideal_time desc");
        XjplhcLotterySg lastSg = xjplhcLotterySgMapper.selectOneByExample(sgExample);

        // 获取理想开奖时间
        // 将字符串转化为Date
        Date dateTime = lastSg.getIdealTime();
        // 最后一期期号
        Long issue = Long.valueOf(lastSg.getIssue());
        XjplhcLotterySg sg;
        String time = DateUtils.formatDate(dateTime, "HH:mm:ss");

        JSONArray jsonArray = new JSONArray();
        if (!"23:55:00".equals(time)) {
            while (!"23:55:00".equals(time)) {
                sg = new XjplhcLotterySg();
                issue += 1;
                sg.setIssue(String.valueOf(issue));
                time = DateUtils.formatDate(dateTime, DateUtils.timePattern);
                dateTime = this.nextIssueTime(dateTime);
                sg.setIdealTime(dateTime);
                sg.setOpenStatus(Constants.WAIT);

                XjplhcLotterySg targetSg = new XjplhcLotterySg();
                BeanUtils.copyProperties(sg, targetSg);
                jsonArray.add(targetSg);
                xjplhcLotterySgMapper.insertSelective(sg);
            }
        }

        if (DateUtils.formatDate(dateTime, DateUtils.datePattern).compareTo(DateUtils.formatDate(new Date(), DateUtils.datePattern)) > 0) {
            if (jsonArray.size() > 0) {
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XJPLHC_YUQI_DATA, "XjplhcLotterySg:" + jsonString);
            }

            return;
        }

        String issueThis = issue.toString();
        for (int i = 0; i < count; i++) {
            sg = new XjplhcLotterySg();
            issueThis = this.createNextIssue(issueThis);
            sg.setIssue(issueThis);
            dateTime = this.nextIssueTime(dateTime);
            sg.setIdealTime(dateTime);
            sg.setOpenStatus(Constants.WAIT);

            XjplhcLotterySg targetSg = new XjplhcLotterySg();
            BeanUtils.copyProperties(sg, targetSg);
            jsonArray.add(targetSg);
            xjplhcLotterySgMapper.insertSelective(sg);
        }

        String jsonString = JSONObject.toJSONString(jsonArray);
        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XJPLHC_YUQI_DATA, "XjplhcLotterySg:" + jsonString);
    }

    /**
     * 随机生成赛果
     */
    @Override
    public void addXjplhcSg() {
        // 获取  最近第一期+ 本地近15期未开奖结果（1天内）
        XjplhcLotterySgExample sgExample = new XjplhcLotterySgExample();
        XjplhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThanOrEqualTo(new Date());
        sgExample.setOffset(0);
        sgExample.setLimit(1);
        sgExample.setOrderByClause("ideal_time desc");
        XjplhcLotterySg xjplhcLotterySg = xjplhcLotterySgMapper.selectOneByExample(sgExample);

        sgExample = new XjplhcLotterySgExample();
        criteria = sgExample.createCriteria();
        criteria.andIdealTimeLessThan(xjplhcLotterySg.getIdealTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        criteria.andIdealTimeGreaterThan(calendar.getTime());
        criteria.andOpenStatusEqualTo("WAIT");
        sgExample.setOffset(0);
        sgExample.setLimit(15);
        sgExample.setOrderByClause("ideal_time desc");
        List<XjplhcLotterySg> sgFifteenList = xjplhcLotterySgMapper.selectByExample(sgExample);

        List<XjplhcLotterySg> sgList = new ArrayList<>();
        sgList.add(xjplhcLotterySg);
        sgList.addAll(sgFifteenList);


        int i = 0;
        for (XjplhcLotterySg sg : sgList) {
            i++;
            // 是否需要修改
            boolean bool = false, isPush = false;

            List<String> numberList = new ArrayList<String>();
            for (int j = 1; j <= 49; j++) {
                numberList.add(j < 10 ? ("0" + j) : String.valueOf(j));
            }
            Collections.shuffle(numberList);
            String numberStr = "";

            String issue = "";
            // 判断是否需要修改赛果
            if (StringUtils.isBlank(sg.getNumber())) {
                String key = "presg" + CaipiaoTypeEnum.XJPLHC.getTagType()+sg.getIssue();
                if(redisTemplate.hasKey(key)){
                    numberStr = String.valueOf(redisTemplate.opsForValue().get(key));
                    logger.info("预开奖获取redis保存的赛果：key:{},sg:{}",key, numberStr);
                    try{
                        boolean chuanTrue = true;
                        String numArray[] = numberStr.split(",");
                        Set<String> numSet = new HashSet<>();
                        for(String num:numArray){
                            numSet.add(num);
                            if(Integer.valueOf(num) < 1 || Integer.valueOf(num) > 49){
                                chuanTrue = false;
                            }
                        }
                        if(numSet.size() != 7){
                            chuanTrue = false;
                        }
                        if(chuanTrue == false){
                            numberStr = killOrderService.getXjplhcKillNumber(sg.getIssue(), CaipiaoTypeEnum.XJPLHC.getTagType());
                        }
                    }catch (Exception e){
                        numberStr = killOrderService.getXjplhcKillNumber(sg.getIssue(), CaipiaoTypeEnum.XJPLHC.getTagType());
                    }
                }else{
                    logger.info("预开奖获取redis保存的赛果为空，自己开奖：{}",numberStr);
                    numberStr = killOrderService.getXjplhcKillNumber(sg.getIssue(), CaipiaoTypeEnum.XJPLHC.getTagType());
                }

                sg.setNumber(numberStr);
                sg.setTime(new Date());
                sg.setOpenStatus("AUTO");
                bool = true;
                isPush = true;
                issue = sg.getIssue();
            }

            int count = 0;
            String jsonXjplhcLotterySg = null;
            if (bool) {
                count = xjplhcLotterySgMapper.updateByPrimaryKeySelective(sg);
                jsonXjplhcLotterySg = JSON.toJSONString(sg).replace(":", "$");
            }

            if (isPush && count > 0) {
                //检查预期数据
                checkFiveLhcYuqiData();

                logger.info("【新加坡六合彩】消息：XJPLHC:{},{}", issue, numberStr);

                try {
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XJPLHC_TM_ZT_LX, "XJPLHC:" + issue + ":" + numberStr + ":" + jsonXjplhcLotterySg);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XJPLHC_ZM_BB_WS, "XJPLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XJPLHC_LM_LX_LW, "XJPLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XJPLHC_BZ_LH_WX, "XJPLHC:" + issue + ":" + numberStr);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XJPLHC_PT_TX, "XJPLHC:" + issue + ":" + numberStr);
                } catch (Exception e) {
                    logger.error("新加坡六合彩发送消息失败：{}", e);
                }


                JSONObject object = new JSONObject();
                JSONObject lottery = new JSONObject();

                object.put("issue", issue);
                object.put("number", numberStr);
                String sgArray[] = numberStr.split(",");
                String shengxiaoString = LhcUtils.getShengXiao(Integer.valueOf(sgArray[0]), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)) + "," +
                        LhcUtils.getShengXiao(Integer.valueOf(sgArray[1]), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)) + "," +
                        LhcUtils.getShengXiao(Integer.valueOf(sgArray[2]), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)) + "," +
                        LhcUtils.getShengXiao(Integer.valueOf(sgArray[3]), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)) + "," +
                        LhcUtils.getShengXiao(Integer.valueOf(sgArray[4]), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)) + "," +
                        LhcUtils.getShengXiao(Integer.valueOf(sgArray[5]), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS)) + "," +
                        LhcUtils.getShengXiao(Integer.valueOf(sgArray[6]), DateUtils.formatDate(sg.getIdealTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                object.put("shengxiao", shengxiaoString);

                object.put("nextIssue", String.valueOf(Long.valueOf(issue) + 1));
                object.put("nextTime", nextIssueTime(sg.getIdealTime()).getTime() / 1000);

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(sg.getIdealTime());
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                long jiange = DateUtils.timeReduce(DateUtils.formatDate(sg.getIdealTime(), DateUtils.fullDatePattern), DateUtils.formatDate(startCal.getTime(), DateUtils.fullDatePattern));
                int openCount = (int) (jiange / 300) + 1;
                int noOpenCount = CaipiaoSumCountEnum.XJPLHC.getSumCount() - openCount;

                object.put("openCount", openCount);
                object.put("noOpenCount", noOpenCount);
                lottery.put(CaipiaoTypeEnum.XJPLHC.getTagType(), object);


                JSONObject objectAll = new JSONObject();
                objectAll.put("data", lottery);
                objectAll.put("status", 1);
                objectAll.put("time", new Date().getTime() / 1000);
                objectAll.put("info", "成功");
                String jsonString = objectAll.toJSONString();

                try {
                    //每次只发送最新一条
                    if (i == 1) {
                        logger.info("TOPIC_LHC_XJP发送消息：{}", jsonString);
                        jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_APP_XJP_LHC, jsonString);
                    }
                } catch (Exception e) {
                    logger.error("新加坡六合彩发送消息失败：{}", e);
                }

            }
        }
    }

    //检查预期数据
    public void checkFiveLhcYuqiData() {
        try {
            //检查预期数据任务开始
            XjplhcLotterySgExample sgExample = new XjplhcLotterySgExample();
            XjplhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            criteria.andIdealTimeGreaterThan(new Date());
            sgExample.setOrderByClause("`ideal_time` asc");
            int afterCount = xjplhcLotterySgMapper.countByExample(sgExample);
            if (afterCount < 10) {  //当预期数据少于10，则跑一下预期数据任务
                xjplhcTaskService.addXjplhcPrevSg();
            }
            //检查预期数据任务结束
        } catch (Exception e) {
            logger.error("新加坡六合彩检查预期数据失败：{}", e);
        }

    }


    /**
     * 获取下一期官方开奖时间
     *
     * @param lastTime 当前最后设定的一个官方开奖时间
     * @return 下一期官方开奖时间
     */
    private Date nextIssueTime(Date lastTime) {
        return DateUtils.getMinuteAfter(lastTime, 5);
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
        if ("288".equals(num)) {
            String prefix = DateUtils.formatDate(DateUtils.getDayAfter(DateUtils.parseDate(issue.substring(0, 8), "yyyyMMdd"), 1L), "yyyyMMdd");
            nextIssue = prefix + "001";
        } else {
            long next = Long.parseLong(issue) + 1;
            nextIssue = Long.toString(next);
        }
        return nextIssue;
    }
}
