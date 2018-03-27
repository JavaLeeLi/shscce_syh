package com.visionet.syh_mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年8月21日下午4:29:49 订单实体类
 */
@Entity
@Table(name = "tbl_order")
public class Order extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String orderSn;// 订单编号
	private String purchaseGoodsId;// 商品求购记录ID（tbl_goods表中求购商品的ID）
	private String payKindsId;// 支付方式
	private String payKindsName;// 支付方式名称
	private BigDecimal totalPrice;// 订单金额
	private String buyerId;// 买家账户id
	private String buyerPhone;// 买家联系电话
	private String shopId;// 店铺id
	private String shopOwnerId;// 店主用户ID
	private String receiverName;// 收件人姓名
	private String receiverPhone;// 收件人电话号码
	private String receiverProvince;// 收件人所在省份
	private String receiverCity;// 收件人所在城市
	private String receiverArea;// 收件人所在区县
	private String receiverStreet;// 收件人所在街道
	private String receiverAddress;// 收件人详细地址
	private String orderStatusCode;// 订单状态编码
	private String orderTypeCode;// 订单类型编码
	private String expressBillNo; // 订单快递单号
	private Integer orderScore; // 订单评价得分（1：好评，0：中品，-1：差评）
	private Integer shopOwnerScore; // 商家评价得分（1：好评，0：中品，-1：差评）
	private String orderComment; // 订单评论
	private String orderRemark;// 订单备注信息
	private String expressCompanyCode;
	private String expressCompanyName;
	private BigDecimal expressFee = new BigDecimal(0);// 邮费
	private Date updateTime = new Date();
	private String isRefund;// 是否退款
	private String groupStatus = "普通订单";// 是否为团购
										// （当不是团购订单时，使用的是默认值，团购订单时分为三个状态:（1：已成团
										// 2：组团中 3：未成团））
	private String sharingCode;// 邀请码
	private int isDeleted = 0;// 是否删除
	private BigDecimal oldTotalPrice;//订单原价
	protected Date createTime = new Date();
	private String payMothed;//支付方式
	private String flowNo;
//	private int isCanRefund=0;// 是否能退款

//	public int getIsCanRefund() {
//		return isCanRefund;
//	}
//
//	public void setIsCanRefund(int isCanRefund) {
//		this.isCanRefund = isCanRefund;
//	}
	

	public String getOrderSn() {
		return orderSn;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getPayKindsId() {
		return payKindsId;
	}

	public void setPayKindsId(String payKindsId) {
		this.payKindsId = payKindsId;
	}

	public String getPayKindsName() {
		return payKindsName;
	}

	public void setPayKindsName(String payKindsName) {
		this.payKindsName = payKindsName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopOwnerId() {
		return shopOwnerId;
	}

	public void setShopOwnerId(String shopOwnerId) {
		this.shopOwnerId = shopOwnerId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverProvince() {
		return receiverProvince;
	}

	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}

	public String getExpressCompanyCode() {
		return expressCompanyCode;
	}

	public void setExpressCompanyCode(String expressCompanyCode) {
		this.expressCompanyCode = expressCompanyCode;
	}

	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getReceiverArea() {
		return receiverArea;
	}

	public void setReceiverArea(String receiverArea) {
		this.receiverArea = receiverArea;
	}

	public String getReceiverStreet() {
		return receiverStreet;
	}

	public void setReceiverStreet(String receiverStreet) {
		this.receiverStreet = receiverStreet;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getOrderStatusCode() {
		return orderStatusCode;
	}

	public void setOrderStatusCode(String orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}

	public String getExpressBillNo() {
		return expressBillNo;
	}

	public void setExpressBillNo(String expressBillNo) {
		this.expressBillNo = expressBillNo;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPurchaseGoodsId() {
		return purchaseGoodsId;
	}

	public void setPurchaseGoodsId(String purchaseGoodsId) {
		this.purchaseGoodsId = purchaseGoodsId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public String getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public String getSharingCode() {
		return sharingCode;
	}

	public void setSharingCode(String sharingCode) {
		this.sharingCode = sharingCode;
	}

	public BigDecimal getOldTotalPrice() {
		return oldTotalPrice;
	}

	public void setOldTotalPrice(BigDecimal oldTotalPrice) {
		this.oldTotalPrice = oldTotalPrice;
	}

	public String getPayMothed() {
		return payMothed;
	}

	public void setPayMothed(String payMothed) {
		this.payMothed = payMothed;
	}
}
