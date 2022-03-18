package com.caipiao.core.library.model.base;

/**
 * 基础DTO
 * @author Qiang
 * @version 1.0.0
 * @date 2017年9月22日 上午9:44:39
 *
 */
public class BaseDTO {

	/**
	 * 当前页数
	 */
	private int pageNum = 1;

	/**
	 * 每页显示的记录数
	 */
	private int pageSize = 10;

	/**
	 * 总记录数
	 */
	private int totalCount;

	/**
	 * 总页数
	 */
	@SuppressWarnings("unused")
	private int totalPage;

	/**
	 * 10位时间戳，当接口没有参数的时候可以传入，
	 * 秘钥校验用
	 */
	private int time;

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

	/**
	 * 从第offset个开始查询
	 * @return
	 */
	public int getOffset() {
		return (this.pageNum - 1) * this.pageSize;
	}

	/**
	 * 查询的个数
	 * @return
	 */
	public int getLimit() {
		return  this.pageSize;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "BaseDTO{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", totalCount=" + totalCount +
				", totalPage=" + totalPage +
				", time=" + time +
				'}';
	}
}
