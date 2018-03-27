package com.visionet.syh_mall.vo.cart;

import java.util.List;

import javax.validation.Valid;

/**
 * 购物车入参类
 * @author mulongfei
 * @date 2017年9月25日上午9:49:26
 */
public class CartGoodsVo {
	private Integer operateType;//操作类型（1：加入  0：编辑）
	@Valid
	private List<CartGoodsInfo> goodsInfos;//购物车商品数组
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	public List<CartGoodsInfo> getGoodsInfos() {
		return goodsInfos;
	}
	public void setGoodsInfos(List<CartGoodsInfo> goodsInfos) {
		this.goodsInfos = goodsInfos;
	}
	@Override
	public String toString() {
		return "CartGoodsVo [operateType=" + operateType + ", goodsInfos=" + goodsInfos + "]";
	}
}
