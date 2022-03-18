package com.caipiao.live.common.model.dto.result;

/**
 * 手动下三路的DTO
 *
 * @author lzy
 * @create 2018-07-20 18:10
 **/
public class ThreeWayCallSgDTO {
    private String year; //年份
    private Integer pageNo; //页码
    private Integer pageSize; //页码

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
