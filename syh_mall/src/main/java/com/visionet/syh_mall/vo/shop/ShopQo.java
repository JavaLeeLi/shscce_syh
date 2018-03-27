package com.visionet.syh_mall.vo.shop;

import org.hibernate.validator.constraints.NotEmpty;

public class ShopQo {
	@NotEmpty(message = "店铺ID不能为空")
	private String shopID;
	private String goodsID;

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	@Override
	public String toString() {
		return "ShopQo [shopID=" + shopID + ", goodsID=" + goodsID + "]";
	}

}
