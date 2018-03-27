package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.SupplyRecord;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 求购响应记录Dao
 * @author mulongfei
 * @date 2017年9月11日下午5:48:23
 */
public interface SupplyRecordRepository extends BaseRepository<SupplyRecord, String> {
	@Query(value="FROM SupplyRecord s WHERE s.goodsId=?1 AND s.isDeleted=0 order by createTime Desc")
	List<SupplyRecord> findBygoodsId(String GoodsId);
	@Query(value="FROM SupplyRecord s WHERE s.supplyOrderId=?1 AND s.isDeleted=0")
	SupplyRecord findBysupplyOrderId(String orderId);
	@Query(value="SELECT s FROM SupplyRecord s,Order o WHERE s.supplyOrderId = o.id AND s.goodsId = ?1 AND o.orderStatusCode IN ('order_unprocessed','order_unreceied','order_uncomment','order_completed') AND s.isDeleted=0 AND o.isDeleted=0")
	List<SupplyRecord> findBygoodsIdAndOrderStatus(String id);
}
