package com.visionet.syh_mall.common.decorate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.marketing.Coupon;
import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.entity.marketing.UserCoupon;
import com.visionet.syh_mall.repository.marketing.CouponRepository;
import com.visionet.syh_mall.repository.marketing.UserCouponRepository;
import com.visionet.syh_mall.service.cart.ShopCartService;
import com.visionet.syh_mall.service.marketing.DiscountTimeService;
/**
 * 购物车装饰类
 * @author mulongfei
 * @date 2017年9月20日下午1:53:05
 */
@Service
public class ShopCartEnhance {
	@Autowired
	private UserCouponRepository userCouponDao;
	@Autowired
	private DiscountTimeService discountTimeService;
	@Autowired
	private ShopCartService shopCartService;
	@Autowired
	private CouponRepository couponDao;
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getShopCartList(String UserId) {
		List<Map<String,Object>> shopCartList = shopCartService.getShopCartList(UserId);
		for (int i=0;i<shopCartList.size();i++) {
			String shopID = (String) shopCartList.get(i).get("shopID");
			List<Coupon> allCoupon = couponDao.findAll(shopID,DateUtil.getCurrentDate());
			int availableCouponCount=0;
			for (Coupon coupon : allCoupon) {
				String couponId = coupon.getId();
				UserCoupon userCoupon = userCouponDao.findOne(UserId,couponId);
				if(!StringUtils.isEmpty(userCoupon)){
					availableCouponCount = availableCouponCount + userCoupon.getCouponHaveNum();
				}
			}
			shopCartList.get(i).put("availableCouponCount", availableCouponCount);
			List<Map<String,Object>> goodsInfos = (List<Map<String, Object>>) shopCartList.get(i).get("goodsInfos");
			for(int j=0;j<goodsInfos.size();j++){
				String goodsId = (String) goodsInfos.get(j).get("goodsID");
				//处理活动
				DiscountTime discountTime = discountTimeService.takeDiscountTime(UserId, goodsId, null);//限时活动
				if(!StringUtils.isEmpty(discountTime)){
					goodsInfos.get(j).put("goodsPrice", discountTime.getDiscountPrice());
					goodsInfos.get(j).put("discountTime", discountTime);
					goodsInfos.get(j).put("goodsStockNum", discountTime.getStockNum());
				}else{
					goodsInfos.get(j).put("goodsPrice", goodsInfos.get(j).get("goodsOriginalPrice"));
					goodsInfos.get(j).put("discountTime", null);
				}
			}
		}
		return shopCartList;
	}
}
