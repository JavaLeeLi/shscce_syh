package com.visionet.syh_mall.vo;

import java.util.Date;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.UserAuthentication;

/**
 * @Author DM
 * @version ：2017年9月17日下午2:48:50 实体类
 */
public class UserAuthenticationVo {
	private String mbrId;// 会员ID
	private String mbrRealName;// 会员/法人真实姓名
	private String mbrProvince;// 用户所属省份
	private String mbrCity;// 用户所属城市
	private String mbrArea;// 用户所属区县
	private String mbrStreet;// 用户所属街道
	private String mbrIDCode;// 会员/法人身份证号码
	private String mbrIDFImgId;// 会员/法人身份证正面照片文件ID
	private String mbrIDFImgUrl;
	private String mbrIDBImgId;// 会员/法人身份证背面照片文件ID
	private String mbrIDBImgUrl;
	private String mbrOrganizationCode;// 商户会员组织机构代码
	private String mbrLicenseCode;// 商户会员营业执照编码
	private String mbrLicenseImgId;// 商户会员营业执照照片文件ID
	private String mbrLicenseImgUrl;
	private int reviewResult;// 会员认证审核结果(0：拒绝,1：通过)
	private String refusalReason;// 拒绝理由
	private String userTypeOngoingCode;
	private String userTypeOngoingDesc;
	private String mbrLoginName;// 会员登录名
	private String mbrName;// 会员昵称
	private String mbrPhone;// 会员手机号
	private String mbrImgId;// 会员头像文件ID
	private String mbrImgUrl;// 会员头像url链接
	private String mbrTypeCode;// 会员认证类型编码
	private String mbrTypeDesc;// 会员认证类型描述
	private String mbrStatusCode;// 会员状态编码
	private String mbrStatusDesc;// 会员状态描述
	private String mbrSignature;// 会员个性签名
	private String mbrAddress;// 会员地址
	private String mbrEmail;// 会员邮箱
	private String mbrWechat;// 会员微信号
	private String mbrQQ;// 会员QQ号
	private int channelLevel;// 会员分销等级
	private Date mbrLastLoginTime;// 会员最近登录时间

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	public String getMbrRealName() {
		return mbrRealName;
	}

	public void setMbrRealName(String mbrRealName) {
		this.mbrRealName = mbrRealName;
	}

	public String getMbrIDCode() {
		return mbrIDCode;
	}

	public void setMbrIDCode(String mbrIDCode) {
		this.mbrIDCode = mbrIDCode;
	}

	public String getMbrIDFImgId() {
		return mbrIDFImgId;
	}

	public void setMbrIDFImgId(String mbrIDFImgId) {
		this.mbrIDFImgId = mbrIDFImgId;
	}

	public String getMbrIDBImgId() {
		return mbrIDBImgId;
	}

	public void setMbrIDBImgId(String mbrIDBImgId) {
		this.mbrIDBImgId = mbrIDBImgId;
	}

	public String getMbrOrganizationCode() {
		return mbrOrganizationCode;
	}

	public void setMbrOrganizationCode(String mbrOrganizationCode) {
		this.mbrOrganizationCode = mbrOrganizationCode;
	}

	public String getMbrLicenseCode() {
		return mbrLicenseCode;
	}

	public void setMbrLicenseCode(String mbrLicenseCode) {
		this.mbrLicenseCode = mbrLicenseCode;
	}

	public String getMbrLicenseImgId() {
		return mbrLicenseImgId;
	}

	public void setMbrLicenseImgId(String mbrLicenseImgId) {
		this.mbrLicenseImgId = mbrLicenseImgId;
	}

	public String getMbrProvince() {
		return mbrProvince;
	}

	public void setMbrProvince(String mbrProvince) {
		this.mbrProvince = mbrProvince;
	}

	public String getMbrCity() {
		return mbrCity;
	}

	public void setMbrCity(String mbrCity) {
		this.mbrCity = mbrCity;
	}

	public String getMbrArea() {
		return mbrArea;
	}

	public void setMbrArea(String mbrArea) {
		this.mbrArea = mbrArea;
	}

	public String getMbrStreet() {
		return mbrStreet;
	}

	public void setMbrStreet(String mbrStreet) {
		this.mbrStreet = mbrStreet;
	}

	public int getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}

	public String getMbrLoginName() {
		return mbrLoginName;
	}

	public void setMbrLoginName(String mbrLoginName) {
		this.mbrLoginName = mbrLoginName;
	}

	public String getMbrName() {
		return mbrName;
	}

	public void setMbrName(String mbrName) {
		this.mbrName = mbrName;
	}

	public String getMbrPhone() {
		return mbrPhone;
	}

	public void setMbrPhone(String mbrPhone) {
		this.mbrPhone = mbrPhone;
	}

	public String getMbrImgId() {
		return mbrImgId;
	}

	public void setMbrImgId(String mbrImgId) {
		this.mbrImgId = mbrImgId;
	}

	public String getMbrImgUrl() {
		return mbrImgUrl;
	}

	public void setMbrImgUrl(String mbrImgUrl) {
		this.mbrImgUrl = mbrImgUrl;
	}

	public String getMbrTypeCode() {
		return mbrTypeCode;
	}

	public void setMbrTypeCode(String mbrTypeCode) {
		this.mbrTypeCode = mbrTypeCode;
	}

	public String getMbrTypeDesc() {
		return mbrTypeDesc;
	}

	public void setMbrTypeDesc(String mbrTypeDesc) {
		this.mbrTypeDesc = mbrTypeDesc;
	}

	public String getMbrStatusCode() {
		return mbrStatusCode;
	}

	public void setMbrStatusCode(String mbrStatusCode) {
		this.mbrStatusCode = mbrStatusCode;
	}

	public String getMbrStatusDesc() {
		return mbrStatusDesc;
	}

	public void setMbrStatusDesc(String mbrStatusDesc) {
		this.mbrStatusDesc = mbrStatusDesc;
	}

	public String getMbrSignature() {
		return mbrSignature;
	}

	public void setMbrSignature(String mbrSignature) {
		this.mbrSignature = mbrSignature;
	}

	public String getMbrAddress() {
		return mbrAddress;
	}

	public void setMbrAddress(String mbrAddress) {
		this.mbrAddress = mbrAddress;
	}

	public String getMbrEmail() {
		return mbrEmail;
	}

	public void setMbrEmail(String mbrEmail) {
		this.mbrEmail = mbrEmail;
	}

	public String getMbrWechat() {
		return mbrWechat;
	}

	public void setMbrWechat(String mbrWechat) {
		this.mbrWechat = mbrWechat;
	}

	public String getMbrQQ() {
		return mbrQQ;
	}

	public void setMbrQQ(String mbrQQ) {
		this.mbrQQ = mbrQQ;
	}

	public int getChannelLevel() {
		return channelLevel;
	}

	public void setChannelLevel(int channelLevel) {
		this.channelLevel = channelLevel;
	}

	public Date getMbrLastLoginTime() {
		return mbrLastLoginTime;
	}

	public void setMbrLastLoginTime(Date mbrLastLoginTime) {
		this.mbrLastLoginTime = mbrLastLoginTime;
	}

	public String getMbrIDFImgUrl() {
		return mbrIDFImgUrl;
	}

	public void setMbrIDFImgUrl(String mbrIDFImgUrl) {
		this.mbrIDFImgUrl = mbrIDFImgUrl;
	}

	public String getMbrIDBImgUrl() {
		return mbrIDBImgUrl;
	}

	public void setMbrIDBImgUrl(String mbrIDBImgUrl) {
		this.mbrIDBImgUrl = mbrIDBImgUrl;
	}

	public String getMbrLicenseImgUrl() {
		return mbrLicenseImgUrl;
	}

	public void setMbrLicenseImgUrl(String mbrLicenseImgUrl) {
		this.mbrLicenseImgUrl = mbrLicenseImgUrl;
	}

	public String getRefusalReason() {
		return refusalReason;
	}

	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}

	public String getUserTypeOngoingCode() {
		return userTypeOngoingCode;
	}

	public void setUserTypeOngoingCode(String userTypeOngoingCode) {
		this.userTypeOngoingCode = userTypeOngoingCode;
	}

	public String getUserTypeOngoingDesc() {
		return userTypeOngoingDesc;
	}

	public void setUserTypeOngoingDesc(String userTypeOngoingDesc) {
		this.userTypeOngoingDesc = userTypeOngoingDesc;
	}

	/**
	 * @Title: coverPo @Description: 个人会员认证的Vo转Po @param @param
	 *         authenticationVo @param @param userAuthentication @param @return
	 *         设定文件 @return UserAuthentication 返回类型 @throws
	 */
	public UserAuthentication coverPo(UserAuthenticationVo authenticationVo, UserAuthentication userAuthentication) {
		userAuthentication.setUserId(authenticationVo.getMbrId());
		userAuthentication.setUserRealName(authenticationVo.getMbrRealName());
		userAuthentication.setIdCode(authenticationVo.getMbrIDCode());
		userAuthentication.setUserProvince(authenticationVo.getMbrProvince());
		userAuthentication.setUserCity(authenticationVo.getMbrCity());
		userAuthentication.setUserArea(authenticationVo.getMbrArea());
		userAuthentication.setUserStreet(authenticationVo.getMbrStreet());
		userAuthentication.setId_f_fileId(authenticationVo.getMbrIDFImgId());
		userAuthentication.setId_b_fileId(authenticationVo.getMbrIDBImgId());
		userAuthentication.setOrganizationCode(authenticationVo.getMbrOrganizationCode());
		userAuthentication.setLicenseNo(authenticationVo.getMbrLicenseCode());
		userAuthentication.setLicenseCertFileId(authenticationVo.getMbrLicenseImgId());
		userAuthentication.setCreateTime(DateUtil.getCurrentDate());
		userAuthentication.setStatus(0);
		userAuthentication.setIsDeleted(0);
		return userAuthentication;
	}

	@Override
	public String toString() {
		return "UserAuthenticationVo [mbrId=" + mbrId + ", mbrRealName=" + mbrRealName + ", mbrProvince=" + mbrProvince
				+ ", mbrCity=" + mbrCity + ", mbrArea=" + mbrArea + ", mbrStreet=" + mbrStreet + ", mbrIDCode="
				+ mbrIDCode + ", mbrIDFImgId=" + mbrIDFImgId + ", mbrIDFImgUrl=" + mbrIDFImgUrl + ", mbrIDBImgId="
				+ mbrIDBImgId + ", mbrIDBImgUrl=" + mbrIDBImgUrl + ", mbrOrganizationCode=" + mbrOrganizationCode
				+ ", mbrLicenseCode=" + mbrLicenseCode + ", mbrLicenseImgId=" + mbrLicenseImgId + ", mbrLicenseImgUrl="
				+ mbrLicenseImgUrl + ", reviewResult=" + reviewResult + ", refusalReason=" + refusalReason
				+ ", userTypeOngoingCode=" + userTypeOngoingCode + ", userTypeOngoingDesc=" + userTypeOngoingDesc
				+ ", mbrLoginName=" + mbrLoginName + ", mbrName=" + mbrName + ", mbrPhone=" + mbrPhone + ", mbrImgId="
				+ mbrImgId + ", mbrImgUrl=" + mbrImgUrl + ", mbrTypeCode=" + mbrTypeCode + ", mbrTypeDesc="
				+ mbrTypeDesc + ", mbrStatusCode=" + mbrStatusCode + ", mbrStatusDesc=" + mbrStatusDesc
				+ ", mbrSignature=" + mbrSignature + ", mbrAddress=" + mbrAddress + ", mbrEmail=" + mbrEmail
				+ ", mbrWechat=" + mbrWechat + ", mbrQQ=" + mbrQQ + ", channelLevel=" + channelLevel
				+ ", mbrLastLoginTime=" + mbrLastLoginTime + "]";
	}

}
