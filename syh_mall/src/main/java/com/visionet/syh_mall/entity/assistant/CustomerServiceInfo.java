package com.visionet.syh_mall.entity.assistant;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * 
 * @ClassName: CustomerServiceInfo
 * @Description: 客服的实体类
 * @author chenghongzhan
 * @date 2017年8月29日 下午4:29:10
 *
 */
@Entity
@Table(name = "tbl_customer_service_info")
public class CustomerServiceInfo extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String customerName;// 客服名称
	private String customerQq;// 客服名称
	private Integer customerType;// 客服类型
	private Integer dutyType;// 客服服务类型
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private String employer;// 雇主
	private Date updateTime = DateUtil.getCurrentDate();// 更新时间
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Integer isDeleted = 0;// 是否删除

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getDutyType() {
		return dutyType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDutyType(Integer dutyType) {
		this.dutyType = dutyType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerQq() {
		return customerQq;
	}

	public void setCustomerQq(String customerQq) {
		this.customerQq = customerQq;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
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
