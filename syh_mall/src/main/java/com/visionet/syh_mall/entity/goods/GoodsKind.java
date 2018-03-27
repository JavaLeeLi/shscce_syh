package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年8月17日下午6:04:10 商品分类实体类
 */
@Entity
@Table(name = "tbl_goods_kinds")
public class GoodsKind extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String kindName;// 商品类别名称
	private String kindIconFileId;// 分类图标文件id
	private int kindSort; // 分类排序
	private int reviewIsAvoid; // 分类商品是否免审核（0:否，1：是）
	private String parentKindId;// 父分类id
	private String createBy;// 创建者id
	private String updateBy;// 创建者id
	private int isDeleted;// 是否删除
	protected Date createTime = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getKindIconFileId() {
		return kindIconFileId;
	}

	public void setKindIconFileId(String kindIconFileId) {
		this.kindIconFileId = kindIconFileId;
	}

	public int getKindSort() {
		return kindSort;
	}

	public void setKindSort(int kindSort) {
		this.kindSort = kindSort;
	}

	public int getReviewIsAvoid() {
		return reviewIsAvoid;
	}

	public void setReviewIsAvoid(int reviewIsAvoid) {
		this.reviewIsAvoid = reviewIsAvoid;
	}

	public String getParentKindId() {
		return parentKindId;
	}

	public void setParentKindId(String parentKindId) {
		this.parentKindId = parentKindId;
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
}
