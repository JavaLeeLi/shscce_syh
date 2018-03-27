package com.visionet.syh_mall.vo;

import java.math.BigDecimal;

/**
 * @ClassName: ChannelCollctVo
 * @Description: 分销汇总Vo
 * @author chenghongzhan
 * @date 2018年2月3日 下午1:15:21
 *
 */
public class ChannelCollctVo {
	private String time;// 日期
	private String loginName;// 会员账号
	private String aliasName;// 会员名字
	private String hierarchy;// 会员层级
	private BigDecimal amount;// 佣金金额
	private BigDecimal commissionRate;// 佣金比例

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

}
