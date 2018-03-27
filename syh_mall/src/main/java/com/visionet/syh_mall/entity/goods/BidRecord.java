package com.visionet.syh_mall.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 竞价记录实体类
 * @author mulongfei
 * @date 2017年8月31日下午2:36:25
 */
@Entity
@Table(name="tbl_bid_record")
public class BidRecord extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String goodsId;//商品id
	private BigDecimal expressFee;//邮费
	private BigDecimal lastBidPrice;//最后出价
	private Integer isBidWinner;//是否为最高价
	private Integer hasRefunded;//是否退款
	private String userId;//出价人id
	private String receiverName;//收件人姓名
	private String receiverPhone;//收件人电话
	private String receiverProvince;//收件人所在省份
	private String receiverCity;//收件人所在城市
	private String receiverArea;//收件人所在区县
	private String receiverStreet;//收件人所在街道
	private String receiverAddress;//收件人详细地址
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private Integer isDeleted = 0;//是否删除
	
	public BigDecimal getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getIsBidWinner() {
		return isBidWinner;
	}
	public void setIsBidWinner(Integer isBidWinner) {
		this.isBidWinner = isBidWinner;
	}
	public Integer getHasRefunded() {
		return hasRefunded;
	}
	public void setHasRefunded(Integer hasRefunded) {
		this.hasRefunded = hasRefunded;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public BigDecimal getLastBidPrice() {
		return lastBidPrice;
	}
	public void setLastBidPrice(BigDecimal lastBidPrice) {
		this.lastBidPrice = lastBidPrice;
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
}
