package com.visionet.syh_mall.entity.order;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: ServiceReason
 * @Description: 售后原因
 * @author chenghongzhan
 * @date 2017年10月17日 下午9:06:20
 *
 */
@Entity
@Table(name = "tbl_service_reason")
public class ServiceReason extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String serviceReason;
	private String createBy;
	private String updateBy;
	private Date createTime = DateUtil.getCurrentDate();
	private Date updateTime = DateUtil.getCurrentDate();
	private Integer isDeleted=0;

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getServiceReason() {
		return serviceReason;
	}

	public void setServiceReason(String serviceReason) {
		this.serviceReason = serviceReason;
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

}
