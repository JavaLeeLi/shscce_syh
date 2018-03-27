package com.visionet.syh_mall.vo.goods;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.goods.EvaluationResult;

public class EvaluationResultVo {
	private int operationType;// 0暂存 ，1提交鉴评，2鉴评部确认，3审核部审核
	private String id;// 数据id(添加没有，编辑有)
	private String evaluationTypeCode;// 暂存evaluation_hold，鉴评中evaluation_in，鉴评审核evaluation_audit，鉴评通过evaluation_pass
	private String factorCode;// 代理商编号
	private String acceptance;// 受理单编号
	private Integer collectTypeCode;// 1-邮票 2-机制币 3-纸钞 4-邮资封片
	private String collectName;// 藏品名称
	private String serialNumber;// 志号
	private String releaseYear;// 发行年份
	private String releaseUnit;// 发行单位
	private String faceValue;// 面值(元)
	private String crownNumber;// 冠号
	private String watermark;// 水印或版别
	private String texture;// 材质
	private Integer specification;// 规格/重量(g)
	private String printing;// 版别/工艺
	private String collectEvaluationCode;// 面值藏品编码

	public String getEvaluationTypeCode() {
		return evaluationTypeCode;
	}

	public void setEvaluationTypeCode(String evaluationTypeCode) {
		this.evaluationTypeCode = evaluationTypeCode;
	}

	private String ratingScore;// 评级分数
	private String isTrue;// 是否真品
	private Integer evaluationNumber;// 藏品数量
	private String evaluationDivision;// 鉴评师(多个人使用，隔开)
	private String remark;// 备注
	private List<EvaluationImgVo> imgs;

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

	public String getIsTrue() {
		return isTrue;
	}

	public String getRatingScore() {
		return ratingScore;
	}

	public void setRatingScore(String ratingScore) {
		this.ratingScore = ratingScore;
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

	public void setEvaluationDivision(List<String> evaluationDivision) {
		String divisions = null;
		for (String evaluation : evaluationDivision) {
			if(StringUtils.isEmpty(divisions)){
				divisions = evaluation;
			}else{
				divisions = divisions + ","+evaluation;
			}
		}
		this.evaluationDivision = divisions;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCollectTypeCode() {
		return collectTypeCode;
	}

	public void setCollectTypeCode(Integer collectTypeCode) {
		this.collectTypeCode = collectTypeCode;
	}

	@Override
	public String toString() {
		return "EvaluationResultVo{" + "operationType=" + operationType + ", id='" + id + '\''
				+ ", evaluationTypeCode='" + evaluationTypeCode + '\'' + ", factorCode='" + factorCode + '\''
				+ ", acceptance='" + acceptance + '\'' + ", collectTypeCode=" + collectTypeCode + ", collectName='"
				+ collectName + '\'' + ", serialNumber='" + serialNumber + '\'' + ", releaseYear='" + releaseYear + '\''
				+ ", releaseUnit='" + releaseUnit + '\'' + ", faceValue=" + faceValue + ", crownNumber='" + crownNumber
				+ '\'' + ", watermark='" + watermark + '\'' + ", texture='" + texture + '\'' + ", specification="
				+ specification + ", printing='" + printing + '\'' + ", collectEvaluationCode='" + collectEvaluationCode
				+ '\'' + ", ratingScore=" + ratingScore + ", isTrue='" + isTrue + '\'' + ", evaluationNumber="
				+ evaluationNumber + ", evaluationDivision='" + evaluationDivision + '\'' + ", remark='" + remark + '\''
				+ ", imgs=" + imgs + '}';
	}

	public static EvaluationResult VoToPo(EvaluationResult evaluationResult, EvaluationResultVo evaluationResultVo, String userName) {
		evaluationResult.setAcceptance(evaluationResultVo.getAcceptance());
		if (StringUtils.isEmpty(evaluationResultVo.getId())) {
			evaluationResult.setCollectEvaluationCode(evaluationResultVo.getCollectEvaluationCode());
		}
		evaluationResult.setCollectName(evaluationResultVo.getCollectName());
		evaluationResult.setCollectTypeCode(evaluationResultVo.getCollectTypeCode().toString());
		evaluationResult.setCrownNumber(evaluationResultVo.getCrownNumber());
		evaluationResult.setEvaluationDivision(evaluationResultVo.getEvaluationDivision());
		evaluationResult.setEvaluationNumber(evaluationResultVo.getEvaluationNumber());
		//整理部保存
		if (evaluationResultVo.getOperationType() == 0) {
			evaluationResult.setEvaluationTypeCode("evaluation_hold");
			evaluationResult.setTrimBy(userName);
		}
		//整理部提交
		if (evaluationResultVo.getOperationType() == 1) {
			if(evaluationResult.getFirstEvaluation() == 1){
				evaluationResult.setEvaluationTypeCode("evaluation_audit");
				evaluationResult.setTrimBy(userName);
			}else{
			evaluationResult.setEvaluationTypeCode("evaluation_in");
			evaluationResult.setTrimBy(userName);
			}
		}
		//鉴评部提交审核
		if (evaluationResultVo.getOperationType() == 2) {
			if ("1".equals(evaluationResultVo.getIsTrue())
					|| !StringUtils.isEmpty(evaluationResultVo.getRatingScore())) {
				evaluationResult.setEvaluationTypeCode("evaluation_audit");
				evaluationResult.setIsTrue("真品");
			}else{
				evaluationResult.setEvaluationTypeCode("evaluation_audit");
				evaluationResult.setIsTrue("存疑");
			}
			evaluationResult.setEvaluationBy(userName);
		}
		//鉴评部保存，特殊管理员修改
		if (evaluationResultVo.getOperationType() == 3) {
			if ("1".equals(evaluationResultVo.getIsTrue())
					|| !StringUtils.isEmpty(evaluationResultVo.getRatingScore())) {
				evaluationResult.setIsTrue("真品");
			}else{
				evaluationResult.setIsTrue("存疑");
			}
			evaluationResult.setEvaluationBy(userName);
		}
		evaluationResult.setFaceValue(evaluationResultVo.getFaceValue());
		evaluationResult.setFactorCode(evaluationResultVo.getFactorCode());
		evaluationResult.setPrinting(evaluationResultVo.getPrinting());
		evaluationResult.setRatingScore(evaluationResultVo.getRatingScore());
		evaluationResult.setReleaseUnit(evaluationResultVo.getReleaseUnit());
		evaluationResult.setReleaseYear(evaluationResultVo.getReleaseYear());
		evaluationResult.setRemark(evaluationResultVo.getRemark());
		evaluationResult.setSerialNumber(evaluationResultVo.getSerialNumber());
		evaluationResult.setSpecification(evaluationResultVo.getSpecification());
		evaluationResult.setTexture(evaluationResultVo.getTexture());
		evaluationResult.setWatermark(evaluationResultVo.getWatermark());
		evaluationResult.setUpdateTime(DateUtil.getCurrentDate());
		return evaluationResult;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}

	public List<EvaluationImgVo> getImgs() {
		return imgs;
	}

	public void setImgs(List<EvaluationImgVo> imgs) {
		this.imgs = imgs;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}

}