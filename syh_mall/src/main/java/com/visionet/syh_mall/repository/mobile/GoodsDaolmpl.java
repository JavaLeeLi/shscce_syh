package com.visionet.syh_mall.repository.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.goods.BidRecord;
import com.visionet.syh_mall.entity.goods.GoodsKind;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
import com.visionet.syh_mall.service.marketing.DiscountTimeService;
import com.visionet.syh_mall.vo.GoodsQo;
import com.visionet.syh_mall.vo.GoodsVo.GoodsInfos;

/**
 * @Author DM
 * @version ：2017年8月29日上午10:18:00 实体类
 */
@Repository
public class GoodsDaolmpl {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private FileManageRepostory fManageRepostory;
	@Autowired
	private KeyMappingRepository keyDao;
	@Autowired
	private DiscountTimeService discountTimeService;
	@Autowired
	private GoodsAuctionRepository goodsAuctionDao;
	@Autowired
	private GoodsPicLinkRepository goodsPicLinkDao;
	@Autowired
	private GoodsKindRepository gKindDao;
	public static String FromTable = "SELECT g.shop_id AS shopId,s.shop_name AS shopName,g.owner_id AS ownerId,fm.file_path AS ownerImgUrl,u.alias_name AS ownerName,u.channel_level AS ownerLevel,u.user_type_code AS ownerType,g.id AS goodsId,g.goods_kind_id as categoryId,g.goods_type_code AS goodsType,g.goods_name AS goodsName,g.goods_description AS goodsDesc,g.goods_sn AS goodsSn,g.status_code as statusCode,g.stock_num AS goodsStockNum,g.total_sales AS goodsSalesNum,g.goods_price AS goodsPrice, g.ban_reason as banReason,g.goods_bid_start as goodsBidStart,g.goods_min_bid as goodsBidMin,g.express_fee as expressFee,g.is_recognized AS goodsIsRecognized,g.valid_start_time AS validStartTime,g.valid_end_time AS validEndTime,g.create_time AS goodsCreateTime,g.buy_goods_type as buyGoodsType , g.goods_channel_rule as goodsChannelRule,et.free_for_express AS freeForExpress FROM tbl_goods g LEFT JOIN tbl_express_templet et ON g.express_templet_id =et.id LEFT JOIN tbl_shop s ON s.id = g.shop_id LEFT JOIN tbl_user u ON g.owner_id = u.id LEFT JOIN tbl_file_manage fm ON fm.id = u.img_file_id LEFT JOIN tbl_goods_kinds gk ON gk.id=g.goods_kind_id where g.is_deleted='0' and s.shop_is_frozen='0'  ";
	public static String FromTable1 = "SELECT g.shop_id AS shopId,s.shop_name AS shopName,g.owner_id AS ownerId,fm.file_path AS ownerImgUrl,u.alias_name AS ownerName,u.channel_level AS ownerLevel,u.user_type_code AS ownerType,g.id AS goodsId,g.goods_kind_id as categoryId,g.goods_type_code AS goodsType,g.goods_name AS goodsName,g.goods_description AS goodsDesc,g.goods_sn AS goodsSn,g.status_code as statusCode,g.stock_num AS goodsStockNum,og.goods_num AS goodsSalesNum,g.goods_price AS goodsPrice, g.ban_reason as banReason,g.goods_bid_start as goodsBidStart,g.goods_min_bid as goodsBidMin,g.express_fee as expressFee,g.is_recognized AS goodsIsRecognized,g.valid_start_time AS validStartTime,g.valid_end_time AS validEndTime,g.create_time AS goodsCreateTime,g.buy_goods_type as buyGoodsType,o.order_status_code as orderStatusCode FROM tbl_goods g LEFT JOIN tbl_shop s ON s.id = g.shop_id LEFT JOIN tbl_user u ON g.owner_id = u.id LEFT JOIN tbl_file_manage fm ON fm.id = u.img_file_id LEFT JOIN tbl_goods_kinds gk ON gk.id=g.goods_kind_id LEFT JOIN tbl_order_goods og ON og.goods_id=g.id LEFT JOIN tbl_order o on o.id=og.order_id where g.is_deleted='0' and o.order_status_code='order_completed' and s.shop_is_frozen='0'";
	public static String FromTable2 = "SELECT g.shop_id AS shopId,s.shop_name AS shopName,g.owner_id AS ownerId,fm.file_path AS ownerImgUrl,u.alias_name AS ownerName,u.channel_level AS ownerLevel,u.user_type_code AS ownerType,g.id AS goodsId,g.goods_kind_id as categoryId,g.goods_type_code AS goodsType,g.goods_name AS goodsName,g.goods_description AS goodsDesc,g.goods_sn AS goodsSn,g.status_code as statusCode,g.stock_num AS goodsStockNum,g.total_sales AS goodsSalesNum,g.goods_price AS goodsPrice, g.ban_reason as banReason,g.goods_bid_start as goodsBidStart,g.goods_min_bid as goodsBidMin,g.express_fee as expressFee,g.is_recognized AS goodsIsRecognized,g.valid_start_time AS validStartTime,g.valid_end_time AS validEndTime,g.create_time AS goodsCreateTime,g.buy_goods_type as buyGoodsType FROM tbl_search_history sh LEFT JOIN tbl_goods g ON sh.goods_id = g.id LEFT JOIN tbl_shop s ON s.id = g.shop_id LEFT JOIN tbl_user u ON g.owner_id = u.id LEFT JOIN tbl_file_manage fm ON fm.id = u.img_file_id LEFT JOIN tbl_goods_kinds gk ON gk.id=g.goods_kind_id where g.is_deleted='0' and s.shop_is_frozen='0' AND g.status_code='goods_grounding' ";
	public static String FromTable3 = "SELECT g.shop_id AS shopId,s.shop_name AS shopName,g.owner_id AS ownerId,fm.file_path AS ownerImgUrl,u.alias_name AS ownerName,u.channel_level AS ownerLevel,u.user_type_code AS ownerType,g.id AS goodsId,g.goods_kind_id as categoryId,g.goods_type_code AS goodsType,g.goods_name AS goodsName,g.goods_description AS goodsDesc,g.goods_sn AS goodsSn,g.status_code as statusCode,g.stock_num AS goodsStockNum,g.total_sales AS goodsSalesNum,g.goods_price AS goodsPrice, g.ban_reason as banReason,g.goods_bid_start as goodsBidStart,g.goods_min_bid as goodsBidMin,g.express_fee as expressFee,g.is_recognized AS goodsIsRecognized,g.valid_start_time AS validStartTime,g.valid_end_time AS validEndTime,g.create_time AS goodsCreateTime,g.buy_goods_type as buyGoodsType , g.goods_channel_rule as goodsChannelRule,et.free_for_express AS freeForExpress FROM tbl_goods g LEFT JOIN tbl_express_templet et ON g.express_templet_id =et.id LEFT JOIN tbl_shop s ON s.id = g.shop_id LEFT JOIN tbl_user u ON g.owner_id = u.id LEFT JOIN tbl_file_manage fm ON fm.id = u.img_file_id LEFT JOIN tbl_goods_kinds gk ON gk.id=g.goods_kind_id where g.is_deleted='0' and s.shop_is_frozen='0' and g.status_code='goods_undercarriage' ";

	@SuppressWarnings("unchecked")
	public Page<GoodsInfos> queryRecommendGoods(GoodsQo qo, PageRequest pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(FromTable2);
		if (Validator.isNotNull(qo.getUserId())) {// 搜索目标店铺ID
			query.append(" and sh.user_id = :userId");
			map.put("userId", qo.getUserId());
		}
		query.append(" ORDER BY sh.sourch_count DESC");
		String sql = query.toString();
		EntityManager em = null;
		List<Object[]> lists1 = null;
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		try {
			em = entityManager.getEntityManagerFactory().createEntityManager();
			Query queryString = em.createNativeQuery(sql);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				queryString.setParameter(entry.getKey(), entry.getValue());
			}
			lists1 = queryString.getResultList();
			queryString.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryString.setFirstResult(qo.getCurrentPageNumber());
			queryString.setMaxResults(qo.getItemCount());
			List<Object> rows = queryString.getResultList();
			for (Object obj : rows) {
				Map<String, Object> row = (Map<String, Object>) obj;
				lists.add(row);
			}

		} catch (RuntimeException e) {
			throw e instanceof IllegalArgumentException ? e : new IllegalArgumentException(e);
		} finally {
			if (em != null) {
				EntityManagerFactoryUtils.closeEntityManager(em);
			}
		}
		return resultToVoPage(lists, pr, lists1);
	}

	@SuppressWarnings("unchecked")
	public Page<GoodsInfos> searchSoldOutGoods(GoodsQo qo, PageRequest pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(FromTable3);
		if (Validator.isNotNull(qo.getUserId())) {// 搜索目标店铺ID
			query.append(" and g.owner_id = :userId");
			map.put("userId", qo.getUserId());
		}
		query.append(" ORDER BY g.update_time DESC");
		String sql = query.toString();
		EntityManager em = null;
		List<Object[]> lists1 = null;
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		try {
			em = entityManager.getEntityManagerFactory().createEntityManager();
			Query queryString = em.createNativeQuery(sql);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				queryString.setParameter(entry.getKey(), entry.getValue());
			}
			lists1 = queryString.getResultList();
			queryString.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryString.setFirstResult(qo.getCurrentPageNumber());
			queryString.setMaxResults(qo.getItemCount());
			List<Object> rows = queryString.getResultList();
			for (Object obj : rows) {
				Map<String, Object> row = (Map<String, Object>) obj;
				lists.add(row);
			}

		} catch (RuntimeException e) {
			throw e instanceof IllegalArgumentException ? e : new IllegalArgumentException(e);
		} finally {
			if (em != null) {
				EntityManagerFactoryUtils.closeEntityManager(em);
			}
		}
		return resultToVoPage(lists, pr, lists1);
	}

	/**
	 * 分页查询
	 * 
	 * @param //tQo
	 * @param //pr
	 * @param //isBack
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page<GoodsInfos> queryCondition(GoodsQo qo, PageRequest pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(FromTable);
		if (Validator.isNotNull(qo.getIsGeneralized())){
			if (qo.getIsGeneralized().equals("0")) {// 推广中的
				query.append(" and g.goods_channel_rule is not null ");

			}else {//未推广的
				query.append(" and g.goods_channel_rule is null ");
			}
		}
		if (Validator.isNotNull(qo.getInShopId())) {// 搜索目标店铺ID
			query.append(" and g.shop_id = :shopId");
			map.put("shopId", qo.getInShopId());
		}
		if (Validator.isNotNull(qo.getStartTime())) { // 开始时间检索
			query.append(" and g.create_time >= :startTime");
			map.put("startTime", qo.getStartTime());
		}
		if (Validator.isNotNull(qo.getStartTime())) { // 结束时间检索
			query.append(" and g.create_time <= :endTime");
			map.put("endTime", qo.getEndTime());
		}
		if (Validator.isNotNull(qo.getAuctionStatus())) {// 拍卖检索
			if (qo.getAuctionStatus() == 0) {// 未开始
				query.append(" and g.valid_start_time > :dateTime");
				map.put("dateTime", DateUtil.getCurrentDate());
			}
			if (qo.getAuctionStatus() == 1) {// 开始
				query.append(" and :dateTime BETWEEN g.valid_start_time AND g.valid_end_time");
				map.put("dateTime", DateUtil.getCurrentDate());
			}
		}
		if (Validator.isNotNull(qo.getOwnerId())) {// 商品所属用户ID
			query.append(" and g.owner_id = :ownerId");
			map.put("ownerId", qo.getOwnerId());
		}
		if (Validator.isNotNull(qo.getCategoryId())) {// 商品类别ID
			query.append(" and gk.parent_kind_id = :categoryId");
			map.put("categoryId", qo.getCategoryId());
		}
		if (Validator.isNotNull(qo.getChildCategoryId())) {// 商品二级分类ID
			query.append(" and g.goods_kind_id = :childCategoryId");
			map.put("childCategoryId", qo.getChildCategoryId());
		}
		if (Validator.isNotNull(qo.getGoodsTypeCode())) {// 商品类型
			if (!"sale_type".equals(qo.getGoodsTypeCode())) {
				query.append(" and g.valid_end_time > :sysTime");
				map.put("sysTime", DateUtil.getCurrentDate());
			}
			query.append(" and g.goods_type_code = :goodsType");
			map.put("goodsType", qo.getGoodsTypeCode());
		}
		if (!Validator.isNotNull(qo.getInShopId()) && qo.getIsbusiness() != 1
				&& !Validator.isNotNull(qo.getGoodsTypeCode()) && !Validator.isNotNull(qo.getStartTime())
				&& !Validator.isNotNull(qo.getEndTime())) {// 没有商品分类时，检索未过期商品
			query.append(" and (g.valid_end_time > :sysTime or g.goods_type_code = :typeCode)");
			map.put("sysTime", DateUtil.getCurrentDate());
			map.put("typeCode","sale_type");
		}
		if (Validator.isNotNull(qo.getStatusCode())) {// 商品状态code
			query.append(" and g.status_code = :statusCode");
			map.put("statusCode", qo.getStatusCode());
		} else {
			query.append(" and g.status_code= :statusCode");
			map.put("statusCode", "goods_grounding");
		}
		if (Validator.isNotNull(qo.getShopName())) {// 店铺名称
			query.append(" and s.shop_name like :shopName");
			map.put("shopName", "%" + qo.getShopName() + "%");
		}
		if (Validator.isNotNull(qo.getGoodsName())) {// 商品名称
			query.append(" and g.goods_name like :goodsName");
			map.put("goodsName", "%" + qo.getGoodsName() + "%");
		}
		if (Validator.isNotNull(qo.getGoodsSn())) {// 商品编码
			query.append(" and g.goods_sn like :goodsSn");
			map.put("goodsSn", "%" + qo.getGoodsSn() + "%");
		}
		if (Validator.isNotNull(qo.getIsRecognized())) {// 商品是否鉴评封装
			query.append(" and g.is_recognized = :goodsIsRecognized");
			map.put("goodsIsRecognized", qo.getIsRecognized());
		}
		if (Validator.isNotNull(qo.getKeywords())) {// 关键词模糊查询
			query.append(" and ( g.goods_name like :goodsName");
			query.append(" or g.goods_sn like :goodsSn");
			query.append(" or g.goods_description like :goodsDesc");
			query.append(" or s.shop_name like :shopName)");
			map.put("goodsName", "%" + qo.getKeywords() + "%");
			map.put("goodsSn", "%" + qo.getKeywords() + "%");
			map.put("goodsDesc", "%" + qo.getKeywords() + "%");
			map.put("shopName", "%" + qo.getKeywords() + "%");
		}
		if (0 == qo.getSortType()) {// 搜索结果排序类型(0=更新时间倒序1=销量倒序2=价格倒序3=更新时间顺序4=销量顺序5=价格顺序)
			query.append(" order by g.create_time desc");
		} else if (1 == qo.getSortType()) {
			query.append(" order by g.total_sales desc");
		} else if (2 == qo.getSortType()) {
			query.append(" order by g.goods_price desc");
		} else if (3 == qo.getSortType()) {
			query.append(" order by g.create_time asc");
		} else if (4 == qo.getSortType()) {
			query.append(" order by g.total_sales asc");
		} else if (5 == qo.getSortType()) {
			query.append(" order by g.goods_price asc");
		}
		String sql = query.toString();
		EntityManager em = null;
		List<Object[]> lists1 = null;
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		try {
			em = entityManager.getEntityManagerFactory().createEntityManager();
			Query queryString = em.createNativeQuery(sql);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				queryString.setParameter(entry.getKey(), entry.getValue());
			}
			lists1 = queryString.getResultList();
			queryString.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryString.setFirstResult(qo.getCurrentPageNumber());
			queryString.setMaxResults(qo.getItemCount());
			List<Object> rows = queryString.getResultList();
			for (Object obj : rows) {
				Map<String, Object> row = (Map<String, Object>) obj;
				lists.add(row);
			}

		} catch (RuntimeException e) {
			throw e instanceof IllegalArgumentException ? e : new IllegalArgumentException(e);
		} finally {
			if (em != null) {
				EntityManagerFactoryUtils.closeEntityManager(em);
			}
		}
		return resultToVoPage(lists, pr, lists1);
	}

	/**
	 * 分页查询
	 * 
	 * @param //tQo
	 * @param //pr
	 * @param //isBack
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page<GoodsInfos> queryConditionCotent(GoodsQo qo, PageRequest pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(FromTable1);
		if (Validator.isNotNull(qo.getInShopId())) {// 搜索目标店铺ID
			query.append(" and g.shop_id = :shopId");
			map.put("shopId", qo.getInShopId());
		}
		if (Validator.isNotNull(qo.getOwnerId())) {// 商品所属用户ID
			query.append(" and g.owner_id = :ownerId");
			map.put("ownerId", qo.getOwnerId());
		}
		if (Validator.isNotNull(qo.getCategoryId())) {// 商品类别ID
			query.append(" and gk.parent_kind_id = :categoryId");
			map.put("categoryId", qo.getCategoryId());
		}
		if (Validator.isNotNull(qo.getChildCategoryId())) {// 商品二级分类ID
			query.append(" and g.goods_kind_id = :categoryId");
			map.put("categoryId", qo.getChildCategoryId());
		}
		if (Validator.isNotNull(qo.getGoodsTypeCode())) {// 商品类型
			query.append(" and g.goods_type_code = :goodsType");
			map.put("goodsType", qo.getGoodsTypeCode());
		}
		if (Validator.isNotNull(qo.getStatusCode())) {// 商品状态code
			query.append(" and g.status_code = :statusCode");
			map.put("statusCode", qo.getStatusCode());
		}
		if (Validator.isNotNull(qo.getShopName())) {// 店铺名称
			query.append(" and s.shop_name like :shopName");
			map.put("shopName", "%" + qo.getShopName() + "%");
		}
		if (Validator.isNotNull(qo.getGoodsName())) {// 商品名称
			query.append(" and g.goods_name like :goodsName");
			map.put("goodsName", "%" + qo.getGoodsName() + "%");
		}
		if (Validator.isNotNull(qo.getGoodsSn())) {// 商品编码
			query.append(" and g.goods_sn like :goodsSn");
			map.put("goodsSn", "%" + qo.getGoodsSn() + "%");
		}
		if (Validator.isNotNull(qo.getIsRecognized())) {// 商品是否鉴评封装
			query.append(" and g.is_recognized = :goodsIsRecognized");
			map.put("goodsIsRecognized", qo.getIsRecognized());
		}
		if (Validator.isNotNull(qo.getKeywords())) {// 关键词模糊查询
			query.append(" and ( g.goods_name like :goodsName");
			query.append(" or g.goods_sn like :goodsSn");
			query.append(" or g.goods_description like :goodsDesc");
			query.append(" or s.shop_name like :shopName)");
			map.put("goodsName", "%" + qo.getKeywords() + "%");
			map.put("goodsSn", "%" + qo.getKeywords() + "%");
			map.put("goodsDesc", "%" + qo.getKeywords() + "%");
			map.put("shopName", "%" + qo.getKeywords() + "%");
		}
		if (0 == qo.getSortType()) {// 搜索结果排序类型(0=更新时间倒序1=销量倒序2=价格倒序3=更新时间顺序4=销量顺序5=价格顺序)
			query.append(" order by g.create_time desc");
		} else if (1 == qo.getSortType()) {
			query.append(" order by g.total_sales desc");
		} else if (2 == qo.getSortType()) {
			query.append(" order by g.goods_price desc");
		} else if (3 == qo.getSortType()) {
			query.append(" order by g.create_time asc");
		} else if (4 == qo.getSortType()) {
			query.append(" order by g.total_sales asc");
		} else if (5 == qo.getSortType()) {
			query.append(" order by g.goods_price asc");
		}
		String sql = query.toString();
		EntityManager em = null;
		List<Object[]> lists1 = null;
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		try {
			em = entityManager.getEntityManagerFactory().createEntityManager();
			Query queryString = em.createNativeQuery(sql);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				queryString.setParameter(entry.getKey(), entry.getValue());
			}
			lists1 = queryString.getResultList();
			queryString.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryString.setFirstResult(qo.getCurrentPageNumber());
			queryString.setMaxResults(qo.getItemCount());
			List<Object> rows = queryString.getResultList();
			for (Object obj : rows) {
				Map<String, Object> row = (Map<String, Object>) obj;
				lists.add(row);
			}

		} catch (RuntimeException e) {
			throw e instanceof IllegalArgumentException ? e : new IllegalArgumentException(e);
		} finally {
			if (em != null) {
				EntityManagerFactoryUtils.closeEntityManager(em);
			}
		}
		return resultToVoPage(lists, pr, lists1);
	}

	private Page<GoodsInfos> resultToVoPage(List<Map<String, Object>> lists, PageRequest pr, List<Object[]> lists1) {
		if (lists != null) {
			// List<GoodsVo> voList = new ArrayList<GoodsVo>();
			List<GoodsInfos> voList = new ArrayList<GoodsInfos>();
			for (Map<String, Object> mp : lists) {
				// GoodsVo tVo = new GoodsVo();
				GoodsInfos tVo = new GoodsInfos();
				if (Validator.isNotNull(mp.get("shopId"))) {
					tVo.setShopId(mp.get("shopId").toString());
				}
				if (Validator.isNotNull(mp.get("shopName"))) {
					tVo.setShopName(mp.get("shopName").toString());
				}
				if (Validator.isNotNull(mp.get("ownerId"))) {
					tVo.setOwnerId(mp.get("ownerId").toString());
				}
				if (Validator.isNotNull(mp.get("ownerImgUrl"))) {
					tVo.setOwnerImgUrl(FilePathUtils.fileUrl(mp.get("ownerImgUrl").toString()));
				}
				if (Validator.isNotNull(mp.get("ownerName"))) {
					tVo.setOwnerName(mp.get("ownerName").toString());
				}
				if (Validator.isNotNull(mp.get("ownerLevel"))) {
					tVo.setOwnerLevel((Integer) mp.get("ownerLevel"));
				}
				if (Validator.isNotNull(mp.get("ownerType"))) {
					tVo.setOwnerTypeCode(mp.get("ownerType").toString());
					KeyMapping key = keyDao.findByKeyCode(mp.get("ownerType").toString());
					tVo.setOwnerTypeDesc(key.getValueDesc());
				}
				if (Validator.isNotNull(mp.get("goodsId"))) {
					if (!"".equals((String) mp.get("goodsChannelRule"))||!StringUtils.isEmpty(mp.get("goodsChannelRule"))) {
						tVo.setGoodsChannelRule((String) mp.get("goodsChannelRule"));
					}
					tVo.setGoodsId(mp.get("goodsId").toString());
					List<FileManage> imgList = fManageRepostory.findPicByGoodsId(mp.get("goodsId").toString()); // 商品图片集合
					List<GoodsPicLink> picList = goodsPicLinkDao.getGoodsPicList(mp.get("goodsId").toString());
					List<Map<String,String>> list = new ArrayList<Map<String,String>>();
					for (GoodsPicLink goodsPicLink : picList) {
						Map<String,String> picMap = new HashMap<String,String>();
						String maxImgUrl = fManageRepostory.findUrlById(goodsPicLink.getMaxImgId());
						String midImgUrl = fManageRepostory.findUrlById(goodsPicLink.getMidImgId());
						String minImgUrl = fManageRepostory.findUrlById(goodsPicLink.getMinImgId());
						picMap.put("maxImgUrl", FilePathUtils.fileUrl(maxImgUrl));
						picMap.put("midImgUrl", FilePathUtils.fileUrl(midImgUrl));
						picMap.put("minImgUrl", FilePathUtils.fileUrl(minImgUrl));
						list.add(picMap);
					}
					tVo.setGoodsImgUrls(imgList);
					tVo.setGoodsPicUrl(list);
				}
				if (Validator.isNotNull(mp.get("goodsType"))) {
					tVo.setGoodsTypeCode(mp.get("goodsType").toString());
					KeyMapping key = keyDao.findByKeyCode(mp.get("goodsType").toString());
					tVo.setGoodsTypeDesc(key.getValueDesc());
				}
				if (Validator.isNotNull(mp.get("goodsName"))) {
					tVo.setGoodsName(mp.get("goodsName").toString());
				}
				if (Validator.isNotNull(mp.get("banReason"))) {
					tVo.setBanReason(mp.get("banReason").toString());
				}
				if (Validator.isNotNull(mp.get("goodsDesc"))) {
					tVo.setGoodsDescription(mp.get("goodsDesc").toString());
				}
				if (Validator.isNotNull(mp.get("buyGoodsType"))) {
					tVo.setBuyGoodsType(mp.get("buyGoodsType").toString());
				}
				if (Validator.isNotNull(mp.get("goodsSn"))) {
					tVo.setGoodsSn(mp.get("goodsSn").toString());
				}
				if (Validator.isNotNull(mp.get("statusCode"))) {
					tVo.setStatusCode(mp.get("statusCode").toString());
					KeyMapping key = keyDao.findByKeyCode(mp.get("statusCode").toString());
					tVo.setStatusDesc(key.getValueDesc());
				}
				if (Validator.isNotNull(mp.get("categoryId"))) {
					tVo.setChildCategoryId(mp.get("categoryId").toString());
					GoodsKind goodsKind = gKindDao.findAllById(mp.get("categoryId").toString());
					tVo.setChildCategoryName(goodsKind.getKindName());
					GoodsKind parentKind = gKindDao.findAllById(goodsKind.getParentKindId());
					if (null != parentKind && null != parentKind.getId()) {
						tVo.setCategoryId(parentKind.getId());
						tVo.setCategoryName(parentKind.getKindName());
					}
				}
				if (Validator.isNotNull(mp.get("freeForExpress"))) {
					tVo.setFreeForExpress((Integer) mp.get("freeForExpress"));
				}

				if (Validator.isNotNull(mp.get("goodsStockNum"))) {
					tVo.setGoodsStockNum((Integer) mp.get("goodsStockNum"));
				}
				if (Validator.isNotNull(mp.get("goodsSalesNum"))) {
					tVo.setGoodsSalesNum((Integer) mp.get("goodsSalesNum"));
				}
				if (Validator.isNotNull(mp.get("goodsPrice"))) {
					tVo.setGoodsPrice((BigDecimal) mp.get("goodsPrice"));
					DiscountTime discountTime = discountTimeService.takeDiscountTime(null, (String) mp.get("goodsId"),
							null);// 限时活动
					if (!StringUtils.isEmpty(discountTime)) {
						tVo.setRealPrice(discountTime.getDiscountPrice());
					} else {
						tVo.setRealPrice((BigDecimal) mp.get("goodsPrice"));
					}
				}
				if (Validator.isNotNull(mp.get("goodsBidStart"))) {
					tVo.setGoodsBidStart((BigDecimal) mp.get("goodsBidStart"));
					BidRecord record = goodsAuctionDao.findByGoodsId(mp.get("goodsId").toString());
					tVo.setGoodsBidMax((BigDecimal) mp.get("goodsBidStart"));
					if (Validator.isNotNull(record)) {
						tVo.setGoodsBidMax(record.getLastBidPrice());
					}
				}
				if (Validator.isNotNull(mp.get("goodsBidMin"))) {
					tVo.setGoodsBidMin((BigDecimal) mp.get("goodsBidMin"));
				}
				if (Validator.isNotNull(mp.get("expressFee"))) {

					tVo.setExpressFee((BigDecimal) mp.get("expressFee"));
				}
				if (Validator.isNotNull(mp.get("isRecognized"))) {
					tVo.setIsRecognized((Integer) mp.get("isRecognized"));
				}
				if (Validator.isNotNull(mp.get("validStartTime"))) {
					tVo.setValidStartTime((Date) mp.get("validStartTime"));
				}
				if (Validator.isNotNull(mp.get("validEndTime"))) {
					tVo.setGoodsCloseTime((Date) mp.get("validEndTime"));
				}
				if (Validator.isNotNull(mp.get("goodsCreateTime"))) {
					tVo.setGoodsCreateTime((Date) mp.get("goodsCreateTime"));
				}
				voList.add(tVo);

			}
			return new PageImpl<GoodsInfos>(voList, pr, lists1.size());
		} else {

			return null;
		}
	}
}
