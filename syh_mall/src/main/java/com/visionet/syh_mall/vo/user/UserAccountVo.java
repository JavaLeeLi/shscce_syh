package com.visionet.syh_mall.vo.user;

import java.math.BigDecimal;

public class UserAccountVo {
	private String loginName;//登录名
	private String userName;//昵称
	private BigDecimal balance;//余额
	private BigDecimal withdrawal;//可用余额
	private BigDecimal frozenAmt;//冻结金额
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getWithdrawal() {
		return withdrawal;
	}
	public void setWithdrawal(BigDecimal withdrawal) {
		this.withdrawal = withdrawal;
	}
	public BigDecimal getFrozenAmt() {
		return frozenAmt;
	}
	public void setFrozenAmt(BigDecimal frozenAmt) {
		this.frozenAmt = frozenAmt;
	}
}
