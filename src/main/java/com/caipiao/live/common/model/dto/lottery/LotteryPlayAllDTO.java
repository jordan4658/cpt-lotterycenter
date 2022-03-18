package com.caipiao.live.common.model.dto.lottery;

import java.util.List;

public class LotteryPlayAllDTO {

    //private List<PlaySettingAndOddsInfo> playinfos;

    private List<LotteryPlayAllDTO> playChildren;

    private Integer id;
    /**
     * 说明: 玩法名称
     */
    private String name;

    /**
     * 说明: 彩种分类id
     */
    private Integer categoryId;

    /**
     * 说明: 父级id
     */
    private Integer parentId;

    /**
     * 说明: 彩种ID
     */
    private Integer lotteryId;

    /**
     * 说明: 玩法规则Tag编号
     */
    private Integer playTagId;

    private Integer sort;

    private LotteryPlaySettingDTO setting; // 玩法配置信息

    private List<LotteryPlayAllOdds> oddsList; // 具体赔率信息

    public LotteryPlaySettingDTO getSetting() {
        return setting;
    }

    public void setSetting(LotteryPlaySettingDTO setting) {
        this.setting = setting;
    }

    public List<LotteryPlayAllOdds> getOddsList() {
        return oddsList;
    }

    public void setOddsList(List<LotteryPlayAllOdds> oddsList) {
        this.oddsList = oddsList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public List<LotteryPlayAllDTO> getPlayChildren() {
        return playChildren;
    }

    public void setPlayChildren(List<LotteryPlayAllDTO> playChildren) {
        this.playChildren = playChildren;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "LotteryPlayAllDTO{" +
                "playChildren=" + playChildren +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", parentId=" + parentId +
                ", lotteryId=" + lotteryId +
                ", playTagId=" + playTagId +
                ", sort=" + sort +
                ", setting=" + setting +
                ", oddsList=" + oddsList +
                '}';
    }
}
