package com.visionet.syh_mall.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.order.TradeSetting;

public class TradeSettingVo {
	@NotBlank(message = "交易设置ID不能为空")
	private String settingID;
	@NotNull(message = "未付款订单保留时限不能为空")
	private Integer closeOrderInterval;
	@NotNull(message = "发货未收货订单完成时限不能为空")
	private Integer completeOrderInterval;
	@NotNull(message = "收货未评级订单关闭评价时限不能为空")
	private Integer closeFeedbackInterval;
	@NotNull(message = "订单完成售后关闭时限不能为空")
	private Integer closeServiceInterval;
	@NotNull(message = "买家售后申请自动通过时限不能为空")
	private Integer applyReviewInterval;
	@NotNull(message = "买家退货申请未发货关闭申请时限不能为空")
	private Integer closeCancInterval;
	@NotNull(message = "买家退货后退款申请自动审核通过时限不能为空")
	private Integer refundReviewInterval;

	public String getSettingID() {
		return settingID;
	}

	public void setSettingID(String settingID) {
		this.settingID = settingID;
	}

	public Integer getCloseOrderInterval() {
		return closeOrderInterval;
	}

	public void setCloseOrderInterval(Integer closeOrderInterval) {
		this.closeOrderInterval = closeOrderInterval;
	}

	public Integer getCompleteOrderInterval() {
		return completeOrderInterval;
	}

	public void setCompleteOrderInterval(Integer completeOrderInterval) {
		this.completeOrderInterval = completeOrderInterval;
	}

	public Integer getCloseFeedbackInterval() {
		return closeFeedbackInterval;
	}

	public void setCloseFeedbackInterval(Integer closeFeedbackInterval) {
		this.closeFeedbackInterval = closeFeedbackInterval;
	}

	public Integer getCloseServiceInterval() {
		return closeServiceInterval;
	}

	public void setCloseServiceInterval(Integer closeServiceInterval) {
		this.closeServiceInterval = closeServiceInterval;
	}

	public Integer getApplyReviewInterval() {
		return applyReviewInterval;
	}

	public void setApplyReviewInterval(Integer applyReviewInterval) {
		this.applyReviewInterval = applyReviewInterval;
	}

	public Integer getCloseCancInterval() {
		return closeCancInterval;
	}

	public void setCloseCancInterval(Integer closeCancInterval) {
		this.closeCancInterval = closeCancInterval;
	}

	public Integer getRefundReviewInterval() {
		return refundReviewInterval;
	}

	public void setRefundReviewInterval(Integer refundReviewInterval) {
		this.refundReviewInterval = refundReviewInterval;
	}

	/**
	 * @Title: converPo @Description: 交易设置的Vo转Po @param @param
	 *         settingVo @param @param setting @param @return 设定文件 @return
	 *         TradeSetting 返回类型 @throws
	 */
	public TradeSetting converPo(TradeSettingVo settingVo, TradeSetting setting, String adminId) {
		setting.setCloseOrderInterval(settingVo.getCloseOrderInterval());
		setting.setCompleteOrderInterval(settingVo.getCompleteOrderInterval());
		setting.setCloseFeedbackInterval(settingVo.getCloseFeedbackInterval());
		setting.setCloseServiceInterval(settingVo.getCloseServiceInterval());
		setting.setApplyReviewInterval(settingVo.getApplyReviewInterval());
		setting.setCloseCancInterval(settingVo.getCloseCancInterval());
		setting.setRefundReviewInterval(settingVo.getRefundReviewInterval());
		setting.setUpdateBy(adminId);
		return setting;
	}

	@Override
	public String toString() {
		return "TradeSettingVo [settingID=" + settingID + ", closeOrderInterval=" + closeOrderInterval
				+ ", completeOrderInterval=" + completeOrderInterval + ", closeFeedbackInterval="
				+ closeFeedbackInterval + ", closeServiceInterval=" + closeServiceInterval + ", applyReviewInterval="
				+ applyReviewInterval + ", closeCancInterval=" + closeCancInterval + ", refundReviewInterval="
				+ refundReviewInterval + "]";
	}

}
