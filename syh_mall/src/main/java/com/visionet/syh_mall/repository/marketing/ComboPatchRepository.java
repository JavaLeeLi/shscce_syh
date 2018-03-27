package com.visionet.syh_mall.repository.marketing;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.shop.ComboPatch;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: ComboPatchRepository
 * @Description: 套餐搭配
 * @author chenghongzhan
 * @date 2017年10月13日 上午11:31:01
 *
 */
public interface ComboPatchRepository extends BaseRepository<ComboPatch, String> {

	@Query("SELECT c FROM ComboPatch c WHERE c.shopId=?1 AND c.isDeleted=0 AND c.startTime<now() AND c.endTime>now()")
	List<ComboPatch> findByShopIds(String shopId);

	@Query("FROM ComboPatch c WHERE c.id=?1 AND c.isDeleted=0")
	ComboPatch findById(String id);
	
	@Query("SELECT c FROM ComboPatch c,ComboGoods g WHERE c.id = g.comboId AND g.goodsId =?1 AND SYSDATE() BETWEEN c.startTime AND c.endTime AND c.isDeleted = 0 GROUP BY c.id")
	List<ComboPatch> findbyGoodsId(String goodsId);
}
