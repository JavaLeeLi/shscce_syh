package com.visionet.syh_mall.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visionet.syh_mall.entity.Note;
import com.visionet.syh_mall.repository.NoteRepository;
import com.visionet.syh_mall.repository.OperateLogRepository;
import com.visionet.syh_mall.repository.TradeSettingRepository;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MessageUtils;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.order.Arbitration;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderCanc;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.entity.order.OrderRefund;
import com.visionet.syh_mall.entity.order.ServiceReason;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mobile.OrderCanRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.repository.order.ArbitrationRepository;
import com.visionet.syh_mall.repository.order.OrderRefundRepository;
import com.visionet.syh_mall.repository.order.ServiceReasonRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.scheduler.QuartzManager;
import com.visionet.syh_mall.service.thridAccount.SoaRefundService;
import com.visionet.syh_mall.vo.ServiceReasonVo;

/**
 * 订单退货
 * 
 * @author xiaofb
 * @time 2017年9月6日
 */
@Service
public class OrderCancService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(OrderCancService.class);
	@Autowired
	private OrderCanRepository orderCancRepo;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ShopRepository shopRepo;
	@Autowired
	private OrderRefundRepository orderRefundDao;
	@Autowired
	private KeyMappingRepository keyDao;
	@Autowired
	private OrderGoodsRepository orderGoodsDao;
	@Autowired
	private ServiceReasonRepository reasonDao;
	@Autowired
	private SoaRefundService soaRefundService;
	@Autowired
	private NoteRepository noteDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private OperateLogRepository operateLogDao;
	@Autowired
	private AuctionJobRepository auctionJobRepo;
	@Autowired
	private QuartzManager quartzManager;
	@Autowired
	private UserAccountFlowRepository userAccountFlowDao;
	@Autowired
	private ArbitrationRepository arbitrationDao;

	/**
	 * @throws Exception
	 * 
	 * @Title: searchCancApplys @Description: 搜索退货申请 @param @param
	 *         param @param @return 设定文件 @return List<Map<String,Object>>
	 *         返回类型 @throws
	 */
	public Map<String, Object> searchCancApplys(Map<String, Object> param, PageInfo pageInfo) throws Exception {
		// 商品的传参
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		result = getReturnMap(result, null, pageInfo);
		result.put("refundInfos", list);
		if (!StringUtils.isEmpty(param.get("goodsName"))) {
			// 商品ID的集合
			List<String> orderIds = orderGoodsDao.findBygoodsName(String.valueOf(param.get("goodsName")));
			if (orderIds.size() <= 0) {
				return result;
			}
			param.put("orderIds", orderIds);
		}
		// 买家昵称的穿参
		if (!StringUtils.isEmpty(param.get("buyerName"))) {
			// 买家用户的ID集合
			List<String> userIds = userRepo.findByloginName(String.valueOf(param.get("buyerName")));
			if (userIds.size() <= 0) {
				return result;
			}
			param.put("buyerId", userIds);
		}
		if (!StringUtils.isEmpty(param.get("shopName"))) {
			// 卖家的ID
			List<String> userIds = shopRepo.findByName(String.valueOf(param.get("shopName")));
			if (userIds.size() <= 0) {
				return result;
			}
			param.put("sellerId", userIds);
		}
		if (!StringUtils.isEmpty(param.get("orderSN"))) {
			List<String> orderIds = orderDao.findOrderIdByOrderName(String.valueOf(param.get("orderSN")));
			if (orderIds.size() <= 0) {
				return result;
			}
			param.put("orderId", orderIds);
		}
		Map<String, SearchFilter> serchFilte = SearchFilter.parse(DynamicParamConvert.searchCancApplys(param));
		Page<OrderCanc> page = orderCancRepo.findAll(
				DynamicSpecifications.bySearchFilter(serchFilte.values(), OrderCanc.class), buildPageRequest(pageInfo));
		for (OrderCanc orderCanc : page) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderID", orderCanc.getOrderId());
			map.put("cancReason", orderCanc.getCancReason());
			map.put("cancStatusCode", orderCanc.getCancStatusCode());
			KeyMapping code = keyDao.findByKeyCode(orderCanc.getCancStatusCode());
			map.put("cancStatusDesc", code.getValueDesc());
			map.put("applyTime", orderCanc.getApplyTime());

			// 订单信息
			Order order = orderDao.findByOrderId(orderCanc.getOrderId());
			map.put("orderSN", order.getOrderSn());

			// 买家用户信息
			User buyUser = userRepo.findById(orderCanc.getBuyerId());
			map.put("buyerName", buyUser.getLoginName());
			map.put("buyerPhone", buyUser.getPhone());

			// 卖家店铺信息
			Shop shop = shopRepo.findOne(order.getShopId());
			map.put("shopName", shop.getShopName());
			map.put("sellerName", shop.getUserName());

			// 退货商品数组
			List<Map<String, Object>> refundGoodsList = new ArrayList<Map<String, Object>>();
			List<OrderGoods> orderId = orderGoodsDao.findByOrderIdGoodId(orderCanc.getOrderId());
			for (OrderGoods orderGoods : orderId) {
				Map<String, Object> goodMap = new HashMap<String, Object>();
				goodMap.put("goodsID", orderGoods.getGoodsId());
				goodMap.put("goodsPrice", orderGoods.getGoodsRealPrice());
				goodMap.put("goodsNum", orderGoods.getGoodsNum());
				goodMap.put("goodsName", orderGoods.getGoodsName());
				refundGoodsList.add(goodMap);
			}
			map.put("cancGoods", refundGoodsList);
			list.add(map);
		}
		result = getReturnMap(result, page, pageInfo);
		result.put("refundInfos", list);
		return result;

	}

	/**
	 * 
	 * @Title: reviewCancApply @Description: 审核退货申请 @param @param
	 *         orderID @param @param reviewIsApproved 设定文件 @return void
	 *         返回类型 @throws
	 */
	public void reviewCancApply(String orderID, String cancStatusCode) {
		// 通过订单ID查询名单退货
		OrderCanc canc = orderCancRepo.findByorderId(orderID);
		if (StringUtils.isEmpty(canc)) {
			throw new RestException("没有该退货订单");
		}
		canc.setUpdateTime(DateUtil.getCurrentDate());
		canc.setCancStatusCode(cancStatusCode);
		orderCancRepo.save(canc);
	}

	/**
	 * @Title: searchRefundApplys @Description: 搜索退款申请 @param @param
	 *         param @param @return 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchRefundApplys(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
		result = getReturnMap(result, null, pageInfo);
		result.put("refundInfos", li);
		// 商品的传参
		if (!StringUtils.isEmpty(param.get("goodsName"))) {
			// 商品ID的集合
			List<String> orderIds = orderGoodsDao.findBygoodsName(String.valueOf(param.get("goodsName")));
			if (orderIds.size() <= 0) {
				return result;
			}
			param.put("orderIds", orderIds);
		}
		// 买家昵称的传参
		if (!StringUtils.isEmpty(param.get("buyerName"))) {
			// 买家用户的ID集合
			List<String> userIds = userRepo.findByloginName(String.valueOf(param.get("buyerName")));
			if (userIds.size() <= 0) {
				return result;
			}
			param.put("buyerId", userIds);
		}
		if (!StringUtils.isEmpty(param.get("shopName"))) {
			// 卖家的ID
			List<String> userIds = shopRepo.findByName(String.valueOf(param.get("shopName")));
			if (userIds.size() <= 0) {
				return result;
			}
			param.put("userId", userIds);
		}
		// 订单编号查订单Sn
		if (!StringUtils.isEmpty(param.get("orderSN"))) {
			List<String> orderIds = orderDao.findOrderIdByOrderName(String.valueOf(param.get("orderSN")));
			if (orderIds.size() <= 0) {
				return result;
			}
			param.put("orderId", orderIds);
		}
		Map<String, SearchFilter> filter = SearchFilter.parse(DynamicParamConvert.searchRefundApplys(param));
		Page<OrderRefund> page = orderRefundDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), OrderRefund.class), buildPageRequest(pageInfo));
		for (OrderRefund orderRefund : page) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderID", orderRefund.getOrderId());
			map.put("refundSum", orderRefund.getRefundSum());
			map.put("refundReason", orderRefund.getRefundReason());
			map.put("refundStatusCode", orderRefund.getRefundStatusCode());
			KeyMapping code = keyDao.findByKeyCode(orderRefund.getRefundStatusCode());
			map.put("refundStatusDesc", code.getValueDesc());
			map.put("applyTime", orderRefund.getApplyTime());

			// 订单信息
			Order order = orderDao.findByOrderId(orderRefund.getOrderId());
			map.put("orderSN", order.getOrderSn());
			
			//第三方业务单号
			map.put("flowNo", orderRefund.getRefundOrderNo());

			// 买家用户信息
			User buyUser = userRepo.findById(orderRefund.getBuyerId());
			map.put("buyerName", buyUser.getAliasName());
			map.put("buyerPhone", buyUser.getPhone());

			// 卖家店铺信息
			Shop shop = shopRepo.findOne(order.getShopId());
			map.put("shopName", shop.getShopName());
			map.put("sellerName", shop.getUserName());

			// 退款商品数组
			List<Map<String, Object>> refundGoodsList = new ArrayList<Map<String, Object>>();
			List<OrderGoods> orderId = orderGoodsDao.findByOrderIdGoodId(orderRefund.getOrderId());
			for (OrderGoods orderGoods : orderId) {
				Map<String, Object> goodMap = new HashMap<String, Object>();
				goodMap.put("goodsID", orderGoods.getGoodsId());
				goodMap.put("goodsPrice", orderGoods.getGoodsRealPrice());
				goodMap.put("goodsNum", orderGoods.getGoodsNum());
				goodMap.put("goodsName", orderGoods.getGoodsName());
				refundGoodsList.add(goodMap);
			}
			map.put("refundGoods", refundGoodsList);
			li.add(map);
		}
		result = getReturnMap(result, page, pageInfo);
		result.put("refundInfos", li);
		return result;
	}

	/**
	 * @throws JSONException
	 * @Title: confimrRefund @Description: 确认退款 @param @param orderID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void confimrRefund(String orderID, String adminUserId) throws JSONException {
		logger.info("确认退款开始：{}", orderID);
		OrderRefund refund = orderRefundDao.findByOrderId(orderID);
		if (StringUtils.isEmpty(refund)) {
			throw new RestException("订单退款不存在或者已删除");
		}
		if (!StringUtils.isEmpty(refund.getRefundOrderNo())) {
			throw new RestException("退款申请处理中，请勿重复提交！");
		}
		//生成业务流水号
		String newOrderSn = SequenceUUID.getOrderIdByUUId("B");
		Order order = orderDao.findByOrderId(orderID);
		long amt = 0;
		//判断订单状态计算退款金额
		if ("order_unprocessed".equals(order.getOrderStatusCode())) {
			amt = Long.valueOf(AmountUtils.changeY2F(order.getTotalPrice().add(order.getExpressFee()).toString()));
		}
		if ("order_unreceied".equals(order.getOrderStatusCode())) {
			amt = Long.valueOf(AmountUtils.changeY2F(order.getTotalPrice().toString()));
		}
		refund.setRefundOrderNo(newOrderSn);
		orderRefundDao.save(refund);
		//添加日志
		operateLogDao.save(addLog(null, adminUserId, "系统管理员对订单进行退款操作，订单号为：" + order.getOrderSn() + "的订单。"));
		//添加账户流水明细
		UserAccountFlow accountFlow = new UserAccountFlow();
		accountFlow.setOrderNo(newOrderSn);
		accountFlow.setStatus("等待确认");
		if ("order_unprocessed".equals(order.getOrderStatusCode())) {
			accountFlow.setAmt(order.getTotalPrice().add(order.getExpressFee()));
			accountFlow.setContent("货款运费退款入账");
		}
		if ("order_unreceied".equals(order.getOrderStatusCode())) {
			accountFlow.setAmt(order.getTotalPrice());
			accountFlow.setContent("货款退款入账");
		}
		userAccountFlowDao.save(accountFlow);
		logger.info("第三方接口调用开始");
		try {
			soaRefundService.Refund(refund.getSellerId(), refund.getBuyerId(), newOrderSn, order.getFlowNo(), amt);
			logger.info("第三方接口调用结束");
		} catch (RestException e) {
			logger.info("第三方接口调用异常");
			e.printStackTrace();
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException(e.getMessage());
		}
		operateLogDao.save(addLog(null, adminUserId, "第三方退款操作已完成，订单号为：" + order.getOrderSn() + "的订单退款已完成。等待第三方回调通知。"));
		//短信提醒
		String content = noteDao.findOne("18").getContent();
		String buyerPhone = order.getBuyerPhone();
		String sellerPhone = userRepo.findById(order.getShopOwnerId()).getPhone();
		String phone = buyerPhone + "," + sellerPhone;
		MessageUtils.sendText(phone, "{text}", content);
	}

	/**
	 * @Title: reviewRefundApply @Description: 审核退款申请 @param @param
	 *         orderID @param @param refundStatusCode 设定文件 @return void
	 *         返回类型 @throws
	 */
	@Transactional
	public void reviewRefundApply(String banReason, String orderID, String refundStatusCode, String adminUserId,
			Integer source) {
		OrderRefund refund = orderRefundDao.findByOrderId(orderID);
		if (StringUtils.isEmpty(refund)) {
			throw new RestException("订单退款不存在或者已删除");
		}
		refund.setRefundStatusCode(refundStatusCode);
		Order byOrderId = orderDao.findByOrderId(orderID);
		try {
			if (refundStatusCode.equals("refund_seller_refused")) {
				Note note = noteDao.findOne("16");
				String content = note.getContent();
				String phone = byOrderId.getBuyerPhone();
				MessageUtils.sendText(phone, "{text}", content);
			}
			if (refundStatusCode.equals("refund_syh_refused")) {
				Note note = noteDao.findOne("17");
				String content = note.getContent();
				String phone = byOrderId.getBuyerPhone() + ","
						+ userDao.findById(byOrderId.getShopOwnerId()).getPhone();

				MessageUtils.sendText(phone, "{text}", content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		refund.setUpdateTime(DateUtil.getCurrentDate());
		refund.setRefundReason(banReason);
		orderRefundDao.save(refund);
		Order order = orderDao.findByOrderId(orderID);
		if ("refund_syh_confirm".equals(refundStatusCode)) {
			operateLogDao.save(addLog(null, order.getShopOwnerId(),
					"订单编号为：" + order.getOrderSn() + "的订单发起的退款申请商家已经审批，审核结果为通过，现转往平台端进行审批！"));
		}
		if ("approve_platform".equals(refundStatusCode)) {
			operateLogDao.save(addLog(null, adminUserId,
					"订单编号为：" + order.getOrderSn() + "的订单发起的退款申请平台已经审批通过，审核结果为通过，等待平台进行退款处理！"));
		}
		if ("refund_seller_refused".equals(refundStatusCode) || "refund_syh_refused".equals(refundStatusCode)) {
			if ("refund_seller_refused".equals(refundStatusCode)) {
				operateLogDao.save(addLog(null, order.getShopOwnerId(),
						"订单编号为：" + order.getOrderSn() + "的订单发起的退款申请商家已经审批，审核结果为拒绝。"));
			}
			if ("refund_syh_refused".equals(refundStatusCode)) {
				operateLogDao
						.save(addLog(null, adminUserId, "订单编号为：" + order.getOrderSn() + "的订单发起的退款申请平台已经审批，审核结果为拒绝。"));
			}
			refundStatusCode = null;
		}
		order.setIsRefund(refundStatusCode);
		orderDao.save(order);

		// 取消商家审核通过定时器任务
		AuctionJob auctionJob = auctionJobRepo.findByJobName("job_by_application_" + order.getId());
		if (source == 0) {
			quartzManager.removeJob(auctionJob.getJobName(), auctionJob.getJobGroupName(), auctionJob.getTriggerName(),
					auctionJob.getTriggerGroupName());
		}
		auctionJob.setJobStatus(1);
		auctionJobRepo.save(auctionJob);
	}

	/**
	 * @Title: addAftersaleReason @Description: 添加/编辑售后原因 @param 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void addAftersaleReason(ServiceReasonVo reasonVo, String currentUserId) {
		if (StringUtils.isEmpty(reasonVo.getReasonID())) {
			ServiceReason reason = new ServiceReason();
			reason.setCreateBy(currentUserId);
			reason.setUpdateBy(currentUserId);
			reasonDao.save(reasonVo.convertVo(reasonVo, reason));
			return;
		}
		ServiceReason reason = reasonDao.findOne(reasonVo.getReasonID());
		if (StringUtils.isEmpty(reason)) {
			throw new RestException("没有该售后原因");
		}
		reason.setUpdateBy(currentUserId);
		reason.setUpdateTime(DateUtil.getCurrentDate());
		reasonDao.save(reasonVo.convertVo(reasonVo, reason));
	}

	/**
	 * @Title:delAftersaleReason @Description:删除售后原因 @param 设定文件 @return void
	 *                           返回类型 @throws
	 */
	public void delAftersaleReason(String reasonID, String currentUserId) {
		ServiceReason one = reasonDao.findOne(reasonID);
		if (StringUtils.isEmpty(one)) {
			throw new RestException("没有该售后原因");
		}
		one.setUpdateTime(DateUtil.getCurrentDate());
		one.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		one.setUpdateBy(currentUserId);
		reasonDao.save(one);
	}

	/**
	 * @Title: getAftersaleReasons @Description: 搜索售后原因列表 @param @return
	 *         设定文件 @return List<ServiceReason> 返回类型 @throws
	 */
	public List<Map<String, Object>> getAftersaleReasons() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, SearchFilter> filter = SearchFilter.parse(DynamicParamConvert.search());
		List<ServiceReason> reason = reasonDao
				.findAll(DynamicSpecifications.bySearchFilter(filter.values(), ServiceReason.class));
		for (ServiceReason serviceReason : reason) {
			Map<String, Object> serviceReasonMap = new HashMap<String, Object>();
			serviceReasonMap.put("reasonID", serviceReason.getId());
			serviceReasonMap.put("reasonContent", serviceReason.getServiceReason());
			result.add(serviceReasonMap);
		}
		return result;
	}

	/**
	 * 添加订单发货仲裁请求
	 * @param 
	 * @return void
	 * @throws
	 */
	public void addArbitration(Map<String, Object> param) {
		String orderId = (String) param.get("orderID");
		Arbitration arbitration = new Arbitration();
		arbitration.setOrderId(orderId);
		arbitration.setArbitrationStatus("arbitration_going");
		arbitrationDao.save(arbitration);
	}
}
