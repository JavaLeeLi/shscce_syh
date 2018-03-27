package com.visionet.syh_mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 订单代付业务流水
 * @author xiaofb
 * @time 2017年10月18日
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tbl_payment_flow")
public class PaymentFlow extends IdEntity {
	private String bussOrderNo;	//业务订单号
	private String sourceOrderNo;	//源订单号
	private String gatheringUserId;	//收款人用户id
	private String payUserId;	//付款人用户id
	private BigDecimal paymentAmt;	//代付金额
	private String status;	//代付状态
	private Date createTime;	//创建时间
	private Date updateTime;	//更新时间
	
	
	public String getBussOrderNo() {
		return bussOrderNo;
	}
	public void setBussOrderNo(String bussOrderNo) {
		this.bussOrderNo = bussOrderNo;
	}
	public String getSourceOrderNo() {
		return sourceOrderNo;
	}
	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}
	public String getGatheringUserId() {
		return gatheringUserId;
	}
	public void setGatheringUserId(String gatheringUserId) {
		this.gatheringUserId = gatheringUserId;
	}
	public String getPayUserId() {
		return payUserId;
	}
	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}
	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
