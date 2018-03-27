package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_goods_hierarchy")
public class GoodsHierarchy extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String sharingUserId;
	private Date createTime = new Date();
	private Date updateTime = new Date();
	private String goodsID;
	private Integer isDeleted = 0;
	private String sharingCode;

	public String getSharingCode() {
		return sharingCode;
	}

	public void setSharingCode(String sharingCode) {
		this.sharingCode = sharingCode;
	}

	public String getSharingUserId() {
		return sharingUserId;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public void setSharingUserId(String sharingUserId) {
		this.sharingUserId = sharingUserId;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
