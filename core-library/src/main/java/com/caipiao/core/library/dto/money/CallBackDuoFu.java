package com.caipiao.core.library.dto.money;

public class CallBackDuoFu {
	private	String orderid ;//下单过程中商户系统传入的orderid 
	private	String opstate ;//0：支付成功 
	private	String ovalue ;//订单实际支付金额，单位元 
	private	String systime;//第一次通知时的时间戳，年/月/日 时：分：秒，如2010/04/05 21:50:58 
	private	String sign  ;//sign 32位小写MD5签名值，GB2312编码  MD5 签名源串及格式如下：orderid={}&opstate={}&ovalue={}key其中，key为商户密钥。 
	private	String sysorderid;//此次订单过程中多宝接口系统内的订单Id 
	private	String completiontime  ;//此次订单过程中多宝接口系统内的订单 结束时间。格式为年/月/日 时：分：秒，如2010/04/05 21:50:58 
	private	String type ;//银行类型，具体请参考文档
	private	String attach ;//备注信息，下单中attach原样返回 
	private	String msg;//订单结果说明 
	private	String RSA_sign ;//Base64+GB2312编码，“+”被换成“_” 
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getOpstate() {
		return opstate;
	}
	public void setOpstate(String opstate) {
		this.opstate = opstate;
	}
	public String getOvalue() {
		return ovalue;
	}
	public void setOvalue(String ovalue) {
		this.ovalue = ovalue;
	}
	public String getSystime() {
		return systime;
	}
	public void setSystime(String systime) {
		this.systime = systime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSysorderid() {
		return sysorderid;
	}
	public void setSysorderid(String sysorderid) {
		this.sysorderid = sysorderid;
	}
	public String getCompletiontime() {
		return completiontime;
	}
	public void setCompletiontime(String completiontime) {
		this.completiontime = completiontime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRSA_sign() {
		return RSA_sign;
	}
	public void setRSA_sign(String rSA_sign) {
		RSA_sign = rSA_sign;
	}
	public CallBackDuoFu() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
