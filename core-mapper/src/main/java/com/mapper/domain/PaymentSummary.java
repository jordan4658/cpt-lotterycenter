package com.mapper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentSummary implements Serializable {
    /**
     * 字段: payment_summary.id<br/>
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
     * 字段: payment_summary.order_no<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 订单号
     *
     * @mbggenerated
     */
    private String orderNo;

    /**
     * 字段: payment_summary.user_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 字段: payment_summary.way<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 支付类型：1线下支付2人工入款3线上支付
     *
     * @mbggenerated
     */
    private Integer way;

    /**
     * 字段: payment_summary.amount<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 支付金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 字段: payment_summary.real_amount<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 实际支付金额
     *
     * @mbggenerated
     */
    private BigDecimal realAmount;

    /**
     * 字段: payment_summary.fee<br/>
     * 必填: false<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 手续费
     *
     * @mbggenerated
     */
    private BigDecimal fee;

    /**
     * 字段: payment_summary.source<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 来源
     *
     * @mbggenerated
     */
    private String source;

    /**
     * 字段: payment_summary.status<br/>
     * 必填: true<br/>
     * 缺省: 3<br/>
     * 长度: 10<br/>
     * 说明: 支付状态：1成功2失败3等待支付
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 字段: payment_summary.type_detail<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private String typeDetail;

    /**
     * 字段: payment_summary.type_status<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private String typeStatus;

    /**
     * 字段: payment_summary.reserve<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private String reserve;

    /**
     * 字段: payment_summary.check_user<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 操作者
     *
     * @mbggenerated
     */
    private String checkUser;

    /**
     * 字段: payment_summary.check_info<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 审核信息
     *
     * @mbggenerated
     */
    private String checkInfo;

    /**
     * 字段: payment_summary.check_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 审核时间
     *
     * @mbggenerated
     */
    private Date checkTime;

    /**
     * 字段: payment_summary.remark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 字段: payment_summary.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table payment_summary
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return payment_summary.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: payment_summary.id<br/>
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
     * @return payment_summary.order_no: 订单号
     *
     * @mbggenerated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 字段: payment_summary.order_no<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 订单号
     *
     * @mbggenerated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return payment_summary.user_id: 用户id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 字段: payment_summary.user_id<br/>
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
     * @return payment_summary.way: 支付类型：1线下支付2人工入款3线上支付
     *
     * @mbggenerated
     */
    public Integer getWay() {
        return way;
    }

    /**
     * 字段: payment_summary.way<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 支付类型：1线下支付2人工入款3线上支付
     *
     * @mbggenerated
     */
    public void setWay(Integer way) {
        this.way = way;
    }

    /**
     * @return payment_summary.amount: 支付金额
     *
     * @mbggenerated
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 字段: payment_summary.amount<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 支付金额
     *
     * @mbggenerated
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return payment_summary.real_amount: 实际支付金额
     *
     * @mbggenerated
     */
    public BigDecimal getRealAmount() {
        return realAmount;
    }

    /**
     * 字段: payment_summary.real_amount<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 实际支付金额
     *
     * @mbggenerated
     */
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    /**
     * @return payment_summary.fee: 手续费
     *
     * @mbggenerated
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * 字段: payment_summary.fee<br/>
     * 必填: false<br/>
     * 缺省: 0.00<br/>
     * 长度: 10<br/>
     * 说明: 手续费
     *
     * @mbggenerated
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * @return payment_summary.source: 来源
     *
     * @mbggenerated
     */
    public String getSource() {
        return source;
    }

    /**
     * 字段: payment_summary.source<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 来源
     *
     * @mbggenerated
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return payment_summary.status: 支付状态：1成功2失败3等待支付
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 字段: payment_summary.status<br/>
     * 必填: true<br/>
     * 缺省: 3<br/>
     * 长度: 10<br/>
     * 说明: 支付状态：1成功2失败3等待支付
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return payment_summary.type_detail: 
     *
     * @mbggenerated
     */
    public String getTypeDetail() {
        return typeDetail;
    }

    /**
     * 字段: payment_summary.type_detail<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setTypeDetail(String typeDetail) {
        this.typeDetail = typeDetail;
    }

    /**
     * @return payment_summary.type_status: 
     *
     * @mbggenerated
     */
    public String getTypeStatus() {
        return typeStatus;
    }

    /**
     * 字段: payment_summary.type_status<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setTypeStatus(String typeStatus) {
        this.typeStatus = typeStatus;
    }

    /**
     * @return payment_summary.reserve: 
     *
     * @mbggenerated
     */
    public String getReserve() {
        return reserve;
    }

    /**
     * 字段: payment_summary.reserve<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 100<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    /**
     * @return payment_summary.check_user: 操作者
     *
     * @mbggenerated
     */
    public String getCheckUser() {
        return checkUser;
    }

    /**
     * 字段: payment_summary.check_user<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 操作者
     *
     * @mbggenerated
     */
    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    /**
     * @return payment_summary.check_info: 审核信息
     *
     * @mbggenerated
     */
    public String getCheckInfo() {
        return checkInfo;
    }

    /**
     * 字段: payment_summary.check_info<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 审核信息
     *
     * @mbggenerated
     */
    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    /**
     * @return payment_summary.check_time: 审核时间
     *
     * @mbggenerated
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * 字段: payment_summary.check_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 审核时间
     *
     * @mbggenerated
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * @return payment_summary.remark: 备注
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 字段: payment_summary.remark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 备注
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return payment_summary.create_time: 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 字段: payment_summary.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_summary
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
        PaymentSummary other = (PaymentSummary) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getWay() == null ? other.getWay() == null : this.getWay().equals(other.getWay()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getRealAmount() == null ? other.getRealAmount() == null : this.getRealAmount().equals(other.getRealAmount()))
            && (this.getFee() == null ? other.getFee() == null : this.getFee().equals(other.getFee()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTypeDetail() == null ? other.getTypeDetail() == null : this.getTypeDetail().equals(other.getTypeDetail()))
            && (this.getTypeStatus() == null ? other.getTypeStatus() == null : this.getTypeStatus().equals(other.getTypeStatus()))
            && (this.getReserve() == null ? other.getReserve() == null : this.getReserve().equals(other.getReserve()))
            && (this.getCheckUser() == null ? other.getCheckUser() == null : this.getCheckUser().equals(other.getCheckUser()))
            && (this.getCheckInfo() == null ? other.getCheckInfo() == null : this.getCheckInfo().equals(other.getCheckInfo()))
            && (this.getCheckTime() == null ? other.getCheckTime() == null : this.getCheckTime().equals(other.getCheckTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_summary
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getWay() == null) ? 0 : getWay().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getRealAmount() == null) ? 0 : getRealAmount().hashCode());
        result = prime * result + ((getFee() == null) ? 0 : getFee().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTypeDetail() == null) ? 0 : getTypeDetail().hashCode());
        result = prime * result + ((getTypeStatus() == null) ? 0 : getTypeStatus().hashCode());
        result = prime * result + ((getReserve() == null) ? 0 : getReserve().hashCode());
        result = prime * result + ((getCheckUser() == null) ? 0 : getCheckUser().hashCode());
        result = prime * result + ((getCheckInfo() == null) ? 0 : getCheckInfo().hashCode());
        result = prime * result + ((getCheckTime() == null) ? 0 : getCheckTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_summary
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
        sb.append(", orderNo=").append(orderNo);
        sb.append(", userId=").append(userId);
        sb.append(", way=").append(way);
        sb.append(", amount=").append(amount);
        sb.append(", realAmount=").append(realAmount);
        sb.append(", fee=").append(fee);
        sb.append(", source=").append(source);
        sb.append(", status=").append(status);
        sb.append(", typeDetail=").append(typeDetail);
        sb.append(", typeStatus=").append(typeStatus);
        sb.append(", reserve=").append(reserve);
        sb.append(", checkUser=").append(checkUser);
        sb.append(", checkInfo=").append(checkInfo);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}