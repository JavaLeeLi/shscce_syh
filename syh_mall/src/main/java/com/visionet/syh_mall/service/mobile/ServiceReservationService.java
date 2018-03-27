package com.visionet.syh_mall.service.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.entity.service.ServiceReservation;
import com.visionet.syh_mall.entity.shop.Marketing;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.GoodsPicLinkRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.ServiceReservationRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.repository.syhservice.MarketingRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.vo.ServiceReservationVo;

/**
 * 服务预约service层
 * 
 * @author chenghongzhan
 * @version 2017年8月24日 上午9:30:38
 *
 */
@Service
public class ServiceReservationService extends BaseService {
	@Autowired
	private ServiceReservationRepository repositoryDao;
	@Autowired
	private KeyMappingRepository keyDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private GoodsPicLinkRepository goodspicDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private FileManageRepostory fileDao;
	@Autowired
	private MarketingRepository marketDao;

	/**
	 * @Title: getAddonPkgs @Description: 获取增值服务 @param @param
	 *         pageInfo @param @return 设定文件 @return List<Map<String,Object>>
	 *         返回类型 @throws
	 */
	public List<Marketing> getAddonPkgs() {
		List<Marketing> result = (List<Marketing>) marketDao.findAll();
		return result;
	}

	/**
	 * 
	 * @Title: searchReservation @Description: 服务预约的查询 @param @param
	 *         serviceType @param @param customerName @param @param
	 *         startTime @param @param endTime @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */
	public Map<String, Object> searchReservation(Map<String, Object> param, PageInfo pageInfo) throws Exception {
		// 返回前端的结果集
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchReservation(param)));
		Page<ServiceReservation> list = repositoryDao.findAll(
				DynamicSpecifications.bySearchFilter(filter.values(), ServiceReservation.class),
				buildPageRequest(pageInfo));
		// 结果集
		List<Map<String, Object>> reservationInfos = new ArrayList<Map<String, Object>>();
		for (ServiceReservation sr : list) {
			String kindsId = sr.getKindsId();
			String goodName = sr.getGoodName();
			String goodNum = sr.getGoodsNum();
			// 预约多个商品
			List<String> kindsIds = Arrays.asList(StringUtils.commaDelimitedListToStringArray(kindsId));
			List<String> goodNames = Arrays.asList(StringUtils.commaDelimitedListToStringArray(goodName));
			List<String> goodNums = Arrays.asList(StringUtils.commaDelimitedListToStringArray(goodNum));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ReservationID", sr.getId());
			map.put("CustomerName", sr.getReservationName());
			map.put("CustomerPhone", sr.getReservationPhone());
			map.put("CustomerAddr", sr.getCustomerAddress());
			map.put("serviceTypeCode", sr.getServiceTypeCode());
			map.put("serviceTypeDesc", keyDao.findByKeyCode(sr.getServiceTypeCode()).getValueDesc());
			map.put("ServiceContent", sr.getServiceContent());
			map.put("ServiceOnsite", sr.getServiceOnsite());
			map.put("ServiceRemark", sr.getReservationRemark());
			if (sr.getReservationType() != null) {
				if (sr.getReservationType() == 0) {
					map.put("reservationType", "业务办理");
				}
				if (sr.getReservationType() == 1) {
					map.put("reservationType", "业务咨询");
				}
			}
			map.put("SiteType", sr.getSiteType());
			map.put("ReservationTime", sr.getReservationTime());
			map.put("GoodsSpec", sr.getGoodsSpec());
			if (!sr.getServiceTypeCode().equals("service_recognize")) {
				map.put("GoodKind", sr.getKindsId());
			}
			List<Map<String, Object>> goodInfos = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < goodNames.size(); i++) {
				Map<String, Object> goodMap = new HashMap<String, Object>();
				goodMap.put("GoodKindName", kindsIds.get(i));
				goodMap.put("GoodName", goodNames.get(i));
				goodMap.put("GoodNum", goodNums.get(i));
				goodInfos.add(goodMap);
			}
			map.put("goodInfo", goodInfos);
			reservationInfos.add(map);
		}
		result.put("itemCount", list.getTotalElements());
		result.put("pageCount", list.getTotalPages());
		result.put("curPageIndex", pageInfo.getPageIndex());
		result.put("hasNext", list.hasNext());
		result.put("reservationInfos", reservationInfos);
		return result;
	}

	/**
	 * @Title: addReserveService @Description: 增加服务预约的方法 @param 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void addReserveService(ServiceReservationVo reservationVo, String currentUserId) {
		if (StringUtils.isEmpty(reservationVo.getReservationID())) {
			ServiceReservation reservation = new ServiceReservation();
			reservation.setCreateBy(currentUserId);
			repositoryDao.save(reservationVo.convertPo(reservationVo, reservation));
			return;
		}
		ServiceReservation reservation = repositoryDao.findById(reservationVo.getReservationID());
		if (StringUtils.isEmpty(reservation)) {
			throw new RestException("没有该服务预约");
		}
		reservation.setCreateBy(currentUserId);
		reservation.setUpdateTime(DateUtil.getCurrentDate());
		repositoryDao.save(reservationVo.convertPo(reservationVo, reservation));
	}

	/**
	 * @Title: inquiryRecognizeCode @Description: 鉴评码查询 @param @param
	 *         userID @param @param recognizeCode @param @return 设定文件 @return
	 *         Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> inquiryRecognizeCode(String userID, String recognizeCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		Goods goods = goodsDao.findByOwnerIdAndRecognizedCode(userID, recognizeCode);
		if (StringUtils.isEmpty(goods)) {
			throw new RestException("没有该鉴评记录");
		}
		result.put("ownerID", goods.getOwnerId());
		result.put("goodsID", goods.getId());
		result.put("goodsTypeCode", goods.getGoodsTypeCode());
		result.put("goodsName", goods.getGoodsName());
		result.put("goodsDesc", goods.getGoodsDescription());
		result.put("goodsSN", goods.getGoodsSn());
		result.put("goodsQualityCode", goods.getGoodsQualityCode());
		result.put("goodsQualityDesc", keyDao.findByKeyCode(goods.getGoodsQualityCode()) != null
				? keyDao.findByKeyCode(goods.getGoodsQualityCode()).getValueDesc() : null);
		result.put("goodsQualityScore", goods.getGoodsQualityScore());
		result.put("goodsIsRecognized", goods.getIsRecognized());
		result.put("shopID", goods.getShopId());
		// 店铺的
		Shop shop = shopDao.findById(goods.getShopId());
		result.put("shopName", shop.getShopName());

		User user = userDao.findById(goods.getOwnerId());
		result.put("ownerImgUrl", FilePathUtils.fileUrl(fileDao.findUrlById(user.getImgFileId())));
		result.put("ownerName", user.getAliasName());
		result.put("ownerLevel", user.getChannelLevel());
		result.put("ownerTypeCode", user.getUserTypeCode());

		KeyMapping key = keyDao.findByKeyCode(user.getUserTypeCode());
		result.put("ownerTypeDesc", key.getValueDesc());
		// 接收商品图片的集合
		List<Map<String, Object>> goodsPicLinkList = new ArrayList<Map<String, Object>>();
		// 找到该食品的图片链接集合
		List<GoodsPicLink> picList = goodspicDao.getGoodsPicList(goods.getId());
		for (GoodsPicLink picLink : picList) {
			Map<String, Object> goodsPicLinkMap = new HashMap<String, Object>();
			goodsPicLinkMap.put("maxImgID", picLink.getMaxImgId());
			String maxImgUrl = fileDao.findUrlById(picLink.getMaxImgId());
			goodsPicLinkMap.put("maxImgUrl", FilePathUtils.fileUrl(maxImgUrl));
			goodsPicLinkMap.put("midImgID", picLink.getMidImgId());
			String midImgUrl = fileDao.findUrlById(picLink.getMidImgId());
			goodsPicLinkMap.put("midImgUrl", FilePathUtils.fileUrl(midImgUrl));
			goodsPicLinkMap.put("minImgID", picLink.getMinImgId());
			String minImgUrl = fileDao.findUrlById(picLink.getMinImgId());
			goodsPicLinkMap.put("minImgUrl", FilePathUtils.fileUrl(minImgUrl));
			goodsPicLinkList.add(goodsPicLinkMap);
		}
		result.put("goodsImgs", goodsPicLinkList);
		result.put("goodsStockSum", goods.getStockNum());
		result.put("goodsRecognizeCode", goods.getRecognizedCode());
		return result;
	}

	/**
	 * @Title: getReservationDetail @Description: 得到预约服务的详情 @param @param
	 *         reservationID @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getReservationDetail(String reservationID) {
		Map<String, Object> result = new HashMap<String, Object>();
		ServiceReservation reservation = repositoryDao.findById(reservationID);
		if (StringUtils.isEmpty(reservation)) {
			throw new RestException("没有该服务");
		}
		result.put("reservationID", reservation.getId());
		result.put("customerName", reservation.getReservationName());
		result.put("customerPhone", reservation.getReservationPhone());
		result.put("customerAddr", reservation.getCustomerAddress());
		result.put("serviceTypeCode", reservation.getServiceTypeCode());
		result.put("serviceTypeDesc", keyDao.findByKeyCode(reservation.getServiceTypeCode()).getValueDesc());
		result.put("serviceContent", reservation.getServiceContent());
		result.put("serviceIsOnsite", reservation.getServiceOnsite());
		result.put("siteType", reservation.getSiteType());
		result.put("reservationType", reservation.getReservationType());
		result.put("reservationTime", reservation.getReservationTime());
		result.put("goodsSpec", reservation.getGoodsSpec());
		result.put("reservationRemark", reservation.getReservationRemark());
		result.put("reservationRemark", reservation.getReservationRemark());
		return result;
	}

}
