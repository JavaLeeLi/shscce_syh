package com.visionet.syh_mall.web.mobile.mbr;

import java.text.ParseException;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.marketing.CouponService;
import com.visionet.syh_mall.vo.message.CouponQo;
import com.visionet.syh_mall.vo.message.CouponVo;
import com.visionet.syh_mall.web.BaseController;
import com.visionet.syh_mall.web.mobile.message.MessageController;

/**
 * @Author DM
 * @version ：2017年9月11日上午11:57:43 实体类
 */
@RestController
@RequestMapping(value = "api/mbr")
public class CouponController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	@Autowired
	private CouponService couponService;

	@RequiresAuthentication
	@RequestMapping(value = "/receiveUserCoupon", method = RequestMethod.POST)
	@Log(name="领取用户优惠券",model="优惠券模块")
	public BaseReturnVo<Object> receiveUserCoupon(@RequestBody Map<String, String> param) {
		logger.info("领取用户优惠劵请求参数：{}", param);
		try {
			couponService.receiveUserCoupon(param.get("userCouponId"),param.get("couponNum"),getCurrentUserId());
		} catch (RestException e) {
			logger.error("领取用户优惠劵异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("领取用户优惠劵异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("领取成功");
	}
	
	/**
	 * 获取用户优惠劵列表
	 * 
	 * @param roleID
	 * @return
	 * @throws ParseException
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getUserCoupons", method = RequestMethod.POST)
	public BaseReturnVo<Object> queryCouponList(@RequestBody CouponQo qo) throws Exception {
		logger.info("获取用户优惠劵列表");
		qo.setUserId(getCurrentUserId());
		Page<CouponVo> list = couponService.queryCouponList(qo);
		return BaseReturnVo.success(list);
	}

	/**
	 * 删除用户优惠劵
	 * 
	 * @param roleID
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/delUserCoupon", method = RequestMethod.POST)
	@Log(name="删除用户优惠券",model="优惠券模块")
	public BaseReturnVo<Object> delUserCoupon(@RequestBody Map<String, String> param) {
		logger.info("删除用户优惠劵请求参数：{}", param);
		try {
			couponService.delUserCoupon(param.get("userCouponId"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常，删除失败");
		}
		return BaseReturnVo.success("删除成功");
	}
}
