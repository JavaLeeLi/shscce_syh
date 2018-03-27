package com.visionet.syh_mall.repository.mobile;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * 订单商品Dao层
 * 
 * @author mulongfei
 * @date 2017年9月1日上午11:44:28
 */
public interface OrderGoodsRepository extends BaseRepository<OrderGoods, String> {
	@Query(value = "FROM OrderGoods o WHERE o.orderId=?1")
	List<OrderGoods> findByOrderId(String orderId);

	@Query(value = "FROM OrderGoods o WHERE o.orderId=?1")
	List<OrderGoods> findByOrderIdGoodId(String orderId);

	@Query(value = "FROM OrderGoods o WHERE o.goodsId=?1")
	List<OrderGoods> findBygoodsId(String goodsId);

	// 通过商品名称搜索订单ID
	@Query(value = "SELECT o.orderId FROM OrderGoods o WHERE o.goodsName LIKE %?1%")
	List<String> findBygoodsName(String goodsName);

	// 活动营销的销量
	@Query(value="SELECT SUM(o.goods_num) FROM tbl_order_goods o LEFT JOIN tbl_order ar ON o.order_id = ar.id WHERE ar.order_status_code = 'order_completed' AND o.goods_id=?1 AND o.update_time>?2  AND o.update_time<?3",nativeQuery=true)
	Integer findByGoodsId(String goodId, Date startTime, Date endTime);

	@Query(value = "SELECT o.orderId FROM OrderGoods o WHERE o.goodsId=?1")
	List<String> findByGoodsId(String goodId);

}
