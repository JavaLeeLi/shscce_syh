package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 商品标签关联表实体类
 * @author mulongfei
 * @date 2017年8月30日下午5:18:27
 */
@Entity
@Table(name="tbl_goods_tag_link")
public class GoodsTagLink extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String goodsId;//商品id
	private String tagId;//标签id
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private Integer isDeleted;//是否删除
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
}
