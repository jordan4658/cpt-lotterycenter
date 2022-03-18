package com.caipiao.live.common.model.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * PageBean
 *
 * @author lzy
 * @create 2018-06-04 17:28
 **/
public class PageResult<T extends Collection> {


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

    public static final String ORDER_ASC = "ASC";
    public static final String ORDER_DESC = "DESC";

    /** 总记录数 */
    private int totalCount;

    /** 总页数 */
    private int totalPage;

    /** 分页数据的起始位置 */
    private int offset;

    private int limit;

    /** 排序字段 */
    private String orderBy;

    /** 排序方式：asc, desc */
    private String order;

    /** 返回数据 */
    private T data;

    /**
     * 返回分页起始位置
     *
     * @return
     */
    @JSONField(serialize = false)
    @JsonIgnore
    public int getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public static PageResult checkAndInit(Integer pageNo, Integer pageSize) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNo(null == pageNo || pageNo <= 0 ? DEFAULT_PAGE_NO : pageNo);
        pageResult.setPageSize(null == pageSize || pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize);
        return pageResult;
    }

    public static PageResult getPageResult(PageBounds pageBounds) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNo(pageBounds.getPageNo());
        pageResult.setPageSize(pageBounds.getPageSize());
        return pageResult;
    }

    /**
     * 从page获取分页总数
     */
    public static PageResult getPageResult(PageBounds pageBounds, Page<?> data) {
        Integer totalCount = null;
        if (null != data && data.getTotal() > 0) {
            totalCount = (int) data.getTotal();
        }
        PageResult pageResult = getPageResult(pageBounds.getPageNo(), pageBounds.getPageSize(), totalCount);
        pageResult.setData(data);
        return pageResult;
    }

    public static PageResult getPageResult(PageBounds pageBounds, List data) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNo(pageBounds.getPageNo());
        pageResult.setPageSize(pageBounds.getPageSize());
        pageResult.setData(data);
        return pageResult;
    }

    public static PageResult getPageResult(Integer totalCount, PageBounds pageBounds, List data) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNo(pageBounds.getPageNo());
        pageResult.setPageSize(pageBounds.getPageSize());
        pageResult.setTotalCount(null == totalCount ? 0 : totalCount);
        pageResult.setData(data);
        return pageResult;
    }

    public static PageResult getPageResult(Integer pageNo, Integer pageSize, Integer totalCount) {
        return getPageResult(pageNo, pageSize, totalCount, null, null);
    }

    public static PageResult getPageResult(Integer pageNo, Integer pageSize, Integer totalCount, String orderBy, String order) {
        PageResult pageResult = PageResult.checkAndInit(pageNo, pageSize);
        pageResult.setOrderBy(orderBy);
        pageResult.setOrder(order);
        pageResult.setLimit(pageResult.getPageSize());
        pageResult.setTotalCount(totalCount == null ? 0 : totalCount);
        if (pageResult.getTotalCount() == 0) {
            pageResult.setTotalPage(0);
        } else {
            int totalPage = pageResult.getTotalCount() / pageResult.getPageSize();
            if (pageResult.getTotalCount() % pageResult.getPageSize() != 0) {
                totalPage += 1;
            }
            pageResult.setTotalPage(totalPage);
            //解决 pageNo 越界
            if (pageResult.getPageNo() > totalPage) {
                pageResult.setPageNo(totalPage);
            }
            int offset = (pageResult.getPageNo() - 1) * pageResult.getPageSize();
            pageResult.setOffset(offset);
        }
        return pageResult;
    }

    public static PageResult getPageResult(Integer pageNo, Integer pageSize, Integer totalCount, List data) {
        PageResult pageResult = PageResult.getPageResult(pageNo, pageSize, totalCount);
        pageResult.setData(data);
        return pageResult;
    }

    /**
     * 填充分页相关字段
     *
     * @param pageResult
     * @param map
     */
    public static void fillPageFields(PageResult pageResult, Map map) {
        if (null == pageResult) {
            pageResult = PageResult.checkAndInit(null, null);
        }
        if (null != map) {
            map.put("orderBy", pageResult.getOrderBy());
            map.put("order", pageResult.getOrder());
            map.put("offset", pageResult.getOffset());
            map.put("limit", pageResult.getLimit());
        }
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
        if (this.totalCount > 0) {
            this.totalPage = this.totalCount / this.pageSize + (this.totalCount % this.pageSize == 0 ? 0 : 1);
        }
        return totalPage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public String getOrder() {
        return null == order || "".equals(order) ? ORDER_DESC : order;
    }

    public void setOrder(String order) {
        this.order = order;
        if (null != this.order && !"".equals(order)) {
            order = order.toUpperCase();
            if (ORDER_ASC.equals(order) || ORDER_DESC.equals(order)) {
                return;
            }
            this.order = ORDER_ASC;
        }
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

//    /**
//     * 总记录数
//     */
//    private Long total = 0l;
//    /**
//     * 记录
//     */
//    private List<?> list = new ArrayList<>();
//
//    public PageResult() {
//    }
//
//    public PageResult(Page<?> list) {
//        super();
//        if (list != null && list.size() > 0) {
//            this.total = list.getTotal();
//            this.list = list;
//        }
//    }
//
//    /**
//     * @param total
//     * @param list
//     */
//    public PageResult(Long total, List<?> list) {
//        super();
//        this.total = total;
//        this.list = list;
//    }
//
//    /**
//     * @return the list
//     */
//    public List<?> getList() {
//        return list;
//    }
//
//    /**
//     * @param list the list to set
//     */
//    public void setList(List<?> list) {
//        this.list = list;
//    }
//
//    /**
//     * @param total the total to set
//     */
//    public void setTotal(Long total) {
//        this.total = total;
//    }
//
//    /**
//     * @return the total
//     */
//    public Long getTotal() {
//        return total;
//    }
}
