package com.visionet.syh_mall.vo.message;

import java.util.ArrayList;
import java.util.List;

import com.visionet.syh_mall.common.utils.OrderCondition;

/**
 *@Author DM
 *@version ：2017年9月21日下午3:05:01
 *实体类
 */
public class CouponQo {
	private String userId;
	private String shopId;
	private int couponStatus;
	private int pageNumber;
	private int pageSize;
	protected List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<OrderCondition> getOrderConditions() {
		return orderConditions;
	}
	public void setOrderConditions(List<OrderCondition> orderConditions) {
		this.orderConditions = orderConditions;
	}
	public int getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(int couponStatus) {
		this.couponStatus = couponStatus;
	}
}
