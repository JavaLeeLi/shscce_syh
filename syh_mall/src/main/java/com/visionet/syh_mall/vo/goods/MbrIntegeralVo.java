package com.visionet.syh_mall.vo.goods;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class MbrIntegeralVo {

	@NotBlank(message = "会员ID不能为空")
	private String mbrID;
	@NotNull(message = "调整积分数量不能为空")
	private Integer integralNum;
	private String operateDesc;

	public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}

	public String getMbrID() {
		return mbrID;
	}

	public void setMbrID(String mbrID) {
		this.mbrID = mbrID;
	}

	public Integer getIntegralNum() {
		return integralNum;
	}

	public void setIntegralNum(Integer integralNum) {
		this.integralNum = integralNum;
	}

	@Override
	public String toString() {
		return "MbrIntegeralVo [mbrID=" + mbrID + ", integralNum=" + integralNum + "]";
	}

}
