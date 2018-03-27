package com.visionet.syh_mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: OrderCanc
 * @Description: 退货申请的实体类
 * @author chenghongzhan
 * @date 2017年8月30日 上午11:23:58
 */

@Entity
@Table(name = "tbl_order_canc")
public class OrderCanc extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String orderId;// 订单ID
	private String goodsId;// 商品ID
	private BigDecimal goodsPrice; // 商品价格
	private int goodsNum; // 商品数量
	private Date applyTime;// 申请时间
	private String cancReason;// 退货原因
	private String buyerId;// 买家ID
	private String buyerPhone;// 买家联系电话
	private String sellerId;// 卖家ID
	private String cancStatusCode;// 退货状态编码
	private Date updateTime;// 更新时间
	private Date createTime;// 创建时间
	private Integer isDeleted;// 是否删除

	public String getCancStatusCode() {
		return cancStatusCode;
	}

	public void setCancStatusCode(String cancStatusCode) {
		this.cancStatusCode = cancStatusCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getCancReason() {
		return cancReason;
	}

	public void setCancReason(String cancReason) {
		this.cancReason = cancReason;
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

	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
}
