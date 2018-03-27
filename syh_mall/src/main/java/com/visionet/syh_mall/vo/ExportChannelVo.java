package com.visionet.syh_mall.vo;

import java.math.BigDecimal;

public class ExportChannelVo {
	private String userNo;// 用户编号
	private String userName;// 用户名字
	private String orderNo;// 订单号
	private BigDecimal orderFee;// 订单金额
	private Integer orderNum;// 订单数量
	private String channelCommission;// 分销比例
	private String channelFee;// 分销金额

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(BigDecimal orderFee) {
		this.orderFee = orderFee;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getChannelCommission() {
		return channelCommission;
	}

	public void setChannelCommission(String channelCommission) {
		this.channelCommission = channelCommission;
	}

	public String getChannelFee() {
		return channelFee;
	}

	public void setChannelFee(String channelFee) {
		this.channelFee = channelFee;
	}


}
