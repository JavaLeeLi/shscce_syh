package com.visionet.syh_mall.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visionet.syh_mall.entity.admin.AdminUser;

/**
 * 管理员vo
 * @author xiaofb
 * @time 2017年9月20日
 */
public class AdminUserVo {
	private String adminUserID;//管理员id
	private String adminLoginName;//管理员登录名
	private Date adminCreateTime;//创建时间
	private String adminRoleID;//角色id
	private String adminRoleName;//角色名
	private String adminLoginPwd;//管理员密码
	public String getAdminLoginPwd() {
		return adminLoginPwd;
	}
	public void setAdminLoginPwd(String adminLoginPwd) {
		this.adminLoginPwd = adminLoginPwd;
	}
	public String getAdminUserID() {
		return adminUserID;
	}
	public void setAdminUserID(String adminUserID) {
		this.adminUserID = adminUserID;
	}
	public String getAdminLoginName() {
		return adminLoginName;
	}
	public void setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName;
	}
	
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getAdminCreateTime() {
		return adminCreateTime;
	}
	public void setAdminCreateTime(Date adminCreateTime) {
		this.adminCreateTime = adminCreateTime;
	}
	public String getAdminRoleID() {
		return adminRoleID;
	}
	public void setAdminRoleID(String adminRoleID) {
		this.adminRoleID = adminRoleID;
	}
	public String getAdminRoleName() {
		return adminRoleName;
	}
	public void setAdminRoleName(String adminRoleName) {
		this.adminRoleName = adminRoleName;
	}
	
	/**
	 * vo to dto
	 * @param vo
	 * @return
	 */
	public AdminUser convertDto(AdminUserVo vo){
		AdminUser dto = new AdminUser();
		dto.setId(vo.getAdminUserID());
		dto.setLoginName(vo.getAdminLoginName());
		dto.setPassword(vo.getAdminLoginPwd());
		dto.setRoleId(vo.getAdminRoleID());
		return dto;
	}
	
	/**
	 * dto to  vo
	 * @param dto
	 * @return
	 */
	public void convertVo(AdminUserVo vo,AdminUser dto){
		vo.setAdminUserID(dto.getId());
		vo.setAdminLoginName(dto.getLoginName());
		vo.setAdminCreateTime(dto.getCreateTime());
		vo.setAdminRoleID(dto.getRoleId());
	}
	
	@Override
	public String toString() {
		return "AdminUserVo [adminUserID=" + adminUserID + ", adminLoginName="
				+ adminLoginName + ", adminCreateTime=" + adminCreateTime
				+ ", adminRoleID=" + adminRoleID + ", adminRoleName="
				+ adminRoleName + "]";
	}
	
}
