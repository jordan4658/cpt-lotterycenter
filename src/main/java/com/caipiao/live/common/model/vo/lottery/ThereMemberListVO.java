package com.caipiao.live.common.model.vo.lottery;

import java.util.List;

/**
 * @author lzy
 * @create 2018-07-31 16:15
 **/
public class ThereMemberListVO {
    private Integer numA;
    private Integer numB;
    private List<String> list;

    public ThereMemberListVO() {
    }

    public ThereMemberListVO(Integer numA, Integer numB, List<String> list) {
        this.numA = numA;
        this.numB = numB;
        this.list = list;
    }

    public Integer getNumA() {
        return numA;
    }

    public void setNumA(Integer numA) {
        this.numA = numA;
    }

    public Integer getNumB() {
        return numB;
    }

    public void setNumB(Integer numB) {
        this.numB = numB;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}

