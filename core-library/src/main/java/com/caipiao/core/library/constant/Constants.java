package com.caipiao.core.library.constant;

public final class Constants {
    // 默认编码
    public static final String DEFAULT_ENCODING = "UTF-8";
    //token过期时间（秒）2周
    public static final Long TOKEN_EXPIRES = 86400 * 14L;

    //app在线监测时间（超过这个时间没有操作则默认为不在线,1800秒）半个小时
    public static final Long APP_ONLINE_TIME = 1800L;

    // Aes加密解密key
    public static final String AES_KEY = "ruanjie2018@379gc!834gfg?d30RJcaipiao";

    //	public static final String HTTP_HOST_IMAGE = "http://47.52.5.170:9001/caipiaoMedia";
    public static final String HTTP_HOST_IMAGE = "http://47.75.199.227:9001/caipiaoMedia";

    // App端参数校验秘钥
    public static final String APP_KEY = "ruanjie2018@jlj34ij34lkj?d30RJcaipiao";

    //定义存放在session中的用户
    public static final String SESSION_KEY = "user";

    //定义shiro中的session是否被管理员强制退出的标识
    public static final String SESSION_KICKOUT_KEY = "force.logout";

    //验证码有效时间（300秒)
    public static final Long CAPTCHA_VALID_SECONDS = 300L;

    //验证码一天的最大发送次数
    public static final int CAPTCHA_MAX_COUNT_DAY = 6;

    //========================极光推送的标签================================================
    public static final String KJ_CQSSC = "kj_cqssc";        //开奖号码推送--重庆时时彩
    public static final String KJ_XJSSC = "kj_xjssc";        //开奖号码推送--新疆时时彩
    public static final String KJ_TJSSC = "kj_tjssc";        //开奖号码推送--天津时时彩
    public static final String KJ_TXFFC = "kj_txffc";        //开奖号码推送--腾讯分分彩
    public static final String KJ_XGLHC = "kj_xglhc";        //开奖号码推送--香港六合彩
    public static final String KJ_PCEGG = "kj_pcegg";        //开奖号码推送--PC蛋蛋
    public static final String KJ_BJPKS = "kj_bjpks";        //开奖号码推送--北京PK10
    public static final String KJ_XYFT = "kj_xyft";        //开奖号码推送--幸运飞艇
    public static final String KJ_XYFT_FT = "kj_xyft_ft";        //开奖号码推送--幸运飞艇番摊
    public static final String KJ_FIVESSC = "kj_fivessc";        //开奖号码推送--五分时时彩
    public static final String KJ_TENSSC = "kj_tenssc";        //开奖号码推送--十分时时彩
    public static final String KJ_JSSSC = "kj_jsssc";        //开奖号码推送--极速时时彩
    public static final String KJ_JSSSC_FT = "kj_jsssc_ft";        //开奖号码推送--极速时时彩番摊
    public static final String KJ_FTJSSSC = "kj_ftjsssc";        //开奖号码推送--极速时时彩
    public static final String KJ_ONELHC = "kj_onelhc"; //开奖号码推送--一分六合彩
    public static final String KJ_FIVELHC = "kj_fivelhc"; //开奖号码推送--五分六合彩
    public static final String KJ_SSLHC = "kj_sslhc"; //开奖号码推送--时时六合彩（澳门六合彩）
    public static final String KJ_TENBJPKS = "kj_tenbjpks"; //开奖号码推送--十分PK10
    public static final String KJ_FIVEBJPKS = "kj_fivebjpks"; //开奖号码推送--五分PK10
    public static final String KJ_JSBJPKS = "kj_jsbjpks"; //开奖号码推送--极速PK10
    public static final String KJ_JSBJPKS_FT = "kj_jsbjpks_ft"; //开奖号码推送--极速PK10番摊
    public static final String KJ_AUSPKS = "kj_auspks";//开奖号码推送--澳洲pks
    public static final String KJ_AUSACT = "kj_ausACT";//开奖号码推送--澳洲ACT


    public static final String KJ_TCPLW = "kj_tcplw";//开奖号码推送--体彩排列5
    public static final String KJ_TCPLS = "kj_tcpls";//开奖号码推送--体彩排列3
    public static final String KJ_TCDLT = "kj_tcdlt";//开奖号码推送--体彩大乐透
    public static final String KJ_TC7XC = "kj_tc7xc";//开奖号码推送--体彩七星彩
    public static final String KJ_FC3D = "kj_fc3d";//开奖号码推送--体彩3d福彩
    public static final String KJ_FC7LC = "kj_fc7lc";//开奖号码推送--体彩7乐彩
    public static final String KJ_FCSSQ = "kj_fcssq";//开奖号码推送--体彩双色球

    //========================极光推送的消息类型================================================
    public static final String MSG_TYPE_WINPUSH = "win_push";        //中奖消息
    public static final String MSG_TYPE_OPENPUSH = "open_push";        //开奖消息

    //========================在线人数统计================================================
    public static final String ANDROIDONLINE = "andOnline";
    public static final String IOSONLINE = "iosOnline";
    public static final String WEBONLINE = "webOnline";

    public static final String SENSITIVE_WORDS = "SENSITIVE_WORDS";

    // 默认空值
    public static final String DEFAULT_NULL = "";
    // 默认空值
    public static final String DEFAULT_NAME = "";
    // 默认Long
    public static final Long DEFAULT_LONG = 0l;
    // 默认pageSize，默认算法查询数量为200条数据
    public static final Integer DEFAULT_ALGORITHM_PAGESIZE = 200;
    // 默认Integer
    public static final Integer DEFAULT_INTEGER = 0;
    // 默认Integer one
    public static final Integer DEFAULT_ONE = 1;
    // 默认Integer TWO
    public static final Integer DEFAULT_TWO = 2;
    // 默认Integer THREE
    public static final Integer DEFAULT_THREE = 3;
    // 默认Integer SIX
    public static final Integer DEFAULT_SIX = 6;
    // 默认Integer TWELVE
    public static final Integer DEFAULT_TWELVE = 12;
    // 默认Integer TWENTY
    public static final Integer DEFAULT_TWENTY = 20;
    // 默认Float
    public static final float DEFAULT_FLOAT = 0;
    // 默认pageNum，默认为首页
    public static final Integer DEFAULT_PAGENUM = 1;
    // 默认pageSize，默认为10条数据
    public static final Integer DEFAULT_PAGESIZE = 10;
    // 增加数据连接符号
    public static final String CONNECTIONSYMBOL = ",";

    /***
     * ==========================================ACT============================================
     */
    public static final Integer DEFAULT_BIGORSMALL_MEDIAN = 810;// 开奖号码计算大小,中间值

    public static final String BIGORSMALL_BIG = "大";

    public static final String TOTAL_BIGORSMALL_BIG = "总和大";

    public static final String CROWN_BIGORSMALL_BIG = "冠亚大";

    public static final String ZONG_BIGORSMALL_BIG = "总大";

    public static final String BIGORSMALL_SMALL = "小";

    public static final String TOTAL_BIGORSMALL_SMALL = "总和小";

    public static final String CROWN_BIGORSMALL_SMALL = "冠亚小";

    public static final String ZONG_BIGORSMALL_SMALL = "总小";

    public static final String BIGORSMALL_SAME = "和";

    public static final String BIGORSMALL_ODD_NUMBER = "单";//奇数

    public static final String TOTAL_BIGORSMALL_ODD_NUMBER = "总和单";//奇数

    public static final String CROWN_BIGORSMALL_ODD_NUMBER = "冠亚单";//奇数

    public static final String ZONG_BIGORSMALL_ODD_NUMBER = "总单";//奇数

    public static final String BIGORSMALL_EVEN_NUMBER = "双";//偶数

    public static final String TOTAL_BIGORSMALL_EVEN_NUMBER = "总和双";//偶数

    public static final String CROWN_BIGORSMALL_EVEN_NUMBER = "冠亚双";//偶数

    public static final String ZONG_BIGORSMALL_EVEN_NUMBER = "总双";//偶数

    public static final Integer BIGORSMALL_GOLD_START = 210;

    public static final Integer BIGORSMALL_GOLD_END = 695;

    public static final String BIGORSMALL_GOLD_TYPE = "金";

    public static final Integer BIGORSMALL_WOOD_START = 696;

    public static final Integer BIGORSMALL_WOOD_END = 763;

    public static final String BIGORSMALL_WOOD_TYPE = "木";

    public static final Integer BIGORSMALL_WATER_START = 764;

    public static final Integer BIGORSMALL_WATER_END = 855;

    public static final String BIGORSMALL_WATER_TYPE = "水";

    public static final Integer BIGORSMALL_FIRE_START = 856;

    public static final Integer BIGORSMALL_FIRE_END = 923;

    public static final String BIGORSMALL_FIRE_TYPE = "火";

    public static final Integer BIGORSMALL_SOIL_START = 924;

    public static final Integer BIGORSMALL_SOIL_END = 1410;

    public static final String BIGORSMALL_SOIL_TYPE = "土";

    public static final String PLAYRESULT_DRAGON = "龙";

    public static final String PLAYRESULT_TIGER = "虎";

    // 时时彩
    public static final String SSC_PLAYWAY_NAME_TOTALBIG = "totalBigAndSmall";//总和大小

    public static final String SSC_PLAYWAY_NAME_TOTALDOUBLE = "totalSigleAndDouble";//总和单双

    public static final String SSC_PLAYWAY_NAME_FIVEBIG = "fiveBigAndSmall";//5球大小

    public static final String SSC_PLAYWAY_NAME_FIVEDOUBLE = "fiveSigleAndDouble";//5球单双

    // PK10
    public static final String PKS_PLAYWAY_NAME_KINGBIG = "kingBigAndSmall";//冠亚大小

    public static final String PKS_PLAYWAY_NAME_KINGDOUBLE = "kingSigleAndDouble";//冠亚单双

    public static final String PKS_PLAYWAY_NAME_BIGANDSMALL = "bigAndSmall";//大小

    public static final String PKS_PLAYWAY_NAME_SIGLEANDDOUBLE = "sigleAndDouble";//单双

    public static final String PKS_PLAYWAY_NAME_DRAGONANDTIGLE = "dragonAndTiger";//龙虎

    // 六合彩
    public static final String LHC_PLAYWAY_TMLM_SIGLE = "lhcTmlmSigleAndDouble";//特码两面单双

    public static final String LHC_PLAYWAY_TMLM_BIG = "lhcTmlmBigAndSmall";//特码两面大小

    public static final String LHC_PLAYWAY_ZMTOTAL_SIGLE = "lhcZmtotalSigleAndDouble";//正码总单总双

    public static final String LHC_PLAYWAY_ZMTOTAL_BIG = "lhcZmtotalBigAndSmall";//正码总大总小

    public static final String LHC_PLAYWAY_ZT_SIGLE = "lhcZTSigleAndDouble";//正特单双

    public static final String LHC_PLAYWAY_ZT_BIG = "lhcZTBigAndSmall";//正特大小

    public static final String LHC_PLAYWAY_ZT_ONE = "正1特";//正1特

    public static final String LHC_PLAYWAY_ZT_TWO = "正2特";//正2特

    public static final String LHC_PLAYWAY_ZT_THREE = "正3特";//正3特

    public static final String LHC_PLAYWAY_ZT_FOUR = "正4特";//正4特

    public static final String LHC_PLAYWAY_ZT_FIVE = "正5特";//正5特

    public static final String LHC_PLAYWAY_ZT_SIX = "正6特";//正6特

    /***
     * ==========================================AG BEGIN============================================
     */
    // AG MD5 密钥
    public static final String AG_MD5_KEY = "SFnf6UhBuStj";
    // AG DES 密钥
    public static final String AG_DES_KEY = "6j98DrSW";
    // AIPURL
    public static final String AG_API_URL = "https://gi.apisummer.com/doBusiness.do?params=";
    // -跳转AG地址
    public static final String AG_FORWARD_URL = "https://gci.apisummer.com/forwardGame.do?params=";
    // 1代理编号
    public static final String AG_CAGENT_VALUE = "DX3_AGIN";
    // md5
    public static final String AG_USER_MD5_KEY = "ruanjie2018@apisummer?AgAGd30RJcaipiao";
    /***
     * -接口名
     */
    // lg” 代表 ”检测并创建游戏账号
    public static final String AG_METHOD_LG = "lg";
    // “gb” 代表”查询余额(GetBalance)”
    public static final String AG_METHOD_GB = "gb";
    // “tc” 代表”预备转账 PrepareTransferCredit”
    public static final String AG_METHOD_TC = "tc";
    // “tcc” 代表“转账确认 TransferCreditComfirm”,
    public static final String AG_METHOD_TCC = "tcc";
    // “qos” 代表”查询(QueryOrderStatus)”,
    public static final String AG_METHOD_QOS = "qos";

    /**
     * ======-参数名===============
     */
    /** a代理编号 */
    public static final String AG_API_PARAM_CAGENT = "cagent";
    /** a登入账号 */
    public static final String AG_API_PARAM_LOGINNAME = "loginname";
    /** a登入密码 */
    public static final String AG_API_PARAM_PASSWORD = "password";
    /** a接口名称 */
    public static final String AG_API_PARAM_METHOD = "method";
    /** a账号类型 1真钱-0试玩 */
    public static final String AG_API_PARAM_ACTYPE = "actype";
    /** a币种 */
    public static final String AG_API_PARAM_CUR = "cur";
    /** a账单号 */
    public static final String AG_API_PARAM_BILLNO = "billno";
    public static final String AG_API_PARAM_GAME_TYPE = "gameType";
    public static final String AG_API_PARAM_MH5 = "mh5";
    public static final String AG_API_PARAM_SID = "sid";
    public static final String AG_API_PARAM_LANG = "lang";
    public static final String AG_API_PARAM_ODDTYPE = "oddtype";

    /**
     * 转账类型
     */
    public static final String AG_API_PARAM_TYPE = "type";
    /**
     * 转款额度(如 000.00), 只保留小数点后两个位, 即:100.00
     */
    public static final String AG_API_PARAM_CREDIT = "credit";
    /**
     * 调用预备转账状态 1成功 0失败
     */
    public static final String AG_API_PARAM_FLAG = "flag";
    /**
     * ==========================================AG END============================================
     */

    // ========================================================
    // 重庆时时彩
    public static final int LOTTERY_CQSSC = 1101;
    // 新疆时时彩
    public static final int LOTTERY_XJSSC = 1102;
    // 天津时时彩
    public static final int LOTTERY_TJSSC = 1103;
    // 六合彩
    public static final int LOTTERY_LHC = 1201;
    // 北京PK10
    public static final int LOTTERY_BJPKS = 1301;
    // 幸运飞艇
    public static final int LOTTERY_XYFT = 1401;
    // PCegg蛋蛋
    public static final int LOTTERY_PCEGG = 1501;
    // 大乐透
    public static final int LOTTERY_DLT = 1701;
    // 排列3/5
    public static final int LOTTERY_PL35 = 1702;
    // 海南七星彩
    public static final int LOTTERY_HNQXC = 1703;
    // 双色球
    public static final int LOTTERY_SSQ = 1801;
    // 福彩3D
    public static final int LOTTERY_FC3D = 1802;
    // 七乐彩
    public static final int LOTTERY_QLC = 1803;
    // 一分六合彩
    public static final int LOTTERY_ONELHC = 1202;
    // 德州时时彩
    public static final int LOTTERY_DZSSC = 1106;
    // 德州PKS
    public static final int LOTTERY_DZPKS = 1304;
    // 五分六合彩
    public static final int LOTTERY_FIVELHC = 1203;
    // 五分时时彩
    public static final int LOTTERY_FIVESSC = 1105;
    // 五分PKS
    public static final int LOTTERY_FIVEPKS = 1303;
    // 澳门六合彩
    public static final int LOTTERY_AMLHC = 1204;
    // 十分时时彩
    public static final int LOTTERY_TENSSC = 1104;
    // 十分PKS
    public static final int LOTTERY_TENPKS = 1302;
    // 腾讯分分彩
    public static final int LOTTERY_TXFFC = 1601;
    // 澳洲ACT
    public static final int LOTTERY_AUSACT = 2201;
    // 澳洲时时彩
    public static final int LOTTERY_AUSSSC = 2202;
    // 澳洲PKS
    public static final int LOTTERY_AUSPKS = 2203;
    // 澳洲快三
    public static final int LOTTERY_AZKS = 2301;
    // 德洲快三
    public static final int LOTTERY_DZKS = 2302;
    // 德洲PC蛋蛋
    public static final int LOTTERY_DZPCEGG = 1502;
    // 德洲幸运飞艇
    public static final int LOTTERY_DZXYFT = 1402;
    // 新加坡六合彩
    public static final int LOTTERY_XJPLHC = 1205;

    public static final String STATUS_AUTO = "AUTO";

    public static final String STATUS_WAIT = "WAIT";
    public static final String WAIT = "WAIT";


}
