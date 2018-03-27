package com.visionet.syh_mall.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.goods.ExpressTemplet;
import com.visionet.syh_mall.entity.goods.ExpressWay;

/**
 * @ClassName: ExpressTempletVo
 * @Description: 运费模块的Vo
 * @author chenghongzhan
 * @date 2017年10月12日 下午6:59:24
 *
 */
public class ExpressTempletVo {

	private String userID;//用户id
	private String shopID;//店铺id
	private String templetID;//运费模板id
	private String templetAddress;//模板地址
	@NotBlank(message = "快递模板名称不能为空")
	private String templetName;//模板名称
	private String templetProvince;//省
	private String templetCity;//城市
	private String templetArea;//县
	private String templetStreet;//街道
	@NotNull(message = "发货时限不能为空")
	@Min(0)
	private Integer deliveryInterval;
	@NotNull(message = "是否包邮不能为空")
	private Integer freeForExpress;
	@DecimalMin("0")
	private BigDecimal defaultPostage;
	@NotNull(message = "计费方式不能为空")
	private Integer priceType;
	private List<ExpressWayVo> expressWays;//配送地区邮费数组

	public BigDecimal getDefaultPostage() {
		return defaultPostage;
	}

	public void setDefaultPostage(BigDecimal defaultPostage) {
		this.defaultPostage = defaultPostage;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getTempletID() {
		return templetID;
	}

	public void setTempletID(String templetID) {
		this.templetID = templetID;
	}

	public String getTempletAddress() {
		return templetAddress;
	}

	public void setTempletAddress(String templetAddress) {
		this.templetAddress = templetAddress;
	}

	public String getTempletName() {
		return templetName;
	}

	public void setTempletName(String templetName) {
		this.templetName = templetName;
	}

	public String getTempletProvince() {
		return templetProvince;
	}

	public void setTempletProvince(String templetProvince) {
		this.templetProvince = templetProvince;
	}

	public String getTempletCity() {
		return templetCity;
	}

	public void setTempletCity(String templetCity) {
		this.templetCity = templetCity;
	}

	public String getTempletArea() {
		return templetArea;
	}

	public void setTempletArea(String templetArea) {
		this.templetArea = templetArea;
	}

	public String getTempletStreet() {
		return templetStreet;
	}

	public void setTempletStreet(String templetStreet) {
		this.templetStreet = templetStreet;
	}

	public Integer getDeliveryInterval() {
		return deliveryInterval;
	}

	public void setDeliveryInterval(Integer deliveryInterval) {
		this.deliveryInterval = deliveryInterval;
	}

	public Integer getFreeForExpress() {
		return freeForExpress;
	}

	public void setFreeForExpress(Integer freeForExpress) {
		this.freeForExpress = freeForExpress;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public List<ExpressWayVo> getExpressWays() {
		return expressWays;
	}

	public void setExpressWays(List<ExpressWayVo> expressWays) {
		this.expressWays = expressWays;
	}

	/**
	 * @Title: coverPo @Description: 运费模块的Vo转Po @param @param
	 *         expressTempletVo @param @param expressTemplet @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public ExpressTemplet coverPo(ExpressTempletVo expressTempletVo, ExpressTemplet expressTemplet) {
		expressTemplet.setId(expressTempletVo.getTempletID());
		expressTemplet.setProvince(expressTempletVo.getTempletProvince());
		expressTemplet.setArea(expressTempletVo.getTempletArea());
		expressTemplet.setCity(expressTempletVo.getTempletCity());
		expressTemplet.setDefaultPostage(expressTempletVo.getDefaultPostage());
		expressTemplet.setStreet(expressTempletVo.getTempletStreet());
		expressTemplet.setDeliveryInterval(expressTempletVo.getDeliveryInterval());
		expressTemplet.setFreeForExpress(expressTempletVo.getFreeForExpress());
		expressTemplet.setGoodsAddress(expressTempletVo.getTempletAddress());
		expressTemplet.setShopId(expressTempletVo.getShopID());
		expressTemplet.setTempletName(expressTempletVo.getTempletName());
		expressTemplet.setPriceType(expressTempletVo.getPriceType());
		return expressTemplet;
	}

	/**
	 * @Title: coverPo @Description: 运费方式Vo转Po @param @return 设定文件 @return
	 *         ExpressTemplet 返回类型 @throws
	 */
	public List<ExpressWay> coverPo(List<ExpressWayVo> expressTempletVos, List<ExpressWay> expressWays) {
		List<ExpressWay> expressWays2 = new ArrayList<ExpressWay>();
		if (expressTempletVos.size() > 0) {
			for (int i = 0; i < expressTempletVos.size(); i++) {
				ExpressWayVo expressWayVo = expressTempletVos.get(i);
				ExpressWay expressWay = expressWays.get(i);
				expressWay.setArea(expressWayVo.getExpressArea());
				expressWay.setCity(expressWayVo.getExpressCity());
				expressWay.setFirstFee(expressWayVo.getExpressFirstFee());
				expressWay.setFirstThing(expressWayVo.getExpressFirstThing());
				expressWay.setProvince(expressWayVo.getExpressProvince());
				expressWay.setRenewFee(expressWayVo.getExpressRenewFee());
				expressWay.setRenewThing(expressWayVo.getExpressRenewThing());
				expressWay.setStreet(expressWayVo.getExpressStreet());
				expressWays2.add(expressWay);
			}
		}
		return expressWays2;
	}


	@Override
	public String toString() {
		return "ExpressTempletVo [userID=" + userID + ", shopID=" + shopID + ", templetID=" + templetID
				+ ", templetAddress=" + templetAddress + ", templetName=" + templetName + ", templetProvince="
				+ templetProvince + ", templetCity=" + templetCity + ", templetArea=" + templetArea + ", templetStreet="
				+ templetStreet + ", deliveryInterval=" + deliveryInterval + ", freeForExpress=" + freeForExpress
				+ ", defaultPostage=" + defaultPostage + ", priceType=" + priceType + ", expressWays=" + expressWays
				+ "]";
	}

}
