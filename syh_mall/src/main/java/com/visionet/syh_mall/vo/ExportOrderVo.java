package com.visionet.syh_mall.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ExportOrderVo {
	private String orderNo;//订单标号
	private Date createTime;//创建时间
	private BigDecimal orderAmt;//订单金额
	private BigDecimal goodsAmt;//商品金额
	private BigDecimal postage;//邮费
	private String buyerAliasName;//买家昵称
	private String sellerAliasName;//卖家昵称
	private String buyerShopName;//店铺名称
	private String orderStatus;//订单状态
	private String expressBillNo;//快递编号
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}
	public BigDecimal getGoodsAmt() {
		return goodsAmt;
	}
	public void setGoodsAmt(BigDecimal goodsAmt) {
		this.goodsAmt = goodsAmt;
	}
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	public String getBuyerShopName() {
		return buyerShopName;
	}
	public void setBuyerShopName(String buyerShopName) {
		this.buyerShopName = buyerShopName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		String status = null;
		if("order_unpaid".equals(orderStatus)){
			status = "待支付";
		}
		if("order_unprocessed".equals(orderStatus)){
			status = "待发货";
		}
		if("order_return".equals(orderStatus)){
			status = "退货中";
		}
		if("order_unreceied".equals(orderStatus)){
			status = "待收货";
		}
		if("order_uncomment".equals(orderStatus)){
			status = "待评价";
		}
		if("order_completed".equals(orderStatus)){
			status = "已完成";
		}
		if("order_closed".equals(orderStatus)){
			status = "已关闭";
		}
		this.orderStatus = status;
	}
	public String getBuyerAliasName() {
		return buyerAliasName;
	}
	public void setBuyerAliasName(String buyerAliasName) {
		this.buyerAliasName = buyerAliasName;
	}
	public String getSellerAliasName() {
		return sellerAliasName;
	}
	public void setSellerAliasName(String sellerAliasName) {
		this.sellerAliasName = sellerAliasName;
	}
	public String getExpressBillNo() {
		return expressBillNo;
	}
	public void setExpressBillNo(String expressBillNo) {
		this.expressBillNo = expressBillNo;
	}
}
