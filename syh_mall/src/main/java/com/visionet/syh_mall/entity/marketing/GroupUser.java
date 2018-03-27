package com.visionet.syh_mall.entity.marketing;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

@Entity
@Table(name = "tbl_group_user")
public class GroupUser extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String groupDetailId;// 组团信息ID
	private String userId;// 参与用户ID
	private String orderId;// 参与订单ID
	private Date createTime;
	private Date updateTime;
	private int isDeleted = 0;

	public String getGroupDetailId() {
		return groupDetailId;
	}

	public void setGroupDetailId(String groupDetailId) {
		this.groupDetailId = groupDetailId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

}
