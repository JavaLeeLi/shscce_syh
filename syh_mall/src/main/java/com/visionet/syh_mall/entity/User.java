package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;

/**
 * 用户的实体类
 * 
 * @author chenghongzhan
 * @version 2017年8月24日 上午11:19:04
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tbl_user")
public class User extends IdEntity {
	private String wechatOpenId;// 微信唯一ID
	private String loginPassword;// 登录密码
	private String userTypeCode;// 会员认证类型
	private String userTypeOngoingCode;// 用户认证中状态编码
	private String loginName;// 会员登录名
	private Integer userValidIntegral = 0;// 会员可用积分
	private Integer userTotalIntegral = 0;// 会员总积分
	private Integer channelLevel;// 用户分销等级
	private Date lastLoginTime;// 会员最后一次登陆的时间
	private String userStatusCode;// 会员状态
	private String imgFileId;// 会员的头像文件id
	private String aliasName;// 用户昵称
	private String signature;// 用户的个性签名
	private String address;// 用户的地址
	private String phone;// 用户电话
	private String mail;// 用户邮箱
	private String wechat;
	private String qq;
	// private String unreadMsg;
	private String qqOpenId;// qq唯一ID
	private Date updateTime = DateUtil.getCurrentDate();// 用户修改时间
	private Date createTime = DateUtil.getCurrentDate();// 注册时间
	private Integer isDeleted = 0;// 是否删除
	private String invitationCode;// 会员邀请码
	private String payPwd = "0";
	private Integer memberType;// 会员类型

	/*
	 * public String getUnreadMsg() { return unreadMsg; }
	 * 
	 * public void setUnreadMsg(String unreadMsg) { this.unreadMsg = unreadMsg;
	 * }
	 */

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getUserValidIntegral() {
		return userValidIntegral;
	}

	public void setUserValidIntegral(Integer userValidIntegral) {
		this.userValidIntegral = userValidIntegral;
	}

	public String getWechatOpenId() {
		return wechatOpenId;
	}

	public void setWechatOpenId(String wechatOpenId) {
		this.wechatOpenId = wechatOpenId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public void setChannelLevel(int channelLevel) {
		this.channelLevel = channelLevel;
	}

	public String getImgFileId() {
		return imgFileId;
	}

	public void setImgFileId(String imgFileId) {
		this.imgFileId = imgFileId;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getChannelLevel() {
		return channelLevel;
	}

	public void setChannelLevel(Integer channelLevel) {
		this.channelLevel = channelLevel;
	}

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getUserStatusCode() {
		return userStatusCode;
	}

	public void setUserStatusCode(String userStatusCode) {
		this.userStatusCode = userStatusCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getUserTotalIntegral() {
		return userTotalIntegral;
	}

	public void setUserTotalIntegral(Integer userTotalIntegral) {
		this.userTotalIntegral = userTotalIntegral;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getUserTypeOngoingCode() {
		return userTypeOngoingCode;
	}

	public void setUserTypeOngoingCode(String userTypeOngoingCode) {
		this.userTypeOngoingCode = userTypeOngoingCode;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

}
