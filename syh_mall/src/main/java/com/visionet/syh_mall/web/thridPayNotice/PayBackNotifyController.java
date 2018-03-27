package com.visionet.syh_mall.web.thridPayNotice;

import ime.service.util.RSAUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MessageUtils;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.entity.channel.RetailDetail;
import com.visionet.syh_mall.entity.finance.Bond;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.entity.order.OrderRefund;
import com.visionet.syh_mall.entity.order.PaymentFlow;
import com.visionet.syh_mall.entity.service.ShopsMarketing;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.NoteRepository;
import com.visionet.syh_mall.repository.OperateLogRepository;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.finance.BondRepository;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.channel.RetailDetailRepository;
import com.visionet.syh_mall.repository.order.OrderRefundRepository;
import com.visionet.syh_mall.repository.syhservice.ShopsMarketingRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.UserService;
import com.visionet.syh_mall.service.account.UserAccountFlowService;
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.service.finance.FinanceService;
import com.visionet.syh_mall.service.order.OrderPayService;
import com.visionet.syh_mall.service.order.OrderService;
import com.visionet.syh_mall.service.order.PaymentFlowService;
import com.visionet.syh_mall.service.scheduler.QuartzManager;
import com.visionet.syh_mall.service.thridAccount.PayClientUtil;
import com.visionet.syh_mall.service.thridAccount.SoaMemberService;

/**
 * 第三方支付回调通知
 * 
 * @author xiaofb
 * @time 2017年10月11日
 */
@RestController
@RequestMapping(value = "/api/notify")
public class PayBackNotifyController {
	private static final Logger logger = LoggerFactory.getLogger(PayBackNotifyController.class);
	@Autowired
	private UserAccountFlowService accountFlowService;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private OrderService orderservice;
	@Autowired
	private PaymentFlowService paymentFlowService;
	@Autowired
	private OrderRefundRepository orderRefundDao;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private OperateLogRepository operateLogDao;
	@Autowired
	private ShopsMarketingRepository shopMarketDao;
	@Autowired
	private OrderGoodsRepository orderGoodsDao;
	@Autowired
	private RetailDetailRepository retailDetailRepo;
	@Autowired
	private AuctionJobRepository auctionJobRepo;
	@Autowired
	private QuartzManager quartzManager;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private UserAccountFlowRepository userAccountFlowDao;
	@Autowired
	private BondRepository bondDao;
	@Autowired
	private UserAuthenticationRepository authenticationDao;
	@Autowired
	private UserService userService;
	@Autowired
	private NoteRepository noteDao;
	@Autowired
	private SoaMemberService memberService;
	@Autowired
	private FinanceService financeService;

	/**
	 * 保证金收入
	 * @param 
	 * @return ResponseEntity<?>
	 * @throws
	 */
	@RequestMapping(value = "/depositSignalPayNotify", method = RequestMethod.POST)
	public ResponseEntity<?> depositSignalPayNotify(HttpServletRequest request) {
		logger.info("保证金缴纳收入  回调通知.....");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String orderNo = String.valueOf(reps.get("orderNo"));
			BigDecimal amount = new BigDecimal(String.valueOf(reps.get("amount")));
			logger.info("================保证金缴纳收入  回调通知.....====================reps:{}", reps);
			// 添加流水记录
			UserAccountFlow flow = userAccountFlowDao.findByOrderNo(orderNo);
			flow.setAmt(amount);
			flow.setContent("保证金收入（保证金账户）");
			flow.setCreateTime(DateUtil.getCurrentDate());
			flow.setStatus("success");
			flow.setBusinessType("交易");
			flow.setFlowType("收入");
			flow.setType(0);
			flow.setUpdateTime(DateUtil.getCurrentDate());
			accountFlowService.addUserAccountFlow(flow);

		} catch (RestException e) {
			logger.error("保证金缴纳收入  回调通知..... 回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("保证金缴纳收入  回调通知.....回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}
	
	
	/**
	 * 保证金交付 @param @return ResponseEntity<?> @throws
	 */
	@RequestMapping(value = "/depositAgentPayNotify", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> depositAgentPayNotify(HttpServletRequest request) {
		logger.info("保证金缴纳申请  回调通知");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String userId = String.valueOf(reps.get("userId"));
			String orderNo = String.valueOf(reps.get("orderNo"));
			BigDecimal amount = new BigDecimal(String.valueOf(reps.get("amount")));
			String param = String.valueOf(reps.get("extendInfo"));
			Map<String, Object> parse = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(param);
			logger.info("================保证金缴纳申请 成功====================reps:{}", reps);
			// 添加流水记录
			UserAccountFlow flow = userAccountFlowDao.findByOrderNo(orderNo);
			flow.setAmt(amount);
			flow.setContent("保证金支出");
			flow.setCreateTime(DateUtil.getCurrentDate());
			flow.setStatus("success");
			flow.setBusinessType("交易");
			flow.setFlowType("支出");
			flow.setType(1);
			flow.setPayMethod(String.valueOf(parse.get("payMethodName")));
			flow.setUpdateTime(DateUtil.getCurrentDate());
			flow.setUserId(userId);
			accountFlowService.addUserAccountFlow(flow);
			//保证金收入操作
			financeService.signalPayNotify(orderNo, String.valueOf(parse.get("payMethodName")), amount);
			// 修改保证金
			String shopId = parse.get("shopId").toString();
			Bond bond = bondDao.findByShopId(shopId);
			if (bond.getBondAmt().compareTo((bond.getShopBalance().add(amount))) == -1) {
				bond.setBondStatus(0);
			}
			bond.setShopBalance(bond.getShopBalance().add(amount));
			bondDao.save(bond);

			// 扣减余额
			if (String.valueOf(parse.get("payMethodName")).equals("BALANCE")) {
				userAccountService.deductionAccountAmt(userId, amount);
			}
			logger.info("缴纳保证金回调结束shopId:{}", shopId);
		} catch (RestException e) {
			logger.error("保证金缴纳申请 回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("保证金缴纳申请 回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * 营销服务消费申请 回调通知
	 * 
	 * @param
	 * @return 通知地址返回 http 的状态为 200 时，则认为通知成功
	 */
	@RequestMapping(value = "/marketAgentPayNotify", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> marketAgentPayNotify(HttpServletRequest request) {
		logger.info("营销服务消费申请  回调通知");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String userId = String.valueOf(reps.get("userId"));
			String orderNo = String.valueOf(reps.get("orderNo"));
			BigDecimal amount = new BigDecimal(String.valueOf(reps.get("amount")));
			String param = String.valueOf(reps.get("extendInfo"));
			Map<String, Object> parse = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(param);
			logger.info("================营销服务消费申请 成功====================reps:{}", reps);
			// 确认支付
			// balanceConfirmPay(userId, orderNo);
			// 编辑营销时限
			Calendar calendar = Calendar.getInstance(); // 得到日历
			String marketingServiceTime = String.valueOf(parse.get("marketingServiceTime"));
			String marketingServiceId = String.valueOf(parse.get("marketingServiceId"));
			String shopId = String.valueOf(parse.get("shopId"));
			ShopsMarketing marketing = shopMarketDao.findMarketingHasUse(shopId, marketingServiceId);
			if (marketing != null) {
				marketing.setStatus(0);
				if (marketing.getValidityDay().before(new Date())) {
					calendar.add(Calendar.MONTH, Integer.parseInt(marketingServiceTime));
					marketing.setValidityDay(calendar.getTime());
				} else {
					calendar.setTime(marketing.getValidityDay());
					calendar.add(Calendar.MONTH, Integer.parseInt(marketingServiceTime));
					marketing.setValidityDay(calendar.getTime());
				}
			} else {
				marketing = new ShopsMarketing();
				marketing.setStatus(0);
				marketing.setMarketingId(marketingServiceId);
				marketing.setShopId(shopId);
				calendar.add(Calendar.MONTH, Integer.parseInt(marketingServiceTime));
				marketing.setValidityDay(calendar.getTime());
			}

			shopMarketDao.save(marketing);
			// 添加流水记录
			UserAccountFlow flow = userAccountFlowDao.findByOrderNo(orderNo);
			flow.setAmt(amount);
			flow.setContent("营销服务");
			flow.setCreateTime(DateUtil.getCurrentDate());
			flow.setStatus("success");
			flow.setBusinessType("交易");
			flow.setFlowType("支出");
			flow.setType(1);
			flow.setPayMethod(String.valueOf(parse.get("payMethodName")));
			flow.setUpdateTime(DateUtil.getCurrentDate());
			flow.setUserId(userId);
			accountFlowService.addUserAccountFlow(flow);
			// 扣减余额
			if (String.valueOf(parse.get("payMethodName")).equals("BALANCE")) {
				userAccountService.deductionAccountAmt(userId, amount);
			}
		} catch (RestException e) {
			logger.error("营销服务消费申请 回调通知异常", e);
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("营销服务消费申请 回调通知异常", e);
			e.printStackTrace();
			// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * @Title: balanceConfirmPay @Description: 营销服务的确认支付 @param @param
	 *         userId @param @param orderNo @param @param
	 *         code @param @return @param @throws Exception 设定文件 @return String
	 *         返回类型 @throws
	 */
	@SuppressWarnings("rawtypes")
	public String balanceConfirmPay(String userId, String orderNo) throws Exception {
		logger.info("thridPay确认支付start.........orderNo:{}", orderNo);
		JSONObject param = new JSONObject();
		param.put("bizUserId", userId);
		param.put("bizOrderNo", orderNo);
		// param.put("verificationCode", "");
		param.put("consumerIp", "127.0.0.1");
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
	 * 充值回调通知
	 * 
	 * @param
	 * @return 通知地址返回 http 的状态为 200 时，则认为通知成功
	 */
	@RequestMapping(value = "/payBackNotify", method = RequestMethod.POST)
	public ResponseEntity<?> payBackNotify(HttpServletRequest request) {
		logger.info("充值回调通知");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String userId = String.valueOf(reps.get("userId"));
			String orderNo = String.valueOf(reps.get("orderNo"));
			BigDecimal amount = new BigDecimal(String.valueOf(reps.get("amount")));
			// 修改账户记录流水状态
			accountFlowService.modifyFlowStatus(userId, orderNo);
			// 修改账户余额
			userAccountService.addAccountAmt(userId, amount, amount, null);
			logger.info("================充值成功======================");
		} catch (Exception e) {
			logger.error("充值支付回调通知异常", e);
			e.printStackTrace();
			// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * @Title: setPayPwd @Description: 设置密码回调通知 @param @param
	 *         request @param @return 设定文件 @return ResponseEntity
	 *         <?> 返回类型 @throws
	 */
	@RequestMapping(value = "/setPayPwd", method = RequestMethod.POST)
	public ResponseEntity<?> setPayPwd(HttpServletRequest request) {
		logger.info("设置密码回调通知");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String bizUserId = (String) reps.get("bizUserId");
			logger.info("买家用户ID:{}", bizUserId);
			User user = userDao.findById(bizUserId);
			user.setPayPwd("1");
			userDao.save(user);
			logger.info("================设置密码成功======================");
		} catch (Exception e) {
			logger.error("设置密码回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * @Title: setCompanyInfo @Description: 设置企业认证信息 @param @param
	 *         request @param @return 设定文件 @return ResponseEntity
	 *         <?> 返回类型 @throws
	 */
	@RequestMapping(value = "/setCompanyInfo", method = RequestMethod.POST)
	public ResponseEntity<?> setCompanyInfo(HttpServletRequest request) {
		logger.info("设置企业认证信息回调通知");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String bizUserId = (String) reps.get("bizUserId");
			logger.info("用户ID:{}", bizUserId);
			// 通知结果
			Long result = (Long) reps.get("resul");
			// 拒绝原因
			String failReason = String.valueOf(reps.get("failReason"));
			// 审核时间
//			String checkTime = String.valueOf(reps.get("checkTime"));
			UserAuthentication authentication = authenticationDao.findByUserID(bizUserId);
			User user = userDao.findById(bizUserId);

			// 通联拒绝
			if (result.compareTo(new Long(3)) == 0) {
				authentication.setStatus(2);
				authentication.setRefusalReason(failReason);
				authentication = authenticationDao.save(authentication);
				user.setUserTypeOngoingCode("authentication_failed");
				user.setUserTypeCode("authentication_failed");
				userDao.save(user);
			}
			// 通联通过
			if (result.compareTo(new Long(2)) == 0) {
				// 设置企业会员信息
				userService.setUserInfo(authentication, user);
				// 修改企业会员状态
				user.setUserTypeOngoingCode("authentication_tenant");
				user.setUserTypeCode("authentication_tenant");
				userDao.save(user);

				// 无验证手机绑定
				JSONObject json = memberService.bindPhoneWithoutConfirm(user.getId(), user.getLoginName());

				String status = (String) json.get("status");
				if ("OK".equals(status)) {
					UserAccount userAccount = new UserAccount();
					userAccount.setUserId(user.getId());
					userAccount.setCreateTime(DateUtil.getCurrentDate());
					userAccount.setUpdateTime(DateUtil.getCurrentDate());
					userAccount.setBalance(new BigDecimal(0));
					userAccount.setWithdrawal(new BigDecimal(0));
					userAccount.setFrozenAmt(new BigDecimal(0));
					userAccountService.saveUserAccount(userAccount);
					// 给企业用户发短信
					String content = noteDao.findOne("21").getContent();
					String phone = user.getPhone();
					MessageUtils.sendText(phone, "{text}", content);
				}
			}
			
			
			logger.info("================设置企业认证信息结束======================");
		} catch (Exception e) {
			logger.error("设置企业认证信息回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * @Title: setPayPwd @Description: 修改密码回调通知 @param @param
	 *         request @param @return 设定文件 @return ResponseEntity
	 *         <?> 返回类型 @throws
	 */
	@RequestMapping(value = "/updatePayPwd", method = RequestMethod.POST)
	public ResponseEntity<?> updatePayPwd(HttpServletRequest request) {
		logger.info("修改密码回调通知");
		try {
			// 通知验签
			/*
			 * Map<String, Object> reps = checkSign(request); String bizUserId =
			 * (String) reps.get("bizUserId"); logger.info("买家用户ID:{}",
			 * bizUserId); User user = userDao.findById(bizUserId);
			 * user.setPayPwd("2"); userDao.save(user);
			 */
			logger.info("================修改密码成功======================");
		} catch (Exception e) {
			logger.error("修改密码回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * @Title: setPayPwd @Description: 重置密码回调通知 @param @param
	 *         request @param @return 设定文件 @return ResponseEntity
	 *         <?> 返回类型 @throws
	 */
	@RequestMapping(value = "/resetPayPwd", method = RequestMethod.POST)
	public ResponseEntity<?> resetPayPwd(HttpServletRequest request) {
		logger.info("重置密码回调通知");
		try {
			// 通知验签
			/*
			 * Map<String, Object> reps = checkSign(request); String bizUserId =
			 * (String) reps.get("bizUserId"); logger.info("买家用户ID:{}",
			 * bizUserId); User user = userDao.findById(bizUserId);
			 * user.setPayPwd("3"); userDao.save(user);
			 */
			logger.info("================重置密码成功======================");
		} catch (Exception e) {
			logger.error("重置密码回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * 托管代收(订单支付)回调通知
	 * 
	 * @param
	 * @return 通知地址返回 http 的状态为 200 时，则认为通知成功
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/signalAgentPayNotify", method = RequestMethod.POST)
	public ResponseEntity<?> signalAgentPayNotify(HttpServletRequest request) {
		logger.info("托管代收(订单支付)回调通知");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String userId = String.valueOf(reps.get("userId"));
			String orderNo = String.valueOf(reps.get("orderNo"));
			BigDecimal amount = new BigDecimal(String.valueOf(reps.get("amount")));
			String extendInfo = String.valueOf(reps.get("extendInfo"));
			Map<String, Object> parse = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(extendInfo);
			String sourceOrderNo = String.valueOf(parse.get("sourceOrderNo"));
			String payMethodName = String.valueOf(parse.get("payMethodName"));
			logger.info("================托管代收(订单支付)成功====================reps:{}", reps);
			// 添加流水记录
			UserAccountFlow flow = userAccountFlowDao.findByOrderNo(orderNo);
			flow.setAmt(amount);
			flow.setContent("商品支付交易");
			flow.setCreateTime(DateUtil.getCurrentDate());
			flow.setStatus("success");
			Order orderSn = orderDao.findByOrderSn(sourceOrderNo);
			if (!StringUtils.isEmpty(orderSn.getPurchaseGoodsId())) {
				flow.setBusinessType("求购");
				flow.setFlowType("支出");
			} else {
				List<OrderGoods> orderGoods = orderGoodsDao.findByOrderId(orderSn.getId());
				if (StringUtils.isEmpty(orderGoods.get(0).getGoodsBidStart())) {
					flow.setBusinessType("交易");
					flow.setFlowType("支出");
				} else {
					flow.setBusinessType("竞拍");
					flow.setFlowType("支出");
				}
			}
			flow.setPayMethod(payMethodName);
			flow.setType(1);
			flow.setUpdateTime(DateUtil.getCurrentDate());
			flow.setUserId(userId);
			accountFlowService.addUserAccountFlow(flow);
			// 扣减余额
			if ("BALANCE".equals(payMethodName)) {
				logger.info("======================payMethodName:{}", payMethodName);
				userAccountService.deductionAccountAmt(userId, amount);
			}
			// 改变订单状态
			Order order = orderservice.changeOrderStatus(sourceOrderNo, "order_unprocessed", amount);

			// 取消关闭订单定时器任务
			AuctionJob auctionJob = auctionJobRepo.findByJobName("job_close_order_" + order.getId());
			quartzManager.removeJob(auctionJob.getJobName(), auctionJob.getJobGroupName(), auctionJob.getTriggerName(),
					auctionJob.getTriggerGroupName());
			auctionJob.setJobStatus(1);
			auctionJobRepo.save(auctionJob);
			if ("普通订单".equals(order.getGroupStatus())) {
				orderservice.sendMessage(order);
			}
		} catch (RestException e) {
			logger.error("托管代收(订单支付)回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("托管代收(订单支付)回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * 退款通知 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/getRefund", method = RequestMethod.POST)
	public ResponseEntity<?> getRefund(HttpServletRequest request) {
		logger.info("退款通知...");
		try {
			request.getReader();
			Map<String, Object> reps = checkSign(request);
			// 获取退款订单编号
			String refundOrderNo = (String) reps.get("orderNo");
			// 获取退款记录
			logger.info("获取退款记录,修改退款申请:{}", refundOrderNo);
			OrderRefund orderRefund = orderRefundDao.findByRefundOrderNo(refundOrderNo);
			if (!StringUtils.isEmpty(orderRefund)) {
				orderRefund.setRefundStatusCode("refund_accepted");
				orderRefund.setUpdateTime(DateUtil.getCurrentDate());
				orderRefundDao.save(orderRefund);
			}
			// 获取订单记录
			logger.info("------------获取订单记录,修改原始订单");
			Order order = orderDao.findByOrderId(orderRefund.getOrderId());
			if(!"order_closed".equals(order.getOrderStatusCode())){
				List<OrderGoods> orderGoods = orderGoodsDao.findByOrderId(order.getId());
				for (OrderGoods orderGood : orderGoods) {
					Goods goods = goodsDao.findOne(orderGood.getGoodsId());
					goods.setStockNum(goods.getStockNum() + orderGood.getGoodsNum());
					goods.setStatusCode("goods_grounding");
					goodsDao.save(goods);
				}
			}
			order.setUpdateTime(DateUtil.getCurrentDate());
			String orderType = order.getOrderStatusCode();
			order.setOrderStatusCode("order_closed");
			order.setIsRefund("refund_accepted");
			orderDao.save(order);
			operateLogDao.save(BaseService.addLog(null, "回调通知",
					"第三方退款操作已完成，回调通知修改订单编号为：" + order.getOrderSn() + "的订单状态为已关闭，订单退款流程结束！"));
			logger.info("退款流水增加----------------------------");
			UserAccountFlow flow = userAccountFlowDao.findByOrderNo(refundOrderNo);
			if ("order_unprocessed".equals(orderType)) {
				flow.setAmt(order.getTotalPrice().add(order.getExpressFee()));
			} else {
				flow.setAmt(order.getTotalPrice());
			}
			if (order.getExpressFee().compareTo(new BigDecimal(0)) != 0 && "order_unreceied".equals(orderType)) {
				flow.setContent("货款退款入账");
			} else {
				flow.setContent("货款运费退款入账");
			}
			flow.setCreateTime(DateUtil.getCurrentDate());
			flow.setOrderNo(refundOrderNo);
			flow.setStatus("success");
			flow.setBusinessType("交易");
			flow.setFlowType("退款");
			flow.setType(0);
			flow.setUpdateTime(DateUtil.getCurrentDate());
			flow.setUserId(order.getBuyerId());
			accountFlowService.addUserAccountFlow(flow);
			// 退款金额入账
			logger.info("退款金额入账");
			userAccountService.addAccountAmt(order.getBuyerId(), flow.getAmt(), flow.getAmt(), null);
			logger.info("=============退款流程通知完结==========");
			if (order.getExpressFee().compareTo(new BigDecimal(0)) != 0 && "order_unreceied".equals(orderType)) {
				logger.info("邮费支付给卖家...");
				orderPayService.agentPay(order.getOrderSn(), order.getBuyerId(), 0, order.getFlowNo());
			}
			if ("order_unreceied".equals(orderType)) {
				// 取消定时任务的确认收货
				AuctionJob auctionJob = auctionJobRepo.findByJobName("job_automatic_receipt_" + flow.getOrderNo());
				quartzManager.removeJob(auctionJob.getJobName(), auctionJob.getJobGroupName(),
						auctionJob.getTriggerName(), auctionJob.getTriggerGroupName());
				auctionJob.setJobStatus(1);
				auctionJobRepo.save(auctionJob);
			}
		} catch (RestException e) {
			logger.info("=============退款流程异常==========:{}", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.info("=============退款流程异常==========:{}", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	@RequestMapping(value = "/postagePayBackNofify", method = RequestMethod.POST)
	public ResponseEntity<?> postagePayBackNofify(HttpServletRequest request) {
		logger.info("邮费支付给卖家回调开始---------------");
		logger.info("+++++++++++++++++托管代付开始++++++++++++++++");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String bussOrderNo = String.valueOf(reps.get("orderNo"));
			BigDecimal amount = new BigDecimal(String.valueOf(reps.get("amount")));
			PaymentFlow payFlow = paymentFlowService.getPaymentFlowRecord(bussOrderNo);
			logger.info("==============添加用户流水开始===============");
			UserAccountFlow flow = userAccountFlowDao.findByOrderNo(bussOrderNo);
			flow.setAmt(amount);
			flow.setContent("邮费入账");
			flow.setCreateTime(DateUtil.getCurrentDate());
			flow.setOrderNo(bussOrderNo);
			flow.setStatus("success");
			flow.setBusinessType("交易");
			flow.setFlowType("收入");
			flow.setType(0);
			flow.setUpdateTime(DateUtil.getCurrentDate());
			flow.setUserId(payFlow.getGatheringUserId());
			accountFlowService.addUserAccountFlow(flow);
			logger.info("==============添加用户流水结束================");
			// 商家入账
			logger.info("---------------------修改卖家用户金额开始-----------------------");
			userAccountService.addAccountAmt(payFlow.getGatheringUserId(), amount, amount, null);
			logger.info("---------------------修改卖家用户金额结束-----------------------");
			payFlow.setStatus("代付交易成功");
			payFlow.setUpdateTime(DateUtil.getCurrentDate());
			paymentFlowService.insertPaymentFlow(payFlow);
			logger.info("+++++++++++++++++++托管代付结束++++++++++++++++++++");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	@RequestMapping(value = "/withdrawApplyBackNotify", method = RequestMethod.POST)
	public ResponseEntity<?> withdrawApplyBackNotify(HttpServletRequest request) {
		logger.info("提现回调通知---------------");
		logger.info("+++++++++++++++++提现回调通知++++++++++++++++");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			String bussOrderNo = String.valueOf(reps.get("orderNo"));
			logger.info("==============提现回调流水修改开始===============");


			UserAccountFlow flow = userAccountFlowDao.findByOrderNo(bussOrderNo);
			flow.setStatus("success");
			flow.setContent("提现成功");
			flow.setUpdateTime(DateUtil.getCurrentDate());
			accountFlowService.addUserAccountFlow(flow);
			logger.info("==============提现回调流水修改结束================");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * 托管代付通知
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hostCollectBackNofify", method = RequestMethod.POST)
	public ResponseEntity<?> hostCollectBackNofify(HttpServletRequest request) {
		logger.info("托管代付通知");
		try {
			// 通知验签
			Map<String, Object> reps = checkSign(request);
			// String userId =String.valueOf(reps.get("userId"));
			String bussOrderNo = String.valueOf(reps.get("orderNo"));
			BigDecimal amount = new BigDecimal(String.valueOf(reps.get("amount")));

			String param = String.valueOf(reps.get("extendInfo"));
			Map<String, Object> parse = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(param);
			PaymentFlow payFlow = paymentFlowService.getPaymentFlowRecord(bussOrderNo);
			// 添加商品分销流水
			if (parse.get("shareUserNo") != null) {
				String shareUserNo = String.valueOf(parse.get("shareUserNo"));
				BigDecimal commissionAmount = new BigDecimal((Integer) parse.get("commissionAmount"))
						.divide(new BigDecimal(100));

				UserAccountFlow shareUserFlow = userAccountFlowDao.findByUserIdAndOrderNoAndContent(shareUserNo,
						bussOrderNo, "商品分销入账");
				shareUserFlow.setAmt(commissionAmount);
				shareUserFlow.setStatus("success");
				shareUserFlow.setType(0);
				shareUserFlow.setUpdateTime(DateUtil.getCurrentDate());
				accountFlowService.addUserAccountFlow(shareUserFlow);
				// 分享人入账
				userAccountService.addAccountAmt(shareUserNo, commissionAmount, commissionAmount, null);

				// 商家商品分销出账
				UserAccountFlow sellUserChannelFlow = userAccountFlowDao
						.findByUserIdAndOrderNoAndContent(payFlow.getGatheringUserId(), bussOrderNo, "商品分销出账");
				sellUserChannelFlow.setAmt(commissionAmount);
				sellUserChannelFlow.setStatus("success");
				sellUserChannelFlow.setType(1);
				sellUserChannelFlow.setUpdateTime(DateUtil.getCurrentDate());
				accountFlowService.addUserAccountFlow(sellUserChannelFlow);
				// 扣除商家商品分销金额
				userAccountService.deductionAccountAmt(payFlow.getGatheringUserId(), commissionAmount);
			}
			// 添加商家流水订单
			UserAccountFlow sellUserFlow = userAccountFlowDao
					.findByUserIdAndOrderNoAndContent(payFlow.getGatheringUserId(), bussOrderNo, "店铺交易入账");
			BigDecimal atm = new BigDecimal((Integer) parse.get("orderFee")).divide(new BigDecimal(100));

			Order orderSn = orderDao.findByOrderSn(payFlow.getSourceOrderNo());
			if (!StringUtils.isEmpty(orderSn.getPurchaseGoodsId())) {
				sellUserFlow.setBusinessType("求购");
				sellUserFlow.setFlowType("收入");
			} else {
				List<OrderGoods> orderGoods = orderGoodsDao.findByOrderId(orderSn.getId());
				if (StringUtils.isEmpty(orderGoods.get(0).getGoodsBidStart())) {
					sellUserFlow.setBusinessType("交易");
					sellUserFlow.setFlowType("收入");
				} else {
					sellUserFlow.setBusinessType("竞拍");
					sellUserFlow.setFlowType("收入");
				}
			}
			sellUserFlow.setAmt(atm);
			sellUserFlow.setStatus("success");
			sellUserFlow.setType(0);
			sellUserFlow.setUpdateTime(DateUtil.getCurrentDate());
			accountFlowService.addUserAccountFlow(sellUserFlow);
			// 商家入账
			userAccountService.addAccountAmt(payFlow.getGatheringUserId(), atm, atm, null);

			// 手续费流水
			UserAccountFlow sellUserFeeFlow = userAccountFlowDao
					.findByUserIdAndOrderNoAndContent(payFlow.getGatheringUserId(), bussOrderNo, "平台手续费");
			BigDecimal decimal = new BigDecimal((Integer) parse.get("fee")).divide(new BigDecimal(100));
			sellUserFeeFlow.setAmt(decimal);
			sellUserFeeFlow.setStatus("success");
			sellUserFeeFlow.setType(1);
			sellUserFeeFlow.setUpdateTime(DateUtil.getCurrentDate());
			accountFlowService.addUserAccountFlow(sellUserFeeFlow);

			// 减去商家平台手续费
			userAccountService.deductionAccountAmt(payFlow.getGatheringUserId(),
					new BigDecimal((Integer) parse.get("fee")).divide(new BigDecimal(100)));
			// 修改代付订单状态
			paymentFlowService.modifyPaymentFlowStatus(bussOrderNo, "托管代付成功");

			if (!"order_closed".equals(orderDao.findByOrderSn(payFlow.getSourceOrderNo()).getOrderStatusCode())) {
				// 修改源订单状态
				orderservice.changeOrderStatus(payFlow.getSourceOrderNo(), "order_uncomment", amount);
			}
			// 修改代付状态
			payFlow.setStatus("代付交易成功");
			payFlow.setUpdateTime(DateUtil.getCurrentDate());
			paymentFlowService.insertPaymentFlow(payFlow);
			logger.info("================托管代付通知成功===================={}", reps);
			// 会员分销回到通知修改状态
			Order order = orderDao.findByOrderSn(payFlow.getSourceOrderNo());
			List<RetailDetail> list = retailDetailRepo.findByRetailObjId(order.getId());
			for (RetailDetail retailDetail : list) {
				retailDetail.setSettlementStatus("untreated");
				retailDetail.setUpdateTime(new Date());
				retailDetailRepo.save(retailDetail);
			}
			// 商品分销回到通知修改状态
			if (parse.get("shareUserNo") != null) {
				List<OrderGoods> orders = orderGoodsDao.findByOrderId(order.getId());
				List<RetailDetail> goodRetailList = retailDetailRepo.getByRetailObjId(orders.get(0).getGoodsId());
				for (RetailDetail retailDetail : goodRetailList) {
					retailDetail.setSettlementStatus("commission_accepted");
					retailDetail.setUpdateTime(new Date());
					retailDetailRepo.save(retailDetail);
				}
			}
			logger.info("================托管代付通知结束！！！====================");
		} catch (RestException e) {
			logger.error("托管代付回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("托管代付回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<T>(HttpStatus.OK);
	}

	/**
	 * 通知验签
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> checkSign(HttpServletRequest request) {
		logger.info("回调通知请求参数param:{}", request);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> param = null;
		try {
			String contentType = request.getContentType();
			logger.info("contentType" + contentType);
			if (contentType.contains("application/x-www-form-urlencoded")) {
				param = getHttpParam(request);
			}
			if (contentType.contains("text/xml")) {
				param = readHttpsBody(request);
			}
			String sysid = param.get("sysid");
			logger.info("sysid" + sysid);
			String sign = param.get("sign");
			logger.info("sign" + sign);
			String timestamp = param.get("timestamp");
			logger.info("timestamp" + timestamp);
			String v = param.get("v");
			String rps = param.get("rps");
			JSONObject json = new JSONObject(rps);
			Map<String, Object> map = json.getJSONObject("returnValue").getMap();
			String orderNo = String.valueOf(map.get("bizOrderNo"));
			String userId = String.valueOf(map.get("buyerBizUserId"));
			String bizUserId = String.valueOf(map.get("bizUserId"));
			String extendInfo = String.valueOf(map.get("extendInfo"));
			Long resul = null;
			if (map.get("result") != null) {
				resul = Long.valueOf((Integer) map.get("result"));
			}
			String failReason = String.valueOf(map.get("failReason"));
			String checkTime = String.valueOf(map.get("checkTime"));
			String fee = null;
			if (map.get("fee") != null) {
				fee = String.valueOf(map.get("fee"));
			}
			BigDecimal amount = null;
			if (map.get("amount") != null) {
				amount = new BigDecimal(AmountUtils.changeF2Y(String.valueOf(map.get("amount")))); // 将分转换为元
			}

			logger.info("回调响应参数" + "[sysid]:" + sysid + ",[sign]:" + sign + ",[timestamp]:" + timestamp + ",[v]:" + v
					+ ",[rps]:" + rps);
			String text = sysid + rps + timestamp;
			logger.info("================通知验签开始======");
			Boolean verifyResult = RSAUtil.verify(PayClientUtil.getPublicKey(), text, sign);
			logger.info("通知签名验证结果verifyResult:{}", verifyResult);
			if (!verifyResult) {
				logger.info("通知验签失败");
				throw new RestException(HttpStatus.ACCEPTED);
			}
			result.put("orderNo", orderNo);
			result.put("userId", userId);
			result.put("bizUserId", bizUserId);
			result.put("resul", resul);
			result.put("failReason", failReason);
			result.put("checkTime", checkTime);
			result.put("amount", amount); // 金额（元）
			result.put("fee", fee); // 金额（元）
			result.put("extendInfo", extendInfo);
			logger.info("================通知验签成功结束======================");
		} catch (Exception e) {
			logger.error("回调通知异常", e);
			e.printStackTrace();
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	/**
	 * http请求参数
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, String> getHttpParam(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String sysid = request.getParameter("sysid");
		String sign = request.getParameter("sign");
		String timestamp = request.getParameter("timestamp");
		String v = request.getParameter("v");
		String rps = request.getParameter("rps");

		map.put("sysid", sysid);
		map.put("sign", sign);
		map.put("timestamp", timestamp);
		map.put("v", v);
		map.put("rps", rps);
		return map;
	}

	/**
	 * 解析https请求中的body数据
	 * 
	 * @param request
	 * @throws IOException
	 */
	public Map<String, String> readHttpsBody(HttpServletRequest request) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		BufferedReader br = request.getReader();
		String str, encodeUrl = "";
		while ((str = br.readLine()) != null) {
			encodeUrl += str;
		}
		// url反编码
		String decodeUrl = URLDecoder.decode(encodeUrl, "utf8");
		logger.info("获取https请求的body数据：" + decodeUrl);
		String[] param = decodeUrl.split("&");
		for (int i = 0; i < param.length; i++) {
			String[] key_value = param[i].split("=", 2); // sign中包含 = 限制limit为2
			map.put(key_value[0], key_value[1]);
		}
		return map;
	}

}
