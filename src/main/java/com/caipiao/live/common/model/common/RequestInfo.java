package com.caipiao.live.common.model.common;

/**
 * 接收参数模型
 * @author admin
 * @date 2017年8月3日 下午5:48:38
 * @param <T>
 */
public class RequestInfo<T>{

	private String apisign;

	private T data;

	public String getApisign() {
		return apisign;
	}

	public void setApisign(String apisign) {
		this.apisign = apisign;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RequestInfo{" +
				"apisign='" + apisign + '\'' +
				", data=" + data +
				'}';
	}
}
