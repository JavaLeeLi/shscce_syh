package com.visionet.syh_mall.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *@Author DM
 *@version ：2017年9月14日下午4:18:18
 *实体类
 */
public class BidRecordVo {
	private String bidGoodsId;//拍卖商品ID
	private String purchaseGoodsId;//求购商品ID
	private String ownerId;//出价用户ID
	private String ownerImgUrl;//出价用户头像url
	private String ownerName;//出价用户名称
	private int ownerLevel;//出价用户等级
	private String ownerTypeCode;//出价用户认证类型编码
	private String ownerTypeDesc;//出价用户认证类型描述
	private BigDecimal lastBidAmt;//最新出价金额
	private int isBidWinner;//是否当前最高出价
	private int hasRefunded;//是否已退款
	private int supplyNum;//供货数量
	private BigDecimal orderAmt;//成交订单金额
	private String orderStatusDesc;//成交订单状态描述
	private Date createTime;//出价时间戳
	private String supplyRecordID;//供货记录ID
	private String supplyGoodsId;//供货商品id
	private String goodsName;//供货商品名
	private BigDecimal goodsPrice;//供货商品价格
	private List<String> goodsPic;//商品图片集
	private BigDecimal orderPrice;//订单金额
	private String supplyOrderId;
	private boolean isSuccess;//是否成功供货
	private String isCanPay;//是否可以支付
	public String getBidGoodsId() {
		return bidGoodsId;
	}
	public void setBidGoodsId(String bidGoodsId) {
		this.bidGoodsId = bidGoodsId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerImgUrl() {
		return ownerImgUrl;
	}
	public void setOwnerImgUrl(String ownerImgUrl) {
		this.ownerImgUrl = ownerImgUrl;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public int getOwnerLevel() {
		return ownerLevel;
	}
	public void setOwnerLevel(int ownerLevel) {
		this.ownerLevel = ownerLevel;
	}
	public String getOwnerTypeCode() {
		return ownerTypeCode;
	}
	public void setOwnerTypeCode(String ownerTypeCode) {
		this.ownerTypeCode = ownerTypeCode;
	}
	public String getOwnerTypeDesc() {
		return ownerTypeDesc;
	}
	public void setOwnerTypeDesc(String ownerTypeDesc) {
		this.ownerTypeDesc = ownerTypeDesc;
	}
	public int getIsBidWinner() {
		return isBidWinner;
	}
	public void setIsBidWinner(int isBidWinner) {
		this.isBidWinner = isBidWinner;
	}
	public int getHasRefunded() {
		return hasRefunded;
	}
	public void setHasRefunded(int hasRefunded) {
		this.hasRefunded = hasRefunded;
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
	public int getSupplyNum() {
		return supplyNum;
	}
	public void setSupplyNum(int supplyNum) {
		this.supplyNum = supplyNum;
	}
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	public BigDecimal getLastBidAmt() {
		return lastBidAmt;
	}
	public void setLastBidAmt(BigDecimal lastBidAmt) {
		this.lastBidAmt = lastBidAmt;
	}
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getSupplyGoodsId() {
		return supplyGoodsId;
	}
	public void setSupplyGoodsId(String supplyGoodsId) {
		this.supplyGoodsId = supplyGoodsId;
	}
	public String getSupplyOrderId() {
		return supplyOrderId;
	}
	public void setSupplyOrderId(String supplyOrderId) {
		this.supplyOrderId = supplyOrderId;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public List<String> getGoodsPic() {
		return goodsPic;
	}
	public void setGoodsPic(List<String> goodsPic) {
		this.goodsPic = goodsPic;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSupplyRecordID() {
		return supplyRecordID;
	}
	public void setSupplyRecordID(String supplyRecordID) {
		this.supplyRecordID = supplyRecordID;
	}
	public String getIsCanPay() {
		return isCanPay;
	}
	public void setIsCanPay(String isCanPay) {
		this.isCanPay = isCanPay;
	}
	
}
