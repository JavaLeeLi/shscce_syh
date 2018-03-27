package com.visionet.syh_mall.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典表
 * @author xiaofb
 * @time 2017年9月1日
 */
@Entity
@Table(name="tbl_key_mapping")
public class KeyMapping extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String keyCode;	//数据字典key编码
	private String valueDesc;	//数据字典value描述
	private int ddGroupCode;	//数据字典分组编码
	private String ddRemark;	//数据字典项备注信息
	
	
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

	@Override
	public String toString() {
		return "KeyMapping{" +
				"keyCode='" + keyCode + '\'' +
				", valueDesc='" + valueDesc + '\'' +
				", ddGroupCode=" + ddGroupCode +
				", ddRemark='" + ddRemark + '\'' +
				'}';
	}
}
