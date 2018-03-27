package com.visionet.syh_mall.entity.order;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: TradeSetting
 * @Description: 交易设置的实体类
 * @author chenghongzhan
 * @date 2017年9月6日 上午10:45:39
 */

@Entity
@Table(name = "tbl_trade_setting")
public class TradeSetting extends IdEntity {
	private static final long serialVersionUID = 1L;
	private Integer closeOrderInterval;// 下单未付款订单保留时限（小时）
	private Integer completeOrderInterval;// 发货未收货订单完成时限(天)
	private Integer closeFeedbackInterval;// 收货未评价订单关闭评价时限(天)
	private Integer closeServiceInterval;// 订单完成后售后关闭时限(天)
	private Integer applyReviewInterval;// 卖家售后申请自动通过时限(天)
	private Integer closeCancInterval;// 买家退货申请未发货关闭申请时限(天)
	private Integer refundReviewInterval;// 买家退货后退款申请自动通过时限(天)
	private String createBy;// 创建人
	private String updateBy;// 修改人
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间

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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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
