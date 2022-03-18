package com.caipiao.core.library.enums;

public enum LotteryTableNameEnum {
    /**
     * ID:1101
     * 重庆时时彩
     */
    CQSSC("cqssc_lottery_sg", 1101),
    /**
     * ID:1102
     * 新疆时时彩
     */
    XJSSC("xjssc_lottery_sg", 1102),
    /**
     * ID:1103
     * 天津时时彩
     */
    TJSSC("tjssc_lottery_sg", 1103),
    /**
     * ID:1104
     * 10分时时彩
     */
    TENSSC("tenssc_lottery_sg", 1104),
    /**
     * ID:1105
     * 5分时时彩
     */
    FIVESSC("fivessc_lottery_sg", 1105),
    /**
     * ID:1106
     * 极速时时彩
     */
    JSSSC("jsssc_lottery_sg", 1106),
    /**
     * ID:1201
     * 六合彩
     */
    LHC("lhc_lottery_sg", 1201),
    /**
     * ID:1202
     * 1分六合彩
     */
    ONELHC("onelhc_lottery_sg", 1202),
    /**
     * ID:1203
     * 5分六合彩
     */
    FIVELHC("fivelhc_lottery_sg", 1203),
    /**
     * ID:1204
     * 澳门六合彩
     */
    AMLHC("amlhc_lottery_sg", 1204),
    /**
     * ID:1301
     * 北京PK10
     */
    BJPKS("bjpks_lottery_sg", 1301),
    /**
     * ID:1302
     * 10分PK10
     */
    TENPKS("tenbjpks_lottery_sg", 1302),
    /**
     * ID:1303
     * 5分PK10
     */
    FIVEPKS("fivebjpks_lottery_sg", 1303),
    /**
     * ID:1304
     * 极速PK10
     */
    JSPKS("jsbjpks_lottery_sg", 1304),
    /**
     * ID:1305
     * 澳洲F1
     */
    AOZHOUFONE("auspks_lottery_sg", 1305),
    /**
     * ID:1401
     * 幸运飞艇
     */
    XYFEIT("xyft_lottery_sg", 1401),
    /**
     * ID:1501
     * PC蛋蛋
     */
    PCDAND("pcegg_lottery_sg", 1501),
    /**
     * ID:1601
     * 腾讯分分彩
     */
    TXFFC("txffc_lottery_sg", 1601),
    /**
     * ID:1701
     * 大乐透
     */
    DLT("tcdlt_lottery_sg", 1701),
    /**
     * ID:1702
     * 排列3/5
     */
    TCPLW("tcplw_lottery_sg", 1702),
    /**
     * ID:1703
     * 7星彩
     */
    TC7XC("tc7xc_lottery_sg", 1703),
    /**
     * ID:1801
     * 双色球
     */
    FCSSQ("fcssq_lottery_sg", 1801),
    /**
     * ID:1802
     * 福彩3D
     */
    FC3D("fc3d_lottery_sg", 1802),
    /**
     * ID:1803
     * 七乐彩
     */
    FC7LC("fc7lc_lottery_sg", 1803),
    /**
     * ID:1901
     * 快乐牛牛
     */
    KLNIU("fivessc_lottery_sg", 1901),
    /**
     * ID:1902
     * 澳洲牛牛
     */
    AZNIU("auspks_lottery_sg", 1902),
    /**
     * ID:1903
     * 极速牛牛
     */
    JSNIU("jsbjpks_lottery_sg", 1903),
    /**
     * ID:2001
     * 极速PK10番摊
     */
    JSPKFT("ftjspks_lottery_sg", 2001),
    /**
     * ID:2002
     * 幸运飞艇番摊
     */
    XYFTFT("ftxyft_lottery_sg", 2002),
    /**
     * ID:2003
     * 极速时时彩番摊
     */
    JSSSCFT("ftjsssc_lottery_sg", 2003),
    /**
     * ID:2101
     * 澳洲ACT
     */
    AUSACT("ausact_lottery_sg", 2201),
    /**
     * ID:2202
     * 澳洲时时彩
     */
    AUSSSC("ausssc_lottery_sg", 2202),
    /**
     * ID:2203
     * 澳洲pks
     */
    AUSPKS("auspks_lottery_sg", 2203),

    /**
     * 德州快三
     */
    DZKS("dzks_lottery_sg",  2302);

    private String tableName;
    private Integer lotteryId;

    private LotteryTableNameEnum(String tableName, Integer lotteryId) {
        this.tableName = tableName;
        this.lotteryId = lotteryId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public static String getValueByType(Integer lotteryId) {
        for (LotteryTableNameEnum lotteryTableNameEnum : LotteryTableNameEnum.values()) {
            if (lotteryId.equals(lotteryTableNameEnum.getLotteryId())) {
                return lotteryTableNameEnum.getTableName();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Integer ttInteger = 1201;
        System.out.println(ttInteger == 1201);

    }
}
