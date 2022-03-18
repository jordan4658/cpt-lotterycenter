package com.caipiao.live.common.enums.lottery;

import com.caipiao.live.common.mybatis.entity.LotteryCategory;
import com.caipiao.live.common.util.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 彩种枚举
 */
public enum LotteryCategoryEnum {
    /** 时时彩 */
    SSC(11, "时时彩", 1, 1, true),
    /** 时时彩系列 */
    SSCXL(11, "时时彩系列", 0, 1, true),
    /** 六合彩系列 */
    LHCXL(12, "六合彩系列", 0, 1, true),
    /** PK拾系列 */
    PK10XL(13, "PK拾系列", 0, 1, true),
    /** 幸运飞艇系列 */
    XYFTXL(14, "幸运飞艇系列", 0, 1, true),
    /** PC蛋蛋系列 */
    PCAGGXL(15, "PC蛋蛋系列", 0, 1, true),
    /** 分分彩系列 */
    FFCXL(16, "分分彩系列", 0, 1, true),
    /** 体彩系列 */
    TCXL(17, "体彩系列", 0, 1, true),
    /** 福彩系列 */
    FCXL(18, "福彩系列", 0, 1, true),
    /** 牛牛系列 */
    NNXL(19, "牛牛系列", 0, 1, true),
    /** 番摊专区 */
    TFZQ(20, "番摊专区", 0, 1, true),
    /** 澳洲ACT */
    AZACT(21, "澳洲ACT", 1, 1, true),
    /** 澳洲系列 */
    AZXL(22, "澳洲系列", 0, 1, true),
    /** 快三系列 */
    KSXL(23, "快三系列", 0, 1, true),
    /** 长龙资讯 */
    CLZX(99, "长龙资讯", 0, 1, true),
    /** 棋牌类 */
    QIPAI(100, "棋牌类", 0, 1, false, LotteryTypeEnum.QIPAI.name()),
    /** 真人视讯 */
    ZRSX(200, "真人视讯", 0, 1, false, LotteryTypeEnum.ZRSX.name()),
    /** 足彩 */
    ZUCAI(300, "足彩", 0, 0, false, LotteryTypeEnum.ZUCAI.name());

    private Integer id;
    private int isDelete;
    private int isWork;
    private String name;
    private boolean isLottery;
    private String type;

    LotteryCategoryEnum(Integer id, String name, int isDelete, int isWork, boolean isLottery, String... type) {
        this.id = id;
        this.name = name;
        this.isDelete = isDelete;
        this.isWork = isWork;
        this.isLottery = isLottery;
        if (null == type || type.length == 0 || StringUtils.isEmpty(type[0])) {
            this.type = LotteryTypeEnum.LOTTERY.name();
        } else {
            this.type = type[0];
        }
    }

    public static List<Integer> getLotteryCategoryIdsByType(List<LotteryCategory> lotteries, String type) {
        if (CollectionUtils.isEmpty(lotteries) || StringUtils.isEmpty(type)) {
            return null;
        }
        return lotteries
                .parallelStream()
                .filter(item -> type.equals(item.getType()))
                .map(item -> item.getCategoryId()).collect(Collectors.toList());
    }


    /**
     * 直播彩种类别id,不包含福彩,体彩,三方游戏系列
     */
    public static final List<Integer> getColorCategory() {
        List<Integer> list = new ArrayList<>();
        list.add(SSCXL.id);
        list.add(LHCXL.id);
        list.add(PK10XL.id);
        list.add(XYFTXL.id);
        list.add(PCAGGXL.id);
        list.add(FFCXL.id);
        list.add(TFZQ.id);
        list.add(AZXL.id);
        list.add(KSXL.id);
        return list;
    }




    public Integer getId() {
        return id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public int getIsWork() {
        return isWork;
    }

    public String getName() {
        return name;
    }

    public boolean isLottery() {
        return isLottery;
    }

    public String getType() {
        return type;
    }
}
