package com.mapper.domain;

import java.io.Serializable;

public class CqsscKillNumber implements Serializable {
    /**
     * 字段: cqssc_kill_number.id<br/>
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
     * 字段: cqssc_kill_number.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    private String issue;

    /**
     * 字段: cqssc_kill_number.open_one<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第一球开奖结果
     *
     * @mbggenerated
     */
    private Integer openOne;

    /**
     * 字段: cqssc_kill_number.ball_one<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    private String ballOne;

    /**
     * 字段: cqssc_kill_number.open_two<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第二球开奖结果
     *
     * @mbggenerated
     */
    private Integer openTwo;

    /**
     * 字段: cqssc_kill_number.ball_two<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    private String ballTwo;

    /**
     * 字段: cqssc_kill_number.open_three<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第三球开奖结果
     *
     * @mbggenerated
     */
    private Integer openThree;

    /**
     * 字段: cqssc_kill_number.ball_three<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    private String ballThree;

    /**
     * 字段: cqssc_kill_number.open_four<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第四球开奖结果
     *
     * @mbggenerated
     */
    private Integer openFour;

    /**
     * 字段: cqssc_kill_number.ball_four<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第四球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    private String ballFour;

    /**
     * 字段: cqssc_kill_number.open_five<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第五球开奖结果
     *
     * @mbggenerated
     */
    private Integer openFive;

    /**
     * 字段: cqssc_kill_number.ball_five<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第五球杀号
     *
     * @mbggenerated
     */
    private String ballFive;

    /**
     * 字段: cqssc_kill_number.time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 时间
     *
     * @mbggenerated
     */
    private String time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table cqssc_kill_number
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return cqssc_kill_number.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: cqssc_kill_number.id<br/>
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
     * @return cqssc_kill_number.issue: 期号
     *
     * @mbggenerated
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 字段: cqssc_kill_number.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * @return cqssc_kill_number.open_one: 第一球开奖结果
     *
     * @mbggenerated
     */
    public Integer getOpenOne() {
        return openOne;
    }

    /**
     * 字段: cqssc_kill_number.open_one<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第一球开奖结果
     *
     * @mbggenerated
     */
    public void setOpenOne(Integer openOne) {
        this.openOne = openOne;
    }

    /**
     * @return cqssc_kill_number.ball_one: 第一球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    public String getBallOne() {
        return ballOne;
    }

    /**
     * 字段: cqssc_kill_number.ball_one<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    public void setBallOne(String ballOne) {
        this.ballOne = ballOne;
    }

    /**
     * @return cqssc_kill_number.open_two: 第二球开奖结果
     *
     * @mbggenerated
     */
    public Integer getOpenTwo() {
        return openTwo;
    }

    /**
     * 字段: cqssc_kill_number.open_two<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第二球开奖结果
     *
     * @mbggenerated
     */
    public void setOpenTwo(Integer openTwo) {
        this.openTwo = openTwo;
    }

    /**
     * @return cqssc_kill_number.ball_two: 第二球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    public String getBallTwo() {
        return ballTwo;
    }

    /**
     * 字段: cqssc_kill_number.ball_two<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    public void setBallTwo(String ballTwo) {
        this.ballTwo = ballTwo;
    }

    /**
     * @return cqssc_kill_number.open_three: 第三球开奖结果
     *
     * @mbggenerated
     */
    public Integer getOpenThree() {
        return openThree;
    }

    /**
     * 字段: cqssc_kill_number.open_three<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第三球开奖结果
     *
     * @mbggenerated
     */
    public void setOpenThree(Integer openThree) {
        this.openThree = openThree;
    }

    /**
     * @return cqssc_kill_number.ball_three: 第三球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    public String getBallThree() {
        return ballThree;
    }

    /**
     * 字段: cqssc_kill_number.ball_three<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    public void setBallThree(String ballThree) {
        this.ballThree = ballThree;
    }

    /**
     * @return cqssc_kill_number.open_four: 第四球开奖结果
     *
     * @mbggenerated
     */
    public Integer getOpenFour() {
        return openFour;
    }

    /**
     * 字段: cqssc_kill_number.open_four<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第四球开奖结果
     *
     * @mbggenerated
     */
    public void setOpenFour(Integer openFour) {
        this.openFour = openFour;
    }

    /**
     * @return cqssc_kill_number.ball_four: 第四球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    public String getBallFour() {
        return ballFour;
    }

    /**
     * 字段: cqssc_kill_number.ball_four<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第四球杀号（sin,sec,cos,cot,tan）
     *
     * @mbggenerated
     */
    public void setBallFour(String ballFour) {
        this.ballFour = ballFour;
    }

    /**
     * @return cqssc_kill_number.open_five: 第五球开奖结果
     *
     * @mbggenerated
     */
    public Integer getOpenFive() {
        return openFive;
    }

    /**
     * 字段: cqssc_kill_number.open_five<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 第五球开奖结果
     *
     * @mbggenerated
     */
    public void setOpenFive(Integer openFive) {
        this.openFive = openFive;
    }

    /**
     * @return cqssc_kill_number.ball_five: 第五球杀号
     *
     * @mbggenerated
     */
    public String getBallFive() {
        return ballFive;
    }

    /**
     * 字段: cqssc_kill_number.ball_five<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第五球杀号
     *
     * @mbggenerated
     */
    public void setBallFive(String ballFive) {
        this.ballFive = ballFive;
    }

    /**
     * @return cqssc_kill_number.time: 时间
     *
     * @mbggenerated
     */
    public String getTime() {
        return time;
    }

    /**
     * 字段: cqssc_kill_number.time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 时间
     *
     * @mbggenerated
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_kill_number
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
        CqsscKillNumber other = (CqsscKillNumber) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIssue() == null ? other.getIssue() == null : this.getIssue().equals(other.getIssue()))
            && (this.getOpenOne() == null ? other.getOpenOne() == null : this.getOpenOne().equals(other.getOpenOne()))
            && (this.getBallOne() == null ? other.getBallOne() == null : this.getBallOne().equals(other.getBallOne()))
            && (this.getOpenTwo() == null ? other.getOpenTwo() == null : this.getOpenTwo().equals(other.getOpenTwo()))
            && (this.getBallTwo() == null ? other.getBallTwo() == null : this.getBallTwo().equals(other.getBallTwo()))
            && (this.getOpenThree() == null ? other.getOpenThree() == null : this.getOpenThree().equals(other.getOpenThree()))
            && (this.getBallThree() == null ? other.getBallThree() == null : this.getBallThree().equals(other.getBallThree()))
            && (this.getOpenFour() == null ? other.getOpenFour() == null : this.getOpenFour().equals(other.getOpenFour()))
            && (this.getBallFour() == null ? other.getBallFour() == null : this.getBallFour().equals(other.getBallFour()))
            && (this.getOpenFive() == null ? other.getOpenFive() == null : this.getOpenFive().equals(other.getOpenFive()))
            && (this.getBallFive() == null ? other.getBallFive() == null : this.getBallFive().equals(other.getBallFive()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_kill_number
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIssue() == null) ? 0 : getIssue().hashCode());
        result = prime * result + ((getOpenOne() == null) ? 0 : getOpenOne().hashCode());
        result = prime * result + ((getBallOne() == null) ? 0 : getBallOne().hashCode());
        result = prime * result + ((getOpenTwo() == null) ? 0 : getOpenTwo().hashCode());
        result = prime * result + ((getBallTwo() == null) ? 0 : getBallTwo().hashCode());
        result = prime * result + ((getOpenThree() == null) ? 0 : getOpenThree().hashCode());
        result = prime * result + ((getBallThree() == null) ? 0 : getBallThree().hashCode());
        result = prime * result + ((getOpenFour() == null) ? 0 : getOpenFour().hashCode());
        result = prime * result + ((getBallFour() == null) ? 0 : getBallFour().hashCode());
        result = prime * result + ((getOpenFive() == null) ? 0 : getOpenFive().hashCode());
        result = prime * result + ((getBallFive() == null) ? 0 : getBallFive().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cqssc_kill_number
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
        sb.append(", issue=").append(issue);
        sb.append(", openOne=").append(openOne);
        sb.append(", ballOne=").append(ballOne);
        sb.append(", openTwo=").append(openTwo);
        sb.append(", ballTwo=").append(ballTwo);
        sb.append(", openThree=").append(openThree);
        sb.append(", ballThree=").append(ballThree);
        sb.append(", openFour=").append(openFour);
        sb.append(", ballFour=").append(ballFour);
        sb.append(", openFive=").append(openFive);
        sb.append(", ballFive=").append(ballFive);
        sb.append(", time=").append(time);
        sb.append("]");
        return sb.toString();
    }
}