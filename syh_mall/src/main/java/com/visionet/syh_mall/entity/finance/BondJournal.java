package com.visionet.syh_mall.entity.finance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 店铺保证金流水实体
 * @author mulongfei
 * @date 2017年10月20日下午8:18:55
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tbl_bond_journal")
public class BondJournal extends IdEntity{
	private String shopId;
	private BigDecimal journalAmt;
	private Integer journalType;
	private String journalDesc;
	private Date createTime;
	private Date updateTime;
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public BigDecimal getJournalAmt() {
		return journalAmt;
	}
	public void setJournalAmt(BigDecimal journalAmt) {
		this.journalAmt = journalAmt;
	}
	public Integer getJournalType() {
		return journalType;
	}
	public void setJournalType(Integer journalType) {
		this.journalType = journalType;
	}
	public String getJournalDesc() {
		return journalDesc;
	}
	public void setJournalDesc(String journalDesc) {
		this.journalDesc = journalDesc;
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
