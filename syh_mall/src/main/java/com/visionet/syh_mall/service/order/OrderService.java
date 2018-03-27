package com.visionet.syh_mall.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.express.KdApiOrderDistinguish;
import com.visionet.syh_mall.common.express.KdniaoTrackQueryAPI;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.MessageUtils;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.Note;
import com.visionet.syh_mall.entity.PayKinds;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.cart.ShopCart;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.goods.ExpressTemplet;
import com.visionet.syh_mall.entity.goods.ExpressWay;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.entity.goods.HomeGoods;
import com.visionet.syh_mall.entity.goods.SupplyRecord;
import com.visionet.syh_mall.entity.marketing.Coupon;
import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.entity.marketing.DiscountTimeUserLink;
import com.visionet.syh_mall.entity.marketing.GroupBuy;
import com.visionet.syh_mall.entity.marketing.GroupDetail;
import com.visionet.syh_mall.entity.marketing.UserCoupon;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.entity.order.OrderRefund;
import com.visionet.syh_mall.entity.order.OrderServiceEntity;
import com.visionet.syh_mall.entity.order.OrderSnapshot;
import com.visionet.syh_mall.entity.order.TradeSetting;
import com.visionet.syh_mall.entity.shop.FulfilRemit;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.NoteRepository;
import com.visionet.syh_mall.repository.OperateLogRepository;
import com.visionet.syh_mall.repository.PayKindsRepository;
import com.visionet.syh_mall.repository.TradeSettingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.cart.ShopCartRepository;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.repository.marketing.CouponRepository;
import com.visionet.syh_mall.repository.marketing.DiscountTimeRepository;
import com.visionet.syh_mall.repository.marketing.DiscountTimeUserLinkRepository;
import com.visionet.syh_mall.repository.marketing.GroupBuyRepository;
import com.visionet.syh_mall.repository.marketing.UserCouponRepository;
import com.visionet.syh_mall.repository.mobile.ExpressTempletRepository;
import com.visionet.syh_mall.repository.mobile.ExpressWayRepository;
import com.visionet.syh_mall.repository.mobile.FulifilRemitRepository;
import com.visionet.syh_mall.repository.mobile.GoodsPicLinkRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.GroupDetailRepository;
import com.visionet.syh_mall.repository.mobile.HomeGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.repository.mobile.SupplyRecordRepository;
import com.visionet.syh_mall.repository.order.OrderRefundRepository;
import com.visionet.syh_mall.repository.order.OrderServiceRepository;
import com.visionet.syh_mall.repository.order.OrderSnapshotRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.service.goods.AuctionJobService;
import com.visionet.syh_mall.service.mobile.ConponsService;
import com.visionet.syh_mall.service.mobile.GoodsService;
import com.visionet.syh_mall.service.scheduler.QuartzManager;
import com.visionet.syh_mall.vo.ExportOrderVo;
import com.visionet.syh_mall.vo.GoodQo;
import com.visionet.syh_mall.vo.OrderInfoVo;
import com.visionet.syh_mall.vo.OrderQo;
import com.visionet.syh_mall.vo.order.OrderVo;

/**
 * @Author DM
 * @version ：2017年8月21日下午5:14:54 订单Service类
 */
@Service
public class OrderService extends BaseService {
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private PayKindsRepository payKindsDao;
	@Autowired
	private ExpressWayRepository expressWayDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private OrderGoodsRepository orderGoodsDao;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;
	@Autowired
	private GoodsPicLinkRepository goodsPicLinkDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired
	private OrderRefundRepository orderRefundDao;
	@Autowired
	private SupplyRecordRepository supplyRecordDao;
	@Autowired
	private OrderSnapshotRepository orderSnapshotDao;
	@Autowired
	private CouponRepository couponDao;
	@Autowired
	private UserCouponRepository userCouponDao;
	@Autowired
	private ShopCartRepository shopCartDao;
	@Autowired
	private KeyMappingRepository keyDao;
	@Autowired
	private OrderServiceRepository orderServiceDao;
	@Autowired
	private HomeGoodsRepository homeGoodsDao;
	@Autowired
	private NoteRepository noteDao;
	@Autowired
	private FulifilRemitRepository fulifilRemitDao;
	@Autowired
	private DiscountTimeRepository discountTimeDao;
	@Autowired
	private DiscountTimeUserLinkRepository discountTimeUserLinkDao;
	@Autowired
	private ConponsService conponsService;
	@Autowired
	private GroupBuyRepository groupBuyDao;
	@Autowired
	private GroupDetailRepository groupDetailDao;
	@Autowired
	private OperateLogRepository operateLogDao;
	@Autowired
	private AuctionJobService auctionJobService;
	@Autowired
	private TradeSettingRepository setDao;
	@Autowired
	private AuctionJobRepository auctionJobRepo;
	@Autowired
	private QuartzManager quartzManager;
	@Autowired
	private ExpressTempletRepository expressTempletDao;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private TradeSettingRepository tradeSettingDao;

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	public List<OrderInfoVo> queryList() throws Exception {
		List<Object[]> obj = orderDao.findCompletedOrderList();
		List<OrderInfoVo> returnVo = new ArrayList<OrderInfoVo>();
		for (Object[] object : obj) {
			OrderInfoVo vo = new OrderInfoVo();
			vo.setOrderID(object[0].toString());
			vo.setBuyerID(object[1].toString());
			vo.setBuyerName(object[2].toString());
			vo.setBuyerProvince(object[3].toString());
			vo.setBuyerCity(object[4].toString());
			vo.setOrderSum(object[5].toString());
			vo.setOrderGoodsID(object[6].toString());
			vo.setOrderGoodsName(object[7].toString());
			vo.setOrderGoodsPrice(new BigDecimal(object[8].toString()));
			vo.setOrderTypeCode(object[9].toString());
			KeyMapping key = keyDao.findByKeyCode(object[9].toString());
			if (null != key && null != key.getId()) {
				vo.setOrderTypeDesc(key.getValueDesc());
			}
			vo.setOrderTime(DateUtil.convertFromString((object[10].toString()), DateUtil.YMD1));
			returnVo.add(vo);
		}
		return returnVo;
	}

	/**
	 * 修改订单状态 @param String @return void @throws
	 */
	public Order changeOrderStatus(String orderNo, String orderType, BigDecimal amount) {
		Order order = orderDao.findByOrderSn(orderNo);
		if (amount.compareTo(order.getTotalPrice().add(order.getExpressFee())) < 0) {
			throw new RestException("订单金额大于实际支付金额异常！");
		}
		if (amount.compareTo(order.getTotalPrice().add(order.getExpressFee())) > 0) {
			throw new RestException("订单金额小于实际支付金额异常！");
		}
		if ("order_unprocessed".equals(orderType)) {// 修改为待发货状态
			if ("order_unpaid".equals(order.getOrderStatusCode())) {// 判断是否为待支付状态
				order.setOrderStatusCode(orderType);
				orderDao.save(order);
				operateLogDao
						.save(addLog(null, order.getBuyerId(), "支付订单完成！订单号：" + order.getOrderSn() + "状态修改为代发货状态！"));
			}
		}
		if ("order_uncomment".equals(orderType)) {// 修改为待评价状态
			if ("order_unreceied".equals(order.getOrderStatusCode())) {// 是否为待收货状态
				order.setOrderStatusCode(orderType);
				orderDao.save(order);
				operateLogDao
						.save(addLog(null, order.getBuyerId(), "订单收货完成！订单号：" + order.getOrderSn() + "状态修改为代待评价状态！"));
			}
		}
		return order;
	}

	/**
	 * 发送短信 @param @return void @throws
	 */
	public void sendMessage(Order order) {
		String content = noteDao.findOne("6").getContent();
		String phone = userDao.findById(order.getShopOwnerId()).getPhone();
		MessageUtils.sendText(phone, "{text}", content);
	}

	/**
	 * 搜索订单 @param @return List<Map<String,Object>> @throws
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchOrders(Map<String, Object> map) throws Exception {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		String keyword = (String) map.get("keyword");// 模糊查询
		PageInfo page = new PageInfo((Integer) map.get("pageIndex"), (Integer) map.get("itemCount"));
		//动态查询数据
		Map<String, Object> searchDate = searchDate(keyword, map, page);
		Page<Order> allOrder = (Page<Order>) searchDate.get("allOrder");
		List<Order> list = (List<Order>) searchDate.get("list");
		// begin 分页数据封装
		orderMap.put("itemCount", allOrder.getTotalElements());
		orderMap.put("pageCount", allOrder.getTotalPages());
		orderMap.put("curPageIndex", page.getPageIndex());
		orderMap.put("hasNext", allOrder.hasNext());
		// end
		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		// 返回数据拼装
		orderList = returnDate(list, orderList);
		orderMap.put("orderInfos", orderList);
		return orderMap;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> returnDate(List<Order> list, List<Map<String, Object>> orderList) {
		for (Order order2 : list) {
			Map<String, Object> inOrder = new HashMap<String, Object>();
			inOrder.put("orderID", order2.getId());
			inOrder.put("orderSN", order2.getOrderSn());
			inOrder.put("purchaseGoodsID", order2.getPurchaseGoodsId());
			inOrder.put("payKindID", order2.getPayKindsId());
			inOrder.put("payKindName", order2.getPayKindsName());
			inOrder.put("groupStatus", order2.getGroupStatus());
			//查询订单商品列表
			List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();
			List<OrderGoods> orders = orderGoodsDao.findByOrderId(order2.getId());
			BigDecimal originalPrice = new BigDecimal(0);
			//查询订单商品以及计算订单金额
			Map<String, Object> returnOrderGoods = returnOrderGoods(orders,originalPrice,goodsList);
			goodsList = (List<Map<String, Object>>) returnOrderGoods.get("goodsList");
			originalPrice = (BigDecimal) returnOrderGoods.get("originalPrice");
			//出参拼接
			inOrder.put("originalPrice", originalPrice);
			inOrder.put("totalPrice", order2.getTotalPrice());
			inOrder.put("buyerID", order2.getBuyerId());
			User user = userDao.findOne(order2.getBuyerId());
			inOrder.put("buyerName", user.getAliasName());
			inOrder.put("buyerPhone", order2.getBuyerPhone());
			inOrder.put("shopID", order2.getShopId());
			Shop shop = shopDao.findOne(order2.getShopId());
			inOrder.put("shopName", shop.getShopName());
			inOrder.put("sellerID", order2.getShopOwnerId());
			User sellerUser = userDao.findOne(order2.getShopOwnerId());
			inOrder.put("sellerName", sellerUser.getAliasName());
			inOrder.put("orderStatusCode", order2.getOrderStatusCode());
			KeyMapping keyMapping = keyMappingDao.findByKeyCode(order2.getOrderStatusCode());
			inOrder.put("orderStatusDesc", keyMapping.getValueDesc());
			inOrder.put("orderTypeCode", order2.getOrderTypeCode());
			inOrder.put("orderExpressFee", order2.getExpressFee());
			inOrder.put("expressBillNo", order2.getExpressBillNo());
			inOrder.put("orderCreateTime", order2.getCreateTime());
			inOrder.put("orderGoods", goodsList);
			inOrder.put("isRefund", order2.getIsRefund());
			inOrder.put("oldTotalPrice", order2.getOldTotalPrice());
			inOrder.put("flowNo", order2.getFlowNo());
			if (StringUtils.isEmpty(order2.getIsRefund())) {
				inOrder.put("isRefundDesc", null);
			} else {
				KeyMapping mapping = keyMappingDao.findByKeyCode(order2.getIsRefund());
				inOrder.put("isRefundDesc", "退款：" + mapping.getValueDesc());
			}
			orderList.add(inOrder);
		}
		return orderList;
	}
	
	private Map<String,Object> returnOrderGoods(List<OrderGoods> orders,BigDecimal originalPrice,List<Map<String, Object>> goodsList){
		Map<String,Object> result = new HashMap<String,Object>();
		for (OrderGoods orderGoods : orders) {
			Map<String, Object> goodsMap = new HashMap<String, Object>();
			goodsMap.put("goodsID", orderGoods.getGoodsId());
			goodsMap.put("goodsName", orderGoods.getGoodsName());
			goodsMap.put("goodsOriginalPrice", orderGoods.getGoodsOriginalPrice());
			goodsMap.put("goodsPrice", orderGoods.getGoodsRealPrice());
			goodsMap.put("goodsNum", orderGoods.getGoodsNum());
			//订单商品图片集
			List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(orderGoods.getGoodsId());
			Iterator<GoodsPicLink> iterator = goodsPicList.iterator();
			if (iterator.hasNext()) {
				GoodsPicLink goodsPicLink = iterator.next();
				FileManage fileManage = fileManageDao.findOne(goodsPicLink.getMaxImgId());
				if (StringUtils.isEmpty(fileManage)) {
					throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
				}
				goodsMap.put("goodsImgUrl", FilePathUtils.fileUrl(fileManage.getFilePath()));
			} else {
				goodsMap.put("goodsImgUrl", null);
			}
			goodsList.add(goodsMap);
			originalPrice = MathUtils.add(originalPrice, orderGoods.getGoodsOriginalPrice());
		}
		result.put("goodsList", goodsList);
		result.put("originalPrice", originalPrice);
		return result;
	}
	
	private Map<String,Object> searchDate(String keyword,Map<String,Object> map,PageInfo page){
		Map<String,Object> result = new HashMap<String,Object>();
		Page<Order> allOrder = null;
		List<Order> list = null;
		// begin 数据查询
		if (!StringUtils.isEmpty(keyword)) {
			// begin 模糊查询
			List<Order> allOrderList = null;
			if (!StringUtils.isEmpty(map.get("buyerID"))) {
				allOrderList = orderDao.getBuyOrders((String) map.get("buyerID"), "%" + keyword + "%");
			} else {
				allOrderList = orderDao.getSellerOrders((String) map.get("sellerID"), "%" + keyword + "%");
			}
			allOrder = new PageImpl<Order>(allOrderList, page.getPageRequestInfo(), allOrderList.size());
			List<Order> content = allOrder.getContent();
			list = PageInfo.getPageContent(content, page.getPageIndex(), page.getItemCount());
			// end
		} else {
			// begin 动态查询入参封装
			Order order = new Order();
			order.setFlowNo((String) map.get("flowNo"));
			order.setBuyerId((String) map.get("buyerID"));
			order.setBuyerPhone((String) map.get("buyerPhone"));
			order.setOrderTypeCode((String) map.get("orderTypeCode"));
			List<String> userIds = null;
			if (StringUtils.isEmpty(map.get("buyerID"))) {
				if (!StringUtils.isEmpty(map.get("buyerName"))) {
					userIds = userDao.getUserId("%" + (String) map.get("buyerName") + "%");
				} else {
					userIds = userDao.getUserId("%");
				}
			}
			order.setShopOwnerId((String) map.get("sellerID"));
			List<String> shopIds = null;
			if (!StringUtils.isEmpty(map.get("shopName"))) {
				shopIds = shopDao.getShopIds("%" + (String) map.get("shopName") + "%");
				if (shopIds.size() == 0) {
					shopIds.add("1");
				}
			} else if (!StringUtils.isEmpty(map.get("shopID"))) {
				shopIds = new ArrayList<String>();
				shopIds.add((String) map.get("shopID"));
			} else {
				shopIds = shopDao.getShopIds("%");
				if (shopIds.size() == 0) {
					shopIds.add("1");
				}
			}
			order.setOrderSn((String) (map.get("orderSN")));
			order.setOrderStatusCode((String) map.get("orderStatusCode"));
			order.setPayKindsId((String) map.get("orderPayKind"));
			Date startTime = null;
			Date endTime = null;
			BigDecimal firstSum=null;
			BigDecimal secondSum=null;
			if (!StringUtils.isEmpty(map.get("startTime"))) {
				startTime = new Date((Long) map.get("startTime"));
			}
			if (!StringUtils.isEmpty(map.get("endTime"))) {
				endTime = DateUtil.seekDate(new Date((Long) map.get("endTime")), 1);
			}
			if(!StringUtils.isEmpty(map.get("firstSum"))){
                 firstSum=MathUtils.getBigDecimal(map.get("firstSum"));
			}
			if(!StringUtils.isEmpty(map.get("secondSum"))){
				secondSum=MathUtils.getBigDecimal(map.get("secondSum"));
			}
			// end
			// begin 动态查询
			Map<String, Object> searchOrders = DynamicParamConvert.searchOrders(order, userIds, shopIds, startTime,
					endTime,firstSum,secondSum);
			Map<String, SearchFilter> parse = SearchFilter.parse(searchOrders);
			allOrder = orderDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), Order.class),
					page.getPageRequestInfo());
			list = allOrder.getContent();
			// end
		}
		// end
		result.put("allOrder", allOrder);
		result.put("list", list);
		return result;
	}

	/**
	 * 生成订单快照 @param Order @return void @throws
	 */
	public boolean saveOrderSnapshot(Order order) {
		boolean bool = false;
		OrderSnapshot orderSnapshot = new OrderSnapshot();
		orderSnapshot.setId(order.getId());
		orderSnapshot.setOrderSn(order.getOrderSn());
		orderSnapshot.setPurchaseGoodsId(order.getPurchaseGoodsId());
		orderSnapshot.setTotalPrice(order.getTotalPrice());
		orderSnapshot.setBuyerId(order.getBuyerId());
		orderSnapshot.setBuyerPhone(order.getBuyerPhone());
		orderSnapshot.setShopId(order.getShopId());
		orderSnapshot.setShopOwnerId(order.getShopOwnerId());
		orderSnapshot.setReceiverName(order.getReceiverName());
		orderSnapshot.setReceiverPhone(order.getReceiverPhone());
		orderSnapshot.setReceiverProvince(order.getReceiverProvince());
		orderSnapshot.setReceiverCity(order.getReceiverCity());
		orderSnapshot.setReceiverArea(order.getReceiverArea());
		orderSnapshot.setReceiverStreet(order.getReceiverStreet());
		orderSnapshot.setReceiverAddress(order.getReceiverAddress());
		orderSnapshot.setOrderStatusCode(order.getOrderStatusCode());
		orderSnapshot.setExpressFee(order.getExpressFee());
		orderSnapshot.setCreateTime(order.getCreateTime());
		orderSnapshot.setUpdateTime(order.getUpdateTime());
		orderSnapshot.setIsDeleted(order.getIsDeleted());
		if (!StringUtils.isEmpty(orderSnapshotDao.save(orderSnapshot))) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 生成拍卖订单 @param @return Map<String,Object> @throws
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getBidOrder(BigDecimal lastTotal, OrderVo orderVo, String userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrderQo> orderInfos = orderVo.getOrderInfos();
		for (OrderQo orderQo : orderInfos) {
			ArrayList<GoodQo> goodsInfos = orderQo.getGoodsInfos();
			for (GoodQo goodQo : goodsInfos) {
				Goods good = goodsDao.findOne(goodQo.getGoodsID());
				// begin 生成订单编号
				Date date = DateUtil.getCurrentDate();
				SequenceUUID sequenceUUID = new SequenceUUID();
				String orderSn = sequenceUUID.getOrderIdByUUId("B");
				// end

				// 邮费
				BigDecimal expressFee = new BigDecimal(0);
				ExpressTemplet expressTemplet = expressTempletDao.findById(good.getExpressTempletId());
				BigDecimal postage = new BigDecimal(0);
				// 商品数量
				Integer goodsNum = goodQo.getGoodsNum();
				expressFee = getExpress(orderQo, good, expressFee, goodQo, expressTemplet, postage, goodsNum);
				// 生成订单信息
				Order saveOrder = createBidOrder(lastTotal, userId, orderQo, good, orderSn, expressFee);
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(saveOrder.getCreateTime());
				List<TradeSetting> setting = (List<TradeSetting>) setDao.findAll();
				Integer orderInterval = setting.get(0).getCloseOrderInterval();
				calendar.add(Calendar.HOUR_OF_DAY, orderInterval);

				// 添加未支付订单的订单关闭任务
				auctionJobService.saveCloseOrderJob(saveOrder.getId(), DateUtil.convertToString(calendar.getTime()));

				// 拍卖商品保存
				saveBidOrderGoods(goodQo, good, date, saveOrder);
				map.put("orderID", saveOrder.getId());
				map.put("orderSn", saveOrder.getOrderSn());
				map.put("orderAmt", saveOrder.getTotalPrice().add(saveOrder.getExpressFee()));
			}
		}
		return map;
	}

	private void saveBidOrderGoods(GoodQo goodQo, Goods good, Date date, Order saveOrder) {
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setOrderId(saveOrder.getId());
		orderGoods.setGoodsId(good.getId());
		orderGoods.setGoodsRealPrice(goodQo.getGoodsPrice());
		orderGoods.setGoodsNum(goodQo.getGoodsNum());
		orderGoods.setGoodsName(good.getGoodsName());
		orderGoods.setGoodsDescription(good.getGoodsDescription());
		orderGoods.setGoodsKindId(good.getGoodsKindId());
		orderGoods.setGoodsOriginalPrice(good.getGoodsPrice());
		orderGoods.setGoodsBidStart(good.getGoodsBidStart());
		orderGoods.setGoodsMinBid(good.getGoodsMinBid());
		orderGoods.setGoodsSn(good.getGoodsSn());
		orderGoods.setIsRecognized(good.getIsRecognized());
		orderGoods.setGoodsQualityCode(good.getGoodsQualityCode());
		orderGoods.setGoodsQualityScore(good.getGoodsQualityScore());
		orderGoods.setRecognizedCode(good.getRecognizedCode());
		orderGoods.setExpressFee(good.getExpressFee());
		orderGoods.setExpressTempletId(good.getExpressTempletId());
		orderGoods.setCreateTime(date);
		orderGoods.setUpdateTime(date);
		orderGoodsDao.save(orderGoods);
	}

	private Order createBidOrder(BigDecimal lastTotal, String userId, OrderQo orderQo, Goods good, String orderSn,
			BigDecimal expressFee) {
		Order order = new Order();
		order.setOrderSn(orderSn);
		order.setTotalPrice(lastTotal);
		order.setOldTotalPrice(lastTotal);
		order.setBuyerId(userId);
		order.setBuyerPhone(userDao.findOne(userId).getPhone());
		order.setShopId(good.getShopId());
		order.setShopOwnerId(good.getOwnerId());
		order.setReceiverName(orderQo.getReceiverName());
		order.setReceiverPhone(orderQo.getReceiverPhone());
		order.setReceiverProvince(orderQo.getReceiverProvince());
		order.setReceiverCity(orderQo.getReceiverCity());
		order.setReceiverArea(orderQo.getReceiverArea());
		order.setReceiverStreet(orderQo.getReceiverStreet());
		order.setReceiverAddress(orderQo.getReceiverAddress());
		order.setOrderRemark(orderQo.getOrderRemark());
		order.setExpressFee(expressFee);
		order.setOrderStatusCode("order_unpaid");
		order.setOrderTypeCode("auction_order");
		Order saveOrder = orderDao.save(order);
		return saveOrder;
	}

	/**
	 * 结算订单 @param OrderQo @return String @throws
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Map<String, Object>> generateOrder(OrderVo orderVo, String userId) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<OrderQo> orderInfos = orderVo.getOrderInfos();
		Integer source = orderVo.getSource();// 订单来源（0：直接购买 1：购物车结算 2：套餐购买）
		for (OrderQo orderQo : orderInfos) {
			ArrayList<GoodQo> goodsInfos = orderQo.getGoodsInfos();
			if (goodsInfos.size() == 0) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.NULL_LIMIT.getDesc());
			}
			Date date = DateUtil.getCurrentDate();
			// 订单编号的生成
			String orderSn = this.getOrderNo();
			Map<String, Object> map = new HashMap<String, Object>();
			// 生成订单
			Map<String, Object> orderMap = CreateOrder(userId, orderQo, goodsInfos, orderSn);
			Order saveOrder = (Order) orderMap.get("order");
			goodsInfos = (ArrayList<GoodQo>) orderMap.get("goodsInfos");
			// 团购活动
			GroupDispose(userId, orderQo, goodsInfos, saveOrder);
			if (!saveOrderSnapshot(saveOrder)) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.ORDER_SNAPSHOT_SAVE_LOSE.getDesc());
			}
			// 保存订单商品到订单商品表
			saveOrderGoods(userId, source, orderQo, goodsInfos, date, saveOrder);
			map.put("orderID", saveOrder.getId());
			map.put("orderSn", saveOrder.getOrderSn());
			map.put("orderAmt", saveOrder.getTotalPrice().add(saveOrder.getExpressFee()));
			list.add(map);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private Map<String,Object> CreateOrder(String userId, OrderQo orderQo, ArrayList<GoodQo> goodsInfos, String orderSn)
			throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Order order = new Order();
		order.setOrderSn(orderSn);
		order.setPayKindsId(orderQo.getPayKindID());
		//验证支付方式
		if (!StringUtils.isEmpty(orderQo.getPayKindID())) {
			PayKinds payKind = payKindsDao.findOne(orderQo.getPayKindID());
			order.setPayKindsName(payKind.getPayMethodName());
			if (StringUtils.isEmpty(payKind)) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
			}
		}
		//计算订单金额与邮费
		Map<String, Object> countOrderTotal = countOrderTotal(orderQo, goodsInfos);
		//通过此商品来获得店铺的信息
		Goods good = (Goods) countOrderTotal.get("good");
		//订单邮费
		BigDecimal expressFee = (BigDecimal) countOrderTotal.get("expressFee");
		//订单货款
		BigDecimal totalPrice = (BigDecimal) countOrderTotal.get("totalPrice");
		//订单类型
		String orderTypeCode = (String) countOrderTotal.get("orderTypeCode");
		
		if (StringUtils.isEmpty(good)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		order.setOldTotalPrice(totalPrice);
		order.setTotalPrice(totalPrice);
		User user = userDao.findOne(userId);
		if (!user.getInvitationCode().equals(orderQo.getSharingCode())) {
			order.setSharingCode(orderQo.getSharingCode());
		}
		order.setBuyerId(userId);
		order.setBuyerPhone(userDao.findOne(userId).getPhone());
		order.setShopId(good.getShopId());
		order.setShopOwnerId(good.getOwnerId());
		order.setReceiverName(orderQo.getReceiverName());
		order.setReceiverPhone(orderQo.getReceiverPhone());
		order.setReceiverProvince(orderQo.getReceiverProvince());
		order.setReceiverCity(orderQo.getReceiverCity());
		order.setReceiverArea(orderQo.getReceiverArea());
		order.setReceiverStreet(orderQo.getReceiverStreet());
		order.setReceiverAddress(orderQo.getReceiverAddress());
		order.setOrderTypeCode(orderTypeCode);
		order.setOrderRemark(orderQo.getOrderRemark());
		order.setExpressFee(expressFee);
		order.setOrderStatusCode("order_unpaid");
		if (!StringUtils.isEmpty(orderQo.getGroupBuyId()) || !StringUtils.isEmpty(orderQo.getGroupDetailId())) {
			order.setGroupStatus("组团中");
		}
		//优惠券处理
		order = CouponDispose(userId, orderQo, order);
		//满减满送处理
		Map<String, Object> map = FulfilRemitDispose(orderQo, goodsInfos, order, good);
		//活动处理后的订单
		order = (Order) map.get("order");
		//订单商品集合
		goodsInfos = (ArrayList<GoodQo>) map.get("goodsInfos");
		
		Order saveOrder = orderDao.save(order);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 3);
		calendar.setTime(saveOrder.getCreateTime());
		List<TradeSetting> setting = (List<TradeSetting>) setDao.findAll();
		Integer orderInterval = setting.get(0).getCloseOrderInterval();
		calendar.add(Calendar.HOUR_OF_DAY, orderInterval);
		// 添加未支付订单的订单关闭任务
		auctionJobService.saveCloseOrderJob(saveOrder.getId(), DateUtil.convertToString(calendar.getTime()));

		operateLogDao
				.save(addLog(null, saveOrder.getBuyerId(), "结算商品生成待支付订单，订单编号：" + saveOrder.getOrderSn() + "！"));
		result.put("order", saveOrder);
		result.put("goodsInfos", goodsInfos);
		return result;
	}
	
	private Map<String,Object> countOrderTotal(OrderQo orderQo, ArrayList<GoodQo> goodsInfos){
		Map<String,Object> result = new HashMap<String,Object>();
		Goods good = null;
		BigDecimal expressFee = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);
		String orderTypeCode = null;
		for (GoodQo goodQo : goodsInfos) {
			totalPrice = BigDecimal
					.valueOf(goodQo.getGoodsNum() * Double.valueOf(goodQo.getGoodsPrice().toString()))
					.add(totalPrice);
			//判断商品类型，通过商品类型给订单赋予订单类型
			good = goodsDao.findOne(goodQo.getGoodsID());
			if ("sale_type".equals(good.getGoodsTypeCode())) {
				orderTypeCode = "sell_order";
			}
			if ("auction_type".equals(good.getGoodsTypeCode())) {
				orderTypeCode = "auction_order";
			}
			if ("purchase_type".equals(good.getGoodsTypeCode())) {
				orderTypeCode = "purchase_order";
			}
			if (StringUtils.isEmpty(good)) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
			}
			if (good.getStockNum() < goodQo.getGoodsNum()) {
				throw new RestException(HttpStatus.ACCEPTED,
						good.getGoodsName() + "-" + BusinessStatus.UNDERSTOCK.getDesc());
			}

			ExpressTemplet expressTemplet = expressTempletDao.findById(good.getExpressTempletId());
			BigDecimal postage = new BigDecimal(0);
			// 商品数量
			Integer goodsNum = goodQo.getGoodsNum();
			//计算运费
			expressFee = getExpress(orderQo, good, expressFee, goodQo, expressTemplet, postage, goodsNum);
		}
		result.put("good", good);
		result.put("expressFee", expressFee);
		result.put("totalPrice", totalPrice);
		result.put("orderTypeCode", orderTypeCode);
		return result;
	}

	private Map<String,Object> FulfilRemitDispose(OrderQo orderQo, ArrayList<GoodQo> goodsInfos, Order order, Goods good) {
		Map<String,Object> result = new HashMap<String,Object>();
		// begin 满减满送活动
		if (!StringUtils.isEmpty(orderQo.getFulfilRemit()) && StringUtils.isEmpty(orderQo.getUsedCouponID())) {
			List<FulfilRemit> fulifilRemits = fulifilRemitDao.findByShopId(good.getShopId());
			for (FulfilRemit fulfilRemit : fulifilRemits) {
				if (order.getTotalPrice().compareTo(fulfilRemit.getFulfilAmt()) >= 0) {
					if (null != fulfilRemit.getRemitAmt()) {
						order.setTotalPrice(order.getTotalPrice().subtract(fulfilRemit.getRemitAmt()));
					}
					if (!StringUtils.isEmpty(fulfilRemit.getGiftGoodsId())) {
						GoodQo e = new GoodQo();
						e.setGoodsID(fulfilRemit.getGiftGoodsId());
						e.setGoodsNum(1);
						e.setGoodsPrice(new BigDecimal(0));
						e.setComplimentary(true);
						e.setFulfilRemitID(fulfilRemit.getId());
						goodsInfos.add(e);
					}
					break;
				}
			}
		}
		result.put("order", order);
		result.put("goodsInfos", goodsInfos);
		return result;
		// end
	}

	private Order CouponDispose(String userId, OrderQo orderQo, Order order) {
		BigDecimal couponValue = new BigDecimal(0);
		// begin 优惠券处理
		if (!StringUtils.isEmpty(orderQo.getUsedCouponID())) {
			Coupon coupon = couponDao.findOne(orderQo.getUsedCouponID());
			coupon.setUsedNum(coupon.getUsedNum() + 1);
			UserCoupon userCoupon = userCouponDao.findOne(userId, coupon.getId());
			if (order.getTotalPrice().compareTo(coupon.getLimitMoney()) < 0) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.CANT_USE_COUPON.getDesc());
			}
			userCoupon.setCouponHaveNum(userCoupon.getCouponHaveNum() - 1);
			if (userCoupon.getCouponHaveNum() == 0) {
				userCoupon.setIsUsed(1);
			}
			userCouponDao.save(userCoupon);
			couponValue = coupon.getCouponValue();
			order.setTotalPrice(order.getTotalPrice().subtract(couponValue));
		}
		// end
		return order;
	}

	private void GroupDispose(String userId, OrderQo orderQo, ArrayList<GoodQo> goodsInfos, Order saveOrder) {
		//团长组团
		if (!StringUtils.isEmpty(orderQo.getGroupBuyId())) {
			boolean groupIsFul = conponsService.groupIsFul(orderQo.getGroupBuyId(), userId,
					goodsInfos.get(0).getGoodsNum());
			if (groupIsFul) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DISCOUNTTIME_ERROR.getDesc());
			}
			conponsService.createGoodsGroup(orderQo.getGroupBuyId(), userId, saveOrder.getId());
		}
		//团员参团
		if (!StringUtils.isEmpty(orderQo.getGroupDetailId())) {
			GroupDetail detail = groupDetailDao.findOne(orderQo.getGroupDetailId());
			boolean groupIsFul = conponsService.groupIsFul(detail.getGroupId(), userId,
					goodsInfos.get(0).getGoodsNum());
			if (groupIsFul) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DISCOUNTTIME_ERROR.getDesc());
			}
			conponsService.addGoodsGroup(orderQo.getGroupDetailId(), userId, saveOrder.getId());
		}
	}

	private void saveOrderGoods(String userId, Integer source, OrderQo orderQo, ArrayList<GoodQo> goodsInfos, Date date,
			Order saveOrder) {
		Goods good = null;
		for (GoodQo goodQo : goodsInfos) {
			good = goodsDao.findOne(goodQo.getGoodsID());
			//团购库存处理
			groupStockDispose(orderQo, goodQo);
			//限时活动处理
			discountTimeDispose(userId, goodQo);
			OrderGoods orderGoods = new OrderGoods();
			orderGoods.setOrderId(saveOrder.getId());
			orderGoods.setGoodsId(good.getId());
			orderGoods.setGoodsRealPrice(goodQo.getGoodsPrice());
			orderGoods.setGoodsNum(goodQo.getGoodsNum());
			if (good.getIsDeleted() == 1) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.GOODS_UNDERCARRIAGE.getDesc());
			}
			logger.info("商品id：{}",good.getId()+"----"+good.getStatusCode());
			if ("goods_undercarriage".equals(good.getStatusCode())&&!goodQo.isComplimentary()) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.GOODS_UNDERCARRIAGE.getDesc());
			}
			good.setStockNum(good.getStockNum() - goodQo.getGoodsNum());
			if (good.getStockNum() == 0) {
				if (goodQo.isComplimentary()) {
					FulfilRemit fulfilRemit = fulifilRemitDao.findByID(goodQo.getFulfilRemitID());
					fulfilRemit.setIsDeleted(1);
					fulifilRemitDao.save(fulfilRemit);
					User sellerUser = userDao.findById(good.getOwnerId());
					MessageUtils.sendText(sellerUser.getPhone(), "{text}",
							"您的满减满送活动:" + fulfilRemit.getId() + "因为赠送商品不足，已经自动失效。");
				}
				good.setStatusCode("goods_undercarriage");
				HomeGoods homeGoods = homeGoodsDao.findByGoodsIdAndIsDelete(good.getId());
				if (!StringUtils.isEmpty(homeGoods)) {
					homeGoods.setIsDeleted(1);
					homeGoodsDao.save(homeGoods);
				}
			}
			goodsDao.save(good);
			orderGoods.setGoodsName(good.getGoodsName());
			orderGoods.setGoodsDescription(good.getGoodsDescription());
			orderGoods.setGoodsKindId(good.getGoodsKindId());
			orderGoods.setGoodsOriginalPrice(good.getGoodsPrice());
			orderGoods.setGoodsBidStart(good.getGoodsBidStart());
			orderGoods.setGoodsMinBid(good.getGoodsMinBid());
			orderGoods.setGoodsSn(good.getGoodsSn());
			orderGoods.setIsRecognized(good.getIsRecognized());
			orderGoods.setGoodsQualityCode(good.getGoodsQualityCode());
			orderGoods.setGoodsQualityScore(good.getGoodsQualityScore());
			orderGoods.setRecognizedCode(good.getRecognizedCode());
			orderGoods.setExpressFee(good.getExpressFee());
			orderGoods.setExpressTempletId(good.getExpressTempletId());
			orderGoods.setCreateTime(date);
			orderGoods.setUpdateTime(date);
			orderGoodsDao.save(orderGoods);
			// begin 清除购物车记录
			if (source == 1) {
				ShopCart cartGoods = shopCartDao.findByUserIdAndGoodId(userId, good.getId());
				if (cartGoods != null) {
					cartGoods.setIsDeleted(1);
					shopCartDao.save(cartGoods);
				}
			}
			// end
		}
	}

	private void discountTimeDispose(String userId, GoodQo goodQo) {
		// begin 限时折扣处理
		if (!StringUtils.isEmpty(goodQo.getDiscountTimeId()) && !goodQo.isComplimentary()) {
			DiscountTimeUserLink discountTimeUserLink = discountTimeUserLinkDao
					.findByDiscountTimeIdAndUserId(goodQo.getDiscountTimeId(), userId);
			DiscountTimeUserLink timeUserLink = null;
			if (discountTimeUserLink == null) {
				timeUserLink = new DiscountTimeUserLink();
			} else {
				timeUserLink = discountTimeUserLink;
			}
			// 判断购买数量和限购数量是否符合
			DiscountTime discountTime = discountTimeDao.findById(goodQo.getDiscountTimeId());
			if (timeUserLink.getDiscountTimeLimitNum() + goodQo.getGoodsNum() > discountTime.getLimitNum()) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DISCOUNTTIME_ERROR.getDesc());
			}
			timeUserLink.setDiscountTimeLimitNum(timeUserLink.getDiscountTimeLimitNum() + goodQo.getGoodsNum());
			timeUserLink.setDiscountTimeId(discountTime.getId());
			timeUserLink.setUserId(userId);
			discountTimeUserLinkDao.save(timeUserLink);
			if (goodQo.getGoodsNum() > discountTime.getLimitNum()) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DISCOUNTTIME_ERROR.getDesc());
			}
			if ((discountTime.getStockNum() - goodQo.getGoodsNum()) < 0) {
				throw new RestException("没有活动库存");
			}
			// 减少活动商品数量
			discountTime.setStockNum(discountTime.getStockNum() - goodQo.getGoodsNum());
			discountTimeDao.save(discountTime);
		}
		// end
	}

	private void groupStockDispose(OrderQo orderQo, GoodQo goodQo) {
		// begin 团购活动库存处理
		if (!StringUtils.isEmpty(orderQo.getGroupDetailId())) {
			GroupDetail groupDetail = groupDetailDao.findOne(orderQo.getGroupDetailId());
			orderQo.setGroupBuyId(groupDetail.getGroupId());
		}
		if (!StringUtils.isEmpty(orderQo.getGroupBuyId())) {
			GroupBuy groupBuy = groupBuyDao.findById(orderQo.getGroupBuyId());
			if (groupBuy.getStockNum() - goodQo.getGoodsNum() < 0) {
				throw new RestException("库存不足");
			}
			if (groupBuy.getMaxNum() < goodQo.getGoodsNum()) {
				throw new RestException("超过个人购买限制！");
			}
			groupBuy.setStockNum(groupBuy.getStockNum() - goodQo.getGoodsNum());
			groupBuyDao.save(groupBuy);
		}
		// end
	}
	
	@SuppressWarnings("static-access")
	public String getOrderNo(){
		SequenceUUID sequenceUUID = new SequenceUUID();
		String orderSn = sequenceUUID.getOrderIdByUUId("B");
		return orderSn;
	}

	@SuppressWarnings("static-access")
	private BigDecimal getExpress(OrderQo orderQo, Goods good, BigDecimal expressFee, GoodQo goodQo,
			ExpressTemplet expressTemplet, BigDecimal postage, Integer goodsNum) {
		if (expressTemplet != null) {
			int express = expressTemplet.getFreeForExpress();
			ExpressWay expressWay = expressWayDao.findBytempletIdAndProvince(good.getExpressTempletId(),
					orderQo.getReceiverProvince());
			// 免邮费的不存在运费方式就免邮费
			if (express == 1) {
				if (expressWay != null) {
					postage = goodsService.getFee(expressWay, goodsNum);
				}
			}
			// 不免邮费的不存在运费方式的按默认邮费计算
			if (express == 0) {
				if (expressWay == null) {
					postage = expressTemplet.getDefaultPostage();
				} else {
					postage = goodsService.getFee(expressWay, goodsNum);
				}
			}
			expressFee = expressFee.compareTo(postage) == 1 ? expressFee : postage;
		} else {
			Goods goods = goodsDao.findById(goodQo.getGoodsID());
			expressFee = expressFee.compareTo(goods.getExpressFee()) == 1 ? expressFee : goods.getExpressFee();
		}
		return expressFee;
	}

	/**
	 * 求购供货 @param OrderQo @return List<Map<String,String>> @throws
	 * 
	 * @throws Exception
	 */
	@Transactional
	public List<Map<String, Object>> BUYResponse(OrderQo orderQo, String userId) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		User user = userDao.findOne(userId);
		if ("authentication_unkno".equals(user.getUserTypeCode())) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.UNCERTIFIED_MEMBER.getDesc());
		}
		ArrayList<GoodQo> goodsInfos = orderQo.getGoodsInfos();
		if (StringUtils.isEmpty(goodsInfos)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.NULL_LIMIT.getDesc());
		}
		list = createBuyOrder(orderQo, list, goodsInfos);
		return list;
	}

	private List<Map<String, Object>> createBuyOrder(OrderQo orderQo, List<Map<String, Object>> list, ArrayList<GoodQo> goodsInfos)
			throws Exception {
		for (GoodQo goodQo : goodsInfos) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> saveBuyOrder = saveBuyOrder(orderQo, goodQo);
			Order saveOrder = (Order) saveBuyOrder.get("saveOrder");
			Goods good = (Goods) saveBuyOrder.get("good");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(saveOrder.getCreateTime());
			List<TradeSetting> setting = (List<TradeSetting>) setDao.findAll();
			Integer orderInterval = setting.get(0).getCloseOrderInterval();
			calendar.add(Calendar.HOUR_OF_DAY, orderInterval);

			// 添加未支付订单的订单关闭任务
			auctionJobService.saveCloseOrderJob(saveOrder.getId(), DateUtil.convertToString(calendar.getTime()));

			if (!saveOrderSnapshot(saveOrder)) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.ORDER_SNAPSHOT_SAVE_LOSE.getDesc());
			}
			//保存订单商品
			saveOrderGoods(orderQo, goodQo, good, saveOrder);
			map.put("orderID", saveOrder.getId());
			map.put("orderSn", saveOrder.getOrderSn());
			map.put("orderAmt", saveOrder.getTotalPrice());
			list.add(map);
		}
		return list;
	}
	
	@SuppressWarnings("static-access")
	private Map<String,Object> saveBuyOrder(OrderQo orderQo,GoodQo goodQo){
		Map<String,Object> result = new HashMap<String,Object>();
		Order order = new Order();
		// begin 订单编号生成
		SequenceUUID sequenceUUID = new SequenceUUID();
		String orderSn = sequenceUUID.getOrderIdByUUId("B");
		// end
		order.setOrderSn(orderSn);
		order.setPayKindsId(orderQo.getPayKindID());
		//支付方式
		if (!StringUtils.isEmpty(orderQo.getPayKindID())) {
			PayKinds payKind = payKindsDao.findOne(orderQo.getPayKindID());
			order.setPayKindsName(payKind.getPayMethodName());
		}
		//订单金额
		order.setTotalPrice(goodsDao.findById(orderQo.getPurchaseGoodsID()).getGoodsPrice()
				.multiply(new BigDecimal(goodQo.getGoodsNum())));
		//原始订单金额
		order.setOldTotalPrice(goodsDao.findById(orderQo.getPurchaseGoodsID()).getGoodsPrice()
				.multiply(new BigDecimal(goodQo.getGoodsNum())));			
		order.setBuyerId(goodsDao.findById(orderQo.getPurchaseGoodsID()).getOwnerId());
		order.setBuyerPhone(
				userDao.findOne(goodsDao.findById(orderQo.getPurchaseGoodsID()).getOwnerId()).getPhone());
		//供货商品
		Goods good = goodsDao.findOne(goodQo.getGoodsID());
		//供货方店铺
		Shop shop = shopDao.findOne(good.getShopId());
		if ("shop_frozen".equals(shop.getStatusCode())) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.FROZEN_SHOP.getDesc());
		}
		Goods BUYGood = goodsDao.findOne(orderQo.getPurchaseGoodsID());
		//求购商品已经供货的数量
		int PurchaseRecordNum = BUYGood.getTotalSales();
		//参数校验
		ParameterVerify(goodQo, good, BUYGood, PurchaseRecordNum);
		//增加求购商品的供货量
		BUYGood.setTotalSales((BUYGood.getTotalSales()==null?0:BUYGood.getTotalSales())+goodQo.getGoodsNum());
		//供满后下架
		if(BUYGood.getTotalSales()==BUYGood.getExpectedNum()){
			BUYGood.setStatusCode("goods_undercarriage");
		}
		goodsDao.save(BUYGood);
		order.setShopId(good.getShopId());
		order.setShopOwnerId(good.getOwnerId());
		order.setReceiverName(orderQo.getReceiverName());
		order.setReceiverPhone(orderQo.getReceiverPhone());
		order.setReceiverProvince(orderQo.getReceiverProvince());
		order.setReceiverCity(orderQo.getReceiverCity());
		order.setReceiverArea(orderQo.getReceiverArea());
		order.setPurchaseGoodsId(orderQo.getPurchaseGoodsID());
		order.setReceiverStreet(orderQo.getReceiverStreet());
		order.setReceiverAddress(orderQo.getReceiverAddress());
		order.setOrderStatusCode("order_unpaid");
		order.setOrderTypeCode("purchase_order");
		Order saveOrder = orderDao.save(order);// 订单生成
		result.put("good", good);
		result.put("saveOrder", saveOrder);
		return result;
	}

	private void ParameterVerify(GoodQo goodQo, Goods good, Goods BUYGood, int PurchaseRecordNum) {
		if (StringUtils.isEmpty(good)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		if (BUYGood.getExpectedNum()<goodQo.getGoodsNum()+(BUYGood.getTotalSales()==null?0:BUYGood.getTotalSales())) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.BUY_GOODS_ERROR.getDesc());
		}
		if (good.getStockNum() < goodQo.getGoodsNum()) {
			throw new RestException(HttpStatus.ACCEPTED,
					good.getGoodsName() + "-" + BusinessStatus.UNDERSTOCK.getDesc());
		}
		if (BUYGood.getExpectedNum() < goodQo.getGoodsNum()) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.SUPPLY_BEYOND_DEMAND.getDesc());
		}
		if (BUYGood.getSupplyMinSum() > goodQo.getGoodsNum()
				&& BUYGood.getSupplyMinSum() <= (BUYGood.getExpectedNum() - PurchaseRecordNum)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.SUPPLY_UNDER_MINDEMAND.getDesc());
		}
		if (BUYGood.getExpectedNum() - PurchaseRecordNum == 0) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DEMAND_OVER.getDesc());
		}
		if ((BUYGood.getExpectedNum() - PurchaseRecordNum) < goodQo.getGoodsNum()) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.SUPPLY_BEYOND_RESIDUE_DEMAND.getDesc());
		}
	}

	private void saveOrderGoods(OrderQo orderQo, GoodQo goodQo, Goods good, Order saveOrder) {
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setOrderId(saveOrder.getId());
		orderGoods.setGoodsId(good.getId());
		orderGoods.setGoodsRealPrice(goodsDao.findById(orderQo.getPurchaseGoodsID()).getGoodsPrice());
		orderGoods.setGoodsNum(goodQo.getGoodsNum());
		good.setStockNum(good.getStockNum()-goodQo.getGoodsNum());
		if(good.getStockNum()==0){
			good.setStatusCode("goods_undercarriage");
		}
		good.setTotalSales((good.getTotalSales()==null?0:good.getTotalSales())+goodQo.getGoodsNum());
		goodsDao.save(good);
		SupplyRecord supplyRecord = supplyRecordDao.findOne(orderQo.getSupplyID());
		supplyRecord.setSupplyOrderId(saveOrder.getId());
		supplyRecordDao.save(supplyRecord);
		orderGoods.setGoodsName(good.getGoodsName());
		orderGoods.setGoodsDescription(good.getGoodsDescription());
		orderGoods.setGoodsKindId(good.getGoodsKindId());
		orderGoods.setGoodsOriginalPrice(good.getGoodsPrice());
		orderGoods.setGoodsBidStart(good.getGoodsBidStart());
		orderGoods.setGoodsMinBid(good.getGoodsMinBid());
		orderGoods.setGoodsSn(good.getGoodsSn());
		orderGoods.setIsRecognized(good.getIsRecognized());
		orderGoods.setGoodsQualityCode(good.getGoodsQualityCode());
		orderGoods.setGoodsQualityScore(good.getGoodsQualityScore());
		orderGoods.setRecognizedCode(good.getRecognizedCode());
		orderGoods.setExpressFee(good.getExpressFee());
		orderGoods.setExpressTempletId(good.getExpressTempletId());
		orderGoods.setCreateTime(DateUtil.getCurrentDate());
		orderGoods.setUpdateTime(DateUtil.getCurrentDate());
		orderGoodsDao.save(orderGoods);// 生成订单商品表数据
	}

	/**
	 * @Title: getPayKinds @Description: 获取支付方式列表 @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */
	public List<Map<String, Object>> getPayKinds() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Iterable<PayKinds> list = payKindsDao.findAll();
		for (PayKinds payKinds : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("payKindID", payKinds.getId());
			map.put("payKindCode", payKinds.getPayMethodCode());
			map.put("payKindName", payKinds.getPayMethodName());
			map.put("isAvailable", payKinds.getIsAvailable());
			result.add(map);
		}
		return result;
	}

	/**
	 * @Title: switchPayKind @Description: 停用/启用平台支持的支付方式 @param 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void switchPayKind(String payKindID, Integer isAvailable) {
		PayKinds one = payKindsDao.findOne(payKindID);
		if (StringUtils.isEmpty(one)) {
			throw new RestException("没有该交易设置");
		}
		one.setUpdateTime(DateUtil.getCurrentDate());
		one.setIsAvailable(isAvailable);
		payKindsDao.save(one);
	}

	/**
	 * 订单发货 @param String @return void @throws
	 * 
	 * @throws Exception
	 */
	public void deliveryOrder(String orderID, String keexpressBillNo) throws Exception {
		Order order = orderDao.findOne(orderID);
		if (StringUtils.isEmpty(order)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		order.setExpressBillNo(keexpressBillNo);
		order.setUpdateTime(DateUtil.getCurrentDate());
		order.setOrderStatusCode("order_unreceied");
		Order save = orderDao.save(order);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 3);
		calendar.setTime(save.getUpdateTime());
		List<TradeSetting> setting = (List<TradeSetting>) setDao.findAll();
		Integer orderInterval = setting.get(0).getCompleteOrderInterval();
		calendar.add(Calendar.DAY_OF_YEAR, orderInterval);
		// 自动确认收货的定时器任务
		auctionJobService.saveAutomaticReceiptJob(save.getOrderSn(), DateUtil.convertToString(calendar.getTime()),save.getFlowNo());

		operateLogDao.save(addLog(null, order.getShopOwnerId(),
				"商家进行发货操作，物流单号为：" + order.getOrderSn() + "的订单经由快递单号：" + keexpressBillNo + "进行运送，订单状态修改之代收货！"));
		String content = null;
		if (order.getPurchaseGoodsId() != null) {
			content = "您求购的商品：" + goodsDao.findById(order.getPurchaseGoodsId()).getGoodsName() + "已发货，物流单号为："
					+ order.getExpressBillNo();
		} else {
			content = "您购买的商品已发货，物流单号为：" + order.getExpressBillNo();
		}
		String phone = order.getBuyerPhone();
		MessageUtils.sendText(phone, "{text}", content);

	}

	/**
	 * 取消订单 @param String @return void @throws
	 */
	@Transactional
	public void cancelOrder(String orderId) {
		logger.info("定时器任务关闭订单：{}", orderId);
		Order order = orderDao.findOne(orderId);
		if (StringUtils.isEmpty(order)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		if (!StringUtils.isEmpty(supplyRecordDao.findBysupplyOrderId(orderId))) {
			SupplyRecord supplyRecord = supplyRecordDao.findBysupplyOrderId(orderId);
			supplyRecord.setIsDeleted(1);
			supplyRecordDao.save(supplyRecord);
		}
		Goods purchaseGood = null;
		if(!StringUtils.isEmpty(order.getPurchaseGoodsId())){			
			purchaseGood = goodsDao.findOne(order.getPurchaseGoodsId());
		}
		order.setUpdateTime(DateUtil.getCurrentDate());
		if ("order_unpaid".equals(order.getOrderStatusCode()) || "order_completed".equals(order.getOrderStatusCode())) {
			order.setOrderStatusCode("order_closed");
			List<OrderGoods> goods = orderGoodsDao.findByOrderId(orderId);
			for (OrderGoods orderGoods : goods) {
				Goods good = goodsDao.findById(orderGoods.getGoodsId());
				if (StringUtils.isEmpty(good)) {
					throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
				}
				good.setStockNum(good.getStockNum() + orderGoods.getGoodsNum());
				good.setStatusCode("goods_grounding");
				goodsDao.save(good);
				if(purchaseGood!=null){					
					purchaseGood.setTotalSales(purchaseGood.getTotalSales()-orderGoods.getGoodsNum());
				}
			}
			if(purchaseGood!=null){					
				goodsDao.save(purchaseGood);
			}
			orderDao.save(order);
			operateLogDao.save(addLog(null, order.getBuyerId(),
					"买家进行取消订单操作，订单号为：" + order.getOrderSn() + "的订单被取消，订单流程结束，订单信息跟踪结束。"));
		} else {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.ORDER_NOT_END.getDesc());
		}
	}

	/**
	 * 评价订单 @param Map @return void @throws
	 */
	@Transactional
	public void scoreOrder(Map<String, Object> map, String userId, Integer source) {
		Order order = orderDao.findOne((String) map.get("orderID"));
		if (StringUtils.isEmpty(order)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		order.setUpdateTime(DateUtil.getCurrentDate());
		order.setOrderScore((Integer) map.get("orderScore"));
		order.setShopOwnerScore((Integer) map.get("shopsScore"));
		order.setOrderComment((String) map.get("orderFeedback"));
		order.setOrderStatusCode("order_completed");
		List<OrderGoods> orderGoods = orderGoodsDao.findByOrderId(order.getId());
		for (OrderGoods orderGood : orderGoods) {
			// 商品销售量
			Goods good = goodsDao.findById(orderGood.getGoodsId());
			if (StringUtils.isEmpty(good.getTotalSales())) {
				good.setTotalSales(0);
			}
			good.setTotalSales(good.getTotalSales() + orderGood.getGoodsNum());
			goodsDao.save(good);
		}
		orderDao.save(order);
		operateLogDao
				.save(addLog(null, order.getBuyerId(), "订单评价完成，订单号为：" + order.getOrderSn() + "状态变更为已关闭状态，订单信息更新流程结束！"));
		OrderServiceEntity orderServiceEntity = new OrderServiceEntity();
		orderServiceEntity.setOrderId(order.getId());
		orderServiceEntity.setBuyerId(order.getBuyerId());
		orderServiceEntity.setSellerId(order.getShopOwnerId());
		orderServiceEntity.setOrderScore(order.getOrderScore());
		orderServiceEntity.setShopOwnerScore(order.getShopOwnerScore());
		orderServiceEntity.setOrderComment(order.getOrderComment());
		orderServiceEntity.setUpdateTime(DateUtil.getCurrentDate());
		orderServiceDao.save(orderServiceEntity);

		// 订单关闭评论
		AuctionJob auctionJob = auctionJobRepo.findByJobName("job_close_review_" + order.getOrderSn());
		if (source == 0) {
			quartzManager.removeJob(auctionJob.getJobName(), auctionJob.getJobGroupName(), auctionJob.getTriggerName(),
					auctionJob.getTriggerGroupName());
		}
		auctionJob.setJobStatus(1);
		auctionJobRepo.save(auctionJob);
	}

	/**
	 * 申请退款 @param String @return void @throws
	 * 
	 * @throws Exception
	 */
	@Transactional
	public void applyRefund(String orderID, String refundReason) throws Exception {
		Order order = orderDao.findOne(orderID);
		//入参校验
		orderRefundVerify(orderID, order);
		OrderRefund orderRefund = new OrderRefund();
		orderRefund.setOrderId(orderID);
		orderRefund.setApplyTime(DateUtil.getCurrentDate());
		if ("order_unreceied".equals(order.getOrderTypeCode())) {
			orderRefund.setRefundSum(order.getTotalPrice());
		} else {
			orderRefund.setRefundSum(order.getTotalPrice().add(order.getExpressFee()));
		}
		orderRefund.setRefundReason(refundReason);
		orderRefund.setBuyerId(order.getBuyerId());
		orderRefund.setBuyerPhone(order.getBuyerPhone());
		orderRefund.setSellerId(order.getShopOwnerId());
		orderRefund.setRefundStatusCode("refund_seller_review");
		orderRefund.setUpdateTime(DateUtil.getCurrentDate());
		orderRefundDao.save(orderRefund);
		order.setIsRefund("refund_seller_review");
		order.setUpdateTime(DateUtil.getCurrentDate());
		Order save = orderDao.save(order);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(save.getUpdateTime());
		List<TradeSetting> setting = (List<TradeSetting>) setDao.findAll();
		Integer orderInterval = setting.get(0).getCloseOrderInterval();
		calendar.add(Calendar.HOUR_OF_DAY, orderInterval * 24);
		// 订单自动通过商家退款审核
		auctionJobService.saveByApplicationJob(save.getId(), DateUtil.convertToString(calendar.getTime()));

		operateLogDao.save(addLog(null, order.getBuyerId(), "买家发起了退款申请，订单号：" + order.getOrderSn() + "的订单等待商家进行审核！"));
		// 查找到退款信息模板
		Note note = noteDao.findOne("12");
		// 短信内容
		String content = note.getContent();
		String phone = userDao.findById(order.getShopOwnerId()).getPhone();
		MessageUtils.sendText(phone, "{text}", content);
	}

	private void orderRefundVerify(String orderID, Order order) {
		if (StringUtils.isEmpty(order)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		if ("order_unpaid".equals(order.getOrderStatusCode())) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.NOT_PAY_ORDER.getDesc());
		}
		if (!StringUtils.isEmpty(orderRefundDao.findOne(orderID))) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.ORDER_IN_DISPOSE.getDesc());
		}
		if (orderRefundDao.findByOrderId(orderID) != null) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.APPLY_REFUND_ERROR.getDesc());
		}
	}

	/**
	 * 订单改价 @param String,BigDecimal @return void @throws
	 */
	@Transactional
	public void modifyOrderAmt(String orderID, BigDecimal orderSum) {
		Order order = orderDao.findOne(orderID);
		if (StringUtils.isEmpty(order)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		order.setUpdateTime(DateUtil.getCurrentDate());
		order.setTotalPrice(orderSum);
		orderDao.save(order);
		operateLogDao.save(addLog(null, order.getShopOwnerId(),
				"卖家对订单号为：" + order.getOrderSn() + "的订单进行了改价操作，修改后的价格为：" + orderSum + "!"));
	}

	/**
	 * 订单修改快递单号 @param @return void @throws
	 */
	@Transactional
	public void modifyExpressNO(String orderID, String expressNO) {
		Order order = orderDao.findOne(orderID);
		if (StringUtils.isEmpty(order)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		if (!StringUtils.isEmpty(order) && order.getIsDeleted() == 1) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.ORDER_LOSE_EFFICACY.getDesc());
		}
		order.setExpressBillNo(expressNO);
		order.setUpdateTime(DateUtil.getCurrentDate());
		orderDao.save(order);
		operateLogDao.save(addLog(null, order.getShopOwnerId(),
				"卖家对订单号为：" + order.getOrderSn() + "的订单进行了修改快递编号的操作，修改后的快递单号为为：" + expressNO + "!"));
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String str = "{\"EBusinessID\": \"1305918\",  \"Success\": true,  \"LogisticCode\": \"3221313\",  \"Shippers\": []}";
		JSONObject json = JSONObject.parseObject(str);
		List<Map<String, Object>> list = (List<Map<String, Object>>) json.get("Shippers");
		System.out.println(list);
	}

	/**
	 * 获取订单收件信息 @param String @return List<Map<String,Object>> @throws
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getOrderReceiverDetail(String string) throws Exception {
		KdApiOrderDistinguish kdApiOrderDistinguish = new KdApiOrderDistinguish();
		String orderTracesByJson = kdApiOrderDistinguish.getOrderTracesByJson(string);
		JSONObject jsonObject = JSONObject.parseObject(orderTracesByJson);
		List<Map<String, Object>> list = (List<Map<String, Object>>) jsonObject.get("Shippers");
		List<String> codes = new ArrayList<String>();
		for (Map<String, Object> map : list) {
			codes.add((String) map.get("ShipperCode"));
		}
		KdniaoTrackQueryAPI kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI();
		String json = null;
		for (String code : codes) {
			json = kdniaoTrackQueryAPI.getOrderTracesByJson(code, string);
			JSONObject news = JSONObject.parseObject(json);
			List<Map<String, Object>> outNews = (List<Map<String, Object>>) news.get("Traces");
			if (outNews.size() != 0) {
				break;
			}
		}
		return json;
	}

	/**
	 * 获取订单详情 @param String @return Map<String,Object> @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrderDetail(String orderId) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		Order order = orderDao.findOne(orderId);
		orderMap.put("orderID", order.getId());
		orderMap.put("orderSN", order.getOrderSn());
		orderMap.put("flowNo", order.getFlowNo());
		orderMap.put("payKindID", order.getPayKindsId());
		orderMap.put("payKindName", order.getPayKindsName());
		List<OrderGoods> orderGoodsList = orderGoodsDao.findByOrderId(order.getId());
		List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();
		BigDecimal originalPrice = new BigDecimal(0);
		//查询订单商品并返回
		Map<String, Object> orderDetailGoods = orderDetailGoods(orderGoodsList, goodsList, originalPrice);
		goodsList = (List<Map<String, Object>>) orderDetailGoods.get("goodsList");
		originalPrice = (BigDecimal) orderDetailGoods.get("originalPrice");
		orderMap.put("originalPrice", originalPrice);
		orderMap.put("totalPrice", order.getTotalPrice());
		orderMap.put("oldTotalPrice", order.getOldTotalPrice());
		orderMap.put("buyerID", order.getBuyerId());
		User user = userDao.findOne(order.getBuyerId());
		if (StringUtils.isEmpty(user)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		orderMap.put("buyerName", user.getAliasName());
		orderMap.put("buyerPhone", order.getBuyerPhone());
		orderMap.put("shopID", order.getShopId());
		Shop shop = shopDao.findOne(order.getShopId());
		if (StringUtils.isEmpty(shop)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		orderMap.put("shopName", shop.getShopName());
		orderMap.put("sellerID", order.getShopOwnerId());
		User sellerUser = userDao.findOne(order.getShopOwnerId());
		if (StringUtils.isEmpty(sellerUser)) {
			throw new RestException(BusinessStatus.DATE_ERROR.getDesc());
		}
		orderMap.put("sellerName", sellerUser.getAliasName());
		orderMap.put("receiverName", order.getReceiverName());
		// orderMap.put("isCanRefund", order.getIsCanRefund());
		orderMap.put("receiverPhone", order.getReceiverPhone());
		orderMap.put("receiverProvince", order.getReceiverProvince());
		orderMap.put("receiverCity", order.getReceiverCity());
		orderMap.put("receiverArea", order.getReceiverArea());
		orderMap.put("receiverStreet", order.getReceiverStreet());
		orderMap.put("receiverAddress", order.getReceiverAddress());
		orderMap.put("orderStatusCode", order.getOrderStatusCode());
		KeyMapping keyMapping = keyMappingDao.findByKeyCode(order.getOrderStatusCode());
		if (StringUtils.isEmpty(keyMapping)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		orderMap.put("orderRemark", order.getOrderRemark());
		orderMap.put("orderStatusDesc", keyMapping.getValueDesc());
		orderMap.put("orderExpressFee", order.getExpressFee());
		orderMap.put("expressBillNO", order.getExpressBillNo());
		orderMap.put("orderCreateTime", order.getCreateTime());
		TradeSetting setting = tradeSettingDao.findOne("1652578215398213");
		orderMap.put("orderCloseTime",
				DateUtil.seekDate(order.getUpdateTime(), setting.getCompleteOrderInterval()).getTime());
		orderMap.put("orderGoods", goodsList);
		orderMap.put("isRefund", order.getIsRefund());
		if (StringUtils.isEmpty(order.getIsRefund())) {
			orderMap.put("isRefundDesc", null);
		} else {
			KeyMapping mapping = keyMappingDao.findByKeyCode(order.getIsRefund());
			orderMap.put("isRefundDesc", mapping.getValueDesc());
		}
		return orderMap;
	}

	private Map<String,Object> orderDetailGoods(List<OrderGoods> orderGoodsList, List<Map<String, Object>> goodsList,
			BigDecimal originalPrice) {
		Map<String,Object> result = new HashMap<String,Object>();
		for (OrderGoods orderGoods : orderGoodsList) {
			Map<String, Object> goodMap = new HashMap<String, Object>();
			originalPrice = MathUtils.add(originalPrice, BigDecimal.valueOf(orderGoods.getGoodsNum()
					* Double.valueOf(orderGoods.getGoodsRealPrice().divide(new BigDecimal(1)).toString())));
			goodMap.put("goodsID", orderGoods.getGoodsId());
			goodMap.put("goodsName", orderGoods.getGoodsName());
			goodMap.put("goodsOriginalPrice", orderGoods.getGoodsOriginalPrice());
			goodMap.put("goodsPrice", orderGoods.getGoodsRealPrice());
			goodMap.put("goodsNum", orderGoods.getGoodsNum());
			List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(orderGoods.getGoodsId());
			Iterator<GoodsPicLink> iterator = goodsPicList.iterator();
			if (iterator.hasNext()) {
				GoodsPicLink goodsPicLink = iterator.next();
				goodMap.put("goodsImgUrl",
						FilePathUtils.fileUrl(fileManageDao.findOne(goodsPicLink.getMaxImgId()).getFilePath()));
			}
			goodsList.add(goodMap);
		}
		result.put("originalPrice", originalPrice);
		result.put("goodsList", goodsList);
		return result;
	}

	/**
	 * 导出订单信息
	 * @param 
	 * @return List<ExportOrderVo>
	 * @throws
	 */
	public List<ExportOrderVo> getOrderExport() {
		List<Object[]> exportOrder = orderDao.findAllOrderByExport();
		List<ExportOrderVo> resultList = new ArrayList<ExportOrderVo>();
		for (Object[] objects : exportOrder) {
			ExportOrderVo exportOrderVo = new ExportOrderVo();
			exportOrderVo.setOrderNo((String) objects[0]);
			exportOrderVo.setCreateTime((Date) objects[1]);
			BigDecimal orderAmt = new BigDecimal(objects[2].toString()).add(new BigDecimal(objects[3].toString()));
			exportOrderVo.setOrderAmt(countAmt(orderAmt));
			exportOrderVo.setGoodsAmt(countAmt((BigDecimal)objects[2]));
			exportOrderVo.setPostage(countAmt((BigDecimal)objects[3]));
			exportOrderVo.setBuyerAliasName((String) objects[4]);
			exportOrderVo.setSellerAliasName((String) objects[5]);
			exportOrderVo.setBuyerShopName((String) objects[6]);
			exportOrderVo.setOrderStatus((String) objects[7]);
			exportOrderVo.setExpressBillNo((String) objects[8]);
			resultList.add(exportOrderVo);
		}
		return resultList;
	}
	
	public static BigDecimal countAmt(BigDecimal amt){
		int indexOf = amt.toString().indexOf(".");
		if(amt.toString().length()<indexOf+3){
			return amt;
		}
		String lastAmt = amt.toString().substring(0, indexOf+3);
		return new BigDecimal(lastAmt);
	}

	public void addSupplyRecord(Map<String, Object> map) {
		SupplyRecord supplyRecord = new SupplyRecord();
		supplyRecord.setGoodsId((String) map.get("PurchaseGoodsID"));
		supplyRecord.setSupplyNum((Integer) map.get("supplyNum"));
		supplyRecord.setSupplyUserId((String) map.get("goodsOwnerID"));
		supplyRecord.setSupplyGoodsId((String) map.get("goodsID"));
		supplyRecordDao.save(supplyRecord);// 生成求购响应信息
	}

	public void orderRemind(Map<String, Object> param) {
		String orderId = (String) param.get("orderID");
		Order order = orderDao.findByOrderId(orderId);
		MessageUtils.sendText(order.getBuyerPhone(), "{text}", "订单号为："+order.getOrderSn()+"的订单被催货，请尽快发货！");
	}
}
