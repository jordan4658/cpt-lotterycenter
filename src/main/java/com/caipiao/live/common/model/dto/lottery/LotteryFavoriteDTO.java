package com.caipiao.live.common.model.dto.lottery;

public class LotteryFavoriteDTO {

    /** 彩票主键id */
    private int id;

    /** 彩票id */
    private int lotteryId;

    /** 彩种id */
    private int categoryId;

    /** 彩票序号 */
    private int sort;

    /** 彩种序号 */
    private int categorySort;

    /** 彩票icon */
    private String icon;

    /** 彩票名称 */
    private String name;

    /** 彩种名称 */
    private String cateName;

    /** 彩种介绍 */
    private String intro;

    private int isWork;

    /** 彩票封盘时间 */
    private Integer endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getCategorySort() {
        return categorySort;
    }

    public void setCategorySort(int categorySort) {
        this.categorySort = categorySort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getIsWork() {
        return isWork;
    }

    public void setIsWork(int isWork) {
        this.isWork = isWork;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "LotteryFavoriteDTO{" +
                "id=" + id +
                ", lotteryId=" + lotteryId +
                ", categoryId=" + categoryId +
                ", sort=" + sort +
                ", categorySort=" + categorySort +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", cateName='" + cateName + '\'' +
                ", intro='" + intro + '\'' +
                ", isWork=" + isWork +
                ", endTime=" + endTime +
                '}';
    }
}
