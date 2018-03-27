package com.visionet.syh_mall.vo;

import java.math.BigDecimal;

/**
 * 订单改价入参
 * @author mulongfei
 * @date 2017年9月8日下午3:02:47
 */
public class OrderInfo {
	private String orderID;
	private BigDecimal orderSum;
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public BigDecimal getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(BigDecimal orderSum) {
		this.orderSum = orderSum;
	}
	
}
