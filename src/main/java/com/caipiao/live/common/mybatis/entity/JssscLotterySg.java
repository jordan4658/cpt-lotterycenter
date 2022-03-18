package com.caipiao.live.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

public class JssscLotterySg implements Serializable {
    /**
     * 字段: jsssc_lottery_sg.id<br/>
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
     * 字段: jsssc_lottery_sg.date<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 日期
     *
     * @mbggenerated
     */
    private String date;

    /**
     * 字段: jsssc_lottery_sg.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    private String issue;

    /**
     * 字段: jsssc_lottery_sg.wan<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 万位
     *
     * @mbggenerated
     */
    private Integer wan;

    /**
     * 字段: jsssc_lottery_sg.qian<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 千位
     *
     * @mbggenerated
     */
    private Integer qian;

    /**
     * 字段: jsssc_lottery_sg.bai<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 百位
     *
     * @mbggenerated
     */
    private Integer bai;

    /**
     * 字段: jsssc_lottery_sg.shi<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 十位
     *
     * @mbggenerated
     */
    private Integer shi;

    /**
     * 字段: jsssc_lottery_sg.ge<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 个位
     *
     * @mbggenerated
     */
    private Integer ge;

    /**
     * 字段: jsssc_lottery_sg.number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 开奖结果
     *
     * @mbggenerated
     */
    private String number;

    /**
     * 字段: jsssc_lottery_sg.time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 实际开奖时间
     *
     * @mbggenerated
     */
    private String time;

    /**
     * 字段: jsssc_lottery_sg.ideal_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 官方开奖时间
     *
     * @mbggenerated
     */
    private String idealTime;

    /**
     * 字段: jsssc_lottery_sg.open_status<br/>
     * 必填: true<br/>
     * 缺省: WAIT<br/>
     * 长度: 50<br/>
     * 说明: 状态：WAIT 等待开奖 | AUTO 自动开奖 | HANDLE 手动开奖
     *
     * @mbggenerated
     */
    private String openStatus;

    /**
     * 字段: jsssc_lottery_sg.ideal_date<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 官方开奖时间
     *
     * @mbggenerated
     */
    private Date idealDate;

    /**
     * 字段: jsssc_lottery_sg.actual_date<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 实际开奖时间
     *
     * @mbggenerated
     */
    private Date actualDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table jsssc_lottery_sg
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return jsssc_lottery_sg.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: jsssc_lottery_sg.id<br/>
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
     * @return jsssc_lottery_sg.date: 日期
     *
     * @mbggenerated
     */
    public String getDate() {
        return date;
    }

    /**
     * 字段: jsssc_lottery_sg.date<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 日期
     *
     * @mbggenerated
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return jsssc_lottery_sg.issue: 期号
     *
     * @mbggenerated
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 字段: jsssc_lottery_sg.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * @return jsssc_lottery_sg.wan: 万位
     *
     * @mbggenerated
     */
    public Integer getWan() {
        return wan;
    }

    /**
     * 字段: jsssc_lottery_sg.wan<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 万位
     *
     * @mbggenerated
     */
    public void setWan(Integer wan) {
        this.wan = wan;
    }

    /**
     * @return jsssc_lottery_sg.qian: 千位
     *
     * @mbggenerated
     */
    public Integer getQian() {
        return qian;
    }

    /**
     * 字段: jsssc_lottery_sg.qian<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 千位
     *
     * @mbggenerated
     */
    public void setQian(Integer qian) {
        this.qian = qian;
    }

    /**
     * @return jsssc_lottery_sg.bai: 百位
     *
     * @mbggenerated
     */
    public Integer getBai() {
        return bai;
    }

    /**
     * 字段: jsssc_lottery_sg.bai<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 百位
     *
     * @mbggenerated
     */
    public void setBai(Integer bai) {
        this.bai = bai;
    }

    /**
     * @return jsssc_lottery_sg.shi: 十位
     *
     * @mbggenerated
     */
    public Integer getShi() {
        return shi;
    }

    /**
     * 字段: jsssc_lottery_sg.shi<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 十位
     *
     * @mbggenerated
     */
    public void setShi(Integer shi) {
        this.shi = shi;
    }

    /**
     * @return jsssc_lottery_sg.ge: 个位
     *
     * @mbggenerated
     */
    public Integer getGe() {
        return ge;
    }

    /**
     * 字段: jsssc_lottery_sg.ge<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 个位
     *
     * @mbggenerated
     */
    public void setGe(Integer ge) {
        this.ge = ge;
    }

    /**
     * @return jsssc_lottery_sg.number: 开奖结果
     *
     * @mbggenerated
     */
    public String getNumber() {
        return number;
    }

    /**
     * 字段: jsssc_lottery_sg.number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 开奖结果
     *
     * @mbggenerated
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return jsssc_lottery_sg.time: 实际开奖时间
     *
     * @mbggenerated
     */
    public String getTime() {
        return time;
    }

    /**
     * 字段: jsssc_lottery_sg.time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 实际开奖时间
     *
     * @mbggenerated
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return jsssc_lottery_sg.ideal_time: 官方开奖时间
     *
     * @mbggenerated
     */
    public String getIdealTime() {
        return idealTime;
    }

    /**
     * 字段: jsssc_lottery_sg.ideal_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 官方开奖时间
     *
     * @mbggenerated
     */
    public void setIdealTime(String idealTime) {
        this.idealTime = idealTime;
    }

    /**
     * @return jsssc_lottery_sg.open_status: 状态：WAIT 等待开奖 | AUTO 自动开奖 | HANDLE 手动开奖
     *
     * @mbggenerated
     */
    public String getOpenStatus() {
        return openStatus;
    }

    /**
     * 字段: jsssc_lottery_sg.open_status<br/>
     * 必填: true<br/>
     * 缺省: WAIT<br/>
     * 长度: 50<br/>
     * 说明: 状态：WAIT 等待开奖 | AUTO 自动开奖 | HANDLE 手动开奖
     *
     * @mbggenerated
     */
    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }

    /**
     * @return jsssc_lottery_sg.ideal_date: 官方开奖时间
     *
     * @mbggenerated
     */
    public Date getIdealDate() {
        return idealDate;
    }

    /**
     * 字段: jsssc_lottery_sg.ideal_date<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 官方开奖时间
     *
     * @mbggenerated
     */
    public void setIdealDate(Date idealDate) {
        this.idealDate = idealDate;
    }

    /**
     * @return jsssc_lottery_sg.actual_date: 实际开奖时间
     *
     * @mbggenerated
     */
    public Date getActualDate() {
        return actualDate;
    }

    /**
     * 字段: jsssc_lottery_sg.actual_date<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 实际开奖时间
     *
     * @mbggenerated
     */
    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_lottery_sg
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
        JssscLotterySg other = (JssscLotterySg) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getIssue() == null ? other.getIssue() == null : this.getIssue().equals(other.getIssue()))
            && (this.getWan() == null ? other.getWan() == null : this.getWan().equals(other.getWan()))
            && (this.getQian() == null ? other.getQian() == null : this.getQian().equals(other.getQian()))
            && (this.getBai() == null ? other.getBai() == null : this.getBai().equals(other.getBai()))
            && (this.getShi() == null ? other.getShi() == null : this.getShi().equals(other.getShi()))
            && (this.getGe() == null ? other.getGe() == null : this.getGe().equals(other.getGe()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getIdealTime() == null ? other.getIdealTime() == null : this.getIdealTime().equals(other.getIdealTime()))
            && (this.getOpenStatus() == null ? other.getOpenStatus() == null : this.getOpenStatus().equals(other.getOpenStatus()))
            && (this.getIdealDate() == null ? other.getIdealDate() == null : this.getIdealDate().equals(other.getIdealDate()))
            && (this.getActualDate() == null ? other.getActualDate() == null : this.getActualDate().equals(other.getActualDate()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_lottery_sg
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getIssue() == null) ? 0 : getIssue().hashCode());
        result = prime * result + ((getWan() == null) ? 0 : getWan().hashCode());
        result = prime * result + ((getQian() == null) ? 0 : getQian().hashCode());
        result = prime * result + ((getBai() == null) ? 0 : getBai().hashCode());
        result = prime * result + ((getShi() == null) ? 0 : getShi().hashCode());
        result = prime * result + ((getGe() == null) ? 0 : getGe().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getIdealTime() == null) ? 0 : getIdealTime().hashCode());
        result = prime * result + ((getOpenStatus() == null) ? 0 : getOpenStatus().hashCode());
        result = prime * result + ((getIdealDate() == null) ? 0 : getIdealDate().hashCode());
        result = prime * result + ((getActualDate() == null) ? 0 : getActualDate().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsssc_lottery_sg
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
        sb.append(", date=").append(date);
        sb.append(", issue=").append(issue);
        sb.append(", wan=").append(wan);
        sb.append(", qian=").append(qian);
        sb.append(", bai=").append(bai);
        sb.append(", shi=").append(shi);
        sb.append(", ge=").append(ge);
        sb.append(", number=").append(number);
        sb.append(", time=").append(time);
        sb.append(", idealTime=").append(idealTime);
        sb.append(", openStatus=").append(openStatus);
        sb.append(", idealDate=").append(idealDate);
        sb.append(", actualDate=").append(actualDate);
        sb.append("]");
        return sb.toString();
    }
}
