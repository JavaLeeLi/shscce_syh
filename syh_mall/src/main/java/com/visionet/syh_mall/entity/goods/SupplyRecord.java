package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 求购响应记录表
 * @author mulongfei
 * @date 2017年9月11日下午5:44:24
 */
@Entity
@Table(name="tbl_supply_record")
public class SupplyRecord extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String goodsId;//商品求购记录id
	private Integer supplyNum = 0;//供货数量
	private String supplyUserId;//响应人id
	private String supplyOrderId;//求购供货订单ID
	private String supplyGoodsId;//求购供货商品id
	private Date createTime = new Date();//创建时间
	private Date updateTime;//修改时间
	private Integer isDeleted = 0;
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getSupplyNum() {
		return supplyNum;
	}
	public void setSupplyNum(Integer supplyNum) {
		this.supplyNum = supplyNum;
	}
	public String getSupplyUserId() {
		return supplyUserId;
	}
	public void setSupplyUserId(String supplyUserId) {
		this.supplyUserId = supplyUserId;
	}
	public String getSupplyOrderId() {
		return supplyOrderId;
	}
	public void setSupplyOrderId(String supplyOrderId) {
		this.supplyOrderId = supplyOrderId;
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
	public String getSupplyGoodsId() {
		return supplyGoodsId;
	}
	public void setSupplyGoodsId(String supplyGoodsId) {
		this.supplyGoodsId = supplyGoodsId;
	}
}
