package com.visionet.syh_mall.vo.cart;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 购物车入参
 * @author mulongfei
 * @date 2017年9月25日上午9:53:09
 */
public class CartGoodsInfo {
	@NotEmpty(message="未选择商品")
	private String goodsID;
	@NotNull(message="为选择商品数量")
	private Integer goodsNum;
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
	@Override
	public String toString() {
		return "CartGoodsInfo [goodsID=" + goodsID + ", goodsNum=" + goodsNum + "]";
	}
}
