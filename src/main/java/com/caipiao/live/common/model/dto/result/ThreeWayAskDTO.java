package com.caipiao.live.common.model.dto.result;

/**
 * 六合彩下三路资讯的DTO
 *
 * @author HANS
 * @create 2019-12-02 18:10
 **/
public class ThreeWayAskDTO {
    private String year; //年份
    private Integer playType;//玩法类型
    private Integer wayName;//玩法类型
    private Integer typeNum; //玩法类型

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public Integer getWayName() {
        return wayName;
    }

    public void setWayName(Integer wayName) {
        this.wayName = wayName;
    }

    public Integer getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(Integer typeNum) {
        this.typeNum = typeNum;
    }
}
