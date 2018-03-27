package com.visionet.syh_mall.vo.finance;

import com.visionet.syh_mall.entity.IdEntity;

import java.math.BigDecimal;
import java.util.Date;

public class FundSummary extends IdEntity{
	private String userLoginName;//登录名
	private String userName;//用户名
	private String userType;//用户类型
	private Date accountCreateTime;//开户时间
	private BigDecimal firstAmt;//初始金额
	private BigDecimal lastAmt;//期末金额
	private BigDecimal frozenAmt;//冻结金额
	private BigDecimal withdrawal;//可用金额
	private BigDecimal incomeAmt;//收入
	private BigDecimal withdrawAmt;//提现金额
	private BigDecimal sellIncome;//出售收入
	private BigDecimal sellExpend;//出售支出
	private BigDecimal bidIncome;//竞拍收入
	private BigDecimal bidExpend;//竞拍支出
	private BigDecimal buyIncome;//求购收入
	private BigDecimal buyExpend;//求购支出
	private BigDecimal penalSumIncome;//违约金收入
	private BigDecimal penalSumExpend;//违约金支出
	private BigDecimal marketIncome;//营销收入
	private BigDecimal marketExpend;//营销支出
	private BigDecimal freightIncome;//邮费收入
	private BigDecimal premiumIncomeAmt;//保险费收入
	private BigDecimal premiumExpendAmt;//保险费支出
	private BigDecimal serviceIncomeAmt;//服务费收入
	private BigDecimal serviceExpendAmt;//服务费支出
	private BigDecimal commissionIncomeAmt;//佣金收入
	private BigDecimal commissionExpendAmt;//佣金支出
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
	public Date getAccountCreateTime() {
		return accountCreateTime;
	}
	public void setAccountCreateTime(Date accountCreateTime) {
		this.accountCreateTime = accountCreateTime;
	}
	public BigDecimal getFirstAmt() {
		return firstAmt;
	}
	public void setFirstAmt(BigDecimal firstAmt) {
		this.firstAmt = firstAmt;
	}
	public BigDecimal getLastAmt() {
		return lastAmt;
	}
	public void setLastAmt(BigDecimal lastAmt) {
		this.lastAmt = lastAmt;
	}
	public BigDecimal getFrozenAmt() {
		return frozenAmt;
	}
	public void setFrozenAmt(BigDecimal frozenAmt) {
		this.frozenAmt = frozenAmt;
	}
	public BigDecimal getWithdrawal() {
		return withdrawal;
	}
	public void setWithdrawal(BigDecimal withdrawal) {
		this.withdrawal = withdrawal;
	}
	public BigDecimal getIncomeAmt() {
		return incomeAmt;
	}
	public void setIncomeAmt(BigDecimal incomeAmt) {
		this.incomeAmt = incomeAmt;
	}
	public BigDecimal getWithdrawAmt() {
		return withdrawAmt;
	}
	public void setWithdrawAmt(BigDecimal withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}
	public BigDecimal getSellIncome() {
		return sellIncome;
	}
	public void setSellIncome(BigDecimal sellIncome) {
		this.sellIncome = sellIncome;
	}
	public BigDecimal getSellExpend() {
		return sellExpend;
	}
	public void setSellExpend(BigDecimal sellExpend) {
		this.sellExpend = sellExpend;
	}
	public BigDecimal getBidIncome() {
		return bidIncome;
	}
	public void setBidIncome(BigDecimal bidIncome) {
		this.bidIncome = bidIncome;
	}
	public BigDecimal getBidExpend() {
		return bidExpend;
	}
	public void setBidExpend(BigDecimal bidExpend) {
		this.bidExpend = bidExpend;
	}
	public BigDecimal getBuyIncome() {
		return buyIncome;
	}
	public void setBuyIncome(BigDecimal buyIncome) {
		this.buyIncome = buyIncome;
	}
	public BigDecimal getBuyExpend() {
		return buyExpend;
	}
	public void setBuyExpend(BigDecimal buyExpend) {
		this.buyExpend = buyExpend;
	}
	public BigDecimal getPenalSumIncome() {
		return penalSumIncome;
	}
	public void setPenalSumIncome(BigDecimal penalSumIncome) {
		this.penalSumIncome = penalSumIncome;
	}
	public BigDecimal getPenalSumExpend() {
		return penalSumExpend;
	}
	public void setPenalSumExpend(BigDecimal penalSumExpend) {
		this.penalSumExpend = penalSumExpend;
	}
	public BigDecimal getMarketIncome() {
		return marketIncome;
	}
	public void setMarketIncome(BigDecimal marketIncome) {
		this.marketIncome = marketIncome;
	}
	public BigDecimal getMarketExpend() {
		return marketExpend;
	}
	public void setMarketExpend(BigDecimal marketExpend) {
		this.marketExpend = marketExpend;
	}
	public BigDecimal getFreightIncome() {
		return freightIncome;
	}
	public void setFreightIncome(BigDecimal freightIncome) {
		this.freightIncome = freightIncome;
	}
	public BigDecimal getPremiumIncomeAmt() {
		return premiumIncomeAmt;
	}
	public void setPremiumIncomeAmt(BigDecimal premiumIncomeAmt) {
		this.premiumIncomeAmt = premiumIncomeAmt;
	}
	public BigDecimal getPremiumExpendAmt() {
		return premiumExpendAmt;
	}
	public void setPremiumExpendAmt(BigDecimal premiumExpendAmt) {
		this.premiumExpendAmt = premiumExpendAmt;
	}
	public BigDecimal getServiceIncomeAmt() {
		return serviceIncomeAmt;
	}
	public void setServiceIncomeAmt(BigDecimal serviceIncomeAmt) {
		this.serviceIncomeAmt = serviceIncomeAmt;
	}
	public BigDecimal getServiceExpendAmt() {
		return serviceExpendAmt;
	}
	public void setServiceExpendAmt(BigDecimal serviceExpendAmt) {
		this.serviceExpendAmt = serviceExpendAmt;
	}
	public BigDecimal getCommissionIncomeAmt() {
		return commissionIncomeAmt;
	}
	public void setCommissionIncomeAmt(BigDecimal commissionIncomeAmt) {
		this.commissionIncomeAmt = commissionIncomeAmt;
	}
	public BigDecimal getCommissionExpendAmt() {
		return commissionExpendAmt;
	}
	public void setCommissionExpendAmt(BigDecimal commissionExpendAmt) {
		this.commissionExpendAmt = commissionExpendAmt;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
