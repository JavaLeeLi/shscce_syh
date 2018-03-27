package com.visionet.syh_mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * 退款申请表实体类
 * 
 * @author mulongfei
 * @date 2017年9月8日上午10:40:57
 */
@Entity
@Table(name = "tbl_order_refund")
public class OrderRefund extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String orderId;// 订单id
	private String refundOrderNo;//退款订单号
	private Date applyTime;// 申请时间
	private BigDecimal refundSum;// 申请退款金额
	private String refundReason;// 退款原因
	private String buyerId;// 买家id
	private String buyerPhone;// 买家联系电话
	private String sellerId;// 卖家id
	private String refundStatusCode;// 退款申请处理状态码
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Integer isDeleted = 0;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRefundStatusCode() {
		return refundStatusCode;
	}

	public void setRefundStatusCode(String refundStatusCode) {
		this.refundStatusCode = refundStatusCode;
	}

	public BigDecimal getRefundSum() {
		return refundSum;
	}

	public void setRefundSum(BigDecimal refundSum) {
		this.refundSum = refundSum;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}
}
