package com.caipiao.task.server.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caipiao.core.library.enums.CaipiaoTypeEnum;
import com.caipiao.core.library.tool.DateUtils;
import com.caipiao.core.library.tool.StringUtils;
import com.caipiao.task.server.config.ActiveMQConfig;
import com.caipiao.task.server.util.CommonService;
import com.mapper.*;
import com.mapper.domain.*;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MissingSgReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MissingSgReceiver.class);
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private CommonService commonService;

    @Autowired
    private AusactLotterySgMapper ausactLotterySgMapper;
    @Autowired
    private AussscLotterySgMapper aussscLotterySgMapper;
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
    private FtxyftLotterySgMapper ftxyftLotterySgMapper;
    @Autowired
    private FtjspksLotterySgMapper ftjspksLotterySgMapper;
    @Autowired
    private FtjssscLotterySgMapper ftjssscLotterySgMapper;

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


    @JmsListener(destination = ActiveMQConfig.TOPIC_MISSING_LOTTERY_SG, containerFactory = "jmsListenerContainerQueue")
    public void receiveMissingLotterySg(String message) { //  从caipiao库 查找没有开奖的数据同步过来， 在sg库查询，  如果有赛果数据，则再同步到caipiao库
        logger.info("接收到丢失数据：{}", message);
        String messageArray[] = message.split("#");
        String lotteryId = messageArray[0];
        String issues[] = messageArray[1].split(",");

        if (lotteryId.equals(CaipiaoTypeEnum.CQSSC.getTagType())) {
            List<CqsscLotterySg> cqsscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                CqsscLotterySgExample cqsscLotterySgExample = new CqsscLotterySgExample();
                CqsscLotterySgExample.Criteria criteria = cqsscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                CqsscLotterySg thisSg = cqsscLotterySgMapper.selectOneByExample(cqsscLotterySgExample);
                if (thisSg.getWan() != null) {
                    cqsscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (CqsscLotterySg sg : cqsscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.CQSSC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.XJSSC.getTagType())) {
            List<XjsscLotterySg> xjsscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                XjsscLotterySgExample xjsscLotterySgExample = new XjsscLotterySgExample();
                XjsscLotterySgExample.Criteria criteria = xjsscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                XjsscLotterySg thisSg = xjsscLotterySgMapper.selectOneByExample(xjsscLotterySgExample);
                if (thisSg.getWan() != null) {
                    xjsscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (XjsscLotterySg sg : xjsscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XJSSC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.TJSSC.getTagType())) {
            List<TjsscLotterySg> tjsscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                TjsscLotterySgExample tjsscLotterySgExample = new TjsscLotterySgExample();
                TjsscLotterySgExample.Criteria criteria = tjsscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                TjsscLotterySg thisSg = tjsscLotterySgMapper.selectOneByExample(tjsscLotterySgExample);
                if (thisSg.getWan() != null) {
                    tjsscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (TjsscLotterySg sg : tjsscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TJSSC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.TENSSC.getTagType())) {
            List<TensscLotterySg> tensscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                TensscLotterySgExample tensscLotterySgExample = new TensscLotterySgExample();
                TensscLotterySgExample.Criteria criteria = tensscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                TensscLotterySg thisSg = tensscLotterySgMapper.selectOneByExample(tensscLotterySgExample);
                if (thisSg.getWan() != null) {
                    tensscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (TensscLotterySg sg : tensscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TENSSC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.FIVESSC.getTagType())) {
            List<FivesscLotterySg> fivesscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                FivesscLotterySgExample fivesscLotterySgExample = new FivesscLotterySgExample();
                FivesscLotterySgExample.Criteria criteria = fivesscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                FivesscLotterySg thisSg = fivesscLotterySgMapper.selectOneByExample(fivesscLotterySgExample);
                if (thisSg.getWan() != null) {
                    fivesscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (FivesscLotterySg sg : fivesscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVESSC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSC.getTagType())) {
            List<JssscLotterySg> jssscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                JssscLotterySgExample jssscLotterySgExample = new JssscLotterySgExample();
                JssscLotterySgExample.Criteria criteria = jssscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                JssscLotterySg thisSg = jssscLotterySgMapper.selectOneByExample(jssscLotterySgExample);
                if (thisSg.getWan() != null) {
                    jssscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (JssscLotterySg sg : jssscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSSSC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.ONELHC.getTagType())) {
            List<OnelhcLotterySg> onelhcLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                OnelhcLotterySgExample onelhcLotterySgExample = new OnelhcLotterySgExample();
                OnelhcLotterySgExample.Criteria criteria = onelhcLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                OnelhcLotterySg thisSg = onelhcLotterySgMapper.selectOneByExample(onelhcLotterySgExample);
                if (thisSg.getNumber() != null) {
                    onelhcLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (OnelhcLotterySg sg : onelhcLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.ONELHC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.FIVELHC.getTagType())) {
            List<FivelhcLotterySg> fivelhcLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                FivelhcLotterySgExample fivelhcLotterySgExample = new FivelhcLotterySgExample();
                FivelhcLotterySgExample.Criteria criteria = fivelhcLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                FivelhcLotterySg thisSg = fivelhcLotterySgMapper.selectOneByExample(fivelhcLotterySgExample);
                if (thisSg.getNumber() != null) {
                    fivelhcLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (FivelhcLotterySg sg : fivelhcLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVELHC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.AMLHC.getTagType())) {
            List<AmlhcLotterySg> sslhcLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                AmlhcLotterySgExample amlhcLotterySgExample = new AmlhcLotterySgExample();
                AmlhcLotterySgExample.Criteria criteria = amlhcLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                AmlhcLotterySg thisSg = amlhcLotterySgMapper.selectOneByExample(amlhcLotterySgExample);
                if (thisSg.getNumber() != null) {
                    sslhcLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (AmlhcLotterySg sg : sslhcLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AMLHC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.BJPKS.getTagType())) {
            List<BjpksLotterySg> bjpksLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                BjpksLotterySgExample bjpksLotterySgExample = new BjpksLotterySgExample();
                BjpksLotterySgExample.Criteria criteria = bjpksLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                BjpksLotterySg thisSg = bjpksLotterySgMapper.selectOneByExample(bjpksLotterySgExample);
                if (thisSg.getNumber() != null) {
                    bjpksLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (BjpksLotterySg sg : bjpksLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.BJPKS.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.TENPKS.getTagType())) {
            List<TenbjpksLotterySg> tenbjpksLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                TenbjpksLotterySgExample tenbjpksLotterySgExample = new TenbjpksLotterySgExample();
                TenbjpksLotterySgExample.Criteria criteria = tenbjpksLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                TenbjpksLotterySg thisSg = tenbjpksLotterySgMapper.selectOneByExample(tenbjpksLotterySgExample);
                if (thisSg.getNumber() != null) {
                    tenbjpksLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (TenbjpksLotterySg sg : tenbjpksLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TENPKS.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.FIVEPKS.getTagType())) {
            List<FivebjpksLotterySg> fivebjpksLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                FivebjpksLotterySgExample fivebjpksLotterySgExample = new FivebjpksLotterySgExample();
                FivebjpksLotterySgExample.Criteria criteria = fivebjpksLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                FivebjpksLotterySg thisSg = fivebjpksLotterySgMapper.selectOneByExample(fivebjpksLotterySgExample);
                if (thisSg.getNumber() != null) {
                    fivebjpksLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (FivebjpksLotterySg sg : fivebjpksLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.FIVEPKS.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKS.getTagType())) {
            List<JsbjpksLotterySg> jsbjpksLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                JsbjpksLotterySgExample jsbjpksLotterySgExample = new JsbjpksLotterySgExample();
                JsbjpksLotterySgExample.Criteria criteria = jsbjpksLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                JsbjpksLotterySg thisSg = jsbjpksLotterySgMapper.selectOneByExample(jsbjpksLotterySgExample);
                if (thisSg.getNumber() != null) {
                    jsbjpksLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (JsbjpksLotterySg sg : jsbjpksLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSPKS.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.XYFEIT.getTagType())) {
            List<XyftLotterySg> xyftLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                XyftLotterySgExample xyftLotterySgExample = new XyftLotterySgExample();
                XyftLotterySgExample.Criteria criteria = xyftLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                XyftLotterySg thisSg = xyftLotterySgMapper.selectOneByExample(xyftLotterySgExample);
                if (thisSg.getNumber() != null) {
                    xyftLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (XyftLotterySg sg : xyftLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XYFEIT.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.XYFEITSC.getTagType())) {
            List<XyftscLotterySg> xyftscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                XyftscLotterySgExample xyftscLotterySgExample = new XyftscLotterySgExample();
                XyftscLotterySgExample.Criteria criteria = xyftscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                XyftscLotterySg thisSg = xyftscLotterySgMapper.selectOneByExample(xyftscLotterySgExample);
                if (thisSg.getNumber() != null) {
                    xyftscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (XyftscLotterySg sg : xyftscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XYFEITSC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.PCDAND.getTagType())) {
            List<PceggLotterySg> pceggLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                PceggLotterySgExample pceggLotterySgExample = new PceggLotterySgExample();
                PceggLotterySgExample.Criteria criteria = pceggLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                PceggLotterySg thisSg = pceggLotterySgMapper.selectOneByExample(pceggLotterySgExample);
                if (thisSg.getNumber() != null) {
                    pceggLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (PceggLotterySg sg : pceggLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.PCDAND.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.TXFFC.getTagType())) {
            List<TxffcLotterySg> txffcLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                TxffcLotterySgExample txffcLotterySgExample = new TxffcLotterySgExample();
                TxffcLotterySgExample.Criteria criteria = txffcLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                TxffcLotterySg thisSg = txffcLotterySgMapper.selectOneByExample(txffcLotterySgExample);
                if (thisSg.getWan() != null) {
                    txffcLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (TxffcLotterySg sg : txffcLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.TXFFC.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.AZNIU.getTagType())) {
            List<AuspksLotterySg> auspksLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                AuspksLotterySgExample auspksLotterySgExample = new AuspksLotterySgExample();
                AuspksLotterySgExample.Criteria criteria = auspksLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                AuspksLotterySg thisSg = auspksLotterySgMapper.selectOneByExample(auspksLotterySgExample);
                if (thisSg.getNumber() != null) {
                    auspksLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (AuspksLotterySg sg : auspksLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AZNIU.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.JSPKFT.getTagType())) {
            List<FtjspksLotterySg> ftjspksLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                FtjspksLotterySgExample ftjspksLotterySgExample = new FtjspksLotterySgExample();
                FtjspksLotterySgExample.Criteria criteria = ftjspksLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                FtjspksLotterySg thisSg = ftjspksLotterySgMapper.selectOneByExample(ftjspksLotterySgExample);
                if (thisSg.getNumber() != null) {
                    ftjspksLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (FtjspksLotterySg sg : ftjspksLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSPKFT.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.XYFTFT.getTagType())) {
            List<FtxyftLotterySg> ftxyftLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                FtxyftLotterySgExample ftxyftLotterySgExample = new FtxyftLotterySgExample();
                FtxyftLotterySgExample.Criteria criteria = ftxyftLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                FtxyftLotterySg thisSg = ftxyftLotterySgMapper.selectOneByExample(ftxyftLotterySgExample);
                if (thisSg.getNumber() != null) {
                    ftxyftLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (FtxyftLotterySg sg : ftxyftLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.XYFTFT.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.JSSSCFT.getTagType())) {
            List<FtjssscLotterySg> ftjssscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                FtjssscLotterySgExample ftjssscLotterySgExample = new FtjssscLotterySgExample();
                FtjssscLotterySgExample.Criteria criteria = ftjssscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                FtjssscLotterySg thisSg = ftjssscLotterySgMapper.selectOneByExample(ftjssscLotterySgExample);
                if (thisSg.getNumber() != null) {
                    ftjssscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (FtjssscLotterySg sg : ftjssscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.JSSSCFT.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.AUSACT.getTagType())) {
            List<AusactLotterySg> ausactLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                AusactLotterySgExample ausactLotterySgExample = new AusactLotterySgExample();
                AusactLotterySgExample.Criteria criteria = ausactLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                AusactLotterySg thisSg = ausactLotterySgMapper.selectOneByExample(ausactLotterySgExample);
                if (thisSg.getNumber() != null) {
                    ausactLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (AusactLotterySg sg : ausactLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AUSACT.getTagType() + "#" + jsonString);
        } else if (lotteryId.equals(CaipiaoTypeEnum.AUSSSC.getTagType())) {
            List<AussscLotterySg> aussscLotterySgList = new ArrayList<>();
            for (String issue : issues) {
                AussscLotterySgExample aussscLotterySgExample = new AussscLotterySgExample();
                AussscLotterySgExample.Criteria criteria = aussscLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                AussscLotterySg thisSg = aussscLotterySgMapper.selectOneByExample(aussscLotterySgExample);
                if (thisSg.getNumber() != null) {
                    aussscLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (AussscLotterySg sg : aussscLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG, CaipiaoTypeEnum.AUSSSC.getTagType() + "#" + jsonString);
        }else         if(lotteryId.equals(CaipiaoTypeEnum.AZKS.getTagType())){
            List<AzksLotterySg> azksLotterySgList = new ArrayList<>();
            for(String issue:issues){
                AzksLotterySgExample azksLotterySgExample = new AzksLotterySgExample();
                AzksLotterySgExample.Criteria criteria = azksLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                AzksLotterySg thisSg = azksLotterySgMapper.selectOneByExample(azksLotterySgExample);
                if(thisSg.getBai() != null){
                    azksLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (AzksLotterySg sg:azksLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG,CaipiaoTypeEnum.AZKS.getTagType()+"#" + jsonString);
        }else if(lotteryId.equals(CaipiaoTypeEnum.DZKS.getTagType())){
            List<DzksLotterySg> dzksLotterySgList = new ArrayList<>();
            for(String issue:issues){
                DzksLotterySgExample dzksLotterySgExample = new DzksLotterySgExample();
                DzksLotterySgExample.Criteria criteria = dzksLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                DzksLotterySg thisSg = dzksLotterySgMapper.selectOneByExample(dzksLotterySgExample);
                if(thisSg.getBai() != null){
                    dzksLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (DzksLotterySg sg:dzksLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG,CaipiaoTypeEnum.DZKS.getTagType()+"#" + jsonString);
        }else if(lotteryId.equals(CaipiaoTypeEnum.DZPCEGG.getTagType())){
            List<DzpceggLotterySg> dzpceggLotterySgList = new ArrayList<>();
            for(String issue:issues){
                DzpceggLotterySgExample dzpceggLotterySgExample = new DzpceggLotterySgExample();
                DzpceggLotterySgExample.Criteria criteria = dzpceggLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                DzpceggLotterySg thisSg = dzpceggLotterySgMapper.selectOneByExample(dzpceggLotterySgExample);
                if(StringUtils.isNotEmpty(thisSg.getNumber())){
                    dzpceggLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (DzpceggLotterySg sg:dzpceggLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG,CaipiaoTypeEnum.DZPCEGG.getTagType()+"#" + jsonString);
        }else if(lotteryId.equals(CaipiaoTypeEnum.DZXYFT.getTagType())){
            List<DzxyftLotterySg> dzxyftLotterySgList = new ArrayList<>();
            for(String issue:issues){
                DzxyftLotterySgExample dzxyftLotterySgExample = new DzxyftLotterySgExample();
                DzxyftLotterySgExample.Criteria criteria = dzxyftLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                DzxyftLotterySg thisSg = dzxyftLotterySgMapper.selectOneByExample(dzxyftLotterySgExample);
                if(StringUtils.isNotEmpty(thisSg.getNumber())){
                    dzxyftLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (DzxyftLotterySg sg:dzxyftLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG,CaipiaoTypeEnum.DZXYFT.getTagType()+"#" + jsonString);
        }else if(lotteryId.equals(CaipiaoTypeEnum.XJPLHC.getTagType())){
            List<XjplhcLotterySg> xjplhcLotterySgList = new ArrayList<>();
            for(String issue:issues){
                XjplhcLotterySgExample xjplhcLotterySgExample = new XjplhcLotterySgExample();
                XjplhcLotterySgExample.Criteria criteria = xjplhcLotterySgExample.createCriteria();
                criteria.andIssueEqualTo(issue);
                XjplhcLotterySg thisSg = xjplhcLotterySgMapper.selectOneByExample(xjplhcLotterySgExample);
                if(StringUtils.isNotEmpty(thisSg.getNumber())){
                    xjplhcLotterySgList.add(thisSg);
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (XjplhcLotterySg sg:xjplhcLotterySgList) {
                jsonArray.add(sg);
            }
            String jsonString = JSONObject.toJSONString(jsonArray);
            jmsMessagingTemplate.convertAndSend(ActiveMQConfig.TOPIC_ALL_LOTTERY_SG,CaipiaoTypeEnum.XJPLHC.getTagType()+"#" + jsonString);
        }
    }

}
