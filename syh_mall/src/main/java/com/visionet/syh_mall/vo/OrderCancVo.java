package com.visionet.syh_mall.vo;

import java.util.Date;
import java.util.List;

/**
 * 订单退货
 * 
 * @author xiaofb
 * @time 2017年9月6日
 */
public class OrderCancVo {
	private int itemCount;
	private int pageCount;
	private int curPageIndex;
	private int hasNext;
	private List<CancInfos> cancInfos;
	private List<CancGoods> cancGoods;

	public CancInfos getCancInfo() {
		return new CancInfos();
	}

	public CancGoods getCancGood() {
		return new CancGoods();
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurPageIndex() {
		return curPageIndex;
	}

	public void setCurPageIndex(int curPageIndex) {
		this.curPageIndex = curPageIndex;
	}

	public int getHasNext() {
		return hasNext;
	}

	public void setHasNext(int hasNext) {
		this.hasNext = hasNext;
	}

	public List<CancInfos> getCancInfos() {
		return cancInfos;
	}

	public void setCancInfos(List<CancInfos> cancInfos) {
		this.cancInfos = cancInfos;
	}

	public List<CancGoods> getCancGoods() {
		return cancGoods;
	}

	public void setCancGoods(List<CancGoods> cancGoods) {
		this.cancGoods = cancGoods;
	}

	public class CancInfos {
		private String orderID;
		private String orderSN;
		private String buyerName;
		private String buyerPhone;
		private String shopName;
		private String sellerName;
		private float refundSum;
		private String refundReason;
		private String cancStatusCode;
		private String cancStatusDesc;
		private Date applyTime;

		public String getOrderID() {
			return orderID;
		}

		public void setOrderID(String orderID) {
			this.orderID = orderID;
		}

		public String getOrderSN() {
			return orderSN;
		}

		public void setOrderSN(String orderSN) {
			this.orderSN = orderSN;
		}

		public String getBuyerName() {
			return buyerName;
		}

		public void setBuyerName(String buyerName) {
			this.buyerName = buyerName;
		}

		public String getBuyerPhone() {
			return buyerPhone;
		}

		public void setBuyerPhone(String buyerPhone) {
			this.buyerPhone = buyerPhone;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getSellerName() {
			return sellerName;
		}

		public void setSellerName(String sellerName) {
			this.sellerName = sellerName;
		}

		public float getRefundSum() {
			return refundSum;
		}

		public void setRefundSum(float refundSum) {
			this.refundSum = refundSum;
		}

		public String getRefundReason() {
			return refundReason;
		}

		public void setRefundReason(String refundReason) {
			this.refundReason = refundReason;
		}

		public String getCancStatusCode() {
			return cancStatusCode;
		}

		public void setCancStatusCode(String cancStatusCode) {
			this.cancStatusCode = cancStatusCode;
		}

		public String getCancStatusDesc() {
			return cancStatusDesc;
		}

		public void setCancStatusDesc(String cancStatusDesc) {
			this.cancStatusDesc = cancStatusDesc;
		}

		public Date getApplyTime() {
			return applyTime;
		}

		public void setApplyTime(Date applyTime) {
			this.applyTime = applyTime;
		}

	}

	/**
	 * 退货商品
	 * 
	 * @author xiaofb
	 * @time 2017年9月6日
	 */
	public class CancGoods {
		private String goodsID;
		private String goodsName;
		private double goodsPrice;
		private int goodsNum;

		public String getGoodsID() {
			return goodsID;
		}

		public void setGoodsID(String goodsID) {
			this.goodsID = goodsID;
		}

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public double getGoodsPrice() {
			return goodsPrice;
		}

		public void setGoodsPrice(double goodsPrice) {
			this.goodsPrice = goodsPrice;
		}

		public int getGoodsNum() {
			return goodsNum;
		}

		public void setGoodsNum(int goodsNum) {
			this.goodsNum = goodsNum;
		}
	}

}
