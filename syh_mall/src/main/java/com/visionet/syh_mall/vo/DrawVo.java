package com.visionet.syh_mall.vo;

public class DrawVo {

	private String drawCashId;//提现记录id
	private String tagName;//提现状态编码

	public String getDrawCashId() {
		return drawCashId;
	}

	public void setDrawCashId(String drawCashId) {
		this.drawCashId = drawCashId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "DrawVo [drawCashId=" + drawCashId + ", tagName=" + tagName + "]";
	}
}
