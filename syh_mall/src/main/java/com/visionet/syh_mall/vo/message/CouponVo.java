package com.visionet.syh_mall.vo.message;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author DM
 * @version ：2017年9月11日下午2:21:57 实体类
 */
public class CouponVo {
	private String userCouponId;// 用户优惠劵记录ID
	private String couponId;// 优惠劵ID
	private int couponNum;// 优惠劵数量
	private int couponIsUsed;// 优惠劵是否已使用
	private String couponName;// 优惠劵名称
	private BigDecimal couponValue;// 优惠劵面额
	private BigDecimal couponLimitAmt;// 优惠劵限制使用金额
	private String shopId;// 所属店铺ID
	private String shopName;// 店铺名称
	private Date effectiveTime;// 生效时间
	private Date expireTime;// 过期时间
	private int isAvailable;// 是否可用(0：否1：是)
	private int couponhaveNum;

	public int getCouponhaveNum() {
		return couponhaveNum;
	}

	public void setCouponhaveNum(int couponhaveNum) {
		this.couponhaveNum = couponhaveNum;
	}

	public String getUserCouponId() {
		return userCouponId;
	}

	public void setUserCouponId(String userCouponId) {
		this.userCouponId = userCouponId;
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

	public int getCouponIsUsed() {
		return couponIsUsed;
	}

	public void setCouponIsUsed(int couponIsUsed) {
		this.couponIsUsed = couponIsUsed;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public BigDecimal getCouponValue() {
		return couponValue;
	}

	public void setCouponValue(BigDecimal couponValue) {
		this.couponValue = couponValue;
	}

	public BigDecimal getCouponLimitAmt() {
		return couponLimitAmt;
	}

	public void setCouponLimitAmt(BigDecimal couponLimitAmt) {
		this.couponLimitAmt = couponLimitAmt;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}
}
