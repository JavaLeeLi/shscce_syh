package com.visionet.syh_mall.entity.syhservice;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * 服务内容介绍实体类
 * 
 * @author mulongfei
 *
 */
@Entity
@Table(name = "tbl_syhservice_description")
public class Syhservice extends IdEntity {

	private static final long serialVersionUID = 1L;

	private String serviceDescTitle;// 服务介绍标题
	private String serviceDescContent;// 服务介绍内容
	private String serviceTypeCode;// 服务类型
	private Integer serviceDescType;// 服务类型介绍
	private String createBy;// 创建人ID
	private String updateBy;// 修改人ID
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Integer isDeleted = 0;// 是否删除

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public Integer getServiceDescType() {
		return serviceDescType;
	}

	public void setServiceDescType(Integer serviceDescType) {
		this.serviceDescType = serviceDescType;
	}

	public String getServiceDescTitle() {
		return serviceDescTitle;
	}

	public void setServiceDescTitle(String serviceDescTitle) {
		this.serviceDescTitle = serviceDescTitle;
	}

	@Type(type = "text")
	public String getServiceDescContent() {
		return serviceDescContent;
	}

	public void setServiceDescContent(String serviceDescContent) {
		this.serviceDescContent = serviceDescContent;
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
