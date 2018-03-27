package com.visionet.syh_mall.repository;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.GoodsHierarchy;

/**
 * @ClassName: GoodsHierarchyRepository
 * @Description: 商品分销
 * @author chenghongzhan
 * @date 2017年11月20日 下午8:01:16
 *
 */
public interface GoodsHierarchyRepository extends BaseRepository<GoodsHierarchy, String> {

	@Query(value = "SELECT COUNT(0) FROM tbl_goods_hierarchy g  WHERE g.goodsid=?1", nativeQuery = true)
	Integer findBygoodsId(String goodsId);

}
