package com.visionet.syh_mall.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @Author DM
 * @version ：2017年9月5日下午4:33:08 实体类
 */
@Entity
@Table(name = "tbl_express_templet")
public class ExpressTemplet extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String templetName;// 模板名称
	private String shopId;// 店铺ID
	private String province;// 省份
	private String city;// 市
	private String area;// 区域
	private String street;// 街道
	private BigDecimal defaultPostage;// 默认邮费
	private String goodsAddress;// 商品地址
	private Integer deliveryInterval;// 发货时限
	private int freeForExpress;// 是否包邮（1:是，0：否）
	private Date deliveryTime;// 发货时间
	private int priceType;// 计价方式（0：按件数，1：按重量，2：按体积）',
	private String expressWay;// 运送方式
	private String deliverRegion;// 配送区域
	private Date updateTime = DateUtil.getCurrentDate();
	private int isDeleted = 0;// 是否删除
	private Date createTime = DateUtil.getCurrentDate();

	public BigDecimal getDefaultPostage() {
		return defaultPostage;
	}

	public void setDefaultPostage(BigDecimal defaultPostage) {
		this.defaultPostage = defaultPostage;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getDeliveryInterval() {
		return deliveryInterval;
	}

	public void setDeliveryInterval(Integer deliveryInterval) {
		this.deliveryInterval = deliveryInterval;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getTempletName() {
		return templetName;
	}

	public void setTempletName(String templetName) {
		this.templetName = templetName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getGoodsAddress() {
		return goodsAddress;
	}

	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}

	public int getFreeForExpress() {
		return freeForExpress;
	}

	public void setFreeForExpress(int freeForExpress) {
		this.freeForExpress = freeForExpress;
	}

	public int getPriceType() {
		return priceType;
	}

	public void setPriceType(int priceType) {
		this.priceType = priceType;
	}

	public String getExpressWay() {
		return expressWay;
	}

	public void setExpressWay(String expressWay) {
		this.expressWay = expressWay;
	}

	public String getDeliverRegion() {
		return deliverRegion;
	}

	public void setDeliverRegion(String deliverRegion) {
		this.deliverRegion = deliverRegion;
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
