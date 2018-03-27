package com.visionet.syh_mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 订单商品表实体类
 * 
 * @author mulongfei
 * @date 2017年9月1日上午10:35:36
 */
@Entity
@Table(name="tbl_order_goods")
public class OrderGoods extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String orderId;//订单id
	private String goodsId;//商品id
	private BigDecimal goodsRealPrice;//商品实际单价
	private Integer goodsNum;//商品数量
	private String goodsName;//商品名称
	private String goodsDescription;//商品描述
	private String goodsKindId;//商品分类id
	private BigDecimal goodsOriginalPrice;//商品原始单价
	private BigDecimal goodsBidStart;//商品拍卖起拍价（多件商品打包拍卖）
	private BigDecimal goodsMinBid;//拍卖商品最低加价幅度
	private String goodsSn;//商品编号
	private Integer isRecognized;//是否真品（1：是，0：否）
	private String goodsQualityCode;//商品品相编码
	private Integer goodsQualityScore;//商品品相评级分
	private String recognizedCode;//鉴定识别码
	private BigDecimal expressFee;//商品快递邮费
	private String expressTempletId;//商品绑定运费模板ID
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
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

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDescription() {
		return goodsDescription;
	}

	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription;
	}

	public String getGoodsKindId() {
		return goodsKindId;
	}

	public void setGoodsKindId(String goodsKindId) {
		this.goodsKindId = goodsKindId;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public Integer getIsRecognized() {
		return isRecognized;
	}

	public void setIsRecognized(Integer isRecognized) {
		this.isRecognized = isRecognized;
	}

	public String getGoodsQualityCode() {
		return goodsQualityCode;
	}

	public void setGoodsQualityCode(String goodsQualityCode) {
		this.goodsQualityCode = goodsQualityCode;
	}

	public Integer getGoodsQualityScore() {
		return goodsQualityScore;
	}

	public void setGoodsQualityScore(Integer goodsQualityScore) {
		this.goodsQualityScore = goodsQualityScore;
	}

	public String getRecognizedCode() {
		return recognizedCode;
	}

	public void setRecognizedCode(String recognizedCode) {
		this.recognizedCode = recognizedCode;
	}

	public String getExpressTempletId() {
		return expressTempletId;
	}

	public void setExpressTempletId(String expressTempletId) {
		this.expressTempletId = expressTempletId;
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
	public BigDecimal getGoodsRealPrice() {
		return goodsRealPrice;
	}
	public void setGoodsRealPrice(BigDecimal goodsRealPrice) {
		this.goodsRealPrice = goodsRealPrice;
	}
	public BigDecimal getGoodsOriginalPrice() {
		return goodsOriginalPrice;
	}
	public void setGoodsOriginalPrice(BigDecimal goodsOriginalPrice) {
		this.goodsOriginalPrice = goodsOriginalPrice;
	}
	public BigDecimal getGoodsBidStart() {
		return goodsBidStart;
	}
	public void setGoodsBidStart(BigDecimal goodsBidStart) {
		this.goodsBidStart = goodsBidStart;
	}
	public BigDecimal getGoodsMinBid() {
		return goodsMinBid;
	}
	public void setGoodsMinBid(BigDecimal goodsMinBid) {
		this.goodsMinBid = goodsMinBid;
	}
	public BigDecimal getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}
}
