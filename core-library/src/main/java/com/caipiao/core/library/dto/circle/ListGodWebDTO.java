package com.caipiao.core.library.dto.circle;

public class ListGodWebDTO {
    private Integer pageNum; //第几页
    private Integer pageSize; //页大小
    private Integer type;   //类型
    private String isMyFocus;  //是否我的关注

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIsMyFocus() {
        return isMyFocus;
    }

    public void setIsMyFocus(String isMyFocus) {
        this.isMyFocus = isMyFocus;
    }
}
