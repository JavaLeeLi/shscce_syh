package com.visionet.syh_mall.entity.integralRule;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 积分规则实体类
 * @author mulongfei
 * @date 2017年8月28日下午1:42:12
 */
@Entity
@Table(name="tbl_integral_rule")
public class IntegralRule extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String ruleItemName;//规则项目名称
	private String ruleItemDesc;//规则项目描述
	private Integer integralNum;//积分数量
	private Integer integralType;//规则类型
	private Integer minSumForIntegral;//订单消费获取积分最小金额
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	public String getRuleItemName() {
		return ruleItemName;
	}
	public void setRuleItemName(String ruleItemName) {
		this.ruleItemName = ruleItemName;
	}
	public String getRuleItemDesc() {
		return ruleItemDesc;
	}
	public void setRuleItemDesc(String ruleItemDesc) {
		this.ruleItemDesc = ruleItemDesc;
	}
	public Integer getIntegralNum() {
		return integralNum;
	}
	public void setIntegralNum(Integer integralNum) {
		this.integralNum = integralNum;
	}
	public Integer getIntegralType() {
		return integralType;
	}
	public void setIntegralType(Integer integralType) {
		this.integralType = integralType;
	}
	public Integer getMinSumForIntegral() {
		return minSumForIntegral;
	}
	public void setMinSumForIntegral(Integer minSumForIntegral) {
		this.minSumForIntegral = minSumForIntegral;
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
}
