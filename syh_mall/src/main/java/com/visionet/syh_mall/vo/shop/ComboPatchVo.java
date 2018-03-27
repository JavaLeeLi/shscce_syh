package com.visionet.syh_mall.vo.shop;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.visionet.syh_mall.entity.shop.ComboPatch;

/**
 * @ClassName: ComboPatchVo
 * @Description: 套餐搭配的Vo
 * @author chenghongzhan
 * @date 2017年9月30日 下午3:14:50
 *
 */
public class ComboPatchVo {

	private String comboID;
	@NotBlank(message = "店铺ID不能为空")
	private String shopID;
	@NotBlank(message = "套餐搭配名称不能为空")
	private String comboName;
	private String comboDesc;
	@NotNull(message = "活动开始时间不能为空")
	@Future(message = "生效时间必须为将来的时间")
	private Date startTime;
	@NotNull(message = "活动结束时间不能为空")
	private Date endTime;
	@NotEmpty(message = "活动商品数组不能为空")
	@Valid
	private List<ComboGoodVo> comboGoods;

	public List<ComboGoodVo> getComboGoods() {
		return comboGoods;
	}

	public void setComboGoods(List<ComboGoodVo> comboGoods) {
		this.comboGoods = comboGoods;
	}

	public String getComboID() {
		return comboID;
	}

	public void setComboID(String comboID) {
		this.comboID = comboID;
	}

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public String getComboDesc() {
		return comboDesc;
	}

	public void setComboDesc(String comboDesc) {
		this.comboDesc = comboDesc;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @Title: converPo @Description:套餐搭配Vo转Po @param @param
	 *         comboPatchVo @param @return 设定文件 @return ComboPatch 返回类型 @throws
	 */
	public ComboPatch converPo(ComboPatchVo comboPatchVo, ComboPatch comboPatch) {
		comboPatch.setComboDescription(comboPatchVo.getComboDesc());
		comboPatch.setComboName(comboPatchVo.getComboName());
		comboPatch.setEndTime(comboPatchVo.getEndTime());
		comboPatch.setShopId(comboPatchVo.getShopID());
		comboPatch.setStartTime(comboPatchVo.getStartTime());
		return comboPatch;
	}

	/**
	 * @Title: converPo @Description:套餐搭配Vo转Po @param @param
	 *         comboPatchVo @param @return 设定文件 @return ComboPatch 返回类型 @throws
	 */
	public ComboGoods converPo(ComboGoodVo comboGoodVo, ComboGoods comboGoods, String comboId) {
		comboGoods.setComboId(comboId);
		comboGoods.setComboSort(comboGoodVo.getGoodsSort());
		comboGoods.setDiscountPrice(comboGoodVo.getGoodsComboPrice());
		comboGoods.setGoodsId(comboGoodVo.getGoodsID());
		comboGoods.setIsMain(comboGoodVo.getIsMain());
		return comboGoods;
	}

	@Override
	public String toString() {
		return "ComboPatchVo [comboID=" + comboID + ", shopID=" + shopID + ", comboName=" + comboName + ", comboDesc="
				+ comboDesc + ", startTime=" + startTime + ", endTime=" + endTime + ", comboGoods=" + comboGoods + "]";
	}

}
