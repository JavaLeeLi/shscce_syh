package com.visionet.syh_mall.entity.shop;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年8月16日下午3:13:57 店铺实体类
 */
@Entity
@Table(name = "tbl_shop")
public class Shop extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String shopName;// 店铺名称
	private String userId;// 创建人id
	private String userName;// 店主姓名
	private int shopLevel;// 店铺等级
	private String shopAddress;// 店铺地址
	private Date validityTime;// 有效期
	private String settingId;// 店铺设置id
	private String statusCode;// 店铺状态编码
	private int shopIsFrozen;// 店铺是否冻结
	private int shopIsOfficial;// 是否官方店铺
	private int isDeleted = 0;// 是否删除
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Date createTime = DateUtil.getCurrentDate();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(int shopLevel) {
		this.shopLevel = shopLevel;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getSettingId() {
		return settingId;
	}

	public void setSettingId(String settingId) {
		this.settingId = settingId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(Date validityTime) {
		this.validityTime = validityTime;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getShopIsFrozen() {
		return shopIsFrozen;
	}

	public void setShopIsFrozen(int shopIsFrozen) {
		this.shopIsFrozen = shopIsFrozen;
	}

	public int getShopIsOfficial() {
		return shopIsOfficial;
	}

	public void setShopIsOfficial(int shopIsOfficial) {
		this.shopIsOfficial = shopIsOfficial;
	}
}
