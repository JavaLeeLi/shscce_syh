package com.visionet.syh_mall.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.entity.assistant.CustomerServiceInfo;
import com.visionet.syh_mall.exception.RestException;

/**
 * @ClassName: CustomerServiceInfoVo
 * @Description: 客服的Vo
 * @author chenghongzhan
 * @date 2017年9月26日 上午10:44:33
 *
 */
public class CustomerServiceInfoVo {

	private String customerServiceID;//客服id
	@NotNull(message = "客服类型不能为空")
	private Integer customerServiceType;
	@NotNull(message = "客服职责类型不能为空")
	private Integer customerServiceDutyType;
	@NotBlank(message = "客服名称不能为空")
	private String customerServiceName;
	@NotBlank(message = "客服QQ不能为空")
	private String customerServiceQQ;
	private String employerID;

	public String getEmployerID() {
		return employerID;
	}

	public void setEmployerID(String employerID) {
		this.employerID = employerID;
	}

	public String getCustomerServiceID() {
		return customerServiceID;
	}

	public void setCustomerServiceID(String customerServiceID) {
		this.customerServiceID = customerServiceID;
	}

	public Integer getCustomerServiceType() {
		return customerServiceType;
	}

	public void setCustomerServiceType(Integer customerServiceType) {
		this.customerServiceType = customerServiceType;
	}

	public Integer getCustomerServiceDutyType() {
		return customerServiceDutyType;
	}

	public void setCustomerServiceDutyType(Integer customerServiceDutyType) {
		this.customerServiceDutyType = customerServiceDutyType;
	}

	public String getCustomerServiceName() {
		return customerServiceName;
	}

	public void setCustomerServiceName(String customerServiceName) {
		this.customerServiceName = customerServiceName;
	}

	public String getCustomerServiceQQ() {
		return customerServiceQQ;
	}

	public void setCustomerServiceQQ(String customerServiceQQ) {
		this.customerServiceQQ = customerServiceQQ;
	}

	/**
	 * @Title: convertPo @Description: Vo转Po @param @param
	 *         customerServiceInfoVo @param @return 设定文件 @return
	 *         CustomerServiceInfo 返回类型 @throws
	 */
	public CustomerServiceInfo convertPo(CustomerServiceInfoVo customerServiceInfoVo,
			CustomerServiceInfo customerServiceInfo) {
		customerServiceInfo.setId(customerServiceInfoVo.getCustomerServiceID());
		customerServiceInfo.setCustomerName(customerServiceInfoVo.getCustomerServiceName());
		customerServiceInfo.setCustomerQq(customerServiceInfoVo.getCustomerServiceQQ());
		customerServiceInfo.setCustomerType(customerServiceInfoVo.getCustomerServiceType());
		customerServiceInfo.setDutyType(customerServiceInfoVo.getCustomerServiceDutyType());
		if (customerServiceInfoVo.getCustomerServiceType() == 2
				&& StringUtils.isEmpty(customerServiceInfoVo.getEmployerID())) {
			throw new RestException(BusinessStatus.SHOP_EMPLOREY.getCode(), BusinessStatus.SHOP_EMPLOREY.getDesc());
		} else if (customerServiceInfoVo.getCustomerServiceType() != 2
				&& !StringUtils.isEmpty(customerServiceInfoVo.getEmployerID())) {
			throw new RestException(BusinessStatus.SHOP_EMPLOREY.getCode(), BusinessStatus.SHOP_EMPLOREY.getDesc());
		} else {
			customerServiceInfo.setEmployer(customerServiceInfoVo.getEmployerID());
		}
		return customerServiceInfo;
	}

	@Override
	public String toString() {
		return "CustomerServiceInfoVo [customerServiceID=" + customerServiceID + ", customerServiceType="
				+ customerServiceType + ", customerServiceDutyType=" + customerServiceDutyType
				+ ", customerServiceName=" + customerServiceName + ", customerServiceQQ=" + customerServiceQQ
				+ ", employerID=" + employerID + "]";
	}

}
