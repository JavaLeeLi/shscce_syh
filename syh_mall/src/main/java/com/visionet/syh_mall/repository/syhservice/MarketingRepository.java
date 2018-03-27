package com.visionet.syh_mall.repository.syhservice;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.shop.Marketing;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: MarketingRepository
 * @Description: 营销方式
 * @author chenghongzhan
 * @date 2017年9月14日 下午5:13:05
 *
 */
public interface MarketingRepository extends BaseRepository<Marketing, String> {
	@Query("FROM Marketing m WHERE m.id=?1 AND m.isDeleted=0")
	public Marketing findById(String id);
	
	@Query("FROM Marketing m WHERE m.marketingCode=?1 AND m.isDeleted=0")
	public Marketing findByMarketingCode(String marketingCode);
	
}
