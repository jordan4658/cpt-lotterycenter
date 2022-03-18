package com.mapper.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CircleGod implements Serializable {
    /**
     * 字段: circle_god.id<br/>
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
     * 字段: circle_god.member_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    private Integer memberId;

    /**
     * 字段: circle_god.account<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 用户名
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 字段: circle_god.calc_profit_rate<br/>
     * 必填: false<br/>
     * 缺省: 0.0<br/>
     * 长度: 10<br/>
     * 说明: 近一周盈利率
     *
     * @mbggenerated
     */
    private BigDecimal calcProfitRate;

    /**
     * 字段: circle_god.show_profit_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示盈利率
     *
     * @mbggenerated
     */
    private BigDecimal showProfitRate;

    /**
     * 字段: circle_god.calc_max_lz<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 近一周最大连中
     *
     * @mbggenerated
     */
    private Integer calcMaxLz;

    /**
     * 字段: circle_god.show_max_lz<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示最大连中
     *
     * @mbggenerated
     */
    private Integer showMaxLz;

    /**
     * 字段: circle_god.calc_win_rate<br/>
     * 必填: false<br/>
     * 缺省: 0.0<br/>
     * 长度: 10<br/>
     * 说明: 近一周胜率
     *
     * @mbggenerated
     */
    private BigDecimal calcWinRate;

    /**
     * 字段: circle_god.show_win_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示胜率
     *
     * @mbggenerated
     */
    private BigDecimal showWinRate;

    /**
     * 字段: circle_god.push_order_status<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 推单状态：1正常2禁止
     *
     * @mbggenerated
     */
    private Integer pushOrderStatus;

    /**
     * 字段: circle_god.personal_content<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 800<br/>
     * 说明: 个人中心展示内容
     *
     * @mbggenerated
     */
    private String personalContent;

    /**
     * 字段: circle_god.calc_jzj<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 统计几中几(得出胜率)
     *
     * @mbggenerated
     */
    private String calcJzj;

    /**
     * 字段: circle_god.update_time<br/>
     * 必填: false<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 统计胜率时的时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 字段: circle_god.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private Date replyTime; //申请时间
    private Date recentlyPushOrderTime; //最近推单时间
    private Integer fansNumber;     //粉丝数

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Date getRecentlyPushOrderTime() {
        return recentlyPushOrderTime;
    }

    public void setRecentlyPushOrderTime(Date recentlyPushOrderTime) {
        this.recentlyPushOrderTime = recentlyPushOrderTime;
    }

    public Integer getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(Integer fansNumber) {
        this.fansNumber = fansNumber;
    }

    /**
     * 字段: circle_god.is_delete<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Integer isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table circle_god
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return circle_god.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: circle_god.id<br/>
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
     * @return circle_god.member_id: 用户id
     *
     * @mbggenerated
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 字段: circle_god.member_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 用户id
     *
     * @mbggenerated
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * @return circle_god.account: 用户名
     *
     * @mbggenerated
     */
    public String getAccount() {
        return account;
    }

    /**
     * 字段: circle_god.account<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 用户名
     *
     * @mbggenerated
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return circle_god.calc_profit_rate: 近一周盈利率
     *
     * @mbggenerated
     */
    public BigDecimal getCalcProfitRate() {
        return calcProfitRate;
    }

    /**
     * 字段: circle_god.calc_profit_rate<br/>
     * 必填: false<br/>
     * 缺省: 0.0<br/>
     * 长度: 10<br/>
     * 说明: 近一周盈利率
     *
     * @mbggenerated
     */
    public void setCalcProfitRate(BigDecimal calcProfitRate) {
        this.calcProfitRate = calcProfitRate;
    }

    /**
     * @return circle_god.show_profit_rate: 展示盈利率
     *
     * @mbggenerated
     */
    public BigDecimal getShowProfitRate() {
        return showProfitRate;
    }

    /**
     * 字段: circle_god.show_profit_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示盈利率
     *
     * @mbggenerated
     */
    public void setShowProfitRate(BigDecimal showProfitRate) {
        this.showProfitRate = showProfitRate;
    }

    /**
     * @return circle_god.calc_max_lz: 近一周最大连中
     *
     * @mbggenerated
     */
    public Integer getCalcMaxLz() {
        return calcMaxLz;
    }

    /**
     * 字段: circle_god.calc_max_lz<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 近一周最大连中
     *
     * @mbggenerated
     */
    public void setCalcMaxLz(Integer calcMaxLz) {
        this.calcMaxLz = calcMaxLz;
    }

    /**
     * @return circle_god.show_max_lz: 展示最大连中
     *
     * @mbggenerated
     */
    public Integer getShowMaxLz() {
        return showMaxLz;
    }

    /**
     * 字段: circle_god.show_max_lz<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示最大连中
     *
     * @mbggenerated
     */
    public void setShowMaxLz(Integer showMaxLz) {
        this.showMaxLz = showMaxLz;
    }

    /**
     * @return circle_god.calc_win_rate: 近一周胜率
     *
     * @mbggenerated
     */
    public BigDecimal getCalcWinRate() {
        return calcWinRate;
    }

    /**
     * 字段: circle_god.calc_win_rate<br/>
     * 必填: false<br/>
     * 缺省: 0.0<br/>
     * 长度: 10<br/>
     * 说明: 近一周胜率
     *
     * @mbggenerated
     */
    public void setCalcWinRate(BigDecimal calcWinRate) {
        this.calcWinRate = calcWinRate;
    }

    /**
     * @return circle_god.show_win_rate: 展示胜率
     *
     * @mbggenerated
     */
    public BigDecimal getShowWinRate() {
        return showWinRate;
    }

    /**
     * 字段: circle_god.show_win_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 展示胜率
     *
     * @mbggenerated
     */
    public void setShowWinRate(BigDecimal showWinRate) {
        this.showWinRate = showWinRate;
    }

    /**
     * @return circle_god.push_order_status: 推单状态：1正常2禁止
     *
     * @mbggenerated
     */
    public Integer getPushOrderStatus() {
        return pushOrderStatus;
    }

    /**
     * 字段: circle_god.push_order_status<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 推单状态：1正常2禁止
     *
     * @mbggenerated
     */
    public void setPushOrderStatus(Integer pushOrderStatus) {
        this.pushOrderStatus = pushOrderStatus;
    }

    /**
     * @return circle_god.personal_content: 个人中心展示内容
     *
     * @mbggenerated
     */
    public String getPersonalContent() {
        return personalContent;
    }

    /**
     * 字段: circle_god.personal_content<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 800<br/>
     * 说明: 个人中心展示内容
     *
     * @mbggenerated
     */
    public void setPersonalContent(String personalContent) {
        this.personalContent = personalContent;
    }

    /**
     * @return circle_god.calc_jzj: 统计几中几(得出胜率)
     *
     * @mbggenerated
     */
    public String getCalcJzj() {
        return calcJzj;
    }

    /**
     * 字段: circle_god.calc_jzj<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 统计几中几(得出胜率)
     *
     * @mbggenerated
     */
    public void setCalcJzj(String calcJzj) {
        this.calcJzj = calcJzj;
    }

    /**
     * @return circle_god.update_time: 统计胜率时的时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 字段: circle_god.update_time<br/>
     * 必填: false<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 统计胜率时的时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return circle_god.create_time: 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 字段: circle_god.create_time<br/>
     * 必填: true<br/>
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
     * @return circle_god.is_delete: 
     *
     * @mbggenerated
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 字段: circle_god.is_delete<br/>
     * 必填: false<br/>
     * 缺省: 0<br/>
     * 长度: 10<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table circle_god
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
        CircleGod other = (CircleGod) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getCalcProfitRate() == null ? other.getCalcProfitRate() == null : this.getCalcProfitRate().equals(other.getCalcProfitRate()))
            && (this.getShowProfitRate() == null ? other.getShowProfitRate() == null : this.getShowProfitRate().equals(other.getShowProfitRate()))
            && (this.getCalcMaxLz() == null ? other.getCalcMaxLz() == null : this.getCalcMaxLz().equals(other.getCalcMaxLz()))
            && (this.getShowMaxLz() == null ? other.getShowMaxLz() == null : this.getShowMaxLz().equals(other.getShowMaxLz()))
            && (this.getCalcWinRate() == null ? other.getCalcWinRate() == null : this.getCalcWinRate().equals(other.getCalcWinRate()))
            && (this.getShowWinRate() == null ? other.getShowWinRate() == null : this.getShowWinRate().equals(other.getShowWinRate()))
            && (this.getPushOrderStatus() == null ? other.getPushOrderStatus() == null : this.getPushOrderStatus().equals(other.getPushOrderStatus()))
            && (this.getPersonalContent() == null ? other.getPersonalContent() == null : this.getPersonalContent().equals(other.getPersonalContent()))
            && (this.getCalcJzj() == null ? other.getCalcJzj() == null : this.getCalcJzj().equals(other.getCalcJzj()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table circle_god
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getCalcProfitRate() == null) ? 0 : getCalcProfitRate().hashCode());
        result = prime * result + ((getShowProfitRate() == null) ? 0 : getShowProfitRate().hashCode());
        result = prime * result + ((getCalcMaxLz() == null) ? 0 : getCalcMaxLz().hashCode());
        result = prime * result + ((getShowMaxLz() == null) ? 0 : getShowMaxLz().hashCode());
        result = prime * result + ((getCalcWinRate() == null) ? 0 : getCalcWinRate().hashCode());
        result = prime * result + ((getShowWinRate() == null) ? 0 : getShowWinRate().hashCode());
        result = prime * result + ((getPushOrderStatus() == null) ? 0 : getPushOrderStatus().hashCode());
        result = prime * result + ((getPersonalContent() == null) ? 0 : getPersonalContent().hashCode());
        result = prime * result + ((getCalcJzj() == null) ? 0 : getCalcJzj().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table circle_god
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
        sb.append(", memberId=").append(memberId);
        sb.append(", account=").append(account);
        sb.append(", calcProfitRate=").append(calcProfitRate);
        sb.append(", showProfitRate=").append(showProfitRate);
        sb.append(", calcMaxLz=").append(calcMaxLz);
        sb.append(", showMaxLz=").append(showMaxLz);
        sb.append(", calcWinRate=").append(calcWinRate);
        sb.append(", showWinRate=").append(showWinRate);
        sb.append(", pushOrderStatus=").append(pushOrderStatus);
        sb.append(", personalContent=").append(personalContent);
        sb.append(", calcJzj=").append(calcJzj);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}