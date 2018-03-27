package com.visionet.syh_mall.entity.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 提现记录实体
 * @author mulongfei
 * @date 2017年10月23日下午3:03:13
 */
@Entity
@Table(name="tbl_draw_cash")
public class DrawCash extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String userId;
	private BigDecimal drawAmt;
	private String drawWay;
	private String drawOrderNo;
	private String drawAccount;
	private String accountName;
	private String statusCode;
	private String rejectReason;
	private String reviewBy;
	private Date createTime;
	private Date updateTime;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigDecimal getDrawAmt() {
		return drawAmt;
	}
	public void setDrawAmt(BigDecimal drawAmt) {
		this.drawAmt = drawAmt;
	}
	public String getDrawWay() {
		return drawWay;
	}
	public void setDrawWay(String drawWay) {
		this.drawWay = drawWay;
	}
	public String getDrawAccount() {
		return drawAccount;
	}
	public void setDrawAccount(String drawAccount) {
		this.drawAccount = drawAccount;
	}
	public String getDrawOrderNo() {
		return drawOrderNo;
	}
	public void setDrawOrderNo(String drawOrderNo) {
		this.drawOrderNo = drawOrderNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getReviewBy() {
		return reviewBy;
	}
	public void setReviewBy(String reviewBy) {
		this.reviewBy = reviewBy;
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
