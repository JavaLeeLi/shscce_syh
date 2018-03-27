package com.visionet.syh_mall.entity.finance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 对账文件表
 * @author xiaofb
 * @time 2018年1月24日
 */
@Entity
@Table(name="tbl_statement_order")
public class StatementOrder  extends IdEntity {
	
	private String allinpayOrderNo; //通联对账文件订单号
	private Integer orderType;	 //订单类型  1.充值    2.消费   3.提现    4.托管代收   5.单笔托管代付    6.批量托管代付    10.退款
	private BigDecimal allinpayTradeAmt; //通联对账文件交易金额
	private BigDecimal allinpayFee;	//通联对账文件手续费
	private Date allinpayTradeDate;	//通联交易日期
	private String allinpayBizOrderNo;	//通联业务订单号
	private String platformOrderNo;	//平台订单
	private BigDecimal platformTradeAmt;	//平台交易金额
	private BigDecimal platformFee;	//平台手续费
	private Integer comporeResult;	//对比结果 1 false  2 true
	private Date createTime;		//创建时间
	private Date  updateTime;	//更新时间
	public String getAllinpayOrderNo() {
		return allinpayOrderNo;
	}
	public void setAllinpayOrderNo(String allinpayOrderNo) {
		this.allinpayOrderNo = allinpayOrderNo;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public BigDecimal getAllinpayTradeAmt() {
		return allinpayTradeAmt;
	}
	public void setAllinpayTradeAmt(BigDecimal allinpayTradeAmt) {
		this.allinpayTradeAmt = allinpayTradeAmt;
	}
	public BigDecimal getAllinpayFee() {
		return allinpayFee;
	}
	public void setAllinpayFee(BigDecimal allinpayFee) {
		this.allinpayFee = allinpayFee;
	}
	public Date getAllinpayTradeDate() {
		return allinpayTradeDate;
	}
	public void setAllinpayTradeDate(Date allinpayTradeDate) {
		this.allinpayTradeDate = allinpayTradeDate;
	}
	public String getAllinpayBizOrderNo() {
		return allinpayBizOrderNo;
	}
	public void setAllinpayBizOrderNo(String allinpayBizOrderNo) {
		this.allinpayBizOrderNo = allinpayBizOrderNo;
	}
	public String getPlatformOrderNo() {
		return platformOrderNo;
	}
	public void setPlatformOrderNo(String platformOrderNo) {
		this.platformOrderNo = platformOrderNo;
	}
	public BigDecimal getPlatformTradeAmt() {
		return platformTradeAmt;
	}
	public void setPlatformTradeAmt(BigDecimal platformTradeAmt) {
		this.platformTradeAmt = platformTradeAmt;
	}
	public BigDecimal getPlatformFee() {
		return platformFee;
	}
	public void setPlatformFee(BigDecimal platformFee) {
		this.platformFee = platformFee;
	}
	public Integer getComporeResult() {
		return comporeResult;
	}
	public void setComporeResult(Integer comporeResult) {
		this.comporeResult = comporeResult;
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
