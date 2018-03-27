package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * 求购地址关联表
 * @author mulongfei
 * @date 2017年9月11日上午10:10:54
 */
@Entity
@Table(name="tbl_purchase_address_link")
public class PurchaseAddressLink extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String goodsId;//商品求购记录id
	private String userAddressId;//商品求购人收件地址id
	private Date createTime = DateUtil.getCurrentDate();//创建时间
	private Date updateTime = DateUtil.getCurrentDate();//更新时间
	private Integer isDeleted = 0;//是否删除
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getUserAddressId() {
		return userAddressId;
	}
	public void setUserAddressId(String userAddressId) {
		this.userAddressId = userAddressId;
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
