package com.visionet.syh_mall.vo.goods;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 商品草稿入参
 * @author mulongfei
 * @date 2017年9月25日下午2:00:12
 */
public class GoodsDraftVo {
	private String goodsDraftID;
	@NotEmpty(message="商品名称不能为空")
	private String goodsName;
	@NotEmpty(message="商品类型不能为空")
	private String goodsTypeCode;
	private String goodsKindID;
	private Integer goodsNum;
	private BigDecimal goodsPrice;
	private String goodsDesc;
	private String goodsSN;
	private String goodsQualityCode;
	private Integer goodsQualityScore;
	private String goodsRecognizeCode;
	private BigDecimal goodsBidStart;
	private BigDecimal goodsMinBid;
	private Integer expectedNum;
	private Integer supplyMinNum;
	private String purchaseAddrID;
	@Future
	private Date validStartTime;
	@Future
	private Date validEndTime;
	private BigDecimal goodsExpressFee;
	private String goodsTempletID;
	private List<GoodsDraftImgVo> goodsImgs;
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}
	public String getGoodsKindID() {
		return goodsKindID;
	}
	public void setGoodsKindID(String goodsKindID) {
		this.goodsKindID = goodsKindID;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public String getGoodsSN() {
		return goodsSN;
	}
	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
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
	public String getGoodsRecognizeCode() {
		return goodsRecognizeCode;
	}
	public void setGoodsRecognizeCode(String goodsRecognizeCode) {
		this.goodsRecognizeCode = goodsRecognizeCode;
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
	public Integer getExpectedNum() {
		return expectedNum;
	}
	public void setExpectedNum(Integer expectedNum) {
		this.expectedNum = expectedNum;
	}
	public Integer getSupplyMinNum() {
		return supplyMinNum;
	}
	public void setSupplyMinNum(Integer supplyMinNum) {
		this.supplyMinNum = supplyMinNum;
	}
	public String getPurchaseAddrID() {
		return purchaseAddrID;
	}
	public void setPurchaseAddrID(String purchaseAddrID) {
		this.purchaseAddrID = purchaseAddrID;
	}
	public Date getValidStartTime() {
		return validStartTime;
	}
	public void setValidStartTime(Date validStartTime) {
		this.validStartTime = validStartTime;
	}
	public Date getValidEndTime() {
		return validEndTime;
	}
	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}
	public BigDecimal getGoodsExpressFee() {
		return goodsExpressFee;
	}
	public void setGoodsExpressFee(BigDecimal goodsExpressFee) {
		this.goodsExpressFee = goodsExpressFee;
	}
	public String getGoodsTempletID() {
		return goodsTempletID;
	}
	public void setGoodsTempletID(String goodsTempletID) {
		this.goodsTempletID = goodsTempletID;
	}
	public List<GoodsDraftImgVo> getGoodsImgs() {
		return goodsImgs;
	}
	public void setGoodsImgs(List<GoodsDraftImgVo> goodsImgs) {
		this.goodsImgs = goodsImgs;
	}
	public String getGoodsDraftID() {
		return goodsDraftID;
	}
	public void setGoodsDraftID(String goodsDraftID) {
		this.goodsDraftID = goodsDraftID;
	}
	@Override
	public String toString() {
		return "GoodsDraftVo [goodsDraftID=" + goodsDraftID + ", goodsName=" + goodsName + ", goodsTypeCode="
				+ goodsTypeCode + ", goodsKindID=" + goodsKindID + ", goodsNum=" + goodsNum + ", goodsPrice="
				+ goodsPrice + ", goodsDesc=" + goodsDesc + ", goodsSN=" + goodsSN + ", goodsQualityCode="
				+ goodsQualityCode + ", goodsQualityScore=" + goodsQualityScore + ", goodsRecognizeCode="
				+ goodsRecognizeCode + ", goodsBidStart=" + goodsBidStart + ", goodsMinBid=" + goodsMinBid
				+ ", expectedNum=" + expectedNum + ", supplyMinNum=" + supplyMinNum + ", purchaseAddrID="
				+ purchaseAddrID + ", validStartTime=" + validStartTime + ", validEndTime=" + validEndTime
				+ ", goodsExpressFee=" + goodsExpressFee + ", goodsTempletID=" + goodsTempletID + ", goodsImgs="
				+ goodsImgs + "]";
	}
}
