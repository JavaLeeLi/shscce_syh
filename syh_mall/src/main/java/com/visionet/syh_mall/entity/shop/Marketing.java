package com.visionet.syh_mall.entity.shop;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: Marketing
 * @Description: 营销主表
 * @author chenghongzhan
 * @date 2017年8月31日 下午3:57:08
 *
 */

@Entity
@Table(name = "tbl_marketing")
public class Marketing extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String marketingCode;// 营销编码
	private String marketingName;// 营销名称
	private BigDecimal marketingPrice;// 营销价格
	private Integer marketingStatus;// 营销状态
	private Date updateTime = new Date();// 更新时间
	private Date createTime = new Date();// 创建时间
	private Integer isDeleted = 0;// 是否删除

	public Integer getMarketingStatus() {
		return marketingStatus;
	}

	public void setMarketingStatus(Integer marketingStatus) {
		this.marketingStatus = marketingStatus;
	}

	public String getMarketingCode() {
		return marketingCode;
	}

	public void setMarketingCode(String marketingCode) {
		this.marketingCode = marketingCode;
	}

	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	public BigDecimal getMarketingPrice() {
		return marketingPrice;
	}

	public void setMarketingPrice(BigDecimal marketingPrice) {
		this.marketingPrice = marketingPrice;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
