package com.visionet.syh_mall.service.marketing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visionet.syh_mall.entity.marketing.Coupon;
import com.visionet.syh_mall.entity.marketing.UserCoupon;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.marketing.CouponRepository;
import com.visionet.syh_mall.repository.marketing.UserCouponRepository;
import com.visionet.syh_mall.repository.mobile.CouponDaolmpl;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.vo.message.CouponQo;
import com.visionet.syh_mall.vo.message.CouponVo;

/**
 * @Author DM
 * @version ：2017年9月11日上午11:35:17 优惠券类
 */
@Service
public class CouponService extends BaseService {
	@Autowired
	private UserCouponRepository userCouponDao;
	@Autowired
	private CouponDaolmpl couponDaolmpl;
	@Autowired
	private CouponRepository couponDao;

	/**
	 * 优惠券列表
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Page<CouponVo> queryCouponList(CouponQo qo) throws Exception {
		PageRequest pr = getPageRequest(qo.getPageNumber(), qo.getPageSize(), qo.getOrderConditions());
		return couponDaolmpl.queryCondition(qo, pr);
	}

	/**
	 * @Title: receiveUserCoupon @Description: 领取优惠卷 @param 设定文件 @return void
	 *         返回类型 @throws
	 */
	@Transactional
	public void receiveUserCoupon(String couponId, String couponNum, String userId) {
		Coupon shopCoupon = couponDao.findById(couponId);
		if (null == shopCoupon) {
			throw new RestException("没有该优惠券");
		}
		int num = shopCoupon.getIssueNum();

		UserCoupon userCoupon = null;
		if (null != userCouponDao.findCouponId(userId, couponId)) {
			userCoupon = userCouponDao.findCouponId(userId, couponId);
		} else {
			userCoupon = new UserCoupon();
		}
		if(userCoupon.getCouponNum()>=shopCoupon.getCouponNum()){
			throw new RestException("可领优惠卷已无");
		}
		if (num <= shopCoupon.getClaimNum()) {
			throw new RestException("优惠卷领取完了");
		}
		userCoupon.setCouponId(couponId);
		userCoupon.setCouponNum(userCoupon.getCouponNum() + Integer.valueOf(couponNum));
		userCoupon.setCouponHaveNum(userCoupon.getCouponHaveNum() + Integer.valueOf(couponNum));
		userCoupon.setUserId(userId);
		userCouponDao.save(userCoupon);
		shopCoupon.setIssueNum(shopCoupon.getIssueNum());
		shopCoupon.setClaimNum(shopCoupon.getClaimNum() + 1);
		couponDao.save(shopCoupon);
	}

	/**
	 * 删除用户优惠券
	 * 
	 * @param roleId
	 */
	@Transactional
	public void delUserCoupon(String userCouponId) {
		// 删除用户优惠券
		userCouponDao.delete(userCouponId);
	}
}
