package com.visionet.syh_mall.service.mobile.channel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visionet.syh_mall.entity.channel.CommissionFlow;
import com.visionet.syh_mall.service.account.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.channel.Channel;

import com.visionet.syh_mall.entity.channel.CommissionTally;
import com.visionet.syh_mall.entity.channel.RetailDetail;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.CommissionFlowRepository;
import com.visionet.syh_mall.repository.CommissionSummaryDaoImpl;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.repository.mobile.channel.ChannelRepository;
import com.visionet.syh_mall.repository.mobile.channel.CommissionTallyRepository;
import com.visionet.syh_mall.repository.mobile.channel.RetailDetailRepository;
import com.visionet.syh_mall.repository.mobile.channel.UserHierarchyRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.service.order.OrderService;
import com.visionet.syh_mall.vo.ChannelCollctVo;
import com.visionet.syh_mall.vo.GoodsQo;
import com.visionet.syh_mall.vo.user.UserChannelVo;

/**
 * 会员分销模板service层
 * 
 * @author mulongfei
 * @date 2017年8月23日下午4:52:47
 */
@Service
public class ChannelService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(ChannelService.class);
	@Autowired
	private ChannelRepository channelDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private UserHierarchyRepository userHierarchyDao;
	@Autowired
	private RetailDetailRepository retailDetailDao;
	@Autowired
	private KeyMappingRepository mappingDao;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private CommissionTallyRepository commissionTallyDao;
	@Autowired
	private SoaChannelService soaChannelService;
	@Autowired
	private OrderGoodsRepository goodsDao;
	@Autowired
	private GoodsRepository repository;
	@Autowired
	private UserAccountFlowRepository accountFlowDao;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private CommissionFlowRepository commissionFlowDao;
	@Autowired
	private CommissionSummaryDaoImpl commissionSummaryDaoImpl;

	/**
	 * @Title: searchTemplateFee @Description: 搜索模板比例 @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, com.visionet.syh_mall.vo.KeyMapping> searchTemplateFee() {
		Map<String, com.visionet.syh_mall.vo.KeyMapping> result = new HashMap<String, com.visionet.syh_mall.vo.KeyMapping>();
		List<KeyMapping> list = mappingDao.findByDdGroupCode(1102);
		for (KeyMapping keyMapping : list) {
			com.visionet.syh_mall.vo.KeyMapping map = new com.visionet.syh_mall.vo.KeyMapping();
			map.setChargeID(keyMapping.getId());
			map.setKeyCode(keyMapping.getKeyCode());
			map.setValueDesc(new BigDecimal(String.valueOf(keyMapping.getValueDesc())).multiply(new BigDecimal(100)));
			map.setDdRemark(keyMapping.getDdRemark());
			result.put(keyMapping.getKeyCode(), map);
		}
		return result;
	}

	/**
	 * @Title: searchChannel @Description: 搜索所有的会员分销模块 @param @return
	 *         设定文件 @return List<Map<String,Object>> 返回类型 @throws
	 */
	public List<Channel> searchChannel() {
		List<Channel> list = (List<Channel>) channelDao.findAll();
		for (Channel channel : list) {
			channel.setSellTotalCommission(
					Float.valueOf((new BigDecimal(String.valueOf(channel.getSellTotalCommission()))
							.multiply(new BigDecimal(100)).toString())));
			channel.setSellFirstCommission(
					Float.valueOf((new BigDecimal(String.valueOf(channel.getSellFirstCommission()))
							.multiply(new BigDecimal(100)).toString())));
			channel.setSellSecondCommission(
					Float.valueOf((new BigDecimal(String.valueOf(channel.getSellSecondCommission()))
							.multiply(new BigDecimal(100)).toString())));
			channel.setSellThirdCommission(
					Float.valueOf((new BigDecimal(String.valueOf(channel.getSellThirdCommission()))
							.multiply(new BigDecimal(100)).toString())));

			channel.setTotalCommission(Float.valueOf((new BigDecimal(String.valueOf(channel.getTotalCommission()))
					.multiply(new BigDecimal(100)).toString())));
			channel.setFirstCommission(Float.valueOf((new BigDecimal(String.valueOf(channel.getFirstCommission()))
					.multiply(new BigDecimal(100)).toString())));
			channel.setSecondCommission(Float.valueOf((new BigDecimal(String.valueOf(channel.getSecondCommission()))
					.multiply(new BigDecimal(100)).toString())));
			channel.setThirdCommission(Float.valueOf((new BigDecimal(String.valueOf(channel.getThirdCommission()))
					.multiply(new BigDecimal(100)).toString())));
		}
		return list;
	}

	/**
	 * 添加/编辑会员分销模板
	 * 
	 * @author mulongfei
	 */
	public void reviceChannel(UserChannelVo channelVo, String adminId) {
		if (StringUtils.isEmpty(channelVo.getMbrChannelRuleID())) {
			Channel channel = new Channel();
			channelDao.save(channelVo.converPo(channelVo, channel));
			return;
		}
		Channel channel = channelDao.findOne(channelVo.getMbrChannelRuleID());
		channel.setUpdateTime(DateUtil.getCurrentDate());
		channelDao.save(channelVo.converPo(channelVo, channel));
		addLog(null, adminId, "添加/编辑会员分销模板!");
	}

	/**
	 * 删除会员分销模板
	 * 
	 * @author mulongfei
	 */
	@Transactional
	public void delChannel(String channelId, String adminId) {
		if (channelDao.findOne(channelId) != null) {
			channelDao.delete(channelId);
			addLog(null, adminId, "删除会员分销模板！");
			return;
		}
		throw new RestException("数据不存在");
	}

	/**
	 * @Title: settlementTemplate @Description: 获取结算模板 @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public List<Map<String, Object>> settlementTemplate() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<KeyMapping> list = mappingDao.findByDdGroupCode(1018);
		for (KeyMapping keyMapping : list) {
			Map<String, Object> keyMappingMap = new HashMap<String, Object>();
			keyMappingMap.put("balanceID", keyMapping.getId());
			keyMappingMap.put("balanceDesc", keyMapping.getValueDesc());
			keyMappingMap.put("balanceRemark", keyMapping.getDdRemark());
			result.add(keyMappingMap);
		}
		return result;
	}

	/**
	 * @Title: settlementTemplate @Description: 获取结算模板详情 @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getSettlementDetails(String balanceId) {
		Map<String, Object> result = new HashMap<String, Object>();
		KeyMapping circle = mappingDao.findOne(balanceId);
		result.put("balanceDesc", circle.getValueDesc());
		result.put("balanceRemark", circle.getDdRemark());
		return result;
	}

	/**
	 * @Title: editSettlement @Description: 编辑平台手续费模块 @param @param keyMapping
	 *         设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void editSettlement(String id, String value, String adminId) {
		KeyMapping mapping = mappingDao.findOne(id);
		mapping.setValueDesc(value);
		mappingDao.save(mapping);
		addLog(null, adminId, "编辑平台手续费模块！");
	}

	/**
	 * @Title: searchChildMbrs @Description: 搜索下级分销会员 @param @param
	 *         param @param @param pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchChildMbrs(Map<String, Object> param, PageInfo pageInfo, String adminId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		returnMap.put("itemCount", 0);
		returnMap.put("pageCount", 0);
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", false);
		returnMap.put("userInfos", list);
		List<String> adminSons = userHierarchyDao.getSon(adminId);
		if (adminSons.size() <= 0) {
			param.put("userIds", "0");
		} else {
			param.put("userIds", adminSons);
		}
		if (!StringUtils.isEmpty(param.get("parentUserName"))) {
			List<String> userId = userDao.getUserId((String) param.get("parentUserName"));
			List<String> ids = new ArrayList<String>();
			for (String parentIds : userId) {
				List<String> sonIds = userHierarchyDao.getSon(parentIds);
				ids.addAll(sonIds);
			}
			if (ids.size() <= 0) {
				return returnMap;
			}
			param.put("userId", ids);
		}

		Map<String, SearchFilter> parse = SearchFilter.parse(DynamicParamConvert.searchChildMbrs(param));
		Page<User> mbrUserList = userDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), User.class),
				buildPageRequest(pageInfo));
		for (User user : mbrUserList) {
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userID", user.getId());
			userMap.put("username", user.getAliasName());
			userMap.put("userTypeCode", user.getUserTypeCode());
			KeyMapping mapping = mappingDao.findByKeyCode(user.getUserTypeCode());
			userMap.put("userTypeDesc", mapping.getValueDesc());
			userMap.put("userRegisterTime", user.getCreateTime());
			String father = userHierarchyDao.getFather(user.getId());
			userMap.put("parentUserID", father);
			userMap.put("parentUserName", null);
			if (father != null) {
				User fatherUser = userDao.findById(father);
				userMap.put("parentUserName", fatherUser.getAliasName());
			}
			list.add(userMap);
		}
		returnMap.put("itemCount", mbrUserList.getTotalElements());
		returnMap.put("pageCount", mbrUserList.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", mbrUserList.hasNext());
		returnMap.put("userInfos", list);
		return returnMap;
	}

	/**
	 * 搜索分销会员
	 * 
	 * @author mulongfei
	 */
	public Map<String, Object> searchChannel(Map<String, Object> param, PageInfo pageInfo) {
		Object statrTime = param.get("startTime");
		Object endTime = null;
		if (param.get("endTime") != null) {
			endTime = param.get("endTime");
		}
		// 返回的 Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// 搜索分销会员的检索条件
		Map<String, SearchFilter> parse = SearchFilter.parse(DynamicParamConvert.searchChannel(param));
		Page<User> mbrUserList = userDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), User.class),
				buildPageRequest(pageInfo));

		// 返回的会员集合
		List<Map<String, Object>> oneList = new ArrayList<Map<String, Object>>();

		for (User mbrUser : mbrUserList) {
			Map<String, Object> oneChannel = searchSubChannel(mbrUser.getId(), statrTime, endTime);
			List<String> sonIds = userHierarchyDao.getSon(mbrUser.getId());
			List<Map<String, Object>> twoList = new ArrayList<Map<String, Object>>();
			for (String id : sonIds) {
				Map<String, Object> twoChannel = searchSubChannel(id, statrTime, endTime);
				twoList.add(twoChannel);
				List<String> threeIds = userHierarchyDao.getSon(id);
				List<Map<String, Object>> threeList = new ArrayList<Map<String, Object>>();
				for (String string : threeIds) {
					Map<String, Object> threeChannel = searchSubChannel(string, statrTime, endTime);
					threeList.add(threeChannel);
				}
				twoChannel.put("subMbrs", threeList);
			}
			oneChannel.put("subMbrs", twoList);
			oneList.add(oneChannel);
		}
		returnMap.put("itemCount", mbrUserList.getTotalElements());
		returnMap.put("pageCount", mbrUserList.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", mbrUserList.hasNext());
		returnMap.put("mbrInfos", oneList);
		return returnMap;
	}

	// 获取子分销会员信息
	public Map<String, Object> searchSubChannel(String userId, Object startTime, Object endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		Map<String, Object> mbrUserMap = new HashMap<String, Object>();
		// 搜索分销会员的检索条件
		User mbrUser = userDao.findOne(userId);
		// 分销会员的信息
		mbrUserMap.put("mbrID", userId);
		mbrUserMap.put("mbrLoginName", mbrUser.getLoginName());
		mbrUserMap.put("mbrName", mbrUser.getAliasName());
		mbrUserMap.put("mbrPhone", mbrUser.getPhone());
		mbrUserMap.put("mbrEmail", mbrUser.getMail());
		mbrUserMap.put("mbrImgID", mbrUser.getImgFileId());
		String imgPath = userDao.findMbrImgUrlById(userId);
		mbrUserMap.put("mbrImgUrl", FilePathUtils.fileUrl(imgPath));
		mbrUserMap.put("channelMbrLevel", mbrUser.getChannelLevel());
		mbrUserMap.put("mbrRegisterTime", mbrUser.getCreateTime());
		BigDecimal buyDecimal = new BigDecimal(0);
		buyDecimal = getChannelTradeSum(userId, startTime, endTime);
		mbrUserMap.put("channelTradeSum", buyDecimal);
		return mbrUserMap;
	}

	// 获取会员分销的买的交易总额
	public BigDecimal getChannelTradeSum(String sonId, Object startTime, Object endTime) {
		BigDecimal tradeSum = new BigDecimal(0);
		Map<String, SearchFilter> filter = SearchFilter
				.parse(DynamicParamConvert.getSonChannelTrade(sonId, startTime, endTime));
		List<RetailDetail> retailDetails = retailDetailDao
				.findAll(DynamicSpecifications.bySearchFilter(filter.values(), RetailDetail.class));

		for (RetailDetail retailDetail : retailDetails) {
			tradeSum = tradeSum.add(retailDetail.getBuyRetailAmt());
		}
		return tradeSum;
	}

	/**
	 * @Title: getServiceCharge @Description: 获取订单的手续费 @param @param
	 *         orderId @param @return 设定文件 @return BigDecimal 返回类型 @throws
	 */
	public BigDecimal getServiceCharge(String orderNo) {
		logger.info(" 获取订单的手续费请求参数{}", orderNo);
		Order order = orderDao.findByOrderSn(orderNo);
		BigDecimal price = order.getTotalPrice();

		BigDecimal decimal = new BigDecimal(0);
		// 订单小于最低手续费金额
		KeyMapping minMapping = mappingDao.findByKeyCode("minFee");
		KeyMapping maxMapping = mappingDao.findByKeyCode("maxFee");
		if (price.compareTo(new BigDecimal(minMapping.getValueDesc())) < 0) {
			return decimal;
		}

		List<OrderGoods> list = goodsDao.findByOrderId(order.getId());
		OrderGoods orderGoods = list.get(0);
		Goods goods = repository.findById(orderGoods.getGoodsId());
		if (goods.getGoodsTypeCode().equals("sale_type")) {
			KeyMapping mapping = mappingDao.findByKeyCode("sellFee");
			BigDecimal ratio = new BigDecimal(mapping.getValueDesc());
			decimal = MathUtils.mul(price, ratio);
			// 小于最小手续费
			if (-1 == decimal.compareTo(new BigDecimal(minMapping.getValueDesc()))) {
				decimal = new BigDecimal(minMapping.getValueDesc());
			}
			// 大于最大手续费
			if (1 == decimal.compareTo(new BigDecimal(maxMapping.getValueDesc()))) {
				decimal = new BigDecimal(maxMapping.getValueDesc());
			}

		}
		if (goods.getGoodsTypeCode().equals("purchase_type")) {
			KeyMapping mapping = mappingDao.findByKeyCode("askFee");
			BigDecimal ratio = new BigDecimal(mapping.getValueDesc());
			decimal = MathUtils.mul(price, ratio);
			if (-1 == decimal.compareTo(new BigDecimal(minMapping.getValueDesc()))) {
				decimal = new BigDecimal(minMapping.getValueDesc());
			}
			if (1 == decimal.compareTo(new BigDecimal(maxMapping.getValueDesc()))) {
				decimal = new BigDecimal(maxMapping.getValueDesc());
			}
		}
		if (goods.getGoodsTypeCode().equals("auction_type")) {
			KeyMapping mapping = mappingDao.findByKeyCode("auctionFee");
			BigDecimal ratio = new BigDecimal(mapping.getValueDesc());
			decimal = MathUtils.mul(price, ratio);
			if (-1 == decimal.compareTo(new BigDecimal(minMapping.getValueDesc()))) {
				decimal = new BigDecimal(minMapping.getValueDesc());
			}
			if (1 == decimal.compareTo(new BigDecimal(maxMapping.getValueDesc()))) {
				decimal = new BigDecimal(maxMapping.getValueDesc());
			}
		}
		return decimal;
	}

	/**
	 * @Title: getParent @Description: 增加三层会员的分销流水 @param @param orderId
	 *         设定文件 @return void 返回类型 @throws
	 */

	public void retailDetail(Order order, BigDecimal platform) {

		// 买家会员当前层
		String buyerId = order.getBuyerId();

		// 买家会员父层
		String buyFather = userHierarchyDao.getFather(buyerId);

		// 买家会员祖父层
		String buygrandFather = userHierarchyDao.getFather(buyFather);

		// 卖家会员
		String sellOwnerId = order.getShopOwnerId();

		// 卖家会员父层
		String sellFather = userHierarchyDao.getFather(sellOwnerId);

		// 卖家会员祖父层
		String sellGrandFather = userHierarchyDao.getFather(sellFather);

		Channel channel = channelDao.getOne();
		// 佣金导出明细
		CommissionFlow commissionFlow = new CommissionFlow();
		// 买家用户
		User buyUser = userDao.findById(buyerId);
		// 买家父级用户
		User buyFatherUser = userDao.findById(buyFather);
		commissionFlow.setBuyUserAccount(buyUser.getLoginName());
		commissionFlow.setBuyUserName(buyUser.getAliasName());
		commissionFlow.setBuyOrderNo(order.getOrderSn());
		commissionFlow.setBuyTransactionAmount(OrderService.countAmt(order.getTotalPrice()));
		commissionFlow.setServiceCharge(OrderService.countAmt(getServiceCharge(order.getOrderSn())));
		// 如果有买家父级
		if (buyFather != null) {
			commissionFlow.setBuyFatherUserAccount(buyFatherUser.getLoginName());
			commissionFlow.setBuyFatherUserName(buyFatherUser.getAliasName());
			commissionFlow.setBuyFatherUserCommissionRate(
					null == channel.getSecondCommission() ? null : channel.getSecondCommission().toString());
			BigDecimal buyFatherPrice = MathUtils.mul(platform, MathUtils.getBigDecimal(channel.getSecondCommission()));

			commissionFlow.setBuyFatherUserCommissionFee(OrderService.countAmt(buyFatherPrice));
			commissionFlow.setBuyAllCommissionFee(OrderService.countAmt(buyFatherPrice));
			logger.info("买家分销总金额：{}", OrderService.countAmt(buyFatherPrice));
			// 如果有买家祖父级
			if (buygrandFather != null) {
				User buyGrandFatherUser = userDao.findById(buygrandFather);
				commissionFlow.setBuyGrandFatherUserAccount(buyGrandFatherUser.getLoginName());
				commissionFlow.setBuyGrandFatherUserName(buyGrandFatherUser.getAliasName());
				commissionFlow.setBuyGrandFatherUserCommissionRate(
						null == channel.getFirstCommission() ? null : channel.getFirstCommission().toString());
				// 买家祖父级分销金额
				BigDecimal buygrandFatherFee = MathUtils.mul(platform,
						MathUtils.getBigDecimal(channel.getFirstCommission()));

				commissionFlow.setBuyGrandFatherUserCommissionFee(OrderService.countAmt(buygrandFatherFee));

				BigDecimal sumBuyFatherPrices = OrderService.countAmt(buyFatherPrice)
						.add(OrderService.countAmt(buygrandFatherFee));
				// 买家所有上层分销
				commissionFlow.setBuyAllCommissionFee(sumBuyFatherPrices);
				logger.info("买家分销总金额：{}", sumBuyFatherPrices);
			}

		}
		// 卖家用户
		User sellUser = userDao.findById(sellOwnerId);
		// 卖家父级用户
		User sellFatherUser = userDao.findById(sellFather);
		commissionFlow.setSellUserAccount(sellUser.getLoginName());
		commissionFlow.setSellUserName(sellUser.getAliasName());
		// 如果卖家父级存在
		if (sellFather != null) {
			// 卖家父级分销金额
			BigDecimal sellFatherFee = MathUtils.mul(platform,
					MathUtils.getBigDecimal(channel.getSellSecondCommission()));

			commissionFlow.setSellFatherUserAccount(sellFatherUser.getLoginName());
			commissionFlow.setSellFatherUserName(sellFatherUser.getAliasName());
			commissionFlow.setSellFatherUserCommissionRate(
					null == channel.getSellSecondCommission() ? null : channel.getSellSecondCommission().toString());

			commissionFlow.setSellFatherUserCommissionFee(OrderService.countAmt(sellFatherFee));

			commissionFlow.setSellAllCommissionFee(OrderService.countAmt(sellFatherFee));

			logger.info("卖家分销总金额：{}", OrderService.countAmt(sellFatherFee));
			// 如果卖家祖父级存在
			if (sellGrandFather != null) {
				// 卖家祖父级分销金额
				BigDecimal sellGrandFatherFee = MathUtils.mul(platform,
						MathUtils.getBigDecimal(channel.getSellFirstCommission()));

				User sellGrandFatherUser = userDao.findById(sellGrandFather);
				commissionFlow.setSellGrandFatherUserAccount(sellGrandFatherUser.getLoginName());
				commissionFlow.setSellGrandFatherUserName(sellGrandFatherUser.getAliasName());
				commissionFlow.setSellGrandFatherUserCommissionRate(
						null == channel.getSellFirstCommission() ? null : channel.getSellFirstCommission().toString());
				commissionFlow.setSellGrandFatherUserCommissionFee(OrderService.countAmt(sellGrandFatherFee));

				// 卖家所有上层分销
				commissionFlow.setSellAllCommissionFee(
						OrderService.countAmt(sellFatherFee).add(OrderService.countAmt(sellGrandFatherFee)));
				logger.info("卖家分销总金额：{}",
						OrderService.countAmt(sellFatherFee).add(OrderService.countAmt(sellGrandFatherFee)));

			}
		}
		if (sellFather != null || buyFather != null) {
			commissionFlowDao.save(commissionFlow);
		}
		// 买家有父层没第祖父层的情况
		if (buyFather != null && buygrandFather == null) {
			User user = userDao.findById(buyFather);
			if ("user_normal".equals(user.getUserStatusCode())) {
				Shop shop = shopDao.findByUserId(buyFather);
				if (!StringUtils.isEmpty(shop)) {
					if (shop.getShopIsFrozen() == 0) {
						tallyOrder(order, 2, buyFather, "buy", platform);
					}
				} else {
					tallyOrder(order, 2, buyFather, "buy", platform);
				}
			}
		}

		// 卖家有父层没第祖父层的情况
		if (sellFather != null && sellGrandFather == null) {
			User user = userDao.findById(sellFather);
			if ("user_normal".equals(user.getUserStatusCode())) {
				Shop shop = shopDao.findByUserId(sellFather);
				if (!StringUtils.isEmpty(shop)) {
					if (shop.getShopIsFrozen() == 0) {
						tallyOrder(order, 2, sellFather, "sell", platform);
					}
				} else {
					tallyOrder(order, 2, sellFather, "sell", platform);
				}
			}
		}

		// 三层分销都存在的情况
		if (buygrandFather != null) {
			User grandFatherUser = userDao.findById(buygrandFather);
			if ("user_normal".equals(grandFatherUser.getUserStatusCode())) {
				Shop shop = shopDao.findByUserId(buygrandFather);
				if (!StringUtils.isEmpty(shop)) {
					if (shop.getShopIsFrozen() == 0) {
						tallyOrder(order, 1, buygrandFather, "buy", platform);
					}
				} else {
					tallyOrder(order, 1, buygrandFather, "buy", platform);
				}
			}

			User fatherUser = userDao.findById(buyFather);
			if ("user_normal".equals(fatherUser.getUserStatusCode())) {
				Shop shop = shopDao.findByUserId(buyFather);
				if (!StringUtils.isEmpty(shop)) {
					if (shop.getShopIsFrozen() == 0) {
						tallyOrder(order, 2, buyFather, "buy", platform);
					}
				} else {
					tallyOrder(order, 2, buyFather, "buy", platform);
				}
			}
		}

		// 三层分销都存在的情况
		if (sellGrandFather != null) {
			User grandFatherUser = userDao.findById(sellGrandFather);
			if ("user_normal".equals(grandFatherUser.getUserStatusCode())) {
				Shop shop = shopDao.findByUserId(sellGrandFather);
				if (!StringUtils.isEmpty(shop)) {
					if (shop.getShopIsFrozen() == 0) {
						tallyOrder(order, 1, sellGrandFather, "sell", platform);
					}
				} else {
					tallyOrder(order, 1, sellGrandFather, "sell", platform);
				}
			}

			User fatherUser = userDao.findById(sellFather);
			if ("user_normal".equals(fatherUser.getUserStatusCode())) {
				Shop shop = shopDao.findByUserId(sellFather);
				if (!StringUtils.isEmpty(shop)) {
					if (shop.getShopIsFrozen() == 0) {
						tallyOrder(order, 2, sellFather, "sell", platform);
					}
				} else {
					tallyOrder(order, 2, sellFather, "sell", platform);
				}
			}
		}

	}

	/**
	 * @Title: tallyOrder @Description: 分销会员结算流水的插入 @param 设定文件 @return void
	 *         返回类型 @throws
	 */
	@Transactional
	public void tallyOrder(Order order, int floors, String userID, String userType, BigDecimal platform) {
		Channel channel = channelDao.getOne();
		BigDecimal totalPrice = order.getTotalPrice();
		String orderId = order.getId();
		RetailDetail detail = new RetailDetail();
		// 用户返佣比例
		float commission = 0;
		// 总返佣比例
		float totalCommission = 0;

		if (userType.equals("buy")) {
			if (floors == 1) {
				commission = channel.getFirstCommission();
				detail.setHierarchy("buyGrandFather");
			}
			if (floors == 2) {
				commission = channel.getSecondCommission();
				detail.setHierarchy("buyFather");
			}
			totalCommission = channel.getTotalCommission();
			detail.setBuyRetailAmt(OrderService.countAmt(totalPrice));
		}
		if (userType.equals("sell")) {
			if (floors == 1) {
				commission = channel.getSellFirstCommission();
				detail.setHierarchy("sellGrandFather");
			}
			if (floors == 2) {
				commission = channel.getSellSecondCommission();
				detail.setHierarchy("sellFather");
			}
			totalCommission = channel.getSellTotalCommission();
			detail.setSellRetailAmt(OrderService.countAmt(totalPrice));
		}
		detail.setCommissionAmt(OrderService.countAmt(MathUtils.mul(platform, MathUtils.getBigDecimal(commission))));
		detail.setCommissionRate(MathUtils.getBigDecimal(totalCommission));
		detail.setRetailUserRate(MathUtils.getBigDecimal(commission));
		detail.setRetailObjId(orderId);
		detail.setRetailType(0);
		detail.setRetailUserId(userID);
		retailDetailDao.save(detail);
	}

	/**
	 * @throws Exception
	 * @Title: getRetailDetails @Description: 分销流水 @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */

	public Map<String, Object> getRetailDetails(Map<String, Object> param, PageInfo pageInfo) throws Exception { // 分页的方法
		Map<String, Object> result = new HashMap<String, Object>();
		List<CommissionFlow> commissionFlows = new ArrayList<CommissionFlow>();
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchRetailDetails(param)));
		Page<CommissionFlow> list = commissionFlowDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), CommissionFlow.class),
				buildPageRequest(pageInfo));
		for (CommissionFlow commissionFlow : list) {
			commissionFlows.add(commissionFlow);
		}

		result.put("retailDetailInfos", commissionFlows);
		result = getReturnMap(result, list, pageInfo);
		return result;
	}

	public Map<String, Object> getRetailDetail(Map<String, Object> param, PageInfo pageInfo) throws Exception { // 分页的方法
		Map<String, Object> result = new HashMap<String, Object>();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String commissionId = (String) param.get("commissionId");
		CommissionTally commissionTally = commissionTallyDao.findOne(commissionId);
		User user = userDao.findUserByLoginName(commissionTally.getUserId());
		param.put("userID", user.getId());
		param.put("startTime", commissionTally.getTallyStartTime().getTime());
		param.put("endTime", commissionTally.getTallyEndTime().getTime());
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchRetailDetails(param)));
		Page<RetailDetail> retailDetails = retailDetailDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), RetailDetail.class), buildPageRequest(pageInfo));
		for (RetailDetail retailDetail : retailDetails) {
			Map<String, Object> retailDetailInfos = new HashMap<String, Object>();

			retailDetailInfos.put("loginName", user.getLoginName());
			retailDetailInfos.put("aliasName", user.getAliasName());
			retailDetailInfos.put("retailObjId", retailDetail.getRetailObjId());
			if (retailDetail.getBuyRetailAmt().compareTo(new BigDecimal(0))!= 0) {
				retailDetailInfos.put("orderAmt", retailDetail.getBuyRetailAmt());
			} else {
				retailDetailInfos.put("orderAmt", retailDetail.getSellRetailAmt());
			}
			retailDetailInfos.put("commissionAmt", retailDetail.getCommissionAmt());
			retailDetailInfos.put("commissionRate", retailDetail.getCommissionRate());
			retailDetailInfos.put("retailUserRate", retailDetail.getRetailUserRate());
			retailDetailInfos.put("createTime", retailDetail.getCreateTime());

			list.add(retailDetailInfos);
		}
		result.put("retailDetailInfos", list);
		result = getReturnMap(result, retailDetails, pageInfo);
		return result;

	}

	/**
	 * @Title: commissionSettlement @Description: 搜索佣金结算列表@param @param
	 *         param @param @param pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> commissionList(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, SearchFilter> filter = SearchFilter.parse(DynamicParamConvert.commissionList(param));
		Page<CommissionTally> commissionTallys = commissionTallyDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), CommissionTally.class),
				buildPageRequest(pageInfo));
		List<Map<String, Object>> commissionTallyList = new ArrayList<Map<String, Object>>();
		for (CommissionTally commissionTally : commissionTallys) {
			Map<String, Object> commissionTallyMap = new HashMap<String, Object>();
			commissionTallyMap.put("commissionId", commissionTally.getId());// 结算ID
			commissionTallyMap.put("userId", commissionTally.getUserId());// 会员账号
			commissionTallyMap.put("retailGoodsId", commissionTally.getRetailGoodsId());
			commissionTallyMap.put("retailMbrGrade", commissionTally.getRetailMbrGrade());
			commissionTallyMap.put("retailMbrLevel", commissionTally.getRetailMbrLevel());
			commissionTallyMap.put("tradeAmt", commissionTally.getTradeAmt());// 总交易金额
			commissionTallyMap.put("commissionAmt", commissionTally.getCommissionAmt());// 应结算金额
			commissionTallyMap.put("finalAmt", commissionTally.getFinalAmt());// 实结算金额
			commissionTallyMap.put("tallyStartTime", commissionTally.getTallyStartTime());
			commissionTallyMap.put("tallyEndTime", commissionTally.getTallyEndTime());
			commissionTallyMap.put("refuseReason", commissionTally.getRefuseReason());
			commissionTallyMap.put("commissionStatusCode", commissionTally.getCommissionStatusCode());
			KeyMapping mapping = mappingDao.findByKeyCode(commissionTally.getCommissionStatusCode());
			commissionTallyMap.put("commissionStatusDesc", mapping.getValueDesc());
			commissionTallyList.add(commissionTallyMap);
		}
		result.put("itemCount", commissionTallys.getTotalElements());
		result.put("pageCount", commissionTallys.getTotalPages());
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", commissionTallys.hasNext());
		result.put("commissionTallyInfos", commissionTallyList);
		return result;
	}

	/**
	 * @Title: getChannelExport @Description: 获取分销流水 @param @param
	 *         param @param @return 设定文件 @return List
	 *         <CommissionFlow> 返回类型 @throws
	 */
	public List<CommissionFlow> getChannelExport(Map<String, Object> param) {
		Map<String, SearchFilter> parse = SearchFilter.parse(param);
		List<CommissionFlow> resultList = commissionFlowDao
				.findAll(DynamicSpecifications.bySearchFilter(parse.values(), CommissionFlow.class));
		int i = 1;
		for (CommissionFlow commissionFlow : resultList) {
			commissionFlow.setId(String.valueOf(i));
			i++;
			commissionFlow.setBuyTransactionAmount(OrderService.countAmt(commissionFlow.getBuyTransactionAmount()));
			String rate1 = null == commissionFlow.getBuyFatherUserCommissionRate() ? null
					: commissionFlow.getBuyFatherUserCommissionRate().substring(2, 4) + "%";
			if (rate1 != null && rate1.charAt(0) == '0') {
				rate1 = rate1.substring(1);
			}
			commissionFlow.setBuyFatherUserCommissionRate(rate1);

			BigDecimal fee1 = null == commissionFlow.getBuyFatherUserCommissionFee() ? null
					: commissionFlow.getBuyFatherUserCommissionFee();
			if (fee1 != null) {
				int indexOf = fee1.toString().indexOf(".");
				fee1 = new BigDecimal(fee1.toString().substring(0, indexOf + 3));
			}
			commissionFlow.setBuyFatherUserCommissionFee(fee1);

			String rate2 = null == commissionFlow.getBuyGrandFatherUserCommissionRate() ? null
					: commissionFlow.getBuyGrandFatherUserCommissionRate().substring(2, 4) + "%";
			if (rate2 != null && rate2.charAt(0) == '0') {
				rate2 = rate2.substring(1);
			}
			commissionFlow.setBuyGrandFatherUserCommissionRate(rate2);

			BigDecimal fee2 = null == commissionFlow.getBuyGrandFatherUserCommissionFee() ? null
					: commissionFlow.getBuyGrandFatherUserCommissionFee();
			if (fee2 != null) {
				int indexOf = fee2.toString().indexOf(".");
				fee2 = new BigDecimal(fee2.toString().substring(0, indexOf + 3));
			}
			commissionFlow.setBuyGrandFatherUserCommissionFee(fee2);
			commissionFlow.setBuyAllCommissionFee(addCommissionFee(fee1, fee2));

			String rate3 = null == commissionFlow.getSellFatherUserCommissionRate() ? null
					: commissionFlow.getSellFatherUserCommissionRate().substring(2, 4) + "%";
			if (rate3 != null && rate3.charAt(0) == '0') {
				rate3 = rate3.substring(1);
			}
			commissionFlow.setSellFatherUserCommissionRate(rate3);

			BigDecimal fee3 = null == commissionFlow.getSellFatherUserCommissionFee() ? null
					: commissionFlow.getSellFatherUserCommissionFee();
			if (fee3 != null) {
				int indexOf = fee3.toString().indexOf(".");
				fee3 = new BigDecimal(fee3.toString().substring(0, indexOf + 3));
			}
			commissionFlow.setSellFatherUserCommissionFee(fee3);

			String rate4 = null == commissionFlow.getSellGrandFatherUserCommissionRate() ? null
					: commissionFlow.getSellGrandFatherUserCommissionRate().substring(2, 4) + "%";
			if (rate4 != null && rate4.charAt(0) == '0') {
				rate4 = rate4.substring(1);
			}
			commissionFlow.setSellGrandFatherUserCommissionRate(rate4);

			BigDecimal fee4 = null == commissionFlow.getSellGrandFatherUserCommissionFee() ? null
					: commissionFlow.getSellGrandFatherUserCommissionFee();
			if (fee4 != null) {
				int indexOf = fee4.toString().indexOf(".");
				fee4 = new BigDecimal(fee4.toString().substring(0, indexOf + 3));
			}
			commissionFlow.setSellGrandFatherUserCommissionFee(fee4);
			commissionFlow.setSellAllCommissionFee(addCommissionFee(fee3, fee4));
		}
		return resultList;
	}

	/**
	 * @Title: getChannelExports @Description: 分销汇总导出 @param @param
	 *         param @param @param pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getChannelExports(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchChannel(param)));
		Page<CommissionFlow> resultList = commissionFlowDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), CommissionFlow.class),
				buildPageRequest(pageInfo));
		result.put("commissionFlows", resultList);
		result = getReturnMap(result, resultList, pageInfo);
		return result;
	}

	public static BigDecimal addCommissionFee(BigDecimal fee1, BigDecimal fee2) {
		if (null == fee1) {
			fee1 = new BigDecimal(0);
		}
		if (null == fee2) {
			fee2 = new BigDecimal(0);
		}
		return fee1.add(fee2);
	}

	/**
	 * @Title: getCommission @Description: 得到所有流水表用户@param @param
	 *         startTime @param @param endTime 设定文件 @return void 返回类型 @throws
	 */
	public void getCommissionUser(Long startTime, Long endTime) {
		List<String> users = retailDetailDao.findUsers(new Date(startTime), DateUtil.seekDate(new Date(endTime), 1));
		for (String userId : users) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userID", userId);
			param.put("startTime", startTime);
			param.put("endTime", endTime);
			param.put("settlementStatus", "untreated");
			saveCommission(param);
		}

	}

	/**
	 * @Title: saveCommission @Description:加入佣金结算表中@param 设定文件 @return void
	 *         返回类型 @throws
	 */
	@Transactional
	public void saveCommission(Map<String, Object> param) {
		try {
			String userId = (String) param.get("userID");
			Date startTime = new Date((Long) param.get("startTime"));
			Date endTime = DateUtil.seekDate(new Date((Long) param.get("endTime")), 1);
			Map<String, SearchFilter> filter;
			filter = SearchFilter.parse((DynamicParamConvert.searchRetailDetails(param)));
			List<RetailDetail> RetailDetailList = retailDetailDao
					.findAll(DynamicSpecifications.bySearchFilter(filter.values(), RetailDetail.class));
			// 实际佣金结算金额
			BigDecimal commissionAtm = new BigDecimal(0);
			// 实际交易金额
			BigDecimal transactionAmount = new BigDecimal(0);
			// 计算该用户该时间段的佣金
			for (RetailDetail retailDetail : RetailDetailList) {
				commissionAtm = commissionAtm.add(retailDetail.getCommissionAmt());
				transactionAmount = transactionAmount
						.add((retailDetail.getSellRetailAmt()).add(retailDetail.getBuyRetailAmt()));
			}
			// 添加佣金结算列表
			if (0 != RetailDetailList.size()) {
				User user = userDao.findById(userId);
				CommissionTally commissionTally = new CommissionTally();
				commissionTally.setUserId(user.getLoginName());
				commissionTally.setCommissionAmt(commissionAtm);
				commissionTally.setFinalAmt(commissionAtm);
				commissionTally.setTradeAmt(transactionAmount);
				commissionTally.setCommissionStatusCode("commission_waiting");
				commissionTally.setRetailMbrGrade(null);
				commissionTally.setRetailGoodsId(null);
				commissionTally.setRetailMbrLevel(null);
				commissionTally.setTallyStartTime(startTime);
				commissionTally.setTallyEndTime(endTime);
				commissionTallyDao.save(commissionTally);
				for (RetailDetail retailDetail : RetailDetailList) {
					retailDetail.setSettlementStatus("commission_waiting");
					retailDetail.setUpdateTime(DateUtil.getCurrentDate());
					retailDetailDao.save(retailDetail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: commissionSettlement @Description: TODO佣金结算 @param @param
	 *         commissionID @param @param adminID @param @param
	 *         refuseReason @param @param statusCode 设定文件 @return void
	 *         返回类型 @throws
	 */
	@Transactional
	public void commissionSettlement(String commissionID, String settlementState, String refuseReason, String adminId) {

		CommissionTally commissionTally = commissionTallyDao.findOne(commissionID);
		if (StringUtils.isEmpty(commissionTally)
				&& "commission_accepted".equals(commissionTally.getCommissionStatusCode())) {
			throw new RestException("佣金结算不存在或者结算过了");
		}
		User user = userDao.findUserByphone(commissionTally.getUserId());
		if ("commission_refused".equals(settlementState)) {
			commissionTally.setCommissionStatusCode(settlementState);
			commissionTally.setRefuseReason(refuseReason);
			commissionTally.setReviewUserId(adminId);
			commissionTally.setUpdateTime(DateUtil.getCurrentDate());
			CommissionTally tally = commissionTallyDao.save(commissionTally);
			List<RetailDetail> list = retailDetailDao.findBySettlementStatus(user.getId(), tally.getTallyStartTime(),
					tally.getTallyEndTime());
			for (RetailDetail retailDetail : list) {
				retailDetail.setSettlementStatus("commission_refused");
				retailDetail.setUpdateTime(DateUtil.getCurrentDate());
				retailDetailDao.save(retailDetail);
			}
			return;
		}
		String newOrderSn = SequenceUUID.getOrderIdByUUId("B");
		long amt = Long.valueOf(AmountUtils.changeY2F(commissionTally.getFinalAmt().toString()));
		try {
			if ("commission_accepted".equals(settlementState)) {
				logger.info("第三方接口调用开始");
				soaChannelService.rebate(user.getId(), newOrderSn, amt);
				logger.info("第三方接口调用结束");
				commissionTally.setCommissionStatusCode(settlementState);
				commissionTally.setReviewUserId(adminId);
				commissionTally.setUpdateTime(DateUtil.getCurrentDate());
				CommissionTally tally = commissionTallyDao.save(commissionTally);
				// 将分销会员流水改为结算状态 ，结算成功添加该佣金记录的流水信息
				saveUserAccountFlow(tally, user.getId());
			}
		} catch (RestException e) {
			logger.info("第三方接口调用异常");
			commissionTally.setCommissionStatusCode("commission_error");
			commissionTallyDao.save(commissionTally);
			e.printStackTrace();
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException(e.getMessage());
		}

	}

	/**
	 * @Title: saveUserAccountFlow @Description: 添加用户流水 @param @param tally
	 *         设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void saveUserAccountFlow(CommissionTally tally, String userId) {
		Date startTime = tally.getTallyStartTime();
		Date endTime = tally.getTallyEndTime();
		List<RetailDetail> list = retailDetailDao.findBySettlementStatus(userId, startTime, endTime);
		for (RetailDetail retailDetail : list) {
			retailDetail.setSettlementStatus("commission_accepted");
			retailDetail.setUpdateTime(DateUtil.getCurrentDate());
			retailDetailDao.save(retailDetail);
			UserAccountFlow accountFlow = new UserAccountFlow();
			accountFlow.setAmt(retailDetail.getCommissionAmt());
			accountFlow.setContent("佣金结算");
			Order order = orderDao.findByOrderId(retailDetail.getRetailObjId());
			accountFlow.setOrderNo(order.getOrderSn());
			accountFlow.setBusinessType("交易");
			accountFlow.setFlowType("收入");
			accountFlow.setStatus("success");
			accountFlow.setType(0);
			accountFlow.setUserId(retailDetail.getRetailUserId());
			accountFlowDao.save(accountFlow);
			userAccountService.addAccountAmt(retailDetail.getRetailUserId(), retailDetail.getCommissionAmt(),
					retailDetail.getCommissionAmt(), null);
		}
	}

	/**
	 * @Title: getUsers @Description: 获取分销的所有会员 @param @return 设定文件 @return List
	 *         <String> 返回类型 @throws
	 */
	public List<String> getUsers() {
		return commissionFlowDao.getUsers();
	}

	/**
	 * @Title: commissionSummary @Description: 分销的汇总 @param @param
	 * qo @param @return @param @throws Exception 设定文件 @return
	 * List<ChannelCollctVo> 返回类型 @throws
	 */
	public List<ChannelCollctVo> commissionSummary(GoodsQo qo) throws Exception {
		List<ChannelCollctVo> commissionSummaryList = commissionSummaryDaoImpl.queryRecommendGoods(qo);
		return commissionSummaryList;
	}

}
