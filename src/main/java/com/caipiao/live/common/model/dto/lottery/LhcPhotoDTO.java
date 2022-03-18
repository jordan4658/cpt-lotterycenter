package com.caipiao.live.common.model.dto.lottery;

/**
 * ClassName:    LhcPhotoDTO
 * Package:    com.caipiao.live.common.model.dto.lottery
 * Description:
 * Datetime:    2020/5/8   14:05
 * Author:   木鱼
 */
public class LhcPhotoDTO {

    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private String issue;
    private Integer oneId;
    private Integer twoId;

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

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Integer getOneId() {
        return oneId;
    }

    public void setOneId(Integer oneId) {
        this.oneId = oneId;
    }

    public Integer getTwoId() {
        return twoId;
    }

    public void setTwoId(Integer twoId) {
        this.twoId = twoId;
    }
}
