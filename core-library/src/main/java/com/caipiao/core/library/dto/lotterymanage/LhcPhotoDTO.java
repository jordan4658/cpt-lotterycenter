package com.caipiao.core.library.dto.lotterymanage;

import com.caipiao.core.library.tool.TimeHelper;
import com.mapper.domain.LhcPhoto;

/**
 * @author lzy
 * @create 2018-08-13 17:39
 **/
public class LhcPhotoDTO {

    private Integer id;

    /**
     * 说明: 分类id
     */
    private Integer categoryId;

    /**
     * 说明: 期号
     */
    private String issue;

    /**
     * 说明: 图片路径
     */
    private String url;

    public static LhcPhoto getLhcPhoto(LhcPhotoDTO lhcPhotoDTO) {
        if (lhcPhotoDTO == null) {
            return null;
        }
        LhcPhoto lhcPhoto = new LhcPhoto();
        lhcPhoto.setId(lhcPhotoDTO.getId());
        lhcPhoto.setCategoryId(lhcPhotoDTO.getCategoryId());
        lhcPhoto.setIssue(lhcPhotoDTO.getIssue());
        lhcPhoto.setUrl(lhcPhotoDTO.getUrl());
        lhcPhoto.setCreateTime(TimeHelper.date());
        return lhcPhoto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
