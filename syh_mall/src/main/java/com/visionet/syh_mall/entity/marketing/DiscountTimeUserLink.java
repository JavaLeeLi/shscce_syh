package com.visionet.syh_mall.entity.marketing;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="tbl_discount_time_user_link")
public class DiscountTimeUserLink extends IdEntity{
	private String userId;
	private String discountTimeId;
	private Integer discountTimeLimitNum = 0;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDiscountTimeId() {
		return discountTimeId;
	}
	public void setDiscountTimeId(String discountTimeId) {
		this.discountTimeId = discountTimeId;
	}
	public Integer getDiscountTimeLimitNum() {
		return discountTimeLimitNum;
	}
	public void setDiscountTimeLimitNum(Integer discountTimeLimitNum) {
		this.discountTimeLimitNum = discountTimeLimitNum;
	}
}
