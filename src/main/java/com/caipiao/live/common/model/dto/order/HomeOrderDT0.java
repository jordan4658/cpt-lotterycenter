package com.caipiao.live.common.model.dto.order;

import java.util.List;

/**
 * @Author: xiaomi
 * @CreateDate: 2018/11/28$ 16:52$
 * @Version: 1.0
 */
public class HomeOrderDT0 {

    private Integer id;
    //彩种名
    private String lotteryName;
    //玩法名
    private String palyName;
    //生成的号码
    private List<Integer> number;
    //玩法id
    private Integer palyId;

    public List<Integer> getNumber() {
        return number;
    }

    public void setNumber(List<Integer> number) {
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPalyId() {
        return palyId;
    }

    public void setPalyId(Integer palyId) {
        this.palyId = palyId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getPalyName() {
        return palyName;
    }

    public void setPalyName(String palyName) {
        this.palyName = palyName;
    }

}
