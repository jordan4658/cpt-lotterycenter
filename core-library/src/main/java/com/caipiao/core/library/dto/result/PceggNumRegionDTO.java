package com.caipiao.core.library.dto.result;

/**
 * @author ShaoMing
 * @datetime 2018/7/27 15:08
 */
public class PceggNumRegionDTO {
    private Integer open1;
    private Integer open2;
    private Integer open3;
    private Integer noOpen1;
    private Integer noOpen2;
    private Integer noOpen3;

    public PceggNumRegionDTO() {
    }

    public PceggNumRegionDTO(Integer open1, Integer open2, Integer open3, Integer noOpen1, Integer noOpen2, Integer noOpen3) {
        this.open1 = open1;
        this.open2 = open2;
        this.open3 = open3;
        this.noOpen1 = noOpen1;
        this.noOpen2 = noOpen2;
        this.noOpen3 = noOpen3;
    }

    public Integer getOpen1() {
        return open1;
    }

    public void setOpen1(Integer open1) {
        this.open1 = open1;
    }

    public Integer getOpen2() {
        return open2;
    }

    public void setOpen2(Integer open2) {
        this.open2 = open2;
    }

    public Integer getOpen3() {
        return open3;
    }

    public void setOpen3(Integer open3) {
        this.open3 = open3;
    }

    public Integer getNoOpen1() {
        return noOpen1;
    }

    public void setNoOpen1(Integer noOpen1) {
        this.noOpen1 = noOpen1;
    }

    public Integer getNoOpen2() {
        return noOpen2;
    }

    public void setNoOpen2(Integer noOpen2) {
        this.noOpen2 = noOpen2;
    }

    public Integer getNoOpen3() {
        return noOpen3;
    }

    public void setNoOpen3(Integer noOpen3) {
        this.noOpen3 = noOpen3;
    }
}
