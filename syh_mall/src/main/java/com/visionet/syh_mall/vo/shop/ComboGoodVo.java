package com.visionet.syh_mall.vo.shop;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ComboGoodVo {

		private String comboGoodId;
		@NotEmpty(message = "活动商品ID不能为空")
		private String goodsID;
		@NotNull(message = "活动商品ID不能为空")
		private BigDecimal goodsComboPrice;
		@NotNull(message = "活动商品排序不能为空")
		private Integer goodsSort;
		@NotNull(message = "是否套餐主商品不能为空")
		private Integer isMain;

		public String getComboGoodId() {
			return comboGoodId;
		}

		public void setComboGoodId(String comboGoodId) {
			this.comboGoodId = comboGoodId;
		}

		public String getGoodsID() {
			return goodsID;
		}

		public void setGoodsID(String goodsID) {
			this.goodsID = goodsID;
		}

		public BigDecimal getGoodsComboPrice() {
			return goodsComboPrice;
		}

		public void setGoodsComboPrice(BigDecimal goodsComboPrice) {
			this.goodsComboPrice = goodsComboPrice;
		}

		public Integer getGoodsSort() {
			return goodsSort;
		}

		public void setGoodsSort(Integer goodsSort) {
			this.goodsSort = goodsSort;
		}

		public Integer getIsMain() {
			return isMain;
		}

		public void setIsMain(Integer isMain) {
			this.isMain = isMain;
		}

		@Override
		public String toString() {
			return "ComboGoods [goodsID=" + goodsID + ", goodsComboPrice=" + goodsComboPrice + ", goodsSort="
					+ goodsSort + ", isMain=" + isMain + "]";
		}

}
