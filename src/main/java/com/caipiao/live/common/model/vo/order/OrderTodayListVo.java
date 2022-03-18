package com.caipiao.live.common.model.vo.order;

import java.util.Objects;

public class OrderTodayListVo {

    private String todayEarnAmount;//当日盈亏
    private String todayWinAmount;//当日中奖金额
    private String todayHasSettle;//当日已结算投注金额
    private String todayNoSettle;//当日未结投注金额

    public String getTodayEarnAmount() {
        return todayEarnAmount;
    }

    public void setTodayEarnAmount(String todayEarnAmount) {
        this.todayEarnAmount = todayEarnAmount;
    }

    public String getTodayWinAmount() {
        return todayWinAmount;
    }

    public void setTodayWinAmount(String todayWinAmount) {
        this.todayWinAmount = todayWinAmount;
    }

    public String getTodayHasSettle() {
        return todayHasSettle;
    }

    public void setTodayHasSettle(String todayHasSettle) {
        this.todayHasSettle = todayHasSettle;
    }

    public String getTodayNoSettle() {
        return todayNoSettle;
    }

    public void setTodayNoSettle(String todayNoSettle) {
        this.todayNoSettle = todayNoSettle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderTodayListVo that = (OrderTodayListVo) o;
        return Objects.equals(todayEarnAmount, that.todayEarnAmount) &&
                Objects.equals(todayWinAmount, that.todayWinAmount) &&
                Objects.equals(todayHasSettle, that.todayHasSettle) &&
                Objects.equals(todayNoSettle, that.todayNoSettle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todayEarnAmount, todayWinAmount, todayHasSettle, todayNoSettle);
    }
}
