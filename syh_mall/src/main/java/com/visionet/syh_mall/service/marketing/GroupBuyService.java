package com.visionet.syh_mall.service.marketing;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.marketing.GroupBuy;
import com.visionet.syh_mall.repository.marketing.GroupBuyRepository;
import com.visionet.syh_mall.service.BaseService;

@Service
public class GroupBuyService extends BaseService {
	@Autowired
	private GroupBuyRepository buyRepository;
	
	/**
	 * 参与团购
	 * @return
	 */
	public BigDecimal takeDiscountTime(String userId,String goodId,String activityId){
		GroupBuy groupBuy = buyRepository.findGroupBuyInfos(goodId);
		//商品是否有活动
		if(null == groupBuy){
			return null;
		}
		//库存是否充足
		if(0 == groupBuy.getStockNum()){
			return null;
		}
		//活动时间
		boolean actityTime = DateUtil.compare(DateUtil.getCurrentDate(), groupBuy.getStartTime(), groupBuy.getEndTime());
		if(!actityTime){
			return null;
		}
		//限制数量
		return groupBuy.getDiscountPrice();
	}
}
