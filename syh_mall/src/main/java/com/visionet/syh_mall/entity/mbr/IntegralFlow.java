package com.visionet.syh_mall.entity.mbr;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * 积分流水表实体类
 * 
 * @author mulongfei
 * @date 2017年8月28日上午10:39:00
 */
@Entity
@Table(name = "tbl_integral_flow")
public class IntegralFlow extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String userId;// 用户id
	private String operateDesc;// 操作描述
	private Integer ingegralNum;// 积分数量
	private Integer type;// 类型
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Date updateTime = DateUtil.getCurrentDate();// 更新时间
	private Integer isDeleted = 0;// 是否删除

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}

	public Integer getIngegralNum() {
		return ingegralNum;
	}

	public void setIngegralNum(Integer ingegralNum) {
		this.ingegralNum = ingegralNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
