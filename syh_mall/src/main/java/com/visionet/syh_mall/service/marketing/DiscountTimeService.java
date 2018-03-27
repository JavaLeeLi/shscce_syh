package com.visionet.syh_mall.service.marketing;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.repository.marketing.DiscountTimeRepository;
import com.visionet.syh_mall.service.BaseService;

/**
 * 限时折扣
 * @author xiaofb
 * @time 2017年8月29日
 */
@Service
public class DiscountTimeService extends BaseService {
	
	@Autowired
	private DiscountTimeRepository dRepository;
	
	/**
	 * 参与限时折扣
	 * @return
	 */
	public DiscountTime takeDiscountTime(String userId,String goodId,String activityId){
		Date sysdate = new Date();
		DiscountTime discountTime = dRepository.findDiscountInfo(goodId,sysdate);
		//商品是否有活动
		if(null == discountTime){
			return null;
		}
		//库存是否充足
		if(0 >= discountTime.getStockNum()){
			return null;
		}
		//活动时间
		boolean actityTime = DateUtil.compare(DateUtil.getCurrentDate(), discountTime.getStartTime(), discountTime.getEndTime());
		if(!actityTime){
			return null;
		}
		//限制数量
		return discountTime;
	}
}
