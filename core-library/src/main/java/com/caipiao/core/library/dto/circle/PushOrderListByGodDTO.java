package com.caipiao.core.library.dto.circle;

public class PushOrderListByGodDTO {
    private Integer pageNum; //第几页
    private Integer pageSize; //页大小
    private Integer godId;  //大神id
    private Integer finishStatus;   //状态：1推单未开奖3已开奖结算

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

    public Integer getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Integer finishStatus) {
        this.finishStatus = finishStatus;
    }
}
