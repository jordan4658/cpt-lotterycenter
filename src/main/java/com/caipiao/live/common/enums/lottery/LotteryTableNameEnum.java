package com.caipiao.live.common.enums.lottery;

public enum LotteryTableNameEnum {
    /**
     * ID:1101
     * 重庆时时彩
     */
    CQSSC("lottery_cqssc_lottery_sg", 1101),
    /**
     * ID:1102
     * 新疆时时彩
     */
    XJSSC("lottery_xjssc_lottery_sg", 1102),
    /**
     * ID:1103
     * 天津时时彩
     */
    TJSSC("lottery_tjssc_lottery_sg", 1103),
    /**
     * ID:1104
     * 10分时时彩
     */
    TENSSC("lottery_tenssc_lottery_sg", 1104),
    /**
     * ID:1105
     * 5分时时彩
     */
    FIVESSC("lottery_fivessc_lottery_sg", 1105),
    /**
     * ID:1106
     * 德州时时彩
     */
    JSSSC("lottery_jsssc_lottery_sg", 1106),
    /**
     * ID:1201
     * 六合彩
     */
    LHC("lottery_lhc_lottery_sg", 1201),
    /**
     * ID:1202
     * 1分六合彩
     */
    ONELHC("lottery_onelhc_lottery_sg", 1202),
    /**
     * ID:1203
     * 5分六合彩
     */
    FIVELHC("lottery_fivelhc_lottery_sg", 1203),
    /**
     * ID:1204
     * 澳门六合彩
     */
    AMLHC("lottery_amlhc_lottery_sg", 1204),
    /**
     * ID:1205
     * 新加坡六合彩
     */
    XJPLHC("lottery_xjplhc_lottery_sg", 1205),
    /**
     * ID:1301
     * 北京PK10
     */
    BJPKS("lottery_bjpks_lottery_sg", 1301),
    /**
     * ID:1302
     * 10分PK10
     */
    TENPKS("lottery_tenbjpks_lottery_sg", 1302),
    /**
     * ID:1303
     * 5分PK10
     */
    FIVEPKS("lottery_fivebjpks_lottery_sg", 1303),
    /**
     * ID:1304
     * 德州PK10
     */
    JSPKS("lottery_jsbjpks_lottery_sg", 1304),
    /**
     * ID:1305
     * 澳洲F1
     */
    AOZHOUFONE("lottery_auspks_lottery_sg", 1305),
    /**
     * ID:1401
     * 幸运飞艇
     */
    XYFEIT("lottery_xyft_lottery_sg", 1401),
    /**
     * ID:1402
     * 德州幸运飞艇
     */
    DZXYFEIT("lottery_dzxyft_lottery_sg", 1402),
    /**
     * ID:1501
     * PC蛋蛋
     */
    PCDAND("lottery_pcegg_lottery_sg", 1501),
    /**
     * ID:1502
     * 德州PC蛋蛋
     */
    DZPCDAND("lottery_dzpcegg_lottery_sg", 1502),
    /**
     * ID:1601
     * 比特币分分彩
     */
    TXFFC("lottery_txffc_lottery_sg", 1601),
//    /**
//     * ID:1701
//     * 大乐透
//     */
//    DLT("tcdlt_lottery_sg", 1701),
//    /**
//     * ID:1702
//     * 排列3/5
//     */
//    TCPLW("tcplw_lottery_sg", 1702),
//    /**
//     * ID:1703
//     * 7星彩
//     */
//    TC7XC("tc7xc_lottery_sg", 1703),
//    /**
//     * ID:1801
//     * 双色球
//     */
//    FCSSQ("fcssq_lottery_sg", 1801),
//    /**
//     * ID:1802
//     * 福彩3D
//     */
//    FC3D("fc3d_lottery_sg", 1802),
//    /**
//     * ID:1803
//     * 七乐彩
//     */
//    FC7LC("fc7lc_lottery_sg", 1803),
//    /**
//     * ID:1901
//     * 快乐牛牛
//     */
//    KLNIU("fivessc_lottery_sg", 1901),
//    /**
//     * ID:1902
//     * 澳洲牛牛
//     */
//    AZNIU("auspks_lottery_sg", 1902),
//    /**
//     * ID:1903
//     * 德州牛牛
//     */
//    JSNIU("jsbjpks_lottery_sg", 1903),
//    /**
//     * ID:2001
//     * 德州PK10番摊
//     */
//    JSPKFT("ftjspks_lottery_sg", 2001),
//    /**
//     * ID:2002
//     * 幸运飞艇番摊
//     */
//    XYFTFT("ftxyft_lottery_sg", 2002),
//    /**
//     * ID:2003
//     * 德州时时彩番摊
//     */
//    JSSSCFT("ftjsssc_lottery_sg", 2003),
    /**
     * ID:2101
     * 澳洲ACT
     */
    AUSACT("lottery_ausact_lottery_sg", 2201),
    /**
     * ID:2202
     * 澳洲时时彩
     */
    AUSSSC("lottery_ausssc_lottery_sg", 2202),
    /**
     * ID:2203
     * 澳洲pks
     */
    AUSPKS("lottery_auspks_lottery_sg", 2203),
    /**
     * ID:2301
     * 澳洲pks
     */
    AZKS("lottery_azks_lottery_sg", 2301),
    /**
     * ID:2302
     * 澳洲pks
     */
    DZKS("lottery_dzks_lottery_sg", 2302);

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

    public static String getTableNameByLotteryId(Integer lotteryId) {
        LotteryTableNameEnum lotteryTableNameEnum = LotteryTableNameEnum.valueOfLotteryId(lotteryId);
        return null == lotteryTableNameEnum ? null : lotteryTableNameEnum.getTableName();
    }

    public static int getLotteryIdByTableName(String tableName) {
        LotteryTableNameEnum lotteryTableNameEnum = LotteryTableNameEnum.valueOfTableName(tableName);
        return null == lotteryTableNameEnum ? null : lotteryTableNameEnum.getLotteryId();
    }

    public static LotteryTableNameEnum valueOfTableName(String tableName) {
        if (null == tableName || "".equals(tableName.trim())) {
            return null;
        }
        LotteryTableNameEnum[] values = LotteryTableNameEnum.values();
        for (LotteryTableNameEnum obj : values) {
            if (obj.getTableName().equals(tableName)) {
                return obj;
            }
        }
        return null;
    }

    public static LotteryTableNameEnum valueOfLotteryId(Integer lotteryId) {
        if (null == lotteryId) {
            return null;
        }
        LotteryTableNameEnum[] values = LotteryTableNameEnum.values();
        for (LotteryTableNameEnum obj : values) {
            if (obj.getLotteryId().equals(lotteryId)) {
                return obj;
            }
        }
        return null;
    }

}
