package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author DM
 * @version ：2017年8月24日下午2:35:21 短信验证码实体类
 */
@Entity
@Table(name = "tbl_verify_code")
public class VerifyCode extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String phone;// 手机号码
	private String verifyCode;// 短信验证码
	private String hasValidated;//是否验证（validated：已验证；unValidated：未验证）
	private int hasSent;// 是否发送成功(0:否，1：是)',
	private Date expireTime;// 过期时间
	protected Date createTime = new Date();

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public int getHasSent() {
		return hasSent;
	}

	public void setHasSent(int hasSent) {
		this.hasSent = hasSent;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHasValidated() {
		return hasValidated;
	}

	public void setHasValidated(String hasValidated) {
		this.hasValidated = hasValidated;
	}
}
