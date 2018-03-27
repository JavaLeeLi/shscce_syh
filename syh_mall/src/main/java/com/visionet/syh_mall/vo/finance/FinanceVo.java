package com.visionet.syh_mall.vo.finance;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 财务管理入参
 * @author mulongfei
 * @date 2017年10月20日下午4:09:26
 */
public class FinanceVo {
	private Integer pageIndex;
	private Integer itemCount;
	private String flowType;//流水类型 
	private String businessType;//业务类型
	private String content;//描述
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String userLoginName;//用户账号
	private String userRealName;//用户姓名
	private String shopName;//店铺名
	private Integer paymentStatus;//缴纳状态 0：正常，1：欠费
	private String drawCashState;//提现记录状态
	private String cardNo;//银行卡号
	private BigDecimal amt;//缴纳金额
	private String shopId;//店铺id
	private String payMethod;//支付渠道
	private String frontUrl;//跳转链接
	private BigDecimal minBalance;//初始余额
	private BigDecimal maxBalance;//结束余额
	private BigDecimal minFrozenAmt;//初始冻结
	private BigDecimal maxFrozenAmt;//结束冻结
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
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex)  {
		this.pageIndex = pageIndex;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	public String getDrawCashState() {
		return drawCashState;
	}
	public void setDrawCashState(String drawCashState) {
		this.drawCashState = drawCashState;
	}
	
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		String flow = null;
		if("0".equals(flowType)){
			flow = "收入";
		}
		if("1".equals(flowType)){
			flow = "支出";
		}
		if("2".equals(flowType)){
			flow = "充值";
		}
		if("3".equals(flowType)){
			flow = "提现";
		}
		if("4".equals(flowType)){
			flow = "退款";			
		}
		if("5".equals(flowType)){
			flow = "托管代付";	
		}
		this.flowType = flow;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		String business = null;
		if("0".equals(businessType)){
			business = "交易";
		}
		if("1".equals(businessType)){
			business = "求购";
		}
		if("2".equals(businessType)){
			business = "竞拍";
		}
		this.businessType = business;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getFrontUrl() {
		return frontUrl;
	}
	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		if("1".equals(content)){
			content = "营销服务";
		}
		if("2".equals(content)){
			content = "平台手续费";			
		}
		this.content = content;
	}
	public BigDecimal getMinBalance() {
		return minBalance;
	}
	public void setMinBalance(BigDecimal minBalance) {
		this.minBalance = minBalance;
	}
	public BigDecimal getMaxBalance() {
		return maxBalance;
	}
	public void setMaxBalance(BigDecimal maxBalance) {
		this.maxBalance = maxBalance;
	}
	public BigDecimal getMinFrozenAmt() {
		return minFrozenAmt;
	}
	public void setMinFrozenAmt(BigDecimal minFrozenAmt) {
		this.minFrozenAmt = minFrozenAmt;
	}
	public BigDecimal getMaxFrozenAmt() {
		return maxFrozenAmt;
	}
	public void setMaxFrozenAmt(BigDecimal maxFrozenAmt) {
		this.maxFrozenAmt = maxFrozenAmt;
	}
	@Override
	public String toString() {
		return "FinanceVo [pageIndex=" + pageIndex + ", itemCount=" + itemCount + ", flowType=" + flowType
				+ ", businessType=" + businessType + ", content=" + content + ", startTime=" + startTime + ", endTime="
				+ endTime + ", userLoginName=" + userLoginName + ", userRealName=" + userRealName + ", shopName="
				+ shopName + ", paymentStatus=" + paymentStatus + ", drawCashState=" + drawCashState + ", cardNo="
				+ cardNo + ", amt=" + amt + ", shopId=" + shopId + ", payMethod=" + payMethod + ", frontUrl=" + frontUrl
				+ ", minBalance=" + minBalance + ", maxBalance=" + maxBalance + ", minFrozenAmt=" + minFrozenAmt
				+ ", maxFrozenAmt=" + maxFrozenAmt + "]";
	}
	
}
