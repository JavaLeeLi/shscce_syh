package com.visionet.syh_mall.entity.userAttention;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 用户关注表实体类
 * @author mulongfei
 * @date 2017年9月4日上午11:44:17
 */
@Entity
@Table(name="tbl_user_attention")
public class UserAttention extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String userId;//用户id
	private String concernedUserId;//被关注用户id
	private Date createTime = new Date();//创建时间
	private Date updateTime;//更新时间
	private Integer isDeleted = 0;//是否删除
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getConcernedUserId() {
		return concernedUserId;
	}
	public void setConcernedUserId(String concernedUserId) {
		this.concernedUserId = concernedUserId;
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
