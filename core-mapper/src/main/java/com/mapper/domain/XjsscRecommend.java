package com.mapper.domain;

import java.io.Serializable;

public class XjsscRecommend implements Serializable {
    /**
     * 字段: xjssc_recommend.id<br/>
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
     * 字段: xjssc_recommend.issue<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 期号
     *
     * @mbggenerated
     */
    private String issue;

    /**
     * 字段: xjssc_recommend.open_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 开奖结果
     *
     * @mbggenerated
     */
    private String openNumber;

    /**
     * 字段: xjssc_recommend.ball_one_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一球推荐号码
     *
     * @mbggenerated
     */
    private String ballOneNumber;

    /**
     * 字段: xjssc_recommend.ball_one_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一球推荐单双
     *
     * @mbggenerated
     */
    private String ballOneSingle;

    /**
     * 字段: xjssc_recommend.ball_one_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一球推荐大小
     *
     * @mbggenerated
     */
    private String ballOneSize;

    /**
     * 字段: xjssc_recommend.ball_two_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二球推荐号码
     *
     * @mbggenerated
     */
    private String ballTwoNumber;

    /**
     * 字段: xjssc_recommend.ball_two_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二球推荐单双
     *
     * @mbggenerated
     */
    private String ballTwoSingle;

    /**
     * 字段: xjssc_recommend.ball_two_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二球推荐大小
     *
     * @mbggenerated
     */
    private String ballTwoSize;

    /**
     * 字段: xjssc_recommend.ball_three_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三球推荐号码
     *
     * @mbggenerated
     */
    private String ballThreeNumber;

    /**
     * 字段: xjssc_recommend.ball_three_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三球推荐单双
     *
     * @mbggenerated
     */
    private String ballThreeSingle;

    /**
     * 字段: xjssc_recommend.ball_three_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三球推荐大小
     *
     * @mbggenerated
     */
    private String ballThreeSize;

    /**
     * 字段: xjssc_recommend.ball_four_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第四球推荐号码
     *
     * @mbggenerated
     */
    private String ballFourNumber;

    /**
     * 字段: xjssc_recommend.ball_four_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第四球推荐单双
     *
     * @mbggenerated
     */
    private String ballFourSingle;

    /**
     * 字段: xjssc_recommend.ball_four_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第四球推荐大小
     *
     * @mbggenerated
     */
    private String ballFourSize;

    /**
     * 字段: xjssc_recommend.ball_five_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第五球推荐号码
     *
     * @mbggenerated
     */
    private String ballFiveNumber;

    /**
     * 字段: xjssc_recommend.ball_five_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第五球推荐单双
     *
     * @mbggenerated
     */
    private String ballFiveSingle;

    /**
     * 字段: xjssc_recommend.ball_five_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第五球推荐大小
     *
     * @mbggenerated
     */
    private String ballFiveSize;

    /**
     * 字段: xjssc_recommend.dragon_tiger<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 推荐龙虎
     *
     * @mbggenerated
     */
    private String dragonTiger;

    /**
     * 字段: xjssc_recommend.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table xjssc_recommend
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return xjssc_recommend.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: xjssc_recommend.id<br/>
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
     * @return xjssc_recommend.issue: 期号
     *
     * @mbggenerated
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 字段: xjssc_recommend.issue<br/>
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
     * @return xjssc_recommend.open_number: 开奖结果
     *
     * @mbggenerated
     */
    public String getOpenNumber() {
        return openNumber;
    }

    /**
     * 字段: xjssc_recommend.open_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 开奖结果
     *
     * @mbggenerated
     */
    public void setOpenNumber(String openNumber) {
        this.openNumber = openNumber;
    }

    /**
     * @return xjssc_recommend.ball_one_number: 第一球推荐号码
     *
     * @mbggenerated
     */
    public String getBallOneNumber() {
        return ballOneNumber;
    }

    /**
     * 字段: xjssc_recommend.ball_one_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一球推荐号码
     *
     * @mbggenerated
     */
    public void setBallOneNumber(String ballOneNumber) {
        this.ballOneNumber = ballOneNumber;
    }

    /**
     * @return xjssc_recommend.ball_one_single: 第一球推荐单双
     *
     * @mbggenerated
     */
    public String getBallOneSingle() {
        return ballOneSingle;
    }

    /**
     * 字段: xjssc_recommend.ball_one_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一球推荐单双
     *
     * @mbggenerated
     */
    public void setBallOneSingle(String ballOneSingle) {
        this.ballOneSingle = ballOneSingle;
    }

    /**
     * @return xjssc_recommend.ball_one_size: 第一球推荐大小
     *
     * @mbggenerated
     */
    public String getBallOneSize() {
        return ballOneSize;
    }

    /**
     * 字段: xjssc_recommend.ball_one_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第一球推荐大小
     *
     * @mbggenerated
     */
    public void setBallOneSize(String ballOneSize) {
        this.ballOneSize = ballOneSize;
    }

    /**
     * @return xjssc_recommend.ball_two_number: 第二球推荐号码
     *
     * @mbggenerated
     */
    public String getBallTwoNumber() {
        return ballTwoNumber;
    }

    /**
     * 字段: xjssc_recommend.ball_two_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二球推荐号码
     *
     * @mbggenerated
     */
    public void setBallTwoNumber(String ballTwoNumber) {
        this.ballTwoNumber = ballTwoNumber;
    }

    /**
     * @return xjssc_recommend.ball_two_single: 第二球推荐单双
     *
     * @mbggenerated
     */
    public String getBallTwoSingle() {
        return ballTwoSingle;
    }

    /**
     * 字段: xjssc_recommend.ball_two_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二球推荐单双
     *
     * @mbggenerated
     */
    public void setBallTwoSingle(String ballTwoSingle) {
        this.ballTwoSingle = ballTwoSingle;
    }

    /**
     * @return xjssc_recommend.ball_two_size: 第二球推荐大小
     *
     * @mbggenerated
     */
    public String getBallTwoSize() {
        return ballTwoSize;
    }

    /**
     * 字段: xjssc_recommend.ball_two_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第二球推荐大小
     *
     * @mbggenerated
     */
    public void setBallTwoSize(String ballTwoSize) {
        this.ballTwoSize = ballTwoSize;
    }

    /**
     * @return xjssc_recommend.ball_three_number: 第三球推荐号码
     *
     * @mbggenerated
     */
    public String getBallThreeNumber() {
        return ballThreeNumber;
    }

    /**
     * 字段: xjssc_recommend.ball_three_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三球推荐号码
     *
     * @mbggenerated
     */
    public void setBallThreeNumber(String ballThreeNumber) {
        this.ballThreeNumber = ballThreeNumber;
    }

    /**
     * @return xjssc_recommend.ball_three_single: 第三球推荐单双
     *
     * @mbggenerated
     */
    public String getBallThreeSingle() {
        return ballThreeSingle;
    }

    /**
     * 字段: xjssc_recommend.ball_three_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三球推荐单双
     *
     * @mbggenerated
     */
    public void setBallThreeSingle(String ballThreeSingle) {
        this.ballThreeSingle = ballThreeSingle;
    }

    /**
     * @return xjssc_recommend.ball_three_size: 第三球推荐大小
     *
     * @mbggenerated
     */
    public String getBallThreeSize() {
        return ballThreeSize;
    }

    /**
     * 字段: xjssc_recommend.ball_three_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第三球推荐大小
     *
     * @mbggenerated
     */
    public void setBallThreeSize(String ballThreeSize) {
        this.ballThreeSize = ballThreeSize;
    }

    /**
     * @return xjssc_recommend.ball_four_number: 第四球推荐号码
     *
     * @mbggenerated
     */
    public String getBallFourNumber() {
        return ballFourNumber;
    }

    /**
     * 字段: xjssc_recommend.ball_four_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第四球推荐号码
     *
     * @mbggenerated
     */
    public void setBallFourNumber(String ballFourNumber) {
        this.ballFourNumber = ballFourNumber;
    }

    /**
     * @return xjssc_recommend.ball_four_single: 第四球推荐单双
     *
     * @mbggenerated
     */
    public String getBallFourSingle() {
        return ballFourSingle;
    }

    /**
     * 字段: xjssc_recommend.ball_four_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第四球推荐单双
     *
     * @mbggenerated
     */
    public void setBallFourSingle(String ballFourSingle) {
        this.ballFourSingle = ballFourSingle;
    }

    /**
     * @return xjssc_recommend.ball_four_size: 第四球推荐大小
     *
     * @mbggenerated
     */
    public String getBallFourSize() {
        return ballFourSize;
    }

    /**
     * 字段: xjssc_recommend.ball_four_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第四球推荐大小
     *
     * @mbggenerated
     */
    public void setBallFourSize(String ballFourSize) {
        this.ballFourSize = ballFourSize;
    }

    /**
     * @return xjssc_recommend.ball_five_number: 第五球推荐号码
     *
     * @mbggenerated
     */
    public String getBallFiveNumber() {
        return ballFiveNumber;
    }

    /**
     * 字段: xjssc_recommend.ball_five_number<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第五球推荐号码
     *
     * @mbggenerated
     */
    public void setBallFiveNumber(String ballFiveNumber) {
        this.ballFiveNumber = ballFiveNumber;
    }

    /**
     * @return xjssc_recommend.ball_five_single: 第五球推荐单双
     *
     * @mbggenerated
     */
    public String getBallFiveSingle() {
        return ballFiveSingle;
    }

    /**
     * 字段: xjssc_recommend.ball_five_single<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第五球推荐单双
     *
     * @mbggenerated
     */
    public void setBallFiveSingle(String ballFiveSingle) {
        this.ballFiveSingle = ballFiveSingle;
    }

    /**
     * @return xjssc_recommend.ball_five_size: 第五球推荐大小
     *
     * @mbggenerated
     */
    public String getBallFiveSize() {
        return ballFiveSize;
    }

    /**
     * 字段: xjssc_recommend.ball_five_size<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 第五球推荐大小
     *
     * @mbggenerated
     */
    public void setBallFiveSize(String ballFiveSize) {
        this.ballFiveSize = ballFiveSize;
    }

    /**
     * @return xjssc_recommend.dragon_tiger: 推荐龙虎
     *
     * @mbggenerated
     */
    public String getDragonTiger() {
        return dragonTiger;
    }

    /**
     * 字段: xjssc_recommend.dragon_tiger<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 推荐龙虎
     *
     * @mbggenerated
     */
    public void setDragonTiger(String dragonTiger) {
        this.dragonTiger = dragonTiger;
    }

    /**
     * @return xjssc_recommend.create_time: 创建时间
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 字段: xjssc_recommend.create_time<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 20<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_recommend
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
        XjsscRecommend other = (XjsscRecommend) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIssue() == null ? other.getIssue() == null : this.getIssue().equals(other.getIssue()))
            && (this.getOpenNumber() == null ? other.getOpenNumber() == null : this.getOpenNumber().equals(other.getOpenNumber()))
            && (this.getBallOneNumber() == null ? other.getBallOneNumber() == null : this.getBallOneNumber().equals(other.getBallOneNumber()))
            && (this.getBallOneSingle() == null ? other.getBallOneSingle() == null : this.getBallOneSingle().equals(other.getBallOneSingle()))
            && (this.getBallOneSize() == null ? other.getBallOneSize() == null : this.getBallOneSize().equals(other.getBallOneSize()))
            && (this.getBallTwoNumber() == null ? other.getBallTwoNumber() == null : this.getBallTwoNumber().equals(other.getBallTwoNumber()))
            && (this.getBallTwoSingle() == null ? other.getBallTwoSingle() == null : this.getBallTwoSingle().equals(other.getBallTwoSingle()))
            && (this.getBallTwoSize() == null ? other.getBallTwoSize() == null : this.getBallTwoSize().equals(other.getBallTwoSize()))
            && (this.getBallThreeNumber() == null ? other.getBallThreeNumber() == null : this.getBallThreeNumber().equals(other.getBallThreeNumber()))
            && (this.getBallThreeSingle() == null ? other.getBallThreeSingle() == null : this.getBallThreeSingle().equals(other.getBallThreeSingle()))
            && (this.getBallThreeSize() == null ? other.getBallThreeSize() == null : this.getBallThreeSize().equals(other.getBallThreeSize()))
            && (this.getBallFourNumber() == null ? other.getBallFourNumber() == null : this.getBallFourNumber().equals(other.getBallFourNumber()))
            && (this.getBallFourSingle() == null ? other.getBallFourSingle() == null : this.getBallFourSingle().equals(other.getBallFourSingle()))
            && (this.getBallFourSize() == null ? other.getBallFourSize() == null : this.getBallFourSize().equals(other.getBallFourSize()))
            && (this.getBallFiveNumber() == null ? other.getBallFiveNumber() == null : this.getBallFiveNumber().equals(other.getBallFiveNumber()))
            && (this.getBallFiveSingle() == null ? other.getBallFiveSingle() == null : this.getBallFiveSingle().equals(other.getBallFiveSingle()))
            && (this.getBallFiveSize() == null ? other.getBallFiveSize() == null : this.getBallFiveSize().equals(other.getBallFiveSize()))
            && (this.getDragonTiger() == null ? other.getDragonTiger() == null : this.getDragonTiger().equals(other.getDragonTiger()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_recommend
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIssue() == null) ? 0 : getIssue().hashCode());
        result = prime * result + ((getOpenNumber() == null) ? 0 : getOpenNumber().hashCode());
        result = prime * result + ((getBallOneNumber() == null) ? 0 : getBallOneNumber().hashCode());
        result = prime * result + ((getBallOneSingle() == null) ? 0 : getBallOneSingle().hashCode());
        result = prime * result + ((getBallOneSize() == null) ? 0 : getBallOneSize().hashCode());
        result = prime * result + ((getBallTwoNumber() == null) ? 0 : getBallTwoNumber().hashCode());
        result = prime * result + ((getBallTwoSingle() == null) ? 0 : getBallTwoSingle().hashCode());
        result = prime * result + ((getBallTwoSize() == null) ? 0 : getBallTwoSize().hashCode());
        result = prime * result + ((getBallThreeNumber() == null) ? 0 : getBallThreeNumber().hashCode());
        result = prime * result + ((getBallThreeSingle() == null) ? 0 : getBallThreeSingle().hashCode());
        result = prime * result + ((getBallThreeSize() == null) ? 0 : getBallThreeSize().hashCode());
        result = prime * result + ((getBallFourNumber() == null) ? 0 : getBallFourNumber().hashCode());
        result = prime * result + ((getBallFourSingle() == null) ? 0 : getBallFourSingle().hashCode());
        result = prime * result + ((getBallFourSize() == null) ? 0 : getBallFourSize().hashCode());
        result = prime * result + ((getBallFiveNumber() == null) ? 0 : getBallFiveNumber().hashCode());
        result = prime * result + ((getBallFiveSingle() == null) ? 0 : getBallFiveSingle().hashCode());
        result = prime * result + ((getBallFiveSize() == null) ? 0 : getBallFiveSize().hashCode());
        result = prime * result + ((getDragonTiger() == null) ? 0 : getDragonTiger().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table xjssc_recommend
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
        sb.append(", openNumber=").append(openNumber);
        sb.append(", ballOneNumber=").append(ballOneNumber);
        sb.append(", ballOneSingle=").append(ballOneSingle);
        sb.append(", ballOneSize=").append(ballOneSize);
        sb.append(", ballTwoNumber=").append(ballTwoNumber);
        sb.append(", ballTwoSingle=").append(ballTwoSingle);
        sb.append(", ballTwoSize=").append(ballTwoSize);
        sb.append(", ballThreeNumber=").append(ballThreeNumber);
        sb.append(", ballThreeSingle=").append(ballThreeSingle);
        sb.append(", ballThreeSize=").append(ballThreeSize);
        sb.append(", ballFourNumber=").append(ballFourNumber);
        sb.append(", ballFourSingle=").append(ballFourSingle);
        sb.append(", ballFourSize=").append(ballFourSize);
        sb.append(", ballFiveNumber=").append(ballFiveNumber);
        sb.append(", ballFiveSingle=").append(ballFiveSingle);
        sb.append(", ballFiveSize=").append(ballFiveSize);
        sb.append(", dragonTiger=").append(dragonTiger);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}