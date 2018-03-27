package com.visionet.syh_mall.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * 商品分销规则的实体类
 * 
 * @author chenghongzhan
 * @version 2017年8月23日 下午5:53:31
 *
 */
@Entity
@Table(name = "tbl_goods_channel_rule")
public class GoodsChannelRule extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String shopId;
	private BigDecimal firstCommission;// 第一层返佣比例
	private String createBy;
	private String updateBy;
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Integer isDeleted = 0;// 是否删除
	private String formworkName;

	public String getFormworkName() {
		return formworkName;
	}

	public void setFormworkName(String formworkName) {
		this.formworkName = formworkName;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getFirstCommission() {
		return firstCommission;
	}

	public void setFirstCommission(BigDecimal firstCommission) {
		this.firstCommission = firstCommission;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
