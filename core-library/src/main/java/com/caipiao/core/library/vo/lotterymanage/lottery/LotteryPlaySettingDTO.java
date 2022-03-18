package com.caipiao.core.library.vo.lotterymanage.lottery;

public class LotteryPlaySettingDTO {

	private Integer id;

    /**
     * 说明: 分类id
     */
    private Integer cateId;

    /**
     * 说明: 玩法id
     */
    private Integer playId;

    /**
     * 说明: 单注金额
     */
    private Double singleMoney;

    /**
     * 说明: 投注示例
     */
    private String example;

    /**
     * 说明: 示例号码
     */
    private String exampleNum;

    /**
     * 说明: 玩法说明
     */
    private String playRemark;

    /**
     * 说明: 玩法简要说明
     */
    private String playRemarkSx;

    /**
     * 说明: 匹配规则
     */
    private String matchtype;

    private Integer playTagId;

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

	public Double getSingleMoney() {
		return singleMoney;
	}

	public void setSingleMoney(Double singleMoney) {
		this.singleMoney = singleMoney;
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

	public String getMatchtype() {
		return matchtype;
	}

	public void setMatchtype(String matchtype) {
		this.matchtype = matchtype;
	}

	public Integer getPlayTagId() {
		return playTagId;
	}

	public void setPlayTagId(Integer playTagId) {
		this.playTagId = playTagId;
	}
}
