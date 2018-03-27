package com.visionet.syh_mall.entity.marketing;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

@Entity
@Table(name = "tbl_group_detail")
public class GroupDetail extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String groupId;
	private String ownerId;
	private Integer isGroupSuccess = 0;
	private Date createTime;
	private Date updateTime;
	private int isDeleted = 0;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getIsGroupSuccess() {
		return isGroupSuccess;
	}

	public void setIsGroupSuccess(Integer isGroupSuccess) {
		this.isGroupSuccess = isGroupSuccess;
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

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

}
