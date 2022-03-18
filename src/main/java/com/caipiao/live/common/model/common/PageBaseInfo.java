package com.caipiao.live.common.model.common;

/**
 * ClassName: PageBase
 * Description: 分页基础对象
 *
 * @author hai
 * @since JDK 1.8
 * date: 2020/4/30 19:36
 */
public class PageBaseInfo {

    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    /** 页号 */
    protected Integer pageNo = 1;
    /** 分页大小 */
    protected Integer pageSize = 10;

    public Integer getPageNo() {
        return null == pageNo || pageNo <= 0 ? DEFAULT_PAGE_NO : pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = null == pageNo ? 1 : pageNo;
    }

    public Integer getPageSize() {
        return null == pageSize || pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = null == pageSize ? DEFAULT_PAGE_SIZE : pageSize;
    }
}
