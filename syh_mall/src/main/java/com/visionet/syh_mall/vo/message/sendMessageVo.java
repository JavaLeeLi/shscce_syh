package com.visionet.syh_mall.vo.message;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 发送消息入参
 * @author mulongfei
 * @date 2017年9月25日上午10:13:55
 */
public class sendMessageVo {
	@NotEmpty(message="没有接收人")
	private String receiverID;
	@NotEmpty(message="请输入消息内容")
	private String msgContent;
	public String getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	@Override
	public String toString() {
		return "MessageVo [receiverID=" + receiverID + ", msgContent=" + msgContent + "]";
	}
}
