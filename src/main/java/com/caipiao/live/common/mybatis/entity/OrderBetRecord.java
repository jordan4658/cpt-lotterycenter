package com.caipiao.live.common.mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderBetRecord implements Serializable {
    /**
     * 字段: order_bet_record.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明:
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 字段: order_bet_record.user_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 字段: order_bet_record.order_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 投注单id
     *
     * @mbggenerated
     */
    private Integer orderId;

    /**
     * 字段: order_bet_record.cate_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 彩种类别id
     *
     * @mbggenerated
     */
    private Integer cateId;

    /**
     * 字段: order_bet_record.lottery_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 彩种id
     *
     * @mbggenerated
     */
    private Integer lotteryId;

    /**
     * 字段: order_bet_record.play_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 玩法id
     *
     * @mbggenerated
     */
    private Integer playId;

    /**
     * 字段: order_bet_record.setting_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 玩法配置id
     *
     * @mbggenerated
     */
    private Integer settingId;

    /**
     * 字段: order_bet_record.play_name<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 玩法名称
     *
     * @mbggenerated
     */
    private String playName;

    /**
     * 字段: order_bet_record.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 购买的期号
     *
     * @mbggenerated
     */
    private String issue;

    /**
     * 字段: order_bet_record.order_sn<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 订单号
     *
     * @mbggenerated
     */
    private String orderSn;

    /**
     * 字段: order_bet_record.bet_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 投注号码
     *
     * @mbggenerated
     */
    private String betNumber;

    /**
     * 字段: order_bet_record.bet_count<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 投注总注数
     *
     * @mbggenerated
     */
    private Integer betCount;

    /**
     * 字段: order_bet_record.bet_amount<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 投注金额
     *
     * @mbggenerated
     */
    private BigDecimal betAmount;

    /**
     * 字段: order_bet_record.win_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 20<br/>
     * 说明: 中奖金额
     *
     * @mbggenerated
     */
    private BigDecimal winAmount;

    /**
     * 字段: order_bet_record.back_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 20<br/>
     * 说明: 返点金额
     *
     * @mbggenerated
     */
    private BigDecimal backAmount;

    /**
     * 字段: order_bet_record.god_order_id<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 大神推单id, 0为自主投注
     *
     * @mbggenerated
     */
    private Integer godOrderId;

    /**
     * 字段: order_bet_record.tb_status<br/>
     * 必填: true<br/>
     * 缺省: WAIT<br/>
     * 长度: 50<br/>
     * 说明: 状态：中奖:WIN | 未中奖:NO_WIN | 等待开奖:WAIT | 和:HE | 撤单:BACK
     *
     * @mbggenerated
     */
    private String tbStatus;

    /**
     * 字段: order_bet_record.is_delete<br/>
     * 必填: true<br/>
     * 缺省: b'0'<br/>
     * 长度: 1<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    private Boolean isDelete;

    /**
     * 字段: order_bet_record.win_count<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 50<br/>
     * 说明: 中奖注数
     *
     * @mbggenerated
     */
    private String winCount;

    /**
     * 字段: order_bet_record.is_push<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否推单 0 否 1 是
     *
     * @mbggenerated
     */
    private Integer isPush;

    /**
     * 字段: order_bet_record.create_time<br/>
     * 必填: true<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 字段: order_bet_record.update_time<br/>
     * 必填: true<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 字段: order_bet_record.source<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 来源：Android | IOS | WEB
     *
     * @mbggenerated
     */
    private String source;

    /**
     * 字段: order_bet_record.familyid<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 直播间购彩对应的家族id
     *
     * @mbggenerated
     */
    private Long familyid;

    /**
     * 字段: order_bet_record.room_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 直播房间id
     *
     * @mbggenerated
     */
    private Long roomId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table order_bet_record
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return order_bet_record.id:
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: order_bet_record.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明:
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return order_bet_record.user_id: 用户id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 字段: order_bet_record.user_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return order_bet_record.order_id: 投注单id
     *
     * @mbggenerated
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 字段: order_bet_record.order_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 投注单id
     *
     * @mbggenerated
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * @return order_bet_record.cate_id: 彩种类别id
     *
     * @mbggenerated
     */
    public Integer getCateId() {
        return cateId;
    }

    /**
     * 字段: order_bet_record.cate_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 彩种类别id
     *
     * @mbggenerated
     */
    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    /**
     * @return order_bet_record.lottery_id: 彩种id
     *
     * @mbggenerated
     */
    public Integer getLotteryId() {
        return lotteryId;
    }

    /**
     * 字段: order_bet_record.lottery_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 彩种id
     *
     * @mbggenerated
     */
    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    /**
     * @return order_bet_record.play_id: 玩法id
     *
     * @mbggenerated
     */
    public Integer getPlayId() {
        return playId;
    }

    /**
     * 字段: order_bet_record.play_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 玩法id
     *
     * @mbggenerated
     */
    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    /**
     * @return order_bet_record.setting_id: 玩法配置id
     *
     * @mbggenerated
     */
    public Integer getSettingId() {
        return settingId;
    }

    /**
     * 字段: order_bet_record.setting_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 玩法配置id
     *
     * @mbggenerated
     */
    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    /**
     * @return order_bet_record.play_name: 玩法名称
     *
     * @mbggenerated
     */
    public String getPlayName() {
        return playName;
    }

    /**
     * 字段: order_bet_record.play_name<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 玩法名称
     *
     * @mbggenerated
     */
    public void setPlayName(String playName) {
        this.playName = playName;
    }

    /**
     * @return order_bet_record.issue: 购买的期号
     *
     * @mbggenerated
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 字段: order_bet_record.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 购买的期号
     *
     * @mbggenerated
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * @return order_bet_record.order_sn: 订单号
     *
     * @mbggenerated
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * 字段: order_bet_record.order_sn<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 订单号
     *
     * @mbggenerated
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * @return order_bet_record.bet_number: 投注号码
     *
     * @mbggenerated
     */
    public String getBetNumber() {
        return betNumber;
    }

    /**
     * 字段: order_bet_record.bet_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 投注号码
     *
     * @mbggenerated
     */
    public void setBetNumber(String betNumber) {
        this.betNumber = betNumber;
    }

    /**
     * @return order_bet_record.bet_count: 投注总注数
     *
     * @mbggenerated
     */
    public Integer getBetCount() {
        return betCount;
    }

    /**
     * 字段: order_bet_record.bet_count<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 投注总注数
     *
     * @mbggenerated
     */
    public void setBetCount(Integer betCount) {
        this.betCount = betCount;
    }

    /**
     * @return order_bet_record.bet_amount: 投注金额
     *
     * @mbggenerated
     */
    public BigDecimal getBetAmount() {
        return betAmount;
    }

    /**
     * 字段: order_bet_record.bet_amount<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 投注金额
     *
     * @mbggenerated
     */
    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    /**
     * @return order_bet_record.win_amount: 中奖金额
     *
     * @mbggenerated
     */
    public BigDecimal getWinAmount() {
        return winAmount;
    }

    /**
     * 字段: order_bet_record.win_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 20<br/>
     * 说明: 中奖金额
     *
     * @mbggenerated
     */
    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    /**
     * @return order_bet_record.back_amount: 返点金额
     *
     * @mbggenerated
     */
    public BigDecimal getBackAmount() {
        return backAmount;
    }

    /**
     * 字段: order_bet_record.back_amount<br/>
     * 必填: true<br/>
     * 缺省: 0.00<br/>
     * 长度: 20<br/>
     * 说明: 返点金额
     *
     * @mbggenerated
     */
    public void setBackAmount(BigDecimal backAmount) {
        this.backAmount = backAmount;
    }

    /**
     * @return order_bet_record.god_order_id: 大神推单id, 0为自主投注
     *
     * @mbggenerated
     */
    public Integer getGodOrderId() {
        return godOrderId;
    }

    /**
     * 字段: order_bet_record.god_order_id<br/>
     * 必填: true<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 大神推单id, 0为自主投注
     *
     * @mbggenerated
     */
    public void setGodOrderId(Integer godOrderId) {
        this.godOrderId = godOrderId;
    }

    /**
     * @return order_bet_record.tb_status: 状态：中奖:WIN | 未中奖:NO_WIN | 等待开奖:WAIT | 和:HE | 撤单:BACK
     *
     * @mbggenerated
     */
    public String getTbStatus() {
        return tbStatus;
    }

    /**
     * 字段: order_bet_record.tb_status<br/>
     * 必填: true<br/>
     * 缺省: WAIT<br/>
     * 长度: 50<br/>
     * 说明: 状态：中奖:WIN | 未中奖:NO_WIN | 等待开奖:WAIT | 和:HE | 撤单:BACK
     *
     * @mbggenerated
     */
    public void setTbStatus(String tbStatus) {
        this.tbStatus = tbStatus;
    }

    /**
     * @return order_bet_record.is_delete: 是否删除
     *
     * @mbggenerated
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 字段: order_bet_record.is_delete<br/>
     * 必填: true<br/>
     * 缺省: b'0'<br/>
     * 长度: 1<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * @return order_bet_record.win_count: 中奖注数
     *
     * @mbggenerated
     */
    public String getWinCount() {
        return winCount;
    }

    /**
     * 字段: order_bet_record.win_count<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 50<br/>
     * 说明: 中奖注数
     *
     * @mbggenerated
     */
    public void setWinCount(String winCount) {
        this.winCount = winCount;
    }

    /**
     * @return order_bet_record.is_push: 是否推单 0 否 1 是
     *
     * @mbggenerated
     */
    public Integer getIsPush() {
        return isPush;
    }

    /**
     * 字段: order_bet_record.is_push<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 是否推单 0 否 1 是
     *
     * @mbggenerated
     */
    public void setIsPush(Integer isPush) {
        this.isPush = isPush;
    }

    /**
     * @return order_bet_record.create_time: 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 字段: order_bet_record.create_time<br/>
     * 必填: true<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return order_bet_record.update_time: 更新时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 字段: order_bet_record.update_time<br/>
     * 必填: true<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 更新时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return order_bet_record.source: 来源：Android | IOS | WEB
     *
     * @mbggenerated
     */
    public String getSource() {
        return source;
    }

    /**
     * 字段: order_bet_record.source<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 来源：Android | IOS | WEB
     *
     * @mbggenerated
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return order_bet_record.familyid: 直播间购彩对应的家族id
     *
     * @mbggenerated
     */
    public Long getFamilyid() {
        return familyid;
    }

    /**
     * 字段: order_bet_record.familyid<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 直播间购彩对应的家族id
     *
     * @mbggenerated
     */
    public void setFamilyid(Long familyid) {
        this.familyid = familyid;
    }

    /**
     * @return order_bet_record.room_id: 直播房间id
     *
     * @mbggenerated
     */
    public Long getRoomId() {
        return roomId;
    }

    /**
     * 字段: order_bet_record.room_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 直播房间id
     *
     * @mbggenerated
     */
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_bet_record
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderBetRecord other = (OrderBetRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
                && (this.getCateId() == null ? other.getCateId() == null : this.getCateId().equals(other.getCateId()))
                && (this.getLotteryId() == null ? other.getLotteryId() == null : this.getLotteryId().equals(other.getLotteryId()))
                && (this.getPlayId() == null ? other.getPlayId() == null : this.getPlayId().equals(other.getPlayId()))
                && (this.getSettingId() == null ? other.getSettingId() == null : this.getSettingId().equals(other.getSettingId()))
                && (this.getPlayName() == null ? other.getPlayName() == null : this.getPlayName().equals(other.getPlayName()))
                && (this.getIssue() == null ? other.getIssue() == null : this.getIssue().equals(other.getIssue()))
                && (this.getOrderSn() == null ? other.getOrderSn() == null : this.getOrderSn().equals(other.getOrderSn()))
                && (this.getBetNumber() == null ? other.getBetNumber() == null : this.getBetNumber().equals(other.getBetNumber()))
                && (this.getBetCount() == null ? other.getBetCount() == null : this.getBetCount().equals(other.getBetCount()))
                && (this.getBetAmount() == null ? other.getBetAmount() == null : this.getBetAmount().equals(other.getBetAmount()))
                && (this.getWinAmount() == null ? other.getWinAmount() == null : this.getWinAmount().equals(other.getWinAmount()))
                && (this.getBackAmount() == null ? other.getBackAmount() == null : this.getBackAmount().equals(other.getBackAmount()))
                && (this.getGodOrderId() == null ? other.getGodOrderId() == null : this.getGodOrderId().equals(other.getGodOrderId()))
                && (this.getTbStatus() == null ? other.getTbStatus() == null : this.getTbStatus().equals(other.getTbStatus()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
                && (this.getWinCount() == null ? other.getWinCount() == null : this.getWinCount().equals(other.getWinCount()))
                && (this.getIsPush() == null ? other.getIsPush() == null : this.getIsPush().equals(other.getIsPush()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
                && (this.getFamilyid() == null ? other.getFamilyid() == null : this.getFamilyid().equals(other.getFamilyid()))
                && (this.getRoomId() == null ? other.getRoomId() == null : this.getRoomId().equals(other.getRoomId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_bet_record
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getCateId() == null) ? 0 : getCateId().hashCode());
        result = prime * result + ((getLotteryId() == null) ? 0 : getLotteryId().hashCode());
        result = prime * result + ((getPlayId() == null) ? 0 : getPlayId().hashCode());
        result = prime * result + ((getSettingId() == null) ? 0 : getSettingId().hashCode());
        result = prime * result + ((getPlayName() == null) ? 0 : getPlayName().hashCode());
        result = prime * result + ((getIssue() == null) ? 0 : getIssue().hashCode());
        result = prime * result + ((getOrderSn() == null) ? 0 : getOrderSn().hashCode());
        result = prime * result + ((getBetNumber() == null) ? 0 : getBetNumber().hashCode());
        result = prime * result + ((getBetCount() == null) ? 0 : getBetCount().hashCode());
        result = prime * result + ((getBetAmount() == null) ? 0 : getBetAmount().hashCode());
        result = prime * result + ((getWinAmount() == null) ? 0 : getWinAmount().hashCode());
        result = prime * result + ((getBackAmount() == null) ? 0 : getBackAmount().hashCode());
        result = prime * result + ((getGodOrderId() == null) ? 0 : getGodOrderId().hashCode());
        result = prime * result + ((getTbStatus() == null) ? 0 : getTbStatus().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getWinCount() == null) ? 0 : getWinCount().hashCode());
        result = prime * result + ((getIsPush() == null) ? 0 : getIsPush().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getFamilyid() == null) ? 0 : getFamilyid().hashCode());
        result = prime * result + ((getRoomId() == null) ? 0 : getRoomId().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_bet_record
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", cateId=").append(cateId);
        sb.append(", lotteryId=").append(lotteryId);
        sb.append(", playId=").append(playId);
        sb.append(", settingId=").append(settingId);
        sb.append(", playName=").append(playName);
        sb.append(", issue=").append(issue);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", betNumber=").append(betNumber);
        sb.append(", betCount=").append(betCount);
        sb.append(", betAmount=").append(betAmount);
        sb.append(", winAmount=").append(winAmount);
        sb.append(", backAmount=").append(backAmount);
        sb.append(", godOrderId=").append(godOrderId);
        sb.append(", tbStatus=").append(tbStatus);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", winCount=").append(winCount);
        sb.append(", isPush=").append(isPush);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", source=").append(source);
        sb.append(", familyid=").append(familyid);
        sb.append(", roomId=").append(roomId);
        sb.append("]");
        return sb.toString();
    }
}
