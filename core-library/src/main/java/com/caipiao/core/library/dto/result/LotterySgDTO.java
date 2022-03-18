package com.caipiao.core.library.dto.result;

import java.util.List;

/**
 * 彩种赛果资讯的DTO
 *
 * @author lzy
 * @create 2018-07-20 18:10
 **/
public class LotterySgDTO {

    private String type; //类型

    private String issue; //期数

    private String year; //年份

    private Integer pageNum; //第几页

    private Integer pageSize; //页大小

    private Integer sort; //排序方式：1，升序；0，降序

    private String date; //日期

    private String startDate; //开始日期

    private String endDate; //结束日期

    private String dateb; //日期

    private Integer count; // 通用型Integer参数

    private String way; //方式

    private Integer number; //号码

    private String sgIssue; //期号

    private Integer id;

    private String ids;

    private String code;


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateb() {
        return dateb;
    }

    public void setDateb(String dateb) {
        this.dateb = dateb;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSgIssue() {
        return sgIssue;
    }

    public void setSgIssue(String sgIssue) {
        this.sgIssue = sgIssue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
