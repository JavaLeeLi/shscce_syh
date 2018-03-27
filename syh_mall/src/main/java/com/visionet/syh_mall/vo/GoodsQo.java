package com.visionet.syh_mall.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.OrderCondition;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.goods.SearchHistory;

/**
 * @Author DM
 * @version ：2017年8月29日上午10:26:40 商品入参类
 */
public class GoodsQo extends PageInfo {
	private String userId;
	private String inShopId;// 搜索目标店铺ID
	private String shopName;// 店铺名称
	private String statusName;// 状态名称
	private String goodsId;
	private int isbusiness;// 是否商家端请求
	private String ownerId;// 商品所属用户ID
	private Integer auctionStatus;// 拍卖状态 0：未开始 1：开始
	private String goodsName;// 商品名称
	private String goodsSn;// 商品编号
	private String categoryId;// 商品类别id
	private String childCategoryId;// 商品二级分类ID
	private String goodsType;
	private Integer isRecognized;// 商品是否鉴评封装
	private String goodsTags;// 商品标签，多个标签使用逗号分隔
	private String id;
	private String keywords;// 关键词
	private int sortType;// 搜索结果排序类型
	private PageInfo pageInfo;// 分页信息 
	private String StatusCode;
	private String goodsTypeCode;
	private String currentUserId;
	private String buyGoodsType;
	private String isGeneralized;// 是否已推广

	private Date startTime;
	private Date endTime;
	private String loginName;
	private String userName;
	private String commissionRate;

	public String getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getIsGeneralized() {
		return isGeneralized;
	}

	public void setIsGeneralized(String isGeneralized) {
		this.isGeneralized = isGeneralized;
	}

	protected List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
	private List<SearchHistory> historyInfos;

	public List<OrderCondition> getOrderConditions() {
		return orderConditions;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setOrderConditions(List<OrderCondition> orderConditions) {
		this.orderConditions = orderConditions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public int getSortType() {
		return sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	public String getInShopId() {
		return inShopId;
	}

	public void setInShopId(String inShopId) {
		this.inShopId = inShopId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getIsRecognized() {
		return isRecognized;
	}

	public void setIsRecognized(Integer isRecognized) {
		this.isRecognized = isRecognized;
	}

	public String getGoodsTags() {
		return goodsTags;
	}

	public void setGoodsTags(String goodsTags) {
		this.goodsTags = goodsTags;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(String childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public List<SearchHistory> getHistoryInfos() {
		return historyInfos;
	}

	public void setHistoryInfos(List<SearchHistory> historyInfos) {
		this.historyInfos = historyInfos;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getBuyGoodsType() {
		return buyGoodsType;
	}

	public void setBuyGoodsType(String buyGoodsType) {
		this.buyGoodsType = buyGoodsType;
	}

	public Integer getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(Integer auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		endTime = DateUtil.seekDate(endTime, 1);
		this.endTime = endTime;
	}

	public int getIsbusiness() {
		return isbusiness;
	}

	public void setIsbusiness(int isbusiness) {
		this.isbusiness = isbusiness;
	}

	@Override
	public String toString() {
		return "GoodsQo [inShopId=" + inShopId + ", shopName=" + shopName + ", statusName=" + statusName + ", goodsId="
				+ goodsId + ", isbusiness=" + isbusiness + ", ownerId=" + ownerId + ", auctionStatus=" + auctionStatus
				+ ", goodsName=" + goodsName + ", goodsSn=" + goodsSn + ", categoryId=" + categoryId
				+ ", childCategoryId=" + childCategoryId + ", goodsType=" + goodsType + ", isRecognized=" + isRecognized
				+ ", goodsTags=" + goodsTags + ", id=" + id + ", keywords=" + keywords + ", sortType=" + sortType
				+ ", pageInfo=" + pageInfo + ", StatusCode=" + StatusCode + ", goodsTypeCode=" + goodsTypeCode
				+ ", currentUserId=" + currentUserId + ", buyGoodsType=" + buyGoodsType + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", orderConditions=" + orderConditions + ", historyInfos=" + historyInfos
				+ ", getOrderConditions()=" + getOrderConditions() + ", getId()=" + getId() + ", getPageInfo()="
				+ getPageInfo() + ", getSortType()=" + getSortType() + ", getInShopId()=" + getInShopId()
				+ ", getCategoryId()=" + getCategoryId() + ", getIsRecognized()=" + getIsRecognized()
				+ ", getGoodsTags()=" + getGoodsTags() + ", getKeywords()=" + getKeywords() + ", getShopName()="
				+ getShopName() + ", getStatusName()=" + getStatusName() + ", getChildCategoryId()="
				+ getChildCategoryId() + ", getGoodsType()=" + getGoodsType() + ", getGoodsName()=" + getGoodsName()
				+ ", getGoodsSn()=" + getGoodsSn() + ", getStatusCode()=" + getStatusCode() + ", getGoodsTypeCode()="
				+ getGoodsTypeCode() + ", getCurrentUserId()=" + getCurrentUserId() + ", getHistoryInfos()="
				+ getHistoryInfos() + ", getGoodsId()=" + getGoodsId() + ", getOwnerId()=" + getOwnerId()
				+ ", getBuyGoodsType()=" + getBuyGoodsType() + ", getAuctionStatus()=" + getAuctionStatus()
				+ ", getStartTime()=" + getStartTime() + ", getEndTime()=" + getEndTime() + ", getIsbusiness()="
				+ getIsbusiness() + ", getPageIndex()=" + getPageIndex() + ", getItemCount()=" + getItemCount()
				+ ", getType()=" + getType() + ", getSortColumn()=" + getSortColumn() + ", getPageRequestInfo()="
				+ getPageRequestInfo() + ", getTotalCount()=" + getTotalCount() + ", getSortTypeStr()="
				+ getSortTypeStr() + ", getCurrentPageNumber()=" + getCurrentPageNumber() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
