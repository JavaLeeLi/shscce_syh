package com.visionet.syh_mall.service.export;

import java.math.BigDecimal;
import java.util.Date;

public class ExportWithdrawVo {
	private String userLoginName;
	private BigDecimal withdrawAmt;
	private String cardNo;
	private String cardUserName;
	private String status;
	private Date applyTime;
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public BigDecimal getWithdrawAmt() {
		return withdrawAmt;
	}
	public void setWithdrawAmt(BigDecimal withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardUserName() {
		return cardUserName;
	}
	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
}
