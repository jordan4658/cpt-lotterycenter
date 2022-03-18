package com.caipiao.core.library.utils;

/**
 * Redis 所有Key 集中类
 */
public class RedisKeys {

    /** 彩种对象key: key后拼接id */
    public static final String LOTTERY_KEY = "LOTTERY_KEY_";

    /** 用户对象key: key后拼接id */
    public static final String APP_MEMBER = "APP_MEMBER_";

    /** 用户名称key: key后拼接id */
    public static final String APP_NICKNAME_NAME = "APP_NICKNAME_NAME_";

    public static final String APP_ACCOUNT_NAME = "APP_ACCOUNT_NAME_";

    /** 搜索用户对象key: key后拼接搜索关键字 */
    public static final String SEARCH_APP_MEMBER = "SEARCE_APP_MEMBER_";

    /** 搜索帖子key: key后拼接搜索关键字 */
    public static final String SEARCH_XS_REFFERE = "SEARCE_XS_REFFERE_";

    /** 所有彩种集合key */
    public static final String LOTTERY_LIST_KEY = "LOTTERY_LIST_KEY";

    /** 所有彩种MAP集合key */
    public static final String LOTTERY_MAP_KEY = "LOTTERY_MAP_KEY";

    /** 所有彩种玩法集合key */
    public static final String LOTTERY_PLAY_LIST_KEY = "LOTTERY_PLAY_LIST_KEY";

    /** 所有彩种玩法MAP集合key */
    public static final String LOTTERY_PLAY_MAP_KEY = "LOTTERY_PLAY_MAP_KEY";

    /** 投注限制对象key: key后拼接id */
    public static final String BONUS_KEY = "BONUS_KEY_";

    /** 赔率配置列表 */
    public static final String ODDS_LIST_SETTING_KEY = "ODDS_LIST_SETTING_";

    /** 所有玩法赔率信息 */
    public static final String LOTTERY_PLAY_ODDS_LIST_KEY = "LOTTERY_PLAY_ODDS_LIST_KEY";

    /** 数据值等级列表 */
    public static final String DATA_VALUE_LEVEL = "DATA_VALUE_LEVEL_";

    /**
     * ==================================赛果缓存============================================*
     */

    /** 重庆时时彩赛果 */
    public static final String CQSSC_RESULT_VALUE = "CQSSC_RESULT_VALUE_1101";

    public static final String CQSSC_NEXT_VALUE = "CQSSC_NEXT_VALUE_1101";

    public static final String CQSSC_ALGORITHM_VALUE = "CQSSC_ALGORITHM_VALUE_1101";// 计算单双，大小，五行数据

    /** 新疆时时彩赛果 */
    public static final String XJSSC_RESULT_VALUE = "XJSSC_RESULT_VALUE_1102";

    public static final String XJSSC_NEXT_VALUE = "XJSSC_NEXT_VALUE_1102";

    public static final String XJSSC_ALGORITHM_VALUE = "XJSSC_ALGORITHM_VALUE_1102";// 计算单双，大小，五行数据

    /** 天津时时彩赛果 */
    public static final String TJSSC_RESULT_VALUE = "TJSSC_RESULT_VALUE_1103";

    public static final String TJSSC_NEXT_VALUE = "TJSSC_NEXT_VALUE_1103";

    public static final String TJSSC_ALGORITHM_VALUE = "TJSSC_ALGORITHM_VALUE_1103";// 计算单双，大小，五行数据

    /** 10分时时彩赛果 */
    public static final String TENSSC_RESULT_VALUE = "TENSSC_RESULT_VALUE_1104";

    public static final String TENSSC_NEXT_VALUE = "TENSSC_NEXT_VALUE_1104";

    public static final String TENSSC_ALGORITHM_VALUE = "TENSSC_ALGORITHM_VALUE_1104";// 计算单双，大小，五行数据

    /** 5分时时彩赛果 */
    public static final String FIVESSC_RESULT_VALUE = "FIVESSC_RESULT_VALUE_1105";

    public static final String FIVESSC_NEXT_VALUE = "FIVESSC_NEXT_VALUE_1105";

    public static final String FIVESSC_ALGORITHM_VALUE = "FIVESSC_ALGORITHM_VALUE_1105";// 计算单双，大小，五行数据

    /** 德州时时彩赛果 */
    public static final String JSSSC_RESULT_VALUE = "JSSSC_RESULT_VALUE_1106";

    public static final String JSSSC_NEXT_VALUE = "JSSSC_NEXT_VALUE_1106";

    public static final String JSSSC_ALGORITHM_VALUE = "JSSSC_ALGORITHM_VALUE_1106";// 计算单双，大小，五行数据

    /** 六合彩赛果 */
    public static final String LHC_RESULT_VALUE = "LHC_RESULT_VALUE_1201";

    /** 1分六合彩赛果 */
    public static final String ONELHC_RESULT_VALUE = "ONELHC_RESULT_VALUE_1202";

    public static final String ONELHC_NEXT_VALUE = "ONELHC_NEXT_VALUE_1202";

    public static final String ONELHC_OPEN_VALUE = "ONELHC_OPEN_VALUE_1202";

    public static final String ONELHC_ALGORITHM_VALUE = "ONELHC_ALGORITHM_VALUE_1202";// 计算单双，大小，五行数据

    /** 5分六合彩赛果 */
    public static final String FIVELHC_RESULT_VALUE = "FIVELHC_RESULT_VALUE_1203";

    public static final String FIVELHC_NEXT_VALUE = "FIVELHC_NEXT_VALUE_1203";

    public static final String FIVELHC_OPEN_VALUE = "FIVELHC_OPEN_VALUE_1203";

    public static final String FIVELHC_ALGORITHM_VALUE = "FIVELHC_ALGORITHM_VALUE_1203";// 计算单双，大小，五行数据

    /** 时时六合彩赛果 */
//    public static final String SSLHC_RESULT_VALUE = "SSLHC_RESULT_VALUE_1204";

//    public static final String SSLHC_NEXT_VALUE = "SSLHC_NEXT_VALUE_1204";

//    public static final String SSLHC_OPEN_VALUE = "SSLHC_OPEN_VALUE_1204";

//    public static final String SSLHC_ALGORITHM_VALUE = "SSLHC_ALGORITHM_VALUE_1204";// 计算单双，大小，五行数据

    /** 北京PK10赛果 */
    public static final String BJPKS_RESULT_VALUE = "BJPKS_RESULT_VALUE_1301";

    public static final String BJPKS_NEXT_VALUE = "BJPKS_NEXT_VALUE_1301";

    public static final String BJPKS_OPEN_VALUE = "BJPKS_OPEN_VALUE_1301";

    public static final String BJPKS_ALGORITHM_VALUE = "BJPKS_ALGORITHM_VALUE_1301";// 计算单双，大小，五行数据

    /** 10分PK10赛果 */
    public static final String TENPKS_RESULT_VALUE = "TENPKS_RESULT_VALUE_1302";

    public static final String TENPKS_NEXT_VALUE = "TENPKS_NEXT_VALUE_1302";

    public static final String TENPKS_OPEN_VALUE = "TENPKS_OPEN_VALUE_1302";

    public static final String TENPKS_ALGORITHM_VALUE = "TENPKS_ALGORITHM_VALUE_1302";// 计算单双，大小，五行数据

    /** 5分PK10赛果 */
    public static final String FIVEPKS_RESULT_VALUE = "FIVEPKS_RESULT_VALUE_1303";

    public static final String FIVEPKS_NEXT_VALUE = "FIVEPKS_NEXT_VALUE_1303";

    public static final String FIVEPKS_OPEN_VALUE = "FIVEPKS_OPEN_VALUE_1303";

    public static final String FIVEPKS_ALGORITHM_VALUE = "FIVEPKS_ALGORITHM_VALUE_1303";// 计算单双，大小，五行数据

    /** 德州PK10赛果 */
    public static final String JSPKS_RESULT_VALUE = "JSPKS_RESULT_VALUE_1304";

    public static final String JSPKS_NEXT_VALUE = "JSPKS_NEXT_VALUE_1304";

    public static final String JSPKS_OPEN_VALUE = "JSPKS_OPEN_VALUE_1304";

    public static final String JSPKS_ALGORITHM_VALUE = "JSPKS_ALGORITHM_VALUE_1304";// 计算单双，大小，五行数据

    /** 幸运飞艇赛果 */
    public static final String XYFEIT_RESULT_VALUE = "XYFEIT_RESULT_VALUE_1401";

    public static final String XYFEIT_NEXT_VALUE = "XYFEIT_NEXT_VALUE_1401";

    public static final String XYFEIT_OPEN_VALUE = "XYFEIT_OPEN_VALUE_1401";

    public static final String XYFEIT_ALGORITHM_VALUE = "XYFEIT_ALGORITHM_VALUE_1401";// 计算单双，大小，五行数据

    /** 幸运飞艇赛果私彩 */
    public static final String XYFEITSC_RESULT_VALUE = "XYFEITSC_RESULT_VALUE_1403";

    public static final String XYFEITSC_NEXT_VALUE = "XYFEIT_NEXT_VALUE_1403";

    public static final String XYFEITSC_OPEN_VALUE = "XYFEIT_OPEN_VALUE_1403";

    public static final String XYFEITSC_ALGORITHM_VALUE = "XYFEIT_ALGORITHM_VALUE_1403";// 计算单双，大小，五行数据

    /** PC蛋蛋赛果 */
    public static final String PCDAND_RESULT_VALUE = "PCDAND_RESULT_VALUE_1501";

    public static final String PCDAND_NEXT_VALUE = "PCDAND_NEXT_VALUE_1501";

    public static final String PCDAND_OPEN_VALUE = "PCDAND_OPEN_VALUE_1501";

    public static final String PCDAND_ALGORITHM_VALUE = "PCDAND_ALGORITHM_VALUE_1501";// 计算单双，大小，五行数据

    /** 腾讯分分彩赛果 */
    public static final String TXFFC_RESULT_VALUE = "TXFFC_RESULT_VALUE_1601";

    public static final String TXFFC_NEXT_VALUE = "TXFFC_NEXT_VALUE_1601";

    public static final String TXFFC_OPEN_VALUE = "TXFFC_OPEN_VALUE_1601";

    public static final String TXFFC_ALGORITHM_VALUE = "TXFFC_ALGORITHM_VALUE_1601";// 计算单双，大小，五行数据

    /** 大乐透赛果 */
    public static final String DLT_RESULT_VALUE = "DLT_RESULT_VALUE_1701";

    public static final String DLT_NEXT_VALUE = "DLT_NEXT_VALUE_1701";

    /** 排列3/5赛果 */
    public static final String TCPLW_RESULT_VALUE = "TCPLW_RESULT_VALUE_1702";

    /** 7星彩赛果 */
    public static final String TC7XC_RESULT_VALUE = "TC7XC_RESULT_VALUE_1703";

    /** 双色球赛果 */
    public static final String FCSSQ_RESULT_VALUE = "FCSSQ_RESULT_VALUE_1801";

    /** 福彩3D赛果 */
    public static final String FC3D_RESULT_VALUE = "FC3D_RESULT_VALUE_1802";

    /** 七乐彩赛果 */
    public static final String FC7LC_RESULT_VALUE = "FC7LC_RESULT_VALUE_1803";

    /** 快乐牛牛赛果 */
    public static final String KLNIU_RESULT_VALUE = "KLNIU_RESULT_VALUE_1901";

    /** 澳洲牛牛赛果 */
    public static final String AZNIU_RESULT_VALUE = "AZNIU_RESULT_VALUE_1902";

    /** 德州牛牛赛果 */
    public static final String JSNIU_RESULT_VALUE = "JSNIU_RESULT_VALUE_1903";

    /** 德州PK10番摊赛果 */
    public static final String JSPKFT_RESULT_VALUE = "JSPKFT_RESULT_VALUE_2001";

    public static final String JSPKFT_NEXT_VALUE = "JSPKFT_NEXT_VALUE_2001";

    public static final String JSPKFT_OPEN_VALUE = "JSPKFT_OPEN_VALUE_2001";

    /** 幸运飞艇番摊赛果 */
    public static final String XYFTFT_RESULT_VALUE = "XYFTFT_RESULT_VALUE_2002";

    public static final String XYFTFT_NEXT_VALUE = "XYFTFT_NEXT_VALUE_2002";

    public static final String XYFTFT_OPEN_VALUE = "XYFTFT_OPEN_VALUE_2002";

    /** 德州时时彩番摊赛果 */
    public static final String JSSSCFT_RESULT_VALUE = "JSSSCFT_RESULT_VALUE_2003";

    public static final String JSSSCFT_NEXT_VALUE = "JSSSCFT_NEXT_VALUE_2003";

    /** 澳洲ACT赛果 */
    public static final String AUSACT_RESULT_VALUE = "AUSACT_RESULT_VALUE_2201";

    public static final String AUSACT_NEXT_VALUE = "AUSACT_NEXT_VALUE_2201";

    public static final String AUSACT_OPEN_VALUE = "AUSACT_OPEN_VALUE_2201";

    public static final String AUSACT_ALGORITHM_VALUE = "AUSACT_ALGORITHM_VALUE_2201";// 计算单双，大小，五行数据

    /** 澳洲时时彩赛果 */
    public static final String AUZSSC_RESULT_VALUE = "AUZSSC_RESULT_VALUE_2202";

    public static final String AUZSSC_NEXT_VALUE = "AUZSSC_NEXT_VALUE_2202";

    public static final String AUZSSC_OPEN_VALUE = "AUZSSC_OPEN_VALUE_2202";

    /** 澳洲澳洲F1赛果 */
    public static final String AUSPKS_RESULT_VALUE = "AUSPKS_RESULT_VALUE_2203";

    public static final String AUSPKS_NEXT_VALUE = "AUSPKS_NEXT_VALUE_2203";

    public static final String AUSPKS_OPEN_VALUE = "AUSPKS_OPEN_VALUE_2203";

    public static final String AUSPKS_ALGORITHM_VALUE = "AUSPKS_ALGORITHM_VALUE_2203";// 计算单双，大小，五行数据

    /** ==================================WEB缓存============================================* */
    /** 六合彩心水推荐列表集合 */
    public static final String APP_GETLHCXSRECOMMENDS_RESULT_VALUE = "appGetlhcxsrecommends_result_value_";

    public static final String WEB_GETLHCXSRECOMMENDS_RESULT_VALUE = "webGetlhcxsrecommends_result_value_";


    /** 私彩订单对象key: key后拼接id */
    public static final String KILL_ORDER = "KILL_ORDER_";

    /**
     * 私彩杀号配置
     */
    public static final String SICAIONELHCRATE = "SICAIONELHCRATE";
    public static final String SICAIONESSCRATE = "SICAIONESSCRATE";
    public static final String SICAIONEPKSRATE = "SICAIONEPKSRATE";
    public static final String SICAIFIVELHCRATE = "SICAIFIVELHCRATE";
    public static final String SICAIFIVESSCRATE = "SICAIFIVESSCRATE";
    public static final String SICAIFIVEPKSRATE = "SICAIFIVEPKSRATE";
    public static final String SICAITENLHCRATE = "SICAITENLHCRATE";
    public static final String SICAITENSSCRATE = "SICAITENSSCRATE";
    public static final String SICAITENPKSRATE = "SICAITENPKSRATE";
    public static final String SICAITXFFCRATE = "SICAITXFFCRATE";
    public static final String KILLORDERTIME = "KILLORDERTIME";
    public static final String SICAIDZPCEGGRATE = "SICAIDZPCEGGRATE";
    public static final String SICAIDZXYFTRATE = "SICAIDZXYFTRATE";
    public static final String SICAIDZKSRATE = "SICAIDZKSRATE";
    public static final String SICAIAZKSRATE = "SICAIAZKSRATE";
    public static final String SICAIXJPLHCRATE = "SICAIXJPLHCRATE";

    /**
     * 第三方调接口 的Token 存储key   数据库同步token到缓存（表token_ip）
     */
    public static final String TOKEN_KEY = "TOKEN_KEY";

    /**
     * 第三方调接口 的Token 存储key   缓存同步token对应的ips到数据库（表token_ip）
     */
    public static final String TOKEN_KEY_RE = "TOKEN_KEY_RE";

    /**
     * 第三方调接口 的 存储key :"token=ip=code",  value:  "时间=访问次数"    访问次数是记录一个小时的，每小时清零一次
     */
    public static final String IP_VISIT_KEY = "IP_VISIT_KEY";

    //幸运飞艇私彩-历史赛果数据缓存key
    public static final String XYFTSC_SG_HS_LIST = "XYFTSC_SG_HS_LIST";


}
