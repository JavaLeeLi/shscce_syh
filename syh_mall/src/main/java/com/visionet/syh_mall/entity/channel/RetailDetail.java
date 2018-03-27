package com.visionet.syh_mall.entity.channel;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: RetailDetail
 * @Description: 分销结算流水表
 * @author chenghongzhan
 * @date 2017年11月6日 上午11:06:26
 *
 */
@Entity
@Table(name = "tbl_retail_detail")
public class RetailDetail extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String retailObjId;// 分销对象ID（会员分销时为订单ID，商品分销时为商品ID）
	private Integer retailType;// 分销业务类型（0:会员分销，1：商品分销）
	private BigDecimal buyRetailAmt = new BigDecimal(0);// 买家分销交易总金额（会员分销时为订单金额，商品分销时为商品销售额）
	private BigDecimal sellRetailAmt = new BigDecimal(0);// 卖家分销交易总金额（会员分销时为订单金额，商品分销时为商品销售额）
	private BigDecimal commissionAmt = new BigDecimal(0);// 分销结算金额
	private BigDecimal commissionRate = new BigDecimal(0);// 分销返佣佣金比率
	private BigDecimal retailUserRate = new BigDecimal(0);// 用户享有返佣比率
	private String retailUserId;// 分销用户ID
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private int isDeleted = 0;// 是否删除
	private String settlementStatus = "waitNotified";// 结算状态
	private String hierarchy;
	private String sellUser;//卖家ID

	public String getSellUser() {
		return sellUser;
	}

	public void setSellUser(String sellUser) {
		this.sellUser = sellUser;
	}

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getRetailObjId() {
		return retailObjId;
	}

	public void setRetailObjId(String retailObjId) {
		this.retailObjId = retailObjId;
	}

	public Integer getRetailType() {
		return retailType;
	}

	public void setRetailType(Integer retailType) {
		this.retailType = retailType;
	}


	public BigDecimal getCommissionAmt() {
		return commissionAmt;
	}

	public void setCommissionAmt(BigDecimal commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	public BigDecimal getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	public BigDecimal getRetailUserRate() {
		return retailUserRate;
	}

	public void setRetailUserRate(BigDecimal retailUserRate) {
		this.retailUserRate = retailUserRate;
	}

	public String getRetailUserId() {
		return retailUserId;
	}

	public void setRetailUserId(String retailUserId) {
		this.retailUserId = retailUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public BigDecimal getBuyRetailAmt() {
		return buyRetailAmt;
	}

	public void setBuyRetailAmt(BigDecimal buyRetailAmt) {
		this.buyRetailAmt = buyRetailAmt;
	}

	public BigDecimal getSellRetailAmt() {
		return sellRetailAmt;
	}

	public void setSellRetailAmt(BigDecimal sellRetailAmt) {
		this.sellRetailAmt = sellRetailAmt;
	}

}
