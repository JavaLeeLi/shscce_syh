package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.visionet.syh_mall.common.utils.DateUtil;

/**
 * @Author DM
 * @version ：2017年8月15日下午3:42:06 公告记录表
 */
@Entity
@Table(name = "tbl_notice")
public class Notice extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String noticeTitle;// 公告标题
	private int noticeStatus;// 公告状态
	// @Type(type="text")
	private String noticeContent;// 公告内容
	private int noticeType;// 公告类型（0：系统公告，1：成交价公告）
	private String createBy;// 创建人id
	private String updateBy;// 修改人id
//	private Integer noticeSort;
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Date createTime = DateUtil.getCurrentDate();
	private int isDeleted = 0;// 是否删除

	/*public Integer getNoticeSort() {
		return noticeSort;
	}

	public void setNoticeSort(Integer noticeSort) {
		this.noticeSort = noticeSort;
	}*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public int getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(int noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	@Type(type = "text")
	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public int getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
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
}
