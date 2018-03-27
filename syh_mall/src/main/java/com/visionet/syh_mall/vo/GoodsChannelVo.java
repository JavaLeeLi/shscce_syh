package com.visionet.syh_mall.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import com.visionet.syh_mall.entity.goods.GoodsChannelRule;

/**
 * @ClassName: GoodsChannelVo
 * @Description: 商品分销规则的Vo
 * @author chenghongzhan
 * @date 2017年10月18日 下午8:28:44
 *
 */
public class GoodsChannelVo {

	private String goodsChannelRuleID;
	@NotNull(message = "第一层返佣比例不能为空")
	private BigDecimal firstRate;

	public String getGoodsChannelRuleID() {
		return goodsChannelRuleID;
	}

	public void setGoodsChannelRuleID(String goodsChannelRuleID) {
		this.goodsChannelRuleID = goodsChannelRuleID;
	}

	public BigDecimal getFirstRate() {
		return firstRate;
	}

	public void setFirstRate(BigDecimal firstRate) {
		this.firstRate = firstRate;
	}

	/**
	 * @Title: convertPo @Description: 商品分销等级的Vo转Po @param @param
	 *         channelVo @param @param channelRule @param @return 设定文件 @return
	 *         GoodsChannelRule 返回类型 @throws
	 */
	public GoodsChannelRule convertPo(GoodsChannelVo channelVo, GoodsChannelRule channelRule) {
		channelRule.setFirstCommission(channelVo.getFirstRate().divide(new BigDecimal(100)));
		channelRule.setId(channelVo.getGoodsChannelRuleID());
		return channelRule;
	}

	@Override
	public String toString() {
		return "GoodsChannelVo [goodsChannelRuleID=" + goodsChannelRuleID + ", firstRate=" + firstRate + "]";
	}

}
