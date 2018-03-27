package com.visionet.syh_mall.vo;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 运费方式的Vo
 * 
 * @ClassName: ExpressWay
 * @Description:
 * @author chenghongzhan
 * @date 2017年10月12日 下午6:59:05
 *
 */
public class ExpressWayVo {
	private String expressWayId;// 运费模块Id
	@NotBlank(message = "配送方式的配送省份不能为空")
	private String expressProvince;
	@NotBlank(message = "配送方式的配送城市不能为空")
	private String expressCity;
	@NotBlank(message = "配送方式的配送区县不能为空")
	private String expressArea;
	@NotBlank(message = "配送方式的配送街道不能为空")
	private String expressStreet;
	@NotBlank(message = "配送方式的首件数量不能为空")
	@Min(1)
	private Integer expressFirstThing;
	@NotBlank(message = "配送方式的首件金额不能为空")
	@DecimalMin("0")
	private BigDecimal expressFirstFee;
	@NotBlank(message = "配送方式不能为空")
	@Min(1)
	private Integer expressRenewThing;
	@NotBlank(message = "配送方式不能为空")
	@DecimalMin("0")
	private BigDecimal expressRenewFee;

	public String getExpressWayId() {
		return expressWayId;
	}

	public void setExpressWayId(String expressWayId) {
		this.expressWayId = expressWayId;
	}

	public String getExpressProvince() {
		return expressProvince;
	}

	public void setExpressProvince(String expressProvince) {
		this.expressProvince = expressProvince;
	}

	public String getExpressCity() {
		return expressCity;
	}

	public void setExpressCity(String expressCity) {
		this.expressCity = expressCity;
	}

	public String getExpressArea() {
		return expressArea;
	}

	public void setExpressArea(String expressArea) {
		this.expressArea = expressArea;
	}

	public String getExpressStreet() {
		return expressStreet;
	}

	public void setExpressStreet(String expressStreet) {
		this.expressStreet = expressStreet;
	}


	public BigDecimal getExpressFirstFee() {
		return expressFirstFee;
	}

	public void setExpressFirstFee(BigDecimal expressFirstFee) {
		this.expressFirstFee = expressFirstFee;
	}

	public Integer getExpressFirstThing() {
		return expressFirstThing;
	}

	public void setExpressFirstThing(Integer expressFirstThing) {
		this.expressFirstThing = expressFirstThing;
	}

	public Integer getExpressRenewThing() {
		return expressRenewThing;
	}

	public void setExpressRenewThing(Integer expressRenewThing) {
		this.expressRenewThing = expressRenewThing;
	}

	public BigDecimal getExpressRenewFee() {
		return expressRenewFee;
	}

	public void setExpressRenewFee(BigDecimal expressRenewFee) {
		this.expressRenewFee = expressRenewFee;
	}

	@Override
	public String toString() {
		return "ExpressWayVo [expressProvince=" + expressProvince + ", expressCity=" + expressCity + ", expressArea="
				+ expressArea + ", expressStreet=" + expressStreet + ", expressFirstThing=" + expressFirstThing
				+ ", expressFirstFee=" + expressFirstFee + ", expressRenewThing=" + expressRenewThing
				+ ", expressRenewFee=" + expressRenewFee + ",templetId=" + expressWayId + "]";
	}

}
