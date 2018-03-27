package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 支付方式实体类
 * @author mulongfei
 * @date 2017年9月1日上午11:04:28
 */
@Entity
@Table(name="tbl_pay_kinds")
public class PayKinds extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String payMethodCode;//
	private String payMethodName;//
	private Integer isAvailable;//
	private Date createTime;
	private Date updateTime;
	public String getPayMethodCode() {
		return payMethodCode;
	}
	public void setPayMethodCode(String payMethodCode) {
		this.payMethodCode = payMethodCode;
	}
	public String getPayMethodName() {
		return payMethodName;
	}
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}
	public Integer getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
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
