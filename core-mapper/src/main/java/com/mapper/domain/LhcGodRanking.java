package com.mapper.domain;

import java.io.Serializable;
import java.util.Date;

public class LhcGodRanking implements Serializable {
    /**
     * 字段: lhc_god_ranking.id<br/>
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
     * 字段: lhc_god_ranking.god type id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 大神类别外键ID
     *
     * @mbggenerated
     */
    private Integer godTypeId;

    /**
     * 字段: lhc_god_ranking.cicle_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 心水推荐外键ID
     *
     * @mbggenerated
     */
    private Integer cicleId;

    /**
     * 字段: lhc_god_ranking.recommender_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 推荐人ID外键
     *
     * @mbggenerated
     */
    private Integer recommenderId;

    /**
     * 字段: lhc_god_ranking.recommender<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 推荐人
     *
     * @mbggenerated
     */
    private String recommender;

    /**
     * 字段: lhc_god_ranking.title<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 2000<br/>
     * 说明: 标题
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 字段: lhc_god_ranking.buy_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 购买次数
     *
     * @mbggenerated
     */
    private Integer buyNumber;

    /**
     * 字段: lhc_god_ranking.wins_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 中奖次数
     *
     * @mbggenerated
     */
    private Integer winsNumber;

    /**
     * 字段: lhc_god_ranking.continuous_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 最大连中
     *
     * @mbggenerated
     */
    private Integer continuousNumber;

    /**
     * 字段: lhc_god_ranking.win_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 7<br/>
     * 说明: 胜率
     *
     * @mbggenerated
     */
    private Float winRate;

    /**
     * 字段: lhc_god_ranking.fans_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 粉丝数量
     *
     * @mbggenerated
     */
    private Integer fansNumber;

    /**
     * 字段: lhc_god_ranking.create_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 字段: lhc_god_ranking.update_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 最后修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 字段: lhc_god_ranking.create_username<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 创建人
     *
     * @mbggenerated
     */
    private String createUsername;

    /**
     * 字段: lhc_god_ranking.update_username<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 最后更新人
     *
     * @mbggenerated
     */
    private String updateUsername;

    /**
     * 字段: lhc_god_ranking.attribute_1<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 扩展字段1
     *
     * @mbggenerated
     */
    private String attribute1;

    /**
     * 字段: lhc_god_ranking.attribute_2<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 扩展字段2
     *
     * @mbggenerated
     */
    private String attribute2;

    /**
     * 字段: lhc_god_ranking.attribute_3<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 扩展字段3
     *
     * @mbggenerated
     */
    private Integer attribute3;

    /**
     * 字段: lhc_god_ranking.attribute_4<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 扩展字段4
     *
     * @mbggenerated
     */
    private Integer attribute4;

    /**
     * 字段: lhc_god_ranking.attribute_5<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 扩展字段5
     *
     * @mbggenerated
     */
    private Integer attribute5;

    /**
     * 字段: lhc_god_ranking.attribute_6<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 扩展字段6
     *
     * @mbggenerated
     */
    private String attribute6;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lhc_god_ranking
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return lhc_god_ranking.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: lhc_god_ranking.id<br/>
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
     * @return lhc_god_ranking.god type id: 大神类别外键ID
     *
     * @mbggenerated
     */
    public Integer getGodTypeId() {
        return godTypeId;
    }

    /**
     * 字段: lhc_god_ranking.god type id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 大神类别外键ID
     *
     * @mbggenerated
     */
    public void setGodTypeId(Integer godTypeId) {
        this.godTypeId = godTypeId;
    }

    /**
     * @return lhc_god_ranking.cicle_id: 心水推荐外键ID
     *
     * @mbggenerated
     */
    public Integer getCicleId() {
        return cicleId;
    }

    /**
     * 字段: lhc_god_ranking.cicle_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 心水推荐外键ID
     *
     * @mbggenerated
     */
    public void setCicleId(Integer cicleId) {
        this.cicleId = cicleId;
    }

    /**
     * @return lhc_god_ranking.recommender_id: 推荐人ID外键
     *
     * @mbggenerated
     */
    public Integer getRecommenderId() {
        return recommenderId;
    }

    /**
     * 字段: lhc_god_ranking.recommender_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 推荐人ID外键
     *
     * @mbggenerated
     */
    public void setRecommenderId(Integer recommenderId) {
        this.recommenderId = recommenderId;
    }

    /**
     * @return lhc_god_ranking.recommender: 推荐人
     *
     * @mbggenerated
     */
    public String getRecommender() {
        return recommender;
    }

    /**
     * 字段: lhc_god_ranking.recommender<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 推荐人
     *
     * @mbggenerated
     */
    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    /**
     * @return lhc_god_ranking.title: 标题
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * 字段: lhc_god_ranking.title<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 2000<br/>
     * 说明: 标题
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return lhc_god_ranking.buy_number: 购买次数
     *
     * @mbggenerated
     */
    public Integer getBuyNumber() {
        return buyNumber;
    }

    /**
     * 字段: lhc_god_ranking.buy_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 购买次数
     *
     * @mbggenerated
     */
    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    /**
     * @return lhc_god_ranking.wins_number: 中奖次数
     *
     * @mbggenerated
     */
    public Integer getWinsNumber() {
        return winsNumber;
    }

    /**
     * 字段: lhc_god_ranking.wins_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 中奖次数
     *
     * @mbggenerated
     */
    public void setWinsNumber(Integer winsNumber) {
        this.winsNumber = winsNumber;
    }

    /**
     * @return lhc_god_ranking.continuous_number: 最大连中
     *
     * @mbggenerated
     */
    public Integer getContinuousNumber() {
        return continuousNumber;
    }

    /**
     * 字段: lhc_god_ranking.continuous_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 最大连中
     *
     * @mbggenerated
     */
    public void setContinuousNumber(Integer continuousNumber) {
        this.continuousNumber = continuousNumber;
    }

    /**
     * @return lhc_god_ranking.win_rate: 胜率
     *
     * @mbggenerated
     */
    public Float getWinRate() {
        return winRate;
    }

    /**
     * 字段: lhc_god_ranking.win_rate<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 7<br/>
     * 说明: 胜率
     *
     * @mbggenerated
     */
    public void setWinRate(Float winRate) {
        this.winRate = winRate;
    }

    /**
     * @return lhc_god_ranking.fans_number: 粉丝数量
     *
     * @mbggenerated
     */
    public Integer getFansNumber() {
        return fansNumber;
    }

    /**
     * 字段: lhc_god_ranking.fans_number<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 粉丝数量
     *
     * @mbggenerated
     */
    public void setFansNumber(Integer fansNumber) {
        this.fansNumber = fansNumber;
    }

    /**
     * @return lhc_god_ranking.create_time: 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 字段: lhc_god_ranking.create_time<br/>
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
     * @return lhc_god_ranking.update_time: 最后修改时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 字段: lhc_god_ranking.update_time<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 19<br/>
     * 说明: 最后修改时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return lhc_god_ranking.create_username: 创建人
     *
     * @mbggenerated
     */
    public String getCreateUsername() {
        return createUsername;
    }

    /**
     * 字段: lhc_god_ranking.create_username<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 创建人
     *
     * @mbggenerated
     */
    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    /**
     * @return lhc_god_ranking.update_username: 最后更新人
     *
     * @mbggenerated
     */
    public String getUpdateUsername() {
        return updateUsername;
    }

    /**
     * 字段: lhc_god_ranking.update_username<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 50<br/>
     * 说明: 最后更新人
     *
     * @mbggenerated
     */
    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
    }

    /**
     * @return lhc_god_ranking.attribute_1: 扩展字段1
     *
     * @mbggenerated
     */
    public String getAttribute1() {
        return attribute1;
    }

    /**
     * 字段: lhc_god_ranking.attribute_1<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 扩展字段1
     *
     * @mbggenerated
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    /**
     * @return lhc_god_ranking.attribute_2: 扩展字段2
     *
     * @mbggenerated
     */
    public String getAttribute2() {
        return attribute2;
    }

    /**
     * 字段: lhc_god_ranking.attribute_2<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 扩展字段2
     *
     * @mbggenerated
     */
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    /**
     * @return lhc_god_ranking.attribute_3: 扩展字段3
     *
     * @mbggenerated
     */
    public Integer getAttribute3() {
        return attribute3;
    }

    /**
     * 字段: lhc_god_ranking.attribute_3<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 扩展字段3
     *
     * @mbggenerated
     */
    public void setAttribute3(Integer attribute3) {
        this.attribute3 = attribute3;
    }

    /**
     * @return lhc_god_ranking.attribute_4: 扩展字段4
     *
     * @mbggenerated
     */
    public Integer getAttribute4() {
        return attribute4;
    }

    /**
     * 字段: lhc_god_ranking.attribute_4<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 扩展字段4
     *
     * @mbggenerated
     */
    public void setAttribute4(Integer attribute4) {
        this.attribute4 = attribute4;
    }

    /**
     * @return lhc_god_ranking.attribute_5: 扩展字段5
     *
     * @mbggenerated
     */
    public Integer getAttribute5() {
        return attribute5;
    }

    /**
     * 字段: lhc_god_ranking.attribute_5<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 扩展字段5
     *
     * @mbggenerated
     */
    public void setAttribute5(Integer attribute5) {
        this.attribute5 = attribute5;
    }

    /**
     * @return lhc_god_ranking.attribute_6: 扩展字段6
     *
     * @mbggenerated
     */
    public String getAttribute6() {
        return attribute6;
    }

    /**
     * 字段: lhc_god_ranking.attribute_6<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 扩展字段6
     *
     * @mbggenerated
     */
    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_god_ranking
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
        LhcGodRanking other = (LhcGodRanking) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGodTypeId() == null ? other.getGodTypeId() == null : this.getGodTypeId().equals(other.getGodTypeId()))
            && (this.getCicleId() == null ? other.getCicleId() == null : this.getCicleId().equals(other.getCicleId()))
            && (this.getRecommenderId() == null ? other.getRecommenderId() == null : this.getRecommenderId().equals(other.getRecommenderId()))
            && (this.getRecommender() == null ? other.getRecommender() == null : this.getRecommender().equals(other.getRecommender()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getBuyNumber() == null ? other.getBuyNumber() == null : this.getBuyNumber().equals(other.getBuyNumber()))
            && (this.getWinsNumber() == null ? other.getWinsNumber() == null : this.getWinsNumber().equals(other.getWinsNumber()))
            && (this.getContinuousNumber() == null ? other.getContinuousNumber() == null : this.getContinuousNumber().equals(other.getContinuousNumber()))
            && (this.getWinRate() == null ? other.getWinRate() == null : this.getWinRate().equals(other.getWinRate()))
            && (this.getFansNumber() == null ? other.getFansNumber() == null : this.getFansNumber().equals(other.getFansNumber()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUsername() == null ? other.getCreateUsername() == null : this.getCreateUsername().equals(other.getCreateUsername()))
            && (this.getUpdateUsername() == null ? other.getUpdateUsername() == null : this.getUpdateUsername().equals(other.getUpdateUsername()))
            && (this.getAttribute1() == null ? other.getAttribute1() == null : this.getAttribute1().equals(other.getAttribute1()))
            && (this.getAttribute2() == null ? other.getAttribute2() == null : this.getAttribute2().equals(other.getAttribute2()))
            && (this.getAttribute3() == null ? other.getAttribute3() == null : this.getAttribute3().equals(other.getAttribute3()))
            && (this.getAttribute4() == null ? other.getAttribute4() == null : this.getAttribute4().equals(other.getAttribute4()))
            && (this.getAttribute5() == null ? other.getAttribute5() == null : this.getAttribute5().equals(other.getAttribute5()))
            && (this.getAttribute6() == null ? other.getAttribute6() == null : this.getAttribute6().equals(other.getAttribute6()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_god_ranking
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGodTypeId() == null) ? 0 : getGodTypeId().hashCode());
        result = prime * result + ((getCicleId() == null) ? 0 : getCicleId().hashCode());
        result = prime * result + ((getRecommenderId() == null) ? 0 : getRecommenderId().hashCode());
        result = prime * result + ((getRecommender() == null) ? 0 : getRecommender().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getBuyNumber() == null) ? 0 : getBuyNumber().hashCode());
        result = prime * result + ((getWinsNumber() == null) ? 0 : getWinsNumber().hashCode());
        result = prime * result + ((getContinuousNumber() == null) ? 0 : getContinuousNumber().hashCode());
        result = prime * result + ((getWinRate() == null) ? 0 : getWinRate().hashCode());
        result = prime * result + ((getFansNumber() == null) ? 0 : getFansNumber().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUsername() == null) ? 0 : getCreateUsername().hashCode());
        result = prime * result + ((getUpdateUsername() == null) ? 0 : getUpdateUsername().hashCode());
        result = prime * result + ((getAttribute1() == null) ? 0 : getAttribute1().hashCode());
        result = prime * result + ((getAttribute2() == null) ? 0 : getAttribute2().hashCode());
        result = prime * result + ((getAttribute3() == null) ? 0 : getAttribute3().hashCode());
        result = prime * result + ((getAttribute4() == null) ? 0 : getAttribute4().hashCode());
        result = prime * result + ((getAttribute5() == null) ? 0 : getAttribute5().hashCode());
        result = prime * result + ((getAttribute6() == null) ? 0 : getAttribute6().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lhc_god_ranking
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
        sb.append(", godTypeId=").append(godTypeId);
        sb.append(", cicleId=").append(cicleId);
        sb.append(", recommenderId=").append(recommenderId);
        sb.append(", recommender=").append(recommender);
        sb.append(", title=").append(title);
        sb.append(", buyNumber=").append(buyNumber);
        sb.append(", winsNumber=").append(winsNumber);
        sb.append(", continuousNumber=").append(continuousNumber);
        sb.append(", winRate=").append(winRate);
        sb.append(", fansNumber=").append(fansNumber);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUsername=").append(createUsername);
        sb.append(", updateUsername=").append(updateUsername);
        sb.append(", attribute1=").append(attribute1);
        sb.append(", attribute2=").append(attribute2);
        sb.append(", attribute3=").append(attribute3);
        sb.append(", attribute4=").append(attribute4);
        sb.append(", attribute5=").append(attribute5);
        sb.append(", attribute6=").append(attribute6);
        sb.append("]");
        return sb.toString();
    }
}