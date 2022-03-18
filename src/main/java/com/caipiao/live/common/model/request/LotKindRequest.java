package com.caipiao.live.common.model.request;

public class LotKindRequest {

    // 彩种
    private Long lotkindid;

    // 玩法
    private Long lotruleid;

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    private Integer roomid;

    public Integer getPlatformno() {
        return platformno;
    }

    public void setPlatformno(Integer platformno) {
        this.platformno = platformno;
    }

    private Integer platformno;

    //private List<BasRuleoddsDO> ruleoddsList;
    private String ruleoddsList;

    public String getRuleoddsList() {
        return ruleoddsList;
    }

    public void setRuleoddsList(String ruleoddsList) {
        this.ruleoddsList = ruleoddsList;
    }

    public Long getLotruleid() {
        return lotruleid;
    }

    public void setLotruleid(Long lotruleid) {
        this.lotruleid = lotruleid;
    }

    public Long getLotkindid() {
        return lotkindid;
    }

    public void setLotkindid(Long lotkindid) {
        this.lotkindid = lotkindid;
    }
}
