package com.visionet.syh_mall.entity.shop;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: FulfilRemit
 * @Description: 满减满送
 * @author chenghongzhan
 * @date 2017年9月29日 下午1:26:43
 *
 */
@Entity
@Table(name = "tbl_fulfil_remit")
public class FulfilRemit extends IdEntity {

	private static final long serialVersionUID = 1L;
	private BigDecimal fulfilAmt;// 订单金额满足条件
	private BigDecimal remitAmt;// 减免金额
	private String giftGoodsId;// 赠送商品ID
	private String shopId;// 所属店铺ID
	private Date createTime = DateUtil.getCurrentDate();// 创建时间
	private Date updateTime = DateUtil.getCurrentDate();// 修改时间
	private Integer isDeleted = 0;// 是否删除

	public BigDecimal getFulfilAmt() {
		return fulfilAmt;
	}

	public void setFulfilAmt(BigDecimal fulfilAmt) {
		this.fulfilAmt = fulfilAmt;
	}

	public BigDecimal getRemitAmt() {
		return remitAmt;
	}

	public void setRemitAmt(BigDecimal remitAmt) {
		this.remitAmt = remitAmt;
	}

	public String getGiftGoodsId() {
		return giftGoodsId;
	}

	public void setGiftGoodsId(String giftGoodsId) {
		this.giftGoodsId = giftGoodsId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
