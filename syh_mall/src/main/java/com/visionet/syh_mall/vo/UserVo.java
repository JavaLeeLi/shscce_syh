package com.visionet.syh_mall.vo;

import com.visionet.syh_mall.entity.account.UserBank;

import java.util.Date;
import java.util.List;

/**
 * @Author DM
 * @version ：2017年9月7日下午1:52:11 实体类
 */
public class UserVo {
	private String mbrId;// 会员ID
	private boolean bindWeiXin;//是否绑定微信
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
	private int mbrValidIntegral;// 会员可用积分
	private int mbrTotalIntegral;// 会员历史积分数
	private Date mbrRegisterTime;// 会员注册时间
	private Float mbrConsumeSum;// 会员总消费金额
	private String mbrWechatOpenId;// 会员绑定微信openid
	private String mbrQQOpenId;// 会员绑定QQ openid
	private int mbrAttentionCount;// 会员关注数
	private int mbrFavoriteCount;// 会员收藏数
	private Float mbrEarning;// 会员平台收入
	private Date mbrAuthenDate;// 会员认证日期
	private int status;
	private String id;
	private String refusalReason;
	private String userTypeOngoingCode;
	private String userTypeOngoingDesc;
	private String userRealName;//认证时真实姓名
	private String payPwd;
	private Integer memberType;// 会员类型
	private String mbrIdCode;//会员身份证号
    private List<UserBank> userAccount;//个人银行卡集或法人银行卡
	private String parentBankName;//企业用户开户行
	private String accountNo;//企业银行卡


	public String getMbrIdCode() {
		return mbrIdCode;
	}

	public void setMbrIdCode(String mbrIdCode) {
		this.mbrIdCode = mbrIdCode;
	}

	public List<UserBank> getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(List<UserBank> userAccount) {
		this.userAccount = userAccount;
	}

	public String getParentBankName() {
		return parentBankName;
	}

	public void setParentBankName(String parentBankName) {
		this.parentBankName = parentBankName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getMbrValidIntegral() {
		return mbrValidIntegral;
	}

	public void setMbrValidIntegral(int mbrValidIntegral) {
		this.mbrValidIntegral = mbrValidIntegral;
	}

	public int getMbrTotalIntegral() {
		return mbrTotalIntegral;
	}

	public void setMbrTotalIntegral(int mbrTotalIntegral) {
		this.mbrTotalIntegral = mbrTotalIntegral;
	}

	public Date getMbrRegisterTime() {
		return mbrRegisterTime;
	}

	public void setMbrRegisterTime(Date mbrRegisterTime) {
		this.mbrRegisterTime = mbrRegisterTime;
	}

	public Float getMbrConsumeSum() {
		return mbrConsumeSum;
	}

	public void setMbrConsumeSum(Float mbrConsumeSum) {
		this.mbrConsumeSum = mbrConsumeSum;
	}

	public String getMbrWechatOpenId() {
		return mbrWechatOpenId;
	}

	public void setMbrWechatOpenId(String mbrWechatOpenId) {
		this.mbrWechatOpenId = mbrWechatOpenId;
	}

	public String getMbrQQOpenId() {
		return mbrQQOpenId;
	}

	public void setMbrQQOpenId(String mbrQQOpenId) {
		this.mbrQQOpenId = mbrQQOpenId;
	}

	public int getMbrAttentionCount() {
		return mbrAttentionCount;
	}

	public void setMbrAttentionCount(int mbrAttentionCount) {
		this.mbrAttentionCount = mbrAttentionCount;
	}

	public int getMbrFavoriteCount() {
		return mbrFavoriteCount;
	}

	public void setMbrFavoriteCount(int mbrFavoriteCount) {
		this.mbrFavoriteCount = mbrFavoriteCount;
	}

	public Float getMbrEarning() {
		return mbrEarning;
	}

	public void setMbrEarning(Float mbrEarning) {
		this.mbrEarning = mbrEarning;
	}

	public Date getMbrAuthenDate() {
		return mbrAuthenDate;
	}

	public void setMbrAuthenDate(Date mbrAuthenDate) {
		this.mbrAuthenDate = mbrAuthenDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	@Override
	public String toString() {
		return "UserVo{" +
				"mbrId='" + mbrId + '\'' +
				", bindWeiXin=" + bindWeiXin +
				", mbrLoginName='" + mbrLoginName + '\'' +
				", mbrName='" + mbrName + '\'' +
				", mbrPhone='" + mbrPhone + '\'' +
				", mbrImgId='" + mbrImgId + '\'' +
				", mbrImgUrl='" + mbrImgUrl + '\'' +
				", mbrTypeCode='" + mbrTypeCode + '\'' +
				", mbrTypeDesc='" + mbrTypeDesc + '\'' +
				", mbrStatusCode='" + mbrStatusCode + '\'' +
				", mbrStatusDesc='" + mbrStatusDesc + '\'' +
				", mbrSignature='" + mbrSignature + '\'' +
				", mbrAddress='" + mbrAddress + '\'' +
				", mbrEmail='" + mbrEmail + '\'' +
				", mbrWechat='" + mbrWechat + '\'' +
				", mbrQQ='" + mbrQQ + '\'' +
				", channelLevel=" + channelLevel +
				", mbrLastLoginTime=" + mbrLastLoginTime +
				", mbrValidIntegral=" + mbrValidIntegral +
				", mbrTotalIntegral=" + mbrTotalIntegral +
				", mbrRegisterTime=" + mbrRegisterTime +
				", mbrConsumeSum=" + mbrConsumeSum +
				", mbrWechatOpenId='" + mbrWechatOpenId + '\'' +
				", mbrQQOpenId='" + mbrQQOpenId + '\'' +
				", mbrAttentionCount=" + mbrAttentionCount +
				", mbrFavoriteCount=" + mbrFavoriteCount +
				", mbrEarning=" + mbrEarning +
				", mbrAuthenDate=" + mbrAuthenDate +
				", status=" + status +
				", id='" + id + '\'' +
				", refusalReason='" + refusalReason + '\'' +
				", userTypeOngoingCode='" + userTypeOngoingCode + '\'' +
				", userTypeOngoingDesc='" + userTypeOngoingDesc + '\'' +
				", userRealName='" + userRealName + '\'' +
				", payPwd='" + payPwd + '\'' +
				", memberType=" + memberType +
				", mbrIdCode='" + mbrIdCode + '\'' +
				", userAccount=" + userAccount +
				", parentBankName='" + parentBankName + '\'' +
				", accountNo='" + accountNo + '\'' +
				'}';
	}

	public boolean isBindWeiXin() {
		return bindWeiXin;
	}

	public void setBindWeiXin(boolean bindWeiXin) {
		this.bindWeiXin = bindWeiXin;
	}
}
