package com.visionet.syh_mall.entity.service;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 增值服务的实体类
 * 
 * @ClassName: ShopsMarketing
 * @Description: TODO()
 * @author chenghongzhan
 * @date 2017年8月28日 下午4:54:11
 *
 */

@Entity
@Table(name = "tbl_shops_marketing")
public class ShopsMarketing extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String marketingId;// 服务ID
	private String marketingName;// 服务名称
	private Integer status;// 服务状态
	private String shopId;// 店铺ID
	private Date validityDay;// 过期时间
	private Date createTime = new Date();// 创建时间
	private Date updateTime = new Date();// 修改时间

	public String getMarketingId() {
		return marketingId;
	}

	public void setMarketingId(String marketingId) {
		this.marketingId = marketingId;
	}

	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Date getValidityDay() {
		return validityDay;
	}

	public void setValidityDay(Date validityDay) {
		this.validityDay = validityDay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
