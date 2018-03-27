package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年9月12日上午10:48:26 搜索历史类
 */
@Entity
@Table(name = "tbl_search_history")
public class SearchHistory extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String userId;//
	private String keywords;
	private String goodsId;
	private Date updateTime = new Date();
	private Integer isDeleted;// 是否删除
	protected Date createTime = new Date();
	private Integer sourchCount;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSourchCount() {
		return sourchCount;
	}

	public void setSourchCount(Integer sourchCount) {
		this.sourchCount = sourchCount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
