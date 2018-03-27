package com.visionet.syh_mall.entity.channel;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

@Entity
@Table(name = "tbl_commission_tally")
public class CommissionTally extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String userId;// 分销会员账号
	private Integer retailMbrGrade;// 会员分销等级
	private Integer retailMbrLevel;// 会员分销层级
	private String retailGoodsId;// 分销商品ID
	private BigDecimal tradeAmt;// 分销交易金额
	private Date tallyStartTime;// 结算周期开始时间
	private Date tallyEndTime;// 结算周期结束时间
	private BigDecimal commissionAmt;// 应结算佣金金额
	private BigDecimal finalAmt;// 实结算佣金金额
	private String commissionStatusCode;// 结算状态编码
	private String refuseReason;// 结算拒绝原因
	private String reviewUserId;// 结算审核人ID
	private Date createTime = new Date();// 创建时间
	private Date updateTime = new Date();// 更新时间



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getRetailMbrGrade() {
		return retailMbrGrade;
	}

	public void setRetailMbrGrade(Integer retailMbrGrade) {
		this.retailMbrGrade = retailMbrGrade;
	}

	public Integer getRetailMbrLevel() {
		return retailMbrLevel;
	}

	public void setRetailMbrLevel(Integer retailMbrLevel) {
		this.retailMbrLevel = retailMbrLevel;
	}

	public String getRetailGoodsId() {
		return retailGoodsId;
	}

	public void setRetailGoodsId(String retailGoodsId) {
		this.retailGoodsId = retailGoodsId;
	}

	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public Date getTallyStartTime() {
		return tallyStartTime;
	}

	public void setTallyStartTime(Date tallyStartTime) {
		this.tallyStartTime = tallyStartTime;
	}

	public Date getTallyEndTime() {
		return tallyEndTime;
	}

	public void setTallyEndTime(Date tallyEndTime) {
		this.tallyEndTime = tallyEndTime;
	}

	public BigDecimal getCommissionAmt() {
		return commissionAmt;
	}

	public void setCommissionAmt(BigDecimal commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	public BigDecimal getFinalAmt() {
		return finalAmt;
	}

	public void setFinalAmt(BigDecimal finalAmt) {
		this.finalAmt = finalAmt;
	}

	public String getCommissionStatusCode() {
		return commissionStatusCode;
	}

	public void setCommissionStatusCode(String commissionStatusCode) {
		this.commissionStatusCode = commissionStatusCode;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(String reviewUserId) {
		this.reviewUserId = reviewUserId;
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

}
