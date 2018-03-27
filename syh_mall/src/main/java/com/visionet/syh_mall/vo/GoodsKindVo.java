package com.visionet.syh_mall.vo;

import java.util.List;

/**
 * 商品种类结构化
 * @author xiaofb
 * @time 2017年8月29日
 */
public class GoodsKindVo {
	private String categoryID;//分类id
	private String categoryName;//分类名称
	private String categoryImgID;//分类图片id
	private String categoryImgUrl;//分类图片url
	private Integer categorySort;//分类排序
	private boolean reviewIsAvoid;//分类商品是否免审核（0:否，1：是）
	private String parentCategoryID;//父级分类id
	
	List<GoodsKindVo> subKinds;

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryImgID() {
		return categoryImgID;
	}

	public void setCategoryImgID(String categoryImgID) {
		this.categoryImgID = categoryImgID;
	}

	public String getCategoryImgUrl() {
		return categoryImgUrl;
	}

	public void setCategoryImgUrl(String categoryImgUrl) {
		this.categoryImgUrl = categoryImgUrl;
	}

	public Integer getCategorySort() {
		return categorySort;
	}

	public void setCategorySort(Integer categorySort) {
		this.categorySort = categorySort;
	}

	public boolean getReviewIsAvoid() {
		return reviewIsAvoid;
	}

	public void setReviewIsAvoid(boolean reviewIsAvoid) {
		this.reviewIsAvoid = reviewIsAvoid;
	}

	public String getParentCategoryID() {
		return parentCategoryID;
	}

	public void setParentCategoryID(String parentCategoryID) {
		this.parentCategoryID = parentCategoryID;
	}

	public List<GoodsKindVo> getSubKinds() {
		return subKinds;
	}

	public void setSubKinds(List<GoodsKindVo> subKinds) {
		this.subKinds = subKinds;
	}
	
}
