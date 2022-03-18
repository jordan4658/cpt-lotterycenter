package com.caipiao.core.library.dto.circle;

public class GodFansOrFocusListDTO {
    private Integer pageNum; //第几页
    private Integer pageSize; //页大小
    private Integer godId;  //大神id
    private Integer type;   //1关注列表2粉丝列表

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getGodId() {
        return godId;
    }

    public void setGodId(Integer godId) {
        this.godId = godId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
