package com.caipiao.core.library.vo.lotterymanage;

/**
 * @author lzy
 * @create 2018-08-13 14:18
 **/
public class LhcPhotoVO {

    private Integer id;

    /**
     * 说明: 分类名称
     */
    private String categoryName;

    /**
     * 说明: 年份
     */
    private String year;

    /**
     * 说明: 期号
     */
    private Integer issue;

    /**
     * 说明: 图片路径
     */
    private String url;

    /**
     * 说明: 创建时间
     */
    private String createTime;

    /**
     * 说明: 一级分类id
     */
    private Integer oneCategoryId;

    /**
     * 说明: 二级分类id
     */
    private Integer twoCategoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getOneCategoryId() {
        return oneCategoryId;
    }

    public void setOneCategoryId(Integer oneCategoryId) {
        this.oneCategoryId = oneCategoryId;
    }

    public Integer getTwoCategoryId() {
        return twoCategoryId;
    }

    public void setTwoCategoryId(Integer twoCategoryId) {
        this.twoCategoryId = twoCategoryId;
    }
}
