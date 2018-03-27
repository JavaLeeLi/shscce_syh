package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户标签关联表
 * 
 * @author chenghongzhan
 * @version 2017年8月24日 下午4:44:46
 *
 */
@Entity
@Table(name = "tbl_user_tag_link")
public class UserTagLink extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String userId;// 用户ID
	private String tagId;// 标签ID
	private Date updateTime = new Date();// 更新时间
	private Date createTime = new Date();// 创建时间
	private Integer isDeleted = 0;// 是否删除

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
}
