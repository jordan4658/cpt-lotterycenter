package com.caipiao.task.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.constant.Constants;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.enums.TaskStatusEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.service.SendSgService;
import com.caipiao.task.server.service.TaskSgService;
import com.mapper.*;
import com.mapper.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SendSgServiceImpl implements SendSgService {

    private static final Logger logger = LoggerFactory.getLogger(JssscTaskServiceImpl.class);

    @Resource
    private TaskSgMapper taskSgMapper;
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
    private OnelhcLotterySgMapper onelhcLotterySgMapper;
    @Autowired
    private FivelhcLotterySgMapper fivelhcLotterySgMapper;
    @Autowired
    private AmlhcLotterySgMapper amlhcLotterySgMapper;
    @Autowired
    private BjpksLotterySgMapper bjpksLotterySgMapper;
    @Autowired
    private TenbjpksLotterySgMapper tenbjpksLotterySgMapper;
    @Autowired
    private FivebjpksLotterySgMapper fivebjpksLotterySgMapper;
    @Autowired
    private JsbjpksLotterySgMapper jsbjpksLotterySgMapper;
    @Autowired
    private XyftLotterySgMapper xyftLotterySgMapper;
    @Autowired
    private XyftscLotterySgMapper xyftscLotterySgMapper;
    @Autowired
    private PceggLotterySgMapper pceggLotterySgMapper;
    @Autowired
    private TxffcLotterySgMapper txffcLotterySgMapper;
    @Autowired
    private TcdltLotterySgMapper tcdltLotterySgMapper;
    @Autowired
    private TcplwLotterySgMapper tcplwLotterySgMapper;
    @Autowired
    private Tc7xcLotterySgMapper tc7xcLotterySgMapper;
    @Autowired
    private FcssqLotterySgMapper fcssqLotterySgMapper;
    @Autowired
    private Fc3dLotterySgMapper fc3dLotterySgMapper;
    @Autowired
    private Fc7lcLotterySgMapper fc7lcLotterySgMapper;
    @Autowired
    private AuspksLotterySgMapper auspksLotterySgMapper;
    @Autowired
    private FtjspksLotterySgMapper ftjspksLotterySgMapper;
    @Autowired
    private AusactLotterySgMapper ausactLotterySgMapper;
    @Autowired
    private FtxyftLotterySgMapper ftxyftLotterySgMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private AussscLotterySgMapper aussscLotterySgMapper;
    @Autowired
    private FtjssscLotterySgMapper ftjssscLotterySgMapper;

    @Override
    public void sendSgData() {
//        1101-      cqssc_lottery_sg
//        1102-      xjssc_lottery_sg
//        1103-      tjssc_lottery_sg
//        1104-      tenssc_lottery_sg
//        1105,1901- fivessc_lottery_sg
//        1106-      jsssc_lottery_sg
//        1201-      lhc_lottery_sg
//        1202-      onelhc_lottery_sg
//        1203-      fivelhc_lottery_sg
//        1204-      sslhc_lottery_sg
//        1301-      bjpks_lottery_sg
//        1302-      tenbjpks_lottery_sg
//        1303-      fivebjpks_lottery_sg
//        1304,1903- jsbjpks_lottery_sg
//        1401-      xyft_lottery_sg
//        1501-      pcegg_lottery_sg
//        1601-      txffc_lottery_sg
//        1701-      tcdlt_lottery_sg
//        1702-      tcplw_lottery_sg
//        1703-      tc7xc_lottery_sg
//        1801-      fcssq_lottery_sg
//        1802-      fc3d_lottery_sg
//        1803-      fc7lc_lottery_sg
//        1902,2203- auspks_lottery_sg
//        2001-      ftjspks_lottery_sg
//        2002-      ftxyft_lottery_sg
//        2003-      ftjsssc_lottery_sg
//        2201-      ausact_lottery_sg
//        2202-      ausssc_lottery_sg
        String lotteryIds[] = {CaipiaoTypeEnum.CQSSC.getTagType(), CaipiaoTypeEnum.XJSSC.getTagType(), CaipiaoTypeEnum.TJSSC.getTagType(), CaipiaoTypeEnum.TENSSC.getTagType(),
                CaipiaoTypeEnum.FIVESSC.getTagType(), CaipiaoTypeEnum.JSSSC.getTagType(), CaipiaoTypeEnum.ONELHC.getTagType(),
                CaipiaoTypeEnum.FIVELHC.getTagType(), CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoTypeEnum.BJPKS.getTagType(), CaipiaoTypeEnum.TENPKS.getTagType(),
                CaipiaoTypeEnum.FIVEPKS.getTagType(), CaipiaoTypeEnum.JSPKS.getTagType(), CaipiaoTypeEnum.XYFEIT.getTagType(), CaipiaoTypeEnum.PCDAND.getTagType(),
                CaipiaoTypeEnum.TXFFC.getTagType(), CaipiaoTypeEnum.DLT.getTagType(), CaipiaoTypeEnum.TCPLW.getTagType(), CaipiaoTypeEnum.TC7XC.getTagType(),
                CaipiaoTypeEnum.FCSSQ.getTagType(), CaipiaoTypeEnum.FC3D.getTagType(), CaipiaoTypeEnum.FC7LC.getTagType(), CaipiaoTypeEnum.KLNIU.getTagType(),
                CaipiaoTypeEnum.AZNIU.getTagType(), CaipiaoTypeEnum.JSNIU.getTagType(), CaipiaoTypeEnum.JSPKFT.getTagType(), CaipiaoTypeEnum.XYFTFT.getTagType(),
                CaipiaoTypeEnum.JSSSCFT.getTagType(), CaipiaoTypeEnum.AUSACT.getTagType(), CaipiaoTypeEnum.AUSSSC.getTagType(), CaipiaoTypeEnum.AUSPKS.getTagType()};
        for (String lotteryId : lotteryIds) {
            if (lotteryId.equals(CaipiaoTypeEnum.CQSSC.getTagType())) {
                CqsscLotterySgExample cqsscLotterySgExample = new CqsscLotterySgExample();
                CqsscLotterySgExample.Criteria criteria = cqsscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                cqsscLotterySgExample.setOrderByClause("`ideal_time` desc");
                cqsscLotterySgExample.setOffset(0);
                cqsscLotterySgExample.setLimit(3);
                List<CqsscLotterySg> list = cqsscLotterySgMapper.selectByExample(cqsscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (CqsscLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.CQSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.XJSSC.getTagType())) {
                XjsscLotterySgExample xjsscLotterySgExample = new XjsscLotterySgExample();
                XjsscLotterySgExample.Criteria criteria = xjsscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                xjsscLotterySgExample.setOrderByClause("`ideal_time` desc");
                xjsscLotterySgExample.setOffset(0);
                xjsscLotterySgExample.setLimit(3);
                List<XjsscLotterySg> list = xjsscLotterySgMapper.selectByExample(xjsscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (XjsscLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XJSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TJSSC.getTagType())) {
                TjsscLotterySgExample tjsscLotterySgExample = new TjsscLotterySgExample();
                TjsscLotterySgExample.Criteria criteria = tjsscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                tjsscLotterySgExample.setOrderByClause("`ideal_time` desc");
                tjsscLotterySgExample.setOffset(0);
                tjsscLotterySgExample.setLimit(3);
                List<TjsscLotterySg> list = tjsscLotterySgMapper.selectByExample(tjsscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TjsscLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TJSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TENSSC.getTagType())) {
                TensscLotterySgExample tensscLotterySgExample = new TensscLotterySgExample();
                TensscLotterySgExample.Criteria criteria = tensscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                tensscLotterySgExample.setOrderByClause("`ideal_time` desc");
                tensscLotterySgExample.setOffset(0);
                tensscLotterySgExample.setLimit(6);
                List<TensscLotterySg> list = tensscLotterySgMapper.selectByExample(tensscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TensscLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TENSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVESSC.getTagType())) {
                FivesscLotterySgExample fivesscLotterySgExample = new FivesscLotterySgExample();
                FivesscLotterySgExample.Criteria criteria = fivesscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                fivesscLotterySgExample.setOrderByClause("`ideal_time` desc");
                fivesscLotterySgExample.setOffset(0);
                fivesscLotterySgExample.setLimit(12);
                List<FivesscLotterySg> list = fivesscLotterySgMapper.selectByExample(fivesscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FivesscLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVESSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSC.getTagType())) {
                JssscLotterySgExample jssscLotterySgExample = new JssscLotterySgExample();
                JssscLotterySgExample.Criteria criteria = jssscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                jssscLotterySgExample.setOrderByClause("`ideal_time` desc");
                jssscLotterySgExample.setOffset(0);
                jssscLotterySgExample.setLimit(15);
                List<JssscLotterySg> list = jssscLotterySgMapper.selectByExample(jssscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (JssscLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.ONELHC.getTagType())) {
                OnelhcLotterySgExample onelhcLotterySgExample = new OnelhcLotterySgExample();
                OnelhcLotterySgExample.Criteria criteria = onelhcLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                onelhcLotterySgExample.setOrderByClause("`ideal_time` desc");
                onelhcLotterySgExample.setOffset(0);
                onelhcLotterySgExample.setLimit(15);
                List<OnelhcLotterySg> list = onelhcLotterySgMapper.selectByExample(onelhcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (OnelhcLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.ONELHC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVELHC.getTagType())) {
                FivelhcLotterySgExample fivelhcLotterySgExample = new FivelhcLotterySgExample();
                FivelhcLotterySgExample.Criteria criteria = fivelhcLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                fivelhcLotterySgExample.setOrderByClause("`ideal_time` desc");
                fivelhcLotterySgExample.setOffset(0);
                fivelhcLotterySgExample.setLimit(12);
                List<FivelhcLotterySg> list = fivelhcLotterySgMapper.selectByExample(fivelhcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FivelhcLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVELHC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AMLHC.getTagType())) {
                AmlhcLotterySgExample sslhcLotterySgExample = new AmlhcLotterySgExample();
                AmlhcLotterySgExample.Criteria criteria = sslhcLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                sslhcLotterySgExample.setOrderByClause("`ideal_time` desc");
                sslhcLotterySgExample.setOffset(0);
                sslhcLotterySgExample.setLimit(6);
                List<AmlhcLotterySg> list = amlhcLotterySgMapper.selectByExample(sslhcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (AmlhcLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AMLHC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.BJPKS.getTagType())) {
                BjpksLotterySgExample bjpksLotterySgExample = new BjpksLotterySgExample();
                BjpksLotterySgExample.Criteria criteria = bjpksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                bjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
                bjpksLotterySgExample.setOffset(0);
                bjpksLotterySgExample.setLimit(3);
                List<BjpksLotterySg> list = bjpksLotterySgMapper.selectByExample(bjpksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (BjpksLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.BJPKS.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TENPKS.getTagType())) {
                TenbjpksLotterySgExample tenbjpksLotterySgExample = new TenbjpksLotterySgExample();
                TenbjpksLotterySgExample.Criteria criteria = tenbjpksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                tenbjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
                tenbjpksLotterySgExample.setOffset(0);
                tenbjpksLotterySgExample.setLimit(6);
                List<TenbjpksLotterySg> list = tenbjpksLotterySgMapper.selectByExample(tenbjpksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TenbjpksLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TENPKS.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVEPKS.getTagType())) {
                FivebjpksLotterySgExample fivebjpksLotterySgExample = new FivebjpksLotterySgExample();
                FivebjpksLotterySgExample.Criteria criteria = fivebjpksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                fivebjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
                fivebjpksLotterySgExample.setOffset(0);
                fivebjpksLotterySgExample.setLimit(12);
                List<FivebjpksLotterySg> list = fivebjpksLotterySgMapper.selectByExample(fivebjpksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FivebjpksLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVEPKS.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKS.getTagType())) {
                JsbjpksLotterySgExample jsbjpksLotterySgExample = new JsbjpksLotterySgExample();
                JsbjpksLotterySgExample.Criteria criteria = jsbjpksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                jsbjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
                jsbjpksLotterySgExample.setOffset(0);
                jsbjpksLotterySgExample.setLimit(15);
                List<JsbjpksLotterySg> list = jsbjpksLotterySgMapper.selectByExample(jsbjpksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (JsbjpksLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSPKS.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.XYFEIT.getTagType())) {
                XyftLotterySgExample xyftLotterySgExample = new XyftLotterySgExample();
                XyftLotterySgExample.Criteria criteria = xyftLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                xyftLotterySgExample.setOrderByClause("`ideal_time` desc");
                xyftLotterySgExample.setOffset(0);
                xyftLotterySgExample.setLimit(12);
                List<XyftLotterySg> list = xyftLotterySgMapper.selectByExample(xyftLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (XyftLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XYFEIT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.PCDAND.getTagType())) {
                PceggLotterySgExample pceggLotterySgExample = new PceggLotterySgExample();
                PceggLotterySgExample.Criteria criteria = pceggLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                pceggLotterySgExample.setOrderByClause("`ideal_time` desc");
                pceggLotterySgExample.setOffset(0);
                pceggLotterySgExample.setLimit(12);
                List<PceggLotterySg> list = pceggLotterySgMapper.selectByExample(pceggLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (PceggLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.PCDAND.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TXFFC.getTagType())) {
                TxffcLotterySgExample txffcLotterySgExample = new TxffcLotterySgExample();
                TxffcLotterySgExample.Criteria criteria = txffcLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                txffcLotterySgExample.setOrderByClause("`ideal_time` desc");
                txffcLotterySgExample.setOffset(0);
                txffcLotterySgExample.setLimit(15);
                List<TxffcLotterySg> list = txffcLotterySgMapper.selectByExample(txffcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TxffcLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TXFFC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.DLT.getTagType())) {
                TcdltLotterySgExample tcdltLotterySgExample = new TcdltLotterySgExample();
                TcdltLotterySgExample.Criteria criteria = tcdltLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                tcdltLotterySgExample.setOrderByClause("`ideal_time` desc");
                tcdltLotterySgExample.setOffset(0);
                tcdltLotterySgExample.setLimit(3);
                List<TcdltLotterySg> list = tcdltLotterySgMapper.selectByExample(tcdltLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TcdltLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.DLT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TCPLW.getTagType())) {
                TcplwLotterySgExample tcPlwLotterySgExample = new TcplwLotterySgExample();
                TcplwLotterySgExample.Criteria criteria = tcPlwLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                tcPlwLotterySgExample.setOrderByClause("`ideal_time` desc");
                tcPlwLotterySgExample.setOffset(0);
                tcPlwLotterySgExample.setLimit(3);
                List<TcplwLotterySg> list = tcplwLotterySgMapper.selectByExample(tcPlwLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TcplwLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TCPLW.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TC7XC.getTagType())) {
                Tc7xcLotterySgExample tc7xcLotterySgExample = new Tc7xcLotterySgExample();
                Tc7xcLotterySgExample.Criteria criteria = tc7xcLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                tc7xcLotterySgExample.setOrderByClause("`ideal_time` desc");
                tc7xcLotterySgExample.setOffset(0);
                tc7xcLotterySgExample.setLimit(3);
                List<Tc7xcLotterySg> list = tc7xcLotterySgMapper.selectByExample(tc7xcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (Tc7xcLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TC7XC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FCSSQ.getTagType())) {
                FcssqLotterySgExample fcssqLotterySgExample = new FcssqLotterySgExample();
                FcssqLotterySgExample.Criteria criteria = fcssqLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                fcssqLotterySgExample.setOrderByClause("`ideal_time` desc");
                fcssqLotterySgExample.setOffset(0);
                fcssqLotterySgExample.setLimit(3);
                List<FcssqLotterySg> list = fcssqLotterySgMapper.selectByExample(fcssqLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FcssqLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FCSSQ.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FC3D.getTagType())) {
                Fc3dLotterySgExample fc3dLotterySgExample = new Fc3dLotterySgExample();
                Fc3dLotterySgExample.Criteria criteria = fc3dLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                fc3dLotterySgExample.setOrderByClause("`ideal_time` desc");
                fc3dLotterySgExample.setOffset(0);
                fc3dLotterySgExample.setLimit(3);
                List<Fc3dLotterySg> list = fc3dLotterySgMapper.selectByExample(fc3dLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (Fc3dLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FC3D.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FC7LC.getTagType())) {
                Fc7lcLotterySgExample fc7lcLotterySgExample = new Fc7lcLotterySgExample();
                Fc7lcLotterySgExample.Criteria criteria = fc7lcLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                fc7lcLotterySgExample.setOrderByClause("`ideal_time` desc");
                fc7lcLotterySgExample.setOffset(0);
                fc7lcLotterySgExample.setLimit(3);
                List<Fc7lcLotterySg> list = fc7lcLotterySgMapper.selectByExample(fc7lcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (Fc7lcLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FC7LC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.KLNIU.getTagType())) {  // 同1105
//				FivesscLotterySgExample fivesscLotterySgExample = new FivesscLotterySgExample();
//				FivesscLotterySgExample.Criteria criteria = fivesscLotterySgExample.createCriteria();
//				criteria.andNumberIsNotNull();
//				criteria.andNumberNotEqualTo("");
//				fivesscLotterySgExample.setOrderByClause("`ideal_time` desc");
//				fivesscLotterySgExample.setOffset(0);
//				fivesscLotterySgExample.setLimit(15);
//				List<FivesscLotterySg> list = fivesscLotterySgMapper.selectByExample(fivesscLotterySgExample);
//
//				JSONArray jsonArray = new JSONArray();
//				for (FivesscLotterySg sg:list) {
//					jsonArray.add(sg);
//				}
//				String jsonString = JSONObject.toJSONString(jsonArray);
//				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG,"1901#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AZNIU.getTagType())) {
                AuspksLotterySgExample auspksLotterySgExample = new AuspksLotterySgExample();
                AuspksLotterySgExample.Criteria criteria = auspksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                auspksLotterySgExample.setOrderByClause("`ideal_time` desc");
                auspksLotterySgExample.setOffset(0);
                auspksLotterySgExample.setLimit(15);
                List<AuspksLotterySg> list = auspksLotterySgMapper.selectByExample(auspksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (AuspksLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AZNIU.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSNIU.getTagType())) {  // 同1304
//				JsbjpksLotterySgExample jsbjpksLotterySgExample = new JsbjpksLotterySgExample();
//				JsbjpksLotterySgExample.Criteria criteria = jsbjpksLotterySgExample.createCriteria();
//				criteria.andNumberIsNotNull();
//				criteria.andNumberNotEqualTo("");
//				jsbjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
//				jsbjpksLotterySgExample.setOffset(0);
//				jsbjpksLotterySgExample.setLimit(15);
//				List<JsbjpksLotterySg> list = jsbjpksLotterySgMapper.selectByExample(jsbjpksLotterySgExample);
//
//				JSONArray jsonArray = new JSONArray();
//				for (JsbjpksLotterySg sg:list) {
//					jsonArray.add(sg);
//				}
//				String jsonString = JSONObject.toJSONString(jsonArray);
//				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG,"1903#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKFT.getTagType())) {
                FtjspksLotterySgExample ftjspksLotterySgExample = new FtjspksLotterySgExample();
                FtjspksLotterySgExample.Criteria criteria = ftjspksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                ftjspksLotterySgExample.setOrderByClause("`ideal_time` desc");
                ftjspksLotterySgExample.setOffset(0);
                ftjspksLotterySgExample.setLimit(15);
                List<FtjspksLotterySg> list = ftjspksLotterySgMapper.selectByExample(ftjspksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FtjspksLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSPKFT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.XYFTFT.getTagType())) {
                FtxyftLotterySgExample ftxyftLotterySgExample = new FtxyftLotterySgExample();
                FtxyftLotterySgExample.Criteria criteria = ftxyftLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                ftxyftLotterySgExample.setOrderByClause("`ideal_time` desc");
                ftxyftLotterySgExample.setOffset(0);
                ftxyftLotterySgExample.setLimit(12);
                List<FtxyftLotterySg> list = ftxyftLotterySgMapper.selectByExample(ftxyftLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FtxyftLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XYFTFT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSCFT.getTagType())) {
                FtjssscLotterySgExample ftjssscLotterySgExample = new FtjssscLotterySgExample();
                FtjssscLotterySgExample.Criteria criteria = ftjssscLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                ftjssscLotterySgExample.setOrderByClause("`ideal_time` desc");
                ftjssscLotterySgExample.setOffset(0);
                ftjssscLotterySgExample.setLimit(15);
                List<FtjssscLotterySg> list = ftjssscLotterySgMapper.selectByExample(ftjssscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FtjssscLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSSSCFT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AUSACT.getTagType())) {
                AusactLotterySgExample ausactLotterySgExample = new AusactLotterySgExample();
                AusactLotterySgExample.Criteria criteria = ausactLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                ausactLotterySgExample.setOrderByClause("`ideal_time` desc");
                ausactLotterySgExample.setOffset(0);
                ausactLotterySgExample.setLimit(15);
                List<AusactLotterySg> list = ausactLotterySgMapper.selectByExample(ausactLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (AusactLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AUSACT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AUSSSC.getTagType())) {
                AussscLotterySgExample aussscLotterySgExample = new AussscLotterySgExample();
                AussscLotterySgExample.Criteria criteria = aussscLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                aussscLotterySgExample.setOrderByClause("`ideal_time` desc");
                aussscLotterySgExample.setOffset(0);
                aussscLotterySgExample.setLimit(15);
                List<AussscLotterySg> list = aussscLotterySgMapper.selectByExample(aussscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (AussscLotterySg sg : list) {
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AUSSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AUSPKS.getTagType())) {  // 同1902
//				AuspksLotterySgExample auspksLotterySgExample = new AuspksLotterySgExample();
//				AuspksLotterySgExample.Criteria criteria = auspksLotterySgExample.createCriteria();
//				criteria.andNumberIsNotNull();
//				criteria.andNumberNotEqualTo("");
//				auspksLotterySgExample.setOrderByClause("`ideal_time` desc");
//				auspksLotterySgExample.setOffset(0);
//				auspksLotterySgExample.setLimit(15);
//				List<AuspksLotterySg> list = aussscLotterySgMapper.selectByExample(aussscLotterySgExample);
//
//				JSONArray jsonArray = new JSONArray();
//				for (AussscLotterySg sg:list) {
//					jsonArray.add(sg);
//				}
//				String jsonString = JSONObject.toJSONString(jsonArray);
//				jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG,"2202:" + jsonString);
            }
        }


    }

    @Override
    public void sendSgOneDayData() {
        String lotteryIds[] = {
                CaipiaoTypeEnum.CQSSC.getTagType(), CaipiaoTypeEnum.XJSSC.getTagType(), CaipiaoTypeEnum.TJSSC.getTagType(), CaipiaoTypeEnum.TENSSC.getTagType(),
                CaipiaoTypeEnum.FIVESSC.getTagType(), CaipiaoTypeEnum.JSSSC.getTagType(), CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoTypeEnum.FIVELHC.getTagType(),
                CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoTypeEnum.BJPKS.getTagType(), CaipiaoTypeEnum.TENPKS.getTagType(), CaipiaoTypeEnum.FIVEPKS.getTagType(),
                CaipiaoTypeEnum.JSPKS.getTagType(), CaipiaoTypeEnum.XYFEIT.getTagType(), CaipiaoTypeEnum.XYFEITSC.getTagType(), CaipiaoTypeEnum.PCDAND.getTagType(), CaipiaoTypeEnum.TXFFC.getTagType(),
                CaipiaoTypeEnum.AZNIU.getTagType(), CaipiaoTypeEnum.JSPKFT.getTagType(), CaipiaoTypeEnum.XYFTFT.getTagType(),
                CaipiaoTypeEnum.JSSSCFT.getTagType(), CaipiaoTypeEnum.AUSACT.getTagType(), CaipiaoTypeEnum.AUSSSC.getTagType()};
        for (String lotteryId : lotteryIds) {
            if (lotteryId.equals(CaipiaoTypeEnum.CQSSC.getTagType())) {
                CqsscLotterySgExample cqsscLotterySgExample = new CqsscLotterySgExample();
                CqsscLotterySgExample.Criteria criteria = cqsscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                cqsscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<CqsscLotterySg> list = cqsscLotterySgMapper.selectByExample(cqsscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (CqsscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.CQSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.XJSSC.getTagType())) {
                XjsscLotterySgExample xjsscLotterySgExample = new XjsscLotterySgExample();
                XjsscLotterySgExample.Criteria criteria = xjsscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                xjsscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<XjsscLotterySg> list = xjsscLotterySgMapper.selectByExample(xjsscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (XjsscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XJSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TJSSC.getTagType())) {
                TjsscLotterySgExample tjsscLotterySgExample = new TjsscLotterySgExample();
                TjsscLotterySgExample.Criteria criteria = tjsscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                tjsscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<TjsscLotterySg> list = tjsscLotterySgMapper.selectByExample(tjsscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TjsscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TJSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TENSSC.getTagType())) {
                TensscLotterySgExample tensscLotterySgExample = new TensscLotterySgExample();
                TensscLotterySgExample.Criteria criteria = tensscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                tensscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<TensscLotterySg> list = tensscLotterySgMapper.selectByExample(tensscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TensscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TENSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVESSC.getTagType())) {
                FivesscLotterySgExample fivesscLotterySgExample = new FivesscLotterySgExample();
                FivesscLotterySgExample.Criteria criteria = fivesscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                fivesscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<FivesscLotterySg> list = fivesscLotterySgMapper.selectByExample(fivesscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FivesscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVESSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSC.getTagType())) {
                JssscLotterySgExample jssscLotterySgExample = new JssscLotterySgExample();
                JssscLotterySgExample.Criteria criteria = jssscLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                jssscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<JssscLotterySg> list = jssscLotterySgMapper.selectByExample(jssscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (JssscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSSSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.ONELHC.getTagType())) {
                OnelhcLotterySgExample onelhcLotterySgExample = new OnelhcLotterySgExample();
                OnelhcLotterySgExample.Criteria criteria = onelhcLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                onelhcLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<OnelhcLotterySg> list = onelhcLotterySgMapper.selectByExample(onelhcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (OnelhcLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.ONELHC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVELHC.getTagType())) {
                FivelhcLotterySgExample fivelhcLotterySgExample = new FivelhcLotterySgExample();
                FivelhcLotterySgExample.Criteria criteria = fivelhcLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                fivelhcLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<FivelhcLotterySg> list = fivelhcLotterySgMapper.selectByExample(fivelhcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FivelhcLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVELHC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AMLHC.getTagType())) {
                AmlhcLotterySgExample amlhcLotterySgExample = new AmlhcLotterySgExample();
                AmlhcLotterySgExample.Criteria criteria = amlhcLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                amlhcLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<AmlhcLotterySg> list = amlhcLotterySgMapper.selectByExample(amlhcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (AmlhcLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AMLHC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.BJPKS.getTagType())) {
                BjpksLotterySgExample bjpksLotterySgExample = new BjpksLotterySgExample();
                BjpksLotterySgExample.Criteria criteria = bjpksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                bjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<BjpksLotterySg> list = bjpksLotterySgMapper.selectByExample(bjpksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (BjpksLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.BJPKS.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TENPKS.getTagType())) {
                TenbjpksLotterySgExample tenbjpksLotterySgExample = new TenbjpksLotterySgExample();
                TenbjpksLotterySgExample.Criteria criteria = tenbjpksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                tenbjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<TenbjpksLotterySg> list = tenbjpksLotterySgMapper.selectByExample(tenbjpksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TenbjpksLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TENPKS.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVEPKS.getTagType())) {
                FivebjpksLotterySgExample fivebjpksLotterySgExample = new FivebjpksLotterySgExample();
                FivebjpksLotterySgExample.Criteria criteria = fivebjpksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                fivebjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<FivebjpksLotterySg> list = fivebjpksLotterySgMapper.selectByExample(fivebjpksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FivebjpksLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVEPKS.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKS.getTagType())) {
                JsbjpksLotterySgExample jsbjpksLotterySgExample = new JsbjpksLotterySgExample();
                JsbjpksLotterySgExample.Criteria criteria = jsbjpksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                jsbjpksLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<JsbjpksLotterySg> list = jsbjpksLotterySgMapper.selectByExample(jsbjpksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (JsbjpksLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSPKS.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.XYFEIT.getTagType())) {
                XyftLotterySgExample xyftLotterySgExample = new XyftLotterySgExample();
                XyftLotterySgExample.Criteria criteria = xyftLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                xyftLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<XyftLotterySg> list = xyftLotterySgMapper.selectByExample(xyftLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (XyftLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XYFEIT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.XYFEITSC.getTagType())) {
                XyftscLotterySgExample xyftscLotterySgExample = new XyftscLotterySgExample();
                XyftscLotterySgExample.Criteria criteria = xyftscLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                xyftscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(calendar.getTime());
                List<XyftscLotterySg> list = xyftscLotterySgMapper.selectByExample(xyftscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (XyftscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XYFEITSC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.PCDAND.getTagType())) {
                PceggLotterySgExample pceggLotterySgExample = new PceggLotterySgExample();
                PceggLotterySgExample.Criteria criteria = pceggLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                pceggLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<PceggLotterySg> list = pceggLotterySgMapper.selectByExample(pceggLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (PceggLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.PCDAND.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.TXFFC.getTagType())) {
                TxffcLotterySgExample txffcLotterySgExample = new TxffcLotterySgExample();
                TxffcLotterySgExample.Criteria criteria = txffcLotterySgExample.createCriteria();
                criteria.andWanIsNotNull();
                txffcLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<TxffcLotterySg> list = txffcLotterySgMapper.selectByExample(txffcLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (TxffcLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TXFFC.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AZNIU.getTagType())) {
                AuspksLotterySgExample auspksLotterySgExample = new AuspksLotterySgExample();
                AuspksLotterySgExample.Criteria criteria = auspksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                auspksLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<AuspksLotterySg> list = auspksLotterySgMapper.selectByExample(auspksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (AuspksLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AZNIU.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKFT.getTagType())) {
                FtjspksLotterySgExample ftjspksLotterySgExample = new FtjspksLotterySgExample();
                FtjspksLotterySgExample.Criteria criteria = ftjspksLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                ftjspksLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<FtjspksLotterySg> list = ftjspksLotterySgMapper.selectByExample(ftjspksLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FtjspksLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSPKFT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.XYFTFT.getTagType())) {
                FtxyftLotterySgExample ftxyftLotterySgExample = new FtxyftLotterySgExample();
                FtxyftLotterySgExample.Criteria criteria = ftxyftLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                ftxyftLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<FtxyftLotterySg> list = ftxyftLotterySgMapper.selectByExample(ftxyftLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FtxyftLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XYFTFT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSCFT.getTagType())) {
                FtjssscLotterySgExample ftjssscLotterySgExample = new FtjssscLotterySgExample();
                FtjssscLotterySgExample.Criteria criteria = ftjssscLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                ftjssscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<FtjssscLotterySg> list = ftjssscLotterySgMapper.selectByExample(ftjssscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (FtjssscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSSSCFT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AUSACT.getTagType())) {
                AusactLotterySgExample ausactLotterySgExample = new AusactLotterySgExample();
                AusactLotterySgExample.Criteria criteria = ausactLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                ausactLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<AusactLotterySg> list = ausactLotterySgMapper.selectByExample(ausactLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (AusactLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AUSACT.getTagType() + "#" + jsonString);
            } else if (lotteryId.equals(CaipiaoTypeEnum.AUSSSC.getTagType())) {
                AussscLotterySgExample aussscLotterySgExample = new AussscLotterySgExample();
                AussscLotterySgExample.Criteria criteria = aussscLotterySgExample.createCriteria();
                criteria.andNumberIsNotNull();
                criteria.andNumberNotEqualTo("");
                aussscLotterySgExample.setOrderByClause("`ideal_time` desc");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                criteria.andIdealTimeGreaterThan(DateUtils.formatDate(calendar.getTime(), DateUtils.FORMAT_YYYY_MM_DD_HHMMSS));
                List<AussscLotterySg> list = aussscLotterySgMapper.selectByExample(aussscLotterySgExample);
                if (list.size() == 0) continue;

                JSONArray jsonArray = new JSONArray();
                for (AussscLotterySg sg : list) {
                    sg.setId(null);
                    jsonArray.add(sg);
                }
                String jsonString = JSONObject.toJSONString(jsonArray);
                jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AUSSSC.getTagType() + "#" + jsonString);
            }

        }
    }

    @Override
    public void sendSgYuqiOneDayData() {
        String lotteryIds[] = {
                CaipiaoTypeEnum.CQSSC.getTagType(), CaipiaoTypeEnum.XJSSC.getTagType(), CaipiaoTypeEnum.TJSSC.getTagType(), CaipiaoTypeEnum.TENSSC.getTagType(),
                CaipiaoTypeEnum.FIVESSC.getTagType(), CaipiaoTypeEnum.JSSSC.getTagType(), CaipiaoTypeEnum.ONELHC.getTagType(), CaipiaoTypeEnum.FIVELHC.getTagType(),
                CaipiaoTypeEnum.AMLHC.getTagType(), CaipiaoTypeEnum.BJPKS.getTagType(), CaipiaoTypeEnum.TENPKS.getTagType(), CaipiaoTypeEnum.FIVEPKS.getTagType(),
                CaipiaoTypeEnum.JSPKS.getTagType(), CaipiaoTypeEnum.XYFEIT.getTagType(), CaipiaoTypeEnum.PCDAND.getTagType(), CaipiaoTypeEnum.TXFFC.getTagType(),
                CaipiaoTypeEnum.AZNIU.getTagType(), CaipiaoTypeEnum.JSPKFT.getTagType(), CaipiaoTypeEnum.XYFTFT.getTagType(),
                CaipiaoTypeEnum.JSSSCFT.getTagType(), CaipiaoTypeEnum.AUSACT.getTagType(), CaipiaoTypeEnum.AUSSSC.getTagType()};
        for (String lotteryId : lotteryIds) {
            if (lotteryId.equals(CaipiaoTypeEnum.CQSSC.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.XJSSC.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.TJSSC.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.TENSSC.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVESSC.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSC.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.ONELHC.getTagType())) {
                OnelhcLotterySgExample sgExample = new OnelhcLotterySgExample();
                OnelhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
                Calendar calendar = Calendar.getInstance();
                criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
                sgExample.setOrderByClause("`ideal_time` asc");
                List<OnelhcLotterySg> sgList = onelhcLotterySgMapper.selectByExample(sgExample);
                if (sgList.size() > 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (OnelhcLotterySg sg : sgList) {
                        sg.setId(null);
                        jsonArray.add(sg);
                    }
                    String jsonString = JSONObject.toJSONString(jsonArray);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ONELHC_YUQI_DATA, "OnelhcLotterySg:" + jsonString);
                }
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVELHC.getTagType())) {
                FivelhcLotterySgExample sgExample = new FivelhcLotterySgExample();
                FivelhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
                Calendar calendar = Calendar.getInstance();
                criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
                sgExample.setOrderByClause("`ideal_time` asc");
                List<FivelhcLotterySg> sgList = fivelhcLotterySgMapper.selectByExample(sgExample);
                if (sgList.size() > 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (FivelhcLotterySg sg : sgList) {
                        sg.setId(null);
                        jsonArray.add(sg);
                    }
                    String jsonString = JSONObject.toJSONString(jsonArray);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_FIVELHC_YUQI_DATA, "FivelhcLotterySg:" + jsonString);
                }
            } else if (lotteryId.equals(CaipiaoTypeEnum.AMLHC.getTagType())) {
                AmlhcLotterySgExample sgExample = new AmlhcLotterySgExample();
                AmlhcLotterySgExample.Criteria criteria = sgExample.createCriteria();
                Calendar calendar = Calendar.getInstance();
                criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
                sgExample.setOrderByClause("`ideal_time` asc");
                List<AmlhcLotterySg> sgList = amlhcLotterySgMapper.selectByExample(sgExample);
                if (sgList.size() > 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (AmlhcLotterySg sg : sgList) {
                        sg.setId(null);
                        jsonArray.add(sg);
                    }
                    String jsonString = JSONObject.toJSONString(jsonArray);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AMLHC_YUQI_DATA, "SslhcLotterySg:" + jsonString);
                }
            } else if (lotteryId.equals(CaipiaoTypeEnum.BJPKS.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.TENPKS.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.FIVEPKS.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKS.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.XYFEIT.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.PCDAND.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.TXFFC.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.AZNIU.getTagType())) {
                AuspksLotterySgExample sgExample = new AuspksLotterySgExample();
                AuspksLotterySgExample.Criteria criteria = sgExample.createCriteria();
                Calendar calendar = Calendar.getInstance();
                criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
                sgExample.setOrderByClause("`ideal_time` asc");
                List<AuspksLotterySg> sgList = auspksLotterySgMapper.selectByExample(sgExample);
                if (sgList.size() > 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (AuspksLotterySg sg : sgList) {
                        sg.setId(null);
                        jsonArray.add(sg);
                    }
                    String jsonString = JSONObject.toJSONString(jsonArray);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_PKS_YUQI_DATA, "AuspksLotterySg:" + jsonString);
                }
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKFT.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.XYFTFT.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSCFT.getTagType())) {
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
            } else if (lotteryId.equals(CaipiaoTypeEnum.AUSACT.getTagType())) {
                AusactLotterySgExample sgExample = new AusactLotterySgExample();
                AusactLotterySgExample.Criteria criteria = sgExample.createCriteria();
                Calendar calendar = Calendar.getInstance();
                criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
                sgExample.setOrderByClause("`ideal_time` asc");
                List<AusactLotterySg> sgList = ausactLotterySgMapper.selectByExample(sgExample);
                if (sgList.size() > 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (AusactLotterySg sg : sgList) {
                        sg.setId(null);
                        jsonArray.add(sg);
                    }
                    String jsonString = JSONObject.toJSONString(jsonArray);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_ACT_YUQI_DATA, "AusactLotterySg:" + jsonString);
                }
            } else if (lotteryId.equals(CaipiaoTypeEnum.AUSSSC.getTagType())) {
                AussscLotterySgExample sgExample = new AussscLotterySgExample();
                AussscLotterySgExample.Criteria criteria = sgExample.createCriteria();
                Calendar calendar = Calendar.getInstance();
                criteria.andIdealTimeGreaterThanOrEqualTo(DateUtils.formatDate(new Date(), DateUtils.datePattern));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                criteria.andIdealTimeLessThan(DateUtils.formatDate(calendar.getTime(), DateUtils.datePattern));
                sgExample.setOrderByClause("`ideal_time` asc");
                List<AussscLotterySg> sgList = aussscLotterySgMapper.selectByExample(sgExample);
                if (sgList.size() > 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (AussscLotterySg sg : sgList) {
                        sg.setId(null);
                        jsonArray.add(sg);
                    }
                    String jsonString = JSONObject.toJSONString(jsonArray);
                    jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_AUS_SSC_YUQI_DATA, "AussscLotterySg:" + jsonString);
                }
            }

        }
    }
}
