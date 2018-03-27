package com.visionet.syh_mall.vo.user;
/**
 * 绑定银行卡入参
 * @author mulongfei
 * @date 2017年10月11日下午4:57:13
 */
public class BindBankCard {
	private String bizUserId;  //用户id
	private String cardNo;	//卡号
	private String phone;	//银行预留手机号
	private String name;	//姓名
	private Long cardCheck = 1L;	//三要素绑卡
	private Long identityType = 1L;	//证件类型 身份证
	private String identityNo;  //证件编号
	private Boolean isSafeCard;   //是否绑定安全卡
	private String unionBank;  //支持行号
	public String getBizUserId() {
		return bizUserId;
	}
	public void setBizUserId(String bizUserId) {
		this.bizUserId = bizUserId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCardCheck() {
		return cardCheck;
	}
	public void setCardCheck(Long cardCheck) {
		this.cardCheck = cardCheck;
	}
	public Long getIdentityType() {
		return identityType;
	}
	public void setIdentityType(Long identityType) {
		this.identityType = identityType;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public Boolean getIsSafeCard() {
		return isSafeCard;
	}
	public void setIsSafeCard(Boolean isSafeCard) {
		this.isSafeCard = isSafeCard;
	}
	public String getUnionBank() {
		return unionBank;
	}
	public void setUnionBank(String unionBank) {
		this.unionBank = unionBank;
	}
}
