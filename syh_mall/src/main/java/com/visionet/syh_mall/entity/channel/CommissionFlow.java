package com.visionet.syh_mall.entity.channel;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.IdEntity;

/**
 * @ClassName: CommissionFlow
 * @Description: 佣金流水
 * @author chenghongzhan
 * @date 2018年1月23日 下午2:19:33
 *
 */
@Entity
@Table(name = "tbl_commission_flow")
public class CommissionFlow extends IdEntity {

	private static final long serialVersionUID = 1L;

	private BigDecimal serviceCharge;// 手续费
	private String buyUserAccount;// 买家用户账户
	private String buyUserName;// 买家用户姓名
	private String buyOrderNo;// 订单流水号
	private BigDecimal buyTransactionAmount;// 买家成交金额
	private String buyFatherUserAccount;// 买家父级账户
	private String buyFatherUserName;// 买家父级用户名称
	private String buyFatherUserCommissionRate;// 买家父级用户结算比例
	private BigDecimal buyFatherUserCommissionFee;// 买家父级用户结算金额
	private String buyGrandFatherUserAccount;// 买家祖父级账户
	private String buyGrandFatherUserName;// 买家祖父级用户名称
	private String buyGrandFatherUserCommissionRate;// 买家祖父级用户结算比例
	private BigDecimal buyGrandFatherUserCommissionFee;// 买家祖父级用户结算金额
	private BigDecimal buyAllCommissionFee;// 买家所有流水金额

	private String sellUserAccount;// 卖家用户账户
	private String sellUserName;// 卖家用户姓名
	private String sellOrderNo;// 订单流水号
	private BigDecimal sellTransactionAmount;// 卖家成交金额
	private String sellFatherUserAccount;// 卖家父级账户
	private String sellFatherUserName;// 卖家父级用户名称
	private String sellFatherUserCommissionRate;// 卖家父级用户结算比例
	private BigDecimal sellFatherUserCommissionFee;// 卖家父级用户结算金额
	private String sellGrandFatherUserAccount;// 卖家祖父级账户
	private String sellGrandFatherUserName;// 卖家祖父级用户名称
	private String sellGrandFatherUserCommissionRate;// 卖家祖父级用户结算比例
	private BigDecimal sellGrandFatherUserCommissionFee;// 卖家祖父级用户结算金额
	private BigDecimal sellAllCommissionFee;// 卖家所有流水金额

	private Date createTime = DateUtil.getCurrentDate();

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getBuyUserAccount() {
		return buyUserAccount;
	}

	public void setBuyUserAccount(String buyUserAccount) {
		this.buyUserAccount = buyUserAccount;
	}

	public String getBuyUserName() {
		return buyUserName;
	}

	public void setBuyUserName(String buyUserName) {
		this.buyUserName = buyUserName;
	}

	public String getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(String buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}

	public BigDecimal getBuyTransactionAmount() {
		return buyTransactionAmount;
	}

	public void setBuyTransactionAmount(BigDecimal buyTransactionAmount) {
		this.buyTransactionAmount = buyTransactionAmount;
	}

	public String getBuyFatherUserAccount() {
		return buyFatherUserAccount;
	}

	public void setBuyFatherUserAccount(String buyFatherUserAccount) {
		this.buyFatherUserAccount = buyFatherUserAccount;
	}

	public String getBuyFatherUserName() {
		return buyFatherUserName;
	}

	public void setBuyFatherUserName(String buyFatherUserName) {
		this.buyFatherUserName = buyFatherUserName;
	}

	public BigDecimal getBuyFatherUserCommissionFee() {
		return buyFatherUserCommissionFee;
	}

	public void setBuyFatherUserCommissionFee(BigDecimal buyFatherUserCommissionFee) {
		this.buyFatherUserCommissionFee = buyFatherUserCommissionFee;
	}

	public String getBuyGrandFatherUserAccount() {
		return buyGrandFatherUserAccount;
	}

	public void setBuyGrandFatherUserAccount(String buyGrandFatherUserAccount) {
		this.buyGrandFatherUserAccount = buyGrandFatherUserAccount;
	}

	public String getBuyGrandFatherUserName() {
		return buyGrandFatherUserName;
	}

	public void setBuyGrandFatherUserName(String buyGrandFatherUserName) {
		this.buyGrandFatherUserName = buyGrandFatherUserName;
	}

	public BigDecimal getBuyGrandFatherUserCommissionFee() {
		return buyGrandFatherUserCommissionFee;
	}

	public void setBuyGrandFatherUserCommissionFee(BigDecimal buyGrandFatherUserCommissionFee) {
		this.buyGrandFatherUserCommissionFee = buyGrandFatherUserCommissionFee;
	}

	public String getSellUserAccount() {
		return sellUserAccount;
	}

	public void setSellUserAccount(String sellUserAccount) {
		this.sellUserAccount = sellUserAccount;
	}

	public String getSellUserName() {
		return sellUserName;
	}

	public void setSellUserName(String sellUserName) {
		this.sellUserName = sellUserName;
	}

	public String getSellOrderNo() {
		return sellOrderNo;
	}

	public void setSellOrderNo(String sellOrderNo) {
		this.sellOrderNo = sellOrderNo;
	}

	public BigDecimal getSellTransactionAmount() {
		return sellTransactionAmount;
	}

	public void setSellTransactionAmount(BigDecimal sellTransactionAmount) {
		this.sellTransactionAmount = sellTransactionAmount;
	}

	public String getSellFatherUserAccount() {
		return sellFatherUserAccount;
	}

	public void setSellFatherUserAccount(String sellFatherUserAccount) {
		this.sellFatherUserAccount = sellFatherUserAccount;
	}

	public String getSellFatherUserName() {
		return sellFatherUserName;
	}

	public void setSellFatherUserName(String sellFatherUserName) {
		this.sellFatherUserName = sellFatherUserName;
	}

	public BigDecimal getSellFatherUserCommissionFee() {
		return sellFatherUserCommissionFee;
	}

	public void setSellFatherUserCommissionFee(BigDecimal sellFatherUserCommissionFee) {
		this.sellFatherUserCommissionFee = sellFatherUserCommissionFee;
	}

	public String getSellGrandFatherUserAccount() {
		return sellGrandFatherUserAccount;
	}

	public void setSellGrandFatherUserAccount(String sellGrandFatherUserAccount) {
		this.sellGrandFatherUserAccount = sellGrandFatherUserAccount;
	}

	public String getSellGrandFatherUserName() {
		return sellGrandFatherUserName;
	}

	public void setSellGrandFatherUserName(String sellGrandFatherUserName) {
		this.sellGrandFatherUserName = sellGrandFatherUserName;
	}

	public BigDecimal getSellGrandFatherUserCommissionFee() {
		return sellGrandFatherUserCommissionFee;
	}

	public void setSellGrandFatherUserCommissionFee(BigDecimal sellGrandFatherUserCommissionFee) {
		this.sellGrandFatherUserCommissionFee = sellGrandFatherUserCommissionFee;
	}

	public String getBuyFatherUserCommissionRate() {
		return buyFatherUserCommissionRate;
	}

	public void setBuyFatherUserCommissionRate(String buyFatherUserCommissionRate) {
		this.buyFatherUserCommissionRate = buyFatherUserCommissionRate;
	}

	public String getBuyGrandFatherUserCommissionRate() {
		return buyGrandFatherUserCommissionRate;
	}

	public void setBuyGrandFatherUserCommissionRate(String buyGrandFatherUserCommissionRate) {
		this.buyGrandFatherUserCommissionRate = buyGrandFatherUserCommissionRate;
	}

	public String getSellFatherUserCommissionRate() {
		return sellFatherUserCommissionRate;
	}

	public void setSellFatherUserCommissionRate(String sellFatherUserCommissionRate) {
		this.sellFatherUserCommissionRate = sellFatherUserCommissionRate;
	}

	public String getSellGrandFatherUserCommissionRate() {
		return sellGrandFatherUserCommissionRate;
	}

	public void setSellGrandFatherUserCommissionRate(String sellGrandFatherUserCommissionRate) {
		this.sellGrandFatherUserCommissionRate = sellGrandFatherUserCommissionRate;
	}

	public BigDecimal getBuyAllCommissionFee() {
		return buyAllCommissionFee;
	}

	public void setBuyAllCommissionFee(BigDecimal buyAllCommissionFee) {
		this.buyAllCommissionFee = buyAllCommissionFee;
	}

	public BigDecimal getSellAllCommissionFee() {
		return sellAllCommissionFee;
	}

	public void setSellAllCommissionFee(BigDecimal sellAllCommissionFee) {
		this.sellAllCommissionFee = sellAllCommissionFee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
