package com.caipiao.core.library.vo.lottery;

import com.mapper.domain.LhcPhoto;

import java.util.List;

/**
 * 六合彩图库ListVO
 *
 * @author lzy
 * @create 2018-08-03 13:50
 **/
public class LhcPhotoListVO {

    private String year; // 年份

    private List<LhcPhotoVO> photoList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<LhcPhotoVO> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<LhcPhotoVO> photoList) {
        this.photoList = photoList;
    }
}
