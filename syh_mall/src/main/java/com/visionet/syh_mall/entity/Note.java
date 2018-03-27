package com.visionet.syh_mall.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 短信实体
 * @author mulongfei
 * @date 2017年10月30日上午10:55:09
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tbl_note")
public class Note extends IdEntity{
	private String promptItem;//提示项目
	private String explain;//说明
	private String receiver;//接收方
	private String content;//内容
	public String getPromptItem() {
		return promptItem;
	}
	public void setPromptItem(String promptItem) {
		this.promptItem = promptItem;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}
