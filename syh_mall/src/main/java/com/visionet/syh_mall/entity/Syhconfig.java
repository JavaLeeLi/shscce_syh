package com.visionet.syh_mall.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 数据字典实体
 * @author mulongfei
 * @date 2017年10月19日下午2:31:13
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tbl_key_mapping")
public class Syhconfig extends IdEntity{
	private String keyCode;
	private String valueDesc;
	private int ddGroupCode;
	private String ddRemark;
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getValueDesc() {
		return valueDesc;
	}
	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}
	public int getDdGroupCode() {
		return ddGroupCode;
	}
	public void setDdGroupCode(int ddGroupCode) {
		this.ddGroupCode = ddGroupCode;
	}
	public String getDdRemark() {
		return ddRemark;
	}
	public void setDdRemark(String ddRemark) {
		this.ddRemark = ddRemark;
	}
}
