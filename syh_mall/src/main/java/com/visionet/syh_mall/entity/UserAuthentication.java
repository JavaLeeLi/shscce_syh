package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;

/**
 * 
 * 用户认证信息实体类
 * 
 * @author mulongfei
 * @date 2017年8月25日上午10:35:01
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tbl_user_authentication")
public class UserAuthentication extends IdEntity {
	private static long serialVersionUID = 1L;
	private String userId;// 用户id
	private String userRealName;// 用户/法人真实姓名
	private String userProvince;// 用户/商家所处省份
	private String userCity;// 用户/商家所处城市
	private String userArea;// 用户/商家所处县区
	private String userStreet;// 用户/商家所处街道
	private String idCode;// 身份证号
	private String id_f_fileId;// 用户/法人身份证正面照片文件ID
	private String id_b_fileId;// 用户/法人身份证背面照片文件ID
	private String licenseNo;// 商家营业执照
	private String organizationCode;// 商家组织机构代码
	private String licenseCertFileId;// 商家营业执照照片文件ID
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Integer status;// 0：未审核；1：已审核；2：已拒绝
	private String refusalReason;// 拒绝理由
	private Integer isDeleted;// 是否删除
	private String companyName;// 公司名称
	private Integer identityType;// 证件类型
	private String legelPhone;// 法人手机号
	private String accountNo;// 企业对公账号
	private String parentBankName;// 开户银行名称
	private String bankCode;//对公帐户机构代码
	private Integer cardType;//银行卡类型

	
	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getLegelPhone() {
		return legelPhone;
	}

	public void setLegelPhone(String legelPhone) {
		this.legelPhone = legelPhone;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getParentBankName() {
		return parentBankName;
	}

	public void setParentBankName(String parentBankName) {
		this.parentBankName = parentBankName;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}


	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserProvince() {
		return userProvince;
	}

	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getUserStreet() {
		return userStreet;
	}

	public void setUserStreet(String userStreet) {
		this.userStreet = userStreet;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getLicenseCertFileId() {
		return licenseCertFileId;
	}

	public void setLicenseCertFileId(String licenseCertFileId) {
		this.licenseCertFileId = licenseCertFileId;
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

	public String getId_b_fileId() {
		return id_b_fileId;
	}

	public void setId_b_fileId(String id_b_fileId) {
		this.id_b_fileId = id_b_fileId;
	}

	public String getId_f_fileId() {
		return id_f_fileId;
	}

	public void setId_f_fileId(String id_f_fileId) {
		this.id_f_fileId = id_f_fileId;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getRefusalReason() {
		return refusalReason;
	}

	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}

}
