package com.visionet.syh_mall.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年8月17日下午3:17:10 商品草稿实体类
 */
@Entity
@Table(name = "tbl_goods_draft")
public class GoodsDraft extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String shopId;// 商品所属店铺Id
	private String ownerId;// 商品所属用户ID
	private String GoodsName;// 商品名称
	private String GoodsDescription;// 商品描述
	private String goodsTypeCode;// 商品类型编码
	private String goodsKindId;// 商品分类id
	private Integer recognizedIsPrint = 0;//是否打印
	private BigDecimal goodsPrice;// 商品价格
	private BigDecimal goodsBidStart;// 商品拍卖起拍价（多件商品打包拍卖）
	private BigDecimal goodsMinBid;// 拍卖商品最低加价幅度
	private String goodsSn;// 商品编号
	private Integer stockNum;// 库存数量
	private Integer isRecognized;// 是否真品（1：是，0：否）
	private String goodsQualityCode;// 商品品相编码
	private Integer goodsQualityScore;// 商品品相评级分
	private String recognizedCode;// 鉴定识别码
	private Integer isOfficialRecognized = 0;//是否平台鉴评（0：否   1：是）
	private Date validStartTime;// 开始生效时间（当求购商品时创建时间即开始生效时间）
	private Date validEndTime;// 结束生效时间
	private Integer expectedNum;// 求购商品总数
	private Integer supplyMinSum;// 求购商品最低供货数量
	private BigDecimal expressFee;// 商品快递邮费(当商品绑定快递模板时可忽略)
	private String expressTempletId;// 商品绑定运费模板ID
	private Date updateTime = new Date();
	private String createBy;// 创建者id
	private String updateBy;// 创建者id
	private Integer isDeleted = 0;// 是否删除
	protected Date createTime = new Date();

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}

	public String getGoodsDescription() {
		return GoodsDescription;
	}

	public void setGoodsDescription(String goodsDescription) {
		GoodsDescription = goodsDescription;
	}

	public String getGoodsKindId() {
		return goodsKindId;
	}

	public void setGoodsKindId(String goodsKindId) {
		this.goodsKindId = goodsKindId;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public Integer getIsRecognized() {
		return isRecognized;
	}

	public void setIsRecognized(Integer isRecognized) {
		this.isRecognized = isRecognized;
	}

	public String getRecognizedCode() {
		return recognizedCode;
	}

	public void setRecognizedCode(String recognizedCode) {
		this.recognizedCode = recognizedCode;
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

	public Integer getExpectedNum() {
		return expectedNum;
	}

	public void setExpectedNum(Integer expectedNum) {
		this.expectedNum = expectedNum;
	}

	public Integer getSupplyMinSum() {
		return supplyMinSum;
	}

	public void setSupplyMinSum(Integer supplyMinSum) {
		this.supplyMinSum = supplyMinSum;
	}

	public String getExpressTempletId() {
		return expressTempletId;
	}

	public void setExpressTempletId(String expressTempletId) {
		this.expressTempletId = expressTempletId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getGoodsQualityScore() {
		return goodsQualityScore;
	}

	public void setGoodsQualityScore(Integer goodsQualityScore) {
		this.goodsQualityScore = goodsQualityScore;
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

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsQualityCode() {
		return goodsQualityCode;
	}

	public void setGoodsQualityCode(String goodsQualityCode) {
		this.goodsQualityCode = goodsQualityCode;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	@Override
	public String toString() {
		return "GoodsDraft [shopId=" + shopId + ", ownerId=" + ownerId + ", GoodsName=" + GoodsName
				+ ", GoodsDescription=" + GoodsDescription + ", goodsTypeCode=" + goodsTypeCode + ", goodsKindId="
				+ goodsKindId + ", goodsPrice=" + goodsPrice + ", goodsBidStart=" + goodsBidStart + ", goodsMinBid="
				+ goodsMinBid + ", goodsSn=" + goodsSn + ", stockNum=" + stockNum + ", isRecognized=" + isRecognized
				+ ", goodsQualityCode=" + goodsQualityCode + ", goodsQualityScore=" + goodsQualityScore
				+ ", recognizedCode=" + recognizedCode + ", validStartTime=" + validStartTime + ", validEndTime="
				+ validEndTime + ", expectedNum=" + expectedNum + ", supplyMinSum=" + supplyMinSum + ", expressFee="
				+ expressFee + ", expressTempletId=" + expressTempletId + ", updateTime=" + updateTime + ", createBy="
				+ createBy + ", updateBy=" + updateBy + ", isDeleted=" + isDeleted + ", createTime=" + createTime + "]";
	}

	public Integer getRecognizedIsPrint() {
		return recognizedIsPrint;
	}

	public void setRecognizedIsPrint(Integer recognizedIsPrint) {
		this.recognizedIsPrint = recognizedIsPrint;
	}

	public Integer getIsOfficialRecognized() {
		return isOfficialRecognized;
	}

	public void setIsOfficialRecognized(Integer isOfficialRecognized) {
		this.isOfficialRecognized = isOfficialRecognized;
	}
	
}
