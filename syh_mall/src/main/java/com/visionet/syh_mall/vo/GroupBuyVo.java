package com.visionet.syh_mall.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.marketing.GroupBuy;

/**
 * @ClassName: GroupBuyVo
 * @Description: 团购信息的Vo
 * @author chenghongzhan
 * @date 2017年10月13日 下午1:47:59
 *
 */
public class GroupBuyVo {

	private String shopID;
	private String groupID;//团购id（null:添加  不为null:编辑）
	private String groupName;
	@NotBlank(message = "团购活动商品ID不能为空")
	private String goodsID;
	@NotNull(message = "团购活动商品团购价不能为空")
	private BigDecimal goodsGroupPrice;
	@Min(2)
	private Integer groupNum;
	@NotNull(message = "团购活动成团时限不能为空")
	private Double groupTime;
	@NotNull(message = "团购活动购买限制数不能为空")
	private Integer maxNum;
	@NotNull(message = "团购活动库存不能为空")
	private Integer groupStockNum;
	@NotNull(message = "活动开始时间不能为空")
	@Future(message = "生效时间必须为将来的时间")
	private Date startTime;
	@NotNull(message = "活动结束时间不能为空")
	private Date endTime;
	private String goodsName;
	private BigDecimal goodsPrice;
	private Integer goodsStockNum;

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsStockNum() {
		return goodsStockNum;
	}

	public void setGoodsStockNum(Integer goodsStockNum) {
		this.goodsStockNum = goodsStockNum;
	}

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public BigDecimal getGoodsGroupPrice() {
		return goodsGroupPrice;
	}

	public void setGoodsGroupPrice(BigDecimal goodsGroupPrice) {
		this.goodsGroupPrice = goodsGroupPrice;
	}

	public Integer getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}

	public Double getGroupTime() {
		return groupTime;
	}

	public void setGroupTime(Double groupTime) {
		this.groupTime = groupTime;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getGroupStockNum() {
		return groupStockNum;
	}

	public void setGroupStockNum(Integer groupStockNum) {
		this.groupStockNum = groupStockNum;
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
	 * @Title: convertPo @Description: 团购的信息Vo转Po @param @param
	 *         buyVo @param @param buy @param @return 设定文件 @return GroupBuy
	 *         返回类型 @throws
	 */
	public GroupBuy convertPo(GroupBuyVo buyVo, GroupBuy buy) {
		buy.setDiscountPrice(buyVo.getGoodsGroupPrice());
		buy.setEndTime(buyVo.getEndTime());
		buy.setGoodsId(buyVo.getGoodsID());
		buy.setGroupNum(buyVo.getGroupNum());
		buy.setGroupTime(buyVo.getGroupTime());
		buy.setMaxNum(buyVo.getMaxNum());
		buy.setShopId(buyVo.getShopID());
		buy.setStartTime(buyVo.getStartTime());
		buy.setStockNum(buyVo.getGroupStockNum());
		buy.setGroupName(buyVo.getGroupName());
		buy.setIsDeleted(0);
		return buy;
	}

	@Override
	public String toString() {
		return "GroupBuyVo [shopID=" + shopID + ", groupID=" + groupID + ", goodsID=" + goodsID + ", goodsGroupPrice="
				+ goodsGroupPrice + ", groupNum=" + groupNum + ", groupTime=" + groupTime + ", maxNum=" + maxNum
				+ ", groupStockNum=" + groupStockNum + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
