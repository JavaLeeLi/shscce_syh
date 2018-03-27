package com.visionet.syh_mall.entity.finance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;
/**
 * 店铺保证金实体
 * @author mulongfei
 * @date 2017年10月20日下午7:52:58
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tbl_bond")
public class Bond extends IdEntity{
	private String shopId;
	private BigDecimal bondAmt;
	private BigDecimal shopBalance;
	private Integer bondStatus;
	private Date createTime = DateUtil.getCurrentDate();
	private Date updateTime = DateUtil.getCurrentDate();
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public BigDecimal getBondAmt() {
		return bondAmt;
	}
	public void setBondAmt(BigDecimal bondAmt) {
		this.bondAmt = bondAmt;
	}
	public BigDecimal getShopBalance() {
		return shopBalance;
	}
	public void setShopBalance(BigDecimal shopBalance) {
		this.shopBalance = shopBalance;
	}
	public Integer getBondStatus() {
		return bondStatus;
	}
	public void setBondStatus(Integer bondStatus) {
		this.bondStatus = bondStatus;
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
