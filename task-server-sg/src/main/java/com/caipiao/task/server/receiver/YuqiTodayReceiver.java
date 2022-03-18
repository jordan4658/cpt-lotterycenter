package com.caipiao.task.server.receiver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.mapper.*;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class YuqiTodayReceiver {
    private static final Logger logger = LoggerFactory.getLogger(YuqiTodayReceiver.class);
    @Autowired
    private BjpksLotterySgMapper bjpksLotterySgMapper;
    @Autowired
    private TxffcLotterySgMapper txffcLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private FivebjpksLotterySgMapper fivebjpksLotterySgMapper;
    @Autowired
    private JsbjpksLotterySgMapper jsbjpksLotterySgMapper;
    @Autowired
    private TenbjpksLotterySgMapper tenbjpksLotterySgMapper;
    @Autowired
    private FtjspksLotterySgMapper ftjspksLotterySgMapper;
    @Autowired
    private FtxyftLotterySgMapper ftxyftLotterySgMapper;
    @Autowired
    private FtjssscLotterySgMapper ftjssscLotterySgMapper;
    @Autowired
    private PceggLotterySgMapper pceggLotterySgMapper;
    @Autowired
    private CqsscLotterySgMapper cqsscLotterySgMapper;
    @Autowired
    private XjsscLotterySgMapper xjsscLotterySgMapper;
    @Autowired
    private TjsscLotterySgMapper tjsscLotterySgMapper;
    @Autowired
    private TensscLotterySgMapper tensscLotterySgMapper;
    @Autowired
    private FivesscLotterySgMapper fivesscLotterySgMapper;
    @Autowired
    private JssscLotterySgMapper jssscLotterySgMapper;
    @Autowired
    private XyftLotterySgMapper xyftLotterySgMapper;
    @Autowired
    private XyftscLotterySgMapper xyftscLotterySgMapper;
    @Autowired
    private OnelhcLotterySgMapper oneLhcLotterySgMapper;
    @Autowired
    private FivelhcLotterySgMapper fiveLhcLotterySgMapper;
    @Autowired
    private AmlhcLotterySgMapper ssLhcLotterySgMapper;

    @Autowired
    private AzksLotterySgMapper azksLotterySgMapper;
    @Autowired
    private DzksLotterySgMapper dzksLotterySgMapper;
    @Autowired
    private DzpceggLotterySgMapper dzpceggLotterySgMapper;
    @Autowired
    private DzxyftLotterySgMapper dzxyftLotterySgMapper;
    @Autowired
    private XjplhcLotterySgMapper xjplhcLotterySgMapper;

    // 传送当天预期数据
//    bjpksYuqiToday  北京pk10     bjpksLotterySgMapper.updateByIssue(updateSg);
//    txffcYuqiToday  腾讯分分彩   txffcLotterySgMapper.updateByIssue(updateSg);
//    fiveBjpksYuqiToday            fivebjpksLotterySgMapper.updateByIssue(updateSg);
//    jsBjpksYuqiToday              jsbjpksLotterySgMapper.updateByIssue(updateSg);
//    tenBjpksYuqiToday             tenbjpksLotterySgMapper.updateByIssue(updateSg);
//    ftjspksYuqiToday              ftjspksLotterySgMapper.updateByIssue(updateSg);
//    ftxyftYuqiToday               ftxyftLotterySgMapper.updateByIssue(updateSg);
//    ftjssscYuqiToday              ftjssscLotterySgMapper.updateByIssue(updateSg);
//    pceggYuqiToday                pceggLotterySgMapper.updateByIssue(updateSg);
//    cqsscYuqiToday                cqsscLotterySgMapper.updateByIssue(updateSg);
//    xjsscYuqiToday                xjsscLotterySgMapper.updateByIssue(updateSg);
//    tjsscYuqiToday                 tjsscLotterySgMapper.updateByIssue(updateSg);
//    tensscYuqiToday                tensscLotterySgMapper.updateByIssue(updateSg);
//    fivesscYuqiToday               fivesscLotterySgMapper.updateByIssue(updateSg);
//    jssscYuqiToday                 jssscLotterySgMapper.updateByIssue(updateSg);
//    xyftYuqiToday                   xyftLotterySgMapper.updateByIssue(updateSg);
//
//    oneLhcYuqiToday                onelhcLotterySgMapper.updateByIssue(updateSg);
//    fiveLhcYuqiToday              fivelhcLotterySgMapper.updateByIssue(updateSg);
//    ssLhcYuqiToday                sslhcLotterySgMapper.updateByIssue(updateSg);

    @JmsListener(destination = ActiveMQConfig.TOPIC_YUQI_TODAYT, containerFactory = "jmsListenerContainerQueue")
    public void sameYuqiTodayTopic(String message) {
        logger.debug("sameYuqiTodayTopic topic={}, message={}",ActiveMQConfig.TOPIC_YUQI_TODAYT,message);
        if (message != null && message.equals("bjpksYuqiToday")) {
            BjpksLotterySgExample sgExample = new BjpksLotterySgExample();
            BjpksLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<BjpksLotterySg> sgList = bjpksLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (BjpksLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_BJPKS_YUQI_DATA, "BjpksLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("txffcYuqiToday")) {
            TxffcLotterySgExample sgExample = new TxffcLotterySgExample();
            TxffcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<TxffcLotterySg> sgList = txffcLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (TxffcLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TX_YUQI_DATA, "TxffcLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("fiveBjpksYuqiToday")) {
            FivebjpksLotterySgExample sgExample = new FivebjpksLotterySgExample();
            FivebjpksLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<FivebjpksLotterySg> sgList = fivebjpksLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (FivebjpksLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVEBJPKS_YUQI_DATA, "FivebjpksLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("jsBjpksYuqiToday")) {
            JsbjpksLotterySgExample sgExample = new JsbjpksLotterySgExample();
            JsbjpksLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<JsbjpksLotterySg> sgList = jsbjpksLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (JsbjpksLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_JSBJPKS_YUQI_DATA, "JsbjpksLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("tenBjpksYuqiToday")) {
            TenbjpksLotterySgExample sgExample = new TenbjpksLotterySgExample();
            TenbjpksLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<TenbjpksLotterySg> sgList = tenbjpksLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (TenbjpksLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_TENBJPKS_YUQI_DATA, "TenbjpksLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("ftjspksYuqiToday")) {
            FtjspksLotterySgExample sgExample = new FtjspksLotterySgExample();
            FtjspksLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<FtjspksLotterySg> sgList = ftjspksLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (FtjspksLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FT_JSBJPKS_YUQI_DATA, "FtjspksLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("ftxyftYuqiToday")) {
            FtxyftLotterySgExample sgExample = new FtxyftLotterySgExample();
            FtxyftLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<FtxyftLotterySg> sgList = ftxyftLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (FtxyftLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FT_XYFT_YUQI_DATA, "FtxyftLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("ftjssscYuqiToday")) {
            FtjssscLotterySgExample sgExample = new FtjssscLotterySgExample();
            FtjssscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<FtjssscLotterySg> sgList = ftjssscLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (FtjssscLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_JS_FT_YUQI_DATA, "FtjssscLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("pceggYuqiToday")) {
            PceggLotterySgExample sgExample = new PceggLotterySgExample();
            PceggLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<PceggLotterySg> sgList = pceggLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (PceggLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_PCEGG_YUQI_DATA, "PceggLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("cqsscYuqiToday")) {
            CqsscLotterySgExample sgExample = new CqsscLotterySgExample();
            CqsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<CqsscLotterySg> sgList = cqsscLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (CqsscLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_CQ_YUQI_DATA, "CqsscLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("xjsscYuqiToday")) {
            XjsscLotterySgExample sgExample = new XjsscLotterySgExample();
            XjsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<XjsscLotterySg> sgList = xjsscLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (XjsscLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_XJ_YUQI_DATA, "XjsscLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("tjsscYuqiToday")) {
            TjsscLotterySgExample sgExample = new TjsscLotterySgExample();
            TjsscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<TjsscLotterySg> sgList = tjsscLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (TjsscLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TJ_YUQI_DATA, "TjsscLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("tensscYuqiToday")) {
            TensscLotterySgExample sgExample = new TensscLotterySgExample();
            TensscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<TensscLotterySg> sgList = tensscLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (TensscLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_TEN_YUQI_DATA, "TensscLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("fivesscYuqiToday")) {
            FivesscLotterySgExample sgExample = new FivesscLotterySgExample();
            FivesscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<FivesscLotterySg> sgList = fivesscLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (FivesscLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_FIVE_YUQI_DATA, "FivesscLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("jssscYuqiToday")) {
            JssscLotterySgExample sgExample = new JssscLotterySgExample();
            JssscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<JssscLotterySg> sgList = jssscLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (JssscLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_SSC_JS_YUQI_DATA, "JssscLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("xyftYuqiToday")) {
            XyftLotterySgExample sgExample = new XyftLotterySgExample();
            XyftLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<XyftLotterySg> sgList = xyftLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (XyftLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFT_YUQI_DATA, "XyftLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("xyftscYuqiToday")) {
            XyftscLotterySgExample sgExample = new XyftscLotterySgExample();
            XyftscLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            criteria.andIdealTimeGreaterThanOrEqualTo(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(calendar.getTime());
            sgExample.setOrderByClause("`ideal_time` asc");
            List<XyftscLotterySg> sgList = xyftscLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (XyftscLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XYFTSC_YUQI_DATA, "XyftscLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("oneLhcYuqiToday")) {
            OnelhcLotterySgExample sgExample = new OnelhcLotterySgExample();
            OnelhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<OnelhcLotterySg> sgList = oneLhcLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (OnelhcLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_YUQI_DATA, "OnelhcLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("fiveLhcYuqiToday")) {
            FivelhcLotterySgExample sgExample = new FivelhcLotterySgExample();
            FivelhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<FivelhcLotterySg> sgList = fiveLhcLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (FivelhcLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_YUQI_DATA, "FivelhcLotterySg:" + jsonString);
            }
        } else if (message != null && message.equals("ssLhcYuqiToday")) {
            AmlhcLotterySgExample sgExample = new AmlhcLotterySgExample();
            AmlhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
            sgExample.setOrderByClause("`ideal_time` asc");
            List<AmlhcLotterySg> sgList = ssLhcLotterySgMapper.selectByExample(sgExample);
            if (sgList.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (AmlhcLotterySg sg : sgList) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_YUQI_DATA, "AmlhcLotterySg:" + jsonString);
            }
        }else   if(message != null && message.equals("azksYuqiToday")){
            AzksLotterySgExample sgExample = new AzksLotterySgExample();
            AzksLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            criteria.andIdealTimeGreaterThanOrEqualTo(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
            criteria.andIdealTimeLessThan(calendar.getTime());
            sgExample.setOrderByClause("`ideal_time` asc");
            List<AzksLotterySg> sgList = azksLotterySgMapper.selectByExample(sgExample);
            if(sgList.size() > 0){
                JSONArray jsonArray = new JSONArray();
                for(AzksLotterySg sg:sgList){
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_AZ_YUQI_DATA,"AzksLotterySg:" + jsonString);
            }
        }else if(message != null && message.equals("dzksYuqiToday")){
            DzksLotterySgExample sgExample = new DzksLotterySgExample();
            DzksLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            criteria.andIdealTimeGreaterThanOrEqualTo(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
            criteria.andIdealTimeLessThan(calendar.getTime());
            sgExample.setOrderByClause("`ideal_time` asc");
            List<DzksLotterySg> sgList = dzksLotterySgMapper.selectByExample(sgExample);
            if(sgList.size() > 0){
                JSONArray jsonArray = new JSONArray();
                for(DzksLotterySg sg:sgList){
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_KS_DZ_YUQI_DATA,"DzksLotterySg:" + jsonString);
            }
        }else if(message != null && message.equals("dzPceggYuqiToday")){
            DzpceggLotterySgExample sgExample = new DzpceggLotterySgExample();
            DzpceggLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            criteria.andIdealTimeGreaterThanOrEqualTo(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
            criteria.andIdealTimeLessThan(calendar.getTime());
            sgExample.setOrderByClause("`ideal_time` asc");
            List<DzpceggLotterySg> sgList = dzpceggLotterySgMapper.selectByExample(sgExample);
            if(sgList.size() > 0){
                JSONArray jsonArray = new JSONArray();
                for(DzpceggLotterySg sg:sgList){
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZPCEGG_YUQI_DATA,"DzpceggLotterySg:" + jsonString);
            }
        }else if(message != null && message.equals("dzXyftYuqiToday")){
            DzxyftLotterySgExample sgExample = new DzxyftLotterySgExample();
            DzxyftLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            criteria.andIdealTimeGreaterThanOrEqualTo(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
            criteria.andIdealTimeLessThan(calendar.getTime());
            sgExample.setOrderByClause("`ideal_time` asc");
            List<DzxyftLotterySg> sgList = dzxyftLotterySgMapper.selectByExample(sgExample);
            if(sgList.size() > 0){
                JSONArray jsonArray = new JSONArray();
                for(DzxyftLotterySg sg:sgList){
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_DZXYFT_YUQI_DATA,"DzxyftLotterySg:" + jsonString);
            }
        }else if(message != null && message.equals("xjpLhcYuqiToday")){
            XjplhcLotterySgExample sgExample = new XjplhcLotterySgExample();
            XjplhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            criteria.andIdealTimeGreaterThanOrEqualTo(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
            criteria.andIdealTimeLessThan(calendar.getTime());
            sgExample.setOrderByClause("`ideal_time` asc");
            List<XjplhcLotterySg> sgList = xjplhcLotterySgMapper.selectByExample(sgExample);
            if(sgList.size() > 0){
                JSONArray jsonArray = new JSONArray();
                for(XjplhcLotterySg sg:sgList){
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_XJPLHC_YUQI_DATA,"XjplhcLotterySg:" + jsonString);
            }
        }


    }
}
