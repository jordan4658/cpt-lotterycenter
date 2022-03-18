package com.caipiao.live.common.model.dto.order;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
	private String accno;

	private String orderno;
	private String orderstatus;

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	private List<String> orderstatusList = new ArrayList<String>();

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public List<String> getOrderstatusList() {
		return orderstatusList;
	}

	public void setOrderstatusList(List<String> orderstatusList) {
		this.orderstatusList = orderstatusList;
	}

}
