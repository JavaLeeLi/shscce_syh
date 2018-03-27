package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 拍卖任务entity
 * 
 * @author xiaofb
 * @time 2017年10月26日
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tbl_auction_job")
public class AuctionJob extends IdEntity {
	
	private String goodsId;
	private String jobName;
	private String jobGroupName;
	private String triggerName;
	private String triggerGroupName;
	private int jobStatus;
	private String cronExpression;
	private String jobClass;
	private Date createTime;
	private Date updateTime;
	
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroupName() {
		return triggerGroupName;
	}
	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}
	public int getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
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
