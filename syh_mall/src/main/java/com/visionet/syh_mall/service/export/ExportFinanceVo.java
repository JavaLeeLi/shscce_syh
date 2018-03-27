package com.visionet.syh_mall.service.export;

import java.math.BigDecimal;
import java.util.Date;

public class ExportFinanceVo {
	private String userLoginName;
	private String userName;
	private String flowNo;
	private String goodName;
	private String businessType;
	private String flowType;
	private BigDecimal income;
	private BigDecimal expend;
	private String described;
	private Date flowCreateTime;
	private String remark;
	private String payMethod;
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public BigDecimal getExpend() {
		return expend;
	}
	public void setExpend(BigDecimal expend) {
		this.expend = expend;
	}
	public String getDescribed() {
		return described;
	}
	public void setDescribed(String described) {
		this.described = described;
	}
	public Date getFlowCreateTime() {
		return flowCreateTime;
	}
	public void setFlowCreateTime(Date flowCreateTime) {
		this.flowCreateTime = flowCreateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
}
