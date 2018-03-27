package com.visionet.syh_mall.repository.syhservice;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.service.ShopsMarketing;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: ShopsMarketingRepository
 * @Description: TODO(怎知服务的Dao层)
 * @author chenghongzhan
 * @date 2017年8月31日 下午3:51:23
 *
 */
public interface ShopsMarketingRepository extends BaseRepository<ShopsMarketing, String> {

	// 查询店铺服务的方法
	@Query(value = "SELECT sm.marketingId From ShopsMarketing sm WHERE sm.shopId=?1")
	List<String> getmarketingIdByShopId(String shopID);

	@Query(value = "From ShopsMarketing sm WHERE sm.shopId=?1")
	List<ShopsMarketing> getmarketingByShopId(String shopID);

	@Query(value = "SELECT sm From ShopsMarketing sm WHERE sm.marketingId=?1")
	ShopsMarketing getShopsMarketing(String marketingID);

	@Query(value = "FROM ShopsMarketing s WHERE s.shopId=?1 AND s.marketingId=?2 AND s.status=0 AND s.validityDay>now()")
	ShopsMarketing findMarketingHasUse(String shopId, String marketingId);
}
