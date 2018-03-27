package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;

/**
 * @Author DM
 * @version ：2017年8月16日上午11:37:30 首页banner实体类
 */
@Entity
@Table(name = "tbl_banner")
public class Banner extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String bannerName;// 活动名称
	private String bannerShopId;//
	private String linkHref;// 轮播图片点击跳转链接
	private String imageFileId;// 轮播图片文件ID
	private Integer bannerSort;// banner项顺序
	private Date updateTime = DateUtil.getCurrentDate();
	private Date createTime = DateUtil.getCurrentDate();
	private int isDeleted = 0;// 是否删除
	private Integer versionNo;

	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public String getBannerShopId() {
		return bannerShopId;
	}

	public void setBannerShopId(String bannerShopId) {
		this.bannerShopId = bannerShopId;
	}

	public String getLinkHref() {
		return linkHref;
	}

	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public Integer getBannerSort() {
		return bannerSort;
	}

	public void setBannerSort(Integer bannerSort) {
		this.bannerSort = bannerSort;
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

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
