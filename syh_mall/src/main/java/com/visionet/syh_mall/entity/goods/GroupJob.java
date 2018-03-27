package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;
/**
 * 团购定时任务
 * @author mulongfei
 * @date 2017年11月17日上午10:50:58
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tbl_group_job")
public class GroupJob extends IdEntity{
	private String groupDetailId;
	private String jobName;
	private String jobGroupName;
	private String triggerName;
	private String triggerGroupName;
	private int jobStatus = 0;
	private String cronExpression;
	private String jobClass;
	private Date createTime;
	private Date updateTime;
	public String getGroupDetailId() {
		return groupDetailId;
	}
	public void setGroupDetailId(String groupDetailId) {
		this.groupDetailId = groupDetailId;
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
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}
