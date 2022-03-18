package com.caipiao.live.common.model.dto.result;

import java.util.List;

/**
 * 六合彩下三路资讯的返回值DTO
 *
 * @author HANS
 * @create 2019-12-02 18:10
 **/
public class ThreeWayAskReturn {
    //返回总数
    private Integer listNum;
    //结果组
    private List<String> list;

    public Integer getListNum() {
        return listNum;
    }

    public void setListNum(Integer listNum) {
        this.listNum = listNum;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
