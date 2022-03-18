package com.caipiao.live.common.model.dto.order;

import com.caipiao.live.common.model.common.PageBaseInfo;
import com.caipiao.live.common.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderBetDTO  extends PageBaseInfo {
    /**
     * 【请求参数】
     */
    private Integer userId; // 用户id
    private Date date; // 投注日期
    private String type; // 投注类型：NORMAL 投注 | BACK 撤单
    private String status; // 状态：WIN 中奖 | NO_WIN 未中奖 | HE 打和 | WAIT 等待开奖
    private List<Integer> lotteryIds; // 彩种id集合
    private String sortName; // 排序字段名称：bet_amount:投注金额 | win_amount:中奖金额 | create_time:投注时间
    private String sortType; // 排序方式：ASC:顺序 | DESC:倒序
    private Integer id; // 投注id
    private String issue; // 期号
    private String lotteryName; // 彩种名称
    private String playName; // 玩法名称
    private String odds; // 赔率
    private Integer orderId; // 订单id
    private String orderSn; // 订单号
    private Integer lotteryId; // 彩种id
    private Integer playId; // 玩法id
    private Integer settingId; // 配置id
    private String betNumber; // 投注号码
    private String openNumber; // 开奖号码
    private Integer betCount; // 投注注数
    private BigDecimal betAmount; // 投注总额
    private String betAmountIos; // 投注总额
    private BigDecimal winAmount; // 中奖金额
    private BigDecimal backAmount; // 返点金额
    private String tbStatus; // 状态
    @JsonFormat(pattern = DateUtils.FORMAT_YYYY_MM_DD_HHMMSS, timezone = "GMT+8")
    private Date createTime; // 投注时间
    @JsonFormat(pattern = DateUtils.FORMAT_YYYY_MM_DD_HHMMSS, timezone = "GMT+8")
    private Date updateTime; // 更新时间
    private String winCount; // 中奖注数

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }
    public String getBetAmountIos() {
        return betAmountIos;
    }

    public void setBetAmountIos(String betAmountIos) {
        this.betAmountIos = betAmountIos;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getLotteryIds() {
        return lotteryIds;
    }

    public void setLotteryIds(List<Integer> lotteryIds) {
        this.lotteryIds = lotteryIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public String getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(String betNumber) {
        this.betNumber = betNumber;
    }

    public Integer getBetCount() {
        return betCount;
    }

    public void setBetCount(Integer betCount) {
        this.betCount = betCount;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public BigDecimal getBackAmount() {
        return backAmount;
    }

    public void setBackAmount(BigDecimal backAmount) {
        this.backAmount = backAmount;
    }

    public String getTbStatus() {
        return tbStatus;
    }

    public void setTbStatus(String tbStatus) {
        this.tbStatus = tbStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOpenNumber() {
        return openNumber;
    }

    public void setOpenNumber(String openNumber) {
        this.openNumber = openNumber;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getWinCount() {
        return winCount;
    }

    public void setWinCount(String winCount) {
        this.winCount = winCount;
    }

}
