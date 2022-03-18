package com.caipiao.core.library.enums;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * 彩种Tag枚舉
 */
public enum CaipiaoTypeEnum {
    /**
     * ID:1101
     * 重庆时时彩
     */
    CQSSC("cqssc", "重庆时时彩", "1101"),
    /**
     * ID:1102
     * 新疆时时彩
     */
    XJSSC("xjssc", "新疆时时彩", "1102"),
    /**
     * ID:1103
     * 天津时时彩
     */
    TJSSC("tjssc", "天津时时彩", "1103"),
    /**
     * ID:1104
     * 10分时时彩
     */
    TENSSC("tenssc", "10分时时彩", "1104"),
    /**
     * ID:1105
     * 5分时时彩
     */
    FIVESSC("fivessc", "5分时时彩", "1105"),
    /**
     * ID:1106
     * 德州时时彩
     */
    JSSSC("jsssc", "德州时时彩", "1106"),
    /**
     * ID:1201
     * 六合彩
     */
    LHC("lhc", "六合彩", "1201"),
    /**
     * ID:1202
     * 1分六合彩
     */
    ONELHC("onelhc", "德州六合彩", "1202"),
    /**
     * ID:1203
     * 5分六合彩
     */
    FIVELHC("fivelhc", "5分六合彩", "1203"),
    /**
     * ID:1204
     * 澳门六合彩
     */
    AMLHC("amlhc", "澳门六合彩", "1204"),
    /**
     * ID:1301
     * 北京PK10
     */
    BJPKS("bjpks", "北京PK10", "1301"),
    /**
     * ID:1302
     * 10分PK10
     */
    TENPKS("tenpks", "10分PK10", "1302"),
    /**
     * ID:1303
     * 5分PK10
     */
    FIVEPKS("fivepks", "5分PK10", "1303"),
    /**
     * ID:1304
     * 德州PK10
     */
    JSPKS("jspks", "德州PK10", "1304"),
    /**
     * ID:1305  /已经废弃， 和2203 是一个
     * 澳洲F1
     */
    AOZHOUFONE("", "澳洲F1", "1305"),
    /**
     * ID:1401
     * 幸运飞艇
     */
    XYFEIT("xyft", "幸运飞艇", "1401"),
    /**
     * ID:1403
     * 幸运飞艇私彩
     */
    XYFEITSC("xyftsc", "幸运飞艇", "1403"),
    /**
     * ID:1501
     * PC蛋蛋
     */
    PCDAND("pcegg", "PC蛋蛋", "1501"),
    /**
     * ID:1601
     * 腾讯分分彩
     */
    TXFFC("txffc", "腾讯分分彩", "1601"),
    /**
     * ID:1601
     * 比特币分分彩
     */
    BTBFFC("btbffc", "比特币分分彩", "16010"),
    /**
     * ID:1701
     * 大乐透
     */
    DLT("dlt", "大乐透", "1701"),
    /**
     * ID:1702
     * 排列3/5
     */
    TCPLW("tcplw", "排列3/5", "1702"),
    /**
     * ID:1703
     * 7星彩
     */
    TC7XC("tc7xc", "7星彩", "1703"),
    /**
     * ID:1801
     * 双色球
     */
    FCSSQ("fcssq", "双色球", "1801"),
    /**
     * ID:1802
     * 福彩3D
     */
    FC3D("fc3d", "福彩3D", "1802"),
    /**
     * ID:1803
     * 七乐彩
     */
    FC7LC("fc7lc", "七乐彩", "1803"),
    /**
     * ID:1901
     * 快乐牛牛
     */
    KLNIU("hpniu", "快乐牛牛", "1901"),
    /**
     * ID:1902
     * 澳洲牛牛
     */
    AZNIU("aozniu", "澳洲牛牛", "1902"),
    /**
     * ID:1903
     * 德州牛牛
     */
    JSNIU("jsniu", "德州牛牛", "1903"),
    /**
     * ID:2001
     * 德州PK10番摊
     */
    JSPKFT("jspkft", "德州PK10番摊", "2001"),
    /**
     * ID:2002
     * 幸运飞艇番摊
     */
    XYFTFT("xyftft", "幸运飞艇番摊", "2002"),
    /**
     * ID:2003
     * 德州时时彩番摊
     */
    JSSSCFT("jssscft", "德州时时彩番摊", "2003"),
    /**
     * ID:2201
     * 澳洲ACT
     */
    AUSACT("ausact", "澳洲ACT", "2201"),
    /**
     * ID:2202
     * 澳洲时时彩
     */
    AUSSSC("ausssc", "澳洲时时彩", "2202"),
    /**
     * ID:2203
     * 澳洲F1赛车
     */
    AUSPKS("auspks", "澳洲F1赛车", "2203"),
    /**
     * ID:2301
     * 澳洲快三
     */
    AZKS("azks", "澳洲快3", "2301"),
    /**
     * ID:2302
     * 德州快三
     */
    DZKS("dzks", "德州快3", "2302"),
    /**
     * ID:2302
     * 德州幸运飞艇
     */
    DZXYFT("dzxyft", "德州幸运飞艇", "1402"),
    /**
     * ID:2302
     * 德州PC蛋蛋
     */
    DZPCEGG("dzpcegg", "德州PC蛋蛋", "1502"),
    /**
     * ID:2302
     * 新加坡六合彩
     */
    XJPLHC("xjplhc", "新加坡六合彩", "1205");

    private String tagEnName;
    private String tagCnName;
    private String tagType;

    /**
     * 私彩id，常量，非此枚举内的所有值
     */
    public static final List<String> SICAI = Lists.newArrayList("1104", "1105", "1106", "1202", "1203", "1204", "1302", "1303", "1304", "1901", "1903", "2003", "2001","1205","2301","2302"
            ,"1502","1402");

    private CaipiaoTypeEnum(String tagEnName, String tagCnName, String tagType) {
        this.tagEnName = tagEnName;
        this.tagCnName = tagCnName;
        this.tagType = tagType;
    }

    public String getTagEnName() {
        return tagEnName;
    }

    public void setTagEnName(String tagEnName) {
        this.tagEnName = tagEnName;
    }

    public String getTagCnName() {
        return tagCnName;
    }

    public void setTagCnName(String tagCnName) {
        this.tagCnName = tagCnName;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public static CaipiaoTypeEnum valueOfTagType(String tagType) {
        if (null == tagType || "".equals(tagType.trim())) {
            return null;
        }
        for (CaipiaoTypeEnum caipiaoTypeEnum : CaipiaoTypeEnum.values()) {
            if (tagType.equals(caipiaoTypeEnum.getTagType())) {
                return caipiaoTypeEnum;
            }
        }
        return null;
    }

    public static CaipiaoTypeEnum valueOfTagEnName(String tagEnName) {
        if (null == tagEnName || "".equals(tagEnName.trim())) {
            return null;
        }
        for (CaipiaoTypeEnum caipiaoTypeEnum : CaipiaoTypeEnum.values()) {
            if (tagEnName.equals(caipiaoTypeEnum.getTagEnName())) {
                return caipiaoTypeEnum;
            }
        }
        return null;
    }

    public static String getValueByType(String enName) {
        for (CaipiaoTypeEnum caipiaoTypeEnum : CaipiaoTypeEnum.values()) {
            if (enName.equals(caipiaoTypeEnum.getTagEnName())) {
                return caipiaoTypeEnum.getTagType();
            }
        }
        return null;
    }

}
