package com.caipiao.live.common.enums.threeway;

/**
 * @ClassName: ThreeWayConversionUtils
 * @Description: 下三路玩法统计玩法枚举
 * @author: HANS
 * @date: 2019年6月11日 下午4:11:13
 */
public enum ThreeWayTypeNumEnum {
    SPECIAL_SINGLE_DOUBLE(1, "特码&两面单双"),
    POSITIVE_SINGLE_DOUBLE(2, "正码&总单双"),
    POSITIVE_ONE_SINGLE_DOUBLE(3, "正码一&两面单双"),
    POSITIVE_TWO_SINGLE_DOUBLE(4, "正码二&两面单双"),
    POSITIVE_THREE_SINGLE_DOUBLE(5, "正码三&两面单双"),
    POSITIVE_FOUR_SINGLE_DOUBLE(6, "正码四&两面单双"),
    POSITIVE_FIVE_SINGLE_DOUBLE(7, "正码五&两面单双"),
    POSITIVE_SIX_SINGLE_DOUBLE(8, "正码六&两面单双"),
    SPECIAL_TOTAL_SINGLE_DOUBLE(9, "特码&两面合单合双"),
    POSITIVE_ONE_TOTAL_SINGLE_DOUBLE(10, "正码一&合单合双"),
    POSITIVE_TWO_TOTAL_SINGLE_DOUBLE(11, "正码二&合单合双"),
    POSITIVE_THREE_TOTAL_SINGLE_DOUBLE(12, "正码三&合单合双"),
    POSITIVE_FOUR_TOTAL_SINGLE_DOUBLE(13, "正码四&合单和双"),
    POSITIVE_FIVE_TOTAL_SINGLE_DOUBLE(14, "正码五&合单合双"),
    POSITIVE_SIX_TOTAL_SINGLE_DOUBLE(15, "正码六&合单合双"),
    SPECIAL_SMALL_BIG(16, "特码&两面大小"),
    POSITIVE_TOTAL_SMALL_BIG(17, "正码&总大小"),
    POSITIVE_ONE_SMALL_BIG(18, "正码一&两面大小"),
    POSITIVE_TWO_SMALL_BIG(19, "正码二&两面大小"),
    POSITIVE_THREE_SMALL_BIG(20, "正码三&两面大小"),
    POSITIVE_FOUR_SMALL_BIG(21, "正码四&两面大小"),
    POSITIVE_FIVE_SMALL_BIG(22, "正码五&两面大小"),
    POSITIVE_SIX_SMALL_BIG(23, "正码六&两面大小"),
    SPECIAL_TAIL_SMALL_BIG(24, "特码&两面尾大尾小"),
    POSITIVE_ONE_TAIL_SMALL_BIG(25, "正码一&两面尾大尾小"),
    POSITIVE_TWO_TAIL_SMALL_BIG(26, "正码二&两面尾大尾小"),
    POSITIVE_THREE_TAIL_SMALL_BIG(27, "正码三&两面尾大尾小"),
    POSITIVE_FOUR_TAIL_SMALL_BIG(28, "正码四&两面尾大尾小"),
    POSITIVE_FIVE_TAIL_SMALL_BIG(29, "正码五&两面尾大尾小"),
    POSITIVE_SIX_TAIL_SMALL_BIG(30, "正码六&两面尾大尾小"),
    POSITIVE_TAIL_SMALL_BIG(31, "正码&总尾大总尾小"),
    POSITIVE_DRAGON_TIGER(32, "正码&龙虎"),
    SPECIAL_ANIMAL(33, "特码&两面家禽野兽");

    private Integer value;
    private String desc;

    ThreeWayTypeNumEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
