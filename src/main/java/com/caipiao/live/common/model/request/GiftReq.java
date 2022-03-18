package com.caipiao.live.common.model.request;

public class GiftReq {
    private Long roomid;
    private Long giftid;

    private String sendAccno;
    private String getAccno;
    private Double gold;
    private Long rewardid;

    public Long getRewardid() {
        return rewardid;
    }

    public void setRewardid(Long rewardid) {
        this.rewardid = rewardid;
    }

    public Double getGold() {
        return gold;
    }

    public void setGold(Double gold) {
        this.gold = gold;
    }

    public String getSendAccno() {
        return sendAccno;
    }

    public void setSendAccno(String sendAccno) {
        this.sendAccno = sendAccno;
    }

    public String getGetAccno() {
        return getAccno;
    }

    public void setGetAccno(String getAccno) {
        this.getAccno = getAccno;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public Long getGiftid() {
        return giftid;
    }

    public void setGiftid(Long giftid) {
        this.giftid = giftid;
    }

}
