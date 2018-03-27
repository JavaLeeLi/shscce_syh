package com.visionet.syh_mall.entity.service;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * 服务预约的实体类
 * 
 * @author chenghongzhan
 * @version 2017年8月23日 下午9:59:51
 *
 */
@Entity
@Table(name = "tbl_syhservice_reservation")
public class ServiceReservation extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String reservationName;// 预约人姓名
	private String reservationPhone;// 预约人电话
	private String customerAddress;// 服务预约的地址
	private String goodsSpec;// 商品规格
	private String serviceTypeCode;// 服务预约类型编码
	private String serviceContent;// 服务预约内容
	private String goodsNum;// 商品数量
	private Integer serviceOnsite;// 是否需要还是那个门服务
	private Integer siteType;// 客户地址类型
	private String kindsId;// 商品种类id
	private Integer reservationType;// 预约类型
	private Date reservationTime;// 预约时间
	private String createBy;// 创建人id
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Integer isDeleted = 0;// 是否删除
	private String reservationRemark;// 预约备注信息
	private String goodName;

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public Integer getServiceOnsite() {
		return serviceOnsite;
	}

	public void setServiceOnsite(Integer serviceOnsite) {
		this.serviceOnsite = serviceOnsite;
	}

	public Integer getSiteType() {
		return siteType;
	}

	public void setSiteType(Integer siteType) {
		this.siteType = siteType;
	}

	public String getReservationRemark() {
		return reservationRemark;
	}

	public void setReservationRemark(String reservationRemark) {
		this.reservationRemark = reservationRemark;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public String getReservationPhone() {
		return reservationPhone;
	}

	public void setReservationPhone(String reservationPhone) {
		this.reservationPhone = reservationPhone;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getKindsId() {
		return kindsId;
	}

	public void setKindsId(String kindsId) {
		this.kindsId = kindsId;
	}

	public Integer getReservationType() {
		return reservationType;
	}

	public void setReservationType(Integer reservationType) {
		this.reservationType = reservationType;
	}

	public Date getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
