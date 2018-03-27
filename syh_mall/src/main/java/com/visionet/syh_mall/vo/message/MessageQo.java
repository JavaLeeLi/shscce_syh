package com.visionet.syh_mall.vo.message;

import java.util.ArrayList;
import java.util.List;

import com.visionet.syh_mall.common.utils.OrderCondition;
import com.visionet.syh_mall.common.utils.PageInfo;

/**
 *@Author DM
 *@version ：2017年8月24日上午11:06:00
 *实体类
 */
public class MessageQo extends PageInfo {
	private String senderId;//发送人id
	private String receiverId;//接收人id
	protected List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public List<OrderCondition> getOrderConditions() {
		return orderConditions;
	}
	public void setOrderConditions(List<OrderCondition> orderConditions) {
		this.orderConditions = orderConditions;
	}
}
