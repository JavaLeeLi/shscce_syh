package com.visionet.syh_mall.entity.message;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年8月23日下午2:56:55 实体类
 */
@Entity
@Table(name = "tbl_user_message")
public class Message extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String senderId;// 发送人id
	private String receiverId;// 接收人id
	private int status;// 消息状态(0未读 1已读)
	private String title;// 消息标题
	private String content;// 内容
	private int isDeleted;// 是否删除
	protected Date createTime = new Date();

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Type(type = "text")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
