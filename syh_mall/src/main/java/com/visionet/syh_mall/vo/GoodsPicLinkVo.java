package com.visionet.syh_mall.vo;

/**
 * @ClassName: GoodsPicLinkVo
 * @Description: 商品图片的Vo
 * @author chenghongzhan
 * @date 2017年10月23日 上午11:40:21
 *
 */
public class GoodsPicLinkVo {
	private String maxImgID;// 大尺寸图片文件id
	private String midImgID;// 中尺寸图片文件id
	private String minImgID;// 小尺寸图片文件id

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
		return "GoodsPicLinkVo [maxImgID=" + maxImgID + ", midImgID=" + midImgID + ", minImgID=" + minImgID + "]";
	}

}
