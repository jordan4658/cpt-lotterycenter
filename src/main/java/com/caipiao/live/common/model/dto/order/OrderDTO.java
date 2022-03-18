package com.caipiao.live.common.model.dto.order;

import com.caipiao.live.common.constant.Constants;
import com.caipiao.live.common.mybatis.entity.LotteryPlaySetting;
import com.caipiao.live.common.mybatis.entity.OrderBetRecord;
import com.caipiao.live.common.mybatis.entity.OrderRecord;
import com.caipiao.live.common.util.ArrayUtils;
import com.caipiao.live.common.util.CollectionUtil;
import com.caipiao.live.common.util.MathUtil;
import com.caipiao.live.common.util.StringUtils;
import com.caipiao.live.common.util.lottery.KsUtils;
import com.caipiao.live.common.util.lottery.LhcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class OrderDTO extends OrderRecord {
    private static final Logger logger = LoggerFactory.getLogger(OrderDTO.class);

    //标识号 (主播id)
    private Long familymemid;

    //直播间id
    private Long roomId;

    private Long familyid;//家族id

    //失败重试次数
    private int reOrderNum;

    private List<OrderBetRecord> orderBetList;

    public Long getFamilymemid() {
        return familymemid;
    }

    public void setFamilymemid(Long familymemid) {
        this.familymemid = familymemid;
    }

    public static Logger getLogger() {
        return logger;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    /*
    0, 无
    1, 购彩页面
    2, 长龙
    3, 购彩篮
    4, 跟单
    5, 追号
    6.直播间投注
    7.直播间跟投
    */
    public List<OrderBetRecord> getOrderBetList() {
        return orderBetList;
    }

    public Long getFamilyid() {
        return familyid;
    }

    public void setFamilyid(Long familyid) {
        this.familyid = familyid;
    }

    public void setOrderBetList(List<OrderBetRecord> orderBetList) {
        this.orderBetList = orderBetList;
    }

    public int getReOrderNum() {
        return reOrderNum;
    }

    public void setReOrderNum(int reOrderNum) {
        this.reOrderNum = reOrderNum;
    }

    /**
     * 数据合法性校验
     *
     * @return
     */
    public boolean isValid(Map<String, LotteryPlaySetting> lotterySettingMap) {
        try {
            if (CollectionUtil.isEmpty(orderBetList)) {
                logger.error("下单信息为空");
                return false;
            }
            for (OrderBetRecord record : orderBetList) {
                if (null == record) {
                    logger.error("下单信息出错：空数据");
                    return false;
                }
                //基本空数据校验
                if (StringUtils.isEmpty(record.getBetNumber()) || null == record.getSettingId() || null == record.getPlayId() || null == record.getBetCount() || null == record.getBetAmount()) {
                    logger.error("下单信息出错空数据 betNumber:{}, settingId:{}, playId:{}, betCount{}, betAmount:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), record.getBetCount(), record.getBetAmount(), this.getSource());
                    return false;
                }
                //基本格式效验
                if (record.getBetNumber().startsWith("null") || record.getBetNumber().startsWith("_") || record.getBetNumber().contains("__") || !record.getBetNumber().contains("@")) {
                    logger.error("下单信息出错格式 betNumber:{}, settingId:{}, playId:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), this.getSource());
                    return false;
                }

                //接口恶意下单验证
                if (betMessageFalse(getLotteryId(), record.getPlayId(), record.getPlayName(), record.getBetNumber())) {
                    logger.error("下单信息出错 内容不对  betNumber:{}, settingId:{}, playId:{}, lotteryId:{}, playName:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), getLotteryId(), record.getPlayName());
                    return false;
                }

                //玩法和lotteryId 校验
                if (!record.getPlayId().toString().contains(getLotteryId().toString())) {
                    logger.error("下单信息出错 lotteryId,playId不对应 betNumber:{}, settingId:{}, playId:{}, lotteryId:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), getLotteryId(), this.getSource());
                    return false;
                }

                //赔率玩法对应校验
                Map<String, String> settingPlayIdMap = new HashMap<>();   //setting_id,play_id  的映射
                for (String playId : lotterySettingMap.keySet()) {
                    String settingId = lotterySettingMap.get(playId).getId().toString();
                    settingPlayIdMap.put(playId.toString(), settingId);
                }

                //下单注数效验
                if (!getTureZhushu(record)) {
                    logger.error("下单信息出错注数不对 betNumber:{}, settingId:{}, playId:{}, clientType:{}, betCount:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), this.getSource(), record.getBetCount());
                    return false;
                }

                //settingId,playId对应关系效验
                if (settingPlayIdMap.get(record.getPlayId().toString()) == null || !settingPlayIdMap.get(record.getPlayId().toString()).equals(record.getSettingId().toString())) {
                    logger.error("下单信息出错settingId,playId不对应 betNumber:{}, settingId:{}, playId:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), this.getSource());
                    return false;
                }

                //单注金额必须为整数校验 或betAmount必须为整数 校验
                int betAmount = record.getBetAmount().intValue();
                if (record.getBetAmount().doubleValue() != Double.valueOf(String.valueOf(betAmount))) {
                    logger.error("下单信息出错单注金额非整数 betNumber:{}, settingId:{}, betAmount:{}, betCount:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getBetAmount(), record.getBetCount(), this.getSource());
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("下单信息出错", e);
            return false;
        }

        return true;
    }

    //    public static void main(String[] args) {
//        System.out.println(betMessageFalse(1205,120129,"三全中","虎,虎,虎,虎,虎,虎"));
//        System.out.println(betMessageFalse(1205,120129,"三全中","虎,龙,兔,牛,狗,狗"));
//        System.out.println(betMessageFalse(1205,120129,"三全中","虎,虎,虎,虎,虎,虎"));
//    }

    //                防止接口下一些重复数字或不存在号码的单，结算异常
    //                连码
    //                三全中(15)，三中二(11)，二全中(14)，二中特(12)，特串(13)，
    //                有问题：买相同的号码,（比如买三全中3,3,3)
    //                 不中
    //                五不中(21)，六不中(22)，七不中(23)，八不中(24)，九不中(25)，十不中(26)
    //                有问题：买相同的号码，或超过范围的号码,（比如买五不中3,3,3,3,3，或51,52,53,54,55)
    //                连尾
    //                二连尾中(37)，二连尾不中(38)，三连尾中(39)，三连尾不中(40)，四连尾中(41)，四连尾不中(42)
    //                有问题：买相同的号码，或超过范围的号码 （比如买二连尾中 2,2  或二连尾不中11,12)
    //                六肖
    //                六肖连中(29)，六肖连不中(30)
    //                有问题：买相同的号码，或超过范围的号码 （比如买六肖连中虎,虎,虎,虎,虎,虎  或六肖连不中51,52,53,54,55)
    public static boolean betMessageFalse(Integer lotteryId, Integer playId, String playName, String betNumber) {
        if (!Constants.playLhcList.contains(lotteryId)) {  //非六合彩系列
            return false;
        }
        boolean isFalse = false;
        String betMessage = betNumber;
        if (!Constants.NEW_LOTTERY_ID_LIST.contains(lotteryId)) {
            betMessage = betNumber.split("@")[1];
        }
        String betNumberArray[] = betMessage.split(",");
        Set<String> numberList = new HashSet<>();
        for (String single : betNumberArray) {
            numberList.add(single);
        }
        if (numberList.size() != betNumberArray.length) {  //表示有重复的数据
            isFalse = true;
        }

        int playType1[] = {11, 12, 13, 14, 15, 21, 22, 23, 24, 25, 26};  // 连码 不中
        if (ArrayUtils.toList(playType1).contains(Integer.valueOf(playId.toString().substring(4, 6)))) {
            for (String single : betNumberArray) {
                if (Integer.valueOf(single) < 1 || Integer.valueOf(single) > 49) {
                    isFalse = true;
                    break;
                }
            }
        }
        int playType2[] = {37, 38, 39, 40, 41, 42};  // 连尾
        if (ArrayUtils.toList(playType2).contains(Integer.valueOf(playId.toString().substring(4, 6)))) {
            String weiArray[] = {"0尾", "1尾", "2尾", "3尾", "4尾", "5尾", "6尾", "7尾", "8尾", "9尾"};
            List<String> weiList = (List<String>) CollectionUtils.arrayToList(weiArray);
            for (String single : betNumberArray) {
                if (!weiList.contains(single)) {
                    isFalse = true;
                    break;
                }
            }
        }
        int playType3[] = {29, 30};  // 六肖
        if (ArrayUtils.toList(playType3).contains(Integer.valueOf(playId.toString().substring(4, 6)))) {
            for (String single : betNumberArray) {
                if (!LhcUtils.SHENGXIAO.contains(single)) {
                    isFalse = true;
                    break;
                }
            }
        }
        return isFalse;
    }

    public boolean getTureZhushu(OrderBetRecord record) {
        record.setLotteryId(Integer.valueOf(record.getPlayId().toString().substring(0, 4)));
        boolean trueZhushu = true;
        String betNumber = null;
        int zhushu = 0; //算出来的注数
        if (record.getBetNumber().contains("@")) { //老彩种
            betNumber = record.getBetNumber().split("@")[1];
        } else { //新彩种
            betNumber = record.getBetNumber();
        }
        if (record.getLotteryId().toString().substring(0, 2).equals("11") || record.getLotteryId() == 1601 || record.getLotteryId() == 2202) {  //时时彩系列+ 腾讯分分彩 + 澳洲时时彩
            //两面01 ,斗牛02,1-5球03，前中后04,第1球05,第2球06,第3球07,第4球08, 第5球09
            String lmArray[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09"};
            if (ArrayUtils.toString(lmArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            }
        } else if (record.getLotteryId().toString().substring(0, 2).equals("12")) { //六合彩系列
            //特码01，两面02，正码03，正码1-6 04，正1特-正6特 05-10，红波/蓝波/绿波16-18，全尾19，特尾20, 平特27，特肖28，1-6龙虎43，五行44
            String temaArray[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "16", "17", "18", "19", "20", "27", "28", "43", "44"};
            String eztArray[] = {"12", "13", "14", "31", "32", "37", "38"};  //二中特12,特串13,二全中，二连肖中31，二连肖不中32，二连尾中37，二连尾不中38
            String szeArray[] = {"11", "15", "33", "34", "39", "40"};  //三中二11，三全中15，三连肖中33，三连肖不中34，，三连尾中39，三连尾不中40
            String slxArray[] = {"35", "36", "41", "42"};  //四连肖中35，四连肖不中36，四连尾中41，四连尾不中42
            String wbzArray[] = {"21"};  //五不中
            String lbzArray[] = {"22", "29", "30"};  //六不中22,六肖连中29，六肖连不中30
            String qbzArray[] = {"23"};  //七不中
            String bbzArray[] = {"24"};  //八不中
            String jbzArray[] = {"25"};  //九不中
            String sbzArray[] = {"26"};  //十不中
            if (ArrayUtils.toString(temaArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            } else if (ArrayUtils.toString(eztArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 2);
            } else if (ArrayUtils.toString(szeArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 3);
            } else if (ArrayUtils.toString(slxArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 4);
            } else if (ArrayUtils.toString(wbzArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 5);
            } else if (ArrayUtils.toString(lbzArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 6);
            } else if (ArrayUtils.toString(qbzArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 7);
            } else if (ArrayUtils.toString(bbzArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 8);
            } else if (ArrayUtils.toString(jbzArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 9);
            } else if (ArrayUtils.toString(sbzArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 10);
            }
        } else if (record.getLotteryId().toString().substring(0, 2).equals("13") || record.getLotteryId() == 2203) {  //pk系列 + 澳洲f1赛车
            //冠亚和01 ,冠亚和02,1-5名03，6-10名04，冠军05，亚军06，第3名07，第4名08，第5名09，第6名10，第7名11，第8名12，第9名13，第10名14
            String gyhArray[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};
            if (ArrayUtils.toString(gyhArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            }
        } else if (record.getLotteryId().toString().substring(0, 2).equals("14")) {  //幸运飞艇系列
            //冠亚和01 ,冠亚和02,1-5名03，6-10名04，冠军05，亚军06，第3名07，第4名08，第5名09，第6名10，第7名11，第8名12，第9名13，第10名14
            String gyhArray[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};
            if (ArrayUtils.toString(gyhArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            }
        } else if (record.getLotteryId().toString().substring(0, 2).equals("15")) {  //pc蛋蛋系列
            //混合01 ,色波02,豹子03，特码05
            String hhArray[] = {"01", "02", "03", "05"};
            String bsArray[] = {"04"}; //特码包三04
            if (ArrayUtils.toString(hhArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            } else if (ArrayUtils.toString(bsArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 3);
            }
        } else if (record.getLotteryId().toString().substring(0, 2).equals("19")) {  //牛牛系列
            //牛牛系列玩法01
            String lmArray[] = {"01"};
            if (ArrayUtils.toString(lmArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            }
        } else if (record.getLotteryId().toString().substring(0, 2).equals("20")) {  //番摊系列
            //番摊系列玩法01
            String lmArray[] = {"01"};
            if (ArrayUtils.toString(lmArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            }
        } else if (record.getLotteryId() == 2201) {  //澳洲ACT
            //澳洲act玩法01
            String lmArray[] = {"01"};
            if (ArrayUtils.toString(lmArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            }
        } else if (record.getLotteryId().toString().substring(0, 2).equals("23")) {  //快三系列
            //和值01，独胆02， 连号03，二不同号04，胆拖05，单选06，复选07，三不同号08，胆拖09， 单选10，通选11
            String hzArray[] = {"01", "02", "10", "07"};
            String lhArray[] = {"03"};
            String ebthArray[] = {"04"};
            String ebthdtArray[] = {"05"};
            String ethdxArray[] = {"06"};
            String sbthdxArray[] = {"08"};
            String sbthdtdxArray[] = {"09"};
            String txArray[] = {"11"};
            if (ArrayUtils.toString(hzArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
            }
            if (ArrayUtils.toString(lhArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                if ("三连号".equals(record.getPlayName())) {
                    zhushu = 1;
                } else if ("二连号".equals(record.getPlayName())) {
                    zhushu = betNumber.split(",").length;
                }

            } else if (ArrayUtils.toString(ebthArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 2);
            } else if (ArrayUtils.toString(ebthdtArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                String qianhouArray[] = betNumber.split("_");
                String qianArray[] = qianhouArray[0].split(",");
                String houArray[] = qianhouArray[1].split(",");
                zhushu = qianArray.length * houArray.length;
            } else if (ArrayUtils.toString(ethdxArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                String qianhouArray[] = betNumber.split("_");
                String qianArray[] = qianhouArray[0].split(",");
                String houArray[] = qianhouArray[1].split(",");
                zhushu = qianArray.length * houArray.length;
            } else if (ArrayUtils.toString(sbthdxArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = betNumber.split(",").length;
                zhushu = MathUtil.getCnm(zhushu, 3);
            } else if (ArrayUtils.toString(sbthdtdxArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                //胆排列C（X，2） 乘 拖数量 + 拖C（X，2）数量  乘 胆数量
                String qianhouArray[] = betNumber.split("_");
                int qian = qianhouArray[0].split(",").length;
                int hou = qianhouArray[1].split(",").length;
                zhushu = MathUtil.getCnm(qian, 2) * hou + MathUtil.getCnm(hou, 2) * qian;
            }
            if (ArrayUtils.toString(txArray).contains(String.format("%02d", record.getPlayId() % 100))) {
                zhushu = 1;
            }
        } else {
            return true;
        }

        if (zhushu != record.getBetCount()) {
            trueZhushu = false;
        }

        return trueZhushu;
    }


    /**
     * 数据合法性校验 (新彩种)
     *
     * @return
     */
    public boolean isValidNew(Map<String, LotteryPlaySetting> lotterySettingMap) {
        try {
            if (CollectionUtil.isEmpty(orderBetList)) {
                logger.error("下单信息为空");
                return false;
            }
            for (OrderBetRecord record : orderBetList) {
                if (null == record) {
                    logger.error("下单信息出错：空数据");
                    return false;
                }
                //基本空数据校验
                if (StringUtils.isEmpty(record.getBetNumber()) || null == record.getSettingId() || null == record.getPlayId() || null == record.getBetCount() || null == record.getBetAmount()) {
                    logger.error("下单信息出错空数据 betNumber:{}, settingId:{}, playId:{}, betCount{}, betAmount:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), record.getBetCount(), record.getBetAmount(), this.getSource());
                    return false;
                }
                //基本格式效验
                //if (record.getBetNumber().startsWith("_") || record.getBetNumber().contains("__") || record.getBetNumber().contains("@") || record.getPlayName().contains("@") || StringUtils.isEmpty(record.getPlayName())) {
                if (record.getBetNumber().startsWith("_") || record.getBetNumber().contains("__") || record.getBetNumber().contains("@") ) {
                    logger.error("下单信息出错格式 betNumber:{}, settingId:{}, playId:{}, clientType:{}",
                            record.getBetNumber(),  record.getSettingId(), record.getPlayId(), this.getSource());
                    return false;
                }

                //接口恶意下单验证
                if (betMessageFalse(getLotteryId(), record.getPlayId(), record.getPlayName(), record.getBetNumber())) {
                    logger.error("下单信息出错 内容不对  betNumber:{}, settingId:{}, playId:{}, lotteryId:{}, playName:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), getLotteryId(), record.getPlayName());
                    return false;
                }

                //玩法和lotteryId 校验
                if (!record.getPlayId().toString().contains(getLotteryId().toString())) {
                    logger.error("下单信息出错 lotteryId,playId不对应 betNumber:{}, settingId:{}, playId:{}, lotteryId:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), getLotteryId(), this.getSource());
                    return false;
                }

                //赔率玩法对应校验
                Map<String, String> settingPlayIdMap = new HashMap<>();   //setting_id,play_id  的映射
                for (String playId : lotterySettingMap.keySet()) {
                    String settingId = lotterySettingMap.get(playId).getId().toString();
                    settingPlayIdMap.put(playId.toString(), settingId);
                }

                //下单注数效验
                if (!getTureZhushu(record)) {
                    logger.error("下单信息出错注数不对 betNumber:{}, settingId:{}, playId:{}, clientType:{}, betCount:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), this.getSource(), record.getBetCount());
                    return false;
                }

                // settingId,playId对应关系校验
                if (settingPlayIdMap.get(record.getPlayId().toString()) == null || !settingPlayIdMap.get(record.getPlayId().toString()).equals(record.getSettingId().toString())) {
                    logger.error("下单信息出错settingId,playId不对应 betNumber:{}, settingId:{}, playId:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getPlayId(), this.getSource());
                    return false;
                }

                //单注金额必须为整数校验 或betAmount必须为整数 校验
                int betAmount = record.getBetAmount().intValue();
                if (record.getBetAmount().doubleValue() != Double.valueOf(String.valueOf(betAmount))) {
                    logger.error("下单信息出错单注金额非整数 betNumber:{}, settingId:{}, betAmount:{}, betCount:{}, clientType:{}",
                            record.getBetNumber(), record.getSettingId(), record.getBetAmount(), record.getBetCount(), this.getSource());
                    return false;
                }

                //胆拖玩法校验，胆码拖码不能相同  NEW_LOTTERY_DT_PLAYID_LIST
                Integer playId = Integer.parseInt(record.getPlayId().toString());
                if (Constants.NEW_LOTTERY_DT_PLAYID_LIST.contains(playId)) {
                    boolean judge = KsUtils.judgeDmAndTm(record.getBetNumber());
                    if (judge) {
                        logger.error("下单信息出错胆码拖码不能相同或为空 betNumber:{}, settingId:{}, betAmount:{}, betCount:{}, clientType:{}",
                                record.getBetNumber(), record.getSettingId(), record.getBetAmount(), record.getBetCount(), this.getSource());
                        return false;
                    }
                }

                record.setPlayName(null);
            }
        } catch (Exception e) {
            logger.error("下单信息出错", e);
            return false;
        }

        return true;
    }

}
