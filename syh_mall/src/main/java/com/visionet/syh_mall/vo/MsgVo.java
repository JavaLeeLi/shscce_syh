package com.visionet.syh_mall.vo;

import java.util.List;

import com.visionet.syh_mall.vo.message.MessageVo;

/**
 *@Author DM
 *@version ：2017年10月20日下午3:44:23
 *实体类
 */
public class MsgVo {
	private long itemCount;	//满足条件的记录总数
	private int pageCount;	//满足条件的记录分页数
	private int curPageIndex;	//当前返回记录页码
	private boolean hasNext;//是否还有下一页数据
	 private String senderImgUrl;//发送人头像图片url链接
	 private String receiverImgUrl;//接收人
	 private int msgsCount;//消息数量
	 private String senderName;//发送人名称
	 private String chatUserId;//聊天对象id
    private List<MessageVo> msgs;
	public long getItemCount() {
		return itemCount;
	}
	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurPageIndex() {
		return curPageIndex;
	}
	public void setCurPageIndex(int curPageIndex) {
		this.curPageIndex = curPageIndex;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public List<MessageVo> getMsgs() {
		return msgs;
	}
	public void setMsgs(List<MessageVo> msgs) {
		this.msgs = msgs;
	}
	public String getSenderImgUrl() {
		return senderImgUrl;
	}
	public void setSenderImgUrl(String senderImgUrl) {
		this.senderImgUrl = senderImgUrl;
	}
	public String getReceiverImgUrl() {
		return receiverImgUrl;
	}
	public void setReceiverImgUrl(String receiverImgUrl) {
		this.receiverImgUrl = receiverImgUrl;
	}
	public int getMsgsCount() {
		return msgsCount;
	}
	public void setMsgsCount(int msgsCount) {
		this.msgsCount = msgsCount;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getChatUserId() {
		return chatUserId;
	}
	public void setChatUserId(String chatUserId) {
		this.chatUserId = chatUserId;
	}
	
}
