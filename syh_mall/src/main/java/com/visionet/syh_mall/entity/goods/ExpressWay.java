package com.visionet.syh_mall.entity.goods;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: ExpressWay
 * @Description: 运费方式
 * @author chenghongzhan
 * @date 2017年9月13日 上午11:26:23
 *
 */
@Entity
@Table(name = "tbl_express_way")
public class ExpressWay extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String templetId;// 运费模块Id
	private String province;// 省份
	private String city;// 城市
	private String area;// 区域
	private String street;// 街道
	private Integer firstThing;// 首件
	private BigDecimal firstFee;// 收费
	private Integer renewThing;// 续件
	private BigDecimal renewFee;// 续费
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Integer isDeleted = 0;// 是否删除

	public void setFirstThing(Integer firstThing) {
		this.firstThing = firstThing;
	}

	public void setRenewThing(Integer renewThing) {
		this.renewThing = renewThing;
	}

	public String getTempletId() {
		return templetId;
	}

	public void setTempletId(String templetId) {
		this.templetId = templetId;
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

	public Integer getFirstThing() {
		return firstThing;
	}

	public Integer getRenewThing() {
		return renewThing;
	}

	public BigDecimal getFirstFee() {
		return firstFee;
	}

	public void setFirstFee(BigDecimal firstFee) {
		this.firstFee = firstFee;
	}

	public BigDecimal getRenewFee() {
		return renewFee;
	}

	public void setRenewFee(BigDecimal renewFee) {
		this.renewFee = renewFee;
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
