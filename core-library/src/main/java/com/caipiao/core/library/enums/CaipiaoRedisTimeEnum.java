package com.caipiao.core.library.enums;

/**
 * 各个彩种Redis时间枚舉
 */
public enum CaipiaoRedisTimeEnum {
    /**
     * ID:1101
     * 重庆时时彩
     */
    CQSSC("cqssc", 1),
    /**
     * ID:1102
     * 新疆时时彩
     */
    XJSSC("xjssc", 1),
    /**
     * ID:1103
     * 天津时时彩
     */
    TJSSC("tjssc", 1),
    /**
     * ID:1104
     * 10分时时彩
     */
    TENSSC("tenssc", 1),
    /**
     * ID:1105
     * 5分时时彩
     */
    FIVESSC("fivessc", 1),
    /**
     * ID:1106
     * 极速时时彩
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
    FIVELHC("fivelhc", 1),
    /**
     * ID:1204
     * 澳门六合彩
     */
    AMLHC("amlhc", 1),
    /**
     * ID:1301
     * 北京PK10
     */
    BJPKS("bjpks", 1),
    /**
     * ID:1302
     * 10分PK10
     */
    TENPKS("tenpks", 1),
    /**
     * ID:1303
     * 5分PK10
     */
    FIVEPKS("fivepks", 1),
    /**
     * ID:1304
     * 极速PK10
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
    XYFEIT("xyft", 1),
    /**
     * ID:1401
     * 幸运飞艇私彩
     */
    XYFEITSC("xyftsc", 1),
    /**
     * ID:1501
     * PC蛋蛋
     */
    PCDAND("pcegg", 1),
    /**
     * ID:1601
     * 腾讯分分彩
     */
    TXFFC("txffc", 1),
    /**
     * ID:1701
     * 大乐透
     */
    DLT("dlt", 1),
    /**
     * ID:1702
     * 排列3/5
     */
    TCPLW("tcplw", 1),
    /**
     * ID:1703
     * 7星彩
     */
    TC7XC("tc7xc", 1),
    /**
     * ID:1801
     * 双色球
     */
    FCSSQ("fcssq", 1),
    /**
     * ID:1802
     * 福彩3D
     */
    FC3D("fc3d", 1),
    /**
     * ID:1803
     * 七乐彩
     */
    FC7LC("fc7lc", 1),
    /**
     * ID:1901
     * 快乐牛牛
     */
    KLNIU("", 1),
    /**
     * ID:1902
     * 澳洲牛牛
     */
    AZNIU("", 1),
    /**
     * ID:1903
     * 极速牛牛
     */
    JSNIU("", 1),
    /**
     * ID:2001
     * 极速PK10番摊
     */
    JSPKFT("jspkft", 1),
    /**
     * ID:2002
     * 幸运飞艇番摊
     */
    XYFTFT("xyftft", 1),
    /**
     * ID:2003
     * 极速时时彩番摊
     */
    JSSSCFT("jssscft", 1),
    /**
     * ID:2101
     * 澳洲ACT
     */
    AZACT("", 1),
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
    WEB_XSREFFER_APPMEMBER_CACHE("web_xsreffer_appmember_cache", 20);

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
