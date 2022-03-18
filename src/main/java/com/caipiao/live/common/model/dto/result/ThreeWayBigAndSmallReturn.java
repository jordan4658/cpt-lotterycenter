package com.caipiao.live.common.model.dto.result;

import java.util.List;

/**
 * 六合彩下三路资讯的返回值DTO
 *
 * @author HANS
 * @create 2019-12-02 18:10
 **/
public class ThreeWayBigAndSmallReturn {
    private Integer bigTotal;
    private Integer smallTotal;
    private Integer heTotal;
    private List<String> list;

    public Integer getBigTotal() {
        return bigTotal;
    }

    public void setBigTotal(Integer bigTotal) {
        this.bigTotal = bigTotal;
    }

    public Integer getSmallTotal() {
        return smallTotal;
    }

    public void setSmallTotal(Integer smallTotal) {
        this.smallTotal = smallTotal;
    }

    public Integer getHeTotal() {
        return heTotal;
    }

    public void setHeTotal(Integer heTotal) {
        this.heTotal = heTotal;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
