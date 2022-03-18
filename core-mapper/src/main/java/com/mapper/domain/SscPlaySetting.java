package com.mapper.domain;

import java.io.Serializable;

public class SscPlaySetting implements Serializable {
    /**
     * 字段: ssc_play_setting.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 字段: ssc_play_setting.ssc_type<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 类型
     *
     * @mbggenerated
     */
    private String sscType;

    /**
     * 字段: ssc_play_setting.ssc_plan<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 方案
     *
     * @mbggenerated
     */
    private String sscPlan;

    /**
     * 字段: ssc_play_setting.ssc_play_name<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 玩法名称
     *
     * @mbggenerated
     */
    private String sscPlayName;

    /**
     * 字段: ssc_play_setting.ssc_combination_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 组合编号
     *
     * @mbggenerated
     */
    private String sscCombinationNumber;

    /**
     * 字段: ssc_play_setting.total_count<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 总注数
     *
     * @mbggenerated
     */
    private String totalCount;

    /**
     * 字段: ssc_play_setting.win_count<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 中奖注数
     *
     * @mbggenerated
     */
    private String winCount;

    /**
     * 字段: ssc_play_setting.beat<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 赔率
     *
     * @mbggenerated
     */
    private String beat;

    /**
     * 字段: ssc_play_setting.ssc_return<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 反水
     *
     * @mbggenerated
     */
    private String sscReturn;

    /**
     * 字段: ssc_play_setting.singel_money<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 单注金额
     *
     * @mbggenerated
     */
    private Long singelMoney;

    /**
     * 字段: ssc_play_setting.ssc_exampl<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 示例
     *
     * @mbggenerated
     */
    private String sscExampl;

    /**
     * 字段: ssc_play_setting.ssc_play_remark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 玩法说明
     *
     * @mbggenerated
     */
    private String sscPlayRemark;

    /**
     * 字段: ssc_play_setting.ssc_play_remark_sx<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 玩法简要说明
     *
     * @mbggenerated
     */
    private String sscPlayRemarkSx;

    /**
     * 字段: ssc_play_setting.ssc_reward<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 奖级
     *
     * @mbggenerated
     */
    private String sscReward;

    /**
     * 字段: ssc_play_setting.ssc_play_sx<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 玩法缩写
     *
     * @mbggenerated
     */
    private String sscPlaySx;

    /**
     * 字段: ssc_play_setting.ssc_return_status<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 反水状态
     *
     * @mbggenerated
     */
    private Integer sscReturnStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ssc_play_setting
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return ssc_play_setting.id: 
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * 字段: ssc_play_setting.id<br/>
     * 主键: 自动增长<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return ssc_play_setting.ssc_type: 类型
     *
     * @mbggenerated
     */
    public String getSscType() {
        return sscType;
    }

    /**
     * 字段: ssc_play_setting.ssc_type<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 类型
     *
     * @mbggenerated
     */
    public void setSscType(String sscType) {
        this.sscType = sscType;
    }

    /**
     * @return ssc_play_setting.ssc_plan: 方案
     *
     * @mbggenerated
     */
    public String getSscPlan() {
        return sscPlan;
    }

    /**
     * 字段: ssc_play_setting.ssc_plan<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 方案
     *
     * @mbggenerated
     */
    public void setSscPlan(String sscPlan) {
        this.sscPlan = sscPlan;
    }

    /**
     * @return ssc_play_setting.ssc_play_name: 玩法名称
     *
     * @mbggenerated
     */
    public String getSscPlayName() {
        return sscPlayName;
    }

    /**
     * 字段: ssc_play_setting.ssc_play_name<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 玩法名称
     *
     * @mbggenerated
     */
    public void setSscPlayName(String sscPlayName) {
        this.sscPlayName = sscPlayName;
    }

    /**
     * @return ssc_play_setting.ssc_combination_number: 组合编号
     *
     * @mbggenerated
     */
    public String getSscCombinationNumber() {
        return sscCombinationNumber;
    }

    /**
     * 字段: ssc_play_setting.ssc_combination_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 组合编号
     *
     * @mbggenerated
     */
    public void setSscCombinationNumber(String sscCombinationNumber) {
        this.sscCombinationNumber = sscCombinationNumber;
    }

    /**
     * @return ssc_play_setting.total_count: 总注数
     *
     * @mbggenerated
     */
    public String getTotalCount() {
        return totalCount;
    }

    /**
     * 字段: ssc_play_setting.total_count<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 总注数
     *
     * @mbggenerated
     */
    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return ssc_play_setting.win_count: 中奖注数
     *
     * @mbggenerated
     */
    public String getWinCount() {
        return winCount;
    }

    /**
     * 字段: ssc_play_setting.win_count<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 中奖注数
     *
     * @mbggenerated
     */
    public void setWinCount(String winCount) {
        this.winCount = winCount;
    }

    /**
     * @return ssc_play_setting.beat: 赔率
     *
     * @mbggenerated
     */
    public String getBeat() {
        return beat;
    }

    /**
     * 字段: ssc_play_setting.beat<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 赔率
     *
     * @mbggenerated
     */
    public void setBeat(String beat) {
        this.beat = beat;
    }

    /**
     * @return ssc_play_setting.ssc_return: 反水
     *
     * @mbggenerated
     */
    public String getSscReturn() {
        return sscReturn;
    }

    /**
     * 字段: ssc_play_setting.ssc_return<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 反水
     *
     * @mbggenerated
     */
    public void setSscReturn(String sscReturn) {
        this.sscReturn = sscReturn;
    }

    /**
     * @return ssc_play_setting.singel_money: 单注金额
     *
     * @mbggenerated
     */
    public Long getSingelMoney() {
        return singelMoney;
    }

    /**
     * 字段: ssc_play_setting.singel_money<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 单注金额
     *
     * @mbggenerated
     */
    public void setSingelMoney(Long singelMoney) {
        this.singelMoney = singelMoney;
    }

    /**
     * @return ssc_play_setting.ssc_exampl: 示例
     *
     * @mbggenerated
     */
    public String getSscExampl() {
        return sscExampl;
    }

    /**
     * 字段: ssc_play_setting.ssc_exampl<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 示例
     *
     * @mbggenerated
     */
    public void setSscExampl(String sscExampl) {
        this.sscExampl = sscExampl;
    }

    /**
     * @return ssc_play_setting.ssc_play_remark: 玩法说明
     *
     * @mbggenerated
     */
    public String getSscPlayRemark() {
        return sscPlayRemark;
    }

    /**
     * 字段: ssc_play_setting.ssc_play_remark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 玩法说明
     *
     * @mbggenerated
     */
    public void setSscPlayRemark(String sscPlayRemark) {
        this.sscPlayRemark = sscPlayRemark;
    }

    /**
     * @return ssc_play_setting.ssc_play_remark_sx: 玩法简要说明
     *
     * @mbggenerated
     */
    public String getSscPlayRemarkSx() {
        return sscPlayRemarkSx;
    }

    /**
     * 字段: ssc_play_setting.ssc_play_remark_sx<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 玩法简要说明
     *
     * @mbggenerated
     */
    public void setSscPlayRemarkSx(String sscPlayRemarkSx) {
        this.sscPlayRemarkSx = sscPlayRemarkSx;
    }

    /**
     * @return ssc_play_setting.ssc_reward: 奖级
     *
     * @mbggenerated
     */
    public String getSscReward() {
        return sscReward;
    }

    /**
     * 字段: ssc_play_setting.ssc_reward<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 奖级
     *
     * @mbggenerated
     */
    public void setSscReward(String sscReward) {
        this.sscReward = sscReward;
    }

    /**
     * @return ssc_play_setting.ssc_play_sx: 玩法缩写
     *
     * @mbggenerated
     */
    public String getSscPlaySx() {
        return sscPlaySx;
    }

    /**
     * 字段: ssc_play_setting.ssc_play_sx<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 500<br/>
     * 说明: 玩法缩写
     *
     * @mbggenerated
     */
    public void setSscPlaySx(String sscPlaySx) {
        this.sscPlaySx = sscPlaySx;
    }

    /**
     * @return ssc_play_setting.ssc_return_status: 反水状态
     *
     * @mbggenerated
     */
    public Integer getSscReturnStatus() {
        return sscReturnStatus;
    }

    /**
     * 字段: ssc_play_setting.ssc_return_status<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 反水状态
     *
     * @mbggenerated
     */
    public void setSscReturnStatus(Integer sscReturnStatus) {
        this.sscReturnStatus = sscReturnStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ssc_play_setting
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
        SscPlaySetting other = (SscPlaySetting) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSscType() == null ? other.getSscType() == null : this.getSscType().equals(other.getSscType()))
            && (this.getSscPlan() == null ? other.getSscPlan() == null : this.getSscPlan().equals(other.getSscPlan()))
            && (this.getSscPlayName() == null ? other.getSscPlayName() == null : this.getSscPlayName().equals(other.getSscPlayName()))
            && (this.getSscCombinationNumber() == null ? other.getSscCombinationNumber() == null : this.getSscCombinationNumber().equals(other.getSscCombinationNumber()))
            && (this.getTotalCount() == null ? other.getTotalCount() == null : this.getTotalCount().equals(other.getTotalCount()))
            && (this.getWinCount() == null ? other.getWinCount() == null : this.getWinCount().equals(other.getWinCount()))
            && (this.getBeat() == null ? other.getBeat() == null : this.getBeat().equals(other.getBeat()))
            && (this.getSscReturn() == null ? other.getSscReturn() == null : this.getSscReturn().equals(other.getSscReturn()))
            && (this.getSingelMoney() == null ? other.getSingelMoney() == null : this.getSingelMoney().equals(other.getSingelMoney()))
            && (this.getSscExampl() == null ? other.getSscExampl() == null : this.getSscExampl().equals(other.getSscExampl()))
            && (this.getSscPlayRemark() == null ? other.getSscPlayRemark() == null : this.getSscPlayRemark().equals(other.getSscPlayRemark()))
            && (this.getSscPlayRemarkSx() == null ? other.getSscPlayRemarkSx() == null : this.getSscPlayRemarkSx().equals(other.getSscPlayRemarkSx()))
            && (this.getSscReward() == null ? other.getSscReward() == null : this.getSscReward().equals(other.getSscReward()))
            && (this.getSscPlaySx() == null ? other.getSscPlaySx() == null : this.getSscPlaySx().equals(other.getSscPlaySx()))
            && (this.getSscReturnStatus() == null ? other.getSscReturnStatus() == null : this.getSscReturnStatus().equals(other.getSscReturnStatus()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ssc_play_setting
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSscType() == null) ? 0 : getSscType().hashCode());
        result = prime * result + ((getSscPlan() == null) ? 0 : getSscPlan().hashCode());
        result = prime * result + ((getSscPlayName() == null) ? 0 : getSscPlayName().hashCode());
        result = prime * result + ((getSscCombinationNumber() == null) ? 0 : getSscCombinationNumber().hashCode());
        result = prime * result + ((getTotalCount() == null) ? 0 : getTotalCount().hashCode());
        result = prime * result + ((getWinCount() == null) ? 0 : getWinCount().hashCode());
        result = prime * result + ((getBeat() == null) ? 0 : getBeat().hashCode());
        result = prime * result + ((getSscReturn() == null) ? 0 : getSscReturn().hashCode());
        result = prime * result + ((getSingelMoney() == null) ? 0 : getSingelMoney().hashCode());
        result = prime * result + ((getSscExampl() == null) ? 0 : getSscExampl().hashCode());
        result = prime * result + ((getSscPlayRemark() == null) ? 0 : getSscPlayRemark().hashCode());
        result = prime * result + ((getSscPlayRemarkSx() == null) ? 0 : getSscPlayRemarkSx().hashCode());
        result = prime * result + ((getSscReward() == null) ? 0 : getSscReward().hashCode());
        result = prime * result + ((getSscPlaySx() == null) ? 0 : getSscPlaySx().hashCode());
        result = prime * result + ((getSscReturnStatus() == null) ? 0 : getSscReturnStatus().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ssc_play_setting
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
        sb.append(", sscType=").append(sscType);
        sb.append(", sscPlan=").append(sscPlan);
        sb.append(", sscPlayName=").append(sscPlayName);
        sb.append(", sscCombinationNumber=").append(sscCombinationNumber);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", winCount=").append(winCount);
        sb.append(", beat=").append(beat);
        sb.append(", sscReturn=").append(sscReturn);
        sb.append(", singelMoney=").append(singelMoney);
        sb.append(", sscExampl=").append(sscExampl);
        sb.append(", sscPlayRemark=").append(sscPlayRemark);
        sb.append(", sscPlayRemarkSx=").append(sscPlayRemarkSx);
        sb.append(", sscReward=").append(sscReward);
        sb.append(", sscPlaySx=").append(sscPlaySx);
        sb.append(", sscReturnStatus=").append(sscReturnStatus);
        sb.append("]");
        return sb.toString();
    }
}