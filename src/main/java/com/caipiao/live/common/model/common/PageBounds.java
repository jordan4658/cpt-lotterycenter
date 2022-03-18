package com.caipiao.live.common.model.common;

import com.caipiao.live.common.model.Order;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分页查询对象
 */
public class PageBounds  implements Serializable {


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

    private static final long serialVersionUID = -6414350656252331011L;

    /** 分页排序信息 */
    protected List<Order> orders = new ArrayList<>();

    public PageBounds() {
    }

    public PageBounds(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageBounds(Order... order) {
        this(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE, order);
    }

    public PageBounds(int pageNo, int pageSize, Order... order) {
        this(pageNo, pageSize, Arrays.asList(order));
    }

    public PageBounds(int pageNo, int pageSize, List<Order> orders) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orders = orders;
    }

    public RowBounds toRowBounds() {
        // startPage()方法后面必须紧跟的是要分页的查询语句
		/*4. 什么时候会导致不安全的分页？
		PageHelper 方法使用了静态的 ThreadLocal 参数，分页参数和线程是绑定的。
		只要你可以保证在 PageHelper 方法调用后紧跟 MyBatis 查询方法，这就是安全的。

		因为 PageHelper 在 finally 代码段中自动清除了 ThreadLocal 存储的对象。
		如果代码在进入 Executor 前发生异常，就会导致线程不可用，这属于人为的 Bug
		（例如接口方法和 XML 中的不匹配，导致找不到 MappedStatement 时）， 这种情况由于线程不可用，也不会导致 ThreadLocal 参数被错误的使用。*/
        //PageHelper.startPage(pageNo, pageSize);

        int offset = 0;
        if (this.pageNo > 0) {
            offset = (this.pageNo - 1) * this.pageSize;
        }
        return new RowBounds(offset, this.pageSize);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
