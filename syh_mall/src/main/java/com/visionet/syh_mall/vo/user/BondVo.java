package com.visionet.syh_mall.vo.user;

import java.math.BigDecimal;
import java.util.Date;

public class BondVo {
	private String shopName;//店铺名
	private String status;//状态
	private BigDecimal bondAmt;//保证金金额
	private BigDecimal shopBalance;//店铺余额
	private BigDecimal amt;//应缴金额
	private Date bondTime;//缴纳时间
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Date getBondTime() {
		return bondTime;
	}
	public void setBondTime(Date bondTime) {
		this.bondTime = bondTime;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
}
