package com.visionet.syh_mall.entity.shop;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 店铺装修设置
 * 
 * @author xiaofb
 * @time 2017年9月12日
 */
@Entity
@Table(name = "tbl_shop_setting")
public class ShopSetting extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String logoImgFileId; // 店铺logo图片文件ID
	private String pcImgFileId; // 店铺pc版背景图片文件ID
	private String mobileImgFileId; // 店铺手机版背景图片文件ID
	private Date updateTime; // 修改时间
	private Date createTime; // 创建时间

	public String getLogoImgFileId() {
		return logoImgFileId;
	}

	public void setLogoImgFileId(String logoImgFileId) {
		this.logoImgFileId = logoImgFileId;
	}

	public String getPcImgFileId() {
		return pcImgFileId;
	}

	public void setPcImgFileId(String pcImgFileId) {
		this.pcImgFileId = pcImgFileId;
	}

	public String getMobileImgFileId() {
		return mobileImgFileId;
	}

	public void setMobileImgFileId(String mobileImgFileId) {
		this.mobileImgFileId = mobileImgFileId;
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
