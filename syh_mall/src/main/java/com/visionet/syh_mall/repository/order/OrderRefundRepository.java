package com.visionet.syh_mall.repository.order;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.order.OrderRefund;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * 订单退款Dao层
 * 
 * @author mulongfei
 * @date 2017年9月8日上午10:51:18
 */
public interface OrderRefundRepository extends BaseRepository<OrderRefund, String> {

	// 通过订单ID查询订单退款
	@Query("SELECT o FROM OrderRefund o WHERE o.refundStatusCode NOT IN ('refund_seller_refused','refund_syh_refused') AND o.orderId=?1 AND o.isDeleted=0")
	OrderRefund findByOrderId(String OrderId);
	
	@Query(value="FROM OrderRefund o WHERE o.refundOrderNo=?1 AND o.isDeleted=0")
	OrderRefund findByRefundOrderNo(String orderNo);
}
