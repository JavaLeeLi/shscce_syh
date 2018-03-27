package com.visionet.syh_mall.entity.order;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * 订单服务实体类
 * @author mulongfei
 * @date 2017年10月19日下午6:39:07
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tbl_order_service")
public class OrderServiceEntity extends IdEntity{
	private String orderId;
	private String buyerId;
	private String sellerId;
	private String serviceReason;
	private String otherReason;
	private String statusCode;
	private Integer orderScore;
	private Integer shopOwnerScore;
	private String orderComment;
	private Date createTime = DateUtil.getCurrentDate();
	private Date updateTime = DateUtil.getCurrentDate();
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getServiceReason() {
		return serviceReason;
	}
	public void setServiceReason(String serviceReason) {
		this.serviceReason = serviceReason;
	}
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Integer getOrderScore() {
		return orderScore;
	}
	public void setOrderScore(Integer orderScore) {
		this.orderScore = orderScore;
	}
	public Integer getShopOwnerScore() {
		return shopOwnerScore;
	}
	public void setShopOwnerScore(Integer shopOwnerScore) {
		this.shopOwnerScore = shopOwnerScore;
	}
	public String getOrderComment() {
		return orderComment;
	}
	public void setOrderComment(String orderComment) {
		this.orderComment = orderComment;
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
