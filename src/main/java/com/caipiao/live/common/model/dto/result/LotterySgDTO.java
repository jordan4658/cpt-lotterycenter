package com.caipiao.live.common.model.dto.result;

/**
 * 彩种赛果资讯的DTO
 *
 * @author lzy
 * @create 2018-07-20 18:10
 **/
public class LotterySgDTO {
    private String type; //类型
    private Integer issue; //期数
    private String year; //年份
    //排序方式：1，升序；0，降序
    private Integer sort; //排序方式：1，升序；0，降序
    private String date; //日期
    private String dateb; //日期
    private Integer count; // 通用型Integer参数
    private String way; //方式
    private Integer number; //号码
    private String sgIssue; //期号
    private Integer id;
    private String ids;

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

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
}
