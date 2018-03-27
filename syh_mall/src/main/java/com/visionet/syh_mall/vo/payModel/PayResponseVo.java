package com.visionet.syh_mall.vo.payModel;

public class PayResponseVo {
	private String bizOrderNo;//业务订单编号
	private String orderNo;//订单编号
	private String payInfo;//支付方式
	
	
	
	public String getBizOrderNo() {
		return bizOrderNo;
	}
	public void setBizOrderNo(String bizOrderNo) {
		this.bizOrderNo = bizOrderNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	
	
}
