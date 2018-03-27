package com.visionet.syh_mall.entity;

public class CompanyAuthentification {
	private String companyName;
	private String companyAddress;
	private String businessLicense;
	private String organizationCode;
	private String telephone;
	private String legalName;
	private String legalIds;
	private Integer identityType;
	private String legalPhone;
	private String accountNo;
	private String parentBankName;
	private String bankCityNo;
	private String bankName;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getLegalIds() {
		return legalIds;
	}
	public void setLegalIds(String legalIds) {
		this.legalIds = legalIds;
	}
	public String getLegalPhone() {
		return legalPhone;
	}
	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
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
	public String getBankCityNo() {
		return bankCityNo;
	}
	public void setBankCityNo(String bankCityNo) {
		this.bankCityNo = bankCityNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Override
	public String toString() {
		return "CompanyAuthentification [companyName=" + companyName + ", companyAddress=" + companyAddress
				+ ", businessLicense=" + businessLicense + ", organizationCode=" + organizationCode + ", telephone="
				+ telephone + ", legalName=" + legalName + ", identityType=" + identityType + ", legalIds=" + legalIds
				+ ", legalPhone=" + legalPhone + ", accountNo=" + accountNo + ", parentBankName=" + parentBankName
				+ ", bankCityNo=" + bankCityNo + ", bankName=" + bankName + "]";
	}
	public Integer getIdentityType() {
		return identityType;
	}
	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}
}
