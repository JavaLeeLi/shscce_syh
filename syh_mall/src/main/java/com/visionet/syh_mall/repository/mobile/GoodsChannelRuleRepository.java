package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.visionet.syh_mall.entity.goods.GoodsChannelRule;

/**
 * @ClassName: GoodsChannelRuleRepository
 * @Description: 商品分销
 * @author chenghongzhan
 *
 */
public interface GoodsChannelRuleRepository
		extends PagingAndSortingRepository<GoodsChannelRule, String>, JpaSpecificationExecutor<GoodsChannelRule> {

	@Query("FROM GoodsChannelRule g WHERE g.isDeleted=0 AND g.id=?1")
	GoodsChannelRule findById(String id);

	@Query("FROM GoodsChannelRule g WHERE g.isDeleted=0 AND g.shopId=?1")
	List<GoodsChannelRule> findAll(String shopId);
	
	
}
