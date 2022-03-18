package com.caipiao.core.library.dto;

/**
 * @author lzy
 * @create 2018-06-04 17:33
 **/
public class PageInfoDTO {
    /**
     * 当前页数,不传默认第一页
     */
    private int pageNum = 1;

    /**
     * 每页显示的记录数
     * 不传默认显示10条
     */
    private int pageSize = 10;

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageInfoDTO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
