package com.caipiao.live.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

public class LotteryPlaySetting implements Serializable {
    /**
     * 字段: lottery_play_setting.id<br/>
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
     * 字段: lottery_play_setting.cate_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 分类id
     *
     * @mbggenerated
     */
    private Integer cateId;

    /**
     * 字段: lottery_play_setting.play_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 玩法id
     *
     * @mbggenerated
     */
    private Integer playId;

    /**
     * 字段: lottery_play_setting.total_count<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 总注数
     *
     * @mbggenerated
     */
    private Integer totalCount;

    /**
     * 字段: lottery_play_setting.win_count<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 中奖注数
     *
     * @mbggenerated
     */
    private Integer winCount;

    /**
     * 字段: lottery_play_setting.single_money<br/>
     * 必填: true<br/>
     * 缺省: 0.5<br/>
     * 长度: 22<br/>
     * 说明: 单注金额
     *
     * @mbggenerated
     */
    private Double singleMoney;

    /**
     * 字段: lottery_play_setting.example<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 投注示例
     *
     * @mbggenerated
     */
    private String example;

    /**
     * 字段: lottery_play_setting.example_num<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 示例号码
     *
     * @mbggenerated
     */
    private String exampleNum;

    /**
     * 字段: lottery_play_setting.play_remark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 玩法说明
     *
     * @mbggenerated
     */
    private String playRemark;

    /**
     * 字段: lottery_play_setting.play_remark_sx<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 玩法简要说明
     *
     * @mbggenerated
     */
    private String playRemarkSx;

    /**
     * 字段: lottery_play_setting.reward<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 奖级
     *
     * @mbggenerated
     */
    private String reward;

    /**
     * 字段: lottery_play_setting.matchtype<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 2<br/>
     * 说明: 匹配规则
     *
     * @mbggenerated
     */
    private String matchtype;

    /**
     * 字段: lottery_play_setting.win_count_bak<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 总注数(后端)
     *
     * @mbggenerated
     */
    private Integer winCountBak;

    /**
     * 字段: lottery_play_setting.total_count_bak<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 中奖注数(后端)
     *
     * @mbggenerated
     */
    private Integer totalCountBak;

    /**
     * 字段: lottery_play_setting.reward_level<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 中奖等级
     *
     * @mbggenerated
     */
    private String rewardLevel;

    /**
     * 字段: lottery_play_setting.play_tag_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 玩法规则Tag编号
     *
     * @mbggenerated
     */
    private Integer playTagId;

    /**
     * 字段: lottery_play_setting.is_delete<br/>
     * 必填: true<br/>
     * 缺省: b'0'<br/>
     * 长度: 1<br/>
     * 说明: 是否删除
     *
     * @mbggenerated
     */
    private Boolean isDelete;

    /**
     * 字段: lottery_play_setting.create_time<br/>
     * 必填: true<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 字段: lottery_play_setting.update_time<br/>
     * 必填: true<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lottery_play_setting
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return lottery_play_setting.id: 
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 字段: lottery_play_setting.id<br/>
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
     * @return lottery_play_setting.cate_id: 分类id
     *
     * @mbggenerated
     */
    public Integer getCateId() {
        return cateId;
    }

    /**
     * 字段: lottery_play_setting.cate_id<br/>
     * 必填: true<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 分类id
     *
     * @mbggenerated
     */
    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    /**
     * @return lottery_play_setting.play_id: 玩法id
     *
     * @mbggenerated
     */
    public Integer getPlayId() {
        return playId;
    }

    /**
     * 字段: lottery_play_setting.play_id<br/>
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
     * @return lottery_play_setting.total_count: 总注数
     *
     * @mbggenerated
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 字段: lottery_play_setting.total_count<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 总注数
     *
     * @mbggenerated
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return lottery_play_setting.win_count: 中奖注数
     *
     * @mbggenerated
     */
    public Integer getWinCount() {
        return winCount;
    }

    /**
     * 字段: lottery_play_setting.win_count<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 中奖注数
     *
     * @mbggenerated
     */
    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    /**
     * @return lottery_play_setting.single_money: 单注金额
     *
     * @mbggenerated
     */
    public Double getSingleMoney() {
        return singleMoney;
    }

    /**
     * 字段: lottery_play_setting.single_money<br/>
     * 必填: true<br/>
     * 缺省: 0.5<br/>
     * 长度: 22<br/>
     * 说明: 单注金额
     *
     * @mbggenerated
     */
    public void setSingleMoney(Double singleMoney) {
        this.singleMoney = singleMoney;
    }

    /**
     * @return lottery_play_setting.example: 投注示例
     *
     * @mbggenerated
     */
    public String getExample() {
        return example;
    }

    /**
     * 字段: lottery_play_setting.example<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 投注示例
     *
     * @mbggenerated
     */
    public void setExample(String example) {
        this.example = example;
    }

    /**
     * @return lottery_play_setting.example_num: 示例号码
     *
     * @mbggenerated
     */
    public String getExampleNum() {
        return exampleNum;
    }

    /**
     * 字段: lottery_play_setting.example_num<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 示例号码
     *
     * @mbggenerated
     */
    public void setExampleNum(String exampleNum) {
        this.exampleNum = exampleNum;
    }

    /**
     * @return lottery_play_setting.play_remark: 玩法说明
     *
     * @mbggenerated
     */
    public String getPlayRemark() {
        return playRemark;
    }

    /**
     * 字段: lottery_play_setting.play_remark<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 玩法说明
     *
     * @mbggenerated
     */
    public void setPlayRemark(String playRemark) {
        this.playRemark = playRemark;
    }

    /**
     * @return lottery_play_setting.play_remark_sx: 玩法简要说明
     *
     * @mbggenerated
     */
    public String getPlayRemarkSx() {
        return playRemarkSx;
    }

    /**
     * 字段: lottery_play_setting.play_remark_sx<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 玩法简要说明
     *
     * @mbggenerated
     */
    public void setPlayRemarkSx(String playRemarkSx) {
        this.playRemarkSx = playRemarkSx;
    }

    /**
     * @return lottery_play_setting.reward: 奖级
     *
     * @mbggenerated
     */
    public String getReward() {
        return reward;
    }

    /**
     * 字段: lottery_play_setting.reward<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 1000<br/>
     * 说明: 奖级
     *
     * @mbggenerated
     */
    public void setReward(String reward) {
        this.reward = reward;
    }

    /**
     * @return lottery_play_setting.matchtype: 匹配规则
     *
     * @mbggenerated
     */
    public String getMatchtype() {
        return matchtype;
    }

    /**
     * 字段: lottery_play_setting.matchtype<br/>
     * 必填: false<br/>
     * 缺省: 1<br/>
     * 长度: 2<br/>
     * 说明: 匹配规则
     *
     * @mbggenerated
     */
    public void setMatchtype(String matchtype) {
        this.matchtype = matchtype;
    }

    /**
     * @return lottery_play_setting.win_count_bak: 总注数(后端)
     *
     * @mbggenerated
     */
    public Integer getWinCountBak() {
        return winCountBak;
    }

    /**
     * 字段: lottery_play_setting.win_count_bak<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 总注数(后端)
     *
     * @mbggenerated
     */
    public void setWinCountBak(Integer winCountBak) {
        this.winCountBak = winCountBak;
    }

    /**
     * @return lottery_play_setting.total_count_bak: 中奖注数(后端)
     *
     * @mbggenerated
     */
    public Integer getTotalCountBak() {
        return totalCountBak;
    }

    /**
     * 字段: lottery_play_setting.total_count_bak<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 中奖注数(后端)
     *
     * @mbggenerated
     */
    public void setTotalCountBak(Integer totalCountBak) {
        this.totalCountBak = totalCountBak;
    }

    /**
     * @return lottery_play_setting.reward_level: 中奖等级
     *
     * @mbggenerated
     */
    public String getRewardLevel() {
        return rewardLevel;
    }

    /**
     * 字段: lottery_play_setting.reward_level<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 255<br/>
     * 说明: 中奖等级
     *
     * @mbggenerated
     */
    public void setRewardLevel(String rewardLevel) {
        this.rewardLevel = rewardLevel;
    }

    /**
     * @return lottery_play_setting.play_tag_id: 玩法规则Tag编号
     *
     * @mbggenerated
     */
    public Integer getPlayTagId() {
        return playTagId;
    }

    /**
     * 字段: lottery_play_setting.play_tag_id<br/>
     * 必填: false<br/>
     * 缺省: <br/>
     * 长度: 10<br/>
     * 说明: 玩法规则Tag编号
     *
     * @mbggenerated
     */
    public void setPlayTagId(Integer playTagId) {
        this.playTagId = playTagId;
    }

    /**
     * @return lottery_play_setting.is_delete: 是否删除
     *
     * @mbggenerated
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 字段: lottery_play_setting.is_delete<br/>
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
     * @return lottery_play_setting.create_time: 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 字段: lottery_play_setting.create_time<br/>
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
     * @return lottery_play_setting.update_time: 修改时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 字段: lottery_play_setting.update_time<br/>
     * 必填: true<br/>
     * 缺省: CURRENT_TIMESTAMP<br/>
     * 长度: 19<br/>
     * 说明: 修改时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_play_setting
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
        LotteryPlaySetting other = (LotteryPlaySetting) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCateId() == null ? other.getCateId() == null : this.getCateId().equals(other.getCateId()))
            && (this.getPlayId() == null ? other.getPlayId() == null : this.getPlayId().equals(other.getPlayId()))
            && (this.getTotalCount() == null ? other.getTotalCount() == null : this.getTotalCount().equals(other.getTotalCount()))
            && (this.getWinCount() == null ? other.getWinCount() == null : this.getWinCount().equals(other.getWinCount()))
            && (this.getSingleMoney() == null ? other.getSingleMoney() == null : this.getSingleMoney().equals(other.getSingleMoney()))
            && (this.getExample() == null ? other.getExample() == null : this.getExample().equals(other.getExample()))
            && (this.getExampleNum() == null ? other.getExampleNum() == null : this.getExampleNum().equals(other.getExampleNum()))
            && (this.getPlayRemark() == null ? other.getPlayRemark() == null : this.getPlayRemark().equals(other.getPlayRemark()))
            && (this.getPlayRemarkSx() == null ? other.getPlayRemarkSx() == null : this.getPlayRemarkSx().equals(other.getPlayRemarkSx()))
            && (this.getReward() == null ? other.getReward() == null : this.getReward().equals(other.getReward()))
            && (this.getMatchtype() == null ? other.getMatchtype() == null : this.getMatchtype().equals(other.getMatchtype()))
            && (this.getWinCountBak() == null ? other.getWinCountBak() == null : this.getWinCountBak().equals(other.getWinCountBak()))
            && (this.getTotalCountBak() == null ? other.getTotalCountBak() == null : this.getTotalCountBak().equals(other.getTotalCountBak()))
            && (this.getRewardLevel() == null ? other.getRewardLevel() == null : this.getRewardLevel().equals(other.getRewardLevel()))
            && (this.getPlayTagId() == null ? other.getPlayTagId() == null : this.getPlayTagId().equals(other.getPlayTagId()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_play_setting
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCateId() == null) ? 0 : getCateId().hashCode());
        result = prime * result + ((getPlayId() == null) ? 0 : getPlayId().hashCode());
        result = prime * result + ((getTotalCount() == null) ? 0 : getTotalCount().hashCode());
        result = prime * result + ((getWinCount() == null) ? 0 : getWinCount().hashCode());
        result = prime * result + ((getSingleMoney() == null) ? 0 : getSingleMoney().hashCode());
        result = prime * result + ((getExample() == null) ? 0 : getExample().hashCode());
        result = prime * result + ((getExampleNum() == null) ? 0 : getExampleNum().hashCode());
        result = prime * result + ((getPlayRemark() == null) ? 0 : getPlayRemark().hashCode());
        result = prime * result + ((getPlayRemarkSx() == null) ? 0 : getPlayRemarkSx().hashCode());
        result = prime * result + ((getReward() == null) ? 0 : getReward().hashCode());
        result = prime * result + ((getMatchtype() == null) ? 0 : getMatchtype().hashCode());
        result = prime * result + ((getWinCountBak() == null) ? 0 : getWinCountBak().hashCode());
        result = prime * result + ((getTotalCountBak() == null) ? 0 : getTotalCountBak().hashCode());
        result = prime * result + ((getRewardLevel() == null) ? 0 : getRewardLevel().hashCode());
        result = prime * result + ((getPlayTagId() == null) ? 0 : getPlayTagId().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lottery_play_setting
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
        sb.append(", cateId=").append(cateId);
        sb.append(", playId=").append(playId);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", winCount=").append(winCount);
        sb.append(", singleMoney=").append(singleMoney);
        sb.append(", example=").append(example);
        sb.append(", exampleNum=").append(exampleNum);
        sb.append(", playRemark=").append(playRemark);
        sb.append(", playRemarkSx=").append(playRemarkSx);
        sb.append(", reward=").append(reward);
        sb.append(", matchtype=").append(matchtype);
        sb.append(", winCountBak=").append(winCountBak);
        sb.append(", totalCountBak=").append(totalCountBak);
        sb.append(", rewardLevel=").append(rewardLevel);
        sb.append(", playTagId=").append(playTagId);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
