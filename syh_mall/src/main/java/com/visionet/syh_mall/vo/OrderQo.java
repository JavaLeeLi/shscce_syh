package com.visionet.syh_mall.vo;

import java.util.ArrayList;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * 结算订单入参类
 * @author mulongfei
 * @date 2017年9月1日上午10:07:01
 */
public class OrderQo {
	private String supplyID;//供货记录id
	private String payKindID;//支付方式id
	private String purchaseGoodsID;//商品表求购记录id
	@NotEmpty(message="收件人名不能为空")
	private String receiverName;//收件人姓名
	@NotEmpty(message="收件人电话不能为空")
	private String receiverPhone;//收件人电话
	private String receiverProvince;//收件人所处省份
	private String receiverCity;//收件人所处城市
	private String receiverArea;//收件人所处区县
	private String receiverStreet;//收件人所处街道
	@NotEmpty(message="收件人详细地址不能为空")
	private String receiverAddress;//收件人详细地址
	private String usedCouponID;//使用优惠券id
	private String fulfilRemit;//满减标识
	private String orderRemark;//订单备注信息
	private String groupBuyId;//团购活动id----组团时必传
	private String groupDetailId;//组团信息id----参团必传
	private String sharingCode;//邀请码
	@Valid
	private ArrayList<GoodQo> goodsInfos;//商品信息数组
	public String getPayKindID() {
		return payKindID;
	}
	public void setPayKindID(String payKindID) {
		this.payKindID = payKindID;
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
	public ArrayList<GoodQo> getGoodsInfos() {
		return goodsInfos;
	}
	public void setGoodsInfos(ArrayList<GoodQo> goodsInfos) {
		this.goodsInfos = goodsInfos;
	}
	public String getPurchaseGoodsID() {
		return purchaseGoodsID;
	}
	public void setPurchaseGoodsID(String purchaseGoodsID) {
		this.purchaseGoodsID = purchaseGoodsID;
	}
	public String getUsedCouponID() {
		return usedCouponID;
	}
	public void setUsedCouponID(String usedCouponID) {
		this.usedCouponID = usedCouponID;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
	//vo to po
	
	class returnVo{
		
		//po to vo
		
	}

	public String getFulfilRemit() {
		return fulfilRemit;
	}
	public void setFulfilRemit(String fulfilRemit) {
		this.fulfilRemit = fulfilRemit;
	}
	public String getGroupBuyId() {
		return groupBuyId;
	}
	public void setGroupBuyId(String groupBuyId) {
		this.groupBuyId = groupBuyId;
	}
	public String getGroupDetailId() {
		return groupDetailId;
	}
	public void setGroupDetailId(String groupDetailId) {
		this.groupDetailId = groupDetailId;
	}
	public String getSharingCode() {
		return sharingCode;
	}
	public void setSharingCode(String sharingCode) {
		this.sharingCode = sharingCode;
	}


	@Override
	public String toString() {
		return "OrderQo{" +
				"payKindID='" + payKindID + '\'' +
				", purchaseGoodsID='" + purchaseGoodsID + '\'' +
				", receiverName='" + receiverName + '\'' +
				", receiverPhone='" + receiverPhone + '\'' +
				", receiverProvince='" + receiverProvince + '\'' +
				", receiverCity='" + receiverCity + '\'' +
				", receiverArea='" + receiverArea + '\'' +
				", receiverStreet='" + receiverStreet + '\'' +
				", receiverAddress='" + receiverAddress + '\'' +
				", usedCouponID='" + usedCouponID + '\'' +
				", fulfilRemit='" + fulfilRemit + '\'' +
				", orderRemark='" + orderRemark + '\'' +
				", groupBuyId='" + groupBuyId + '\'' +
				", groupDetailId='" + groupDetailId + '\'' +
				", sharingCode='" + sharingCode + '\'' +
				", goodsInfos=" + goodsInfos +
				'}';
	}
	public String getSupplyID() {
		return supplyID;
	}
	public void setSupplyID(String supplyID) {
		this.supplyID = supplyID;
	}
}
