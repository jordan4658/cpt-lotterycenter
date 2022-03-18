package com.caipiao.core.library.dto.result;

/**
 * @author ShaoMing
 * @datetime 2018/7/27 15:07
 */
public class PceggNumberDTO {
    private Integer num;
    private PceggNumRegionDTO numRegion;
    private Integer openCount;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public PceggNumRegionDTO getNumRegion() {
        return numRegion;
    }

    public void setNumRegion(PceggNumRegionDTO numRegion) {
        this.numRegion = numRegion;
    }

    public Integer getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Integer openCount) {
        this.openCount = openCount;
    }
}
