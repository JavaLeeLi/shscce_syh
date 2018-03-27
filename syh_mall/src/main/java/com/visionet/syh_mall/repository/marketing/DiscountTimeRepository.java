package com.visionet.syh_mall.repository.marketing;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.marketing.DiscountTime;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: DiscountTimeRepository
 * @Description: 限时折扣
 * @author chenghongzhan
 * @date 2017年10月14日 上午11:19:44
 *
 */
public interface DiscountTimeRepository extends BaseRepository<DiscountTime, String> {

	@Query("select dt from DiscountTime dt,Goods g where g.id = dt.goodsId and g.shopId = dt.shopId	and dt.goodsId = ?1 and dt.isDeleted = 0 and ?2 BETWEEN dt.startTime AND dt.endTime")
	public DiscountTime findDiscountInfo(String goodsId,Date sysdate);

	@Query("FROM DiscountTime d WHERE d.id=?1 AND d.isDeleted=0")
	DiscountTime findById(String Id);

	@Query("SELECT dt FROM DiscountTime dt WHERE dt.shopId = ?1	AND dt.goodsId = ?2 AND dt.isDeleted = 0 AND dt.endTime>now() AND dt.startTime<now()")
	DiscountTime findByShopIdAndGoodsId(String shopId, String goodsId);

	@Query("FROM DiscountTime d WHERE d.goodsId=?1 AND d.isDeleted=0")
	List<DiscountTime> findByGoodsId(String GoodsId);
}
