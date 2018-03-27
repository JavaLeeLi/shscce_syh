package com.visionet.syh_mall.repository.mobile;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.PurchaseAddressLink;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 求购地址Dao
 * @author mulongfei
 * @date 2017年9月11日上午10:16:48
 */
public interface PurchaseAddressLinkRepository extends BaseRepository<PurchaseAddressLink, String> {
	@Query(value="FROM PurchaseAddressLink p WHERE p.goodsId=?1 AND p.isDeleted=0")
	PurchaseAddressLink findOne(String goodsDraftID);
}
