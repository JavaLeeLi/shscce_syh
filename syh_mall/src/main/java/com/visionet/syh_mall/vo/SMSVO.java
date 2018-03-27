package com.visionet.syh_mall.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;
public class SMSVO {
	@NotNull(message = "手机号不能为空")
	private String telephone;
	
	private Date time;
	private String code;
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "SMSVO [telephone=" + telephone + ", time=" + time + ", code="
				+ code + "]";
	}

}
