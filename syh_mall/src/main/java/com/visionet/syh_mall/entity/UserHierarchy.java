package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 用户层级关系实体类
 * 
 * @author mulongfei
 * @date 2017年8月24日上午11:41:49
 */
@Entity
@Table(name = "tbl_user_hierarchy")
public class UserHierarchy extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String userId;// 用户id
	private String parentUserId;// 父用户id
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private Integer isDeleted;// 是否删除

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
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
