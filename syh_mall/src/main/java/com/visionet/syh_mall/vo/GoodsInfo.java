package com.visionet.syh_mall.vo;
/**
 * 商品检索入参实体类
 * @author mulongfei
 * @date 2017年8月30日下午4:49:27
 */
public class GoodsInfo {
	private String inShopID;//搜索目标店铺
	private String categoryID;//商品类别ID
	private String childCategoryID;//商品二级分类ID
	private Integer isRecognized;//商品是否鉴评封装
	private String goodsTags;//商品标签，多个标签使用逗号分隔
	private String keywords;//搜索关键字
	private Integer sortType;//搜索结果排序类型
	private Integer pageIndex;//记录分页页码
	private Integer itemCount;//分页记录数
	public String getInShopID() {
		return inShopID;
	}
	public void setInShopID(String inShopID) {
		this.inShopID = inShopID;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getChildCategoryID() {
		return childCategoryID;
	}
	public void setChildCategoryID(String childCategoryID) {
		this.childCategoryID = childCategoryID;
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
	public Integer getSortType() {
		return sortType;
	}
	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
}
