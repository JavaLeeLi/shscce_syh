package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @Author DM
 * @version ：2017年8月17日下午5:32:46 实体类
 */
public interface GoodsRepository extends BaseRepository<Goods, String> {
	@Query("from Goods")
	Page<Goods> findGoodsList(Pageable page);

	// 通过商品ID查询商品
	@Query(value = "from Goods g where g.id=?1 and g.isDeleted=0")
	public Goods findById(String id);

	@Query(value="SELECT g.shop_id AS shopId, s.shop_name AS shopName,g.owner_id AS ownerId,fm.file_path AS ownerImgUrl,u.alias_name AS ownerName,	u.channel_level AS ownerLevel,	u.user_type_code AS ownerType,	g.id AS goodsId,	g.goods_kind_id AS categoryId,	g.goods_type_code AS goodsType,g.goods_name AS goodsName,	g.goods_description AS goodsDesc,g.goods_sn AS goodsSn,	g.status_code AS statusCode,g.stock_num AS goodsStockNum,	g.total_sales AS goodsSalesNum,g.goods_price AS goodsPrice,	g.ban_reason AS banReason,g.goods_bid_start AS goodsBidStart,	g.goods_min_bid AS goodsBidMin,g.express_fee AS expressFee,	g.is_recognized AS goodsIsRecognized,	g.valid_start_time AS validStartTime,	g.valid_end_time AS validEndTime,	g.create_time AS goodsCreateTime,	g.buy_goods_type AS buyGoodsType FROM tbl_search_history sh LEFT JOIN tbl_goods g ON g.id = sh.goods_id LEFT JOIN tbl_shop s ON s.id = g.shop_id LEFT JOIN tbl_user u ON g.owner_id = u.id LEFT JOIN tbl_file_manage fm ON fm.id = u.img_file_id LEFT JOIN tbl_goods_kinds gk ON gk.id = g.goods_kind_id WHERE	g.is_deleted = '0' AND s.shop_is_frozen = '0'AND sh.user_id =?1 ORDER BY sh.sourch_count DESC LIMIT 0,5",nativeQuery = true)
	public List<Object[]> getgoodInfo(String userId);

	@Query("select g from Goods g ,GoodsKind gk,Shop sh where gk.id = g.goodsKindId and sh.id = g.shopId")
	public List<Goods> findDynamicPre(Specification<Goods> spec);

	// 通过鉴评码查询店铺
	Goods findByOwnerIdAndRecognizedCode(String userID, String recognizeCode);

	@Query("SELECT g.id FROM Goods g WHERE g.goodsName like %?1% AND g.isDeleted=0")
	List<String> findBygoodsName(String goodsName);

	@Query(value = "FROM Goods g WHERE g.shopId=?1 AND g.goodsTypeCode='sale_type' AND g.isDeleted=0 AND g.statusCode='goods_grounding' AND g.stockNum > 0")
	List<Goods> findByShopId(String shopId);
	
}
