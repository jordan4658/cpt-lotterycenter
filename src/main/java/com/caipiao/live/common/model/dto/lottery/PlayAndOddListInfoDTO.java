package com.caipiao.live.common.model.dto.lottery;

/**
 * ClassName:    PlayAndOddListInfoDTO
 * Package:    com.caipiao.live.common.model.dto.lottery
 * Description:
 * Datetime:    2020/5/8   14:52
 * Author:   木鱼
 */
public class PlayAndOddListInfoDTO {

    private String caipiaoName;
    private String  lotteryPlayName;
    private String  caipiaoType;
    private String  cachKey;
    private String  zhengTeName;

    public String getCaipiaoName() {
        return caipiaoName;
    }

    public void setCaipiaoName(String caipiaoName) {
        this.caipiaoName = caipiaoName;
    }

    public String getLotteryPlayName() {
        return lotteryPlayName;
    }

    public void setLotteryPlayName(String lotteryPlayName) {
        this.lotteryPlayName = lotteryPlayName;
    }

    public String getCaipiaoType() {
        return caipiaoType;
    }

    public void setCaipiaoType(String caipiaoType) {
        this.caipiaoType = caipiaoType;
    }

    public String getCachKey() {
        return cachKey;
    }

    public void setCachKey(String cachKey) {
        this.cachKey = cachKey;
    }

    public String getZhengTeName() {
        return zhengTeName;
    }

    public void setZhengTeName(String zhengTeName) {
        this.zhengTeName = zhengTeName;
    }
}
