package com.visionet.syh_mall.vo.shop;

import java.util.Date;


import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.shop.Shop;

/**
 * @ClassName: ShopVo
 * @Description: 店铺的修改Vo
 * @author chenghongzhan
 * @date 2017年9月28日 下午2:30:45
 *
 */
public class ShopVo {
	@NotBlank(message = "店铺ID不能为空")
	private String shopID;
	@NotBlank(message = "用户ID不能为空")
	private String userID;
	@NotBlank(message = "店主用户真实姓名不能为空")
	private String userRealName;
	@NotBlank(message = "店主用户所处省份不能为空")
	private String userProvince;
	@NotBlank(message = "店主用户所处城市不能为空")
	private String userCity;
	@NotBlank(message = "店主用户所处区县不能为空")
	private String userArea;
	@NotBlank(message = "店主用户所处街道不能为空")
	private String userStreet;//用户街道
	private String userAddress;//用户地址
	private String userIDFImgID;//身份证正面
	private String userIDBImgID;//身份证反面
	private String licenseNO;//营业执照
	private String organizationCode;//组织机构码
	private String licenseCertImgID;//持有人图片
	@NotBlank(message = "店铺名称不能为空")
	private String shopName;//店铺名称
	private Integer shopLevel;//店铺等级
	private Date validityTime;//创建时间
	private String shopAddress;//店铺地址

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserIDFImgID() {
		return userIDFImgID;
	}

	public void setUserIDFImgID(String userIDFImgID) {
		this.userIDFImgID = userIDFImgID;
	}

	public String getUserIDBImgID() {
		return userIDBImgID;
	}

	public void setUserIDBImgID(String userIDBImgID) {
		this.userIDBImgID = userIDBImgID;
	}

	public String getLicenseNO() {
		return licenseNO;
	}

	public void setLicenseNO(String licenseNO) {
		this.licenseNO = licenseNO;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getLicenseCertImgID() {
		return licenseCertImgID;
	}

	public void setLicenseCertImgID(String licenseCertImgID) {
		this.licenseCertImgID = licenseCertImgID;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(Integer shopLevel) {
		this.shopLevel = shopLevel;
	}

	public Date getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(Date validityTime) {
		this.validityTime = validityTime;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	/**
	 * @Title: ShopconvertVo @Description: 店铺的Vo转Po @param @param
	 *         shopVo @param @return 设定文件 @return Shop 返回类型 @throws
	 */
	public Shop shopConvertPo(ShopVo shopVo, Shop shop) {
		shop.setShopName(shopVo.getShopName());
		shop.setShopLevel(shopVo.getShopLevel());
		shop.setValidityTime(shopVo.getValidityTime());
		
		if (!StringUtils.isEmpty(shopVo.getShopAddress())) {
			shop.setShopAddress(shopVo.getShopAddress());
		}
		shop.setUpdateTime(DateUtil.getCurrentDate());
		return shop;
	}

	/**
	 * @Title: ShopconvertVo @Description: 认证信息的Vo转Po @param @param
	 *         shopVo @param @return 设定文件 @return Shop 返回类型 @throws
	 */
	public UserAuthentication userConvertPo(ShopVo shopVo, UserAuthentication authentication) {
		authentication.setUserRealName(shopVo.getUserRealName());
		authentication.setUserProvince(shopVo.getUserProvince());
		authentication.setUserCity(shopVo.getUserCity());
		authentication.setUserArea(shopVo.getUserArea());
		authentication.setUserStreet(shopVo.getUserStreet());
		if (!StringUtils.isEmpty(shopVo.getUserIDBImgID())) {
			authentication.setId_b_fileId(shopVo.getUserIDBImgID());
		}
		if (!StringUtils.isEmpty(shopVo.getUserIDFImgID())) {
			authentication.setId_f_fileId(shopVo.getUserIDFImgID());
		}
		if (!StringUtils.isEmpty(shopVo.getLicenseNO())) {
			authentication.setLicenseNo(shopVo.getLicenseNO());
		}
		if (!StringUtils.isEmpty(shopVo.getOrganizationCode())) {
			authentication.setOrganizationCode(shopVo.getOrganizationCode());
		}
		if (!StringUtils.isEmpty(shopVo.getLicenseCertImgID())) {
			authentication.setLicenseCertFileId(shopVo.getLicenseCertImgID());
		}
		authentication.setUpdateTime(DateUtil.getCurrentDate());
		return authentication;
	}

	@Override
	public String toString() {
		return "ShopVo [shopID=" + shopID + ", userID=" + userID + ", userRealName=" + userRealName + ", userProvince="
				+ userProvince + ", userCity=" + userCity + ", userArea=" + userArea + ", userStreet=" + userStreet
				+ ", userAddress=" + userAddress + ", userIDFImgID=" + userIDFImgID + ", userIDBImgID=" + userIDBImgID
				+ ", licenseNO=" + licenseNO + ", organizationCode=" + organizationCode + ", licenseCertImgID="
				+ licenseCertImgID + ", shopName=" + shopName + ", shopLevel=" + shopLevel + ", validityTime="
				+ validityTime + ", shopAddress=" + shopAddress + "]";
	}

}
