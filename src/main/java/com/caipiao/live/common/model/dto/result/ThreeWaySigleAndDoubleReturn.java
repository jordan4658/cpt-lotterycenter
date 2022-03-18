package com.caipiao.live.common.model.dto.result;

import java.util.List;

/**
 * 六合彩下三路资讯的返回值DTO
 *
 * @author HANS
 * @create 2019-12-02 18:10
 **/
public class ThreeWaySigleAndDoubleReturn {
    //单总数
    private Integer sigleTotal;
    //双总数
    private Integer doubleTotal;
    //和总数
    private Integer heTotal;
    //结果组
    private List<String> list;

    public Integer getSigleTotal() {
        return sigleTotal;
    }

    public void setSigleTotal(Integer sigleTotal) {
        this.sigleTotal = sigleTotal;
    }

    public Integer getDoubleTotal() {
        return doubleTotal;
    }

    public void setDoubleTotal(Integer doubleTotal) {
        this.doubleTotal = doubleTotal;
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
