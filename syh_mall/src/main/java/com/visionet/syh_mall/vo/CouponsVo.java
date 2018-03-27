package com.visionet.syh_mall.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.marketing.Coupon;
import com.visionet.syh_mall.exception.RestException;

/**
 * @ClassName: CouponsVo
 * @Description: 添加优惠券
 * @author chenghongzhan
 * @date 2017年10月13日 下午3:15:30
 *
 */
public class CouponsVo {
	@NotBlank(message = "店铺ID不能为空")
	private String shopID;
	private String couponID;//优惠券id
	@NotBlank(message = "优惠劵名称不能为空")
	private String couponName;
	@NotNull(message = "优惠劵面值不能为空")
	@DecimalMin("0")
	private BigDecimal couponValue;
	@NotNull(message = "优惠劵限领数不能为空")
	@Min(0)
	private Integer couponLimitNum;
	@NotNull(message = "优惠劵限制订单金额不能为空")
	@DecimalMin("0")
	private BigDecimal couponLimitAmt;
	@NotNull(message = "优惠劵发放数不能为空")
	@Min(0)
	private Integer couponIssueNum;
	@NotNull(message = "生效时间不能为空")
	@Future(message = "生效时间必须为将来的时间")
	private Date effectiveTime;
	@NotNull(message = "过期时间不能为空")
	private Date expireTime;
	@NotNull(message = "是否有效不能为空")
	private Integer isAvailable;

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getCouponID() {
		return couponID;
	}

	public void setCouponID(String couponID) {
		this.couponID = couponID;
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

	public Integer getCouponLimitNum() {
		return couponLimitNum;
	}

	public void setCouponLimitNum(Integer couponLimitNum) {
		this.couponLimitNum = couponLimitNum;
	}

	public BigDecimal getCouponLimitAmt() {
		return couponLimitAmt;
	}

	public void setCouponLimitAmt(BigDecimal couponLimitAmt) {
		this.couponLimitAmt = couponLimitAmt;
	}

	public Integer getCouponIssueNum() {
		return couponIssueNum;
	}

	public void setCouponIssueNum(Integer couponIssueNum) {
		this.couponIssueNum = couponIssueNum;
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

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * @Title: converPo @Description: 优惠券的Vo转Po @param @return 设定文件 @return
	 *         Coupon 返回类型 @throws
	 */
	public Coupon converPo(CouponsVo couponsVo, Coupon coupon) {
		if(-1==couponsVo.getCouponLimitAmt().compareTo(couponsVo.couponValue)){
			throw new RestException("优惠券订单金额不能小于优惠券面值");
		}
		if(couponsVo.getCouponLimitNum()>couponsVo.getCouponIssueNum()){
			throw new RestException("限领次数必须小于发放次数");
		}
		coupon.setCouponName(couponsVo.getCouponName());
		coupon.setCouponNum(couponsVo.getCouponLimitNum());
		coupon.setCouponValue(couponsVo.getCouponValue());
		coupon.setEffectiveTime(couponsVo.getEffectiveTime());
		coupon.setExpirationTime(couponsVo.getExpireTime());
		coupon.setIsAvailable(couponsVo.getIsAvailable());
		coupon.setIssueNum(couponsVo.getCouponIssueNum());
		coupon.setLimitMoney(couponsVo.getCouponLimitAmt());
		coupon.setShopId(couponsVo.getShopID());
		return coupon;
	}

	@Override
	public String toString() {
		return "CouponsVo [shopID=" + shopID + ", couponID=" + couponID + ", couponName=" + couponName
				+ ", couponValue=" + couponValue + ", couponLimitNum=" + couponLimitNum + ", couponLimitAmt="
				+ couponLimitAmt + ", couponIssueNum=" + couponIssueNum + ", effectiveTime=" + effectiveTime
				+ ", expireTime=" + expireTime + ", isAvailable=" + isAvailable + "]";
	}

}
