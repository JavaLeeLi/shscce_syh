package com.visionet.syh_mall.vo;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class RoleVo {
	private String roleID;//角色id
	@NotEmpty
	private String roleName;//角色名
	private List<String> roleModules;//角色权限集合
	
	
	
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public List<String> getRoleModules() {
		return roleModules;
	}
	public void setRoleModules(List<String> roleModules) {
		this.roleModules = roleModules;
	}
	
	
}
