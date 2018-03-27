package com.visionet.syh_mall.service.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.entity.integralRule.IntegralRule;
import com.visionet.syh_mall.entity.mbr.IntegralFlow;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.PaymentFlow;
import com.visionet.syh_mall.entity.order.TradeSetting;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.TradeSettingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.integralRule.IntegralFlowRepository;
import com.visionet.syh_mall.repository.integralRule.IntegralRuleRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.order.PaymentFlowRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.service.goods.AuctionJobService;
import com.visionet.syh_mall.service.mobile.GoodsChannelRuleService;
import com.visionet.syh_mall.service.mobile.channel.ChannelService;
import com.visionet.syh_mall.service.thridAccount.PayClientUtil;
import com.visionet.syh_mall.service.thridAccount.SoaAccountService;
import com.visionet.syh_mall.service.thridAccount.SoaAgentPayService;
import com.visionet.syh_mall.service.thridAccount.SoaPostagePayService;

import ime.service.util.RSAUtil;

/**
 * 订单支付service
 * 
 * @author xiaofb
 * @time 2017年10月16日
 */
@Service
public class OrderPayService extends BaseService {

	@Autowired
	private SoaAccountService soaAccountService;
	@Autowired
	private SoaAgentPayService soaAgentPayService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SoaPostagePayService soaPostagePayService;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private PaymentFlowService payFlowService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private KeyMappingRepository mappingDao;
	@Autowired
	private AuctionJobService auctionJobService;
	@Autowired
	private TradeSettingRepository settingDao;
	@Autowired
	private UserAccountFlowRepository userAccountFlowDao;
	@Autowired
	private GoodsChannelRuleService channelRuleService;

	@Autowired
	private IntegralFlowRepository integralFlowDao;
	@Autowired
	private IntegralRuleRepository integralRuleDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private PaymentFlowRepository paymentFlowRepo;

	private final static Logger logger = LoggerFactory.getLogger(OrderPayService.class);

	/**
	 * 营销支付
	 * 
	 * @param userId
	 *            用户id
	 * @param payMethod
	 *            支付方式
	 * @param amount
	 *            交易金额(元)
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> consumerPay(String userId, String payMethod, String amount, String orderNo,
			String bankCode, String marketingServiceId, String marketingServiceTime, String frontUrl, String shopId)
					throws Exception {
		// 验证余额是否充足
		User user = userDao.findById(userId);
		if ("BALANCE".equals(payMethod)) {
			if (user.getPayPwd().equals("0")) {
				throw new RestException("用户未设置密码,请先设置密码");
			}
			UserAccount userAccount = userAccountService.getUserAccountInfo(userId);
			if (null == userAccount || -1 == userAccount.getWithdrawal().compareTo(new BigDecimal(amount))) {
				throw new RestException("账户可用余额不足");
			}
		}
		Long amt = Long.valueOf(AmountUtils.changeY2F(amount)); // 支付金额 元转分

		// 提前添加待确认流水
		if (userAccountFlowDao.findByOrderNo(orderNo) == null) {
			UserAccountFlow flow = new UserAccountFlow();
			flow.setOrderNo(orderNo);
			flow.setStatus("等待确认");
			flow.setPayMethod(payMethod);
			userAccountFlowDao.save(flow);
		}

		// 提交消费申请
		Map<String, String> result = soaAccountService.marketHostCollect(user, amt, payMethod, orderNo, bankCode,
				marketingServiceId, marketingServiceTime, frontUrl, shopId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 提交托管代收申请
		if ("BALANCE".equals(payMethod)) {
			String payUrl = null;
			if (user.getMemberType() == 3) {
				payUrl = confirmationPay(userId, orderNo, frontUrl);
			}
			resultMap.put("payUrl", payUrl);
			resultMap.put("orderNo", orderNo);
			return resultMap;
		}
		// 扫码支付返回二维码字符串
			resultMap.put("payUrl", result.get("payInfo"));
			if ("SCAN_WEIXIN".equals(payMethod) || "SCAN_ALIPAY".equals(payMethod)) {
			resultMap.put("orderNo", orderNo);
			return resultMap;
		}
		// 网关确认支付url
		if ("GATEWAY".equals(payMethod)) {
			String payUrl = soaAccountService.confirmPay(userId, orderNo);
			resultMap.put("payUrl", payUrl);
			resultMap.put("orderNo", orderNo);
			return resultMap;
		}
		return null;
	}

	/**
	 * 订单支付
	 * 
	 * @param userId
	 *            用户id
	 * @param payMethod
	 *            支付方式
	 * @param amount
	 *            交易金额(元)
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> orderPay(String userId, String payMethod, String amount, String orderNo, String bankCode,
			String frontUrl, String sourceOrderNo, String verificationCode) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		// 验证余额是否充足
		User user = userDao.findById(userId);
		if ("BALANCE".equals(payMethod)) {
			if (user.getMemberType() == 3) {
				if (user.getPayPwd().equals("0")) {
					throw new RestException("用户未设置密码,请先设置密码");
				}
			}
			UserAccount userAccount = userAccountService.getUserAccountInfo(userId);
			if (null == userAccount || -1 == userAccount.getWithdrawal().compareTo(new BigDecimal(amount))) {
				throw new RestException("账户可用余额不足");
			}
		}
		Long amt = Long.valueOf(AmountUtils.changeY2F(amount)); // 支付金额 元转分

		// 提前添加待确认流水
		if (userAccountFlowDao.findByOrderNo(orderNo) == null) {
			UserAccountFlow flow = new UserAccountFlow();
			flow.setOrderNo(orderNo);
			flow.setStatus("等待确认");
			userAccountFlowDao.save(flow);
		}
		Order order = orderRepo.findByOrderSn(sourceOrderNo);
		order.setFlowNo(orderNo);
		orderRepo.save(order);
		// 提交托管代收申请
		Map<String, String> result = soaAccountService.applyHostCollect(user, amt, payMethod, orderNo, bankCode,
				frontUrl, sourceOrderNo);

		// 提交托管代收申请
		if ("BALANCE".equals(payMethod)) {
			String payUrl = null;
			if (user.getMemberType() == 3) {
				payUrl = confirmationPay(userId, orderNo, frontUrl);
			}
			param.put("payUrl", payUrl);
			param.put("orderNo", orderNo);
			return param;
		}
		//微信公众号
		if ("WECHAT_PUBLIC".equals(payMethod)) {
			logger.info("微信公众号:{}", result.get("payInfo"));
			param.put("orderNo", orderNo);
			param.put("payInfo", result.get("payInfo"));
			return param;
		}

		// 扫码支付返回二维码字符串
		if ("SCAN_WEIXIN".equals(payMethod) || "SCAN_ALIPAY".equals(payMethod)) {
			param.put("payUrl", result.get("payInfo"));
			param.put("orderNo", orderNo);
			return param;
		}
		// 网关确认支付url
		if ("GATEWAY".equals(payMethod)) {
			String payUrl = soaAccountService.confirmPay(userId, orderNo);
			param.put("payUrl", payUrl);
			param.put("orderNo", orderNo);
			return param;
		}

		return param;
	}

	private void getIntegralRule(String userId, BigDecimal totalPrice) {
		List<IntegralRule> findAll = integralRuleDao.findByIntegralType(1);
		for (IntegralRule integralRule : findAll) {
			// 有积分活动时
			if (integralRule.getMinSumForIntegral() != 0) {
				Integer minSumForIntegral = integralRule.getMinSumForIntegral();// 积分规则最小金额
				Integer integralNum = integralRule.getIntegralNum();// 积分数量
				BigDecimal value = totalPrice.divideToIntegralValue(new BigDecimal(minSumForIntegral));
				BigInteger bigInteger = value.toBigInteger();
				int intValue = bigInteger.intValue();
				int totalIntegral = intValue * integralNum;
				User user = userDao.findById(userId);
				user.setUserTotalIntegral(user.getUserTotalIntegral() + totalIntegral);
				user.setUserValidIntegral(user.getUserValidIntegral() + totalIntegral);
				userDao.save(user);
				IntegralFlow integralFlow = new IntegralFlow();
				integralFlow.setUserId(userId);
				integralFlow.setOperateDesc("购买商品");
				integralFlow.setIngegralNum(totalIntegral);
				integralFlow.setType(0);
				integralFlow.setCreateTime(DateUtil.getCurrentDate());
				integralFlow.setUpdateTime(DateUtil.getCurrentDate());
				integralFlowDao.save(integralFlow);
			}
		}
	}

	/**
	 * 托管代付
	 * 
	 * @param orderNo
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void agentPay(String orderNo, String userId, Integer source, String flowNo) throws Exception {
		String bussOrderNo = SequenceUUID.getOrderIdByUUId("B"); // 托管代付订单号
		Order order = orderRepo.findByOrderSn(orderNo); // 源订单信息
		PaymentFlow paymentFlow = paymentFlowRepo.findBySourceOrderNo(orderNo);
		if (null != paymentFlow) {
			throw new RestException("您的订单已经提交确认收货");
		}
		if (order.getOrderStatusCode().equals("order_uncomment")) {
			throw new RestException("您的订单已经提交确认收货，等待后台确认后评价订单");
		}
		getIntegralRule(userId, order.getTotalPrice());
		// 代付流水记录
		PaymentFlow payFlow = new PaymentFlow();
		payFlow.setBussOrderNo(bussOrderNo);
		payFlow.setCreateTime(DateUtil.getCurrentDate());
		payFlow.setGatheringUserId(order.getShopOwnerId()); // 收款用户
		payFlow.setPayUserId(userId);
		payFlow.setStatus("已提交代付申请");
		if (null != order.getIsRefund() && "order_closed".equals(order.getOrderStatusCode())) {
			payFlow.setPaymentAmt(order.getExpressFee()); // 将邮费给卖家
			payFlow.setSourceOrderNo(orderNo);
			payFlow.setUpdateTime(DateUtil.getCurrentDate());
			payFlowService.insertPaymentFlow(payFlow);
			UserAccountFlow accountFlow = new UserAccountFlow();
			accountFlow.setOrderNo(bussOrderNo);
			accountFlow.setAmt(order.getExpressFee());
			accountFlow.setStatus("等待确认");
			userAccountFlowDao.save(accountFlow);
			soaPostagePayService.signalAgentPay(bussOrderNo, orderNo);
			return;
		}
		if (null == order.getIsRefund()) {
			payFlow.setPaymentAmt(MathUtils.add(order.getTotalPrice(), order.getExpressFee())); // 代付总金额
		}
		payFlow.setSourceOrderNo(orderNo);
		payFlow.setUpdateTime(DateUtil.getCurrentDate());
		payFlowService.insertPaymentFlow(payFlow);

		// 交易的手续费
		BigDecimal transactionFee = channelService.getServiceCharge(orderNo);
		// 平台成本费用 %0.3s
		KeyMapping mapping = mappingDao.findByKeyCode("costFee");
		BigDecimal platformCost = MathUtils.mul(transactionFee, new BigDecimal(mapping.getValueDesc()));

		// 分销奖励的金额
		KeyMapping mapping1 = mappingDao.findByKeyCode("platformFee");
		BigDecimal platform = MathUtils.mul(MathUtils.sub(transactionFee, platformCost),
				MathUtils.sub(new BigDecimal(1), new BigDecimal(mapping1.getValueDesc())));

		// 在佣金表中添加所有会员分销的佣金
		channelService.retailDetail(order, platform);

		// 卖家用户流水
		UserAccountFlow sellUserFlow = new UserAccountFlow();
		sellUserFlow.setUserId(order.getShopOwnerId());
		sellUserFlow.setContent("店铺交易入账");
		sellUserFlow.setFlowType("收入");
		sellUserFlow.setOrderNo(bussOrderNo);
		sellUserFlow.setStatus("等待确认");
		userAccountFlowDao.save(sellUserFlow);

		// 卖家用户商品分销出账流水
		UserAccountFlow sellUserChannelFlow = new UserAccountFlow();
		sellUserChannelFlow.setUserId(order.getShopOwnerId());
		sellUserChannelFlow.setBusinessType("交易");
		sellUserChannelFlow.setFlowType("支出");
		sellUserChannelFlow.setContent("商品分销出账");
		sellUserChannelFlow.setOrderNo(bussOrderNo);
		sellUserChannelFlow.setStatus("等待确认");
		userAccountFlowDao.save(sellUserChannelFlow);

		// 卖家平台手续费流水
		UserAccountFlow sellUserFeeFlow = new UserAccountFlow();
		sellUserFeeFlow.setUserId(order.getShopOwnerId());
		sellUserFeeFlow.setBusinessType("交易");
		sellUserFeeFlow.setContent("平台手续费");
		sellUserFeeFlow.setFlowType("支出");
		sellUserFeeFlow.setOrderNo(bussOrderNo);
		sellUserFeeFlow.setStatus("等待确认");
		userAccountFlowDao.save(sellUserFeeFlow);

		// 商品分销用户
		String shareUserNo = channelRuleService.getGoodChannelUser(order);

		// 分享人流水
		if (shareUserNo != null) {
			UserAccountFlow shareUserFlow = new UserAccountFlow();
			shareUserFlow.setUserId(shareUserNo);
			shareUserFlow.setBusinessType("交易");
			shareUserFlow.setContent("商品分销入账");
			shareUserFlow.setFlowType("收入");
			shareUserFlow.setOrderNo(bussOrderNo);
			shareUserFlow.setStatus("等待确认");
			userAccountFlowDao.save(shareUserFlow);
		}

		// 确认收货分账
		soaAgentPayService.signalAgentPay(bussOrderNo, orderNo, flowNo);

		// 关闭订单评价时限
		Order order2 = orderRepo.findByOrderSn(orderNo);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 3);
		calendar.setTime(order2.getUpdateTime());
		List<TradeSetting> list = (List<TradeSetting>) settingDao.findAll();
		Integer feedbackInterval = list.get(0).getCloseFeedbackInterval();
		calendar.add(Calendar.DAY_OF_YEAR, feedbackInterval);
		auctionJobService.saveCloseReviewJob(orderNo, DateUtil.convertToString(calendar.getTime()));
	}

	/**
	 * @Title: confirmationPay @Description: 密码确认支付 @param @param
	 *         userId @param @param orderNo @param @param jumpUrl @param @throws
	 *         Exception 设定文件 @return void 返回类型 @throws
	 */
	@SuppressWarnings("rawtypes")
	public String confirmationPay(String userId, String orderNo, String jumpUrl) throws Exception {
		logger.info("thridPay余额确认支付start.........orderNo:{}", orderNo);
		JSONObject param = new JSONObject();
		param.put("bizUserId", userId);
		param.put("bizOrderNo", orderNo);
		param.put("jumpUrl", PayClientUtil.notifyAddress + jumpUrl);
		param.put("consumerIp", "127.0.0.2");

		String sysid = PayClientUtil.sysid;
		String timestamp = DateUtil.convertToString(new Date()); // 时间戳

		JSONObject req = new JSONObject();
		req.put("service", "OrderService");
		req.put("method", "pay");
		req.put("param", param);
		// 签名
		StringBuilder sb = new StringBuilder();
		String reqStr1 = req.toString();
		sb.append(sysid).append(reqStr1).append(timestamp);
		String sign = RSAUtil.sign(PayClientUtil.getPrivateKey(), sb.toString());
		sb.setLength(0);
		Map<String, String> map = new HashMap<String, String>();
		map.put("sysid", sysid);
		map.put("sign", sign);
		map.put("timestamp", timestamp);
		map.put("v", "1.0");
		map.put("req", req.toString());
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (java.util.Map.Entry) iterator.next();
			sb.append((String) entry.getKey()).append("=").append(URLEncoder.encode((String) entry.getValue(), "UTF-8"))
					.append("&");
		}
		String hrefStr = sb.toString().substring(0, sb.length() - 1);
		String resUrl = PayClientUtil.payPwdAddress + "payOrder.html" + "?" + hrefStr;
		logger.info("确认支付end.........resUrl：{}", resUrl);
		return resUrl;
	}

	/**
	 * @Title: verificationCodePay @Description: 前台+短信验证码支付 @param @param
	 *         userId @param @param orderNo @param @param jumpUrl @param @param
	 *         verificationCode @param @return @param @throws Exception
	 *         设定文件 @return String 返回类型 @throws
	 */
	/*
	 * @SuppressWarnings("rawtypes") public String verificationCodePay(String
	 * userId, String orderNo, String jumpUrl, String verificationCode) throws
	 * Exception { logger.info("thridPay余额确认支付start.........orderNo:{}",
	 * orderNo); JSONObject param = new JSONObject(); param.put("bizUserId",
	 * userId); param.put("bizOrderNo", orderNo); param.put("jumpUrl",
	 * PayClientUtil.notifyAddress + jumpUrl); param.put("consumerIp",
	 * "127.0.0.2");
	 * 
	 * String sysid = PayClientUtil.sysid; String timestamp =
	 * DateUtil.convertToString(new Date()); // 时间戳
	 * 
	 * JSONObject req = new JSONObject(); req.put("service", "OrderService");
	 * req.put("method", "pay"); req.put("param", param); // 签名 StringBuilder sb
	 * = new StringBuilder(); String reqStr1 = req.toString();
	 * sb.append(sysid).append(reqStr1).append(timestamp); String sign =
	 * RSAUtil.sign(PayClientUtil.getPrivateKey(), sb.toString());
	 * sb.setLength(0); Map<String, String> map = new HashMap<String, String>();
	 * map.put("sysid", sysid); map.put("sign", sign); map.put("timestamp",
	 * timestamp); map.put("v", "1.0"); map.put("req", req.toString()); Iterator
	 * iterator = map.entrySet().iterator(); while (iterator.hasNext()) {
	 * Map.Entry entry = (java.util.Map.Entry) iterator.next();
	 * sb.append((String)
	 * entry.getKey()).append("=").append(URLEncoder.encode((String)
	 * entry.getValue(), "UTF-8")) .append("&"); } String hrefStr =
	 * sb.toString().substring(0, sb.length() - 1); String resUrl =
	 * PayClientUtil.gateWayUrl + "?" + hrefStr;
	 * logger.info("确认支付end.........resUrl：{}", resUrl); return resUrl; }
	 */

	/**
	 * 余额确定支付
	 * 
	 * @param userId
	 * @param orderNo
	 * @param code
	 * @throws Exception
	 */
	public void blanceConfirmPay(String userId, String orderNo, String code) throws Exception {
		soaAccountService.balanceConfirmPay(userId, orderNo, code);
	}
}
