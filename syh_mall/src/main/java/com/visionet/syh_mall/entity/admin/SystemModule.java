package com.visionet.syh_mall.entity.admin;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;


@Entity
@Table(name = "tbl_system_module")
public class SystemModule extends IdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String moduleName;
	private String moduleCode;
	private String parentModuleId;
	private String moduleRoute;
	private Integer moduleSort;
	private String moduleIcon;
	private Date updateTime;
	private Date createTime;
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getParentModuleId() {
		return parentModuleId;
	}
	public int getModuleSort() {
		return moduleSort;
	}
	public void setModuleSort(int moduleSort) {
		this.moduleSort = moduleSort;
	}
	public void setModuleSort(Integer moduleSort) {
		this.moduleSort = moduleSort;
	}
	public String getModuleIcon() {
		return moduleIcon;
	}
	public void setModuleIcon(String moduleIcon) {
		this.moduleIcon = moduleIcon;
	}
	public void setParentModuleId(String parentModuleId) {
		this.parentModuleId = parentModuleId;
	}
	public String getModuleRoute() {
		return moduleRoute;
	}
	public void setModuleRoute(String moduleRoute) {
		this.moduleRoute = moduleRoute;
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
}
