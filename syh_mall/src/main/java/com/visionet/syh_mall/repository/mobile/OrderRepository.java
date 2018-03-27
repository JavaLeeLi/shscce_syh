package com.visionet.syh_mall.repository.mobile;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @Author DM
 * @version ：2017年8月21日下午4:57:04 实体类
 */
public interface OrderRepository extends BaseRepository<Order, String> {
	@Query("from Order o order by o.createTime Desc")
	List<Order> findOrderList();
	
	@Query(value="FROM Order o WHERE o.orderStatusCode=?1 AND o.isDeleted=0")
	List<Order> findOrderListByStatus(String orderstatus);
	
	@Query(value ="select o.id as orderId,o.buyer_id as buyerId,o.receiver_name as buyerName,o.receiver_province as buyerProvince,o.receiver_city as buyerCity,o.total_price as orderSum,og.goods_id as orderGoodsId,og.goods_name as orderGoodsName,og.goods_real_price as orderGoodsPrice,o.order_status_code as orderTypeCode,o.create_time as orderTime from tbl_order o LEFT JOIN tbl_order_goods og on og.order_id=o.id where o.order_status_code='order_completed' order by o.create_time Desc",nativeQuery=true)
	public List<Object[]> findCompletedOrderList();

	@Query(value = "SELECT o.id FROM Order o WHERE o.orderSn=?1")
	List<String> findOrderListByOrderName(String Orders);

	@Query(value = "FROM Order o WHERE o.purchaseGoodsId=?1 AND o.isDeleted=0")
	List<Order> findAll(String purchaseGoodsId);

	@Query(value = "SELECT o.id FROM Order o WHERE o.orderSn LIKE %?1% AND o.isDeleted=0")
	List<String> findOrderIdByOrderName(String Orders);

	@Query("SELECT o FROM Order o WHERE o.id=?1 AND o.isDeleted=0")
	Order findByOrderId(String OrderId);
	
	@Query(value="SELECT o.* FROM tbl_order o,tbl_order_goods g WHERE o.id=g.order_id AND o.is_deleted=0 AND o.buyer_id=?1 AND (o.id LIKE ?2 OR o.buyer_id LIKE ?2 OR o.order_sn LIKE ?2 OR o.purchase_goods_id LIKE ?2 OR o.pay_kinds_id LIKE ?2 OR o.pay_kinds_name LIKE ?2 OR o.buyer_phone LIKE ?2 OR o.shop_id LIKE ?2 OR o.shop_owner_id LIKE ?2 OR o.receiver_name LIKE ?2 OR o.receiver_phone LIKE ?2 OR o.receiver_province LIKE ?2 OR o.receiver_city LIKE ?2 OR o.receiver_area LIKE ?2 OR o.receiver_address LIKE ?2 OR g.goods_name LIKE ?2) AND o.group_status!='团购中' GROUP BY o.id",nativeQuery=true)
	List<Order> getBuyOrders(String UserId,String keyWord);
	
	@Query(value="SELECT o.* FROM tbl_order o,tbl_order_goods g WHERE o.id=g.order_id AND o.is_deleted=0 AND o.shop_owner_id=?1 AND (o.id LIKE ?2 OR o.buyer_id LIKE ?2 OR o.order_sn LIKE ?2 OR o.purchase_goods_id LIKE ?2 OR o.pay_kinds_id LIKE ?2 OR o.pay_kinds_name LIKE ?2 OR o.buyer_phone LIKE ?2 OR o.shop_id LIKE ?2 OR o.shop_owner_id LIKE ?2 OR o.receiver_name LIKE ?2 OR o.receiver_phone LIKE ?2 OR o.receiver_province LIKE ?2 OR o.receiver_city LIKE ?2 OR o.receiver_area LIKE ?2 OR o.receiver_address LIKE ?2 OR g.goods_name LIKE ?2) AND o.group_status!='团购中' GROUP BY o.id",nativeQuery=true)
	List<Order> getSellerOrders(String UserId,String keyWord);
	
	public Order findByOrderSn(String orderSn);
	
	@Query(value="SELECT COUNT(1) FROM tbl_order o WHERE o.buyer_id=?1 OR o.shop_owner_id=?1 AND o.order_status_code NOT IN ('order_closed','order_completed') AND o.is_deleted=0",nativeQuery=true)
	public Integer getIncompleteOrderNum(String currentUserId);
	
	@Query(value="SELECT COUNT(1) FROM tbl_order o WHERE o.buyer_id=?1 OR o.shop_owner_id=?1 AND o.order_status_code NOT IN ('order_closed') AND o.is_deleted=0",nativeQuery=true)
	public Integer getOrderNum(String currentUserId);
	
	@Query(value="SELECT SUM(o.total_price) FROM tbl_order o WHERE o.shop_owner_id=?1 AND o.order_status_code='order_unreceied' AND o.is_deleted=0",nativeQuery=true)
	public BigDecimal findTotalByShopOwnerId(String shopOwnerId);
	
	@Query(value="SELECT SUM(o.express_fee) FROM tbl_order o WHERE o.shop_owner_id=?1 AND o.order_status_code='order_unreceied' AND o.is_deleted=0",nativeQuery=true)
	public BigDecimal findExpressByShopOwnerId(String shopOwnerId);
	
	@Query(value="SELECT o.order_sn, o.create_time, o.total_price, o.express_fee, bu.alias_name bn, su.alias_name sn, s.shop_name, o.order_status_code, o.express_bill_no FROM tbl_order o, tbl_user bu, tbl_user su, tbl_shop s WHERE o.buyer_id = bu.id AND o.shop_owner_id = su.id AND o.shop_id = s.id",nativeQuery=true)
	List<Object[]> findAllOrderByExport();
}
