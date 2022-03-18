package com.caipiao.live.common.model.dto.order;

public class CaiConentDO {

    private Integer index;
    private String content;
    private Double oddsval;
    private String winamt;
    private String statusname;
    private Double singleamt;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getOddsval() {
        return oddsval;
    }

    public void setOddsval(Double oddsval) {
        this.oddsval = oddsval;
    }

    public String getWinamt() {
        return winamt;
    }

    public void setWinamt(String winamt) {
        this.winamt = winamt;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public Double getSingleamt() {
        return singleamt;
    }

    public void setSingleamt(Double singleamt) {
        this.singleamt = singleamt;
    }
}
