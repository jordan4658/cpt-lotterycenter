package com.caipiao.live.common.model.vo.lottery;

import java.io.Serializable;

public class LotteryCateidVo implements Serializable {
    private Integer lotteryId;

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    private Integer categoryId;

}
