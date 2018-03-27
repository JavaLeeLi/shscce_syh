package com.visionet.syh_mall.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 订单结算入参类
 * @author mulongfei
 * @date 2017年9月1日上午10:22:43
 */
public class GoodQo {
	//private String BUYId;//求购商品id
	@NotEmpty(message="商品id不能为空")
	private String goodsID;//商品id
	@NotNull(message="商品单价不能为空")
	private BigDecimal goodsPrice;//商品单价
	@NotNull(message="商品数量不能为空")
	private Integer goodsNum;//商品数量
	private boolean isComplimentary = false;//是否是赠品
	private String discountTimeId;//限时折扣id
	private String fulfilRemitID;//满减满送
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	/*public String getBUYId() {
		return BUYId;
	}
	public void setBUYId(String bUYId) {
		BUYId = bUYId;
	}*/
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getDiscountTimeId() {
		return discountTimeId;
	}
	public void setDiscountTimeId(String discountTimeId) {
		this.discountTimeId = discountTimeId;
	}
	@Override
	public String toString() {
		return "GoodQo [goodsID=" + goodsID + ", goodsPrice=" + goodsPrice + ", goodsNum=" + goodsNum
				+ ", discountTimeId=" + discountTimeId + "]";
	}
	public boolean isComplimentary() {
		return isComplimentary;
	}
	public void setComplimentary(boolean isComplimentary) {
		this.isComplimentary = isComplimentary;
	}
	public String getFulfilRemitID() {
		return fulfilRemitID;
	}
	public void setFulfilRemitID(String fulfilRemitID) {
		this.fulfilRemitID = fulfilRemitID;
	}
	
}
