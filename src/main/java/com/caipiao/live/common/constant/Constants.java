package com.caipiao.live.common.constant;

import com.caipiao.live.common.enums.threeway.ThreeWayTypeNumEnum;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Constants {

    /**
     * 人工存提订单类型: 0-存入
     */
    public static final Integer ART_ORDER_TYPE_DEPOSIT = 0;
    /**
     * 人工存提订单类型: 1-提出
     */
    public static final Integer ART_ORDER_TYPE_WITHDRAW = 1;
    public static final String DEPOSITS_NULL = "-";
    public static String LOCAL_ADDRESS = "";
    public static int THREAD_POOL_CORE_POOL_SIZE = 8;
    public static int THREAD_POOL_MAX_POOL_SIZE = THREAD_POOL_CORE_POOL_SIZE * 10;
    public static int THREAD_POOL_QUEUE_CAPACITY = 2000000;
    public static int THREAD_POOL_AWAIT_TERMINATION_SECONDS = 30;//等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止

    static {
        try {
            LOCAL_ADDRESS = InetAddress.getLocalHost().getHostAddress();
            THREAD_POOL_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 10;
            if (THREAD_POOL_CORE_POOL_SIZE < 32) {
                THREAD_POOL_CORE_POOL_SIZE = 32;
            } else {
                THREAD_POOL_MAX_POOL_SIZE = THREAD_POOL_CORE_POOL_SIZE * 10;
            }
        } catch (Exception e) {
        }
    }


    // 默认编码
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String ANDROID_STRING = "Android";
    public static final String IOS_STRING = "IOS";
    public static final String H5_STRING = "H5";
    public static final String WEB_STRING = "WEB";
    // token过期时间（秒）2周
    public static final Long TOKEN_EXPIRES = 86400 * 14L;

    // app在线监测时间（超过这个时间没有操作则默认为不在线,600秒）10分钟
    public static final Long APP_ONLINE_TIME = 600L;

    public static String HEADER_USER_IP = "userIP";
    public static final String CLIENT_TYPE_STRING = "clientType";
    // 手机型号
    public static final String CLIENT_PHONE_MODEL = "phoneModel";
    public static String ATTR_USER_ID = "live-user-id";
    public static String TALKSERVER_STRING = "talkserver";
    // 网易滑动验证启停标志：1，开启；0，关闭
    public static String FUNCTION_ENABLE_FLAG = "1";
    public static String FUNCTION_DISABLE_FLAG = "0";
    // 功能启停标识：1，开启；0，关闭
    public static String SWIPE_VERIFICATION_SWIFT_OPEN = "0";
    public static String SWIPE_VERIFICATION_SWIFT_CLOSE = "9";
    //银行卡启用状态  0：禁用；1：启用；2：自动启用
    public static Integer BANK_WORK_STATUS_START = 1;
    public static Integer BANK_WORK_STATUS_STOP = 0;
    public static Integer BANK_WORK_STATUS_AUTO = 2;
    //禁止充值
    public static Integer PAYMENT_STATUS_STOP = 0;

    //橘子 异步返回数据的类型，默认1 返回数据为表单数据（Content-Type: application/x-www-form-urlencoded; charset=utf-8），2 返回post json数据。
    public static String PAYMENT_STATUS_ONE = "1";
    public static String PAYMENT_STATUS_TWO = "2";

    /**
     * 毫秒数：一小时
     */
    public static final long MILL_SEC_ONE_HOURS = 60 * 60 * 1000;

    /**
     * 默认页数
     */
    public static final int DEFAULT_PAGE_NO = 1;
    /**
     * 默认页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 财务角色id
     */
    public static final int CAIWU_ACCOUNT_ROLE_ID = 10000006;

    // Aes加密解密key
    public static final String AES_KEY = "ruanjie2018@379gc!834gfg?d30RJcaipiao";
    public static final String SLIDE_VALIDATE_SECRET = "haizuishuai!!!";

    // public static final String HTTP_HOST_IMAGE =
    // "http://47.52.5.170:9001/caipiaoMedia";
    public static final String HTTP_HOST_IMAGE = "http://47.75.199.227:9001/caipiaoMedia";

    // App端参数校验秘钥
    public static final String APP_KEY = "ruanjie2018@jlj34ij34lkj?d30RJcaipiao";
    // 聊天消息推送加密密钥
    public static final String APP_KEY_CHAT_SIGN = "chatsign@notify?CPT!@#99";

    /**
     * 签名验证密钥
     */
    public static final String APP_KEY_SIGN_CHECK = "nishibushisha???cpt321890";

    // 定义存放在session中的用户
    public static final String SESSION_KEY = "user";
    public static final String ACCTOKEN_KEY = "acctoken";
    public static final String FEGIN_SIGN = "Fegin_sign";
    public static final String FEGIN_SIGN_VALUE = "45165bde56864010a3f0cfc8c18bc007";
    public static final String SESSION_KEY_ROLENAME = "user_role_name";

    // 定义shiro中的session是否被管理员强制退出的标识
    public static final String SESSION_KICKOUT_KEY = "force.logout";

    // 验证码有效时间（300秒)
    public static final Long CAPTCHA_VALID_SECONDS = 300L;

    // 验证码一天的最大发送次数
    public static final int CAPTCHA_MAX_COUNT_DAY = 6;

    // 系统返回默认结尾字段
    public static final String DEFAULT_USER = "用户";

    // 游客数据缓存时间(分钟)
    public static final Integer TOURISTS_CACHE_TIME = 3;

    // 长龙赔率缓存时间(分钟)
    public static final Integer DRAGON_ODDS_CACHE_TIME = 10;

    // ========================极光推送的标签================================================
    public static final String KJ_CQSSC = "kj_cqssc"; // 开奖号码推送--重庆时时彩
    public static final String KJ_XJSSC = "kj_xjssc"; // 开奖号码推送--新疆时时彩
    public static final String KJ_TJSSC = "kj_tjssc"; // 开奖号码推送--天津时时彩
    public static final String KJ_TXFFC = "kj_txffc"; // 开奖号码推送--比特币分分彩
    public static final String KJ_XGLHC = "kj_xglhc"; // 开奖号码推送--香港六合彩
    public static final String KJ_PCEGG = "kj_pcegg"; // 开奖号码推送--PC蛋蛋
    public static final String KJ_BJPKS = "kj_bjpks"; // 开奖号码推送--北京PK10
    public static final String KJ_XYFT = "kj_xyft"; // 开奖号码推送--幸运飞艇
    public static final String KJ_XYFT_FT = "kj_xyft_ft"; // 开奖号码推送--幸运飞艇番摊
    public static final String KJ_FIVESSC = "kj_fivessc"; // 开奖号码推送--五分时时彩
    public static final String KJ_TENSSC = "kj_tenssc"; // 开奖号码推送--十分时时彩
    public static final String KJ_JSSSC = "kj_jsssc"; // 开奖号码推送--德州时时彩
    public static final String KJ_JSSSC_FT = "kj_jsssc_ft"; // 开奖号码推送--德州时时彩番摊
    public static final String KJ_FTJSSSC = "kj_ftjsssc"; // 开奖号码推送--德州时时彩
    public static final String KJ_ONELHC = "kj_onelhc"; // 开奖号码推送--一分六合彩
    public static final String KJ_FIVELHC = "kj_fivelhc"; // 开奖号码推送--五分六合彩
    public static final String KJ_SSLHC = "kj_sslhc"; // 开奖号码推送--时时六合彩
    public static final String KJ_TENBJPKS = "kj_tenbjpks"; // 开奖号码推送--十分PK10
    public static final String KJ_FIVEBJPKS = "kj_fivebjpks"; // 开奖号码推送--五分PK10
    public static final String KJ_JSBJPKS = "kj_jsbjpks"; // 开奖号码推送--德州PK10
    public static final String KJ_JSBJPKS_FT = "kj_jsbjpks_ft"; // 开奖号码推送--德州PK10番摊
    public static final String KJ_AUSPKS = "kj_auspks";// 开奖号码推送--澳洲pks
    public static final String KJ_AUSACT = "kj_ausACT";// 开奖号码推送--澳洲ACT

    public static final String KJ_TCPLW = "kj_tcplw";// 开奖号码推送--体彩排列5
    public static final String KJ_TCPLS = "kj_tcpls";// 开奖号码推送--体彩排列3
    public static final String KJ_TCDLT = "kj_tcdlt";// 开奖号码推送--体彩大乐透
    public static final String KJ_TC7XC = "kj_tc7xc";// 开奖号码推送--体彩七星彩
    public static final String KJ_FC3D = "kj_fc3d";// 开奖号码推送--体彩3d福彩
    public static final String KJ_FC7LC = "kj_fc7lc";// 开奖号码推送--体彩7乐彩
    public static final String KJ_FCSSQ = "kj_fcssq";// 开奖号码推送--体彩双色球

    public static final String KJ_XGLHC_RECOMMEND = "kj_xglhc_recommend"; // 开奖号码推荐--香港六合彩

    // ========================极光推送的消息类型================================================
    public static final String MSG_TYPE_WINPUSH = "win_push"; // 中奖消息
    public static final String MSG_TYPE_OPENPUSH = "open_push"; // 开奖消息

    //来源(用于报表统计)
    public static final String SOURCE_IOS = "IOS";
    public static final String SOURCE_ANDROID = "ANDROID";
    public static final String SOURCE_WEB = "WEB";

    public static final String SENSITIVE_WORDS = "SENSITIVE_WORDS_STRING";

    // 默认空值
    public static final String DEFAULT_NULL = "";
    // 默认空值
    public static final String DEFAULT_NICKNAME = "用户";
    // 默认空值
    public static final String DEFAULT_NAME = "";
    // 默认Long
    public static final Long DEFAULT_LONG = 0L;
    // 默认Integer -1
    public static final Integer DEFAULT_NEGATIVE = -1;
    // 默认Integer
    public static final Integer DEFAULT_INTEGER = 0;
    // 点赞次数限制
    public static final Integer ADMIRE_LIMIT = 100;
    // 默认Integer one
    public static final Integer DEFAULT_ONE = 1;
    // 默认Integer TWO
    public static final Integer DEFAULT_TWO = 2;
    // 默认Integer THREE
    public static final Integer DEFAULT_THREE = 3;
    // 默认Integer FOUR
    public static final Integer DEFAULT_FOUR = 4;
    // 默认Integer FIVE
    public static final Integer DEFAULT_FIVE = 5;
    // 默认Integer DEFAULT_INTEGER
    public static final Integer DEFAULT_SIX = 6;
    // 默认Integer SERVER
    public static final Integer DEFAULT_SEVEN = 7;
    // 默认Float
    public static final float DEFAULT_FLOAT = 0;
    //	// 默认pageNum，默认为首页
//	public static final Integer DEFAULT_PAGENUM = 1;
//	// 默认pageSize，默认为10条数据
//	public static final Integer DEFAULT_PAGESIZE = 10;
    // 刷新Redis数据
    public static final Integer DEFAULT_REDIS_PAGESIZE = 10000;
    // 默认pageSize，默认算法查询数量为200条数据
    public static final Integer DEFAULT_ALGORITHM_PAGESIZE = 100;
    // 增加数据连接符号
    public static final String CONNECTIONSYMBOL = ",";
    // 同步用户数据数量
    public static final Integer DEFAULT_SYNONER_USER = 10;

    //用户账户
    public static final String MEMBER_ACCOUNT = "MEMBER_ACCOUNT";

    //排序
    public static final String APP_TYPE = "NORMAL";
    public static final String APP_SORT_TYPE = "DESC";
    public static final String APP_SORT_NAME = "create_time";


    /***
     * ==========================================ACT============================================
     */
    public static final Integer DEFAULT_BIGORSMALL_MEDIAN = 810;// 开奖号码计算大小,中间值

    public static final String BIGORSMALL_BIG = "大";

    public static final String BIGORSMALL_BIG_NUMBER = "大单";

    public static final String BIGORSMALL_BIG_DOUBLE = "大双";

    public static final String TAIL_BIGORSMALL_BIG = "尾大";

    public static final String TOTAL_BIGORSMALL_BIG = "总和大";

    public static final String CROWN_BIGORSMALL_BIG = "冠亚大";

    public static final String ZONG_BIGORSMALL_BIG = "总大";

    public static final String BIGORSMALL_SMALL = "小";

    public static final String BIGORSMALL_SMALL_NUMBER = "小单";

    public static final String BIGORSMALL_SMALL_DOUBLE = "小双";

    public static final String TAIL_BIGORSMALL_SMALL = "尾小";

    public static final String TOTAL_BIGORSMALL_SMALL = "总和小";

    public static final String CROWN_BIGORSMALL_SMALL = "冠亚小";

    public static final String ZONG_BIGORSMALL_SMALL = "总小";

    public static final String BIGORSMALL_SAME = "和";

    public static final String BIGORSMALL_ODD_NUMBER = "单";// 奇数

    public static final String TOTAL_BIGORSMALL_ODD_NUMBER = "总和单";// 奇数

    public static final String CROWN_BIGORSMALL_ODD_NUMBER = "冠亚单";// 奇数

    public static final String ZONG_BIGORSMALL_ODD_NUMBER = "总单";// 奇数

    public static final String JOIN_BIGORSMALL_ODD_NUMBER = "合单";// 奇数

    public static final String BIGORSMALL_EVEN_NUMBER = "双";// 偶数

    public static final String TOTAL_BIGORSMALL_EVEN_NUMBER = "总和双";// 偶数

    public static final String CROWN_BIGORSMALL_EVEN_NUMBER = "冠亚双";// 偶数

    public static final String ZONG_BIGORSMALL_EVEN_NUMBER = "总双";// 偶数

    public static final String JOIN_BIGORSMALL_EVEN_NUMBER = "合双";// 偶数

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

    public static final String BIGORSMALL_POULTRY = "家禽";

    public static final String BIGORSMALL_POULTRY_SHORT = "禽";

    public static final String BIGORSMALL_BEAST = "野兽";

    public static final String BIGORSMALL_BEAST_SHORT = "兽";

    public static final String BIGORSMALL_RED = "红波";

    public static final String BIGORSMALL_GREEN = "绿波";

    public static final String BIGORSMALL_BLUE = "蓝波";

    /***************************** 六合彩号码 **************************************/
    public static final String BIGORSMALL_ONE = "01";
    public static final String BIGORSMALL_TWO = "02";
    public static final String BIGORSMALL_THREE = "03";
    public static final String BIGORSMALL_FOUR = "04";
    public static final String BIGORSMALL_FIVE = "05";
    public static final String BIGORSMALL_SIX = "06";
    public static final String BIGORSMALL_SEVEN = "07";
    public static final String BIGORSMALL_EIGHT = "08";
    public static final String BIGORSMALL_NINE = "09";
    public static final String BIGORSMALL_TEN = "10";
    public static final String BIGORSMALL_ELEVEN = "11";
    public static final String BIGORSMALL_TWELVE = "12";
    public static final String BIGORSMALL_THIRTEEN = "13";
    public static final String BIGORSMALL_FOURTEEN = "14";
    public static final String BIGORSMALL_FIFTEEN = "15";
    public static final String BIGORSMALL_SIXTEEN = "16";
    public static final String BIGORSMALL_SEVENTEEN = "17";
    public static final String BIGORSMALL_EIGHTEEN = "18";
    public static final String BIGORSMALL_NINETEEN = "19";
    public static final String BIGORSMALL_TWENTY = "20";
    public static final String BIGORSMALL_TWENTY_ONE = "21";
    public static final String BIGORSMALL_TWENTY_TWO = "22";
    public static final String BIGORSMALL_TWENTY_THREE = "23";
    public static final String BIGORSMALL_TWENTY_FOUR = "24";
    public static final String BIGORSMALL_TWENTY_FIVE = "25";
    public static final String BIGORSMALL_TWENTY_SIX = "26";
    public static final String BIGORSMALL_TWENTY_SEVEN = "27";
    public static final String BIGORSMALL_TWENTY_EIGHT = "28";
    public static final String BIGORSMALL_TWENTY_NINE = "29";
    public static final String BIGORSMALL_THIRTY = "30";
    public static final String BIGORSMALL_THIRTY_ONE = "31";
    public static final String BIGORSMALL_THIRTY_TWO = "32";
    public static final String BIGORSMALL_THIRTY_THREE = "33";
    public static final String BIGORSMALL_THIRTY_FOUR = "34";
    public static final String BIGORSMALL_THIRTY_FIVE = "35";
    public static final String BIGORSMALL_THIRTY_SIX = "36";
    public static final String BIGORSMALL_THIRTY_SEVEN = "37";
    public static final String BIGORSMALL_THIRTY_EIGHT = "38";
    public static final String BIGORSMALL_THIRTY_NINE = "39";
    public static final String BIGORSMALL_FORTY = "40";
    public static final String BIGORSMALL_FORTY_ONE = "41";
    public static final String BIGORSMALL_FORTY_TWO = "42";
    public static final String BIGORSMALL_FORTY_THREE = "43";
    public static final String BIGORSMALL_FORTY_FOUR = "44";
    public static final String BIGORSMALL_FORTY_FIVE = "45";
    public static final String BIGORSMALL_FORTY_SIX = "46";
    public static final String BIGORSMALL_FORTY_SEVEN = "47";
    public static final String BIGORSMALL_FORTY_EIGHT = "48";
    public static final String BIGORSMALL_FORTY_NINE = "49";
    /**************************************
     * 六合彩生肖
     ***************************************************/
    public static final String LHC_ATTRIBUTE_RAT = "鼠";

    public static final String LHC_ATTRIBUTE_CATTLE = "牛";

    public static final String LHC_ATTRIBUTE_TIGER = "虎";

    public static final String LHC_ATTRIBUTE_RABBIT = "兔";

    public static final String LHC_ATTRIBUTE_DRAGON = "龙";

    public static final String LHC_ATTRIBUTE_SNAKE = "蛇";

    public static final String LHC_ATTRIBUTE_HORSE = "马";

    public static final String LHC_ATTRIBUTE_SHEEP = "羊";

    public static final String LHC_ATTRIBUTE_MONKEY = "猴";

    public static final String LHC_ATTRIBUTE_CHICKEN = "鸡";

    public static final String LHC_ATTRIBUTE_DOG = "狗";

    public static final String LHC_ATTRIBUTE_PIG = "猪";

    // 时时彩
    public static final String SSC_PLAYWAY_NAME_TOTALBIG = "totalBigAndSmall";// 总和大小
    public static final String SSC_PLAYWAY_NAME_TOTALDOUBLE = "totalSigleAndDouble";// 总和单双
    public static final String SSC_PLAYWAY_NAME_FIVEBIG = "fiveBigAndSmall";// 5球大小
    public static final String SSC_PLAYWAY_NAME_FIVEDOUBLE = "fiveSigleAndDouble";// 5球单双

    // PK10
    public static final String PKS_PLAYWAY_NAME_KINGBIG = "kingBigAndSmall";// 冠亚大小
    public static final String PKS_PLAYWAY_NAME_KINGDOUBLE = "kingSigleAndDouble";// 冠亚单双
    public static final String PKS_PLAYWAY_NAME_BIGANDSMALL = "bigAndSmall";// 大小
    public static final String PKS_PLAYWAY_NAME_SIGLEANDDOUBLE = "sigleAndDouble";// 单双
    public static final String PKS_PLAYWAY_NAME_DRAGONANDTIGLE = "dragonAndTiger";// 龙虎

    // 六合彩
    public static final String LHC_PLAYWAY_TMLM_SIGLE = "lhcTmlmSigleAndDouble";// 特码两面单双
    public static final String LHC_PLAYWAY_TMLM_BIG = "lhcTmlmBigAndSmall";// 特码两面大小
    public static final String LHC_PLAYWAY_ZMTOTAL_SIGLE = "lhcZmtotalSigleAndDouble";// 正码总单总双
    public static final String LHC_PLAYWAY_ZMTOTAL_BIG = "lhcZmtotalBigAndSmall";// 正码总大总小
    public static final String LHC_PLAYWAY_ZT_SIGLE = "lhcZTSigleAndDouble";// 正特单双
    public static final String LHC_PLAYWAY_ZT_BIG = "lhcZTBigAndSmall";// 正特大小
    public static final String LHC_PLAYWAY_ZT_ONE = "正1特";// 正1特
    public static final String LHC_PLAYWAY_ZT_TWO = "正2特";// 正2特
    public static final String LHC_PLAYWAY_ZT_THREE = "正3特";// 正3特
    public static final String LHC_PLAYWAY_ZT_FOUR = "正4特";// 正4特
    public static final String LHC_PLAYWAY_ZT_FIVE = "正5特";// 正5特
    public static final String LHC_PLAYWAY_ZT_SIX = "正6特";// 正6特
    public static final Integer LHC_BIGORSMALL_MEDIAN = 25;// 开奖号码计算大小,中间值

    /***
     * ==========================================AG
     * BEGIN============================================
     */
    // AG MD5 密钥
    // public static final String AG_MD5_KEY = "SFnf6UhBuStj";
    // AG DES 密钥
    // public static final String AG_DES_KEY = "6j98DrSW";
    // AIPURL
    // -跳转AG地址
    // 1代理编号
    public static final String AG_CAGENT_VALUE = "FX7_AGIN";
    // md5
    public static final String AG_USER_MD5_KEY = "ruanjie2018@apisummer?AgAGd30RJcaipiao";
    public static String AG_FTP_DEF_DIR = "/AGIN/";
    public static String AG_FTP_LAST_DIR = "/lostAndfound/";
    public static String AG_FTP_YOPLAY_DIR = "/YOPLAY/";
    public static String AG_FTP_HUNTER_DIR = "/HUNTER/";
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
    /**
     * a代理编号
     */
    public static final String AG_API_PARAM_CAGENT = "cagent";
    /**
     * a登入账号
     */
    public static final String AG_API_PARAM_LOGINNAME = "loginname";
    /**
     * a登入密码
     */
    public static final String AG_API_PARAM_PASSWORD = "password";
    /**
     * a接口名称
     */
    public static final String AG_API_PARAM_METHOD = "method";
    /**
     * a账号类型 1真钱-0试玩
     */
    public static final String AG_API_PARAM_ACTYPE = "actype";
    /**
     * a币种
     */
    public static final String AG_API_PARAM_CUR = "cur";
    /**
     * a账单号
     */
    public static final String AG_API_PARAM_BILLNO = "billno";
    public static final String AG_API_PARAM_GAME_TYPE = "gameType";
    public static final String AG_API_PARAM_MH5 = "mh5";
    public static final String AG_API_PARAM_SID = "sid";
    public static final String AG_API_PARAM_LANG = "lang";
    public static final String AG_API_PARAM_ODDTYPE = "oddtype";
    public static final String AG_API_PARAM_DM = "dm";

    /**
     * ======-xml 类型 =============== BR下注记录，EBR电子游戏下注记录，TR户口转账记录，GR游戏结果，LBR彩票下注记录，LGR彩票结果
     */
    public static final String AG_XML_DATA_TYPE_BR = "BR";
    public static final String AG_XML_DATA_TYPE_TR = "TR";
    public static final String AG_XML_DATA_TYPE_GR = "GR";
    public static final String AG_XML_DATA_TYPE_EBR = "EBR";
    public static final String AG_XML_DATA_TYPE_LBR = "LBR";
    public static final String AG_XML_DATA_TYPE_LGR = "LGR";
    public static final String AG_XML_DATA_TYPE_HSR = "HSR";

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
    public static final String AG_IN = "IN";
    public static final String AG_OUT = "OUT";


    public static final String THIRD_GAME_IN = "1";
    public static final String THIRD_GAME_OUT = "0";
    /**
     * ==========================================AG END============================================
     */


    //TOKEN
    public static final String DB_GAME_TOKEN = "11";
    //试玩TOKEN
    public static final String DB_DEMO_GAME_TOKEN = "47";
    //创建
    public static final String DB_GAME_CREATE = "12";
    //查询
    public static final String DB_GAME_QUERY = "15";
    //存取款
    public static final String DB_GAME_MONEY = "19";
    //检查现金转账
    public static final String DB_GAME_QUERY_MONEY = "55";
    //注单查询
    public static final String DB_GAME_QUERY_ORDER = "29";
    //0 真人 1 试玩
    public static final Integer DB_GAME_TYPE = 0;
    public static final Integer DB_GAME_DEMO_TYPE = 1;
    /**
     * 彩种版本控制 LOTTERY_VERSION_ZIP_URL
     */

//	public static final String LOTTERY_VERSION_ZIP_URL = "LOTTERY_VERSION_ZIP_URL";
    public static final String SYSTEM_UPGRADE_UPLOAD_URL = "https://uploadfile.chengykj.com/fileEntry/oss/uploadFileByOriginalName";

    // 第三方游戏账号类型
    public static final String AG_ACCOUNT_TYPE = "T_AG";
    public static final String KY_ACCOUNT_TYPE = "T_KY";
    public static final String ES_ACCOUNT_TYPE = "T_ES";
    public static final String AE_ACCOUNT_TYPE = "T_AE";
    public static final String MG_ACCOUNT_TYPE = "T_MG";
    public static final String DB_ACCOUNT_TYPE = "T_DB";

    public static final String AE_EXIT_KEY = "AE_KEY_";
    public static final String KY_EXIT_KEY = "KY_KEY_";
    public static final String AG_EXIT_KEY = "AG_KEY_";
    public static final String ES_EXIT_KEY = "ES_KEY_";
    public static final String MG_EXIT_KEY = "MG_KEY_";
    public static final String DB_EXIT_KEY = "DB_KEY_";

    public static final String AE_INIT_KEY = "AE_INIT_";
    public static final String KY_INIT_KEY = "KY_INIT_";
    public static final String AG_INIT_KEY = "AG_INIT_";
    public static final String ES_INIT_KEY = "ES_INIT_";
    public static final String MG_INIT_KEY = "MG_INIT_";
    public static final String DB_INIT_KEY = "DB_INIT_";

    //MG 语言
    public static final String MG_LANGUAGE = "zh-CN";
    //MG CODE
    public static final String MG_WALLET_CODE = "gf_mgplus_wallet";

    public static final String MG_VENDOR_CODE = "MGPLUS";
    //MG 默认时间戳位
    public static final String MG_TIMESTAMP_DIGIT = "13";
    //MG 拉取注单数
    public static final String MG_COUNT = "5000";

    public static final String MG_INIT_TIME_KEY = "MG_INIT_TIME_";
    public static final String MG_EXIT_TIME_KEY = "MG_EXIT_TIME_";
    public static final String DB_EXIT_TIME_KEY = "DB_EXIT_TIME_";
    public static final String DB_INIT_TIME_KEY = "DB_INIT_TIME_";
    /**
     * 随机码状态状态（0：未使用；1：已使用；2：使用中，一般为预加载到redis中）即程序只能使用状态为0的随机码，且状态为2的使用完后要更新为1
     */
    public static final int SYSTEM_RANDOM_CODE_STATUS_UN_USED = 0;
    public static final int SYSTEM_RANDOM_CODE_STATUS_USED = 1;
    public static final int SYSTEM_RANDOM_CODE_STATUS_IN_USEING = 2;

    /**
     * 随机码默认生成条数：10万；最大生成条数：100万；加载条数：500;批量插入条数：5000
     */
    public static final int SYSTEM_RANDOM_CODE_GEN_DEFAULT_SIZE = 10 * 10000;
    public static final int SYSTEM_RANDOM_CODE_GEN_MAX_SIZE = 100 * 10000;
    public static final int SYSTEM_RANDOM_CODE_LOAD_DEFAULT_SIZE = 1000;
    public static final int SYSTEM_RANDOM_CODE_BATCH_INSERT_SIZE = 5000;
    public static final int SYSTEM_RANDOM_CODE_BATCH_UPDATE_SIZE = 500;
    public static final float SYSTEM_RANDOM_CODE_REGEN_FACTORY = 0.75F;
    /**
     * 生成随机码时间
     */
    public static final long REDISSON_LOCK_GEN_RANDOM_CODE_LEASE_TIME = 60 * 5L;
    /**
     * 推广随机码标志字符
     */
    public static final String SYSTEM_RANDOM_CODE_PROMOTION = "PROMOTION";
    public static final String LHC_KAIJIANG_STATUS = "LHC_KAIJIANG_STATUS";

    /**
     * 超过最大银行卡绑定数
     */
    public static final String BANK_CARD_BUNDING_OUT_OF_MAX = "BANK_CARD_BUNDING_OUT_OF_MAX";
    public static final String BANK_CARD_BUNDING_RE_BUNDING = "BANK_CARD_BUNDING_RE_BUNDING";

    /**
     * 银行是否能绑卡，0-禁用，1-启用
     */
    public static final int BANK_CARD_BINGDING_PROHABIT = 0;
    public static final int BANK_CARD_BINGDING_INUSE = 1;

    /**
     * 默认连接符
     */
    public static final String UNDERLINE_CONNECTOR = "_";

    /**
     * 默认统计期数: 100; 最大统计期数:1000
     */
    public static final int DEFAULT_STAT_ISSUES = 100;
    public static final int MAX_STAT_ISSUES = 1000;

    /* 订单状态 */
    public static final String ORDER_WAIT = "WAIT";
    /* 同时结算订单数 */
    public static final Integer CLEARNUM = 1000;
    // 心水数据放入ES每次数据量
    public static final Integer ONETIMEDATANUM = 1000;
    // 心水数据放入ES中Type
    public static final String esType = "recommend";
    /**
     * 默认快捷支付额度
     */
    public static final String DEFAULT_QUICK_MONEYS = "100,200,300,500,1000";

    public static final String CLIENT_SOURCE_ANDROID = "Android";
    public static final String CLIENT_SOURCE_IOS = "iOS";
    public static final String CLIENT_SOURCE_H5 = "H5";
    public static final String CLIENT_SOURCE_WEB = "web";

    // 六和大神类型集合缓存时间
    public static final Integer LHC_GOD_TYPE_TIME = 3;

    // 数据等级集合缓存时间
    public static final Integer DATA_LEVEL_TIME = 3;

    // 无效的贴子数据
    public static final Set<String> INVALIDARRAY = new HashSet<>();

    // ip注册限制
    public static final String IP_ENROLL = "IP_ENROLL";

    static {
        INVALIDARRAY.add("&nbsp");
    }

    // 用户数据缓存时间(秒)
    public static final Integer CUSTOMER_CACHE_XS_TIME = 10;

    // 游客数据缓存时间(秒)
    public static final Integer TOURISTS_CACHE_XS_TIME = 60;

    // 投票缓存时间
    public static final Integer ADD_VOTENS_NUM_TIME = 8;

    // 提现说明
//	public static final String WITHDRAW_DEPOSIT_EXPLAIN = "WITHDRAW_DEPOSIT_EXPLAIN";

    // 支付回调成功
    public static final String PAY_CALLBACK_SUSSESS = "success";
    public static final String PAY_CALLBACK_OK = "ok";
    public static final String PAY_CALLBACK_BIG_OK = "OK";
    public static final String PAY_CALLBACK_BIG_SUCCESS = "SUCCESS";
    public static final String PAY_CALLBACK_STATUS = "000000";
    // 支付回调失败
    public static final String PAY_CALLBACK_FAIL = "fail";

    // 业务要求每个用户发帖子间隔时间为8分钟
    public static final int REQUEST_TIMEOUT_MILLIS = 480000;

    // 广大
    public static final String PAY_PLATFORM_THOUSAND_ONE = "10001";
    // 天网
    public static final String PAY_PLATFORM_THOUSAND_TWO = "10002";
    // 金发
    public static final String PAY_PLATFORM_THOUSAND_THREE = "10003";
    // 隆发
    public static final String PAY_PLATFORM_THOUSAND_FOUR = "10004";
    // 隆润
    public static final String PAY_PLATFORM_THOUSAND_FIVE = "10005";
    // 虎鲸
    public static final String PAY_PLATFORM_THOUSAND_SIX = "10006";
    // 捷易通
    public static final String PAY_PLATFORM_THOUSAND_SEVEN = "10007";
    // 众城
    public static final String PAY_PLATFORM_THOUSAND_EIGHT = "10008";
    // 松鼠
    public static final String PAY_PLATFORM_THOUSAND_NINE = "10009";
    // 全球付
    public static final String PAY_PLATFORM_THOUSAND_TEN = "10010";
    // 诚意通
    public static final String PAY_PLATFORM_THOUSAND_ELEVEN = "10011";
    // AZ
    public static final String PAY_PLATFORM_THOUSAND_TWELVE = "10012";
    // 腾飞
    public static final String PAY_PLATFORM_THOUSAND_THIRTEEN = "10013";
    // 诚意
    public static final String PAY_PLATFORM_THOUSAND_FOURTEEN = "10014";
    // 天行者
    public static final String PAY_PLATFORM_THOUSAND_FIFTEEN = "10015";
    // 777支付
    public static final String PAY_PLATFORM_THOUSAND_SIXTEEN = "10016";
    // 熊猫
    public static final String PAY_PLATFORM_THOUSAND_SEVENTEEN = "10017";
    // 中新
    public static final String PAY_PLATFORM_THOUSAND_ZX = "10020";
    // 聚财厅
    public static final String PAY_PLATFORM_THOUSAND_EIGHTTEEN = "10018";
    // 富豪
    public static final String PAY_PLATFORM_TWENTY_ONE = "10021";
    // truts
    public static final String PAY_PLATFORM_TWENTY_TWO = "10022";
    // CK
    public static final String PAY_PLATFORM_TWENTY_THREE = "10023";
    // ff
    public static final String PAY_PLATFORM_TWENTY_FOUR = "10024";
    //宝德
    public static final String PAY_PLATFORM_TWENTY_FIVE = "10025";
    //超凡
    public static final String PAY_PLATFORM_TWENTY_SIX = "10026";
    //信达
    public static final String PAY_PLATFORM_TWENTY_SEVENTEEN = "10027";
    //喜付
    public static final String PAY_PLATFORM_TWENTY_EIGHTTEEN = "10028";
    //亿咖
    public static final String PAY_PLATFORM_TWENTY_NINE = "10029";
    //创世
    public static final String PAY_PLATFORM_THIRTY = "10030";
    //FY
    public static final String PAY_PLATFORM_THIRTY_ONE = "10031";
    //钉钉
    public static final String PAY_PLATFORM_THIRTY_TWO = "10032";
    //橘子
    public static final String PAY_PLATFORM_THIRTY_THREE = "10033";
    //渔夫
    public static final String PAY_PLATFORM_THIRTY_FOUR = "10034";
    //epay
    public static final String PAY_PLATFORM_THIRTY_FIVE = "10035";
    //路路通
    public static final String PAY_PLATFORM_THIRTY_SIX = "10036";
    //谷歌金服
    public static final String PAY_PLATFORM_THIRTY_SEVEN = "10037";
    //ESPAY
    public static final String PAY_PLATFORM_THIRTY_EIGHT = "10038";
    //33aa
    public static final String PAY_PLATFORM_THIRTY_NINE = "10039";
    //亿盛
    public static final String PAY_PLATFORM_FORTY = "10040";
    //四方
    public static final String PAY_PLATFORM_FORTY_ONE = "10041";
    //99支付
    public static final String PAY_PLATFORM_FORTY_TWO = "10042";
    //灿星支付
    public static final String PAY_PLATFORM_FORTY_THREE = "10043";
    //King支付
    public static final String PAY_PLATFORM_FORTY_FOUR = "10044";
    //ace
    public static final String PAY_PLATFORM_FORTY_FIVE = "10045";
    //新乐富
    public static final String PAY_PLATFORM_FORTY_SIX = "10046";
    //爱支付
    public static final String PAY_PLATFORM_FORTY_SEVEN = "10047";
    //AMK
    public static final String PAY_PLATFORM_FORTY_EIGHT = "10048";
    //默默支付
    public static final String PAY_PLATFORM_FORTY_NINE = "10049";
    //支付service 统一后缀
    public static final String PAY_PLATFORM_SERVER_SUFFIX = "PayService";
    //支付回调对象 后缀
    public static final String PAY_PLATFORM_NOTIFY_MODLE_SUFFIX = "Notify";
    //支付回调service 后缀
    public static final String PAY_PLATFORM_NOTIFY_SERVER_SUFFIX = "PayNotifyService";


    // 富豪扫码
    public static final String PAY_FH_SM = "sm";
    // 富豪唤醒
    public static final String PAY_FH_H5 = "h5";
    // srust 1 json
    public static final String PAY_SRUST_ONE = "01";
    // srust 2跳转
    public static final String PAY_SRUST_TWO = "02";
    // 支付返回链接
    public static final Integer PAY_RETURN_CODE_ZERO = 0;
    // 支付返回HTML
    public static final Integer PAY_RETURN_CODE_ONE = 1;
    // 支付返回链接生成二维码
    public static final Integer PAY_RETURN_CODE_TWO = 2;
    // 聚财厅
    public static final String PAY_PLATFORM_THOUSAND_EIGHTEEN = "10018";
    // 我来付
    public static final String PAY_PLATFORM_THOUSAND_NINETEEN = "10019";

    /**
     * 默认银行卡收款账户最小值
     */
    public static final int DEFALT_MIN_RECHARGE_ACCOUNT_AMOUNT = 100;

    /**
     * 最小卡号长度
     */
    public static final int CARD_MIN_LENGTH = 6;

    // 客户端会有发送 undefined
    public static final String UNDEFINED = "undefined";

    // *******************************用户管理操作*****************************
    public static final String UPDATEFREEZESTATUS = "/appMember/updateFreezeStatus.json";

    public static final String UPDATEAM = "/appMember/updateam.json";

    // *******************************心水推荐管理操作*****************************
    public static final String DELETEXS = "/lhcXs/deleteXs.json";

    public static final String MATCHDELETEXS = "/lhcXs/matchDeleteXs.json";

    public static final String FJXS = "/lhcXs/fjXs.json";

    public static final String MATCHXZXS = "/lhcXs/matchXzxs.json";

    // *******************************心水推荐评论管理操作*****************************

    public static final String MATCHDELETEXSPL = "/lhcXspl/matchDeleteXs.json";

    public static final String MATCHXZXSPL = "/lhcXspl/matchXzxs.json";

    public static final String DELETEXSPL = "/lhcXspl/delete.json";

    // *******************************批量心水推荐管理操作*****************************

    public static final String MATCHDELETEXSBBS = "/xsBbs/matchDeleteXs.json";

    public static final String MATCHXZXSBBS = "/xsBbs/matchXzxs.json";

    // *******************************批量心水推荐评论管理操作*****************************

    public static final String MATCHDELETEXSCOMD = "/xsCommend/matchDeleteXs.json";

    public static final String MATCHXZXSXSCOMD = "/xsCommend/matchXzxs.json";

    public static final String MATCHJFXSXSCOMD = "/xsCommend/matchJfxs.json";

    /* 特码玩法- 特码 */
    public static final int LHC_PLAY_TM = 120101;

    /* 特码玩法- 特码两面 */
    public static final int LHC_PLAY_TMLM = 120102;

    /* 特码玩法- 正码 */
    public static final int LHC_PLAY_ZM = 120103;

    /* 特码玩法- 正码1-6 */
    public static final int LHC_PLAY_ZM16 = 120104;

    /* 特码玩法- 正1特 */
    public static final int LHC_PLAY_Z1T = 120105;

    /* 特码玩法- 正2特 */
    public static final int LHC_PLAY_Z2T = 120106;

    /* 特码玩法- 正3特 */
    public static final int LHC_PLAY_Z3T = 120107;

    /* 特码玩法- 正4特 */
    public static final int LHC_PLAY_Z4T = 120108;

    /* 特码玩法- 正5特 */
    public static final int LHC_PLAY_Z5T = 120109;

    /* 特码玩法- 正6特 */
    public static final int LHC_PLAY_Z6T = 120110;

    /* 特码玩法- 三中二 */
    public static final int LHC_PLAY_3Z2 = 120111;

    /* 特码玩法- 二中特 */
    public static final int LHC_PLAY_2ZT = 120112;

    /* 特码玩法- 特串 */
    public static final int LHC_PLAY_TC = 120113;

    /* 特码玩法- 二全中 */
    public static final int LHC_PLAY_EQZ = 120114;

    /* 特码玩法- 三全中 */
    public static final int LHC_PLAY_3QZ = 120115;

    /* 特码玩法- 红波 */
    public static final int LHC_PLAY_RED = 120116;

    /* 特码玩法- 蓝波 */
    public static final int LHC_PLAY_BLUE = 120117;

    /* 特码玩法- 绿波 */
    public static final int LHC_PLAY_GREEN = 120118;

    /* 特码玩法- 全尾 */
    public static final int LHC_PLAY_ALLTAIL = 120119;

    /* 特码玩法- 特尾 */
    public static final int LHC_PLAY_TETAIL = 120120;

    /* 特码玩法- 五不中 */
    public static final int LHC_PLAY_5BZ = 120121;

    /* 特码玩法- 六不中 */
    public static final int LHC_PLAY_6BZ = 120122;

    /* 特码玩法- 七不中 */
    public static final int LHC_PLAY_7BZ = 120123;

    /* 特码玩法- 八不中 */
    public static final int LHC_PLAY_8BZ = 120124;

    /* 特码玩法- 九不中 */
    public static final int LHC_PLAY_9BZ = 120125;

    /* 特码玩法- 十不中 */
    public static final int LHC_PLAY_10BZ = 120126;

    /* 特码玩法- 平特 */
    public static final int LHC_PLAY_PT = 120127;

    /* 特码玩法- 6连肖中 */
    public static final int LHC_PLAY_6XLZ = 120129;

    /* 特码玩法- 6连肖不中 */
    public static final int LHC_PLAY_6XLBZ = 120130;

    /* 特码玩法- 2连肖中 */
    public static final int LHC_PLAY_2LXZ = 120131;

    /* 特码玩法- 2连肖不中 */
    public static final int LHC_PLAY_2LXBZ = 120132;

    /* 特码玩法- 3连肖中 */
    public static final int LHC_PLAY_3LXZ = 120133;

    /* 特码玩法- 3连肖不中 */
    public static final int LHC_PLAY_3LXBZ = 120134;

    /* 特码玩法- 4连肖中 */
    public static final int LHC_PLAY_4LXZ = 120135;

    /* 特码玩法- 4连肖不中 */
    public static final int LHC_PLAY_4LXBZ = 120136;

    /* 特码玩法- 特肖 */
    public static final int LHC_PLAY_TX = 120128;

    /* 特码玩法- 二连尾中 */
    public static final int LHC_PLAY_2LWZ = 120137;

    /* 特码玩法- 二连尾不中 */
    public static final int LHC_PLAY_2LWBZ = 120138;

    /* 特码玩法- 三连尾中 */
    public static final int LHC_PLAY_3LWZ = 120139;

    /* 特码玩法- 三连尾不中 */
    public static final int LHC_PLAY_3LWBZ = 120140;

    /* 特码玩法- 四连尾中 */
    public static final int LHC_PLAY_4LWZ = 120141;

    /* 特码玩法- 四连尾不中 */
    public static final int LHC_PLAY_4LWBZ = 120142;

    /* 特码玩法- 1-6龙虎 */
    public static final int LHC_PLAY_16LH = 120143;

    /* 特码玩法- 五行 */
    public static final int LHC_PLAY_WX = 120144;

    /* 福彩3D玩法- 2D */
    public static final int FC3D_PLAY_2D = 180206;

    /* 海南七星彩玩法- 不定位2 */
    public static final int QXC_PLAY_BDW2 = 170302;

    /* 海南七星彩玩法- 不定位3 */
    public static final int QXC_PLAY_BDW3 = 170303;

    /* 福彩3D玩法- 2D */
    public static final int FC3D_PLAY_2D_2BTH = 180208;

    /* 福彩3D玩法- 1D */
    public static final int FC3D_PLAY_1D = 180204;

    /* 福彩3D玩法- 猜1D */
    public static final int FC3D_PLAY_C1D = 180205;

    /* 福彩3D玩法- 两面 */
    public static final int FC3D_PLAY_LM = 180210;

    /* 福彩3D玩法- 和数 */
    public static final int FC3D_PLAY_HE = 180209;

    /* 海南七星彩玩法- 两面 */
    public static final int QXC_PLAY_LM = 170304;

    /* 海南七星彩玩法- 定位胆 */
    public static final int QXC_PLAY_DWD = 170305;

    /* 排列3/5 - 两面 */
    public static final int PL35_PLAY_LM = 170203;

    /* 排列3/5 - 定位胆 */
    public static final int PL35_PLAY_DWD = 170204;

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
    // 时时六合彩
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

    // ================================== 下三路位置
    public static final Integer THREE_WAY_PART_ID = 0; // 数据唯一
    public static final String THREE_WAY_PART_A = "partA"; // A位
    public static final String THREE_WAY_PART_B = "partB"; // B位
    public static final String THREE_WAY_PART_C = "partC"; // C位
    public static final String THREE_WAY_PART_D = "partD"; // C位

    // ================================= 下三路主类型名称
    public static final String THREE_WAY_MAIN = "WAY_MAIN_";// 大路
    public static final String THREE_WAY_LARGE = "WAY_LARGE_";// 大眼仔
    public static final String THREE_WAY_SMALL = "WAY_SMALL";// 小路
    public static final String THREE_WAY_STRONG = "WAY_STRONG";// 小强路

    // ================================= 下三路层级名称
    public static final String THREE_DS_LEVEL = "DS";
    public static final String THREE_DX_LEVEL = "DX";

    // ================================= 下三路单双层级
    public static final Set<Integer> THREE_DS_GROUP = new HashSet<Integer>() {
        {
            add(ThreeWayTypeNumEnum.SPECIAL_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_ONE_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_TWO_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_THREE_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_FOUR_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_FIVE_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_SIX_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.SPECIAL_TOTAL_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_ONE_TOTAL_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_TWO_TOTAL_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_THREE_TOTAL_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_FOUR_TOTAL_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_FIVE_TOTAL_SINGLE_DOUBLE.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_SIX_TOTAL_SINGLE_DOUBLE.getValue());
        }
    };

    // ================================= 下三路大小层级
    public static final Set<Integer> THREE_DX_GROUP = new HashSet<Integer>() {
        {
            add(ThreeWayTypeNumEnum.SPECIAL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_TOTAL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_ONE_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_TWO_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_THREE_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_FOUR_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_FIVE_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_SIX_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.SPECIAL_TAIL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_ONE_TAIL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_TWO_TAIL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_THREE_TAIL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_FOUR_TAIL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_FIVE_TAIL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_SIX_TAIL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_TAIL_SMALL_BIG.getValue());
            add(ThreeWayTypeNumEnum.POSITIVE_DRAGON_TIGER.getValue());
            add(ThreeWayTypeNumEnum.SPECIAL_ANIMAL.getValue());
        }
    };

    // ===============================中新支付============================
    public final static String PAY_STATUS_PAYED = "PAYED";
    public final static String PAY_RETURN_SUC = "SUC";
    public final static String PAY_RETURN_FAIL = "FAIL";
    // ===============================超凡支付============================
    public final static String PAY_RETURN_TRUE = "true";
    // ===============================喜付支付============================
    public final static String PAY_RETURN_STATUS = "{\"code\":\"SUCCESS\",\"msg\":\"message\"}";
    // ===============================创世支付============================
    public final static String PAY_STATUS_ONE = "1"; //成功
    public final static String PAY_RETURN_TWO = "2"; //失败

    public final static String PAY_ACE_RETURN_TWO = "2"; //成功

    public final static String PAY_PRAMS_STATUS_ONE = "200"; //成功
    // =============================代理===========================
    public final static String DAY_END_TIME = " 23:59:59";
    public final static String DAY_START_TIME = " 00:00:00";
    public final static BigDecimal ZERO = new BigDecimal("0");
    public final static String OFF = "off";
    public final static String ON = "on";

    public final static String SGSIGN = "sgSign";

    public final static String PAY_RETURN_CX = "00"; //成功
    // ========================================= 聊天室
    public final static String NO_CODE = "NO_CODE"; // 此code标识直接跳到对应的 id 聊天室
    public final static String CHAT_LHC = "CHAT_LHC"; // 六合彩系列聊天室
    public final static String CHAT_AUSTRALIA = "CHAT_AUSTRALIA"; // 澳洲系列聊天室
    public final static String CHAT_SSC = "CHAT_SSC"; // 时时彩系列聊天室
    public final static String CHAT_PK10 = "CHAT_PK10"; // PK10系列聊天室
    public final static String CHAT_XYFT = "CHAT_XYFT"; // 幸运飞艇系列聊天室
    public final static String CHAT_TANFAN = "CHAT_TANFAN"; // 番摊系列聊天室
    public final static String CHAT_NIUNIU = "CHAT_NIUNIU"; // 牛牛系列聊天室
    public final static String CHAT_PCEGG = "CHAT_PCEGG"; // PC蛋蛋系列聊天室
    public final static String CHAT_FFC = "CHAT_FFC"; // 分分彩系列聊天室
    public final static String CHAT_TICAI = "CHAT_TICAI"; // 体彩系列聊天室
    public final static String CHAT_FUCAI = "CHAT_FUCAI"; // 福彩系列聊天室
    public final static String CHAT_KS = "CHAT_KS"; // 快三系列聊天室
    public final static String CHAT_USER_LEVEL = "CHAT_USER_LEVEL";
    public final static String CHAT_USER = "CHAT_USER";
    public final static String CHAT_ROOM_ID = "CHAT_ROOM_ID";
    public final static String CHAT_ROOM_TOPIC = "CHAT_ROOM_TOPIC_";

    // 默认
    public static final List<Integer> playDefultList = Lists.newArrayList();

    // 六合彩系列
    public static final List<Integer> playLhcList = Lists.newArrayList(1201, 1202, 1203, 1204, 1205);

    // 澳洲系列
    public static final List<Integer> playACTList = Lists.newArrayList(2201, 2202, 2203);

    // 时时彩系
    public static final List<Integer> playSSCList = Lists.newArrayList(1101, 1102, 1103, 1104, 1105, 1106);

    // PK10系列
    public static final List<Integer> playPKSList = Lists.newArrayList(1301, 1302, 1303, 1304);

    // 幸运飞艇系列
    public static final List<Integer> playXYFTList = Lists.newArrayList(1401, 1402);

    // 番摊系列
    public static final List<Integer> playFTList = Lists.newArrayList(2001, 2002, 2003);

    // 牛牛系列
    public static final List<Integer> playNNList = Lists.newArrayList(1901, 1902, 1903);

    // PC蛋蛋系列
    public static final List<Integer> playPCGGList = Lists.newArrayList(1501, 1502);

    // 分分彩系列
    public static final List<Integer> playFFCList = Lists.newArrayList(1601);

    // 体彩系列
    public static final List<Integer> playTCList = Lists.newArrayList(1701, 1702, 1703);

    // 福彩系列
    public static final List<Integer> playFCList = Lists.newArrayList(1801, 1802, 1803);

    // 快三系列
    public static final List<Integer> playKSList = Lists.newArrayList(2301, 2302);

    // 新彩种系列2301:澳洲快三, 2302德州快三, 1402德州幸运飞艇, 1502德州pc蛋蛋,1205新加坡六合彩
    public static final List<Integer> NEW_LOTTERY_ID_LIST = Lists.newArrayList(2301, 2302, 1402, 1502, 1205);
    // 新彩种胆拖玩法的playid
    public static final List<Integer> NEW_LOTTERY_DT_PLAYID_LIST = Lists.newArrayList(230105, 230109, 230205, 230209);

    public static final List<Integer> NEW_JIESUAN_LOTTERY_ID_LIST = Lists.newArrayList(2301, 2302); //新彩种 新结算算法 （因为1402,1502,1205 沿用老的结算方法）

    public static final List<Integer> NEW_OLDJIESUAN_LOTTERY_ID_LIST = Lists.newArrayList(1402, 1502, 1205); //新彩种 旧结算算法 （因为1402,1502,1205 沿用老的结算方法）
    /**
     * 中奖
     */
    public static final String WIN = "WIN";
    /**
     * 未中奖
     */
    public static final String NO_WIN = "NO_WIN";
    /**
     * 打和
     */
    public static final String HE = "HE";
    /**
     * 等待开奖
     */
    public static final String WAIT = "WAIT";
    /**
     * 撤单
     */
    public static final String BACK = "BACK";

    /**
     * 已经开奖状态
     */
    public static final String AUTO = "AUTO";

    // 聊天返回成功码
    public static final String RETURN_KEY = "200";

    // 下三路初始数据A路值
    public static final List<Integer> threeInitialList = Lists.newArrayList(1, 2, 3, 4, 5);

    //彩种类别表
    public static final String LOTTERY_CATEGORY_TYPE_LOTTERY = "LOTTERY";

    /*************************代付*******************************************************/
    //虎鲸代付
    public static final String PAY_FOR_ONE = "1001";
    //AE代付
    public static final String PAY_FOR_TWO = "1002";
    //鸿运代付
    public static final String PAY_FOR_THREE = "1003";
    //AE 默认方式
    public static final String PAY_FOR_TWO_TYPE = "DF_ZFBYHK";

    public static final String STATUS_AUTO = "AUTO";

    public static final String STATUS_WAIT = "WAIT";

    public static final String AE_SF_ORDER_SUFFIX = "_SF_AE";
    public static final String AE_XF_ORDER_SUFFIX = "_XF_AE";
    public static final String AE_GAME_URL = "AE_GAME_URL_";

    public static final String AE_GAME_OPERATE_FLAG = "AE_GAME_OPERATE_FLAG_";
    public static final String GAME_MG_SF = "MG_SF_";
    public static final String GAME_MG_XF = "MG_XF_";

    public static final String GAME_DB_SF = "DB_SF_";
    public static final String GAME_DB_XF = "DB_XF_";

    //操作类型
    public static final Integer EXTERNAL_TYPE = 110;

    /**
     * AE请求超时
     */
    public static final int AE_TIMEOUT_MSECS = 60000 * 10;

    /**
     * AE异常最大重试次数
     */
    public static int AE_MAX_RETRY_COUNT = 3;

    /**
     * 基本字符常量
     */
    public static final String STR_ZERO = "0";
    public static final String STR_EMPTY = "";
    public static final String STR_PER = "%";
    public static final String STR_UNDERLINE = "_";
    public static final String STR_AT = "@";
    public static final String STRING_ONE = "1";
    public static final String STR_NULL = "null";
    public static final String STR_COLON = ":";
    public static final String STR_POINT = ".";

    public static int PLANTYPE_INDEX_ONE = 1;   //某期计划第1条数据
    public static int PLANTYPE_INDEX_SECOND = 2;  //某期计划第2条数据
    public static int PLANTYPE_INDEX_THREE = 3;   //某期计划第3条数据

    public static int GODPLAN_STATUS_YUTUI = 0;   //预推
    public static int GODPLAN_STATUS_WAIT = 1;  //待开奖
    public static int GODPLAN_STATUS_HASKJ = 2;   //已开奖

    public static int GODPLAN_STATUS_OPEN = 1;   //大神推荐开启
    public static int GODPLAN_STATUS_CLOSE = 0;  //大神推荐关闭

    /**
     * 系统信息值，缓存后缀
     */
    public static final String SYSTEM_INFO_VALUE_SUFFIX = "_INFO";
    // 签到循坏
    public static final Integer SIGNCYCLE = 7;
    public static final Integer SIGNCYCLE_GOLDNUM = 5;
    public static final Integer SIGNCYCLE_GOLDNUM_MAXDAY = 40;

    // bdaction地址
    public static String bdaction_url;
    public static final String OPENID = "openid";
    // 订单AES 加密字符串
    public static final String ORDERAES = "PPAYORDER_10000";
    public static final String WX_LOGIN_INFO = "WX_LOGIN_INFO";// 微信用户端登录
    public static final String APP_LOGIN_INFO = "APP_LOGIN_INFO";// APP用户端登录
    public static final String ADMIN_LOGIN_INFO = "ADMIN_LOGIN_INFO";// 机构后台登录
    public static final String WEB_LOGIN_INFO = "WEB_LOGIN_INFO";// web登录
    public static final String ADMIN_VERCODE = "ADMIN_VERCODE";// 机构后台图形验证码

    // 创建acctoken是，给 用户的accno 加一个前缀 区别是 web 还是 app用户
    public static final String YELLOW_WEB_LOGIN_INFO = "YELLOWWEB";// 聚合站点web登录
    // 用户设置 setAttribute
    public static final String YELLOW_WEB_APP_LOGIN_INFO = "YELLOW_WEB_APP_LOGIN_INFO";// 聚合站点web端登录
    public static final String YELLOW_WEB_ADMIN_LOGIN_INFO = "YELLOW_WEB_ADMIN_LOGIN_INFO";// 聚合站点后台

    public static final Integer ISDELETE_0 = 0;// 删除标记 未删除
    public static final Integer ISDELETE_9 = 9;// 删除标记 已删除

    public static final String SUCCESS_MSG = "success";
    public static final String FAIL_MSG = "fail";
    public static final String LOGINNUM = "LOGINNUM";
    public static final String LOGIN_NUM_ANCHOR = "login_num_anchor";
    public static final String LOGINNUMWEBSITE = "WEBSITELOGINNUM";
    public static final String LOGINNUMHOU = "LOGINNUMHOU";
    public static final String LOGINNUMHOUWEBSITE = "WEBSITELOGINNUMHOU";

    /**
     * 管理后台aes加密key
     */
    public static final String MANAGEKEY = "happyrun@2019!manage";

    // 特殊角色 充值用
    public static final String SPEAILROLENAME = "线下客服";
    // 礼物礼物数
    public static final String GIFTNUMS = "giftnums_";
    // 官方邀请码
    public static final String GUANFANGRECOMCODE = "guanfang_recomcode";
    /**
     * 短信验证码
     */
    public static final String CAPTCHA = "captcha";
    /**
     * 发送短信等待时间
     */
    public static final String CAPTCHA_SESSION = "captchasession";
    /**
     * 邮件验证码
     */
    public static final String EMAILCAP = "emailcap";
    /**
     * 直播stream加密key
     */
    public static final String STREAMKEY = "bblive&SRSKEY!2020";

    // 主播每周几提现 0 周一;1 周二;2周三 。。。不能超过6
    public static final String ANCHOR_INCAR_TIME = "anchor_incar_time";
    // 主播提现时间段 格式HH:mm-HHmm
    public static final String ANCHOR_INCAR_WEEKDAY = "anchor_incar_weekday";
    // anchor_incar_time
    //

    /**
     * ======================================USER============================================
     */
    /**
     * 角色id对于的接口 缓存 key
     */
    // public static final String ROLEIDFORINTERFACES = "roleidforinterfaces_";
    /**
     * sessionid 前缀
     */
    public static final String S_TEL_SESSIONID = "hr_sessionid_";
    public static final String S_SESSIONID_TEL_USER = "hr_tel_";
    public static final String S_SESSIONID_ACCNO_USER = "hr_accno_";

    // 账号状态 1正常 9禁止登陆
    public static final Integer ACCOUNT_ONE = 1;
    public static final Integer ACCOUNT_NINE = 9;

    /**
     * 审核状态 1未审核 8审核通过 9审核未通过
     */
    public static final Integer CHECKSTATUS_1 = 1;
    public static final Integer CHECKSTATUS_8 = 8;
    public static final Integer CHECKSTATUS_9 = 9;

    /**
     * 上首页 0置顶 9非置顶
     */
    public static final Integer ISTOPHOME_0 = 0;
    public static final Integer ISTOPHOME_9 = 9;

    /**
     * 热门 0是 9否
     */
    public static final Integer ISHOT_0 = 0;
    public static final Integer ISHOT_9 = 9;

    /**
     * ======================================USER============================================
     */
    // 验证码 倒计时 秒
    // public static final Integer SMS_COUNTDOWN = 60 ;
    // 验证码有效期 分钟
    // public static final Integer SMS_COUNTDOWN_YANZHENG = 5 ;
    public static final String STATUS_SUCCESS = "0";
    public static final String STATUS_USED = "8";
    public static final String STATUS_FAILE = "9";


    /**
     * 短信是否发送开关 0 不发送  1 发送
     */
    public static final String SMS_REAL_OFF = "0";
    public static final String SMS_REAL_ON = "1";


    /**
     * 中国大陆
     */
    public static final String AREACODE_CHINA_MAINLAND_86 = "86";
    public static final String AREACODE_CHINA_MAINLAND_086 = "086";

    /**
     * 账号状态 1正常 9禁止登陆
     */
    public static final Integer ACCSTATUS_9 = 9;
    public static final Integer ACCSTATUS_1 = 1;

    public static final String LEVEL_ONE = "0";
    public static final String LEVEL_TWO = "1";

    /**
     * ======================================AWS============================================
     */
    public static final Long FILEMAXSIZE = 80l;

    /**
     * 视频存储桶
     */
    // public static final String LIVE_DIANBOVIDEO = "livedianbovideo";

    public static final String LIVE_DIANBO_VIDEO_DIANBO = "dianbo/";
    public static final String LIVE_DIANBO_VIDEO_ARTICLE = "article/";
    /**
     * 广告存储桶
     */
    // public static final String LIVE_AD = "livead";
    /**
     * 广告-图片
     */
    public static final String LIVE_PHOTO_AD = "ad/";

    /**
     * 头像/视频封面存储桶
     */
    // public static final String LIVE_PHOTO = "livephoto";
    /**
     * 头像 前缀key
     */
    public static final String LIVE_PHOTO_AVATAR = "avatar/";
    /**
     * 视频封面 前缀key
     */
    public static final String LIVE_PHOTO_VIDEOCOVER = "videocover/";

    /**
     * 图文 的 图片/视频 前缀key
     */
    public static final String LIVE_PHOTO_ARTICLE = "article/";

    /**
     * 上传文件的路径
     */
    public static final String LIVE_FILE = "file/";
    /**
     * 是否禁言
     */
    public static final String ISFORBID = "ISFORBID";
    /**
     * ======================================AWS============================================
     */


    /**
     * ======================================播币============================================
     */
    ////////////////////////////////////////// 消息提醒/////////////////////////////////////////////////////////////
    // 提醒信息类型：order订单提醒 pay 支付消息，auditvideo视频审核提醒 auditimg图文审核提醒 other通用提醒
    ////////////////////////////////////////// offline强制下线 notalk禁言
    public static final String RMTYPE_AUDITVIDEO = "auditvideo";
    public static final String RMTYPE_AUDITIMG = "auditimg";
    public static final String RMTYPE_COMMENT = "comment";
    public static final String RMTYPE_SYSTEM = "system";
    public static final String RMTYPE_OFFLINE = "offline";
    public static final String RMTYPE_NOTALK = "notalk";

    public static final Integer ISSEE_0 = 0;
    public static final Integer ISSEE_9 = 9;
    public static final Integer ISTODO_0 = 0;
    public static final Integer ISTODO_9 = 9;

    /**
     * ======================================播币============================================
     */
    // 充值比例 1元 = 1播币
    public static final Integer CHONGZHIBILIE = 1;
    // 体现的金币数
    public static final Integer TIXIANJINBIBILIE = 900;

    // 订单类型 1在线支付 2线下支付 3在线提现 4线下提现 5彩票购彩 6彩票兑奖 7棋牌上分 8棋牌下分 9其他收入(发帖/推荐) 10其他支出(打赏) 11代理结算收入 12投注分成 13礼物分成
    public static final Integer ORDERTYPE1 = 1;
    public static final Integer ORDERTYPE2 = 2;
    public static final Integer ORDERTYPE3 = 3;
    public static final Integer ORDERTYPE4 = 4;
    public static final Integer ORDERTYPE5 = 5;
    public static final Integer ORDERTYPE6 = 6;
    public static final Integer ORDERTYPE7 = 7;
    public static final Integer ORDERTYPE8 = 8;
    public static final Integer ORDERTYPE9 = 9;
    public static final Integer ORDERTYPE10 = 10;
    public static final Integer ORDERTYPE12 = 12;
    public static final Integer ORDERTYPE13 = 13;
    /**
     * 代理结算收入
     */
    public static final Integer ORDERTYPE11 = 11;
    //代充人充值
    public static final Integer ORDERTYPE14 = 14;
    //代充人给会员充值
    public static final Integer ORDERTYPE15 = 15;
    /**
     * 订单类型: 16-代充存入
     */
    public static final Integer ORDER_TYPE_ART_IN = 16;
    /**
     * 订单类型: 17-代充提出
     */
    public static final Integer ORDER_TYPE_ART_OUT = 17;
    // 订单状态 ord01新订单 ord04待付款 ord05提现申请 ord06提现取消 ord07提现处理中 ord08已付款
    // ord09用户取消 ord10已评价 ord11已退款 ord12已提现 ord13充值失败 ord14t提现失败 ord99已过期  ord98 有注单数据
    public static final String ORDER_ORD01 = "ord01";
    public static final String ORDER_ORD04 = "ord04";
    public static final String ORDER_ORD05 = "ord05";
    public static final String ORDER_ORD06 = "ord06";
    public static final String ORDER_ORD07 = "ord07";
    public static final String ORDER_ORD08 = "ord08";
    public static final String ORDER_ORD09 = "ord09";
    public static final String ORDER_ORD10 = "ord10";
    public static final String ORDER_ORD11 = "ord11";
    public static final String ORDER_ORD12 = "ord12";
    public static final String ORDER_ORD13 = "ord13";
    public static final String ORDER_ORD14 = "ord14";
    public static final String ORDER_ORD98 = "ord98";
    public static final String ORDER_ORD99 = "ord99";
    // 结算状态 acc04待结算（不可提现部分） acc08已结算（可提现部分） acc16已提现 acc99已取消（不可结算）
    public static final String ORDER_ACC04 = "acc04";
    public static final String ORDER_ACC16 = "acc16";
    // 申请状态 1提交申请 2提现处理中 3已经失败 4已打款 8已到账 9已取消
    public static final Integer APYCSTATUS1 = 1;
    public static final Integer APYCSTATUS2 = 2;
    public static final Integer APYCSTATUS3 = 3;
    public static final Integer APYCSTATUS4 = 4;
    public static final Integer APYCSTATUS8 = 8;
    public static final Integer APYCSTATUS9 = 9;

    public static final String LOGIN_USER = "LOGIN_USER";
    public static final String H5_SHORT_URL = "h5_short_url";

    // 时时彩
    public static final String LOTTERY_SSC = "ssc";
    public static final Long LOTTERY_SANFENSHISHICAI = 2l;
    public static final String KS = "ks";

    // 超级管理员 角色ID
    public static final Long SUPERADMINSYSROLEID = 1L;
    /**
     * 系统参数启用状态0启用9未启用
     */
    public static final Integer STATUS_9 = 9;
    public static final Integer STATUS_0 = 0;

    // 直播间主播彩票分成比例
    public static final String BET_RATIO = "bet_ratio";
    // 密码隐藏
    public static final String PASSWORD_XING = "************";
    // 结算状态 acc04待结算（未打码） acc08已结算（已打码） acc99已取消（不可结算）
    public static final String ORDER_ACC08 = "acc08";
    public static final String ORDER_ACC99 = "acc99";

    // 独立拉单接口
    public static final String KYQP_API_RECORD_URL = "kyqp_api_record_url";
    // 开元棋牌Agent
    public static final String KYQP_AGENT = "kyqp_agent";
    // 开元棋牌Deskey
    public static final String KYQP_DESKEY = "kyqp_deskey";
    // 开元棋牌Md5key
    public static final String KYQP_MD5KEY = "kyqp_md5key";
    // 开元棋牌lineCode
    public static final String KYQP_LINECODE = "kyqp_linecode";
    // 账号前缀
    public static final String ZHQZ = "lpzb";
    // 开元棋牌ROOT ID
    public static final Long QIPAI_KAIYUAN_ROOTID = 1L;


    public static final String NIUNIU = "niuniu";
    public static final String GONGZHUFU = "gongzhufu";
    public static final String NEWBIAOBAI = "newbiaobai";

    // 支付状态0支付成功/退款成功 1支付中/退款中 9支付失败/退款失败
    public static final Integer PAYSTATUS0 = 0;
    public static final Integer PAYSTATUS1 = 1;
    public static final Integer PAYSTATUS9 = 9;
    // 用于回调参数加密key
    public static final String KYQP_JIAMI = "KYQPJIAMI0000@~";

    // 时时彩
    public static final String SSC = "ssc";
    public static final String ZHIBOJIAN = "直播间";


    // 一分快三
    public static final String KS_YFKS = "yfks";
    public static final Integer CAI_LIUSHI_JIANGE_MIAO = 60;
    public static final Integer CAI_WEISHU = 4;
    public static final Double CAI_YI_JIANGE = 1.0;
    public static final String KS_SANBUTONGHAO = "三不同号";
    public static final String KS_ERBUTONGHAO = "二不同号";
    public static final String KS_ERTONGHAODANXUAN = "二同号单选";
    public static final Integer PLATFORMNO0 = 0;
    public static final String RMTYPE_SYSTEMNOTICE = "systemnotice";
    public static final Double CAI_SAN_JIANGE = 3.0;
    public static final Integer CAI_YIBAIBA_JIANGE_MIAO = 180;
    // 跟头前缀
    public static final String GENTOU = "gentou";
    public static final String SSC_XJSSC = "xjssc";
    public static final String SSC_XJSSC_ZH = "xjssc_zh";
    public static final Double SIGNCYCLE_GOLDNUM_D = 0.25d;
    public static final Double SIGNCYCLE_GOLDNUM_MAXDAY_D = 2.0d;
    public static final Integer QIPAI_XIAFEN = 2;
    // 开元棋牌 上下分 订单查询 等待间隔时间
    public static final Long QIPAI_KAIYUAN_DENGDAITIME = 4500l;
    public static final Integer QIPAI_SHANGFEN = 1;
    public static final Integer TYPE_QIPAI = 2;
    // 开元棋牌API接口
    public static final String KYQP_API_URL = "kyqp_api_url";
    // 开元棋牌回调接口
    public static final String KYQP_BACKURL = "kyqp_backurl";
    // 三分时时彩
    public static final String SSC_SFSSC = "cbssc";
    public static final Integer PLATFORMNO1 = 1;
    public static final Integer TYPE_CAIZHONG = 1;
    public static final String NEGATIVE_ONE = "-1";

    /**
     * 根据用户id手动结算
     */
    public static final String SETTLE_ID = "settle_id";
    /**
     * 根据期号手动结算
     */
    public static final String SETTLE_ISSUE = "settle_issue";
    /**
     * 根据期号手动撤销
     */
    public static final String SETTLE_CANCLE = "settle_cancle";
    /**
     * 上锁
     */
    public static final String LOCK = "lock";

    public static final String PLAY_01 = "01";
    public static final String PLAY_02 = "02";
    public static final String PLAY_03 = "03";
    public static final String PLAY_04 = "04";
    public static final String PLAY_05 = "05";
    public static final String PLAY_06 = "06";
    public static final String PLAY_07 = "07";
    public static final String PLAY_08 = "08";
    public static final String PLAY_09 = "09";
    public static final String PLAY_10 = "10";
    public static final String PLAY_11 = "11";
    public static final String PLAY_12 = "12";
    public static final String PLAY_13 = "13";
    public static final String PLAY_14 = "14";

    //直播间彩票大类
    /**
     * PK10系列
     */
    public static final Integer PKIDLIVE = 13;
    /**
     * 六合彩系列
     */
    public static final Integer SIXCOLORSIDLIVE = 12;
    /**
     * 时时彩系列
     */
    public static final Integer TIMEIDLIVE = 11;


    /**
     * 投注排序
     */
    public static final String BET = "bet";
    /**
     * 中奖排序
     */
    public static final String HIT = "hit";


    public static final String LOTTERY_REPORT = "LOTTERY";

    public static final String AE_REPORT = "AE";

    public static final String AG_REPORT = "AG";

    public static final String KY_REPORT = "KY";

    public static final String ES_REPORT = "ES";

    public static final String MG_REPORT = "MG";

    public static final String AGBY_REPORT = "AGBY";

    public static final String JDB_REPORT = "JDB";

    /**
     * 房间管理员
     */
    public static final Integer ROOM_MANAGER = 0;
    /**
     * 不是房间管理员
     */
    public static final Integer NO_ROOM_MANAGER = 9;

    /**
     * 是
     */
    public static final Integer YES = 0;

    /**
     * 否
     */
    public static final Integer NO = 9;

    /**
     * 启用
     */
    public static final Integer OPEN = 0;

    /**
     * 不启用
     */
    public static final Integer CLOSE = 9;

    //上下分记录
    //上分成功
    public static final String IN_SUCCESS = "insuccess";
    //上分失败
    public static final String IN_FAIL = "infail";
    //下分成功
    public static final String OUT_SUCCESS = "outsuccess";
    //下分失败
    public static final String OUT_FAIL = "outfail";

    /**
     * 代理结算打款
     */
    public static final String AGENT_DES = "代理结算打款";
    /**
     * 注册标识
     */
    public static final String REGIST = "regist";
    /**
     * 推荐
     */
    public static final String RECOMMEND = "recommend";

    /**
     * 动态文件格式
     */
    public static final String SVGA = "svga";

    public static String MESSAGE_NOTICE = "MESSAGE_NOTICE";


    /**
     * 第三方返水表参数 1开元  2 AG 3 电竞 4 AE
     */
    public static Integer THIRD_TYPE_ONE = 1;
    public static Integer THIRD_TYPE_TWO = 2;
    public static Integer THIRD_TYPE_THREE = 3;
    public static Integer THIRD_TYPE_FOUR = 4;


    /**
     * 返水控制表参数 1 购彩 2 开元 3 AG 4 电竞 5 AE
     */
    public static Integer WATER_TYPE_ONE = 1;
    public static Integer WATER_TYPE_TWO = 2;
    public static Integer WATER_TYPE_THREE = 3;
    public static Integer WATER_YPE_FOUR = 4;
    public static Integer WATER_YPE_FIVE = 5;

    public static int THREAD_MAX_RETRY_XIAFEN = 5;
    //订单状态(0-处理中,1-成功,2-超过上限,3-余额不足,4-在地图内,无法执行操作,5-在游戏中,无法执行操作
    public static Integer AE_ORDER_ZERO = 0;
    public static Integer AE_ORDER_ONE = 1;
    public static Integer AE_ORDER_TWO = 2;
    public static Integer AE_ORDER_THREE = 3;
    public static Integer AE_ORDER_FOUR = 4;
    public static Integer AE_ORDER_FIVE = 5;

    public static String D = "大";
    public static String X = "小";
    public static String SINGLE = "单";
    public static String DOUBLE = "双";
    public static String DRAGON = "龙";
    public static String TIGER = "虎";

    //直播聊天室消息最大值
    public static int MAX_TEXT_MESSAGE_BUFFER_SIZE = 1024;

    /**
     * 活跃状态(直播)
     */
    public static final String ACTIVE = "active";
    /**
     * 非活跃状态(正常下播)
     */
    public static final String INACTIVE = "inactive";
    /**
     * 禁播（异常或管理员操作）
     */
    public static final String FORBID = "forbid";

    /**
     * 腾讯云直播地址
     */
    public static final String HTTP_PROFILE_END_POINT = "live.tencentcloudapi.com";


    /**
     * 帐户类型
     */
    public static final String ALIPAY = "支付宝";
    public static final String WECHAT = "微信";
    public static final String UNIONPAY = "银联";

    public static final int ACCOUNTTYPE_ALIPAY = 1;
    public static final int ACCOUNTTYPE_WECHAT = 2;
    public static final int ACCOUNTTYPE_UNIONPAY = 3;

    //首充
    public static final int PAY_SET_ONE = 1;
    //每次
    public static final int PAY_SET_TWO = 2;

    public static final String TRANSFORM_360_KEY = "url360";
    public static final String TRANSFORM_480_KEY = "url480";
    public static final String TRANSFORM_720_KEY = "url720";

    public static final String LIVE_BAS_ANCHOR_PLATFORM = "LIVE_BAS_ANCHOR_PLATFORM_";
    public static final String LIVE_BAS_ANCHOR_SG_PUSH_PLATFORM = "LIVE_BAS_ANCHOR_SG_PUSH_PLATFORM_";//主播端赛果推送


    public static final int ANCHOR_UNIQUE_CODE_GEN_SIZE = 100000;
    public static final int UNIQUE_CODE_GEN_SIZE = 200 * 10000;
    public static final int UNIQUE_CODE_LOAD_SIZE = 500;
    public static final int UNIQUE_CODE_BATCH_SIZE = 5000;


    public static final String PLANT_BOUNS = "plant_bouns";
    public static final String PLANT_OPERATE = "plant_operate";

    // 日志等级： 正常normal 系统错误error
    public static final String ERROR_LEVEL_NORMAL = "normal";
    public static final String ERROR_LEVEL_ERROR = "error";


    public static final int EXP_FUND_STATISTICS_DAYS = 59;


    public static final String HEAD_IMG_CODE = "HEADIMG";


    public static final String COMPANY = "公司入款";
    public static final String PAYFOR = "代充充值";
    public static final String THIRDONLINE = "线上入款";
    public static final String ARTIFICIAL = "人工入款";
    public static final String CACULATE = "小计";


    public static final String HOME_REGISTERED = "registered";
//    public static final String HOME_PROFITALLDATA = "profitAllData";
    public static final String HOME_RECHARGEALLDATA = "rechargeAllData";
    public static final String HOME_PAYMENTALLDATA = "paymentAllData";
    public static final String HOME_ACTIVITYALLDATA = "activityAllData";
    public static final String HOME_CONSUMPTIONALLDATA = "consumptionAllData";
    public static final String HOME_LOTTERYALLDATA = "lotteryAllData";

}
