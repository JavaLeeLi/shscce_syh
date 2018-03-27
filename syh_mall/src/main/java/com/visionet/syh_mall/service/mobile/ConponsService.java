package com.visionet.syh_mall.service.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.entity.marketing.Coupon;
import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.entity.marketing.GroupBuy;
import com.visionet.syh_mall.entity.marketing.GroupDetail;
import com.visionet.syh_mall.entity.marketing.GroupUser;
import com.visionet.syh_mall.entity.marketing.UserCoupon;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.entity.order.OrderRefund;
import com.visionet.syh_mall.entity.service.ShopsMarketing;
import com.visionet.syh_mall.entity.shop.ComboPatch;
import com.visionet.syh_mall.entity.shop.FulfilRemit;
import com.visionet.syh_mall.entity.shop.Marketing;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.marketing.ComboGoodsRepository;
import com.visionet.syh_mall.repository.marketing.ComboPatchRepository;
import com.visionet.syh_mall.repository.marketing.CouponRepository;
import com.visionet.syh_mall.repository.marketing.DiscountTimeRepository;
import com.visionet.syh_mall.repository.marketing.GroupBuyRepository;
import com.visionet.syh_mall.repository.marketing.UserCouponRepository;
import com.visionet.syh_mall.repository.mobile.FulifilRemitRepository;
import com.visionet.syh_mall.repository.mobile.GoodsPicLinkRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.GroupDetailRepository;
import com.visionet.syh_mall.repository.mobile.GroupUserRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.repository.order.OrderRefundRepository;
import com.visionet.syh_mall.repository.syhservice.MarketingRepository;
import com.visionet.syh_mall.repository.syhservice.ShopsMarketingRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.service.goods.GroupJobService;
import com.visionet.syh_mall.service.order.OrderCancService;
import com.visionet.syh_mall.vo.CouponsVo;
import com.visionet.syh_mall.vo.GroupBuyVo;
import com.visionet.syh_mall.vo.shop.ComboGoodVo;
import com.visionet.syh_mall.vo.shop.ComboGoods;
import com.visionet.syh_mall.vo.shop.ComboPatchVo;
import com.visionet.syh_mall.vo.shop.DiscountTimeVo;
import com.visionet.syh_mall.vo.shop.FulfilRemitVo;

/**
 * @ClassName: ConponsService
 * @Description: 优惠券和活动的Service
 * @author chenghongzhan
 * @date 2017年9月30日 上午11:04:07
 *
 */
@Service
public class ConponsService extends BaseService {

	@Autowired
	private FulifilRemitRepository fulifilRemitDao;
	@Autowired
	private DiscountTimeRepository discountTimeDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private ComboPatchRepository comboPatchDao;
	@Autowired
	private ComboGoodsRepository comboGoodsDao;
	@Autowired
	private GroupBuyRepository groupBuyDao;
	@Autowired
	private CouponRepository couponDao;
	@Autowired
	private GroupDetailRepository detailDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private GroupUserRepository groupUserDao;
	@Autowired
	private OrderGoodsRepository orderGoodsDao;
	@Autowired
	private GoodsPicLinkRepository goodsPicLinkDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private OrderCancService orderCancService;
	@Autowired
	private GroupJobService groupJobService;
	@Autowired
	private ShopsMarketingRepository shopsMarketingDao;
	@Autowired
	private MarketingRepository marketDao;
	@Autowired
	private UserCouponRepository userCouponDao;
	@Autowired
	private OrderRefundRepository orderRefundDao;

	/**
	 * @Title: searchCoupons @Description: 搜索优惠券 @param @param
	 *         admin @param @param param @param @param pageInfo @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchCoupons(Map<String, Object> param, PageInfo pageInfo) {
		if (!StringUtils.isEmpty(param.get("shopID")) && StringUtils.isEmpty(param.get("source"))) {
			boolean hasMarketing = hasMarketing((String) param.get("shopID"), "402890595f2fa901135f3283836w0122");
			if (!hasMarketing) {
				throw new RestException("未购买服务");
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if (null != param.get("userId")) {
			param.put("startTime", new Date().getTime());
			param.put("endTime", new Date().getTime());
		}
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchCoupons(param)));
		Page<Coupon> coupons = couponDao.findAll(DynamicSpecifications.bySearchFilter(filter.values(), Coupon.class),
				buildPageRequest(pageInfo));
		List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
		for (Coupon coupon : coupons) {
			Map<String, Object> couponMap = new HashMap<String, Object>();
			couponMap.put("couponID", coupon.getId());
			couponMap.put("shopID", coupon.getShopId());
			couponMap.put("couponName", coupon.getCouponName());
			couponMap.put("couponValue", coupon.getCouponValue());
			couponMap.put("couponLimitNum", coupon.getCouponNum());
			couponMap.put("couponLimitAmt", coupon.getLimitMoney());
			couponMap.put("couponIssueNum", coupon.getIssueNum());
			couponMap.put("couponClaimNum", coupon.getClaimNum());
			UserCoupon userCoupon = userCouponDao.findOne((String) param.get("userId"), coupon.getId());
			couponMap.put("passableClaimNum", coupon.getCouponNum());
			if (null != userCoupon) {
				couponMap.put("passableClaimNum", coupon.getCouponNum() - userCoupon.getCouponNum());
			}
			if (coupon.getExpirationTime().compareTo(new Date()) == -1) {
				coupon.setIsAvailable(0);
				coupon = couponDao.save(coupon);
			}
			couponMap.put("couponUsedNum", coupon.getUsedNum());
			couponMap.put("effectiveTime", coupon.getEffectiveTime());
			couponMap.put("expireTime", coupon.getExpirationTime());
			couponMap.put("isAvailable", coupon.getIsAvailable());
			//如果是进行中的优惠券禁止修改
			if(DateUtil.compare(DateUtil.getCurrentDate(), coupon.getEffectiveTime(), coupon.getExpirationTime())){
				couponMap.put("isCanUpdate", false);
			}else{				
				couponMap.put("isCanUpdate", true);
			}
			couponList.add(couponMap);
		}
		result = getReturnMap(result, coupons, pageInfo);
		result.put("couponInfos", couponList);
		return result;
	}

	/**
	 * @Title: searchCoupons @Description: 搜索优惠券 @param @param
	 *         admin @param @param param @param @param pageInfo @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchCoupon(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null != param.get("userId")) {
			param.put("startTime", new Date().getTime());
			param.put("endTime", new Date().getTime());
		}
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchCoupon(param)));
		Page<Coupon> coupons = couponDao.findAll(DynamicSpecifications.bySearchFilter(filter.values(), Coupon.class),
				buildPageRequest(pageInfo));
		List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
		for (Coupon coupon : coupons) {
			Map<String, Object> couponMap = new HashMap<String, Object>();
			couponMap.put("couponID", coupon.getId());
			couponMap.put("shopID", coupon.getShopId());
			couponMap.put("couponName", coupon.getCouponName());
			couponMap.put("couponValue", coupon.getCouponValue());
			couponMap.put("couponLimitNum", coupon.getCouponNum());
			couponMap.put("couponLimitAmt", coupon.getLimitMoney());
			couponMap.put("couponIssueNum", coupon.getIssueNum());
			couponMap.put("couponClaimNum", coupon.getClaimNum());
			UserCoupon userCoupon = userCouponDao.findOne((String) param.get("userId"), coupon.getId());
			couponMap.put("passableClaimNum", coupon.getCouponNum());
			if (null != userCoupon) {
				couponMap.put("passableClaimNum", coupon.getCouponNum() - userCoupon.getCouponNum());
			}
			couponMap.put("couponUsedNum", coupon.getUsedNum());
			couponMap.put("effectiveTime", coupon.getEffectiveTime());
			couponMap.put("expireTime", coupon.getExpirationTime());
			if (coupon.getExpirationTime().compareTo(new Date()) == -1) {
				coupon.setIsAvailable(0);
				coupon = couponDao.save(coupon);
			}
			couponMap.put("isAvailable", coupon.getIsAvailable());

			couponList.add(couponMap);
		}
		result = getReturnMap(result,coupons, pageInfo);
		result.put("couponInfos", couponList);
		return result;
	}

	/**
	 * 相关服务是否有效 @param @return boolean @throws
	 */
	public boolean hasMarketing(String ShopId, String marketingId) {
		boolean bool = false;
		ShopsMarketing shopsMarketing = shopsMarketingDao.findMarketingHasUse(ShopId, marketingId);
		if (shopsMarketing != null && shopsMarketing.getStatus() == 0) {
			bool = true;
		}
		return bool;
	}

	public ShopsMarketing Marketing(String ShopId, String marketingId) {
		ShopsMarketing shopsMarketing = shopsMarketingDao.findMarketingHasUse(ShopId, marketingId);
		return shopsMarketing;
	}

	/**
	 * @Title: addCoupon @Description: 添加编辑优惠券 @param @param
	 *         fulfilRemitVo @param @param admin 设定文件 @return void 返回类型 @throws
	 */
	public void addCoupon(CouponsVo couponsVo) {
		Marketing marketing = marketDao.findByMarketingCode("SAVER_TICKET");
		boolean hasMarketing = hasMarketing(couponsVo.getShopID(), marketing.getId());
		ShopsMarketing shopsMarketing = Marketing(couponsVo.getShopID(), marketing.getId());
		if (hasMarketing == false) {
			throw new RestException("未购买优惠券服务");
		}
		if (couponsVo.getEffectiveTime().getTime() > couponsVo.getExpireTime().getTime()) {
			throw new RestException("生效时间必须小于过期时间");
		}
		if (couponsVo.getExpireTime().getTime() < DateUtil.getCurrentDate().getTime()) {
			throw new RestException("过期时间不能小于当前时间");
		}
		if (couponsVo.getExpireTime().getTime() > shopsMarketing.getValidityDay().getTime()) {
			throw new RestException("过期时间超过服务有效期");
		}
		if (StringUtils.isEmpty(couponsVo.getCouponID())) {
			Coupon coupon = new Coupon();
			couponDao.save(couponsVo.converPo(couponsVo, coupon));
			return;
		}
		Coupon coupon = couponDao.findById(couponsVo.getCouponID());
		coupon.setUpdateTime(DateUtil.getCurrentDate());
		couponDao.save(couponsVo.converPo(couponsVo, coupon));
	}

	/**
	 * @Title: delFulfil disableCoupon: 优惠券失效 @param @param fulfilID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void disableCoupon(String id) {
		Coupon coupon = couponDao.findById(id);
		if (StringUtils.isEmpty(coupon)) {
			throw new RestException("没有该优惠券");
		}
		coupon.setUpdateTime(DateUtil.getCurrentDate());
		coupon.setIsAvailable(SysConstants.DELETE_FLAG_NO);
		couponDao.save(coupon);
	}

	/**
	 * @Title: getFulfils @Description: 满减满送活动列表 @param @param
	 *         shopID @param @return 设定文件 @return List<Map<String,Object>>
	 *         返回类型 @throws
	 */
	public List<Map<String, Object>> getFulfils(Map<String, String> param) {
		if (!StringUtils.isEmpty(param.get("shopID")) && StringUtils.isEmpty(param.get("source"))) {
			boolean hasMarketing = hasMarketing((String) param.get("shopID"), "402890595f2f3901132f3283836w0122");
			if (!hasMarketing) {
				throw new RestException("未购买服务");
			}
		}
		List<Map<String, Object>> fulfilRemitList = new ArrayList<Map<String, Object>>();
		if (!StringUtils.isEmpty(param.get("shopID")) && !StringUtils.isEmpty(param.get("source"))) {
			boolean hasMarketing = hasMarketing((String) param.get("shopID"), "402890595f2f3901132f3283836w0122");
			if (!hasMarketing) {
				return fulfilRemitList;
			}
		}
		List<FulfilRemit> fulfilRemits = fulifilRemitDao.findByShopId(param.get("shopID"));
		for (FulfilRemit fulfilRemit : fulfilRemits) {
			Map<String, Object> fulfilRemitMap = new HashMap<String, Object>();
			fulfilRemitMap.put("fulfilID", fulfilRemit.getId());
			fulfilRemitMap.put("shopID", fulfilRemit.getShopId());
			fulfilRemitMap.put("fulfilAmt", fulfilRemit.getFulfilAmt());
			fulfilRemitMap.put("remitAmt", fulfilRemit.getRemitAmt());
			fulfilRemitMap.put("giftGoodsID", fulfilRemit.getGiftGoodsId());
			Goods goods = goodsDao.findById(fulfilRemit.getGiftGoodsId());
			fulfilRemitMap.put("giftGoodsName", StringUtils.isEmpty(goods) ? null : goods.getGoodsName());
			fulfilRemitList.add(fulfilRemitMap);
		}
		return fulfilRemitList;
	}

	/**
	 * @Title: addFulfil @Description: 添加编辑满减满送 @param @param fulfilRemitVo
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void addFulfil(FulfilRemitVo fulfilRemitVo) {
		Marketing marketing = marketDao.findByMarketingCode("MONEY_OFF");
		boolean hasMarketing = hasMarketing(fulfilRemitVo.getShopID(), marketing.getId());
		if (fulfilRemitVo.getFulfilAmt().compareTo(fulfilRemitVo.getRemitAmt()) == -1) {
			throw new RestException("减免额度不能高于商品金额");
		}
		if (hasMarketing == false) {
			throw new RestException("未购买满减满送服务");
		}
		if (StringUtils.isEmpty(fulfilRemitVo.getFulfilID())) {
			FulfilRemit fulfilRemit = new FulfilRemit();
			FulfilRemit converPo = fulfilRemitVo.converPo(fulfilRemitVo, fulfilRemit);
			FulfilRemit oldFulfilRemit = fulifilRemitDao.findByFulfilAmtAndShopIdAndIsDeleted(converPo.getFulfilAmt(),
					converPo.getShopId(), 0);
			if (!StringUtils.isEmpty(oldFulfilRemit)) {
				throw new RestException(BusinessStatus.FULFILREMIT_ERROR.getDesc());
			}
			fulifilRemitDao.save(converPo);
			return;
		}
		FulfilRemit fulfilRemit = fulifilRemitDao.findByID(fulfilRemitVo.getFulfilID());
		fulfilRemit.setUpdateTime(DateUtil.getCurrentDate());
		FulfilRemit converPo = fulfilRemitVo.converPo(fulfilRemitVo, fulfilRemit);
		FulfilRemit oldFulfilRemit = fulifilRemitDao.findByFulfilAmtAndShopIdAndIsDeleted(converPo.getFulfilAmt(),
				converPo.getShopId(), 0);
		if (!StringUtils.isEmpty(oldFulfilRemit) && !oldFulfilRemit.getId().equals(fulfilRemitVo.getFulfilID())) {
			throw new RestException(BusinessStatus.FULFILREMIT_ERROR.getDesc());
		}
		fulifilRemitDao.save(converPo);
	}

	/**
	 * @Title: delFulfil @Description: 删除满减满送 @param @param fulfilID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void delFulfil(String fulfilID) {
		FulfilRemit fulfilRemit = fulifilRemitDao.findByID(fulfilID);
		if (StringUtils.isEmpty(fulfilRemit)) {
			throw new RestException("没有该满减满送");
		}
		fulfilRemit.setUpdateTime(DateUtil.getCurrentDate());
		fulfilRemit.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		fulifilRemitDao.save(fulfilRemit);
	}

	/**
	 * @Title: searchDiscounts @Description: 搜索限时折扣 @param @param
	 *         shopID @param @return 设定文件 @return List<Map<String,Object>>
	 *         返回类型 @throws
	 */
	public Map<String, Object> searchDiscounts(Map<String, Object> param, PageInfo pageInfo) {
		if (!StringUtils.isEmpty(param.get("shopID")) && StringUtils.isEmpty(param.get("source"))) {
			boolean hasMarketing = hasMarketing((String) param.get("shopID"), "402890595f2fa901135f3283836e0123");
			if (!hasMarketing) {
				throw new RestException("未购买服务");
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> discountTimeList = new ArrayList<Map<String, Object>>();
		result = getReturnMap(result,null, pageInfo);
		result.put("discountInfos", discountTimeList);
		if (!StringUtils.isEmpty(param.get("goodsName"))) {
			List<String> ids = goodsDao.findBygoodsName((String) param.get("goodsName"));
			if (ids.size() <= 0) {
				return result;
			}
			param.put("goodsIds", ids);
		}
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchDiscounts(param)));
		Page<DiscountTime> discountTimes = discountTimeDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), DiscountTime.class), buildPageRequest(pageInfo));
		for (DiscountTime discountTime : discountTimes) {
			Map<String, Object> discountTimeMap = new HashMap<String, Object>();
			discountTimeMap.put("discountID", discountTime.getId());
			discountTimeMap.put("shopID", discountTime.getShopId());
			discountTimeMap.put("goodsID", discountTime.getGoodsId());
			discountTimeMap.put("stockNum", goodsDao.findById(discountTime.getGoodsId()).getStockNum());// 商品库存
			discountTimeMap.put("discountName", discountTime.getDiscountName());

			Goods goods = goodsDao.findById(discountTime.getGoodsId());
			discountTimeMap.put("goodsName", goods.getGoodsName());
			discountTimeMap.put("goodsPrice", goods.getGoodsPrice());
			discountTimeMap.put("discountPrice", discountTime.getDiscountPrice());
			discountTimeMap.put("discountLimitNum", discountTime.getLimitNum());
			if (discountTime.getEndTime().getTime() < DateUtil.getCurrentDate().getTime()) {
				discountTimeMap.put("discountStatusDesc", "已过期");
			}
			if (discountTime.getStartTime().getTime() > DateUtil.getCurrentDate().getTime()) {
				discountTimeMap.put("discountStatusDesc", "未开始");
			}
			if (discountTime.getStartTime().getTime() < DateUtil.getCurrentDate().getTime()
					&& discountTime.getEndTime().getTime() > DateUtil.getCurrentDate().getTime()) {
				discountTimeMap.put("discountStatusDesc", "进行中");
			}
			Integer salesNum = orderGoodsDao.findByGoodsId(discountTime.getGoodsId(), discountTime.getStartTime(),
					discountTime.getEndTime());
			if (StringUtils.isEmpty(salesNum)) {
				salesNum = 0;
			}
			discountTimeMap.put("salesNum", salesNum);
			discountTimeMap.put("discountStockNum", discountTime.getStockNum());// 活动库存
			discountTimeMap.put("startTime", discountTime.getStartTime());
			discountTimeMap.put("endTime", discountTime.getEndTime());
			discountTimeList.add(discountTimeMap);
		}
		result = getReturnMap(result,discountTimes, pageInfo);
		result.put("discountInfos", discountTimeList);
		return result;
	}

	/**
	 * @Title: addDiscount @Description: 添加限时折扣 @param @param discountTimeVo
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void addDiscount(DiscountTimeVo discountTimeVo) {
		Marketing marketing = marketDao.findByMarketingCode("LIMITED_TIME");
		boolean hasMarketing = hasMarketing(discountTimeVo.getShopID(), marketing.getId());
		ShopsMarketing shopsMarketing = Marketing(discountTimeVo.getShopID(), marketing.getId());
		if (hasMarketing == false) {
			throw new RestException("未购买限时折扣服务");
		}
		if (discountTimeVo.getStartTime() > discountTimeVo.getEndTime()) {
			throw new RestException("活动开始时间必须大于活动结束时间");
		}
		if (discountTimeVo.getEndTime() < DateUtil.getCurrentDate().getTime()) {
			throw new RestException("活动结束时间必须大于当前时间");
		}
		if (discountTimeVo.getStartTime() < DateUtil.getCurrentDate().getTime()) {
			throw new RestException("活动开始时间必须大于当前时间");
		}
		if (discountTimeVo.getEndTime() > shopsMarketing.getValidityDay().getTime()) {
			throw new RestException("活动结束时间超出服务有效期");
		}
		if (discountTimeVo.getDiscountLimitNum() > discountTimeVo.getDiscountStockNum()) {
			throw new RestException("活动库存不能小于限购数量");
		}
		DiscountTime discountTimes = discountTimeDao.findByShopIdAndGoodsId(discountTimeVo.getShopID(),
				discountTimeVo.getGoodsID());
		if (StringUtils.isEmpty(discountTimeVo.getDiscountID())) {
			if (!StringUtils.isEmpty(discountTimes)) {
				if (discountTimes.getEndTime().getTime() > discountTimeVo.getStartTime()) {
					throw new RestException("该店铺商品在该时间段,已经有限时折扣活动。如果想有该商品限时折扣请去编辑或者等活动结束后添加");
				}
			}
			DiscountTime discountTime = new DiscountTime();
			discountTimeDao.save(discountTimeVo.converPo(discountTimeVo, discountTime));
			return;
		}
		DiscountTime discountTime = discountTimeDao.findById(discountTimeVo.getDiscountID());
		if (StringUtils.isEmpty(discountTime)) {
			throw new RestException("没有该限时折扣活动");
		}
		discountTime.setUpdateTime(DateUtil.getCurrentDate());
		discountTimeDao.save(discountTimeVo.converPo(discountTimeVo, discountTime));
	}

	/**
	 * @Title: delDiscount @Description: 删除限时折扣 @param @param discountID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void delDiscount(String discountID) {
		DiscountTime discountTime = discountTimeDao.findById(discountID);
		if (StringUtils.isEmpty(discountTime)) {
			throw new RestException("没有该限时折扣");
		}
		discountTime.setUpdateTime(DateUtil.getCurrentDate());
		discountTime.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		discountTimeDao.save(discountTime);
	}

	/**
	 * @Title: searchCombos @Description: 搜索套餐搭配 @param @param
	 *         param @param @param pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchCombos(Map<String, Object> param, PageInfo pageInfo) {
		if (!StringUtils.isEmpty(param.get("shopID")) && StringUtils.isEmpty(param.get("source"))) {
			boolean hasMarketing = hasMarketing((String) param.get("shopID"), "402890595f2f390113213223836w0122");
			if (!hasMarketing) {
				throw new RestException("未购买服务");
			}
		}
		// 返回的结果集
		Map<String, Object> result = new HashMap<String, Object>();
		// begin 套餐搭配查询
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchCombos(param)));
		Page<ComboPatch> comboPatchs = comboPatchDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), ComboPatch.class), buildPageRequest(pageInfo));
		// end
		List<Map<String, Object>> comboPatchList = new ArrayList<Map<String, Object>>();
		// begin 套餐搭配出参拼接
		for (ComboPatch comboPatch : comboPatchs) {
			Map<String, Object> comboPatchMap = new HashMap<String, Object>();
			comboPatchMap.put("comboID", comboPatch.getId());
			comboPatchMap.put("shopID", comboPatch.getShopId());
			comboPatchMap.put("comboName", comboPatch.getComboName());
			comboPatchMap.put("comboDesc", comboPatch.getComboDescription());
			comboPatchMap.put("startTime", comboPatch.getStartTime());
			comboPatchMap.put("endTime", comboPatch.getEndTime());
			if (DateUtil.compare(DateUtil.getCurrentDate(), comboPatch.getStartTime(), comboPatch.getEndTime())) {
				comboPatchMap.put("comboStatus", "进行中");
			} else if (DateUtil.getCurrentDate().before(comboPatch.getStartTime())) {
				comboPatchMap.put("comboStatus", "未开始");
			} else {
				comboPatchMap.put("comboStatus", "已结束");
			}
			// begin 套餐商品查询
			List<ComboGoods> comboGoods = comboGoodsDao.findByComboIds(comboPatch.getId());
			List<Map<String, Object>> comboGoodsList = new ArrayList<Map<String, Object>>();
			for (ComboGoods comboGood : comboGoods) {
				Map<String, Object> comboGoodMap = new HashMap<String, Object>();
				comboGoodMap.put("comboGoodsId", comboGood.getId());
				comboGoodMap.put("goodsID", comboGood.getGoodsId());
				Goods goods = goodsDao.findById(comboGood.getGoodsId());
				comboGoodMap.put("goodsName", goods.getGoodsName());
				comboGoodMap.put("goodsPrice", goods.getGoodsPrice());
				List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(comboGood.getGoodsId());
				if (goodsPicList.size() > 0) {
					FileManage fileManage = fileManageDao.findOne(goodsPicList.get(0).getMaxImgId());
					comboGoodMap.put("goodsImage", FilePathUtils.fileUrl(fileManage.getFilePath()));
				}
				comboGoodMap.put("goodsComboPrice", comboGood.getDiscountPrice());
				comboGoodMap.put("goodsSort", comboGood.getComboSort());
				comboGoodMap.put("isMain", comboGood.getIsMain());
				comboGoodsList.add(comboGoodMap);
			}
			// end
			comboPatchMap.put("comboGoods", comboGoodsList);
			comboPatchList.add(comboPatchMap);
		}
		// end
		result = getReturnMap(result, comboPatchs,pageInfo);
		result.put("comboInfos", comboPatchList);
		return result;
	}

	/**
	 * 删除套餐单配内的商品 @param @return List<Map<String,Object>> @throws
	 */
	public void delGoodsCombos(String id) {
		ComboGoods one = comboGoodsDao.findOne(id);
		if (!StringUtils.isEmpty(one)) {
			one.setIsDeleted(SysConstants.DELETE_FLAG_YES);
			comboGoodsDao.save(one);
			return;
		}
		throw new RestException("找不到该优惠商品");
	}

	/**
	 * 获取商品套餐搭配 @param @return List<Map<String,Object>> @throws
	 */
	public List<Map<String, Object>> searchGoodsCombos(Map<String, Object> param) {
		List<ComboPatch> comboPatchs = comboPatchDao.findbyGoodsId((String) param.get("goodsId"));
		List<Map<String, Object>> comboPatchList = new ArrayList<Map<String, Object>>();
		// begin 套餐搭配出参拼接
		for (ComboPatch comboPatch : comboPatchs) {
			Map<String, Object> comboPatchMap = new HashMap<String, Object>();
			comboPatchMap.put("comboID", comboPatch.getId());
			comboPatchMap.put("shopID", comboPatch.getShopId());
			comboPatchMap.put("comboName", comboPatch.getComboName());
			comboPatchMap.put("comboDesc", comboPatch.getComboDescription());
			comboPatchMap.put("startTime", comboPatch.getStartTime());
			comboPatchMap.put("endTime", comboPatch.getEndTime());
			List<Map<String, Object>> comboGoodsList = new ArrayList<Map<String, Object>>();
			// begin 套餐商品查询
			List<ComboGoods> comboGoods = comboGoodsDao.findByComboIds(comboPatch.getId());
			for (ComboGoods comboGood : comboGoods) {
				Map<String, Object> comboGoodMap = new HashMap<String, Object>();
				comboGoodMap.put("comboGoodsId", comboGood.getId());
				comboGoodMap.put("goodsID", comboGood.getGoodsId());
				Goods goods = goodsDao.findById(comboGood.getGoodsId());
				comboGoodMap.put("goodsName", goods.getGoodsName());
				comboGoodMap.put("goodsPrice", goods.getGoodsPrice());
				List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(comboGood.getGoodsId());
				if (goodsPicList.size() > 0) {
					FileManage fileManage = fileManageDao.findOne(goodsPicList.get(0).getMaxImgId());
					comboGoodMap.put("goodsImage", FilePathUtils.fileUrl(fileManage.getFilePath()));
				}
				comboGoodMap.put("goodsExpree", goods.getExpressFee());
				comboGoodMap.put("goodsComboPrice", comboGood.getDiscountPrice());
				comboGoodMap.put("goodsSort", comboGood.getComboSort());
				comboGoodMap.put("isMain", comboGood.getIsMain());
				Map<String, Object> expressMap = new HashMap<String, Object>();
				String goodId = comboGood.getGoodsId();
				Goods goods2 = goodsDao.findById(goodId);
				expressMap.put("id", goods2.getExpressTempletId());
				comboGoodMap.put("expressTemplet", expressMap);
				comboGoodsList.add(comboGoodMap);
			}
			// end
			comboPatchMap.put("comboGoods", comboGoodsList);
			comboPatchList.add(comboPatchMap);
		}
		// end
		return comboPatchList;
	}

	/**
	 * @Title: addCombo @Description:添加/编辑套餐搭配 @param @param comboPatchVo
	 *         设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void addCombo(ComboPatchVo comboPatchVo) {
		Marketing marketing = marketDao.findByMarketingCode("CLASSICAL_MIX");
		boolean hasMarketing = hasMarketing(comboPatchVo.getShopID(), marketing.getId());
		ShopsMarketing shopsMarketing = Marketing(comboPatchVo.getShopID(), marketing.getId());
		if (hasMarketing == false) {
			throw new RestException("未购买套餐搭配服务");
		}
		if (DateUtil.getCurrentDate().after(comboPatchVo.getStartTime())){
			throw new RestException("进行中的活动禁止修改");
		}
		if (comboPatchVo.getStartTime().getTime() > comboPatchVo.getEndTime().getTime()) {
			throw new RestException("套餐搭配的开始时间不能大于结束时间");
		}
		if (comboPatchVo.getEndTime().getTime() < DateUtil.getCurrentDate().getTime()) {
			throw new RestException("套餐搭配的结束时间不能小于当前时间");
		}
		if (comboPatchVo.getStartTime().getTime() < DateUtil.getCurrentDate().getTime()) {
			throw new RestException("套餐搭配的开始时间不能小于当前时间");
		}
		if (comboPatchVo.getEndTime().getTime() > shopsMarketing.getValidityDay().getTime()) {
			throw new RestException("套餐结束时间超出服务有效时间");
		}
		// 添加套餐搭配
		if (StringUtils.isEmpty(comboPatchVo.getComboID())) {
			ComboPatch comboPatch = new ComboPatch();
			ComboPatch patch = comboPatchDao.save(comboPatchVo.converPo(comboPatchVo, comboPatch));

			List<ComboGoodVo> goods = comboPatchVo.getComboGoods();
			for (ComboGoodVo comboGoodVo : goods) {
				ComboGoods comboGoods = new ComboGoods();
				comboGoodsDao.save(comboPatchVo.converPo(comboGoodVo, comboGoods, patch.getId()));
			}
		}
		// 编辑套餐搭配
		if (!StringUtils.isEmpty(comboPatchVo.getComboID())) {
			ComboPatch comboPatch = comboPatchDao.findById(comboPatchVo.getComboID());
			if (StringUtils.isEmpty(comboPatch)) {
				throw new RestException("套餐搭配不存在");
			}
			comboPatch.setUpdateTime(DateUtil.getCurrentDate());
			comboPatchDao.save(comboPatchVo.converPo(comboPatchVo, comboPatch));
			String comboID = comboPatchVo.getComboID();
			List<ComboGoodVo> goods = comboPatchVo.getComboGoods();
			for (ComboGoodVo comboGoodVo : goods) {
				ComboGoods comboGoods = comboGoodsDao.findById(comboGoodVo.getComboGoodId());
				if (null == comboGoods) {
					ComboGoods comboGood = new ComboGoods();
					comboGoodsDao.save(comboPatchVo.converPo(comboGoodVo, comboGood, comboID));
				} else {
					comboGoods.setUpdateTime(DateUtil.getCurrentDate());
					comboGoodsDao.save(comboPatchVo.converPo(comboGoodVo, comboGoods, comboID));
				}
			}
		}
	}

	/**
	 * @Title: delCombo @Description: 删除套餐搭配@param @param discountID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void delCombo(String comboID) {
		ComboPatch patch = comboPatchDao.findById(comboID);
		if (StringUtils.isEmpty(patch)) {
			throw new RestException("没有该套餐搭配");
		}
		patch.setUpdateTime(DateUtil.getCurrentDate());
		patch.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		comboPatchDao.save(patch);
	}

	/**
	 * @Title: searchGroups @Description: 搜索团购 @param @param param @param @param
	 *         pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchGroups(Map<String, Object> param, PageInfo pageInfo) {
		if (!StringUtils.isEmpty(param.get("shopID")) && StringUtils.isEmpty(param.get("source"))) {
			boolean hasMarketing = hasMarketing((String) param.get("shopID"), "402890595f2fa901015f3283836e0123");
			if (!hasMarketing) {
				throw new RestException("未购买服务");
			}
		}
		// 返回的结果集
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> groupBuyList = new ArrayList<Map<String, Object>>();
		result = getReturnMap(result, null,pageInfo);
		result.put("groupInfos", groupBuyList);
		if (!StringUtils.isEmpty(param.get("goodsName"))) {
			List<String> ids = goodsDao.findBygoodsName((String) param.get("goodsName"));
			if (ids.size() <= 0) {
				return result;
			}
			param.put("goodsIds", ids);
		}
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchGroups(param)));
		Page<GroupBuy> groupBuys = groupBuyDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), GroupBuy.class), buildPageRequest(pageInfo));
		for (GroupBuy groupBuy : groupBuys) {
			Map<String, Object> comboPatchMap = new HashMap<String, Object>();
			comboPatchMap.put("groupID", groupBuy.getId());
			comboPatchMap.put("shopID", groupBuy.getShopId());
			comboPatchMap.put("groupName", groupBuy.getGroupName());
			comboPatchMap.put("goodsID", groupBuy.getGoodsId());
			Goods goods = goodsDao.findById(groupBuy.getGoodsId());
			comboPatchMap.put("goodsName", goods.getGoodsName());
			comboPatchMap.put("goodsPrice", goods.getGoodsPrice());
			comboPatchMap.put("goodsGroupPrice", groupBuy.getDiscountPrice());
			comboPatchMap.put("groupNum", groupBuy.getGroupNum());
			comboPatchMap.put("groupTime", groupBuy.getGroupTime());
			comboPatchMap.put("maxNum", groupBuy.getMaxNum());
			int groupNumber = detailDao.findGroupNumberByGroupId(groupBuy.getId());
			int successGroupNumber = detailDao.findSuccessGroupByGroupId(groupBuy.getId());
			comboPatchMap.put("groupNumber", groupNumber);
			comboPatchMap.put("successGroupNumber", successGroupNumber);
			comboPatchMap.put("groupStockNum",
					groupBuy.getStockNum() == 0 ? goods.getStockNum() : groupBuy.getStockNum());
			comboPatchMap.put("startTime", groupBuy.getStartTime());
			comboPatchMap.put("endTime", groupBuy.getEndTime());
			if (groupBuy.getStartTime().getTime() > DateUtil.getCurrentDate().getTime()) {
				comboPatchMap.put("groupStatus", "未开始");
			}
			if (groupBuy.getEndTime().getTime() < DateUtil.getCurrentDate().getTime()) {
				comboPatchMap.put("groupStatus", "已过期");
			}
			if (groupBuy.getStartTime().getTime() < DateUtil.getCurrentDate().getTime()
					&& groupBuy.getEndTime().getTime() > DateUtil.getCurrentDate().getTime()) {
				comboPatchMap.put("groupStatus", "进行中");
			}
			groupBuyList.add(comboPatchMap);
		}
		result = getReturnMap(result,groupBuys, pageInfo);
		result.put("groupInfos", groupBuyList);
		return result;
	}

	/**
	 * 是否组团成功 @param @return boolean @throws
	 */
	public boolean groupIsSuccess(String goodsGroupDetailId) {
		List<GroupUser> findByGroupDetailIds = groupUserDao.findByGroupDetailIds(goodsGroupDetailId);
		GroupBuy groupBuy = groupBuyDao.findByGroupDetailId(goodsGroupDetailId);
		if (groupBuy.getGroupNum() <= findByGroupDetailIds.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改订单组团信息标识 @param @return void @throws
	 */
	public void changeOrderGroupStatus(String goodsGroupDetailId, String GroupStatus) {
		List<GroupUser> findByGroupDetailIds = groupUserDao.findByGroupDetailIds(goodsGroupDetailId);
		for (GroupUser groupUser : findByGroupDetailIds) {
			Order order = orderDao.findByOrderId(groupUser.getOrderId());
			order.setGroupStatus(GroupStatus);
			order.setUpdateTime(DateUtil.getCurrentDate());
			orderDao.save(order);
		}
	}

	/**
	 * 团购退款处理 @param @return void @throws
	 */
	public void groupRefund(String goodsGroupDetailId) {
		List<GroupUser> findByGroupDetailIds = groupUserDao.findByGroupDetailIds(goodsGroupDetailId);
		for (GroupUser groupUser : findByGroupDetailIds) {
			try {
				String orderId = groupUser.getOrderId();
				Order order = orderDao.findOne(orderId);
				if ("order_unprocessed".equals(order.getOrderStatusCode())) {
					OrderRefund orderRefund = new OrderRefund();
					orderRefund.setOrderId(orderId);
					orderRefund.setApplyTime(order.getUpdateTime());
					orderRefund.setRefundSum(order.getExpressFee().add(order.getTotalPrice()));
					orderRefund.setBuyerId(order.getBuyerId());
					orderRefund.setBuyerPhone(order.getBuyerPhone());
					orderRefund.setSellerId(order.getShopOwnerId());
					orderRefund.setRefundStatusCode("approve_platform");
					orderRefundDao.save(orderRefund);
					orderCancService.confimrRefund(orderId, "组团失败退款");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: addGroup @Description: 添加团购活动 @param @param
	 *         groupBuyVo @param @param admin 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void addGroup(GroupBuyVo groupBuyVo) {
		Marketing marketing = marketDao.findByMarketingCode("TEAM_ORDER");
		boolean hasMarketing = hasMarketing(groupBuyVo.getShopID(), marketing.getId());
		ShopsMarketing shopsMarketing = Marketing(groupBuyVo.getShopID(), marketing.getId());
		if (hasMarketing == false) {
			throw new RestException("未购买团购服务");
		}
		if (!(groupBuyVo.getGroupNum() * groupBuyVo.getMaxNum() <= groupBuyVo.getGroupStockNum())) {
			throw new RestException("活动库存过少！");
		}
		if (groupBuyVo.getStartTime().getTime() > groupBuyVo.getEndTime().getTime()) {
			throw new RestException("团购活动的开始时间不能大于结束时间");
		}
		if (groupBuyVo.getStartTime().getTime() + groupBuyVo.getGroupTime() * 3600 * 1000 > groupBuyVo.getEndTime()
				.getTime()) {
			throw new RestException("团购活动的时间过短，不满足成团时限");
		}
		if (groupBuyVo.getEndTime().getTime() < DateUtil.getCurrentDate().getTime()) {
			throw new RestException("团购活动的结束时间不能小于当前时间");
		}
		if (groupBuyVo.getEndTime().getTime() > shopsMarketing.getValidityDay().getTime()) {
			throw new RestException("团购活动的结束时间超出服务有效期");
		}
		Goods goods = goodsDao.findOne(groupBuyVo.getGoodsID());
		if(goods.getStockNum()<groupBuyVo.getGroupStockNum()){
			throw new RestException("团购活动库存大于商品库存");
		}
		//新增团购
		if (StringUtils.isEmpty(groupBuyVo.getGroupID())) {
			GroupBuy groupBuy = new GroupBuy();
			GroupBuy buy = groupBuyVo.convertPo(groupBuyVo, groupBuy);
			GroupBuy groupBuy2 = groupBuyDao.findByGoodsId(buy.getGoodsId(), groupBuyVo.getStartTime(),groupBuyVo.getEndTime());
			if (!StringUtils.isEmpty(groupBuy2)) {
				throw new RestException("该商品已存在团购");
			}
			groupBuyDao.save(buy);
			return;
		}
		//编辑团购
		GroupBuy groupBuy = groupBuyDao.findById(groupBuyVo.getGroupID());
		if (StringUtils.isEmpty(groupBuy)) {
			throw new RestException("没有该团购");
		}
		groupBuy.setUpdateTime(DateUtil.getCurrentDate());
		List<FileManage> picByGoodsId = fileManageDao.findPicByGoodsId(groupBuyVo.getGoodsID());
		if (picByGoodsId.size() > 0) {
			groupBuy.setImageUrl(FilePathUtils.fileUrl(picByGoodsId.get(0).getFilePath()));
		} else {
			groupBuy.setImageUrl(null);
		}
		if (DateUtil.compare(DateUtil.getCurrentDate(), groupBuy.getStartTime(), groupBuy.getEndTime())) {
			throw new RestException("进行中的团购禁止修改");
		} else {
			groupBuy.setIsDeleted(SysConstants.DELETE_FLAG_YES);
			groupBuyDao.save(groupBuy);
		}
		GroupBuy convertPo = groupBuyVo.convertPo(groupBuyVo, groupBuy);
		GroupBuy groupBuy2 = groupBuyDao.findByGoodsId(convertPo.getGoodsId(), groupBuyVo.getStartTime(),groupBuyVo.getEndTime());
		if (!StringUtils.isEmpty(groupBuy2)) {
			if(!groupBuy2.getId().equals(groupBuyVo.getGroupID())){
				throw new RestException("该商品已存在团购");
			}
			groupBuyDao.save(convertPo);
		} else {
			groupBuyDao.save(convertPo);
		}

	}

	/**
	 * @Title: delGroup @Description: 删除团购 @param @param comboID 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void delGroup(String groupBuyId) {
		GroupBuy groupBuy = groupBuyDao.findById(groupBuyId);
		if (StringUtils.isEmpty(groupBuy)) {
			throw new RestException("没有该团购");
		}
		if (DateUtil.compare(DateUtil.getCurrentDate(), groupBuy.getStartTime(), groupBuy.getEndTime())) {
			throw new RestException("进行中的团购禁止删除");
		}
		groupBuy.setUpdateTime(DateUtil.getCurrentDate());
		groupBuy.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		groupBuyDao.save(groupBuy);
	}

	/**
	 * @Title: searchGroupDetails @Description: 搜索组团信息 @param @param
	 *         param @param @param pageInfo @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	@SuppressWarnings("unused")
	public Map<String, Object> searchGroupDetails(Map<String, Object> param, PageInfo pageInfo) {
		// 返回的结果集
		Map<String, Object> result = new HashMap<String, Object>();
		Shop shop = shopDao.findById((String) param.get("shopID"));
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchGroupDetails(param)));
		Page<GroupDetail> groupDetails = detailDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), GroupDetail.class), buildPageRequest(pageInfo));
		List<Map<String, Object>> groupBuyList = new ArrayList<Map<String, Object>>();
		for (GroupDetail groupDetail : groupDetails) {
			Map<String, Object> groupDetailMap = new HashMap<String, Object>();
			groupDetailMap.put("groupDetailId", groupDetail.getId());
			groupDetailMap.put("groupID", groupDetail.getGroupId());
			groupDetailMap.put("ownerID", groupDetail.getOwnerId());
			User user = userDao.findById(groupDetail.getOwnerId());
			groupDetailMap.put("ownerName", user.getAliasName());
			GroupBuy groupBuy = groupBuyDao.findById(groupDetail.getGroupId());
			Integer userNum = groupUserDao.fingAllUser(groupDetail.getId());
			groupDetailMap.put("groupUserNum", userNum == null ? 0 : userNum);
			groupDetailMap.put("groupNum", groupBuy.getGroupNum());
			groupDetailMap.put("groupIsSuccess", groupDetail.getIsGroupSuccess());
			groupDetailMap.put("groupStartTime", groupDetail.getCreateTime());
			groupDetailMap.put("groupTime", groupBuy.getGroupTime());
			groupDetailMap.put("groupEndTime",
					groupDetail.getCreateTime().getTime() + groupBuy.getGroupTime() * 3600000);

			List<GroupUser> byGroupDetailIds = groupUserDao.findByGroupDetailIds(groupDetail.getId());

			int groupGoodsNum = 0;
			List<Map<String, Object>> groupUsers = new ArrayList<Map<String, Object>>();
			for (GroupUser groupUser : byGroupDetailIds) {
				Map<String, Object> userResult = new HashMap<String, Object>();
				User groupDetailUser = userDao.findOne(groupUser.getUserId());
				userResult.put("userName", groupDetailUser != null ? groupDetailUser.getAliasName() : null);
				String url = fileManageDao.findUrlById(groupDetailUser.getImgFileId());
				userResult.put("userImg", FilePathUtils.fileUrl(url));
				String orderId = groupUser.getOrderId();
				Order order = orderDao.findOne(orderId);
				List<OrderGoods> findByOrderId = orderGoodsDao.findByOrderId(orderId);
				for (OrderGoods orderGoods : findByOrderId) {
					groupGoodsNum = groupGoodsNum + orderGoods.getGoodsNum();
				}
				String address = order.getReceiverProvince() + order.getReceiverCity() + order.getReceiverArea()
						+ order.getReceiverStreet() + order.getReceiverAddress();
				userResult.put("userAddress", address);
				groupUsers.add(userResult);
			}
			groupDetailMap.put("crowdorderedGoodsNum", groupGoodsNum);
			groupDetailMap.put("groupOrders", groupUsers);
			groupBuyList.add(groupDetailMap);
		}
		result = getReturnMap(result,groupDetails, pageInfo);
		result.put("groupInfos", groupBuyList);
		return result;
	}

	/**
	 * 组团 @param @return void @throws
	 */
	@Transactional
	public void createGoodsGroup(String groupBuyId, String userId, String orderId) {
		GroupDetail groupDetail = new GroupDetail();
		groupDetail.setGroupId(groupBuyId);
		groupDetail.setOwnerId(userId);
		groupDetail.setIsGroupSuccess(0);
		groupDetail.setCreateTime(DateUtil.getCurrentDate());
		groupDetail.setUpdateTime(DateUtil.getCurrentDate());
		GroupDetail detail = detailDao.save(groupDetail);
		GroupUser groupUser = new GroupUser();
		groupUser.setGroupDetailId(detail.getId());
		groupUser.setOrderId(orderId);
		groupUser.setUserId(userId);
		groupUser.setCreateTime(DateUtil.getCurrentDate());
		groupUser.setUpdateTime(DateUtil.getCurrentDate());
		groupUser.setIsDeleted(0);
		groupUserDao.save(groupUser);
		GroupBuy groupBuy = groupBuyDao.findById(groupBuyId);
		try {
			// 添加任务调度
			groupJobService.saveAuctionJob(detail.getId(), DateUtil.convertToString(
					new Date((long) (detail.getCreateTime().getTime() + groupBuy.getGroupTime() * 3600000))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 参团 @param @return void @throws
	 */
	public void addGoodsGroup(String groupDetailId, String userId, String orderId) {
		synchronized (groupDetailId) {
			// 查询是否参与了团购
			GroupUser user = groupUserDao.findByUserIdAndGroupDetailId(userId, groupDetailId);
			if (!StringUtils.isEmpty(user)) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.GROUP_ERROR.getDesc());
			}
			// 团购成团人数和已参加团购人数查询
			List<GroupUser> detailIds = groupUserDao.findByGroupDetailIds(groupDetailId);
			GroupBuy groupBuy = groupBuyDao.findByGroupDetailId(groupDetailId);
			if (detailIds.size() >= groupBuy.getGroupNum()) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.GROUP_USER_ERROR.getDesc());
			}
			// 添加参团人员
			GroupUser groupUser = new GroupUser();
			groupUser.setGroupDetailId(groupDetailId);
			groupUser.setOrderId(orderId);
			groupUser.setUserId(userId);
			groupUser.setCreateTime(DateUtil.getCurrentDate());
			groupUser.setUpdateTime(DateUtil.getCurrentDate());
			groupUser.setIsDeleted(0);
			groupUserDao.save(groupUser);
			// 添加人员后成团，修改团信息
			if (detailIds.size() + 1 == groupBuy.getGroupNum()) {
				GroupDetail groupDetail = detailDao.findOne(groupDetailId);
				groupDetail.setIsGroupSuccess(1);
				detailDao.save(groupDetail);
				changeOrderGroupStatus(groupDetailId, "已成团");
			}
		}
	}

	/**
	 * 限购数量是否超出 @param @return boolean @throws
	 */
	public boolean groupIsFul(String groupBuyId, String userId, Integer croupGoodsNum) {
		boolean bool = false;
		GroupBuy groupBuy = groupBuyDao.findOne(groupBuyId);
		List<Integer> findNum = detailDao.findNum(groupBuyId, userId);
		int num = 0;
		for (Integer orderGoods : findNum) {
			num = num + orderGoods;
		}
		if (groupBuy.getMaxNum() < (num + croupGoodsNum)) {
			bool = true;
		}
		return bool;
	}

	public GroupBuyVo groupDetil(Map<String, Object> param) {
		GroupBuyVo groupBuyVo = new GroupBuyVo();
		GroupBuy groupBuy = groupBuyDao.findOne((String) param.get("groupId"));
		if (StringUtils.isEmpty(groupBuy)) {
			throw new RestException("没有该团购信息");
		}
		if (!StringUtils.isEmpty(groupBuy)) {
			groupBuyVo.setGroupID(groupBuy.getId());
			groupBuyVo.setShopID(groupBuy.getShopId());
			groupBuyVo.setGroupName(groupBuy.getGroupName());
			groupBuyVo.setGoodsID(groupBuy.getGoodsId());
			groupBuyVo.setGoodsGroupPrice(groupBuy.getDiscountPrice());
			groupBuyVo.setGroupNum(groupBuy.getGroupNum());
			groupBuyVo.setGroupTime(groupBuy.getGroupTime());
			groupBuyVo.setMaxNum(groupBuy.getMaxNum());
			groupBuyVo.setGroupStockNum(groupBuy.getStockNum());
			groupBuyVo.setStartTime(groupBuy.getStartTime());
			groupBuyVo.setEndTime(groupBuy.getEndTime());
			Goods goods = goodsDao.findById(groupBuy.getGoodsId());
			if (!StringUtils.isEmpty(goods)) {
				groupBuyVo.setGoodsName(goods.getGoodsName());
				groupBuyVo.setGoodsPrice(goods.getGoodsPrice());
				groupBuyVo.setGoodsStockNum(goods.getStockNum());
			}
		}
		return groupBuyVo;
	}
}
