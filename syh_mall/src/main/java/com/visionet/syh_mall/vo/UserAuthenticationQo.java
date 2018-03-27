package com.visionet.syh_mall.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.visionet.syh_mall.common.utils.OrderCondition;
import com.visionet.syh_mall.common.utils.PageInfo;

/**
 *@Author DM
 *@version ：2017年9月29日上午10:51:27
 *实体类
 */
public class UserAuthenticationQo extends PageInfo {
	private String mbrLoginName;//会员登录名
	private String mbrName;//会员昵称
	private String mbrPhone;//会员手机号
	private String mbrTypeCode;//会员认证类型编码
	private Date startTime;//起始认证时间
	private Date endTime;//截止认证时间
	private int status;
	private String id;
	private String userTypeOngoingCode;
	protected List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
	public String getMbrLoginName() {
		return mbrLoginName;
	}
	public void setMbrLoginName(String mbrLoginName) {
		this.mbrLoginName = mbrLoginName;
	}
	public String getMbrName() {
		return mbrName;
	}
	public void setMbrName(String mbrName) {
		this.mbrName = mbrName;
	}
	public String getMbrPhone() {
		return mbrPhone;
	}
	public void setMbrPhone(String mbrPhone) {
		this.mbrPhone = mbrPhone;
	}
	public String getMbrTypeCode() {
		return mbrTypeCode;
	}
	public void setMbrTypeCode(String mbrTypeCode) {
		this.mbrTypeCode = mbrTypeCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public List<OrderCondition> getOrderConditions() {
		return orderConditions;
	}
	public void setOrderConditions(List<OrderCondition> orderConditions) {
		this.orderConditions = orderConditions;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserTypeOngoingCode() {
		return userTypeOngoingCode;
	}
	public void setUserTypeOngoingCode(String userTypeOngoingCode) {
		this.userTypeOngoingCode = userTypeOngoingCode;
	}
	
}
