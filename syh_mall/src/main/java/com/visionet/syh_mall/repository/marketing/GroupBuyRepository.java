package com.visionet.syh_mall.repository.marketing;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.marketing.GroupBuy;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * 团购
 * 
 * @author xiaofb
 * @time 2017年8月29日
 */
public interface GroupBuyRepository extends BaseRepository<GroupBuy, String> {

	@Query("from GroupBuy gb,Goods g where gb.goodsId = g.id and gb.shopId = g.shopId and gb.isDeleted = 0 and gb.goodsId = ?1")
	GroupBuy findGroupBuyInfos(String goodsId);

	@Query("FROM GroupBuy gb WHERE gb.id=?1 AND gb.isDeleted = 0")
	GroupBuy findById(String Id);
	
	@Query("FROM GroupBuy gb,GroupDetail gd WHERE gb.id = gd.groupId AND gd.id=?1 AND gd.isDeleted = 0")
	GroupBuy findByGroupDetailId(String groupDetailId);

	@Query("FROM GroupBuy gb WHERE gb.goodsId=?1 AND gb.isDeleted = 0 AND (?2 BETWEEN gb.startTime AND gb.endTime OR ?3 BETWEEN gb.startTime AND gb.endTime)")
	GroupBuy findByGoodsId(String goodsId,Date startTime,Date endTime);
}
