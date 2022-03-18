package com.caipiao.live.common.enums.lottery;

/**
 * 各个彩种Redis时间枚举
 */
public enum CaipiaoRedisTimeEnum {
    /**
     * ID:1101
     * 重庆时时彩
     */
    CQSSC("cqssc", 20),
    /**
     * ID:1102
     * 新疆时时彩
     */
    XJSSC("xjssc", 20),
    /**
     * ID:1103
     * 天津时时彩
     */
    TJSSC("tjssc", 20),
    /**
     * ID:1104
     * 10分时时彩
     */
    TENSSC("tenssc", 10),
    /**
     * ID:1105
     * 5分时时彩
     */
    FIVESSC("fivessc", 5),
    /**
     * ID:1106
     * 德州时时彩
     */
    JSSSC("jsssc", 1),
    /**
     * ID:1201
     * 六合彩
     */
    LHC("lhc", 1),
    /**
     * ID:1202
     * 1分六合彩
     */
    ONELHC("onelhc", 1),
    /**
     * ID:1203
     * 5分六合彩
     */
    FIVELHC("fivelhc", 5),
    /**
     * ID:1204
     * 时时六合彩
     */
    AMLHC("amlhc", 1440),
    /**
     * ID:1205
     * 新加坡六合彩
     */
    XJPLHC("xjplhc", 1),
    /**
     * ID:1301
     * 北京PK10
     */
    BJPKS("bjpks", 10),
    /**
     * ID:1302
     * 10分PK10
     */
    TENPKS("tenpks", 10),
    /**
     * ID:1303
     * 5分PK10
     */
    FIVEPKS("fivepks", 5),
    /**
     * ID:1304
     * 德州PK10
     */
    JSPKS("jspks", 1),
    /**
     * ID:1305
     * 澳洲F1
     */
    AOZHOUFONE("", 1),
    /**
     * ID:1401
     * 幸运飞艇
     */
    XYFEIT("xyft", 5),
    /**
     * ID:1402
     * 幸运飞艇
     */
    DZXYFEIT("dzxyft", 5),
    /**
     * ID:1501
     * PC蛋蛋
     */
    PCDAND("pcegg", 5),
    /**
     * ID:1502
     * PC蛋蛋
     */
    DZPCDAND("dzpcegg", 5),
    /**
     * ID:1601
     * 比特币分分彩
     */
    TXFFC("txffc", 58),
//	/**
//	 * ID:1701
//	 * 大乐透
//	 * */
//	DLT("dlt",10),
//	/**
//	 * ID:1702
//	 * 排列3/5
//	 * */
//	TCPLW("tcplw",10),
//	/**
//	 * ID:1703
//	 * 7星彩
//	 * */
//	TC7XC("tc7xc",20),
//	/**
//	 * ID:1801
//	 * 双色球
//	 * */
//	FCSSQ("fcssq",10),
//	/**
//	 * ID:1802
//	 * 福彩3D
//	 * */
//	FC3D("fc3d",30),
//	/**
//	 * ID:1803
//	 * 七乐彩
//	 * */
//	FC7LC("fc7lc",30),
//	/**
//	 * ID:1901
//	 * 快乐牛牛
//	 * */
//	KLNIU("",1),
//	/**
//	 * ID:1902
//	 * 澳洲牛牛
//	 * */
//	AZNIU("",1),
//	/**
//	 * ID:1903
//	 * 德州牛牛
//	 * */
//	JSNIU("",1),
//	/**
//	 * ID:2001
//	 * 德州PK10番摊
//	 * */
//	JSPKFT("jspkft",20),
//	/**
//	 * ID:2002
//	 * 幸运飞艇番摊
//	 * */
//	XYFTFT("xyftft",30),
//	/**
//	 * ID:2003
//	 * 德州时时彩番摊
//	 * */
//	JSSSCFT("jssscft",10),
    /**
     * ID:2201
     * 澳洲ACT
     */
    AUSACT("ausact", 2),
    /**
     * ID:2202
     * 澳洲时时彩
     */
    AUZSSC("auzssc", 2),
    /**
     * ID:2202
     * 澳洲时时彩
     */
    AUSPKS("auspks", 2),
    /**
     * ID:2201
     * 欢乐赛车
     */
    HLSAIC("", 1),
    /**
     * ID:2202
     * 欢乐时时彩
     */
    HLSSC("", 1),
    /**
     * ID:2203
     * 欢乐飞艇
     */
    HLFEIT("", 1),
    /**
     * ID:2301
     * 澳洲快三
     */
    AZKS("azks", 2),
    /**
     * ID:2302
     * 德州快三
     */
    DZKS("dzks", 2),
    /** ==================================WEB REDIS ============================================* */
    /**
     * 六合彩心水推荐列表集合
     */
    WEB_LHCXSRECOMMENDS("weblhcxsrecommends", 2),
    /**
     * 六合彩心水推荐列表集合
     */
    APP_LHCXSRECOMMENDS("applhcxsrecommends", 2),
    /**
     * 心水列表查询用户缓存时间
     */
    WEB_XINSHUI_APPMEMBER_CACHE("web_xinshui_appmember_cache", 20),
    /**
     * 心水列表查询帖子缓存时间
     */
    WEB_XSREFFER_APPMEMBER_CACHE("web_xsreffer_appmember_cache", 20),
    /**
     * 心水发帖子间隔时间
     */
    RECOMMEND_SEND_LIMIT_TIME("recommend_send_limit_time", 1);

    private String paramEnName;
    private long redisTime;

    private CaipiaoRedisTimeEnum(String paramEnName, long redisTime) {
        this.paramEnName = paramEnName;
        this.redisTime = redisTime;
    }

    public String getParamEnName() {
        return paramEnName;
    }

    public void setParamEnName(String paramEnName) {
        this.paramEnName = paramEnName;
    }

    public long getRedisTime() {
        return redisTime;
    }

    public void setRedisTime(long redisTime) {
        this.redisTime = redisTime;
    }

}
