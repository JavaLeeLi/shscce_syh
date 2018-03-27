package com.visionet.syh_mall.vo.shop;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.shop.Marketing;

/**
 * 增值服务的VO chenghongzhan
 */
public class MarketVo {
	@NotBlank(message = "增值服务ID不能为空")
	private String marketingID;
	@NotNull(message = "增值服务价格不能为空")
	private BigDecimal marketingPrice;
	@NotNull(message = "增值服务状态不能为空")
	private Integer marketingStatus;

	public String getMarketingID() {
		return marketingID;
	}

	public void setMarketingID(String marketingID) {
		this.marketingID = marketingID;
	}

	public BigDecimal getMarketingPrice() {
		return marketingPrice;
	}

	public void setMarketingPrice(BigDecimal marketingPrice) {
		this.marketingPrice = marketingPrice;
	}

	public Integer getMarketingStatus() {
		return marketingStatus;
	}

	public void setMarketingStatus(Integer marketingStatus) {
		this.marketingStatus = marketingStatus;
	}

	/**
	 * @Title: converPo @Description: 增值服务的编辑 @param @param
	 *         marketVo @param @param marketing @param @return 设定文件 @return
	 *         Marketing 返回类型 @throws
	 */
	public Marketing converPo(MarketVo marketVo, Marketing marketing) {
		marketing.setMarketingPrice(marketVo.getMarketingPrice());
		marketing.setMarketingStatus(marketVo.getMarketingStatus());
		marketing.setUpdateTime(DateUtil.getCurrentDate());
		return marketing;
	}
	
	@Override
	public String toString() {
		return "MarketVo [marketingID=" + marketingID + ", marketingPrice=" + marketingPrice + ", marketingStatus="
				+ marketingStatus + "]";
	}

}
