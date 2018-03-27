package com.visionet.syh_mall.entity.marketing;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年9月11日上午10:59:25 优惠券
 */
@Entity
@Table(name = "tbl_coupon")
public class Coupon extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String couponName;// 优惠劵名称
	private BigDecimal couponValue;// 优惠劵面值
	private int couponNum;// 每人限领数
	private BigDecimal limitMoney;// 限制订单金额
	private int issueNum;// 发放数量
	private int claimNum;// 领取数量
	private int usedNum;// 使用数量
	private Date effectiveTime;// 生效时间
	private Date expirationTime;// 过期时间
	private String collectMethod;// 领取方式
	private String shopId;// 所属店铺ID
	private int isAvailable;// 是否有效（0：否，1：是）
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Date createTime = DateUtil.getCurrentDate();
	private Integer isDeleted = 0;// 是否删除

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

	public BigDecimal getLimitMoney() {
		return limitMoney;
	}

	public void setLimitMoney(BigDecimal limitMoney) {
		this.limitMoney = limitMoney;
	}

	public int getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(int couponNum) {
		this.couponNum = couponNum;
	}

	public int getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(int issueNum) {
		this.issueNum = issueNum;
	}

	public int getClaimNum() {
		return claimNum;
	}

	public void setClaimNum(int claimNum) {
		this.claimNum = claimNum;
	}

	public int getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
}
