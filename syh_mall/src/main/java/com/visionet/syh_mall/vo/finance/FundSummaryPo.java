package com.visionet.syh_mall.vo.finance;

import java.math.BigDecimal;
import java.util.Date;

public class FundSummaryPo {
	private String userLoginName;//会员账号
	private String userName;//会员姓名
	private BigDecimal littleAmt;//最低余额
	private BigDecimal heightAmt;//最高余额
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private int pageIndex;
	private int itemCount;
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
	public BigDecimal getLittleAmt() {
		return littleAmt;
	}
	public void setLittleAmt(BigDecimal littleAmt) {
		this.littleAmt = littleAmt;
	}
	public BigDecimal getHeightAmt() {
		return heightAmt;
	}
	public void setHeightAmt(BigDecimal heightAmt) {
		this.heightAmt = heightAmt;
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
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
}
