package com.visionet.syh_mall.entity.admin;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;


@Entity
@Table(name = "tbl_role")
public class Role extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String roleName;
	private String roleDesc;
	private Date createTime;
	private Date updateTime;
	private int isDeleted;
	
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
