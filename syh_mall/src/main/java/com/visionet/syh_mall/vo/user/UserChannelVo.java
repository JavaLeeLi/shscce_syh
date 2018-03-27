package com.visionet.syh_mall.vo.user;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.visionet.syh_mall.entity.channel.Channel;
import com.visionet.syh_mall.exception.RestException;

public class UserChannelVo {

	private String mbrChannelRuleID;
	@NotNull(message = "会员分销等级不能为空")
	private Integer mbrLevel;
	@NotNull(message = "会员分销买家返佣总比例不能为空")
	private Integer buyTotalRate;
	@NotNull(message = "第一层买家返佣比例不能为空")
	private Integer buyFirstRate;
	@NotNull(message = "第二层买家返佣比例不能为空")
	private Integer buySecondRate;
	@NotNull(message = "第三层买家返佣比例不能为空")
	private Integer buyThirdRate;
	@NotNull(message = "会员分销卖家返佣总比例不能为空")
	private Integer sellTotalRate;
	@NotNull(message = "第一层卖家返佣比例不能为空")
	private Integer sellFirstRate;
	@NotNull(message = "第二层卖家返佣比例不能为空")
	private Integer sellSecondRate;
	@NotNull(message = "第三层卖家返佣比例不能为空")
	private Integer sellThirdRate;

	public String getMbrChannelRuleID() {
		return mbrChannelRuleID;
	}

	public void setMbrChannelRuleID(String mbrChannelRuleID) {
		this.mbrChannelRuleID = mbrChannelRuleID;
	}

	public Integer getMbrLevel() {
		return mbrLevel;
	}

	public void setMbrLevel(Integer mbrLevel) {
		this.mbrLevel = mbrLevel;
	}

	public Integer getBuyTotalRate() {
		return buyTotalRate;
	}

	public Integer getBuyFirstRate() {
		return buyFirstRate;
	}

	public void setBuyFirstRate(Integer buyFirstRate) {
		this.buyFirstRate = buyFirstRate;
	}

	public Integer getBuySecondRate() {
		return buySecondRate;
	}

	public void setBuySecondRate(Integer buySecondRate) {
		this.buySecondRate = buySecondRate;
	}

	public Integer getBuyThirdRate() {
		return buyThirdRate;
	}

	public void setBuyThirdRate(Integer buyThirdRate) {
		this.buyThirdRate = buyThirdRate;
	}

	public Integer getSellTotalRate() {
		return sellTotalRate;
	}

	public void setSellTotalRate(Integer sellTotalRate) {
		this.sellTotalRate = sellTotalRate;
	}

	public Integer getSellFirstRate() {
		return sellFirstRate;
	}

	public void setSellFirstRate(Integer sellFirstRate) {
		this.sellFirstRate = sellFirstRate;
	}

	public Integer getSellSecondRate() {
		return sellSecondRate;
	}

	public void setSellSecondRate(Integer sellSecondRate) {
		this.sellSecondRate = sellSecondRate;
	}

	public Integer getSellThirdRate() {
		return sellThirdRate;
	}

	public void setSellThirdRate(Integer sellThirdRate) {
		this.sellThirdRate = sellThirdRate;
	}

	public void setBuyTotalRate(Integer buyTotalRate) {
		this.buyTotalRate = buyTotalRate;
	}

	public Channel converPo(UserChannelVo channelVo, Channel channel) {
		Integer sellTotalRate = channelVo.getSellTotalRate();
		Integer sellFirstRate = channelVo.getSellFirstRate();
		Integer sellSecondRate = channelVo.getSellSecondRate();
		Integer sellThirdRate = channelVo.getSellThirdRate();

		if (sellTotalRate != sellFirstRate + sellSecondRate + sellThirdRate) {
			throw new RestException("卖家三层比例相加要等于总比例");
		}

		Integer buyTotalRate = channelVo.getBuyTotalRate();
		Integer buyFirstRate = channelVo.getBuyFirstRate();
		Integer buySecondRate = channelVo.getBuySecondRate();
		Integer buyThirdRate = channelVo.getBuyThirdRate();
		if(sellTotalRate+buyTotalRate!=100){
			throw new RestException("卖家买家总比例和为100%");
		}
		if (buyTotalRate != buyFirstRate + buySecondRate + buyThirdRate) {
			throw new RestException("买家三层比例相加要等于总比例");
		}

		channel.setId(channelVo.getMbrChannelRuleID());
		channel.setMbrLevel(channelVo.getMbrLevel());
		channel.setTotalCommission(Float.valueOf((new BigDecimal(String.valueOf(channelVo.getBuyTotalRate())).divide(new BigDecimal(100)).toString())));
		channel.setFirstCommission(Float.valueOf((new BigDecimal(String.valueOf(channelVo.getBuyFirstRate())).divide(new BigDecimal(100)).toString())));
		channel.setSecondCommission(Float.valueOf((new BigDecimal(String.valueOf(channelVo.getBuySecondRate())).divide(new BigDecimal(100)).toString())));
		channel.setThirdCommission(Float.valueOf((new BigDecimal(String.valueOf(channelVo.getBuyThirdRate())).divide(new BigDecimal(100)).toString())));
		channel.setSellTotalCommission(Float.valueOf((new BigDecimal(String.valueOf(channelVo.getSellTotalRate())).divide(new BigDecimal(100)).toString())));
		channel.setSellFirstCommission(Float.valueOf((new BigDecimal(String.valueOf(channelVo.getSellFirstRate())).divide(new BigDecimal(100)).toString())));
		channel.setSellSecondCommission(Float.valueOf((new BigDecimal(String.valueOf(channelVo.getSellSecondRate()))).divide(new BigDecimal(100)).toString()));
		channel.setSellThirdCommission(Float.valueOf((new BigDecimal(String.valueOf(channelVo.getSellThirdRate())).divide(new BigDecimal(100)).toString())));
		return channel;
	}
	
}
