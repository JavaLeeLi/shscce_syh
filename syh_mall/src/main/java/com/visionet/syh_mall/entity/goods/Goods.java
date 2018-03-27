package com.visionet.syh_mall.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年8月17日下午3:17:10 商品实体类
 */
@Entity
@Table(name = "tbl_goods")
public class Goods extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String shopId;// 商品所属店铺Id
	private String ownerId;// 商品所属用户ID
	private String goodsName;// 商品名称
	private String goodsDescription;// 商品描述
	private String buyGoodsType;// 求购状态
	private String goodsTypeCode;// 商品分类编码集
	private Integer goodsType;// 商品类型（1：求购 2：出售 3：拍卖）
	private String goodsKindId;// 商品分类id
	private BigDecimal goodsPrice;// 商品价格
	private BigDecimal goodsBidStart;// 商品拍卖起拍价（多件商品打包拍卖）
	private BigDecimal goodsMinBid;// 拍卖商品最低加价幅度
	private String goodsSn;// 商品编号
	private Integer stockNum;// 库存数量
	private Integer isRecognized;// 是否真品（1：是，0：否）
	private String goodsQualityCode;// 商品品相编码
	private Integer goodsQuality;// 商品品相(0：极品，1：上品，2：中品，3：下品)
	private Integer goodsQualityScore;// 商品品相评级分
	private Integer recognizedIsPrint = 0;// 鉴评标签是否已打印
	private String recognizedCode;// 鉴定识别码
	private Date validStartTime;// 开始生效时间（当求购商品时创建时间即开始生效时间）
	private Date validEndTime;// 结束生效时间
	private Integer expectedNum;// 求购商品总数
	private String banReason;// 商品下架原因
	private Integer supplyMinSum;// 求购商品最低供货数量
	private BigDecimal expressFee = new BigDecimal(0);// 商品快递邮费(当商品绑定快递模板时可忽略)
	private String expressTempletId;// 商品绑定运费模板ID
	private Integer totalSales = 0;// 累计销售量
	private String statusCode;// 商品状态
	private Date updateTime = new Date();
	private String createBy;// 创建者id
	private String updateBy;// 创建者id
	private Integer isDeleted = 0;// 是否删除
	protected Date createTime = new Date();
	private String goodsChannelRule;

	public String getGoodsChannelRule() {
		return goodsChannelRule;
	}

	public void setGoodsChannelRule(String goodsChannelRule) {
		this.goodsChannelRule = goodsChannelRule;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

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

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
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

	public Integer getGoodsQuality() {
		return goodsQuality;
	}

	public void setGoodsQuality(Integer goodsQuality) {
		this.goodsQuality = goodsQuality;
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

	public String getBanReason() {
		return banReason;
	}

	public void setBanReason(String banReason) {
		this.banReason = banReason;
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

	public Integer getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Integer totalSales) {
		this.totalSales = totalSales;
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

	public Integer getRecognizedIsPrint() {
		return recognizedIsPrint;
	}

	public void setRecognizedIsPrint(Integer recognizedIsPrint) {
		this.recognizedIsPrint = recognizedIsPrint;
	}

	public BigDecimal getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
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

	public String getBuyGoodsType() {
		return buyGoodsType;
	}

	public void setBuyGoodsType(String buyGoodsType) {
		this.buyGoodsType = buyGoodsType;
	}
}
