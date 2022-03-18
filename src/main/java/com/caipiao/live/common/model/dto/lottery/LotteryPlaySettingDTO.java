package com.caipiao.live.common.model.dto.lottery;

import com.caipiao.live.common.mybatis.entity.LotteryPlayOdds;

import java.util.Date;
import java.util.List;

public class LotteryPlaySettingDTO {
    private Integer id;
    private Integer cateId; // 分类id
    private Integer playId; // 玩法id
    private Integer totalCount; // 总注数
    private Integer winCount; // 中奖注数
    private Double singleMoney; // 单注金额
    private String example; // 投注示例
    private String exampleNum; // 示例号码
    private String playRemark; // 玩法说明
    private String playRemarkSx; // 玩法简要说明
    private String reward; // 奖级
    private Integer winCountBak; // 中奖注数（后端）
    private Integer totalCountBak; // 总注数（后端）
    private String rewardLevel; // 中奖等级
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private Boolean isDelete; // 是否删除
    private String cateName; // 分类名称
    private String type; // 玩法类型1
    private String plan; // 玩法分类2
    private String planName; // 玩法分类3
    private String matchtype;
    private Integer lotteryId;//彩种编号
    private Integer playTagId;//玩法规则TagId
    private String lotteryName;
    
    public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getMatchtype() {
		return matchtype;
	}

	public void setMatchtype(String matchtype) {
		this.matchtype = matchtype;
	}

	private List<LotteryPlayOdds> oddsList; // 赔率

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExampleNum() {
        return exampleNum;
    }

    public void setExampleNum(String exampleNum) {
        this.exampleNum = exampleNum;
    }

    public String getPlayRemark() {
        return playRemark;
    }

    public void setPlayRemark(String playRemark) {
        this.playRemark = playRemark;
    }

    public String getPlayRemarkSx() {
        return playRemarkSx;
    }

    public void setPlayRemarkSx(String playRemarkSx) {
        this.playRemarkSx = playRemarkSx;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getWinCount() {
        return winCount;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    public Double getSingleMoney() {
        return singleMoney;
    }

    public void setSingleMoney(Double singleMoney) {
        this.singleMoney = singleMoney;
    }

    public Integer getWinCountBak() {
        return winCountBak;
    }

    public void setWinCountBak(Integer winCountBak) {
        this.winCountBak = winCountBak;
    }

    public Integer getTotalCountBak() {
        return totalCountBak;
    }

    public void setTotalCountBak(Integer totalCountBak) {
        this.totalCountBak = totalCountBak;
    }

    public String getRewardLevel() {
        return rewardLevel;
    }

    public void setRewardLevel(String rewardLevel) {
        this.rewardLevel = rewardLevel;
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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<LotteryPlayOdds> getOddsList() {
        return oddsList;
    }

    public void setOddsList(List<LotteryPlayOdds> oddsList) {
        this.oddsList = oddsList;
    }

	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getPlayTagId() {
		return playTagId;
	}

	public void setPlayTagId(Integer playTagId) {
		this.playTagId = playTagId;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
}
