package com.visionet.syh_mall.service.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.shop.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.GoodsHierarchy;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.channel.RetailDetail;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsChannelRule;
import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.GoodsHierarchyRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.GoodsChannelRuleRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.channel.RetailDetailRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.marketing.DiscountTimeService;
import com.visionet.syh_mall.service.mobile.channel.GoodsChannelDaoImpl;
import com.visionet.syh_mall.vo.GoodsChannalVo;
import com.visionet.syh_mall.vo.GoodsQo;
import com.visionet.syh_mall.vo.GoodsVo.GoodsInfos;
import com.visionet.syh_mall.web.mobile.BannerController;

/**
 * 商品分销规则的Service层
 * 
 * @author chenghongzhan
 * @version 2017年8月23日 下午6:02:55
 *
 */

@Service
public class GoodsChannelRuleService extends BaseService {
	@Autowired
	private GoodsChannelRuleRepository channelRuleDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private GoodsHierarchyRepository goodsHierarchyDao;
	@Autowired
	private GoodsRepository goodDao;
	@Autowired
	private OrderGoodsRepository orderGoodsDao;
	@Autowired
	private DiscountTimeService discountTimeService;
	@Autowired
	private RetailDetailRepository detailDao;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private GoodsChannelDaoImpl channelDaoImpl;
	@Autowired
	private RetailDetailRepository retailDetailRepository;

	private static final Logger logger = LoggerFactory.getLogger(BannerController.class);

	private static final BigDecimal a=new BigDecimal(0);
	private static final BigDecimal b=new BigDecimal(100);


	/**
	 * @Title: configGoodsChannel @Description: 商家用户管理配置商品分销 @param @param
	 *         channelRuleVo 设定文件 @return void 返回类型 @throws
	 */
	public void configGoodsChannel(GoodsChannalVo channelRuleVo, String adminID) {
		if (channelRuleVo.getFirstRate().compareTo(a)==-1||channelRuleVo.getFirstRate().compareTo(b)==1){
			throw new RestException("请输入0至100内的分佣比例");
		}
		if (StringUtils.isEmpty(channelRuleVo.getGoodsChannelId())) {
			GoodsChannelRule channelRule = new GoodsChannelRule();
			channelRule.setCreateBy(adminID);
			channelRule.setUpdateBy(adminID);
			channelRuleDao.save(channelRuleVo.convertPo(channelRuleVo, channelRule));
			return;
		}
		GoodsChannelRule channelRule = channelRuleDao.findById(channelRuleVo.getGoodsChannelId());
		if (StringUtils.isEmpty(channelRule)) {
			throw new RestException("不存在该商品规则修改失败");
		}
		channelRule.setUpdateBy(adminID);
		channelRule.setUpdateTime(DateUtil.getCurrentDate());
		channelRuleDao.save(channelRuleVo.convertPo(channelRuleVo, channelRule));
	}

	/**
	 * @Title: delGoodsChannelRule @Description: 删除商品分销规则 @param @param
	 *         goodsChannelRuleID 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void delGoodsChannelRule(String goodsChannelRuleID, String adminID) {
		GoodsChannelRule channelRule = channelRuleDao.findById(goodsChannelRuleID);
		if (StringUtils.isEmpty(channelRule)) {
			throw new RestException("没有此条信息删除失败");
		}
		channelRule.setUpdateBy(adminID);
		channelRule.setUpdateTime(DateUtil.getCurrentDate());
		channelRule.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		channelRuleDao.save(channelRule);
	}

	/**
	 * @Title: goodsChannelRule @Description: 获取店铺详情 @param @param
	 *         goodChannelId @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> goodsChannelRule(String goodChannelId) {
		Map<String, Object> result = new HashMap<String, Object>();
		GoodsChannelRule channelRule = channelRuleDao.findById(goodChannelId);
		if (null == channelRule) {
			throw new RestException("没有该分销规则");
		}
		result.put("firstCommission", channelRule.getFirstCommission().multiply(new BigDecimal(100)));
		result.put("goodsChannelRuleId", channelRule.getId());
		result.put("formworkName", channelRule.getFormworkName());
		result.put("goodsChannelId", channelRule.getId());
		result.put("shopId", channelRule.getShopId());
		return result;
	}

	/**
	 * @Title: getGoodsChannelRule @Description: 配置商品分销 @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getGoodsChannelRule(String shopID, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchChannel(shopID)));
		List<Map<String, Object>> goodsChannelList = new ArrayList<Map<String, Object>>();
		// 分页的方法
		Page<GoodsChannelRule> list = channelRuleDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), GoodsChannelRule.class),
				buildPageRequest(pageInfo));
		for (GoodsChannelRule goodsChannelRule : list) {
			Map<String, Object> goodsChannelMap = new HashMap<String, Object>();
			goodsChannelMap.put("firstCommission", goodsChannelRule.getFirstCommission().multiply(new BigDecimal(100)));
			goodsChannelMap.put("goodsChannelRuleId", goodsChannelRule.getId());
			goodsChannelMap.put("formworkName", goodsChannelRule.getFormworkName());
			goodsChannelList.add(goodsChannelMap);
		}
		result.put("shopInfos", goodsChannelList);
		result.put("itemCount", list.getTotalElements());
		result.put("pageCount", list.getTotalPages());
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", list.hasNext());
		return result;
	}

	/**
	 * @Title: changeGoodChannel @Description: 选择商品分销模板 @param @param
	 *         goodId @param @param goodChannelId 设定文件 @return void 返回类型 @throws
	 */
	public void selectFormwork(String goodId, String formworklId) {
		Goods goods = goodDao.findById(goodId);
		goods.setGoodsChannelRule(formworklId);
		goods.setUpdateTime(new Date());
		goodDao.save(goods);
	}

	/**
	 * @Title: getGoodChannelAmount @Description: 获取商品分销金额 @param @param
	 *         sharingCode @param @return 设定文件 @return BigDecimal 返回类型 @throws
	 */
	@Transactional
	public BigDecimal getGoodChannelAmount(Order order) {
		BigDecimal decimal = new BigDecimal(0);
		// 分享码不为空
		if (null != order.getSharingCode()) {
			// 获得订单商品
			List<OrderGoods> list = orderGoodsDao.findByOrderId(order.getId());
			for (OrderGoods orderGoods : list) {
				Goods goods = goodDao.findById(orderGoods.getGoodsId());
				// 商品分销规则为空则无分销
				if (null == goods.getGoodsChannelRule()) {
					continue;
				}
				// 该商品的分销规则
				GoodsChannelRule channelRule = channelRuleDao.findById(goods.getGoodsChannelRule());
				// 返佣比例
				BigDecimal commission = channelRule.getFirstCommission();
				// 商品价格
				BigDecimal goodPrice = new BigDecimal(0);
				DiscountTime discountTime = discountTimeService.takeDiscountTime(null, goods.getId(), null);// 限时活动
				if (!StringUtils.isEmpty(discountTime)) {
					goodPrice = discountTime.getDiscountPrice();
				} else {
					goodPrice = orderGoods.getGoodsRealPrice();
					logger.info("商品价格为:{}", goodPrice);
				}

				// 返佣的金额
				decimal = MathUtils.mul(goodPrice.multiply(new BigDecimal(orderGoods.getGoodsNum())), commission);

				// 分享用户
				User user = userDao.findByInvitationCode(order.getSharingCode());
				// 添加待通知商品分销流水
				if (0 <= goodPrice.compareTo(new BigDecimal(0))) {
					RetailDetail detail = new RetailDetail();
					detail.setBuyRetailAmt(goodPrice.multiply(new BigDecimal(orderGoods.getGoodsNum())));
					detail.setCommissionAmt(decimal);
					detail.setCommissionRate(channelRule.getFirstCommission());
					detail.setRetailUserRate(channelRule.getFirstCommission());
					detail.setRetailObjId(order.getId());
					detail.setRetailType(1);
					detail.setRetailUserId(user.getId());
					detailDao.save(detail);
				}
			}

		}
		return decimal;
	}
	
	
	@Transactional
	public BigDecimal getGoodChannelFee(Order order) {
		BigDecimal decimal = new BigDecimal(0);
		// 分享码不为空
		if (null != order.getSharingCode()) {
			// 获得订单商品
			List<OrderGoods> list = orderGoodsDao.findByOrderId(order.getId());
			for (OrderGoods orderGoods : list) {
				Goods goods = goodDao.findById(orderGoods.getGoodsId());
				// 商品分销规则为空则无分销
				if (null == goods.getGoodsChannelRule()) {
					continue;
				}
				// 该商品的分销规则
				GoodsChannelRule channelRule = channelRuleDao.findById(goods.getGoodsChannelRule());

				// 返佣比例
				BigDecimal commission = channelRule.getFirstCommission();
				// 商品价格
				BigDecimal goodPrice = new BigDecimal(0);
				DiscountTime discountTime = discountTimeService.takeDiscountTime(null, goods.getId(), null);// 限时活动
				if (!StringUtils.isEmpty(discountTime)) {
					goodPrice = discountTime.getDiscountPrice();
				} else {
					goodPrice = orderGoods.getGoodsRealPrice();
					logger.info("商品价格为:{}", goodPrice);
				}

				// 返佣的金额
				decimal = MathUtils.mul(goodPrice.multiply(new BigDecimal(orderGoods.getGoodsNum())), commission);

			}

		}
		return decimal;
	}
	

	/**
	 * @Title: getGoodChannelUser @Description: 商品分销用户 @param @param
	 *         order @param @return 设定文件 @return String 返回类型 @throws
	 */
	@Transactional
	public String getGoodChannelUser(Order order) {
		String shareUserId = null;
		// 分享码不为空
		if (null != order.getSharingCode()) {
			// 获得订单商品
			List<OrderGoods> list = orderGoodsDao.findByOrderId(order.getId());
			for (OrderGoods orderGoods : list) {
				Goods goods = goodDao.findById(orderGoods.getGoodsId());
				// 商品分销规则为空则无分销
				if (null == goods.getGoodsChannelRule()) {
					break;
				}
				// 分享用户
				User user = userDao.findByInvitationCode(order.getSharingCode());
				shareUserId = user.getId();
			}

		}
		return shareUserId;
	}

	/**
	 * @Title: getGoodsPromotion @Description:获取店铺商品分销流水 @param @param
	 *         userId @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Page<String> searchChannelGoods(GoodsQo qo) throws Exception {
		PageRequest pr = getPageRequest(qo.getPageIndex(), qo.getItemCount(), qo.getOrderConditions());
		return channelDaoImpl.queryRecommendGoods(qo, pr);
	}


	/**
	 * 获取商品分销
	 * @param param
	 * @param pageInfo
	 * @return
	 */
   public Map<String, Object> getGoodsChannel( Map<String, Object> param,PageInfo pageInfo){
	   Map<String, Object> result = new HashMap<String, Object>();
	   List<Map<String, Object>> retailList = new ArrayList<Map<String, Object>>();
	   result = getReturnMap(result, null, pageInfo);

	   // 非空查询的参数
	   Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchGoodsChannel(param)));
	   // 分页的方法
	   Page<RetailDetail> list = retailDetailRepository.findAll(DynamicSpecifications.bySearchFilter(filter.values(), RetailDetail.class),
			   buildPageRequest(pageInfo));
	   for (RetailDetail retail:list
			) {
			Map<String,Object> retailMap = new HashMap<>();
			//卖家用户
		   User buyUser = userDao.findById(retail.getRetailUserId());

		    retailMap.put("userAccount",buyUser.getLoginName());
		    retailMap.put("userName",buyUser.getAliasName());
		    retailMap.put("commissionFee",retail.getCommissionAmt());
		    retailMap.put("commissionRate",retail.getCommissionRate());
		    retailMap.put("orderNo",retail.getRetailObjId());
		    retailMap.put("createTime",retail.getCreateTime());
		    retailMap.put("orderFee",retail.getBuyRetailAmt());
		    retailList.add(retailMap);

	   }
	   result.put("goodsChannel", retailList);
	   result = getReturnMap(result, list, pageInfo);
	   return result;


   }



	/**
	 * 商品分销汇总
	 * @param goodList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getGoodsPromotion(Page<String> goodList) throws Exception {
		List<Map<String, Object>> extensionList = new ArrayList<Map<String, Object>>();
		for (String goodId : goodList) {
			Goods goods = goodDao.findById(goodId);
			Map<String, Object> goodMap = new HashMap<String, Object>();
			List<OrderGoods> orderGoods = orderGoodsDao.findBygoodsId(goodId);
			Integer extensionNum = goodsHierarchyDao.findBygoodsId(goodId);
			Integer extensionVolaMount = 0;
			BigDecimal extensionVolaFee = new BigDecimal(0);
			BigDecimal alreadySettledFee = new BigDecimal(0);
			for (OrderGoods orderGoods2 : orderGoods) {
				String orderId = orderGoods2.getOrderId();
				Order order = orderDao.findByOrderId(orderId);
				if ("order_uncomment".equals(order.getOrderStatusCode()) || "order_completed".equals(order.getOrderStatusCode())) {
					if (null != order.getSharingCode()) {
						extensionVolaMount += orderGoods2.getGoodsNum();
						extensionVolaFee = extensionVolaFee.add(order.getTotalPrice());
						alreadySettledFee = alreadySettledFee.add(getGoodChannelFee(order));
					}
				}
			}
			// 推广次数
			goodMap.put("extensionNum", extensionNum);
			// 商品名称
			goodMap.put("goodsName", goods.getGoodsName());
			// 推广成交数量
			goodMap.put("extensionVolaMount", extensionVolaMount);
			// 推广成交金额
			goodMap.put("extensionVolaFee", extensionVolaFee);
			// 已结算金额
			goodMap.put("alreadySettledFee", alreadySettledFee);

			extensionList.add(goodMap);
		}
		return extensionList;
	}

	/**
	 * @Title: commodityDistribution @Description:分享商品 @param @param
	 *         goodsID @param @param parentShareCode @param @param adminID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void commodityDistribution(String goodsID, String shareCode) {
		User user = userDao.findByInvitationCode(shareCode);
		if (null != user) {
			GoodsHierarchy goodsHierarchy = new GoodsHierarchy();
			goodsHierarchy.setSharingUserId(user.getId());
			goodsHierarchy.setSharingCode(shareCode);
			goodsHierarchy.setGoodsID(goodsID);
			goodsHierarchyDao.save(goodsHierarchy);
		}
	}

}
