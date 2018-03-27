package com.visionet.syh_mall.service.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.SysConstants;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.Banner;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.entity.marketing.GroupBuy;
import com.visionet.syh_mall.entity.marketing.GroupDetail;
import com.visionet.syh_mall.entity.marketing.GroupUser;
import com.visionet.syh_mall.entity.service.ShopsMarketing;
import com.visionet.syh_mall.entity.shop.ComboPatch;
import com.visionet.syh_mall.entity.shop.FulfilRemit;
import com.visionet.syh_mall.entity.shop.Marketing;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.entity.shop.ShopSetting;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.marketing.ComboGoodsRepository;
import com.visionet.syh_mall.repository.marketing.ComboPatchRepository;
import com.visionet.syh_mall.repository.marketing.DiscountTimeRepository;
import com.visionet.syh_mall.repository.marketing.GroupBuyRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mobile.BannerRepository;
import com.visionet.syh_mall.repository.mobile.FulifilRemitRepository;
import com.visionet.syh_mall.repository.mobile.GoodsPicLinkRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.GropDetailRepository;
import com.visionet.syh_mall.repository.mobile.GroupUserRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.repository.mobile.ShopSettingRepository;
import com.visionet.syh_mall.repository.syhservice.MarketingRepository;
import com.visionet.syh_mall.repository.syhservice.ShopsMarketingRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.vo.shop.ComboGoods;
import com.visionet.syh_mall.vo.shop.MarketVo;
import com.visionet.syh_mall.vo.shop.ShopQo;
import com.visionet.syh_mall.vo.shop.ShopVo;

/**
 * @Author DM
 * @version ：2017年8月16日下午4:06:50 实体类
 */
@Service
public class ShopService extends BaseService {
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;
	@Autowired
	private UserAuthenticationRepository userAauthenticationDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired
	private MarketingRepository marketDao;
	@Autowired
	private BannerRepository bannerDao;
	@Autowired
	private ShopSettingRepository shopSetDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private FulifilRemitRepository fulifilRemitDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private GoodsPicLinkRepository goodsPicLinkDao;
	@Autowired
	private DiscountTimeRepository discountTimeDao;
	@Autowired
	private ComboPatchRepository comboPatchDao;
	@Autowired
	private ComboGoodsRepository comboGoodsDao;
	@Autowired
	private GroupBuyRepository groupBuyDao;
	@Autowired
	private GropDetailRepository detailDao;
	@Autowired
	private GroupUserRepository groupUserDao;
	@Autowired
	private UserAccountFlowRepository userAccountFlowDao;
	@Autowired
	private ShopsMarketingRepository shopMarketDao;
	/**
	 * @Title: findIdByUserId @Description: 找到店铺的方法 @param @param
	 *         userID @param @return 设定文件 @return Shop 返回类型 @throws
	 */
	public Shop findIdByUserId(String userID) {
		Shop shop = shopDao.findIdByUserId(userID);
		return shop;
	}

	/**
	 * 
	 * @Title: searchShops @Description: (店铺搜索的方法) @param @param
	 *         param @param @param pageInfo @param @return @param @throws
	 *         Exception 设定文件 @return List<Map<String,Object>> 返回类型 @throws
	 */
	public Map<String, Object> searchShops(Map<String, Object> param, PageInfo pageInfo) throws Exception {
		// 返回的结果集
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> shopList = new ArrayList<Map<String, Object>>();
		result = getReturnMap(result, null, pageInfo);
		result.put("shopInfos", shopList);
		if (!StringUtils.isEmpty(param.get("userShopTypeCode"))) {
			List<String> list = userDao.findByUserTypeCode((String) param.get("userShopTypeCode"));
			if (list.size() <= 0) {
				return result;
			}
			param.put("users", list);
		}
		// 非空查询的参数
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchShops(param)));
		// 分页的方法
		Page<Shop> list = shopDao.findAll(DynamicSpecifications.bySearchFilter(filter.values(), Shop.class),
				buildPageRequest(pageInfo));
		// 店铺的信息数组
		for (Shop shop : list) {
			Map<String, Object> shopMap = new HashMap<String, Object>();
			shopMap.put("shopID", shop.getId());
			shopMap.put("userID", shop.getUserId());
			shopMap.put("userName", shop.getUserName());
			shopMap.put("shopName", shop.getShopName());
			shopMap.put("shopLevel", shop.getShopLevel());
			shopMap.put("shopStatusCode", shop.getStatusCode());
			User user = userDao.findById(shop.getUserId());
			shopMap.put("aliasName", user.getAliasName());
			
			shopMap.put("userShopTypeCode", user.getUserTypeCode());
			shopMap.put("loginname", user.getLoginName());
			if (user.getUserTypeCode().equals("authentication_personal")) {
				shopMap.put("userShopType", "个人店铺");
			}
			if (user.getUserTypeCode().equals("authentication_tenant")) {
				shopMap.put("userShopType", "企业店铺");
			}
			KeyMapping keyMapping = keyMappingDao.findByKeyCode(shop.getStatusCode());
			shopMap.put("shopStatusDesc", keyMapping.getValueDesc());

			shopMap.put("validityTime", shop.getValidityTime());
			shopMap.put("shopIsFrozen", shop.getShopIsFrozen());
			shopMap.put("shopIsOfficial", shop.getShopIsOfficial());
			shopMap.put("shopAddress", shop.getShopAddress());
			shopList.add(shopMap);
		}
		result.put("shopInfos", shopList);
		result = getReturnMap(result, list, pageInfo);
		return result;
	}

	/**
	 * 
	 * @Title: getShopDetail @Description: 查询店铺详情的方法 @param @param
	 *         shopID @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getShopDetail(String shopID) {
		// 返回的结果集
		Map<String, Object> result = new HashMap<String, Object>();
		// 通过店铺ID搜索店铺
		Shop shop = shopDao.findById(shopID);
		if (StringUtils.isEmpty(shop)) {
			throw new RestException("没有该店铺");
		}
		result.put("shopID", shop.getId());
		result.put("userID", shop.getUserId());
		result.put("userName", shop.getUserName());
		// 通过店主ID查询店主的认证信息
		UserAuthentication userAuthentication = userAauthenticationDao.findByUserId(shop.getUserId());
		if (StringUtils.isEmpty(userAuthentication)) {
			throw new RestException("没有店铺用户认证信息");
		}
		result.put("userRealName", userAuthentication.getUserRealName());
		result.put("userProvince", userAuthentication.getUserProvince());
		result.put("userCity", userAuthentication.getUserCity());
		result.put("userArea", userAuthentication.getUserArea());
		result.put("userStreet", userAuthentication.getUserStreet());
		User user = userDao.findById(shop.getUserId());
		result.put("userShopTypeCode", user.getUserTypeCode());
		if (user.getUserTypeCode().equals("authentication_personal")) {
			result.put("userShopType", "个人店铺");
		}
		if (user.getUserTypeCode().equals("authentication_tenant")) {
			result.put("userShopType", "企业店铺");
		}
		result.put("userAddress", user.getAddress());
		result.put("userIDFImgID", userAuthentication.getId_f_fileId());
		String userIDFImgUrl = fileManageDao.findUrlById(userAuthentication.getId_f_fileId());
		result.put("userIDFImgUrl", FilePathUtils.fileUrl(userIDFImgUrl));
		result.put("userIDBImgID", userAuthentication.getId_b_fileId());
		String userIDBImgID = fileManageDao.findUrlById(userAuthentication.getId_b_fileId());
		result.put("userIDBImgUrl", FilePathUtils.fileUrl(userIDBImgID));
		result.put("licenseNO", userAuthentication.getLicenseNo());
		result.put("organizationCode", userAuthentication.getOrganizationCode());
		result.put("licenseCertImgID", userAuthentication.getLicenseCertFileId());
		String licenseCertImgID = fileManageDao.findUrlById(userAuthentication.getLicenseCertFileId());
		result.put("licenseCertImgUrl", FilePathUtils.fileUrl(licenseCertImgID));
		result.put("shopName", shop.getShopName());
		result.put("shopLevel", shop.getShopLevel());
		result.put("shopStatusCode", shop.getStatusCode());
		KeyMapping keyMapping = keyMappingDao.findByKeyCode(shop.getStatusCode());
		result.put("shopStatusDesc", keyMapping.getValueDesc());
		result.put("validityTime", shop.getValidityTime());
		result.put("shopIsFrozen", shop.getShopIsFrozen());
		result.put("shopIsOfficial", shop.getShopIsOfficial());
		result.put("shopAddress", shop.getShopAddress());
		return result;
	}

	/**
	 * 
	 * @Title: frozeShop @Description: 店铺冻结 @param @param shopID 设定文件 @return
	 *         void 返回类型 @throws
	 */
//	@Log(name="店铺冻结",model="店铺模块",log="冻结店铺",field="")
	public void frozeShop(String shopID) {
		Shop shop = shopDao.findById(shopID);
		if (StringUtils.isEmpty(shop)) {
			throw new RestException("店铺不存在");
		}
		shop.setStatusCode("shop_frozen");
		shop.setShopIsFrozen(SysConstants.YES);
		shop.setUpdateTime(DateUtil.getCurrentDate());
		shopDao.save(shop);
	}

	/**
	 * @Title: unfrozeShop @Description: 解冻店铺 @param @param shopID 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void unfrozeShop(String shopID) {
		Shop shop = shopDao.findById(shopID);
		if (StringUtils.isEmpty(shop)) {
			throw new RestException("店铺不存在");
		}
		shop.setStatusCode("shop_normal");
		shop.setShopIsFrozen(SysConstants.NO);
		shop.setUpdateTime(DateUtil.getCurrentDate());
		shopDao.save(shop);
	}

	/**
	 * @Title: editShopDetail @Description: 编辑店铺 @param @param
	 *         param @param @throws Exception 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void editShopDetail(ShopVo shopVo) {
		// 店铺的修改
		Shop shop = shopDao.findById(shopVo.getShopID());
		if (StringUtils.isEmpty(shop)) {
			throw new RestException("店铺不存在");
		}
		shopDao.save(shopVo.shopConvertPo(shopVo, shop));

		// 店主地址的修改
		User user = userDao.findById(shopVo.getUserID());
		if (StringUtils.isEmpty(user)) {
			throw new RestException("用户不存在");
		}
		user.setAddress(shopVo.getUserAddress());
		user.setUpdateTime(DateUtil.getCurrentDate());
		userDao.save(user);

		// 用户的认证信息的修改
		UserAuthentication userAuthentication = userAauthenticationDao.findByUserId(shopVo.getUserID());
		if (StringUtils.isEmpty(userAuthentication)) {
			throw new RestException("该用户没有认证信息");
		}
		userAauthenticationDao.save(shopVo.userConvertPo(shopVo, userAuthentication));
	}

	/**
	 * 
	 * @Title: reviewShop @Description:审核店铺 @param @param shopID @param @param
	 *         isApproved 设定文件 @return void 返回类型 @throws
	 */
	public void reviewShop(String shopID, Integer isApproved) {
		Shop shop = shopDao.findById(shopID);
		if (StringUtils.isEmpty(shop)) {
			throw new RestException("没有该店铺");
		}
		if (!StringUtils.isEmpty(shop) && isApproved.equals(SysConstants.YES)) {
			shop.setUpdateTime(DateUtil.getCurrentDate());
			shop.setStatusCode("shop_normal");
			shopDao.save(shop);
		}
		if (!StringUtils.isEmpty(shop) && isApproved.equals(SysConstants.NO)) {
			shop.setUpdateTime(DateUtil.getCurrentDate());
			shop.setStatusCode("shop_frozen");
			shopDao.save(shop);
		}
	}

	/**
	 * @Title: getAddonPkgs @Description: 获取增值服务 @param @param
	 *         pageInfo @param @return 设定文件 @return List<Map<String,Object>>
	 *         返回类型 @throws
	 */
	public List<Map<String, Object>> getAddonPkgs(Map<String, Object> param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Marketing> list = (List<Marketing>) marketDao.findAll();
		for (Marketing market : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketingName", market.getMarketingName());
			ShopsMarketing shopsMarketing=shopMarketDao.findMarketingHasUse((String)param.get("shopID"),market.getId());
			map.put("validityDay", "");
			map.put("marketingID", market.getId());
			map.put("status", 1);
			if(null!=shopsMarketing){
				map.put("validityDay", shopsMarketing.getValidityDay());
				map.put("status", shopsMarketing.getStatus());
			}
			map.put("marketingPrice", market.getMarketingPrice());
			map.put("marketingStatus", market.getMarketingStatus());
			result.add(map);
		}
		return result;
	}

	/**
	 * @Title: editAddonPkg @Description: 编辑增值服务的方法 @param @param
	 *         param @param @throws Exception 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void editAddonPkg(MarketVo marketVo) throws Exception {
		Marketing mark = marketDao.findById(marketVo.getMarketingID());
		if (StringUtils.isEmpty(mark)) {
			throw new RestException("没有该营销方式");
		}
		marketDao.save(marketVo.converPo(marketVo, mark));
	}

	/**
	 * @Title: editShopSetting @Description:店铺设置 @param @param
	 *         shop @param @param param @param @throws Exception 设定文件 @return
	 *         void 返回类型 @throws
	 */
	@Transactional
	public void editShopSetting(Map<String, String> param) throws Exception {
		List<Shop> shopName = shopDao.findoneByShopName(param.get("shopName"));
		if (shopName.size()==0){
			Shop shop = shopDao.findById(param.get("shopID"));
			shop.setShopName(param.get("shopName"));
			shop.setUpdateTime(DateUtil.getCurrentDate());
			shopDao.save(shop);
			String settingId = shop.getSettingId();
			ShopSetting setting = shopSetDao.findOne(settingId);
			setting.setLogoImgFileId(param.get("shopLogoImgID"));
			setting.setMobileImgFileId(param.get("shopMobileBgImgID"));
			setting.setPcImgFileId(param.get("shopPCBgImgID"));
			setting.setUpdateTime(DateUtil.getCurrentDate());
			shopSetDao.save(setting);
			return;
		}
		for (int i = 0; i < shopName.size(); i++) {
			if (null!=shopName&&shopName.get(i).getId().equals(param.get("shopID"))){
				Shop shop = shopDao.findById(param.get("shopID"));
				shop.setShopName(param.get("shopName"));
				shop.setUpdateTime(DateUtil.getCurrentDate());
				shopDao.save(shop);
				String settingId = shop.getSettingId();
				ShopSetting setting = shopSetDao.findOne(settingId);
				setting.setLogoImgFileId(param.get("shopLogoImgID"));
				setting.setMobileImgFileId(param.get("shopMobileBgImgID"));
				setting.setPcImgFileId(param.get("shopPCBgImgID"));
				setting.setUpdateTime(DateUtil.getCurrentDate());
				shopSetDao.save(setting);
				return;
			}
		}
			throw new RestException("店铺昵称已存在");
	}



	/**
	 * @Title: queryList @Description: 店铺banner搜索 @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */
	public Map<String, Object> getShopInfo(String shopID) {
		// 返回的Map<String, Object> 结果集
		Map<String, Object> result = new HashMap<String, Object>();
		Shop shop = null;
		List<Banner> list = null;
		if (StringUtils.isEmpty(shopID)) {
			// 搜索官方店铺
			shop = shopDao.findShopIsOfficial();
			// 搜索官方店铺的banner
			list = bannerDao.findBannerByShopId(shop.getId());
		} else {
			// 搜索普通店铺
			shop = shopDao.findById(shopID);
			// 搜索普通用户的banner
			list = bannerDao.findBannerByShopId(shopID);
		}
		// 是否有该店铺
		if (StringUtils.isEmpty(shop)) {
			throw new RestException("没有该店铺");
		}
		result.put("shopID", shop.getId());
		result.put("shopName", shop.getShopName());
		result.put("shopAddress", shop.getShopAddress());
		result.put("shopLevel", shop.getShopLevel());
		result.put("shopIsFrozen", shop.getShopIsFrozen());
		if (!StringUtils.isEmpty(shop.getSettingId())) {
			ShopSetting setting = shopSetDao.findOne(shop.getSettingId());
			String shopLogoImgUrl = fileManageDao.findUrlById(setting.getLogoImgFileId());
			result.put("shopLogoImgUrl", FilePathUtils.fileUrl(shopLogoImgUrl));
			String shopPCImgUrl = fileManageDao.findUrlById(setting.getPcImgFileId());
			result.put("shopPCImgUrl", FilePathUtils.fileUrl(shopPCImgUrl));
			String shopMobileImgUrl = fileManageDao.findUrlById(setting.getMobileImgFileId());
			result.put("shopMobileImgUrl", FilePathUtils.fileUrl(shopMobileImgUrl));
		} else {
			result.put("shopLogoImgUrl", null);
			result.put("shopPCImgUrl", null);
			result.put("shopMobileImgUrl", null);
		}
		// 返回的店铺banner数组
		List<Map<String, Object>> bannerList = new ArrayList<Map<String, Object>>();
		for (Banner banner : list) {
			Map<String, Object> bannerMap = new HashMap<String, Object>();
			bannerMap.put("bannerID", banner.getId());
			bannerMap.put("bannerImgID", banner.getImageFileId());
			String bannerImgUrl = fileManageDao.findUrlById(banner.getImageFileId());
			bannerMap.put("bannerImgUrl", FilePathUtils.fileUrl(bannerImgUrl));
			bannerMap.put("bannerClickUrl", banner.getLinkHref());
			bannerMap.put("bannerSort", banner.getBannerSort());
			bannerList.add(bannerMap);
		}
		result.put("bannerInfos", bannerList);
		return result;
	}

	public Date getStartTime(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, num);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public Date getEndTime(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, num);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		return calendar.getTime();
	}

	/**
	 * @Title: getSaleroom @Description: 得到店铺的销售额 @param @return 设定文件 @return
	 *         Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getSaleroom(String userId) {
		// 返回的Map<String, Object> 结果集
		Map<String, Object> result = new HashMap<String, Object>();
		// 第一季度的金额
		BigDecimal firstCountSaleroom = countSaleroom(userId, getStartTime(0), getEndTime(2));
		BigDecimal sencondCountSaleroom = countSaleroom(userId, getStartTime(3), getEndTime(5));
		BigDecimal thirdCountSaleroom = countSaleroom(userId, getStartTime(6), getEndTime(8));
		BigDecimal fourCountSaleroom = countSaleroom(userId, getStartTime(9), getEndTime(11));
		BigDecimal totalCountSaleroom = firstCountSaleroom.add(sencondCountSaleroom).add(thirdCountSaleroom)
				.add(fourCountSaleroom);
		result.put("firstCountSaleroom", firstCountSaleroom);
		result.put("sencondCountSaleroom", sencondCountSaleroom);
		result.put("thirdCountSaleroom", thirdCountSaleroom);
		result.put("fourCountSaleroom", fourCountSaleroom);
		result.put("totalCountSaleroom", totalCountSaleroom);
		return result;
	}

	/**
	 * @Title: getShopSetting @Description: 获取店铺设置 @param @param
	 *         userId @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getShopSetting(String shopId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Shop shop = shopDao.findById(shopId);
		result.put("shopID", shopId);
		result.put("shopName", shop.getShopName());
		ShopSetting setting = shopSetDao.findOne(shop.getSettingId());
		List<Map<String,Object>> imgList=new ArrayList<Map<String, Object>>();
		Map<String,Object> logoImg=new HashMap<String,Object>();
		logoImg.put("shopLogoImgID", setting.getLogoImgFileId());
		logoImg.put("shopLogoImgUrl", FilePathUtils.fileUrl(fileManageDao.findUrlById(setting.getLogoImgFileId())));
		imgList.add(logoImg);
		Map<String,Object> PCBgImg=new HashMap<String,Object>();
		PCBgImg.put("shopPCBgImgID", setting.getPcImgFileId());
		PCBgImg.put("shopPCBgImgUrl", FilePathUtils.fileUrl(fileManageDao.findUrlById(setting.getPcImgFileId())));
		imgList.add(PCBgImg);
		Map<String,Object> MobileBgImg=new HashMap<String,Object>();
		MobileBgImg.put("shopMobileBgImgID", setting.getMobileImgFileId());
		MobileBgImg.put("shopMobileBgImgUrl",
				FilePathUtils.fileUrl(fileManageDao.findUrlById(setting.getMobileImgFileId())));
		imgList.add(MobileBgImg);
		result.put("imgs", imgList);
		return result;
	}

	/**
	 * @Title: countSaleroom @Description: 计算总金额 @param @param
	 *         userID @param @param startTime @param @param
	 *         endTime @param @return 设定文件 @return BigDecimal 返回类型 @throws
	 */
	public BigDecimal countSaleroom(String userID, Date startTime, Date endTime) {
		BigDecimal totalSales = new BigDecimal(0);
		List<UserAccountFlow> list = userAccountFlowDao.findByUserId(userID, startTime, endTime);
		for (UserAccountFlow userAccountFlow : list) {
			totalSales = totalSales.add(userAccountFlow.getAmt());
		}
		return totalSales;
	}

	/**
	 * @Title: checkShopMarketing @Description: 获取店铺活动 @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> checkShopMarketing(ShopQo qo) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 满减满送信息集
		List<FulfilRemit> fulfilRemits = fulifilRemitDao.findByShopId(qo.getShopID());
		if (fulfilRemits.size() > 0) {
			List<Map<String, Object>> fulfilRemitList = new ArrayList<Map<String, Object>>();
			for (FulfilRemit fulfilRemit : fulfilRemits) {
				Map<String, Object> fulfilRemitMap = new HashMap<String, Object>();
				fulfilRemitMap.put("fulfilAmt", fulfilRemit.getFulfilAmt());
				fulfilRemitMap.put("remitAmt", fulfilRemit.getRemitAmt());
				fulfilRemitMap.put("giftGoodsID", fulfilRemit.getGiftGoodsId());
				Goods goods = goodsDao.findById(fulfilRemit.getGiftGoodsId());
				fulfilRemitMap.put("giftGoodsName", goods.getGoodsName());
				List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(goods.getId());
				fulfilRemitMap.put("giftGoodsImgUrl", null);
				if (goodsPicList.size() > 0) {
					String path = fileManageDao.findUrlById(goodsPicList.get(0).getMinImgId());
					fulfilRemitMap.put("giftGoodsImgUrl", FilePathUtils.fileUrl(path));
				}
				fulfilRemitMap.put("hasMarketing", 1);
				fulfilRemitList.add(fulfilRemitMap);
			}
			result.put("fulfilInfo", fulfilRemitList);
		}
		// 限时折扣活动的信息集
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchDiscountTime(qo)));
		List<DiscountTime> discountTimes = discountTimeDao
				.findAll(DynamicSpecifications.bySearchFilter(filter.values(), DiscountTime.class));
		if (discountTimes.size() > 0) {
			List<Map<String, Object>> discountTimeList = new ArrayList<Map<String, Object>>();
			for (DiscountTime discountTime : discountTimes) {
				Map<String, Object> discountTimeMap = new HashMap<String, Object>();
				discountTimeMap.put("discountName", discountTime.getDiscountName());
				discountTimeMap.put("discountPrice", discountTime.getDiscountPrice());
				discountTimeMap.put("limitNum", discountTime.getLimitNum());
				discountTimeMap.put("stockNum", discountTime.getStockNum());
				discountTimeMap.put("startTime", discountTime.getStartTime());
				discountTimeMap.put("endTime", discountTime.getEndTime());
				discountTimeMap.put("hasMarketing", 1);
				discountTimeList.add(discountTimeMap);
			}
			result.put("discountInfo", discountTimeList);
		}
		// 套餐搭配活动的信息集
		List<ComboPatch> ComboPatchs = comboPatchDao.findByShopIds(qo.getShopID());
		if (ComboPatchs.size() > 0) {
			List<Map<String, Object>> comboPatchList = new ArrayList<Map<String, Object>>();
			for (ComboPatch comboPatch : ComboPatchs) {
				Map<String, Object> comboPatchMap = new HashMap<String, Object>();
				comboPatchMap.put("comboName", comboPatch.getComboName());
				comboPatchMap.put("comboDesc", comboPatch.getComboDescription());
				comboPatchMap.put("startTime", comboPatch.getStartTime());
				comboPatchMap.put("endTime", comboPatch.getEndTime());
				List<ComboGoods> comboGoods = comboGoodsDao.findByComboIds(comboPatch.getId());
				List<Map<String, Object>> comboGoodsList = new ArrayList<Map<String, Object>>();
				for (ComboGoods comboGood : comboGoods) {
					Map<String, Object> comboGoodMap = new HashMap<String, Object>();
					comboGoodMap.put("comboGoodsID", comboGood.getGoodsId());
					Goods goods = goodsDao.findById(comboGood.getGoodsId());
					comboGoodMap.put("comboGoodsName", goods.getGoodsName());
					List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(goods.getId());
					comboGoodMap.put("comboGoodsImgUrl", null);
					if (goodsPicList.size() > 0) {
						String path = fileManageDao.findUrlById(goodsPicList.get(0).getMinImgId());
						comboGoodMap.put("comboGoodsImgUrl", FilePathUtils.fileUrl(path));
					}
					comboGoodMap.put("isMainGoods", comboGood.getIsMain());
					comboGoodMap.put("comboPrice", comboGood.getDiscountPrice());
					comboGoodMap.put("comboSort", comboGood.getComboSort());
					comboGoodsList.add(comboGoodMap);
				}
				comboPatchMap.put("comboPatchInfo", comboGoodsList);
				comboPatchMap.put("hasMarketing", 1);
				comboPatchList.add(comboPatchMap);
			}
			result.put("comboInfo", comboPatchList);
		}
		// 团购活动信息集
		List<GroupBuy> groupBuys = groupBuyDao
				.findAll(DynamicSpecifications.bySearchFilter(filter.values(), GroupBuy.class));
		if (groupBuys.size() > 0) {
			List<Map<String, Object>> groupBuyList = new ArrayList<Map<String, Object>>();
			for (GroupBuy groupBuy : groupBuys) {
				Map<String, Object> groupBuyMap = new HashMap<String, Object>();
				groupBuyMap.put("groupName", groupBuy.getGroupName());
				groupBuyMap.put("groupPrice", groupBuy.getDiscountPrice());
				groupBuyMap.put("limitNum", groupBuy.getMaxNum());
				groupBuyMap.put("stockNum", groupBuy.getStockNum());
				groupBuyMap.put("groupNum", groupBuy.getGroupNum());
				groupBuyMap.put("groupInterval", groupBuy.getGroupTime());
				groupBuyMap.put("startTime", groupBuy.getStartTime());
				groupBuyMap.put("endTime", groupBuy.getEndTime());
				List<GroupDetail> groupDetails = detailDao.findByGroupIds(groupBuy.getId());
				List<Map<String, Object>> groupUserList = new ArrayList<Map<String, Object>>();
				for (GroupDetail groupDetail : groupDetails) {
					List<GroupUser> groupUsers = groupUserDao.findByGroupDetailIds(groupDetail.getId());
					for (GroupUser groupUser : groupUsers) {
						Map<String, Object> groupUserMap = new HashMap<String, Object>();
						groupUserMap.put("userID", groupUser.getUserId());
						groupUserMap.put("isGroupOwner", 0);
						if (groupDetail.getOwnerId().equals(groupUser.getUserId())) {
							groupUserMap.put("isGroupOwner", 1);
						}
						User user = userDao.findById(groupUser.getUserId());
						String path = fileManageDao.findUrlById(user.getImgFileId());
						groupUserMap.put("userImgUrl", FilePathUtils.fileUrl(path));
						groupUserMap.put("userName", user.getAliasName());
						groupUserMap.put("userLevel", user.getChannelLevel());
						groupUserMap.put("userTypeCode", user.getUserTypeCode());
						KeyMapping mapping = keyMappingDao.findByKeyCode(user.getUserTypeCode());
						groupUserMap.put("userTypeDesc", mapping.getValueDesc());
						groupUserList.add(groupUserMap);
					}
				}
				groupBuyMap.put("groupUsers", groupUserList);
				groupBuyMap.put("hasMarketing", 1);
				groupBuyList.add(groupBuyMap);
			}
			result.put("groupInfo", groupBuyList);
		}

		if (fulfilRemits.size() <= 0 && discountTimes.size() <= 0 && ComboPatchs.size() <= 0 && groupBuys.size() <= 0) {
			result.put("hasMarketing", 0);
		}

		return result;

	}
}
