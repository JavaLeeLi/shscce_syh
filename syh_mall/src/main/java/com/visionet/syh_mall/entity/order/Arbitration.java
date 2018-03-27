package com.visionet.syh_mall.entity.order;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

@Entity
@Table(name="tbl_arbitration")
public class Arbitration extends IdEntity{
	private String orderId;
	private Date createTime = DateUtil.getCurrentDate();
	private Date updateTime = DateUtil.getCurrentDate();
	private int isDeleted = 0;
	private String arbitrationStatus;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getArbitrationStatus() {
		return arbitrationStatus;
	}
	public void setArbitrationStatus(String arbitrationStatus) {
		this.arbitrationStatus = arbitrationStatus;
	}
}
