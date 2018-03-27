package com.visionet.syh_mall.entity.cart;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 购物车表实体类
 * @author mulongfei
 * @date 2017年8月31日上午10:12:30
 */
@Entity
@Table(name="tbl_shop_cart")
public class ShopCart extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String goodsId;//商品id
	private Integer goodsNum;//商品数量
	private String userId;//用户id
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private Integer isDeleted = 0;//是否删除
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
