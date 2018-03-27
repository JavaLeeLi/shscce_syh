package com.visionet.syh_mall.vo.shop;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.shop.FulfilRemit;

/**
 * @ClassName: FulfilRemitVo
 * @Description: 满减满送的Vo
 * @author chenghongzhan
 * @date 2017年9月30日 上午11:16:02
 *
 */
public class FulfilRemitVo {

	private String fulfilID;
	@NotBlank(message = "店铺ID不能为空")
	private String shopID;
	@NotNull(message = "满足金额不能为空")
	private BigDecimal fulfilAmt;
	@NotNull(message = "减免金额不能为空")
	private BigDecimal remitAmt;
	private String giftGoodsID;

	public String getFulfilID() {
		return fulfilID;
	}

	public void setFulfilID(String fulfilID) {
		this.fulfilID = fulfilID;
	}

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public BigDecimal getFulfilAmt() {
		return fulfilAmt;
	}

	public void setFulfilAmt(BigDecimal fulfilAmt) {
		this.fulfilAmt = fulfilAmt;
	}

	public BigDecimal getRemitAmt() {
		return remitAmt;
	}

	public void setRemitAmt(BigDecimal remitAmt) {
		this.remitAmt = remitAmt;
	}

	public String getGiftGoodsID() {
		return giftGoodsID;
	}

	public void setGiftGoodsID(String giftGoodsID) {
		this.giftGoodsID = giftGoodsID;
	}

	@Override
	public String toString() {
		return "FulfilRemitVo [fulfilID=" + fulfilID + ", shopID=" + shopID + ", fulfilAmt=" + fulfilAmt + ", remitAmt="
				+ remitAmt + ", giftGoodsID=" + giftGoodsID + "]";
	}

	/**
	 * @Title: converPo @Description: 满减满送的Vo转Po @param @param
	 *         fulfilRemitVo @param @param fulfilRemit @param @return
	 *         设定文件 @return FulfilRemit 返回类型 @throws
	 */
	public FulfilRemit converPo(FulfilRemitVo fulfilRemitVo, FulfilRemit fulfilRemit) {
		fulfilRemit.setFulfilAmt(fulfilRemitVo.getFulfilAmt());
		fulfilRemit.setGiftGoodsId(fulfilRemitVo.getGiftGoodsID());
		fulfilRemit.setRemitAmt(fulfilRemitVo.getRemitAmt());
		fulfilRemit.setShopId(fulfilRemitVo.getShopID());
		return fulfilRemit;
	}

}
