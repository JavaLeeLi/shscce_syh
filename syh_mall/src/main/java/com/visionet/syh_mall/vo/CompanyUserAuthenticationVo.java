package com.visionet.syh_mall.vo;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.UserAuthentication;
import org.hibernate.validator.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: CompanyUserAuthenticationVo
 * @Description:企业会员认证
 * @author chenghongzhan
 * @date 2018年1月31日 下午12:07:13
 *
 */
public class CompanyUserAuthenticationVo {


	@NotBlank(message = "法人用户ID不能为空")
	private String bizUserId;// 法人用户ID
	@NotBlank(message = "公司名称不能为空")
	private String companyName;// 公司名称
	@NotBlank(message = "营业执照号不能为空")
	private String businessLicense;// 营业执照号
	@NotBlank(message = "组织机构代码不能为空")
	private String organizationCode;// 组织机构代码
	@NotBlank(message = "法人姓名不能为空")
	private String legalName;// 法人姓名
	@NotBlank(message = "法人证件类型不能为空")
	private Integer identityType;// 法人证件类型
	@NotBlank(message = "法人证件号码不能为空")
	private String legalIds;// 法人证件号码
	@NotBlank(message = "法人手机号不能为空")
	private String legalPhone;// 法人手机号
	@NotBlank(message = "企业对公账户不能为空")
	private String accountNo;// 企业对公账户
	private String parentBankName;// 开户银行名称
	@NotBlank(message = "用户所属省份不能为空")
	private String province;// 用户所属省份
	@NotBlank(message = "用户所属城市不能为空")
	private String city;// 用户所属城市
	@NotBlank(message = "用户所属区县不能为空")
	private String area;// 用户所属区县
	@NotBlank(message = "用户所属街道不能为空")
	private String street;// 用户所属街道
	@NotBlank(message = "法人证件正面照片文件ID不能为空")
	private String voucherFImgId;// 法人证件正面照片文件ID
	@NotBlank(message = "法人证件正面照片文件URL不能为空")
	private String voucherFImgUrl;
	@NotBlank(message = "法人证件反面照片文件ID不能为空")
	private String voucherBImgId;// 法人证件背面照片文件ID
	@NotBlank(message = "法人证件反面照片文件URL不能为空")
	private String voucherBImgUrl;
	@NotBlank(message = "商户会员营业执照照片文件ID不能为空")
	private String mbrLicenseImgId;// 商户会员营业执照照片文件ID
	@NotBlank(message = "商户会员营业执照照片文件URL不能为空")
	private String mbrLicenseImgUrl;
	@NotBlank(message = "银行卡编码不能为空")
	private String ddKeyCode;

	private int reviewResult;// 会员认证审核结果(0：拒绝,1：通过)
	private String refusalReason;// 拒绝理由
    private static final Logger logger = LoggerFactory.getLogger(CompanyUserAuthenticationVo.class);
	
	public String getDdKeyCode() {
		return ddKeyCode;
	}

	public void setDdKeyCode(String ddKeyCode) {
		this.ddKeyCode = ddKeyCode;
	}

	public String getRefusalReason() {
		return refusalReason;
	}

	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}

	public int getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getVoucherFImgId() {
		return voucherFImgId;
	}

	public void setVoucherFImgId(String voucherFImgId) {
		this.voucherFImgId = voucherFImgId;
	}

	public String getVoucherFImgUrl() {
		return voucherFImgUrl;
	}

	public void setVoucherFImgUrl(String voucherFImgUrl) {
		this.voucherFImgUrl = voucherFImgUrl;
	}

	public String getVoucherBImgId() {
		return voucherBImgId;
	}

	public void setVoucherBImgId(String voucherBImgId) {
		this.voucherBImgId = voucherBImgId;
	}

	public String getVoucherBImgUrl() {
		return voucherBImgUrl;
	}

	public void setVoucherBImgUrl(String voucherBImgUrl) {
		this.voucherBImgUrl = voucherBImgUrl;
	}

	public String getMbrLicenseImgId() {
		return mbrLicenseImgId;
	}

	public void setMbrLicenseImgId(String mbrLicenseImgId) {
		this.mbrLicenseImgId = mbrLicenseImgId;
	}

	public String getMbrLicenseImgUrl() {
		return mbrLicenseImgUrl;
	}

	public void setMbrLicenseImgUrl(String mbrLicenseImgUrl) {
		this.mbrLicenseImgUrl = mbrLicenseImgUrl;
	}

	public String getBizUserId() {
		return bizUserId;
	}

	public void setBizUserId(String bizUserId) {
		this.bizUserId = bizUserId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
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

	public UserAuthentication converPo(CompanyUserAuthenticationVo qo, UserAuthentication userAuthentication) {
		userAuthentication.setUserId(qo.getBizUserId());
		userAuthentication.setUserRealName(qo.getLegalName());
		userAuthentication.setIdCode(qo.getLegalIds());
		userAuthentication.setUserProvince(qo.getProvince());
		userAuthentication.setUserCity(qo.getCity());
		userAuthentication.setUserArea(qo.getArea());
		userAuthentication.setUserStreet(qo.getStreet());
		userAuthentication.setId_f_fileId(qo.getVoucherFImgId());
		userAuthentication.setId_b_fileId(qo.getVoucherBImgId());
		userAuthentication.setOrganizationCode(qo.getOrganizationCode());
		userAuthentication.setLicenseNo(qo.getBusinessLicense());
		userAuthentication.setLicenseCertFileId(qo.getMbrLicenseImgId());
		userAuthentication.setCreateTime(DateUtil.getCurrentDate());
		userAuthentication.setCompanyName(qo.getCompanyName());
		userAuthentication.setIdentityType(qo.getIdentityType());
		userAuthentication.setLegelPhone(qo.getLegalPhone());

		userAuthentication.setAccountNo(qo.getAccountNo());
		userAuthentication.setBankCode(qo.getDdKeyCode());
		userAuthentication.setStatus(0);
		userAuthentication.setIsDeleted(0);
		return userAuthentication;
	}

	@Override
	public String toString() {
		return "CompanyUserAuthenticationVo{" +
				"keyMappingDao=" +
				", bizUserId='" + bizUserId + '\'' +
				", companyName='" + companyName + '\'' +
				", businessLicense='" + businessLicense + '\'' +
				", organizationCode='" + organizationCode + '\'' +
				", legalName='" + legalName + '\'' +
				", identityType=" + identityType +
				", legalIds='" + legalIds + '\'' +
				", legalPhone='" + legalPhone + '\'' +
				", accountNo='" + accountNo + '\'' +
				", parentBankName='" + parentBankName + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", area='" + area + '\'' +
				", street='" + street + '\'' +
				", voucherFImgId='" + voucherFImgId + '\'' +
				", voucherFImgUrl='" + voucherFImgUrl + '\'' +
				", voucherBImgId='" + voucherBImgId + '\'' +
				", voucherBImgUrl='" + voucherBImgUrl + '\'' +
				", mbrLicenseImgId='" + mbrLicenseImgId + '\'' +
				", mbrLicenseImgUrl='" + mbrLicenseImgUrl + '\'' +
				", ddKeyCode='" + ddKeyCode + '\'' +
				", reviewResult=" + reviewResult +
				", refusalReason='" + refusalReason + '\'' +
				'}';
	}
}
