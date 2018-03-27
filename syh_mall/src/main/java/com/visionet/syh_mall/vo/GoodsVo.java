package com.visionet.syh_mall.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.goods.ExpressTemplet;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.HomeGoods;
import com.visionet.syh_mall.entity.marketing.DiscountTime;

/**
 * @Author DM
 * @version ：2017年8月21日下午3:10:07 实体类
 */
public class GoodsVo {
	private long itemCount; // 满足条件的记录总数
	private int pageCount; // 满足条件的记录分页数
	private int curPageIndex; // 当前返回记录页码
	private boolean hasNext;// 是否还有下一页数据
	private List<GoodsInfos> goodsInfos;

	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurPageIndex() {
		return curPageIndex;
	}

	public void setCurPageIndex(int curPageIndex) {
		this.curPageIndex = curPageIndex;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public List<GoodsInfos> getGoodsInfos() {
		return goodsInfos;
	}

	public void setGoodsInfos(List<GoodsInfos> goodsInfos) {
		this.goodsInfos = goodsInfos;
	}

	public static class GoodsInfos {
		private String id;
		private String homeGoodsId;// 热推商品项记录ID
		private String shopId;// 推销商所属店铺ID
		private String goodsId;// 热推商品ID
		private String goodsType;// 商品类型编码
		private int homeGoodsSort;// 热推商品顺序
		private String shopName;// 商铺名称
		private String ownerId;// 商品所属用户ID
		private String GoodsName;// 商品名称
		private String GoodsDescription;// 商品描述
		private BigDecimal goodsPrice;// 商品价格
		private String goodsSn;// 商品编号
		private int isRecognized;// 是否真品（1：是，0：否）
		private Date validStartTime;// 开始生效时间（当求购商品时创建时间即开始生效时间）
		private Date goodsCloseTime;// 商品发布时间
		private Date validEndTime;// 结束生效时间
		private String ownerImgUrl;// 商品所属用户头像url
		private String ownerName;// 商品所属用户名称
		private int ownerLevel;// 商品所属用户等级
		private String ownerType;// 商品所属用户认证类型
		private String ownerTypeCode;// 商品所属用户认证类型
		private String ownerTypeDesc;
		private String goodsTypeCode;
		private String goodsTypeDesc;
		private String goodsDesc;
		private String categoryId;
		private String categoryName;
		private String childCategoryId;
		private String childCategoryName;
		private String goodsQualityCode;// 商品品相编码
		private String goodsQualityDesc;
		private int goodsQualityScore;
		private int goodsIsRecognized;
		private int recognizedIsPrint;
		private BigDecimal realPrice;
		private String goodsRecognizeCode;
		private Integer goodsStockNum;// 商品库存数量
		private int goodsSalesNum;// 商品累计销售量
		private BigDecimal goodsBidStart;// 商品拍卖起拍价（多件商品打包拍卖）
		private BigDecimal goodsBidMax;
		private BigDecimal goodsBidMin;// 拍卖商品最低加价幅度
		private int goodsMinSupplyNum;
		private int expectedNum;
		private Integer residueSupplyNum;//剩余供货数量
		private String banReason;
		private String expressTempletID;
		private String statusCode;
		private String statusDesc;
		private BigDecimal expressFee;// 商品快递邮费
		private int freeForExpress;
		private Date goodsCreateTime;// 商品发布时间
		private int ownerIsAttented;// 所属用户是否已关注
		private int goodsIsFavorite;// 商品是否已收藏
		private int suppliedNum;// 求购已供货数量
		private String buyGoodsType;// 求购状态
		private List<Goods> goods;
		private Goods good;
		private PurchaseAddrInfoVo purchaseAddrInfo;
		private List<HomeGoods> homeGoods;
		private List<FileManage> goodsImgs;
		private List<FileManage> goodsImgUrls;
		private ExpressTemplet expressTemplet;
		private DiscountTime discountTime;
		private String goodsChannelRule;
		private List<Map<String,String>> goodsPicUrl;
		
		public int getFreeForExpress() {
			return freeForExpress;
		}

		public void setFreeForExpress(int freeForExpress) {
			this.freeForExpress = freeForExpress;
		}

		public String getGoodsChannelRule() {
			return goodsChannelRule;
		}

		public void setGoodsChannelRule(String goodsChannelRule) {
			this.goodsChannelRule = goodsChannelRule;
		}

		public BigDecimal getGoodsBidMax() {
			return goodsBidMax;
		}

		public void setGoodsBidMax(BigDecimal goodsBidMax) {
			this.goodsBidMax = goodsBidMax;
		}

		public List<Goods> getGoods() {
			return goods;
		}

		public void setGoods(List<Goods> goods) {
			this.goods = goods;
		}

		public PurchaseAddrInfoVo getPurchaseAddrInfo() {
			return purchaseAddrInfo;
		}

		public void setPurchaseAddrInfo(PurchaseAddrInfoVo purchaseAddrInfo) {
			this.purchaseAddrInfo = purchaseAddrInfo;
		}

		public List<HomeGoods> getHomeGoods() {
			return homeGoods;
		}

		public void setHomeGoods(List<HomeGoods> homeGoods) {
			this.homeGoods = homeGoods;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getHomeGoodsId() {
			return homeGoodsId;
		}

		public void setHomeGoodsId(String homeGoodsId) {
			this.homeGoodsId = homeGoodsId;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

		public String getGoodsType() {
			return goodsType;
		}

		public void setGoodsType(String goodsType) {
			this.goodsType = goodsType;
		}

		public int getHomeGoodsSort() {
			return homeGoodsSort;
		}

		public void setHomeGoodsSort(int homeGoodsSort) {
			this.homeGoodsSort = homeGoodsSort;
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

		public BigDecimal getGoodsPrice() {
			return goodsPrice;
		}

		public void setGoodsPrice(BigDecimal goodsPrice) {
			this.goodsPrice = goodsPrice;
		}

		public String getGoodsSn() {
			return goodsSn;
		}

		public void setGoodsSn(String goodsSn) {
			this.goodsSn = goodsSn;
		}

		public int getIsRecognized() {
			return isRecognized;
		}

		public void setIsRecognized(int isRecognized) {
			this.isRecognized = isRecognized;
		}

		public Date getValidStartTime() {
			return validStartTime;
		}

		public void setValidStartTime(Date validStartTime) {
			this.validStartTime = validStartTime;
		}

		public String getChildCategoryId() {
			return childCategoryId;
		}

		public void setChildCategoryId(String childCategoryId) {
			this.childCategoryId = childCategoryId;
		}

		public String getChildCategoryName() {
			return childCategoryName;
		}

		public void setChildCategoryName(String childCategoryName) {
			this.childCategoryName = childCategoryName;
		}

		public Date getValidEndTime() {
			return validEndTime;
		}

		public void setValidEndTime(Date validEndTime) {
			this.validEndTime = validEndTime;
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

		public String getOwnerType() {
			return ownerType;
		}

		public void setOwnerType(String ownerType) {
			this.ownerType = ownerType;
		}

		public List<FileManage> getGoodsImgUrls() {
			return goodsImgUrls;
		}

		public void setGoodsImgUrls(List<FileManage> goodsImgUrls) {
			this.goodsImgUrls = goodsImgUrls;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Integer getGoodsStockNum() {
			return goodsStockNum;
		}

		public void setGoodsStockNum(Integer goodsStockNum) {
			this.goodsStockNum = goodsStockNum;
		}

		public int getGoodsSalesNum() {
			return goodsSalesNum;
		}

		public void setGoodsSalesNum(int goodsSalesNum) {
			this.goodsSalesNum = goodsSalesNum;
		}

		public BigDecimal getExpressFee() {
			return expressFee;
		}

		public void setExpressFee(BigDecimal expressFee) {
			this.expressFee = expressFee;
		}

		public Date getGoodsCreateTime() {
			return goodsCreateTime;
		}

		public void setGoodsCreateTime(Date goodsCreateTime) {
			this.goodsCreateTime = goodsCreateTime;
		}

		public List<FileManage> getGoodsImgs() {
			return goodsImgs;
		}

		public void setGoodsImgs(List<FileManage> goodsImgs) {
			this.goodsImgs = goodsImgs;
		}

		public Date getGoodsCloseTime() {
			return goodsCloseTime;
		}

		public void setGoodsCloseTime(Date goodsCloseTime) {
			this.goodsCloseTime = goodsCloseTime;
		}

		public Goods getGood() {
			return good;
		}

		public void setGood(Goods good) {
			this.good = good;
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

		public String getGoodsTypeCode() {
			return goodsTypeCode;
		}

		public void setGoodsTypeCode(String goodsTypeCode) {
			this.goodsTypeCode = goodsTypeCode;
		}

		public String getGoodsTypeDesc() {
			return goodsTypeDesc;
		}

		public void setGoodsTypeDesc(String goodsTypeDesc) {
			this.goodsTypeDesc = goodsTypeDesc;
		}

		public String getGoodsDesc() {
			return goodsDesc;
		}

		public void setGoodsDesc(String goodsDesc) {
			this.goodsDesc = goodsDesc;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getGoodsQualityCode() {
			return goodsQualityCode;
		}

		public void setGoodsQualityCode(String goodsQualityCode) {
			this.goodsQualityCode = goodsQualityCode;
		}

		public String getGoodsQualityDesc() {
			return goodsQualityDesc;
		}

		public void setGoodsQualityDesc(String goodsQualityDesc) {
			this.goodsQualityDesc = goodsQualityDesc;
		}

		public int getGoodsQualityScore() {
			return goodsQualityScore;
		}

		public void setGoodsQualityScore(int goodsQualityScore) {
			this.goodsQualityScore = goodsQualityScore;
		}

		public int getGoodsIsRecognized() {
			return goodsIsRecognized;
		}

		public void setGoodsIsRecognized(int goodsIsRecognized) {
			this.goodsIsRecognized = goodsIsRecognized;
		}

		public int getRecognizedIsPrint() {
			return recognizedIsPrint;
		}

		public void setRecognizedIsPrint(int recognizedIsPrint) {
			this.recognizedIsPrint = recognizedIsPrint;
		}

		public String getGoodsRecognizeCode() {
			return goodsRecognizeCode;
		}

		public void setGoodsRecognizeCode(String goodsRecognizeCode) {
			this.goodsRecognizeCode = goodsRecognizeCode;
		}

		public int getGoodsMinSupplyNum() {
			return goodsMinSupplyNum;
		}

		public void setGoodsMinSupplyNum(int goodsMinSupplyNum) {
			this.goodsMinSupplyNum = goodsMinSupplyNum;
		}

		public int getExpectedNum() {
			return expectedNum;
		}

		public void setExpectedNum(int expectedNum) {
			this.expectedNum = expectedNum;
		}

		public String getBanReason() {
			return banReason;
		}

		public void setBanReason(String banReason) {
			this.banReason = banReason;
		}

		public String getExpressTempletID() {
			return expressTempletID;
		}

		public void setExpressTempletID(String expressTempletID) {
			this.expressTempletID = expressTempletID;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public String getStatusDesc() {
			return statusDesc;
		}

		public void setStatusDesc(String statusDesc) {
			this.statusDesc = statusDesc;
		}

		public ExpressTemplet getExpressTemplet() {
			return expressTemplet;
		}

		public void setExpressTemplet(ExpressTemplet expressTemplet) {
			this.expressTemplet = expressTemplet;
		}

		public int getOwnerIsAttented() {
			return ownerIsAttented;
		}

		public void setOwnerIsAttented(int ownerIsAttented) {
			this.ownerIsAttented = ownerIsAttented;
		}

		public String getBuyGoodsType() {
			return buyGoodsType;
		}

		public void setBuyGoodsType(String buyGoodsType) {
			this.buyGoodsType = buyGoodsType;
		}

		public int getGoodsIsFavorite() {
			return goodsIsFavorite;
		}

		public void setGoodsIsFavorite(int goodsIsFavorite) {
			this.goodsIsFavorite = goodsIsFavorite;
		}

		public int getSuppliedNum() {
			return suppliedNum;
		}

		public void setSuppliedNum(int suppliedNum) {
			this.suppliedNum = suppliedNum;
		}

		public BigDecimal getRealPrice() {
			return realPrice;
		}

		public void setRealPrice(BigDecimal realPrice) {
			this.realPrice = realPrice;
		}

		public BigDecimal getGoodsBidStart() {
			return goodsBidStart;
		}

		public void setGoodsBidStart(BigDecimal goodsBidStart) {
			this.goodsBidStart = goodsBidStart;
		}

		public BigDecimal getGoodsBidMin() {
			return goodsBidMin;
		}

		public void setGoodsBidMin(BigDecimal goodsBidMin) {
			this.goodsBidMin = goodsBidMin;
		}

		public DiscountTime getDiscountTime() {
			return discountTime;
		}

		public void setDiscountTime(DiscountTime discountTime) {
			this.discountTime = discountTime;
		}

		public Integer getResidueSupplyNum() {
			return residueSupplyNum;
		}

		public void setResidueSupplyNum(Integer residueSupplyNum) {
			this.residueSupplyNum = residueSupplyNum;
		}

		public List<Map<String, String>> getGoodsPicUrl() {
			return goodsPicUrl;
		}

		public void setGoodsPicUrl(List<Map<String, String>> goodsPicUrl) {
			this.goodsPicUrl = goodsPicUrl;
		}

	}
}
