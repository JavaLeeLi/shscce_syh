package com.visionet.syh_mall.vo.message;

import java.util.Date;
import java.util.List;



/**
 *@Author DM
 *@version ：2017年8月23日下午4:47:40
 *实体类
 */
public class MessageVo {
	 private String id;//消息id
	 private String senderId;//发送人id
	 private String receiverId;//接收人id
	 private int isSelfMsg;//是否自己发送的消息（0：否1：是）
	 private String senderName;//发送人名称
	 private String senderImgUrl;//发送人头像图片url链接
	 private String receiverImgUrl;//接收人
	 private String msgTitle;//消息标题
	 private String msgContent;//最近消息内容
	 private Date msgTime;//最近消息时间
	 private int msgsCount;//消息数量
	 private int isSelfmsgCount;//消息未读数量
	 private List<MessageVo> msgs;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public int getIsSelfMsg() {
		return isSelfMsg;
	}
	public void setIsSelfMsg(int isSelfMsg) {
		this.isSelfMsg = isSelfMsg;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderImgUrl() {
		return senderImgUrl;
	}
	public void setSenderImgUrl(String senderImgUrl) {
		this.senderImgUrl = senderImgUrl;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public Date getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}
	
	public List<MessageVo> getMsgs() {
		return msgs;
	}
	public void setMsgs(List<MessageVo> msgs) {
		this.msgs = msgs;
	}
	public int getMsgsCount() {
		return msgsCount;
	}
	public void setMsgsCount(int msgsCount) {
		this.msgsCount = msgsCount;
	}
	public String getReceiverImgUrl() {
		return receiverImgUrl;
	}
	public void setReceiverImgUrl(String receiverImgUrl) {
		this.receiverImgUrl = receiverImgUrl;
	}
	public int getIsSelfmsgCount() {
		return isSelfmsgCount;
	}
	public void setIsSelfmsgCount(int isSelfmsgCount) {
		this.isSelfmsgCount = isSelfmsgCount;
	}
	
}
