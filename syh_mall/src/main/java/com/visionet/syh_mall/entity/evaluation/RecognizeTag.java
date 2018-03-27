package com.visionet.syh_mall.entity.evaluation;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;
/**
 * 鉴评标签
 * @author mulongfei
 * @date 2017年11月7日上午11:42:31
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tbl_recognize_tag")
public class RecognizeTag extends IdEntity{
	private String tagTemplatFileId;//鉴评图片id
	private String qrCode;//二维码信息
	private String assessorInstitution;//鉴评机构
	private Integer isPrint = 0;//是否打印
	private Date createTime = DateUtil.getCurrentDate();
	private Date updateTime;
	private String goodsId;//关联商品id
	private Integer isDelete = 0;
	public String getTagTemplatFileId() {
		return tagTemplatFileId;
	}
	public void setTagTemplatFileId(String tagTemplatFileId) {
		this.tagTemplatFileId = tagTemplatFileId;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getAssessorInstitution() {
		return assessorInstitution;
	}
	public void setAssessorInstitution(String assessorInstitution) {
		this.assessorInstitution = assessorInstitution;
	}
	public Integer getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
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
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}
