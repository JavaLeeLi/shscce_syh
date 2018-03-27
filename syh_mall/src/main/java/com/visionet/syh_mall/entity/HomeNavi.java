package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;

/**
 * @Author DM
 * @version ：2017年8月21日下午1:52:08 快捷导航实体类
 */
@Entity
@Table(name = "tbl_home_navi")
public class HomeNavi extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String naviItemName;// 快捷导航项名称
	private String iconFileId;// 快捷导航项图标图片文件ID
	private String naviItemUrl;// 快捷导航目标url链接
	private int naviItemSort;// 快捷导航项顺序
	private Date updateTime = DateUtil.getCurrentDate();
	private Date createTime = DateUtil.getCurrentDate();
	private String createBy;// 创建者id
	private String updateBy;// 创建者id
	private int isDeleted = 0;// 是否删除

	public String getNaviItemName() {
		return naviItemName;
	}

	public void setNaviItemName(String naviItemName) {
		this.naviItemName = naviItemName;
	}

	public String getIconFileId() {
		return iconFileId;
	}

	public void setIconFileId(String iconFileId) {
		this.iconFileId = iconFileId;
	}

	public String getNaviItemUrl() {
		return naviItemUrl;
	}

	public void setNaviItemUrl(String naviItemUrl) {
		this.naviItemUrl = naviItemUrl;
	}

	public int getNaviItemSort() {
		return naviItemSort;
	}

	public void setNaviItemSort(int naviItemSort) {
		this.naviItemSort = naviItemSort;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
