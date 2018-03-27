package com.visionet.syh_mall.web.account;

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
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.account.RechargePayService;
import com.visionet.syh_mall.web.BaseController;

/**
 * 账户充值
 * 
 * @author xiaofb
 * @time 2017年10月12日
 */
@RestController
@RequestMapping("api/recharge")
public class RechargeController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(RechargeController.class);

	@Autowired
	private RechargePayService orderPayService;

	/**
	 * 余额充值
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/rechargeAccount", method = RequestMethod.POST)
	@Log(name = "余额充值", model = "财务模块")
	public Object queryList(@RequestBody Map<String, String> map) {
		logger.info("余额充值支付请求参数:{}", map);
		Map<String, String> result = null;
		try {
			String payMethod = map.get("payMethod"); // 支付方式
			String amount = map.get("amount"); // 支付金额(元)
			String bankCode = map.get("bankCode");
			String frontUrl = map.get("frontUrl");
			String userId = getCurrentUserId();
			result = orderPayService.rechargePay(userId, payMethod, amount, bankCode, frontUrl);
		} catch (RestException re) {
			re.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED, re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BaseReturnVo.success((Object) result);
	}
}
