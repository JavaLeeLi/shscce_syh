package com.visionet.syh_mall.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.order.ServiceReason;

/**
 * @ClassName: ServiceReasonVo
 * @Description: 售后原因
 * @author chenghongzhan
 * @date 2017年10月14日 下午8:54:50
 *
 */
public class ServiceReasonVo {

	private String reasonID;
	@NotBlank(message = "售后原因内容不能为空")
	private String reasonContent;

	public String getReasonID() {
		return reasonID;
	}

	public void setReasonID(String reasonID) {
		this.reasonID = reasonID;
	}

	public String getReasonContent() {
		return reasonContent;
	}

	public void setReasonContent(String reasonContent) {
		this.reasonContent = reasonContent;
	}

	/**
	 * @Title: convertVo @Description: Vo转Po @param @param
	 *         reasonVo @param @return 设定文件 @return ServiceReason 返回类型 @throws
	 */
	public ServiceReason convertVo(ServiceReasonVo reasonVo, ServiceReason reason) {
		reason.setId(reasonVo.getReasonID());
		reason.setServiceReason(reasonVo.getReasonContent());
		return reason;
	}

}
