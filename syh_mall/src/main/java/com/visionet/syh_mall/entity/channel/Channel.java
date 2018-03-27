package com.visionet.syh_mall.entity.channel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 会员分销实体类
 * 
 * @author mulongfei
 * @date 2017年8月23日下午4:54:29
 */
@Entity
@Table(name = "tbl_mbr_channel_rule")
public class Channel extends IdEntity {
	private static final long serialVersionUID = 1L;
	private Float sellTotalCommission;// 卖家总返佣比例
	private Float totalCommission;// 买家总返佣比例
	private Float sellFirstCommission;// 第一层返佣比例
	private Float firstCommission;// 第一层返佣比例
	private Float sellSecondCommission;// 第二层返佣比例
	private Float secondCommission;// 第二层返佣比例
	private Float sellThirdCommission;// 第三层返佣比例
	private Float thirdCommission;// 第三层返佣比例
	private Integer mbrLevel;// 会员分销等级
	private Date createTime = new Date();// 创建时间
	private Date updateTime = new Date();// 更新时间
	private int isDelete = 0;


	public Float getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(Float totalCommission) {
		this.totalCommission = totalCommission;
	}

	public Float getFirstCommission() {
		return firstCommission;
	}

	public void setFirstCommission(Float firstCommission) {
		this.firstCommission = firstCommission;
	}

	public Float getSecondCommission() {
		return secondCommission;
	}

	public void setSecondCommission(Float secondCommission) {
		this.secondCommission = secondCommission;
	}

	public Float getThirdCommission() {
		return thirdCommission;
	}

	public void setThirdCommission(Float thirdCommission) {
		this.thirdCommission = thirdCommission;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Float getSellTotalCommission() {
		return sellTotalCommission;
	}

	public void setSellTotalCommission(Float sellTotalCommission) {
		this.sellTotalCommission = sellTotalCommission;
	}

	public Float getSellFirstCommission() {
		return sellFirstCommission;
	}

	public void setSellFirstCommission(Float sellFirstCommission) {
		this.sellFirstCommission = sellFirstCommission;
	}

	public Float getSellSecondCommission() {
		return sellSecondCommission;
	}

	public void setSellSecondCommission(Float sellSecondCommission) {
		this.sellSecondCommission = sellSecondCommission;
	}

	public Float getSellThirdCommission() {
		return sellThirdCommission;
	}

	public void setSellThirdCommission(Float sellThirdCommission) {
		this.sellThirdCommission = sellThirdCommission;
	}

	public Integer getMbrLevel() {
		return mbrLevel;
	}

	public void setMbrLevel(Integer mbrLevel) {
		this.mbrLevel = mbrLevel;
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
