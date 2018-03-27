package com.visionet.syh_mall.repository.mobile;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.shop.FulfilRemit;
import com.visionet.syh_mall.repository.BaseRepository;

public interface FulifilRemitRepository extends BaseRepository<FulfilRemit, String> {

	@Query("SELECT f FROM FulfilRemit f WHERE f.shopId=?1 AND f.isDeleted=0 ORDER BY f.fulfilAmt DESC")
	List<FulfilRemit> findByShopId(String shopID);

	@Query("FROM FulfilRemit f WHERE f.id=?1 AND f.isDeleted=0")
	FulfilRemit findByID(String ID);
	
	List<FulfilRemit> findByFulfilAmtAndShopIdAndIsDeletedOrderByRemitAmtDesc(BigDecimal fulfilAmt,String shopId,int isDeleted,BigDecimal remitAmt);

	FulfilRemit findByFulfilAmtAndShopIdAndIsDeleted(BigDecimal fulfilAmt, String shopId, int isDeleted);

	@Query("SELECT f FROM FulfilRemit f where f.giftGoodsId=?1 and f.isDeleted=0")
	List<FulfilRemit> findByGiftGoodsId(String id);
}
