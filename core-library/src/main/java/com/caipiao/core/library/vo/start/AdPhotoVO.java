package com.caipiao.core.library.vo.start;

/**
 * @author lzy
 * @create 2018-09-07 17:17
 **/
public class AdPhotoVO {

    private Integer id;

    private String photo;  //图片路径

    private String url;  // 跳转url

    private String title;

    /**
     * 说明: 有效开始时间
     */
    private String startTime;

    /**
     * 说明: 有效结束时间
     */
    private String endTime;

    private Integer sort;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
