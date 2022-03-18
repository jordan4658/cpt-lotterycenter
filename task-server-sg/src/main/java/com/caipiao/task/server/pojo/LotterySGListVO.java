package com.caipiao.task.server.pojo;

/**
 * 开奖的彩票
 */
public class LotterySGListVO {
    private Integer lotteryId;

    private String tableName;

    public LotterySGListVO() {
    }

    public LotterySGListVO(String tableName, Integer lotteryId) {
        this.tableName = tableName;
        this.lotteryId = lotteryId;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "LotterySGListVO{" +
                "lotteryId='" + lotteryId + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
