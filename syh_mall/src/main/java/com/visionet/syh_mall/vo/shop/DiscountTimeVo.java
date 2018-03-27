package com.visionet.syh_mall.vo.shop;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.marketing.DiscountTime;

/**
 * @ClassName: DiscountTimeVo
 * @Description: 限时折扣的Vo
 * @author chenghongzhan
 * @date 2017年9月30日 下午1:43:51
 *
 */
public class DiscountTimeVo {

	private String discountID;
	@NotBlank(message = "店铺ID不能为空")
	private String shopID;
	@NotBlank(message = "限时折扣的活动名称不能为空")
	private String discountName;
	@NotBlank(message = "活动商品ID不能为空")
	private String goodsID;
	@NotNull(message = "限时折扣价不能为空")
	private BigDecimal discountPrice;
	@NotNull(message = "限制购买数量不能为空")
	private Integer discountLimitNum;
	@NotNull(message = "活动库存不能为空")
	private Integer discountStockNum;
	@NotNull(message = "活动开始时间不能为空")
	private Long startTime;
	@NotNull(message = "活动结束时间不能为空")
	private Long endTime;

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getDiscountID() {
		return discountID;
	}

	public void setDiscountID(String discountID) {
		this.discountID = discountID;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getDiscountLimitNum() {
		return discountLimitNum;
	}

	public void setDiscountLimitNum(Integer discountLimitNum) {
		this.discountLimitNum = discountLimitNum;
	}

	public Integer getDiscountStockNum() {
		return discountStockNum;
	}

	public void setDiscountStockNum(Integer discountStockNum) {
		this.discountStockNum = discountStockNum;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @Title: converPo @Description: 限时折扣的Vo转Po @param @param
	 *         discountTimeVo @param @param discountTime @param @return
	 *         设定文件 @return DiscountTime 返回类型 @throws
	 */
	public DiscountTime converPo(DiscountTimeVo discountTimeVo, DiscountTime discountTime) {
		discountTime.setShopId(discountTimeVo.getShopID());
		discountTime.setDiscountPrice(discountTimeVo.getDiscountPrice());
		discountTime.setEndTime(new Date(discountTimeVo.getEndTime()));
		discountTime.setGoodsId(discountTimeVo.getGoodsID());
		discountTime.setLimitNum(discountTimeVo.getDiscountLimitNum());
		discountTime.setStartTime(new Date(discountTimeVo.getStartTime()));
		discountTime.setStockNum(discountTimeVo.getDiscountStockNum());
		discountTime.setDiscountName(discountTimeVo.getDiscountName());
		return discountTime;
	}

	@Override
	public String toString() {
		return "DiscountTimeVo [shopID=" + shopID + ", discountID=" + discountID + ", goodsID=" + goodsID
				+ ", discountPrice=" + discountPrice + ", discountLimitNum=" + discountLimitNum + ", discountStockNum="
				+ discountStockNum + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
