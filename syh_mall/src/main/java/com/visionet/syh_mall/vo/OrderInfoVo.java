package com.visionet.syh_mall.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *@Author DM
 *@version ：2017年10月17日下午3:01:41
 *实体类
 */
public class OrderInfoVo {
	private String orderID;//交易订单ID	
	private String buyerID;//交易买家ID	
	private String buyerName;//	交易买家名称	
	private String buyerProvince;//交易买家所处省份
	private String buyerCity;//交易买家所处城市	
	private String orderSum;//交易订单金额
	private String orderGoodsID;//订单商品ID	
	private String orderGoodsName;//订单商品名称	
	private BigDecimal orderGoodsPrice;//订单商品成交价	
	private String orderTypeCode;//交易订单类型编码	
	private String orderTypeDesc;//交易订单类型描述	
	private Date orderTime;//交易订单成交时间
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(String buyerID) {
		this.buyerID = buyerID;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerProvince() {
		return buyerProvince;
	}
	public void setBuyerProvince(String buyerProvince) {
		this.buyerProvince = buyerProvince;
	}
	public String getBuyerCity() {
		return buyerCity;
	}
	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}
	public String getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(String orderSum) {
		this.orderSum = orderSum;
	}
	public String getOrderGoodsID() {
		return orderGoodsID;
	}
	public void setOrderGoodsID(String orderGoodsID) {
		this.orderGoodsID = orderGoodsID;
	}
	public String getOrderGoodsName() {
		return orderGoodsName;
	}
	public void setOrderGoodsName(String orderGoodsName) {
		this.orderGoodsName = orderGoodsName;
	}

	public BigDecimal getOrderGoodsPrice() {
		return orderGoodsPrice;
	}
	public void setOrderGoodsPrice(BigDecimal orderGoodsPrice) {
		this.orderGoodsPrice = orderGoodsPrice;
	}
	public String getOrderTypeCode() {
		return orderTypeCode;
	}
	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}
	public String getOrderTypeDesc() {
		return orderTypeDesc;
	}
	public void setOrderTypeDesc(String orderTypeDesc) {
		this.orderTypeDesc = orderTypeDesc;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
}
