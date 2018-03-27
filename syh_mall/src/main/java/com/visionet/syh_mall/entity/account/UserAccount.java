package com.visionet.syh_mall.entity.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 用户账户
 * @author xiaofb
 * @time 2017年9月20日
 */
@Entity
@Table(name="tbl_user_account")
public class UserAccount extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String userId;
	private BigDecimal balance;
	private BigDecimal withdrawal;
	private BigDecimal frozenAmt;
	private String alipay;
	private String wechat;
	private String unionpay;
	private Date updateTime; 
	private Date createTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getUnionpay() {
		return unionpay;
	}
	public void setUnionpay(String unionpay) {
		this.unionpay = unionpay;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
