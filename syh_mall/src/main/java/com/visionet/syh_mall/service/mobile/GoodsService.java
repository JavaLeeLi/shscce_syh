package com.visionet.syh_mall.service.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.visionet.syh_mall.entity.shop.FulfilRemit;
import com.visionet.syh_mall.repository.marketing.ComboGoodsRepository;
import com.visionet.syh_mall.repository.mobile.*;
import com.visionet.syh_mall.vo.shop.ComboGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.constant.StatusType;
import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.evaluation.RecognizeTag;
import com.visionet.syh_mall.entity.goods.BidRecord;
import com.visionet.syh_mall.entity.goods.ExpressTemplet;
import com.visionet.syh_mall.entity.goods.ExpressWay;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsDraft;
import com.visionet.syh_mall.entity.goods.GoodsKind;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.entity.goods.HomeGoods;
import com.visionet.syh_mall.entity.goods.PurchaseAddressLink;
import com.visionet.syh_mall.entity.goods.SearchHistory;
import com.visionet.syh_mall.entity.goods.SupplyRecord;
import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.entity.marketing.GroupBuy;
import com.visionet.syh_mall.entity.mbr.UserAddress;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderServiceEntity;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.entity.userAttention.GoodsFavorite;
import com.visionet.syh_mall.entity.userAttention.UserAttention;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.OperateLogRepository;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.evaluation.RecognizeTagRepository;
import com.visionet.syh_mall.repository.marketing.DiscountTimeRepository;
import com.visionet.syh_mall.repository.marketing.GroupBuyRepository;
import com.visionet.syh_mall.repository.mbr.UserAddressRepository;
import com.visionet.syh_mall.repository.order.OrderServiceRepository;
import com.visionet.syh_mall.repository.userAttention.GoodsFavoriteRepository;
import com.visionet.syh_mall.repository.userAttention.UserAttentionRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.service.goods.AuctionJobService;
import com.visionet.syh_mall.service.goods.PurchaseJobService;
import com.visionet.syh_mall.service.marketing.DiscountTimeService;
import com.visionet.syh_mall.vo.BidRecordVo;
import com.visionet.syh_mall.vo.ExpressTempletVo;
import com.visionet.syh_mall.vo.ExpressWayVo;
import com.visionet.syh_mall.vo.GoodsInfo;
import com.visionet.syh_mall.vo.GoodsKindVo;
import com.visionet.syh_mall.vo.GoodsPicLinkVo;
import com.visionet.syh_mall.vo.GoodsQo;
import com.visionet.syh_mall.vo.GoodsVo.GoodsInfos;
import com.visionet.syh_mall.vo.PurchaseAddrInfoVo;
import com.visionet.syh_mall.vo.RecognizeVo;
import com.visionet.syh_mall.vo.goods.GoodsDraftImgVo;
import com.visionet.syh_mall.vo.goods.GoodsDraftVo;
import com.visionet.syh_mall.vo.goods.HomeGoodsVo;
import com.visionet.syh_mall.vo.goods.PublishGoodsVo;

/**
 * @Author DM
 * @version ：2017年8月17日下午5:38:22 实体类
 */
@Service
public class GoodsService extends BaseService {
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private GoodsKindRepository goodsKindDao;
	@Autowired
	private HomeGoodsRepository homeGoodsDao;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private GoodsPicLinkRepository goodsPicLinkDao;
	@Autowired
	private GoodsDaolmpl goodsDaolmpl;
	@Autowired
	private FileManageRepostory fManageRepostory;
	@Autowired
	private KeyMappingRepository keyDao;
	@Autowired
	private PurchaseAddressLinkRepository linkDao;
	@Autowired
	private ExpressTempletRepository expressTempletDao;
	@Autowired
	private GoodsDraftRepository goodsDraftDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired
	private UserAttentionRepository attentionDao;
	@Autowired
	private GoodsFavoriteRepository favoriteDao;
	@Autowired
	private SearchHistoryRepository searchHistoryDao;
	@Autowired
	private PurchaseAddressLinkRepository purchaseAddressLinkDao;
	@Autowired
	private ExpressWayRepository expressWayDao;
	@Autowired
	private GoodsAuctionRepository recordDao;
	@Autowired
	private UserRepository userRep;
	@Autowired
	private SupplyRecordRepository supplyDao;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private UserAddressRepository addrDao;
	@Autowired
	private OrderGoodsRepository orderGood;
	@Autowired
	private OrderServiceRepository orderServiceDao;
	@Autowired
	private AuctionJobService auctionJobService;
	@Autowired
	private RecognizeTagRepository recognizeTagDao;
	@Autowired
	private UserAuthenticationRepository authenticationDao;
	@Autowired
	private DiscountTimeRepository discountTimeDao;
	@Autowired
	private GroupBuyRepository groupBuyDao;
	@Autowired
	private DiscountTimeService discountTimeService;
	@Autowired
	private OperateLogRepository operateLogDao;
	@Autowired
	private PurchaseJobService purchaseJobService;
    @Autowired
	private ComboGoodsRepository comboGoodsRepository;
    @Autowired
	private FulifilRemitRepository fulifilRemitRepository;
	/**
	 * 首页查询热推商品信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<GoodsInfos> queryList(GoodsQo qo) throws Exception {
		List<Object[]> obj = homeGoodsDao.findGoodsList();
		// List<GoodsVo> returnVo = new ArrayList<GoodsVo>();
		List<GoodsInfos> returnVo = new ArrayList<GoodsInfos>();
		for (Object[] object : obj) {
			// GoodsVo vo = new GoodsVo();
			GoodsInfos vo = new GoodsInfos();
			vo.setHomeGoodsId(object[0].toString());
			vo.setHomeGoodsSort(Integer.parseInt(object[1].toString()));
			vo.setShopId(object[2].toString());
			vo.setGoodsId(object[3].toString());
			vo.setShopName(object[4].toString());
			vo.setOwnerId(object[5].toString());
			vo.setGoodsTypeCode((object[6].toString()));
			vo.setGoodsName(object[7].toString());
			vo.setGoodsDesc(String.valueOf(object[8]));
			vo.setGoodsSn(String.valueOf(object[9]));
			if (Validator.isNotNull(object[10])) {
				vo.setGoodsPrice(new BigDecimal(object[10].toString()));
				vo.setRealPrice(new BigDecimal(object[10].toString()));
			}
			// vo.setIsRecognized(Integer.parseInt(object[11].toString()));
			List<FileManage> list = fManageRepostory.findPicByGoodsId(object[3].toString()); // 商品图片集合
			vo.setGoodsImgUrls(list);
			if (Validator.isNotNull(object[12])) {
				vo.setValidStartTime(DateUtil.convertFromString((object[12].toString()), DateUtil.YMD_FULL));
			}
			if (Validator.isNotNull(object[13])) {
				vo.setValidEndTime(DateUtil.convertFromString((object[13].toString()), DateUtil.YMD_FULL));
				vo.setGoodsCloseTime(DateUtil.convertFromString((object[13].toString()), DateUtil.YMD_FULL));
			}
			vo.setOwnerImgUrl(FilePathUtils.fileUrl(String.valueOf(object[14])));
			vo.setOwnerName(object[15].toString());
			vo.setOwnerLevel(Integer.parseInt(object[16].toString()));
			vo.setOwnerTypeCode(object[17].toString());
			KeyMapping key = keyDao.findByKeyCode(object[17].toString());
			if (null != key && null != key.getId()) {
				vo.setOwnerTypeDesc(key.getValueDesc());
			}
			if (Validator.isNotNull(object[18])) {
				vo.setGoodsBidStart(new BigDecimal(object[18].toString()));
			}
			if (Validator.isNotNull(object[19])) {
				vo.setGoodsBidMin(new BigDecimal(object[19].toString()));
			}
			returnVo.add(vo);
		}
		return returnVo;
	}

	/**
	 * @author DM
	 * @param:
	 * @return:
	 * @Description:商品检索
	 * @date
	 */
	public Page<GoodsInfos> searchGoods(GoodsQo qo) throws Exception {
		PageRequest pr = getPageRequest(qo.getPageIndex(), qo.getItemCount(), qo.getOrderConditions());
		return goodsDaolmpl.queryCondition(qo, pr);
	}

	/**
	 * 求购供货商品检索
	 * @param 
	 * @return List<Goods>
	 * @throws
	 */
	public List<GoodsInfos> searchPurchaseGoods(Map<String,String> param){
		String ownerid = param.get("ownerId");
		String chilCategoryId = param.get("childCategoryId");
		String keywords = param.get("keywords");
		String goodsTypeCode = param.get("goodsTypeCode");
		HashMap<String,Object> searchPurchaseMap = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(ownerid)){			
			searchPurchaseMap.put("EQ_ownerId", ownerid);
		}
		if(!StringUtils.isEmpty(chilCategoryId)){			
			searchPurchaseMap.put("EQ_goodsKindId", chilCategoryId);
		}
		if(!StringUtils.isEmpty(keywords)){			
			searchPurchaseMap.put("LIKE_goodsName", keywords);
		}
		if(!StringUtils.isEmpty(goodsTypeCode)){			
			searchPurchaseMap.put("EQ_goodsTypeCode", goodsTypeCode);
		}
		searchPurchaseMap.put("GT_stockNum", 0);
		Map<String, SearchFilter> parse = SearchFilter.parse(searchPurchaseMap);
		List<Goods> allGoods = goodsDao.findAll(DynamicSpecifications.bySearchFilter(parse.values(), Goods.class));
		List<GoodsInfos> purchaseGoodsVo = new ArrayList<GoodsInfos>();
		//商品信息
		for (Goods goods : allGoods) {
			GoodsInfos goodsInfos = new GoodsInfos();
			goodsInfos.setGoodsName(goods.getGoodsName());
			goodsInfos.setGoodsPrice(goods.getGoodsPrice());
			goodsInfos.setGoodsStockNum(goods.getStockNum());
			goodsInfos.setGoodsId(goods.getId());
			//商品图片集合
			List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(goods.getId());
			List<FileManage> goodsImgs = new ArrayList<FileManage>();
			for (GoodsPicLink goodsPicLink : goodsPicList) {
				FileManage max = fileManageDao.findOne(goodsPicLink.getMaxImgId());
				FileManage mid = fileManageDao.findOne(goodsPicLink.getMidImgId());
				FileManage min = fileManageDao.findOne(goodsPicLink.getMinImgId());
				goodsImgs.add(min);
				goodsImgs.add(mid);
				goodsImgs.add(max);
			}
			goodsInfos.setGoodsImgs(goodsImgs);
			purchaseGoodsVo.add(goodsInfos);
		}
		return purchaseGoodsVo;
	}
	
	
	
	/**
	 * @author DM
	 * @param:
	 * @return:
	 * @Description:搜索下架商品
	 * @date
	 */
	public Page<GoodsInfos> searchSoldOutGoods(GoodsQo qo) throws Exception {
		PageRequest pr = getPageRequest(qo.getPageIndex(), qo.getItemCount(), qo.getOrderConditions());
		return goodsDaolmpl.searchSoldOutGoods(qo, pr);
	}

	/**
	 * @Title: searchRecommendGoods @Description: 搜索推荐商品 @param @param
	 *         qo @param @return @param @throws Exception 设定文件 @return Page
	 *         <GoodsInfos> 返回类型 @throws
	 */
	public Page<GoodsInfos> searchRecommendGoods(GoodsQo qo) throws Exception {
		PageRequest pr = getPageRequest(qo.getPageIndex(), qo.getItemCount(), qo.getOrderConditions());
		return goodsDaolmpl.queryRecommendGoods(qo, pr);
	}

	/**
	 * @author DM
	 * @param:
	 * @return:
	 * @Description:查询已完成的订单商品
	 * @date
	 */
	public Page<GoodsInfos> searchOrderGoods(GoodsQo qo) throws Exception {
		PageRequest pr = getPageRequest(qo.getPageIndex(), qo.getItemCount(), qo.getOrderConditions());
		return goodsDaolmpl.queryConditionCotent(qo, pr);
	}

	public void addSearchHistory(GoodsQo qo) {
		// 新增历史记录
		if (Validator.isNotNull(qo.getKeywords())) {
			SearchHistory searchHistory = searchHistoryDao.findById(qo.getCurrentUserId(), qo.getKeywords());
			if (null == searchHistory) {
				SearchHistory s = new SearchHistory();
				s.setUserId(qo.getCurrentUserId());
				s.setKeywords(qo.getKeywords());
				s.setSourchCount(1);
				s.setIsDeleted(0);
				s = searchHistoryDao.save(s);
			} else {
				searchHistory.setSourchCount(searchHistory.getSourchCount() + 1);
				searchHistoryDao.save(searchHistory);
			}
		}
	}

	/**
	 * @Title: addSearchGoodHistory @Description: 查看商品详情的历史记录 @param @param qo
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void addSearchGoodHistory(GoodsQo qo) {
		// 新增历史记录
		if (Validator.isNotNull(qo.getId())) {
			Goods goods = goodsDao.findById(qo.getId());
			if ("sale_type".equals(goods.getGoodsTypeCode())) {
				SearchHistory searchHistory = searchHistoryDao.findByGoodsId(qo.getCurrentUserId(), qo.getId());
				if (null == searchHistory) {
					SearchHistory s = new SearchHistory();
					s.setUserId(qo.getCurrentUserId());
					s.setKeywords(qo.getKeywords());
					s.setGoodsId(qo.getId());
					s.setSourchCount(1);
					s.setIsDeleted(0);
					s = searchHistoryDao.save(s);
				} else {
					searchHistory.setSourchCount(searchHistory.getSourchCount() + 1);
					searchHistoryDao.save(searchHistory);
				}
			}
		}
	}

	/**
	 * 获取用户在平台中的搜索历史关键字
	 * 
	 * @param UserAddress
	 */
	public List<SearchHistory> getHistoryKeywords(GoodsQo qo) throws Exception {
		List<SearchHistory> list = searchHistoryDao.findAll(qo.getCurrentUserId());
		if (list != null && list.size() > 0) {
			Iterator<SearchHistory> iterator = list.iterator();
			while (iterator.hasNext()) {
				SearchHistory searchHistory = iterator.next();
				if (null == searchHistory.getKeywords()) {
					iterator.remove();
				}
			}
		}

		return list;
	}

	/**
	 * @author DM
	 * @Description:删除用户在平台中的搜索历史
	 */
	public void delHistoryKeywords(GoodsQo qo) {
		for (SearchHistory searchHistory : qo.getHistoryInfos()) {
			SearchHistory s = searchHistoryDao.findOne(searchHistory.getId());
			s.setIsDeleted(1);
			s.setUpdateTime(DateUtil.getCurrentDate());
			searchHistoryDao.save(s);
		}
	}

	/**
	 * 首页查询商品分类信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<GoodsKindVo> queryKindList() throws Exception {
		Sort sort = new Sort(Direction.ASC, "kindSort");
		List<GoodsKind> parent = goodsKindDao.findByParentKindIdIsNullAndIsDeleted(SysConstants.DELETE_FLAG_NO, sort);
		List<GoodsKindVo> vo = convert(parent);
		// 查询子类
		for (GoodsKindVo goodsKindVo : vo) {
			List<GoodsKind> subList = goodsKindDao.findByParentKindIdAndIsDeleted(goodsKindVo.getCategoryID(),
					SysConstants.DELETE_FLAG_NO, sort);
			goodsKindVo.setSubKinds(convert(subList));
		}
		return vo;
	}

	/**
	 * GoodsKind to GoodsKindVo
	 * 
	 * @param list
	 * @return
	 */
	private List<GoodsKindVo> convert(List<GoodsKind> list) {
		List<GoodsKindVo> result = new ArrayList<GoodsKindVo>();
		GoodsKindVo vo = null;
		FileManage file = null;
		for (GoodsKind goodsKind : list) {
			if (!StringUtils.isEmpty(goodsKind.getKindIconFileId())) {
				file = fileManageDao.findOne(goodsKind.getKindIconFileId());
			}
			vo = new GoodsKindVo();
			vo.setCategoryID(goodsKind.getId());
			vo.setCategoryImgID(goodsKind.getKindIconFileId());
			vo.setCategoryName(goodsKind.getKindName());
			vo.setCategoryImgUrl(null == file ? null : FilePathUtils.fileUrl(file.getFilePath()));
			vo.setCategorySort(goodsKind.getKindSort());
			vo.setParentCategoryID(goodsKind.getParentKindId());
			vo.setReviewIsAvoid(goodsKind.getReviewIsAvoid() == 0 ? false : true);
			result.add(vo);
		}
		return result;
	}

	/**
	 * @author DM
	 * @param:
	 * @return:
	 * @Description:查询商品详情
	 * @date
	 */
	public GoodsInfos queryGoodsDetails(GoodsQo qo) throws Exception {

		// GoodsVo returnVo = new GoodsVo();
		GoodsInfos returnVo = new GoodsInfos();
		Goods goods = goodsDao.findById(qo.getId());
		Shop shop = shopDao.findOne(goods.getShopId());
		returnVo.setShopId(shop.getId());
		returnVo.setShopName(shop.getShopName());
		User user = userDao.findOne(goods.getOwnerId());
		returnVo.setOwnerId(user.getId());
		if (Validator.isNotNull(user.getImgFileId())) {
			FileManage pic = fManageRepostory.findOne(user.getImgFileId());
			if (null != pic && null != pic.getId()) {
				returnVo.setOwnerImgUrl(pic.getAbsolutePath());
			}
		}
		returnVo.setOwnerName(user.getAliasName());
		returnVo.setOwnerLevel(user.getChannelLevel());
		returnVo.setOwnerTypeCode(user.getUserTypeCode());
		KeyMapping key = keyDao.findByKeyCode(user.getUserTypeCode());
		if (null != key && null != key.getId()) {
			returnVo.setOwnerTypeDesc(key.getValueDesc());
		}
		returnVo.setGoodsId(goods.getId());
		returnVo.setGoodsTypeCode(goods.getGoodsTypeCode());
		KeyMapping keyMap = keyDao.findByKeyCode(goods.getGoodsTypeCode());
		if (null != keyMap && null != keyMap.getId()) {
			returnVo.setGoodsTypeDesc(keyMap.getValueDesc());
		}
		UserAttention attention = attentionDao.findUserAttention(qo.getCurrentUserId(), user.getId());
		if (null != attention && null != attention.getId()) {
			returnVo.setOwnerIsAttented(1);
		} else {
			returnVo.setOwnerIsAttented(0);
		}
		returnVo.setGoodsName(goods.getGoodsName());
		returnVo.setGoodsDesc(goods.getGoodsDescription());
		returnVo.setGoodsSn(goods.getGoodsSn());
		returnVo.setBuyGoodsType(goods.getBuyGoodsType());
		returnVo.setChildCategoryId(goods.getGoodsKindId());
		GoodsKind kind = goodsKindDao.findOne(goods.getGoodsKindId());
		if (!StringUtils.isEmpty(kind.getParentKindId())) {
			GoodsKind parentGoodsKind = goodsKindDao.findOne(kind.getParentKindId());
			returnVo.setCategoryId(parentGoodsKind.getId());
			returnVo.setCategoryName(parentGoodsKind.getKindName());
		} else {
			returnVo.setCategoryId(null);
			returnVo.setCategoryName(null);
		}
		if (null != kind && null != kind.getId()) {
			returnVo.setChildCategoryName(kind.getKindName());
		}
		List<FileManage> list = fManageRepostory.findPicByGoodsId(qo.getId()); // 商品图片集合
		returnVo.setGoodsImgs(list);
		returnVo.setGoodsPrice(goods.getGoodsPrice());
		returnVo.setGoodsQualityCode(goods.getGoodsQualityCode());
		KeyMapping keyMap1 = keyDao.findByKeyCode(goods.getGoodsQualityCode());
		if (null != keyMap1 && null != keyMap1.getId()) {
			returnVo.setGoodsQualityDesc(keyMap1.getValueDesc());
		}
		if (Validator.isNotNull(goods.getGoodsQualityScore())) {
			returnVo.setGoodsQualityScore(goods.getGoodsQualityScore());
		}
		if (Validator.isNotNull(goods.getIsRecognized())) {
			returnVo.setGoodsIsRecognized(goods.getIsRecognized());
		}
		if (Validator.isNotNull(goods.getRecognizedIsPrint())) {
			returnVo.setRecognizedIsPrint(goods.getRecognizedIsPrint());
		}
		GoodsFavorite favorite = favoriteDao.findByUserIdAndGoodId(qo.getCurrentUserId(), goods.getId());
		if (null != favorite && null != favorite.getId()) {
			returnVo.setGoodsIsFavorite(1);
		} else {
			returnVo.setGoodsIsFavorite(0);
		}
		DiscountTime discountTime = discountTimeService.takeDiscountTime(null, goods.getId(), null);// 限时活动
		if (!StringUtils.isEmpty(discountTime)) {
			returnVo.setRealPrice(discountTime.getDiscountPrice());
			returnVo.setDiscountTime(discountTime);
			returnVo.setGoodsStockNum(discountTime.getStockNum());
		} else {
			returnVo.setRealPrice(goods.getGoodsPrice());
		}
		returnVo.setGoodsBidStart(goods.getGoodsBidStart());
		returnVo.setExpressFee(goods.getExpressFee());
		returnVo.setValidStartTime(goods.getValidStartTime());
		returnVo.setValidEndTime(goods.getValidEndTime());
		returnVo.setGoodsCloseTime(goods.getValidEndTime());
		returnVo.setGoodsCreateTime(goods.getCreateTime());
		returnVo.setGoodsStockNum(goods.getStockNum());
		returnVo.setGoodsRecognizeCode(goods.getRecognizedCode());
		if (Validator.isNotNull(goods.getGoodsMinBid())) {
			returnVo.setGoodsBidMin(goods.getGoodsMinBid());
		}
		if (Validator.isNotNull(goods.getSupplyMinSum())) {
			returnVo.setGoodsMinSupplyNum(goods.getSupplyMinSum());
		}
		if (Validator.isNotNull(goods.getExpectedNum())) {
			returnVo.setExpectedNum(goods.getExpectedNum());
		}
		if ("purchase_type".equals(goods.getGoodsTypeCode())) {
			List<SupplyRecord> supplyGoods = supplyDao.findBygoodsIdAndOrderStatus(goods.getId());
			Integer supplyNum = 0;
			for (SupplyRecord supplyRecord : supplyGoods) {
				supplyNum = supplyNum + supplyRecord.getSupplyNum();
			}
			Integer residueSupplyNum = goods.getExpectedNum() - supplyNum;
			returnVo.setResidueSupplyNum(residueSupplyNum);
			if (returnVo.getResidueSupplyNum() < goods.getSupplyMinSum()) {
				returnVo.setGoodsMinSupplyNum(residueSupplyNum);
			}
		}
		returnVo.setBanReason(goods.getBanReason());
		if (Validator.isNotNull(goods.getExpressTempletId())) {
			returnVo.setExpressTempletID(goods.getExpressTempletId());
			ExpressTemplet exp = expressTempletDao.findOne(goods.getExpressTempletId());
			if (null != exp && null != exp.getId()) {
				returnVo.setExpressTemplet(exp);
			}
		}
		returnVo.setStatusCode(goods.getStatusCode());
		KeyMapping keyMap2 = keyDao.findByKeyCode(goods.getStatusCode());
		if (null != keyMap2 && null != keyMap2.getId()) {
			returnVo.setStatusDesc(keyMap2.getValueDesc());
		}
		if (Validator.isNotNull(goods.getTotalSales())) {
			returnVo.setGoodsSalesNum(goods.getTotalSales());
		}
		PurchaseAddressLink link = linkDao.findOne(qo.getId());
		if (null != link && null != link.getUserAddressId()) {
			UserAddress addr = addrDao.findOne(link.getUserAddressId());
			if (null != addr && null != addr.getId()) {
				PurchaseAddrInfoVo purchase = new PurchaseAddrInfoVo();
				purchase.setAddrId(addr.getId());
				purchase.setReceiverName(addr.getReceiverName());
				purchase.setReceiverPhone(addr.getPhone());
				purchase.setAddrProvince(addr.getProvince());
				purchase.setAddrCity(addr.getCity());
				purchase.setAddrArea(addr.getArea());
				purchase.setAddrStreet(addr.getStreet());
				purchase.setAddrDetail(addr.getAddress());
				returnVo.setPurchaseAddrInfo(purchase);
			}
		}
		return returnVo;
	}

	/**
	 * @author DM
	 * @Description:删除商品
	 */
	@Transactional
	public void delGoods(GoodsQo qo) {
		Goods goods = goodsDao.findById(qo.getGoodsId());
		if ("auction_type".equals(goods.getGoodsTypeCode())) {
			boolean compare = DateUtil.compare(DateUtil.getCurrentDate(), goods.getValidStartTime(),
					goods.getValidEndTime());
			if (compare) {
				throw new RestException("拍卖中的商品禁止删除");
			}
		}
        List<ComboGoods> comboGoods = comboGoodsRepository.findByGoodsId(goods.getId());
		if (comboGoods.size()>0){
                throw new RestException("该商品参与了套餐搭配，请先删除对应的套餐搭配");
        }
        List<FulfilRemit> fulfilRemits = fulifilRemitRepository.findByGiftGoodsId(goods.getId());
		if (fulfilRemits.size()>0){
		    throw new RestException("该商品参与了满减满送，请先删除对应的满减满送");
        }
        goods.setIsDeleted(1);
		goods.setUpdateTime(DateUtil.getCurrentDate());
		goodsDao.save(goods);
		List<DiscountTime> discountTimes = discountTimeDao.findByGoodsId(goods.getId());
		for (DiscountTime discountTime : discountTimes) {
			discountTime.setIsDeleted(SysConstants.DELETE_FLAG_YES);
			discountTimeDao.save(discountTime);
		}
		GroupBuy groupBuy = groupBuyDao.findGroupBuyInfos(goods.getId());
		if (!StringUtils.isEmpty(groupBuy)) {
			groupBuy.setIsDeleted(SysConstants.DELETE_FLAG_YES);
			groupBuyDao.save(groupBuy);
		}
	}

	/**
	 * @Title: delHomeGoods @Description: 删除热销商品 @param @param
	 *         homeGoodsID @param @return 设定文件 @return int 返回类型 @throws
	 */
	@Transactional
	public void delHomeGoods(String homeGoodsID, String adminID) {
		HomeGoods one = homeGoodsDao.findOne(homeGoodsID);
		if (StringUtils.isEmpty(one)) {
			throw new RestException(HttpStatus.ACCEPTED);
		}
		one.setUpdateBy(adminID);
		one.setUpdateTime(DateUtil.getCurrentDate());
		one.setIsDeleted(SysConstants.DELETE_FLAG_YES);
		homeGoodsDao.save(one);
		operateLogDao.save(addLog(null, adminID, "删除热销商品，热销商品id为：" + homeGoodsID));
	}

	/**
	 * @Title: addHomeGoods @Description: 增加热推商品的方法 @param @param
	 *         homeGoods @param @param Adminid 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void addHomeGoods(HomeGoodsVo goodsVo, String Adminid) {
		if (StringUtils.isEmpty(goodsVo.getHomeGoodsID())) {
			Goods good = goodsDao.findById(goodsVo.getGoodsID());
			if (StringUtils.isEmpty(good)) {
				throw new RestException("没有该商品");
			}
			HomeGoods goods = homeGoodsDao.findByGoodsId(goodsVo.getGoodsID());
			if (!StringUtils.isEmpty(goods)) {
				throw new RestException("该商品已经热推过了,不能除服热推");
			}
			HomeGoods homeGoods = new HomeGoods();
			homeGoods.setCreateBy(Adminid);
			homeGoods.setUpdateBy(Adminid);
			HomeGoods homeGood = homeGoodsDao.save(goodsVo.converPo(homeGoods, goodsVo, good));
			operateLogDao.save(addLog(null, Adminid, "添加热推商品处理，商品id为：" + homeGood.getId()));
			return;
		}
		HomeGoods goods = homeGoodsDao.findOne(goodsVo.getHomeGoodsID());
		if (StringUtils.isEmpty(goods)) {
			throw new RestException("没有该热销商品");
		}
		goods.setUpdateTime(DateUtil.getCurrentDate());
		goods.setUpdateBy(Adminid);
		Goods good = goodsDao.findById(goods.getGoodsId());
		if (StringUtils.isEmpty(good)) {
			throw new RestException("没有该商品");
		}
		homeGoodsDao.save(goodsVo.converPo(goods, goodsVo, good));
		operateLogDao.save(addLog(null, Adminid, "编辑热推商品处理，商品id为：" + goods.getId()));
	}

	/**
	 * @Title: reviseGoods @Description: 添加/编辑鉴评@param @param good @param @param
	 *         roleId @param @param list 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void reviseGoods(RecognizeVo recognizeVo, String roleId) {
		if (recognizeVo.getIsBatchCreate()) {
			for (int i = 0; i < recognizeVo.getGoodsNum(); i++) {
				GoodsDraft goodsDraft = null;
				List<GoodsPicLink> picLinks = null;
				String phone = recognizeVo.getGoodsOwnerPhone();
				String userId = userDao.findByPhone(phone);
				if (StringUtils.isEmpty(recognizeVo.getGoodsID())) {
					goodsDraft = new GoodsDraft();
					picLinks = new ArrayList<GoodsPicLink>();
					Shop shop = shopDao.findByUserId(userId);
					goodsDraft.setShopId(shop.getId());
					goodsDraft.setOwnerId(userId);
					goodsDraft.setCreateBy(roleId);
					goodsDraft.setUpdateBy(roleId);
				} else {
					goodsDraft = goodsDraftDao.findOne(recognizeVo.getGoodsID());
					goodsDraftDao.delete(goodsDraft);
					goodsDraft.setId(null);
					picLinks = goodsPicLinkDao.getGoodsPicList(goodsDraft.getId());
					goodsDraft.setUpdateBy(roleId);
					goodsDraft.setUpdateTime(DateUtil.getCurrentDate());
				}
				GoodsDraft convertPo = recognizeVo.convertPo(recognizeVo, goodsDraft);
				convertPo.setStockNum(1);
				convertPo.setIsOfficialRecognized(1);
				GoodsDraft draft = goodsDraftDao.save(convertPo);
				goodsPicLinkDao.save(recognizeVo.convertList(recognizeVo, picLinks, draft.getId()));
				RecognizeTag recognizeTag = new RecognizeTag();
				GoodsPicLinkVo goodsPicLinkVo = recognizeVo.getGoodsImgs().get(0);
				if (null != goodsPicLinkVo) {
					recognizeTag.setTagTemplatFileId(recognizeVo.getGoodsImgs().get(0).getMaxImgID());
				}
				recognizeTag.setAssessorInstitution("上邮汇");
				recognizeTag.setQrCode(
						"https://www.shangyou-hui.com/admin_platform/smartphone/index.html?evaluationGoodsId="
								+ draft.getId());
				recognizeTag.setUpdateTime(DateUtil.getCurrentDate());
				recognizeTag.setGoodsId(draft.getId());
				recognizeTagDao.save(recognizeTag);
			}
		}
		if (!recognizeVo.getIsBatchCreate()) {
			GoodsDraft goodsDraft = null;
			List<GoodsPicLink> picLinks = null;
			String phone = recognizeVo.getGoodsOwnerPhone();
			String userId = userDao.findByPhone(phone);
			if (StringUtils.isEmpty(recognizeVo.getGoodsID())) {
				goodsDraft = new GoodsDraft();
				picLinks = new ArrayList<GoodsPicLink>();
				Shop shop = shopDao.findByUserId(userId);
				goodsDraft.setShopId(shop.getId());
				goodsDraft.setOwnerId(userId);
				goodsDraft.setCreateBy(roleId);
				goodsDraft.setUpdateBy(roleId);
			} else {
				goodsDraft = goodsDraftDao.findOne(recognizeVo.getGoodsID());
				picLinks = goodsPicLinkDao.getGoodsPicList(goodsDraft.getId());
				goodsDraft.setUpdateBy(roleId);
				goodsDraft.setUpdateTime(DateUtil.getCurrentDate());
			}
			goodsDraft.setIsOfficialRecognized(1);
			GoodsDraft draft = goodsDraftDao.save(recognizeVo.convertPo(recognizeVo, goodsDraft));
			goodsPicLinkDao.save(recognizeVo.convertList(recognizeVo, picLinks, draft.getId()));
			RecognizeTag recognizeTag = new RecognizeTag();
			if (recognizeVo.getGoodsImgs().size() > 0) {
				GoodsPicLinkVo goodsPicLinkVo = recognizeVo.getGoodsImgs().get(0);
				if (null != goodsPicLinkVo) {
					recognizeTag.setTagTemplatFileId(recognizeVo.getGoodsImgs().get(0).getMaxImgID());
				}
			}
			recognizeTag.setAssessorInstitution("上邮汇");
			recognizeTag
					.setQrCode("https://www.shangyou-hui.com/admin_platform/smartphone/index.html?evaluationGoodsId="
							+ draft.getId());
			recognizeTag.setUpdateTime(DateUtil.getCurrentDate());
			recognizeTag.setGoodsId(draft.getId());
			recognizeTagDao.save(recognizeTag);
		}
		operateLogDao.save(addLog(null, roleId, "添加鉴评结果"));
	}

	/**
	 * 下架商品
	 * 
	 * @param id
	 * @param userId
	 */
	@Transactional
	public void undercarriageGoods(String id, String banReason, String userId) {
		Goods goods = goodsDao.findById(id);
		if (null == goods) {
			throw new RestException(HttpStatus.ACCEPTED, "商品下架失败");
		}
		if (isUnderwayAuction(goods)) {
			throw new RestException(HttpStatus.ACCEPTED, "拍卖中的商品禁止下架");
		}
		HomeGoods homeGoods = homeGoodsDao.findByGoodsId(goods.getId());
		if (!StringUtils.isEmpty(homeGoods)) {
			homeGoods.setUpdateBy(userId);
			homeGoods.setUpdateTime(DateUtil.getCurrentDate());
			homeGoods.setIsDeleted(SysConstants.DELETE_FLAG_YES);
			homeGoodsDao.save(homeGoods);
		}
		goods.setBanReason(banReason);
		goods.setStatusCode(StatusType.GOODS_UNDERCARRIAGE);
		goods.setUpdateBy(userId);
		goods.setUpdateTime(DateUtil.getCurrentDate());
		goodsDao.save(goods);
		operateLogDao.save(addLog(null, userId, "管理员进行商品下架，下架商品为：" + id));
	}

	/**
	 * 商品审核（上架商品）
	 * 
	 * @param id
	 * @param userId
	 */
	public void reviewGoods(String id, String isApproved, String userId) {
		if (SysConstants.NO == Integer.valueOf(isApproved)) {
			Goods goods = goodsDao.findById(id);
			goods.setStatusCode(StatusType.GOODS_REFUSE);
			goods.setUpdateBy(userId);
			goods.setUpdateTime(DateUtil.getCurrentDate());
			goodsDao.save(goods);
			operateLogDao.save(addLog(null, userId, "管理员进行商品审核，审核通过，商品为：" + id));
			throw new RestException(HttpStatus.ACCEPTED, "审核拒绝");
		}
		if (SysConstants.YES == Integer.valueOf(isApproved)) {
			Goods goods = goodsDao.findById(id);
			goods.setStatusCode(StatusType.GOODS_GROUNDING);
			goods.setUpdateBy(userId);
			goods.setUpdateTime(DateUtil.getCurrentDate());
			goodsDao.save(goods);
		}
		operateLogDao.save(addLog(null, userId, "管理员进行商品审核，审核通过，商品为：" + id));
	}

	/**
	 * @Title: onsaleGoods @Description: 上架商品 @param @param id @param @param
	 *         userId 设定文件 @return void 返回类型 @throws
	 */
	public void onsaleGoods(String id, String userId, Integer stockNum) {
		Goods goods = goodsDao.findById(id);
		if (stockNum <= 0) {
			throw new RestException("商铺库存必须大于0");
		}
		if("purchase_type".equals(goods.getGoodsTypeCode())){
			throw new RestException("求购商品禁止重新上架");
		}
		goods.setStockNum(stockNum);
		goods.setStatusCode(StatusType.GOODS_GROUNDING);
		goods.setUpdateBy(userId);
		goods.setUpdateTime(DateUtil.getCurrentDate());
		goodsDao.save(goods);
		operateLogDao.save(addLog(null, userId, "管理员进行商品上架，上架商品为：" + id));
	}

	/**
	 * 获取商品列表
	 * 
	 * @param map
	 */
	public List<Goods> searchGoods(Map<String, Object> map) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		List<Goods> list = goodsDao.findAll(DynamicSpecifications.bySearchFilter(filter.values(), Goods.class));
		return list;
	}

	/**
	 * 出售、求购、拍卖商品发布
	 * 
	 * @param Map
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> publishGoods(PublishGoodsVo publishGoodsVo, String ownerID) throws Exception {
		// 清除草稿
		this.delGoodsDraft(publishGoodsVo);
		// 商品添加
		Map<String, Object> map = null;
		if ("auction_type".equals(publishGoodsVo.getGoodsTypeCode())) {
			// 拍卖参数校验
			this.isAuction(publishGoodsVo);
			// 拍卖商品信息存储
			map = this.addGoods(publishGoodsVo, ownerID);
			// 添加拍卖任务调度
			String goodsId = String.valueOf(map.get("goodsID"));

			auctionJobService.saveAuctionJob(goodsId, DateUtil.convertToString(publishGoodsVo.getValidEndTime()));
		}
		if ("purchase_type".equals(publishGoodsVo.getGoodsTypeCode())) {
			// 求购参数校验
			this.isPurchase(publishGoodsVo);
			// 求购商品信息存储
			map = this.addGoods(publishGoodsVo, ownerID);
			
			//添加求购任务调度
			String goodsId = String.valueOf(map.get("goodsID"));
			
			purchaseJobService.savePurchaseJob(goodsId, DateUtil.convertToString(publishGoodsVo.getValidEndTime()));
		}
		if ("sale_type".equals(publishGoodsVo.getGoodsTypeCode())) {
			// 出售商品存储
			map = this.addGoods(publishGoodsVo, ownerID);
		}
		return map;
	}

	/**
	 * 保存商品草稿
	 * 
	 * @param Map
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> saveGoodsDraft(GoodsDraftVo goodsDraftVo, String ownerID) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsDraft good = null;
		if ("auction_type".equals(goodsDraftVo.getGoodsTypeCode())) {
			if (StringUtils.isEmpty(goodsDraftVo.getValidStartTime())
					|| StringUtils.isEmpty(goodsDraftVo.getValidEndTime())) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION_DATE_ERROR.getDesc());
			}
		}
		if ("purchase_type".equals(goodsDraftVo.getGoodsTypeCode())) {
			if (StringUtils.isEmpty(goodsDraftVo.getValidEndTime())) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.BUY_DATE_ERROR.getDesc());
			}
		}
		if (StringUtils.isEmpty(goodsDraftVo.getGoodsDraftID())) {
			good = new GoodsDraft();
		} else {
			good = goodsDraftDao.findOne(goodsDraftVo.getGoodsDraftID());
		}
		// ownerID="40288099213d76e2015e2d772b020000";//测试数据
		good.setOwnerId(ownerID);
		Shop shop = shopDao.findIdByUserId(ownerID);
		if (StringUtils.isEmpty(shop)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.UNCERTIFIED_MEMBER.getDesc());
		}
		good.setShopId(shop.getId());
		good.setGoodsName(goodsDraftVo.getGoodsName());
		good.setGoodsDescription(goodsDraftVo.getGoodsDesc());
		good.setGoodsTypeCode(goodsDraftVo.getGoodsTypeCode());
		good.setGoodsKindId(goodsDraftVo.getGoodsKindID());
		if (!StringUtils.isEmpty(goodsDraftVo.getGoodsPrice())) {
			good.setGoodsPrice(goodsDraftVo.getGoodsPrice());
		}
		if (!StringUtils.isEmpty(goodsDraftVo.getGoodsBidStart())) {
			good.setGoodsBidStart(goodsDraftVo.getGoodsBidStart());
		}
		if (!StringUtils.isEmpty(goodsDraftVo.getGoodsMinBid())) {
			good.setGoodsMinBid(goodsDraftVo.getGoodsMinBid());
		}
		good.setGoodsSn(goodsDraftVo.getGoodsSN());
		good.setStockNum(goodsDraftVo.getGoodsNum());
		good.setGoodsQualityCode(goodsDraftVo.getGoodsQualityCode());
		good.setGoodsQualityScore(goodsDraftVo.getGoodsQualityScore());
		good.setRecognizedCode(goodsDraftVo.getGoodsRecognizeCode());
		if (!StringUtils.isEmpty(goodsDraftVo.getValidStartTime())) {
			good.setValidStartTime(goodsDraftVo.getValidStartTime());
		}
		if (!StringUtils.isEmpty(goodsDraftVo.getValidEndTime())) {
			good.setValidEndTime(goodsDraftVo.getValidEndTime());
		}
		good.setExpectedNum(goodsDraftVo.getExpectedNum());
		good.setSupplyMinSum(goodsDraftVo.getSupplyMinNum());
		if (!StringUtils.isEmpty(goodsDraftVo.getGoodsExpressFee())) {
			good.setExpressFee(goodsDraftVo.getGoodsExpressFee());
		}
		good.setExpressTempletId(goodsDraftVo.getGoodsTempletID());
		good.setCreateBy(ownerID);
		GoodsDraft saveGoodsDraft = goodsDraftDao.save(good);
		// 设置求购收货地址
		PurchaseAddressLink addressLink = purchaseAddressLinkDao.findOne(good.getId());
		if (!StringUtils.isEmpty(addressLink)) {
			purchaseAddressLinkDao.delete(addressLink);
		}
		PurchaseAddressLink purchaseAddressLink = new PurchaseAddressLink();
		purchaseAddressLink.setGoodsId(saveGoodsDraft.getId());
		purchaseAddressLink.setUserAddressId(goodsDraftVo.getPurchaseAddrID());
		purchaseAddressLink.setCreateTime(DateUtil.getCurrentDate());
		purchaseAddressLink.setUpdateTime(DateUtil.getCurrentDate());
		purchaseAddressLinkDao.save(purchaseAddressLink);
		List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(saveGoodsDraft.getId());
		for (GoodsPicLink goodsPicLink : goodsPicList) {
			goodsPicLinkDao.delete(goodsPicLink);
		}
		List<GoodsDraftImgVo> goodsImgs = goodsDraftVo.getGoodsImgs();
		if (!StringUtils.isEmpty(goodsImgs)) {
			for (GoodsDraftImgVo imageVo : goodsImgs) {
				GoodsPicLink goodsPicLink = new GoodsPicLink();
				goodsPicLink.setGoodsId(saveGoodsDraft.getId());
				goodsPicLink.setMaxImgId(imageVo.getMaxImgID());
				goodsPicLink.setMidImgId(imageVo.getMidImgID());
				goodsPicLink.setMinImgId(imageVo.getMinImgID());
				goodsPicLinkDao.save(goodsPicLink);
			}
		}
		map.put("goodsID", saveGoodsDraft.getId());
		map.put("goodsDetailUrl", "api/goods/getGoodsDraftDetail?goodsDraftID=" + saveGoodsDraft.getId());
		return map;
	}

	/**
	 * 获取草稿列表 @param Integer @return Map<String,Object> @throws
	 */
	public Map<String, Object> getGoodsDrafts(String userId, String goodsTypeCode, Integer pageNum, Integer pageSize)
			throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_ownerId", userId);
		map.put("EQ_isDeleted", 0);
		map.put("EQ_goodsTypeCode", goodsTypeCode);
		Map<String, SearchFilter> parse = SearchFilter.parse(map);
		PageInfo page = new PageInfo(pageNum, pageSize);
		Page<GoodsDraft> goodsDrafts = goodsDraftDao.findAll(
				DynamicSpecifications.bySearchFilter(parse.values(), GoodsDraft.class), page.getPageRequestInfo());
		returnMap.put("itemCount", goodsDrafts.getTotalElements());
		returnMap.put("pageCount", goodsDrafts.getTotalPages());
		returnMap.put("curPageIndex", page.getPageIndex());
		returnMap.put("hasNext", goodsDrafts.hasNext());
		List<Map<String, Object>> goodList = new ArrayList<Map<String, Object>>();
		for (GoodsDraft goodsDraft : goodsDrafts) {
			Map<String, Object> goodmap = new HashMap<String, Object>();
			goodmap.put("goodsDraftID", goodsDraft.getId());
			goodmap.put("draftDetailUrl", "api/goods/getGoodsDraftDetail?goodsDraftID=" + goodsDraft.getId());
			goodmap.put("goodsName", goodsDraft.getGoodsName());
			goodmap.put("goodsTypeCode", goodsDraft.getGoodsTypeCode());
			KeyMapping keyMapping = keyMappingDao.findByKeyCode(goodsDraft.getGoodsTypeCode());
			goodmap.put("goodsTypeDesc", keyMapping.getValueDesc());
			goodmap.put("goodsKindID", goodsDraft.getGoodsKindId());
			goodmap.put("goodsNum", goodsDraft.getStockNum());
			goodmap.put("goodsPrice", goodsDraft.getGoodsPrice());
			goodmap.put("goodsDesc", goodsDraft.getGoodsDescription());
			goodmap.put("goodsSN", goodsDraft.getGoodsSn());
			goodmap.put("goodsQualityCode", goodsDraft.getGoodsQualityCode());
			goodmap.put("goodsQualityScore", goodsDraft.getGoodsQualityScore());
			goodmap.put("goodsRecognizeCode", goodsDraft.getRecognizedCode());
			goodmap.put("goodsBidStart", goodsDraft.getGoodsBidStart());
			goodmap.put("goodsMinBid", goodsDraft.getGoodsMinBid());
			goodmap.put("expectedNum", goodsDraft.getExpectedNum());
			goodmap.put("supplyMinNum", goodsDraft.getSupplyMinSum());
			goodmap.put("validStartTime", goodsDraft.getValidStartTime());
			goodmap.put("validEndTime", goodsDraft.getValidEndTime());
			goodmap.put("goodsExpressFee", goodsDraft.getExpressFee());
			goodmap.put("goodsTempletID", goodsDraft.getExpressTempletId());
			List<Map<String, Object>> picList = new ArrayList<Map<String, Object>>();
			List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(goodsDraft.getId());
			for (GoodsPicLink goodsPicLink : goodsPicList) {
				Map<String, Object> picMap = new HashMap<String, Object>();
				FileManage MaxImgId = fileManageDao.findOne(goodsPicLink.getMaxImgId());
				FileManage MidImgId = fileManageDao.findOne(goodsPicLink.getMidImgId());
				FileManage MinImgId = fileManageDao.findOne(goodsPicLink.getMinImgId());
				if (!StringUtils.isEmpty(MaxImgId)) {
					picMap.put("maxImgID", goodsPicLink.getMaxImgId());
					picMap.put("maxImgUrl", FilePathUtils.fileUrl(MaxImgId.getFilePath()));
				}
				if (!StringUtils.isEmpty(MidImgId)) {
					picMap.put("midImgID", goodsPicLink.getMidImgId());
					picMap.put("midImgUrl", FilePathUtils.fileUrl(MidImgId.getFilePath()));
				}
				if (!StringUtils.isEmpty(MinImgId)) {
					picMap.put("minImgID", goodsPicLink.getMinImgId());
					picMap.put("minImgUrl", FilePathUtils.fileUrl(MinImgId.getFilePath()));
				}
				picList.add(picMap);
			}
			goodmap.put("goodsImgs", picList);
			goodList.add(goodmap);
		}
		returnMap.put("draftInfos", goodList);
		return returnMap;
	}

	/**
	 * 删除用户已保存的商品草稿 @param String @return void @throws
	 */
	public void delGoodsDraft(String goodsDraftID, String UserId) {
		GoodsDraft goodsDraft = goodsDraftDao.findOne(goodsDraftID);
		if (StringUtils.isEmpty(goodsDraft)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.NULL_LIMIT.getDesc());
		}
		goodsDraft.setUpdateTime(DateUtil.getCurrentDate());
		goodsDraft.setUpdateBy(UserId);
		goodsDraft.setIsDeleted(1);
		goodsDraftDao.save(goodsDraft);
	}

	/**
	 * 获取商品草稿详情 @param String @return List<Map<String,Object>> @throws
	 */
	public Map<String, Object> getGoodsDraftDetail(String GoodsDraftId) {
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsDraft goods = goodsDraftDao.findOne(GoodsDraftId);
		if (StringUtils.isEmpty(goods)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		Shop shop = shopDao.findOne(goods.getShopId());
		if (StringUtils.isEmpty(shop)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		map.put("shopID", goods.getShopId());
		map.put("shopName", shop.getShopName());
		map.put("ownerID", goods.getOwnerId());
		User user = userDao.findOne(goods.getOwnerId());
		if (!StringUtils.isEmpty(user.getImgFileId())) {
			FileManage fileManage = fileManageDao.findOne(user.getImgFileId());
			map.put("ownerImgUrl", FilePathUtils.fileUrl(fileManage.getFilePath()));
		} else {
			map.put("ownerImgUrl", null);
		}
		map.put("ownerPhone", user.getPhone());
		map.put("ownerName", user.getAliasName());
		map.put("ownerLevel", user.getChannelLevel());
		map.put("ownerTypeCode", user.getUserTypeCode());
		KeyMapping keyMapping = keyMappingDao.findByKeyCode(user.getUserTypeCode());
		map.put("ownerTypeDesc", keyMapping.getValueDesc());
		map.put("goodsID", goods.getId());
		map.put("goodsTypeCode", goods.getGoodsTypeCode());
		KeyMapping goodKeyMapping = keyMappingDao.findByKeyCode(goods.getGoodsTypeCode());
		if (!StringUtils.isEmpty(goodKeyMapping)) {
			map.put("goodsTypeDesc", goodKeyMapping.getValueDesc());
		}
		map.put("goodsName", goods.getGoodsName());
		map.put("goodsDesc", goods.getGoodsDescription());
		map.put("goodsSN", goods.getGoodsSn());
		GoodsKind goodsKind = goodsKindDao.findOne(goods.getGoodsKindId());
		if (!StringUtils.isEmpty(goodsKind.getParentKindId())) {
			GoodsKind parentGoodsKind = goodsKindDao.findOne(goodsKind.getParentKindId());
			map.put("categoryID", parentGoodsKind.getId());
			map.put("categoryName", parentGoodsKind.getKindName());
		} else {
			map.put("categoryID", null);
			map.put("categoryName", null);
		}
		map.put("childCategoryID", goods.getGoodsKindId());
		map.put("childCategoryName", goodsKind.getKindName());
		List<GoodsPicLink> goodsPicLists = goodsPicLinkDao.getGoodsPicList(GoodsDraftId);
		List<Map<String, Object>> picList = new ArrayList<Map<String, Object>>();
		for (GoodsPicLink goodsPicLink : goodsPicLists) {
			Map<String, Object> picMap = new HashMap<String, Object>();
			FileManage MaxImgId = fileManageDao.findOne(goodsPicLink.getMaxImgId());
			FileManage MidImgId = fileManageDao.findOne(goodsPicLink.getMidImgId());
			FileManage MinImgId = fileManageDao.findOne(goodsPicLink.getMinImgId());
			if (!StringUtils.isEmpty(MaxImgId)) {
				picMap.put("maxImgID", goodsPicLink.getMaxImgId());
				picMap.put("maxImgUrl", FilePathUtils.fileUrl(MaxImgId.getFilePath()));
			}
			if (!StringUtils.isEmpty(MidImgId)) {
				picMap.put("midImgID", goodsPicLink.getMidImgId());
				picMap.put("midImgUrl", FilePathUtils.fileUrl(MidImgId.getFilePath()));
			}
			if (!StringUtils.isEmpty(MinImgId)) {
				picMap.put("minImgID", goodsPicLink.getMinImgId());
				picMap.put("minImgUrl", FilePathUtils.fileUrl(MinImgId.getFilePath()));
			}
			picList.add(picMap);
		}
		map.put("goodsImgs", picList);
		map.put("goodsPrice", goods.getGoodsPrice());
		map.put("goodsQualityCode", goods.getGoodsQualityCode());
		KeyMapping goodKindsKeyMapping = keyMappingDao.findByKeyCode(goods.getGoodsQualityCode());
		if (!StringUtils.isEmpty(goodKindsKeyMapping)) {
			map.put("goodsQualityDesc", goodKindsKeyMapping.getValueDesc());
		}
		map.put("goodsQualityScore", goods.getGoodsQualityScore());
		map.put("goodsIsRecognized", goods.getIsRecognized());
		map.put("recognizedIsPrint", null);
		map.put("goodsBidStart", goods.getGoodsBidStart());
		map.put("expressFee", goods.getExpressFee());
		map.put("goodsValidTime", DateUtil.convertToString(goods.getValidStartTime(), DateUtil.YMD_FULL));
		map.put("goodsCloseTime", DateUtil.convertToString(goods.getValidEndTime(), DateUtil.YMD_FULL));
		map.put("goodsCreateTime", DateUtil.convertToString(goods.getCreateTime(), DateUtil.YMD_FULL));
		map.put("goodsStockSum", goods.getStockNum());
		map.put("goodsRecognizeCode", goods.getRecognizedCode());
		map.put("goodsMinBid", goods.getGoodsMinBid());
		map.put("goodsMinSupplyNum", goods.getSupplyMinSum());
		PurchaseAddressLink addressLink = purchaseAddressLinkDao.findOne(GoodsDraftId);
		if (!StringUtils.isEmpty(addressLink)) {
			map.put("purchaseAddrID", addressLink.getUserAddressId());
		}
		map.put("expectedNum", goods.getExpectedNum());
		map.put("expressTempletID", goods.getExpressTempletId());
		map.put("statusCode", null);
		map.put("statusDesc", null);
		return map;
	}

	/**
	 * @Title: getPostage @Description: 获取订单邮费 @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getPostage(List<Map<String, Object>> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BigDecimal decimal = new BigDecimal(0);
		for (Map<String, Object> map : param) {
			String expressTempletID = (String) map.get("ExpressTempletID");
			String province = (String) map.get("Province");
			String goodID = (String) map.get("goodID");
			int goodNum = (Integer) map.get("goodsNum");
			ExpressTemplet expressTemplet = expressTempletDao.findById(expressTempletID);
			BigDecimal postage = new BigDecimal(0);
			if (expressTemplet != null) {
				int express = expressTemplet.getFreeForExpress();
				ExpressWay expressWay = expressWayDao.findBytempletIdAndProvince(expressTempletID, province);
				// 免邮费的不存在运费方式就免邮费
				if (express == 1) {
					if (expressWay != null) {
						postage = getFee(expressWay, goodNum);
					}
				}
				// 不免邮费的不存在运费方式的按默认邮费计算
				if (express == 0) {
					if (expressWay == null) {
						postage = expressTemplet.getDefaultPostage();
					} else {
						postage = getFee(expressWay, goodNum);
					}
				}

				decimal = decimal.compareTo(postage) == 1 ? decimal : postage;
			} else {
				Goods goods = goodsDao.findById(goodID);
				decimal = decimal.compareTo(goods.getExpressFee()) == 1 ? decimal : goods.getExpressFee();
			}
		}
		resultMap.put("Postage", decimal);
		return resultMap;
	}

	public static BigDecimal getFee(ExpressWay expressWay, int goodNum) {
		BigDecimal postage = new BigDecimal(0);
		// 首件数量
		int firstThing = expressWay.getFirstThing();
		// 商品数量小于首件数量按首件计费
		if (firstThing >= goodNum) {
			postage = expressWay.getFirstFee();
		} else {
			// 首件邮费
			BigDecimal firstFee = expressWay.getFirstFee();
			// 续件的件数
			int parseInt = expressWay.getRenewThing();
			// 续件邮费
			BigDecimal renewFee = expressWay.getRenewFee();
			// 超出首件件数
			int renewThing = goodNum - firstThing;
			// 续件邮费金额
			postage = renewThing % parseInt == 0
					? firstFee.add(MathUtils.mul(new BigDecimal(renewThing / parseInt), renewFee))
					: firstFee.add(
							MathUtils.mul(new BigDecimal(renewThing / parseInt).add(new BigDecimal("1")), renewFee));

		}
		return postage;
	}

	/**
	 * @Title: getExpressTemplets @Description: 商家用户获取运费模板 @param @param
	 *         shopId @param @return 设定文件 @return List<Map<String,Object>>
	 *         返回类型 @throws
	 */
	public List<Map<String, Object>> getExpressTemplets(Map<String, String> param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<ExpressTemplet> express = null;
		if (StringUtils.isEmpty(param.get("templetID"))) {
			express = expressTempletDao.findByshopId(param.get("shopID"));
		} else {
			express = expressTempletDao.findByIdAndShopId(param.get("shopID"), param.get("templetID"));
		}
		for (ExpressTemplet exp : express) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("templetID", exp.getId());
			map.put("shopID", exp.getShopId());
			map.put("templetName", exp.getTempletName());
			map.put("templetProvince", exp.getProvince());
			map.put("templetCity", exp.getCity());
			map.put("templetArea", exp.getArea());
			map.put("templetStreet", exp.getStreet());
			map.put("templetAddress", exp.getGoodsAddress());
			map.put("deliveryInterval", exp.getDeliveryInterval());
			map.put("freeForExpress", exp.getFreeForExpress());
			map.put("priceType", exp.getPriceType());
			map.put("defaultPostage", exp.getDefaultPostage());
			List<ExpressWay> list = expressWayDao.findBytempletId(exp.getId());
			List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
			for (ExpressWay expressWay : list) {
				Map<String, Object> subMap = new HashMap<String, Object>();
				subMap.put("expressId", expressWay.getId());
				subMap.put("expressProvince", expressWay.getProvince());
				subMap.put("expressCity", expressWay.getCity());
				subMap.put("expressArea", expressWay.getArea());
				subMap.put("expressStreet", expressWay.getStreet());
				subMap.put("expressFirstThing", expressWay.getFirstThing());
				subMap.put("expressFirstFee", expressWay.getFirstFee());
				subMap.put("expressRenewThing", expressWay.getRenewThing());
				subMap.put("expressRenewFee", expressWay.getRenewFee());
				subList.add(subMap);
			}
			map.put("expressWays", subList);
			result.add(map);
		}
		return result;
	}

	/**
	 * @Title: getExpressTempletDetis @Description: 获取运费模块详情的接口 @param @param
	 *         userId @param @param param @param @return 设定文件 @return List<Map
	 *         <String,Object>> 返回类型 @throws
	 */
	/*
	 * public List<Map<String, Object>> getExpressTempletDetis(String shopID,
	 * Map<String, String> param) { List<Map<String, Object>> result = new
	 * ArrayList<Map<String, Object>>(); List<ExpressTemplet> express = null;
	 * 
	 * if (StringUtils.isEmpty(param.get("templetID"))) { express =
	 * expressTempletDao.findByshopId(shopID); } else { express =
	 * expressTempletDao.findByIdAndShopId(shopID, param.get("templetID")); }
	 * for (ExpressTemplet exp : express) { Map<String, Object> map = new
	 * HashMap<String, Object>(); map.put("templetID", exp.getId());
	 * map.put("shopID", exp.getShopId()); map.put("templetName",
	 * exp.getTempletName()); map.put("templetProvince", exp.getProvince());
	 * map.put("templetCity", exp.getCity()); map.put("templetArea",
	 * exp.getArea()); map.put("templetStreet", exp.getStreet());
	 * map.put("templetAddress", exp.getGoodsAddress());
	 * map.put("deliveryInterval", exp.getDeliveryInterval());
	 * map.put("freeForExpress", exp.getFreeForExpress()); map.put("priceType",
	 * exp.getPriceType()); List<ExpressWay> list =
	 * expressWayDao.findBytempletId(exp.getId()); List<Map<String, Object>>
	 * subList = new ArrayList<Map<String, Object>>(); for (ExpressWay
	 * expressWay : list) { Map<String, Object> subMap = new HashMap<String,
	 * Object>(); subMap.put("expressId", expressWay.getId());
	 * subMap.put("expressProvince", expressWay.getProvince());
	 * subMap.put("expressCity", expressWay.getCity());
	 * subMap.put("expressArea", expressWay.getArea());
	 * subMap.put("expressStreet", expressWay.getStreet());
	 * subMap.put("expressFirstThing", expressWay.getFirstThing());
	 * subMap.put("expressRenewThing", expressWay.getRenewThing());
	 * subMap.put("expressRenewFee", expressWay.getRenewFee());
	 * subList.add(subMap); } map.put("expressWays", subList); result.add(map);
	 * } return result; }
	 */
	/**
	 * @Title: editExpressTemplet @Description: 添加/编辑运费模板 @param 设定文件 @return
	 *         void 返回类型 @throws
	 */
	@Transactional
	public void editExpressTemplet(ExpressTempletVo expressTempletVo) {
		expressTempletVo.setShopID(expressTempletVo.getShopID());
		// 添加运费模块和运费方式
		if (StringUtils.isEmpty(expressTempletVo.getTempletID())) {
			ExpressTemplet expressTemplet = new ExpressTemplet();
			ExpressTemplet templet = expressTempletDao.save(expressTempletVo.coverPo(expressTempletVo, expressTemplet));
			List<ExpressWay> express = new ArrayList<ExpressWay>();
			List<ExpressWayVo> expressWays = expressTempletVo.getExpressWays();
			for (ExpressWayVo expressWayVo : expressWays) {
				if (StringUtils.isEmpty(expressWayVo.getExpressWayId())) {
					ExpressWay way = new ExpressWay();
					express.add(way);
					continue;
				}
			}
			List<ExpressWay> list = expressTempletVo.coverPo(expressWays, express);
			for (ExpressWay expressWay : list) {
				expressWay.setTempletId(templet.getId());
				expressWayDao.save(expressWay);
			}
			return;
		}
		// 编辑运费模式
		ExpressTemplet templet = expressTempletDao.findById(expressTempletVo.getTempletID());
		templet.setUpdateTime(DateUtil.getCurrentDate());
		ExpressTemplet save = expressTempletDao.save(expressTempletVo.coverPo(expressTempletVo, templet));
		// 编辑运费方式
		List<ExpressWay> express = new ArrayList<ExpressWay>();
		List<ExpressWayVo> expressWays = expressTempletVo.getExpressWays();
		if (expressWays != null && expressWays.size() > 0) {
			for (ExpressWayVo expressWayVo : expressWays) {
				if (StringUtils.isEmpty(expressWayVo.getExpressWayId())) {
					ExpressWay way = new ExpressWay();
					express.add(way);
					continue;
				}
				ExpressWay way = expressWayDao.findById(expressWayVo.getExpressWayId());
				express.add(way);
			}
		}
		List<ExpressWay> coverPo = expressTempletVo.coverPo(expressTempletVo.getExpressWays(), express);
		for (ExpressWay ways : coverPo) {
			if (StringUtils.isEmpty(ways.getId())) {
				ways.setTempletId(save.getId());
				expressWayDao.save(ways);
				continue;
			}
			ways.setUpdateTime(DateUtil.getCurrentDate());
			expressWayDao.save(ways);
		}
	}

	/**
	 * @Title: delExpressTemplet @Description: 删除运费模块的方法 @param @param templetID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void delExpressTemplet(String templetID) {
		if (StringUtils.isEmpty(templetID)) {
			throw new RestException("没传运费模块ID");
		}
		ExpressTemplet templet = expressTempletDao.findById(templetID);
		if (StringUtils.isEmpty(templet)) {
			throw new RestException("没有该模块");
		}
		templet.setIsDeleted(1);
		expressTempletDao.save(templet);
	}

	/**
	 * @Title: delExpressWay @Description: 删除运费方式 @param @param templetID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void delExpressWay(String templetWayId) {
		if (StringUtils.isEmpty(templetWayId)) {
			throw new RestException("没传运费方式的Id");
		}
		ExpressWay expressWay = expressWayDao.findById(templetWayId);
		if (StringUtils.isEmpty(expressWay)) {
			throw new RestException("没有该运费方式");
		}
		expressWay.setIsDeleted(1);
		expressWayDao.save(expressWay);
	}

	/**
	 * 获取平台拍卖商品出价记录
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<BidRecordVo> getBidRecord(GoodsQo qo) throws Exception {
		List<BidRecord> record = recordDao.findAll(qo.getGoodsId());
		List<BidRecordVo> returnVo = new ArrayList<BidRecordVo>();
		for (BidRecord bidRecord : record) {
			BidRecordVo vo = new BidRecordVo();
			vo.setBidGoodsId(bidRecord.getGoodsId());
			vo.setOwnerId(bidRecord.getUserId());
			User user = userRep.findOne(bidRecord.getUserId());
			if (null != user && null != user.getId()) {
				if (Validator.isNotNull(user.getImgFileId())) {
					FileManage file = fManageRepostory.findOne(user.getImgFileId());
					if (null != file && null != file.getId()) {
						vo.setOwnerImgUrl(file.getAbsolutePath());
					}
				}
				vo.setOwnerName("*******" + user.getLoginName().substring(7));
				if (null != user.getAliasName()) {
					vo.setOwnerName(user.getAliasName());
				}
				vo.setOwnerLevel(user.getChannelLevel());
				vo.setOwnerTypeCode(user.getUserTypeCode());
				KeyMapping key = keyDao.findByKeyCode(user.getUserTypeCode());
				if (null != key && null != key.getId()) {
					vo.setOwnerTypeDesc(key.getValueDesc());
				}
			}
			vo.setLastBidAmt(bidRecord.getLastBidPrice());
			vo.setIsBidWinner(bidRecord.getIsBidWinner());
			vo.setHasRefunded(bidRecord.getHasRefunded());
			vo.setCreateTime(bidRecord.getCreateTime());
			returnVo.add(vo);
		}
		return returnVo;
	}

	/**
	 * 获取平台求购商品商家响应供货记录
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<BidRecordVo> getSupplyRecord(GoodsQo qo) throws Exception {
		List<SupplyRecord> record = supplyDao.findBygoodsId(qo.getGoodsId());
		List<BidRecordVo> returnVo = new ArrayList<BidRecordVo>();
		for (SupplyRecord supplyRecord : record) {
			BidRecordVo vo = new BidRecordVo();
			vo.setBidGoodsId(supplyRecord.getGoodsId());
			vo.setOwnerId(supplyRecord.getSupplyUserId());
			User user = userRep.findOne(supplyRecord.getSupplyUserId());
			if (null != user && null != user.getId()) {
				if (Validator.isNotNull(user.getImgFileId())) {
					FileManage file = fManageRepostory.findOne(user.getImgFileId());
					if (null != file && null != file.getId()) {
						vo.setOwnerImgUrl(file.getAbsolutePath());
					}
				}
				vo.setOwnerName(user.getAliasName());
				vo.setOwnerLevel(user.getChannelLevel());
				vo.setOwnerTypeCode(user.getUserTypeCode());
				KeyMapping key = keyDao.findByKeyCode(user.getUserTypeCode());
				if (null != key && null != key.getId()) {
					vo.setOwnerTypeDesc(key.getValueDesc());
				}
			}
			vo.setSupplyNum(null==supplyRecord.getSupplyNum()?0:supplyRecord.getSupplyNum());// 供货数量
			vo.setSupplyGoodsId(supplyRecord.getSupplyGoodsId());// 供货商品ID
			vo.setSupplyRecordID(supplyRecord.getId());// 供货记录ID
			Goods supplyGoods = goodsDao.findOne(supplyRecord.getSupplyGoodsId());// 供货商品
			vo.setGoodsPrice(supplyGoods.getGoodsPrice());// 供货商品价格
			Goods goods = goodsDao.findOne(supplyRecord.getGoodsId());// 求购商品
			vo.setGoodsName(supplyGoods.getGoodsName());// 供货商品名
			// 求购订单实际价格
			vo.setOrderPrice(MathUtils.mul(goods.getGoodsPrice(), new BigDecimal(supplyRecord.getSupplyNum())));
			// 供货商品图片集
			List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(goods.getId());
			List<String> picList = new ArrayList<String>();
			for (GoodsPicLink goodsPicLink : goodsPicList) {
				String url = fileManageDao.findUrlById(goodsPicLink.getMaxImgId());
				picList.add(FilePathUtils.fileUrl(url));
			}
			vo.setGoodsPic(picList);
			vo.setSupplyOrderId(null == supplyRecord.getSupplyOrderId() ? null : supplyRecord.getSupplyOrderId());
			// 求购供货是否成功
			// 没有生成订单，标识求购方未确认，供货未完成
			if (StringUtils.isEmpty(vo.getSupplyOrderId())) {
				vo.setSuccess(false);
			} else {
				Order order = orderDao.findOne(vo.getSupplyOrderId());
				// 生成订单，但是还没有支付，供货未完成
				if ("order_unpaid".equals(order.getOrderTypeCode())) {
					vo.setSuccess(false);
					vo.setIsCanPay("待支付");
					// 生成订单，但是发生了退款，供货未完成
				} else
					if ("order_closed".equals(order.getOrderTypeCode()) && !StringUtils.isEmpty(order.getIsRefund())) {
					vo.setSuccess(false);
					// 生成订单，状态为已关闭，但是没有快递单号，说明是订单中途取消，供货未完成
				} else if ("order_closed".equals(order.getOrderTypeCode())
						&& StringUtils.isEmpty(order.getExpressBillNo())) {
					vo.setSuccess(false);
					// 其他情况，包括订单正常完成和正在运送中，均认为是供货完成。
				} else {
					vo.setSuccess(true);
				}
			}
			Order order = null;
			if (!StringUtils.isEmpty(supplyRecord.getSupplyOrderId())) {
				order = orderDao.findOne(supplyRecord.getSupplyOrderId());
			}
			if (null != order && null != order.getId()) {
				vo.setOrderAmt(order.getTotalPrice());
				KeyMapping key = keyDao.findByKeyCode(order.getOrderStatusCode());
				if (null != key && null != key.getId()) {
					vo.setOrderStatusDesc(key.getValueDesc());
				}
			}
			vo.setCreateTime(supplyRecord.getCreateTime());
			returnVo.add(vo);
		}
		return returnVo;
	}

	/**
	 * @Title: getGoods @Description:获取商品评价记录 @param @param param @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getGoods(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> goodDiscuss = new ArrayList<Map<String, Object>>();
		result.put("itemCount", 0);
		result.put("pageCount", 0);
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", false);
		result.put("goodsScoreInfos", goodDiscuss);
		List<String> list = orderGood.findByGoodsId((String) param.get("goodsID"));
		if (list.size() <= 0) {
			return result;
		}
		param.put("orderID", list);
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.getGoods(param)));
		// 分页的方法
		Page<OrderServiceEntity> orderServiceEntitys = orderServiceDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), OrderServiceEntity.class),
				buildPageRequest(pageInfo));
		for (OrderServiceEntity orderServiceEntity : orderServiceEntitys) {
			Map<String, Object> orderServiceEntityMap = new HashMap<String, Object>();
			orderServiceEntityMap.put("orderServiceID", orderServiceEntity.getId());
			orderServiceEntityMap.put("orderID", orderServiceEntity.getOrderId());
			orderServiceEntityMap.put("buyerID", orderServiceEntity.getBuyerId());
			User user = userDao.findById(orderServiceEntity.getBuyerId());
			orderServiceEntityMap.put("buyerName", user.getAliasName());
			if (!StringUtils.isEmpty(user.getImgFileId())) {
				String path = fManageRepostory.findUrlById(user.getImgFileId());
				orderServiceEntityMap.put("buyerImgUrl", FilePathUtils.fileUrl(path));
			}
			orderServiceEntityMap.put("orderScore", orderServiceEntity.getOrderScore());
			orderServiceEntityMap.put("shopOwnerScore", orderServiceEntity.getShopOwnerScore());
			orderServiceEntityMap.put("orderComment", orderServiceEntity.getOrderComment());
			orderServiceEntityMap.put("statusCode", orderServiceEntity.getStatusCode());
			KeyMapping mapping = keyDao.findByKeyCode(orderServiceEntity.getStatusCode());
			orderServiceEntityMap.put("statusDesc", mapping);
			orderServiceEntityMap.put("updateTime", orderServiceEntity.getUpdateTime());
			goodDiscuss.add(orderServiceEntityMap);
		}
		result.put("itemCount", orderServiceEntitys.getTotalElements());
		result.put("pageCount", orderServiceEntitys.getTotalPages());
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", orderServiceEntitys.hasNext());
		result.put("goodsScoreInfos", goodDiscuss);
		return result;
	}

	// 草稿清除
	public void delGoodsDraft(PublishGoodsVo publishGoodsVo) {
		GoodsDraft goodsDraft = goodsDraftDao.findOne(publishGoodsVo.getGoodsDraftID());
		if (!StringUtils.isEmpty(goodsDraft)) {
			goodsDraft.setIsDeleted(1);
			goodsDraftDao.save(goodsDraft);
		}
	}

	// 添加商品到信息表
	public Map<String, Object> addGoods(PublishGoodsVo publishGoodsVo, String ownerID) {
		Map<String, Object> map = new HashMap<String, Object>();
		Goods good = null;
		if (StringUtils.isEmpty(publishGoodsVo.getGoodsId())) {
			good = new Goods();
		} else {
			good = goodsDao.findById(publishGoodsVo.getGoodsId());
		}
		if (isUnderwayAuction(good)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION_UPDATE_ERROR.getDesc());
		}
		good.setOwnerId(ownerID);
		Shop shop = shopDao.findIdByUserId(ownerID);
		if (StringUtils.isEmpty(shop)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.UNCERTIFIED_MEMBER.getDesc());
		}
		if ("shop_frozen".equals(shop.getStatusCode())) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.FROZEN_SHOP.getDesc());
		}
		good.setShopId(shop.getId());
		good.setGoodsName(publishGoodsVo.getGoodsName());
		good.setGoodsDescription(publishGoodsVo.getGoodsDesc());
		good.setGoodsTypeCode(publishGoodsVo.getGoodsTypeCode());
		good.setGoodsKindId(publishGoodsVo.getGoodsKindID());
		if (!StringUtils.isEmpty(publishGoodsVo.getGoodsPrice())) {
			good.setGoodsPrice(publishGoodsVo.getGoodsPrice());
		}
		if (!StringUtils.isEmpty(publishGoodsVo.getGoodsBidStart())) {
			good.setGoodsBidStart(publishGoodsVo.getGoodsBidStart());
		}
		if (!StringUtils.isEmpty(publishGoodsVo.getGoodsMinBid())) {
			good.setGoodsMinBid(publishGoodsVo.getGoodsMinBid());
		}
		good.setGoodsSn(publishGoodsVo.getGoodsSN());
		good.setStockNum(publishGoodsVo.getGoodsNum());
		good.setGoodsQualityCode(publishGoodsVo.getGoodsQualityCode());
		good.setGoodsQualityScore(publishGoodsVo.getGoodsQualityScore());
		good.setRecognizedCode(publishGoodsVo.getGoodsRecognizeCode());
		if (!StringUtils.isEmpty(publishGoodsVo.getValidStartTime())) {
			good.setValidStartTime(publishGoodsVo.getValidStartTime());
		}
		if (!StringUtils.isEmpty(publishGoodsVo.getValidEndTime())) {
			good.setValidEndTime(publishGoodsVo.getValidEndTime());
		}
		if ("auction_type".equals(publishGoodsVo.getGoodsTypeCode())) {
			KeyMapping mapping = keyMappingDao.findByKeyCode("auction_time");
			String desc = mapping.getValueDesc();
			if(publishGoodsVo.getValidStartTime().before(DateUtil.getCurrentDate())){
				throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.AUCTION_DATE_ERROR2.getDesc());
			}
			if(publishGoodsVo.getValidEndTime().before(publishGoodsVo.getValidStartTime())){
				throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.AUCTION_DATE_ERROR5.getDesc());
			}
			if(DateUtil.seekDate(DateUtil.getCurrentDate(),Integer.valueOf(desc)).before(publishGoodsVo.getValidStartTime())){
				throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.AUCTION_DATE_ERROR3.getDesc());
			}
			if(DateUtil.seekDate(publishGoodsVo.getValidStartTime(), Integer.valueOf(desc)).before(publishGoodsVo.getValidEndTime())){
				throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.AUCTION_DATE_ERROR4.getDesc());
			}
		}
		if ("purchase_type".equals(publishGoodsVo.getGoodsTypeCode())) {
			KeyMapping mapping = keyMappingDao.findByKeyCode("purchase_time");
			String desc = mapping.getValueDesc();
			if(publishGoodsVo.getValidEndTime().before(DateUtil.getCurrentDate())){
				throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.PURCHASE_DATE_ERROR2.getDesc());
			}
			if(DateUtil.seekDate(DateUtil.getCurrentDate(),Integer.valueOf(desc)).before(publishGoodsVo.getValidEndTime())){
				throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.PURCHASE_DATE_ERROR3.getDesc());
			}
		}
		good.setExpectedNum(publishGoodsVo.getExpectedNum());
		good.setSupplyMinSum(publishGoodsVo.getSupplyMinNum());
		if (!StringUtils.isEmpty(publishGoodsVo.getGoodsExpressFee())) {
			good.setExpressFee(publishGoodsVo.getGoodsExpressFee());
		}
		good.setExpressTempletId(publishGoodsVo.getGoodsTempletID());
		String goodsKindId = publishGoodsVo.getGoodsKindID();
		if(!"goods_undercarriage".equals(good.getStatusCode())){			
			if (goodsKindDao.findAllById(goodsKindId).getReviewIsAvoid() == 1) {
				good.setStatusCode("goods_grounding");// 免审
			} else {
				good.setStatusCode("goods_pending");// 待审
			}
		}
		good.setCreateBy(ownerID);
		Goods saveGood = goodsDao.save(good);
		PurchaseAddressLink purchaseAddressLink = null;
		if (!StringUtils.isEmpty(publishGoodsVo.getGoodsId())) {
			purchaseAddressLink = purchaseAddressLinkDao.findOne(publishGoodsVo.getGoodsId());
		} else {
			purchaseAddressLink = new PurchaseAddressLink();
		}
		purchaseAddressLink.setGoodsId(saveGood.getId());
		purchaseAddressLink.setUserAddressId(publishGoodsVo.getPurchaseAddrID());
		purchaseAddressLink.setCreateTime(DateUtil.getCurrentDate());
		purchaseAddressLink.setUpdateTime(DateUtil.getCurrentDate());
		purchaseAddressLinkDao.save(purchaseAddressLink);
		List<GoodsDraftImgVo> goodsImgs = publishGoodsVo.getGoodsImgs();
		if (!StringUtils.isEmpty(goodsImgs)) {
			for (GoodsDraftImgVo imageVo : goodsImgs) {
				GoodsPicLink goodsPicLink = new GoodsPicLink();
				goodsPicLink.setGoodsId(saveGood.getId());
				goodsPicLink.setMaxImgId(imageVo.getMaxImgID());
				goodsPicLink.setMidImgId(imageVo.getMidImgID());
				goodsPicLink.setMinImgId(imageVo.getMinImgID());
				goodsPicLinkDao.save(goodsPicLink);
			}
		}
		map.put("goodsID", saveGood.getId());
		map.put("goodsDetailUrl", "api/goods/queryGoodsDetails?goodsID=" + saveGood.getId());
		return map;
	}

	// 拍卖参数校验
	public void isAuction(PublishGoodsVo publishGoodsVo) {
		if ("auction_type".equals(publishGoodsVo.getGoodsTypeCode())) {
			if (StringUtils.isEmpty(publishGoodsVo.getValidStartTime())
					|| StringUtils.isEmpty(publishGoodsVo.getValidEndTime())) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION_DATE_ERROR.getDesc());
			}
			if (!publishGoodsVo.getValidStartTime().before(publishGoodsVo.getValidEndTime())) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION_DATE_ERROR.getDesc());
			}
		}
	}

	// 求购参数校验
	public void isPurchase(PublishGoodsVo publishGoodsVo) {
		if ("purchase_type".equals(publishGoodsVo.getGoodsTypeCode())) {
			if (StringUtils.isEmpty(publishGoodsVo.getValidEndTime())) {
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.BUY_DATE_ERROR.getDesc());
			}
		}
	}

	/**
	 * 获取鉴评标签 @param @return Map<String,Object> @throws
	 */
	public Map<String, Object> getGoodsEvaluation(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userDao.findUserByphone((String) param.get("userPhone"));
		String userId = null;
		if (!StringUtils.isEmpty(user)) {
			userId = user.getId();
		}
		if (!StringUtils.isEmpty(userId)) {
			map.put("EQ_ownerId", userId);
		}
		if (!StringUtils.isEmpty(param.get("goodsName"))) {
			map.put("LIKE_GoodsName", param.get("goodsName"));
		}
		if (!StringUtils.isEmpty(param.get("isPrint"))) {
			map.put("EQ_recognizedIsPrint", param.get("isPrint"));
		}
		Map<String, SearchFilter> parse = SearchFilter.parse(map);
		Page<GoodsDraft> pageGoods = goodsDraftDao.findAll(
				DynamicSpecifications.bySearchFilter(parse.values(), GoodsDraft.class), pageInfo.getPageRequestInfo());
		List<Object> evaluation = new ArrayList<Object>();
		for (GoodsDraft goods : pageGoods) {
			Map<String, Object> evaluationMap = new HashMap<String, Object>();
			User goodsUser = userDao.findById(goods.getOwnerId());
			evaluationMap.put("goodsId", goods.getId());// 商品Id
			evaluationMap.put("userName", goodsUser.getAliasName());// 用户昵称
			evaluationMap.put("userPhone", goodsUser.getPhone());// 用户电话
			evaluationMap.put("goodsQualityCode", goods.getGoodsQualityCode());// 商品品相编码
			if (!StringUtils.isEmpty(goods.getGoodsQualityCode())) {
				evaluationMap.put("goodsQualityDesc",
						keyMappingDao.findByKeyCode(goods.getGoodsQualityCode()).getValueDesc());// 商品品相
			}
			if (StringUtils.isEmpty(goods.getGoodsQualityCode())) {
				evaluationMap.put("goodsQualityDesc", null);
			}
			evaluationMap.put("goodsName", goods.getGoodsName());// 商品名称
			evaluationMap.put("recognizedIsPrint", goods.getRecognizedIsPrint());// 是否打印
			evaluationMap.put("recognizedCode", goods.getRecognizedCode());// 鉴评识别码
			evaluation.add(evaluationMap);
		}
		returnMap.put("itemCount", pageGoods.getTotalElements());
		returnMap.put("pageCount", pageGoods.getTotalPages());
		returnMap.put("curPageIndex", pageInfo.getPageIndex());
		returnMap.put("hasNext", pageGoods.hasNext());
		returnMap.put("goodsEvaluationInfos", evaluation);
		return returnMap;
	}

	/**
	 * 获取鉴评标签详情 @param @return Map<String,Object> @throws
	 */
	public Map<String, Object> getGoodsEvaluationDetail(String evaluationGoodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsDraft goodsDraft = goodsDraftDao.findOne(evaluationGoodsId);
		if (StringUtils.isEmpty(goodsDraft)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		User user = userDao.findById(goodsDraft.getOwnerId());
		UserAuthentication userAuthentication = authenticationDao.findByUserId(goodsDraft.getOwnerId());
		if (StringUtils.isEmpty(user)) {
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		List<FileManage> findPicByGoodsId = fileManageDao.findPicByGoodsId(evaluationGoodsId);
		List<String> imgPath = new ArrayList<String>();
		for (FileManage fileManage : findPicByGoodsId) {
			imgPath.add(FilePathUtils.fileUrl(fileManage.getFilePath()));
		}
		RecognizeTag recognizeTag = recognizeTagDao.findByGoodsId(evaluationGoodsId);
		map.put("imgs", imgPath);
		KeyMapping userKeyMapping = keyMappingDao.findByKeyCode(user.getUserTypeCode());
		map.put("userTypeDesc", userKeyMapping.getValueDesc());// 用户认证类型
		map.put("userName", userAuthentication.getUserRealName());
		map.put("userPhone", user.getPhone());
		map.put("goodsInfo", goodsDraft);
		Integer isOfficialRecognized = goodsDraft.getIsOfficialRecognized();
		map.put("isOfficialRecognized", isOfficialRecognized == 0 ? "否" : "是");// 是否平台鉴评
		map.put("isRecognized", goodsDraft.getRecognizedCode() == null ? "未鉴评" : "已鉴评");
		if (StringUtils.isEmpty(goodsDraft.getRecognizedCode())) {
			map.put("goodsQualityDesc", null);// 品相
		} else {
			KeyMapping keyMapping = keyMappingDao.findByKeyCode(goodsDraft.getGoodsQualityCode());
			map.put("goodsQualityDesc", keyMapping.getValueDesc());// 品相
		}
		map.put("recognizeTag", recognizeTag);
		return map;
	}

	/**
	 * 获取当前店铺销售中的商品 @param @return List<Goods> @throws
	 */
	public List<Goods> searchShopGoods(Map<String, Object> param) {
		String shopId = (String) param.get("shopId");
		List<Goods> goodsList = goodsDao.findByShopId(shopId);
		return goodsList;
	}

	/**
	 * 是否是进行中的拍卖 @param @return boolean @throws
	 */
	public static boolean isUnderwayAuction(Goods goods) {
		boolean bool = false;
		if ("auction_type".equals(goods.getGoodsTypeCode())) {
			if (DateUtil.compare(DateUtil.getCurrentDate(), goods.getValidStartTime(), goods.getValidEndTime())) {
				bool = true;
			}
		}
		return bool;
	}

	@SuppressWarnings("unchecked")
	public void modificationGoodsSetting(Map<String, Object> param) {
		List<Map<String,Object>> setList = (List<Map<String, Object>>) param.get("setList");
		for (Map<String, Object> setting : setList) {			
			KeyMapping mapping = keyMappingDao.findOne((String) setting.get("settingID"));
			mapping.setValueDesc((String) setting.get("settingValue"));
			keyMappingDao.save(mapping);
		}
	}
}
