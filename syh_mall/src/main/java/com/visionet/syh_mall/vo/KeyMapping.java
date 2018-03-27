package com.visionet.syh_mall.vo;

import java.math.BigDecimal;

public class KeyMapping {

	private String keyCode;//字典表键值
	private BigDecimal valueDesc;//字典表描述
	private String chargeID;
	private String ddRemark;//备注

	public String getDdRemark() {
		return ddRemark;
	}

	public void setDdRemark(String ddRemark) {
		this.ddRemark = ddRemark;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public BigDecimal getValueDesc() {
		return valueDesc;
	}

	public void setValueDesc(BigDecimal valueDesc) {
		this.valueDesc = valueDesc;
	}

	public String getChargeID() {
		return chargeID;
	}

	public void setChargeID(String chargeID) {
		this.chargeID = chargeID;
	}

}
