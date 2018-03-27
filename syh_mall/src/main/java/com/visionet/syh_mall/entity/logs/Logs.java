package com.visionet.syh_mall.entity.logs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

@Entity
@Table(name = "tbl_logs")
public class Logs extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private String operatingPersonnel;
	private String operatingName;
	private String operatingModel;
	private String operatingSignature;
	private String operatingParams;
	private Date operatingTime = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperatingPersonnel() {
		return operatingPersonnel;
	}

	public void setOperatingPersonnel(String operatingPersonnel) {
		this.operatingPersonnel = operatingPersonnel;
	}

	public String getOperatingName() {
		return operatingName;
	}

	public void setOperatingName(String operatingName) {
		this.operatingName = operatingName;
	}

	public String getOperatingModel() {
		return operatingModel;
	}

	public void setOperatingModel(String operatingModel) {
		this.operatingModel = operatingModel;
	}

	public String getOperatingSignature() {
		return operatingSignature;
	}

	public void setOperatingSignature(String operatingSignature) {
		this.operatingSignature = operatingSignature;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getOperatingParams() {
		return operatingParams;
	}

	public void setOperatingParams(String operatingParams) {
		this.operatingParams = operatingParams;
	}

}
