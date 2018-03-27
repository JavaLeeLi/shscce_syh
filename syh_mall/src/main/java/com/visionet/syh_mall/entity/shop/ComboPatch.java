package com.visionet.syh_mall.entity.shop;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: ComboPatch
 * @Description: 套餐搭配信息
 * @author chenghongzhan
 * @date 2017年9月29日 下午1:36:08
 *
 */
@Entity
@Table(name = "tbl_combo_patch")
public class ComboPatch extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String comboName;// 活动名称
	private String comboDescription;// 套餐描述
	private Date startTime;
	private Date endTime;
	private String shopId;
	private Date createTime = DateUtil.getCurrentDate();
	private Date updateTime = DateUtil.getCurrentDate();
	private int isDeleted = 0;

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public String getComboDescription() {
		return comboDescription;
	}

	public void setComboDescription(String comboDescription) {
		this.comboDescription = comboDescription;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
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
