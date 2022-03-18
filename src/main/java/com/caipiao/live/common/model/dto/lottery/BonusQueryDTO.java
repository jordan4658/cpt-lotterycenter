package com.caipiao.live.common.model.dto.lottery;

/**
 * ClassName:    BonusQueryDTO
 * Package:    com.caipiao.live.common.model.dto.lottery
 * Description:
 * Datetime:    2020/5/8   13:57
 * Author:   木鱼
 */
public class BonusQueryDTO {

    /**
     * 投注限制分类
     */
    private Integer categoryId;

    /**
     * 彩种分类
     */
    private Integer cateId;

    /**
     * 玩法分类1
     */
    private Integer typeId;

    /**
     * 玩法分类2
     */
    private Integer planId;

    /**
     * 玩法分类3
     */
    private Integer playId;

    /**
     * 页码
     */
    private Integer pageNo = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getPlayId() {
        return playId;
    }

    public void setPlayId(Integer playId) {
        this.playId = playId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
