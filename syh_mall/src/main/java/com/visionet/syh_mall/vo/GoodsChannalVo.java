package com.visionet.syh_mall.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.visionet.syh_mall.entity.goods.GoodsChannelRule;

public class GoodsChannalVo {
	private String goodsChannelId;
	@NotNull(message = "店铺ID不能为空")
	private String shopID;
	@NotNull(message = "模板名称不能为空")
	private String formworkName;
	@NotNull(message = "第一层返佣比例不能为空")
	private BigDecimal firstRate;

	public String getFormworkName() {
		return formworkName;
	}

	public void setFormworkName(String formworkName) {
		this.formworkName = formworkName;
	}

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getGoodsChannelId() {
		return goodsChannelId;
	}

	public void setGoodsChannelId(String goodsChannelId) {
		this.goodsChannelId = goodsChannelId;
	}

	public BigDecimal getFirstRate() {
		return firstRate;
	}

	public void setFirstRate(BigDecimal firstRate) {
		this.firstRate = firstRate;
	}

	/**
	 * @Title: convertPo @Description: 商家用户管理配置商品分销Vo转Po @param @param
	 *         channelVo @param @param channelRule @param @return 设定文件 @return
	 *         GoodsChannelRule 返回类型 @throws
	 */
	public GoodsChannelRule convertPo(GoodsChannalVo channelVo, GoodsChannelRule channelRule) {
		channelRule.setFormworkName(channelVo.getFormworkName());
		channelRule.setShopId(channelVo.getShopID());
		channelRule.setFirstCommission(channelVo.getFirstRate().divide(new BigDecimal(100)));
		return channelRule;
	}

	@Override
	public String toString() {
		return "GoodsChannalVo [goodsChannelId=" + goodsChannelId + ", shopID=" + shopID + ", formworkName="
				+ formworkName + ", firstRate=" + firstRate + "]";
	}

}
