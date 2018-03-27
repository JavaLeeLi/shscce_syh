package com.visionet.syh_mall.service.thridAccount;

import ime.service.util.RSAUtil;

import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;

/**
 * 第三方支付账户管理 soa 充值、提现
 * 
 * @author xiaofb
 * @time 2017年10月10日
 */
@Service
public class SoaAccountService {
	private static final Logger logger = LoggerFactory.getLogger(SoaAccountService.class);

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserAuthenticationRepository userAuthenticationDao;

	/**
	 * @Title: marketHostCollect @Description: 营销服务的消费申请 @param @param
	 *         bizUserId @param @param amount @param @param
	 *         payMethodName @param @param bizOrderNo @param @param
	 *         bankCode @param @return @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> marketHostCollect(User user, Long amount, String payMethod, String bizOrderNo,
			String bankCode, String marketingServiceId, String marketingServiceTime, String frontUrl, String shopId)
					throws Exception {
		logger.info("thridPay营销支付的消费申请  申请start.........bizOrderNo:{}", bizOrderNo);
		Map<String, String> returnMap = null;
		// 参数组装
		JSONObject param = marketHostCollectParam(user, amount, bizOrderNo, payMethod, bankCode, marketingServiceId,
				marketingServiceTime, frontUrl, shopId);
		logger.info("thridPay营销支付的消费申请  请求参数：{}", param);
		JSONObject response = PayClientUtil.getSOAClient().request("OrderService", "consumeApply", param);
		logger.info("thridPay营销支付的消费申请响应：{}", response);
		Map result = response.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("thridPay营销支付的消费申请失败bizOrderNo:{}", bizOrderNo);
			throw new RestException(String.valueOf(result.get("errorCode")), String.valueOf(result.get("message")));
		}
		if ("OK".equals(result.get("status"))) {
			JSONObject returnValue = new JSONObject(String.valueOf(result.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}

	/**
	 * @Title: marketHostCollectParam @Description: 营销服务的消费申请组装参数 @param @param
	 *         bizUserId @param @param amount @param @param
	 *         bizOrderNo @param @param payMethodName @param @param
	 *         bankCode @param @return @param @throws JSONException 设定文件 @return
	 *         JSONObject 返回类型 @throws
	 */
	private JSONObject marketHostCollectParam(User user, Long amount, String bizOrderNo, String payMethod,
			String bankCode, String marketingServiceId, String marketingServiceTime, String frontUrl, String shopId)
					throws JSONException {
		JSONObject extendInfo = new JSONObject();
		extendInfo.put("marketingServiceId", marketingServiceId);
		extendInfo.put("marketingServiceTime", marketingServiceTime);
		extendInfo.put("shopId", shopId);
		extendInfo.put("payMethodName", payMethod);

		// 组装请求参数
		JSONObject param = new JSONObject();
		if ("BALANCE".equals(payMethod)) {
			param.put("bizOrderNo", bizOrderNo);
			param.put("payerId", user.getId());
			param.put("recieverId", "#yunBizUserId_B2C#");
			param.put("amount", amount);
			param.put("fee", 0);
			if (user.getMemberType() == 3) {
				param.put("validateType", 2);// 验证方式支付
			}
			// param.put("frontUrl", PayClientUtil.notifyAddress + frontUrl);
			param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/marketAgentPayNotify");
			param.put("payMethod", getPayMethod(payMethod, amount, bankCode, user.getId()));
			param.put("industryCode", "1914");
			param.put("industryName", "交易市场");
			param.put("source", 1);
			param.put("summary", "营销购买"); // 交易内容
			param.put("extendInfo", extendInfo.toString());
		} else {
			param.put("bizOrderNo", bizOrderNo);
			param.put("payerId", user.getId());
			param.put("recieverId", "#yunBizUserId_B2C#");
			param.put("amount", amount);
			param.put("fee", 0);
			param.put("validateType", 0);// 无验证方式支付
			param.put("frontUrl", PayClientUtil.notifyAddress + frontUrl);
			param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/marketAgentPayNotify");
			param.put("payMethod", getPayMethod(payMethod, amount, bankCode, user.getId()));
			param.put("industryCode", "1914");
			param.put("industryName", "交易市场");
			param.put("source", 1);
			param.put("summary", "营销购买"); // 交易内容
			param.put("extendInfo", extendInfo.toString());
		}

		return param;
	}

	/**
	 * 充值申请 （账户余额充值）
	 * 
	 * @param bizUserId
	 *            业务用户id
	 * @param amount
	 *            交易金额（分）
	 * @param payMethodName
	 *            支付方式名称
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> applyDeposit(String bizUserId, Long amount, String payMethodName, String bizOrderNo,
			String bankCode, String frontUrl) throws Exception {
		logger.info("thridPay充值申请start.........");
		Map<String, String> returnMap = null;
		String accountSetNo = PayClientUtil.accountSetNo;
		System.out.println("支付方式名称："+payMethodName);
		// 参数组装
		JSONObject param = applyDepositParam(bizUserId, bizOrderNo, accountSetNo, amount, payMethodName, bankCode,
				frontUrl);
		logger.info("thridPay充值申请请求参数：{}", param);
		JSONObject response = PayClientUtil.getSOAClient().request("OrderService", "depositApply", param);
		logger.info("thridPay充值申请响应：{}", response);
		Map result = response.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("充值申请失败bizOrderNo:{}", bizOrderNo);
			throw new RestException(String.valueOf(result.get("errorCode")), String.valueOf(result.get("message")));
		}
		if ("OK".equals(result.get("status"))) {
			JSONObject returnValue = new JSONObject(String.valueOf(result.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}

	/**
	 * 托管代收申请（支付订单）
	 * 
	 * @param bizUserId
	 *            业务用户id
	 * @param amount
	 *            交易金额（分）
	 * @param payMethodName
	 *            支付方式名称
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> applyHostCollect(User user, Long amount, String payMethodName, String bizOrderNo,
			String bankCode, String frontUrl, String sourceOrderNo) throws Exception {
		logger.info("thridPay托管代收申请（支付订单）申请start.........bizOrderNo:{}", bizOrderNo);
		Map<String, String> returnMap = null;
		// 参数组装
		JSONObject param = applyHostCollectParam(user, amount, bizOrderNo, payMethodName, bankCode, frontUrl,
				sourceOrderNo);
		logger.info("thridPay托管代收申请（支付订单）请求参数：{}", param);
		JSONObject response = PayClientUtil.getSOAClient().request("OrderService", "agentCollectApply", param);
		logger.info("thridPay托管代收申请（支付订单）响应：{}", response);
		Map result = response.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("thridPay托管代收申请（支付订单）失败bizOrderNo:{}", bizOrderNo);
			throw new RestException(String.valueOf(result.get("errorCode")), String.valueOf(result.get("message")));
		}
		if ("OK".equals(result.get("status"))) {
			JSONObject returnValue = new JSONObject(String.valueOf(result.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}

	/**
	 * 网关确认支付url
	 * 
	 * @param bizUserId
	 * @param bizOrderNo
	 * @param code
	 */
	@SuppressWarnings("rawtypes")
	public String confirmPay(String userId, String orderNo) {
		logger.info("thridPay网关确认支付start.........");
		String resUrl = null;
		try {
			String bizUserId = userId;
			String bizOrderNo = orderNo; // 商户系统订单号
			String consumerIp = "127.0.0.2"; // ip
			// String verificationCode = ""; //如果是纯网关，则不传
			String sysid = PayClientUtil.sysid;
			String timestamp = DateUtil.convertToString(new Date()); // 时间戳
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("bizOrderNo", bizOrderNo);
			param.put("consumerIp", consumerIp);
			JSONObject req = new JSONObject();
			req.put("service", "OrderService");
			req.put("method", "pay");
			req.put("param", param);
			// 签名
			StringBuilder sb = new StringBuilder();
			String reqStr1 = req.toString();
			sb.append(sysid).append(reqStr1).append(timestamp);
			String sign = RSAUtil.sign(PayClientUtil.getPrivateKey(), sb.toString());
			Map<String, String> map = new HashMap<String, String>();
			map.put("sysid", sysid);
			map.put("sign", sign);
			map.put("timestamp", timestamp);
			map.put("v", "1.0");
			map.put("req", req.toString());
			sb.setLength(0);
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (java.util.Map.Entry) iterator.next();
				sb.append((String) entry.getKey()).append("=")
						.append(URLEncoder.encode((String) entry.getValue(), "UTF-8")).append("&");
			}
			resUrl = PayClientUtil.gateWayUrl + "?" + sb.toString();
			logger.info("thridPay网关确认支付end.........resUrl：{}", resUrl);
		} catch (Exception e) {
			logger.error("thridPay网关确认支付", e);
			e.printStackTrace();
		}
		return resUrl;
	}

	/**
	 * 余额确认支付（后台+验证码）
	 * 
	 * @param bizUserId
	 * @param bizOrderNo
	 * @param code
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String balanceConfirmPay(String userId, String orderNo, String code) throws Exception {
		logger.info("thridPay余额确认支付start.........orderNo:{}", orderNo);
		JSONObject param = new JSONObject();
		param.put("bizUserId", userId);
		param.put("bizOrderNo", orderNo);
		param.put("verificationCode", code);
		param.put("consumerIp", "127.0.0.2");
		JSONObject reps = PayClientUtil.getSOAClient().request("OrderService", "pay", param);
		logger.info("thridPay网关确认支付end.........reps：{}", reps);
		Map result = reps.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("thridPay余额确认支付失败bizOrderNo:{}", orderNo);
			throw new RestException(String.valueOf(result.get("errorCode")), String.valueOf(result.get("message")));
		}
		return null;
	}

	/**
	 * 支付方式
	 * 
	 * @param payMethodName
	 *            支付方式名称
	 * @param amount
	 *            金额 （分）
	 * @return
	 * @throws JSONException
	 */
	private JSONObject getPayMethod(String payMethodName, Long amount, String bankCode, String userId)
			throws JSONException {
		JSONObject payMethod = new JSONObject();
		if ("BALANCE".equals(payMethodName)) { // 账户余额支付
			JSONArray accountPay = new JSONArray();
			JSONObject balance = new JSONObject();
			balance.put("accountSetNo", PayClientUtil.accountSetNo);
			balance.put("amount", amount);
			accountPay.put(balance);
			payMethod.put("BALANCE", accountPay);
		}
		// 对公账号
		if ("WITHHOLD_TLT".equals(payMethodName)) {
			UserAuthentication authentication = userAuthenticationDao.findByUserId(userId);
			JSONObject withholdall = new JSONObject();
			RSAUtil rsaUtil = new RSAUtil((RSAPublicKey) PayClientUtil.getPublicKey(),
					(RSAPrivateKey) PayClientUtil.getPrivateKey());
			try {
				// 银行卡号加密
				withholdall.put("bankCardNo", rsaUtil.encrypt(authentication.getAccountNo()));
				withholdall.put("amount", amount);
			} catch (Exception e) {
				e.printStackTrace();
			}
			payMethod.put("WITHHOLD_TLT", withholdall);
		}
		if ("GATEWAY".equals(payMethodName)) { // 网关支付
			JSONObject gateway = new JSONObject();
			gateway.put("bankCode", bankCode); // 银行编码
			gateway.put("payType", 1); // 借记卡
			gateway.put("amount", amount);
			payMethod.put("GATEWAY", gateway);
		}
		if ("WECHAT_PUBLIC".equals(payMethodName)) { // 微信js支付（公众号）
			User user = userRepo.findOne(userId);
			if (StringUtils.isEmpty(user.getWechatOpenId())) {
				throw new RestException("请先关注公众号");
			}
			JSONObject wechatPublic = new JSONObject();
			wechatPublic.put("payType", "no_credit");
			wechatPublic.put("acct", user.getWechatOpenId()); // 微信 JS 支付
																// openid——微信分配
			wechatPublic.put("amount", amount);
			payMethod.put("WECHAT_PUBLIC", wechatPublic);
		}
		if ("SCAN_WEIXIN".equals(payMethodName)) { // 微信正扫
			JSONObject scanWeixin = new JSONObject();
			scanWeixin.put("payType", "W01");
			scanWeixin.put("amount", amount);
			payMethod.put("SCAN_WEIXIN", scanWeixin);
		}
		if ("SCAN_ALIPAY".equals(payMethodName)) { // 支付宝正扫
			JSONObject scanAlipay = new JSONObject();
			scanAlipay.put("payType", "A01");
			scanAlipay.put("amount", amount);
			payMethod.put("SCAN_ALIPAY", scanAlipay);
		}
		return payMethod;
	}

	/**
	 * 充值申请参数组装
	 * 
	 * @param bizUserId
	 * @param bizOrderNo
	 * @param accountSetNo
	 * @param amount
	 * @param payMethodName
	 * @return
	 * @throws JSONException
	 */
	private JSONObject applyDepositParam(String bizUserId, String bizOrderNo, String accountSetNo, Long amount,
			String payMethodName, String bankCode, String frontUrl) throws JSONException {
		JSONObject param = new JSONObject();
		param.put("bizUserId", bizUserId);
		param.put("bizOrderNo", bizOrderNo);
		param.put("accountSetNo", accountSetNo);
		param.put("amount", amount);
		param.put("fee", 0);
		param.put("frontUrl", PayClientUtil.notifyAddress + frontUrl);
		param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/payBackNotify");
		// param.put("ordErexpireDatetime", ordErexpireDatetime);
		param.put("payMethod", getPayMethod(payMethodName, amount, bankCode, bizUserId));
		param.put("industryCode", "1914");
		param.put("industryName", "交易市场");
		param.put("source", 1);
		param.put("summary", "用户充值"); // 交易内容
		param.put("extendInfo", "");
		return param;
	}

	/**
	 * 托管代收参数组装
	 * 
	 * @param bizUserId
	 * @param amount
	 * @param bizOrderNo
	 * @param payMethodName
	 * @return
	 * @throws JSONException
	 */
	private JSONObject applyHostCollectParam(User user, Long amount, String bizOrderNo, String payMethodName,
			String bankCode, String frontUrl, String sourceOrderNo) throws JSONException {
		// 托管代收订单收款列表
		JSONArray recieverList = getRecieverList(sourceOrderNo, amount);
		JSONObject param = new JSONObject();
		JSONObject extendInfo = new JSONObject();
		extendInfo.put("sourceOrderNo", sourceOrderNo);
		extendInfo.put("payMethodName", payMethodName);
		// 组装请求参数
		if ("BALANCE".equals(payMethodName)) {
			if (user.getMemberType() == 3) {
				param.put("validateType", 2);// 密码验证方式支付
			}
			param.put("bizOrderNo", bizOrderNo);
			param.put("payerId", user.getId());
			param.put("recieverList", recieverList);
			// param.put("goodsType", 3L);
			// param.put("goodsNo", 3L);
			param.put("tradeCode", "3001");
			param.put("amount", amount);
			param.put("fee", 0);
			// param.put("accountSetNo", accountSetNo);
			// param.put("frontUrl", PayClientUtil.notifyAddress + frontUrl);
			param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/signalAgentPayNotify");
			// param.put("ordErexpireDatetime", ordErexpireDatetime);
			param.put("payMethod", getPayMethod(payMethodName, amount, bankCode, user.getId()));
			param.put("industryCode", "1914");
			param.put("industryName", "交易市场");
			param.put("source", 1);
			param.put("summary", "订单支付"); // 交易内容
			param.put("extendInfo", extendInfo.toString());
		} else {
			param.put("bizOrderNo", bizOrderNo);
			param.put("payerId", user.getId());
			param.put("recieverList", recieverList);
			// param.put("goodsType", 3L);
			// param.put("goodsNo", 3L);
			param.put("tradeCode", "3001");
			param.put("amount", amount);
			param.put("fee", 0);
			param.put("validateType", 0);// 免密支付
			// param.put("accountSetNo", accountSetNo);
			param.put("frontUrl", PayClientUtil.notifyAddress + frontUrl);
			param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/signalAgentPayNotify");
			// param.put("ordErexpireDatetime", ordErexpireDatetime);
			param.put("payMethod", getPayMethod(payMethodName, amount, bankCode, user.getId()));
			param.put("industryCode", "1914");
			param.put("industryName", "交易市场");
			param.put("source", 1);
			param.put("summary", "订单支付"); // 交易内容
			param.put("extendInfo", extendInfo.toString());

		}
		return param;
	}

	/**
	 * 获取收款人列表
	 * 
	 * @param orderNo
	 *            订单号
	 * @return
	 * @throws JSONException
	 */
	private JSONArray getRecieverList(String orderNo, Long amount) throws JSONException {
		Order order = orderRepo.findByOrderSn(orderNo);
		if (null == order || StringUtils.isEmpty(order.getShopOwnerId())) {
			throw new RestException(HttpStatus.ACCEPTED, "订单信息有误");
		}
		JSONArray recieverList = new JSONArray();
		JSONObject reciever1 = new JSONObject();
		reciever1.put("bizUserId", order.getShopOwnerId());
		reciever1.put("amount", amount);
		recieverList.put(reciever1);
		return recieverList;
	}

}
