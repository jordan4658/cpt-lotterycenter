package com.caipiao.live.common.mybatis.entity;

public class LotteryRequest {

    // 彩种
    private Long lotkindid;

    // 玩法
    private Long lotruleid;


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
