package com.caipiao.core.library.vo.money;

/**
 * 礼金记录统计VO
 *
 * @author lzy
 * @create 2018-07-17 14:49
 **/
public class CashGiftCountVO {

    //活动返水(首充礼金)
    private Integer firstGift = 0;

    //VIP返水
    private Integer vipBackwater = 0;

    //VIP礼金
    private Integer vipGift = 0;

    //分享礼金
    private Integer shareGift = 0;

    //活动赠送
    private Integer activitySend = 0;

    //系统入款
    private Integer system = 0;

    //体验金
    private Integer bbin = 0;

    public Integer getFirstGift() {
        return firstGift;
    }

    public void setFirstGift(Integer firstGift) {
        this.firstGift = firstGift;
    }

    public Integer getVipBackwater() {
        return vipBackwater;
    }

    public void setVipBackwater(Integer vipBackwater) {
        this.vipBackwater = vipBackwater;
    }

    public Integer getVipGift() {
        return vipGift;
    }

    public void setVipGift(Integer vipGift) {
        this.vipGift = vipGift;
    }

    public Integer getShareGift() {
        return shareGift;
    }

    public void setShareGift(Integer shareGift) {
        this.shareGift = shareGift;
    }

    public Integer getActivitySend() {
        return activitySend;
    }

    public void setActivitySend(Integer activitySend) {
        this.activitySend = activitySend;
    }

    public Integer getSystem() {
        return system;
    }

    public void setSystem(Integer system) {
        this.system = system;
    }

    public Integer getBbin() {
        return bbin;
    }

    public void setBbin(Integer bbin) {
        this.bbin = bbin;
    }
}
