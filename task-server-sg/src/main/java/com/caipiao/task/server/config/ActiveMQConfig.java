package com.caipiao.task.server.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
@EnableJms
public class ActiveMQConfig implements InitializingBean {

    private static final Logger logger= LoggerFactory.getLogger(ActiveMQConfig.class);
    /**
     * 点对点
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("kaijiang-queue");
    }

    /**
     * 发布开奖信息
     *
     * @return
     */
    @Bean
    public Topic topic() {
        return new ActiveMQTopic("kaijiang");
    }


    //##############################################################################################
    //###                                   极速pk最新一期发布开奖信息                           ###
    // 极速PK10 队列名（app调用）
    public final static String TOPIC_PK10_JS = "new_jsbjpks";

    @Bean
    public Topic topicNewJspks() {
        return new ActiveMQTopic(TOPIC_PK10_JS);
    }

    // 极速PK10番摊 队列名（app调用）
    public final static String TOPIC_PK10_JS_FT = "TOPIC_PK10_JS_FT";

    @Bean
    public Topic topicPk10JsFt() {
        return new ActiveMQTopic(TOPIC_PK10_JS_FT);
    }

    // 极速牛牛 队列名（app调用）
    public final static String TOPIC_APP_JS_NN = "TOPIC_APP_JS_NN";

    @Bean
    public Topic topicAppJsNn() {
        return new ActiveMQTopic(TOPIC_APP_JS_NN);
    }

    // 极速北京PK10两面 队列名
    public final static String TOPIC_JSBJPKS_LM = "TOPIC_JSBJPKS_LM";

    @Bean
    public Topic queueJsbjpksLm() {
        return new ActiveMQTopic(TOPIC_JSBJPKS_LM);
    }

    // 极速牛牛 队列名
    public final static String TOPIC_JSNN = "TOPIC_JSNN";

    @Bean
    public Topic queueJsNn() {
        return new ActiveMQTopic(TOPIC_JSNN);
    }

    // 极速北京PK10猜名次猜前几 队列名
    public final static String TOPIC_JSBJPKS_CMC_CQJ = "TOPIC_JSBJPKS_CMC_CQJ";

    @Bean
    public Topic queueJsbjpksCmcCqj() {
        return new ActiveMQTopic(TOPIC_JSBJPKS_CMC_CQJ);
    }

    // 极速北京PK10单式猜前几 队列名
    public final static String TOPIC_JSBJPKS_DS_CQJ = "TOPIC_JSBJPKS_DS_CQJ";

    @Bean
    public Topic queueJsbjpksDsCqj() {
        return new ActiveMQTopic(TOPIC_JSBJPKS_DS_CQJ);
    }

    // 极速北京PK10定位胆 队列名
    public final static String TOPIC_JSBJPKS_DWD = "TOPIC_JSBJPKS_DWD";

    @Bean
    public Topic queueJsbjpksDwd() {
        return new ActiveMQTopic(TOPIC_JSBJPKS_DWD);
    }

    // 极速北京PK10冠亚和 队列名
    public final static String TOPIC_JSBJPKS_GYH = "TOPIC_JSBJPKS_GYH";

    @Bean
    public Topic queueJsbjpksGyh() {
        return new ActiveMQTopic(TOPIC_JSBJPKS_GYH);
    }

    // 极速北京PK10 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_JSBJPKS_UPDATE_DATA = "TOPIC_JSBJPKS_UPDATE_DATA";

    @Bean
    public Topic queueJsbjpksUpdateData() {
        return new ActiveMQTopic(TOPIC_JSBJPKS_UPDATE_DATA);
    }

    // 极速pk10 番摊 队列名
    public final static String TOPIC_JSBJPKS_FT = "TOPIC_JS_FT";

    @Bean
    public Topic queueJsbjpksFt() {
        return new ActiveMQTopic(TOPIC_JSBJPKS_FT);
    }

    // WEB结果推送 队列名
    public final static String TOPIC_WEB_RESULT_PUSH = "TOPIC_WEB_RESULT_PUSH";

    @Bean
    public Topic queueWebResultPush() {
        return new ActiveMQTopic(TOPIC_WEB_RESULT_PUSH);
    }

    // 极速pk10 预期 队列名
    public final static String TOPIC_JSBJPKS_YUQI_DATA = "TOPIC_JSBJPKS_YUQI_DATA";

    @Bean
    public Topic queueJsbjpksYuqiData() {
        return new ActiveMQTopic(TOPIC_JSBJPKS_YUQI_DATA);
    }

    // 番摊极速pk10 预期 队列名
    public final static String TOPIC_FT_JSBJPKS_YUQI_DATA = "TOPIC_FT_JSBJPKS_YUQI_DATA";

    @Bean
    public Topic queueFtJsbjpksYuqiData() {
        return new ActiveMQTopic(TOPIC_FT_JSBJPKS_YUQI_DATA);
    }


    //##############################################################################################
    //###                                   澳门六合彩开采结算                                   ###
    // 澳门六合彩 队列名（app调用）
    public final static String TOPIC_AM_LHC = "TOPIC_SS_LHC";

    @Bean
    public Topic topicAmLhc() {
        return new ActiveMQTopic(TOPIC_AM_LHC);
    }

    // 澳门六合彩(特码,正特,六肖,正码1-6) 队列名
    public final static String TOPIC_AMLHC_TM_ZT_LX = "TOPIC_AMLHC_TM_ZT_LX";

    @Bean
    public Topic queueAmlhcTmZtLx() {
        return new ActiveMQTopic(TOPIC_AMLHC_TM_ZT_LX);
    }

    // 澳门六合彩(正码,半波,尾数) 队列名
    public final static String TOPIC_AMLHC_ZM_BB_WS = "QUEUE_AMLHC_ZM_BB_WS";

    @Bean
    public Topic queueAmlhcZmBbWs() {
        return new ActiveMQTopic(TOPIC_AMLHC_ZM_BB_WS);
    }

    // 澳门六合彩(连码,连肖,连尾) 队列名
    public final static String TOPIC_AMLHC_LM_LX_LW = "TOPIC_AMLHC_LM_LX_LW";

    @Bean
    public Topic queueAmlhcLmLxLw() {
        return new ActiveMQTopic(TOPIC_AMLHC_LM_LX_LW);
    }

    // 澳门六合彩(不中,1-6龙虎,五行) 队列名
    public final static String TOPIC_AMLHC_BZ_LH_WX = "TOPIC_AMLHC_BZ_LH_WX";

    @Bean
    public Topic queueAmlhcBzLhWx() {
        return new ActiveMQTopic(TOPIC_AMLHC_BZ_LH_WX);
    }

    // 澳门六合彩(平特,特肖) 队列名
    public final static String TOPIC_AMLHC_PT_TX = "TOPIC_SSLHC_PT_TX";

    @Bean
    public Topic queueAmlhcPtTx() {
        return new ActiveMQTopic(TOPIC_AMLHC_PT_TX);
    }

    // 澳门六合彩 预期 队列名
    public final static String TOPIC_AMLHC_YUQI_DATA = "TOPIC_AMLHC_YUQI_DATA";

    @Bean
    public Topic queueSslhcYuqiData() {
        return new ActiveMQTopic(TOPIC_AMLHC_YUQI_DATA);
    }

    // 澳门六合彩 - 公式杀号 队列名
    public final static String TOPIC_AMLHC_KILL_DATA = "TOPIC_AMLHC_KILL_DATA";

    @Bean
    public Topic queueAmlhcKillData() {
        return new ActiveMQTopic(TOPIC_AMLHC_KILL_DATA);
    }

    //##############################################################################################
    //###                                   香港六合彩预期数据                                   ###
    // 香港六合彩 预期 队列名
    public final static String TOPIC_XGLHC_YUQI_DATA = "TOPIC_XGLHC_YUQI_DATA";

    @Bean
    public Topic queueXglhcYuqiData() {
        return new ActiveMQTopic(TOPIC_XGLHC_YUQI_DATA);
    }


    //##############################################################################################
    //###                                   /澳洲ACT                                          ###
    // 澳洲ACT 队列名（app调用）
    public final static String TOPIC_AUS_ACT = "TOPIC_AUS_ACT";

    @Bean
    public Topic topicAusActApp() {
        return new ActiveMQTopic(TOPIC_AUS_ACT);
    }

    // 澳洲F1 队列名（app调用）
    public final static String TOPIC_AUS_F1 = "TOPIC_AUS_F1";

    @Bean
    public Topic topicAusF1App() {
        return new ActiveMQTopic(TOPIC_AUS_F1);
    }

    // 澳洲牛牛 队列名（app调用）
    public final static String TOPIC_AUS_NN = "TOPIC_AUS_NN";

    @Bean
    public Topic topicAusNn() {
        return new ActiveMQTopic(TOPIC_AUS_NN);
    }

    // 澳洲时时彩 队列名（app调用）
    public final static String TOPIC_AUS_SSC = "TOPIC_AUS_SSC";

    @Bean
    public Topic topicAusSSCApp() {
        return new ActiveMQTopic(TOPIC_AUS_SSC);
    }


    // 澳洲ACT 队列名
    public final static String TOPIC_AUS_ACT_NAME = "TOPIC_AUS_ACT_NAME";

    @Bean
    public Topic queueAusAct() {
        return new ActiveMQTopic(TOPIC_AUS_ACT_NAME);
    }

    // 澳洲F1 队列名
    public final static String TOPIC_AUS_F1_NAME = "TOPIC_AUS_F1_NAME";

    @Bean
    public Topic queueAusF1() {
        return new ActiveMQTopic(TOPIC_AUS_F1_NAME);
    }

    // 澳洲时时彩 队列名
    public final static String TOPIC_AUS_SSC_NAME = "TOPIC_AUS_SSC_NAME";

    @Bean
    public Topic queueAusSsc() {
        return new ActiveMQTopic(TOPIC_AUS_SSC_NAME);
    }

    // 澳洲F1 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_AUS_F1_UPDATE_DATA = "TOPIC_AUS_F1_UPDATE_DATA";

    @Bean
    public Topic topicAusF1UpdateData() {
        return new ActiveMQTopic(TOPIC_AUS_F1_UPDATE_DATA);
    }


    // 澳洲ACT 预期 队列名
    public final static String TOPIC_AUS_ACT_YUQI_DATA = "TOPIC_AUS_ACT_YUQI_DATA";

    @Bean
    public Topic queueAusActYuqiData() {
        return new ActiveMQTopic(TOPIC_AUS_ACT_YUQI_DATA);
    }

    // 澳洲时时彩 预期 队列名
    public final static String TOPIC_AUS_SSC_YUQI_DATA = "TOPIC_AUS_SSC_YUQI_DATA";

    @Bean
    public Topic queueAusSscYuqiData() {
        return new ActiveMQTopic(TOPIC_AUS_SSC_YUQI_DATA);
    }

    // 澳洲PKS 预期 队列名
    public final static String TOPIC_AUS_PKS_YUQI_DATA = "TOPIC_AUS_PKS_YUQI_DATA";

    @Bean
    public Topic queueAusPksYuqiData() {
        return new ActiveMQTopic(TOPIC_AUS_PKS_YUQI_DATA);
    }

    //##############################################################################################
    //###                                   五分六合彩开采结算                                         ###
    //##############################################################################################
    // 五分六合彩 队列名（app调用）
    public final static String TOPIC_LHC_FIVE = "TOPIC_LHC_FIVE";

    @Bean
    public Topic topicLhcFive() {
        return new ActiveMQTopic(TOPIC_LHC_FIVE);
    }

    // 六合彩(特码,正特,六肖,正码1-6) 队列名
    public final static String TOPIC_FIVELHC_TM_ZT_LX = "TOPIC_FIVELHC_TM_ZT_LX";

    @Bean
    public Topic queueFivelhcTmZtLx() {
        return new ActiveMQTopic(TOPIC_FIVELHC_TM_ZT_LX);
    }

    // 六合彩(正码,半波,尾数) 队列名
    public final static String TOPIC_FIVELHC_ZM_BB_WS = "TOPIC_FIVELHC_ZM_BB_WS";

    @Bean
    public Topic queueFivelhcZmBbWs() {
        return new ActiveMQTopic(TOPIC_FIVELHC_ZM_BB_WS);
    }

    // 六合彩(连码,连肖,连尾) 队列名
    public final static String TOPIC_FIVELHC_LM_LX_LW = "TOPIC_FIVELHC_LM_LX_LW";

    @Bean
    public Topic queueFivelhcLmLxLw() {
        return new ActiveMQTopic(TOPIC_FIVELHC_LM_LX_LW);
    }

    // 六合彩(不中,1-6龙虎,五行) 队列名
    public final static String TOPIC_FIVELHC_BZ_LH_WX = "TOPIC_FIVELHC_BZ_LH_WX";

    @Bean
    public Topic queueFivelhcBzLhWx() {
        return new ActiveMQTopic(TOPIC_FIVELHC_BZ_LH_WX);
    }

    // 六合彩(平特,特肖) 队列名
    public final static String TOPIC_FIVELHC_PT_TX = "TOPIC_FIVELHC_PT_TX";

    @Bean
    public Topic queueFivelhcPtTx() {
        return new ActiveMQTopic(TOPIC_FIVELHC_PT_TX);
    }

    //五分六合彩  预期 队列名
    public final static String TOPIC_FIVELHC_YUQI_DATA = "TOPIC_FIVELHC_YUQI_DATA";

    @Bean
    public Topic queueFivelhcYuqiData() {
        return new ActiveMQTopic(TOPIC_FIVELHC_YUQI_DATA);
    }

    // 五分六合彩 - 公式杀号 队列名
    public final static String TOPIC_FIVELHC_KILL_DATA = "TOPIC_FIVELHC_KILL_DATA";

    @Bean
    public Topic queueFivelhcKillData() {
        return new ActiveMQTopic(TOPIC_FIVELHC_KILL_DATA);
    }


    //##############################################################################################
    //###                                   一分六合彩开采结算                                         ###
    //##############################################################################################
    // 一分六合彩 队列名（app调用）
    public final static String TOPIC_LHC_ONE = "TOPIC_LHC_ONE";

    @Bean
    public Topic topicLhcOne() {
        return new ActiveMQTopic(TOPIC_LHC_ONE);
    }

    // 六合彩(特码,正特,六肖,正码1-6) 队列名
    public final static String TOPIC_ONELHC_TM_ZT_LX = "TOPIC_ONELHC_TM_ZT_LX";

    @Bean
    public Topic queueOnelhcTmZtLx() {
        return new ActiveMQTopic(TOPIC_ONELHC_TM_ZT_LX);
    }

    // 六合彩(正码,半波,尾数) 队列名
    public final static String TOPIC_ONELHC_ZM_BB_WS = "TOPIC_ONELHC_ZM_BB_WS";

    @Bean
    public Topic queueOnelhcZmBbWs() {
        return new ActiveMQTopic(TOPIC_ONELHC_ZM_BB_WS);
    }

    // 六合彩(连码,连肖,连尾) 队列名
    public final static String TOPIC_ONELHC_LM_LX_LW = "TOPIC_ONELHC_LM_LX_LW";

    @Bean
    public Topic queueOnelhcLmLxLw() {
        return new ActiveMQTopic(TOPIC_ONELHC_LM_LX_LW);
    }

    // 六合彩(不中,1-6龙虎,五行) 队列名
    public final static String TOPIC_ONELHC_BZ_LH_WX = "TOPIC_ONELHC_BZ_LH_WX";

    @Bean
    public Topic queueOnelhcBzLhWx() {
        return new ActiveMQTopic(TOPIC_ONELHC_BZ_LH_WX);
    }

    // 六合彩(平特,特肖) 队列名
    public final static String TOPIC_ONELHC_PT_TX = "TOPIC_ONELHC_PT_TX";

    @Bean
    public Topic queueOnelhcPtTx() {
        return new ActiveMQTopic(TOPIC_ONELHC_PT_TX);
    }

    // 一分六合彩 预期 队列名
    public final static String TOPIC_ONELHC_YUQI_DATA = "TOPIC_ONELHC_YUQI_DATA";

    @Bean
    public Topic queueOnelhcYuqiData() {
        return new ActiveMQTopic(TOPIC_ONELHC_YUQI_DATA);
    }

    // 一分六合彩 - 公式杀号 队列名
    public final static String TOPIC_ONELHC_KILL_DATA = "TOPIC_ONELHC_KILL_DATA";

    @Bean
    public Topic queueOnelhcKillData() {
        return new ActiveMQTopic(TOPIC_ONELHC_KILL_DATA);
    }


    //##############################################################################################
    //###                                   十分PK10开采结算                                       ###
    //##############################################################################################
    //  十分PK10 队列名（app调用）
    public final static String TOPIC_TENBJPKS = "TOPIC_TENBJPKS";

    @Bean
    public Topic topicTenbjpks() {
        return new ActiveMQTopic(TOPIC_TENBJPKS);
    }

    // 十分北京PK10两面 队列名
    public final static String TOPIC_TENBJPKS_LM = "TOPIC_TENBJPKS_LM";

    @Bean
    public Topic queueTenbjpksLm() {
        return new ActiveMQTopic(TOPIC_TENBJPKS_LM);
    }

    // 十分北京PK10猜名次猜前几 队列名
    public final static String TOPIC_TENBJPKS_CMC_CQJ = "TOPIC_TENBJPKS_CMC_CQJ";

    @Bean
    public Topic queueTenbjpksCmcCqj() {
        return new ActiveMQTopic(TOPIC_TENBJPKS_CMC_CQJ);
    }

    // 十分北京PK10单式猜前几 队列名
    public final static String TOPIC_TENBJPKS_DS_CQJ = "TOPIC_TENBJPKS_DS_CQJ";

    @Bean
    public Topic queueTenbjpksDsCqj() {
        return new ActiveMQTopic(TOPIC_TENBJPKS_DS_CQJ);
    }

    // 十分北京PK10定位胆 队列名
    public final static String TOPIC_TENBJPKS_DWD = "TOPIC_TENBJPKS_DWD";

    @Bean
    public Topic queueTenbjpksDwd() {
        return new ActiveMQTopic(TOPIC_TENBJPKS_DWD);
    }

    // 十分北京PK10冠亚和 队列名
    public final static String TOPIC_TENBJPKS_GYH = "TOPIC_TENBJPKS_GYH";

    @Bean
    public Topic queueTenbjpksGyh() {
        return new ActiveMQTopic(TOPIC_TENBJPKS_GYH);
    }

    // 十分北京PK10 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_TENBJPKS_UPDATE_DATA = "TOPIC_TENBJPKS_UPDATE_DATA";

    @Bean
    public Topic queueTenbjpksUpdateData() {
        return new ActiveMQTopic(TOPIC_TENBJPKS_UPDATE_DATA);
    }

    // 十分北京PK10 - 预期 队列名
    public final static String TOPIC_TENBJPKS_YUQI_DATA = "TOPIC_TENBJPKS_YUQI_DATA";

    @Bean
    public Topic queueTenbjpksYuqiData() {
        return new ActiveMQTopic(TOPIC_TENBJPKS_YUQI_DATA);
    }

    //##############################################################################################
    //###                                   五分PK10开采结算                                       ###
    //##############################################################################################
//  五分PK10 队列名（app调用）
    public final static String TOPIC_FIVEBJPKS = "TOPIC_FIVEBJPKS";

    @Bean
    public Topic topicFivebjpks() {
        return new ActiveMQTopic(TOPIC_FIVEBJPKS);
    }

    // 五分PK10两面 队列名
    public final static String TOPIC_FIVEBJPKS_LM = "TOPIC_FIVEBJPKS_LM";

    @Bean
    public Topic queueFivebjpksLm() {
        return new ActiveMQTopic(TOPIC_FIVEBJPKS_LM);
    }

    // 五分PK10猜名次猜前几 队列名
    public final static String TOPIC_FIVEBJPKS_CMC_CQJ = "TOPIC_FIVEBJPKS_CMC_CQJ";

    @Bean
    public Topic queueFivebjpksCmcCqj() {
        return new ActiveMQTopic(TOPIC_FIVEBJPKS_CMC_CQJ);
    }

    // 五分PK10单式猜前几 队列名
    public final static String TOPIC_FIVEBJPKS_DS_CQJ = "TOPIC_FIVEBJPKS_DS_CQJ";

    @Bean
    public Topic queueFivebjpksDsCqj() {
        return new ActiveMQTopic(TOPIC_FIVEBJPKS_DS_CQJ);
    }

    // 五分PK10定位胆 队列名
    public final static String TOPIC_FIVEBJPKS_DWD = "TOPIC_FIVEBJPKS_DWD";

    @Bean
    public Topic queueFivebjpksDwd() {
        return new ActiveMQTopic(TOPIC_FIVEBJPKS_DWD);
    }

    // 五分PK10冠亚和 队列名
    public final static String TOPIC_FIVEBJPKS_GYH = "TOPIC_FIVEBJPKS_GYH";

    @Bean
    public Topic queueFivebjpksGyh() {
        return new ActiveMQTopic(TOPIC_FIVEBJPKS_GYH);
    }

    // 五分PK10 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_FIVEBJPKS_UPDATE_DATA = "TOPIC_FIVEBJPKS_UPDATE_DATA";

    @Bean
    public Topic queueFivebjpksUpdateData() {
        return new ActiveMQTopic(TOPIC_FIVEBJPKS_UPDATE_DATA);
    }

    // 五分PK10 预期 队列名
    public final static String TOPIC_FIVEBJPKS_YUQI_DATA = "TOPIC_FIVEBJPKS_YUQI_DATA";

    @Bean
    public Topic queueFivebjpksYuqiData() {
        return new ActiveMQTopic(TOPIC_FIVEBJPKS_YUQI_DATA);
    }

    //##############################################################################################
    //###                                   北京PK10开采结算                                       ###
    //##############################################################################################
//  北京PK10 队列名（app调用）
    public final static String TOPIC_BJPKS = "TOPIC_BJPKS";

    @Bean
    public Topic topicBjpks() {
        return new ActiveMQTopic(TOPIC_BJPKS);
    }

    // 北京PK10队列名（app调用切换期号）
    public final static String TOPIC_APP_CHANGE_ISSUE_PK10_BJ = "TOPIC_APP_CHANGE_ISSUE_PK10_BJ";

    @Bean
    public Topic topicAppChangeIssuePK10Bj() {
        return new ActiveMQTopic(TOPIC_APP_CHANGE_ISSUE_PK10_BJ);
    }

    // 北京PK10两面 队列名
    public final static String TOPIC_BJPKS_LM = "TOPIC_BJPKS_LM";

    @Bean
    public Topic queueBjpksLm() {
        return new ActiveMQTopic(TOPIC_BJPKS_LM);
    }

    // 北京PK10猜名次猜前几 队列名
    public final static String TOPIC_BJPKS_CMC_CQJ = "TOPIC_BJPKS_CMC_CQJ";

    @Bean
    public Topic queueBjpksCmcCqj() {
        return new ActiveMQTopic(TOPIC_BJPKS_CMC_CQJ);
    }

    // 北京PK10单式猜前几 队列名
    public final static String TOPIC_BJPKS_DS_CQJ = "TOPIC_BJPKS_DS_CQJ";

    @Bean
    public Topic queueBjpksDsCqj() {
        return new ActiveMQTopic(TOPIC_BJPKS_DS_CQJ);
    }

    // 北京PK10定位胆 队列名
    public final static String TOPIC_BJPKS_DWD = "TOPIC_BJPKS_DWD";

    @Bean
    public Topic queueBjpksDwd() {
        return new ActiveMQTopic(TOPIC_BJPKS_DWD);
    }

    /// 北京PK10冠亚和 队列名
    public final static String TOPIC_BJPKS_GYH = "TOPIC_BJPKS_GYH";

    @Bean
    public Topic queueBjpksGyh() {
        return new ActiveMQTopic(TOPIC_BJPKS_GYH);
    }

    // 北京PK10 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_BJPKS_UPDATE_DATA = "TOPIC_BJPKS_UPDATE_DATA";

    @Bean
    public Topic queueBjpksUpdateData() {
        return new ActiveMQTopic(TOPIC_BJPKS_UPDATE_DATA);
    }

    // 北京PK10 - 推荐号 队列名
    public final static String TOPIC_BJPKS_RECOMMEND_DATA = "TOPIC_BJPKS_RECOMMEND_DATA";

    @Bean
    public Topic queueBjpksRecommendData() {
        return new ActiveMQTopic(TOPIC_BJPKS_RECOMMEND_DATA);
    }

    // 北京PK10 - 杀号 队列名
    public final static String TOPIC_BJPKS_KILL_DATA = "TOPIC_BJPKS_KILL_DATA";

    @Bean
    public Topic queueBjpksKillData() {
        return new ActiveMQTopic(TOPIC_BJPKS_KILL_DATA);
    }

    // 北京PK10 - 预期 队列名
    public final static String TOPIC_BJPKS_YUQI_DATA = "TOPIC_BJPKS_YUQI_DATA";

    @Bean
    public Topic queueBjpksYuqiData() {
        return new ActiveMQTopic(TOPIC_BJPKS_YUQI_DATA);
    }

    // 北京PK10 大小统计 队列名
    public final static String TOPIC_BJPKS_DX_TJ_DATA = "TOPIC_BJPKS_DX_TJ_DATA";

    @Bean
    public Topic queueBjpksDxTjData() {
        return new ActiveMQTopic(TOPIC_BJPKS_DX_TJ_DATA);
    }

    // 北京PK10 单双统计 队列名
    public final static String TOPIC_BJPKS_DS_TJ_DATA = "TOPIC_BJPKS_DS_TJ_DATA";

    @Bean
    public Topic queueBjpksDsTjData() {
        return new ActiveMQTopic(TOPIC_BJPKS_DS_TJ_DATA);
    }

    // 北京PK10 龙虎统计 队列名
    public final static String TOPIC_BJPKS_LH_TJ_DATA = "TOPIC_BJPKS_LH_TJ_DATA";

    @Bean
    public Topic queueBjpksLhTjData() {
        return new ActiveMQTopic(TOPIC_BJPKS_LH_TJ_DATA);
    }

    //##############################################################################################
    //###                                   澳洲F1开采结算                                       ###
    //##############################################################################################
    // 澳洲F1 - 推荐号 队列名
    public final static String TOPIC_AUSPKS_RECOMMEND_DATA = "TOPIC_AUSPKS_RECOMMEND_DATA";

    @Bean
    public Topic queueAuspksRecommendData() {
        return new ActiveMQTopic(TOPIC_AUSPKS_RECOMMEND_DATA);
    }

    // 澳洲F1 - 杀号 队列名
    public final static String TOPIC_AUSPKS_KILL_DATA = "TOPIC_AUSPKS_KILL_DATA";

    @Bean
    public Topic queueAuspksKillData() {
        return new ActiveMQTopic(TOPIC_AUSPKS_KILL_DATA);
    }

    // 澳洲F1 大小统计 队列名
    public final static String TOPIC_AUSPKS_DX_TJ_DATA = "TOPIC_AUSPKS_DX_TJ_DATA";

    @Bean
    public Topic queueAuspksDxTjData() {
        return new ActiveMQTopic(TOPIC_AUSPKS_DX_TJ_DATA);
    }

    // 澳洲F1 单双统计 队列名
    public final static String TOPIC_AUSPKS_DS_TJ_DATA = "TOPIC_AUSPKS_DS_TJ_DATA";

    @Bean
    public Topic queueAuspksDsTjData() {
        return new ActiveMQTopic(TOPIC_AUSPKS_DS_TJ_DATA);
    }

    // 澳洲F1 龙虎统计 队列名
    public final static String TOPIC_AUSPKS_LH_TJ_DATA = "TOPIC_AUSPKS_LH_TJ_DATA";

    @Bean
    public Topic queueAuspksLhTjData() {
        return new ActiveMQTopic(TOPIC_AUSPKS_LH_TJ_DATA);
    }

    //##############################################################################################
    //###                                   长龙推送消息                                       ###
    //##############################################################################################
    // 澳洲F1 - 长龙推送 队列名
    public final static String TOPIC_AUSPKS_DRAGONLONG_DATA = "TOPIC_AUSPKS_DRAGONLONG_DATA";

    @Bean
    public Topic queueAuspksDragonData() {
        return new ActiveMQTopic(TOPIC_AUSPKS_DRAGONLONG_DATA);
    }

    // PC蛋蛋 - 长龙推送 队列名
    public final static String TOPIC_PCEGG_DRAGONLONG_DATA = "TOPIC_PCEGG_DRAGONLONG_DATA";

    @Bean
    public Topic queuePceggDragonData() {
        return new ActiveMQTopic(TOPIC_PCEGG_DRAGONLONG_DATA);
    }

    // 时时彩 - 长龙推送 队列名
    public final static String TOPIC_SSC_DRAGONLONG_DATA = "TOPIC_SSC_DRAGONLONG_DATA";

    @Bean
    public Topic queueSscDragonData() {
        return new ActiveMQTopic(TOPIC_SSC_DRAGONLONG_DATA);
    }

    // 六合彩 - 长龙推送 队列名
    public final static String TOPIC_LHC_DRAGONLONG_DATA = "TOPIC_LHC_DRAGONLONG_DATA";

    @Bean
    public Topic queueLhcDragonData() {
        return new ActiveMQTopic(TOPIC_LHC_DRAGONLONG_DATA);
    }

    // PK10 - 长龙推送 队列名
    public final static String TOPIC_PKTEN_DRAGONLONG_DATA = "TOPIC_PKTEN_DRAGONLONG_DATA";

    @Bean
    public Topic queuePktenDragonData() {
        return new ActiveMQTopic(TOPIC_PKTEN_DRAGONLONG_DATA);
    }

    //##############################################################################################
    //###                                   福彩双色球开采结算                                       ###
    //##############################################################################################
    //福彩双色球队列名（app调用）
    public final static String TOPIC_APP_FC_SSQ = "TOPIC_APP_FC_SSQ";

    @Bean
    public Topic topicAppFcSsq() {
        return new ActiveMQTopic(TOPIC_APP_FC_SSQ);
    }

    // 福彩双色球 队列名
    public final static String TOPIC_FC_SSQ = "TOPIC_FC_SSQ";

    @Bean
    public Topic queueFcSsq() {
        return new ActiveMQTopic(TOPIC_FC_SSQ);
    }

    // 福彩双色球 预期 队列名
    public final static String TOPIC_FC_SSQ_YUQI_DATA = "TOPIC_FC_SSQ_YUQI_DATA";

    @Bean
    public Topic queueFcSsqYuqiData() {
        return new ActiveMQTopic(TOPIC_FC_SSQ_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  福彩七乐彩开采结算                                       ###
    //##############################################################################################
    //福彩七乐彩队列名（app调用）
    public final static String TOPIC_APP_FC_7LC = "TOPIC_APP_FC_7LC";

    @Bean
    public Topic topicAppFc7lc() {
        return new ActiveMQTopic(TOPIC_APP_FC_7LC);
    }

    // 福彩七乐彩 队列名
    public final static String TOPIC_FC_7LC = "TOPIC_FC_7LC";

    @Bean
    public Topic queueFc7lc() {
        return new ActiveMQTopic(TOPIC_FC_7LC);
    }

    // 福彩七乐彩 预期 队列名
    public final static String TOPIC_FC_7LC_YUQI_DATA = "TOPIC_FC_7LC_YUQI_DATA";

    @Bean
    public Topic queueFc7lcYuqiData() {
        return new ActiveMQTopic(TOPIC_FC_7LC_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  福彩3D开采结算                                       ###
    //##############################################################################################
    //福彩3D队列名（app调用）
    public final static String TOPIC_APP_FC_3D = "TOPIC_APP_FC_3D";

    @Bean
    public Topic topicAppFc3d() {
        return new ActiveMQTopic(TOPIC_APP_FC_3D);
    }

    // 福彩3D 队列名
    public final static String TOPIC_FC_3D = "TOPIC_FC_3D";

    @Bean
    public Topic queueFc3d() {
        return new ActiveMQTopic(TOPIC_FC_3D);
    }

    //福彩3D 预期 队列
    public final static String TOPIC_FC_3D_YUQI_DATA = "TOPIC_FC_3D_YUQI_DATA";

    @Bean
    public Topic queueAppFc3dYuqiData() {
        return new ActiveMQTopic(TOPIC_FC_3D_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  体彩七星彩开采结算                                       ###
    //##############################################################################################
    //体彩七星彩队列名（app调用）
    public final static String TOPIC_APP_TC_7XC = "TOPIC_APP_TC_7XC";

    @Bean
    public Topic topicAppTc7xc() {
        return new ActiveMQTopic(TOPIC_APP_TC_7XC);
    }

    // 体彩七星彩 队列名  --海南7星彩
    public final static String TOPIC_HN_7XC = "TOPIC_HN_7XC";

    @Bean
    public Topic queueHn7xc() {
        return new ActiveMQTopic(TOPIC_HN_7XC);
    }

    // 体彩七星彩 预期 队列名  --海南7星彩
    public final static String TOPIC_HN_7XC_YUQI_DATA = "TOPIC_HN_7XC_YUQI_DATA";

    @Bean
    public Topic queueHn7xcYuqiData() {
        return new ActiveMQTopic(TOPIC_HN_7XC_YUQI_DATA);
    }

    //##############################################################################################
    //###                                  体彩大乐透开采结算                                       ###
    //##############################################################################################
    //体彩大乐透队列名（app调用）
    public final static String TOPIC_APP_TC_DLT = "TOPIC_APP_TC_DLT";

    @Bean
    public Topic topicAppTcDlt() {
        return new ActiveMQTopic(TOPIC_APP_TC_DLT);
    }

    // 体彩大乐透【直选】 队列名
    public final static String TOPIC_TC_DLT_LM = "TOPIC_TC_DLT_LM";

    @Bean
    public Topic queueTcDltLm() {
        return new ActiveMQTopic(TOPIC_TC_DLT_LM);
    }

    // 体彩大乐透【组选】 队列名
    public final static String TOPIC_TC_DLT_DN = "TOPIC_TC_DLT_DN";

    @Bean
    public Topic queueTcDltDm() {
        return new ActiveMQTopic(TOPIC_TC_DLT_DN);
    }

    // 体彩大乐透【定位胆】 队列名
    public final static String TOPIC_TC_DLT_15 = "TOPIC_TC_DLT_15";

    @Bean
    public Topic queueTcDltL5() {
        return new ActiveMQTopic(TOPIC_TC_DLT_15);
    }

    // 体彩大乐透【大小单双】 队列名
    public final static String TOPIC_TC_DLT_QZH = "TOPIC_TC_DLT_QZH";

    @Bean
    public Topic queueTcDltQzh() {
        return new ActiveMQTopic(TOPIC_TC_DLT_QZH);
    }

    //  体彩大乐透
    public final static String TOPIC_TC_DLT = "TOPIC_TC_DLT";

    @Bean
    public Topic queueTcDlt() {
        return new ActiveMQTopic(TOPIC_TC_DLT);
    }

    // 体彩大乐透 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_TC_DLT_UPDATE_DATA = "TOPIC_TC_DLT_UPDATE_DATA";

    @Bean
    public Topic queueTcDltUpdateData() {
        return new ActiveMQTopic(TOPIC_TC_DLT_UPDATE_DATA);
    }

    // 体彩大乐透 预期 队列名
    public final static String TOPIC_TC_DLT_YUQI_DATA = "TOPIC_TC_DLT_YUQI_DATA";

    @Bean
    public Topic queueTcDltYuqiData() {
        return new ActiveMQTopic(TOPIC_TC_DLT_YUQI_DATA);
    }

    //##############################################################################################
    //###                                  体彩排列五开采结算                                       ###
    //##############################################################################################
    //体彩大乐透队列名（app调用）
    public final static String TOPIC_APP_TC_PLW = "TOPIC_APP_TC_PLW";

    @Bean
    public Topic topicAppTcPlw() {
        return new ActiveMQTopic(TOPIC_APP_TC_PLW);
    }

    //  体彩排列五直选组选 队列名
    public final static String TOPIC_TCPLW_ZX = "TOPIC_TCPLW_ZX";

    @Bean
    public Topic queueTcplwZx() {
        return new ActiveMQTopic(TOPIC_TCPLW_ZX);
    }

    //  体彩排列五定位胆 队列名
    public final static String TOPIC_TCPLW_DWD = "TOPIC_TCPLW_DWD";

    @Bean
    public Topic queueTcplwDwd() {
        return new ActiveMQTopic(TOPIC_TCPLW_DWD);
    }

    //  体彩排列五定位胆 队列名
    public final static String TOPIC_TCPLW_LM = "TOPIC_TCPLW_LM";

    @Bean
    public Topic queueTcplwLm() {
        return new ActiveMQTopic(TOPIC_TCPLW_LM);
    }

    //  体彩排列五 预期 队列名
    public final static String TOPIC_TCPLW_YUQI_DATA = "TOPIC_TCPLW_YUQI_DATA";

    @Bean
    public Topic queueTcplwYuqiData() {
        return new ActiveMQTopic(TOPIC_TCPLW_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  体彩排列三开采结算                                       ###
    //##############################################################################################
    // 体彩排列三队列名（app调用）
    public final static String TOPIC_APP_TC_PLS = "TOPIC_APP_TC_PLS";

    @Bean
    public Topic topicAppTcPlS() {
        return new ActiveMQTopic(TOPIC_APP_TC_PLS);
    }

    //  体彩排列三直选组选 队列名
    public final static String TOPIC_TCPLS_ZX = "TOPIC_TCPLS_ZX";

    @Bean
    public Topic queueTcplsZx() {
        return new ActiveMQTopic(TOPIC_TCPLS_ZX);
    }

    //  体彩排列三 预期 队列名
    public final static String TOPIC_TCPLS_YUQI_DATA = "TOPIC_TCPLS_YUQI_DATA";

    @Bean
    public Topic queueTcplsYuqiData() {
        return new ActiveMQTopic(TOPIC_TCPLS_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  五分时时彩开采结算                                       ###
    //##############################################################################################
    // 五分时时彩 队列名（app调用）
    public final static String TOPIC_APP_SSC_FIVE = "TOPIC_APP_SSC_FIVE";

    @Bean
    public Topic topicAppSscFive() {
        return new ActiveMQTopic(TOPIC_APP_SSC_FIVE);
    }

    // 快乐牛牛 队列名（app调用）
    public final static String TOPIC_APP_NN_KL = "TOPIC_APP_NN_KL";

    @Bean
    public Topic topicAppNnKl() {
        return new ActiveMQTopic(TOPIC_APP_NN_KL);
    }

    // 五分时时彩【直选】 队列名
    public final static String TOPIC_SSC_FIVE_LM = "TOPIC_SSC_FIVE_LM";

    @Bean
    public Topic queueSscFiveLm() {
        return new ActiveMQTopic(TOPIC_SSC_FIVE_LM);
    }

    // 快乐牛牛  队列名
    public final static String TOPIC_KLNN_FIVE = "TOPIC_KLNN_FIVE";

    @Bean
    public Topic queueKlnnFive() {
        return new ActiveMQTopic(TOPIC_KLNN_FIVE);
    }

    // 五分时时彩【组选】 队列名
    public final static String TOPIC_SSC_FIVE_DN = "TOPIC_SSC_FIVE_DN";

    @Bean
    public Topic queueSscFiveDn() {
        return new ActiveMQTopic(TOPIC_SSC_FIVE_DN);
    }

    // 五分时时彩【定位胆】 队列名
    public final static String TOPIC_SSC_FIVE_15 = "TOPIC_SSC_FIVE_15";

    @Bean
    public Topic queueSscFiveL5() {
        return new ActiveMQTopic(TOPIC_SSC_FIVE_15);
    }

    // 五分时时彩【大小单双】 队列名
    public final static String TOPIC_SSC_FIVE_QZH = "TOPIC_SSC_FIVE_QZH";

    @Bean
    public Topic queueSscFiveQzh() {
        return new ActiveMQTopic(TOPIC_SSC_FIVE_QZH);
    }

    // 五分时时彩 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_SSC_FIVE_UPDATE_DATA = "TOPIC_SSC_FIVE_UPDATE_DATA";

    @Bean
    public Topic queueSscFiveUpdateData() {
        return new ActiveMQTopic(TOPIC_SSC_FIVE_UPDATE_DATA);
    }

    // 五分时时彩 - 预期 队列名
    public final static String TOPIC_SSC_FIVE_YUQI_DATA = "TOPIC_SSC_FIVE_YUQI_DATA";

    @Bean
    public Topic queueSscFiveYuqiData() {
        return new ActiveMQTopic(TOPIC_SSC_FIVE_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  十分时时彩开采结算                                       ###
    //##############################################################################################
    // 十分时时彩队列名（app调用）
    public final static String TOPIC_APP_SSC_TEN = "TOPIC_APP_SSC_TEN";

    @Bean
    public Topic topicAppSscTen() {
        return new ActiveMQTopic(TOPIC_APP_SSC_TEN);
    }

    // 十分时时彩【直选】 队列名
    public final static String TOPIC_SSC_TEN_LM = "TOPIC_SSC_TEN_LM";

    @Bean
    public Topic queueSscTenLm() {
        return new ActiveMQTopic(TOPIC_SSC_TEN_LM);
    }

    // 十分时时彩【组选】 队列名
    public final static String TOPIC_SSC_TEN_DN = "TOPIC_SSC_TEN_DN";

    @Bean
    public Topic queueSscTenDn() {
        return new ActiveMQTopic(TOPIC_SSC_TEN_DN);
    }

    // 十分时时彩【定位胆】 队列名
    public final static String TOPIC_SSC_TEN_15 = "TOPIC_SSC_TEN_15";

    @Bean
    public Topic queueSscTenL5() {
        return new ActiveMQTopic(TOPIC_SSC_TEN_15);
    }

    // 十分时时彩【大小单双】 队列名
    public final static String TOPIC_SSC_TEN_QZH = "TOPIC_SSC_TEN_QZH";

    @Bean
    public Topic queueSscTenQzh() {
        return new ActiveMQTopic(TOPIC_SSC_TEN_QZH);
    }

    // 十分时时彩 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_SSC_TEN_UPDATE_DATA = "TOPIC_SSC_TEN_UPDATE_DATA";

    @Bean
    public Topic queueSscTenUpdateData() {
        return new ActiveMQTopic(TOPIC_SSC_TEN_UPDATE_DATA);
    }

    // 十分时时彩 - 预期 队列名
    public final static String TOPIC_SSC_TEN_YUQI_DATA = "TOPIC_SSC_TEN_YUQI_DATA";

    @Bean
    public Topic queueSscTenYuqiData() {
        return new ActiveMQTopic(TOPIC_SSC_TEN_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  天津时时彩开采结算                                       ###
    //##############################################################################################
    // 天津时时彩队列名（app调用）
    public final static String TOPIC_APP_SSC_TJ = "TOPIC_APP_SSC_TJ";

    @Bean
    public Topic topicAppSscTj() {
        return new ActiveMQTopic(TOPIC_APP_SSC_TJ);
    }

    // 天津时时彩队列名（app调用切换期号）
    public final static String TOPIC_APP_CHANGE_ISSUE_SSC_TJ = "TOPIC_APP_CHANGE_ISSUE_SSC_TJ";

    @Bean
    public Topic topicAppChangeIssueSscTj() {
        return new ActiveMQTopic(TOPIC_APP_CHANGE_ISSUE_SSC_TJ);
    }

    // 天津时时彩【直选】 队列名
    public final static String TOPIC_SSC_TJ_LM = "TOPIC_SSC_TJ_LM";

    @Bean
    public Topic queueSscTjLm() {
        return new ActiveMQTopic(TOPIC_SSC_TJ_LM);
    }

    // 天津时时彩【组选】 队列名
    public final static String TOPIC_SSC_TJ_DN = "TOPIC_SSC_TJ_DN";

    @Bean
    public Topic queueSscTjDn() {
        return new ActiveMQTopic(TOPIC_SSC_TJ_DN);
    }

    // 天津时时彩【定位胆】 队列名
    public final static String TOPIC_SSC_TJ_15 = "TOPIC_SSC_TJ_15";

    @Bean
    public Topic queueSscTjL5() {
        return new ActiveMQTopic(TOPIC_SSC_TJ_15);
    }

    // 天津时时彩【大小单双】 队列名
    public final static String TOPIC_SSC_TJ_QZH = "TOPIC_SSC_TJ_QZH";

    @Bean
    public Topic queueSscTjQzh() {
        return new ActiveMQTopic(TOPIC_SSC_TJ_QZH);
    }

    // 天津时时彩 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_SSC_TJ_UPDATE_DATA = "TOPIC_SSC_TJ_UPDATE_DATA";

    @Bean
    public Topic queueSscTjUpdateData() {
        return new ActiveMQTopic(TOPIC_SSC_TJ_UPDATE_DATA);
    }

    // 天津时时彩 - 推荐号 队列名
    public final static String TOPIC_SSC_TJ_RECOMMEND_DATA = "TOPIC_SSC_TJ_RECOMMEND_DATA";

    @Bean
    public Topic queueSscTjRecommendData() {
        return new ActiveMQTopic(TOPIC_SSC_TJ_RECOMMEND_DATA);
    }

    // 天津时时彩 - 杀号 队列名
    public final static String TOPIC_SSC_TJ_KILL_DATA = "TOPIC_SSC_TJ_KILL_DATA";

    @Bean
    public Topic queueSscTjKillData() {
        return new ActiveMQTopic(TOPIC_SSC_TJ_KILL_DATA);
    }

    // 天津时时彩 - 预期 队列名
    public final static String TOPIC_SSC_TJ_YUQI_DATA = "TOPIC_SSC_TJ_YUQI_DATA";

    @Bean
    public Topic queueSscTjYuqiData() {
        return new ActiveMQTopic(TOPIC_SSC_TJ_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  极速时时彩开采结算                                       ###
    //##############################################################################################
    // 极速时时彩队列名（app调用）
    public final static String TOPIC_APP_SSC_JS = "TOPIC_APP_SSC_JS";

    @Bean
    public Topic topicAppSscJs() {
        return new ActiveMQTopic(TOPIC_APP_SSC_JS);
    }

    // 番摊极速时时彩队列名（app调用）
    public final static String TOPIC_APP_SSC_JS_FT = "TOPIC_APP_SSC_JS_FT";

    @Bean
    public Topic topicAppSscJsFt() {
        return new ActiveMQTopic(TOPIC_APP_SSC_JS_FT);
    }

    // 极速时时彩【直选】 队列名
    public final static String TOPIC_SSC_JS_LM = "TOPIC_SSC_JS_LM";

    @Bean
    public Topic queueSscJsLm() {
        return new ActiveMQTopic(TOPIC_SSC_JS_LM);
    }

    // 极速时时彩【组选】 队列名
    public final static String TOPIC_SSC_JS_DN = "TOPIC_SSC_JS_DN";

    @Bean
    public Topic queueSscJsDm() {
        return new ActiveMQTopic(TOPIC_SSC_JS_DN);
    }

    // 极速时时彩【定位胆】 队列名
    public final static String TOPIC_SSC_JS_15 = "TOPIC_SSC_JS_15";

    @Bean
    public Topic queueSscJsL5() {
        return new ActiveMQTopic(TOPIC_SSC_JS_15);
    }

    // 极速时时彩【大小单双】 队列名
    public final static String TOPIC_SSC_JS_QZH = "TOPIC_SSC_JS_QZH";

    @Bean
    public Topic queueSscJsQzh() {
        return new ActiveMQTopic(TOPIC_SSC_JS_QZH);
    }

    // 极速时时彩【番摊】 队列名
    public final static String TOPIC_SSC_JS_FT = "TOPIC_SSC_JS_FT";

    @Bean
    public Topic queueSscJsFt() {
        return new ActiveMQTopic(TOPIC_SSC_JS_FT);
    }

    // 极速时时彩 预期 队列名
    public final static String TOPIC_SSC_JS_YUQI_DATA = "TOPIC_SSC_JS_YUQI_DATA";

    @Bean
    public Topic queueSscJsYuqiData() {
        return new ActiveMQTopic(TOPIC_SSC_JS_YUQI_DATA);
    }

    // 极速时时彩【番摊】 预期 队列名
    public final static String TOPIC_SSC_JS_FT_YUQI_DATA = "TOPIC_SSC_JS_FT_YUQI_DATA";

    @Bean
    public Topic queueSscJsFtYuqiData() {
        return new ActiveMQTopic(TOPIC_SSC_JS_FT_YUQI_DATA);
    }


    //##############################################################################################
    //###                                   PC蛋蛋开采结算                                         ###
    //##############################################################################################
    // PC蛋蛋队列名（app调用）
    public final static String TOPIC_APP_PCEGG = "TOPIC_APP_PCEGG";

    @Bean
    public Topic topicAppPcegg() {
        return new ActiveMQTopic(TOPIC_APP_PCEGG);
    }

    // PC蛋蛋队列名（app调用切换期号）
    public final static String TOPIC_APP_CHANGE_ISSUE_PCEGG = "TOPIC_APP_CHANGE_ISSUE_PCEGG";

    @Bean
    public Topic topicAppChangeIssuePcegg() {
        return new ActiveMQTopic(TOPIC_APP_CHANGE_ISSUE_PCEGG);
    }

    // PC蛋蛋特码 队列名
    public final static String TOPIC_PCEGG_TM = "TOPIC_PCEGG_TM";

    @Bean
    public Topic queuePceggTm() {
        return new ActiveMQTopic(TOPIC_PCEGG_TM);
    }

    // PC蛋蛋豹子 队列名
    public final static String TOPIC_PCEGG_BZ = "TOPIC_PCEGG_BZ";

    @Bean
    public Topic queuePceggBz() {
        return new ActiveMQTopic(TOPIC_PCEGG_BZ);
    }

    // PC蛋蛋特码包三 队列名
    public final static String TOPIC_PCEGG_TMBS = "TOPIC_PCEGG_TMBS";

    @Bean
    public Topic queuePceggTmbs() {
        return new ActiveMQTopic(TOPIC_PCEGG_TMBS);
    }

    // PC蛋蛋色波 队列名
    public final static String TOPIC_PCEGG_SB = "TOPIC_PCEGG_SB";

    @Bean
    public Topic queuePceggSb() {
        return new ActiveMQTopic(TOPIC_PCEGG_SB);
    }

    // PC蛋蛋混合 队列名
    public final static String TOPIC_PCEGG_HH = "TOPIC_PCEGG_HH";

    @Bean
    public Topic queuePceggHh() {
        return new ActiveMQTopic(TOPIC_PCEGG_HH);
    }

    // PC蛋蛋混合 推荐号 队列名
    public final static String TOPIC_PCEGG_RECOMMEND_DATA = "TOPIC_PCEGG_RECOMMEND_DATA";

    @Bean
    public Topic queuePceggRecommendData() {
        return new ActiveMQTopic(TOPIC_PCEGG_RECOMMEND_DATA);
    }

    // PC蛋蛋混合 杀号 队列名
    public final static String TOPIC_PCEGG_KILL_DATA = "TOPIC_PCEGG_KILL_DATA";

    @Bean
    public Topic queuePceggKillData() {
        return new ActiveMQTopic(TOPIC_PCEGG_KILL_DATA);
    }

    // PC蛋蛋 预期 队列名
    public final static String TOPIC_PCEGG_YUQI_DATA = "TOPIC_PCEGG_YUQI_DATA";

    @Bean
    public Topic queuePceggYuqiData() {
        return new ActiveMQTopic(TOPIC_PCEGG_YUQI_DATA);
    }

    //##############################################################################################
    //###                                  腾讯分分彩开采结算                                       ###
    //##############################################################################################
    // 腾讯分分彩队列名（app调用）
    public final static String TOPIC_APP_SSC_TX = "TOPIC_APP_SSC_TX";

    @Bean
    public Topic topicAppSscTx() {
        return new ActiveMQTopic(TOPIC_APP_SSC_TX);
    }

    // 腾讯分分彩队列名（app调用切换期号）
    public final static String TOPIC_APP_CHANGE_ISSUE_TX_FFC = "TOPIC_APP_CHANGE_ISSUE_TX_FFC";

    @Bean
    public Topic topicAppChangeIssueTxFfc() {
        return new ActiveMQTopic(TOPIC_APP_CHANGE_ISSUE_TX_FFC);
    }

    // 腾讯分分彩【直选】 队列名
    public final static String TOPIC_SSC_TX_LM = "TOPIC_SSC_TX_LM";

    @Bean
    public Topic queueTxLm() {
        return new ActiveMQTopic(TOPIC_SSC_TX_LM);
    }

    // 腾讯分分彩【组选】 队列名
    public final static String TOPIC_SSC_TX_DN = "TOPIC_SSC_TX_DN";

    @Bean
    public Topic queueTxDm() {
        return new ActiveMQTopic(TOPIC_SSC_TX_DN);
    }

    // 腾讯分分彩【定位胆】 队列名
    public final static String TOPIC_SSC_TX_15 = "TOPIC_SSC_TX_15";

    @Bean
    public Topic queueTxL5() {
        return new ActiveMQTopic(TOPIC_SSC_TX_15);
    }

    // 腾讯分分彩【定位胆】 队列名
    public final static String TOPIC_SSC_TX_QZH = "TOPIC_SSC_TX_QZH";

    @Bean
    public Topic queueTxQzh() {
        return new ActiveMQTopic(TOPIC_SSC_TX_QZH);
    }

    // 腾讯分分彩【定位胆】 队列名
    public final static String TOPIC_SSC_TX_UPDATE_DATA = "TOPIC_SSC_TX_UPDATE_DATA";

    @Bean
    public Topic queueTxUpdateData() {
        return new ActiveMQTopic(TOPIC_SSC_TX_UPDATE_DATA);
    }

    // 腾讯分分彩 推荐号 队列名
    public final static String TOPIC_SSC_TX_RECOMMEND_DATA = "TOPIC_SSC_TX_RECOMMEND_DATA";

    @Bean
    public Topic queueTxRecommendData() {
        return new ActiveMQTopic(TOPIC_SSC_TX_RECOMMEND_DATA);
    }

    // 腾讯分分彩 杀号 队列名
    public final static String TOPIC_SSC_TX_KILL_DATA = "TOPIC_SSC_TX_KILL_DATA";

    @Bean
    public Topic queueTxKillData() {
        return new ActiveMQTopic(TOPIC_SSC_TX_KILL_DATA);
    }

    // 腾讯分分彩 预期 队列名
    public final static String TOPIC_SSC_TX_YUQI_DATA = "TOPIC_SSC_TX_YUQI_DATA";

    @Bean
    public Topic queueSscTxYuqiData() {
        return new ActiveMQTopic(TOPIC_SSC_TX_YUQI_DATA);
    }

    //##############################################################################################
    //###                                   幸运飞艇开采结算                                         ###
    //##############################################################################################
    // 幸运飞艇队列名（app调用）
    public final static String TOPIC_APP_XYFT = "TOPIC_APP_XYFT";

    @Bean
    public Topic topicAppXyft() {
        return new ActiveMQTopic(TOPIC_APP_XYFT);
    }

    // 幸运飞艇番摊（app调用）
    public final static String TOPIC_APP_XYFT_FT = "TOPIC_APP_XYFT_FT";

    @Bean
    public Topic topicAppXyftFt() {
        return new ActiveMQTopic(TOPIC_APP_XYFT_FT);
    }

    // 幸运飞艇队列名（app调用切换期号）
    public final static String TOPIC_APP_CHANGE_ISSUE_XYFT = "TOPIC_APP_CHANGE_ISSUE_XYFT";

    @Bean
    public Topic topicAppChangeIssueXyft() {
        return new ActiveMQTopic(TOPIC_APP_CHANGE_ISSUE_XYFT);
    }

    // 幸运飞艇两面 队列名
    public final static String TOPIC_XYFT_LM = "TOPIC_XYFT_LM";

    @Bean
    public Topic queueXyftLm() {
        return new ActiveMQTopic(TOPIC_XYFT_LM);
    }

    // 幸运飞艇猜名次猜前几 队列名
    public final static String TOPIC_XYFT_CMC_CQJ = "TOPIC_XYFT_CMC_CQJ";

    @Bean
    public Topic queueXyftCmcCqj() {
        return new ActiveMQTopic(TOPIC_XYFT_CMC_CQJ);
    }

    // 幸运飞艇单式猜前几 队列名
    public final static String TOPIC_XYFT_DS_CQJ = "TOPIC_XYFT_DS_CQJ";

    @Bean
    public Topic queueXyftDsCqj() {
        return new ActiveMQTopic(TOPIC_XYFT_DS_CQJ);
    }

    // 幸运飞艇定位胆 队列名
    public final static String TOPIC_XYFT_DWD = "TOPIC_XYFT_DWD";

    @Bean
    public Topic queueXyftDwd() {
        return new ActiveMQTopic(TOPIC_XYFT_DWD);
    }

    // 幸运飞艇冠亚和 队列名
    public final static String TOPIC_XYFT_GYH = "TOPIC_XYFT_GYH";

    @Bean
    public Topic queueXyftGyh() {
        return new ActiveMQTopic(TOPIC_XYFT_GYH);
    }

    // 幸运飞艇 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_XYFT_UPDATE_DATA = "TOPIC_XYFT_UPDATE_DATA";

    @Bean
    public Topic queueXyftUpdateData() {
        return new ActiveMQTopic(TOPIC_XYFT_UPDATE_DATA);
    }

    // 幸运飞艇番摊 队列名
    public final static String TOPIC_XYFT_FT = "TOPIC_XYFT_FT";

    @Bean
    public Topic queueXyftFt() {
        return new ActiveMQTopic(TOPIC_XYFT_FT);
    }

    // 幸运飞艇 推荐号 队列名
    public final static String TOPIC_XYFT_RECOMMEND_DATA = "TOPIC_XYFT_RECOMMEND_DATA";

    @Bean
    public Topic queueXyftRecommendData() {
        return new ActiveMQTopic(TOPIC_XYFT_RECOMMEND_DATA);
    }

    // 幸运飞艇 杀号 队列名
    public final static String TOPIC_XYFT_KILL_DATA = "TOPIC_XYFT_KILL_DATA";

    @Bean
    public Topic queueXyftKillData() {
        return new ActiveMQTopic(TOPIC_XYFT_KILL_DATA);
    }

    // 幸运飞艇 预期 队列名
    public final static String TOPIC_XYFT_YUQI_DATA = "TOPIC_XYFT_YUQI_DATA";

    @Bean
    public Topic queueXyftYuqiData() {
        return new ActiveMQTopic(TOPIC_XYFT_YUQI_DATA);
    }

    // 幸运飞艇番摊 预期 队列名
    public final static String TOPIC_FT_XYFT_YUQI_DATA = "TOPIC__FT_XYFT_YUQI_DATA";

    @Bean
    public Topic queueFtXyftYuqiData() {
        return new ActiveMQTopic(TOPIC_FT_XYFT_YUQI_DATA);
    }

    // 幸运飞艇 大小统计 队列名
    public final static String TOPIC_XYFT_DX_TJ_DATA = "TOPIC_XYFT_DX_TJ_DATA";

    @Bean
    public Topic queueXyftDxTjData() {
        return new ActiveMQTopic(TOPIC_XYFT_DX_TJ_DATA);
    }

    // 幸运飞艇 单双统计 队列名
    public final static String TOPIC_XYFT_DS_TJ_DATA = "TOPIC_XYFT_DS_TJ_DATA";

    @Bean
    public Topic queueXyftDsTjData() {
        return new ActiveMQTopic(TOPIC_XYFT_DS_TJ_DATA);
    }

    // 幸运飞艇 龙虎统计 队列名
    public final static String TOPIC_XYFT_LH_TJ_DATA = "TOPIC_XYFT_LH_TJ_DATA";

    @Bean
    public Topic queueXyftLhTjData() {
        return new ActiveMQTopic(TOPIC_XYFT_LH_TJ_DATA);
    }


    //##############################################################################################
    //###                                   幸运飞艇私彩开采结算                                         ###
    //##############################################################################################
    // 幸运飞艇队列名（app调用）
    public final static String TOPIC_APP_XYFTSC = "TOPIC_APP_XYFTSC";

    @Bean
    public Topic topicAppXyftsc() {
        return new ActiveMQTopic(TOPIC_APP_XYFTSC);
    }

    // 幸运飞艇私彩两面 队列名
    public final static String TOPIC_XYFTSC_LM = "TOPIC_XYFTSC_LM";

    @Bean
    public Topic queueXyftscLm() {
        return new ActiveMQTopic(TOPIC_XYFTSC_LM);
    }

    // 幸运飞艇猜名次猜前几 队列名
    public final static String TOPIC_XYFTSC_CMC_CQJ = "TOPIC_XYFTSC_CMC_CQJ";

    @Bean
    public Topic queueXyftscCmcCqj() {
        return new ActiveMQTopic(TOPIC_XYFTSC_CMC_CQJ);
    }

    // 幸运飞艇单式猜前几 队列名
    public final static String TOPIC_XYFTSC_DS_CQJ = "TOPIC_XYFTSC_DS_CQJ";

    @Bean
    public Topic queueXyftscDsCqj() {
        return new ActiveMQTopic(TOPIC_XYFTSC_DS_CQJ);
    }

    // 幸运飞艇定位胆 队列名
    public final static String TOPIC_XYFTSC_DWD = "TOPIC_XYFTSC_DWD";

    @Bean
    public Topic queueXyftscDwd() {
        return new ActiveMQTopic(TOPIC_XYFTSC_DWD);
    }

    // 幸运飞艇冠亚和 队列名
    public final static String TOPIC_XYFTSC_GYH = "TOPIC_XYFTSC_GYH";

    @Bean
    public Topic queueXyftscGyh() {
        return new ActiveMQTopic(TOPIC_XYFTSC_GYH);
    }

    // 幸运飞艇 预期 队列名
    public final static String TOPIC_XYFTSC_YUQI_DATA = "TOPIC_XYFTSC_YUQI_DATA";

    @Bean
    public Topic queueXyftscYuqiData() {
        return new ActiveMQTopic(TOPIC_XYFTSC_YUQI_DATA);
    }




    //##############################################################################################
    //###                                  新疆时时彩开采结算                                       ###
    //##############################################################################################
    // 新疆时时彩队列名（app调用）
    public final static String TOPIC_APP_SSC_XJ = "TOPIC_APP_SSC_XJ";

    @Bean
    public Topic topicAppSscXj() {
        return new ActiveMQTopic(TOPIC_APP_SSC_XJ);
    }

    // 新疆时时彩队列名（app调用切换期号）
    public final static String TOPIC_APP_CHANGE_ISSUE_SSC_XJ = "TOPIC_APP_CHANGE_ISSUE_SSC_XJ";

    @Bean
    public Topic topicAppChangeIssueSscXj() {
        return new ActiveMQTopic(TOPIC_APP_CHANGE_ISSUE_SSC_XJ);
    }

    // 新疆时时彩【直选】 队列名
    public final static String TOPIC_SSC_XJ_LM = "TOPIC_SSC_XJ_LM";

    @Bean
    public Topic queueSscXjLm() {
        return new ActiveMQTopic(TOPIC_SSC_XJ_LM);
    }

    // 新疆时时彩【组选】 队列名
    public final static String TOPIC_SSC_XJ_DN = "TOPIC_SSC_XJ_DN";

    @Bean
    public Topic queueSscXjDn() {
        return new ActiveMQTopic(TOPIC_SSC_XJ_DN);
    }

    // 新疆时时彩【定位胆】 队列名
    public final static String TOPIC_SSC_XJ_15 = "TOPIC_SSC_XJ_15";

    @Bean
    public Topic queueSscXjL5() {
        return new ActiveMQTopic(TOPIC_SSC_XJ_15);
    }

    // 新疆时时彩【大小单双】 队列名
    public final static String TOPIC_SSC_XJ_QZH = "TOPIC_SSC_XJ_QZH";

    @Bean
    public Topic queueSscXjQzh() {
        return new ActiveMQTopic(TOPIC_SSC_XJ_QZH);
    }

    // 新疆时时彩 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_SSC_XJ_UPDATE_DATA = "TOPIC_SSC_XJ_UPDATE_DATA";

    @Bean
    public Topic queueSscXjUpdateData() {
        return new ActiveMQTopic(TOPIC_SSC_XJ_UPDATE_DATA);
    }

    // 新疆时时彩 杀号 队列名
    public final static String TOPIC_SSC_XJ_KILL_DATA = "TOPIC_SSC_XJ_KILL_DATA";

    @Bean
    public Topic queueSscXjKillData() {
        return new ActiveMQTopic(TOPIC_SSC_XJ_KILL_DATA);
    }

    // 新疆时时彩 推荐号 队列名
    public final static String TOPIC_SSC_XJ_RECOMMEND_DATA = "TOPIC_SSC_XJ_RECOMMEND_DATA";

    @Bean
    public Topic queueSscXjRecommendData() {
        return new ActiveMQTopic(TOPIC_SSC_XJ_RECOMMEND_DATA);
    }

    // 新疆时时彩 预期 队列名
    public final static String TOPIC_SSC_XJ_YUQI_DATA = "TOPIC_SSC_XJ_YUQI_DATA";

    @Bean
    public Topic queueSscXjYuqiData() {
        return new ActiveMQTopic(TOPIC_SSC_XJ_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  重庆时时彩开采结算                                       ###
    //##############################################################################################
    // 重庆时时彩队列名（app调用）
    public final static String TOPIC_APP_SSC_CQ = "TOPIC_APP_SSC_CQ";

    @Bean
    public Topic topicAppSscCq() {
        return new ActiveMQTopic(TOPIC_APP_SSC_CQ);
    }

    // 重庆时时彩队列名（app调用切换期号）
    public final static String TOPIC_APP_CHANGE_ISSUE_SSC_CQ = "TOPIC_APP_CHANGE_ISSUE_SSC_CQ";

    @Bean
    public Topic topicAppChangeIssueSscCq() {
        return new ActiveMQTopic(TOPIC_APP_CHANGE_ISSUE_SSC_CQ);
    }

    // 重庆时时彩【直选】 队列名
    public final static String TOPIC_SSC_CQ_LM = "TOPIC_SSC_CQ_LM";

    @Bean
    public Topic queueSscCqLm() {
        return new ActiveMQTopic(TOPIC_SSC_CQ_LM);
    }

    // 重庆时时彩【组选】 队列名
    public final static String TOPIC_SSC_CQ_DN = "TOPIC_SSC_CQ_DN";

    @Bean
    public Topic queueSscCqDn() {
        return new ActiveMQTopic(TOPIC_SSC_CQ_DN);
    }

    // 重庆时时彩【定位胆】 队列名
    public final static String TOPIC_SSC_CQ_15 = "TOPIC_SSC_CQ_15";

    @Bean
    public Topic queueSscCqL5() {
        return new ActiveMQTopic(TOPIC_SSC_CQ_15);
    }

    // 重庆时时彩【大小单双】 队列名
    public final static String TOPIC_SSC_CQ_QZH = "TOPIC_SSC_CQ_QZH";

    @Bean
    public Topic queueSscCqQzh() {
        return new ActiveMQTopic(TOPIC_SSC_CQ_QZH);
    }

    // 重庆时时彩 - 更新【免费推荐】/【公式杀号】数据 队列名
    public final static String TOPIC_SSC_CQ_UPDATE_DATA = "TOPIC_SSC_CQ_UPDATE_DATA";

    @Bean
    public Topic queueSscCqUpdateData() {
        return new ActiveMQTopic(TOPIC_SSC_CQ_UPDATE_DATA);
    }

    // 重庆时时彩 - 推荐数据 队列名
    public final static String TOPIC_SSC_CQ_RECOMMEND_DATA = "TOPIC_SSC_CQ_RECOMMEND_DATA";

    @Bean
    public Topic queueSscCqRecommendData() {
        return new ActiveMQTopic(TOPIC_SSC_CQ_RECOMMEND_DATA);
    }

    // 重庆时时彩 - 公式杀号 队列名
    public final static String TOPIC_SSC_CQ_kill_DATA = "TOPIC_SSC_CQ_kill_DATA";

    @Bean
    public Topic queueSscCqKillData() {
        return new ActiveMQTopic(TOPIC_SSC_CQ_kill_DATA);
    }

    // 重庆时时彩 - 预期 队列名
    public final static String TOPIC_SSC_CQ_YUQI_DATA = "TOPIC_SSC_CQ_YUQI_DATA";

    @Bean
    public Topic queueSscCqYuqiData() {
        return new ActiveMQTopic(TOPIC_SSC_CQ_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  澳洲快三开采结算                                       ###
    //##############################################################################################
    // 澳洲快三队列名（app调用）
    public final static String TOPIC_APP_KS_AZ = "TOPIC_APP_KS_AZ";
    @Bean
    public Topic topicAppKsAz(){  return new ActiveMQTopic(TOPIC_APP_KS_AZ);
    }
    // 澳洲快三【和值】 队列名
    public final static String TOPIC_KS_AZ_HZ = "TOPIC_KS_AZ_HZ";
    @Bean
    public Topic topicKsAzHz(){  return new ActiveMQTopic(TOPIC_KS_AZ_HZ);
    }
    // 澳洲快三【独胆】 队列名
    public final static String TOPIC_KS_AZ_DD = "TOPIC_KS_AZ_DD";
    @Bean
    public Topic topicKsAzDd(){  return new ActiveMQTopic(TOPIC_KS_AZ_DD);
    }
    // 澳洲快三【连号】 队列名
    public final static String TOPIC_KS_AZ_LH = "TOPIC_KS_AZ_LH";
    @Bean
    public Topic topicKsAzLh(){  return new ActiveMQTopic(TOPIC_KS_AZ_LH);
    }
    // 澳洲快三【三同号，三不同号】 队列名
    public final static String TOPIC_KS_AZ_THREE = "TOPIC_KS_AZ_THREE";
    @Bean
    public Topic topicKsAzThree(){  return new ActiveMQTopic(TOPIC_KS_AZ_THREE);
    }
    // 澳洲快三【二同号，二不同号】 队列名
    public final static String TOPIC_KS_AZ_TWO = "TOPIC_KS_AZ_TWO";
    @Bean
    public Topic topicKsAzTwo(){  return new ActiveMQTopic(TOPIC_KS_AZ_TWO);
    }
    // 澳洲快三 - 预期 队列名
    public final static String TOPIC_KS_AZ_YUQI_DATA = "TOPIC_KS_AZ_YUQI_DATA";
    @Bean
    public Topic topicKsAzYuqiData() {
        return new ActiveMQTopic(TOPIC_KS_AZ_YUQI_DATA);
    }

    //##############################################################################################
    //###                                  德洲快三开采结算                                       ###
    //##############################################################################################
    // 德洲快三队列名（app调用）
    public final static String TOPIC_APP_KS_DZ = "TOPIC_APP_KS_DZ";
    @Bean
    public Topic topicAppKsDz(){  return new ActiveMQTopic(TOPIC_APP_KS_DZ);
    }
    // 德洲快三【和值】 队列名
    public final static String TOPIC_KS_DZ_HZ = "TOPIC_KS_DZ_HZ";
    @Bean
    public Topic topicKsDzHz(){  return new ActiveMQTopic(TOPIC_KS_DZ_HZ);
    }
    // 德洲快三【独胆】 队列名
    public final static String TOPIC_KS_DZ_DD = "TOPIC_KS_DZ_DD";
    @Bean
    public Topic topicKsDzDd(){  return new ActiveMQTopic(TOPIC_KS_DZ_DD);
    }
    // 德洲快三【连号】 队列名
    public final static String TOPIC_KS_DZ_LH = "TOPIC_KS_DZ_LH";
    @Bean
    public Topic topicKsDzLh(){  return new ActiveMQTopic(TOPIC_KS_DZ_LH);
    }
    // 德洲快三【三同号，三不同号】 队列名
    public final static String TOPIC_KS_DZ_THREE = "TOPIC_KS_DZ_THREE";
    @Bean
    public Topic topicKsDzThree(){  return new ActiveMQTopic(TOPIC_KS_DZ_THREE);
    }
    // 德洲快三【二同号，二不同号】 队列名
    public final static String TOPIC_KS_DZ_TWO = "TOPIC_KS_DZ_TWO";
    @Bean
    public Topic topicKsDzTwo(){  return new ActiveMQTopic(TOPIC_KS_DZ_TWO);
    }
    // 德洲快三 - 预期 队列名
    public final static String TOPIC_KS_DZ_YUQI_DATA = "TOPIC_KS_DZ_YUQI_DATA";
    @Bean
    public Topic topicKsDzYuqiData() {
        return new ActiveMQTopic(TOPIC_KS_DZ_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  德洲幸运飞艇结算                                       ###
    //##############################################################################################
    // 德洲幸运飞艇队列名（app调用）
    public final static String TOPIC_APP_DZXYFT = "TOPIC_APP_DZXYFT";
    @Bean
    public Topic topicAppDzXyft(){  return new ActiveMQTopic(TOPIC_APP_DZXYFT);
    }
    // 德洲幸运飞艇-两面 队列名
    public final static String TOPIC_DZXYFT_LM = "TOPIC_DZXYFT_LM";
    @Bean
    public Topic topicDzxyftLm() {
        return new ActiveMQTopic(TOPIC_DZXYFT_LM);
    }
    // 德洲幸运飞艇-猜名次猜前几 队列名
    public final static String TOPIC_DZXYFT_CMC_CQJ = "TOPIC_DZXYFT_CMC_CQJ";
    @Bean
    public Topic topicDzxyftCmcCqj() {
        return new ActiveMQTopic(TOPIC_DZXYFT_CMC_CQJ);
    }
    // 德洲幸运飞艇-冠亚和 队列名
    public final static String TOPIC_DZXYFT_GYH = "TOPIC_DZXYFT_GYH";
    @Bean
    public Topic topicDzxyftGyh() {
        return new ActiveMQTopic(TOPIC_DZXYFT_GYH);
    }
    // 德洲幸运飞艇 - 预期 队列名
    public final static String TOPIC_DZXYFT_YUQI_DATA = "TOPIC_DZXYFT_YUQI_DATA";
    @Bean
    public Topic topicDzxyftYuqiData() {
        return new ActiveMQTopic(TOPIC_DZXYFT_YUQI_DATA);
    }


    //##############################################################################################
    //###                                  德洲PC蛋蛋结算                                       ###
    //##############################################################################################
    // 德洲PC蛋蛋队列名（app调用）
    public final static String TOPIC_APP_DZPCEGG = "TOPIC_APP_DZPCEGG";
    @Bean
    public Topic topicAppDzpcegg(){  return new ActiveMQTopic(TOPIC_APP_DZPCEGG);
    }
    // 德洲PC蛋蛋特码 队列名
    public final static String TOPIC_DZPCEGG_TM = "TOPIC_DZPCEGG_TM";
    @Bean
    public Topic topicDzpceggTm() {
        return new ActiveMQTopic(TOPIC_DZPCEGG_TM);
    }
    // 德洲PC蛋蛋豹子 队列名
    public final static String TOPIC_DZPCEGG_BZ = "TOPIC_DZPCEGG_BZ";
    @Bean
    public Topic topicDzpceggBz() {
        return new ActiveMQTopic(TOPIC_DZPCEGG_BZ);
    }
    // 德洲PC蛋蛋特码包三 队列名
    public final static String TOPIC_DZPCEGG_TMBS = "TOPIC_DZPCEGG_TMBS";
    @Bean
    public Topic topicDzpceggTmbs() {
        return new ActiveMQTopic(TOPIC_DZPCEGG_TMBS);
    }
    // 德洲PC蛋蛋色波 队列名
    public final static String TOPIC_DZPCEGG_SB = "TOPIC_DZPCEGG_SB";
    @Bean
    public Topic topicDzpceggSb() {
        return new ActiveMQTopic(TOPIC_DZPCEGG_SB);
    }
    // 德洲PC蛋蛋混合 队列名
    public final static String TOPIC_DZPCEGG_HH = "TOPIC_DZPCEGG_HH";
    @Bean
    public Topic topicDzpceggHh() {
        return new ActiveMQTopic(TOPIC_DZPCEGG_HH);
    }
    // 德洲pc蛋蛋 - 预期 队列名
    public final static String TOPIC_DZPCEGG_YUQI_DATA = "TOPIC_DZPCEGG_YUQI_DATA";
    @Bean
    public Topic topicDzpceggYuqiData() {
        return new ActiveMQTopic(TOPIC_DZPCEGG_YUQI_DATA);
    }


    //##############################################################################################
    //###                                   新加坡六合彩开采结算                                   ###
    // 新加坡六合彩 队列名（app调用）
    public final static String TOPIC_APP_XJP_LHC = "TOPIC_APP_XJP_LHC";
    @Bean
    public Topic topicAppXjpLhc(){  return new ActiveMQTopic(TOPIC_APP_XJP_LHC);
    }
    // 新加坡六合彩(特码,正特,六肖,正码1-6) 队列名
    public final static String TOPIC_XJPLHC_TM_ZT_LX = "TOPIC_XJPLHC_TM_ZT_LX";
    @Bean
    public Topic topicXjplhcTmZtLx(){  return new ActiveMQTopic(TOPIC_XJPLHC_TM_ZT_LX);
    }
    // 新加坡六合彩(正码,半波,尾数) 队列名
    public final static String TOPIC_XJPLHC_ZM_BB_WS = "QUEUE_XJPLHC_ZM_BB_WS";
    @Bean
    public Topic topicXjplhcZmBbWs(){  return new ActiveMQTopic(TOPIC_XJPLHC_ZM_BB_WS);
    }
    // 新加坡六合彩(连码,连肖,连尾) 队列名
    public final static String TOPIC_XJPLHC_LM_LX_LW = "TOPIC_XJPLHC_LM_LX_LW";
    @Bean
    public Topic topicXjplhcLmLxLw(){  return new ActiveMQTopic(TOPIC_XJPLHC_LM_LX_LW);
    }
    // 新加坡六合彩(不中,1-6龙虎,五行) 队列名
    public final static String TOPIC_XJPLHC_BZ_LH_WX = "TOPIC_XJPLHC_BZ_LH_WX";
    @Bean
    public Topic topicXjplhcBzLhWx(){  return new ActiveMQTopic(TOPIC_XJPLHC_BZ_LH_WX);
    }
    // 新加坡六合彩(平特,特肖) 队列名
    public final static String TOPIC_XJPLHC_PT_TX = "TOPIC_XJPLHC_PT_TX";
    @Bean
    public Topic topicXjplhcPtTx(){  return new ActiveMQTopic(TOPIC_XJPLHC_PT_TX);
    }
    // 新加坡六合彩 预期 队列名
    public final static String TOPIC_XJPLHC_YUQI_DATA = "TOPIC_XJPLHC_YUQI_DATA";
    @Bean
    public Topic topicXjplhcYuqiData(){  return new ActiveMQTopic(TOPIC_XJPLHC_YUQI_DATA);
    }

    //##############################################################################################
    //###                                   六合图库  ###
    //##############################################################################################
    // 六合图库 - 队列名
    public final static String TOPIC_LHC_PHTO = "TOPIC_LHC_PHTO";

    @Bean
    public Topic queueLhcPhoto() {
        return new ActiveMQTopic(TOPIC_LHC_PHTO);
    }

    //##############################################################################################
    //###                                   在线人数统计  ###
    //##############################################################################################
    // 在线人数统计 - 队列名
    public final static String TOPIC_MEMBER_ONLINE = "TOPIC_MEMBER_ONLINE";

    @Bean
    public Topic queueMemberOnline() {
        return new ActiveMQTopic(TOPIC_MEMBER_ONLINE);
    }

    //##############################################################################################
    //###                                   检查未结算订单              ###
    //##############################################################################################
    // 检查未结算订单 - 队列名
    public final static String TOPIC_CHECK_ORDER = "TOPIC_CHECK_ORDER";

    @Bean
    public Topic topicCheckOrder() {
        return new ActiveMQTopic(TOPIC_CHECK_ORDER);
    }


    //##############################################################################################
    //###                                   VIP返水 统计  ###
    //##############################################################################################
    // VIP返水统计 - 队列名
    public final static String TOPIC_VIP_FANSHUI = "TOPIC_VIP_FANSHUI";

    @Bean
    public Topic queueVipFanshui() {
        return new ActiveMQTopic(TOPIC_VIP_FANSHUI);
    }

    //##############################################################################################
    //###                                   分享赠送 统计  ###
    //##############################################################################################
    // 分享赠送 - 队列名
    public final static String TOPIC_FX_ZENGSONG = "TOPIC_FX_ZENGSONG";

    @Bean
    public Topic queueFxZengsong() {
        return new ActiveMQTopic(TOPIC_FX_ZENGSONG);
    }

    //##############################################################################################
    //###                                   分享返水 统计  ###
    //##############################################################################################
    // 分享返水 - 队列名
    public final static String TOPIC_FX_FANSHUI = "TOPIC_FX_FANSHUI";

    @Bean
    public Topic queueFxFanshui() {
        return new ActiveMQTopic(TOPIC_FX_FANSHUI);
    }

    //##############################################################################################
    //###                                   计算首次充值礼金-每周返还 统计  ###
    //##############################################################################################
    // 每周返回 - 队列名
    public final static String TOPIC_FH_MEIZHOU = "TOPIC_FH_MEIZHOU";

    @Bean
    public Topic queueFhMeizhou() {
        return new ActiveMQTopic(TOPIC_FH_MEIZHOU);
    }

    //##############################################################################################
    //###                                  大神盈利率、胜率和连中 计算  ###
    //##############################################################################################
    // 大神计算 - 队列名
    public final static String TOPIC_DS_JISUAN = "TOPIC_DS_JISUAN";

    @Bean
    public Topic queueDsJisuan() {
        return new ActiveMQTopic(TOPIC_DS_JISUAN);
    }

    // 所有彩种预期数据 当天 队列名
    public final static String TOPIC_YUQI_TODAYT = "TOPIC_YUQI_TODAYT";

    @Bean
    public Queue queueYuqiTODAY() {
        return new ActiveMQQueue(TOPIC_YUQI_TODAYT);
    }

    // 检查sg数据
    public final static String TOPIC_ALL_LOTTERY_SG = "TOPIC_ALL_LOTTERY_SG";

    @Bean
    public Topic topicAllLotterySg() {
        return new ActiveMQTopic(TOPIC_ALL_LOTTERY_SG);
    }

    // 查找缺失的 sg数据
    public final static String TOPIC_MISSING_LOTTERY_SG = "TOPIC_MISSING_LOTTERY_SG";

    @Bean
    public Queue queueMissingLotterySg() {
        return new ActiveMQQueue(TOPIC_MISSING_LOTTERY_SG);
    }

    // 更新订单表数据 order_bet_record
    public final static String TOPIC_ORDER_DATA_UPDATE = "TOPIC_ORDER_DATA_UPDATE";

    @Bean
    public Topic topicOrderDataUpdate() {
        return new ActiveMQTopic(TOPIC_ORDER_DATA_UPDATE);
    }

    // 更新 用户表 等待开奖金额字段 app_member
    public final static String TOPIC_APP_WAITAMOUNT_UPDATE = "TOPIC_APP_WAITAMOUNT_UPDATE";

    @Bean
    public Topic topicAppWaitAmountUpdate() {
        return new ActiveMQTopic(TOPIC_APP_WAITAMOUNT_UPDATE);
    }


    // 订单队列名
    public final static String QUEUE_ORDER = "QUEUE_ORDER";

    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次,这里设置为10次,-1表示不限次数
        redeliveryPolicy.setMaximumRedeliveries(-1);
        //重发时间间隔,默认为1毫秒,设置为10000毫秒
        redeliveryPolicy.setInitialRedeliveryDelay(10000);
        //表示没有拖延只有UseExponentialBackOff(true)为true时生效
        //第一次失败后重新发送之前等待10000毫秒,第二次失败再等待10000 * 2毫秒
        //第三次翻倍10000 * 2 * 2，以此类推
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(true);
        //设置重发最大拖延时间360000毫秒 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(360000);
        return redeliveryPolicy;
    }

    @Bean
    public PooledConnectionFactory connectionPoolFactory() {
        logger.info("activeMq配置信息:" + USER + "," + PASSWORD + "," + BROKERURL);
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(USER, PASSWORD, BROKERURL);
        factory.setRedeliveryPolicy(redeliveryPolicy());
        PooledConnectionFactory poolFactory = new PooledConnectionFactory(factory);
        return poolFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(PooledConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        bean.setPubSubDomain(false);
        return bean;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(PooledConnectionFactory pooledConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(pooledConnectionFactory);
        return bean;
    }

    @Bean(name = "jmsTemplatetopicDurable")
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionPoolFactory());

        //可以不设置,默认是持久化
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);

        //设置为订阅/发布模式
        jmsTemplate.setPubSubDomain(true);

//        jmsTemplate.setPriority(5);
//        jmsTemplate.setTimeToLive(1000l);
        //必须开启,不然持久化设置无效
        jmsTemplate.setExplicitQosEnabled(true);
        jmsTemplate.setDeliveryPersistent(true);
        return jmsTemplate;

    }


    @Value("${spring.activemq.user}")
    private String user;
    @Value("${spring.activemq.password}")
    private String password;
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;
    @Value("${platform.name}")  //标记平台参数，比如CESHI,CPT，XGC，
    private String platform;


    public static String USER;
    public static String PASSWORD;
    public static String BROKERURL;
    public static String PLATFORM;


    @Override
    public void afterPropertiesSet() {
        USER = this.user;
        PASSWORD = this.password;
        BROKERURL = this.brokerUrl;
        PLATFORM = this.platform;
    }


}
