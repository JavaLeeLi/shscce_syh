package com.visionet.syh_mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 订单快照
 * @author mulongfei
 * @date 2017年9月18日下午2:54:44
 */
@Entity
@Table(name="tbl_order_snapshot")
public class OrderSnapshot extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String orderSn;//订单编号
	private String purchaseGoodsId;//商品求购记录id
	private BigDecimal totalPrice;//订单金额
	private String buyerId;//买家账户id
	private String buyerPhone;//买家联系电话
	private String shopId;//店铺id
	private String shopOwnerId;//店主用户id
	private String receiverName;//收件人姓名
	private String receiverPhone;//收件人电话号码
	private String receiverProvince;//收件人所在省份
	private String receiverCity;//收件人所在城市
	private String receiverArea;//收件人所在区县
	private String receiverStreet;//收件人所在街道
	private String receiverAddress;//收件人详细地址
	private String orderStatusCode;//订单状态编码
	private BigDecimal expressFee;//订单邮费
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private Integer isDeleted;//是否删除
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public BigDecimal getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}
}
