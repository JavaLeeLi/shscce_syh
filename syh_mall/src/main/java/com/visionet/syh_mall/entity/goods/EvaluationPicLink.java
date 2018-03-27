package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="tbl_evaluation_pic_link")
public class EvaluationPicLink extends IdEntity{
	private String evaluationId;
	private String maxImgId;
	private String midImgId;
	private String minImgId;
	private Integer isDeleted = 0;
	private Date createTime  = DateUtil.getCurrentDate();
	public String getEvaluationId() {
		return evaluationId;
	}
	public void setEvaluationId(String evaluationId) {
		this.evaluationId = evaluationId;
	}
	public String getMaxImgId() {
		return maxImgId;
	}
	public void setMaxImgId(String maxImgId) {
		this.maxImgId = maxImgId;
	}
	public String getMidImgId() {
		return midImgId;
	}
	public void setMidImgId(String midImgId) {
		this.midImgId = midImgId;
	}
	public String getMinImgId() {
		return minImgId;
	}
	public void setMinImgId(String minImgId) {
		this.minImgId = minImgId;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
