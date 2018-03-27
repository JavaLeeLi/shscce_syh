package com.visionet.syh_mall.vo.goods;
/**
 * 商品草稿入参
 * @author mulongfei
 * @date 2017年9月25日下午2:18:15
 */
public class GoodsDraftImgVo {
	private String maxImgID;
	private String midImgID;
	private String minImgID;
	public String getMaxImgID() {
		return maxImgID;
	}
	public void setMaxImgID(String maxImgID) {
		this.maxImgID = maxImgID;
	}
	public String getMidImgID() {
		return midImgID;
	}
	public void setMidImgID(String midImgID) {
		this.midImgID = midImgID;
	}
	public String getMinImgID() {
		return minImgID;
	}
	public void setMinImgID(String minImgID) {
		this.minImgID = minImgID;
	}
	@Override
	public String toString() {
		return "goodsDraftImgVo [maxImgID=" + maxImgID + ", midImgID=" + midImgID + ", minImgID=" + minImgID + "]";
	}
}
