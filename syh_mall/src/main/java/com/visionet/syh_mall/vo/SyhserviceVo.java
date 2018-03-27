package com.visionet.syh_mall.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.syhservice.Syhservice;

public class SyhserviceVo {

	private String serviceContentID;
	@NotBlank(message = "服务类型编码不能为空")
	private String serviceTypeCode;
	@NotNull(message = "服务介绍类型不能为空")
	private Integer serviceDescType;
	private String serviceDescTitle;
	@NotBlank(message = "服务介绍内容不能为空")
	private String serviceDescContent;
	@NotBlank(message = "管理员用户ID不能为空")
	private String adminUserID;

	public String getServiceContentID() {
		return serviceContentID;
	}

	public void setServiceContentID(String serviceContentID) {
		this.serviceContentID = serviceContentID;
	}

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public Integer getServiceDescType() {
		return serviceDescType;
	}

	public void setServiceDescType(Integer serviceDescType) {
		this.serviceDescType = serviceDescType;
	}

	public String getServiceDescTitle() {
		return serviceDescTitle;
	}

	public void setServiceDescTitle(String serviceDescTitle) {
		this.serviceDescTitle = serviceDescTitle;
	}

	public String getServiceDescContent() {
		return serviceDescContent;
	}

	public void setServiceDescContent(String serviceDescContent) {
		this.serviceDescContent = serviceDescContent;
	}

	public String getAdminUserID() {
		return adminUserID;
	}

	public void setAdminUserID(String adminUserID) {
		this.adminUserID = adminUserID;
	}

	/**
	 * @Title: convertPo @Description: 服务内容的Vo转Po @param @param
	 *         syhserviceVo @param @param syhservice @param @return 设定文件 @return
	 *         Syhservice 返回类型 @throws
	 */
	public Syhservice convertPo(SyhserviceVo syhserviceVo, Syhservice syhservice) {
		syhservice.setId(syhserviceVo.getServiceContentID());
		syhservice.setServiceDescContent(syhserviceVo.getServiceDescContent());
		syhservice.setServiceDescTitle(syhserviceVo.getServiceDescTitle());
		syhservice.setServiceDescType(syhserviceVo.getServiceDescType());
		syhservice.setServiceTypeCode(syhserviceVo.getServiceTypeCode());
		return syhservice;
	}

	@Override
	public String toString() {
		return "SyhserviceVo [serviceContentID=" + serviceContentID + ", serviceTypeCode=" + serviceTypeCode
				+ ", serviceDescType=" + serviceDescType + ", serviceDescTitle=" + serviceDescTitle
				+ ", serviceDescContent=" + serviceDescContent + ", adminUserID=" + adminUserID + "]";
	}

}
