package com.visionet.syh_mall.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "tbl_evaluation_result")
public class EvaluationResult extends IdEntity {
	private String evaluationTypeCode;

	public String getEvaluationTypeCode() {
		return evaluationTypeCode;
	}

	public void setEvaluationTypeCode(String evaluationTypeCode) {
		this.evaluationTypeCode = evaluationTypeCode;
	}

	private String factorCode;
	private String acceptance;
	private String collectTypeCode;
	private String collectName;
	private String serialNumber;
	private String releaseYear;
	private String releaseUnit;
	private String faceValue;
	private String crownNumber;
	private String watermark;
	private String texture;
	private Integer specification;
	private String printing;
	private String collectEvaluationCode;
	private String ratingScore;
	private String isTrue = "暂无";
	private Integer evaluationNumber;
	private String evaluationDivision;
	private int isDeleted = 0;
	private String remark;
	private Date createTime = DateUtil.getCurrentDate();
	private String evaluationBy;// 鉴评人
	private Date updateTime = DateUtil.getCurrentDate();
	private String trimBy;// 整理人
	private String auditBy;// 审核人
	private Integer firstEvaluation = 0;
	private String qrCode;// 二维码
	private String objection;// 拒绝理由

	public String getFactorCode() {
		return factorCode;
	}

	public void setFactorCode(String factorCode) {
		this.factorCode = factorCode;
	}

	public String getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}

	public String getCollectTypeCode() {
		return collectTypeCode;
	}

	public void setCollectTypeCode(String collectTypeCode) {
		this.collectTypeCode = collectTypeCode;
	}

	public String getCollectName() {
		return collectName;
	}

	public void setCollectName(String collectName) {
		this.collectName = collectName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getReleaseUnit() {
		return releaseUnit;
	}

	public void setReleaseUnit(String releaseUnit) {
		this.releaseUnit = releaseUnit;
	}

	public String getCrownNumber() {
		return crownNumber;
	}

	public void setCrownNumber(String crownNumber) {
		this.crownNumber = crownNumber;
	}

	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public Integer getSpecification() {
		return specification;
	}

	public void setSpecification(Integer specification) {
		this.specification = specification;
	}

	public String getPrinting() {
		return printing;
	}

	public void setPrinting(String printing) {
		this.printing = printing;
	}

	public String getCollectEvaluationCode() {
		return collectEvaluationCode;
	}

	public void setCollectEvaluationCode(String collectEvaluationCode) {
		this.collectEvaluationCode = collectEvaluationCode;
	}

	public String getRatingScore() {
		return ratingScore;
	}

	public void setRatingScore(String ratingScore) {
		this.ratingScore = ratingScore;
	}

	public String getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(String isTrue) {
		this.isTrue = isTrue;
	}

	public Integer getEvaluationNumber() {
		return evaluationNumber;
	}

	public void setEvaluationNumber(Integer evaluationNumber) {
		this.evaluationNumber = evaluationNumber;
	}

	public String getEvaluationDivision() {
		return evaluationDivision;
	}

	public void setEvaluationDivision(String evaluationDivision) {
		this.evaluationDivision = evaluationDivision;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getFirstEvaluation() {
		return firstEvaluation;
	}

	public void setFirstEvaluation(Integer firstEvaluation) {
		this.firstEvaluation = firstEvaluation;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getObjection() {
		return objection;
	}

	public void setObjection(String objection) {
		this.objection = objection;
	}

	public String getEvaluationBy() {
		return evaluationBy;
	}

	public void setEvaluationBy(String evaluationBy) {
		this.evaluationBy = evaluationBy;
	}

	public String getTrimBy() {
		return trimBy;
	}

	public void setTrimBy(String trimBy) {
		this.trimBy = trimBy;
	}

	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
}
