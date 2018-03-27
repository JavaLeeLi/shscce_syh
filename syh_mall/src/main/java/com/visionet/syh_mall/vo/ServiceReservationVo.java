package com.visionet.syh_mall.vo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.entity.service.ServiceReservation;

/**
 * @ClassName: ServiceReservationVo
 * @Description: 服务预约Vo
 * @author chenghongzhan
 * @date 2017年9月25日 下午3:57:01
 *
 */
public class ServiceReservationVo {
	private String reservationID;
	@NotBlank(message = "客户姓名不能为空")
	private String customerName;
	@NotBlank(message = "客户手机号不能为空")
	private String customerPhone;
	private String customerAddr;
	@NotBlank(message = "服务类型编码不能为空")
	private String serviceTypeCode;
	private String serviceContent;
	private Integer serviceIsOnsite;
	private Integer siteType;
	@NotNull(message = "预约时间不能为空")
	private Date reserveTime;
	private Integer reserveType;
	private String goodsSpec;
	private String reserveRemark;
	private List<String> goodsKinds;
	private List<String> goodName;
	private List<String> goodNum;
	
	public List<String> getGoodsKinds() {
		return goodsKinds;
	}

	public void setGoodsKinds(List<String> goodsKinds) {
		this.goodsKinds = goodsKinds;
	}

	public List<String> getGoodName() {
		return goodName;
	}

	public void setGoodName(List<String> goodName) {
		this.goodName = goodName;
	}

	public List<String> getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(List<String> goodNum) {
		this.goodNum = goodNum;
	}

	public String getReservationID() {
		return reservationID;
	}

	public void setReservationID(String reservationID) {
		this.reservationID = reservationID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddr() {
		return customerAddr;
	}

	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public Integer getServiceIsOnsite() {
		return serviceIsOnsite;
	}

	public void setServiceIsOnsite(Integer serviceIsOnsite) {
		this.serviceIsOnsite = serviceIsOnsite;
	}

	public Integer getSiteType() {
		return siteType;
	}

	public void setSiteType(Integer siteType) {
		this.siteType = siteType;
	}

	public Date getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(Date reserveTime) {
		this.reserveTime = reserveTime;
	}

	public Integer getReserveType() {
		return reserveType;
	}

	public void setReserveType(Integer reserveType) {
		this.reserveType = reserveType;
	}

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public String getReserveRemark() {
		return reserveRemark;
	}

	public void setReserveRemark(String reserveRemark) {
		this.reserveRemark = reserveRemark;
	}

	/**
	 * @Title: convertPo @Description: Vo转Po @param @param
	 *         reservationVo @param @return 设定文件 @return ServiceReservation
	 *         返回类型 @throws
	 */
	public ServiceReservation convertPo(ServiceReservationVo reservationVo, ServiceReservation reservation) {
		reservation.setId(reservationVo.getReservationID());
		reservation.setCustomerAddress(reservationVo.getCustomerAddr());
		reservation.setGoodsSpec(reservationVo.getGoodsSpec());
		reservation.setReservationName(reservationVo.getCustomerName());
		reservation.setReservationPhone(reservationVo.getCustomerPhone());
		reservation.setReservationRemark(reservationVo.getReserveRemark());
		reservation.setReservationType(reservationVo.getReserveType());
		reservation.setServiceContent(reservationVo.getServiceContent());
		reservation.setServiceOnsite(reservationVo.getServiceIsOnsite());
		reservation.setServiceTypeCode(reservationVo.getServiceTypeCode());
		reservation.setSiteType(reservationVo.getSiteType());
		reservation.setReservationTime(reservationVo.getReserveTime());
		String goodkinds = StringUtils.collectionToDelimitedString(reservationVo.getGoodsKinds(), ",");
		reservation.setKindsId(goodkinds);
		String goodName = StringUtils.collectionToDelimitedString(reservationVo.getGoodName(), ",");
		reservation.setGoodName(goodName);
		String goodNum = StringUtils.collectionToDelimitedString(reservationVo.getGoodNum(), ",");
		reservation.setGoodsNum(goodNum);
		return reservation;
	}

	@Override
	public String toString() {
		return "ServiceReservationVo [reservationID=" + reservationID + ", customerName="
				+ customerName + ", customerPhone=" + customerPhone + ", customerAddr=" + customerAddr
				+ ", serviceTypeCode=" + serviceTypeCode + ", serviceContent=" + serviceContent + ", serviceIsOnsite="
				+ serviceIsOnsite + ", siteType=" + siteType + ", goodsKinds=" + goodsKinds + ", reserveTime="
				+ reserveTime + ", reserveType=" + reserveType + ", goodsSpec=" + goodsSpec + ", reserveRemark="
				+ reserveRemark + "]";
	}

}
