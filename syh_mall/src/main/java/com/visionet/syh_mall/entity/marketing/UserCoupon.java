package com.visionet.syh_mall.entity.marketing;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年9月11日下午1:47:48 实体类
 */
@Entity
@Table(name = "tbl_user_coupon")
public class UserCoupon extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String userId;// 用户id
	private String couponId;// 角色id
	private int couponNum=0;// 优惠劵数量
	private int couponHaveNum=0;//现有优惠券数量
	private int isUsed=0;// 是否被使用(0:否，1：是)',
	protected Date createTime = DateUtil.getCurrentDate();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public int getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(int couponNum) {
		this.couponNum = couponNum;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCouponHaveNum() {
		return couponHaveNum;
	}

	public void setCouponHaveNum(int couponHaveNum) {
		this.couponHaveNum = couponHaveNum;
	}
}
