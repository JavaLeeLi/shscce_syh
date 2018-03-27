package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.visionet.syh_mall.entity.goods.HomeGoods;

/**
 * @Author DM
 * @version ：2017年8月21日下午3:05:24 实体类
 */
public interface HomeGoodsRepository
		extends PagingAndSortingRepository<HomeGoods, String>, JpaSpecificationExecutor<HomeGoods> {
	// @Query("from HomeGoods")
	// List<HomeGoods> findHomeGoodsList();
	@Query(value = "select h.id as homeGoodsId,h.item_sort as homeGoodsSort,h.shop_id as shopId,h.goods_id as goodsId,s.shop_name as shopName,g.owner_id as ownerId,g.goods_type_code as goodsType,g.goods_name as goodsName,g.goods_description as goodsDesc,g.goods_sn as goodsSn,g.goods_price as goodsPrice,g.is_recognized as goodsIsRecognized,g.valid_start_time as validStartTime,g.valid_end_time as validEndTime,fm.file_path AS ownerImgUrl,u.alias_name as ownerName,u.channel_level as ownerLevel,u.user_type_code as ownerType,g.goods_bid_start as goodsBidStart,g.goods_min_bid as goodsBidMin from tbl_home_goods h LEFT JOIN tbl_shop s ON s.id=h.shop_id LEFT JOIN tbl_goods g ON g.id=h.goods_id LEFT JOIN tbl_user u ON g.owner_id=u.id LEFT JOIN tbl_file_manage fm ON fm.id = u.img_file_id where h.is_deleted = 0 AND s.shop_is_frozen = '0' AND g.is_deleted = '0' AND g.status_code='goods_grounding' AND g.goods_type_code='sale_type' OR (g.goods_type_code='auction_type' AND g.valid_end_time > NOW()) ORDER BY h.item_sort ASC", nativeQuery = true)
	public List<Object[]> findGoodsList();

	@Query("FROM HomeGoods h WHERE h.goodsId=?1 AND h.isDeleted=0")
	HomeGoods findByGoodsId(String goodsId);

	@Query(value="FROM HomeGoods h WHERE h.goodsId=?1 AND h.isDeleted=0")
	HomeGoods findByGoodsIdAndIsDelete(String GoodsId);
}
