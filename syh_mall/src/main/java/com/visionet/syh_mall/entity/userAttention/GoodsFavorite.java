package com.visionet.syh_mall.entity.userAttention;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 商品收藏实体类
 * @author mulongfei
 * @date 2017年9月4日下午1:37:44
 */
@Entity
@Table(name="tbl_goods_favorite")
public class GoodsFavorite extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String userId;//收藏人id
	private String goodsId;//商品id
	private Date createTime=new Date();//创建时间
	private Date updateTime;//更新时间
	private Integer isDeleted = 0;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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
