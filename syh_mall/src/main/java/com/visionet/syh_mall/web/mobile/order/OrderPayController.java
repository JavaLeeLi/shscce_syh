package com.visionet.syh_mall.web.mobile.order;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.service.order.OrderPayService;
import com.visionet.syh_mall.web.BaseController;

/**
 * 订单支付Controller
 * 
 * @author xiaofb
 * @time 2017年10月14日
 */
@RestController
@RequestMapping(value = "api/order")
public class OrderPayController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(OrderPayController.class);

	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private ShopRepository shopDao;

	/**
	 * @Title: marketPay @Description: 营销支付 @param @param param @param @return
	 *         设定文件 @return Object 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/consumerPay", method = RequestMethod.POST)
	@Log(name = "营销支付", model = "支付模块")
	public Object consumerPay(@RequestBody Map<String, String> param) {
		logger.info("营销支付请求参数:{}", param);
		Map<String,Object> result = null;
		try {
			String marketingServiceId = param.get("marketingServiceId");
			String marketingServiceTime = param.get("marketingServiceTime");
			String payMethod = param.get("payMethod"); // 支付方式
			String amount = param.get("amount"); // 支付金额(元)
			String frontUrl = param.get("frontUrl");
			String orderNo = SequenceUUID.getOrderIdByUUId("B");
			String bankCode = param.get("bankCode");
			String userId = getCurrentUserId();
			Shop shop = shopDao.findByUserId(userId);
			if (shop.getId() == null) {
				throw new RestException("店铺不存在");
			}
			result = orderPayService.consumerPay(userId, payMethod, amount, orderNo, bankCode, marketingServiceId,
					marketingServiceTime, frontUrl, shop.getId());
		} catch (RestException re) {
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success((Object) result);
	}

	/**
	 * 订单支付
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/orderPay", method = RequestMethod.POST)
	@Log(name = "订单支付", model = "支付模块")
	public Object queryList(@RequestBody Map<String, String> param) {
		logger.info("订单支付请求参数:{}", param);
		Map<String, String> result = null;
		try {
			String payMethod = param.get("payMethod"); // 支付方式
			String amount = param.get("amount"); // 支付金额(元)
			String sourceOrderNo = param.get("orderNo");
			String orderNo = SequenceUUID.getOrderIdByUUId("B");
			String bankCode = param.get("bankCode");
			String frontUrl = param.get("frontUrl");
			String verificationCode = param.get("verificationCodePay");
			String userId = getCurrentUserId();
			result = orderPayService.orderPay(userId, payMethod, amount, orderNo, bankCode, frontUrl,sourceOrderNo,verificationCode);
			
		} catch (RestException re) {
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success((Object) result);
	}

	/**
	 * 确认收货
	 * 
	 * @param param
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/confirmGoods", method = RequestMethod.POST)
	@Log(name = "确认收货", model = "收货模块")
	public Object confirmGoods(@RequestBody Map<String, String> param) {
		logger.info("确认收货请求参数:{}", param);
		try {
			String orderNo = param.get("orderNo");
			String flowNo = param.get("flowNo");
			orderPayService.agentPay(orderNo, getCurrentUserId(), 0,flowNo);
		} catch (RestException re) {
			logger.error("确认收货异常", re);
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			logger.error("确认收货异常", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: confirmationPay @Description: 密码确认支付 @param @param
	 *         param @param @return 设定文件 @return Object 返回类型 @throws
	 */
	/*@RequiresAuthentication
	@RequestMapping(value = "/confirmationPay", method = RequestMethod.POST)
	@Log(name = "余额支付确认支付", model = "支付模块")
	public Object confirmationPay(@RequestBody Map<String, String> param) {
		logger.info("余额支付确定支付(前台+密码)请求参数:{}", param);
		Map<String, Object> result = null;
		try {
			String jumpUrl = param.get("jumpUrl"); // 跳转链接
			String orderNo = param.get("orderNo"); // 订单号
			String userId = getCurrentUserId();
			result = orderPayService.confirmationPay(userId, orderNo, jumpUrl);
		} catch (RestException re) {
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			logger.error("余额支付确定支付(前台+密码)异常", e);
			e.printStackTrace();
		}
		return BaseReturnVo.success(result);
	}*/

	/**
	 * 余额支付确定支付(后台+验证码)
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/blanceConfirmPay", method = RequestMethod.POST)
	@Log(name = "余额支付确认支付", model = "支付模块")
	public Object blanceConfirmPay(@RequestBody Map<String, String> param) {
		logger.info("余额支付确定支付(后台+验证码)请求参数:{}", param);
		String result = null;
		try {
			String vifycode = param.get("vifycode"); // 验证码
			String orderNo = param.get("orderNo"); // 订单号
			String userId = getCurrentUserId();
			orderPayService.blanceConfirmPay(userId, orderNo, vifycode);
		} catch (RestException re) {
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			logger.error("余额支付确定支付(后台+验证码)异常", e);
			e.printStackTrace();
		}
		return BaseReturnVo.success((Object) result);
	}

}
