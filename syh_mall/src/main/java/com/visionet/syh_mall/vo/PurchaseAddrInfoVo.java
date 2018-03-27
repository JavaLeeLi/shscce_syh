package com.visionet.syh_mall.vo;
/**
 *@Author DM
 *@version ：2017年9月27日下午3:25:56
 *实体类
 */
public class PurchaseAddrInfoVo {
	private String addrId;//收货地址ID
	private String receiverName;//收件人姓名
	private String receiverPhone;//收件人手机号
	private String addrProvince;//省份
	private String addrCity;//城市
	private String addrArea;//区县
	private String addrStreet;//街道
	private String addrDetail;//详细收货地址
	public String getAddrId() {
		return addrId;
	}
	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getAddrProvince() {
		return addrProvince;
	}
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public String getAddrArea() {
		return addrArea;
	}
	public void setAddrArea(String addrArea) {
		this.addrArea = addrArea;
	}
	public String getAddrStreet() {
		return addrStreet;
	}
	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}
	public String getAddrDetail() {
		return addrDetail;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
}
