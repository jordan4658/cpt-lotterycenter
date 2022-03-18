package com.caipiao.core.library.enums;

/**
 * 各个彩种Redis时间枚舉
 */
public enum CaipiaoSumCountEnum {
    /**
     * ID:1101 重庆时时彩
     */
    CQSSC("cqssc", 59),
    /**
     * ID:1102 新疆时时彩
     */
    XJSSC("xjssc", 48),
    /**
     * ID:1103 天津时时彩
     */
    TJSSC("tjssc", 42),
    /**
     * ID:1104 10分时时彩
     */
    TENSSC("tenssc", 144),
    /**
     * ID:1105 5分时时彩
     */
    FIVESSC("fivessc", 288),
    /**
     * ID:1106 极速时时彩
     */
    JSSSC("jsssc", 1440),
    /**
     * ID:1201 六合彩
     */
    LHC("lhc", 156),
    /**
     * ID:1202 1分六合彩
     */
    ONELHC("onelhc", 1440),
    /**
     * ID:1203 5分六合彩
     */
    FIVELHC("fivelhc", 288),
    /**
     * ID:1204 澳门六合彩
     */
    AMLHC("amlhc", 1),
    /**
     * ID:1301 北京PK10
     */
    BJPKS("bjpks", 44),
    /**
     * ID:1302 10分PK10
     */
    TENPKS("tenpks", 144),
    /**
     * ID:1303 5分PK10
     */
    FIVEPKS("fivepks", 288),
    /**
     * ID:1304 极速PK10
     */
    JSPKS("jspks", 1440),
    /**
     * ID:1305 澳洲F1
     */
    AOZHOUFONE("", 0),
    /**
     * ID:1401 幸运飞艇
     */
    XYFEIT("xyft", 180),
    /**
     * ID:1401 幸运飞艇
     */
    XYFEITSC("xyftsc", 180),
    /**
     * ID:1501 PC蛋蛋
     */
    PCDAND("pcegg", 179),
    /**
     * ID:1601 腾讯分分彩
     */
    TXFFC("txffc", 1440),
    /**
     * ID:1701 大乐透
     */
    DLT("dlt", 156),
    /**
     * ID:1702 排列3/5
     */
    TCPLW("tcplw", 1),
    /**
     * ID:1703 7星彩
     */
    TC7XC("tc7xc", 2),
    /**
     * ID:1801 双色球
     */
    FCSSQ("fcssq", 156),
    /**
     * ID:1802 福彩3D
     */
    FC3D("fc3d", 1),
    /**
     * ID:1803 七乐彩
     */
    FC7LC("fc7lc", 156),
    /**
     * ID:1901 快乐牛牛
     */
    KLNIU("", 288),
    /**
     * ID:1902 澳洲牛牛
     */
    AZNIU("", 209),
    /**
     * ID:1903 极速牛牛
     */
    JSNIU("", 1440),
    /**
     * ID:2001 极速PK10番摊
     */
    JSPKFT("jspkft", 1440),
    /**
     * ID:2002 幸运飞艇番摊
     */
    XYFTFT("xyftft", 180),
    /**
     * ID:2003 极速时时彩番摊
     */
    JSSSCFT("jssscft", 1440),
    /**
     * ID:2101 澳洲ACT
     */
    AZACT("ausact", 540),
    /**
     * ID:2201 欢乐赛车
     */
    HLSAIC("", 209),
    /**
     * ID:2202 欢乐时时彩
     */
    HLSSC("", 209),
    /**
     * ID:2203 欢乐飞艇
     */
    HLFEIT("", 209),
    /**
     * ID:2301 澳洲快三
     */
    AZKS("azks", 540),
    /**
     * ID:2302 德州快三
     */
    DZKS("dzks", 1440),
    /**
     * ID:1402
     */
    DZXYFT("dzxyft", 1440),
    /**
     * ID:1201 德州PC蛋蛋
     */
    DZPCEGG("dzpcegg", 1440),
    /**
     * ID:1205 新加坡六合彩
     */
    XJPLHC("xjplhc", 288);

    private String caipiaoName;
    private Integer sumCount;

    private CaipiaoSumCountEnum(String caipiaoName, Integer sumCount) {
        this.caipiaoName = caipiaoName;
        this.sumCount = sumCount;
    }

    public String getCaipiaoName() {
        return caipiaoName;
    }

    public void setCaipiaoName(String caipiaoName) {
        this.caipiaoName = caipiaoName;
    }

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

}
