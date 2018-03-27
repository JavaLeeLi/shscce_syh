package com.visionet.syh_mall.vo.goods;

import com.visionet.syh_mall.entity.goods.EvaluationPicLink;

public class EvaluationImgVo {
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
		return "EvaluationImgVo [maxImgID=" + maxImgID + ", midImgID=" + midImgID + ", minImgID=" + minImgID + "]";
	}
	public static EvaluationPicLink VoToPo(EvaluationPicLink evaluationPicLink,EvaluationImgVo evaluationImgVo,String evaluationId){
		evaluationPicLink.setEvaluationId(evaluationId);
		evaluationPicLink.setMaxImgId(evaluationImgVo.getMaxImgID());
		evaluationPicLink.setMidImgId(evaluationImgVo.getMidImgID());
		evaluationPicLink.setMinImgId(evaluationImgVo.getMinImgID());
		return evaluationPicLink;
	}
}
