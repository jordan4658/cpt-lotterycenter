package com.caipiao.core.library.model;

/**
 * PageBean
 *
 * @author lzy
 * @create 2018-06-04 17:28
 **/
public class PageResult<T> {
    /**
     * 当前页数,不传默认第一页
     */
    private int pageNum = 1;

    /**
     * 每页显示的记录数
     * 不传默认显示10条
     */
    private int pageSize = 10;

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 返回数据
     */
    private T data;

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
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getTotalPage() {
        return this.totalCount%this.pageSize==0?this.totalCount/this.pageSize:this.totalCount/this.pageSize+1;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public static <T> PageResult<T> getPagePesult(Integer pageNum, Integer pageSize, Integer totalCount) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageNum(pageNum == null ? 1 : pageNum);
        pageResult.setPageSize(pageSize == null ? 10 : pageSize);
        pageResult.setTotalCount(totalCount == null ? 0 : totalCount);
        pageResult.setTotalPage(totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1);
        return pageResult;
    }

    public static <T> PageResult<T> getPagePesult(Integer pageNum, Integer pageSize, Integer totalCount, T data) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageNum(pageNum == null ? 1 : pageNum);
        pageResult.setPageSize(pageSize == null ? 10 : pageSize);
        pageResult.setTotalCount(totalCount == null ? 0 : totalCount);
        pageResult.setTotalPage(totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1);
        pageResult.setData(data);
        return pageResult;
    }
}
