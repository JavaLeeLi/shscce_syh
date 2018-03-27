package com.visionet.syh_mall.vo.finance;

import java.math.BigDecimal;
import java.util.Date;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;

/**
 * 对账文件vo
 * @author xiaofb
 * @time 2018年1月19日
 */
public class AccountFileOrderVo {
	//第三方对账文件订单信息
	private String allinpayOrderNo; //第三方支付订单号
	private Integer orderType; //订单类型  1.充值    2.消费   3.提现    4.托管代收   5.单笔托管代付    6.批量托管代付    10.退款
	private BigDecimal tradeAmt; //交易金额
	private BigDecimal fee;	//渠道手续费金额 
	private Date tradeDate; //交易时间
	private String bizOrderNo; //商户订单号
	
	//平台订单信息
	private String platformOrderNo;
	private BigDecimal platformTradeAmt;
	private BigDecimal platformFee;	//渠道手续费金额 
	
	//比较结果状态
	private Boolean status = true;
	
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
	
	public BigDecimal getFee() {
		if(null == this.fee){
			fee = new BigDecimal(0);
		}
		return fee;
	}
	public void setFee(String fee) {
		try {
			fee = AmountUtils.changeF2Y(fee); //分转元
		} catch (Exception e) {
			e.printStackTrace();
		}	
		this.fee = new BigDecimal(fee);
	}
	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}
	public void setTradeAmt(String tradeAmt) {
		try {
			tradeAmt = AmountUtils.changeF2Y(tradeAmt); //分转元
		} catch (Exception e) {
			e.printStackTrace();
		}	
		this.tradeAmt = new BigDecimal(tradeAmt);
	}
	
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		try {
			this.tradeDate = DateUtil.convertFromString(tradeDate, DateUtil.YMD_FULL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getBizOrderNo() {
		return bizOrderNo;
	}
	public void setBizOrderNo(String bizOrderNo) {
		this.bizOrderNo = bizOrderNo;
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
		if(null == platformFee){
			platformFee = new BigDecimal(0);
		}
		return platformFee;
	}
	public void setPlatformFee(BigDecimal platformFee) {
		this.platformFee = platformFee;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
 }
