package com.visionet.syh_mall.vo.goods;

import java.util.Date;

public class SearchEvaluationVo {
	private String collectEvaluationCode;//藏品编码
	private String collectName;//藏品名称
	private String collectTypeCode;//藏品分类编码（1，2，3，4）
	private String acceptance;//受理单编号
	private Date evaluationStartTime;//鉴评时间(开始)
	private Date evaluationEndTime;//鉴评时间(结束)
	private String evaluationTypeCode;//不同页面传不同的参数
	private String firstEvaluation;//状态--（审核部检索使用字段）
	private String factorCode;//代理商编码
	private String division;//鉴评师
	private Integer itemCount;
	private Integer pageIndex;
	public String getCollectEvaluationCode() {
		return collectEvaluationCode;
	}
	public void setCollectEvaluationCode(String collectEvaluationCode) {
		this.collectEvaluationCode = collectEvaluationCode;
	}
	public String getCollectName() {
		return collectName;
	}
	public void setCollectName(String collectName) {
		this.collectName = collectName;
	}
	public String getCollectTypeCode() {
		return collectTypeCode;
	}
	public void setCollectTypeCode(String collectTypeCode) {
		this.collectTypeCode = collectTypeCode;
	}
	public String getAcceptance() {
		return acceptance;
	}
	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}
	public Date getEvaluationStartTime() {
		return evaluationStartTime;
	}
	public void setEvaluationStartTime(Date evaluationStartTime) {
		this.evaluationStartTime = evaluationStartTime;
	}
	public Date getEvaluationEndTime() {
		return evaluationEndTime;
	}
	public void setEvaluationEndTime(Date evaluationEndTime) {
		this.evaluationEndTime = evaluationEndTime;
	}
	public String getEvaluationTypeCode() {
		return evaluationTypeCode;
	}
	public void setEvaluationTypeCode(String evaluationTypeCode) {
		this.evaluationTypeCode = evaluationTypeCode;
	}
	public String getFirstEvaluation() {
		return firstEvaluation;
	}
	public void setFirstEvaluation(String firstEvaluation) {
		this.firstEvaluation = firstEvaluation;
	}
	public String getFactorCode() {
		return factorCode;
	}
	public void setFactorCode(String factorCode) {
		this.factorCode = factorCode;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
}
